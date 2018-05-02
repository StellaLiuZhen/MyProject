<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>订单详情</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#no").focus();
			$("#inputForm").validate({
				rules: {
					loginName: {remote: "${ctx}/sys/user/checkLoginName?oldLoginName=" + encodeURIComponent('${user.loginName}')}
				},
				messages: {
					loginName: {remote: "用户登录名已存在"},
					confirmNewPassword: {equalTo: "输入与上面相同的密码"}
				},
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
	</script>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/wxshop/orders/list">订单列表</a></li>
	</ul><br/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
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
	<br>
	<table id="ordersTable" class="table table-striped table-bordered table-condensed" width="50%">
		<tr>
			<td>商品名称</td>
			<td>商品单价</td>
			<td width="70%">购买数量</td>
		</tr>
		<c:if test="${orders.allDetails!=null}">
			<c:forEach items="${orders.allDetails}" var="details">
				<tr>
					<td><a href="${ctx}/wxshop/goods/form?gid=${details.goods.gid}">${details.title}</a></td>
					<td>${details.price}</td>
					<td>${details.amount}</td>
				</tr>
			</c:forEach>
		</c:if>
	</table>

</body>
</html>