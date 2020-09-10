package com.movie.redis.CacheObject;


import com.movie.Entity.User;
import com.movie.redis.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.jws.soap.SOAPBinding;
import javax.sound.midi.Soundbank;
import java.util.concurrent.TimeUnit;

@Component
public class UserInfoRe {

    @Autowired
    private RedisTemplate<String, User>redisTemplate;

    /**
     * 命令空间
     */
    private String namespace = RedisKeys.User;

    /**
     * 缓存默认过期时间为7天
     */
    private Integer CLIENT_EXPIRE_DEFAULT = 7 * 24 * 60 * 60;

    /**
     * 保存
     * @param code

     */
    public void save(String code, User user) {
        save(code, user, CLIENT_EXPIRE_DEFAULT, TimeUnit.SECONDS);
    }

    /**
     * 保存key-value并设置过期时间
     * @param code
     * @param ssoClient
     * @param expireIn
     * @param unit
     */
    public void save(String code, User ssoClient, int expireIn, TimeUnit unit) {
        System.out.println(getKey(code));
        redisTemplate.opsForValue().set(getKey(code), ssoClient, expireIn, unit);
    }

    /**
     * 从缓存中获取account
     * @param clientId
     * @return
     */
    public User get(String clientId) {
        return redisTemplate.opsForValue().get(getKey(clientId));
    }

    /**
     * 删除缓存
     * @param clientId
     * @return
     */
    public void remove(String clientId) {
        redisTemplate.delete(getKey(clientId));
    }


    /**
     * 生成redis的key
     * @param clientId
     * @return
     */
    private String getKey(String clientId) {
        System.out.println(namespace + clientId);
        return namespace + clientId;
    }
}
