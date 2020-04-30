package com.movie.Serivce;


import com.movie.Entity.Cinema;
import com.movie.Entity.Hall;
import com.movie.Entity.Movie;
import com.movie.Entity.Schedual;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public  void addCinemaHall(String cinemaName, Hall hall){
        List<Cinema >cinemaList=cinemaRepository.findByCinemaName(cinemaName);
        Cinema cinema;
        if(cinemaList.size()>0){
            cinema=cinemaList.get(0);
            hall.setCinema(cinema);
            hallRepository.save(hall);
        }else {
            /**
             * 处理方法
             */
            System.out.println(Util.failure(ExceptionEnums.UNFIND_DATA_ERROR));
        }
    }

    public  void addSchedule(Long movieId,Long hallId,Long cinemaId,String startTime,String endTime,Double price,String description) throws ParseException {

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

        scheudalRepository.save(schedual);
    }



}
