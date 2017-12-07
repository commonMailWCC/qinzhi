/**
 * Created by frank on 16-1-20.
 */

var selectedNode;
$(function () {
    $('#mytree').tree({
        url: 'authority_list.json',
        animate: true,
        onSelect:function(node){
            selectedNode = $('#mytree').tree("getSelected");
            $('#auth-dg').datagrid({
                method: 'get',
                url: 'find_authority_pid_list.json?id=' + node.id,
                pageNumber: 1,
                pageSize: 20,
                columns: [
                    [
                        {field: 'authorityCode', title: '权限编号', width: 200},
                        {field: 'authorityName', title: '权限名称', width: 200},
                        {field: 'authorityOrder', title: '权限排序', width: 50},
                        {field: 'authorityDesc', title: '权限描述', width: 200}
                    ]],
                pagination: true,
                fitColumns: true,
                singleSelect:true
            });
        },
        onLoadSuccess:function(){
            if(selectedNode){
                $('#mytree').tree('expandTo', selectedNode.target);
//                $('#mytree').tree('expandAll',selectedNode.target);
            }
            $('#auth-dg').datagrid({
                method: 'get',
                url: 'find_authority_pid_list.json?id=' + $('#mytree').tree('getRoot').id,
                pageNumber: 1,
                pageSize: 20,
                columns: [
                    [
                        {field: 'authorityCode', title: '权限编号', width: 200},
                        {field: 'authorityName', title: '权限名称', width: 200},
                        {field: 'authorityOrder', title: '权限排序', width: 50},
                        {field: 'authorityDesc', title: '权限描述', width: 200}
                    ]],
                pagination: true,
                fitColumns: true,
                rownumbers:true,
                singleSelect:true
            });
        }
    });
})

var url;
function newAuth() {
    var node = $('#mytree').tree("getSelected");
    if (!node) {
        $.messager.show({
            title: '警告',
            msg: '至少得选择一个节点',
            showType: 'show'
        });
    } else {
        $('#add-dlg').dialog({
            title: '新增权限信息',
            modal: true
        });
        $("#authorityCode").textbox({editable:true});
        $('#add-dlg').dialog('open').dialog('center');
        $('#fm').form('clear');
        $("#authorityFid").val(node.id);
        url = 'add_authority.json';
    }
}

function editAuth() {
    var node = $('#mytree').tree("getSelected");
    if (!node) {
        $.messager.show({
            title: '警告',
            msg: '至少得选择一个节点',
            showType: 'show'
        });
    } else {
        if (node.pid == 0 || node.pid == null) {
            $.messager.show({
                title: '警告',
                msg: '不能修改根节点',
                showType: 'show'
            });
            return false;
        }
        $('#fm').form('clear');
        $("#authorityId").val(node.id);
        $.get("to_update_authority.json?id=" + node.id, function (data) {
            $('#add-dlg').dialog({
                title: '修改权限信息',
                modal: true
            });
            $('#add-dlg').dialog('open').dialog('center');
            $('#fm').form('load', data);
            $("#authorityCode").textbox({editable:false});
        });
        url = 'update_authority.json';
    }
}

function saveAuth() {
    $('#fm').form('submit', {
        url: url,
        onSubmit: function () {
            var result = $(this).form('validate');
            if(result) {
                $('#add-dlg').dialog('close');
            }
            return result;
        },
        success: function (result) {
//            var selectedNode = $("#mytree").tree("getSelected");
            if (result == 'success') {
                $.messager.show({
                    title: '成功',
                    msg: "新增、修改权限成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "新增、修改权限失败"
                });
            }
            $('#mytree').tree("reload");
          /*  if(selectedNode){
                $('#mytree').tree('expandTo', selectedNode.target);
            }*/
        }
    });
}

function removeAuth(){
    var node = $('#mytree').tree("getSelected");
    if (!node) {
        TVBOS_APP.showRemoveTreeWarnFunc();
    } else {
        $.messager.confirm('确认', '确认删除当前节点及下属的全部节点？', function (r) {
            if (r) {
                $.post('delete_authority.json', {id: node.id}, function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '成功',
                            msg: "删除权限成功"
                        });
                        $('#mytree').tree("reload");
                    } else {
                        $.messager.show({
                            title: '失败',
                            msg: "删除权限失败"
                        });
                    }
                });
            }
        });
    }
}