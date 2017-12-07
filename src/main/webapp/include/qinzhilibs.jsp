<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro"  uri="http://shiro.apache.org/tags"%>
<%
    String cxp = request.getContextPath();
    request.setAttribute("cxp", cxp);
%>
<link rel="shortcut icon" href="${cxp}/img/favicon.ico"> <link href="${cxp}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${cxp}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${cxp}/css/animate.css" rel="stylesheet">
<link href="${cxp}/css/mega-menu.css" rel="stylesheet">
<link href="${cxp}/css/style.css?v=4.1.0" rel="stylesheet">
<link href="${cxp}/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

<!-- 全局js -->
<script src="${cxp}/js/jquery.min.js?v=2.1.4"></script>
<script src="${cxp}/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${cxp}/js/bootstrap-hover-dropdown.min.js"></script>
<script src="${cxp}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${cxp}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${cxp}/js/plugins/layer/layer.js"></script>
<script src="${cxp}/js/plugins/layer/laydate/laydate.js"></script>

<!-- 自定义js -->
<script src="${cxp}/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="${cxp}/js/contabs.js"></script>
<%--<script src="${cxp}/js/qinzhi.js"></script>--%>

<!-- 第三方插件 -->
<script src="${cxp}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${cxp}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${cxp}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${cxp}/js/qinzhi/distpicker.js"></script>
<script>
    var vPath = "<c:out value="${cxp}"/>";
    var contextPath = "<c:out value="${cxp}"/>";
</script>
