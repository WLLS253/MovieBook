package com.movie.Controller;


import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.MovieRepository;
import com.movie.Repository.TagRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.MovieService;
import com.movie.Serivce.SearchService;
import com.movie.Util.Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import org.springframework.data.domain.Pageable;
import java.util.Date;
import java.util.List;

@RestController
public class SearchController {

    @Autowired
    SearchService searchService;

//    @PostMapping(value = "moive/tagFliter")
//    public Result filterMovies(@RequestBody List<String> key_string){
//        try {
//            return Util.success(searchService.filterMovies(filter_setting.start_year,filter_setting.end_year,filter_setting.key_string,filter_setting.tags));
//        }catch (Exception e){
//            e.printStackTrace();
//            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
//        }
//    }

    @PostMapping(value = "moive/Fliter/brief")
    public Result filterMovies(@RequestBody FilterSetting filter_setting){
        try {
            Pageable p = PageRequest.of(filter_setting.pageNumber,filter_setting.pageSize);

            return Util.success(searchService.filterMoviesBrief(filter_setting.start_year,filter_setting.end_year,filter_setting.key_string,filter_setting.tags,filter_setting.date,filter_setting.state,filter_setting.cinema_name,p));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }
    @GetMapping(value = "moive/getHotMovies")
    public Result getHotMovies(){
        try {
            return Util.success(searchService.getHotMovies());
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 返回电影搜索页面下需求的所有该电影相关的信息
    @PostMapping(value = "moive/Fliter/details")
    public Result filterMoviesDetails(@RequestBody FilterSetting filter_setting){
        try {
            Pageable p = PageRequest.of(filter_setting.pageNumber,filter_setting.pageSize);
            System.out.println(filter_setting);
            return Util.success(searchService.filterMoviesDetail(filter_setting.start_year,filter_setting.end_year,filter_setting.key_string,filter_setting.tags,filter_setting.date,filter_setting.state,filter_setting.cinema_name,p));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value="movie/directorFilter")
    public Result filterMovies(@RequestParam("role") String role,@RequestParam("name") String name){
        try {
            return Util.success(searchService.filterMovies(role,name));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    // 按照  关键字获取电影院信息
    @PostMapping(value = "cinema/cinemaFliter")
    public Result filterCinemas(@RequestParam("cinema_name") String cinema_names){
        try {
            return Util.success(searchService.filterCinema(cinema_names));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }








    // requestBody classes
    @Data
    private static class FilterSetting{
        // 上映年份区间
        Integer start_year;
        Integer end_year;

        //搜索关键字
        String key_string;
        // 选择的 tags
        List<String> tags;

        //日程的日期
        Date date;

        //电影的 状态
        String state;

        // 影院名称
        String cinema_name;

        int pageNumber;
        int pageSize;

        @Override
        public String toString() {
            return "start_year"+start_year+" end_year"+end_year+" tags"+tags;
        }
    }




}
