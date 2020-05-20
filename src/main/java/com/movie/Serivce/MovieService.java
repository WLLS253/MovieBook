package com.movie.Serivce;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Comment;
import com.movie.Entity.Movie;
import com.movie.Entity.Tag;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;

@Service
public class MovieService {


    public static int pageNum = 10;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UploadSerivce uploadSerivce;


    public JSONObject getMovieComments(Long movieId){

        Movie movie=movieRepository.findById(movieId).get();
        List<Comment>commentList=commentRepository.findByMovie(movie);

        JSONObject jsonObject=new JSONObject();
        JSONArray commentArray=new JSONArray();

        for (Comment comment : commentList) {
            JSONObject temp=new JSONObject();
            temp.put("cotent",comment.getContent());
            temp.put("title",comment.getTitle());
            temp.put("userName",comment.getUser().getUsername());
            commentArray.add(temp);
        }
        jsonObject.put("comments",commentArray);
        return  jsonObject;
    }

    public JSONObject getMovieDetail(Long movie_id){
        JSONObject jsonObject=new JSONObject();
        Movie movie=movieRepository.findById(movie_id).get();
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

//    public JSONObject getMovieBreifInfos(String state){
//
//    }


}

