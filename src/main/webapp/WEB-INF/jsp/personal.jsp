<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>个人中心</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/personal.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upfilebutton.css"/>

</head>
<%--顶部--%>
<div class="indexnavigation">
    <a href="${pageContext.request.contextPath}/UserController/toindex" title="返回店铺列表">返回</a>
    <a href="${pageContext.request.contextPath}/UserController/loginout"style="float: right">退出登录</a>
</div>

<%--主体--%>
<body class="signin">
<%--style="word-break: break-word;--%>
<div class="personal_maindiv">

    <%--头像--%>
    <div style="text-align: center;margin: 10px 0">
        <img src="${LoginUser.getHeadpicture()}" class="personal_img">
    </div>

        <%--        头像更改是将图片文件上传到oss，然后修改用户数据库的头像图片名称--%>
        <form action="${pageContext.request.contextPath}/UserController/alterhead" enctype="multipart/form-data" method="post">
            <div  style="text-align: center">
                    <p><span style="color: red;font-weight: bold">${ToLargeFile}</span></p>
                <label for="file-input" class="upload-btn">选择文件</label>
                <input type="file" name="upfile" id="file-input" required>
                <input type="submit" value="更新头像" class="upload-btn">
            </div>
        </form>

    <div style="text-align: center">
            <a href="${LoginUser.getHeadpicture()}" type="submit">下载头像</a>
    </div>


    <div class="personal_label" >
        <%--所有信息--%>
        <div>
            <label  style="color: red">用户名：</label>
            <label>${LoginUser.getUsername()}</></label>
        </div>
        <br>
        <div>
            <label style="color: red">性&nbsp;&nbsp;别：</label>
            <label  >${LoginUser.getSex()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">学&nbsp;&nbsp;历：</label>
            <label  >${LoginUser.getEducation()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">邮&nbsp;&nbsp;箱：</label>
            <label  >${LoginUser.getEmail()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">简&nbsp;&nbsp;介：</label>
            <label >${LoginUser.getIntroduce()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">电&nbsp;&nbsp;话：</label>
            <label >${LoginUser.getTel()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">地&nbsp;&nbsp;址：</label>
            <label >${LoginUser.getAddress()}</></label>
        </div>
        <br>
        <div>
            <label  style="color: red">余&nbsp;&nbsp;额：</label>
            <label >￥${LoginUser.getMymoney()}</></label>
        </div>

            <div style="text-align: center">
                <form action="${pageContext.request.contextPath}/UserController/toaltermymessage" method="post">
                   <p><input type="submit" class="othersubmit" value="编辑资料"></p>
                </form>
            </div>

            <div style="text-align: center">
                <form action="${pageContext.request.contextPath}/UserController/torecharge" method="post">
                    <p><input type="submit" class="othersubmit" value="充值"></p>
                </form>
            </div>

            <div style="text-align: center">
                <form action="${pageContext.request.contextPath}/RollController/tomyroll" method="post">
                    <p><input type="submit" class="othersubmit" value="我的优惠卷"></p>
                </form>
            </div>
            <div style="text-align: center">
                <form action="${pageContext.request.contextPath}/RollController/togetroll" method="post">
                    <p><input type="submit" class="othersubmit" value="兑换优惠卷"></p>
                </form>
            </div>

            <div style="text-align: center">
                <form action="${pageContext.request.contextPath}/StoreController/toadd" method="post">
                    <p><input type="submit" class="othersubmit" value="成为商家"></p>
                </form>
            </div>

    </div>

</div>

</body>
</html>