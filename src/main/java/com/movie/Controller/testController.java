package com.movie.Controller;


import com.movie.Config.MahoutConfig;
import com.movie.Domain.MovieRecommender;
import com.alibaba.fastjson.JSONObject;
import com.movie.Dto.MovieDto;
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
import com.movie.Serivce.ElasticSearchService;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;

import com.movie.redis.CacheObject.JsonObjectInfo;
import com.movie.redis.CacheObject.MovieInfoRe;
import com.movie.redis.CacheObject.UserInfoRe;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
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
    private ElasticSearchService elasticSearchService;

    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private MovieRecommender movieRecommender;
    @Autowired
    private MahoutConfig mahoutConfig;
    private MovieInfoRe movieInfoRe;


    @PostMapping(value = "/test/add")
    public Result add(@RequestParam("name") String name,@RequestParam("pass")String pass) throws InvalidKeySpecException, NoSuchAlgorithmException {

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

    @Autowired
    private JsonObjectInfo jsonObjectInfo;

    @Autowired
    private  UserInfoRe userInfoRe;

    @GetMapping(value = "/test/ggget")
    public Result tmse(@RequestParam long id){

        return Util.success(userRepository.findById(id).get());
    }

    @GetMapping(value="/test/elas")
    public Result es() throws Exception{
        return Util.success(elasticSearchService.importCommentToEs());
    }
    public void  tmse() throws InvalidKeySpecException, NoSuchAlgorithmException {

//        JSONObject jsonObject = jsonObjectInfo.get("1");
        //System.out.println(jsonObject);

        Movie movie = new Movie();
        movie.setName("asdasd");
        movie.setBrief("asdasdasd");
        movie.setState("Y");

        MovieDto movieDto = new MovieDto();
        BeanUtils.copyProperties(movie,movieDto);
        //movieInfoRe.save("12",movie);

        movieInfoRe.save("321",movieDto);
        User user =new User();
        user.setUsername("clyjjj");
        user.setPassword("lllll");
        userInfoRe.save("31",user);
        System.out.println(userInfoRe.get("31"));
        System.out.println(movieInfoRe.get("321"));

//        return Util.success(userRepository.findById((long)1).get());
    }

//    @GetMapping(value="/test/mahout")
//    public Result eee1() throws Exception{
//        mahoutConfig.refreshPreferenceData();
//        return Util.success();
//    }


    @GetMapping(value="/test/recommand")
    public Result recommand(@RequestParam long id) throws Exception{
        return Util.success(movieRecommender.userBasedRecommender(id,20));
    }

    @PostMapping(value="/test/upload_test")
    public Result uploadImg(@RequestParam MultipartFile files){
        return Util.success(uploadSerivce.upImageFire(files));
    }

}
