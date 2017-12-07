var head = "crash";
$(document).ready(function(){
    $("input:radio[name='adminFlag']").change(function () {
        if($(this).val()==1){
            //统计累计信息
            getData();
        }else{
            getData();
        }

    });

  $(".head_name").click(function(){
	  var result = sessionStorage.getItem("result");
	  var json = JSON.parse(result);
	  $("#headName").html($(this).text());
	  if ($(this).text() =='发生次数') {
		  var count = json.HEAD_COUNT;
		  $("#number_crash").html("&nbsp;" +count[0])
		  $("#number_anr").html("&nbsp;" +count[1])
		  $("#number_error").html("&nbsp;" +count[2])
	  }else if ($(this).text() =='影响用户数'){
		  $("#number_crash").html("&nbsp;" +json.HEAD_USER_COUNT[0])
		  $("#number_anr").html("&nbsp;" +json.HEAD_USER_COUNT[1])
		  $("#number_error").html("&nbsp;" +json.HEAD_USER_COUNT[2])
	  }else if ($(this).text() =='用户异常率'){
          $("#number_crash").html("&nbsp;" +json.HEAD_COUNT_RATE[0])
          $("#number_anr").html("&nbsp;" +json.HEAD_COUNT_RATE[1])
          $("#number_error").html("&nbsp;" +json.HEAD_COUNT_RATE[2])
      }
	  
	  
  })	
  var date = laydate.now();
  $("#dateType2").val(date);
  $("._1QvVF7uLNlQWYOKoejmhli").click(function(){
	  $("#toplogTable").bootstrapTable('removeAll');
	  $("._1QvVF7uLNlQWYOKoejmhli").removeClass("_1uAed4rjAp9SovsdhYntOO");
	  var s = $(this).attr("value");
	  $(this).addClass("_1uAed4rjAp9SovsdhYntOO");
	  var user_percent = '用户' + $(this).attr("value") +"率"
	  var time_percent = '次数' + $(this).attr("value") +"率"
	  $("#user_percent").empty().append(user_percent);
	  $("#time_percent").empty().append(time_percent);
	  if(s=='崩溃'){
		  $("#crachTable").show();
		  $("#anrTable").hide();
		  $("#topthree").show();
		  head = "crash";
		  refresh(head,"");
	  }else if(s=='ANR'){
		  $("#crachTable").hide();
		  $("#anrTable").show();
		  $("#topthree").hide();
		  $('#anrlogTable').bootstrapTable('refresh',{
		      url: "financeApplyLogs.json?t=" + Math.random()+"&type=anr"});
		  head = "anr";
	  }else{
		  $("#crachTable").show();
		  $("#anrTable").hide();
		  $("#topthree").show();
		  head = "error";
		  refresh(head,"");
	  }
	 
	  getData();

  });
  $(".cfR1aMZlBkE_yK7jWGQ-C").click(function(){
	  $(".cfR1aMZlBkE_yK7jWGQ-C").removeClass("active");
	  $(this).addClass("active");
	  changeOption($(this).attr("id"));
	  
  })
  
  $("#version2").change(function(){
	  getData();
  })
  
  $(".easyui-combobox").change(function(){
	  getData();
  })
   
  var dateType2 = {
    elem: '#dateType2',
    format: 'YYYY-MM-DD',
    max: '2099-06-16',
    istime: true,
    istoday: true,
    choose: function(datas){
    	getData();
    }
  };
  
  laydate(dateType2);
  
  // 加载完页面 查询数据
  getData();
  loadLogTable(head);
  loadTopTable();
	$('#anrlogTable').bootstrapTable({
		method:'get',
      url: "financeApplyLogs.json?t=" + Math.random()+"&type=anr&appId="+$("#appId").val(),
      //toolbar: '#toolbar',                //工具按钮用哪个容器
      striped: true,                      //是否显示行间隔色
      cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      pagination: true,                   //是否显示分页（*）
      sortable: false,                     //是否启用排序
      sortOrder: "asc",                   //排序方式
      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
      pageNumber:1,                       //初始化加载第一页，默认第一页
      pageSize: 4,                       //每页的记录行数（*）
      pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      strictSearch: true,
      showColumns: false,                  //是否显示所有的列
      showRefresh: false,                  //是否显示刷新按钮
      minimumCountColumns: 2,             //最少允许的列数
      clickToSelect: true,                //是否启用点击选中行
      height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
      uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
      showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
      cardView: false,                    //是否显示详细视图
      detailView: false,                     //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      columns: [
	  {
		  field: 'userId',
		  title: '用户Id'
	  },
      {
          field: 'packageName',
          title: '包名'
      }, {
          field: 'anrDownUrl',
          title: '下载地址',
          formatter:YST_APP.showDownUrl
      }, {
          field: 'createTime',
          title: '创建时间',
          formatter:YST_APP.showDate
      }],
      detailFormatter:function(index, row) {
    	  return '<table><tr>' +
			'<td style="border:0">' +
			'<p><font  color=blue>' + row.threadInfo +'&nbsp&nbsp'+ row.errorType + '&nbsp&nbsp'+ row.exceptInfo + '</font></p>' +
			'<p><br/>' + row.exceptStack + '</p>' +
			'</td>' +
			'</tr></table>';
      }
  });
		
});

function showDetail(index,type){
	var allTableData;
	if(type=="top20"){
		allTableData = $("#logTable").bootstrapTable('getData');
	}else{
		allTableData = $("#toplogTable").bootstrapTable('getData');
	}
	
	var errorType = allTableData[index].errorType;
	var exceptInfo = allTableData[index].exceptInfo;
	$.post('findRecentlyLogs.json', {errorType: errorType,exceptInfo:exceptInfo,type:head}, function (result) {
		if(result!=null){
			$("#userId").text(result.userId);
			$("#deviceId").text(result.deviceId);
			$("#userId").attr("title",result.userId);
			$("#occTime").text(result.occTime);
			$("#exceptionThread").text(result.packageName);
			$("#packageName").text(result.packageName);
			$("#exceptStack").text(result.exceptStack);
			$("#threadInfo").text(result.threadInfo);
			$("#romInfo").text(result.romInfo);
			$("#romInfo").attr("title",result.romInfo);
			$("#systemVersion").text(result.systemVersion);
			$("#device").text(result.device);
			$("#usedTime").text(result.usedTime);
			$("#appVersion").text(result.appVersion);
			$("#packageName").attr("title",result.packageName);
			$("#cpuInfo").text(result.cpuInfo);
			$("#reportTime").text(result.reportTime);
		}else{
			$("#userId").text("");
			$("#deviceId").text("");
			$("#occTime").text("");
			$("#exceptionThread").text("");
			$("#packageName").text("");
			$("#exceptStack").text("");
			$("#threadInfo").text("");
			$("#romInfo").text("");
			$("#systemVersion").text("");
			$("#device").text("");
			$("#usedTime").text("");
			$("#appVersion").text("");
			$("#cpuInfo").text("");
			$("#reportTime").text("");
		}
		
    });
	$("#myModal").modal();
	
	//清除弹窗原数据
	$("#myModal").on("hidden.bs.modal", function() {
	    $(this).removeData("bs.modal");
	});
}

function refresh(type,time){
	 var  url = "findTopLogs.json?type="+type+"&topType=top20&time="+time+"&appId="+$("#appId").val();
	 $("#logTable").bootstrapTable('refresh',{'url':url}); 
}
// top20数据表格
function loadLogTable(type){
	$('#logTable').bootstrapTable({
		method:'get',
      url: "findTopLogs.json?t=" + Math.random()+"&type="+type+"&topType=top20&appId="+$("#appId").val(),
      toolbar: '#toolbar',                //工具按钮用哪个容器
      striped: true,                      //是否显示行间隔色
      cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      pagination: false,                   //是否显示分页（*）
      sortable: false,                     //是否启用排序
      sortOrder: "asc",                   //排序方式
      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
      pageNumber:1,                       //初始化加载第一页，默认第一页
      pageSize: 4,                       //每页的记录行数（*）
      pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      strictSearch: true,
      showColumns: false,                  //是否显示所有的列
      showRefresh: false,                  //是否显示刷新按钮
      minimumCountColumns: 2,             //最少允许的列数
      clickToSelect: true,                //是否启用点击选中行
      height: 500,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
      uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
      showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
      cardView: false,                    //是否显示详细视图
      detailView: false,                     //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      columns: [
      {
          field: 'errorType',
          title: '错误类型',
          formatter:function (value, row, index) {
              if (!value) {
                  return "";
              } else {
                  return '<a href="javascript:void(0)" onclick= showDetail("'+ index + '","top20")>' + value + '</a>';
              }
          }
      }, {
          field: 'exceptInfo',
          title: '异常信息'
      }, {
          field: 'createTime',
          title: '创建时间',
          formatter:YST_APP.showDate
      }, {
          field: 'count',
          title: '发生次数'
      }, {
          field: 'userCount',
          title: '影响用户数'
      }, ]
  });
}
// top3数据表格
function loadTopTable(){
	
	$('#toplogTable').bootstrapTable({
		method:'get',
      //url: "findTopLogs.json?time=" + 10+"&type=error&topType=top3",
      toolbar: '#toolbar',                //工具按钮用哪个容器
      striped: true,                      //是否显示行间隔色
      cache: false,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
      pagination: false,                   //是否显示分页（*）
      sortable: false,                     //是否启用排序
      sortOrder: "asc",                   //排序方式
      sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
      pageNumber:1,                       //初始化加载第一页，默认第一页
      pageSize: 3,                       //每页的记录行数（*）
      pageList: [10, 25, 50, 100],        //可供选择的每页的行数（*）
      search: false,                       //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      strictSearch: true,
      showColumns: false,                  //是否显示所有的列
      showRefresh: false,                  //是否显示刷新按钮
      minimumCountColumns: 2,             //最少允许的列数
      clickToSelect: true,                //是否启用点击选中行
      //height: 180,                        //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度
      uniqueId: "ID",                     //每一行的唯一标识，一般为主键列
      showToggle:false,                    //是否显示详细视图和列表视图的切换按钮
      cardView: false,                    //是否显示详细视图
      detailView: false,                     //是否显示表格搜索，此搜索是客户端搜索，不会进服务端，所以，个人感觉意义不大
      columns: [
      {
          field: 'errorType',
          title: '错误类型',
          formatter:function (value, row, index) {
              if (!value) {
                  return "";
              } else {
                  return '<a href="javascript:void(0)" onclick= showDetail("'+ index + '","top3")>' + value + '</a>';
              }
          }
      }, {
          field: 'exceptInfo',
          title: '异常信息'
      }, {
          field: 'createTime',
          title: '创建时间',
          formatter:YST_APP.showDate
      }, {
          field: 'count',
          title: '发生次数'
      }, {
          field: 'userCount',
          title: '影响用户数'
      }, ]
  });
}

var option = getOption("全版本","(对比)全版本",[],[],["全版本","(对比)全版本"],[])
// 基于准备好的dom，初始化echarts实例
var myChart_program_fail = echarts.init(document
		.getElementById('crash_chart'));
$(function() {
	var xData = [ 1, 2, 3, 4, 5, 6, 7 ]
	myChart_program_fail.setOption(option);
});

//点击事件
myChart_program_fail.on('click', ego);

function ego(param) {
	var name = new Array;
	name = param.name;
	// 异常类型
	var exceptionType = $("._1uAed4rjAp9SovsdhYntOO").attr("queryValue");
    var statisticDateType = $("#dateType1").val();
	if ("anr" != exceptionType && statisticDateType == 0) {
		$("#topthree").show();
		var url = "findTopLogs.json?&type=" + exceptionType
				+ "&topType=top3&time=" + name + "&appId="+$("#appId").val();
		$("#toplogTable").bootstrapTable('refresh', {
			'url' : url
		});
		
		//refresh(head,name);
	}
}

function getData(){
	layer.load();
	// 统计维度
	var statisticType = $('input:radio[name="adminFlag"]:checked').val();
	// 异常类型
	var exceptionType = $("._1uAed4rjAp9SovsdhYntOO").attr("queryValue");
	// 统计版本
	var statisticVersion = $("#version1").val();
	// 统计日期类型
	var statisticDateType = $("#dateType1").val();

    if (statisticDateType!=0){
        $("#radioDiv").hide();
    } else {
        $("#radioDiv").show();
    }

	// 对比版本
	var compareVersion = $("#version2").val();
	// 对比日期类型
	var compareDateType = $("#dateType2").val();
	var data = {
		    statisticType: statisticType,
		    exceptionType: exceptionType,
		    statisticVersion: statisticVersion,
		    statisticDateType: statisticDateType,
		    compareVersion: compareVersion,
		    compareDateType: compareDateType,
		    appId:$("#appId").val()
		};
	$.post("get_exception_overview.json",data,function(result){
		layer.closeAll('loading');
		var obj=JSON.stringify(result);
		sessionStorage.setItem("result", obj);
		var str = sessionStorage.getItem("result");
		var json = JSON.parse(str);
        var headName = $('#headName').text();

		if (headName ==' 发生次数 ') {

			  var count = json.HEAD_COUNT;
			  $("#number_crash").html("&nbsp;" +count[0]);
			  $("#number_anr").html("&nbsp;" +count[1]);
			  $("#number_error").html("&nbsp;" +count[2]);
              $(".cfR1aMZlBkE_yK7jWGQ-C").removeClass("active");
              $("#COUNT").addClass("active");
		  }else if (headName == '影响用户数'){
			  $("#number_crash").html("&nbsp;" +json.HEAD_USER_COUNT[0]);
			  $("#number_anr").html("&nbsp;" +json.HEAD_USER_COUNT[1]);
			  $("#number_error").html("&nbsp;" +json.HEAD_USER_COUNT[2]);
              $(".cfR1aMZlBkE_yK7jWGQ-C").removeClass("active");
              $("#USER_COUNT").addClass("active");
		  }else if (headName == '用户异常率'){
            $("#number_crash").html("&nbsp;" +json.HEAD_COUNT_RATE[0]);
            $("#number_anr").html("&nbsp;" +json.HEAD_COUNT_RATE[1]);
            $("#number_error").html("&nbsp;" +json.HEAD_COUNT_RATE[2]);
            $(".cfR1aMZlBkE_yK7jWGQ-C").removeClass("active");
            $("#USER_PERCENT").addClass("active");

          }

		   var type1 =  $("#version1").find("option:selected").text().replace(/(^\s*)|(\s*$)/g, ""); 
		   var type2 =  $("#version2").find("option:selected").text().replace(/(^\s*)|(\s*$)/g, "");
			var legend = [type1];
			if("-" !== $("#version2").val()){
				legend = [type1,type2];
			}
			var data1 = json.USER_PERCENT;
			var data2 = json.COMPARE_USER_PERCENT;
			xData = json.date;	
			option = getOption(type1,type2,data1,data2,legend,xData);	
			myChart_program_fail.clear;
			myChart_program_fail.setOption( option,true);
	  });
}

function changeOption(query){
   var result = sessionStorage.getItem("result");
   var json = JSON.parse(result);
   var data1 = json.USER_PERCENT;
   var data2=  json.COMPARE_USER_PERCENT;
   if(query =='USER_COUNT'){
	   data1 = json.USER_COUNT;
	   data2 = json.COMPARE_USER_COUNT;
   } else if(query == 'USER_ONLINE'){
   	    data1 = json.USER_ONLINE;
		data2 = json.COMPARE_USER_ONLINE;
	}else if(query == "USER_PERCENT"){
		data1 = json.USER_PERCENT;
		data2 = json.COMPARE_USER_PERCENT;
	}else if(query == "COUNT"){
        data1 = json.COUNT;
        data2=  json.COMPARE_COUNT;
   }
   var type1 =  $("#version1").find("option:selected").text().replace(/(^\s*)|(\s*$)/g, ""); 
   var type2 =  $("#version2").find("option:selected").text().replace(/(^\s*)|(\s*$)/g, "");
	var legend = [type1];
	if("-" !== $("#version2").val()){
		legend = [type1,type2];
	}
	var xData = json.date;	
	option = getOption(type1,type2,data1,data2,legend,xData);	
	myChart_program_fail.clear;
	myChart_program_fail.setOption( option,true);
	}


   function getOption(type1,type2,data1,data2,legend,xData) {
	   var option = {
				splitLine : {
					show : true,
				},
				backgroundColor : "rgba(228, 128, 128, 0)",
				"tooltip" : {
					"trigger" : "axis",
				},
				"grid" : {
					"borderWidth" : 0,
					"top" : 90,
					"bottom" : 30,
					textStyle : {
						color : "#fff"
					}
				},
				"legend" : {
					x: 'center',
					top : '1%',
					textStyle : {
						color : '#90979c',
					},
					"data" : legend,
				},

				"calculable" : true,
				"xAxis" : [ {
					"type" : "category",
					"axisLine" : {
						lineStyle : {
							color : '#90979c'
						}
					},
					"splitLine" : {
						"show" : false,
						"lineStyle" : {
							"type" : "dashed",
						},
					},
					"axisTick" : {
						"show" : false
					},
					 
					"axisLabel" : {
						"interval" : 0,

					},
					"data" : xData,
				} ],
				"yAxis" : [ {
					"type" : "value",
					"splitLine" : {
						"show" : true,
						"lineStyle" : {
							"type" : "dashed",
						},
					},
					"axisLine" : {
						lineStyle : {
							color : '#90979c'
						}
					},
					"axisTick" : {
						"show" : false
					},
					"axisLabel" : {
						"interval" : 0,

					},
				} ],
				"series" : [{
					"name" : type1,
					"type" : "line",
					symbolSize : 10,
					symbol : 'circle',
					"itemStyle" : {
						"normal" : {
							"color" : "#42a5f5",
							"label" : {
								"show" : true,
								"textStyle" : {
									"color" : "#fff"
								},
							}
						}
					},
					"data" : data1,
				}  , 
				{
					"name" : type2,
					"type" : "line",
					"symbolSize" : 10,
					"symbol" : 'circle',
					"itemStyle" : {
						"normal" : {
							"color" : "purple",
							"label" : {
								"show" : true,
								"textStyle" : {
									"color" : "#fff"
								},
							}
						}
					},
					"data" :  data2,
				},    ]
			} 
	return option;
   }
