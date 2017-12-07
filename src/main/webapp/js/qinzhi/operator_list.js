$(function(){
	var operatorId = $("#operatorId").val();

  $("#roles").combobox({disabled:true});
	console.log(operatorId)
	var url = "find_operator_list.json";
	if(null !=operatorId && "" != operatorId){
		console.log(operatorId)
		url = url + "?operatorId="+operatorId;
	}
    $('#dg').datagrid({
        url: url
    });
})

var url;
function newUser() {
    $('#importUser').dialog({
        title: '新增商户',
        modal: true
    });
    $('#importUser').dialog('open').dialog('center');
    url = 'add_operator.json';
}
function editUser() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fm').form('clear');
        $("#operatorId").val(rows[0].operatorId);
        $.get("get_operator_info.json?id=" + rows[0].operatorId, function (data) {
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '编辑商户');
            $('#fm').form('load', data);
            if (!data.organizationId) {
                $('#organizationId').combotree('setValue', '');
            }
            $.get("get_operator_roleIds.json?id=" + rows[0].operatorId, function (data) {
                $('#roles').combobox('setValues', data.split(','))
            });
        });
        url = 'update_operator.json';
    } else {
        YST_APP.showEditWarnFunc();
    }
}

function updateUser() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fmLevel').form('clear');
        $("#operatorId2").val(rows[0].operatorId);
        $("#operatorLevel2").combobox('select', rows[0].operatorLevel);
        $('#dlgLevel').dialog('open').dialog('center').dialog('setTitle', '会员升级');
    } else {
        YST_APP.showEditWarnFunc();
    }
}
function updateLevel(){
  var operateId = $("#operatorId2").val();
  var levelId = $("#operatorLevel2").combobox('getValue');
   
  $.post("update_level.json?levelId="+levelId+"&operateId="+operateId, function(data) {
     if (data == 'success') {
         $.messager.show({
             title: '成功',
             msg: "升级会员成功"
         });
         $('#dlgLevel').dialog('close');
     } else {
         $.messager.show({
             title: '失败',
             msg: "升级会员失败"
         });
     }
     $('#dg').datagrid('reload');    // reload the user data
  }); 
  return false; 
}
function saveUser() {
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
                    msg: "新增、修改商户成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "新增、修改商户失败"
                });
            }
            $('#dg').datagrid('reload');    // reload the user data
        }
    });
}
function deleteUser() {
  var rows = $('#dg').datagrid('getSelections');
     if (rows && rows.length == 1) {
         $.messager.confirm('确认', '确认要删除吗？', function (r) {
             if (r) {
                var ids =  rows[0].operatorId;
                $.post('delete_operator.json', {ids: ids}, function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '成功',
                            msg: "删除商户成功"
                        });
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({
                            title: '失败',
                            msg: "删除商户失败"
                        });
                    }
                });
            }
        });
    } else {
        YST_APP.showDeleteWarnFunc();
    }
}

function searchUser() {
	var operatorId = $("#operatorId").val();

    $('#dg').datagrid('load', {
    	  saleGoods:$('#saleGoods1').val(), 
    	  levelId:$('#operatorLevel1').combobox('getValue'),
        operatorLoginName: $('#operatorLoginName1').val(),
        operatorContact: $('#operatorContact1').val(),
        operatorId : operatorId
    });
}



function uploadExcel(){ 
  $("#uploadExcel").form('submit', {  
               type : 'post',   
               url : 'loaduser.json',  
               dataType : "json",  
               onSubmit: function() {  
                   var fileName= $('#excel').filebox('getValue');   
                     //对文件格式进行校验    
                    var d1=/\.[^\.]+$/.exec(fileName);  
                   if (fileName == "") {    
                         $.messager.alert('Excel批量用户导入', '请选择将要上传的文件!');   
                         return false;    
                    }else if(d1!=".xls" && d1!=".xlsx"){  
                        $.messager.alert('提示','请选择xls格式文件！','info');    
                        return false;   
                    }  
                    $("#booten").linkbutton('disable');  
                   return true;    
               },   
               success : function(result) {  
                      var obj = eval('(' + result + ')'); 
                      var msg = obj.message;
                      if (obj.status == 1) {
                          $.messager.show({
                              title: '成功',
                              msg: msg
                          });
                      } else {
                          $.messager.show({
                              title: '失败',
                              msg: msg
                          });
                      }
                      $('#uploadExcel').form('clear');     // reload the user data
                      $('#importUser').dialog('close');
                      $("#booten").linkbutton('enable');  
                      $('#dg').datagrid('reload');    // reload the user data
               }  
           });     
}
$("#roles").combobox({disabled:true});