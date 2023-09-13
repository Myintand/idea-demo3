<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>路线规划</title>
  <meta name="viewport" content="initial-scale=1.0, user-scalable=no">
  <meta charset="UTF-8">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/map.css">
  <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/module.css">
  <script type="text/javascript">
    window._AMapSecurityConfig = {
      //安全密钥
      securityJsCode:'2aa3afd4cc177683f92fbe5727968bf5',
    }
  </script>
  <script type="text/javascript" src="https://webapi.amap.com/maps?v=1.4.15&key=a4fa35804dd64ef997e065ed14b0d9d8&plugin=AMap.Driving"></script>
</head>

<%--顶部--%>
<div class="indexnavigation"  style="margin: 10px auto;width: 80%;padding: 10px 0">
  <a href="${pageContext.request.contextPath}/CommonController/returnjsp?returnjsp=${returnjsp}" title="返回" style="padding: 0 10px">返回</a>
</div>
<%--地图绘制--%>
<div id="container" class="container"></div>
<%--路线信息--%>
<div id="panel" style="width: 80%;margin: 5px auto"></div>

</body>

<script type="text/javascript">
  const map = new AMap.Map('container', {
    zoom:11,  //初始化地图层级
  });

  const startLngLat = [${beginmaplocation}]
  const endLngLat = [${endmaplocation}]

  const driving = new AMap.Driving({
    map: map,
    panel: "panel"
  });

  driving.search(startLngLat, endLngLat, function (status, result) {
    // 未出错时，result即是对应的路线规划方案
    if (status === 'complete') {
      log.success('绘制驾车路线完成')
    } else {
      log.error('获取驾车数据失败：' + result)
    }
  })
</script>
</html>
