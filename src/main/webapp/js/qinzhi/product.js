$(document).ready(function(){
	$('#product').bootstrapTable({
	  method:'get',
      url: "findProductList.json?t=" + Math.random(),
      //toolbar: '#toolbar',                //工具按钮用哪个容器
      striped: true,                      //是否显示行间隔色
      cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      pagination: true,                   //是否显示分页（*）
      sortable: false,                     //是否启用排序
      sortOrder: "asc",                   //排序方式
      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
      pageNumber:1,                       //初始化加载第一页，默认第一页
      pageSize: 2,                       //每页的记录行数（*）
      pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      strictSearch: true,
      showColumns: false,                  //是否显示所有的列
      showRefresh: false,                  //是否显示刷新按钮
      minimumCountColumns: 2,             //最少允许的列数
      clickToSelect: true,                //是否启用点击选中行
      height: 300,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
      uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
      showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
      cardView: false,                    //是否显示详细视图
      detailView: false,                     //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      columns: [
	  {
		  field: 'productName',
		  title: '产品',
		  formatter:function (value, row, index){
			  return '<a href = "jumpindex?appId='+row.appId+'" >' + value + '</a>';
		  } 
	  },{
          field: 'appId',
          title: '应用Id'
      },{
          field: 'appKey',
          title: '应用Key'
      },{
          field: 'platform',
          title: '平台'
      },{
          field: 'createDate',
          title: '创建时间'
      }, {
          field: 'createUser',
          title: '创建人'
      }]
  });
	
});

function newProduction() {
	var appId = uuid(10,16);
	$("#productName").val("");
	$("#appId").val(appId);
	$("#appKey").val(hex_md5(appId));
    $("#myModal").modal();
}

function closeModal() {
    $("#myModal").c
}

function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;
 
    if (len) {
      // Compact form
      for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
      // rfc4122, version 4 form
      var r;
 
      // rfc4122 requires these characters
      uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
      uuid[14] = '4';
 
      // Fill in random data.  At i==19 set the high bits of clock sequence as
      // per rfc4122, sec. 4.1.5
      for (i = 0; i < 36; i++) {
        if (!uuid[i]) {
          r = 0 | Math.random()*16;
          uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
        }
      }
    }
 
    return uuid.join('');
}