<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=0">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" /><title>岗位进度 </title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/progressDetail.css">
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript">
$(function(){
var url=window.location.href;
var positionId=url.split("=")[2];
var userId=url.split("=")[1].split("&")[0];
var request ="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/search/subscribed/deatil/info.do?userId="+userId+"&positionId="+positionId;

$.get(request, function(data) {
					var jsonObj = eval("(" + data + ")");
					var obj = jsonObj.data;
					document.getElementById("title").innerHTML = obj.position.title;
					document.getElementById("createTime").innerHTML = obj.createTime;
					
					document.getElementById("recommendTime").innerHTML = obj.recommendTime;
					document.getElementById("screenResumeTime").innerHTML = obj.screenResumeTime;
					document.getElementById("firstInterviewTime").innerHTML = obj.firstInterviewTime;
					document.getElementById("secondInterviewTime").innerHTML = obj.secondInterviewTime;
					document.getElementById("thirdInterviewTime").innerHTML = obj.thirdInterviewTime;
					});
					
					
					
	$(".timeline").eq(0).animate({
		height:'500px'
	},2000);
});
</script>



</head>
<body>

<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>
		
</div>
<div class="timeline">
	<div class="timeline-date">
		<ul>
			<h2 class="second" style="position: relative;">
				<span id="title"></span>
			</h2>
			<li>
				<h3 >投递</h3>
				<dl class="right">
					<span id="createTime"></span>
				</dl>
			</li>
			<li>
				<h3 >推荐</h3>
				<dl class="right">
					<span id="recommendTime"></span>
				</dl>
			</li>
			<li>
				<h3 >筛选</h3>
				<dl class="right">
					<span id="screenResumeTime"></span>
				</dl>
			</li>
			<li>
				<h3 >一面</h3>
				<dl class="right">
					<span id="firstInterviewTime"></span>
				</dl>
			</li>
			<li>
				<h3 >二面</h3>
				<dl class="right">
					<span id="secondInterviewTime"></span>
				</dl>
			</li>
			<li>
				<h3  >三面</h3>
				<dl class="right">
					<span id="thirdInterviewTime"></span>
				</dl>
			</li>
			
		</ul>
	</div>
	
</div>

 
</body>
</html>