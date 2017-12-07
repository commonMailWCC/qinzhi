<%@ page language="java" pageEncoding="utf-8" isELIgnored="false" %>
<%@ taglib uri="/WEB-INF/tld/c.tld" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="shiro"  uri="http://shiro.apache.org/tags"%>
<%
    String cxp = request.getContextPath();
    request.setAttribute("cxp", cxp);
%>
<!DOCTYPE html>
<html>
<head>
    <title>站点管理</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>
    <link rel="shortcut icon" href="${cxp}/img/favicon.ico"> <link href="${cxp}/css/bootstrap.min.css?v=3.3.6" rel="stylesheet">
<link href="${cxp}/css/font-awesome.min.css?v=4.4.0" rel="stylesheet">
<link href="${cxp}/css/animate.css" rel="stylesheet">
<link href="${cxp}/css/mega-menu.css" rel="stylesheet">
<link href="${cxp}/css/style.css?v=4.1.0" rel="stylesheet">
<link href="${cxp}/css/plugins/bootstrap-table/bootstrap-table.min.css" rel="stylesheet">

<!-- 全局js -->
<script src="${cxp}/js/jquery.min.js?v=2.1.4"></script>
<script src="${cxp}/js/bootstrap.min.js?v=3.3.6"></script>
<script src="${cxp}/js/bootstrap-hover-dropdown.min.js"></script>
<script src="${cxp}/js/plugins/metisMenu/jquery.metisMenu.js"></script>
<script src="${cxp}/js/plugins/slimscroll/jquery.slimscroll.min.js"></script>
<script src="${cxp}/js/plugins/layer/layer.js"></script>
<script src="${cxp}/js/plugins/layer/laydate/laydate.js"></script>

<!-- 自定义js -->
<script src="${cxp}/js/hplus.js?v=4.1.0"></script>
<script type="text/javascript" src="${cxp}/js/contabs.js"></script>
<%--<script src="${cxp}/js/qinzhi.js"></script>--%>

<!-- 第三方插件 -->
<script src="${cxp}/js/plugins/bootstrap-table/bootstrap-table.min.js"></script>
<script src="${cxp}/js/plugins/bootstrap-table/bootstrap-table-mobile.min.js"></script>
<script src="${cxp}/js/plugins/bootstrap-table/locale/bootstrap-table-zh-CN.min.js"></script>
<script src="${cxp}/js/qinzhi/distpicker.js"></script>
<script type="text/javascript" charset="utf-8" src="${cxp}/editor/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${cxp}/editor/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${cxp}/editor/lang/zh-cn/zh-cn.js"></script>

    <style type="text/css">
        div{
            width:90%;
        }
    </style>
</head>
<body>
<div style="margin-left: 75px">
     <input type="hidden" id="key" value="${key}">
    <script id="editor" type="text/plain" style="width:100%;height:300px;"> ${htmlValue}</script>
</div>
<div id="btns">
    <div>
        <button onclick="getAllHtml()">保存</button>
         <button onclick="getContent()">获得内容</button>
        <button onclick="setContent()">写入内容</button>
        <button onclick="setContent(true)">追加内容</button>
        <button onclick="getContentTxt()">获得纯文本</button>
        <button onclick="getPlainTxt()">获得带格式的纯文本</button>
        <button onclick="hasContent()">判断是否有内容</button>
        <button onclick="setFocus()">使编辑器获得焦点</button>
        <button onmousedown="isFocus(event)">编辑器是否获得焦点</button>
        <button onmousedown="setblur(event)" >编辑器失去焦点</button> -->

    </div>
    <!-- <div>
        <button onclick="getText()">获得当前选中的文本</button>
        <button onclick="insertHtml()">插入给定的内容</button>
        <button id="enable" onclick="setEnabled()">可以编辑</button>
        <button onclick="setDisabled()">不可编辑</button>
        <button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
        <button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
        <button onclick=" UE.getEditor('editor').setHeight(300)">设置高度为300默认关闭了自动长高</button>
    </div>

    <div>
        <button onclick="getLocalData()" >获取草稿箱内容</button>
        <button onclick="clearLocalData()" >清空草稿箱</button>
    </div> -->

</div>
<div>
<!--     <button onclick="createEditor()">
    创建编辑器</button>
    <button onclick="deleteEditor()">
    删除编辑器</button> -->
</div>

<script type="text/javascript">
    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
   var ue = UE.getEditor('editor');
   
   $(document).ready(function(){  
      /*  var proinfo=${htmlValue};  
         console.log(proinfo)
       ue.ready(function() {//编辑器初始化完成再赋值  
           ue.setContent(proinfo);  //赋值给UEditor  
       });   */
   
   });  

    function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        var dataValue = UE.getEditor('editor').getAllHtml();
        console.log(dataValue);
        var keyValue = $("#key").val();
        $.post("to_save", { key: keyValue, data: dataValue },
 		   function(data){
 		   });
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        var keyValue = $("#key").val();
        var dataValue = UE.getEditor('editor').getContent();
        $.post("to_save", { key: keyValue, data: dataValue },
 		   function(data){
 		   });
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }
</script>
</body>
</html>