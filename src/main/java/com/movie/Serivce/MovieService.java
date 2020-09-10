package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Repository.CommentEsRepo;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TakePartRepository;
import com.movie.Util.PageHelper;
import com.movie.Util.RecommendUtils;
import com.movie.redis.RedisApi;
import com.movie.redis.RedisKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

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

    @Resource
    private RedisApi redisApi;

    @Resource
    private CommentEsRepo commentEsRepo;





    // 将评论信息写入es index
    public CommentEs submitComment(CommentEs commentEs) {
        // times 用来做防刷爬虫攻击，限制每个用户短时间内的第五次提交
        Integer times = Integer.valueOf(Optional
                .ofNullable(redisApi.getString(RecommendUtils.getKey(RedisKeys.BRUSH, commentEs.getUserId().toString())))
                .orElse("0"));
        if (times > 5){
            return null;
        } else {
            times = times + 1;
            redisApi.setValue(RecommendUtils.getKey(RedisKeys.BRUSH, commentEs.getUserId().toString()), times.toString(), 3, TimeUnit.MINUTES);
        }
        return commentEsRepo.save(commentEs);
    }

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

    public JSONObject getMovieCommentEs(Long movieId,Pageable pageable){
        Page<CommentEs> commentPage=commentEsRepo.findByMovieId(movieId,pageable);
        List<CommentEs> commentList = commentPage.getContent();
        JSONObject jsonObject=new JSONObject();
        JSONArray commentArray=new JSONArray();
        for (CommentEs commentEs:commentList){
            JSONObject temp=new JSONObject();
            JSONObject userInfo = new JSONObject();
            temp.put("cotent",commentEs.getContent());
            temp.put("id",commentEs.getCommentId());
            temp.put("date",commentEs.getCreatedTime());
            temp.put("score",commentEs.getScore());
            userInfo.put("userName",commentEs.getUserName());
            userInfo.put("avatar",commentEs.getUserAvatar());
            userInfo.put("user_id",commentEs.getUserId());
            temp.put("user_info",userInfo);
            commentArray.add(temp);
        }
        jsonObject.put("comments",commentArray);
        jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(commentPage));
        return  jsonObject;
    }

    public JSONObject getMovieDetail(Long movie_id,Long user_id){
        JSONObject jsonObject=new JSONObject();
        Movie movie=movieRepository.findById(movie_id).get();
        JSONArray staffs = getTakePartInfos(movie_id);
        jsonObject.put("movie_info",movie);
        jsonObject.put("tags_info",movie.getTagList());
        jsonObject.put("staff_info",staffs);
        List<Comment> comments = commentRepository.findAllByUser_IdAndMovie_Id(user_id,movie_id);
        if(comments.size()!=0)
            jsonObject.put("comment",comments.get(0));
        return  jsonObject;
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

    //@Scheduled(fixedRate = 10000)
    public void updateMovieStates() {
        List<Movie> preMovies  = movieRepository.getMoviesByState("pre");
        Long cur = new Date().getTime();
        for (Movie m:
                preMovies) {
            if(Math.abs(m.getReleaseTime().getTime() - cur) <= 10000 || m.getReleaseTime().getTime() < cur){
                m.setState("on");
            }
        }
        movieRepository.saveAll(preMovies);
    }

//    public JSONObject getMovieBreifInfos(String state){
//
//    }





}

