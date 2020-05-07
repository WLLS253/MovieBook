package com.movie.Controller;


import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UserService;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private UserService userService;

    @GetMapping(value = "user/getMovieList")
    public Result getUserMovieList(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserMovieList(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }



}
