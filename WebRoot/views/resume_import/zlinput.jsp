<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=yes" />
<meta name="format-detection" content="email=no" />
<title></title>
<link rel="stylesheet" type="text/css" href="../css/css.css" />
<link rel="stylesheet" type="text/css" href="../css/import.css" />
<script type="text/javascript"
	src="http://s.ebaoyang.cn/custom-service/My97DatePicker/WdatePicker.js"></script>

<meta content="" name="keywords" />
<style type="text/css">
.overlay {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 998;
	width: 100%;
	height: 100%;
	_padding: 0 20px 0 0;
	background-color: rgba(51, 51, 51, 0.8);
	display: none;
}

.showbox {
	position: fixed;
	top: 0;
	left: 50%;
	z-index: 9999;
	opacity: 0;
	filter: alpha(opacity =   0);
	margin-left: -80px;
}

* html .showbox,* html .overlay {
	position: absolute;
	top: expression(eval(document.documentElement.scrollTop) );
}

#AjaxLoading div.loadingWord {
	width: 180px;
	height: 50px;
	line-height: 50px;
	border: 2px solid #fe8323;
	background: #fff4e8;
	text-align: center;
}

span {
	display: none;
}
</style>


</head>
<body >
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title">智联简历导入</div>
			<div class="btn_ch_r"></div>
		</div>
		<div class="log_box">


<!--验证码中添加type=“text” 提交按钮加class=“button_blue” 提交按钮加父div-->
<form action="/craftsman_weixin/c/im/zl.do" method="post">
    		<input type="hidden" value="123" name="openId"></input>
    		用户名：<input name="name" type="text" value="2645442899@qq.com"/><br/>
    		密码：<input name="password" type="password" value="wanggaoping0306"/><br/>
    		<span id="showVerify">
    		验证码：<input name="login_verify" type="text"/><img src="" id="yzm"></img><br/></span>
    		<div style="text-align: center;"><input class="button blue"  type="button" value="导入" id="import"></input></div>
    		
    	</form>


    	
    	
		</div>
		<script type="text/javascript" src="js/DeleteSession.js"></script>

		<div class="footer">
			<div class="footer_top">

				<ul class="back_top">
					<a href="javascript:scroll(0,0)">TOP</a>
				</ul>
			</div>
			<ul class="copyright">
				<li>2015 &copy; Craftsman. ALL Rights Reserved.</li>
			</ul>
		</div>

	</div>

	</div>
	<div class="overlay">&nbsp;</div>
	<div class="showbox" id="AjaxLoading">
		<div class="loadingWord" style="overflow:hidden;zoom:1">
			<img src="../images/loadingx.gif" alt=""
				style="float:left;padding-top:6px;" />跳转中，请稍候...
		</div>
	</div>
	<script type="text/javascript" src="../js/ShowTip.js"></script>

	<script type="text/javascript" src="../js/jquery.js"></script>
<script>
	$(function(){
  			var url=window.location.href;
  			var userId=url.split("=")[1];
  			
  			$("#import").click(function(){
				 var name=$("input[name='name']").val();
				 var password=$("input[name='password']").val();
			
				 var login_verify=$("input[name='login_verify']").val();
				 alert("导入中");
				 $.ajax({
			      url: '/craftsman_weixin/c/im/zl.do',
			      type: 'post',
			      dataType: 'json',
			      data: {
			        "name":name,
			        "password":password,
			    	"userId":userId,
			        "login_verify":login_verify
			      },
			      async : false,
			      success: function(result) {
			        if(result.code==0)
			        {
			         alert("导入成功！");
			        }
			        else if(result.code==3)
			        {
			          alert("需要验证码");
			          $("#yzm").attr("src",result.yzmUrl);
			          $("#showVerify").show();
			        }
			        else if(result.code==2)
			        {
			          alert("账号或密码错了");
			        }
			        else if(result.code==4)
			        {
			          alert("验证码错误");
			           $("#yzm").attr("src",result.yzmUrl);
			        }
			        else if(result.code==1)
			        {
			          alert("导入失败");
			        }
			        else if(result.code==5)
			        {
			          alert("尚未创建简历");
			        }

			      }
			    });
			});
  			
  		});
</script>






</body>
</html>
