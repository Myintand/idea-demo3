<%@ page import="java.text.DecimalFormat" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>店铺详情</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css ">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/product.css ">
</head>
<%--顶部--%>
<div class="indexnavigation" >
    <a href="${pageContext.request.contextPath}/StoreController/showstores" title="返回店铺列表">返回</a>
    <a href="${pageContext.request.contextPath}/UserController/topersonal" style="float: right">个人中心</a>
</div>

<body>

<%--店铺部分--%>
<%--头像--%>
<div style="text-align: center;margin: 10px 0">
    <img src="${nowstore.getPicture()}" style="width: 40%;max-width:400px;height: auto;margin:0 auto;border-radius: 50%;box-shadow: 0 4px 10px rgba(0, 0, 0, 0.4);">
</div>
<%--店铺信息--%>
<div class="module">
    <div class="text">
        <h2>${nowstore.getStorename()}</h2>
        <!-- 在 JSP 页面中获取 a 和 b 的值 -->
        <c:set var="a" value="${nowstore.getScore()+5}" />
        <c:set var="b" value="${nowstore.getNumber()+1}" />
        <%
            double a = Double.parseDouble(pageContext.getAttribute("a").toString());
            int b = Integer.parseInt(pageContext.getAttribute("b").toString());
            double result = a / b; // 除以

            DecimalFormat df = new DecimalFormat("#.0"); // 创建 DecimalFormat 对象
            request.setAttribute("ans",df.format(result));// 格式化结果为保留一位小数的字符串
        %>
        <label>${ans}&nbsp;分&nbsp;&nbsp;&nbsp;销量：${nowstore.getSalenumber()}</label>
        <p>起送：${nowstore.getBeginning()}￥</p>
        <p>${nowstore.getIntroduce()}</p>
        <p><a href="${pageContext.request.contextPath}/StoreController/toviewmappoint?returnjsp=storeIndex.jsp&location=${nowstore.getStorelocation()}&name=${nowstore.getStorename()}" style="float: right" class="locationax">店铺地址</a></p>
    </div>
</div>


<div style="text-align: center">
    <p><span style="color: red;font-weight: bold">${talkmessage}</span></p>
    <form action="${pageContext.request.contextPath}/TalkController/totalk" method="post">
        <p><input type="submit" class="button" value="联系商家"></p>
    </form>
</div>

<%--商品部分--%>
<div class="container">
    <div class="product-list">

        <c:forEach var="product" items="${productlist}">
        <div class="product">
            <div class="product-image">
                <img src="${product.getPicture()}">
            </div>
            <div style="flex: 1;">
                <div class="product-name">${product.getProductname()}</div>
                <div class="product-price">¥${product.getPrice()}</div>
                <p>${product.getIntroduce()}</p>
                <div class="quantity">
                    <button class="quantity-btn minus">-</button>
                    <input type="number" class="quantity-input" value="0" min="0" readonly>
                    <button class="quantity-btn plus">+</button>
                </div>
                <div class="subtotal" style="float: right;color: green"></div>
            </div>

        </div>
        </c:forEach>
    </div>

    <div style="height: 150px"></div>





<%--    底部固定的购物车--%>
    <div class="checkout">
        <p><span style="color: red;font-weight: bold">${message}</span></p>
        <div class="label">购物车</div><br>

<%--        隐藏的表单--%>
        <form  action="${pageContext.request.contextPath}/UserController/selectroll" method="POST">
            <input type="hidden" name="info" id="checkout-info-input">
            <input type="hidden" name="total" id="checkout-total-input">


            <div class="checkout-info">购物车为空</div>

                <div style="text-align: center">
                    <label class="note">备注信息</label>
                    <label>
                        <input type="text" class="note_input" placeholder="无" name="note">
                    </label>
                    <button class="checkout-btn">结算</button>
                    <div class="checkout-total"></div>
                </div>
        </form>
    </div>


</div>

<%--生产订单内容和计算价格--%>
<script src="${pageContext.request.contextPath}/js/button.js"></script>
<%--提交表单--%>
<script>
    const form = document.querySelector('#checkout-form')
    const infoInput = document.querySelector('#checkout-info-input')
    const totalInput = document.querySelector('#checkout-total-input')
    const button = document.querySelector('.checkout-btn')

    //给按钮添加监听事件，点击了就获取文本并提交表单
    button.addEventListener('click', () => {
        infoInput.value = getInfo()
        totalInput.value = getTotal()
        form.submit()
    })

    //获取前端div块内内容的函数
    function getInfo() {
        return document.querySelector('.checkout-info').textContent
    }

    function getTotal() {
        return document.querySelector('.checkout-total').textContent
    }
</script>
</body>
</html>