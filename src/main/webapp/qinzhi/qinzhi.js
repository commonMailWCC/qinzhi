var tip = "温馨提示：";
var time = 5000;
var rFrameHeight = window.innerHeight-24;
var hFrameHeight = window.innerHeight;
var limit = 20;

var srcElement = null;
var valueElement = null;

function showTree(item, valueId, combdtree) {
    srcElement = item;
    valueElement = document.getElementById(valueId);
    var x = getLeft(item);
    var y = getTop(item) + item.offsetHeight;
    var w = item.offsetWidth;
    //blockDTree(x,y,w);
    var show = document.getElementById(combdtree);
    show.style.position = "absolute";
    show.style.left = x;
    show.style.top = y;
    show.style.width = w + 20;
    show.style.display = "block";
}

function hiddenDTree(combdtree) {
    var item = document.getElementById(combdtree);
    if (item) {
        item.style.display = 'none';
    }
}

function setSrcValue(text, value, combdtree) {
    srcElement.value = text;
    valueElement.value = value;
    hiddenDTree(combdtree);
}

function getTop(e) {
    var offset = e.offsetTop;
    if (e.offsetParent != null) offset += getTop(e.offsetParent);
    return offset;
}


function getLeft(e) {
    var offset = e.offsetLeft;
    if (e.offsetParent != null) offset += getLeft(e.offsetParent);
    return offset;
}
function isChinese(s) {
    // 正则表达式对象
    var re = new RegExp("[\\u4e00-\\u9fa5]", "");
    // 验证是否刚好匹配
    return re.test(s);
}

var maxtime = "1";
function countDown() {
    if (maxtime < 30) {
        msg = "温馨提示：数据正在处理中，请耐心等待... " + maxtime + " 秒";
        $("#show").text(msg);
        ++maxtime;
    }
}

function showDiv() {
    $("#bg").fadeIn(1000);
    var $loader;
	var totalKb = 100;
	var kb = 0;
	$loader  = $("#show").percentageLoader({
		width : 180, 
		height : 180, 
		progress : 0
	});
    var animateFunc = function() {
		kb += 1;	    
		if (kb > totalKb) {
			kb = totalKb;  
		}
		$loader.setProgress(kb / totalKb);
		$loader.setValue(kb.toString() + '%');
		if (kb < totalKb) {
			setTimeout(animateFunc, 1000);
		} 
    };
    setTimeout(animateFunc, 1000);
    $("#show").fadeIn();
}
function hideDiv() {
    $("#bg").fadeOut(1000);
    $("#show").fadeOut(1000);
}

function getCurrentDate() {
    var now = new Date();
    return now.getFullYear()
        + "-" + ((now.getMonth() + 1) < 10 ? "0" : "") + (now.getMonth() + 1)
        + "-" + (now.getDate() < 10 ? "0" : "") + now.getDate();
}

function getCurrentTime() {
    var now = new Date();
    return now.getFullYear() + "-"
        + ((now.getMonth() + 1) < 10 ? "0" : "")
        + (now.getMonth() + 1) + "-"
        + (now.getDate() < 10 ? "0" : "") + now.getDate()
        + " " + (now.getHours() < 10 ? "0" : "") + now.getHours() + ":"
        + (now.getMinutes() < 10 ? "0" : "") + now.getMinutes() + ":"
        + (now.getSeconds() < 10 ? "0" : "") + now.getSeconds();
}

var LOCK = {};

$(function(){
    $(document).ajaxSend(function (e, xhr, settings) {
        var url = settings.url;
        var index = url.indexOf("?");
        var key = url;
        if (index > 7) {
            key = url.substring(7, index);
        }
        if (url.indexOf("verify") == 0) {
            if (LOCK[key]) {
                $.omMessageBox.alert({
                    type: 'alert',
                    title: '温馨提示',
                    content: '请不要频繁提交数据！'
                });
                xhr.abort();
            } else {
                settings.url = url.substring(7);
                LOCK[key] = new Date().getTime();
            }
        }
    }).ajaxComplete(function (e, xhr, setting) {
        var url = setting.url;
        var index = url.indexOf("?");
        var key = url;
        if (index > 0) {
            key = url.substring(0, index);
        }
        LOCK[key] = undefined;
        LOCK["verify_" + key] = undefined;
    })
})

$.ajaxSetup({
    timeout : 60000
});

//added by frank
var YST_APP = (function(){
    var APP = {};
    APP.Error = {};
    APP.YES_NO = [{text:'是',value:'1'},
        {text:'否',value:'0'}];

    APP.STATUS = [{
        text:"有效",value:"1"
    },{
        text:"无效",value:"0"
    }];
    APP.ON_OFF = [
        {text:"请选择",value:""},
        {text:"开机",value:"ON"},
        {text:"关机",value:"OFF"}
    ];

    APP.YES_NO_FUNC = function(value){
        if (value == 1) {
            return "是";
        }
        if (value == 0) {
            return "否";
        }
        return "";
    };

    APP.Error.errorPlacement = function (error, element) {
        if (error.html()) {
            $(element).parents().map(function () {
                if (this.tagName.toLowerCase() == 'td') {
                    var attentionElement = $(this).next().children().eq(0);
                    attentionElement.html(error);
                }
            });
        }
    };

    APP.Error.showErrors = function (errorMap, errorList) {
        if (errorList && errorList.length > 0) {
            $.each(errorList, function (index, obj) {
                var msg = this.message;
                $(obj.element).parents().map(function () {
                    if (this.tagName.toLowerCase() == 'td') {
                        var attentionElement = $(this).next().children().eq(0);
                        attentionElement.show();
                        attentionElement.html(msg);
                    }
                });
            });
        } else {
            $(this.currentElements).parents().map(function () {
                if (this.tagName.toLowerCase() == 'td') {
                    $(this).next().children().eq(0).hide();
                }
            });
        }
        this.defaultShowErrors();
    };

    APP.alertMsg = function(msg){
        $.omMessageBox.alert({
            type: 'alert',
            title: "温馨提示",
            content: msg
        });
    };

    APP.convertIds2Str = function(selections){
        var toDeleteRecordID = "";
        if(!$.isArray(selections)) return toDeleteRecordID;
        $.each(selections,function(i,value){
            if (i != selections.length - 1) {
                toDeleteRecordID += selections[i].id + ",";
            } else {
                toDeleteRecordID += selections[i].id;
            }
        });
        return toDeleteRecordID;
    };

    APP.show_message = function(msg,type){
        $.omMessageTip.show({title: tip, content: msg, type: type, timeout: time});
    };

    APP.showDate = function(value){
        return new Date(value).Format("yyyy-MM-dd hh:mm:ss");
    };
    
    APP.showDownUrl = function (value, rowData, rowIndex) {
        if (!value) {
            return "";
        } else {
            return '<a href = "' + value + '" target="_blank">' + value + '</a>';
        }
    }

    return APP;
}());

Date.prototype.Format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)){
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o){
        if (new RegExp("(" + k + ")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

//add by mwl
/*过滤字符串的前后空格*/
function filterStartEndSpaceTrim(str){   
    str = str.replace(/^(\s|\u00A0)+/,'');   
    for(var i=str.length-1; i>=0; i--){   
        if(/\S/.test(str.charAt(i))){   
            str = str.substring(0, i+1);   
            break;   
        }   
    }   
    return str;   
}  

var docEle = function() {  
    return document.getElementById(arguments[0]) || false;  
}  

function closeNewDiv(_id){
	var _id = _id+"newDiv";
	var m = _id+"mask";  
//	//关闭新图层和mask遮罩层  
//	if (document.all) {  
//        window.detachEvent("onscroll", newDivCenter);  
//    }  
//    else {  
//        window.removeEventListener('scroll', newDivCenter, false);  
//    }  
	if(docEle(_id)){
		document.body.removeChild(docEle(_id));  
	}
	if(docEle(m)){
		document.body.removeChild(docEle(m));  
	}
}

function openNewDiv(_id) {  
	var _id = _id+"newDiv";
	var m = _id+"mask";  
  if (docEle(_id)) document.body.removeChild(docEle(_id));  
  if (docEle(m)) document.body.removeChild(docEle(m));  

  //mask遮罩层  

  var newMask = document.createElement("div");  
  newMask.id = m;  
  newMask.style.position = "absolute";  
  newMask.style.zIndex = "9998";  
  _scrollWidth = Math.max(document.body.scrollWidth, document.documentElement.scrollWidth);  
  _scrollHeight = Math.max(document.body.scrollHeight, document.documentElement.scrollHeight);  
  newMask.style.width = _scrollWidth + "px";  
  newMask.style.height = _scrollHeight + "px";  
  newMask.style.top = "0px";  
  newMask.style.left = "0px";  
  newMask.style.background = "#33393C";  
  newMask.style.filter = "alpha(opacity=40)";  
  newMask.style.opacity = "0.40";  
  document.body.appendChild(newMask);  

  //新弹出层  

  var newDiv = document.createElement("div");  
  newDiv.id = _id;  
  newDiv.style.position = "absolute";  
  newDiv.style.zIndex = "9999";  
  newDivWidth = 50;  
  newDivHeight = 50;  
  newDiv.style.width = newDivWidth + "px";  
  newDiv.style.height = newDivHeight + "px";  
  newDiv.style.top = (document.body.scrollTop + document.body.clientHeight / 2 - newDivHeight / 2) + "px";  
  newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2) + "px";  
//  newDiv.style.background = "#33393C";  
//  newDiv.style.opacity = "0.40"; 
//  newDiv.style.border = "1px solid #860001";  
  newDiv.style.padding = "5px";  
//  newDiv.innerHTML = "加载数据中,请等待... ";  

  document.body.appendChild(newDiv);  
  
  var newImg = document.createElement("img"); 
  newImg.src = vPath+"/images/lvomsimg/wait.gif";//vPath
  newDiv.appendChild(newImg);  

  //弹出层滚动居中  

//  function newDivCenter() {  
//      newDiv.style.top = (document.body.scrollTop + document.body.clientHeight / 2 - newDivHeight / 2) + "px";  
//      newDiv.style.left = (document.body.scrollLeft + document.body.clientWidth / 2 - newDivWidth / 2) + "px";  
//  }  
//  if (document.all) {  
//      window.attachEvent("onscroll", newDivCenter);  
//  }  
//  else {  
//      window.addEventListener('scroll', newDivCenter, false);  
//  }  

//  //关闭新图层和mask遮罩层  
//
//  var newA = document.createElement("a");  
//  newA.href = "#";  
//  newA.innerHTML = "关闭";  
//  newA.onclick = function() {  
//      if (document.all) {  
//          window.detachEvent("onscroll", newDivCenter);  
//      }  
//      else {  
//          window.removeEventListener('scroll', newDivCenter, false);  
//      }  
//      document.body.removeChild(docEle(_id));  
//      document.body.removeChild(docEle(m));  
//      return false;  
//  }  
//  newDiv.appendChild(newA);  
}  