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
    <h1>我的订单</h1>
</div>

<body>

<p><span style="color: red;font-weight: bold">${message}</span></p>

<c:forEach var="orders" items="${myorderslist}">
    <div>
            <%--    选取那些进行中和已完成未评价的订单--%>
        <div class="order">
                <%--        顶端部分--%>
            <h2>${orders.getStorename()}</h2>
                <%--        分割线--%>
            <div style="border: 1px solid #ccc;">
                <div  style="float:left;padding: 2px 5px;font-size: 20px;border-radius: 5px;background-color: #3CB371;color: #FFFFFF;">${orders.getTime()}</div>
            </div>
                <%--    中间部分--%>
                <br><br>
                <img src="${orders.getPicture()}" style="width: 140px;height: 140px;float: left">
            <div style="text-align: left;padding: 0 15px;margin: 0 15px">
                <div style="font-weight: bold;color: red">&nbsp;&nbsp;&nbsp;购买内容：${orders.getContent()}</div>
                <div style="font-weight: bold;color: dodgerblue;">&nbsp;&nbsp;&nbsp;用户名：${orders.getUsername()}</div>
                <div style="font-weight: bold;color: dodgerblue">&nbsp;&nbsp;&nbsp;联系电话：${orders.getTel()}</div>
                <div style="font-weight: bold;color: dodgerblue">&nbsp;&nbsp;&nbsp;配送地址：${orders.getAddress()}</div>
                <div style="font-weight: bold;color: dodgerblue">&nbsp;&nbsp;&nbsp;备注：${orders.getIntroduce()}</div>
            </div>
                <%--    底部--%>
            <div>
                <%--    删除订单--%>
                <form action="${pageContext.request.contextPath}/OrdersController/deleteorders" method="POST">
                    <input type="hidden" value="${orders.getUsername()}" name="username">
                    <input type="hidden" value="${orders.getStoreusername()}" name="storeusername">
                    <input type="hidden" value="${orders.getTime()}" name="time">
                    <input type="hidden" value="${orders.getState()}" name="state">
                    <button type="submit" class="order-button1">${orders.getState()}</button>
                </form>
                <%--    评价--%>
                <form action="${pageContext.request.contextPath}/OrdersController/evaluate" method="POST">
                    <input type="hidden" value="${orders.getUsername()}" name="username">
                    <input type="hidden" value="${orders.getStoreusername()}" name="storeusername">
                    <input type="hidden" value="${orders.getTime()}" name="time">
                    <input type="hidden" value="${orders.getState()}" name="state">

                    <button type="submit" class="order-button1">评价</button>

                    <div>
                        <label style="font-size:20px;float:right;margin-top:10px;color:#FFD700;font-family: 楷体;font-weight: bold">
                            <input type="radio"  name="score" value="1" required><span>1星</span>
                            <input type="radio"  name="score" value="2" required><span>2星</span>
                            <input type="radio"  name="score" value="3" required><span>3星</span>
                            <input type="radio"  name="score" value="4" required><span>4星</span>
                            <input type="radio"  name="score" value="5" required><span>5星</span>
                        </label>
                    </div>

                </form>

                <div class="order-price">&nbsp;&nbsp;&nbsp;${orders.getPrice()}</div>
            </div>


        </div>

    </div>
</c:forEach>









</body>
</html>
