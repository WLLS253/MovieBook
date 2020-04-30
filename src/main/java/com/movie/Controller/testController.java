package com.movie.Controller;


import com.movie.Entity.Assessor;
import com.movie.Repository.AssessorRepository;
import com.movie.Result.Result;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin
@RestController
public class testController {

    @Autowired
    private AssessorRepository assessorRepository;


    @Autowired
    private UploadSerivce uploadSerivce;


    @PostMapping(value = "/test/add")
    public Result add(@RequestParam("name") String name,@RequestParam("pass")String pass){

        Assessor assessor=new Assessor();
        assessor.setAssessorPassword(pass);
        assessor.setAssessorName(name);

        return Util.success(assessorRepository.save(assessor));
    }


    @PostMapping(value = "/test/find")
    public Result find(@RequestParam("name") String name){

        List<Assessor>assessorList=assessorRepository.findByAssessorName(name);
        System.out.println(name);
        System.out.println(assessorList);
        return Util.success(111);
    }
}
