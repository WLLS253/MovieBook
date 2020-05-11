package com.movie.Controller;


import com.movie.Entity.Cinema;
import com.movie.Entity.CinemaMng;
import com.movie.Entity.Hall;
import com.movie.Entity.Schedual;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.CinemaRepository;
import com.movie.Result.Result;
import com.movie.Serivce.CinemaMngService;
import com.movie.Serivce.CinemaService;
import com.movie.Util.Util;
import com.sun.org.apache.regexp.internal.RE;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 处理跨域@CrossOrigin现在可以先不用加
 */
//@CrossOrigin
@RestController
@Api("cinema")
public class CinemaController {


    @Autowired
    private CinemaService cinemaService;

    @Autowired
    private CinemaMngService cinemaMngService;
    @Autowired
    private CinemaRepository cinemaRepository;

    @Autowired
    private  CinemaMngRepository cinemaMngRepository;

    @PostMapping(value = "/cinema/add")
    public Result addCinema(Cinema cinema){
        try {

            Cinema cinema1=new Cinema();
            return Util.success(cinemaRepository.save(cinema));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PutMapping(value = "cinema/update")
    public Result updateCinema(Cinema cinema){
        try {
            Long id=cinema.getId();
            Cinema temp=cinemaRepository.findById(id).get();
            temp.setCinemaDescription(cinema.getCinemaDescription());
            temp.setCinemaName(cinema.getCinemaName());
            temp.setLocation(cinema.getLocation());
            temp.setPhone(cinema.getPhone());
            temp.setGrade(cinema.getGrade());
            return  Util.success(cinemaRepository.save(temp));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @DeleteMapping(value = "cinema/delete")
    public  Result deleteCinema(@RequestParam("cinemaId")Long cinemaId){
        try {
            cinemaRepository.deleteById(cinemaId);
            return Util.success(ExceptionEnums.DEL_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }

    }


    @PostMapping(value = "/cinemaMng/add")
    public Result addCinemaMng(CinemaMng cinemaMng){
        try {
            CinemaMng cinemaMng1=new CinemaMng();
            cinemaMng1=cinemaMng;
            return Util.success(cinemaMngRepository.save(cinemaMng));
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "/cinemaHall/add")
    public Result addCinemaHall(@RequestParam("cinemaName")String cinameName, Hall hall){
        try {

            Hall ourHall=cinemaService.addCinemaHall(cinameName,hall);
            if(ourHall!=null){
                return  Util.success(ourHall);
            }else {
                return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
                /**
                 * 待完善
                 */
            }

        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }


    @PostMapping(value = "shedule/add")
    public  Result addSchedule(@RequestParam("movieId")Long movieId,@RequestParam("hallId")Long hallId,
                               @RequestParam("cinemaId")Long cinemaId,@RequestParam("start")String stratTime,@RequestParam("end")String endTime,
                               @RequestParam("price")Double price,@RequestParam("descirbe")String description)
    {

        try {

            Schedual schedual=cinemaService.addSchedule(movieId,hallId,cinemaId,stratTime,endTime,price,description);
            return Util.success(schedual);
        }catch (Exception e){
            e.printStackTrace();
            return  Util.failure(ExceptionEnums.ADD_ERROR);
        }
    }












//    @PostMapping(value = "schedual/add")
//    private







}
