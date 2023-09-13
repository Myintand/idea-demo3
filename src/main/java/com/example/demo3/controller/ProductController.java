package com.example.demo3.controller;

import com.example.demo3.pojo.Product;
import com.example.demo3.pojo.Store;
import com.example.demo3.pojo.User;
import com.example.demo3.service.ProductService;
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

import static com.example.demo3.controller.tool.UploadOss.uploadImage;


@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/ProductController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class ProductController
{
    @Resource(name = "productServiceImpl")
    private ProductService productService;
    @Resource(name = "userServiceImpl")
    private UserService userService;


    /**
     * 跳转到内部添加商品okk
     */
    @RequestMapping("/toaddproduct1")
    public String toaddproduct1() {
        return "/WEB-INF/jsp/addProductMessage.jsp";
    }

    /**
     * 跳转到内部添加商品okk
     */
    @RequestMapping("/add")
    public String add(MultipartFile upfile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取当前店铺
        HttpSession session= request.getSession();
        Store store= (Store) session.getAttribute("LoginStore");
        String username=store.getUsername();

        //获取表单数据
        String productname=request.getParameter("productname");
        int price= Integer.parseInt(request.getParameter("price"));
        String introduce=request.getParameter("introduce");

        //图片
        String picture = upfile.getOriginalFilename();//获取文件名
        //上传到OSS并返回url
        String URL = uploadImage(upfile,username+"/product");
        System.out.println(URL);

        if(picture!=null)
        {
            //赋值添加
            Product product = new Product();
            product.setUsername(username);
            product.setProductname(productname);
            product.setPrice(price);
            product.setPicture(URL);
            product.setIntroduce(introduce);


            System.out.println(product);
            Product temp=null;
            temp=productService.findProductList(username,productname);
            if(temp!=null)
            {
                //跳转
                request.setAttribute("message","商品名已存在！");
                return "/WEB-INF/jsp/addProductMessage.jsp";
            }
            else
            {
                if(productService.addproduct(product)>0)
                {
                    //设置新的session
                    ArrayList<Product> products=productService.getProductList(username);
                    session.setAttribute("myproductlist",products);
                    //跳转
                    request.setAttribute("message","添加商品成功！");
                    return "/WEB-INF/jsp/myProduct.jsp";
                }
                else
                {
                    //跳转
                    request.setAttribute("message","添加商品失败！");
                    return "/WEB-INF/jsp/myProduct.jsp";
                }

            }
        }
        else
        {
            //跳转
            request.setAttribute("message","添加商品失败！");
            return "/WEB-INF/jsp/myProduct.jsp";
        }
    }


    /**
     * 修改商品资料okk
     */
    @RequestMapping("/alterproduct")
    public String alterproduct(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        HttpSession session= request.getSession();

        //先看看新的名称会不会重复
        String productname = request.getParameter("productname");//从前端获取数据用request
        if(productService.findProductList(((User)session.getAttribute("LoginUser")).getUsername(),productname)!=null)
        {
            //跳转
            request.setAttribute("message","商品名称已存在");
            return "/WEB-INF/jsp/myProduct.jsp";
        }

        //从表单获取剩下的值
        int price = Integer.parseInt(request.getParameter("price"));
        String picture = request.getParameter("picture");
        String introduce = request.getParameter("introduce");

        //构造商品对象
        Product product=new Product();
        product.setUsername(((User)session.getAttribute("LoginUser")).getUsername());
        product.setProductname(productname);
        product.setPrice(price);
        product.setPicture(picture);
        product.setIntroduce(introduce);

        System.out.println(product+request.getParameter("lastproductname"));//之前的产品名

        if(productService.modify(product,request.getParameter("lastproductname"))>0)
        {
            //跳转
            request.setAttribute("message","修改成功");
            //重新获取
            //设置新的session
            ArrayList<Product> products=productService.getProductList(((Store)session.getAttribute("LoginStore")).getUsername());
            session.setAttribute("myproductlist",products);
        }
        else
        {
            //跳转
            request.setAttribute("message","修改失败");
        }
        return "/WEB-INF/jsp/myProduct.jsp";

    }



    /**
     * 跳转到内部店铺商品okk
     */
    @RequestMapping("/toaddproduct")
    public String toaddproduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");


        HttpSession session = request.getSession();//创建session对象
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
                    if("wenzi1".equals(co))//如果存在名称为wenzi1的cookie，说明已登录
                    {
                        String value= cookie.getValue();
                        if(value!=null)
                        {
                            User user=null;
                            try
                            {
                                user=userService.getoneuserbyusername(value);//获取数据
                            }
                            catch (SQLException e)
                            {
                                throw new RuntimeException(e);
                            }
                            if(user!=null)//防止删除的用户（else的话就是不修改，还是前往登录）
                            {
                                //赋值到session，表示登录
                                System.out.println("进入我的商品cookie登陆："+user);
                                session.setAttribute("LoginUser",user);

                                ArrayList<Product> products=null;
                                products=productService.getProductList(value);//不能再new
                                if(products!=null)
                                {
                                    System.out.println("进入我的商品cookie登陆："+products);
                                    session.setAttribute("myproductlist",products);
                                }
                                return "/WEB-INF/jsp/myProduct.jsp";
                            }
                            break;
                        }
                    }
                }
            }
            request.setAttribute("message","您还未登陆，请先登陆");
            return "/WEB-INF/jsp/login.jsp";
        }
        else//LoginUser不为空
        {
            User user= (User) session.getAttribute("LoginUser");
            ArrayList<Product> products=productService.getProductList(user.getUsername());
            if(products!=null)
            {
                System.out.println("进入我的商品LoginUser不为空时的查找："+products);
                session.setAttribute("myproductlist",products);
            }
            return "/WEB-INF/jsp/myProduct.jsp";
        }
//        //获取商品
//        HttpSession session= request.getSession();
//        Store store= (Store) session.getAttribute("LoginStore");
//        String username=store.getUsername();
//        System.out.println(username);
//        //根据用户名获取商品信息
//        ArrayList<Product> products=productService.getProductList(username);
//        session.setAttribute("myproductlist",products);
//        return "/WEB-INF/jsp/myProduct.jsp";
    }

    /**
     * 删除商品okk
     */
    @RequestMapping("/deleteproduct")
    public String deleteproduct(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        //获取信息
        String username=request.getParameter("username");
        String productname=request.getParameter("productname");
        System.out.println(username+productname);

        //根据用户名和商品名删除（主键组删除，唯一）
        if(productService.delete(username,productname)>0)
        {
            //根据用户名获取商品信息
            ArrayList<Product> products=productService.getProductList(username);
            request.getSession().setAttribute("myproductlist",products);
        }
        return "/WEB-INF/jsp/myProduct.jsp";
    }



}