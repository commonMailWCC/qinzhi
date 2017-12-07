<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>钦智防伪</title>
<%@ include file="/include/qinzhilibs.jsp"%>
<%@ include file="/include/index.jsp"%>
<style type="text/css">
.header_foot {
	width: 100%;
	height: 44px;
	/* float: left; */
	background: url(${ctx}/img/nav_bj1.jpg) repeat-x;
	margin-bottom: 1px;
	/* display: inline; */
}

#content-main {
	height: calc(100% - 165px);
	overflow: hidden;
}
</style>

<script type="text/javascript">
	function SetHome(obj, url) {
		try {
			obj.style.behavior = 'url(#default#homepage)';
			obj.setHomePage(url);
		} catch (e) {
			if (window.netscape) {
				try {
					netscape.security.PrivilegeManager
							.enablePrivilege("UniversalXPConnect");
				} catch (e) {
					alert("抱歉，此操作被浏览器拒绝！\n\n请在浏览器地址栏输入“about:config”并回车然后将[signed.applets.codebase_principal_support]设置为'true'");
				}
			} else {
				alert("抱歉，您所使用的浏览器无法完成此操作。\n\n您需要手动将【" + url + "】设置为首页。");
			}
		}
	}
	function AddFavorite(title, url) {
		try {
			window.external.addFavorite(url, title);
		}
		catch (e) {
			try {
				window.sidebar.addPanel(title, url, "");
			}
			catch (e) {
				alert("抱歉，您所使用的浏览器无法完成此操作。\n\n加入收藏失败，请使用Ctrl+D进行添加");
			}
		}
	}
</script>

</script>
</head>
<body>
	<div id="header" style= "width:1000px;margin-left:150px; position: fixed; background-color: #dedad6">
		<div id="user_nav" style="text-align: left;float: left;">
		     <a href="/non/signup.html" class="nobg">&nbsp; &nbsp;&nbsp; 商户注册|</a>
			 <a href="/login">登录</a>
		</div>

       <div id="blog_site_nav" style=" float:right;">
			<a href="javascript:void(0);" onclick="SetHome(this,'http://www.qinzhi.com');">设为首页|</a>
            <a href="javascript:void(0);" onclick="AddFavorite('我的网站',location.href)">收藏本站&nbsp;</a>
		</div>
	</div>
	<div class="header_top">
		<div class="top_logo">
			<a title="防伪方案提供商" href="http://www.qinzhi.com/"><img
				src="${ctx}/img/logo.png"
				alt="防伪方案提供商"></a><span class="imgmid"></span>
		</div>
		<div class="top_rig">
			<div class="rig_foot">
				<div class="rig_rig" style="margin-top: 10px;">
					<span >服务热线：</span><strong style="color:black">400-00000000 <br>1xx-xxxx-xxxx
					</strong>
				</div>
			</div>
		</div>
		<div class="cle"></div>
	</div>
	<!-- <nav class="navbar navbar-inverse" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">菜鸟教程</a>
			</div>
			<div>
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">iOS</a></li>
					<li><a href="#">SVN</a></li>
					<li class="dropdown"><a href="#" class="dropdown-toggle"
						data-toggle="dropdown"> Java <b class="caret"></b>
					</a>
						<ul class="dropdown-menu">
							<li><a href="#">jmeter</a></li>
							<li><a href="#">EJB</a></li>
							<li><a href="#">Jasper Report</a></li>
							<li class="divider"></li>
							<li><a href="#">分离的链接</a></li>
							<li class="divider"></li>
							<li><a href="#">另一个分离的链接</a></li>
						</ul></li>
				</ul>
			</div>
		</div>
	</nav> -->
	<div class="header_foot">
		<div class="meun_nav">
			<ul>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">动态公告</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">网站首页</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">关于我们</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">行业应用</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">产品服务</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">合作案例</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_welcome.html" />">联系我们</a></li>
			</ul>
		</div>
	</div>
	<div class="row J_mainContent" id="content-main">
		<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
			src="<c:url value="/statistic/to_simple_statistic.html?appId=${appId}" />"
			frameborder="0" data-id="to_welcome.html" seamless></iframe>
	</div>
</body>
</html>