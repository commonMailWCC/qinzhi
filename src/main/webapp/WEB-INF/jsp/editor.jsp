<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>站点管理</title>
<%@ include file="/include/editor.jsp"%>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<style type="text/css">
</style>
</head>
<body style="width: 90%;">
	<div style="margin-left: 55px">
		<input type="hidden" id="key" value="${key}">
		<script id="editor" type="text/plain"
			style="width: 100%; height: 350px;"></script>
	</div>
	<div id="btns">
		<div></div>
	</div>
	<div></div>

	<script type="text/javascript">
		//实例化编辑器
		var ue = UE.getEditor('editor', {
			toolbars : [ [ 'save', '|','fullscreen', 'source', '|', 'undo', 'redo', '|',
					'bold', 'italic', 'underline', 'fontborder',
					'strikethrough', 'superscript', 'subscript',
					'removeformat', 'formatmatch', 'autotypeset', 'blockquote',
					'pasteplain', '|', 'forecolor', 'backcolor',
					'insertorderedlist', 'insertunorderedlist', 'selectall',
					'cleardoc', '|', 'rowspacingtop', 'rowspacingbottom',
					'lineheight', '|', 'customstyle', 'paragraph',
					'fontfamily', 'fontsize', '|', 'directionalityltr',
					'directionalityrtl', 'indent', '|', 'justifyleft',
					'justifycenter', 'justifyright', 'justifyjustify', '|',
					'touppercase', 'tolowercase', '|', 'link', 'unlink',
					'anchor', '|', 'imagenone', 'imageleft', 'imageright',
					'imagecenter', '|', 'simpleupload', 'insertimage',
					'emotion', 'scrawl', 'insertvideo', 'music', 'attachment',
					'map', 'gmap', 'insertframe', 'insertcode', 'webapp',
					'pagebreak', 'template', 'background', '|', 'horizontal',
					'date', 'time', 'spechars', 'snapscreen', 'wordimage', '|',
					'inserttable', 'deletetable', 'insertparagraphbeforetable',
					'insertrow', 'deleterow', 'insertcol', 'deletecol',
					'mergecells', 'mergeright', 'mergedown', 'splittocells',
					'splittorows', 'splittocols', 'charts', '|', 'print',
					'preview', 'searchreplace' ] ],
					autoHeightEnabled : false,
					autoFloatEnabled : true,
					labelMap : {
						'save' : '保存编辑内容'
					}
					,elementPathEnabled : false
					,wordCount :false
		});
		UE.commands['save'] = {
			execCommand : function() {
				var keyValue = $("#key").val();
				var dataValue = UE.getEditor('editor').getContent();
				$.post("to_save", {
					key : keyValue,
					data : dataValue
				}, function(data) {
					uid = ue.trigger('showmessage', {
						content : '保存成功',
						timeout : 2000
					});
				});
			},
		};
		$(document).ready(function() {
			var proinfo = '${htmlValue}';
			ue.ready(function() {//编辑器初始化完成再赋值  
				ue.setContent(proinfo); //赋值给UEditor  
			});

		});

		function getContent() {
			var keyValue = $("#key").val();
			var dataValue = UE.getEditor('editor').getContent();
			$.post("to_save", {
				key : keyValue,
				data : dataValue
			}, function(data) {
			});
		}
	</script>
</body>
</html>