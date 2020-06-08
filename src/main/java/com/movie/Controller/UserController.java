package com.movie.Controller;


import com.movie.Entity.Comment;
import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UserService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import org.hibernate.stat.internal.CategorizedStatistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentRepository commentRepository;


    @SysLog(value = "获取电影评论")
    @GetMapping(value = "user/getCommentedMovies")
    public Result getUserMovieList(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserCommentedMovies(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @GetMapping(value = "user/getUserInfo")
    public Result getUserInfo(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserInfo(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "user/getCollectedMovies")
    public Result getUserCollectedMovies(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getUserCollectedMovies(userId));
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "user/getBuyList")
    public Result getUserBuyList(@RequestParam("userId")Long userId){
        try {
            return Util.success(userService.getBuyList(userId));
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

    @DeleteMapping(value = "user/deLComment")
    public  Result delCommentByUser(@RequestParam("userId")Long userId,@RequestParam("commentId")Long comment_id){
        try {
            Comment comment=commentRepository.findById(comment_id).get();
            if(userId==comment.getUser().getId()){
                commentRepository.deleteById(comment_id);
                return  Util.success(ExceptionEnums.DEL_SUCCESS);
            }else {
                return Util.failure(ExceptionEnums.USER_CANT_DEL);
            }

        } catch ( Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }




}
