<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>编辑店铺资料</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/alter.css "/>">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/StoreController/alterstoremessage" method="post">

        <h2 align="center" style="font-family:楷体;color:black">编辑店铺资料</h2>

        <div>
            <label class="label" style="font-size: 25px">${LoginUser.getUsername()}</label>
            <%--            可以获取--%>
        </div>

        <div>
            <label class="label">旧密码：</label>
            <input type="password" name="ps" class="input" placeholder="旧密码验证" required="required">
        </div>
        <p><span style="color: red;font-weight: bold">${message}</span></p><%--        旧密码未验证，错误警告--%>



        <div>
            <label class="label">店铺名：</label>
            <input type="text" name="storename" class="input" placeholder="店铺名">
        </div>

        <div>
            <label class="label">起&nbsp;&nbsp;送：</label>
            <input type="number" name="beginning" class="input" placeholder="起送">
        </div>

        <div>
            <label class="label">简&nbsp;&nbsp;介：</label>
            <textarea class="input" cols="20" name="introduce" rows="3" placeholder="店铺简介"></textarea>
        </div>

        <p style="text-align:center">
            <button type="submit" class="submit">保 存</button>
            <button type="reset" class="submit">重 置</button>

            <br>
            <a href="<c:url value="/UserController/topersonal"/>" >返回个人中心</a>
        </p>
    </form>

    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2023
        </div>
    </div>

</div>

</body>
</html>
