package com.movie.Serivce;



import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Repository.*;
import com.movie.Util.PageHelper;
import io.swagger.models.auth.In;
import javafx.collections.transformation.SortedList;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;


@Service
public class SearchService {

    private List<String> null_tags = new ArrayList<String>(){{
        add("null");
    } };

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private TakePartRepository takePartRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieService movieService;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;
    @Autowired
    StatisticsService statisticsService;


    public JSONObject filterMoviesBrief(Integer start_year,
                                        Integer end_year,
                                        String key_string,
                                        List<String> tags,
                                        Date date,
                                        String state,
                                        String cinema_name,String country,Pageable pageable) {
        JSONObject jsonObject = new JSONObject();
        String tag_regex = getRegex(tags);
        if(key_string!=null)
            key_string = key_string.join("|",key_string.split("[\\s]+"));
        if(cinema_name!=null)
            cinema_name = cinema_name.join("|",cinema_name.split("[\\s]+"));
        Page<Movie> filtered_movies=movieRepository.filterMovies(start_year,end_year,tag_regex ,date,state,cinema_name,key_string,country,pageable);
        jsonObject.put("movies",filtered_movies.getContent());
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(filtered_movies));
        return jsonObject;
    }



    public JSONObject filterMoviesDetail(Integer start_year,
                                         Integer end_year,
                                         String key_string,
                                         List<String> tags,
                                         Date date,
                                         String state,
                                         String cinema_name,String country ,Pageable pageable) {

        JSONObject jsonObject = new JSONObject();
        //tags = tags==null?null_tags:tags;
        // 构成 tag正则
        String tag_regex = getRegex(tags);

        //TODO cienma的地区问题
        if(key_string!=null)
            key_string = key_string.join("|",key_string.split("[\\s]+"));
        if(cinema_name!=null)
            cinema_name = cinema_name.join("|",cinema_name.split("[\\s]+"));
        Page<Movie> filtered_movies=movieRepository.filterMovies(start_year,end_year,tag_regex,date,state,cinema_name,key_string,country,pageable);
        jsonObject.put("cinemas_infos",filtered_movies.getContent());
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(filtered_movies));
        return jsonObject;
    }

    private String getRegex(List<String> tags) {
        String tag_regex = null;
        if(tags!=null){
            //String notRegex = "[^(" + tags.get(0)+')';
            String notRegex = ".*";
            String isRegex = "("+ tags.get(0);
            for(int i=1;i<tags.size();i++){
                isRegex += "|"+tags.get(i);
            }
            isRegex+=")";
            tag_regex = "";
            tag_regex += notRegex;
            for (String a:tags) {
                tag_regex += isRegex+notRegex;
            }
        }
        return tag_regex;
    }

    // 按照员工 职位和姓名来 过滤电影
    public JSONObject filterMovies(String role ,String name){
        JSONObject jsonObject = new JSONObject();

        List<Movie> flitered_movies  = movieRepository.filterMovies(role,name);

        jsonObject.put("movies",flitered_movies);
        return jsonObject;
    }


    // 根据名称关键字来过滤 电影院
    public JSONObject filterCinema(String keyString,Integer grade,Pageable pageable){
        JSONObject jsonObject = new JSONObject();
        //TODO cienma的地区问题
        if(keyString!=null)
            keyString = keyString.join("|",keyString.split("[\\s]+"));
        Page<Cinema> filtered_cinemas = cinemaRepository.filterCinema(grade,keyString,null,pageable);
        jsonObject.put("cinemas_infos",filtered_cinemas.getContent());
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(filtered_cinemas));
        return jsonObject;
    }

    // 根据特定内容来过滤 电影管理员
    public JSONObject filterCinemaMng(String keyString,String sex,String cinema_name,Integer prio,Pageable pageable){
        JSONObject jsonObject = new JSONObject();
        if(cinema_name!=null)
            cinema_name = getRegexString(cinema_name);
        if(keyString!=null)
            keyString = getRegexString(keyString);
        Page<CinemaMng> filtered_cinemaMngs = cinemaMngRepository.filterCinemaMng(keyString,sex,cinema_name,prio,pageable);
        jsonObject.put("cinema_mng_infos",filtered_cinemaMngs.getContent());
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(filtered_cinemaMngs));
        return jsonObject;
    }



    // 获取热映电影
    public JSONObject getHotMovies(){
        statisticsService.addIndexVisitor();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("movies",movieRepository.getLimitMoviesByState("on",0,6));
        return jsonObject;
    }


    private String getRegexString(String raw){
        return raw.join("|",raw.split("[\\s]+"));
    }

    //按照某种算法重排 对应的 cinema 序列
//    private JSONArray arrangeCinema(JSONObject cin_infos){
//        Iterator it =  cin_infos.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry cin_info = (Map.Entry)it.next();
//            JSONObject info =  (JSONObject)cin_info.getValue();
//        }
//    }

    // 获取过滤后的 电影列表
        // 而返回的 具体JSONObject 格式将由对应的调用方法决定
//    private List<Movie> getFliteredMovies(Integer start_year,
//                                          Integer end_year,
//            String key_string,
//            List<String> tags,
//            Date date,
//            String state,
//            String cinema_name,String country){
//        tags = tags==null?null_tags:tags;
//        List<Movie> movies =movieRepository.filterMovies(start_year,end_year,tags,date,state,cinema_name,country);
//        if(key_string == null)
//            return  movies;
//        String[] keys = key_string.split(" +");
//        return getFilterResultsByKeys(movies,keys);
//    }
//
//    private List<Cinema> getFliteredCinemas(String key_string,
//                                          Integer grade){
//        List<Cinema> cinemas =cinemaRepository.filterCinema(grade);
//        if(key_string == null)
//            return cinemas;
//        String[] keys = key_string.split(" +");
//        return getFilterResultsByKeys(cinemas,keys);
//    }
//
//


//    // 按照关键字的相关性排序 entities
//    private <T extends BaseEntity> List<T> getFilterResultsByKeys(List<T> entities, String[] keys){
//        int relativeness = 0;//关键字相关性
//        List<Pair<Integer,T>> list =new ArrayList<>();
//        for (int i=0;i<entities.size();i++) {
//            T entity = entities.get(i);
//            System.out.println(entity );
//            String name = entity.getSearchName();
//            relativeness = calRelativeness(name,keys);
//            if(relativeness!=0)
//                list.add(new Pair<>(relativeness,entity));
//        }
//        list.sort(Comparator.comparingInt(Pair::getKey));
//        List<T> result = new ArrayList<>();
//        for (Pair<Integer, T> p:list){
//            result.add(p.getValue());
//        }
//        return result;
//    }
//
//    private String getName(Cinema cinema){
//        return cinema.getCinemaName();
//    }
//
//    private int calRelativeness(String tar_str,String[] keys){
//        int relativeness = 0;
//        for (String key:keys) {
//            if(tar_str.contains(key)){
//                relativeness += 1;
//            }
//        }
//        return relativeness;
//    }


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
//            else k = next[k];
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
