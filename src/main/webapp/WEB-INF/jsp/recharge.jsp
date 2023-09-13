<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>用户充值</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/recharge.css "/>">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/UserController/recharge" method="post">
        <h2 align="center" style="font-family:楷体;color:black">充值</h2>

        <div>
<%--            订单金额--%>
            <input type="number" name="number" class="input" placeholder="请输入金额" required="required">
        </div>
        <p><span style="color: red;font-weight: bold">${message}</span></p>
        <p style="text-align:center"><button type="submit" class="submit">点击充值</button></p>

        <p align="center"><a href="${pageContext.request.contextPath}/UserController/topersonal" class="ax">返回</a></p>
    </form>

    <a href="${pageContext.request.contextPath}/UserController/toalipay" class="ax">支付宝</a>

    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2023
        </div>
    </div>

</div>



</body>
</html>
