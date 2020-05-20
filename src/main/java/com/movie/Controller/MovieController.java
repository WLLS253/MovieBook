package com.movie.Controller;


import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Result.Result;
import com.movie.Serivce.MovieService;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@CrossOrigin
@RestController
public class MovieController {


    @Autowired
    private StaffRepository staffRepository;


    @Autowired
    private TakePartRepository takePartRepository;



    @Autowired
    private MovieRepository movieRepository;


    @Autowired
    private CommentRepository commentRepository;


    @Autowired
    private UserRepository userRepository;


    @Autowired
    private TagRepository tagRepository;


    @Autowired
    private MovieService movieService;


    @PostMapping(value = "staff/add")
    public Result addStaff(@RequestParam("staffName")String name,@RequestParam("staffBrief")String brief){
        try {
            Staff staff=new Staff();
            staff.setStaffBrief(brief);
            staff.setStaffName(name);
            return Util.success(staffRepository.save(staff));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "comment/add")
    public  Result addComment(@RequestParam("userId")Long userId,@RequestParam("movieId")Long movieId,@RequestParam("content")String content,@RequestParam("title")String title){
        try {
            User user=userRepository.findById(userId).get();
            Movie movie=movieRepository.findById(movieId).get();
            Comment comment=new Comment();
            comment.setContent(content);
            comment.setTitle(title);
            comment.setUser(user);
            comment.setMovie(movie);
            comment=commentRepository.save(comment);
            return  Util.success(comment);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "tag/add")
    public  Result addTag(@RequestParam("tagName")String tagName){
        try {
            Tag tag=new Tag();
            tag.setTagName(tagName);
            return Util.success(tagRepository.save(tag));
        }catch (Exception e){
            e.printStackTrace();;
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "movie/getCommentList")
    public Result getMovieCommentList(@RequestParam("movieId")Long movieId){
        try {
            return Util.success(movieService.getMovieComments(movieId));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 电影的主页信息
    @GetMapping(value = "movie/movieDetails")
    public Result getMovieDetail(long movie_id){
        try {
            return Util.success(movieService.getMovieComments(movie_id));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 电影的主页信息
    @PostMapping(value = "movie/add")
    public Result addMovie(Double score,
                           String brief,
                           String name,
                           Date releaseTime,
                           String language,
                           String country,
                            MultipartFile cover_img){
        try {

            return Util.success();
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }






}
