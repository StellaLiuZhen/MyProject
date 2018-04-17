<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品类型管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wxshop/item/list">类型列表</a></li>
		<shiro:hasPermission name="wxshop:item:edit"><li><a href="${ctx}/wxshop/item/form">类型添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="item" action="${ctx}/wxshop/item/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>编&nbsp;&nbsp;&nbsp;号：</label>
				<form:input path="iid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>名&nbsp;&nbsp;&nbsp;称：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><><th class="sort-column login_name">编号</th><th class="sort-column name">名称</th><shiro:hasPermission name="wxshop:item:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="item">
			<tr>
				<td>${item.iid}</td>
				<td><a href="${ctx}/wxshop/item/form?iid=${item.iid}">${item.title}</a></td>
				<shiro:hasPermission name="wxshop:item:edit"><td>
    				<a href="${ctx}/wxshop/item/form?iid=${item.iid}">修改</a>
					<a href="${ctx}/wxshop/item/delete?iid=${item.iid}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>