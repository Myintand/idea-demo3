<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加店铺优惠卷</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/addroll.css ">
</head>
<body class="signin">
<div class="signinpanel">

    <!-- 满减卷表单内容 -->
    <form action="${pageContext.request.contextPath}/RollController/addoneroll" method="post" id="form1" style="display: block;">
        <h2 align="center" style="font-family:楷体;color:black">添加店铺满减优惠卷</h2>

        <%--        获取商家用户名和商家名称作为优惠卷可使用商家--%>
        <input type="hidden" name="rollstoreusernanme" value="${LoginStore.getUsername()}">
        <input type="hidden" name="rollstoreanme" value="${LoginStore.getStorename()}">

<%--        必须要填写的--%>
        <div>
            <input type="text" name="number1" class="input" placeholder="编辑优惠卷兑换码" required="required">
        </div>
        <div>
            <input type="number" name="reduce" class="input" placeholder="优惠金额(整数)" required="required" step="1">
        </div>
        <div>
            <input type="number" name="condition" class="input" placeholder="优惠卷使用条件(订单金额满足该数额才能使用(整数))" required="required" step="1">
        </div>

<%--        可选择填写的--%>
        <%--发放数量--%>
        <input type="checkbox" id="isAllUsable1" name="isAllUsable" onchange="setRollNumber1()">是否发放多张（默认只发一张）
        <div id="setRollNumberHiddenInput1" style="display: none;">
            发放数量：<input type="number" class="input" name="sendNumber" placeholder="优惠卷的发放数量" min="1" step="1" value="1">
        </div>
        <br>
        <%--可兑换时间--%>
        <input type="checkbox" id="isSetBeginAndEndTime1" name="isSetBeginAndEndTime" onchange="setBeginAndEndTime1()">开始/结束时间（默认不限制,可以只填一项）
        <div id="beginAndEndTimeInput1" style="display: none;">
            开始时间：<input type="datetime-local" class="input" name="beginTime" placeholder="开始兑换时间">
            结束时间：<input type="datetime-local" class="input" name="endTime" placeholder="结束兑换时间">
        </div>

<%--        默认的数值--%>
        <%--优惠比率--%>
        <div>
            <input type="hidden" name="rate" class="input" value="1.00">
        </div>

<%--        添加按钮--%>
        <p><span style="color: red;font-weight: bold">${message}</span></p>
        <p style="text-align:center"><button type="submit" class="submit">添加</button></p>
    </form>





    <!-- 折扣卷表单内容 -->
    <form action="${pageContext.request.contextPath}/RollController/addoneroll" method="post" id="form2" style="display: none;">
        <h2 align="center" style="font-family:楷体;color:black">添加店铺折扣优惠卷</h2>

        <%--        获取商家用户名和商家名称作为优惠卷可使用商家--%>
        <input type="hidden" name="rollstoreusernanme" value="${LoginStore.getUsername()}">
        <input type="hidden" name="rollstoreanme" value="${LoginStore.getStorename()}">

<%--        要填写的--%>
        <div>
            <input type="text" name="number1" class="input" placeholder="编辑优惠卷兑换码" required="required">
        </div>
        <div>
            <input type="number" name="rate" class="input" placeholder="优惠比率(折扣比率0.10-1.00)" required="required" step="0.01" max="1.00" min="0.10">
        </div>
        <div>
            <input type="number" name="condition" class="input" placeholder="优惠卷使用条件(订单金额满足该数额才能使用)" required="required" step="1">
        </div>

<%--        可选择填写的--%>
        <%--发放数量--%>
        <input type="checkbox" id="isAllUsable2" name="isAllUsable" onchange="setRollNumber2()">是否发放多张（默认只发一张）
        <div id="setRollNumberHiddenInput2" style="display: none;">
            发放数量：<input type="number" class="input" name="sendNumber" placeholder="优惠卷的发放数量" min="1" step="1" value="1">
        </div>
        <br>
        <%--可兑换时间--%>
        <input type="checkbox" id="isSetBeginAndEndTime2" name="isSetBeginAndEndTime" onchange="setBeginAndEndTime2()">开始/结束时间（默认不限制,可以只填一项）
        <div id="beginAndEndTimeInput2" style="display: none;">
            开始时间：<input type="datetime-local" class="input" name="beginTime" placeholder="开始兑换时间">
            结束时间：<input type="datetime-local" class="input" name="endTime" placeholder="结束兑换时间">
        </div>

<%--        默认的数值--%>
        <%--优惠金额--%>
        <div>
            <input type="hidden" name="reduce" class="input" value="0">
        </div>

<%--        添加按钮--%>
        <p><span style="color: red;font-weight: bold">${message}</span></p>
        <p style="text-align:center"><button type="submit" class="submit">添加</button></p>
    </form>

    <button id="fullcouponBtn" onclick="changeForm(this)" class="rollbutton">满减卷</button>
    <button id="generalcouponBtn" onclick="changeForm(this)" class="rollbutton">折扣卷</button>
    <p align="center"><a href="${pageContext.request.contextPath}/UserController/topersonal" class="ax">返回</a></p>


</div>

<script>
// 满减卷和折扣卷的切换逻辑
    function changeForm(button) {
        var form1 = document.getElementById("form1");
        var form2 = document.getElementById("form2");
        var fullcouponBtn = document.getElementById("fullcouponBtn");
        var generalcouponBtn = document.getElementById("generalcouponBtn");

        if (button.id === "fullcouponBtn" && form2.style.display === "block") {
            // 如果当前显示的是通用卷表单，并且点击的是满减卷按钮
            form1.style.display = "block";
            form2.style.display = "none";

            fullcouponBtn.classList.add("active");
            generalcouponBtn.classList.remove("active");
        } else if (button.id === "generalcouponBtn" && form1.style.display === "block") {
            // 如果当前显示的是满减卷表单，并且点击的是通用卷按钮
            form1.style.display = "none";
            form2.style.display = "block";

            fullcouponBtn.classList.remove("active");
            generalcouponBtn.classList.add("active");
        }
    }

//是否发放多张的显示逻辑
    //满减卷是否发放多张
    function setRollNumber1() {
        var checkbox = document.getElementById("isAllUsable1");
        var hiddenInput = document.getElementById("setRollNumberHiddenInput1");
        var sendNumberInput = hiddenInput.querySelector('input[name="sendNumber"]');

        if (checkbox.checked) {
            hiddenInput.style.display = "block";
            sendNumberInput.required = true;
        } else {
            hiddenInput.style.display = "none";
            sendNumberInput.required = false;
        }
    }
    //折扣卷是否发放多张
    function setRollNumber2() {
        var checkbox = document.getElementById("isAllUsable2");
        var hiddenInput = document.getElementById("setRollNumberHiddenInput2");
        var sendNumberInput = hiddenInput.querySelector('input[name="sendNumber"]');

        if (checkbox.checked) {
            hiddenInput.style.display = "block";
            sendNumberInput.required = true;
        } else {
            hiddenInput.style.display = "none";
            sendNumberInput.required = false;
        }
    }


//是否设置开始结束时间的显示逻辑
    //满减卷是否设置时间
    function setBeginAndEndTime1() {
        var checkbox = document.getElementById("isSetBeginAndEndTime1");
        var hiddenInput = document.getElementById("beginAndEndTimeInput1");

        if (checkbox.checked) {
            hiddenInput.style.display = "block";
        } else {
            hiddenInput.style.display = "none";
        }
    }
    //折扣卷是否设置时间
    function setBeginAndEndTime2() {
         var checkbox = document.getElementById("isSetBeginAndEndTime2");
         var hiddenInput = document.getElementById("beginAndEndTimeInput2");

        if (checkbox.checked) {
           hiddenInput.style.display = "block";
        } else {
           hiddenInput.style.display = "none";
        }
}


</script>


</body>
</html>
