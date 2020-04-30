package com.movie.Controller;



import com.movie.Enums.ExceptionEnums;
import com.movie.Result.Result;
import com.movie.Serivce.UploadSerivce;
import com.movie.Util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
public class fileController {


    @Autowired
    private UploadSerivce uploadSerivce;


    @PostMapping(value = "/test/file")
    public Result upfile(@RequestParam("file")MultipartFile file){
        try {
            String path= uploadSerivce.upImageFire(file);
            return Util.success(path);
        }catch (Exception e){
            e.printStackTrace();
            return Util.failure(ExceptionEnums.USER_TEL_ERROR);
        }

    }





}
