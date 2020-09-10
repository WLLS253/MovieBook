package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvReader;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.movie.Entity.Comment;
import com.movie.Entity.CommentEs;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import com.movie.Repository.CommentEsRepo;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Util.RecommendUtils;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.map.HashedMap;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.ResultsExtractor;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.naming.directory.SearchResult;
import javax.xml.transform.Source;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.rangeQuery;

/**
 * ElasticSearchService
 *
 * @author pqdong
 * @since 2020/03/31
 */

@Service
@Slf4j
public class ElasticSearchService {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentEsRepo commentEsRepo;

    public Map<Long,List<Pair<Long,Float>>> getAllIndex() {
        Sort s = Sort.by(Sort.Direction.ASC,"userId");
        SortBuilder sortBuilder= SortBuilders.fieldSort("userId").order(SortOrder.ASC);
        //TermsAggregationBuilder aggregation = AggregationBuilders.terms("comment_num").field("userId").size(2000);
        String[] include = {"userId","movieId","score"};
        SourceFilter sourceFilter =  new FetchSourceFilter(include,null);
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
                .withSourceFilter(sourceFilter)
                .withPageable(PageRequest.of(0, 20000,s));
//                .addAggregation(aggregation);

        SearchQuery query = builder.build();
        //List<CommentEs> index = elasticsearchTemplate.queryForList(query, CommentEs.class);

        SearchResponse result = elasticsearchTemplate.query(query, new ResultsExtractor<SearchResponse>() {
            @Override
            public SearchResponse extract(SearchResponse response) {
                return response;
            }
        });

        //List<Aggregation> aggregations = result.getAggregations().asList();
        //ParsedLongTerms comment_num = (ParsedLongTerms)aggregations.get(0);
        SearchHits commentEsList = result.getHits();
        List<Map<String,Object>> comments = new ArrayList<>();
        Map<Long,List<Pair<Long,Float>>> comment_info = new HashedMap();
//        List terms = new JSONArray();
        for (SearchHit hit: commentEsList) {
            comments.add(hit.getSourceAsMap());
        }
        for (Map<String,Object> c:comments) {
            Long usr_id =  ((Integer)c.get("userId")).longValue();
            Long movie_id = ((Integer)c.get("movieId")).longValue();
            Float score = ((Double) c.get("score")).floatValue();
            if(comment_info.containsKey(usr_id)){
                List<Pair<Long,Float>> items = comment_info.get(usr_id);
                items.add(new Pair(movie_id,score));
            }else{
                List<Pair<Long,Float>> items = new ArrayList();
                items.add(new Pair(movie_id,score));
                comment_info.put(usr_id,items);
            }
        }
        return comment_info;
    }
    public List<CommentEs> getAllComment(long userId){
        List<CommentEs> commentEs = commentEsRepo.findByUserId(userId);
        return commentEs;
   }
    // 更新 对应的用户的所有评论，异步处理
    @Async("taskExecutor")
    public void updateAllComment(User userEntity) {
        List<CommentEs> commentEs = commentEsRepo.findByUserId(userEntity.getId());
        List queries = new ArrayList();
        int counter = 0;
        for (CommentEs comment : commentEs) {
            comment.setUserAvatar(userEntity.getShowimage());
            comment.setUserName(userEntity.getUsername());
            IndexQuery indexQuery = new IndexQuery();
            indexQuery.setId(Optional.ofNullable(comment.getCommentId())
                    .orElse(System.currentTimeMillis())
                    .toString());
            try {
                indexQuery.setSource(new ObjectMapper().writeValueAsString(comment));
            } catch (JsonProcessingException e) {
                log.info("{}", e.getMessage());
                continue;
            }
            indexQuery.setIndexName("comment");
            indexQuery.setType("comment");
            queries.add(indexQuery);
            //分批提交修改
            if (counter != 0 && counter % 1000 == 0) {
                elasticsearchTemplate.bulkIndex(queries);
                queries.clear();
            }
            counter++;
        }
        // 提交不足量修改
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
        }
        if (counter > 0) {
            elasticsearchTemplate.refresh("comment");
        }
        log.info("commentEs has update" + counter);
    }


    // 用于将csv文件中的数据导入到es表中，在处理用户昵称和电影名称时考虑到速度，不查询数据库，用现有数据代替
    public long importCommentToEs() throws Exception{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
//        try {
            ArrayList<String[]> csvList = new ArrayList<String[]>();
            CsvReader reader = new CsvReader("C:\\Users\\BlueBurger\\Documents\\Tencent Files\\2017348541\\FileRecv\\comments.csv", ',', StandardCharsets.UTF_8);
            reader.readHeaders(); //跳过表头,不跳可以注释掉

            while (reader.readRecord()) {
                csvList.add(reader.getValues()); //按行读取，并把每一行的数据添加到list集合
            }
            reader.close();
//            Date d = dateFormat.parse("2018/08/18 20:50");
//            CommentEs e= CommentEs.builder()
//                    .createdTime(dateFormat.parse("2018/08/18 20:50"))
//                    .updateTime(dateFormat.parse("2018/08/18 20:50"))
//                    .build();
            List queries = new ArrayList();
            int counter = 0;
            for (String[] comment : csvList) {
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(comment[0]);

                indexQuery.setSource(new ObjectMapper().writeValueAsString(CommentEs.builder()
                        .userAvatar(comment[4])
                        .userId(Long.parseLong(comment[12]))
                        .userName(comment[3])
                        .createdTime(dateFormat.parse(comment[11]))
                        .updateTime(dateFormat.parse(comment[11]))
                        .movieId(Long.valueOf(comment[1]))
                        .content(comment[6])
                        .score(Float.parseFloat(comment[8]))
                        .votes(Long.parseLong(comment[7]))
                        .build()));
                indexQuery.setIndexName("comment");
                indexQuery.setType("comment");
                queries.add(indexQuery);
                //分批提交修改
                if (counter != 0 && counter % 1000 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    log.info("comment to es has update");
                    queries.clear();
                }
                counter++;
            }
            // 提交不足量修改
            if (queries.size() > 0) {
                elasticsearchTemplate.bulkIndex(queries);
                log.info("comment to es has update");
            }
            if (counter > 0) {
                elasticsearchTemplate.refresh("comment");
                log.info("comment to es has refresh");
            }
            log.info("commentEs has update" + counter);
            return counter;

//        } catch (Exception e) {
//            log.info("{}", e.getMessage());
//        }
        //return 0;
    }

//    @Test
//    public void Import() throws Exception{
//        importCommentToEs();
//    }

    // 对电影下的评论数据进行数据处理
    public long updateCommentToEs(Long movieId) {
        List<Movie> movieEntities = new LinkedList<>();
        if (movieId != null && movieId != 0) {
            movieEntities.add(movieRepository.findById(movieId).get());
        } else {
            movieEntities = movieRepository.findAllByCountLimit(50);
            movieEntities.addAll(movieRepository.findAllByHighScore());
        }
        List queries = new ArrayList();
        int counter = 0;
        for (Movie movieEntity : movieEntities) {
            List<CommentEs> commentEs = commentEsRepo.findByMovieId(movieEntity.getId());
            for (CommentEs comment : commentEs) {
                User userEntity = userRepository.findById(comment.getUserId()).get();
                if (userEntity == null) {
                    if (comment.getContent().length()>5){
                        comment.setUserName(comment.getContent().substring(0,5));
                    }else{
                        comment.setUserName(comment.getContent());
                    }
                } else{
                    comment.setUserName(userEntity.getUsername());
                }
                comment.setMovieName(movieEntity.getName());
                IndexQuery indexQuery = new IndexQuery();
                indexQuery.setId(Optional.ofNullable(comment.getCommentId())
                        .orElse(System.currentTimeMillis())
                        .toString());
                try {
                    indexQuery.setSource(new ObjectMapper().writeValueAsString(comment));
                } catch (JsonProcessingException e) {
                    log.info("{}", e.getMessage());
                    continue;
                }
                indexQuery.setIndexName("comment");
                indexQuery.setType("comment");
                queries.add(indexQuery);
                //分批提交修改
                if (counter != 0 && counter % 1000 == 0) {
                    elasticsearchTemplate.bulkIndex(queries);
                    queries.clear();
                }
                counter++;
            }
        }
        // 提交不足量修改
        if (queries.size() > 0) {
            elasticsearchTemplate.bulkIndex(queries);
        }
        if (counter > 0) {
            elasticsearchTemplate.refresh("comment");
        }
        log.info("commentEs has update" + counter);
        return counter;
    }

}
