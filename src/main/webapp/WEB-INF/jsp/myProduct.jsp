<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>店铺详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css ">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css ">
    <%--    <script src="<c:url value="/js/button.js"/>"></script>--%>
</head>
<%--顶部--%>
<div class="indexnavigation" >
    <a href="${pageContext.request.contextPath}/UserController/topersonal" title="返回个人中心">返回个人中心</a>
</div>
<div style="text-align: center">
    <h1>我的商品</h1>
</div>
<body>
<p><span style="color: red;font-weight: bold">${message}</span></p>
<%--商品部分--%>
<c:forEach var="product" items="${myproductlist}">
    <div class="product">

        <div class="product-image">
            <img src="${product.getPicture()}">
        </div>
        <div>
            <form action="${pageContext.request.contextPath}/ProductController/alterproduct" enctype="multipart/form-data" method="post">
                <div class="product-info">

                        <%--                可编辑的内容--%>
                    <h2 class="product-name">${product.getProductname()}
                        <input type="hidden" name="lastproductname" value="${product.getProductname()}">
                        <input type="text" name="productname" class="input-box" placeholder="商品名" required="required">
                    </h2>
                    <p class="product-price">${product.getPrice()}¥
                        <input type="number" name="price" class="input-box" placeholder="价格" min="0" required="required">
                    </p>
                    <p>${product.getIntroduce()}
                        <input type="text" name="introduce" class="input-box" placeholder="简介" required="required">
                    </p>
                    <div class="product-quantity" style="float:left;margin: 0 10px">
                        <button type="submit" class="add-to-cart">修改商品信息</button>
                    </div>
                    <a href="${pageContext.request.contextPath}/ProductController/deleteproduct?username=${product.getUsername()}&productname=${product.getProductname()}" style="text-decoration:none;">
                        <div class="product-quantity" style="float:left">
                            <label class="add-to-cart">删除商品</label>
                        </div>
                    </a>
                </div>
            </form>
        </div>

    </div>
</c:forEach>

<a href="${pageContext.request.contextPath}/ProductController/toaddproduct1" style="text-decoration:none;">
    <div class="button">
        添加商品
    </div>
</a>



</body>
</html>
