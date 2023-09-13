<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css ">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/find.css ">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css ">
    <script>
        function displayTime() {
            var date = new Date();
            var year = date.getFullYear();
            var month = date.getMonth() + 1; // 注意：月份是从0开始的，所以要 +1
            var day = date.getDate();
            var hours = date.getHours();
            var minutes = date.getMinutes();
            var seconds = date.getSeconds();
            // 如果分钟或秒小于10，前面补一个'0'
            month = month < 10 ? '0' + month : month;
            day = day < 10 ? '0' + day : day;
            hours = hours < 10 ? '0' + hours : hours;
            minutes = minutes < 10 ? '0' + minutes : minutes;
            seconds = seconds < 10 ? '0' + seconds : seconds;
            var timeStr = year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
            document.getElementById("SYSTEMTIME").innerHTML = timeStr;
        }
        // 每隔1000毫秒（即1秒）调用一次 displayTime 函数
        setInterval(displayTime, 1000);
    </script>
</head>

<%--顶部--%>
<div class="indexnavigation"  style="margin: 10px 0;">
    <a href="${pageContext.request.contextPath}/UserController/tologin" title="前往登录/注册">登录/注册</a>
    <a href="${pageContext.request.contextPath}/UserController/tomyorders" title="我的订单" style="float: right">订单</a>
    <a href="${pageContext.request.contextPath}/MenuController/tomenu" title="菜谱" style="float: right">菜谱</a>
    <a href="${pageContext.request.contextPath}/UserController/topersonal" style="float: right">个人中心</a>
</div>

<form action="${pageContext.request.contextPath}/StoreController/findStorelist" method="post">
    <div class="search-box">
        <input type="text" placeholder="请输入关键字" name="keyword">
        <button type="submit">搜索</button>
    </div>
</form>


<body>
<div class="systemtime">
    <span>当前系统时间：</span>
    <span id="SYSTEMTIME"></span>
</div>
<br>
<c:forEach var="store" items="${storelist}">
    <div class="module">
        <img src="${store.getPicture()}">
        <div class="text">
            <h2>${store.getStorename()}</h2>
            <!-- 在 JSP 页面中获取 a 和 b 的值 -->
            <c:set var="a" value="${store.getScore()+5}" />
            <c:set var="b" value="${store.getNumber()+1}" />
            <%
                double a = Double.parseDouble(pageContext.getAttribute("a").toString());
                int b = Integer.parseInt(pageContext.getAttribute("b").toString());
                double result = a / b; // 除以

                DecimalFormat df = new DecimalFormat("#.0"); // 创建 DecimalFormat 对象
                request.setAttribute("ans",df.format(result));// 格式化结果为保留一位小数的字符串
            %>
            <label>${ans}&nbsp;分&nbsp;&nbsp;&nbsp;销量：${store.getSalenumber()}</label>
            <p>起送：￥${store.getBeginning()}</p>
            <p>${store.getIntroduce()}</p>
            <a href="${pageContext.request.contextPath}/StoreController/tostoreindex?username=${store.getUsername()}" class="btn">查看详情</a>
        </div>
    </div>
</c:forEach>

<%--&lt;%&ndash;填表达式可以获取值&ndash;%&gt;--%>
<%--<hr color="black">--%>
<%--<table>--%>
<%--    <tr>--%>
<%--        <th>头像图片</th>--%>
<%--        <th>用户名</th>--%>
<%--        <th>密码</th>--%>
<%--        <th>性别</th>--%>
<%--        <th>学历</th>--%>
<%--        <th>邮箱</th>--%>
<%--        <th>简介</th>--%>
<%--        <th>密保问题</th>--%>
<%--        <th>密保答案</th>--%>
<%--    </tr>--%>

<%--    <c:forEach var="user" items="${userlist}">--%>
<%--        <tr>--%>
<%--            <td><img width="50px" height="50px" src="<c:url value="/Upload/${user.getHeadpicture()}"/>" ></td>--%>
<%--            <td>${user.getUsername()}</td>--%>
<%--            <td>${user.getPassword()}</td>--%>
<%--            <td>${user.getSex()}</td>--%>
<%--            <td>${user.getEducation()}</td>--%>
<%--            <td>${user.getEmail()}</td>--%>
<%--            <td>${user.getIntroduce()}</td>--%>
<%--            <td>${user.getProblem()}</td>--%>
<%--            <td>${user.getAnswer()}</td>--%>
<%--        </tr>--%>
<%--    </c:forEach>--%>
<%--</table>--%>

<%--<hr color="black">--%>


</body>
</html>