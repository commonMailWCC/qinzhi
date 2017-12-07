<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%
        String cxp = request.getContextPath();
        request.setAttribute("cxp", cxp);
    %>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">


    <title>qinzhi - 404 页面</title>
    <meta name="keywords" content="qinzhi平台">
    <meta name="description" content="qinzhi平台">

    <link rel="shortcut icon" href="favicon.ico"> <link href="${cxp}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${cxp}/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${cxp}/css/animate.css" rel="stylesheet">
    <link href="${cxp}/css/style.css?v=4.1.0" rel="stylesheet">
</head>
<body class="gray-bg">


<div class="middle-box text-center animated fadeInDown">
    <h1>404</h1>
    <h3 class="font-bold">页面未找到！</h3>

    <div class="error-desc">
        抱歉，页面好像去火星了~
    </div>
</div>

<!-- 全局js -->
<script src="${cxp}/js/jquery.min.js?v=2.1.4"></script>
<script src="${cxp}/js/bootstrap.min.js?v=3.3.6"></script>

</body>
</html>