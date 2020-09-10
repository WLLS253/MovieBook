package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.movie.Dto.CommentDto;
import com.movie.Dto.MovieDto;
import com.movie.Dto.StaffDto;
import com.movie.Dto.TagDto;
import com.movie.Entity.*;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TakePartRepository;
import com.movie.Util.PageHelper;
import com.movie.redis.CacheObject.JsonObjectInfo;
import com.movie.redis.CacheObject.MovieInfoRe;
import com.movie.redis.RedisApi;
import com.movie.redis.RedisKeys;
import com.movie.redis.RedisParse;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MovieService {


    public static int pageNum = 10;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private TakePartRepository takePartRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private UploadSerivce uploadSerivce;
    @Autowired
    private StatisticsService statisticsService;


    @Autowired
    private MovieInfoRe movieInfoRe;

    @Autowired
    private JsonObjectInfo jsonObjectInfo;


    @Autowired
    private RedisApi redisApi;

    @Autowired
    private RedisParse redisParse;





    public JSONObject getMovieComments(Long movieId, Pageable pageable){
        Page<Comment> commentPage=commentRepository.getAllByMovieOrderByCreatedTime(movieId,pageable);
        List<Comment> commentList = commentPage.getContent();
        JSONObject jsonObject=new JSONObject();
        JSONArray commentArray=new JSONArray();

        for (Comment comment : commentList) {
            JSONObject temp=new JSONObject();
            JSONObject userInfo = new JSONObject();
            User u = comment.getUser();
            temp.put("cotent",comment.getContent());
            temp.put("id",comment.getId());
            temp.put("date",comment.getCreatedTime());
            temp.put("score",comment.getScore());
            userInfo.put("userName",u.getUsername());
            userInfo.put("avatar",u.getShowimage());
            userInfo.put("user_id",u.getId());
            temp.put("user_info",userInfo);
            commentArray.add(temp);
        }
        jsonObject.put("comments",commentArray);
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(commentPage));
        return  jsonObject;
    }

    public JSONObject getMovieDetail(Long movie_id,Long user_id){

        MovieDto movieDto = new MovieDto();

        System.out.println("?????");
        JSONObject  Object = (JSONObject)redisParse.getObject(movie_id.toString(),RedisKeys.Movie);
        System.out.println(Object);
        if(Object ==null){
            JSONObject jsonObject=new JSONObject();
            Movie movie=movieRepository.findById(movie_id).get();
            BeanUtils.copyProperties(movie,movieDto);
            movieDto.setReleaseTime(movie.getReleaseTime().toString());
            //JSONArray staffs = getTakePartInfos(movie_id);
            List<StaffDto>staffDtos =getTakePartStaff(movie_id);
            List<String>tagDtos = new ArrayList<>();
            for (Tag tag : movie.getTagList()) {
                tagDtos.add(tag.getTagName());
            }
            jsonObject.put("movie_info",movieDto);
            jsonObject.put("tags_info",tagDtos);
            jsonObject.put("staff_info",staffDtos);
            List<Comment> comments = commentRepository.findAllByUser_IdAndMovie_Id(user_id,movie_id);
            if(comments.size()!=0){
                CommentDto commentDto =new CommentDto();
                BeanUtils.copyProperties(comments.get(0),commentDto);
                jsonObject.put("comment",commentDto);
            }
            //jsonObjectInfo.save(movie_id.toString(),jsonObject);
            redisParse.saveObject(movie_id.toString(),jsonObject,RedisKeys.Movie);
            movieInfoRe.save(movie_id.toString(),movieDto);
            System.out.println(movieInfoRe.get(movie_id.toString()));
            System.out.println(redisParse.getObject(movie_id.toString(),RedisKeys.Movie).toString());
            return  jsonObject;
        }
        else {
            if(user_id!=null){
                List<Comment> comments = commentRepository.findAllByUser_IdAndMovie_Id(user_id,movie_id);
                if(comments.size()!=0){
                    CommentDto commentDto =new CommentDto();
                    BeanUtils.copyProperties(comments.get(0),commentDto);
                    Object.put("comment",commentDto);
                }
            }
            return Object;
        }

        //return  jsonObject;
    }


    public JSONObject addMovie(Double score,
                               String brief,
                               String name,
                               Date releaseTime,
                               String language,
                               String country,
                              MultipartFile cover_img){
        JSONObject jsonObject = new JSONObject();
        Movie m = new Movie();
        m.setBrief(brief);
        m.setScore(score);
        m.setName(name);
        m.setReleaseTime(releaseTime);
        m.setLanguage(language);
        m.setCountry(country);
        String img_url = uploadSerivce.upImageFire(cover_img);
        m.setShowImage(img_url);
        movieRepository.save(m);
        jsonObject.put("movie",m);
        return jsonObject;
    }


    public JSONArray getTakePartInfos(Long movie_id){
        List<TakePart> takeParts = takePartRepository.findAllByMovie_Id(movie_id);
        JSONArray staffs = new JSONArray();
        for (TakePart t:takeParts) {
            JSONObject staff_json = new JSONObject();
            Staff s = t.getStaff();
            staff_json.put("id",s.getId());
            staff_json.put("name",s.getStaffName());
            staff_json.put("role",t.getRole());
            staff_json.put("img",s.getShowImage());
            staffs.add(staff_json);
        }
        return  staffs;
    }


    public List<StaffDto>getTakePartStaff(Long movie_id){
        List<TakePart> takeParts = takePartRepository.findAllByMovie_Id(movie_id);
        List<StaffDto>staffDtos = new ArrayList<>();
        for (TakePart t:takeParts) {
            StaffDto staffDto =new StaffDto();
            BeanUtils.copyProperties(t.getStaff(),staffDto);
            staffDto.setRole(t.getRole());
            staffDtos.add(staffDto);

        }
        return  staffDtos;
    }

    @Scheduled(cron = "0 * * * * *")
    //@Scheduled(fixedRate = 10000)
    public void updateMovieStates() {
        List<Movie> preMovies  = movieRepository.getMoviesByState("pre");
        Long cur = new Date().getTime();
        for (Movie m:
                preMovies) {
            if(Math.abs(m.getReleaseTime().getTime() - cur) <= 10000 || m.getReleaseTime().getTime() < cur){
                m.setState("on");
                JSONObject jsonObject = (JSONObject) redisParse.getObject(m.getId().toString(),RedisKeys.Movie);
                if(jsonObject != null){
                    MovieDto movieDto = new MovieDto();
                    System.out.println(m);
                    BeanUtils.copyProperties(m,movieDto);
                    jsonObject.put("movie_info",movieDto);
                    //System.out.println(jsonObject);
                    redisParse.saveObject(m.getId().toString(),jsonObject,RedisKeys.Movie);
                }
            }
        }
        movieRepository.saveAll(preMovies);
    }

//    public JSONObject getMovieBreifInfos(String state){
//
//    }





}

