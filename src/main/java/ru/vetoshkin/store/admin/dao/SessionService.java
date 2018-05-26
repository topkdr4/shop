package ru.vetoshkin.store.admin.dao;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import ru.vetoshkin.store.admin.Admin;
import ru.vetoshkin.store.util.HikariPool;

import java.sql.Connection;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class SessionService {
    private static final ScheduledFuture<?> sessionIdCleaner = Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(() -> {

    }, 0, 24, TimeUnit.HOURS);


    private static final LoadingCache<String, Admin> sessionCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Admin>() {

                @Override
                public Admin load(String key) throws Exception {
                    try (Connection connection = HikariPool.getSource().getConnection()) {
                        // TODO
                    }
                    return null;
                }

            });


    public static Admin getAdmin(String sessionId) throws ExecutionException {
        return sessionCache.get(sessionId);
    }

}
