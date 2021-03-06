package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.movie.Entity.*;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.*;
import com.movie.Util.Util;
import com.movie.redis.RedisParse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.OriginTrackedValue;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

    @Autowired
    private  UploadSerivce uploadSerivce;

    @Autowired
    private  FigureRepository figureRepository;


    @Autowired
    private RedisParse redisParse;

    public  Hall addCinemaHall(Cinema cinema, Hall hall,List<MultipartFile>fileList){




        List<Figure>figures=new ArrayList<>();
        if(fileList!=null){
            if(fileList!=null){
                for (MultipartFile multipartFile : fileList) {
                    String image=uploadSerivce.upImageFire(multipartFile);
                    Figure figure=new Figure();
                    figure.setImageurl(image);
                    figures.add(figure);
                }
                figureRepository.saveAll(figures);
            }
        }
        hall.setFigureList(figures);
            hall.setCinema(cinema);
            System.out.println(hall);
            return  hallRepository.save(hall);

    }

    public  Schedual addSchedule(Long movieId,Long hallId,Long cinemaId,String startTime,String endTime,Double price,String description) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start=simpleDateFormat.parse(startTime);
        Date  end=simpleDateFormat.parse(endTime);
        Schedual schedual=new Schedual();
        Cinema cinema=cinemaRepository.findById(cinemaId).get();
        Hall hall=hallRepository.findById(hallId).get();
        Movie movie=movieRepository.findById(movieId).get();

        schedual.setSchedDescription(description);
        schedual.setCinema(cinema);
        schedual.setEndDate(end);
        schedual.setMovie(movie);
        schedual.setHall(hall);
        schedual.setPrice(price);
        schedual.setStartDate(start);

         return  scheudalRepository.save(schedual);
    }

    public  Schedual updateSchedule(Long schedualId,Long movieId,Long hallId,Long cinemaId,String startTime,String endTime,Double price,String description) throws ParseException {

        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start=simpleDateFormat.parse(startTime);
        Date  end=simpleDateFormat.parse(endTime);
        Schedual schedual=scheudalRepository.findById(schedualId).get();
        Cinema cinema=cinemaRepository.findById(cinemaId).get();
        Hall hall=hallRepository.findById(hallId).get();
        Movie movie=movieRepository.findById(movieId).get();

        schedual.setCinema(cinema);
        schedual.setEndDate(end);
        schedual.setMovie(movie);
        schedual.setHall(hall);
        schedual.setPrice(price);
        schedual.setStartDate(start);
        schedual.setSchedDescription(description);

        return  scheudalRepository.save(schedual);
    }

    // 通过moive 来获取所有正在上映该电影的 电影院
    public JSONObject  movieScheduals(Long movie_id,Date date){
        JSONObject jsonObject = new JSONObject();
        JSONObject cinArrs  = new JSONObject();

        List<Object[]> cin_sches = cinemaRepository.getOnShowCinemas(movie_id,date);
        for (Object[] row:cin_sches) {
            if(cinArrs.containsKey(row[0].toString())){
                JSONObject sche_info = new JSONObject();
                sche_info.put("sched_id",row[2]);
                sche_info.put("start_date",row[3]);
                JSONObject info =(JSONObject) cinArrs.get(row[0].toString());
                JSONArray sche_breifs =  (JSONArray)info.get("sched_infos");
                sche_breifs.add(sche_info);
            }else{
                JSONObject infos = new JSONObject();
                JSONArray sched_infos = new JSONArray();
                JSONObject sche_info = new JSONObject();
                sche_info.put("sched_id",row[2]);
                sche_info.put("start_date",row[3]);
                sched_infos.add(sche_info);
                infos.put("cinema_name",row[1]);
                infos.put("cinema_id",row[0]);
                infos.put("location",row[4]);
                infos.put("sched_infos",sched_infos);
                //infos.put("location",)
                infos.put("location",row[4]);
                cinArrs.put((String) row[0].toString(),infos);
            }
        }
        jsonObject.put("cinema_infos",cinArrs);

        return jsonObject;
    }


    public Cinema addJsonCinema( Cinema cinema,String cinemaName, String location, String phone, Integer grade, String cinemaDescription, List<MultipartFile>figureList){

        cinema.setGrade(grade);
        cinema.setPhone(phone);
        cinema.setCinemaName(cinemaName);
        cinema.setCinemaDescription(cinemaDescription);
        cinema.setLocation(location);
        if(figureList!=null){
            if(figureList.size()>0){
                List<Figure>figures=new ArrayList<>();
                for (MultipartFile multipartFile : figureList) {
                    Figure figure=new Figure();
                    String url=uploadSerivce.upImageFire(multipartFile);
                    figure.setImageurl(url);
                    figures.add(figure);
                    figureRepository.save(figure);
                }
                cinema.setFigureList(figures);
            }
        }

        return cinemaRepository.save(cinema);
    }


    public  JSONObject getStaff(Staff staff){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("staff_id",staff.getId());
        jsonObject.put("name",staff.getStaffName());
        jsonObject.put("brief",staff.getStaffBrief());
        jsonObject.put("image",staff.getShowImage());
        JSONArray jsonArray=new JSONArray();
        List<Movie>movieList=staff.getMovieList();

        for (Movie movie : movieList) {
            JSONObject temp=new JSONObject();
            temp.put("movie_id",movie.getId());
            temp.put("name",movie.getName());
            temp.put("image",movie.getShowImage());
            temp.put("score",movie.getScore());
            temp.put("brief",movie.getBrief());
            jsonArray.add(temp);
        }
        jsonObject.put("movieList",jsonArray);
        return  jsonObject;
    }


    public Hall updateHall(Long hadllId,Hall hall,List<MultipartFile>figureList){
        Hall hallOri=hallRepository.findById(hadllId).get();
        hallOri.updateObject(hall);
        List<Figure>figures=new ArrayList<>();

        if(figureList!=null){
            if(figureList!=null){
                for (MultipartFile multipartFile : figureList) {
                    String image=uploadSerivce.upImageFire(multipartFile);
                    Figure figure=new Figure();
                    figure.setImageurl(image);
                    figures.add(figure);
                    figureRepository.save(figure);
                }
            }
        }
        hallOri.setFigureList(figures);
        hallRepository.save(hallOri);
        return hallOri;
    }

    //@Scheduled(fixedRate = 10000)
    public void reportCurrentTime() {
        List<Schedual> norm_scheds = scheudalRepository.findAllByState("normal");
        Long cur = new Date().getTime();
        for (Schedual s:
                norm_scheds) {
            if(Math.abs(s.getStartDate().getTime() - cur) <= 5000 || s.getStartDate().getTime() < cur){
                s.setState("dated");
            }
        }
        scheudalRepository.saveAll(norm_scheds);
    }


}
