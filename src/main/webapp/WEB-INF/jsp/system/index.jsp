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
	background: url(${cxp}/img/nav_bj1.jpg) repeat-x;
	margin-bottom: 1px;
}

#content-main {
	height: calc(100% - 165px);
	overflow: hidden;
	text-align: center;
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
	$(document).ready(function() {
	    var trigger = $('.hamburger'),
	        overlay = $('.overlay'),
	        isClosed = false;
	    trigger.click(function() {
	        hamburger_cross();
	    });
	    function hamburger_cross() {
	        if (isClosed == true) {
	            overlay.hide();
	            trigger.removeClass('is-open');
	            trigger.addClass('is-closed');
	            isClosed = false;
	        } else {
	            overlay.show();
	            trigger.removeClass('is-closed');
	            trigger.addClass('is-open');
	            isClosed = true;
	        }
	    }
	    $('[data-toggle="offcanvas"]').click(function() {
	        $('#wrapper').toggleClass('toggled');
	    });
	});
</script>

</script>
</head>
<body style="color: #666;  margin-left: 10px; margin-right:10px ;" >
	<div id="header" style= "width:100%;margin-left:0px; position: fixed; background-color: #dedad6">
		<div id="user_nav" style="text-align: left;float: left;">
		     <c:if test="${empty user}">
		     <a href="<c:url value="/non/signup.html"/>"  class="J_menuItem">&nbsp; &nbsp;&nbsp; 商户注册|</a>
			 <a  href="<c:url value="/login" />">登录</a>
		     </c:if>
		     <c:if test="${not empty user}">
		      <span>&nbsp;&nbsp;欢迎:</span>${userType}-${user} <a  href="<c:url value="/logout" />" >退出</a>
		     </c:if>
		</div>

       <div id="blog_site_nav" style="float:right;">
			<a href="javascript:void(0);" onclick="SetHome(this,'http://www.qinzhi.com');">&nbsp;设为首页|</a>
            <a href="javascript:void(0);" onclick="AddFavorite('我的网站',location.href)">收藏本站&nbsp;</a>
		</div>
	</div>
	<div class="header_top">
		<div class="top_logo">
			<a title="防伪方案提供商" href="http://www.qinzhi.com/"><img
				src="${cxp}/img/logo.png"
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
	<div class="header_foot">
		<div class="meun_nav">
			<ul>
			    <shiro:hasPermission name="create_authority">
			    <li class="dropdown">
			            <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"> 站点管理 <b class="caret"></b> </a>
						<ul class="dropdown-menu" >
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=1" />">动态公告</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=2" />">网站首页</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=3" />">关于我们</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=4" />">行业应用</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=5" />">产品服务</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=6" />">合作案例</a> 
							 <a class="J_menuItem" href="<c:url value="/to_edit.html?key=7" />">联系我们</a> 
						</ul></li>
				 <li><a class="J_menuItem" href="<c:url value="/levelinfo"  />">会员权限</a></li> 		
				 <li><a class="J_menuItem"
					href="<c:url value="/web/to_operator_list" />">会员管理</a></li>	
				 <li><a class="J_menuItem"
					href="<c:url value="/to_goods_list" />">商品管理</a></li>	
				 <li><a class="J_menuItem"
					href="<c:url value="/to_push_list" />">极光推送</a></li>				
				 </shiro:hasPermission>	

				 <shiro:hasPermission name="user_manage">
                 <li class="dropdown">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-hover="dropdown"> 我是商户 <b class="caret"></b> </a>
            			<ul class="dropdown-menu" >
            				 <a class="J_menuItem" href="<c:url value="/to_goods_list" />?operatorId=${operatorId}">我的商品</a> 
            				 <a class="J_menuItem" href="<c:url value="/my_level"  />?operatorId=${operatorId}">会员权限</a> 
            				  <li><a class="J_menuItem" href="<c:url value="/web/to_operator_list" />?operatorId=${operatorId}">会员信息管理</a></li>	
            			</ul></li>
				 </shiro:hasPermission>
				 <c:if test="${empty user || userType=='商户'}">
				 <c:if test="${empty user}">		
					 <li><a class="J_menuItem"
						href="<c:url value="/to_show.html?key=1" />">动态公告</a></li>
				 </c:if>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=2" />">网站首页</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=3" />">关于我们</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=4" />">行业应用</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=5" />">产品服务</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=6" />">合作案例</a></li>
				<li><a class="J_menuItem"
					href="<c:url value="/to_show.html?key=7" />">联系我们</a></li>
				</c:if>
			</ul>
		</div>
	</div>
	<div class="row J_mainContent" id="content-main">
		<iframe class="J_iframe" name="iframe0" width="100%" height="100%"
			src="<c:url value="/to_show.html?key=1" />"
			frameborder="0" data-id="/to_show.html?key=1" seamless></iframe>
	</div>
</body>
</html>