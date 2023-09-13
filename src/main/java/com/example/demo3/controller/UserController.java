package com.example.demo3.controller;


import com.alipay.api.AlipayApiException;
import com.example.demo3.pojo.*;
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
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.example.demo3.controller.tool.AlipayTool.pay;
import static com.example.demo3.controller.tool.IsLegalEmailAddress.isLegalEmailAddress;
import static com.example.demo3.controller.tool.IsLegalPassword.isLegalPassword;
import static com.example.demo3.controller.tool.IsLegalTel.isLegalTel;
import static com.example.demo3.controller.tool.IsLegalUserName.isLegalUsername;
import static com.example.demo3.controller.tool.UploadOss.uploadImage;

@Controller//使其可扫描，control层注解,//被这个注解的类中的所有方法，如果返回值是String，并且有具体页面可以跳转，那么就会被视图解析器解析;
@RequestMapping("/UserController")//一级路由
//此servlet 负责将进入的 HTTP 请求路由到控制器的处理方法，有点像过滤器，只负责传递，具体处理还是在servlet中
public class UserController
{
    @Resource(name = "userServiceImpl")
    private UserService userService;
    @Resource(name = "storeServiceImpl")
    private StoreService storeService;
    @Resource(name = "ordersServiceImpl")
    private OrdersService ordersService;

    @Resource(name = "tempServiceImpl")
    private TempService tempService;

    @Resource(name = "userrollServiceImpl")
    private UserrollService userrollService;


    /**
     * 跳转到内部首页okk（获取店铺数据并跳转）（已不成问题）
     */
    @RequestMapping("/toindex")
    public String toindex(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session=request.getSession();
        ArrayList<Store> stores = storeService.getStoreList();
        session.setAttribute("storelist",stores);
        return "/WEB-INF/jsp/index.jsp";
    }


    /**
     * 跳转到内部充值界面okk
     */
    @RequestMapping("/torecharge")
    public String torecharge(HttpServletRequest request, HttpServletResponse response)
    {
        HttpSession session= request.getSession();
        if(session.getAttribute("LoginUser")==null)
        {
            request.setAttribute("message","您还未登陆，请先登录");
            return "/WEB-INF/jsp/login.jsp";
        }
        return "/WEB-INF/jsp/recharge.jsp";
    }


    /**
     * 跳转到内部首页okk
     */
    @RequestMapping("/toindex1")
    public String toindex1()
    {
        return "/WEB-INF/jsp/index.jsp";
    }


    /**
     * 跳转到内部登陆okk
     */
    @RequestMapping("/tologin")
    public String tologin()
    {
        return "/WEB-INF/jsp/login.jsp";
    }


    /**
     * 跳转到内部忘记密码okk
     */
    @RequestMapping("/toforget")
    public String toforget()
    {
        return "/WEB-INF/jsp/forget.jsp";
    }


    /**
     * 跳转到内部注册okk
     */
    @RequestMapping("/toregister")
    public String toregister()
    {
        return "/WEB-INF/jsp/register.jsp";
    }


    /**
     * 跳转到内部编辑资料okk
     */
    @RequestMapping("/toaltermymessage")
    public String toaltermymessage()
    {
        return "/WEB-INF/jsp/alterMyMessage.jsp";
    }


    @RequestMapping("/toalipay")
    public String toalipay(){
        return "/WEB-INF/jsp/alipay.jsp";
    }


    @RequestMapping("/usealipay")
    public void usealipay(HttpServletResponse response) throws IOException {

        String t= String.valueOf(System.currentTimeMillis());
        String form =pay(t,88.88,"测试");
        response.setContentType("text/html");
        response.getWriter().write(form);
    }


    /**
     * 跳转到个人中心或者是登录界面okk（先判断LoginUser是否存在，有就去获取个人店铺数据然后进入，没有就从cookie查找，如果cookie里也找不到，说明未登陆）ok(进入个人中心判断已不成问题)
     */
    @RequestMapping("/topersonal")
    public String topersonal(HttpServletRequest request, HttpServletResponse response) throws SQLException {
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
                    System.out.println("cookie: "+co);
                    if("wenzi1".equals(co))//如果存在名称为wenzi1的cookie，说明已登录
                    {
                        String value= cookie.getValue();
                        if(value!=null)
                        {
                            User user=null;
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
                                //赋值到session，表示登录
                                System.out.println("进入个人中心cookie登陆："+user);
                                session.setAttribute("LoginUser",user);

                                Store store=null;
                                store=storeService.getonestore(value);//不能再new
                                if(store!=null)
                                {
                                    System.out.println("进入个人中心cookie登陆："+store);
                                    session.setAttribute("LoginStore",store);
                                    return "/WEB-INF/jsp/storePersonal.jsp";
                                }
                                else
                                {
                                    return "/WEB-INF/jsp/personal.jsp";
                                }

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
            //查找LoginStore
            User user= (User) session.getAttribute("LoginUser");
            User user1=userService.getoneuserbyusername(user.getUsername());
            session.setAttribute("LoginUser",user1);
            Store store=storeService.getonestore(user.getUsername());
            if(store!=null)
            {
                System.out.println("进入个人中心Login不为空时的查找："+store);
                session.setAttribute("LoginStore",store);
                return "/WEB-INF/jsp/storePersonal.jsp";
            }
            else
            {
                return "/WEB-INF/jsp/personal.jsp";
            }
        }
    }


    /**
     * 跳转到订单页面或者是登录界面okk（先判断LoginUser是否存在，有就去获取用户然后进入，没有就从cookie查找，如果cookie里也找不到，说明未登陆）ok(进入订单界面判断已不成问题)
     */
    @RequestMapping("/tomyorders")
    public String tomyorders(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
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
                    System.out.println("cookie: "+co);
                    if("wenzi1".equals(co))//如果存在名称为wenzi1的cookie，说明已登录
                    {
                        String value= cookie.getValue();
                        if(value!=null)
                        {
                            User user=null;
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
                                //赋值到session，表示登录
                                System.out.println("进入订单界面cookie登陆："+user);
                                session.setAttribute("LoginUser",user);

                                ArrayList<Orders> myorderslist=null;
                                myorderslist=ordersService.getmyorders(value);//不能再new
                                if(myorderslist!=null)
                                {
                                    System.out.println("进入订单界面cookie登陆："+myorderslist);
                                    session.setAttribute("myorderslist",myorderslist);
                                }
                                return "/WEB-INF/jsp/myOrders.jsp";

                            }
                            break;
                        }
                    }
                }
            }
            model.addAttribute("message","您还未登陆，请先登陆再进入订单页面");
            return "/WEB-INF/jsp/login.jsp";
        }
        else//LoginUser不为空
        {
            //查找myorderslist
            User user= (User) session.getAttribute("LoginUser");
            ArrayList<Orders> myorderslist=ordersService.getmyorders(user.getUsername());
            if(myorderslist!=null)
            {
                System.out.println("进入个人中心Login不为空时的查找："+myorderslist);
                session.setAttribute("myorderslist",myorderslist);
            }
            return "/WEB-INF/jsp/myOrders.jsp";
        }
    }


    /**
     * 前往选择使用优惠卷
     */
    @RequestMapping("/selectroll")
    public String selectroll(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        Orders orders=new Orders();//构建预订单对象

        //先判断是否满足起送金额
        Store store= (Store) session.getAttribute("nowstore");
        String price1 = request.getParameter("total");//订单原始价格
        double price= Double.parseDouble(price1);
        if(store.getBeginning()>price)
        {
            request.setAttribute("message","不满起送价格");
            return "/WEB-INF/jsp/storeIndex.jsp";
        }

        //判断用户是否登录
        User user=null;
        boolean flag = false;
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
                                flag=true;
                                session.setAttribute("LoginUser",user);
                            }
                            break;
                        }
                    }
                }
            }

        }
        else//LoginUser不为空,说明已经登陆
        {
            //已登录
            flag = true;
            user= (User) session.getAttribute("LoginUser");
        }
        if(!flag)
        {
            request.setAttribute("message","您还未登陆，请先登陆再下单");
            return "/WEB-INF/jsp/login.jsp";
        }

        //给预订单赋值，先不加入数据库
        //生成订单
        orders.setUsername(user.getUsername());//下单人用户名
        //订单电话
        orders.setTel(user.getTel());
        //订单内容
        String content = request.getParameter("info");
        System.out.println("内容和价格："+content+" + "+price);
        orders.setContent(content);
        //订单价格
        orders.setPrice(price);
        //配送地址
        orders.setAddress(user.getAddress());
        //店家用户名
        orders.setStoreusername(store.getUsername());
        //店家名
        orders.setStorename(store.getStorename());
        //商家头像
        orders.setPicture(store.getPicture());
        //订单备注
        orders.setIntroduce(request.getParameter("note"));
        //下单时间
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String datetime = sdf.format(date);
        System.out.println("当前时间："+datetime);
        orders.setTime(datetime);//下单时间
        //订单状态
        orders.setState("取消订单");//订单状态
        System.out.println("所下预订单内容"+orders);
        session.setAttribute("firstorders",orders);//将预订单传入

        //到这就说明是已经登录的用户，显示可使用的优惠卷，然后进入选择界面
        ArrayList<Roll> myuserablerolllist = userrollService.getuserableroll(user.getUsername(),store.getUsername(), price);

        // 创建一个存储无效Roll的列表
        ArrayList<Roll> myInvalidRollList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前系统时间
        LocalDateTime now = LocalDateTime.now();

        // 使用迭代器遍历myuserablerolllist，并在迭代过程中删除不符合条件的元素
        for (Iterator<Roll> iterator = myuserablerolllist.iterator(); iterator.hasNext();) {
            Roll roll = iterator.next();
            // 将Roll的begintime和endtime转换为LocalDateTime
            LocalDateTime beginTime = LocalDateTime.parse(roll.getBegintime(), formatter);
            LocalDateTime endTime = LocalDateTime.parse(roll.getEndtime(), formatter);
            // 如果当前时间不在begintime和endtime之间，则将roll从myuserablerolllist中删除，并添加到myInvalidRollList中
            if (now.isBefore(beginTime) || now.isAfter(endTime)) {
                iterator.remove();
                myInvalidRollList.add(roll);
            }
        }
        session.setAttribute("myuserablerolllist",myuserablerolllist);
        session.setAttribute("myInvalidRollList",myInvalidRollList);
        return "/WEB-INF/jsp/userSelectRoll.jsp";//前往选择优惠卷的界面

    }

    /**
     * 添加订单并进入订单页面okk
     */
    @RequestMapping("/addorders")
    public String addorders(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //获取预订单和当前用户对象
        HttpSession session= request.getSession();
        Orders orders= (Orders) session.getAttribute("firstorders");
        User user= (User) session.getAttribute("LoginUser");

        //获取最终价格和使用的优惠卷编号
        String rollnumber=null;
        rollnumber=request.getParameter("rollnumber");//获取使用的优惠卷编号
        double newprice= Double.parseDouble(request.getParameter("newprice"));//获取最新价格

        if(rollnumber==null)//没有使用优惠卷，不做修改，直接生成订单
        {
            //余额判断,不足就打回
            if(user.getMymoney()<newprice)//余额不足
            {
                request.setAttribute("message","余额不足");
                return "/WEB-INF/jsp/storeIndex.jsp";
            }

            ordersService.addorders(orders);
            //订单添加后，向temp插入数据，并对用户余额扣款
            //扣款
            userService.reducemymoney(user.getUsername(),newprice);
            user.setMymoney(user.getMymoney()-newprice);
            System.out.println("用户扣款："+newprice);

            //生成temp
            Temp temp=new Temp();
            temp.setUsername(orders.getUsername());
            temp.setStoreusername(orders.getStoreusername());
            temp.setMoney(newprice);
            temp.setTime(orders.getTime());
            System.out.println("下单时生成的temp："+temp);
            tempService.add(temp);

            //查找出我的订单
            ArrayList<Orders> myorderslist=null;//根据登陆信息查找订单
            myorderslist=ordersService.getmyorders(user.getUsername());//不能再new
            if(myorderslist!=null)
            {
                System.out.println("下单后更新myorderslist(没有使用优惠卷)："+myorderslist);
                session.setAttribute("myorderslist",myorderslist);
            }
            return "/WEB-INF/jsp/myOrders.jsp";

        }
        else//使用了优惠卷进行下单
        {
            //余额判断,不足就打回
            if(user.getMymoney()<newprice)//余额不足
            {
                session.setAttribute("message","余额不足");
                return "/WEB-INF/jsp/storeIndex.jsp";
            }

            //更改优惠卷为已使用状态
            System.out.println("rollnumber: "+rollnumber);
            userrollService.alterrolltoalreadyuse(user.getUsername(),rollnumber);
            //更新session
            //根据店铺用户名获取优惠卷信息
            ArrayList<Roll> myrolllist=userrollService.getallrollbyusername(user.getUsername());
            session.setAttribute("myrolllist",myrolllist);

            //更新预订单价格
            orders.setPrice(newprice);
            //设置优惠卷编号
            orders.setTheroll(rollnumber);
            //生成订单
            ordersService.addorders(orders);

            //订单添加后，向temp插入数据，并对用户余额扣款
            //扣款
            userService.reducemymoney(user.getUsername(),newprice);
            user.setMymoney(user.getMymoney()-newprice);
            System.out.println("用户扣款："+newprice);

            //生成temp
            Temp temp=new Temp();
            temp.setUsername(orders.getUsername());
            temp.setStoreusername(orders.getStoreusername());
            temp.setMoney(newprice);
            temp.setTime(orders.getTime());
            System.out.println("下单时生成的temp："+temp);
            tempService.add(temp);

            //查找出我的订单
            ArrayList<Orders> myorderslist=null;//根据登陆信息查找订单
            myorderslist=ordersService.getmyorders(user.getUsername());//不能再new
            if(myorderslist!=null)
            {
                System.out.println("下单后更新myorderslist（使用了优惠卷）："+myorderslist);
                session.setAttribute("myorderslist",myorderslist);
            }
            return "/WEB-INF/jsp/myOrders.jsp";

        }

    }



    /**
     * 显示所有用户
     */
    @RequestMapping("/showusers")
    public String showusers(Model model,HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        ArrayList<User> users=userService.getUserList();
        model.addAttribute("userlist",users);
        return "/WEB-INF/jsp/index.jsp";
    }


    /**
     * 注册功能实现okk（云okk）
     */
    @RequestMapping("/register")
    public String register(MultipartFile uploadfile,HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //赋值变量
        String username = request.getParameter("username");//从前端获取数据用request
        String password = request.getParameter("passwd");
        String surePassword = request.getParameter("surePasswd");
        String sex = request.getParameter("sex");//性别
        String education = request.getParameter("education");//学历
        String email = request.getParameter("email");//邮箱地址
        String introduce = request.getParameter("introduce");//简介
        String problem = request.getParameter("problem");//问题
        String answer = request.getParameter("answer");//答案
        String tel=request.getParameter("tel");//电话
        String address=request.getParameter("address");//配送地址

        /**
         * 数据保存
         */
        request.setAttribute("un", username);//名称数据保存
        request.setAttribute("pw", password);//密码数据保存
        request.setAttribute("spw", surePassword);//确认密码数据保存
        if(Objects.equals(sex, "男"))
        {
            request.setAttribute("man", "checked");
        }
        else
        {
            request.setAttribute("woman", "checked");
        }
        request.setAttribute("ea", email);//邮箱数据保存
        request.setAttribute("i", introduce);//简介
        request.setAttribute("p", problem);//问题
        request.setAttribute("a", answer);//答案
        request.setAttribute("t", tel);//电话
        request.setAttribute("ad", address);//配送地址

        /**
         * 合法验证
         */
        //名称合法性验证
        int usernameFlag = isLegalUsername(username);
        if(usernameFlag==2)
        {
            request.setAttribute("error", "用户名不能为空");
            return "/WEB-INF/jsp/register.jsp";
        }
        else if (usernameFlag==3)
        {
            request.setAttribute("error", "用户名不能超过15位");
            return "/WEB-INF/jsp/register.jsp";
        }
        else if (usernameFlag==4)
        {
            request.setAttribute("error", "用户名不能包含空格、换行、制表符号");
            return "/WEB-INF/jsp/register.jsp";
        }
        else if (usernameFlag==5)
        {
            request.setAttribute("error", "用户名不能以0开头");
            return "/WEB-INF/jsp/register.jsp";
        }
        else if (usernameFlag==6)
        {
            request.setAttribute("error", "用户名不能包含文字");
            return "/WEB-INF/jsp/register.jsp";
        }
        if(userService.getoneuserbyusername(username)!=null)//查找是否存在该用户
        {
            request.setAttribute("error", "用户已存在");
            return "/WEB-INF/jsp/register.jsp";
        }

        //密码合法验证
        if(!password.equals(surePassword))
        {
            request.setAttribute("error", "两次密码不一致");//将提示信息保存在session中,可以通过${}获取数据
            return "/WEB-INF/jsp/register.jsp";
        }
        int passwordFlag = isLegalPassword(password);
        if(passwordFlag==2)
        {
            request.setAttribute("error", "密码不能为空");
            return "/WEB-INF/jsp/register.jsp";
        }
        if(passwordFlag==3)
        {
            request.setAttribute("error", "密码长度不低于6位，不高于20位");
            return "/WEB-INF/jsp/register.jsp";
        }
        if(passwordFlag==4)
        {
            request.setAttribute("error", "密码不能包含文字");
            return "/WEB-INF/jsp/register.jsp";
        }
        if(passwordFlag==5)
        {
            request.setAttribute("error", "密码至少要使用两种字符");
            return "/WEB-INF/jsp/register.jsp";
        }

        //邮箱合法性验证
        if(!isLegalEmailAddress(email))
        {
            request.setAttribute("error", "请输入正确的邮箱");
            return "/WEB-INF/jsp/register.jsp";
        }

        //电话合法性验证
        if (!isLegalTel(tel))
        {
            request.setAttribute("error", "请输入正确的电话号码");
            return "/WEB-INF/jsp/register.jsp";
        }


        /**
         * 数据上传
         */
        String URL="https://zqy-287189.oss-cn-beijing.aliyuncs.com/DeliverySystem/moreng.gif";//设为默认头像的URL
        //图片为空验证
        String headpicture = uploadfile.getOriginalFilename();//获取文件名
        if( !(Objects.equals(headpicture, "")) && headpicture != null)//图片名称非空，就赋值
        {
            //上传到OSS并返回url
            URL= uploadImage(uploadfile,username+"/headPicture");
        }
        System.out.println(URL);

        //封装类
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setSex(sex);
        user.setEducation(education);
        user.setEmail(email);
        user.setIntroduce(introduce);
        user.setProblem(problem);//问题
        user.setAnswer(answer);//答案
        user.setHeadpicture(URL);//头像文件路径
        user.setTel(tel);//电话
        user.setAddress(address);//地址
        System.out.println("register:"+user);
        //存入数据库
        try
        {
            userService.add(user);
            request.setAttribute("message", "注册成功，请登陆");//将提示信息保存在session中,可以通过${}获取数据
            return "/WEB-INF/jsp/login.jsp";
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

    }


    /**
     * 登录验证okk（登录判定，并跳转到主界面（能获取主界面商家的那种））ok(登陆不成问题)
     */
    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");


        String username = request.getParameter("username");//用户名
        String password = request.getParameter("passwd");//密码
        String sure = request.getParameter("security");//验证码
        String autLogin = request.getParameter("autLogin");//自动登录


        //判断验证码
        //获取session
        HttpSession session = request.getSession();//创建session对象
        Object sss = session.getAttribute("rand");
        String se = sss.toString();//获取并赋值
        System.out.println("login验证端获取的Session验证码：" + se);
        System.out.println("login我输入的验证码：" + sure);

        if (!sure.equals(se))
        {
            request.setAttribute("message","验证码错误，请重新登录");
            return "/WEB-INF/jsp/login.jsp";
        }
        else
        {
            //数据库查询是否有这个用户
            User user = userService.getoneuser(username, password);
            if (user != null)//有这个用户
            {
                //下次自动登录
                if (autLogin != null)
                {
                    Cookie cookie = new Cookie("wenzi1", username);
                    cookie.setMaxAge(60 * 60);//设置存活时间
                    response.addCookie(cookie);
                }
                System.out.println("登录时："+user);
                session.setAttribute("LoginUser",user);//赋值用户

                return toindex(request,response);//主界面跳转
            }
            else
            {
                // 无此用户，转发到登录页,返回提示信息
                request.setAttribute("message","用户名或密码错误，请重新登录");
                return "/WEB-INF/jsp/login.jsp";
            }
        }
    }


    /**
     * 忘记密码1（用户查找）okk
     */
    @RequestMapping("/updatePwd")
    public String updatePwd(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //赋值变量
        String username = request.getParameter("forget_username");//必填不可能为空
        if(username!=null)
        {
            //数据库查询是否有这个用户
            User user = null;
            try
            {
                user = userService.getoneuserbyusername(username);
            }
            catch (SQLException e)
            {
                throw new RuntimeException(e);
            }
            if (user != null)
            {
                System.out.println("UserController:updatePwd:用户存在"+user);
                //记得先获取session对象再做操作
                HttpSession session=request.getSession();
                session.setAttribute("user", user);
                return "/WEB-INF/jsp/forget2.jsp";
            }
            else
            {
                request.setAttribute("message","用户名不存在");
                return "/WEB-INF/jsp/forget.jsp";
            }
        }
        else
        {
            request.setAttribute("message","请输入用户名");
            return "/WEB-INF/jsp/forget.jsp";
        }
    }


    /**
     * 忘记密码2(密保验证)okk
     */
    @RequestMapping("/updatePwd2")
    public String updatePwd2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //赋值变量
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("user");
        String answer1 = user.getAnswer();
        String answer2 = request.getParameter("forget2_answer");//输入的答案

        //判断
        if(answer1.equals(answer2))
        {
            request.setAttribute("msg3","验证成功，请输入新密码");
            return "/WEB-INF/jsp/forget3.jsp";
        }
        else
        {
            request.setAttribute("msg2","答案错误");
            return "/WEB-INF/jsp/forget2.jsp";
        }
    }


    /**
     * 忘记密码3(新密码)ok
     */
    @RequestMapping("/updatePwd3")
    public String updatePwd3(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException {
        //解决中文乱码,浏览器相应
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("UTF-8");

        //赋值变量(获取两次输入的密码)
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("user");
        String pass= request.getParameter("forget3_pass");
        String surepass= request.getParameter("forget3_surepass");

        //新密码验证
        if(pass.equals(surepass))
        {
            //传入新密码
            user.setPassword(pass);
            //数据库修改
            if(userService.getoneuserbyusername(user.getUsername())!=null)//用户存在
            {
                int i=userService.modifypassword(user.getPassword(),user.getUsername());
                if(i>0)
                {
                    session.removeAttribute("user");
                    request.setAttribute("message","修改成功，请登陆");
                }
                else
                {
                    session.removeAttribute("user");
                    request.setAttribute("message","修改失败");
                }

            }
            else
            {
                session.removeAttribute("user");
                request.setAttribute("message","修改失败");
            }
            return "/WEB-INF/jsp/login.jsp";
        }
        else
        {
            request.setAttribute("msg3","两次密码不一致");
            return "/WEB-INF/jsp/forget3.jsp";
        }
    }


    /**
     * 退出登录okk(退出登陆时清空登陆用户和登陆cookie)
     */
    @RequestMapping("/loginout")
    public String loginout(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        //清除session
        HttpSession session=request.getSession();
        session.removeAttribute("LoginUser");

        //删除cookie
        Cookie cookie=new Cookie("wenzi1",null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        System.out.println("登陆用户已退出");

        //重定向到主页
        return toindex(request,response);
    }


    /**
     * 修改头像okk(云ok)
     */
    @RequestMapping("/alterhead")
    private String alterhead(MultipartFile upfile, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //保存头像文件
//        String headpicture=upfile.getOriginalFilename();
//        String path = request.getServletContext().getRealPath("/Upload");//文件保存路径
//        File UpLoadFile = new File(path);

        //获取用户
        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");//获取用户
        //上传到OSS
        String URL= uploadImage(upfile,user.getUsername()+"/headPicture");
        System.out.println(URL);
        //修改数据库


//        if (!UpLoadFile.exists())
//        {
//            UpLoadFile.mkdir();//如果目录不存在就创建这样一个目录
//        }
//        String savePath = path + "\\" + headpicture;
//        System.out.println("路径："+savePath);
//        upfile.transferTo(new File(savePath));
//
        //数据库更新

        user.setHeadpicture(URL);//更改该用户头像文件路径
        userService.modify(user);//保存到数据库
        session.setAttribute("LoginUser",user);//更新session
        if(storeService.getonestore(user.getUsername())!=null)
        {
            return "/WEB-INF/jsp/storePersonal.jsp";
        }
        else
        {
            return "/WEB-INF/jsp/personal.jsp";
        }
    }


    /**
     * 修改个人信息okk
     */
    @RequestMapping("/altermymessage")
    private String altermymessage(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
            return "/WEB-INF/jsp/alterMyMessage.jsp";
        }
        else
        {
            //旧密码验证成功，获取所有输入
            String newpasswd = request.getParameter("passwd");//从前端获取数据用request
            String surePassword = request.getParameter("surePasswd");

            if(!newpasswd.equals(surePassword))
            {
                request.setAttribute("error","两次密码不一致");
                return "/WEB-INF/jsp/alterMyMessage.jsp";
            }
            else//旧密码正确且新密码一致
            {
                //从表单获取剩下的值
                String sex = request.getParameter("sex");//从前端获取数据用request
                String education = request.getParameter("education");
                String email = request.getParameter("email");
                String introduce = request.getParameter("introduce");
                String problem = request.getParameter("problem");
                String answer = request.getParameter("answer");
                String tel=request.getParameter("tel");//电话
                String address=request.getParameter("address");//地址

                //修改数据库
                user.setPassword(newpasswd);//新密码
                user.setSex(sex);//性别
                user.setEducation(education);//学历
                user.setEmail(email);//邮箱
                user.setIntroduce(introduce);//简介
                user.setProblem(problem);//问题
                user.setAnswer(answer);//答案
                user.setTel(tel);//电话
                user.setAddress(address);//地址
                System.out.println("modify:"+user);
                if(userService.modify(user)>0)
                {
                    session.setAttribute("LoginUser",user);//更新session
                    if(session.getAttribute("LoginStore")!=null)
                    {
                        return "/WEB-INF/jsp/storePersonal.jsp";
                    }
                    else
                    {
                        return "/WEB-INF/jsp/personal.jsp";
                    }
                }
                else
                {
                    request.setAttribute("message","更新信息失败");
                    return "/WEB-INF/jsp/alterMyMessage.jsp";
                }

            }
        }
    }


    /**
     * 充值okk
     */
    @RequestMapping("/recharge")
    public String recharge(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, AlipayApiException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");

        HttpSession session= request.getSession();
        User user= (User) session.getAttribute("LoginUser");
        double number= Double.parseDouble(request.getParameter("number"));//充值金额


        // 设置日期格式
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        // 获取当前时间
        Date currentTime = new Date();
        // 格式化日期为字符串
        String formattedTime = dateFormat.format(currentTime);
        // 去除空格字符
        String trimmedTime = formattedTime.replaceAll("\\s", "");
        // 输出结果
        System.out.println("当前时间： "+trimmedTime);

//        pay(trimmedTime,number,"充值");//支付

        if(userService.recharge(user.getUsername(),number)>0)
        {
            request.setAttribute("message","充值成功");
        }
        else
        {
            request.setAttribute("message","充值失败");
        }

        return topersonal(request,response);

    }





//    /**
//     * 下载头像okk
//     */
//    @RequestMapping("/FileUpload")
//    public String FileUpload(HttpServletRequest request, HttpServletResponse response) throws IOException
//    {
//        //对应下载的文件名（要修改为变量）
//        HttpSession session=request.getSession();
//        String fileName = ((User) session.getAttribute("LoginUser")).getHeadpicture();
//        System.out.println("UserController:fileName:"+fileName);
//
//        String path = request.getServletContext().getRealPath("/Upload");//获取图像文件夹路径（不是图片路径）
//        String mineType = request.getServletContext().getMimeType(fileName);//获取图片路径
//
//        System.out.println("UserController:mineType:" + mineType);
//        String filePath = path + "\\" + fileName;
//        System.out.println("UserController:被下载文件的路径"+filePath);
//
//        File downloadfile = new File(filePath);//文件对象对准文件路径
//        if (!downloadfile.exists())//判断是否存在
//        {
//            System.out.println("UserController:文件 " + filePath + "不存在");
//            request.setAttribute("downloadstatus","文件不存在");
//            if(session.getAttribute("LoginStore")!=null)
//            {
//                return "/WEB-INF/jsp/storePersonal.jsp";
//            }
//            else
//            {
//                return "/WEB-INF/jsp/personal.jsp";
//            }
//        }
//        //文件存在，开始下载
//        //1、设置response 响应头
//        response.reset(); //设置页面不缓存,清空buffer
//        response.setContentType(mineType);
////设置响应头
//        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, "UTF-8"));
//        File file = new File(path, fileName);
////2、 读取文件--输入流
//        InputStream input = new FileInputStream(file);
////3、 写出文件--输出流
//        OutputStream out = response.getOutputStream();
//        byte[] buff = new byte[1024];
//        int index = 0;
////4、执行 写出操作
//        while ((index = input.read(buff)) != -1)
//        {
//            out.write(buff, 0, index);
//            out.flush();
//        }
//        out.close();
//        input.close();
//        if(session.getAttribute("LoginStore")!=null)
//        {
//            return "/WEB-INF/jsp/storePersonal.jsp";
//        }
//        else
//        {
//            return "/WEB-INF/jsp/personal.jsp";
//        }
//    }


    /**
     * 验证码生成okk
     */
    public static String code="abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPGRSTUVWXYZ";
    Color getRandColor(int fc, int bc){//给定范围获得随机颜色
        Random random = new Random();
        if(fc>255) fc=255;
        if(bc>255) bc=255;
        int r=fc+random.nextInt(bc-fc);
        int g=fc+random.nextInt(bc-fc);
        int b=fc+random.nextInt(bc-fc);
        return new Color(r,g,b);
    }
    @RequestMapping("/code")//二级路由
    public void code(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        //设置页面不缓存
        response.setHeader("Pragma","No-cache");
        response.setHeader("Cache-Control","no-cache");
        response.setDateHeader("Expires", 0);
// 在内存中创建图象,设置图片的显示大小
        int width=100, height=35;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
// 获取图形上下文
        Graphics g = image.getGraphics();
//生成随机类
        Random random = new Random();
// 设定背景色
        g.setColor(getRandColor(200,250));
        g.fillRect(0, 0, width, height);
//设定字体
        g.setFont(new Font("Times New Roman",Font.PLAIN,18));
//画边框
//g.setColor(new Color());
//g.drawRect(0,0,width-1,height-1);
// 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(160,200));
        for (int i=0;i<155;i++)
        {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x,y,x+xl,y+yl);
        }
// 取随机产生的认证码(由数字和字母组长的)
        String sRand="";
        for (int i=0;i<4;i++){
            int rand=random.nextInt(36);
            sRand+=String.valueOf(code.charAt(rand));
// 将认证码显示到图象中
            g.setColor(new Color(20+random.nextInt(110),20+random.nextInt(110),20+random.nextInt(110)));// 调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(String.valueOf(code.charAt(rand)),15*i+15,26);
        }
// 将认证码存入SESSION
        HttpSession session=request.getSession();
        session.setAttribute("rand",sRand);
        System.out.println("codeServlet:生成的验证码为："+sRand);
// 图象生效
        g.dispose();
// 输出图象到页面
        ImageIO.write(image, "JPEG", response.getOutputStream());
    }

}