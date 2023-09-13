<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>首页</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/table.css ">
</head>

<%--顶部--%>
<div class="indexnavigation" >
    <a href="${pageContext.request.contextPath}/UserController/tologin" title="前往登录/注册">登录/注册</a>
    <a href="${pageContext.request.contextPath}/UserController/topersonal" style="float: right">个人中心</a>
</div>

<h1 style="margin: 40% auto;color: black">欢迎使用嘉庚学院外卖系统!</h1>
<body class="signin">
<%--获取所有商家--%>
<form action="${pageContext.request.contextPath}/StoreController/showstores" method="post">
    <p  style="text-align: center"><input type="submit" class="index_submit"  value="进去逛逛"></p>
</form>


<%--<table>--%>
<%--    <tr>--%>
<%--        <th>列1</th>--%>
<%--        <th>列2</th>--%>
<%--        <th>列3</th>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <td>这是一段很长很长的文本，超出了单元格宽度，需要自动换行。</td>--%>
<%--        <td>这是一段很长很长的文本，超出了单元格宽度，需要自动换行。</td>--%>
<%--        <td>这是一段很长很长的文本，超出了单元格宽度，需要自动换行。</td>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <td>这是一段较短的文本。</td>--%>
<%--        <td>这是一段较短的文本。</td>--%>
<%--        <td>这是一段较短的文本。</td>--%>
<%--    </tr>--%>
<%--    <tr>--%>
<%--        <td>这是一段稍微长一点的文本，但不到单元格宽度。</td>--%>
<%--        <td>这是一段稍微长一点的文本，但不到单元格宽度。</td>--%>
<%--        <td>这是一段稍微长一点的文本，但不到单元格宽度。</td>--%>
<%--    </tr>--%>
<%--</table>--%>

<%--<hr color="black">--%>


<%--${LoginUser.getUsername()}--%>
<%--${LoginUser}--%>
<%--&lt;%&ndash;填表达式可以获取值&ndash;%&gt;--%>
<%--<hr color="black">--%>
<%--<table>--%>
<%--    <tr style="font-weight:bold;font-size: 30px;font-family: 楷体 ">--%>
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