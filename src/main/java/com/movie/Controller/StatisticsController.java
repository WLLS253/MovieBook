package com.movie.Controller;

import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Movie;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TagRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.MovieService;
import com.movie.Serivce.SearchService;
import com.movie.Serivce.StatisticsService;
import com.movie.Util.Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

@RestController
public class StatisticsController {

    @Autowired
    StatisticsService statisticsService;

    @GetMapping(value = "statistics/getBuyStatisByDate")
    public Result getBuyStatisByDate(Long cienma_id, Date start_date,Date end_date) {

        try {
            JSONObject jsonObject = statisticsService.getBuyStatisticsByDate(cienma_id,start_date,end_date);
            return Util.success(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.USER_TEL_ERROR);
        }
    }

    @GetMapping(value = "statistics/getBuyStatisByYear")
    public Result getBuyStatisByYear(Long cienma_id,int start_year,int end_year) {

        try {
            JSONObject jsonObject = statisticsService.getBuyStatisticsByYears(cienma_id,start_year,end_year);
            return Util.success(jsonObject);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.USER_TEL_ERROR);
        }
    }



}
