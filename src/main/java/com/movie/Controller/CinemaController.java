package com.movie.Controller;



import antlr.collections.impl.LList;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Result.Result;
import com.movie.Serivce.CinemaMngService;
import com.movie.Serivce.CinemaService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping(value = "/cinema/add")
    public Result addCinema(String cinemaName,
                            String location,
                            String phone,
                            Integer grade,
                            String cinemaDescription){
        try {

            Cinema cinema1=new Cinema();
            cinema1.setCinemaName(cinemaName);
            cinema1.setLocation(location);
            cinema1.setPhone(phone);
            cinema1.setGrade(grade);
            cinema1.setCinemaDescription(cinemaDescription);
            return Util.success(cinemaRepository.save(cinema1));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PutMapping(value = "cinema/update")
    public Result updateCinema(Long cinema_id,String cinemaName,String location,String phone,String cinemaDescription,int grade){
        try {
            Cinema temp=cinemaRepository.findById(cinema_id).get();
            temp.setCinemaDescription(cinemaDescription);
            temp.setCinemaName(cinemaName);
            temp.setLocation(location);
            temp.setPhone(phone);
            temp.setGrade(grade);
            return  Util.success(cinemaRepository.save(temp));
        }catch (Exception e){
            e.printStackTrace();
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


    @PostMapping(value = "/cinemaMng/add")
    public Result addCinemaMng(Long cinema_id, int prio,String mng_phone,String mng_username,
        String mng_email,
        String mng_password,
        String mng_sex){
        try {
            CinemaMng cinemaMng1=new CinemaMng();
            cinemaMng1.setCinema(cinemaRepository.findById(cinema_id).get());
            cinemaMng1.setPrio(prio);
            cinemaMng1.setMngPhone(mng_phone);
            cinemaMng1.setMngUsername(mng_username);
            cinemaMng1.setMngEmail(mng_email);
            cinemaMng1.setMngSex(mng_sex);
            cinemaMng1.setMngPassword(mng_password);
            return Util.success(cinemaMngRepository.save(cinemaMng1));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "/cinemaHall/add")
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
















//    @PostMapping(value = "schedual/add")
//    private







}
