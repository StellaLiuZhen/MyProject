<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
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

<ul class="nav nav-tabs">
    <li class="active"><a href="frontOrdersList">订单列表</a></li>
</ul>
<form:form id="searchForm" modelAttribute="goods" action="front/frontOrdersList" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
</form:form>
<sys:message content="${message}"/>
<c:if test="${orders != null}">
    <table border="1" cellpadding="5" cellspacing="0" width="42%">
        <tr>
            <td width="5%">订单编号</td>
            <td width="5%">联系人</td>
            <td width="5%">联系电话</td>
            <td width="7%">收件地址</td>
            <td width="7%">下单日期</td>
        </tr>
        <c:forEach items="${page.list}" var="orders">
            <tr>
                <td><a href="detailsList?oid=${orders.oid}"> ${orders.oid}</a></td>
                <td>${orders.member.mid}</td>
                <td>${orders.phone}</td>
                <td>${orders.address}</td>
                <td><fmt:formatDate value="${orders.credate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
            </tr>
        </c:forEach>
    </table>

</c:if>
<div class="pagination">${page}</div>
</body>
</html>