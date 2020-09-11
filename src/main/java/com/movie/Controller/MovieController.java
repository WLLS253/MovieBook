package com.movie.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
import com.movie.Repository.*;
import com.movie.Result.Result;
import com.movie.Serivce.*;
import com.movie.Util.RecommendUtils;
import com.movie.Util.Util;
import com.movie.redis.CacheObject.MovieInfoRe;
import com.movie.redis.RedisKeys;
import com.movie.redis.RedisParse;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
public class MovieController {


    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    ElasticSearchService elasticSearchService;

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


    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CommentEsRepo commentEsRepo;
    private MovieInfoRe movieInfoRe;

    @Autowired
    private  RedisParse redisParse;

    @Autowired
    private UserService userService;

   @PostMapping(value = "movie/add")
    public Result addMovie(MovieInformation movieInformation) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date releaseTime=simpleDateFormat.parse(movieInformation.releaseTime);
        Movie movie1=new Movie();
        movie1.setBrief(movieInformation.brief);
        movie1.setCountry(movieInformation.country);
        movie1.setDuration(movieInformation.duration);
        movie1.setReleaseTime(releaseTime);
        movie1.setLanguage(movieInformation.language);
        movie1.setState(movieInformation.state);
        movie1.setName(movieInformation.name);

        if(movieInformation.showImage!=null){
            String showImage=uploadSerivce.upImageFire(movieInformation.showImage);
            movie1.setShowImage(showImage);
        }

        if(movieInformation.figureList!=null){
            List<Figure>figureList=new ArrayList<>();
            for (MultipartFile multipartFile : movieInformation.figureList) {
                Figure figure=new Figure();
                String url=uploadSerivce.upImageFire(multipartFile);
                figure.setImageurl(url);
                figureList.add(figure);
                figureRepository.save(figure);
            }
            movie1.setFigureList(figureList);
        }

        //movieInfoRe.save(movie1.getId().toString(),movie1);
        return Util.success(movieRepository.save(movie1));
    }

    @PostMapping(value = "staff/add")
    public Result addStaff(@RequestParam("staffName")String name,@RequestParam("staffBrief")String brief){
        try {
            Staff staff=new Staff();
            staff.setStaffBrief(brief);
            staff.setStaffName(name);
            return Util.success(cinemaService.getStaff(staffRepository.save(staff)));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PutMapping(value = "staff/update")
    public Result UpdateStaff(@RequestParam("id")Long id,@RequestParam("staffName")String name,@RequestParam("staffBrief")String brief,@RequestParam("showImage")MultipartFile image){
        try {
            String showImage=uploadSerivce.upImageFire(image);
            Staff staff=staffRepository.findById(id).get();
            staff.setStaffName(name);
            staff.setStaffBrief(brief);
            uploadSerivce.deleteimage(staff.getShowImage());
            staff.setShowImage(showImage);
            return Util.success(cinemaService.getStaff(staffRepository.save(staff)));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "comment/add")
    public  Result addComment(@RequestParam("userId")Long userId,@RequestParam("movieId")Long movieId,@RequestParam(value = "content",required = false)String content,@RequestParam(value = "score",required = false)Float score){
        try {
            User user=userRepository.findById(userId).get();
            Movie movie=movieRepository.findById(movieId).get();

            Comment comment=null;
            List<Comment>commentList=commentRepository.findByUserAndMovie(user,movie);
            if(commentList.size()==0){
                 comment=new Comment();
            }else {
                comment=commentList.get(0);
            }

            if(content!=null){
                comment.setContent(content);
            }
            if(score!=null){
                comment.setScore(score);
            }
            comment.setUser(user);
            comment.setMovie(movie);
            comment=commentRepository.save(comment);
            return  Util.success(comment);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "commentEs/add")
    public  Result addCommentEs(@RequestParam("userId")Long userId,
                                @RequestParam("movieId")Long movieId,
                                @RequestParam(value = "content",required = false)String content,
                                @RequestParam(value = "score",required = false)Float score){
        try{

            List<CommentEs> commentEs = elasticSearchService.getAllCommentByMovieId(userId,movieId);
                    //commentEsRepo.findByUserIdAndMovieId(userId,movieId);

            CommentEs comment;
            User user = userRepository.findById(userId).get();
            Movie m = movieRepository.findById(movieId).get();
            if(commentEs.size() == 0){
                comment = new CommentEs(null,userId,user.getUsername(),user.getShowimage(),movieId,m.getName(),content,(long)0,score,new Date(),new Date());
            }else{
                comment = commentEs.get(0);
                comment.setContent(content);
                comment.setScore(score);
            }
            movieService.submitComment(comment);
            return Util.success(comment);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }



    @PostMapping(value = "collect/add")
    public Result addCollect(Long userId,Long movieId){
        try {
            User user=userRepository.findById(userId).get();
            Movie movie=movieRepository.findById(movieId).get();
            List<Movie> movies =user.getCollectedMovies();
            JSONObject jsonObject =new JSONObject();
            if(!movies.contains(movie)){
                movies.add(movie);
                userRepository.save(user);
                jsonObject.put("msg","添加成功");
                JSONArray jsonArray =userService.addMovieDetails(movies);
                JSONObject temp = new JSONObject();
                temp.put("movies",jsonArray);
                redisParse.saveObject(userId.toString(),temp,RedisKeys.User_Movie);
            }else{
                jsonObject.put("msg","添加失败，已经收藏过该电影");
            }
            return Util.success(jsonObject);
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "tag/add")
    public  Result addTag(@RequestParam("tagName")String tagName){
        try {
            Tag tag=new Tag(tagName);
            //tag.setTagName(tagName);
            return Util.success(tagRepository.save(tag));
        }catch (Exception e){
            e.printStackTrace();;
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "movie/getCommentList")
    public Result getMovieCommentList(@RequestParam("movieId")Long movieId,int pageSize,int pageNumber){
        try {

            Sort s = Sort.by(Sort.Direction.ASC,"created_time");
            Pageable p = PageRequest.of(pageNumber,pageSize,s);
            return Util.success(movieService.getMovieComments(movieId,p));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "movie/getCommentEsList")
    public Result getMovieCommentEsList(@RequestParam("movieId")Long movieId,int pageSize,int pageNumber){
        try {

            Sort s = Sort.by(Sort.Direction.ASC,"created_time");
            Pageable p = PageRequest.of(pageNumber,pageSize,s);
            return Util.success(movieService.getMovieComments(movieId,p));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "影院管理员删除评论")
    @DeleteMapping(value = "comment/delbymng")
    public  Result delCommentByMng(@RequestParam("commentId")Long comment_id){
        try {
            commentRepository.deleteById(comment_id);
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();;
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @DeleteMapping(value = "collect/del")
    public Result delCollect(@RequestParam("userId")Long userId,@RequestParam("movieId")Long movieId){
        try {
            User user=userRepository.findById(userId).get();
            Movie movie=movieRepository.findById(movieId).get();
            List<Movie> movies =user.getCollectedMovies();
            JSONObject jsonObject =new JSONObject();
            if(movies.contains(movie)){
                movies.remove(movie);
                userRepository.save(user);
                jsonObject.put("msg","删除成功");
            }else{
                jsonObject.put("msg","删除失败，你没有收藏过该电影");
            }
            return Util.success(jsonObject);
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    // 电影的主页信息
    @GetMapping(value = "movie/movieDetails")
    public Result getMovieDetail(@RequestParam("movie_Id")Long movie_id,Long user_id){
        try {
            return Util.success(movieService.getMovieDetail(movie_id,user_id));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }




    @DeleteMapping(value = "movie/del")
    public  Result delMovie(@RequestParam("movieId")Long movieId){
        try {
            movieRepository.deleteById(movieId);
            Boolean bool = redisParse.delObject(movieId.toString(), RedisKeys.Movie);
            if(bool){
                return  Util.success(ExceptionEnums.DEL_SUCCESS);
            }else {
                return Util.failure(ExceptionEnums.UNKNOW_ERROR);
            }
        }catch (Exception e){
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }






//
//    @GetMapping(value = "movie/movieUpdate")
//    public Result getMovieDetail(Moive m){
//        try {
//            return Util.success(movieService.getMovieDetail(movie_id));
//        }catch (Exception e){
//            e.printStackTrace();
//            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
//        }
//    }


//    @PostMapping(value = "movie/add")
//    public Result addMovie(Double score,
//                           String brief,
//                           String name,
//                           Date releaseTime,
//                           String language,
//                           String country,
//                            MultipartFile cover_img){
//        try {
//
//            return Util.success();
//        }catch (Exception e){
//            e.printStackTrace();
//            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
//        }
//    }






    @Data
    private static class MovieInformation{
        //简介
        private String brief;
        //名字
        private String name;
        //上映时间
        private String releaseTime;

        private  String language;

        private  String country;
        //电影时长
        private String duration;

        private  MultipartFile showImage;
        //目前状态
        private  String state;



        //其他图片
        private List<MultipartFile> figureList;



        @Override
        public String toString() {
            return "MovieInformation{" +
                    "brief='" + brief + '\'' +
                    ", name='" + name + '\'' +
                    ", releaseTime=" + releaseTime +
                    ", language='" + language + '\'' +
                    ", country='" + country + '\'' +
                    ", duration='" + duration + '\'' +
                    ", showImage='" + showImage + '\'' +
                    ", state='" + state + '\'' +
                    ", figureList=" + figureList +
                    '}';
        }
    }




}
