/**
 * Created by frank on 16-1-20.
 */
$(function(){
    $('#dg').datagrid({
        method: 'get',
        url: 'find_role_list.json'
    });
})
var url;
function newRole() {
    $('#dlg').dialog({
        title: '新增角色',
        modal: true
    });
    $('#dlg').dialog('open').dialog('center');
    $('#fm').form('clear');
    url = 'add_role.json';
}
function editRole() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fm').form('clear');
        $('#dlg').dialog('open').dialog('center').dialog('setTitle', '编辑角色');
        $('#fm').form('load', rows[0]);
        url = 'update_role.json';
    } else {
        qinzhi_APP.showEditWarnFunc();
    }
}
function saveRole() {
    $('#fm').form('submit', {
        url: url,
        onSubmit: function () {
            var result = $(this).form('validate');
            if(result) {
                $('#dlg').dialog('close');
            }
            return result;
        },
        success: function (result) {
            if (result == 'success') {
                $.messager.show({
                    title: '成功',
                    msg: "新增、修改角色成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "新增、修改角色失败"
                });
            }
            $('#dg').datagrid('reload');    // reload the user data
        }
    });
}
function deleteRole() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length > 0) {
        $.messager.confirm('确认', '确认要删除吗？', function (r) {
            if (r) {
                var ids = qinzhi_APP.convertIds2Str(rows,"roleId");
                $.post('delete_role.json', {ids: ids}, function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '成功',
                            msg: "删除角色成功"
                        });
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({
                            title: '失败',
                            msg: "删除角色失败"
                        });
                    }
                });
            }
        });
    } else {
        qinzhi_APP.showDeleteWarnFunc();
    }
}

function searchRole() {
    $('#dg').datagrid('load', {
        roleName: $('#roleNameQuery').val()
    });
}

function assignAuth() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $("#roleId").val(rows[0].roleId);
        $('#auth_dlg').dialog({
            title: '分配权限',
            modal: true
        }).dialog('open').dialog('center');
        var _auth_tree = $('#auth_tree');
        _auth_tree.tree({
            url: 'authority_list.json',
            checkbox: true,
            animate: true,
            onLoadSuccess: function () {
                $.get("selected_authority_for_role.json?id=" + rows[0].roleId, function (data) {
                    if (data) {
                        var authIdArr = data.split(',');
                        $(authIdArr).each(function (i, id) {
                            var node = _auth_tree.tree('find', id);
                            if (node) {
                                _auth_tree.tree('check', node.target);
                            }
                        });
                    }
                });
            }
        });
    } else {
        $.messager.show({
            title: '警告',
            msg: '权限分配操作请选择一行记录',
            showType: 'show'
        });
    }
}
function saveAuthRole() {
    var authIds = $('#auth_tree').tree('getChecked', ['checked', 'indeterminate']);
    if (authIds) {
        var toSaveAuthID = qinzhi_APP.convertIds2Str(authIds);
        $.post('assign_role_authority.json', {authIds: toSaveAuthID, roleId: $("#roleId").val()}, function (result) {
            if (result == 'success') {
                $.messager.show({
                    title: '成功',
                    msg: "角色分配权限成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "角色分配权限失败"
                });
            }
            $('#auth_dlg').dialog('close');
        });
    }
}
