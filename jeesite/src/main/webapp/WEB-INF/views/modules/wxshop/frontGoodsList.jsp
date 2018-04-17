<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wxshop/goods/frontList">商品列表</a></li>
	</ul>
	<form:form id="searchForm" modelAttribute="goods" action="${ctx}/wxshop/goods/frontList" method="post" class="breadcrumb form-search ">
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
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<c:forEach items="${page.list}" var="goods">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td rowspan="4">
					<a href="${ctx}/wxshop/goods/frontView?gid=${goods.gid}">
						<img src="${goods.photo}" style="width: 60px;height: 60px" >
					</a>
				</td>
			</tr>
			<tr>
				<td width="10%"><strong>商品名称：</strong></td>
				<td width="10%"><a href="${ctx}/wxshop/goods/frontView?gid=${goods.gid}">${goods.title}</a></td>
				<td width="10%"><strong>上架日期：</strong></td>
				<td width="63%"><fmt:formatDate value="${goods.pubdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
			<tr>
				<td><strong>商品价格：</strong></td>
				<td>${goods.price}</td>
				<td colspan="2">
						${goods.note}
				</td>

			</tr>
			<tr>
				<td><strong>浏览次数：</strong></td>
				<td>${goods.bow}</td>
				<td colspan="2">
					加入到购物车
				</td>
			</tr>
		</table>
	</c:forEach>
	<div class="pagination">${page}</div>
</body>
</html>