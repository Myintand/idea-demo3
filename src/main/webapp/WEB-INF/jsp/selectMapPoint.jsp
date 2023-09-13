<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>地图选点</title>
    <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/map.css">
    <script type="text/javascript">
        window._AMapSecurityConfig = {
            //安全密钥
            securityJsCode:'2aa3afd4cc177683f92fbe5727968bf5',
        }
    </script>
    <script src="https://webapi.amap.com/maps?v=1.4.15&key=a4fa35804dd64ef997e065ed14b0d9d8"></script>
</head>
<body>

<br><br><br><br>

<form action="${pageContext.request.contextPath}/StoreController/selectmappoint?returnjsp=${returnjsp}" method="post">
    <input placeholder="位置信息(点击地图自动获取)" id="position" class="position" name="storeselectlocation" required="required" readonly/>
    <p align="center"><span style="color: red;font-weight: bold">${message}</span></p>
    <p style="text-align:center">
        <button type="submit" class="submit">确 认</button>
        <button type="reset" class="submit">重 置</button>
        <br>
    </p>
</form>

<div id="container" class="container"></div>

<script>
    //搜索功能

    //加载地图
    var map = new AMap.Map('container', {
        zoom:13,//级别(3-18数字越大镜头越低)
        center: [118.04571,24.366591],//中心点坐标
    });

    // //地图搜索插件
    // map.plugin(["AMap.Autocomplete"], function() {
    //     var autoOptions = {
    //         input: "tipinput" //使用联想输入的input的id
    //     };
    //     autocomplete= new AMap.Autocomplete(autoOptions);
    //     //TODO: 使用autocomplete对象调用相关功能
    //     AMap.event.addListener(autocomplete, "select", function(e){
    //         //TODO 针对选中的poi实现自己的功能
    //         map.setCenter(e.poi.location);
    //     });
    // });


    // 为地图注册click事件获取鼠标点击出的经纬度坐标
    var marker;
    var clickEventListener = map.on('click', function(e) {
        document.getElementById('position').value = e.lnglat.getLng() + ',' + e.lnglat.getLat();//将获取的经纬度赋值给表单的输入框
        if (marker) { // 判断marker是否存在，存在则清除
            marker.setMap(null);
            marker = null;
        }
        marker = new AMap.Marker({ // 加载标记
            position: [e.lnglat.getLng(), e.lnglat.getLat()],
            map: map
        });
    });
</script>
</body>
</html>