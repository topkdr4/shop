package ru.vetoshkin.store.admin.dao;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.admin.AdminAuth;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.concurrent.*;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class SessionService {
    private static final ScheduledExecutorService sessionIdCleaner = Executors.newScheduledThreadPool(1);


    public static void init() {
        sessionIdCleaner.scheduleWithFixedDelay(() -> {
            try (Connection connection = HikariPool.getSource().getConnection()) {
                connection.setAutoCommit(true);

                String method = "{call public.remove_sessions(?)}";

                CallableStatement statement = connection.prepareCall(method);
                statement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));

                statement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }, 0, 1, TimeUnit.HOURS);

    }


    private static final LoadingCache<String, Admin> sessionCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Admin>() {

                @Override
                public Admin load(String session) throws Exception {
                    Admin result = new Admin();

                    try (Connection connection = HikariPool.getSource().getConnection()) {
                        connection.setAutoCommit(false);

                        String method = "{? = call public.get_admin(?)}";

                        CallableStatement statement = connection.prepareCall(method);
                        statement.registerOutParameter(1, Types.OTHER);
                        statement.setString(2, session);

                        statement.execute();

                        ResultSet set = (ResultSet) statement.getObject(1);

                        if (set.next()) {
                            result = AdminService.getAdmin(set.getInt(1));
                        }

                    }
                    return result;
                }

            });


    public static Admin getAdmin(String sessionId) throws ExecutionException {
        return sessionCache.get(sessionId);
    }


    /**
     * Сохранить сессию
     */
    public static String save(Admin admin) {
        try (Connection connection = HikariPool.getSource().getConnection()) {

            String method = "{? = call public.save_session(?, ?)}";
            long time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(AdminAuth.expiry);

            CallableStatement statement = connection.prepareCall(method);
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setInt(2, admin.getId());
            statement.setTimestamp(3, new Timestamp(time));

            statement.execute();
            String sessionId = statement.getString(1);

            sessionCache.put(sessionId, admin);

            return sessionId;
        } catch (SQLException e) {
            return null;
        }
    }

}
