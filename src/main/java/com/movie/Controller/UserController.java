package com.movie.Controller;


import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UserService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @GetMapping(value = "user/getCommentedMovies")
    public Result getUserMovieList(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserCommentedMovies(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @DeleteMapping(value = "user/del")
    public Result delUser(@RequestParam("userId")Long userId) {
        try {
            userRepository.deleteById(userId);
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }



}
