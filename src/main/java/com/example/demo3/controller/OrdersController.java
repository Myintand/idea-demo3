package com.example.demo3.controller;


import com.example.demo3.pojo.Orders;
import com.example.demo3.pojo.Store;
import com.example.demo3.pojo.Temp;
import com.example.demo3.pojo.User;
import com.example.demo3.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/OrdersController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class OrdersController
{
    @Resource(name = "ordersServiceImpl")
    private OrdersService ordersService;
    @Resource(name = "storeServiceImpl")
    private StoreService storeService;
    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource(name = "tempServiceImpl")
    private TempService tempService;


    @Resource(name = "userrollServiceImpl")
    private UserrollService userrollService;

    /**
     * 跳转到内部店铺订单(获取店铺订单后跳转)
     */
    @RequestMapping("/tomyacceptorders")
    public String tomyacceptorders(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        Store store = (Store) session.getAttribute("LoginStore");

        //根据店铺用户名获取订单信息
        ArrayList<Orders> myacceptorderslist=ordersService.getmyacceptorders(store.getUsername());
        request.getSession().setAttribute("myacceptorderslist",myacceptorderslist);
        return "/WEB-INF/jsp/sotreAcceptOrders.jsp";
    }


    /**
     * 删除订单okk（买家）
     */
    @RequestMapping("/deleteorders")
    public String deleteorders(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取信息
        String username=request.getParameter("username");
        String storeusername=request.getParameter("storeusername");
        String time=request.getParameter("time");
        Orders orders=ordersService.getoneorders(username,storeusername,time);
        System.out.println("被删除订单的关键信息："+username+"  "+storeusername+"  "+time);

        //返回金额
        if(request.getParameter("state").equals("取消订单"))
        {
            //获取temp
            Temp temp=tempService.get(username,storeusername,time);
            System.out.println("temp:"+temp);
            double price=temp.getMoney();
            //修改用户余额
            HttpSession session= request.getSession();
            User user= (User) session.getAttribute("LoginUser");
            userService.addmymoney(user.getUsername(),price);
            System.out.println("用户：用户余额增加"+price);
            //更新session
            user.setMymoney(user.getMymoney()+price);
            session.setAttribute("LoginUser",user);
            //删除temp
            tempService.delete(temp.getUsername(),temp.getStoreusername(),temp.getTime());
        }

        //返回优惠卷
        if(orders.getTheroll()!=null)
        {
            if(request.getParameter("state").equals("取消订单"))
            {
                System.out.println("被恢复的优惠卷： "+orders.getTheroll());
                userrollService.alterrolltonouse(username,orders.getTheroll());
            }
        }

        //根据订单删除（主键组删除，唯一）
        if(ordersService.delete(username,storeusername,time)>0)
        {
            request.setAttribute("message","订单已删除,金额已原路返回");
            //根据用户名获取商品信息
            ArrayList<Orders> myorderslist=ordersService.getmyorders(username);
            request.getSession().setAttribute("myorderslist",myorderslist);
        }
        return "/WEB-INF/jsp/myOrders.jsp";
    }

    /**
     * 删除订单okk（商家）
     */
    @RequestMapping("/deleteorders1")
    public String deleteorders1(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取信息
        String username=request.getParameter("username");
        String storeusername=request.getParameter("storeusername");
        String time=request.getParameter("time");
        Orders orders=ordersService.getoneorders(username,storeusername,time);
        System.out.println("被删除订单的关键信息："+username+"  "+storeusername+"  "+time);

        //返回金额
        if(request.getParameter("state").equals("取消订单"))
        {
            //获取
            Temp temp=tempService.get(username,storeusername,time);
            System.out.println("temp:"+temp);
            double price=temp.getMoney();
            //修改用户余额(注意这里要获取下单用户而不是当前登陆的用户)
            HttpSession session= request.getSession();
            User user= userService.getoneuserbyusername(username);
            userService.addmymoney(user.getUsername(),price);
            System.out.println("商家：用户余额增加"+price);
            //更新sesion
            if(username.equals(storeusername))//如果商家就是买家
            {
                user.setMymoney(user.getMymoney()+price);
                session.setAttribute("LoginUser",user);
            }
            //删除temp
            tempService.delete(temp.getUsername(),temp.getStoreusername(),temp.getTime());
        }

        //返回优惠卷
        if(orders.getTheroll()!=null)
        {
            if(request.getParameter("state").equals("取消订单"))
            {
                System.out.println("被恢复的优惠卷： "+orders.getTheroll());
                userrollService.alterrolltonouse(username,orders.getTheroll());
//                rollService.modifystateto0(orders.getTheroll());
            }
        }

        //根据订单信息删除（主键组删除，唯一）
        if(ordersService.delete(username,storeusername,time)>0)
        {
            request.setAttribute("message","订单已删除！");
            //根据店铺名获取订单信息
            ArrayList<Orders> myacceptorderslist=ordersService.getmyacceptorders(storeusername);
            request.getSession().setAttribute("myacceptorderslist",myacceptorderslist);
        }
        return "/WEB-INF/jsp/sotreAcceptOrders.jsp";
    }


    /**
     * 评价订单okk（更改订单状态，修改店家评分，修改店家评价人数）
     */
    @RequestMapping("/evaluate")
    public String evaluate(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //首先获取订单状态,看订单是否完成修改
        String orderstate=request.getParameter("state");
        if(orderstate.equals("取消订单"))
        {
            System.out.println("订单未完成，无法评价！");
            request.setAttribute("message","该订单未完成，无法评价！");
            return "/WEB-INF/jsp/myOrders.jsp";
        }

        //获取表单数据
        String username=request.getParameter("username");//下单用户名
        String storeusername=request.getParameter("storeusername");//店铺用户名
        String time=request.getParameter("time");//下单时间
        double score= Integer.parseInt(request.getParameter("score"));//评分
        String state="已完成";
        System.out.println("被评价订单的关键信息："+username+"  "+storeusername+"  "+time);

        //修改
        //根据订单信息修改订单状态（主键组修改，唯一）
        if(ordersService.evaluate(username,storeusername,time,state)>0)
        {
            //根据用户名获取商品信息，更新session区域
            ArrayList<Orders> myorderslist=ordersService.getmyorders(username);
            request.getSession().setAttribute("myorderslist",myorderslist);
            System.out.println("订单状态修改完成，myorderslist更新完成");
        }
        //修改店铺评分，更新session
        if(storeService.addscore(storeusername,score)>0)
        {
            request.setAttribute("message","订单评价完成！");
            //根据店铺用户名获取店铺信息，更新session
            ArrayList<Store> stores=storeService.getStoreList();
            request.getSession().setAttribute("storelist",stores);
            System.out.println("店铺评分添加完成，storelist更新完成");
        }
        return "/WEB-INF/jsp/myOrders.jsp";
    }

    /**
     * 完成订单okk（商家）
     */
    @RequestMapping("/finishorders")
    public String finishorders(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取前端数据
        String username=request.getParameter("username");
        String storeusername=request.getParameter("storeusername");
        String time=request.getParameter("time");
        System.out.println("被完成订单的关键信息："+username+"  "+storeusername+"  "+time);


        //商家获得金额
        //获取temp
        Temp temp=tempService.get(username,storeusername,time);
        System.out.println("temp:"+temp);
        double price=temp.getMoney();
        //修改用户余额
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");
        userService.addmymoney(user.getUsername(),price);
        System.out.println("商家：商家余额增加"+price);
        //更新session
        user.setMymoney(user.getMymoney()+price);
        session.setAttribute("LoginUser",user);
        //删除temp
        tempService.delete(temp.getUsername(),temp.getStoreusername(),temp.getTime());


        //根据订单信息修改修改，唯一）,完成订单就是将其设置为不评价
        if(ordersService.finish(username,storeusername,time)>0)
        {
            //更新session
            ArrayList<Orders> myacceptorderslist=ordersService.getmyacceptorders(storeusername);//根据订单的店铺编号进行查找赋值
            request.getSession().setAttribute("myacceptorderslist",myacceptorderslist);
        }
        //销量+1
        if(storeService.salenumberaddone(storeusername)>0)
        {
            //更新session
            Store store=storeService.getonestore(storeusername);
            request.getSession().setAttribute("LoginStore",store);
        }

        return "/WEB-INF/jsp/sotreAcceptOrders.jsp";
    }
}