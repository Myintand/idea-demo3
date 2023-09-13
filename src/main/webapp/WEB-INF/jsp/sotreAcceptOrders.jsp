<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>我的订单</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/orders.css"/>

</head>
<%--顶部--%>
<div class="indexnavigation">
    <a href="<c:url value="/UserController/toindex"/>" title="返回店铺列表">返回</a>
    <a href="<c:url value="/UserController/loginout"/>" style="float: right">退出登录</a>
</div>

<div style="text-align: center">
    <h1>店铺订单</h1>
</div>

<body>

<p><span style="color: red;font-weight: bold">${message}</span></p>

<c:forEach var="orders" items="${myacceptorderslist}">
    <div>
            <%--    选取那些进行中和已完成未评价的订单--%>
        <div class="order">
                <%--        顶端部分--%>
            <h2>${orders.getUsername()}</h2>
            <div style="  border: 1px solid #ccc;">
                <div  style="float:left;padding: 2px 5px;font-size: 20px;border-radius: 5px;background-color: #3CB371;color: #FFFFFF;">${orders.getTime()}</div>
            </div>
            <br>
                <%--    中间部分--%>
            <div style="text-align: left">
                <div style="font-weight: bold;color: red;margin-top:15px;">店铺名：${orders.getStorename()}</div>
                <div style="font-weight: bold;color: dodgerblue">用户购买内容：${orders.getContent()}</div>
                <div style="font-weight: bold;color: dodgerblue">用户联系电话：${orders.getTel()}</div>
                <div style="font-weight: bold;color: dodgerblue">用户配送地址：${orders.getAddress()}</div>
                <div style="font-weight: bold;color: dodgerblue">用户备注：${orders.getIntroduce()}</div>
            </div>
                <%--    底部--%>
            <div>
                <%--删除订单--%>
                <form action="${pageContext.request.contextPath}/OrdersController/deleteorders1" method="POST">
                    <input type="hidden" value="${orders.getUsername()}" name="username">
                    <input type="hidden" value="${orders.getStoreusername()}" name="storeusername">
                    <input type="hidden" value="${orders.getTime()}" name="time">
                    <input type="hidden" value="${orders.getState()}" name="state">
                    <button type="submit" class="order-button1">${orders.getState()}</button>
                </form>

                <%--完成订单--%>
                <form action="${pageContext.request.contextPath}/OrdersController/finishorders" method="post">
                    <input type="hidden" value="${orders.getUsername()}" name="username">
                    <input type="hidden" value="${orders.getStoreusername()}" name="storeusername">
                    <input type="hidden" value="${orders.getTime()}" name="time">
                    <button type="submit" class="order-button1">完成订单</button>
                </form>

                <div class="order-price">${orders.getPrice()}</div>
            </div>


        </div>

    </div>
</c:forEach>









</body>
</html>
