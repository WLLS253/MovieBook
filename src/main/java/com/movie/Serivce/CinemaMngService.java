package com.movie.Serivce;


import com.movie.Entity.Cinema;
import com.movie.Entity.CinemaMng;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaMngService {

    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;


    public  void addCinema(Cinema cinema){
        try {
            cinemaRepository.save(cinema);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public  void addCinemaMng(String cinemaName,String cinameMngName){
        List<Cinema> cinemaList=cinemaRepository.findByCinemaName(cinemaName);
        if(cinemaList.size()>0){
            Cinema cinema=cinemaList.get(0);
        }
    }





}


