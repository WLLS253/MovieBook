package com.movie.Serivce;


import com.movie.Entity.Cinema;
import com.movie.Entity.CinemaMng;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaMngService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;


    public Cinema addCinema(Cinema cinema){
        try {
            return cinemaRepository.save(cinema);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public  void addCinemaMng(String cinemaName,String cinameMngName){
        List<Cinema> cinemaList=cinemaRepository.findByCinemaName(cinemaName);
        if(cinemaList.size()>0){
            Cinema cinema=cinemaList.get(0);
        }
    }





}


