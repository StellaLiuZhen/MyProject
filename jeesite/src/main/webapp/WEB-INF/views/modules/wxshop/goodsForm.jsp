<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
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
		<li><a href="${ctx}/wxshop/goods/list">商品列表</a></li>
		<li class="active"><a href="${ctx}/front/form?gid=${goods.gid}">商品<shiro:hasPermission name="wxshop:goods:edit">${not empty goods.gid?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="wxshop:goods:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="goods" action="${ctx}/wxshop/goods/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>
		<c:if test="${not empty goods.gid}">
			<div class="control-group">
				<label class="control-label">编号:</label>
				<div class="controls">
					<form:input path="gid" htmlEscape="false" maxlength="50" class="required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			</div>
		</c:if>
		<div class="control-group">
			<label class="control-label">类型:</label>
			<div class="controls">
				<sys:treeselect
						id="item"
						name="item.iid"
						value="${goods.item.iid}"
						labelName="item.title"
						labelValue="${goods.item.iid}"
						title="类型"
						url="/wxshop/item/treeData"
						cssClass="required"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">照片:</label>
			<div class="controls">
				<form:hidden id="nameImage" path="photo" htmlEscape="false" maxlength="255" class="input-xlarge"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/photo" selectMultiple="false" maxWidth="100" maxHeight="100"/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">名称:</label>
			<div class="controls">
				<form:input path="title" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">价格:</label>
			<div class="controls">
				<form:input path="price" htmlEscape="false" maxlength="100" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">数量:</label>
			<div class="controls">
				<form:input path="amount" htmlEscape="false" maxlength="50" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">描述:</label>
			<div class="controls">
				<form:input path="note" htmlEscape="false" maxlength="50" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">状态:</label>
			<div class="controls">
				<form:select path="status">
					<form:options items="${fns:getDictList('yes_no')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>
				<span class="help-inline"><font color="red">*</font> “是”代表此账号允许登录，“否”则表示此账号不允许登录</span>
			</div>
		</div>
        <c:if test="${not empty goods.gid}">
            <div class="control-group">
                <label class="control-label">发布日期:</label>
                <div class="controls">
					<label class="lbl"><fmt:formatDate value="${goods.pubdate}" type="both" dateStyle="full"/></label>
				</div>
            </div>
        </c:if>
		<div class="form-actions">
			<shiro:hasPermission name="wxshop:goods:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>