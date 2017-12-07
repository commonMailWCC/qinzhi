<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<% String cxp =  request.getContextPath();
		request.setAttribute("cxp",cxp);
%>
<script type="text/javascript" src="${cxp}/ueditor/third-party/jquery-1.10.2.js"></script>
<script type="text/javascript" charset="utf-8" src="${cxp}/ueditor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${cxp}/ueditor/ueditor.all.js"> </script>
<script type="text/javascript" charset="utf-8" src="${cxp}/ueditor/ueditor.parse.js"> </script>