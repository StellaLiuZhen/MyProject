<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp" %>
<html>
<head>
    <title>商品管理</title>
    <meta name="decorator" content="default"/>
</head>

<body>
<c:if test="${msg != null}">
    <script type="text/javascript">
        window.alert("${msg}");
    </script>
</c:if>
<div id="mainDiv">
    <form id="loginForm" class="form-signin" action="/front/login" method="post">
        <label class="input-label" for="mid">登录名</label>
        <input type="text" id="mid" name="mid" class="input-block-level required">
        <br>
        <br>
        <input type="checkbox" name="reme" id="reme" value="7760000">记住密码

        <%--
		<label for="mobile" title="手机登录"><input type="checkbox" id="mobileLogin" name="mobileLogin" ${mobileLogin ? 'checked' : ''}/></label> --%>
        <input class="btn btn-large btn-primary" type="submit" value="登 录"/>&nbsp;&nbsp;
    </form>
</div>
</body>
</html>
