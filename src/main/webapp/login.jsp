<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>钦智防伪</title>
    <%@ include file="/include/qinzhilibs.jsp" %>
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
    <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <style type="text/css">
        #container {
            width: 358px;
            height: 400px;
            text-align:center;
            margin:0 auto;
            margin-top: 100px;
        }
    </style>
</head>
<body style="background:#2E3E4E;">

<div id="container">
    <div class="panel panel-default" style="border:none;">
                     <input type="hidden" id="result" value="${error}">
            <div  style="">
                <div style="margin:0 0 15px;font-size:40px;padding:20px 0 0 10px;"><%----%>
                    钦智防伪
                </div>
                <form action="<c:url value="/login" />" method="post">
                     <div class="form-group">
                        <label for="operatorLoginName" style="font-size:15px;">帐号类型：</label>
                        <input type="radio" style="width:75%;margin-left: 40px" class="form-control"  name="operatorLoginName"   value="1">管理员</input>
                        <input type="radio" style="width:75%;margin-left: 40px" class="form-control"  name="operatorLoginName"   value="0">商户</input>
                    </div>
                    <div class="form-group">
                        <label for="operatorLoginName" style="font-size:15px;">用户名：</label>
                        <input type="text" style="width:75%;margin-left: 40px" class="form-control"  name="operatorLoginName" id="operatorLoginName" placeholder="请输入用户名" value="admin">
                    </div>
                    <div class="form-group">
                        <label for="operatorPassword" style="font-size:15px;">密&nbsp;&nbsp;&nbsp;&nbsp;码：</label>
                        <input type="password" style="width:75%;margin-left: 40px" class="form-control" name="operatorPassword" id="operatorPassword" placeholder="请输入密码" value="admin">
                    </div>
                    <div class="form-group">
                        <button type="submit" id="sub" class="btn btn-large btn-block btn-primary" style="width:100%;margin-top:10px;font-size:25px;">登录</button>
                    </div>
                    <c:if test="${param.error}"><span style="color:red;">用户名密码输入错误,请重新输入！</span></c:if>
                </form>
            </div>
    </div>
</div>
<script type="text/javascript">
    $(function(){
    	var error = $("#result");
    	if(null !==error && "" !==error)
    		layer.msg(error, {icon: 5});
    })
    
    $(document).on("keydown", function(event) {
        if (event.keyCode == 13) {
            $("#sub").click();
        }
    });

    if (self != top){
        window.top.location = window.location;
    }
</script>

</body>
</html>