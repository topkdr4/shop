package ru.vetoshkin.store.admin.dao;
import ru.vetoshkin.store.admin.Admin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class AdminService {
    private static final Map<Integer, Admin> adminCache = new ConcurrentHashMap<>();



    public static Admin save(Admin admin) {

        return admin;
    }



    public static Admin getAdmin(String sessionId) {
        return null;
    }
}
