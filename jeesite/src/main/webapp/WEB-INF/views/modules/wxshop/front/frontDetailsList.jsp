<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
    <title>查看订单</title>
    <meta name="decorator" content="default"/>
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

<sys:message content="${message}"/>
<c:if test="${orders != null}">
    <table border="1" cellpadding="5" cellspacing="0" >
        <tr>
            <td width="5%"><font size="5">订单编号</font></td>
            <td width="5%"><font size="5">收件人</font></td>
            <td width="5%"><font size="5">联系电话</font></td>
            <td width="5%"><font size="5">订单金额</font></td>
            <td width="7%"><font size="5">收件地址</font></td>
            <td width="7%"><font size="5">下单日期</font></td>
            <td width="7%"><font size="5">发货状态</font></td>
        </tr>
        <tr>
            <td><a href="detailsList?oid=${orders.oid}"> <font size="5">${orders.oid}</font></a></td>
            <td><font size="5">${orders.member.name}</font></td>
            <td><font size="5">${orders.phone}</font></td>
            <td><font size="5">${orders.pay}</font></td>
            <td><font size="5">${orders.address}</font></td>
            <td><font size="5"><fmt:formatDate value="${orders.credate}" pattern="yyyy-MM-dd HH:mm:ss"/></font></td>
            <td><font size="5">${orders.delivery}</font></td>
        </tr>
    </table>
    <br>
    <br>
    <table border="1" cellpadding="5" cellspacing="0" >
        <tr>
            <td><font size="5">购买商品名称</font></td>
            <td><font size="5">商品单价</font></td>
            <td><font size="5">购买数量</font></td>
        </tr>
        <c:if test="${orders.allDetails!=null}">
            <c:forEach items="${orders.allDetails}" var="details">
                <tr>
                    <td><a href="frontGoodsView?gid=${details.goods.gid}"><font size="5">${details.title}</font> </a></td>
                    <td><font size="5">${details.price}</font></td>
                    <td><font size="5">${details.amount}</font></td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

</c:if>
<div class="pagination">${page}</div>
</body>
</html>