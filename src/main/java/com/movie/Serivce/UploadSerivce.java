package com.movie.Serivce;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@Service
public class UploadSerivce {

    public  final static Logger logger= LoggerFactory.getLogger(UploadSerivce.class);

    //图片存放根路径
    @Value("${file.rootPath}")
    private String ROOT_PATH;

    //图片存放根目录下的子目录
    @Value("${file.sonPath}")
    private String SON_PATH;

    @Value("${server.port}")
    //获取主机端口
    private String POST;

    @Value("${file.fileServer}")
    public static String uploadServer;


    public String upImageFire(MultipartFile file){
        if (file.isEmpty()) {
            throw new NullPointerException("文件为空");
        }

        // 设置文件上传后的路径
        String filePath = ROOT_PATH + SON_PATH;
        // 获取文件名后缀名
        String suffix = file.getOriginalFilename();
        String prefix = suffix.substring(suffix.lastIndexOf(".")+1);
        //为防止文件重名被覆盖，文件名取名为：当前日期 + 1-1000内随机数
        Random random = new Random();
        Integer randomFileName = random.nextInt(1000);

        Date date=new Date(System.currentTimeMillis() /100);
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyyMMddHHmmss");

        String fileName = simpleDateFormat.format(date) + randomFileName +"." +  prefix;
        //创建文件路径
        File dest = new File(filePath + fileName);
        // 解决中文问题,中文路径，图片显示问题
        // fileName = UUID.randomUUID() + suffixName;
        // 检测是否存在目录
        if (!dest.getParentFile().exists()) {
            //假如文件不存在即重新创建新的文件已防止异常发生
            dest.getParentFile().mkdirs();
        }
        try {
            //transferTo（dest）方法将上传文件写到服务器上指定的文件
            file.transferTo(dest);
            String filePathNew = ROOT_PATH+SON_PATH + fileName;
//            String profilePhoto = saveUploadFile(filePathNew);
//            System.out.println(profilePhoto);
            return filePathNew;
        } catch (Exception e) {
            return dest.toString();
        }


    }

    public boolean deleteimage(String fileName){
        File file = new File(fileName);
        if (!file.exists()) {
            System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return file.delete();
            else
                return false;
        }


    }
//hh
//    private String saveUploadFile(String filePathNew) {
//        //获取本机IP
//        String host = null;
//        try {
//            host = InetAddress.getLocalHost().getHostAddress();
//        } catch (UnknownHostException e) {
//            logger.error("get server host Exception e:", e);
//        }
//
//        return host + ":" + POST + filePathNew;
//    }





}
