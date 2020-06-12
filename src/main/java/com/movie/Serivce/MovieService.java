package com.movie.Serivce;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TakePartRepository;
import com.movie.Util.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.data.domain.Pageable;
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
            temp.put("title",comment.getTitle());
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

//    public JSONObject getMovieBreifInfos(String state){
//
//    }





}

