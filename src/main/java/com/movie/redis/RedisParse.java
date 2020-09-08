package com.movie.redis;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.concurrent.TimeUnit;


@Service
public class RedisParse {


        @Autowired
        private RedisApi redisApi;

        /**
         * 缓存默认过期时间为7天
         */
        private Integer EXPIRE_DEFAULT = 7 * 24 * 60 * 60;



        public void saveObject(String key, JSONObject object,String namespace){
                String jsonString = JSONObject.toJSONString(object);
                redisApi.setValue(namespace+key,jsonString,EXPIRE_DEFAULT, TimeUnit.SECONDS);
        }

        public Object getObject(String key,String namspcace){

                String redisApiString = redisApi.getString(namspcace+key);
                Object o = JSONObject.parseObject(redisApiString);
                return  o;
        }

        public Boolean delObject(String key,String namespace){
                Boolean bool = redisApi.delKey(namespace+key);
                return  bool;
        }



        //public Object getObject(String key){}



}
