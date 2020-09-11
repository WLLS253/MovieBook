package com.movie.Serivce;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Dto.MovieDto;
import com.movie.Dto.UserInfoDto;
import com.movie.Entity.Buy;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import com.movie.Repository.BuyRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.redis.RedisKeys;
import com.movie.redis.RedisParse;
import org.hibernate.procedure.spi.ParameterRegistrationImplementor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private RedisParse redisParse;



    public JSONObject getUserCommentedMovies(Long userId){
        User user=userRepository.findById(userId).get();
        List<Movie>movieList=user.getCommentedMovies();

        JSONObject jsonObject=new JSONObject();
        JSONArray movieArray=addMovieDetails(movieList);
        jsonObject.put("movies",movieArray);
        return  jsonObject;
    }

    public JSONObject getUserCollectedMovies(Long userId){
        JSONObject json = (JSONObject) redisParse.getObject(userId.toString(),RedisKeys.User_Movie);
        if(json!=null){
            return json;
        }else {
            User user=userRepository.findById(userId).get();
            List<Movie>movieList=user.getCollectedMovies();
            JSONObject jsonObject=new JSONObject();
            JSONArray movieArray=addMovieDetails(movieList);
            jsonObject.put("movies",movieArray);
            redisParse.saveObject(userId.toString(),jsonObject,RedisKeys.User_Movie);
            return  jsonObject;

        }

    }
    public JSONObject getUserInfo(Long userId){
        JSONObject json = (JSONObject) redisParse.getObject(userId.toString(), RedisKeys.User);
        if(json != null){
            return  json;
        }else {
            User user=userRepository.findById(userId).get();
            JSONObject jsonObject=new JSONObject();
            UserInfoDto userInfoDto =new UserInfoDto();
            BeanUtils.copyProperties(user,userInfoDto);
            jsonObject.put("user_info",userInfoDto);
            redisParse.saveObject(userId.toString(),jsonObject,RedisKeys.User);
            return  jsonObject;
        }
    }

    public JSONObject getBuyList(Long userId){
        //User user=userRepository.findById(userId).get();
        List<Buy> buys = buyRepository.getAllByUser_Id(userId);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("buy_info",buys);
        return  jsonObject;
    }


    private JSONArray addMovieBreif(List<Movie> movieList){
        JSONArray movieArray=new JSONArray();
        for (Movie movie : movieList) {
            JSONObject temp=new JSONObject();
            temp.put("movieId",movie.getId());
            temp.put("score",movie.getScore());
            temp.put("brief",movie.getBrief());
            temp.put("releaseTime",movie.getReleaseTime());
            movieArray.add(temp);
        }
        return movieArray;
    }

    public JSONArray addMovieDetails(List<Movie> movieList){
        JSONArray movieArray=new JSONArray();
        List<MovieDto>movieDtoList = new ArrayList<>();
        for (Movie movie : movieList) {
            MovieDto movieDto =new MovieDto();
            BeanUtils.copyProperties(movie,movieDto);
            movieDto.setReleaseTime(movie.getReleaseTime().toString());
            movieArray.add(movieDto);
        }
        return movieArray;
    }

}
