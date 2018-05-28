package ru.vetoshkin.store.admin.dao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.core.Initialize;
import ru.vetoshkin.store.util.HikariPool;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
@Service
@Initialize
public class AdminService {
    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private static final Map<Integer, Admin> adminCache = new ConcurrentHashMap<>();
    private static MessageDigest md;


    /**
     * Инициализация
     */
    public static void init() {
        try {
            md = MessageDigest.getInstance("SHA-512");


            try (Connection connection = HikariPool.getSource().getConnection()) {
                connection.setAutoCommit(false);

                String method = "{? = call public.get_admins()}";

                CallableStatement statement = connection.prepareCall(method);
                statement.registerOutParameter(1, Types.OTHER);

                logger.info(method);
                statement.execute();

                ResultSet set = (ResultSet) statement.getObject(1);
                while (set.next()) {

                    Admin admin = new Admin();
                    admin.setId(set.getInt(1));
                    admin.setLogin(set.getString(2));
                    admin.setPassword(set.getString(3));

                    adminCache.put(admin.getId(), admin);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.warn(e.getMessage());
        }
    }


    /**
     * Сохранить админа
     */
    public static Admin save(Admin admin) {
        try (Connection connection = HikariPool.getSource().getConnection()) {
            connection.setAutoCommit(false);

            String method = "{? = call public.save_admin(?, ?)}";
            String hash = getHash(admin.getLogin());

            CallableStatement statement = connection.prepareCall(method);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, admin.getLogin());
            statement.setString(3, hash);

            logger.info(method);
            statement.execute();

            ResultSet set = (ResultSet) statement.getObject(1);
            if (set.next()) {
                admin.setId(set.getInt(1));
                admin.setPassword(hash);

                adminCache.put(admin.getId(), admin);
            }

        } catch (SQLException e) {
            logger.warn(e.getMessage());
        }
        return admin;
    }


    /**
     * Проверка существования админа
     */
    public static Admin exist(Admin admin) {
        String hash = getHash(admin.getPassword());

        for (Admin inCacheAdmin : adminCache.values()) {
            if (inCacheAdmin.getLogin().equals(admin.getLogin()) && inCacheAdmin.getPassword().equals(hash))
                return inCacheAdmin;
        }

        return null;
    }



    private static String getHash(String password) {
        byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
        StringBuilder sb = new StringBuilder();

        for (byte aHash : hash) {
            sb.append(Integer.toString((aHash & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }


    /**
     * Получить админа по ИД
     */
    public static Admin getAdmin(int id) {
        return adminCache.get(id);
    }
}
