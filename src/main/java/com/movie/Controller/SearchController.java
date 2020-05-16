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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping(value = "moive/tagFliter")
    public Result filterMovies(@RequestBody FilterSetting filter_setting){
        try {
            System.out.println(filter_setting);
            return Util.success(searchService.filterMovies(filter_setting.start_year,filter_setting.end_year,filter_setting.key_string,filter_setting.tags));
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
        int start_year;
        int end_year;
        //搜索关键字
        String key_string;
        // 选择的 tags
        List<String> tags;


        @Override
        public String toString() {
            return "start_year"+start_year+" end_year"+end_year+" tags"+tags;
        }
    }




}
