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

<c:if test="${msg != null}">
  <script type="text/javascript">
      window.alert("${msg}");
  </script>
</c:if>
</body>
</html>