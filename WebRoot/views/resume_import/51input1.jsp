<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>My JSP 'resumeinput.jsp' starting page</title>
    
    
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <script src="/craftsman_weixin/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	  <script type="text/javascript" src="http://s.ebaoyang.cn/custom-service/My97DatePicker/WdatePicker.js"></script>
	<style>
		span{
			display: none;
		}
	</style>
  </head>
  
  <body>
    	<form action="/craftsman_weixin/c/im/51.do" method="post">
    		<input type="hidden" value="123" name="openId">
    		用户名：<input name="name" type="text"/><br>
    		密码：<input name="password" type="password"/><br>
    		<span id="showVerify">
    		验证码：<input name="login_verify" /><img src="" id="yzm"><br></span>
    		<input type="button" value="导入" id="import">
    		
    	</form>
  </body>
  
  <script>
  		$(function(){
  			 var url=window.location.href;
  			var userId=url.split("=")[1];
  			alert(userId);
  			
  			$("#import").click(function(){
				 var name=$("input[name='name']").val();
				 var password=$("input[name='password']").val();
				 var openId=$("input[name='openId']").val();
				 var login_verify=$("input[name='login_verify']").val();
				 alert("导入中");
				 $.ajax({
			      url: '/craftsman_weixin/c/im/51.do',
			      type: 'post',
			      dataType: 'json',
			      data: {
			        "name":name,
			        "password":password,
			        "openId":openId,
			        "login_verify":login_verify,
			        "userId":userId
			      },
			      async : false,
			      success: function(result) {
			        if(result.code==0)
			        {
			         alert("导入成功！");
			        }
			        else if(result.code==1)
			        {
			          alert("需要验证码");
			          $("#yzm").attr("src",result.yzmUrl);
			          $("#showVerify").show();
			        }
			        else if(result.code==2)
			        {
			          alert("账号或密码错了");
			        }
			        else if(result.code==3)
			        {
			          alert("验证码错误");
			           $("#yzm").attr("src",result.yzmUrl);
			        }
			        else if(result.code==4)
			        {
			          alert("用户名或密码不能为空");
			        }
			        else if(result.code==5)
			        {
			          alert("没绑定手机号");
			        }
			        else if(result.code==6)
			        {
			          alert("用户名或密码错误");
			          $("#yzm").attr("src",result.yzmUrl);
			        }
			      }
			    });
			});
  			
  		});
  </script>
</html>
