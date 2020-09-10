package com.movie;

import com.movie.Serivce.ElasticSearchService;
import com.movie.Entity.User;
import com.movie.Util.RecommendUtils;
import com.movie.redis.CacheObject.UserInfoRe;
import com.movie.redis.RedisApi;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.concurrent.TimeUnit;

@SpringBootTest
class MovieBookApplicationTests {

    @Autowired
    private ElasticSearchService elasticSearchService;
    private RedisApi redisApi;

    @Autowired
    private UserInfoRe userInfoRe;

    @Test
    void contextLoads() throws Exception{
        elasticSearchService.importCommentToEs();
    }


    @Test
    public void testRedis() throws InvalidKeySpecException, NoSuchAlgorithmException {


        User user = new User();
        user.setUsername("cly");
        user.setPassword("lcydb");
        user.setUserSex("asdasd");
        //redisApi.setValue(RecommendUtils.getKey());
        //redisApi.setValue("clysb","you are true",3, TimeUnit.DAYS);

        userInfoRe.save("123456",user);
        //System.out.println(user);
        System.out.println(userInfoRe.get("123456"));
        //redisApi.setValue("cly",user,3,TimeUnit.DAYS);
        //System.out.println(redisApi.getObject("cly"));
        //System.out.println(redisApi.getString("clysb"));
        //System.out.println(redisApi.hget("clysb","movie"));
    }


}
