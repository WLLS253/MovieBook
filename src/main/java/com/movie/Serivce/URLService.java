package com.movie.Serivce;


import com.alibaba.fastjson.JSONArray;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mysql.cj.xdevapi.TableImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.resource.HttpResource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.midi.Soundbank;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.sql.ParameterMetaData;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class URLService {





//    public static ResultVO sendPostRequest(String url, MultiValueMap<String, String> params){
//
//        RestTemplate client = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        HttpMethod method = HttpMethod.POST;
//        // 以表单的方式提交
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        //将请求头部和参数合成一个请求
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        System.out.println(requestEntity);
//        //执行HTTP请求，将返回的结构使用ResultVO类格式化
//        System.out.println("test");
//        ResponseEntity<ResultVO> response = client.exchange(url,method,requestEntity, ResultVO.class);
//        System.out.println(response);
//        System.out.println(response.getBody());
//        ResultVO resultVO=response.getBody();
//        ResultVO a=new ResultVO();
//        return resultVO;
//    }

//    public Volunteer getUserOpenid(String appid, String sercet, String js_code, String grant_type, Volunteer volunteer) {
//
//        String url="https://api.q.qq.com/sns/jscode2session?"+"appid="+appid+"&secret="+sercet+"&js_code="+js_code+"&grant_type=authorization_code";
//
//        System.out.println(url);
//        //发送Post数据并返回数据
//
//        RestTemplate client = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        HttpMethod method = HttpMethod.GET;
//        //headers.setContentType(MediaType.APPLICATION_JSON);
//        //System.out.println(client.getForEntity(url,ResultVO.class));
//        ResponseEntity<String> response = client.getForEntity(url, String.class);
////        ResultVO resylt=(ResultVO) response.getBody();
////      System.out.println(resylt);
//        ObjectMapper objectMapper=new ObjectMapper();
//        String result=response.getBody();
//        ResultVO resultVO=new ResultVO();
//        try {
//            resultVO=objectMapper.readValue(result,ResultVO.class);
//        }catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        volunteer.setOpenid(resultVO.getOpenid());
//
//        System.out.println(resultVO);
//        volunteer.setSession_key(resultVO.getSession_key());
//
//        return volunteer;
//    }


    public JSONObject getHttp(){

        String url="http://59.110.174.204:7280/v1.0/api/metro/station/find";
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders headers=new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        restTemplate.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        headers.set("userId","1");
        HttpMethod method=HttpMethod.POST;
        MultiValueMap<String,String>params=new LinkedMultiValueMap<String, String>();
        HttpEntity<MultiValueMap<String, String>> requestEntity =new HttpEntity<MultiValueMap<String, String>>(params,headers);
        params.add("city_id","3202");
        params.add("user_id","1");
        ResponseEntity<String>responseEntity=restTemplate.exchange(url,method,requestEntity,String.class);

        System.out.println(responseEntity.getBody());
        JSONObject jsonObject=JSONObject.parseObject(responseEntity.getBody());
        System.out.println(jsonObject);
        System.out.println(jsonObject.get("errcode"));
        return  jsonObject;
    }

    public  JSONObject test(){
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("hhsiad","ashd");
        jsonObject.put("sad","asd");
        JSONArray jsonArray=new JSONArray();
        jsonArray.add("123");
        jsonArray.add("456");
        jsonObject.put("array",jsonArray);

        return jsonObject;


    }


//    public  boolean sessionfilter(HttpServletRequest request, HttpServletResponse resource){
//        String openid=request.getHeader("openid");
//        String session_key=request.getHeader("session_key");
//        List<Volunteer>volunteers=volunteerRepository.findByOpenid(openid);
//        if(volunteers.size()==0)return false;
//        else {
//            if(volunteers.get(0).getSession_key().equals(session_key))return true;
//            else return false;
//        }
//    }

//    public  boolean lofinBeijing(String uname,String upass){
//        String url="http://0.0.0.0:8081/login";
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("uname", uname);
//        params.add("upass", upass);
//        //发送Post数据并返回数据
//        RestTemplate client = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        HttpMethod method = HttpMethod.POST;
//        // 以表单的方式提交
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        //将请求头部和参数合成一个请求
//        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);
//        ResponseEntity<String> response = client.exchange(url, method,requestEntity,String.class);
//        System.out.println(response);
//        JSONObject jsonObject=new JSONObject(response.getBody());
//        String token=jsonObject.get("token").toString();
//        Tokeners tokeners=new Tokeners();
//        tokeners.setToken(token);
//        tokeners.setUuid(jsonObject.get("id").toString());
//        tokenersRepository.save(tokeners);
//        return  true;
//    }

//    public void getTimeCodeList(Long team_id, String opp_id, String job_id, String token, Post post){
//
//        String url="/get_code_list?"+"token="+token+"opp_id="+opp_id+"job_id="+job_id;
//
//        RestTemplate restTemplate=new RestTemplate();
//        HttpHeaders httpHeaders=new HttpHeaders();
//        HttpMethod httpMethod=HttpMethod.POST;
//
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
//        System.out.println(response.getBody());
//        JSONArray jsonArray=JSON.parseArray(response.getBody());
//        List<Volunteer>volunteers=post.getVolunteerList();
//
//        List<TimeCode>timeCodeList=new ArrayList<>();
//
//        int size=jsonArray.size();
//        for (int i=0;i<=size;i++){
//            Volunteer volunteer=volunteers.get(i);
//            JSONObject jsonObject=jsonArray.getJSONObject(i);
//            String code=jsonObject.getString("code");
//            String time=jsonObject.getString("time");
//            String date=jsonObject.getString("date");
//            Object use=jsonObject.getJSONObject("use");
//            Object user=((JSONObject) use).getJSONObject("user");
//            String uuid=((JSONObject) user).getString("uuid");
//            String name=((JSONObject) user).getString("name");
//            String use_date=((JSONObject) use).getString("date");
//            TimeCode timeCode=new TimeCode();
//            timeCode.setCode(code);
//            timeCode.setTime(time);
//            timeCode.setDetial(post.getPostname());
//            //timeCode.setPost_id(post.getId().toString());
//            timeCode.setShcoolid(volunteer.getSchoolid());
//            timeCodeRepository.save(timeCode);
//            timeCodeList.add(timeCode);
//        }
//
//    }


}
