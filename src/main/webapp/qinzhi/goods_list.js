$(function(){
    $('#dg').datagrid({
        url: 'findGoodsList.json?operatorId='+$('#operatorId1').val(),
        columns: [[
        	 {
                 field: 'goodsImage', title: '商品图片', width: 80, sortable: false,
                 formatter : function(value){
                   
                     return '<img src='+value+'   height="75px" width="75px">';
                 }
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
                 field: 'goodsAddress', title: '销售地', width: 80, sortable: false,
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
function newGoods() {
    $('#dlg').dialog({
        title: '新增商品',
        modal: true
    });
    $('#dlg').dialog('open').dialog('center');
    $('#fm').form('clear');
    $("#goodsState").combobox('select', 'NORMAL');
    url = 'addGoods.json';
}
Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
    };
function editGoods() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fm').form('clear');
        $.get("get_goods.json?id=" + rows[0].id, function (data) {
            $('#dlg').dialog('open').dialog('center').dialog('setTitle', '编辑商品信息');
            var unixTimestamp = new Date(data.goodsDate) ;
            data.goodsDate = unixTimestamp.toLocaleString();
            $('#fm').form('load', data);
            $('#imageUrl').attr( "src",data.goodsImage);
            //$('#fm').form('load', data);

        });
        $('#dlg').dialog('open').dialog('center');
        url = 'update_goods.json';
    } else {
        YST_APP.showEditWarnFunc();
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
                    msg: "新增、修改商品成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: result
                });
            }
            $('#dg').datagrid('reload');    // reload the Goods data
        }
    });
}
function deleteGoods() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length > 0) {
        $.messager.confirm('确认', '确认要删除吗？', function (r) {
            if (r) {
                var ids = YST_APP.convertIds2Str(rows,"id");
                $.post('delete_goods.json', {ids: ids}, function (result) {
                    if (result == "success") {
                        $.messager.show({
                            title: '成功',
                            msg: "删除商品成功"
                        });
                        $('#dg').datagrid('reload');    // reload the Goods data
                    } else {
                        $.messager.show({
                            title: '失败',
                            msg: "删除商品失败"
                        });
                    }
                });
            }
        });
    } else {
        YST_APP.showDeleteWarnFunc();
    }
}

function searchGoods() {
    $('#dg').datagrid('load', {
        goodsName: $('#goodsName1').val(),
        goodsBrand: $('#goodsBrand1').val(),
        operatorId: $('#operatorId1').val(),
    });
}

function ajaxFileUpload()
{
    $.ajaxFileUpload({
            url:'uploadImage',//用于文件上传的服务器端请求地址
            secureuri:false ,//一般设置为false
            fileElementId:'goodsImages',//文件上传控件的id属性  <input type="file" id="upload" name="upload" />
            dataType: 'text',//返回值类型 一般设置为json
            success: function (message)  //服务器成功响应处理函数
            {
                  layer.msg("上传成功")
                $("#levelImage").val(message);
                $('#imageUrl').attr( "src",message);
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                alert(e);
            }
        });
    return false;
}

function addGoods() {
    $('#uploadExcel').form('clear'); 
    $('#importGoods').dialog({
        title: '新增商商品',
        modal: true
    });
    $('#importGoods').dialog('open').dialog('center');
    url = 'add_operator.json';
}


function uploadExcel(){ 
  $("#uploadExcel").form('submit', {  
               type : 'post',   
               url : 'loadGoods.json',  
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
                      $("#booten").linkbutton('enable');  
                      $('#uploadExcel').form('clear');     // reload the user data
                      $('#importGoods').dialog('close');
                      $('#dg').datagrid('reload');    // reload the user data
               }  
           });     
}