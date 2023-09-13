<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>选择可用的优惠卷</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/roll.css"/>
</head>
<div style="text-align: center">
    <h1>请选择使用优惠卷</h1>
</div>

<div style="color: dodgerblue;font-size: 18px;font-weight: bold">
    订单原价：${firstorders.getPrice()}
</div>

<body>
<%--    可使用的优惠卷--%>
<c:forEach var="useableroll" items="${myuserablerolllist}">
    <div class="module">
        <img src="https://zqy-287189.oss-cn-beijing.aliyuncs.com/roll.jpg">
        <div class="text">

            <h2 style="color: red">${useableroll.getIntroduce()}</h2>

            <label style="color: #05af05">可使用商家：</label>
            <label>${useableroll.getStorename()}</label>

            <br>

            <!-- 在 JSP 页面中获取 a 和 b 的值 -->
            <c:set var="c" value="${useableroll.getRate()}" />
            <c:set var="d" value="${useableroll.getReduce()}" />
            <c:set var="e" value="${firstorders.getPrice()}" />
            <%
                double c = Double.parseDouble(pageContext.getAttribute("c").toString());
                double d = Double.parseDouble(pageContext.getAttribute("d").toString());
                double e = Double.parseDouble(pageContext.getAttribute("e").toString());
                double result = e * c - d;

                DecimalFormat df = new DecimalFormat("#.00"); // 创建 DecimalFormat 对象
                request.setAttribute("ans1",df.format(result));// 格式化结果为保留一位小数的字符串
            %>

            <label style="color: #05af05">优惠后价格：</label>
            <label>${ans1}</label>
            <br>
            <label style="color: #05af05">有效期：</label>
            <label>${useableroll.getBegintime()} — ${useableroll.getEndtime()}</label>

            <form action="${pageContext.request.contextPath}/UserController/addorders?newprice=${ans1}" style="float: right" enctype="multipart/form-data" method="post">
                <input type="hidden" name="rollnumber" value="${useableroll.getNumber()}">

                <button type="submit" class="button" >
                    使用此优惠卷并下单￥${ans1}
                </button>
            </form>

        </div>
    </div>
</c:forEach>
<%--    无法使用的优惠卷--%>
<c:forEach var="invalidroll" items="${myInvalidRollList}">
<%--    设置透明度和灰度为百分之五十--%>
    <div class="module" style="opacity: 0.5; filter: grayscale(50%);">
        <img src="https://zqy-287189.oss-cn-beijing.aliyuncs.com/roll.jpg">
        <div class="text">

            <h2 style="color: red">${invalidroll.getIntroduce()}</h2>

            <label style="color: #05af05">可使用商家：</label>
            <label>${invalidroll.getStorename()}</label>
            <br>
            <label style="color: darkred">有效期：</label>
            <label style="color: darkred">${invalidroll.getBegintime()} —— ${invalidroll.getEndtime()}</label>
            <label style="color: darkred;float: right">不在有效期内</label>
        </div>
    </div>
</c:forEach>

<form action="${pageContext.request.contextPath}/UserController/addorders?newprice=${firstorders.getPrice()}" enctype="multipart/form-data" method="post">

    <button type="submit" class="button" >
        不使用优惠卷，直接下单￥${firstorders.getPrice()}
    </button>
</form>

<form action="${pageContext.request.contextPath}/StoreController/tostoreindex?username=${firstorders.getStoreusername()}" enctype="multipart/form-data" method="post">

    <button type="submit" class="button" >
        返回
    </button>
</form>

</body>
</html>
