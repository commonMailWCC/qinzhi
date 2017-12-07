<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>钦智防伪</title>
<%@ include file="/include/qinzhilibs.jsp" %>
<script src="${cxp}/js/bootstrapValidator.js"></script>
<link href="${cxp}/js/bootstrapValidator.css" rel="stylesheet">
<style type="text/css">  
.form{
width:800px;
margin:50px auto;
}  
.container{
  border: 1;
}
</style>
</head>
<body >
   <div class="container">  
           <div class="form row">  
               <form  class="form-horizontal col-sm-offset-3 col-md-offset-3" id="register_form">  
                   <div class="col-sm-9 col-md-9">  
                       <div class="form-group">  
                           <i class="fa fa-user fa-lg">商户登录账号[手机号]</i>  
                           <input class="form-control required" type="text" placeholder="请输入手机号" name="operatorLoginName" autofocus="autofocus" width="90%"/>  
                       </div>  
                       <div class="form-group">  
                               <i class="fa fa-lock fa-lg">登录密码</i>  
                               <input class="form-control required" type="password" placeholder="输入密码" id="register_password" name="pwd1"/>  
                       </div>  
                       <div class="form-group">  
                               <i class="fa fa-check fa-lg">重复密码</i>  
                               <input class="form-control required" type="password" placeholder="重新输入密码" name="pwd2"/>  
                       </div>
                       <div class="form-group">  
                               <i class="fa fa-envelope fa-lg">企业名称</i>  
                               <input class="form-control required" type="text" placeholder="请输入企业名称" name="operatorName"/>  
                       </div> 
                       <div class="form-group">  
                               <i class="fa fa-male fa-lg">企业联系人</i>  
                               <input class="form-control required" type="text" placeholder="企业联系人" name="operatorContact"/>  
                       </div>   
                       <div class="form-group">  
                               <i class="fa fa-envelope fa-lg">商户邮箱</i>  
                               <input class="form-control eamil" type="text" placeholder="Email" name="operatorEmail"/>  
                       </div>
                       <div class="form-group">  
                              <i class="fa fa-qq fa-lg">QQ</i>  
                              <input class="form-control" type="text" placeholder="请输入QQ" name="operatorQq"/>  
                      </div>
                       <div class="form-group">  
                              <i class="fa fa-location-arrow fa-lg">详细地址</i>  
                              <input class="form-control required" type="text" placeholder="请输入详细地址" name="operatorAddress"/>  
                      </div>     
                       <div class="form-group">  
                           <input type="submit" class="btn btn-success pull-right" value="注册"/>  
                       </div>  
                   </div>  
               </form>  
           </div>  
           </div>  
<script type="text/javascript">
    $(function(){/* 文档加载，执行一个函数*/
         $('#register_form').bootstrapValidator({
             message: 'This value is not valid',
             feedbackIcons: {/*input状态样式图片*/
                 valid: 'glyphicon glyphicon-ok',
                 invalid: 'glyphicon glyphicon-remove',
                 validating: 'glyphicon glyphicon-refresh'
             },
             fields: {/*验证：规则*/
                 operatorLoginName: {//验证input项：验证规则
                     message: 'The operatorLoginName is not valid',
                     validators: {
                         notEmpty: {//非空验证：提示消息
                             message: '用户名不能为空'
                         },
                        stringLength: {
                            min: 11,
                            max: 11,
                            message: '请输入11位手机号码'
                        },
                        regexp: {
                            regexp: /^1[3|5|8|7]{1}[0-9]{9}$/,
                            message: '请输入正确的手机号码'
                        },
                        threshold :  6 , //有6字符以上才发送ajax请求，（input中输入一个字符，插件会向服务器发送一次，设置限制，6字符以上才开始）
                        remote: {//ajax验证。server result:{"valid",true or false} 向服务发送当前input name值，获得一个json数据。例表示正确：{"valid",true}  
                             url: 'check_login_name_exists',//验证地址
                             message: '用户已存在',//提示消息
                             delay :  200,//每输入一个字符，就发ajax请求，服务器压力还是太大，设置2秒发送一次ajax（默认输入一个字符，提交一次，服务器压力太大）
                             type: 'POST'//请求方式
                         },
                     }
                 },
                 pwd1: {
                     message:'密码无效',
                     validators: {
                         notEmpty: {
                             message: '密码不能为空'
                         },
                         stringLength: {
                             min: 6,
                             max: 30,
                             message: '密码长度必须在6到30之间'
                         },
                         identical: {//相同
                             field: 'pwd2', //需要进行比较的input name值
                             message: '两次密码不一致'
                         },
                         different: {//不能和用户名相同
                             field: 'operatorLoginName',//需要进行比较的input name值
                             message: '不能和用户名相同'
                         },
                         regexp: {
                             regexp: /^[a-zA-Z0-9_\.]+$/,
                             message: 'The operatorLoginName can only consist of alphabetical, number, dot and underscore'
                         }
                     }
                 },
                 pwd2: {
                     message: '密码无效',
                     validators: {
                         notEmpty: {
                             message: '密码不能为空'
                         },
                         stringLength: {
                             min: 6,
                             max: 30,
                             message: '密码长度必须在6到30之间'
                         },
                         identical: {//相同
                             field: 'pwd1',
                             message: '两次密码不一致'
                         },
                         regexp: {//匹配规则
                             regexp: /^[a-zA-Z0-9_\.]+$/,
                             message: 'The operatorLoginName can only consist of alphabetical, number, dot and underscore'
                         }
                     }
                 },
                 operatorEmail: {
                     validators: {
                         notEmpty: {
                             message: '邮件不能为空'
                         },
                         emailAddress: {
                             message: '请输入正确的邮件地址如：123@qq.com'
                         }
                     }
                 },
                 phone: {
                     message: 'The phone is not valid',
                     validators: {
                         notEmpty: {
                             message: '手机号码不能为空'
                         },
                         stringLength: {
                             min: 11,
                             max: 11,
                             message: '请输入11位手机号码'
                         },
                         regexp: {
                             regexp: /^1[3|5|8]{1}[0-9]{9}$/,
                             message: '请输入正确的手机号码'
                         }
                     }
                 },
                 invite: {
                     message: '邀请码',
                     validators: {
                         notEmpty: {
                             message: '邀请码不能为空'
                         },
                         stringLength: {
                             min: 8,
                             max: 8,
                             message: '请输入正确长度的邀请码'
                         },
                         regexp: {
                             regexp: /^[\w]{8}$/,
                             message: '请输入正确的邀请码(包含数字字母)'
                         }
                     }
                 },
             }
         })
         .on('success.form.bv', function(e) {//点击提交之后
             // Prevent form submission
             e.preventDefault();

             // Get the form instance
             var $form = $(e.target);

             // Get the BootstrapValidator instance
             var bv = $form.data('bootstrapValidator');

             saveUser(); 
         });
    });
    
    $(document).on("keydown", function(event) {
        if (event.keyCode == 13) {
            $("#sub").click();
        }
    });
    
    function saveUser() {
        //jquery 表单提交 
        $.ajax({ type: "POST",
                 url:"signup",
                 data:$('#register_form').serialize() , success: function(data){
                     if(data=='success'){
                           layer.msg("注册成功-进入登录页面");
                           window.top.location.href='${cxp}/login';
                           return false;   
                     }else{
                         layer.msg("注册失败!");
                         return false;
                     }
                 }, error: function(data) {
                     layer.msg(data);
                     return false;
                 }});
           return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转 
    }
</script>
</body>
</html>