package org.aidan.ssm.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.concurrent.TimeUnit;

/**
 * @author Aidan
 * @创建时间：2019/6/16 4:12 PM
 * @描述: TODO
 */
public class RedisUtils {

    public static Object get(String key) {

        RedisTemplate redisTemplate = SpringHelper.getBean(RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();

        return valueOperations.get(key);
    }

    public static void set(String key, Object value) {
        RedisTemplate redisTemplate = SpringHelper.getBean(RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    public static void set(String key, Object value, long expires, TimeUnit timeUnit) {
        RedisTemplate redisTemplate = SpringHelper.getBean(RedisTemplate.class);
        ValueOperations valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, expires, timeUnit);
    }

    public static Long currentTime() {
        // TODO
        return null;
    }
}
