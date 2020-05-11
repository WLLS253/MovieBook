package com.movie.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Schedual;
import com.movie.Entity.Ticket;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.HallRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Repository.TicketRepository;
import com.movie.Result.Result;
import com.movie.Serivce.PurchaseService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
public class HallController  {
    @Autowired
    private HallRepository hallRepository;

    @Autowired
    private ScheudalRepository scheudalRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private PurchaseService purchaseService;

    @GetMapping(value = "ticket/choose")
    public Result result(@RequestParam("schedualId")Long schedualId){
        try {
            Schedual schedual=scheudalRepository.findById(schedualId).get();
            List<Ticket>tickets=ticketRepository.findBySchedual(schedual);
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("tickets",tickets);
            return Util.success(jsonObject);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


}
