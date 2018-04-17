<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品类型管理</title>
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
<ul class="nav nav-tabs">
    <li><a href="${ctx}/wxshop/item/list">类型列表</a></li>
    <li class="active"><a href="${ctx}/wxshop/item/form?iid=${item.iid}">类型<shiro:hasPermission
            name="wxshop:item:edit">${not empty item.iid?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission
            name="wxshop:item:edit">查看</shiro:lacksPermission></a></li>
</ul>
<br/>
<form:form id="inputForm" modelAttribute="item" action="${ctx}/wxshop/item/save" method="post" class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <c:if test="${not empty item.iid}">
        <div class="control-group">
            <label class="control-label">编号:</label>
            <div class="controls">
                <form:input path="iid" htmlEscape="false" maxlength="50" class="required"/>
                <span class="help-inline"><font color="red">*</font> </span>
            </div>
        </div>
    </c:if>
    <div class="control-group">
        <label class="control-label">名称:</label>
        <div class="controls">
            <form:input path="title" htmlEscape="false" maxlength="100"/>
        </div>
    </div>
    <div class="form-actions">
        <shiro:hasPermission name="wxshop:item:edit">
            <input id="btnSubmit" class="btn btn-primary" type="submit"value="保 存"/>&nbsp;
        </shiro:hasPermission>
        <input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
    </div>
</form:form>
</body>
</html>