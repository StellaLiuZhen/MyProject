<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
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
        <font size="7">${mid}</font> <font size="7">您好，感谢登录使用！</font> <br><br><br>
        <a href="frontList"><font size="7">商品列表</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="shopcarList"><font size="7">购物车</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="ordersList"><font size="7">全部订单</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="showMember"><font size="7">个人中心</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="logout"><font size="5">安全退出</font></a><br><br><br>
    </c:if>
</div>


<form:form id="inputForm" modelAttribute="member" action="updateMember" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <div class="control-group">
        <label class="control-label"><font size="6" >收件人:</font> </label>
        <div class="controls">
            <form:input path="name" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <br><br><br>
    <div class="control-group">
        <label class="control-label"><font size="6" >手机:</font> </label>
        <div class="controls">
            <form:input path="phone" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <br><br><br>
    <div class="control-group">
        <label class="control-label"><font size="6" > 地址:</font></label>
        <div class="controls">
            <form:input path="address" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <br><br><br>
    <div class="form-actions">
        <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>