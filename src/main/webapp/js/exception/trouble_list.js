/**
 * Created by frank on 16-1-20.
 */
$(function(){
    $('#dg').datagrid({
        method: 'get',
        url: 'get_trouble_info.json',
        view: detailview,
		detailFormatter: function(rowIndex, rowData){
			return '<table><tr>' +
					'<td style="border:0">' +
					'<p><font  color=blue>' + rowData.threadInfo +'&nbsp&nbsp'+ rowData.errorType + '&nbsp&nbsp'+ rowData.exceptInfo + '</font></p>' +
					'<p><br/>' + rowData.exceptStack + '</p>' +
					'<p><br/><font  color=blue>其他线程：</font>'+ rowData.allThreadInfo + '</p>' +
					'</td>' +
					'</tr></table>';
		}

    });
})

function searchTrouble(){
	 $('#dg').datagrid('load', {
		 deviceId : $('#deviceId').val(),
		 appVersion: $('#appVersion').val(),
		 appId:$('#appId').val(),
		 device:$('#device').val(),
		 systemVersion:$('#systemVersion').val(),
		 type:$('#type').combobox('getValue'),
		 userId:$('#userId').val()
	 });
}

