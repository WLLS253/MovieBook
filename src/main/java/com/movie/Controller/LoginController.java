package com.movie.Controller;


import com.movie.Entity.CinemaMng;
import com.movie.Enums.ExceptionEnums;
import com.movie.Repository.AccessorRepository;
import com.movie.Repository.CinemaMngRepository;
import com.movie.Repository.UserRepository;
import com.movie.Result.Result;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sun.text.normalizer.UTF16;

import java.security.spec.ECField;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CinemaMngRepository cinemaMngRepository;

    @Autowired
    private AccessorRepository accessorRepository;

//    @PostMapping(value = "user/login")
//    public Result userLogin(@RequestParam("userName")String username,@RequestParam("password")String password){
//        try{
//
//
//        } catch (Exception e){
//            e.printStackTrace();
//            return Util.failure(ExceptionEnums.PASSWORD_ERROR);
//        }
//
//    }


}
