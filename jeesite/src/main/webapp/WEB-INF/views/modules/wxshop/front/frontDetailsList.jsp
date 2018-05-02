<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>查看订单</title>
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

<sys:message content="${message}"/>
<c:if test="${orders != null}">
    <table border="1" cellpadding="5" cellspacing="0" >
        <tr>
            <td>订单编号：</td>
            <td>收件人：</td>
            <td>联系电话：</td>
            <td>订单金额：</td>
            <td>收件地址：</td>
            <td>下单日期：</td>
        </tr>
        <tr>
            <td><a href="${ctx}/wxshop/orders/form?oid=${orders.oid}"> ${orders.oid}</a></td>
            <td>${orders.member.mid}</td>
            <td>${orders.phone}</td>
            <td>${orders.pay}</td>
            <td>${orders.address}</td>
            <td><fmt:formatDate value="${orders.credate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
        </tr>
    </table>
    <br>
    <br>
    <table border="1" cellpadding="5" cellspacing="0" >
        <tr>
            <td>购买商品名称</td>
            <td>商品单价</td>
            <td>购买数量</td>
        </tr>
        <c:if test="${orders.allDetails!=null}">
            <c:forEach items="${orders.allDetails}" var="details">
                <tr>
                    <td><a href="frontGoodsView?gid=${details.goods.gid}">${details.title}</a></td>
                    <td>${details.price}</td>
                    <td>${details.amount}</td>
                </tr>
            </c:forEach>
        </c:if>
    </table>

</c:if>
<div class="pagination">${page}</div>
</body>
</html>