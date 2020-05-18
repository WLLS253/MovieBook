package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.movie.Entity.BaseEntity;
import com.movie.Entity.Cinema;
import com.movie.Entity.Movie;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TagRepository;
import com.movie.Repository.UserRepository;
import org.apache.commons.collections.SequencedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchService {
    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;


    public JSONObject filterMovies(int year_start, int year_end,String keyString,List<String> tags){
        String[] keys = keyString.split(" +"); // 空格分离得到所有的 关键字
        JSONObject jsonObject = new JSONObject();
        List<Movie> flitered_movies;
        if(keyString.isEmpty())
            if(tags.size() == 0) {
                flitered_movies =  movieRepository.filterMovies(year_start,year_end);
            }else{
                flitered_movies = movieRepository.filterMovies(tags,year_start,year_end);
            }
        else{
            if(tags.size() == 0) {
                flitered_movies =  getFilterResultsByKeys(movieRepository.filterMovies(year_start,year_end),keys) ;
            }else{
                flitered_movies = getFilterResultsByKeys(movieRepository.filterMovies(tags,year_start,year_end),keys) ;
            }
        }

        jsonObject.put("movies",flitered_movies);
        return jsonObject;
    }

    // 按照员工 职位和姓名来 过滤电影
    public JSONObject filterMovies(String role ,String name){
        JSONObject jsonObject = new JSONObject();
        List<Movie> flitered_movies  = movieRepository.filterMovies(role,name);
        jsonObject.put("movies",flitered_movies);
        return jsonObject;
    }


    // 根据名称关键字来过滤 电影院
    public JSONObject filterCinema(String keyString){
        String[] keys = keyString.split(" +");
        //TODO cienma的地区问题
        List<Cinema> cinemas = cinemaRepository.findAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("cinemas",getFilterResultsByKeys(cinemas,keys));
        return jsonObject;
    }

    // 通过moive 来获取所有正在上映该电影的 电影院
    public JSONObject  filterCinema(Long movie_id){
        Cinema cinema = cinemaRepository.findById(movie_id).get();
        JSONObject jsonObject = new JSONObject();
        return jsonObject;
    }


    // 按照关键字的相关性排序 entities
    private <T extends BaseEntity> List<T> getFilterResultsByKeys(List<T> entities, String[] keys){
        int relativeness = 0;//关键字相关性
        SortedMap sortedMap = new TreeMap();
        for (int i=0;i<entities.size();i++) {
            T entity = entities.get(i);
            System.out.println(entity );
            String name = entity.getSearchName();
            relativeness = calRelativeness(name,keys);
            if(relativeness!=0)
                sortedMap.put(relativeness,entity);
        }

        return new ArrayList<>((Collection<T>)sortedMap.values());
    }

    private String getName(Cinema cinema){
        return cinema.getCinemaName();
    }

    private int calRelativeness(String tar_str,String[] keys){
        int relativeness = 0;
        for (String key:keys) {
            if(tar_str.contains(key)){
                relativeness += 1;
            }
        }
        return relativeness;
    }


    //KMP
//    void Getnext(int next[],String t)
//    {
//        int j=0,k=-1;
//        next[0]=-1;
//        while(j<t.length()-1)
//        {
//            if(k == -1 || t[j] == t[k])
//            {
//                j++;k++;
//                next[j] = k;
//            }
//            else k = next[k];//此语句是这段代码最反人类的地方，如果你一下子就能看懂，那么请允许我称呼你一声大神！
//        }
//    }
//    int KMP(String s,String t)
//    {
//        int next[MaxSize],i=0;j=0;
//        Getnext(t,next);
//        while(i<s.length&&j<t.length)
//        {
//            if(j==-1 || s[i]==t[j])
//            {
//                i++;
//                j++;
//            }
//            else j=next[j];               //j回退。。。
//        }
//        if(j>=t.length())
//            return (i-t.length());         //匹配成功，返回子串的位置
//        else
//            return (-1);                  //没找到
//    }

    //插入排序
//    private<T> int insertSort(List<T> list,int prio,T entity){
//
//    }


}
