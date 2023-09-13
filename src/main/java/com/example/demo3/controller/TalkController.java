package com.example.demo3.controller;

import com.example.demo3.pojo.*;
import com.example.demo3.service.StoreService;
import com.example.demo3.service.TalkService;
import com.example.demo3.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.demo3.controller.tool.UploadOss.uploadImage;


@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/TalkController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class TalkController {

    @Resource(name = "talkServiceImpl")
    private TalkService talkService;

    @Resource(name = "userServiceImpl")
    private UserService userService;

    @Resource(name = "storeServiceImpl")
    private StoreService storeService;


    /**
     * 查找我和商家的所有对话,并前往商家界面
     */
    @RequestMapping("/totalk")
    public String totalk(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();

        //判断是否登录
        //判断用户是否登录
        User user=null;
        if(session.getAttribute("LoginUser")==null)//如果是未登录状态，就看cookie有没有存活的
        {
            //保持登录状态，60分钟，获取所有cookie，看是否还在有效期内
            Cookie[] cookies = request.getCookies();
            if(cookies != null && cookies.length>0)//cookie非空的话
            {
                for(Cookie cookie : cookies)
                {
                    //获取cookie名称
                    String co=cookie.getName();
                    System.out.println("cookie: "+co);
                    if("wenzi1".equals(co))//如果存在名称为wenzi1的cookie，说明已登录
                    {
                        String value= cookie.getValue();
                        if(value!=null)
                        {
                            try
                            {
                                user=userService.getoneuserbyusername(value);//不能再new
                            }
                            catch (SQLException e)
                            {
                                throw new RuntimeException(e);
                            }
                            if(user!=null)//防止删除的用户（else的话就是不修改，还是前往登录）
                            {
                                //登录成功
                                session.setAttribute("LoginUser",user);

                                Store store = (Store) session.getAttribute("nowstore");//获取当前店铺

                                //判断商家和店铺是否是同一个
                                if(store.getUsername().equals(user.getUsername()))
                                {
                                    request.setAttribute("talkmessage","请不要与您的店铺进行联系！");
                                    return "/WEB-INF/jsp/storeIndex.jsp";
                                }


                                //根据店铺用户名,顾客用户名，店铺名称获取对话
                                ArrayList<Talk> myalltalklist=talkService.getalltalkList(user.getUsername(),store.getUsername(),store.getStorename());
                                session.setAttribute("myalltalklist",myalltalklist);


                                return "/WEB-INF/jsp/talk.jsp";

                            }
                            break;
                        }
                    }
                }

            }
            request.setAttribute("message","您还未登陆，请先登陆再下单");
            return "/WEB-INF/jsp/login.jsp";

        }
        else//LoginUser不为空,说明已经登陆
        {
            //已登录
            user= (User) session.getAttribute("LoginUser");
            Store store = (Store) session.getAttribute("nowstore");//获取当前店铺

            //判断商家和店铺是否是同一个
            if(store.getUsername().equals(user.getUsername()))
            {
                request.setAttribute("talkmessage","请不要与您的店铺进行联系！");
                return "/WEB-INF/jsp/storeIndex.jsp";
            }


            //根据店铺用户名获取对话
            ArrayList<Talk> myalltalklist=talkService.getalltalkList(user.getUsername(),store.getUsername(),store.getStorename());
            session.setAttribute("myalltalklist",myalltalklist);


            return "/WEB-INF/jsp/talk.jsp";
        }
    }

    /**
     * 添加对话(用户)
     */
    @RequestMapping("/addonetalk")
    public String addonetalk(MultipartFile uploadfile,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");//当前登录用户，也就是发消息的用户
        Store store= (Store) session.getAttribute("nowstore");//接收消息的商家对象
        //赋值变量
        String username = user.getUsername();//当前用户的用户名
        String storeusername = store.getUsername();//接收人为商家用户名
        String storename = store.getStorename();//店铺名称为商家店铺名称


        String talking = request.getParameter("sendmessage");//谈话内容
        System.out.println("输出："+username+" "+storeusername+" "+storename+" "+talking);
        int flag=0;
        String headpicture = uploadfile.getOriginalFilename();//获取文件名
        System.out.println("发送图片的文件名： "+headpicture);
        //上传到OSS
        String URL=null;
        if( headpicture!=null & !Objects.equals(headpicture, ""))//图片返回值非空(包含null和“”两种情况)
        {
            flag=1;
            //上传到OSS并返回url
            //上传到OSS
            URL = uploadImage(uploadfile,username+"/talk");
            System.out.println(URL);
        }

        //封装类
        Talk talk = new Talk();
        talk.setUsername(username);
        talk.setStoreusername(storeusername);
        talk.setStorename(storename);
        talk.setFlag(flag);
        talk.setTalking(talking);
        talk.setPicture(URL);
        System.out.println(talk);


        //存入数据库
        talkService.addtalk(talk);
        request.setAttribute("message", "发送成功");//将提示信息保存在session中,可以通过${}获取数据
        //根据店铺用户名获取对话，更新session
        ArrayList<Talk> myalltalklist=talkService.getalltalkList(user.getUsername(),store.getUsername(),store.getStorename());
        session.setAttribute("myalltalklist",myalltalklist);

        return "/WEB-INF/jsp/talk.jsp";
    }


    /**
     * 添加对话(商家)
     */
    @RequestMapping("/addonetalk1")
    public String addonetalk1(MultipartFile uploadfile,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");//商家对象，是发送人
        Store store= storeService.getonestore(user.getUsername());//商家店铺对象
        String buyer= (String) session.getAttribute("talkusername");//用户编号
        //获取用户
        User user1=userService.getoneuserbyusername(buyer);

        //赋值变量
        String username = store.getUsername();//发消息人的编号
        String storeusername = buyer;//接收人的编号
        String storename = store.getStorename();//商家名称
        String talking = request.getParameter("sendmessage");//谈话内容
        System.out.println("输出："+username+" "+storeusername+" "+storename+" "+talking);
        int flag=0;
        String headpicture = uploadfile.getOriginalFilename();//获取文件名
        System.out.println("发送图片的文件名： "+headpicture);
        //上传到OSS
        String URL=null;
        if( headpicture!=null & !Objects.equals(headpicture, ""))//图片返回值非空(包含null和“”两种情况)
        {
            flag=1;
            //上传到OSS并返回url
            //上传到OSS
            URL = uploadImage(uploadfile,username+"/storeTalk");
            System.out.println(URL);
        }

        //封装类
        Talk talk = new Talk();
        talk.setUsername(username);
        talk.setStoreusername(storeusername);
        talk.setStorename(storename);
        talk.setFlag(flag);
        talk.setTalking(talking);
        talk.setPicture(URL);
        System.out.println(talk);


        //存入数据库
        talkService.addtalk(talk);
        request.setAttribute("message", "发送成功");//将提示信息保存在session中,可以通过${}获取数据
        //根据店铺用户名获取对话，更新session
        ArrayList<Talk> myalltalklist1=talkService.getalltalkList(store.getUsername(),user1.getUsername(),store.getStorename());
        session.setAttribute("myalltalklist1",myalltalklist1);
        session.setAttribute("talkpicture",user1.getHeadpicture());//头像图片路径
        session.setAttribute("talkusername",user1.getUsername());//用户编号

        System.out.println("myalltalklist1，talkpicture，talkusername："+myalltalklist1+" "+user1.getHeadpicture()+" "+user1.getUsername());
        return "/WEB-INF/jsp/storeTalk.jsp";
    }



    /**
     * 添加对话(商家)
     */
    @RequestMapping("/selectonetalk")
    public String selectonetalk(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");//获取当前用户账号，也就是当前商家账号

        //查找收件人为user.getUsername()的所有发送人,并设置在session里面
        ArrayList<User> talkusers = talkService.findtalk(user.getUsername());
        session.setAttribute("talkusers",talkusers);

        return "/WEB-INF/jsp/storeSelectTalk.jsp";
    }


    /**
     * 点击回复按钮之后(商家)
     */
    @RequestMapping("/totalkbuyer")
    public String totalkbuyer(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");//获取当前用户账号，也就是当前商家账号
        Store store= storeService.getonestore(user.getUsername());//商家店铺对象
        String talkusername=request.getParameter("talkusername");//对话顾客编号（表单提交）
        String talkpicture=request.getParameter("talkpicture");//对话顾客头像图片（表单提交）

        //根据店铺用户名获取对话,更新session
        ArrayList<Talk> myalltalklist1=talkService.getalltalkList(store.getUsername(),talkusername,store.getStorename());
        session.setAttribute("myalltalklist1",myalltalklist1);
        session.setAttribute("talkpicture",talkpicture);//头像图片路径
        session.setAttribute("talkusername",talkusername);//用户编号

        return "/WEB-INF/jsp/storeTalk.jsp";

    }

}
