package com.movie.Controller;

import com.alibaba.fastjson.JSONObject;
import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
import com.movie.Plugins.UserStatisticsListener;
import com.movie.Result.Result;
import com.movie.Serivce.StatisticsService;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class SysStatisticsController {

    @Autowired
    private StatisticsService statisticsService;


    @SysLog(value = "获取系统统计信息")
    @PostMapping(value = "user/statistics")
    public Result getUserCurrent(){
        try {
            AtomicInteger userCount=UserStatisticsListener.userCount;
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("sum",userCount);
            Date today=new Date();
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
            String date=simpleDateFormat.format(today);
            jsonObject.put("Tickets",statisticsService.getCountBuyDate("Done",date));
            statisticsService.addIndexVisitor();
            jsonObject.put("stat",StatisticsService.IndexVisitor);
            jsonObject.put("Schedule",statisticsService.getCountScheDate(date));
            jsonObject.put("Table1",statisticsService.getCountTicketsByWeek(date));
            return Util.success(jsonObject);
        }catch ( Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

}
