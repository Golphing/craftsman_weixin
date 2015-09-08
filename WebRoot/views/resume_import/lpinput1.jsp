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
	filter: alpha(opacity = 0);
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
</style>


</head>
<body>
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title"></div>
			<div class="btn_ch_r"></div>
		</div>
		<div class="log_box">

<form action="/craftsman_weixin/c/im/51.do" method="post">
			<ul>

		<input type="hidden" value="123" name="openId">
				<li class="username"><input type="text" value=""
					placeholder="用户名" id="name" /></li><br/>
				
				<li class="username"><input type="text" value=""
					placeholder="密码" id="password" /></li><br/>

				<li class="username"><input type="text" value=""
					placeholder="验证码" id="home" /><img src="" id="yzm"></li><br/>
					
				<li class="btn_submit">
					<button type="submit">导入</button></li>
			</ul>
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
				<li><a href="../../tzrl/default.htm">电脑版</a><span>|</span><a
					href="../../old/default.htm">普通版</a><span>|</span><a
					href="../fankui/default.htm">用户反馈</a><span>|</span><a
					href="../contact/default.htm">联系我们</a>
				</li>
				<li>&copy;2015</li>
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
  			$(".button").click(function(){
				 var name=$("input[name='name']").val();
				 var password=$("input[name='password']").val();
				 var openId=$("input[name='openId']").val();
				 var login_verify=$("input[name='login_verify']").val();
				 alert("导入中");
				 $.ajax({
			      url: '/craftsman_weixin/c/im/lp.do',
			      type: 'post',
			      dataType: 'json',
			      data: {
			        "name":name,
			        "password":password,
			        "openId":openId,
			        "login_verify":login_verify
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

	

















</body>
</html>
