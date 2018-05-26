package ru.vetoshkin.store.product.dao;
import com.google.common.cache.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;





/**
 * Ветошкин А.В. РИС-16бзу
 * */
public class ImageStorage {
    private static final LoadingCache<String, byte[]> images = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .build(new CacheLoader<String, byte[]>() {
                @Override
                public byte[] load(String key) throws Exception {
                    return new byte[0];
                }
            });


    public static byte[] getImage(String id) throws ExecutionException {
        return images.get(id);
    }

}
