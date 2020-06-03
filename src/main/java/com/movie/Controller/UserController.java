package com.movie.Controller;


import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UserService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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


    @SysLog(value = "获取电影评论")
    @GetMapping(value = "user/getCommentedMovies")
    public Result getUserMovieList(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserCommentedMovies(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @SysLog(value = "删除用户")
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
