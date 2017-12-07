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


    <title>qinzhi - 500 错误</title>
    <meta name="keywords" content="qinzhi平台">
    <meta name="description" content="qinzhi平台，500错误">

    <link rel="shortcut icon" href="favicon.ico">
    <link href="${cxp}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
    <link href="${cxp}/css/font-awesome.css?v=4.4.0" rel="stylesheet">

    <link href="${cxp}/css/animate.css" rel="stylesheet">
    <link href="${cxp}/css/style.css?v=4.1.0" rel="stylesheet">
</head>
<title>500 - 系统内部错误</title>
</head>
<body class="gray-bg">
<div class="middle-box text-center animated fadeInDown">
    <h1>500</h1>

    <h3 class="font-bold">服务器内部错误</h3>

    <div class="error-desc">
        服务器好像出错了...
        <br/>您可以返回主页看看
        <br/><a href="index.html" class="btn btn-primary m-t">主页</a>
    </div>
</div>

<!-- 全局js -->
<script src="${cxp}/js/jquery.min.js?v=2.1.4"></script>
<script src="${cxp}/js/bootstrap.min.js?v=3.3.6"></script>

</body>
</html>
