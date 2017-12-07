<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/include/easyuilibs.jsp"%>
<script src="${cxp}/qinzhi/level.js"></script>
<script src="${cxp}/qinzhi/qinzhi.js"></script>
<script src="${cxp}/qinzhi/fileupload.js"></script>
</head>
<body class="gray-bg" style="width:80%; text-align:center;margin: auto"> 
    <input type="hidden" id="operatorId1" value="${operatorId}">
    <input type="hidden" id="operatorLevel2" value="${operator.levelId}">
	<div id="tb" class="query-div">
	    <span>商户名称:</span>     <input style='border: 0;font-weight:bold;' value="${operator.operatorName}">
		<span>当前会员等级:</span> <input style='border: 0;font-weight:bold;' value="${operator.operatorLevel}">  
	</div>
	<div id="pagination-div">
		<table id="dg" class="easyui-datagrid"
			style="width:100%;  min-height: 385px;" toolbar="#toolbar"
			pagination="false" rownumbers="false" fitColumns="true"
			singleSelect="true" checkOnSelect="false" selectOnCheck="false" nowrap="false">
			<thead>
				<tr>
				    <th field="id" width="100">ID</th>
				    <th field="levelImage" width="100">会员图片</th>
				    <th field="levelLimit" width="100">会员上传限制</th>
				    <th field="levelWelfare" width="100">会员福利</th>
					<th field="levelDesc" width="200">备注</th>
				 	<th field="levelD" width="200">会员升级</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="dlg" class="easyui-dialog"
		style="width: 380px; height: 380px; padding: 10px 10px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">请扫描二维码进行支付</div>
		<form id="fm" method="post" novalidate>
		    <input id="id" name="id"  type="hidden" >
			<div class="fitem" style="text-align: center;">
				 <img src='http://7xiy0w.com1.z0.glb.clouddn.com/zhifubao.png' id="imageUrl" height="225px" width="225px"> 
			</div>
		</form>
	</div>
</body>
<script type="text/javascript">

</script>
</html>
