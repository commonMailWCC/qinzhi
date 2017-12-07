<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>钦智防伪</title>
    <%@ include file="/include/qinzhilibs.jsp" %>
    <style type="text/css">
        #container {
            width: 358px;
            height: 400px;
            margin:0 auto;
            margin-top: 100px;
        }
    </style>
</head>
<body style="background:#2E3E4E;">

<div id="container">
    <div class="panel panel-default" style="border:none;">
            <div  style="">
                <div style="margin:0 0 15px;font-size:40px;padding:20px 0 0 10px;text-align:center; "><%----%>
                    钦智防伪系统
                </div>
                <form action="<c:url value="/login" />" method="post">
                <input type="hidden" id="result" value="${error}">
                     <div class="form-group">
                        <label for="operatorLoginName" style="font-size:15px;">&nbsp;帐号类型：</label>
                        <input type="radio" style=""  name="operatorLoginName"   value="1">管理员&nbsp;&nbsp;&nbsp;</input>
                        <input type="radio" style="" name="operatorLoginName"   value="0" checked="checked" >商户</input>
                    </div>
                    <div class="form-group">
                        <label for="operatorLoginName" style="font-size:15px;">&nbsp;用户名：</label>
                        <input type="text" style="width:70%;margin-left: 60px;margin-top: -30px" class="form-control"  name="operatorLoginName" id="operatorLoginName" placeholder="请输入用户名" required=true>
                    </div>
                    <div class="form-group">
                        <label for="operatorPassword" style="font-size:15px;">&nbsp;密&nbsp;&nbsp;&nbsp;码：</label>
                        <input type="password" style="width:70%;margin-left: 60px;margin-top: -30px" class="form-control" name="operatorPassword" id="operatorPassword" placeholder="请输入密码" required=true>
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
    	var error = $("#result").val();
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