package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Comment;
import com.movie.Entity.Movie;
import com.movie.Entity.Tag;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRepository movieRepository;


    public JSONObject getMovieComment(Long movieId){

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





}

