package com.movie.Config;

import com.movie.Entity.Comment;
import com.movie.Entity.CommentEs;
import io.swagger.models.auth.In;
import javafx.util.Pair;
import org.apache.commons.collections.map.HashedMap;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.jdbc.MySQLJDBCDataModel;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.elasticsearch.client.ml.job.results.Bucket;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.movie.Serivce.ElasticSearchService;


import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Configuration
public class MahoutConfig {

    @Autowired
    private DataSource dataSource;
    @Autowired
    private ElasticSearchService elasticSearchService;

    public FastByIDMap<PreferenceArray> preMap = new FastByIDMap<>();
    private GenericDataModel dataModel;

    @Bean(autowire = Autowire.BY_NAME,value = "mySQLDataModel")
    public DataModel getMySQLJDBCDataModel(){

//        org.apache.mahout.cf.taste.impl.model.AbstractDataModel
        return new MySQLJDBCDataModel(dataSource,"comment","user_id",
                "movie_id","score", "created_time");
    }

    @Bean(autowire = Autowire.BY_NAME,value = "elasticDataModel")
    public DataModel getElasticDataModel(){
        return new GenericDataModel(preMap);
    }


    public void insertUser(long userId){
        List<CommentEs> commentEsList  = elasticSearchService.getAllComment(userId);
        PreferenceArray array = new GenericUserPreferenceArray(commentEsList.size());
        for (int i = 0; i < commentEsList.size(); i++) {
            CommentEs c =commentEsList.get(i);
            insertToMap(array,userId,c.getMovieId(),c.getScore(),i);
        }
        preMap.put(userId,array);

    }

    @PostConstruct
    public void refreshPreferenceData(){
        Map<Long,List<Pair<Long,Float>>> result =  elasticSearchService.getAllIndex();

        for (Map.Entry<Long,List<Pair<Long,Float>>> entry:result.entrySet()) {
            List<Pair<Long,Float>> values = entry.getValue();
            Long user_id = entry.getKey();
            PreferenceArray array = new GenericUserPreferenceArray(values.size());
            int size = values.size();
            for (int i=0;i<size;i++){
                Pair<Long,Float> p  = values.get(i);
                insertToMap(array,user_id,p.getKey(),p.getValue(),i);
            }
            preMap.put(user_id-1,array);
        }
        return;
    }
//    private void insertToMap(List<CommentEs> commentEsList){
//        Map<Long,Integer> map = new HashedMap();
//        //构造用户1的偏好
//        for (CommentEs comment:commentEsList) {
//            long user_id = comment.getUserId();
//            PreferenceArray user1  = preMap.get(user_id);
//            if(user1 == null){
//                user1 = new GenericUserPreferenceArray(40);
//                map.put(user_id,0);
//                preMap.put(user_id,user1);
//            }
//            int time = map.get(user_id);
//            user1.setUserID(time,user_id);
//            user1.setItemID(time,comment.getMovieId());
//            user1.setValue(time,comment.getScore());
//            time++;
//            map.put(user_id,time);
//        }
//        return;
//    }


    private void insertToMap(PreferenceArray preferences,Long id,Long movieId,Float score,int idx){
        preferences.setUserID(idx,id);
        preferences.setItemID(idx,movieId);
        preferences.setValue(idx,score);
        return;
    }


//    @Bean(autowire = Autowire.BY_NAME,value = "fileDataModel")
//    public DataModel getDataModel() throws IOException {
//        URL url=MahoutConfig.class.getClassLoader().getResource("/rating.csv");
//        return new FileDataModel(new File(Objects.requireNonNull(url).getFile()));
//    }
}