package com.movie.Controller;


import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Result.Result;
import com.movie.Serivce.CinemaService;
import com.movie.Serivce.MovieService;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private FigureRepository figureRepository;

    @Autowired
    private CinemaService cinemaService;


    @PostMapping(value = "movie/add")
    public Result addMovie(MovieInformation movieInformation) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date releaseTime=simpleDateFormat.parse(movieInformation.releaseTime);
        Movie movie1=new Movie();
        movie1.setBrief(movieInformation.brief);
        movie1.setCountry(movieInformation.country);
        movie1.setDuration(movieInformation.brief);
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
    public  Result addComment(@RequestParam("userId")Long userId,@RequestParam("movieId")Long movieId,@RequestParam(value = "content",required = false)String content,@RequestParam(value = "title",required = false)String title){
        try {
            User user=userRepository.findById(userId).get();
            Movie movie=movieRepository.findById(movieId).get();
            Comment comment=new Comment();

            if(content!=null){
                comment.setContent(content);
            }
            if(title!=null){
                comment.setTitle(title);
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
    public Result getMovieCommentList(@RequestParam("movieId")Long movieId,int pageSize,int pageNumber){
        try {
            Sort s = Sort.by(Sort.Direction.ASC,"createdTime");
            Pageable p = PageRequest.of(pageNumber,pageSize,s);
            return Util.success(movieService.getMovieComments(movieId,p));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 电影的主页信息
    @GetMapping(value = "movie/movieDetails")
    public Result getMovieDetail(long movie_id){
        try {
            return Util.success(movieService.getMovieDetail(movie_id));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @DeleteMapping(value = "movie/del")
    public  Result delMovie(@RequestParam("movieId")Long movieId){
        try {
            movieRepository.deleteById(movieId);
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
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
