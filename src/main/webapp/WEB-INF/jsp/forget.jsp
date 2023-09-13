<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>忘记密码</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/loginandregister.css">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/UserController/updatePwd" method="post">
        <h2 align="center" style="font-family:楷体;color:black">忘记密码</h2>

        <div>
            <input type="text" name="forget_username" class="input" placeholder="要找回的用户名" required="required">
        </div>
        <p><span style="color: red;font-weight: bold">${message}</span></p>
        <p style="text-align:center"><button type="submit" class="submit">确 认</button></p>

        <p align="center"><a href="${pageContext.request.contextPath}/UserController/tologin" class="ax">已有账号，点击登录</a></p>
    </form>


    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2023
        </div>
    </div>

</div>

</body>
</html>
