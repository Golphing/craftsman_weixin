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

*{margin:0px; padding:0px;}
body{font-family: Arial, Helvetica, sans-serif;font-size: 12px;}
.cur{cursor:pointer; display:block;color:#080808;width:146px; height:22px; line-height:22px; padding:0px 0px 0px 2px;}
.am{border: 0px;color:#080808;cursor: pointer;background:#fff url('0.gif') no-repeat; width: 150px;height: 19px;margin:10px 0px 0px 10px; padding:3px 0px 0px 2px;}
.bm{border: 1px #999999 solid ;width: 148px;}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
	var name="";
			var gender ="";
			var email = "";
			var home = "";
			var birthday = "";
			var telephone = "";
			var url=window.location.href;
var userId=url.split("=")[1];
$.get("<%=basePath%>resumeAction/search/resume.do?userId="+userId+"&t="+Math.random(), function(data) {
			var obj = eval("(" + data + ")").data;
			 $('input#name').attr('value',obj.name); 
			 $('input#gender').attr('value',obj.gender);
			 $('input#email').attr('value',obj.email);
			 $('input#home').attr('value',obj.home);
			 
			 $('input#birthday').attr('value',obj.birthday);
			 $('input#telephone').attr('value',obj.telephone);	 
			document.getElementById("name").onfocus= function() {
            if(this.value==obj.name){
             this.value="";};
   			};
   			document.getElementById("name").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.name;
   			 };   			 
   			 document.getElementById("gender").onfocus= function() {
            if(this.value==obj.gender){
             this.value="";};
   			};
   			document.getElementById("gender").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.gender;
   			 };
   			 
   			 
   			 document.getElementById("email").onfocus= function() {
            if(this.value==obj.email){
             this.value="";};
   			};
   			document.getElementById("email").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.email;
   			 };
   			 
   			 
   			 document.getElementById("home").onfocus= function() 

		{
            if(this.value==obj.home){
             this.value="";};
   			};
   			document.getElementById("home").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.home;
   			 };
   			 
   			 
   			 document.getElementById("birthday").onfocus= function() {
            if(this.value==obj.birthday){
             this.value="";};
   			};
   			document.getElementById("birthday").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.birthday;
   			 };
   			 
   			 
   			  document.getElementById("telephone").onfocus= function() {
            if(this.value==obj.telephone){
             this.value="";};
   			};
   			document.getElementById("telephone").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.telephone;
   			 };
			
});




/*修改个人信息  */
		$('#btn').click(function() {
			name = $('input#name').val();
			var select = document.getElementById('gender');
			 gender = select.value;
			email = $('input#email').val();
		 home = $('input#home').val();
			 birthday = $('input#birthday').val();
			telephone = $('input#telephone').val();
			var reg = /^w+((-w+)|(.w+))*@[A-Za-z0-9]+((.|-)[A-Za-z0-9]+)*.[A-Za-z0-9]+$/;
 if (name == "") {
				alert("请输入姓名!");
				return false;
			} else if (gender == "性别") {
				alert("请选择性别!");
				return false;
			} else if (email == "") {
				alert("请输入邮箱！");
				return false;
			} else if (home == "") {
				alert("请输入地址！");
				return false;
			} else if (birthday == "") {
				alert("请输入出生年月！");
				return false;
			}		
			$.ajax({
				type : "POST",
				url : "<%=basePath%>resumeAction/resume/modify.do?t="+Math.random(),
				data : "userId="+userId+"&name="+name+"&gender="+gender+"&email="+email+"&home="+home+"&birthday="+birthday+"&telephone="+telephone,
				success : function(msg) {
				var jsonObj = eval("(" + msg + ")");
					if(jsonObj.status==true){
				
					window.location.href="myResume.jsp?userId="+userId;}else{alert("提交错误，请重新输入！");}
				}
			});
		});
		
	});
	
	
	function $$$(_sId){
 return document.getElementById(_sId);
 }
function hide(_sId)
 {$$$(_sId).style.display = $$$(_sId).style.display == "none" ? "" : "none";}
function pick(v) {
 document.getElementById('gender').value=v;
hide('HMF-1');
}
function bgcolor(id){
 document.getElementById(id).style.background="#F7FFFA";
 document.getElementById(id).style.color="#000";
}
function nocolor(id){
 document.getElementById(id).style.background="";
 document.getElementById(id).style.color="#788F72";
}
</script>

</head>
<body>
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title">个人信息</div>
			<div class="btn_ch_r"></div>
		</div>
		<div class="log_box">


			<ul>
				<form action="<%=basePath%>resumeAction/resume/create.do" method="get">
					<li class="username"><input type="text" 
						placeholder="姓名" id="name" />
					</li><br /><br />
					 <li class="username"><input type="text" 
						value="请选择" id="gender" onclick="hide('HMF-1')" />
						<div id="HMF-1" style="display: none " class="bm">
 <span id="a1" onclick="pick('男')" onMouseOver="bgcolor('a1')" onMouseOut="nocolor('a1')" class="cur">男</span>
 <span id="a2" onclick="pick('女')" onMouseOver="bgcolor('a2')" onMouseOut="nocolor('a2')" class="cur">女</span>
</div></li><br /><br />
					<li class="username"><input type="text" 
						placeholder="邮箱" id="email" />
					</li><br /><br />
					<li class="username"><input type="text" 
						placeholder="电话" id="telephone" />
					</li>
<br /><br />
					<li class="username"><input type="text"
						placeholder="地址" id="home" />
					</li>
<br /><br />
					<li class="username"><input type="text"
						placeholder="出生年月" id="birthday" />
					</li>
				</form>
				<li class="login_free"></li>
				<li class="btn_submit">
					<button type="button" id="btn">提交</button>
				</li>
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
		<div style="display:none;">
			
		</div>

	</div>
	
	<script type="text/javascript" src="../js/ShowTip.js"></script>





	<script src="js/mobiscroll.custom-2.5.0.min.js" type="text/javascript"></script>

	<script type="text/javascript">
			$(function() {
			

			//初始化日期控件
			var opt = {
				preset : 'date', //日期，可选：date\datetime\time\tree_list\image_text\select
				theme : 'default', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp
				display : 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
				mode : 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
				lang : 'zh',
				dateFormat : 'yyyy-mm', // 日期格式
				setText : '确定', //确认按钮名称
				cancelText : '取消',//取消按钮名籍我
				dateOrder : 'yyyymm', //面板中日期排列格式				
				monthText : '月',
				yearText : '年', //面板中年月日文字
				showNow : false,
				nowText : "今",
				startYear : 1950, //开始年份  
				endYear : 2015
			//结束年份  
			//endYear:2099 //结束年份
			};
			$("#birthday").mobiscroll(opt);
		});
</script>


</body>
</html>
