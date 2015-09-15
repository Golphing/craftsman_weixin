<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML>
<html>
<head>
<title></title>
<link href="css/bootstrap.css" rel='stylesheet' type='text/css' />
<!-- jQuery (necessary JavaScript plugins) -->
<script src="js/jquery-1.10.2.min.js"></script>
<!-- Custom Theme files -->
<link href="css/dashboard.css" rel="stylesheet">
<link href="css/style.css" rel='stylesheet' type='text/css' />

<!-- Custom Theme files -->
<!--//theme-style-->
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
<link
	href='http://fonts.googleapis.com/css?family=Ubuntu:300,400,500,700'
	rel='stylesheet' type='text/css'>
<link href='http://fonts.googleapis.com/css?family=Varela+Round'
	rel='stylesheet' type='text/css'>
<!-- start menu -->

<script type="text/javascript"> 

$(document).ready(function() {
var url=window.location.href;
var userId=url.split("=")[1];
/*  根据用户id得到个人信息*/	

$.get("<%=basePath%>admin/user/search/resume.do?userId="+userId, function(data) {
			var jsonObj = eval("(" + data + ")");
			var obj=jsonObj.data;//obj是一个包含多个选项的数组
			document.getElementById("email").innerHTML = obj.email;
			document.getElementById("name").innerHTML = obj.name;
			document.getElementById("telephone").innerHTML = obj.telephone;
			document.getElementById("home").innerHTML = obj.home;
			if(obj.gender=="女"){ $("img").attr("src","images/girl.jpg");
			}else{$("img").attr("src","images/boy.jpg");
			}
			var str='<div class="company"><h3 class="clr1">工作经历</h3>';
			for ( var i in obj.works) {
			var work = obj.works[i];
			 str+='<div class="company_details"><h4>'+work.company+"<span>"+work.beginTime+'--'+work.endTime+"</span>"+'</h4><h6>'+work.department+'</h6><p class="cmpny1">'+work.description+'</p></div></div>';

		 	}
		 	 document.getElementById("content").innerHTML=str; 
		});

	})
</script>


</head>
<body>
	<!-- header -->
	<div class="col-sm-3 col-md-2 sidebar">
		<div class="sidebar_top">
			<h1 id ="name">牛洪波</h1>
			<img  alt="" />
		</div>
		<div class="details">
			<h3>电话</h3>
			<p id = "telephone">+00 234 56 789</p>
			<h3>邮箱</h3>
			<p id = "email">
				mail@example.com
			</p>
			<address>
				<h3>地址</h3>
				<span id = "home">河南</span>
			</address>

		</div>
		<div class="clearfix"></div>
	</div>
	<!---->
	<link href="css/popuo-box.css" rel="stylesheet" type="text/css" media="all" />
	<script src="js/jquery.magnific-popup.js" type="text/javascript"></script>
	<!---//pop-up-box---->
	<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
		<div class="content" id="content">


			<!-- <div class="company">
				<h3 class="clr1">工作经历</h3>
				<div class="company_details">
					<h4>部门+时间</h4>
					<h6>部门职位</h6>
					<p class="cmpny1">详细描述</p>
				</div>					
			</div> -->



		</div></div>
		<!---->
</body>
</html>