<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>商品列表</title>
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
	<form:form id="searchForm" modelAttribute="goods" action="/front/frontList" method="post" class="breadcrumb form-search ">
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
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<c:forEach items="${page.list}" var="goods">
		<table id="contentTable" class="table table-striped table-bordered table-condensed">
			<tr>
				<td rowspan="4">
					<a href="frontGoodsView?gid=${goods.gid}">
						<img src="${goods.photo}" style="width: 60px;height: 60px" >
					</a>
				</td>
			</tr>
			<tr>
				<td width="7%"><strong>商品名称：</strong></td>
				<td width="7%"><a href="frontGoodsView?gid=${goods.gid}">${goods.title}</a></td>
				<td colspan="2" width="81%">
						${goods.note}
				</td>
			</tr>
			<tr>
				<td><strong>商品价格：</strong></td>
				<td>${goods.price}</td>
				<td colspan="2">
					<a href="addCar?gid=${goods.gid}">
					加入到购物车
                    </a>
                </td>
			</tr>
		</table>
	</c:forEach>
	<div class="pagination">${page}</div>
</body>
</html>