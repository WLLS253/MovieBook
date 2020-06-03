package com.movie.Controller;



import antlr.collections.impl.LList;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.FigureRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Result.Result;
import com.movie.Serivce.CinemaMngService;
import com.movie.Serivce.CinemaService;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.PageHelper;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.awt.*;
import java.util.List;

/**
 * 处理跨域@CrossOrigin现在可以先不用加
 */
//@CrossOrigin
@RestController
@Api("cinema")
public class CinemaController {


    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaMngService cinemaMngService;
    @Autowired
    private CinemaRepository cinemaRepository;
    @Autowired
    private ScheudalRepository scheudalRepository;

    @Autowired
    private  CinemaMngRepository cinemaMngRepository;

    @Autowired
    private UploadSerivce uploadSerivce;

    @PostMapping(value = "/cinema/add")
    public Result addCinema(String cinemaName,
                            String location,
                            String phone,
                            Integer grade,
                            String cinemaDescription,
                            List<MultipartFile> imgs){

        try {
            Cinema cinema1=new Cinema();
            cinema1.setCinemaName(cinemaName);
            cinema1.setLocation(location);
            cinema1.setPhone(phone);
            cinema1.setGrade(grade);
            cinema1.setCinemaDescription(cinemaDescription);
            cinema1.setCover_img_url(uploadSerivce.upImageFire(imgs.get(0)));
            return Util.success(cinemaRepository.save(cinema1));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }
    /*
    @PostMapping(value = "cinema/add")
    public Result addCinema(Cinema_Infor cinema_infor){
        try {
            return Util.success(cinemaService.addJsonCinema(cinema,cinema_infor.cinemaName,ci
            Cinema cinema=new Cinema();nema_infor.location,cinema_infor.phone,cinema_infor.grade,cinema_infor.cinemaDescription,cinema_infor.figureList));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }*/

    @PutMapping(value = "cinema/update")
    public Result updateCinema(Cinema_Infor cinema_infor){
        try {
            List<Cinema>cinemas=cinemaRepository.findByCinemaName(cinema_infor.cinemaName);
            if(cinemas.size()==0){
                return  Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
            }else {
                Cinema cinema=cinemas.get(0);
                return Util.success(cinemaService.addJsonCinema(cinema,cinema_infor.cinemaName,cinema_infor.location,cinema_infor.phone,cinema_infor.grade,cinema_infor.cinemaDescription,cinema_infor.figureList));
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @GetMapping(value = "/cinema/getList")
    public Result getCinemas(Pageable pageable) {
        try {
            Page<Cinema> cinemaMngs = cinemaRepository.findAll(pageable);
            List<Cinema> cinemaList =  cinemaMngs.getContent();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cinemas",cinemaList);
            JSONObject page_info = PageHelper.getPageInfoWithoutContent(cinemaMngs);
            jsonObject.put("pageInfo",page_info);
            return Util.success(jsonObject);
        }catch (Exception e){
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @DeleteMapping(value = "cinema/delete")
    public  Result deleteCinema(@RequestParam("cinemaId")Long cinemaId){
        try {
            cinemaRepository.deleteById(cinemaId);
            return Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }

    }

    @GetMapping(value = "/cinemaMng/getList")
    public Result getCinemaMngs(Pageable pageable) {
        try {
            Page<CinemaMng> cinemaMngs = cinemaMngRepository.findAll(pageable);
            List<CinemaMng> cinemaMngList =  cinemaMngs.getContent();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cinemaMngs",cinemaMngList);
            return Util.success(jsonObject);
        }catch (Exception e){
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "cinemaMng/add")
    public Result addCinemaMng(CinemaMng cinemaMng,@RequestParam(value = "cinema_id",required = false)Long cinema_id,@RequestParam(value = "image",required = false)MultipartFile image){
        try {
            String showImage=uploadSerivce.upImageFire(image);
            CinemaMng cinemaMng1=cinemaMng;
            Cinema cinema=cinemaRepository.findById(cinema_id).get();
            cinemaMng1.setCinema(cinema);
            cinemaMng1.setShowImage(showImage);
            JSONObject jsonObject=cinemaMngService.getCinemaMngJson(cinemaMngRepository.save(cinemaMng1));
            return Util.success(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 按照电影 来找到所有正在上映该电影院的电影
    @GetMapping(value = "cinema/movieScheduals")
    public Result getSchedualsbyId(Long movie_id,Date date){
        try {
            return Util.success(cinemaService.movieScheduals(movie_id,date));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PutMapping(value = "cinemaMng/update")
    public  Result updateCinemaMng(CinemaMng cinemaMng,@RequestParam(value = "image",required = false)MultipartFile image){
        try {
            CinemaMng cinemaMng1;

            List<CinemaMng>cinemaMngs=cinemaMngRepository.findByMngUsername(cinemaMng.getMngUsername());
            if(cinemaMngs.size()>0){
                cinemaMng1=cinemaMngs.get(0);

                String showImage=uploadSerivce.upImageFire(image);
                uploadSerivce.deleteimage(cinemaMng1.getShowImage());
                cinemaMng1.setShowImage(showImage);

                JSONObject jsonObject=cinemaMngService.updateCinemaMng(cinemaMng1,cinemaMng);
                return  Util.success(jsonObject);
            }else {
                return Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
            }

        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "cinemaHall/add")
    public Result addCinemaHall(@RequestParam("cinemaName")String cinameName, Hall hall){
        try {

            Hall ourHall=cinemaService.addCinemaHall(cinameName,hall);
            if(ourHall!=null){
                return  Util.success(ourHall);
            }else {
                return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
                /**
                 * 待完善
                 */
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "shedule/add")
    public  Result addSchedule(@RequestParam("movieId")Long movieId,@RequestParam("hallId")Long hallId,
                               @RequestParam("cinemaId")Long cinemaId,@RequestParam("start")String stratTime,@RequestParam("end")String endTime,
                               @RequestParam("price")Double price,@RequestParam("describe")String description)
    {

        try {

            Schedual schedual=cinemaService.addSchedule(movieId,hallId,cinemaId,stratTime,endTime,price,description);
            return Util.success(schedual);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.ADD_ERROR);
        }
    }

    @GetMapping(value = "cinema/getScheduals")
    public Result getScheduals(@RequestParam("cinema_id")Long cinema_id){
        try {
            Cinema c =  cinemaRepository.findById(cinema_id).get();
            return Util.success(scheudalRepository.findAllByCinema(c));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.ADD_ERROR);
        }
    }


    @Data
    private static class Cinema_Infor{

        private Long cinemaId;


        private String cinemaName;

        private String location;

        private String phone;

        private Integer grade;

        private String cinemaDescription;

        private List<MultipartFile>figureList;
    }














//    @PostMapping(value = "schedual/add")
//    private







}
