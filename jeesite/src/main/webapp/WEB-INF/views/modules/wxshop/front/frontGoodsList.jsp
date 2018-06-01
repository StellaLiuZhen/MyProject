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
        <font size="7">${mid}</font><br><br><br>
        <font size="7">您好，感谢登录使用！</font> <br><br><br>
        <a href="frontList"><font size="7">商品列表</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="shopcarList"><font size="7">购物车</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="ordersList"><font size="7">全部订单</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="showMember"><font size="7">个人中心</font></a>&nbsp;&nbsp;&nbsp;&nbsp;
        <a href="logout"><font size="5">安全退出</font></a><br><br><br>
    </c:if>
</div>
<form:form id="searchForm" modelAttribute="goods" action="/front/frontList" method="post"
           class="breadcrumb form-search ">
    <input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
    <input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
    <sys:tableSort id="orderBy" name="orderBy" value="${page.orderBy}" callback="page();"/>
    <ul class="ul-form">
        <li>
            <label><font size="5">类&nbsp;&nbsp;&nbsp;型：</font></label>
            <form:select path="item.iid">
                <form:option value="" label="请选择"/>
                <form:options items="${items}" itemLabel="title" itemValue="iid" htmlEscape="false"/>
            </form:select>
        </li>
        <li>
            <label><font size="5">名&nbsp;&nbsp;&nbsp;称：</font></label>
            <form:input path="title" htmlEscape="false" maxlength="50" class="input-medium"/>
        </li>
        <li class="btns">
            <input id="btnSubmit" class="btn btn-primary" type="submit" value="查询" onclick="return page();"/>
        </li>
    </ul>
</form:form>
<sys:message content="${message}"/>
<table id="contentTable" class="table table-striped table-bordered table-condensed">
    <thead><th><font size="5">图片</font></th>
    <th><font size="5">名称</font></th>
    <th><font size="5">价格</font></th>
    <th><font size="5">说明</font></th>
    <th><font size="5">操作</font></th></thead>
    <tbody>
    <c:forEach items="${page.list}" var="goods">
        <tr>
            <td width="25%"><a href="frontGoodsView?gid=${goods.gid}"><img src="${goods.photo}" style="width: 240px;height: 240px"></a></td>
            <td><a href="frontGoodsView?gid=${goods.gid}"><font size="5">${goods.title}</font></a></td>
            <td><font size="5">${goods.price}</font></td>
            <td><font size="5">${goods.note}</font></td>
            <td>
                <a href="addCar?gid=${goods.gid}">
                    <font size="5">加入到购物车</font>
                </a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<div class="pagination">${page}</div>
</body>
</html>