<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
</head>
<body>
<ul class="nav nav-tabs">
    <li class="active"><a href="${ctx}/wxshop/orders/list">订单列表</a></li>
    <%--<shiro:hasPermission name="wxshop:orders:edit">--%>
        <%--<li><a href="${ctx}/wxshop/orders/form">商品添加</a></li>--%>
    <%--</shiro:hasPermission>--%>
</ul>
<form:form id="searchForm" modelAttribute="orders" action="${ctx}/wxshop/orders/list" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li>
            <label>名&nbsp;&nbsp;&nbsp;称：</label>
            <form:input path="member.mid" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
        </li>
        <li class="clearfix"></li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><>
    <th class="sort-column login_name">订单编号</th>
    <th class="sort-column name">收件人</th>
    <th>电话</th>
    <th>地址</th>
    <th>发布日期</th>
    <th>金额</th>
    <th>订单状态</th>
    <th>操作</th>
    <%--<shiro:hasPermission name="wxshop:orders:edit">--%>
        <%--<th>操作</th>--%>
    <%--</shiro:hasPermission></thead>--%>
    <tbody>
    <c:forEach items="${page.list}" var="orders">
        <tr>
            <td><a href="${ctx}/wxshop/orders/form?oid=${orders.oid}">${orders.oid}</a></td>
            <td><a href="${ctx}/wxshop/orders/form?oid=${orders.oid}">${orders.member.mid}</a></td>
            <td>${orders.phone}</td>
            <td>${orders.address}</td>
            <td><fmt:formatDate value="${orders.credate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            <td>${orders.pay}</td>
            <td>${orders.delivery}</td>
            <td><a href="${ctx}/wxshop/orders/formOrders?oid=${orders.oid}">修改订单信息</a></td>
            <%--<shiro:hasPermission name="wxshop:orders:edit">--%>
                <%--<td>--%>
                    <%--<a href="${ctx}/wxshop/orders/form?oid=${orders.oid}">修改</a>--%>
                    <%--<a href="${ctx}/wxshop/orders/delete?oid=${orders.oid}"--%>
                       <%--onclick="return confirmx('确认要删除该用户吗？', this.href)">删除</a>--%>
                <%--</td>--%>
            <%--</shiro:hasPermission>--%>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>