package com.example.demo3.controller;

import com.example.demo3.pojo.User;
import com.example.demo3.service.MenuServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/MenuController")//一级路由
public class MenuController {

    @Resource(name = "menuServiceImpl")
    private MenuServiceImpl menuService;

    @RequestMapping("/tomenu")
    public String tomenu(Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        HttpSession session=request.getSession();
        if(session.getAttribute("LoginUser")==null)
        {
            request.setAttribute("message", "您当前未登录，请先登录");
            return "forward:/WEB-INF/jsp/menu.jsp";
        }
        else
        {
            User user = (User) request.getSession().getAttribute("LoginUser");
            String username=user.getUsername();
            request.setAttribute("message", "尊敬的"+username+":");
            //获取数据
            List<String> menuList;
            menuList=menuService.getcollect(username);
            model.addAttribute("menuList",menuList);
            return "forward:/WEB-INF/jsp/menu.jsp";
        }
    }

    @RequestMapping("/addcollect")
    public String addcollect(Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        String recipeName=request.getParameter("recipeName");
        User user = (User) request.getSession().getAttribute("LoginUser");
        if(session.getAttribute("LoginUser")==null)
        {
            request.setAttribute("message2", "请先登录");
            request.setAttribute("message", "您当前未登录，请先登录");
            return "forward:/WEB-INF/jsp/menu.jsp";
        }
        else {
            String username = user.getUsername();
            System.out.println(username);
            System.out.println(recipeName);
            //添加
            menuService.addcollect(username, recipeName);
            //更新
            request.setAttribute("message", "尊敬的"+username+":");
            List<String> menuList;
            menuList=menuService.getcollect(username);
            model.addAttribute("menuList",menuList);
            return "forward:/WEB-INF/jsp/menu.jsp";
        }
    }

    //删除
    @RequestMapping("/delete")
    public String delete(Model model, HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        HttpSession session=request.getSession();
        String recipeName=request.getParameter("recipeName");
        User user = (User) request.getSession().getAttribute("LoginUser");
        String username = user.getUsername();
        System.out.println(username);
        System.out.println(recipeName);
        //删除
        menuService.delete(username,recipeName);
        //更新
        request.setAttribute("message", "尊敬的"+username+":");
        List<String> menuList;
        menuList=menuService.getcollect(username);
        model.addAttribute("menuList",menuList);
        return "forward:/WEB-INF/jsp/menu.jsp";
    }

}
