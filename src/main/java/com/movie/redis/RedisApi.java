package com.movie.redis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class RedisApi {

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redis;


    @Resource
    RedisTemplate<String, Object> redisTemplate;

    public boolean exist(String key) {
        try {
            return Objects.requireNonNull(redis.hasKey(key));
        } catch (Exception e) {
            log.warn("redis exist error key={}", key, e);
            return false;
        }
    }

    public void expire(String key, long time, TimeUnit timeUnit) {
        try {
            redis.expire(key, time, timeUnit);
        } catch (Exception e) {
            log.warn("redis expire error key={}", key, e);
        }
    }

    public Boolean  delKey(String key) {
        Boolean bool;
        try {
            bool = redis.delete(key);
        } catch (Exception e) {
            log.warn("redis delKey error key={}", key, e);
            bool =false;
        }
        return  bool;
    }

    public void delKeys(List<String> keys) {
        try {
            redis.delete(keys);
        } catch (Exception e) {
            log.warn("redis delKeys error keys={}", keys, e);
        }
    }


    public String getString(String key) {
        try {
            return redis.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("redis getString error key={}", key, e);
            return null;
        }
    }

    public Object getObject(String key) {
        try {
            return redisTemplate.opsForValue().get(key);
        } catch (Exception e) {
            log.warn("redis getString error key={}", key, e);
            return null;
        }
    }

    public void delHashKey(String key, String name) {
        try {
            HashOperations<String, String, String> opt = redis.opsForHash();
            opt.delete(key, name);
        } catch (Exception e) {
            log.warn("redis delHashKey error key={}, name={}", key, name, e);
        }
    }

    public String hget(String key, String name) {
        try {
            HashOperations<String, String, String> opt = redis.opsForHash();
            return opt.get(key, name);
        } catch (Exception e) {
            log.warn("redis hget error key={}, name={}", key, name, e);
            return null;
        }
    }

    public Map<String, String> hgetAll(String key) {
        try {
            HashOperations<String, String, String> opt = redis.opsForHash();
            return opt.entries(key);
        } catch (Exception e) {
            log.warn("redis hgetAll error key={}", key, e);
            return new HashMap<>();
        }
    }

    public void hdel(String key, String field) {
        try {
            HashOperations<String, String, String> opt = redis.opsForHash();
            opt.delete(key, field);
        } catch (Exception e) {
            log.warn("redis hdel error key={}, field={}", key, field, e);
        }
    }

    public List<String> hmget(String key, List<String> hashKeys) {
        try {
            HashOperations<String, String, String> opt = redis.opsForHash();
            return opt.multiGet(key, hashKeys);
        } catch (Exception e) {
            log.warn("redis hmget error key={}, hashKeys={}", key, hashKeys, e);
        }
        return Collections.emptyList();
    }

    public void hset(String key, String name, String value, long time, TimeUnit unit) {
        try {
            redis.executePipelined((RedisCallback) connection -> {
                HashOperations<String, String, String> opt = redis.opsForHash();
                connection.openPipeline();
                opt.put(key, name, value);
                redis.expire(key, time, unit);
                connection.closePipeline();
                return null;
            });
        } catch (Exception e) {
            log.warn("redis hset error key={}, name={}, value={}", key, name, value, e);
        }
    }

    public void publish(String key, String message) {
        try {
            redis.convertAndSend(key, message);
        } catch (Exception e) {
            log.warn("redis publish error key={}, message={}", key, message, e);
        }
    }


    public void setValue(String key, String value, long time, TimeUnit unit) {
        try {
            redis.opsForValue().set(key, value, time, unit);
        } catch (Exception e) {
            log.warn("redis setValue error key={}, value={}", key, value, e);
        }
    }

    public void setValue(String key,Object value,long time,TimeUnit unit) {
        try {
            redisTemplate.opsForValue().set(key,value,time,unit);
        }catch (Exception e){
            log.warn("redis setValue error key={}, value={}", key, value, e);
        }
    }

    public void setValue(String key, String value, long time) {
        try {
            redis.executePipelined((RedisCallback) connection -> {
                connection.openPipeline();
                redis.opsForValue().set(key, value);
                redis.expireAt(key, new Date(time));
                connection.closePipeline();
                return null;
            });
        } catch (Exception e) {
            log.warn("redis setValue error key={}, value={}", key, value, e);
        }
    }


}