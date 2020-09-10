package com.movie;

import com.movie.Serivce.ElasticSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MovieBookApplicationTests {

    @Autowired
    private ElasticSearchService elasticSearchService;
    @Test
    void contextLoads() throws Exception{
        elasticSearchService.importCommentToEs();
    }

}
