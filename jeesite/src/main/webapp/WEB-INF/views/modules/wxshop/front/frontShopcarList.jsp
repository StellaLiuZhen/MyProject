<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="http://47.106.71.180:8080/${ctxStatic}/js/shopcar.js"></script>
</head>
<body>

<div>
    <c:if test="${mid != null}">
        <h1>${mid} 您好，感谢登录使用！</h1>
        <a href="frontList">商品列表</a><br>
        <a href="shopcarList">购物车</a><br>
        <a href=" ">个人中心</a><br>
        <a href="logout">安全退出</a><br><br>
    </c:if>
</div>

<ul class="nav nav-tabs">
    <li class="active"><a href="shopcarList">购物车列表</a></li>
</ul>
<sys:message content="${message}"/>

<c:if test="${allGoods != null && shopcars != null}">
    <form action="/front/updateShopcar" method="post">
        <table border="1" cellpadding="5" cellspacing="0" width="42%">
            <tr>
                <td width="5%">图片</td>
                <td width="5%">名称</td>
                <td width="5%">价格</td>
                <td width="13%">数量</td>
                <td width="7%">总价</td>
                <td width="7%">操作</td>
            </tr>
            <c:forEach items="${allGoods}" var="goods">
                <c:forEach items="${shopcars}" var="shopcar">
                    <c:if test="${shopcar.goods.gid eq goods.gid}">
                        <tr>
                            <td>
                                <a href="frontGoodsView?gid=${goods.gid}">
                                    <img src="${goods.photo}" style="width: 60px;height: 60px">
                                </a>
                            </td>
                            <td><a href="frontGoodsView?gid=${goods.gid}">${goods.title}</a></td>
                            <td><span id="price-${goods.gid}">${goods.price}</span></td>
                            <td>
                                <input type="button" value="-" onclick="subBut(${goods.gid})">
                                <input type="text" value="${shopcar.amount}" size="3" name="${goods.gid}"
                                       id="${goods.gid}">
                                <input type="button" value="+" onclick="addBut(${goods.gid})">
                            </td>
                            <td>
                                <span id="cal-${goods.gid}"></span>
                                <script type="text/javascript">
                                    calGoods(${goods.gid});
                                </script>
                            </td>
                            <td>
                                <a href="deleteShopcar?goods.gid=${shopcar.goods.gid}&amount=${shopcar.amount}">删除</a>
                            </td>
                        </tr>
                    </c:if>
                </c:forEach>
            </c:forEach>
        </table>
        <br>
        <br>
        <input type="submit" value="修改">
        总金额：<span id="result"></span>
        <a href="insertOrders">去下单</a>
    </form>
</c:if>
<div class="pagination">${page}</div>
</body>
</html>