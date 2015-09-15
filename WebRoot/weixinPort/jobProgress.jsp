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
<title>卡富文思</title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/select.css" />
<meta content="" name="keywords" />
<link rel="apple-touch-icon" href="app/tzrllogo.png" />
<script type="text/javascript" src="js/jquery.js"></script>


<script type="text/javascript"> 
 
$(document).ready(function() {
var userId='<%=session.getAttribute("userId")%>';
var request ="<%=basePath%>/wechat/user/jobprogress.do?userId=" + userId;

						$.get(request, function(position) {
							
					var jsonObj = eval("(" + position + ")");
					var obj = jsonObj.position;//obj是一个包含多个选项的数组			
					var str="";
					for ( var i in obj) {
						str+='<li><a href='+'"'+"progressDetails.jsp?userId="+userId+"&positionId="+obj[i].id+'"'+'><dl><dt>'+obj[i].title+'</dt><dd>'+obj[i].company.name+'</dd><dd class="area">查看进度</dd></dl></a></li>'
					}
			
						document.getElementById("joblist").innerHTML = str;

						});

					})
</script>
</head>
<body>
	<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>


		<div class="hot_com">
			<h4>
				<a href="job/default.htm" class="a_hot_title">岗位进度</a>
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
				<li><a href="../tzrl/default.htm">电脑版</a><span>|</span><a
					href="../old/default.htm">普通版</a><span>|</span><a
					href="fankui/default.htm">用户反馈</a><span>|</span><a
					href="contact/default.htm">联系我们</a></li>
				<li>&copy;2015</li>
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



	<script type="text/javascript" src="js/showtip.js"></script>

	<script type="text/javascript">
		$(function() {
			$('.search_btn').click(function() {
				if ($('#keyword').val() == "" && $('#area').val() == "") {
					showTip('请输入关键字或选择工作地区');
					return false;
				}
				$(this).closest('form').submit();
			});

			//-------------------------------------------------------------------
			$('.right_menu').height($('body').height()).click(function(e) {
				if ($(e.target).parents('.f_body').size() == 0) {
					$('.arrow_icon').click();
					return false;
				}
			});

			$('.arrow_icon').css({
				top : ($(window).height() - 16) / 2 + $(window).scrollTop(),
				left : $(window).width() - 312
			});
			$(window).scroll(function() {
				$('.arrow_icon').css({
					top : ($(this).height() - 16) / 2 + $(this).scrollTop()
				});
			});

			$('.selectorClear').click(function() {
				$('.contentbody2 :radio:checked').prop('checked', false);
				return false;
			});

			$('.contentbody2 :radio').click(function() {
				if ($(this).prop('checked')) {
					$('#area').val($(this).val());
					$('.select_area').text($.trim($(this).next().text()));
					$('.arrow_icon').click();
				}
			});

			$('.arrow_icon').click(function() {
				$('.arrow_icon').hide();
				$('.right_menu').css({
					'background-color' : 'rgba(51, 51, 51, 0)'
				});
				$('.f_body h2,.contentbody2').css({
					'-webkit-transform' : 'translate(280px, 0px)'
				});
				setTimeout(function() {
					$('.right_menu').hide();
				}, 100);
				return false;
			});
			$('.selectorOk').click(function() {
				var c = $('.contentbody2 :radio:checked');
				if (c.size() > 0) {
					$('#area').val($(c).val());
					$('.select_area').text($.trim($(c).next().text()));
				} else {
					$('#area').val('');
					$('.select_area').text('地区');
				}
				$('.arrow_icon').click();
				return false;
			});

			$('.select_area').click(function() {
				$('.arrow_icon').show();
				$('.right_menu').css({
					'background-color' : 'rgba(51, 51, 51, 0.8)'
				}).show();
				setTimeout(function() {
					$('.f_body h2,.contentbody2').css({
						'-webkit-transform' : 'translate(0px, 0px)'
					});
				}, 100);

				return false;
			});
		})
	</script>

</body>
</html>
