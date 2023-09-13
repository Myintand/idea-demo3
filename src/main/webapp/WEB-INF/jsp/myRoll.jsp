<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>我的优惠卷</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/roll.css"/>
</head>

<%--    顶部--%>
<div class="indexnavigation">
    <a href="${pageContext.request.contextPath}/UserController/topersonal" title="返回个人中心">返回</a>
    <a href="${pageContext.request.contextPath}/UserController/loginout" style="float: right">退出登录</a>
</div>
<div style="text-align: center">
    <h1>我的优惠卷</h1>
</div>
<body>

<c:forEach var="roll" items="${myrolllist}">
    <div class="module">
        <img src="https://zqy-287189.oss-cn-beijing.aliyuncs.com/roll.jpg">
        <div class="text">
            <h2 style="color: red">${roll.getIntroduce()}</h2>

            可使用商家：
            <label>${roll.getStorename()}</label>
            <br>

            有效期：
            <label>${roll.getBegintime()} — ${roll.getEndtime()}</label>
            <br>

            <a href="${pageContext.request.contextPath}/StoreController/tostoreindex?username=${roll.getStoreusername()}" class="btn">去使用</a>
        </div>
    </div>
</c:forEach>

<c:forEach var="invalidroll" items="${myallinvalidrolllist}">
<%--    设置透明度和灰度为百分之五十--%>
    <div class="module" style="opacity: 0.5; filter: grayscale(50%);">
        <img src="https://zqy-287189.oss-cn-beijing.aliyuncs.com/roll.jpg">
        <div class="text">
            <h2 style="color: red">${invalidroll.getIntroduce()}</h2>

            可使用商家：
            <label>${invalidroll.getStorename()}</label>
            <br>

            有效期：
            <label>${invalidroll.getBegintime()} — ${invalidroll.getEndtime()}</label>
            <br>

            <label style="color: darkred;float: right">不在有效期内</label>
        </div>
    </div>
</c:forEach>

</body>
</html>
