package com.movie.Serivce;

import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.MyLogger;
import com.movie.Enums.Role;
import com.movie.Repository.MyLoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class LoggerService {

    @Autowired
    private MyLoggerRepository  myLoggerRepository;

    public JSONObject getLoggeer(String Date) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2=simpleDateFormat.parse(Date);

        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByCreateDateAfter(date2);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }

    public JSONObject getLoggeeRecent() throws ParseException {
        Date date=new Date();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE,-7);
        Date date2=calendar.getTime();
        System.out.println(date2);
        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByCreateDateAfter(date2);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }


    public JSONObject getLoggerByRole(Role role) throws ParseException {

        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByRole(role);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }

    public JSONObject getLoggerByUserId(String userId)  {

        List<MyLogger>myLoggerList=myLoggerRepository.findMyLoggerByUserId(userId);
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("loggers",myLoggerList);
        return  jsonObject;
    }


}
