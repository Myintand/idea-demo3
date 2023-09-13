package com.example.demo3.controller;

import com.example.demo3.pojo.Store;
import com.example.demo3.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;

@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/CommonController")//一级路由
public class CommonController {

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
     * 回到上一个页面
     */
    @RequestMapping("/returnjsp")
    public String torecharge(HttpServletRequest request)
    {
        String returnjsp = request.getParameter("returnjsp");
        System.out.println("返回上一个页面："+returnjsp);
        if(returnjsp==null||returnjsp.equals(""))
        {
            HttpSession session = request.getSession();
            ArrayList<Store> stores = storeService.getStoreList();
            session.setAttribute("storelist",stores);
            return "/WEB-INF/jsp/index.jsp";//默认返回首页
        }
        return "/WEB-INF/jsp/"+request.getParameter("returnjsp");
    }
}
