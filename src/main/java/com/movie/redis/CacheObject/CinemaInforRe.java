package com.movie.redis.CacheObject;

import com.movie.Dto.CinemaDto;
import com.movie.Entity.User;
import com.movie.redis.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;

public class CinemaInforRe {

    @Autowired
    private RedisTemplate<String, CinemaDto> redisTemplate;

    /**
     * 命令空间
     */
    private String namespace = RedisKeys.Cinema;

    /**
     * 缓存默认过期时间为7天
     */
    private Integer CLIENT_EXPIRE_DEFAULT = 7 * 24 * 60 * 60;

    /**
     * 保存
     * @param code

     */
    public void save(String code, CinemaDto cinemaDto) {
        save(code, cinemaDto, CLIENT_EXPIRE_DEFAULT, TimeUnit.SECONDS);
    }

    /**
     * 保存key-value并设置过期时间
     * @param code

     * @param expireIn
     * @param unit
     */
    public void save(String code, CinemaDto cinemaDto, int expireIn, TimeUnit unit) {
        System.out.println(getKey(code));
        redisTemplate.opsForValue().set(getKey(code), cinemaDto, expireIn, unit);
    }

    /**


     * @return
     */
    public CinemaDto get(String Id) {
        return redisTemplate.opsForValue().get(getKey(Id));
    }

    /**


     * @return
     */
    public void remove(String Id) {
        redisTemplate.delete(getKey(Id));
    }


    /**
     * 生成redis的key

     * @return
     */
    private String getKey(String id) {
        System.out.println(namespace + id);
        return namespace + id;
    }
}
