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
var positionId=url.split("?")[1].split("=")[1];
var request ="<%=basePath%>wechat/position/search/own.do?positionId="+positionId;

/*职位详情  */
		$.get(request, function(data) {
					var jsonObj = eval("(" + data + ")");
					var obj = jsonObj.data;//obj是一个包含多个选项的数组
						document.getElementById("companyName").innerHTML = obj[0].company.name;
						document.getElementById("title").innerHTML = obj[0].title;
					document.getElementById("city").innerHTML = obj[0].city;
					document.getElementById("requirement").innerHTML = obj[0].requirement;
					document.getElementById("wage").innerHTML = obj[0].wage;
					document.getElementById("createTime").innerHTML =obj[0].createTime;
					document.getElementById("updateTime").innerHTML = obj[0].updateTime;
					var expired="";
					if(obj[0].isExpired==0){
					expired="否";
					}else{
					expired="是";}
					document.getElementById("isExpired").innerHTML = expired;
					
				});
				
				
/*  岗位投递*/		
var ll ="http://ghosters.imwork.net/craftsman_weixin/wechat/position/subscribe.do?userId=2&positionId=-7";	
$(".btn_apply").click(function() {
				alert(positionId);
				
				$.post(ll, function(data) {
					alert(data);
					
				});
				
				
				
				})
				
		



	
				<%-- $(".btn_apply").click(function() {
				alert(positionId);
				if (confirm('您确定应聘该岗位？')) {
					$.ajax({
						 type : "post",
						url : "<%=basePath%>wechat/position/subscribe.do", 
						data : {
							userId : 2,
							positionId : 7
						},
						dataType : "html", 
						error : function() {
							alert('系统出错,请稍候再试.');
							return false
						}, 
						success : function(data) {
							alert('应聘成功.');
						} 
					}); }
				})--%>
		
			
			
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
						<dd id="requirement"></dd>
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
			<!-- <div class="d_description">
					<h3 class="d_title">职位描述</h3>
					<div class="d_word">
						<p></p>
					</div>
					<h3 class="d_title">联系方式</h3>
					<span class="link"></span>
				</div> -->
			</div>


			<div class="overlay">&nbsp;</div>
			<div class="apply_favorites">
				<button class="btn_apply" data-id="59285" data-id2="190317">
					应聘</button>
				<button class="favorties" data-id="190317">收藏</button>
				<a href="javascript:scroll(0,0)" class="btn_top fr"></a>
			</div>
		</div>
		


		<!-- <div class="job_content hide">
			<div class="job_box">
				<h1 class="d_posName">百度</h1>
				<div class="d_posInfo_box">
					<dl>
						<dt>公司性质：</dt>
						<dd>合资企业</dd>
					</dl>
					<dl>
						<dt>公司规模：</dt>
						<dd>大于500人</dd>
					</dl>
					<dl>
						<dt>主页：</dt>
						<dd>www.baidu.com &nbsp;</dd>
					</dl>
				</div>
				<div class="d_description">
					<h3 class="d_title">企业简介</h3>
					<div class="d_word">
						<p>百度（Nasdaq：BIDU）是全球最大的中文搜索引擎、最大的中文网站。2000年1月由李彦宏创立于北京中关村，致力于向人们提供“简单，可依赖”的信息获取方式。“百度”二字源于中国宋朝词人辛弃疾的《青玉案·元夕》词句“众里寻他千百度”
						</p>
					</div>
					<h3 class="d_title">联系方式</h3>
					<span class="link"></span>
				</div>
			</div>
		</div> -->
		


		<script type="text/javascript" src="js/DeleteSession.js"></script>
		
		<div class="footer">
			<div class="footer_top">
				<ul class="user_info">
					<span><a href="../login/login.aspx">登录</a> <a
						href="../reg/default.htm">注册</a>
					</span>
				</ul>
				<ul class="back_top">
					<a href="javascript:scroll(0,0)">TOP</a>
				</ul>
			</div>
			<ul class="copyright">
				<li><a href="../../tzrl/default.htm">电脑版</a><span>|</span><a
					href="../../old/default.htm">普通版</a><span>|</span><a
					href="../fankui/default.htm">用户反馈</a><span>|</span><a
					href="../contact/default.htm">联系我们</a>
				</li>
				<li>&copy;2003-2013 浙B2-20110048</li>
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

			/* $(".btn_apply").click(function() {
				if (confirm('您确定应聘该岗位？')) {
					$.ajax({
						type : "post",
						url : "ajax/yingpin.ashx",
						data : {
							comid : $(this).data("id"),
							jobid : $(this).data("id2")
						},
						dataType : "html",
						error : function() {
							alert('系统出错,请稍候再试.');
							return false
						},
						success : function(data) {
							if (data == "1") {
								$('.overlay').css({
									'display' : 'block',
									'opacity' : '0.8'
								});
								LoginTip('未登陆的');
							} else if (data == "2") {
								showTip('请先完善您的简历！');
							} else if (data == "3") {
								showTip('成功应聘！');
							} else if (data == "4") {
								showTip('禁用会员！');
							} else if (data == "5") {
								showTip('成功应聘！');
							}
						}
					});
				}
			}); */

			$(".favorties").click(function() {
				if (confirm('您确定收藏该岗位？')) {
					$.ajax({
						type : "post",
						url : "ajax/shoucang.ashx",
						data : {
							jobid : $(this).data("id")
						},
						dataType : "html",
						error : function() {
							alert('系统出错,请稍候再试.');
							return false
						},
						success : function(data) {
							if (data == "1") {
								$('.overlay').css({
									'display' : 'block',
									'opacity' : '0.8'
								});
								LoginTip('未登陆的');
							} else if (data == "2") {
								showTip('请先完善您的简历！');
							} else if (data == "3") {
								showTip('禁用会员！');
							} else if (data == "4") {
								showTip('成功收藏！');
							}

						}
					});
				}
			});
		});
	</script>
</body>
</html>
