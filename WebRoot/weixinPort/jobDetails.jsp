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

<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<title></title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
	var url=window.location.href;
var positionId=url.split("=")[1].split("&")[0];
var userId=url.split("=")[2];
var requestUrl ="http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/info.do?positionId="+positionId+"&t="+Math.random();

/*职位详情  */
		$.get(requestUrl, function(data) {
					var jsonObj = eval("(" + data + ")");
					var obj = jsonObj.data;//obj是一个包含多个选项的数组
					
						document.getElementById("companyName").innerHTML = obj.company.name;
						document.getElementById("title").innerHTML = obj.title;
					document.getElementById("city").innerHTML = obj.city;
					document.getElementById("requirement").innerHTML = obj.requirement;
					document.getElementById("wage").innerHTML = obj.wage;
					document.getElementById("createTime").innerHTML =obj.createTime;
					document.getElementById("updateTime").innerHTML = obj.updateTime;
					var expired="";
					if(obj.isExpired==0){
					expired="否";
					}else{
					expired="是";}
					document.getElementById("isExpired").innerHTML = expired;
					
				});
				
				
/*  岗位投递*/		
	
				
 $(".btn_apply").click(function() {
				if(userId==0){alert("请先登陆！");}else{
				if (confirm('您确定应聘该岗位？')) {
					$.ajax({
						 type : "get",
						url : "http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/subscribe.do?t="+Math.random(),
						data : {
							userId : userId,
							positionId : positionId
						},
						dataType : "html", 
						error : function() {
							alert('系统出错,请稍候再试.');
							return false;
						}, 
						success : function(data) {
							var jsonObj = eval("(" + data + ")");
							var obj=jsonObj.status;//obj是一个包含多个选项的数组
							if(obj==true){alert("投递成功！");}
							else{alert(jsonObj.msg);
							}
						} 
					}); }}
				});
				/*岗位收藏  */
				$(".favorties").click(function() {
				if(userId==0){alert("请先登陆！");}else{
				if (confirm('您确定收藏该岗位？')) {
					$.ajax({
						type : "get",
						url : "http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/collect.do?t="+Math.random(),
						data : {
							userId : userId,
							positionId : positionId
						},
						dataType : "html",
						error : function() {
							alert('系统出错,请稍候再试.');
							return false;
						},
						success : function(data) {
							var jsonObj = eval("(" + data + ")");
							var obj=jsonObj.status;//obj是一个包含多个选项的数组
							if(obj==true){alert("收藏成功！");}else{alert(jsonObj.msg);

							}

						}
					});
				}}
			});
			
			
			})
</script>

</head>
<body>
<div class="mtzrl_box">

		
		<div class="mtzrl_header">
			<h1 class="m_logo">
				<a href="#"></a>
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
				<li class="active"><a href="javascript:;">职位详情</a></li>
			<!-- 	<li><a href="javascript:;">企业简介</a></li> -->
				
			</ul>
		</div>
		<div class="job_content">
			<div class="job_box">
				<h1 class="d_posName" id="title"></h1>
				<!-- <h4 class="d_pos_Num">1人</h4> -->
				<div class="d_posInfo_box">

					<dl>
						<dt >公司名字：</dt>
						<dd id="companyName"></dd>
					</dl>

					<dl>
						<dt >要求：</dt>
						<dd id="requirement" style="word-break:break-all;"></dd>
					</dl>
					<dl>
						<dt >工作地址：</dt>
						<dd id="city"></dd>
					</dl>
					<dl>
						<dt >工资水平：</dt>
						<dd id="wage"></dd>
					</dl>
					<dl>
						<dt >发布时间：</dt>
						<dd id="createTime"></dd>
					</dl>
					<dl>
						<dt >更新时间：</dt>
						<dd id="updateTime"></dd>
					</dl>
					<dl>
						<dt >是否过期：</dt>
						<dd id="isExpired"></dd>
					</dl>
				</div>
			
			</div>


			<div class="overlay">&nbsp;</div>
			<div class="apply_favorites">
				<button class="btn_apply" data-id="59285" data-id2="190317">
					应聘</button>
				<button class="favorties" data-id="190317">收藏</button>
				<a href="javascript:scroll(0,0)" class="btn_top fr"></a>
			</div>
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
		<div style="display:none;">
			
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
