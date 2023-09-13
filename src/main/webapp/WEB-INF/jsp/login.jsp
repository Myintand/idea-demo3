<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">

    <title>登录</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/loginandregister.css "/>">
    <script>
        // 刷新验证码图片
        function refreshCaptcha()
        {
            var captchaImg = document.getElementById("captchaImg");
            captchaImg.src = "${pageContext.request.contextPath}/UserController/code?" + new Date().getTime();
        }
    </script>

</head>
<body class="signin">

<div class="signinpanel">
    <div class="row">
        <div>
            <form action="${pageContext.request.contextPath}/UserController/login" method="post">
<%--                填的是servlet的value而不是文件名--%>
                <h2 align="center" style="font-family:楷体;color:black">欢迎，请登录</h2>

                <div class="form-group">
                    <input type="text" name="username" class="input" placeholder="用户名" required="required">
                </div>

                <div class="form-group">
                    <input type="password" name="passwd" class="input" placeholder="密码" required="required">
                </div>

                <div class="form-group">
                    <div style="width: 100px;height: 40px">
                        <input type="text" name="security" class="input" placeholder="验证码" required="required">
                    </div>
                    <img id="captchaImg" src="${pageContext.request.contextPath}/UserController/code">
                    <div><a href="javascript:void(0);" onclick="refreshCaptcha();" class="ax">看不清，换一张</a></div>
                </div>
                <p><span style="color: red;font-weight: bold">${message}</span></p>

                <label style="color: black;font-family: 楷体; font-size: 15px;font-weight: bold;float: right">下次自动登录</label><input style="float: right" type="checkbox" name="autLogin">
                <br>

                <p style="text-align:center"><button type="submit" class="submit">登 录</button></p>
                <p style="text-align:center">
                    <a href="<c:url value="/UserController/toforget"/>" title="忘记密码" class="ax">忘记密码？</a> |
                    <a href="<c:url value="/UserController/toregister"/>" title="注册" class="ax">注册账号</a> |
                    <a href="<c:url value="/UserController/toindex"/>" title="返回首页" class="ax">暂不登录</a></p>
            </form>
        </div>
    </div>
    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2023
        </div>
    </div>
</div>
</body>

</html>
