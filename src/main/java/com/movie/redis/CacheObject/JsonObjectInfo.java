package com.movie.redis.CacheObject;



import com.alibaba.fastjson.JSONObject;

import com.movie.redis.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.zip.CheckedOutputStream;

@Service
public class JsonObjectInfo {
    @Autowired
    private RedisTemplate<String, JSONObject> redisTemplate;

    /**
     * 命令空间
     */
    private String namespace = RedisKeys.JSON;

    /**
     * 缓存默认过期时间为7天
     */
    private Integer CLIENT_EXPIRE_DEFAULT = 7 * 24 * 60 * 60;

    /**
     * 保存
     * @param code

     */
    public void save(String code, JSONObject movie,String name) {
        System.out.println(movie);
        save(code,movie, CLIENT_EXPIRE_DEFAULT, TimeUnit.SECONDS,name);
    }

    /**
     * 保存key-value并设置过期时间
     * @param code

     * @param expireIn
     * @param unit
     */
    public void save(String code, JSONObject movie, int expireIn, TimeUnit unit,String name) {
        System.out.println(getKey(code,name)+"llllll");
        redisTemplate.opsForValue().set(getKey(code,name), movie, expireIn, unit);
    }

    /**
     * 从缓存中获取account

     * @return
     */
    public JSONObject get(String movieId,String name) {
        return redisTemplate.opsForValue().get(getKey(movieId,name));
    }

    /**
     * 删除缓存

     * @return
     */
    public void remove(String MovieId,String name) {
        redisTemplate.delete(getKey(MovieId,name));
    }


    /**
     * 生成redis的key

     * @return
     */
    private String getKey(String MovieId,String name) {
        System.out.println(namespace + name+MovieId);
        return namespace + MovieId;
    }
}
