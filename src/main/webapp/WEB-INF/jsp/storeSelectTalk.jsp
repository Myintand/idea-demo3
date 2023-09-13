<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>回复顾客</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/talk.css"/>
</head>
<%--顶部--%>
<div class="indexnavigation">
    <a href="${pageContext.request.contextPath}/UserController/topersonal" title="返回个人中心">返回</a>
    <a href="${pageContext.request.contextPath}/UserController/loginout" style="float: right">退出登录</a>
</div>

<div style="text-align: center">
    <h1>回复顾客</h1>
</div>

<body>

<%--选择回复部分--%>
<c:forEach var="talkuser" items="${talkusers}">
    <!-- 无图片的消息 -->
    <div class="module">
        <div class="text">
            <img src="${talkuser.getHeadpicture()}" style="float: left;border-radius:50%;box-shadow: 0 0 5px rgba(0,0,0,.3);">
            <p style="font-size: 25px;float: left;padding: 0 10px">用户名： ${talkuser.getUsername()}</p>

            <br><br><br><br><br>
            <form action="${pageContext.request.contextPath}/TalkController/totalkbuyer"  style="text-decoration:none;float: right">
                <input type="hidden" name="talkusername"  value="${talkuser.getUsername()}">
                <input type="hidden" name="talkpicture"  value="${talkuser.getHeadpicture()}">
                <button type="submit" class="add-to-cart" >回复</button>
            </form>
        </div>
    </div>
</c:forEach>

</body>
</html>