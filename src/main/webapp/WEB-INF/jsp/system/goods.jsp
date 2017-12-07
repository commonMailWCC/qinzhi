<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/include/easyuilibs.jsp"%>
<script src="${cxp}/qinzhi/goods_list.js"></script>
<script src="${cxp}/qinzhi/qinzhi.js"></script>
<script src="${cxp}/qinzhi/fileupload.js"></script>
</head>
<body class="gray-bg" style="width:90%; text-align:center;margin: auto"> 
    <input type="hidden" id="operatorId1" value="${operatorId}">
	<div id="tb" class="query-div">
	    <span>商品名称:</span> <input name="goodsName1" id="goodsName1"
			class="easyui-textbox" type="text">
		<span>品牌型号:</span> <input name="goodsBrand1" id="goodsBrand1"
			class="easyui-textbox" type="text">  
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-search'" onclick="searchGoods()">查询</a>
	</div>
	<div id="pagination-div">
		<table id="dg" class="easyui-datagrid"
			style="width: 100%;  min-height: 385px;" toolbar="#toolbar"
			pagination="true" pageSize="10" pageList=[10,50,100] rownumbers="false" fitColumns="true"
			singleSelect="true" checkOnSelect="false" selectOnCheck="false">
			<thead>
				<tr>
				    <th field="goodsImage" width="100">商品图片</th>
				    <th field="id" width="100">商品编号</th>
					<th field="goodsName" width="200">商品名称</th>
				 	<th field="goodsBrand" width="200">品牌型号</th>
				    <th field="goodsPrice" width="100">价格</th>
				 	<th field="goodsDate" width="100">生产日期</th>
				 	<th field="goodsPlace" width="200">上次读卡地点</th>
				 	<th field="goodsAddress" width="100">销售地</th>
				 	<th field="goodsCount" width="200">读卡次数</th>
				 	<th field="goodsDesc" width="200">备注</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
	    <shiro:hasPermission name="user_manage">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newGoods()">新增</a>
		</shiro:hasPermission>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editGoods()">修改</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteGoods()">删除</a>
		    <a href="${cxp}/qinzhi/goods.xlsx" class="easyui-linkbutton"
			iconCls="icon-add" plain="true"  >下载商品导入模板</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="addGoods()">导入商品</a>	
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 640px; height: 350px; padding: 10px 10px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">商品信息</div>
		<form id="fm" method="post" novalidate>
		    <input id="id" name="id"  type="hidden" >
		    <input id="goodsImage" name="goodsImage"  type="hidden" >
			<input type="hidden" name="operatorId" id="operatorId" value="${operatorId}">
			<div class="fitem">
				<label>商品名称:</label> <input name="goodsName" id="goodsName" class="easyui-textbox" required="true"> 
				<label>商品密码:</label> <input id="goodsCode" name="goodsCode"  class="easyui-textbox" required="true"> 
			</div>
			<div class="fitem">
				<label>商品价格:</label> <input name="goodsPrice" id="goodsPrice" class="easyui-textbox" validType="data" required="true">  
				<label>品牌型号:</label> <input name="goodsBrand" id="goodsBrand" class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>生产日期:</label> <input id="goodsDate" name="goodsDate"  class="easyui-datebox" required="true"> 
				<label>商品备注:</label> <input name="goodsDesc" id="goodsDesc" class="easyui-textbox" >  
			</div>
			<div class="fitem">
				<label>销售地:</label> <input name="goodsAddress" id="goodsAddress" class="easyui-textbox" >  
			</div>
			<div class="fitem">
				<label>商品图片:</label> <input id="goodsImages" name="goodsImages"   
				required="true"   type="file" style="width:50%;height:30px" accept="image/png,image/gif,image/jpeg"  > 
				<input  type="button"  value="上传" onclick="return ajaxFileUpload();"  style="height:30px;width: 40px"/>
			</div>
			<div class="fitem">
				<label>图片预览:</label> <img src='' id="imageUrl" height="75px" width="75px"> 
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveGoods()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
    
    <div id="importGoods" class="easyui-dialog"
       	style="width: 640px; height: 280px; padding: 10px 10px" closed="true"
       	buttons="#importGoods-buttons">
       	<div class="ftitle">商品导入</div>
       <form id="uploadExcel"  method="post" enctype="multipart/form-data">    
                  选择文件：　<input id = "excel" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">   
                      <a id = "booten" href="javascript:void(0)" class="easyui-linkbutton"  
                          onclick="uploadExcel()" style="width: 80px" id="tt">导入</a>  
              </form>    
       <div id="importGoods-buttons">
       	   <a href="javascript:void(0)" class="easyui-linkbutton"
       		iconCls="icon-cancel" onclick="javascript:$('#importGoods').dialog('close')">取消</a>
       </div>

</body>
<script type="text/javascript">

</script>
</html>
