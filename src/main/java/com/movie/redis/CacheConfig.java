//package com.movie.redis;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.CachingConfigurerSupport;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.data.redis.serializer.StringRedisSerializer;
//
//@Configuration
//@Slf4j
//public class CacheConfig extends CachingConfigurerSupport {
//
//    @Bean
//    public StringRedisTemplate stringRedisTemplate(LettuceConnectionFactory redisConnectionFactory) {
//        StringRedisTemplate template = new StringRedisTemplate();
//        template.setKeySerializer(new PrefixRedisSerializer());
//        template.setValueSerializer(new StringRedisSerializer());
//        template.setConnectionFactory(redisConnectionFactory);
//        template.setHashKeySerializer(new StringRedisSerializer());
//        template.setHashValueSerializer(new StringRedisSerializer());
//        template.afterPropertiesSet();
//        return template;
//    }
//
//}