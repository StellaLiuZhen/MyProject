<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
  <title>商品管理</title>
  <meta name="decorator" content="default"/>
</head>

<body>
<div id="mainDiv">

</div>

<c:if test="${member.mid != null}">
  <h1>${member.mid} 您好，感谢登录使用！</h1>
  <a href="frontList">商品列表</a><br>
  <a href=" ">购物车</a><br>
  <a href=" ">个人中心</a><br>
  <a href="logout">安全退出</a><br><br>
</c:if>

</body>
</html>