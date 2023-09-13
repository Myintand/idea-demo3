<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>添加商品</title>
  <link rel="stylesheet" type="text/css" href="<c:url value="/css/addproduct.css "/>">
</head>
<body>
<form action="${pageContext.request.contextPath}/ProductController/add" method="post" enctype="multipart/form-data">
  <h1>添加商品</h1>
  <p><span style="color: red;font-weight: bold">${message}</span></p>
  <div>
    <label for="product-image">商品图片：</label>
    <div id="product-image">
      <img src="${pageContext.request.contextPath}/pictures/worng.png" alt="商品图片预览" id="preview">
      <input type="file" name="upfile" id="image-input" required>
    </div>
  </div>
  <div>
    <label >商品名称：</label>
    <input type="text" name="productname"  required>
  </div>
  <div>
    <label >商品价格：</label>
    <input type="number" name="price"  min="0" step="1" required>
  </div>
  <div>
    <label >商品介绍：</label>
    <textarea name="introduce"  required></textarea>
  </div>
  <div>
    <input type="submit" value="添加商品">
    <a href="${pageContext.request.contextPath}/ProductController/toaddproduct" title="返回商品列表">返回</a>
  </div>
</form>


<script type="text/javascript">
  // 商品图片预览
  var preview = document.getElementById('preview');
  var imageInput = document.getElementById('image-input');
  imageInput.addEventListener('change', function() {
    var file = this.files[0];
    if (file.type.indexOf('image') === -1) {
      alert('请上传图片文件！');
      return;
    }
    var reader = new FileReader();
    reader.onload = function() {
      preview.src = this.result;
    };
    reader.readAsDataURL(file);
  });
</script>
</body>
</html>