<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
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
		<li><a href="${ctx}/wxshop/member/list">用户列表</a></li>
		<li class="active"><a href="${ctx}/wxshop/member/form?mid=${member.mid}">用户<shiro:hasPermission name="wxshop:member:edit">${not empty member.mid?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wxshop:member:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="member" action="${ctx}/wxshop/member/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<div class="control-group">
			<label class="control-label">头像:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">编号:</label>
			<div class="controls">
				<form:input path="mid" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<c:if test="${ empty member.mid}">
		<div class="control-group">
			<label class="control-label">密码:</label>
			<div class="controls">
				<input id="password" name="password" type="password" value="" maxlength="50" minlength="3" class="${empty member.mid?'required':''}"/>
				<c:if test="${empty member.mid}"><span class="help-inline"><font color="red">*</font> </span></c:if>
				<c:if test="${not empty member.mid}"><span class="help-inline">若不修改密码，请留空。</span></c:if>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">确认密码:</label>
			<div class="controls">
				<input id="confirmNewPassword" name="confirmNewPassword" type="password" value="" maxlength="50" minlength="3" equalTo="#password"/>
				<c:if test="${empty member.mid}"><span class="help-inline"><font color="red">*</font> </span></c:if>
			</div>
		</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">手机:</label>
			<div class="controls">
				<form:input path="phone" htmlEscape="false" maxlength="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<form:select path="delFlag">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">地址:</label>
			<div class="controls">
				<form:input path="address" htmlEscape="false" maxlength="100" />
			</div>
		</div>
        <c:if test="${not empty member.mid}">
            <div class="control-group">
                <label class="control-label">注册日期:</label>
                <div class="controls">
					<label class="lbl"><fmt:formatDate value="${member.regdate}" type="both" dateStyle="full"/></label>
				</div>
            </div>
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="wxshop:member:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>