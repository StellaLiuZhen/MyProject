<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
    <title>商品详情</title>
</head>
<body>

<div>
    <c:if test="${mid != null}">
        <br><br>
        ${mid} 您好，感谢登录使用！  <a href="showMember">个人中心</a>     <a href="logout">安全退出</a><br><br>
        <a href="frontList">商品列表</a>  <a href="shopcarList">购物车</a>  <a href="ordersList">全部订单</a><br><br><br>
    </c:if>
</div>
<br>
<br>
<form:form id="inputForm" modelAttribute="goods" action="${ctx}/wxshop/goods/save" method="post"
           class="form-horizontal">
    <form:hidden path="id"/>
    <sys:message content="${message}"/>
    <table id="contentTable" class="table table-striped table-bordered table-condensed">
        <tr>
            <td>
                <a href="frontGoodsView?gid=${goods.gid}">
                    <img src="${goods.photo}" style="width: 100px;height: 100px" >
                </a>
            </td>
        </tr>
        <tr>
            <td><strong>商品名称：</strong></td>
        </tr>
        <tr>
            <td>${goods.title}</td>
        </tr>
        <tr>
            <td><strong>商品价格：</strong></td>
        </tr>
        <tr>
            <td>${goods.price}</td>
        </tr>
        <tr>
            <td><strong>数量：</strong></td>
        </tr>
        <tr>
            <td>${goods.amount}</td>
        </tr>
        <tr>
            <td><strong>描述：</strong></td>
        </tr>
        <tr>
            <td>${goods.note}</td>
        </tr>
        <tr>
            <td>
                <a href="addCar?gid=${goods.gid}">
                    加入到购物车
                </a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>