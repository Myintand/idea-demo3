<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
  <meta charset="UTF-8">
  <title>注册店铺</title>
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/personal.css">
</head>
<body class="signin">
<div class="signinpanel">

  <form action="${pageContext.request.contextPath}/StoreController/addstore" enctype="multipart/form-data" method="post" id="form1">

    <h2 align="center" style="font-family:楷体;color:black">注册店铺资料</h2>

    <div>
      <label class="label" style="font-size: 25px">${LoginUser.getUsername()}</label>
      <%--            可以获取--%>
    </div>

    <div>
      <label class="label">店铺地址：</label>
      <input type="text" name="storelocation" class="input" placeholder="请通过地图选点选取" value="${sl}" readonly>
      <a href="${pageContext.request.contextPath}/StoreController/toselectmappoint?returnjsp=becomeStore.jsp" >地图选点</a>
      <span style="color: red;font-weight: bold">${success}</span>
    </div>

    <div>
      <label class="label">店铺名：</label>
      <input type="text" name="storename" class="input" value="${sn}" placeholder="店铺名">
    </div>

    <div>
      <label class="label">起&nbsp;&nbsp;送：</label>
      <input type="number" name="beginning" class="input" value="${bn}" step="1" min="0" placeholder="起送">
    </div>

    <div>
      <label class="label">简&nbsp;&nbsp;介：</label>
      <textarea class="input" cols="20" name="introduce" rows="3" placeholder="介绍自己">${i}</textarea>
    </div>

    <label class="label">店铺头像</label>
    <br>
    <input type="file" name="uploadfile" required>

    <p><span style="color: red;font-weight: bold">${ToLargeFile}</span></p>

    <p style="text-align:center">
      <button type="submit" class="submit">注 册</button>
      <button type="reset" class="submit">重 置</button>

      <br>
      <a href="${pageContext.request.contextPath}/UserController/topersonal" >返回个人中心</a>
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
