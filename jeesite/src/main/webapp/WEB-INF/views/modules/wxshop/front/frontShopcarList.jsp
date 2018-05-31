<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript" src="../js/shopcar.js"></script>
</head>
<body>
<h1>${pageContext.request.contextPath}</h1>
<div>
    <c:if test="${mid != null}">
        <br><br>
        ${mid} 您好，感谢登录使用！  <a href="showMember">个人中心</a>     <a href="logout">安全退出</a><br><br>
        <a href="frontList">商品列表</a>  <a href="shopcarList">购物车</a>  <a href="ordersList">全部订单</a><br><br><br>
    </c:if>
</div>

<script type="text/javascript">
    var allPrice = 0.0;         //保存总价格
    window.onload = function () {     //在页面加载完毕之后进行统计计算
        document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
    }
    function calGoods(gid) {
        var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
        var count = parseInt(document.getElementById(gid).value) ;
        var num = accMul(price,count);
        num.toFixed(2);
        allPrice = accAdd(allPrice,num);
        allPrice.toFixed(2);
        document.getElementById("cal-" + gid).innerHTML = "<font color = 'red'>" + num + "</font>";
        if(document.getElementById("result") != undefined){
            document.getElementById("result").innerHTML =  "<font color = 'red'>" + allPrice + "</font>" ;
        }
    }

    function addBut(gid) {
        var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
        var count = parseInt(document.getElementById(gid).value) ;
        allPrice -= accMul(price,count) ;
        count ++ ;
        document.getElementById(gid).value = count ;
        calGoods(gid) ;
    }
    function subBut(gid) {
        var price = parseFloat(document.getElementById("price-"+gid).innerHTML) ;
        var count = parseInt(document.getElementById(gid).value) ;
        allPrice -= accMul(price,count) ;
        if(count != 0){
            count -- ;
        }
        document.getElementById(gid).value = count ;
        calGoods(gid);
    }

    function accMul(arg1,arg2)
    {
        var m=0,s1=arg1.toString(),s2=arg2.toString();
        try{m+=s1.split(".")[1].length}catch(e){}
        try{m+=s2.split(".")[1].length}catch(e){}
        return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
    }

    function accAdd(arg1,arg2){
        var r1,r2,m;
        try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
        try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
        m=Math.pow(10,Math.max(r1,r2))
        return (arg1*m+arg2*m)/m
    }
</script>



<ul class="nav nav-tabs">
    <li class="active"><a href="shopcarList">购物车列表</a></li>
</ul>
<sys:message content="${message}"/>

<c:if test="${allGoods != null && shopcars != null}">
    <form action="/front/updateShopcar" method="post">
        <table border="1" cellpadding="5" cellspacing="0" >
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