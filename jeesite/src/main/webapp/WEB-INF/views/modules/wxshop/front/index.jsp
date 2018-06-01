<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@include file="/WEB-INF/views/include/head.jsp" %>
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
  <font size="7">${mid}</font><br><br><br>
  <font size="7">您好，感谢登录使用！</font> <br><br><br>
  <a href="frontList"><font size="7">商品列表</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="shopcarList"><font size="7">购物车</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="ordersList"><font size="7">全部订单</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="showMember"><font size="7">个人中心</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
  <a href="logout"><font size="5">安全退出</font></a><br><br><br>
</c:if>

</body>
</html>