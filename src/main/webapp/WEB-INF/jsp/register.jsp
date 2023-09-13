<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>注册</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/loginandregister.css "/>">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/UserController/register" enctype="multipart/form-data" method="post">
        <input type="hidden" name="theMethod"  value="register">

        <h2 align="center" style="font-family:楷体;color:black">注册</h2>

        <div class="form-group">
            <input type="text" name="username" class="input" placeholder="用户名(必填)" required="required" value="${un}">
        </div>

        <div class="form-group">
            <input type="password" name="passwd" class="input" placeholder="密码(必填)" required="required" value="${pw}">
        </div>

        <div class="form-group">
            <input type="password" name="surePasswd" class="input" placeholder="再输一次密码(必填)" required="required" value="${spw}">
        </div>
        <p><span style="color: red;font-weight: bold">${error}</span></p>

        <div>
            <label class="label">
                性别(必填)：
                男<input type="radio" class="BoxInput" name="sex" value="男" required ${man}>
                女<input type="radio" class="BoxInput" name="sex" value="女" required ${woman}>
            </label>
        </div>

        <div>
            <label class="label">
                学历
                <select name="education">
                    <option value="大学">大学</option>
                    <option value="高中">高中</option>
                    <option value="初中">初中</option>
                    <option value="小学">小学</option>
                </select>
            </label>
        </div>

        <div>
            <label class="label">邮 箱：</label>
            <input type="text" name="email" class="input" placeholder="邮箱地址(必填)" value="${ea}">
        </div>

        <div>
            <label class="label">简 介：</label>
            <textarea class="input" cols="20" name="introduce" rows="3" placeholder="介绍自己">${i}</textarea>
        </div>

        <div>
            <label class="label">联系电话（将作为订单联系电话）：</label>
            <input type="text" name="tel" class="input" placeholder="联系电话(必填)" required value="${t}">
        </div>
        <div>
            <label class="label">地址（将作为订单配送地址）：</label>
            <input type="text" name="address" class="input" placeholder="地址(必填)" required value="${ad}">
        </div>

        <div>
            <label class="label">密保问题：</label>
            <input type="text" name="problem" class="input" placeholder="密保问题（必填）" required value="${p}">
        </div>
        <div>
            <label class="label">密保答案：</label>
            <input type="text" name="answer" class="input" placeholder="密保答案（必填）" required value="${a}">
        </div>



        <label class="label">上传头像</label>
        <br>
        <input type="file" name="uploadfile">

        <p><span style="color: red;font-weight: bold">${ToLargeFile}</span></p>



        <p style="text-align:center">
            <button type="submit" class="submit">注 册</button>


            <br>
            <a href="<c:url value="/UserController/tologin"/>" >已有账号，点击登录</a>
        </p>
    </form>


</div>

</body>
</html>
