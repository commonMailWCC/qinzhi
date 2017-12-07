<%--
  Created by IntelliJ IDEA.
  User: frank
  Date: 16-1-15
  Time: 下午3:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <%@ include file="/include/easyuilibs.jsp" %>
    <script src="${cxp}/js/qinzhi/role_list.js"></script>
</head>
<body>
<div id="tb" class="query-div">
    <span>角色名称:</span>
    <input name="roleNameQuery" id="roleNameQuery" class="easyui-textbox" type="text">
    <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchRole()">查询</a>
</div>
<div id="pagination-div">
    <table id="dg" class="easyui-datagrid" style="width: 100%;  min-height: 555px;"
           toolbar="#toolbar" pagination="true" pageSize="14" pageList=[14,50,100]
           rownumbers="true" fitColumns="true" singleSelect="true" checkOnSelect="false" selectOnCheck="false">
        <thead>
        <tr>
            <th field="roleName" width="200">角色名称</th>
            <th field="createDate" width="200">创建时间</th>
            <th field="roleDesc" width="400">描述</th>
        </tr>
        </thead>
    </table>
</div>
<div id="toolbar">
    <shiro:hasPermission name="create_role">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true"
           onclick="newRole()">新增</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="update_role">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true"
           onclick="editRole()">修改</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="delete_role">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true"
           onclick="deleteRole()">删除</a>
    </shiro:hasPermission>
    <shiro:hasPermission name="assgin_role_authority">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-lock" plain="true"
           onclick="assignAuth()">权限分配</a>
    </shiro:hasPermission>
</div>
<div id="dlg" class="easyui-dialog" style="width:400px;height:280px;padding:10px 20px"
     closed="true" buttons="#dlg-buttons">
    <div class="ftitle">角色信息</div>
    <form id="fm" method="post" novalidate>
        <input type="hidden" name="roleId" id="roleId">

        <div class="fitem">
            <label>角色名称:</label>
            <input name="roleName" id="roleName" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>描述:</label>
            <input name="roleDesc" id="roleDesc" class="easyui-textbox" data-options="multiline:true"
                   style="height:80px">
        </div>
    </form>
</div>
<%-- 权限分配 --%>
<div id="auth_dlg" class="easyui-dialog" style="width:380px;height:440px;padding:10px 20px" closed="true"
     buttons="#auth-dlg-buttons">
    <div class="ftitle">权限信息</div>
    <div id="auth_tree"></div>
</div>
<%-- 模板分配 --%>
<div id="template_dlg" class="easyui-dialog" style="width:60%;height:95%;" closed="true"
     buttons="#template-dlg-buttons">
    <div style="padding: 3px">
        <span>EPG分组名称:</span>
        <input name="templateNameQuery" id="templateNameQuery" class="easyui-textbox" type="text">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'" onclick="searchTemplate()">查询</a>
    </div>
    <div>
        <table id="template-dg">
        </table>
    </div>
</div>
<div id="template_detail_dlg" class="easyui-dialog" style="width:60%;height:95%;" closed="true">
    <div style="padding: 3px">
        <span>EPG分组名称:</span>
        <input id="bindedtemplateNameQuery" class="easyui-textbox" type="text">
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search'"
           onclick="searchBindedTemplate()">查询</a>
        <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"
           onclick="unBindTemplate()">解除EPG分组与角色关联</a>
    </div>
    <div>
        <table id="template-detail-dg">
        </table>
    </div>
</div>
<div id="dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveRole()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#dlg').dialog('close')">取消</a>
</div>
<div id="auth-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveAuthRole()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#auth_dlg').dialog('close')">取消</a>
</div>
<div id="template-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveTemplateRole()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#template_dlg').dialog('close')">取消</a>
</div>
<%--<style type="text/css">
    html, body {
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
    }
</style>--%>
</body>
</html>
