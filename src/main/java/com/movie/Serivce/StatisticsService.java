package com.movie.Serivce;


import com.alibaba.fastjson.JSONObject;
import com.movie.Repository.BuyRepository;
import com.movie.Repository.ScheudalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.function.LongSupplier;

@Service
public class StatisticsService {

    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private ScheudalRepository scheudalRepository;


    public  static  Long IndexVisitor=Long.valueOf(0);


    public  Integer getCountBuyDate(String state,String date) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        Date dateAfter=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateAfter);
        calendar.add(Calendar.DATE,1);
        Date dateBefore=calendar.getTime();
        Integer sum=buyRepository.countAllByStateAndPurchaseDateBeforeAndPurchaseDateAfter(state,dateBefore,dateAfter);
        //System.out.println(sum);
        return  sum;
    }


    public  Integer getCountScheDate(String date) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        Date dateAfter=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateAfter);
        calendar.add(Calendar.DATE,1);
        Date dateBefore=calendar.getTime();
        Integer sum=scheudalRepository.countAllByStartDateAfterAndStartDateBefore(dateAfter,dateBefore);
        //System.out.println(sum);
        return  sum;
    }
    public Long addIndexVisitor(){
        IndexVisitor++;
        return  IndexVisitor;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void TimeFix(){
        IndexVisitor=Long.valueOf(0);
    }


}
