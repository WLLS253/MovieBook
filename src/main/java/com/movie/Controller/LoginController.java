package com.movie.Controller;


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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import sun.text.normalizer.UTF16;

import javax.servlet.http.HttpServletResponse;
import java.security.spec.ECField;
import java.util.List;

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


    @SysLog(value = "user登录")
    @PostMapping(value = "user/login")
    public Result userLogin(@RequestParam("username")String username, @RequestParam("password")String password, HttpServletResponse response){
        try{
            List<User>userList=userRepository.findByUsername(username);
            if(userList.size()>0){
                User user=userList.get(0);
                if(user.checkPassword(password)){
                    String token=tokenService.generateToken(user.getId().toString());
                    response.setHeader("isLogin", "true");
                    response.setHeader("token", token);
                    response.setHeader("type", String.valueOf(Role.User));
                    cookieService.writeCookie(response,"id",user.getId().toString());
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
    public Result cinemaMngLogin(@RequestParam("mngName")String name, @RequestParam("password")String password, HttpServletResponse response){
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
            String image=uploadSerivce.upImageFire(file);
            user.setShowimage(image);
            User result=userRepository.save(user);
            String token=tokenService.generateToken(result.getId().toString());
            response.setHeader("isLogin","true");
            response.setHeader("token",token);
            response.setHeader("type", String.valueOf(Role.User));
            cookieService.writeCookie(response,"id",result.getId().toString());
            return  Util.success(userRepository.save(user));
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.UNKNOW_ERROR);
        }
    }

}
