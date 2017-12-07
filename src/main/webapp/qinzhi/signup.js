function saveUser() {
	//jquery 表单提交 
	$.ajax({ type: "POST",
		     url:"/non/signup",
		     data:$('#fm').serialize() , success: function(data){
		    	 if(data=='success'){
		    	       layer.msg("注册成功-进入登录页面");
		    	       window.location.href='/login';
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
