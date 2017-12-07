$(function(){
    $('#dg').datagrid({
        url: 'findGoodsList.json',
        columns: [[
        	 {
                 field: 'goodsImage', title: '商品图片', width: 80, sortable: false,

             },
             {
                 field: 'id', title: '商品编号', width: 80, sortable: false,

             }, 
             {
                 field: 'goodsName', title: '商品名称', width: 80, sortable: false,

             },
             {
                 field: 'goodsBrand', title: '品牌型号', width: 80, sortable: false,

             },
             
             {
                 field: 'goodsPrice', title: '价格', width: 80, sortable: false,

             },
             {
                 field: 'goodsPlace', title: '上次读卡地点', width: 80, sortable: false,

             },
             {
                 field: 'goodsCount', title: '读卡次数', width: 80, sortable: false,

             },
            {
                field: 'goodsDate', title: '生产日期', width: 80, sortable: false,
                formatter : function(value){
                    var date = new Date(value);
                    var y = date.getFullYear();
                    var m = date.getMonth() + 1;
                    var d = date.getDate();
                    return y + '-' +m + '-' + d;
                }

            },
            {
                field: 'goodsDesc', title: '备注', width: 80, sortable: false,

            },
        ]]
    });
})

var url;
function newUser() {
    $('#dlg').dialog({
        title: '新增用户',
        modal: true
    });
    $('#dlg').dialog('open').dialog('center');
    $('#fm').form('clear');
    $("#goodsState").combobox('select', 'NORMAL');
    url = 'add_goods.json';
}
function editUser() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fm').form('clear');
        $("#id").val(rows[0].id);
        
        url = 'update_goods.json';
    } else {
        qinzhi_APP.showEditWarnFunc();
    }
}
function saveGoods() {
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
                    msg: "新增、修改用户成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "新增、修改用户失败"
                });
            }
            $('#dg').datagrid('reload');    // reload the user data
        }
    });
}
function deleteUser() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length > 0) {
        $.messager.confirm('确认', '确认要删除吗？', function (r) {
            if (r) {
                var ids = qinzhi_APP.convertIds2Str(rows,"id");
                $.post('delete_goods.json', {ids: ids}, function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '成功',
                            msg: "删除用户成功"
                        });
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({
                            title: '失败',
                            msg: "删除用户失败"
                        });
                    }
                });
            }
        });
    } else {
        qinzhi_APP.showDeleteWarnFunc();
    }
}

function searchUser() {
    $('#dg').datagrid('load', {
        goodsName: $('#goodsName1').val(),
        goodsBrand: $('#goodsBrand1').val()
    });
}