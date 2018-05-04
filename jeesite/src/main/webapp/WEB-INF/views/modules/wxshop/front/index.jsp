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

<c:if test="${mid != null}">
  <br><br>
  ${mid} 您好，感谢登录使用！  <a href="showMember">个人中心</a>     <a href="logout">安全退出</a><br><br>
  <a href="frontList">商品列表</a>  <a href="shopcarList">购物车</a>  <a href="ordersList">全部订单</a><br><br><br>
</c:if>

</body>
</html>