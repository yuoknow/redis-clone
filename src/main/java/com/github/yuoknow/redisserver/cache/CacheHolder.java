package com.github.yuoknow.redisserver.cache;

import lombok.experimental.UtilityClass;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@UtilityClass
public class CacheHolder {
    private static final Map<String, String> CACHE = new ConcurrentHashMap<>();

    public static String get(String key) {
        return CACHE.get(key);
    }

    public static void set(String key, String value) {
        CACHE.put(key, value);
    }
}
