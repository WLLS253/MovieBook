package com.movie.Serivce;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.Buy;
import com.movie.Entity.Cinema;
import com.movie.Entity.Schedual;
import com.movie.Repository.BuyRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Repository.ScheudalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

import org.springframework.scheduling.annotation.Scheduled;

import java.security.PublicKey;
import java.text.ParseException;
import java.util.function.LongSupplier;


@Service
public class StatisticsService {
    @Autowired
    ScheudalRepository scheudalRepository;
    @Autowired
    CinemaRepository cinemaRepository;
    @Autowired
    BuyRepository buyRepository;

    public JSONObject getMngStatistics(Long cinema_id,String date){
        JSONObject jsonObject = new JSONObject();


        return  jsonObject;
    }

    public JSONObject getBuyStatisticsByYears(Long cinema_id,int start_year,int end_year){
        JSONObject jsonObject = new JSONObject();
//        int year_diff = end_year - start_year + 1;
        for (int i =start_year;i<=end_year;i++){
            JSONObject year_json = new JSONObject();
            List<Buy> buys =  buyRepository.getIncomeStatistic(cinema_id,i);
            if(buys.size() == 0) continue;
            double total_income =0;
            for (Buy b:buys) {
                total_income += b.getPrice();
            }
            year_json.put("total_income",total_income);
            year_json.put("total_buys",buys.size());
            jsonObject.put(Integer.toString(i),year_json);
        }
        return  jsonObject;
    }

    public JSONObject getBuyStatisticsByDate(Long cinema_id,Date start_date,Date end_date){
        JSONObject jsonObject = new JSONObject();
        Calendar c = new GregorianCalendar();
        long days = (end_date.getTime()-start_date.getTime())/(1000 * 3600 * 24) + 1;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(start_date);
        for (int i =0;i<days;i++){
            JSONObject day_json = new JSONObject();
            String format_date = f.format(c.getTime());
            List<Buy> buys =  buyRepository.getIncomeStatistic(cinema_id,format_date);
            if(buys.size() != 0) {
                double total_income =0;
                for (Buy b:buys) {
                    total_income += b.getPrice();
                }
                day_json.put("total_income",total_income);
                day_json.put("total_buys",buys.size());
                day_json.put("day_of_week",c.getTime().toString().split(" ")[0]);
                jsonObject.put(format_date,day_json);
            }
            c.add(Calendar.DAY_OF_MONTH,1);
        }
        return jsonObject;
    }

    public JSONObject getIncomeInfoByDate(Long cinema_id,Date start_date,Date end_date){
        JSONObject jsonObject = new JSONObject();
        Cinema c = cinemaRepository.findById(cinema_id).get();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        List<Schedual>  scheduals =scheudalRepository.findAllByCinemaSchedualsByEndDate(cinema_id,f.format(start_date),f.format(end_date));
        JSONObject buyInfos = new JSONObject();
        if(scheduals.size() == 0){
            return jsonObject;
        }
        double total_price = 0.0;
        JSONObject buyInfo= new JSONObject();
        JSONArray paidArr= new JSONArray();
        JSONArray doneArr= new JSONArray();
        JSONArray refundArr= new JSONArray();
        String former_d = f.format(scheduals.get(0).getEndDate());
        for (Schedual s:scheduals) {
            String formated_date =f.format(s.getEndDate());
            if(former_d != formated_date){
                buyInfo.put("paid",paidArr);
                buyInfo.put("done",doneArr);
                buyInfo.put("refund",refundArr);
                buyInfo.put("total_income",total_price);
                buyInfos.put(former_d,buyInfo);
                total_price = 0.0;
                buyInfo = new JSONObject();
                paidArr = new JSONArray();
                doneArr = new JSONArray();
                refundArr = new JSONArray();
                former_d = formated_date;
            }
            List<Buy> buys=  s.getBuyList();
            System.out.println(buys.size());
            for (Buy b:buys) {
                String state =b.getState();
                switch (state){
                    case "paid":
                        total_price += b.getPrice();
                        paidArr.add(b);
                        break;
                    case "done":
                        total_price += b.getPrice();
                        doneArr.add(b);
                        break;
                    case "refund":
                        refundArr.add(b);
                        break;
                }
            }
        }
        buyInfo.put("paid",paidArr);
        buyInfo.put("done",doneArr);
        buyInfo.put("refund",refundArr);
        buyInfo.put("total_income",total_price);
        buyInfos.put(former_d,buyInfo);
        jsonObject.put("buy_Infos",buyInfos);

        return jsonObject;
    }



    public  static  Long IndexVisitor=Long.valueOf(0);


    public  Integer getCountBuyDate(String state,String date) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        Date dateAfter=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateAfter);
        calendar.add(Calendar.DATE,1);
        Date dateBefore=calendar.getTime();
        Integer sum=buyRepository.countAllByStateAndPurchaseDateBeforeAndPurchaseDateAfter(state,dateBefore,dateAfter);
        //System.out.println(sum);
        return  sum;
    }


    public  Integer getCountScheDate(String date) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        Date dateAfter=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(dateAfter);
        calendar.add(Calendar.DATE,1);
        Date dateBefore=calendar.getTime();
        Integer sum=scheudalRepository.countAllByStartDateAfterAndStartDateBefore(dateAfter,dateBefore);
        //System.out.println(sum);
        return  sum;
    }


    public Long addIndexVisitor(){
        IndexVisitor++;
        return  IndexVisitor;
    }

    public  JSONObject  getCountTicketsByWeek(String date) throws ParseException {
        JSONObject jsonObject=new JSONObject();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd ");
        Date date1=simpleDateFormat.parse(date);
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DAY_OF_WEEK,-1);
        List<Integer>Counts=new ArrayList<>();
        List<Integer>ScheCount=new ArrayList<>();
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<7;i++) {
            calendar.add(Calendar.DATE,1);
            Date date2=calendar.getTime();
            String dateStr=simpleDateFormat.format(date2);
            Counts.add(getCountBuyDate("Done",dateStr));
            ScheCount.add(getCountScheDate(dateStr));
        }
        jsonObject.put("TicketList",Counts);
        jsonObject.put("ScheList",ScheCount);

        return  jsonObject;
    }




    //@Scheduled(cron = "0 0 0 * * *")
    public void TimeFix(){
        IndexVisitor=Long.valueOf(0);
    }




}
