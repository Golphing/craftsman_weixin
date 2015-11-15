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



</head>
<body>
	<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>
		
			<div class="mtzrl_search">
				<ul id="search_box01">
					
				</ul>
			</div>
		

		<div class="hot_com">
			<h4>
				<a href="#" class="a_hot_title"></a>
			</h4>
			<ul id="joblist">
	<div id="wrap" style="background-size:100%;">		
			  <div class="modbgd">
                       <a id="fillResume">
        <div class="menu clr">
      <div class="icon"><img src="images/icon02.png" width="32" height="32"></div>
      <div class="tle">添加简历</div>
    </div>
    </a>
    <!--  <a id="linked">
        <div class="menu clr" >
      <div class="icon"><img src="images/icon05.png" width="32" height="32"></div>
      <div class="tle">linkedin导入</div>
    </div>
    </a> -->
                      <a id="51job">
        <div class="menu clr">
      <div class="icon"><img src="images/icon01.png" width="32" height="32"></div>
      <div class="tle">51job导入</div>
    </div>
    </a>
                      <a id="zhilian">
        <div class="menu clr" >
      <div class="icon"><img src="images/icon03.png" width="32" height="32"></div>
      <div class="tle">智联导入</div>
    </div>
    </a>
                  <a id="liepin">
            <div class="menu clr" >
      <div class="icon"><img src="images/icon04.png" width="32" height="32"></div>
      <div class="tle">猎聘导入</div>
    </div>
    </a>
     <a id="lying">
            <div class="menu clr" >
      <div class="icon"><img src="images/icon04.png" width="32" height="32"></div>
      <div class="tle">领英导入</div>
    </div>
    </a>
  
  </div> </div>
			
			</ul>
		</div>

		<script type="text/javascript" src="js/DeleteSession.js"></script>
		<script type="text/javascript" language="JavaScript">
			$(function() {
				$(".per_name").click(function(e) {
					var parent = $(this).parent().parent().parent();
					if (e && e.stopPropagation)
						e.stopPropagation();
					if (e && e.preventDefault)
						e.preventDefault();
					if (parent.hasClass("hover")) {
						parent.removeClass("hover");
					} else {
						parent.addClass("hover");
						var hideTip = function() {
							parent.removeClass("hover");
							$(document).off("click", hideTip);
						};
						$(document).on("click", hideTip);
					}
				});
			});
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

		<div class="right_menu mask selecter hide "
			style="height: auto; background-color: rgba(51, 51, 51, 0);">
			<div class="f_left" style="">
				<i
					style="position: relative; -webkit-transition: top 0.3s ease; transition: top 0.3s ease;"
					class="arrow_icon"><i></i><i></i> </i>
			</div>
			

	<script type="text/javascript" src="js/showtip.js"></script>
<script type="text/javascript"> 
 
$(document).ready(function() {

			var url=window.location.href;
var userId=url.split("?")[1].split("=")[1];

document.getElementById("fillResume").href="fillResume.jsp?userId="+userId;
document.getElementById("51job").href="../views/resume_import/51input.jsp?userId="+userId;
document.getElementById("zhilian").href="../views/resume_import/zlinput.jsp?userId="+userId;
document.getElementById("liepin").href="../views/resume_import/lpinput.jsp?userId="+userId;

document.getElementById("lying").href="../views/resume_import/lyinput.jsp?userId="+userId;						

})
</script>
	
</body>
</html>
