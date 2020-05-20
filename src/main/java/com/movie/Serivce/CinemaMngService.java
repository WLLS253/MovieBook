package com.movie.Serivce;


import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Cinema;
import com.movie.Entity.CinemaMng;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Result.Result;
import jdk.nashorn.internal.runtime.regexp.JoniRegExp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
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

    public JSONObject getCinemaMngJson(CinemaMng cinemaMng){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("username",cinemaMng.getMngUsername());
        jsonObject.put("email",cinemaMng.getMngEmail());
        jsonObject.put("mngPhone",cinemaMng.getMngPhone());
        jsonObject.put("sex",cinemaMng.getMngSex());
        jsonObject.put("prio",cinemaMng.getPrio());
        jsonObject.put("cinema",cinemaMng.getCinema().getCinemaName());
        jsonObject.put("image",cinemaMng.getShowImage());
        return jsonObject;
    }

    public JSONObject   updateCinemaMng(CinemaMng cinemaMng1,CinemaMng cinemaMng2){
        cinemaMng1.updateObject(cinemaMng2);
        return  getCinemaMngJson(cinemaMng1);
    }





}


