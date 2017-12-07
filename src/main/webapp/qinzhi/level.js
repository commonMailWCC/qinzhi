$(function(){
    $('#dg').datagrid({
        url: 'findLevelList.json',
        columns: [[
             {
                 field: 'id', title: 'ＩＤ', width: 10, hidden:'true'
             },
        	 {
                 field: 'levelImage', title: '会员级别', width: 20, 
                 formatter : function(value,row){
                     return '<img src='+value+'   height="75px" width="75px"> <br> '+row.levelName;
                 }
             },
             {
                 field: 'levelWelfare', title: '每条信息服务费', width: 60,  
             },
             {
                 field: 'levelLimit', title: '上传商品数量', width: 60,  
             },
             {
                 field: 'levelDesc', title: '单次账号充值', width: 50,  
             },
            {
                field: 'levelD', title: '会员升级', width: 40, 
                formatter : function(value,row){
                    value = row.id;
                    var level =$("#operatorLevel2").val();
                  if (level =="1" && (value =="2" || value =="3" || value =="4")) {
                      return '<a href="javascript:volid(0);" onclick=updateLevel()  height="75px" width="75px">升级会员</a>';
                  }else if(level=="2" && (value =="3" || value =="4")) {
                      return '<a href="javascript:volid(0);" onclick=updateLevel()  height="75px" width="75px">升级会员</a>';
                  }else if(level=="3" && (value =="4")) {
                      return '<a href="javascript:volid(0);" onclick=updateLevel()  height="75px" width="75px">升级会员</a>';
                  }else {
                      
                  }
                }
            },
        ]]
    });
})

var url;
function updateLevel() {
    $('#dlg').dialog({
        title: '升级',
        modal: true
    });
    $('#dlg').dialog('open').dialog('center');
    $('#fm').form('clear');
}
Date.prototype.toLocaleString = function() {
          return this.getFullYear() + "-" + (this.getMonth() + 1) + "-" + this.getDate();
};


function editLevel() {
    var rows = $('#dg').datagrid('getSelections');
    if (rows && rows.length == 1) {
        $('#fm2').form('clear');
        $("#operatorId").val(rows[0].id);
        $.get("get_level_info.json?id=" + rows[0].id, function (data) {
            $('#dlg2').dialog('open').dialog('center').dialog('setTitle', '编辑会员');
            $('#fm2').form('load', data);
            $('#imageUrl').attr( "src",data.levelImage);
        });
        url = 'update.json';
    } else {
        YST_APP.showEditWarnFunc();
    }
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
                  layer.msg("上传成功" + message)

                $("#levelImage").val(message);
                $("#levelImageUrl").attr('src',message); 
            },
            error: function (data, status, e)//服务器响应失败处理函数
            {
                alert(e);
            }
        });
    return false;
}

function saveGoods() {
    $('#fm2').form('submit', {
        url: "update.json",
        onSubmit: function () {
            var result = $(this).form('validate');
            if(result) {
                $('#dlg2').dialog('close');
            }
            return result;
        },
        success: function (result) {
            if (result == 'success') {
                $.messager.show({
                    title: '成功',
                    msg: "修改会员成功"
                });
            } else {
                $.messager.show({
                    title: '失败',
                    msg: "修改会员失败"
                });
            }
            $('#dg').datagrid('reload');    // reload the Goods data
        }
    });
}