package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CinemaService {


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private ScheudalRepository scheudalRepository;

    @Autowired
    private  UploadSerivce uploadSerivce;

    @Autowired
    private  FigureRepository figureRepository;

    public  Hall addCinemaHall(String cinemaName, Hall hall){
        List<Cinema >cinemaList=cinemaRepository.findByCinemaName(cinemaName);
        Cinema cinema;
        if(cinemaList.size()>0){
            cinema=cinemaList.get(0);
            hall.setCinema(cinema);
            System.out.println(hall);
            return  hallRepository.save(hall);
        }else {
            return  null;
        }
    }

    public  Schedual addSchedule(Long movieId,Long hallId,Long cinemaId,String startTime,String endTime,Double price,String description) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start=simpleDateFormat.parse(startTime);
        Date  end=simpleDateFormat.parse(endTime);
        Schedual schedual=new Schedual();
        Cinema cinema=cinemaRepository.findById(cinemaId).get();
        Hall hall=hallRepository.findById(hallId).get();
        Movie movie=movieRepository.findById(movieId).get();

        schedual.setCinema(cinema);
        schedual.setEndDate(end);
        schedual.setMovie(movie);
        schedual.setHall(hall);
        schedual.setPrice(price);
        schedual.setStartDate(start);

         return  scheudalRepository.save(schedual);
    }

    public Cinema addJsonCinema( Cinema cinema,String cinemaName, String location, String phone, Integer grade, String cinemaDescription, List<MultipartFile>figureList){

        cinema.setGrade(grade);
        cinema.setPhone(phone);
        cinema.setCinemaName(cinemaName);
        cinema.setCinemaDescription(cinemaDescription);
        cinema.setLocation(location);
        List<Figure>figures=new ArrayList<>();
        for (MultipartFile multipartFile : figureList) {
            Figure figure=new Figure();
            String url=uploadSerivce.upImageFire(multipartFile);
            figure.setImageurl(url);
            figures.add(figure);
            figureRepository.save(figure);
        }
        cinema.setFigureList(figures);

        return cinemaRepository.save(cinema);
    }


    public  JSONObject getStaff(Staff staff){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("staff_id",staff.getId());
        jsonObject.put("name",staff.getStaffName());
        jsonObject.put("brief",staff.getStaffBrief());
        jsonObject.put("image",staff.getShowImage());
        JSONArray jsonArray=new JSONArray();
        List<Movie>movieList=staff.getMovieList();

        for (Movie movie : movieList) {
            JSONObject temp=new JSONObject();
            temp.put("movie_id",movie.getId());
            temp.put("name",movie.getName());
            temp.put("image",movie.getShowImage());
            temp.put("score",movie.getScore());
            temp.put("brief",movie.getBrief());
            jsonArray.add(temp);
        }
        jsonObject.put("movieList",jsonArray);
        return  jsonObject;
    }


}
