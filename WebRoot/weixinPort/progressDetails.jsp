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

<meta name="viewport"
	content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
<meta name="apple-mobile-web-app-status-bar-style" content="black" />
<meta name="format-detection" content="telephone=yes" />
<meta name="format-detection" content="email=no" />
<meta content="" name="keywords" />
<title></title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
var url=window.location.href;


var userId=url.split("=")[1].split("&")[0];
var positionId=url.split("=")[2];
var request ="<%=basePath%>/wechat/position/search/subscribed/deatil/info.do?userId="+userId+"&positionId="+positionId;

/*职位详情  */
		$.get(request, function(data) {
					var jsonObj = eval("(" + data + ")");
					var obj = jsonObj.data;
						document.getElementById("companyname").innerHTML = obj.position.company.name;
						
					document.getElementById("create_time").innerHTML = obj.createTime;
					if(obj.recommendTime==""){document.getElementById("recommend_time").innerHTML = "</br>";
					}else{document.getElementById("recommend_time").innerHTML = obj.recommendTime;}
					if(obj.createTime==""){document.getElementById("screen_resume_time").innerHTML ="</br>";}else{document.getElementById("screen_resume_time").innerHTML =obj.createTime;}
					
					
					if(obj.recommendTime==""){document.getElementById("first_interview_time").innerHTML = "</br>";
					}else{document.getElementById("first_interview_time").innerHTML = obj.firstInterviewTime;}
					
					
					if(obj.recommendTime==""){document.getElementById("second_interview_time").innerHTML = "</br>";
					}else{document.getElementById("second_interview_time").innerHTML =obj.secondInterviewTime;}
					
					
					if(obj.recommendTime==""){document.getElementById("third_interview_time").innerHTML = "</br>";
					}else{document.getElementById("third_interview_time").innerHTML = obj.thirdInterviewTime;}
					
					if(obj.recommendTime==""){document.getElementById("reject_time").innerHTML ="</br>";
					}else{document.getElementById("reject_time").innerHTML =obj.rejectTime;}
					

					var status="";
					if(obj.statusId==1){
					status="新投递";
					}else if(obj.statusId==2){
					status="简历已推荐";
					}else if(obj.statusId==3){
					status="简历已通过筛选";
					}else if(obj.statusId==4){
					status="一面通过";
					}else if(obj.statusId==5){
					status="二面通过";
					}else if(obj.statusId==6){
					status="三面通过";
					}else if(obj.statusId==7){
					status="等待offer";
					}else{
					status="已拒绝";
					}
					document.getElementById("status").innerHTML = status;
					
				});
			
			
			})
</script>

</head>
<body>
<div class="mtzrl_box">

		
		<div class="mtzrl_header">
			<h1 class="m_logo">
				<a href="../default.htm"></a>
			</h1>


			<div class="quick_links hide">
				<ul>

				</ul>
			</div>
		</div>

		<div class="job_top c_bg">
			<div class="btn_back_l">
				<a class="btn_back" onclick="history.go(-1)"><span></span> <nav>返回</nav>
				</a>
			</div>

		</div>
		<div class="c_menu">
			<ul>
				<li class="active"><a href="javascript:;">职位进度详情</a></li>
			<!-- 	<li><a href="javascript:;">企业简介</a></li> -->
				
			</ul>
		</div>
		<div class="job_content">
			<div class="job_box">
				<h1 class="d_posName" id="title"></h1>
				<!-- <h4 class="d_pos_Num">1人</h4> -->
				<div class="d_posInfo_box">
				<dl>
						<dt >职位名称：</dt>
						<dd id="companyname"></dd>
					</dl>
<dl>
						<dt >状态：</dt>
						<dd id="status"></dd>
					</dl>
					<dl>
						<dt >投递时间：</dt>
						<dd id="create_time"></dd>
					</dl>

					
					<dl>
						<dt >推荐时间：</dt>
						<dd id="recommend_time"></dd>
					</dl>
					<dl>
						<dt >通过筛选时间：</dt>
						<dd id="screen_resume_time"></dd>
					</dl>
					<dl>
						<dt >一面时间：</dt>
						<dd id="first_interview_time"></dd>
					</dl>
					<dl>
						<dt >二面时间：</dt>
						<dd id="second_interview_time"></dd>
					</dl>
					
					<dl>
						<dt >三面时间：</dt>
						<dd id="third_interview_time"></dd>
					</dl>
					<dl>
						<dt >被拒时间：</dt>
						<dd id="reject_time"></dd>
					</dl>
					
				</div>
			
			</div>


			<div class="overlay">&nbsp;</div>
			<div class="apply_favorites">
				
			</div>
		</div>
		


		
		


		<script type="text/javascript" src="js/DeleteSession.js"></script>
		<script type="text/javascript" language="JavaScript">
			$(function() {
				$(".per_name").click(function(e) {
					var parent = $(this).parent().parent().parent()
					if (e && e.stopPropagation)
						e.stopPropagation()
					if (e && e.preventDefault)
						e.preventDefault()
					if (parent.hasClass("hover")) {
						parent.removeClass("hover")
					} else {
						parent.addClass("hover")
						var hideTip = function() {
							parent.removeClass("hover")
							$(document).off("click", hideTip)
						}
						$(document).on("click", hideTip)
					}
				})
			})
		</script>
		<div class="footer">
			<div class="footer_top">
				
				<ul class="back_top">
					<a href="javascript:scroll(0,0)">TOP</a>
				</ul>
			</div>
			<ul>
				<li>2015 &copy; Craftsman. ALL Rights Reserved.</li>
			</ul>
		</div>
		

	</div>
	<script type="text/javascript" src="js/jquery.cookie.js"></script>
	<script type="text/javascript" src="js/LoginTip.js"></script>
	<script type="text/javascript" src="js/ShowTip.js"></script>

	<script type="text/javascript">
		$(function() {
			$(".footer").hide();

			$.ajax({
				type : "post",
				url : "ajax/link.ashx",
				data : {
					com_id : '59285',
					com_rand : 'cqa'
				},
				dataType : "html",
				success : function(data) {
					$('.link').html(data);
				}
			})

			$(".c_menu ul li").click(function() {
				$(".active").removeClass("active");
				$(this).addClass("active");

				$(".job_content").hide();
				$(".job_content").eq($(".c_menu ul li").index($(this))).show();

				if ($(".c_menu ul li").index($(this)) != 0) {
					$(".footer").show();
				} else {
					$(".footer").hide();
				}

			});


		});
	</script>
</body>
</html>
