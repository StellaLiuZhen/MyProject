<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wxshop/goods/list">商品列表</a></li>
		<shiro:hasPermission name="wxshop:goods:edit"><li><a href="${ctx}/wxshop/goods/form">商品添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/wxshop/goods/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>类&nbsp;&nbsp;&nbsp;型：</label>
				<form:input path="item.iid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>名&nbsp;&nbsp;&nbsp;称：</label>
				<form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>状&nbsp;&nbsp;&nbsp;态：</label>
				<form:input path="status" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><><th class="sort-column login_name">编号</th><th class="sort-column name">名称</th><th>类型</th><th>发布日期</th><th>价格</th><th>数量</th><th>点击</th><th>状态</th><shiro:hasPermission name="wxshop:goods:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="goods">
			<tr>
				<td><a href="${ctx}/wxshop/goods/form?gid=${goods.gid}">${goods.gid}</a></td>
				<td><a href="${ctx}/wxshop/goods/form?gid=${goods.gid}">${goods.title}</a></td>
				<td>${goods.item.iid}</td>
                <td><fmt:formatDate value="${goods.pubdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${goods.price}</td>
				<td>${goods.amount}</td>
				<td>${goods.bow}</td>
				<td>${goods.status}</td>
				<shiro:hasPermission name="wxshop:goods:edit"><td>
    				<a href="${ctx}/wxshop/goods/form?gid=${goods.gid}">修改</a>
					<a href="${ctx}/wxshop/goods/delete?gid=${goods.gid}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>