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
<body class="gray-bg" style="width:90%; text-align:center;margin: auto"> 
	<div id="pagination-div">
		<table id="dg" class="easyui-datagrid"
			style="width:100%;  min-height: 385px;" toolbar="#toolbar"
			pagination="false" rownumbers="false" fitColumns="true"
			singleSelect="true" checkOnSelect="false" selectOnCheck="false" nowrap="false">
			<thead>
				<tr>
				    <th field="id" width="100">ID</th>
				    <th field="levelImage" width="100">会员图片</th>
				    <th field="levelLimit" width="100">上传商品数量</th>
				    <th field="levelWelfare" width="100">每条信息服务费</th>
					<th field="levelDesc" width="200">单次账号充值</th>
				 	<th field="levelD" width="200">会员升级</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editLevel()">修改</a>
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

	<div id="dlg2" class="easyui-dialog"
		style="width: 640px; height: 320px; padding: 10px 10px" closed="true"
		buttons="#dlg-buttons">
		<form id="fm2" method="post" novalidate>
			<input type="hidden" name="id" id="id">
	         <input id="levelImage" name="levelImage"  type="hidden" >   
			<div class="fitem">
				<label>会员级别:</label> <input name="levelName" id="userName"
					class="easyui-textbox" required="true"> <label>上传商品数量:</label>
				<input name="levelLimit" id="levelLimit"
					class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>每条信息服务费:</label> <input name="levelWelfare" id="levelWelfare"
					class="easyui-textbox" > <label>单次账户充值:</label>
				<input name="levelDesc" id="levelDesc" class="easyui-textbox" > 
			</div>
			<div class="fitem">
				<label>图片:</label> <input id="goodsImages" name="goodsImages"   
				required="true"   type="file" style="width:50%;height:30px" accept="image/png,image/gif,image/jpeg"  > 
				<input  type="button"  value="上传" onclick="return ajaxFileUpload();"  style="height:30px;width: 40px"/>
			</div>
			<div class="fitem">
				<label>图片预览:</label> <img src='' id="levelImageUrl" height="75px" width="75px"> 
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveGoods()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
</body>
<script type="text/javascript">

</script>
</html>
