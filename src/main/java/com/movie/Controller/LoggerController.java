package com.movie.Controller;


import com.movie.Enums.ExceptionEnums;
import com.movie.Enums.Role;
import com.movie.Repository.MyLoggerRepository;
import com.movie.Result.Result;
import com.movie.Serivce.LoggerService;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Transactional
@RestController
public class LoggerController {

    @Autowired
    private MyLoggerRepository myLoggerRepository;

    @Autowired
    private LoggerService loggerService;

    @PostMapping(value = "myLogger/getBydate")
    public Result getLogger(@RequestParam("dateAfter") String Date,@RequestParam(value = "dateBefore",required = false)String date2,Integer pageSize ,Integer pageNumber){
        try {
            Pageable pageable= packPage(pageSize,pageNumber);
            return Util.success(loggerService.getLoggeer(Date,date2,pageable));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "myLogger/getByOnedate")
    public Result getLoggerOneDay(@RequestParam("date")String Date,Integer pageSize ,Integer pageNumber){
        try {
            Pageable pageable= packPage(pageSize,pageNumber);
            return Util.success(loggerService.getLoggerOneDay(Date,pageable));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }



    @PostMapping(value = "myLogger/getRecent")
    public Result getLoggerRecent(Integer pageSize ,Integer pageNumber){
        try {
            Pageable pageable= packPage(pageSize,pageNumber);
            return Util.success(loggerService.getLoggeeRecent(pageable));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "loggers/getByRole")
    public  Result getLoggerByRole(@RequestParam("role")String role,Integer pageSize ,Integer pageNumber){
        try {
            Pageable pageable= packPage(pageSize,pageNumber);
            Role role1=Role.valueOf(role);
            System.out.println(role1);
            return  Util.success(loggerService.getLoggerByRole(role1,pageable));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "myLogger/getByUser")
    public  Result getLoggerByUser(@RequestParam("userId")String user_id,Integer pageSize ,Integer pageNumber ){
        try {
            Pageable pageable= packPage(pageSize,pageNumber);
            return  Util.success(loggerService.getLoggerByUserId(user_id,pageable));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @DeleteMapping(value = "myLogger/delAll")
    public Result delLogger(){
        try {
            myLoggerRepository.deleteAll();
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }



    @DeleteMapping(value = "myLogger/delByDate")
    public Result delLoggerByDate(@RequestParam("date")String date){
        try {
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date1=simpleDateFormat.parse(date);
            myLoggerRepository.deleteByCreateDateAfter(date1);
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    private Pageable packPage(Integer pageSize,Integer pageNumber){
        Pageable p;
//        Sort s = Sort.by(Sort.Direction.ASC,"creatDate");
        if(pageSize == null || pageNumber == null){
            p = PageRequest.of(0,10);
        }else
            p = PageRequest.of(pageNumber,pageSize);

        return p;
    }



}
