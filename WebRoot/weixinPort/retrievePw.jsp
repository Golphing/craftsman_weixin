<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
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
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="../views/css/import.css" />
<link href="css/mobiscroll.custom-2.5.0.min.css" rel="stylesheet"
	type="text/css" />
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
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(function() {	
		$('#btn').click(function() {
		var url=window.location.href;
var openId=url.split("=")[1];
			var telephone = $('input#telephone').val();
			var password = $('input#password').val();
			var yzm= $('input#yzm').val();
			var password1 = $('input#password1').val();
			if (telephone == "") {
				alert("请输入手机号!");
				return false;
			}else if(yzm==""){
			alert("请输入验证码!");
			return false;
			}
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if(!myreg.test(telephone)) 
				{ 
    			alert('请输入有效的手机号码！'); 
    			return false; 
				} 
			var request ="http://weixin.craftsmanasia.com/craftsman_weixin/user/findPwd.do?telephone="+telephone+"&yzm="+yzm;
			
			$.post(request, function(data) {
			var jsonObj = eval("(" + data + ")");
			alert(jsonObj.status);
			/* if(jsonObj.status=="验证码不正确"){alert(jsonObj.status);}
			else if(jsonObj.status=="已存在该电话"){alert(jsonObj.status);}else{
			location.href = "importResume.jsp?userId="+jsonObj.status;} */
			
		});
		});
	});

</script>

</head>
<body>
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title">找回密码</div>
			<div class="btn_ch_r"></div>
		</div>
		<div class="log_box">


			<ul>
				<form action="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/user/login.do" method="get">
					<li class="telephone"><input type="text" 
						placeholder="手机号" id="telephone" />
					</li>
					<br /><br />
				<li class="telephone" ><input type="text" style="width:40%;margin-left:auto;margin-right:auto;float: left;"
						placeholder="验证码" id="yzm" /><input class="button blue"  style="float: right;" type="button" value="获取验证码" id="getyzm" onclick="settime(this)"></input>
					</li><br />
				<li class="login_free"></li>
				<li style="text-align:center;" id="pw" hidden></li>
				<li class="btn_submit">
				<br/>
					<button type="button" id="btn" >提交</button>
				</li>
				</form>
			</ul>
		</div>

		<script type="text/javascript" src="js/DeleteSession.js"></script>

		</script>
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
	<div class="overlay">&nbsp;</div>
	<div class="showbox" id="AjaxLoading">
		<div class="loadingWord" style="overflow:hidden;zoom:1">
			<img src="../images/loadingx.gif" alt=""
				style="float:left;padding-top:6px;" />跳转中，请稍候...
		</div>
	</div>
	<script type="text/javascript" src="../js/ShowTip.js"></script>
	<script src="js/mobiscroll.custom-2.5.0.min.js" type="text/javascript"></script>

	<script type="text/javascript">
	 $(function () {
            $('#getyzm').click(function () {
            var telephone = $('input#telephone').val();
	if(telephone==""){
	alert("请先输入手机号！");}else{
	var request ="http://weixin.craftsmanasia.com/craftsman_weixin/yzm/sendV.do?phoneNumber="+telephone;
	$.post(request, function(data) {
	var jsonObj = eval("(" + data + ")");
	
	if(jsonObj.status==-3){
	alert("请填写正确的手机号！");}else{var count = 60;
                var countdown = setInterval(CountDown, 1000);
                function CountDown() {
                    $("#getyzm").attr("disabled", true);
                    $("#getyzm").val("请在" + count + " 秒后再次获取!");
                    if (count == 0) {
                        $("#getyzm").val("重新获取验证码").removeAttr("disabled");
                        clearInterval(countdown);
                    }
                    count--;
                }
                }
	});}
	
            
               
            });
        });
	
	
	</script>

</body>
</html>
