package com.movie.redis.CacheObject;


import com.movie.Dto.MovieDto;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import com.movie.redis.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Component
public class MovieInfoRe {
    @Autowired
    private RedisTemplate<String, MovieDto> redisTemplate;

    /**
     * 命令空间
     */
    private String namespace = RedisKeys.Movie;

    /**
     * 缓存默认过期时间为7天
     */
    private Integer CLIENT_EXPIRE_DEFAULT = 7 * 24 * 60 * 60;

    /**
     * 保存
     * @param code

     */
    public void save(String code, MovieDto movieDto) {
        save(code,movieDto, CLIENT_EXPIRE_DEFAULT, TimeUnit.SECONDS);
    }

    /**
     * 保存key-value并设置过期时间
     * @param code

     * @param expireIn
     * @param unit
     */
    public void save(String code, MovieDto movieDto, int expireIn, TimeUnit unit) {
        System.out.println(getKey(code));
        System.out.println(movieDto);
        redisTemplate.opsForValue().set(getKey(code), movieDto, expireIn, unit);
    }

    /**
     * 从缓存中获取account

     * @return
     */
    public MovieDto get(String movieId) {
        return redisTemplate.opsForValue().get(getKey(movieId));
    }

    /**
     * 删除缓存

     * @return
     */
    public void remove(String MovieId) {
        redisTemplate.delete(getKey(MovieId));
    }


    /**
     * 生成redis的key

     * @return
     */
    private String getKey(String MovieId) {
        System.out.println(namespace + MovieId);
        return namespace + MovieId;
    }
}
