<%--
  Created by IntelliJ IDEA.
  User: frank
  Date: 16-1-19
  Time: 上午9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title></title>
    <%@ include file="/include/easyuilibs.jsp" %>
    <script src="${cxp}/js/qinzhi/authority_list.js"></script>
</head>
<body style="position: relative">

<div class="easyui-layout" style="width:100%;height:100%;">
    <div data-options="region:'west',split:true" title="权限树" style="width:23%;height: 100%">
        <div style="padding:5px;">
            <shiro:hasPermission name="create_authority">
                <a href="#" class="easyui-linkbutton" iconCls="icon-add" onclick="newAuth()">新增</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="update_authority">
                <a href="#" class="easyui-linkbutton" iconCls="icon-edit" onclick="editAuth()">修改</a>
            </shiro:hasPermission>
            <shiro:hasPermission name="delete_authority">
                <a href="#" class="easyui-linkbutton" iconCls="icon-remove" onclick="removeAuth()">删除</a>
            </shiro:hasPermission>
        </div>
        <div id="mytree"></div>
    </div>
    <div data-options="region:'center'" title="权限信息" style="width:77%;height: 100%">
        <table id="auth-dg" style="height: 100%">
        </table>
    </div>
</div>

<div id="add-dlg" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
     closed="true" buttons="#add-dlg-buttons">
    <div class="ftitle">权限信息</div>
    <form id="fm" method="post" novalidate>
        <input type="hidden" value="" name="authorityId" id="authorityId"/>
        <input type="hidden" value="" name="authorityFid" id="authorityFid"/>

        <div class="fitem">
            <label>权限名称:</label>
            <input name="authorityName" id="authorityName" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>权限代码:</label>
            <input name="authorityCode" id="authorityCode" class="easyui-textbox" required="true">
        </div>
        <div class="fitem">
            <label>权限排序:</label>
            <input id="authorityOrder" name="authorityOrder" class="easyui-numberbox" required="true">
        </div>
        <div class="fitem">
            <label>权限描述:</label>
            <input id="authorityDesc" name="authorityDesc" class="easyui-textbox" data-options="multiline:true"
                   style="height:50px">
        </div>
    </form>
</div>
<div id="add-dlg-buttons">
    <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveAuth()">保存</a>
    <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel"
       onclick="javascript:$('#add-dlg').dialog('close')">取消</a>
</div>
<style type="text/css">
    html, body {
        width: 100%;
        height: 100%;
        padding: 0;
        margin: 0;
    }
</style>
</body>
</html>
