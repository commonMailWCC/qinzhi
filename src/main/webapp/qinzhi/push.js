$(function(){
    $('#dg').datagrid({
        url: 'findPushList.json',
        columns: [[
             {
                 field: 'id', title: '编号', width: 5, sortable: false,
             }, 
             {
                 field: 'message', title: '推送详情', width: 80, sortable: false,
             },
             {
                 field: 'createTime', title: '创建时间', width: 15, sortable: false,
                 formatter : function(value){
                     if (null !=value && ''!=value) {
                        var date =   YST_APP.showDate(value);
                        return date;
                     }
                     else{
                        return value;
                     }
                 }
             },
        ]]
    });
})

var url;
function newPush() {
    $('#dlg').dialog({
        title: '新增推送',
        modal: true
    });
    $('#dlg').dialog('open').dialog('center');
    $('#fm').form('clear');
    $("#PushState").combobox('select', 'NORMAL');
    url = 'addPush.json';
}
Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
    };
 
function savePush() {
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
                    msg: "推送成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "推送失败"
                });
            }
            $('#dg').datagrid('reload');    // reload the Push data
        }
    });
}


function searchPush() {
    $('#dg').datagrid('load', {
    });
} 