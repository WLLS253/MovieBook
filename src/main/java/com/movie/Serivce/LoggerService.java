package com.movie.Serivce;

import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.MyLogger;
import com.movie.Enums.Role;
import com.movie.Repository.MyLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LoggerService {

    @Autowired
    private MyLoggerRepository  myLoggerRepository;

    public JSONObject getLoggeer(String Date,String Date2,Pageable pageable) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2=simpleDateFormat.parse(Date);

        List<MyLogger>myLoggerList=new ArrayList<>();
        if(Date2!=null){
            Date date3=simpleDateFormat.parse(Date2);
            myLoggerList=myLoggerRepository.findMyLoggerByCreateDateAfterAndCreateDateBefore(date2,date3,pageable).getContent();
        }else {
            myLoggerList=myLoggerRepository.findByCreateDateAfter(date2,pageable).getContent();
        }



//        Pageable p= PageRequest.of(5,1);
//        Page<MyLogger>myLoggerPage=myLoggerRepository.findMyLoggerByCreateDateAfter(date2,p);
//        List<MyLogger>myLoggerList=myLoggerPage.getContent();
//        System.out.println(myLoggerList);

        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }

    public JSONObject getLoggerOneDay(String Date,Pageable pageable) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        Date date2=simpleDateFormat.parse(Date);

        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date2);
        calendar.add(Calendar.DATE,1);
        Date date3=calendar.getTime();
//        Pageable p= PageRequest.of(5,1);
//        Page<MyLogger>myLoggerPage=myLoggerRepository.findMyLoggerByCreateDateAfter(date2,p);
//        List<MyLogger>myLoggerList=myLoggerPage.getContent();
//        System.out.println(myLoggerList);
        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByCreateDateAfterAndCreateDateBefore(date2,date3,pageable).getContent();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }
    public JSONObject getLoggeeRecent(Pageable pageable) throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-7);
        Date date2=calendar.getTime();
        System.out.println(date2);

//        Pageable p= PageRequest.of(5,2);
//        Page<MyLogger>myLoggerPage=myLoggerRepository.findMyLoggerByCreateDateAfter(date2,p);
//        List<MyLogger>myLoggerList=myLoggerPage.getContent();
        Page<MyLogger> p = myLoggerRepository.findByCreateDateAfter(date2,pageable);
        List<MyLogger>myLoggerList=p.getContent();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }


    public JSONObject getLoggerByRole(Role role,Pageable pageable) throws ParseException {
        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByRole(role,pageable).getContent();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }

    public JSONObject getLoggerByUserId(String userId,Pageable pageable)  {

        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByUserId(userId,pageable).getContent();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }


}
