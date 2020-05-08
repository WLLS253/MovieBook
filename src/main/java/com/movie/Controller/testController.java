package com.movie.Controller;


import com.movie.Entity.Assessor;
import com.movie.Entity.Comment;
import com.movie.Entity.Movie;
import com.movie.Entity.User;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.AssessorRepository;
import com.movie.Repository.CommentRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.zip.CheckedOutputStream;


@CrossOrigin
@RestController
public class testController {

    @Autowired
    private AssessorRepository assessorRepository;

    @Autowired
    private MovieRepository movieRepository;
    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @PostMapping(value = "/test/add")
    public Result add(@RequestParam("name") String name,@RequestParam("pass")String pass){

        Assessor assessor=new Assessor();
        assessor.setAssessorPassword(pass);
        assessor.setAssessorName(name);

        return Util.success(assessorRepository.save(assessor));
    }


    @PostMapping(value = "/test/find")
    public Result find(@RequestParam("name") String name){
        List<Assessor>assessorList=assessorRepository.findByAssessorName(name);
        System.out.println(name);
        System.out.println(assessorList);
        return Util.success(111);
    }

    @PostMapping(value = "test/user/add")
    public Result addUser(@RequestParam("name")String name){

        User user=new User();
        user.setUsername(name);
        System.out.println(user);
        return Util.success(userRepository.save(user));
    }
    @PostMapping(value = "test/movie/add")
    public Result addMovie(Movie movie){
        Movie movie1=new Movie();
        movie1.setBrief(movie.getBrief());
        movie1.setReleaseTime(movie.getReleaseTime());
        movie1.setScore(movie.getScore());
        return Util.success(movieRepository.save(movie1));
    }




}
