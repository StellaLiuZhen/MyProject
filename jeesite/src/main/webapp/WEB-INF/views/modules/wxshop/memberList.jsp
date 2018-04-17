<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>用户管理</title>
	<meta name="decorator" content="default"/>
</head>
<body>
	<ul class="nav nav-tabs">
		<li class="active"><a href="${ctx}/wxshop/member/list">用户列表</a></li>
		<shiro:hasPermission name="wxshop:member:edit"><li><a href="${ctx}/wxshop/member/form">用户添加</a></li></shiro:hasPermission>
	</ul>
	<form:form id="searchForm" modelAttribute="member" action="${ctx}/wxshop/member/list" method="post" class="breadcrumb form-search ">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		<sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
		<ul class="ul-form">
			<li>
				<label>姓&nbsp;&nbsp;&nbsp;名：</label>
				<form:input path="mid" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li>
				<label>状&nbsp;&nbsp;&nbsp;态：</label>
				<form:input path="delFlag" htmlEscape="false" maxlength="50" class="input-medium"/>
			</li>
			<li class="btns">
				<input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
			</li>
			<li class="clearfix"></li>
		</ul>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead><><th class="sort-column login_name">用户</th><th class="sort-column name">编号</th><th>注册日期</th><th>状态</th><shiro:hasPermission name="wxshop:member:edit"><th>操作</th></shiro:hasPermission></tr></thead>
		<tbody>
		<c:forEach items="${page.list}" var="member">
			<tr>
				<td><a href="${ctx}/wxshop/member/form?mid=${member.mid}">${member.mid}</a></td>
				<td>${member.name}</td>
                <td><fmt:formatDate value="${member.regdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${member.delFlag}</td>
				<shiro:hasPermission name="wxshop:member:edit"><td>
    				<a href="${ctx}/wxshop/member/form?mid=${member.mid}">修改</a>
					<a href="${ctx}/wxshop/member/delete?mid=${member.mid}" onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>
				</td></shiro:hasPermission>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>