<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<%@include file="/WEB-INF/views/include/head.jsp" %>
<html style="overflow-x:auto;overflow-y:auto;">
<head>
    <title>商品列表</title>
    <meta name="decorator" content="default"/>
    <script type="text/javascript">
        $(document).ready(function() {
            $("#btnExport").click(function(){
                top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
                    if(v=="ok"){
                        $("#searchForm").attr("action","${ctx}/sys/user/export");
                        $("#searchForm").submit();
                    }
                },{buttonsFocus:1});
                top.$('.jbox-body .jbox-icon').css('top','55px');
            });
            $("#btnImport").click(function(){
                $.jBox($("#importBox").html(), {title:"导入数据", buttons:{"关闭":true},
                    bottomText:"导入文件不能超过5M，仅允许导入“xls”或“xlsx”格式文件！"});
            });
        });
        function page(n,s){
            if(n) $("#pageNo").val(n);
            if(s) $("#pageSize").val(s);
            $("#searchForm").attr("action","/front/frontList");
            $("#searchForm").submit();
            return false;
        }
    </script>
</head>
<body>
<c:if test="${msg != null}">
    <script type="text/javascript">
        window.alert("${msg}");
    </script>
</c:if>
<div>
    <c:if test="${mid != null}">
        <br><br>
        ${mid} 您好，感谢登录使用！ <a href="showMember">个人中心</a> <a href="logout">安全退出</a><br><br>
        <a href="frontList">商品列表</a> <a href="shopcarList">购物车</a> <a href="ordersList">全部订单</a><br><br><br>
    </c:if>
</div>
<form:form id="searchForm" modelAttribute="goods" action="/front/frontList" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li>
            <label>类&nbsp;&nbsp;&nbsp;型：</label>
            <form:select path="item.iid">
                <form:option value="" label="请选择"/>
                <form:options items="${items}" itemLabel="title" itemValue="iid" htmlEscape="false"/>
            </form:select>
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
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><th class="sort-column login_name">图片</th><th class="sort-column name">名称</th><th>价格</th><th>说明</th><th>操作</th></thead>
    <tbody>
    <c:forEach items="${page.list}" var="goods">
        <tr>
            <td><a href="frontGoodsView?gid=${goods.gid}"><img src="${goods.photo}" style="width: 60px;height: 60px"></a></td>
            <td><a href="frontGoodsView?gid=${goods.gid}">${goods.title}</a></td>
            <td>${goods.price}</td>
            <td>${goods.note}</td>
            <td>
                <a href="addCar?gid=${goods.gid}">
                    加入到购物车
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>