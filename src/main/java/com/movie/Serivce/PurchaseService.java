package com.movie.Serivce;


import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Buy;
import com.movie.Entity.Schedual;
import com.movie.Entity.Ticket;
import com.movie.Entity.User;
import com.movie.Repository.BuyRepository;
import com.movie.Repository.ScheudalRepository;
import com.movie.Repository.TicketRepository;
import com.movie.Repository.UserRepository;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.ServiceMode;
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

    //@Transactional(rollbackFor = {RuntimeException.class})
    public JSONObject  purchase(Integer row, Integer col, Double price, Long userId, Long schedualId){
        Schedual schedual=scheudalRepository.findById(schedualId).get();
        User user=userRepository.findById(userId).get();

        Ticket ticket=new Ticket();
        ticket.setAvaliable(false);
        ticket.setPrice(price);
        //ticket.setSchedual(schedual);
        ticket.setTicketRow(row);
        ticket.setTicketCol(col);
        ticket.setUser(user);

        System.out.println(ticket);
        ticketRepository.save(ticket);

//
//        List<Ticket>tickets=user.getTicketList();
//        tickets.add(ticket);
//        user.setTicketList(tickets);
//        userRepository.save(user);
//


        Buy buy=new Buy();
        Date date=new Date();
        buy.setPurchaseDate(date);
        buy.setState("0");
        buy.setTicket(ticket);
        buy.setUser(user);

//        System.out.println(buy);
        buyRepository.save(buy);
//


        JSONObject jsonObject=new JSONObject();
        jsonObject.put("userId",user.getId());
        jsonObject.put("username",user.getUsername());
        jsonObject.put("cinema",schedual.getCinema());
        jsonObject.put("start",schedual.getStartDate());
        jsonObject.put("end",schedual.getEndDate());
        jsonObject.put("hall",schedual.getHall().getHallName());
        jsonObject.put("row",row);
        jsonObject.put("col",col);
        jsonObject.put("price",price);
        return jsonObject;

    }
}
