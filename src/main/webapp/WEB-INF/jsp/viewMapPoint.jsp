<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看地图</title>
    <script src="https://webapi.amap.com/maps?v=1.4.15&key=a4fa35804dd64ef997e065ed14b0d9d8"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/map.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css ">
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            //安全密钥
            securityJsCode:'2aa3afd4cc177683f92fbe5727968bf5',
        }
    </script>
</head>

<body>
<%--每次进入该界面必须设置session：
    查看的点的坐标信息：nowviewlocation
    该点标题：theviewlocationmname
    回调地址：returnjsp--%>
<%--顶部--%>
<div class="indexnavigation"  style="margin: 10px auto;width: 80%;padding: 10px 0">
    <a href="${pageContext.request.contextPath}/CommonController/returnjsp?returnjsp=${thereturnjsp}" title="返回" style="padding: 0 10px">返回</a>
</div>
<form action="${pageContext.request.contextPath}/StoreController/viewmappath?returnjsp=viewMapPoint.jsp" method="post">
<%--    起点位置--%>
    <h2 style="text-align: center">我的位置</h2>
    <input type="text" placeholder="起点位置信息(点击地图自动获取)" id="position" class="position" name="beginmaplocation" required="required" value="${sl}" readonly/>
    <p style="text-align:center;font-weight: bold;font-size: 18px"><a href="${pageContext.request.contextPath}/StoreController/toselectmappoint?returnjsp=viewMapPoint.jsp">地图选点</a></p>
<%--    终点位置--%>
    <h2 style="text-align: center">目标位置（如下图所示）</h2>
    <input type="text" class="position" name="endmaplocation" value="${nowviewlocation}" readonly/>
<%--    提示信息--%>
    <p align="center"><span style="color: red;font-weight: bold">${message}</span></p>
<%--    查看路线按钮--%>
    <p style="text-align:center">
        <button type="submit" class="submit">查看路线</button>
    </p>
<%--    地图显示--%>
    <div id="container" class="container"></div>

</form>
</body>

<script type="text/javascript">
    var map = new AMap.Map("container", {
        zoom:18,//级别(3-18数字越大镜头越低)
        center: [${nowviewlocation}],//中心点坐标
    });
    var marker;
    marker = new AMap.Marker({ // 加载标记
        position: [${nowviewlocation}],
        map: map,
        title: "${theviewlocationmname}"
    });

</script>
</html>
