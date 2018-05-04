<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>用户管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<div>
    <c:if test="${mid != null}">
        <br><br>
        ${mid} 您好，感谢登录使用！  <a href="showMember">个人中心</a>     <a href="logout">安全退出</a><br><br>
        <a href="frontList">商品列表</a>  <a href="shopcarList">购物车</a>  <a href="ordersList">全部订单</a><br><br><br>
    </c:if>
</div>


<form:form id="inputForm" modelAttribute="member" action="updateMember" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label">手机:</label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">地址:</label>
        <div class="controls">
            <form:input path="address" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>