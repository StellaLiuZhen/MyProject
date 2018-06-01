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
        <font size="7">${mid}</font><br><br><br>
        <font size="7">您好，感谢登录使用！</font> <br><br><br>
        <a href="frontList"><font size="7">商品列表</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="shopcarList"><font size="7">购物车</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="ordersList"><font size="7">全部订单</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="showMember"><font size="7">个人中心</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="logout"><font size="5">安全退出</font></a><br><br><br>
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
            <td rowspan="6" width="55%">
                <a href="frontGoodsView?gid=${goods.gid}">
                    <img src="${goods.photo}" style="width: 480px;height: 480px" >
                </a>
            </td>
        </tr>
        <tr>
            <td><strong><font size="5"> 商品名称：</font></strong></td>
            <td><font size="6" >${goods.title}</font> </td>
        </tr>
        <tr>
            <td><strong><font size="6" > 商品价格：</font></strong></td>
            <td><font size="6" >${goods.price}</font> </td>
        </tr>
        <tr>
            <td><strong><font size="6" >描述：</font> </strong></td>
            <td><font size="6" >${goods.note}</font> </td>
        </tr>
        <tr>
            <td><strong><font size="6" > 数量：</font></strong></td>
            <td><font size="6" >${goods.amount}</font> </td>
        </tr>
        <tr>
            <td></td>
            <td>
                <a href="addCar?gid=${goods.gid}">
                    <font size="6" > 加入到购物车</font>
                </a>
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>