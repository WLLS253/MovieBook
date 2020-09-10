package com.movie.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Plugins.SysLog;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.HallRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Repository.TicketRepository;
import com.movie.Result.Result;
import com.movie.Serivce.CinemaService;
import com.movie.Serivce.PurchaseService;
import com.movie.Util.PageHelper;
import com.movie.Util.Util;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.swing.*;
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

    @Autowired
    private  CinemaService cinemaService;

    @Autowired
    private CinemaRepository cinemaRepository;


    @SysLog(value = "选择电影票")
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


    @SysLog(value = "更新影厅")
    @PutMapping(value = "cinemaHall/update")
    public Result updateCinemaHall(@RequestParam("cinemaId")Long cinemaId, Hall_infor hall_infor){
        try {

            //System.out.println(hall_infor.getFigureList());
            Hall hall_info=new Hall();
            hall_info.setCol(hall_infor.col);
            hall_info.setRow(hall_infor.row);
            hall_info.setHallName(hall_infor.hallName);
            hall_info.setHallType(hall_infor.hallType);
            hall_info.setLayout(hall_infor.layout);
            Hall hall=cinemaService.updateHall(hall_infor.hallId,hall_info,hall_infor.figureList);
            return  Util.success(hall);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @SysLog(value = "删除影厅")
    @DeleteMapping(value = "cinemaHall/del")
    public Result delHall(@RequestParam("hallId")Long id){
        try {
            hallRepository.deleteById(id);
            return  Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @SysLog(value = "查询电影院影厅")
    @GetMapping(value = "cinemaHall/getList")
    public Result getHallList(@RequestParam("cinema_id")Long cinema_id,int pageNumber,int pageSize){
        try {
            JSONObject jsonObject = new JSONObject();
            Pageable p = PageRequest.of(pageNumber,pageSize);
           Cinema cinema=cinemaRepository.findById(cinema_id).get();
           Page<Hall> halls=hallRepository.findByCinema(cinema,p);
           jsonObject.put("hall_infos",halls.getContent());
            jsonObject.put("page_infos",PageHelper.getPageInfoWithoutContent(halls));
           return  Util.success(jsonObject);
        }catch (NoSuchElementException e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNFIND_DATA_ERROR);
        }
        catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @Data
    private  static class  Hall_infor{

        private  Long hallId;

        private Integer col;

        private Integer row;

        private String hallType;

        private String layout;

        private String hallName;

        private List<MultipartFile>figureList;
    }


}
