package com.movie.Controller;


import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Result.Result;
import com.movie.Serivce.MovieService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.Year;
import java.util.Date;
import java.util.List;

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
            return Util.success(movieService.getMovieComment(movieId));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "moive/fliter")
    public Result filterMovies(@RequestBody FilterSetting filter_setting){
        try {
            System.out.println(filter_setting);
            return Util.success(movieService.filterMovies(filter_setting.start_year,filter_setting.end_year,filter_setting.tags));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    // requestBody classes
    @Data
    private static class FilterSetting{
        // 上映年份区间
        int start_year;
        int end_year;

        // 选择的 tags
        List<String> tags;

        @Override
        public String toString() {
            return "start_year"+start_year+" end_year"+end_year+" tags"+tags;
        }
    }






}
