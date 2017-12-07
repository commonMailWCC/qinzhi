<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro"  uri="http://shiro.apache.org/tags"%>
<%
    String cxp = request.getContextPath();
    request.setAttribute("cxp", cxp);
%>
<link href="${cxp}/easy-ui/themes/gray/easyui.css" rel="stylesheet" type="text/css"/>
<link href="${cxp}/easy-ui/themes/icon.css" rel="stylesheet" type="text/css"/>
<link href="${cxp}/easy-ui/themes/color.css" rel="stylesheet" type="text/css"/>
<link href="${cxp}/easy-ui/themes/tvbos.css" rel="stylesheet" type="text/css"/>
<script src="${cxp}/easy-ui/js/jquery.min.js"></script>
<script src="${cxp}/easy-ui/js/jquery.easyui.min.js"></script>
<script src="${cxp}/easy-ui/locale/easyui-lang-zh_CN.js"></script>
<script src="${cxp}/easy-ui/js/easyui_extends.js"></script>
<script src="${cxp}/js/plugins/layer/layer.js"></script>
<script>
    var vPath = "<c:out value="${cxp}"/>";
    var contextPath = "<c:out value="${cxp}"/>";
</script>
