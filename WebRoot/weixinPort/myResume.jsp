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
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link rel="stylesheet" type="text/css" href="css/css.css" />
<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>

<!-- start menu -->

<script type="text/javascript"> 

$(document).ready(function() {
var url=window.location.href;
var userId=url.split("=")[1];
/*  根据用户id得到个人信息weixin.craftsmanasia.com*/	

$.get("http://weixin.craftsmanasia.com/craftsman_weixin/resumeAction/search/resume.do?userId="+userId+"&t="+Math.random(), function(data) {
			var jsonObj = eval("(" + data + ")");
			var obj=jsonObj.data;//obj是一个包含多个选项的数组
			document.getElementById("email").innerHTML = obj.email;
			document.getElementById("name").innerHTML = obj.name;
			document.getElementById("telephone").innerHTML = obj.telephone;
			document.getElementById("home").innerHTML = obj.home;
			 if(obj.gender=="女"){ $("#gender").attr("src","images/girl.jpg");
			}else{$("#gender").attr("src","images/boy.jpg");
			} 
			var str='<div class="company"><h3 class="clr1">工作经历    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="images/add1.png" onclick="addjob('+userId+')"/></h3>';
			for ( var i in obj.works) {
			var work = obj.works[i];
			 str+='<div class="company_details" id="'+work.id+'"><h4>'+work.company+"<span>"+work.beginTime+'--'+work.endTime+'</span><span style="float:right"><img src=images/modify.png onclick="modify1('+work.id+')">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src=images/delete1.png onclick="delete1('+work.id+')"></span>'+'</h4><h6>'+work.department+'</h6><p class="cmpny1">'+work.description+'</p></div></div>';

		 	}
		 	 document.getElementById("content").innerHTML=str; 
		});

	});
	
	
	function modify1(workId){
  if (confirm('确定修改？')) {
  window.location.href="modifyWork.jsp?workId="+workId;
  
  };
};
function delete1(workId){
  if (confirm('确定删除？')) {
  $.get("<%=basePath%>resumeAction/work/delete.do?workId="+workId, function(data) {
		//$('#'+workId).remove();	
		history.go(0);
		});
  
  };
};
function addjob(userId){
  if (confirm('确定添加？')) {
  
  location.href = "fillWork.jsp?userId="+userId;
  };
};

</script>


</head>
<body>
	<!-- header -->
	<div class="col-sm-3 col-md-2 sidebar">
		<div class="sidebar_top">
			<h1 id ="name"></h1>
			<img  id="gender" />
		</div>
		<div style="position:absolute; right:0px; top:10px; width:46px; height:38px;"><img id="modify" src="images/modify.png" style="position:absolute;width: 44px;height: 44px;border: 0px solid #999;"></div>
		<div class="details">
		
			<h3>电话</h3>
			<p id = "telephone"></p>
			<h3>邮箱</h3>
			<p id = "email">
				
			</p>
			<address>
				<h3>地址</h3>
				<span id = "home"></span>
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
		</div></div>
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
</body>
<script type="text/javascript">
  $("#modify").click(function(){
  var url=window.location.href;
var userId=url.split("=")[1];
   window.location.href="modifyResume.jsp?userId="+userId;
   });


</script>

</html>