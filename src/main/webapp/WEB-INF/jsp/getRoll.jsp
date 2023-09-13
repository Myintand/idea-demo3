<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>兑换店铺优惠卷</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/getroll.css ">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/RollController/getroll" method="post">
        <h2 align="center" style="font-family:楷体;color:black">兑换店铺优惠卷</h2>

        <%--        获取商家用户名和商家名称作为优惠卷可使用商家--%>
        <input type="hidden" name="usernanme2" value="${getroll-username}">

        <div>
            <input type="text" name="number2" class="input" placeholder="请输入兑换码" required="required">
        </div>

        <p><span style="color: red;font-weight: bold">${message}</span></p>

        <p style="text-align:center"><button type="submit" class="submit">兑换</button></p>

        <p align="center"><a href="${pageContext.request.contextPath}/UserController/topersonal" class="ax">返回</a></p>
    </form>

</div>

</body>
</html>
