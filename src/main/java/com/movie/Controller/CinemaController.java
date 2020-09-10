package com.movie.Controller;



import antlr.collections.impl.LList;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
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

import com.movie.redis.RedisKeys;
import com.movie.redis.RedisParse;
import io.swagger.annotations.Api;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.util.*;

import java.util.List;

/**
 * 处理跨域@CrossOrigin现在可以先不用加
 */
//@CrossOrigin
@CrossOrigin
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

    @Autowired
    private RedisParse redisParse;

    @SysLog(value = "添加电影院")
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

    @SysLog(value = "更新电影院")
    @PutMapping(value = "cinema/update")
    public Result updateCinema(Cinema_Infor cinema_infor){
        try {
            List<Cinema>cinemas=cinemaRepository.findByCinemaName(cinema_infor.cinemaName);
            if(cinemas.size()==0){
                return  Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
            }else {
                Cinema cinema=cinemas.get(0);
                Long id = cinema.getId();
                cinema=cinemaService.addJsonCinema(cinema,cinema_infor.cinemaName,cinema_infor.location,cinema_infor.phone,cinema_infor.grade,cinema_infor.cinemaDescription,cinema_infor.figureList);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("cinema_info",cinema);
                redisParse.saveObject(id.toString(),jsonObject,RedisKeys.Cinema);
                return Util.success(cinema);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }
    @GetMapping(value = "/cinema/get")
    public Result getCinema(Long id) {
        try {
            JSONObject jsonObject;
            jsonObject = (JSONObject) redisParse.getObject(id.toString(),RedisKeys.Cinema);
            if(jsonObject != null){
                return  Util.success(jsonObject);
            }else {
                jsonObject = new JSONObject();
                jsonObject.put("cinema_info",cinemaRepository.findById(id));
                redisParse.saveObject(id.toString(),jsonObject, RedisKeys.Cinema);
                return Util.success(jsonObject);
            }
        }catch (Exception e){
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @SysLog(value = "获取电影院列表")
    @GetMapping(value = "/cinema/getList")
    public Result getCinemas(int pageSize,int pageNumber) {
        try {
            Pageable p = PageRequest.of(pageNumber,pageSize);
            Page<Cinema> cinemaMngs = cinemaRepository.findAll(p);
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


    @SysLog(value = "删除电影院")
    @DeleteMapping(value = "cinema/delete")
    public  Result deleteCinema(@RequestParam("cinemaId")Long cinemaId){
        try {
            cinemaRepository.deleteById(cinemaId);
            Boolean bool =redisParse.delObject(cinemaId.toString(),RedisKeys.Cinema);
            if(bool){
                return Util.success(ExceptionEnums.DEL_SUCCESS);
            }else {
                return Util.failure(ExceptionEnums.UNKNOW_ERROR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }

    }

    @SysLog(value = "获取影院管理员列表")
    @GetMapping(value = "/cinemaMng/getList")
    public Result getCinemaMngs(int pageSize,int pageNumber) {
        try {
            Pageable pageable = PageRequest.of(pageNumber,pageSize);
            Page<CinemaMng> cinemaMngs = cinemaMngRepository.findAll(pageable);
            List<CinemaMng> cinemaMngList =  cinemaMngs.getContent();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("cinemaMngs",cinemaMngList);
            jsonObject.put("pageInfo",PageHelper.getPageInfoWithoutContent(cinemaMngs));
            return Util.success(jsonObject);
        }catch (Exception e){
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "新增影院管理员")
    @PostMapping(value = "cinemaMng/add")
    public Result addCinemaMng(CinemaMng cinemaMng,@RequestParam(value = "cinema_id",required = false)Long cinema_id,@RequestParam(value = "image",required = false)MultipartFile image){
        try {

            CinemaMng cinemaMng1=cinemaMng;
            Cinema cinema=cinemaRepository.findById(cinema_id).get();
            cinemaMng1.setCinema(cinema);
            if(image!=null){
                String showImage=uploadSerivce.upImageFire(image);
                cinemaMng1.setShowImage(showImage);
            }
            JSONObject jsonObject=cinemaMngService.getCinemaMngJson(cinemaMngRepository.save(cinemaMng1));
            return Util.success(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 按照电影 来找到所有正在上映该电影院的电影
    @SysLog(value = "按照电影获取影院正在上映电影")
    @GetMapping(value = "cinema/movieScheduals")
    public Result getSchedualsbyId(Long movie_id,Date date){
        try {
            return Util.success(cinemaService.movieScheduals(movie_id,date));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "影院管理员信息更新")
    @PutMapping(value = "cinemaMng/update")
    public  Result updateCinemaMng(CinemaMng cinemaMng,@RequestParam(value = "image",required = false)MultipartFile image){
        try {
            CinemaMng cinemaMng1;

            List<CinemaMng>cinemaMngs=cinemaMngRepository.findByMngUsername(cinemaMng.getMngUsername());
            if(cinemaMngs.size()>0){
                cinemaMng1=cinemaMngs.get(0);

                if(image!=null){
                    String showImage=uploadSerivce.upImageFire(image);
                    uploadSerivce.deleteimage(cinemaMng1.getShowImage());
                    cinemaMng1.setShowImage(showImage);
                }
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


    //@SysLog(value = "新增影厅")
    @PostMapping(value = "cinemaHall/add")
    public Result addCinemaHall(@RequestParam("cinemaId")Long cinemaId, Hall_Infor hall){
        try {

            Cinema cinema=cinemaRepository.findById(cinemaId).get();
            Hall hall2=new Hall();
            hall2.setHallName(hall.hallName);
            hall2.setLayout(hall.layout);
            hall2.setRow(hall.row);
            hall2.setCol(hall.col);
            hall2.setHallType(hall.hallType);
            Hall ourHall=cinemaService.addCinemaHall(cinema,hall2,hall.figureList);
            if(ourHall!=null){
                return  Util.success(ourHall);
            }else {
                return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
                /**
                 * 待完善
                 */
            }
        }catch ( NoSuchElementException e){
            return  Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "新增排片")
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


    @SysLog(value = "简单删除排片")
    @DeleteMapping(value = "schedual/delEasy")
    public Result delSchedualEasy(@RequestParam("sche_id")Long sche_id){
        try {
                scheudalRepository.deleteById(sche_id);
                return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "验证删除排片")
    @DeleteMapping(value = "schedual/del")
    public Result delSchedual(@RequestParam("sche_id")Long sche_id,@RequestParam("cinema_id")Long cinema_id){
        try {
            Schedual schedual=scheudalRepository.findById(sche_id).get();
            if(cinema_id==schedual.getCinema().getId()){
                scheudalRepository.deleteById(sche_id);
                return  Util.success(ExceptionEnums.DEL_SUCCESS);
            }else {
                return  Util.failure(ExceptionEnums.MNG_NO_AUTHOR);
            }
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PutMapping(value = "schedual/update")
    public  Result upadteSchedual(@RequestParam("schedualId")Long schedual_id ,@RequestParam("movieId")Long movieId,@RequestParam("hallId")Long hallId,
                                  @RequestParam("cinemaId")Long cinemaId,@RequestParam("start")String stratTime,@RequestParam("end")String endTime,
                                  @RequestParam("price")Double price,@RequestParam("describe")String description)
    {
        try {

            Schedual schedual=cinemaService.updateSchedule(schedual_id,movieId,hallId,cinemaId,stratTime,endTime,price,description);
            return Util.success(schedual);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UPDATE_ERROR);
        }
    }



    @SysLog(value = "获取电影排片")
    @GetMapping(value = "cinema/getScheduals")
    public Result getScheduals(@RequestParam("cinema_id")Long cinema_id,int pageNumber,int pageSize){
        try {
            JSONObject jsonObject = new JSONObject();
            Pageable p = PageRequest.of(pageNumber,pageSize);
            Cinema cinema=cinemaRepository.findById(cinema_id).get();
            Page<Schedual> scheduals=scheudalRepository.findAllByCinema_Id(cinema_id,p);
            jsonObject.put("schedual_info",scheduals.getContent());
            jsonObject.put("page_info",PageHelper.getPageInfoWithoutContent(scheduals));
            return  Util.success(jsonObject);
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



    @Data
    private static class Hall_Infor{

        private Integer col;

        private Integer row;

        private String hallType;

        private String layout;

        private String hallName;


        private List<MultipartFile>figureList;
    }












//    @PostMapping(value = "schedual/add")
//    private







}
