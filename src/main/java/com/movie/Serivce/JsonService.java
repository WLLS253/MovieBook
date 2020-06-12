//package com.example.demo.Serivce;
//
//
//import com.example.demo.Entity.Post;
//import com.example.demo.Entity.Project;
//import com.example.demo.Entity.Volunteer;
//import net.sf.json.JSONArray;
//import net.sf.json.JSONObject;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class JsonService {
//
//    public JSONObject getProjectJson(Project project){
//        System.out.println(project);
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("id",project.getId());
//        jsonObject.put("score",project.getScore());
//        jsonObject.put("endTime",project.getEndTime().toString());
//        jsonObject.put("location",project.getLocation());
//        jsonObject.put("picture",project.getPicture());
//        jsonObject.put("projectDetail",project.getProjectDetial());
//        jsonObject.put("startTime",project.getStartTime().toString());
//        jsonObject.put("team",project.getTeam().getTeamname());
//
//        List<Post>postList=project.getPosts();
//        JSONArray posts=new JSONArray();
//        JSONObject postTest=new JSONObject();
//        for(int i=0;i<postList.size();i++){
//            postTest.put("postname",postList.get(i).getPostname());
//            List<Volunteer>postvoluun=postList.get(i).getVolunteerList();
//            JSONArray postvolun=new JSONArray();
//            for (int t=0;t<postvoluun.size();t++){
//                postvolun.add(t,postvoluun.get(t).getId());
//            }
//           postTest.put("volunteers",postvolun);
//            postTest.put("max",postList.get(i).getMax());
//            postTest.put("postDetail",postList.get(i).getPostDetail());
//            postTest.put("requirement",postList.get(i).getRequirement());
//            postTest.put("id",postList.get(i).getId());
//           posts.add(i,postTest);
//        }
//        jsonObject.element("posts",posts);
//        return jsonObject;
//    }
//    public  JSONObject getPostJson(Post post){
//        System.out.println(post);
//
//
//
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("id",post.getId());
//        jsonObject.put("postname",post.getPostname());
//        jsonObject.put("postDetail",post.getPostDetail());
//        jsonObject.put("requirement",post.getRequirement());
//        jsonObject.put("max",post.getMax());
//
//
//        List<Volunteer>volunteerList=post.getVolunteerList();
//        JSONArray volunteers=new JSONArray();
//        JSONObject volunteerTest=new JSONObject();
//        for(int i=0;i<volunteerList.size();i++){
//            Volunteer volunteer=volunteerList.get(i);
//            JSONArray volundetail=new JSONArray();
////            for (int t=0;t<postvoluun.size();t++){
////                postvolun.add(t,postvoluun.get(t).getId());
////            }
//            volunteerTest.put("id",volunteer.getId());
//            volunteerTest.put("schoolid",volunteer.getSchoolid());
//            volunteerTest.put("name",volunteer.getName());
//            volunteerTest.put("credit",volunteer.getCredit());
//            volunteerTest.put("grade",volunteer.getGrade());
//            volunteerTest.put("description",volunteer.getDescription());
//            volunteerTest.put("username",volunteer.getProjects());
//            volunteers.add(i,volunteerTest);
//        }
//        jsonObject.element("volunteers",volunteers);
//        return  jsonObject;
//    }
//
//    public JSONObject getAppliersJSON(Project project){
//        JSONObject jsonObject=new JSONObject();
//        jsonObject.put("score",project.getScore());
//        List<Post>postList=project.getPosts();
//        JSONArray posts=new JSONArray();
//        JSONObject postTest=new JSONObject();
//        for(int i=0;i<postList.size();i++){
//            postTest.put("postname",postList.get(i).getPostname());
//            postTest.put("id",postList.get(i).getId());
//            List<Volunteer>postvoluun=postList.get(i).getVolunteerList();
//            JSONArray postvolun=new JSONArray();
//            for (int t=0;t<postvoluun.size();t++){
//                Volunteer volunteer = postvoluun.get(t);
//                JSONObject volJson =  new JSONObject();
//                volJson.element("id",volunteer.getId());
//                volJson.element("schoolid",volunteer.getSchoolid());
//                volJson.element("name",volunteer.getName());
//                volJson.element("credit",volunteer.getCredit());
//                volJson.element("qq",volunteer.getQq());
//                postvolun.add(t,volJson);
//            }
//            postTest.put("volunteers",postvolun);
//            postTest.put("max",postList.get(i).getMax());
//            posts.add(i,postTest);
//        }
//        jsonObject.element("posts",posts);
//        return jsonObject;
//
//    }
//
//
//
//
//}
