package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;


    public JSONObject getUserCommentedMovies(Long userId){
        User user=userRepository.findById(userId).get();
        List<Movie>movieList=user.getCommentedMovies();

        JSONObject jsonObject=new JSONObject();
        JSONArray movieArray=new JSONArray();
        for (Movie movie : movieList) {
            JSONObject temp=new JSONObject();
            temp.put("movieId",movie.getId());
            temp.put("score",movie.getScore());
            temp.put("brief",movie.getBrief());
            temp.put("releaseTime",movie.getReleaseTime());
            movieArray.add(temp);
        }
        jsonObject.put("movies",movieArray);
        return  jsonObject;
    }
    public JSONObject getUserInfo(Long userId){
        User user=userRepository.findById(userId).get();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("user_info",user);
        return  jsonObject;
    }
}
