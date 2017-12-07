<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<META HTTP-EQUIV="Expires " CONTENT="0 " />
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="Cache-Control" content="no-cache, must-revalidate" />
<%@ include file="/include/editor.jsp"%>
<script type="text/javascript">
	
</script>
<style type="text/css">
</style>
</head>
<body>
	<div id="content" style="margin-left: 10px"></div>
</body>

<script type="text/javascript">
	$(function() {
		var text = '${htmlValue}';
		$("#content").append(text);
	})
	uParse('#content', {
		rootPath : '${cxp}/ueditor/'
	})
</script>
</html>