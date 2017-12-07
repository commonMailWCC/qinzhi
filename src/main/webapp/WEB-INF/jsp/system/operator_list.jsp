<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
<%@ include file="/include/easyuilibs.jsp"%>
<script src="${cxp}/js/qinzhi/operator_list.js"></script>
<script src="${cxp}/qinzhi/qinzhi.js"></script>
</head>
<body class="gray-bg" style="width:90%; text-align:center;margin: auto">
   <input type="hidden" id="operatorId" value="${operatorId }">
	<div id="tb" class="query-div">
		<span>商户等级:</span> <input   class="easyui-combobox" name="dept" name="operatorLevel1" id="operatorLevel1"  data-options="valueField:'id',textField:'text',url:'get_level_list.json'" />  
	    <span>商品类别:</span> <input name="saleGoods1" id="saleGoods1"
			class="easyui-textbox" type="text">
		<span>商户联系人:</span> <input name="operatorContact1" id="operatorContact1"
			class="easyui-textbox" type="text"> <span>手机号码:</span> <input
			name="operatorLoginName1" id="operatorLoginName1" class="easyui-textbox" type="text">
		<a href="#" class="easyui-linkbutton"
			data-options="iconCls:'icon-search'" onclick="searchUser()">查询</a>
	</div>
	<div id="pagination-div">
		<table id="dg" class="easyui-datagrid"
			style="width: 100%;  min-height: 335px;" toolbar="#toolbar"
			pagination="true" pageSize="10" pageList=[10,50,100] rownumbers="true" fitColumns="true"
			singleSelect="true" checkOnSelect="false" selectOnCheck="false">
			<thead>
				<tr>
				    <th field="operatorId" width="100">商户编号</th>
				    <th field="operatorLevel" width="100">商户等级</th>
					<th field="operatorName" width="200">商户名称</th>
					<th field="saleGoods" width="200">销售商品类别</th>
					<th field="operatorContact" width="200">联系人</th>
					<th field="operatorLoginName" width="200">登录账号[手机号]</th>
					<th field="operatorEmail" width="250">邮箱</th>
					<th field="operatorAddress" width="250">详细地址</th>
				</tr>
			</thead>
		</table>
	</div>
	<div id="toolbar">
		<!-- <shiro:hasPermission name="create_operator">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newUser()">新增</a>
		</shiro:hasPermission> -->
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="editUser()">修改商户信息</a>
		<shiro:hasPermission name="update_operator">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-edit" plain="true" onclick="updateUser()">升级权限</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="delete_operator">
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-remove" plain="true" onclick="deleteUser()">删除</a>
		</shiro:hasPermission>
		<shiro:hasPermission name="create_operator">
		    <a href="${cxp}/qinzhi/user.xlsx" class="easyui-linkbutton"
			iconCls="icon-add" plain="true"  >下载商户导入模板</a>
			<a href="javascript:void(0)" class="easyui-linkbutton"
				iconCls="icon-add" plain="true" onclick="newUser()">导入商户</a>
		</shiro:hasPermission>
	</div>

	<div id="dlg" class="easyui-dialog"
		style="width: 640px; height: 280px; padding: 10px 10px" closed="true"
		buttons="#dlg-buttons">
		<div class="ftitle">商户信息</div>
		<form id="fm" method="post" novalidate>
			<input type="hidden" name="operatorId" id="operatorId">
             
			<div class="fitem">
				<label>商户名称:</label> <input name="operatorName" id="userName"
					class="easyui-textbox" required="true"> <label>登录名称:</label>
				<input name="operatorLoginName" id="loginName"
					class="easyui-textbox" required="true">
			</div>
			<div class="fitem">
				<label>商户邮箱:</label> <input name="operatorEmail" id="email"
					class="easyui-textbox" validType="email"> <label>角色:</label>
				<input id="roles" name="roles" class="easyui-combobox"
					data-options="valueField:'value',textField:'text',url:'system_role_list.json',panelHeight:'auto',required:'true',editable:'false'">
			</div>
			<div class="fitem">
				<label>登录密码:</label> <input id="pwd1" name="pwd1" type="password"
					class="easyui-textbox" > <label>确认密码:</label>
				<input id="pwd2" name="pwd2" type="password"
					validType="equalTo['#pwd1']" class="easyui-textbox"  
					invalidMessage="两次输入密码不匹配">
			</div>
		</form>
	</div>
	<div id="dlg-buttons">
		<a href="javascript:void(0)" class="easyui-linkbutton c6"
			iconCls="icon-ok" onclick="saveUser()">保存</a> <a
			href="javascript:void(0)" class="easyui-linkbutton"
			iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')">取消</a>
	</div>
   
   <div id="dlgLevel" class="easyui-dialog"
   	style="width: 640px; height: 280px; padding: 10px 10px" closed="true"
   	buttons="#dlgLevel-buttons">
   	<div class="ftitle">商户会员升级</div>
   	<form id="fmLevel" method="post" novalidate>
   		<input type="hidden" name="operatorId2" id="operatorId2">
   		<div class="fitem">
   			<label>会员升级:</label>
            <input   class="easyui-combobox" name="dept" name="operatorLevel2" id="operatorLevel2"  data-options="valueField:'id',textField:'text',url:'get_level_list.json'" />  
   		</div>
   		 
   	</form>
   </div>
   <div id="dlgLevel-buttons">
   	<a href="javascript:void(0)" class="easyui-linkbutton c6"
   		iconCls="icon-ok" onclick="updateLevel()">保存</a> <a
   		href="javascript:void(0)" class="easyui-linkbutton"
   		iconCls="icon-cancel" onclick="javascript:$('#dlgLevel').dialog('close')">取消</a>
   </div>
   
   <div id="importUser" class="easyui-dialog"
      	style="width: 640px; height: 280px; padding: 10px 10px" closed="true"
      	buttons="#importUser-buttons">
      	<div class="ftitle">商户会员导入</div>
      <form id="uploadExcel"  method="post" enctype="multipart/form-data">    
                 选择文件：　<input id = "excel" name = "excel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">    
             </form>    
             <div style="text-align: center; padding: 5px 0;">  
                 <a id = "booten" href="javascript:void(0)" class="easyui-linkbutton"  
                     onclick="uploadExcel()" style="width: 80px" id="tt">导入</a>  
             </div> 
      <div id="importUser-buttons">
      	   <a href="javascript:void(0)" class="easyui-linkbutton"
      		iconCls="icon-cancel" onclick="javascript:$('#importUser').dialog('close')">取消</a>
      </div>

</body>
</html>
