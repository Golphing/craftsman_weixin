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
	filter: alpha(opacity =     0);
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
	var telephone="";
	var password = "";
	var url=window.location.href;
var openId=url.split("?")[1].split("=")[1];
document.getElementById("register").href="http://weixin.craftsmanasia.com/craftsman_weixin/weixinPort/register.jsp?openId="+openId;

		$('#btn').click(function() {
			telephone = $('input#telephone').val();
			password = $('input#password').val();
			
			if(telephone==""){
			alert("手机号不能为空！");
			return false;
			}else{
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			if(!myreg.test(telephone)) 
				{ 
    			alert('请输入有效的手机号码！'); 
    			return false; 
				} 
			};
			
			
			if(password==""){
			alert("密码不能为空！");
			return false;
			}
			var request ="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/user/userlogin.do?telephone="+ telephone+ "&password="+ password+ "&openId=" + openId;
							$.post(request,	function(data) {
							var i = data.indexOf("&");
							if(i<0){
							alert(data);
							}else{
							var userId=data.split("&")[1];
							i = data.split("&")[0];
							if(i==1){
							location.href = "importResume.jsp?userId="+ userId;
							}else if(i==2){
							location.href = "fillWork.jsp?userId="+ userId;
							}else if(i=3){
							location.href = "myResume.jsp?userId="	+ userId;
							}else{
							alert("请稍后重试");
							}
							}
							});
			
			});
			
	});
</script>

</head>
<body>
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title">用户登陆</div>
			<div class="btn_ch_r">
				<a id="register" style="color:#FDFAFA">注册</a>
			</div>
		</div>
		<div class="log_box">


			<ul>
				<form action="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/user/login.do" method="get">
					<li class="telephone"><input type="text" value=""
						placeholder="手机号" id="telephone" /></li> <br />
					<br />
					<li class="telephone"><input type="password" value=""
						placeholder="密码" id="password" /></li>


					<li class="login_free"></li>
					<li class="btn_submit"><br />
						<button type="button" id="btn">登陆</button></li>
				</form>
			</ul>
		</div>

		<script type="text/javascript" src="js/DeleteSession.js"></script>

		</script>
		<div class="footer">
			<div class="footer_top">
				<ul class="user_info">
            <span><a href="retrievePw.jsp">找回密码</a> </span>
        </ul>

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






	<script src="js/mobiscroll.custom-2.5.0.min.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(function() {
			$("#birthday").mobiscroll().date();

			var currYear = (new Date()).getFullYear();

			//初始化日期控件
			var opt = {
				preset : 'date', //日期，可选：date\datetime\time\tree_list\image_text\select
				theme : 'default', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp
				display : 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
				mode : 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
				lang : 'zh',
				dateFormat : 'yyyy-mm-dd', // 日期格式
				setText : '确定', //确认按钮名称
				cancelText : '取消',//取消按钮名籍我
				dateOrder : 'yyyymmdd', //面板中日期排列格式
				dayText : '日',
				monthText : '月',
				yearText : '年', //面板中年月日文字
				showNow : false,
				nowText : "今",
				startYear : 1950, //开始年份  
				endYear : 2015
			//结束年份  
			//endYear:2099 //结束年份
			};

			$("#scroller").mobiscroll(opt);
		});
	</script>

</body>
</html>
