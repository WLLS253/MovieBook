package com.movie.Controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Staff;
import com.movie.Enums.ExceptionEnums;
import com.movie.Result.Result;
import com.movie.Serivce.PurchaseService;
import com.movie.Util.Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
public class PurchaseController {


    @Autowired
    PurchaseService purchaseService;

    @GetMapping("purchase/seatInfo")
    public Result getSeatInfo(@RequestParam(value = "schedualId")Long schedualId){
        try {
            return Util.success(purchaseService.getSeatInfo(schedualId));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    //结算界面
    @PostMapping("purchase/checkOut")
    public Result checkOut(@RequestBody Ticket_info ticket_infos){
        try {
//            JSONArray ticket_infos = JSONArray.parseArray(ticket_infos_string);
            return Util.success(purchaseService.checkOut(ticket_infos.schedualId,ticket_infos.row_col));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "purchase/buy")
    public  Result purchaseTickets(@RequestBody  Buy_Info buy_info)
//            (@RequestParam("price") Double price,
//                             @RequestParam("userId") Long userId,@RequestParam("sche") Long schedualId,@RequestBody List<Long> ticket_ids)
    {

        try {
            JSONObject json=purchaseService.purchase(buy_info.schedualId,buy_info.ticket_ids,buy_info.userId,buy_info.price);
            return Util.success(json);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }

    }

    @PostMapping(value = "purchase/refund")
    public Result refund(@RequestParam("buy_id") Long buy_id){
        try {
            return Util.success(purchaseService.refund(buy_id));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    // requestBody 结构
    @Data
    private static class Buy_Info{
        Double price;
        Long userId;
        Long schedualId;
        List<Long> ticket_ids;
    }

    @Data
    private static class Ticket_info{
        Long schedualId;
        List<int[]> row_col;
    }

}
