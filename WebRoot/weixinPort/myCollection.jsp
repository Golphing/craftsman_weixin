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
<title>卡富文思</title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/select.css" />
<meta content="" name="keywords" />
<link rel="apple-touch-icon" href="app/tzrllogo.png" />
<script type="text/javascript" src="js/jquery.js"></script>


<script type="text/javascript"> 
 
$(document).ready(function() {
var url=window.location.href;
			var userId=url.split("=")[1]; 
var request ="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/serach/collection/positions.do?userId=" + userId+"&t="+Math.random();

						$.get(request, function(position) {
						
					var jsonObj = eval("(" + position + ")");
					var obj = jsonObj.data;//obj是一个包含多个选项的数组	
							
					var str="";
					for ( var i in obj) {
						str+='<li><a href='+'"'+"myCollectionDetails.jsp?userId="+userId+"&positionId="+obj[i].id+'"'+'><dl><dt>'+obj[i].title+'</dt><dd>'+obj[i].company.name+'</dd><dd class="area">查看详细</dd></dl></a></li>';
					};
			
						document.getElementById("joblist").innerHTML = str;

						});

					});
</script>
</head>
<body>
	<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>


		<div class="hot_com">
			<h4>
				<a href="#" class="a_hot_title">我的收藏</a>
			</h4>
			<ul id="joblist">
			</ul>
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
		

		<div class="right_menu mask selecter hide "
			style="height: auto; background-color: rgba(51, 51, 51, 0);">
			<div class="f_left" style="">
				<i
					style="position: relative; -webkit-transition: top 0.3s ease; transition: top 0.3s ease;"
					class="arrow_icon"><i></i><i></i> </i>
			</div>



		</div>
	</div>



	
</body>
</html>
