<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <title>联系商家</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/talk.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/upfilebutton.css"/>
    <SCRIPT>
        // 当页面内容加载完成时执行
        window.addEventListener('DOMContentLoaded', function()
        {
            // 获取.me-container元素
            var container = document.querySelector('.me-container');

            // 将滚动条位置设置为最底部
            container.scrollTop = container.scrollHeight;
        });
    </SCRIPT>
</head>

<%--顶部--%>
<div class="indexnavigation">
    <a href="${pageContext.request.contextPath}/StoreController/tostoreindex?username=${nowstore.getUsername()}" title="返回商品详情页面">返回</a>
    <a href="${pageContext.request.contextPath}/UserController/loginout" style="float: right">退出登录</a>
</div>
<body>

<div  class="me-container" style="padding: 10px 10px;   box-shadow: 0 2px 10px rgba(0, 0, 0, 0.3);">

    <c:forEach var="talk" items="${myalltalklist}">
        <c:choose>
            <c:when test="${talk.getUsername() eq LoginUser.getUsername()}">
                <%--            我发送的消息--%>
                <c:choose>
                    <c:when test="${talk.getFlag() eq 0}">
                        <!-- 无图片的消息 -->
                        <%System.out.println("用户无图片消息");%>
                        <div class="module">

                            <div class="text">
                            <img src="${LoginUser.getHeadpicture()}" style="float: right;border-radius:50%;box-shadow: 0 0 5px rgba(0,0,0,.3);">
                            <p style="float: right;font-size: 20px">${talk.getTalking()}</p>
                            </div>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- 有图片的消息 -->
                        <%System.out.println("用户有图片消息");%>
                        <div class="module">

                            <div class="text">
                            <img src="${LoginUser.getHeadpicture()}" style="float: right;border-radius:50%;box-shadow: 0 0 5px rgba(0,0,0,.3);">
                            <p style="float: right;font-size: 20px">${talk.getTalking()}</p>
                            <img src="${talk.getPicture()}" style="float: right">
                            </div>

                        </div>
                    </c:otherwise>
                </c:choose>
            </c:when>
            <c:otherwise>
                <%--            商家发送的消息--%>
                <c:choose>
                    <c:when test="${talk.getFlag() eq 0}">
                        <!-- 无图片的消息 -->
                        <div class="module">
                            <div class="text">
                                <img src="${nowstore.getPicture()}" style="float: left;border-radius:50%;box-shadow: 0 0 5px rgba(0,0,0,.3);">
                                <p style="font-size: 20px;float: left">${talk.getTalking()}</p>
                            </div>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <!-- 有图片的消息 -->
                        <div class="module">
                            <div class="text">
                                <img src="${nowstore.getPicture()}" style="float: left;border-radius:50%;box-shadow: 0 0 5px rgba(0,0,0,.3);">
                                <img src="${talk.getPicture()}">
                                <p style="font-size: 20px;float: left">${talk.getTalking()}</p>
                            </div>
                        </div>
                    </c:otherwise>
                </c:choose>

            </c:otherwise>
        </c:choose>
    </c:forEach>


</div>



<%--底部栏目--%>
<div class="checkout">

    <%--        隐藏的表单--%>
    <form  action="${pageContext.request.contextPath}/TalkController/addonetalk" enctype="multipart/form-data" method="POST">


        <div style="text-align: center">
            <label>
                <input type="text" class="sendmessage" placeholder="无" name="sendmessage">
                <label for="file-input" class="upload-btn">选择文件</label>
                <input type="file" name="uploadfile" id="file-input">
            </label>
            <button type="submit" class="button30">发送</button>
            <span style="color: red;font-weight: bold;padding: 0 5px">${message}</span>
        </div>
    </form>
</div>

</body>
</html>
