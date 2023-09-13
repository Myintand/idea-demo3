package com.example.demo3.controller;

import com.example.demo3.pojo.*;
import com.example.demo3.service.RollService;
import com.example.demo3.service.UserrollService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/RollController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class RollController {

    @Resource(name = "rollServiceImpl")
    private RollService rollService;

    @Resource(name = "userrollServiceImpl")
    private UserrollService userrollService;

    /**
     * 商家前往添加优惠卷兑换码
     */
    @RequestMapping("/toaddroll")
    public String toaddroll()
    {
        return "/WEB-INF/jsp/storeAddRoll.jsp";
    }


    /**
     * 用户前往兑换优惠卷
     */
    @RequestMapping("/togetroll")
    public String togetroll(){
        return "/WEB-INF/jsp/getRoll.jsp";
    }


    /**
     * 前往我的优惠卷界面okk
     */
    @RequestMapping("/tomyroll")
    public String tomyroll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user = (User) session.getAttribute("LoginUser");//获取当前登录的用户

        //根据店铺用户名获取优惠卷信息
        ArrayList<Roll> myrolllist=userrollService.getallrollbyusername(user.getUsername());

        // 创建一个存储无效Roll的列表
        ArrayList<Roll> myAllInvalidRollList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间
        LocalDateTime now = LocalDateTime.now();

        // 使用迭代器遍历myrolllist，并在迭代过程中删除不符合条件的元素
        for (Iterator<Roll> iterator = myrolllist.iterator(); iterator.hasNext();) {
            Roll roll = iterator.next();
            // 将Roll的begintime和endtime转换为LocalDateTime
            LocalDateTime beginTime = LocalDateTime.parse(roll.getBegintime(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(roll.getEndtime(), formatter);
            // 如果当前时间不在begintime和endtime之间，则将roll从myuserablerolllist中删除，并添加到myInvalidRollList中
            if (now.isBefore(beginTime) || now.isAfter(endTime)) {
                iterator.remove();
                myAllInvalidRollList.add(roll);
            }
        }

        session.setAttribute("myrolllist",myrolllist);
        session.setAttribute("myallinvalidrolllist",myAllInvalidRollList);
        return "/WEB-INF/jsp/myRoll.jsp";
    }


    /**
     * 商家添加优惠卷
     */
    @RequestMapping("/addoneroll")
    public String addoneroll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException, ParseException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取表单信息
        String number=request.getParameter("number1");
        System.out.println("number: "+number);

        //判断该优惠卷编号是否存在
        if(rollService.getonerollbynumber(number)!=null)//根据优惠卷编号查找优惠卷
        {
            request.setAttribute("message","兑换码已存在！");
            return "/WEB-INF/jsp/storeAddRoll.jsp";
        }
        //获取剩余表单信息
        String storeusernanme=request.getParameter("rollstoreusernanme");
        String storename=request.getParameter("rollstoreanme");
        double rate= Double.parseDouble(request.getParameter("rate"));
        System.out.println("rate: "+rate);
        double reduce= Double.parseDouble(request.getParameter("reduce"));
        double conditions= Double.parseDouble(request.getParameter("condition"));
        boolean isAllUsable = "on".equals(request.getParameter("isAllUsable"));
        int maxnumber=1;
        boolean isSetBeginAndEndTime = "on".equals(request.getParameter("isSetBeginAndEndTime"));
        String beginTime="";//开始时间
        String endTime="";//结束时间
        if(isSetBeginAndEndTime)//如果设置了开始或者结束时间
        {
            beginTime=request.getParameter("beginTime");
            endTime=request.getParameter("endTime");
            System.out.println("刚获取时");
            System.out.println(beginTime);
            System.out.println(endTime);
            String TempBeginTime="";
            String TempEndTime="";
            //先判断开始时间
            if(!Objects.equals(beginTime, ""))
            {
                System.out.println("开始时间不为空");
                beginTime=beginTime+":00";
                for(int i=0;i<beginTime.length();i++)
                {
                    if(i==10)
                    {
                        TempBeginTime = TempBeginTime+" ";
                    }
                    else
                    {
                        TempBeginTime = TempBeginTime+beginTime.charAt(i);
                    }
                }
                beginTime=TempBeginTime;
            }
            //再判断结束时间
            if(!Objects.equals(endTime, ""))
            {
                System.out.println("结束时间不为空");
                endTime=endTime+":00";
                for(int i=0;i<endTime.length();i++)
                {
                    if(i==10)
                    {
                        TempEndTime = TempEndTime+" ";
                    }
                    else
                    {
                        TempEndTime = TempEndTime+endTime.charAt(i);
                    }
                }
                endTime=TempEndTime;
            }
            System.out.println("修改后,进行时间比较前");
            System.out.println(beginTime);
            System.out.println(endTime);

            //生成时间类型数据，以便校验合法性
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date begin=new Date();
            Date end =new Date();
            if(!beginTime.equals(""))
            {
                begin = format.parse(beginTime);
            }
            if(!endTime.equals(""))
            {
                end = format.parse(endTime);
            }

            //当两者都不为空时，才要校验时间合法性
            if(!beginTime.equals("") && !endTime.equals(""))
            {
                if(begin.after(end))
                {
                    request.setAttribute("message","开始时间需在结束时间之前！");
                    return "/WEB-INF/jsp/storeAddRoll.jsp";
                }
            }
            //最后将字符串为空的数据赋值为空值，方便插入数据库
            if(Objects.equals(beginTime, ""))
            {
                beginTime=null;
            }
            if(Objects.equals(endTime, ""))
            {
                endTime=null;
            }
        }
        if(isAllUsable)
        {
            maxnumber = Integer.parseInt(request.getParameter("sendNumber"));
        }

        //防止金额出错
        if(reduce>=conditions)//如果满减金额超过了，那么就全免
        {
            reduce=conditions;
        }

        //编辑优惠卷简介
        String introduce="";
        double epsilon = 1e-6; // 设置一个误差范围，根据实际情况选择合适的值
        if (Math.abs(rate - 1.0) < epsilon)
        {
            System.out.println("该数字等于1");//是满减
            introduce="满"+(int)conditions+"减"+(int)reduce;
        }
        else
        {
            System.out.println("该数字不等于1");
            int t=(int)(rate*100);
            System.out.println("t: "+t);
            introduce="折";
            while(t!=0)
            {
                int m=t%10;
                if(m==1)
                {
                    introduce="一"+introduce;
                }
                else if (m==2)
                {
                    introduce="二"+introduce;
                }
                else if (m==3)
                {
                    introduce="三"+introduce;
                }
                else if (m==4)
                {
                    introduce="四"+introduce;
                }
                else if (m==5)
                {
                    introduce="五"+introduce;
                }
                else if (m==6)
                {
                    introduce="六"+introduce;
                }
                else if (m==7)
                {
                    introduce="七"+introduce;
                }
                else if (m==8)
                {
                    introduce="八"+introduce;
                }
                else if (m==9)
                {
                    introduce="九"+introduce;
                }
                t=t/10;
            }
        }
        System.out.println("introduce: "+introduce);//输出描述

        //构建优惠卷对象并赋值
        Roll roll = new Roll();
        if(isAllUsable)
        {
            roll.setNumber(number);//兑换码
            roll.setStoreusername(storeusernanme);//店铺id
            roll.setStorename(storename);//店铺名
            roll.setRate(rate);//比率
            roll.setReduce(reduce);//减少金额
            roll.setConditions(conditions);//使用条件
            roll.setIntroduce(introduce);//介绍
            roll.setIsalluseable(isAllUsable);//是否可被多人使用
            roll.setBegintime(beginTime);//开始兑换时间
            roll.setEndtime(endTime);//结束兑换时间
            roll.setNownumber(0);//当前兑换次数
            roll.setMaxnumber(maxnumber);//最大兑换次数
            roll.setState(1);//状态
        }
        else
        {
            roll.setNumber(number);//兑换码
            roll.setStoreusername(storeusernanme);//店铺id
            roll.setStorename(storename);//店铺名
            roll.setRate(rate);//比率
            roll.setReduce(reduce);//减少金额
            roll.setConditions(conditions);//使用条件
            roll.setIntroduce(introduce);//介绍
            roll.setIsalluseable(isAllUsable);//是否可被多人使用
            roll.setBegintime(beginTime);//开始兑换时间
            roll.setEndtime(endTime);//结束兑换时间
            roll.setNownumber(0);//当前兑换次数
            roll.setMaxnumber(1);//最大兑换次数
            roll.setState(1);//状态
        }

        System.out.println(roll);

        //添加优惠卷到数据库
        rollService.addoneroll(roll);

        //跳转
        request.setAttribute("message","添加成功！");
        return "/WEB-INF/jsp/storeAddRoll.jsp";
    }


    /**
     * 用户兑换优惠卷
     */
    @RequestMapping("/getroll")
    public String getroll(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取当前兑换的用户
        HttpSession session=request.getSession();
        User user= (User) session.getAttribute("LoginUser");

        //获取表单信息
        String number=request.getParameter("number2");
        System.out.println("用户兑换输入的兑换码number: "+number);

        //判断该优惠卷编号是否存在
        Roll roll=null;
        Userroll userroll=null;
        roll=rollService.getonerollbynumber(number);//兑换码不会重复
        userroll=userrollService.getonerollbyusernameandnumber(user.getUsername(),number);//看看该用户是否使用过该兑换码
        //确保用户有输入
        if(Objects.equals(number, "") || number==null )
        {
            request.setAttribute("message","请输入兑换码！");
            return "/WEB-INF/jsp/getRoll.jsp";
        }
        //判断兑换码是否为空
        if(roll==null)
        {
            request.setAttribute("message","兑换码不存在！");
            return "/WEB-INF/jsp/getRoll.jsp";
        }
        //判断兑换码是否启用
        if(roll.getState()==0)
        {
            request.setAttribute("message","禁用的兑换码！");
            return "/WEB-INF/jsp/getRoll.jsp";
        }
        //判断是否在兑换时间前
        if(roll.getBegintime()!=null)
        {
            //转为时间对象
            String stringDate = roll.getBegintime();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime begintime = LocalDateTime.parse(stringDate, format);
            //获取当前时间
            LocalDateTime now = LocalDateTime.now();
            //比较(如果当前时间在开始时间之前)
            if (begintime.isAfter(now)) {
                request.setAttribute("message","未到兑换时间！");
                return "/WEB-INF/jsp/getRoll.jsp";
            }
        }
        //判断是否在兑换时间后
        if(roll.getEndtime()!=null)
        {
            //转为时间对象
            String stringDate = roll.getEndtime();
            DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime endtime = LocalDateTime.parse(stringDate, format);
            //获取当前时间
            LocalDateTime now = LocalDateTime.now();
            //比较(如果当前时间在结束时间之后)
            if (now.isAfter(endtime)) {
                request.setAttribute("message","已过兑换时间！");
                return "/WEB-INF/jsp/getRoll.jsp";
            }
        }
        //判断该用户是否兑换过
        if(userroll!=null)
        {
            request.setAttribute("message","您已领取了该兑换码的奖励");
            return "/WEB-INF/jsp/getRoll.jsp";
        }
        //判断是否达到最大可兑换次数
        if(roll.getNownumber()>= roll.getMaxnumber())
        {
            request.setAttribute("message","优惠卷被抢光了！");
            return "/WEB-INF/jsp/getRoll.jsp";
        }

        System.out.println("正在兑换的用户username: "+user.getUsername());

        //优惠卷已兑换次数+1
        rollService.nownumberaddone(number);
        Userroll tempuserroll=new Userroll();
        tempuserroll.setNumber(number);
        tempuserroll.setUsername(user.getUsername());
        tempuserroll.setState(1);
        //用户领取优惠卷
        userrollService.addoneuserroll(tempuserroll);

        //跳转
        request.setAttribute("message","兑换成功，请前往我的优惠卷查看！");
        return "/WEB-INF/jsp/getRoll.jsp";

    }


}