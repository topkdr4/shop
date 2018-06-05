package ru.vetoshkin.store.user.dao;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.admin.AdminAuth;
import ru.vetoshkin.store.admin.dao.AdminService;
import ru.vetoshkin.store.user.User;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
public class UserStorage {
    private static final LoadingCache<String, User> sessionCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, User>() {

                @Override
                public User load(String session) throws Exception {
                    User result = new User();

                    try (Connection connection = HikariPool.getSource().getConnection()) {
                        connection.setAutoCommit(false);

                        CallableStatement statement = connection.prepareCall("{? = call client.get_client(?)}");
                        statement.registerOutParameter(1, Types.OTHER);
                        statement.setString(2, session);

                        statement.execute();

                        ResultSet set = (ResultSet) statement.getObject(1);

                        if (set.next()) {
                            result.setEmail(set.getString(1));
                            result.setPassword(set.getString(2));
                            result.setName(set.getString(3));
                            result.setDispatch(set.getBoolean(4));
                        }
                    }
                    return result;
                }

            });




    public static User getUser(String sessionId) throws ExecutionException {
        return sessionCache.get(sessionId);
    }



    /**
     * Сохранить сессию
     */
    public static String saveSession(User user) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            long time = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(AdminAuth.expiry);

            CallableStatement statement = connection.prepareCall("{? = call client.save_session(?, ?)}");
            statement.registerOutParameter(1, Types.VARCHAR);
            statement.setString(2, user.getEmail());
            statement.setTimestamp(3, new Timestamp(time));

            statement.execute();
            String sessionId = statement.getString(1);

            sessionCache.put(sessionId, user);

            return sessionId;
        } catch (SQLException e) {
            return null;
        }
    }



    /**
     * Регистрация клиента
     */
    public static void registration(User user) throws SQLException {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(true);

            String hash = AdminService.getHash(user.getPassword());

            CallableStatement statement = connection.prepareCall("{call client.save_client(?, ?, ?)}");
            statement.setString(1, user.getEmail());
            statement.setString(2, hash);
            statement.setString(3, user.getName());
            statement.setBoolean(4, user.isDispatch());

            statement.execute();
        }
    }



    /**
     * Проверка существования Клиента
     */
    public static User exist(User user) {
        String hash = AdminService.getHash(user.getPassword());
        User result = null;
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call client.get_client(?, ?)}");
            statement.registerOutParameter(1, Types.OTHER);
            statement.setString(2, user.getEmail());
            statement.setString(3, hash);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next()) {
                result = new User();
                result.setEmail(set.getString(1));
                result.setPassword(set.getString(2));
                result.setName(set.getString(3));
                result.setDispatch(set.getBoolean(4));
            }

            return result;
        } catch (SQLException e) {
            return result;
        }
    }



    /**
     * Получить все emails для отправки
     */
    public static List<String> getAllEmailsForSend() {
        List<String> result = new LinkedList<>();
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            CallableStatement statement = connection.prepareCall("{? = call client.get_all_client()}");
            statement.registerOutParameter(1, Types.OTHER);

            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            while (set.next()) {
                result.add(set.getString(1));
            }

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            return result;
        }
    }

}
