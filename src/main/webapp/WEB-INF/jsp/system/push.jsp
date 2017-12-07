<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/include/easyuilibs.jsp"%>
<script src="${cxp}/qinzhi/push.js"></script>
<script src="${cxp}/qinzhi/qinzhi.js"></script>
<script src="${cxp}/qinzhi/fileupload.js"></script>
</head>
<body class="gray-bg" style="width:90%; text-align:center;margin: auto"> 
    <input type="hidden" id="operatorId1" value="${operatorId}">
	<div id="tb" class="query-div">
	   <!--  <span>商品名称:</span> <input name="pushName1" id="pushName1"
	   			class="easyui-textbox" type="text">
	   		<span>品牌型号:</span> <input name="pushBrand1" id="pushBrand1"
	   			class="easyui-textbox" type="text"> 
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-search'" onclick="searchPush()">查询</a>  -->
	</div>
	<div id="pagination-div">
		<table id="dg" class="easyui-datagrid"
			style="width: 100%;  min-height: 385px;" toolbar="#toolbar"
			pagination="true" pageSize="10" pageList=[10,50,100] rownumbers="false" fitColumns="true"
			singleSelect="true" checkOnSelect="false" selectOnCheck="false">
			<thead>
				<tr>
				    <th field="pushImage" width="100">商品图片</th>
				    <th field="id" width="100">商品编号</th>
					<th field="pushName" width="200">商品名称</th>
				 	<th field="pushBrand" width="200">品牌型号</th>
				    <th field="pushPrice" width="100">价格</th>
				 	<th field="pushDate" width="100">生产日期</th>
				 	<th field="pushPlace" width="200">上次读卡地点</th>
				 	<th field="pushCount" width="200">读卡次数</th>
				 	<th field="pushDesc" width="200">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newPush()">新增推送</a>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 640px; height: 350px; padding: 10px 10px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">推送信息</div>
		<form id="fm" method="post" novalidate>
			<div class="fitem">
				 <input id="message" name="message"  class="easyui-textbox" data-options="multiline:true" required="true"  style="width:500px;height:180px"> 
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="savePush()">推送</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>

</body>
<script type="text/javascript">

</script>
</html>
