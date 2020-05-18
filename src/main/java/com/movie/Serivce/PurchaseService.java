package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.movie.Entity.*;
import com.movie.Enums.States;
import com.movie.Repository.BuyRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Repository.TicketRepository;
import com.movie.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;



@Service
public class PurchaseService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScheudalRepository scheudalRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private BuyRepository buyRepository;


    public JSONObject getSeatInfo(Long schedualId){
        Schedual schedual=scheudalRepository.findById(schedualId).get();
        Hall hall = schedual.getHall();
        List<Ticket> tickets = schedual.getTicketList();
        int row = hall.getRow();
        int col = hall.getCol();
        boolean[][] seat_info = new boolean[row][col];

        for (Ticket t: tickets) {
            if(t.isAvaliable()){
                seat_info[t.getTicketRow()-1][t.getTicketCol()-1] = true;
            }else{
                seat_info[t.getTicketRow()-1][t.getTicketCol()-1] = false;
            }
        }
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hall_info",hall);
        jsonObject.put("seat_info",seat_info);
        return  jsonObject;
    }


    //购买结算页面

    public JSONObject checkOut(Long schedual_id,List<int[]> row_col){
        Schedual schedual = scheudalRepository.findById(schedual_id).get();
        int selected_size =  row_col.size();
        boolean success = true;
        if(selected_size == 0){
            return null; // 返回空
        }
        JSONArray ticket_infos = new JSONArray();
        for (int i=0;i<selected_size;i++){
            int[] seat_info = row_col.get(i);
            int row =(int)seat_info[0];
            int col =(int)seat_info[1];
            Ticket ticket = findTicketBySeat(schedual,row,col); //select_seat_info.get(i);
            if(ticket==null){
                ticket =new Ticket();
                ticket.setAvaliable(true);
                ticket.setTicketRow(row);
                ticket.setTicketCol(col);
                ticketRepository.save(ticket);
            }
            boolean ticket_avaliable=ticket.isAvaliable();
            JSONObject ticket_info = new JSONObject();
            ticket_info.put("id",ticket.getId());
            ticket_info.put("row",row);
            ticket_info.put("col",col);
            ticket_info.put("avaliable",ticket.isAvaliable());
            ticket_infos.add(ticket_info);
            if(!ticket_avaliable){
                success = false;
            }
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("ticket_infos",ticket_infos);
        jsonObject.put("success",success);
        if(!success){
            jsonObject.put("msg","结算失败，请重新选票");
        }
        jsonObject.put("price",success?calTotalPrice(schedual,selected_size):null);

        return  jsonObject;

    }

    //总价计算
    //TODO 在这里加入优惠折算
    //TODO msg 转为 code
    //TALK JSONIGNORE
    public float calTotalPrice(Schedual schedual,int ticket_num){
        return (float)(schedual.getPrice() * ticket_num);
    }

    //@Transactional(rollbackFor = {RuntimeException.class})
    public JSONObject  purchase(Long schedualId,List<Long> ticket_ids, Long userId,Double price){
        Schedual schedual=scheudalRepository.findById(schedualId).get();
        User user=userRepository.findById(userId).get();
        List<Ticket> purchased_tickets= ticketRepository.findAllById(ticket_ids);
        JSONObject jsonObject = new JSONObject();


        //核对价格 ，如果不对则 说明价格中途发生了改变
            //要求用户重新选择
        if(calTotalPrice(schedual,ticket_ids.size()) != price){
            jsonObject.put("msg","票价发生变动，请重新购买");
            return jsonObject;
        }

        boolean success = true;

        JSONArray purchased_ticket_infos = new JSONArray();
        for (Ticket t: purchased_tickets) {
            int row = t.getTicketRow();
            int col = t.getTicketCol();
            if(!t.isAvaliable()){
                success = false;
                jsonObject.put("msg",row+"行" + col +"列的座位已被购买，请重新选择座位");
                return jsonObject;
            }else{
                t.setAvaliable(false);
                JSONObject ticket_info = new JSONObject();
                ticket_info.put("row",row);
                ticket_info.put("col",col);
                ticket_info.put("id",t.getId());
                purchased_ticket_infos.add(ticket_info);
            }
        }
        Buy buy=new Buy();
        Date date=new Date();
        buy.setPurchaseDate(date);
        buy.setState(States.Buy_State.Ready.name());
        buy.setTickets(purchased_tickets);
        buy.setUser(user);
        buyRepository.save(buy);
        ticketRepository.saveAll(purchased_tickets);



        JSONObject purchase_info=new JSONObject();
        purchase_info.put("cinema",schedual.getCinema());
        purchase_info.put("start",schedual.getStartDate());
        purchase_info.put("end",schedual.getEndDate());
        purchase_info.put("hall_name",schedual.getHall().getHallName());
        purchase_info.put("buy_record",buy);
        jsonObject.put("purchase_info",purchase_info);
        jsonObject.put("ticket_infos",purchased_ticket_infos);
        jsonObject.put("msg","购买成功");

        return jsonObject;
    }
    public JSONObject refund(Long buy_id){
        JSONObject jsonObject = new JSONObject();
        Buy buy = buyRepository.findById(buy_id).get();
        int hour_limit = 10;
        //检查日期
        List<Ticket> tickets = buy.getTickets();
        Schedual schedual =  tickets.get(0).getSchedual();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(schedual.getStartDate());

        //TODO 设置 退票时间限制
        calendar.add(Calendar.HOUR,-hour_limit);
        if(now.before(calendar.getTime())){// 在退票期限之前
            buy.setState(States.Buy_State.Refund.name());
            for (Ticket t: tickets) {
                t.setAvaliable(true);
            }
            buyRepository.save(buy);
            ticketRepository.saveAll(tickets);
            //TODO 退款状态等等的枚举
            jsonObject.put("msg","退款成功");
        }else{
            jsonObject.put("msg","只能在电影开始"+hour_limit+"小时之前退票");
        }
        return jsonObject;
    }


    // private
    private Ticket findTicketBySeat(Schedual schedual,int row,int col){
        List<Ticket> tickets = schedual.getTicketList();
        Ticket ticket = null;
        for (Ticket t:tickets) {
            if(t.getTicketRow() == row && t.getTicketCol() == col){
                ticket = t;
                break;
            }
        }
        return ticket;
    }
}
