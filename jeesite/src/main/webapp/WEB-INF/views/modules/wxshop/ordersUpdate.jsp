<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>修改订单信息</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#no").focus();
            $("#inputForm").validate({
                rules: {
                    loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
                },
                messages: {
                    loginName: {remote: "用户登录名已存在"},
                    confirmNewPassword: {equalTo: "输入与上面相同的密码"}
                },
                submitHandler: function (form) {
                    loading('正在提交，请稍等...');
                    form.submit();
                },
                errorContainer: "#messageBox",
                errorPlacement: function (error, element) {
                    $("#messageBox").text("输入有误，请先更正。");
                    if (element.is(":checkbox") || element.is(":radio") || element.parent().is(".input-append")) {
                        error.appendTo(element.parent().parent());
                    } else {
                        error.insertAfter(element);
                    }
                }
            });
        });
    </script>
</head>
<body>
<form:form id="inputForm" modelAttribute="orders" action="${ctx}/wxshop/orders/ordersUpdate?oid=${orders.oid}" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <br>
    <br>
    <c:if test="${not empty orders.oid}">
        <div class="control-group">
            <label class="control-label">编号:</label>
            <div class="controls">
                <label class="lbl">${orders.oid}</label>
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">收件人:</label>
        <div class="controls">
            <form:input path="member.name" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">电话:</label>
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
    <div class="control-group">
        <label class="control-label">下单日期:</label>
        <div class="controls">
            <label class="lbl"><fmt:formatDate value="${orders.credate}" type="both" dateStyle="full"/></label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">支付金额:</label>
        <div class="controls">
            <label class="lbl">${orders.pay}</label>
        </div>
    </div>
    <div class="control-group">
        <label class="control-label">订单状态:</label>
        <div class="controls">
            <form:select path="delivery">
                <form:option value="未发货"/>
                <form:option value="已发货"/>
            </form:select>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wxshop:orders:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>