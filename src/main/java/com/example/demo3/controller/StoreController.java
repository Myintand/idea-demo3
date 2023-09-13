package com.example.demo3.controller;

import com.example.demo3.pojo.Product;
import com.example.demo3.pojo.Store;
import com.example.demo3.pojo.User;
import com.example.demo3.service.ProductService;
import com.example.demo3.service.StoreService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.*;
import java.io.*;

import java.util.ArrayList;

import static com.example.demo3.controller.tool.UploadOss.uploadImage;

@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/StoreController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class StoreController
{
    @Resource(name = "storeServiceImpl")
    private StoreService storeService;
    @Resource(name = "productServiceImpl")
    private ProductService productService;


    /**
     * 跳转到内部首页okk
     */
    @RequestMapping("/toindex1")
    public String toindex1()
    {
        return "/WEB-INF/jsp/index.jsp";
    }
    /**
     * 跳转到内部编辑店铺资料okk
     */
    @RequestMapping("/toalterstoremessage")
    public String toalterstoremessage()
    {
        return "/WEB-INF/jsp/alterStoreMessage.jsp";
    }

    /**
     * 跳转到商品列表okk
     */
    @RequestMapping("/tostoreindex")
    public String tostoreindex(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String username=request.getParameter("username");
        System.out.println("进入店铺的storeusname：" + username);
        //根据用户名获取商品信息
        ArrayList<Product> products=productService.getProductList(username);
        //根据用户名获得点击的商店信息
        Store store=storeService.getonestore(username);
        HttpSession session= request.getSession();
        session.setAttribute("productlist",products);
        session.setAttribute("nowstore",store);
        return "/WEB-INF/jsp/storeIndex.jsp";
    }

    /**
     * 跳转到内部商家注册界面okk
     */
    @RequestMapping("/toadd")
    public String toadd()
    {
        return "/WEB-INF/jsp/becomeStore.jsp";
    }

    /**
     * 跳转到地图选点界面okk
     * 必须传入回调jsp，如index.jsp
     */
    @RequestMapping("/toselectmappoint")
    public String toselectmappoint(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        String returnjsp = request.getParameter("returnjsp");//传输回调地址
        request.setAttribute("returnjsp",returnjsp);
        return "/WEB-INF/jsp/selectMapPoint.jsp";
    }

    /**
     * 地图选点，经纬度字符串保存变量为${sl}
     * 必须传入回调jsp，默认回调index.jsp
     */
    @RequestMapping("/selectmappoint")
    public String selectmappoint(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取选取的位置数据
        String storeselectlocation = request.getParameter("storeselectlocation");//选取的位置
        String returnjsp = request.getParameter("returnjsp");
        System.out.println("选点位置： "+storeselectlocation+"回调地址： "+returnjsp);
        //保存最新选取的位置
        if(storeselectlocation ==null || storeselectlocation.equals(""))
        {
            request.setAttribute("message","请选取位置");
            request.setAttribute("returnjsp",returnjsp);
            return "/WEB-INF/jsp/selectMapPoint.jsp";
        }
        request.setAttribute("sl",storeselectlocation);
        request.setAttribute("success","选取成功");

        //看用户是否有传入回调jsp，没传入就返回首页
        if(returnjsp==null||returnjsp.equals(""))
        {
            HttpSession session = request.getSession();
            ArrayList<Store> stores = storeService.getStoreList();
            session.setAttribute("storelist",stores);
            return "/WEB-INF/jsp/index.jsp";//默认返回首页
        }
        return "/WEB-INF/jsp/"+returnjsp;
    }

    /**
     * 跳转到地图点查看界面okk
     * 必须传入位置信息
     * 必须传入回调jsp，默认回调index.jsp
     */
    @RequestMapping("/toviewmappoint")
    public String toviewmappoint(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取传来的要显示的位置信息
        String location = request.getParameter("location");
        String name = request.getParameter("name");
        //看用户是否有传入回调jsp，没传入就返回首页
        String returnjsp = request.getParameter("returnjsp");
        //验证
        if(location==null||location.equals(""))
        {
            request.setAttribute("message","无效地址");
            //返回
            if(returnjsp==null||returnjsp.equals(""))
            {
                HttpSession session = request.getSession();
                ArrayList<Store> stores = storeService.getStoreList();
                session.setAttribute("storelist",stores);
                return "/WEB-INF/jsp/index.jsp";//默认返回首页
            }
            else
            {
                return "/WEB-INF/jsp/"+returnjsp;
            }

        }

        //传递参数
        HttpSession session= request.getSession();
        session.setAttribute("nowviewlocation",location);
        session.setAttribute("theviewlocationmname",name);
        session.setAttribute("thereturnjsp",returnjsp);
        return "/WEB-INF/jsp/viewMapPoint.jsp";
    }

    /**
     * 查看规划的路线okk
     * 必须传入起点和终点的位置信息
     * 必须传入回调jsp，默认回调index.jsp
     */
    @RequestMapping("/viewmappath")
    public String viewmappath(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取传来的参数
        String beginmaplocation = request.getParameter("beginmaplocation");//起点
        String endmaplocation = request.getParameter("endmaplocation");//终点
        String returnjsp = request.getParameter("returnjsp");//回调地址
        //验证
        if(beginmaplocation==null||beginmaplocation.equals("")||endmaplocation==null||endmaplocation.equals(""))
        {
            request.setAttribute("message","请传入完整的地址位置");
            //返回
            if(returnjsp==null||returnjsp.equals(""))
            {
                HttpSession session = request.getSession();
                ArrayList<Store> stores = storeService.getStoreList();
                session.setAttribute("storelist",stores);
                return "/WEB-INF/jsp/index.jsp";//默认返回首页
            }
            else
            {
                return "/WEB-INF/jsp/"+returnjsp;
            }

        }
        //传参
        request.setAttribute("beginmaplocation",beginmaplocation);
        request.setAttribute("endmaplocation",endmaplocation);
        request.setAttribute("returnjsp",returnjsp);
        System.out.println("查看路径传入的参数：起点："+beginmaplocation+" 终点："+endmaplocation+" 回调："+returnjsp);
        return "/WEB-INF/jsp/viewMapPath.jsp";
    }

    /**
     * 注册商家okk,注册的话会将LoginStore设置在session
     */
    @RequestMapping("/addstore")
    public String addstore(MultipartFile uploadfile,HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //赋值变量
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");
        String username = user.getUsername();//账号
        String storename = request.getParameter("storename");//店铺名
        int beginning = Integer.parseInt(request.getParameter("beginning"));//起送
        String introduce = request.getParameter("introduce");//简介
        String storelocation = request.getParameter("storelocation");//店铺经纬度地址

        //头像
        String headpicture = uploadfile.getOriginalFilename();//获取文件名

        //上传到OSS并返回url
        String URL = uploadImage(uploadfile,username+"/storeHeadPicture");
        System.out.println(URL);

        if(headpicture!=null)//图片返回值非空
        {
            //封装类
            Store store =new Store();
            store.setUsername(username);
            store.setStorename(storename);
            store.setBeginning(beginning);
            store.setIntroduce(introduce);
            store.setPicture(URL);
            store.setStorelocation(storelocation);
            System.out.println("registerstore:"+store);
            //存入数据库
            if(storeService.add(store)>0)
            {
                request.setAttribute("message", "注册成功");//将提示信息保存在session中,可以通过${}获取数据
                session.setAttribute("LoginStore",store);
                return "/WEB-INF/jsp/storePersonal.jsp";
            }
            else
            {
                request.setAttribute("message", "注册失败");//将提示信息保存在session中,可以通过${}获取数据
                return "/WEB-INF/jsp/personal.jsp";
            }

        }
        else//密码一致，图片名为空
        {
            request.setAttribute("error", "请上传图片");//将提示信息保存在session中,可以通过${}获取数据
            return "/WEB-INF/jsp/register.jsp";
        }
    }


    /**
     * 显示所有商家okk
     */
    @RequestMapping("/showstores")
    public String showusers(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        ArrayList<Store> stores=storeService.getStoreList();
        HttpSession session= request.getSession();
        session.setAttribute("storelist",stores);
        return "/WEB-INF/jsp/index.jsp";
    }

    /**
     * 修改商户头像okk(云okk)
     */
    @RequestMapping("/alterhead")
    private String alterhead(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //保存头像文件
        String headpicture=upfile.getOriginalFilename();

        //获取用户
        HttpSession session= request.getSession();
        Store temp= (Store) session.getAttribute("LoginStore");
        //上传到OSS
        String URL = uploadImage(upfile,temp.getUsername()+"/storeHeadPicture");
        System.out.println(URL);

        //数据库更新
        Store store = storeService.getonestore(temp.getUsername());//根据用户名找到该商家
        store.setPicture(URL);//更改该商家头像文件路径
        storeService.modify(store);//保存到数据库
        session.setAttribute("LoginStore",store);//更新session
        return "/WEB-INF/jsp/storePersonal.jsp";
    }

    /**
     * 查找商家okk
     */
    @RequestMapping("/findStorelist")
    public String showusers(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        System.out.println(request.getParameter("keyword"));

        ArrayList<Store> stores=storeService.findStoreList(request.getParameter("keyword"));

        HttpSession session= request.getSession();
        session.setAttribute("storelist",stores);
        return "/WEB-INF/jsp/index.jsp";
    }


    /**
     * 修改店铺信息okk
     */
    @RequestMapping("/alterstoremessage")
    private String alterstoremessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //判断旧密码
        String pass=request.getParameter("ps");//输入的旧密码值
        HttpSession session = request.getSession();
        User user=(User)session.getAttribute("LoginUser");
        String truepass= user.getPassword();

        //跳转
        if(!pass.equals(truepass))//密码不正确
        {
            request.setAttribute("message","密码错误");//只会在下一个页面生效
            return "/WEB-INF/jsp/alterStoreMessage.jsp";
        }
        else
        {
//从表单获取剩下的值
            String storename = request.getParameter("storename");//从前端获取数据用request
            int beginning = Integer.parseInt(request.getParameter("beginning"));
            String introduce = request.getParameter("introduce");

            //修改数据库
            Store store= (Store) session.getAttribute("LoginStore");
            store.setStorename(storename);
            store.setBeginning(beginning);
            store.setIntroduce(introduce);
            if(storeService.modify(store)>0)
            {
                session.setAttribute("LoginStore",store);//更新session
                request.setAttribute("message","更新信息成功");
                return "/WEB-INF/jsp/storePersonal.jsp";
            }
            else
            {
                request.setAttribute("message","更新信息失败");
                return "/WEB-INF/jsp/alterStoreMessage.jsp";
            }
        }
    }



}