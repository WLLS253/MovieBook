package com.movie.Controller;


import com.movie.Entity.Assessor;
import com.movie.Entity.CinemaMng;
import com.movie.Entity.User;
import com.movie.Enums.ExceptionEnums;
import com.movie.Enums.Role;
import com.movie.Plugins.SysLog;
import com.movie.Repository.AssessorRepository;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Serivce.CookieService;
import com.movie.Serivce.TokenService;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.text.normalizer.UTF16;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.spec.ECField;
import java.util.List;


@CrossOrigin
@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;

    @Autowired
    private AssessorRepository  accessorRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private CookieService cookieService;

    @Autowired
    private UploadSerivce uploadSerivce;

    @Autowired
    private AssessorRepository assessorRepository;


    @SysLog(value = "user登录")
    @PostMapping(value = "user/login")
    public Result userLogin(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletResponse response,HttpServletRequest request){
        try{
            List<User>userList=userRepository.findByUsername(username);
            if(userList.size()>0){
                User user=userList.get(0);
                if(user.checkPassword(password)){
                    String token=tokenService.generateToken(user.getId().toString());
                    response.setHeader("isLogin", "true");
                    response.setHeader("token", token);
                    response.setHeader("type", String.valueOf(Role.User));
                    System.out.println(request.getSession().getId());
                    System.out.println(request.getSession().getMaxInactiveInterval());
//                    request.getSession().setMaxInactiveInterval(10);
//                    System.out.println(request.getSession().getCreationTime());
                    cookieService.writeCookie(response,"id",user.getId().toString());
                    cookieService.writeCookie(response,"type", String.valueOf(Role.User));
                    cookieService.writeCookie(response,"name",user.getUsername());
                    cookieService.writeCookie(response,"image",user.getShowimage());
                    return  Util.success(user);
                }else {
                    return  Util.failure(ExceptionEnums.PASSWORD_ERROR);
                }
            }else {
                return  Util.failure(ExceptionEnums.UNFIND_Player_ERROR);
            }

        } catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.PASSWORD_ERROR);
        }
    }


    @PostMapping(value = "cinemaMng/login")
    public Result cinemaMngLogin(@RequestParam("mngName")String name, @RequestParam("password")String password, HttpServletResponse response, HttpServletRequest request){
        try{
            List<CinemaMng>cinemaMngs=cinemaMngRepository.findByMngUsername(name);
            if(cinemaMngs.size()>0){
               CinemaMng cinemaMng=cinemaMngs.get(0);
                if(cinemaMng.getMngPassword().equals(password)){
//                    String token=tokenService.generateToken((cinemaMng.getId()));
                    response.setHeader("isLogin", "true");
                    response.setHeader("token", "mng");
                    response.setHeader("type", String.valueOf(Role.CinemaMng));

                    cookieService.writeCookie(response,"id",cinemaMng.getId().toString());
                    cookieService.writeCookie(response,"type3", String.valueOf(Role.CinemaMng));
                    cookieService.writeCookie(response,"name",cinemaMng.getMngUsername());
                    cookieService.writeCookie(response,"image",cinemaMng.getShowImage());
                    return  Util.success(cinemaMng);
                }else {
                    return  Util.failure(ExceptionEnums.PASSWORD_ERROR);
                }
            }else {
                return  Util.failure(ExceptionEnums.UNFIND_Player_ERROR);
            }

        } catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.PASSWORD_ERROR);
        }
    }

    @SysLog(value = "user注册")
    @PostMapping(value = "user/sign")
    public  Result userSign(@RequestParam(value = "usersex",required = false)String sex, @RequestParam("username")String name, @RequestParam("password")String password,
                            @RequestParam(value = "email",required = false)String email, @RequestParam(value = "phone",required = false)String phone, @RequestParam(value = "image",required = false)MultipartFile file,HttpServletResponse response){

        try {
            User user=new User();
            user.setUsername(name);
            user.setPassword(password);
            user.setUserEmail(email);
            user.setUserSex(sex);
            user.setUserPhone(phone);

            if(file!=null){
                String image=uploadSerivce.upImageFire(file);
                user.setShowimage(image);
            }

            User result=userRepository.save(user);
            String token=tokenService.generateToken(result.getId().toString());
            response.setHeader("isLogin","true");
            response.setHeader("token",token);
            response.setHeader("type", String.valueOf(Role.User));

            cookieService.writeCookie(response,"id",result.getId().toString());
            cookieService.writeCookie(response,"type", String.valueOf(Role.User));
            cookieService.writeCookie(response,"name",result.getUsername());
            cookieService.writeCookie(response,"image",result.getShowimage());

            return  Util.success(userRepository.save(user));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @PostMapping(value = "sys/login")
    public Result sysLogin(@RequestParam("sysName")String name, @RequestParam("password")String password, HttpServletResponse response, HttpServletRequest request){
        try{
            List<Assessor>assessors=accessorRepository.findByAssessorName(name);
            if(assessors.size()>0){
                Assessor assessor=assessors.get(0);
                if(assessor.checkPassword(password)){
                    response.setHeader("isLogin", "true");
                    response.setHeader("token", "sys");
                    response.setHeader("type", String.valueOf(Role.SystemMng));
                    request.getSession().setAttribute("id",assessor.getId());
                    cookieService.writeCookie(response,"id",assessor.getId().toString());
                    cookieService.writeCookie(response,"type2", String.valueOf(Role.SystemMng));
                    cookieService.writeCookie(response,"sysName",assessor.getAssessorName());
                    return  Util.success(assessor);
                }else {
                    return  Util.failure(ExceptionEnums.PASSWORD_ERROR);
                }
            }else {
                return  Util.failure(ExceptionEnums.UNFIND_Player_ERROR);
            }
        } catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.PASSWORD_ERROR);
        }
    }

    //@SysLog(value = "Sys添加")
    @PostMapping(value = "sys/add")
    public  Result SysAdd(Assessor assessor, @RequestParam(value = "image",required = false)MultipartFile file,HttpServletResponse response){
        try {
            //Assessor assessor=new Assessor();
            if(file!=null){
                String image=uploadSerivce.upImageFire(file);
                assessor.setShowImage(image);
            }
//            assessor.setAssessorName(sys_info.assessorName);
//            assessor.setPhone(sys_info.assessorName);
//            assessor.setAssessorPassword(sys_info.assessorPassword);
            Assessor result=assessorRepository.save(assessor);
            response.setHeader("type", String.valueOf(Role.SystemMng));
            cookieService.writeCookie(response,"id",result.getId().toString());
            cookieService.writeCookie(response,"type", String.valueOf(Role.SystemMng));
            return  Util.success(result);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

    @Data
    private static class Sys_Info{

        private  String assessorName;

        private String assessorPassword;

        private  String showImage;

        private String phone;

    }






}
