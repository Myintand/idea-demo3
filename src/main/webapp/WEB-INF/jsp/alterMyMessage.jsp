<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>编辑个人资料</title>
    <link rel="stylesheet" type="text/css" href="<c:url value="/css/alter.css "/>">
</head>
<body class="signin">
<div class="signinpanel">

    <form action="${pageContext.request.contextPath}/UserController/altermymessage" method="post">

        <h2 align="center" style="font-family:楷体;color:black">编辑资料</h2>

        <div>
            <label class="label" style="font-size: 25px">${LoginUser.getUsername()}</label>
<%--            可以获取--%>
        </div>

        <div>
            <label class="label">旧密码：</label>
            <input type="password" name="ps" class="input" placeholder="旧密码验证" required="required">
        </div>
        <p><span style="color: red;font-weight: bold">${message}</span></p>
<%--        旧密码未验证，错误警告--%>

        <div>
            <input type="password" name="passwd" class="input" placeholder="新密码">
        </div>

        <div>
            <input type="password" name="surePasswd" class="input" placeholder="再输一次">
        </div>

        <p><span style="color: red;font-weight: bold">${error}</span></p>
<%--        两次密码不一致--%>

        <div>
            <label class="label">
                性别：
                男<input type="radio" class="BoxInput" name="sex" value="男" required>
                女<input type="radio" class="BoxInput" name="sex" value="女" required>
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
            <input type="text" name="email" class="input" placeholder="邮箱地址">
        </div>

        <div>
            <label class="label">简 介：</label>
            <textarea class="input" cols="20" name="introduce" rows="3" placeholder="介绍自己"></textarea>
        </div>

        <div>
            <label class="label">联系电话（将作为订单联系电话）</label>
            <input type="text" name="tel" class="input" placeholder="联系电话" required>
        </div>

        <div>
            <label class="label">地址（将作为订单配送地址）</label>
            <input type="text" name="address" class="input" placeholder="地址" required>
        </div>

        <div>
            <label class="label">密保问题（必填）：</label>
            <input type="text" name="problem" class="input" placeholder="密保问题" required>
        </div>
        <div>
            <label class="label">密保答案（必填）：</label>
            <input type="text" name="answer" class="input" placeholder="密保答案" required>
        </div>

        <p style="text-align:center">
            <button type="submit" class="submit">保 存</button>
            <button type="reset" class="submit">重 置</button>

            <br>
            <a href="<c:url value="/UserController/topersonal"/>" >返回</a>
        </p>
    </form>

    <div class="signup-footer">
        <div class="pull-left">
            &copy; 2023
        </div>
    </div>

</div>

</body>
</html>
