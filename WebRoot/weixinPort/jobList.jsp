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
 var url=window.location.href;
var userId=url.split("?")[1].split("=")[1];
$(document).ready(function() {
			alert("dd");
$.get("http://weixin.craftsmanasia.com/craftsman_weixin/wechat/position/search/own.do?t="+Math.random(), function(data) {
			alert(data);
			var jsonObj = eval("(" + data + ")");
			var obj=jsonObj.data;//obj是一个包含多个选项的数组
			var str="";
			for ( var i in obj) {
			str+='<li><a href='+'"'+"jobDetails.jsp?positionId="+obj[i].id+"&userId="+userId+'"'+'><dl><dt>'+obj[i].title+'</dt><dd>'+obj[i].company.name+'</dd><dd class="dateTime">'+obj[i].createTime+'</dd><dd class="area">'+obj[i].city+'</dd></dl></a></li>'
			}
			
			document.getElementById("joblist").innerHTML = str;
		});
	});

	function search(){
	var title=document.getElementById("keyword").value;
	var area=document.getElementById("area").value;
	var url="http://weixin.craftsmanasia.com/wechat/position/search/company/position/byTitle.do?title="+title;
	url=encodeURI(url);
url=encodeURI(url);
	$.get(url, function(data) {
			var jsonObj = eval("(" + data + ")");
			var obj=jsonObj.data;//obj是一个包含多个选项的数组
			var str="";
			for ( var i in obj) {
			if(area==""){
			str+='<li><a href='+'"'+"jobDetails.jsp?positionId="+obj[i].id+"&userId="+userId+'"'+'><dl><dt>'+obj[i].title+'</dt><dd class="dateTime">'+obj[i].createTime+'</dd><dd class="area">'+obj[i].city+'</dd></dl></a></li>';
			}else{
			if(area==obj[i].city){
			str+='<li><a href='+'"'+"jobDetails.jsp?positionId="+obj[i].id+"&userId="+userId+'"'+'><dl><dt>'+obj[i].title+'</dt><dd class="dateTime">'+obj[i].createTime+'</dd><dd class="area">'+obj[i].city+'</dd></dl></a></li>';
			}
			}
						}
			
			document.getElementById("joblist").innerHTML = str;
		});
	};
	
	
</script>
</head>
<body>
	<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>
		
			<div class="mtzrl_search">
				<ul id="search_box01">
					<li class="keyword_wrap">
						<div class="add_icon">
							<input id="area" type="hidden" name="area" value="" /> <span
								class="select_area">地区</span>
						</div>
						<div class="input_box">
							<input class="inp_search" type="search" placeholder="输入职位名称"
								name="keyword" id="keyword" >
						</div>
						<div class="btn_icon">
							<button class="search_btn" type="submit" onclick="search()"></button>
						</div></li>
				</ul>
			</div>
		

		<div class="hot_com">
			<h4>
				<a href="#" class="a_hot_title">企业热招</a>
			</h4>
			<ul id="joblist">
			</ul>
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
			<div
				style="float: right; overflow: hidden; background-image: none; width: 280px;"
				class="f_body">
				<h2
					style="position: relative; -webkit-transform: translate(280px, 0px); -webkit-transition: 0.2s ease;
                    transition: 0.2s ease; -webkit-transform-style: preserve-3d; -webkit-backface-visibility: hidden;">
					城市地区
					<button class="ok selectorOk">确定</button>
					<button class="clear selectorClear">清除</button>
				</h2>
				<div class="contentbody2 area"
					style="-webkit-transform: translate(280px, 0px); -webkit-transition: 0.2s ease;
                    transition: 0.2s ease; -webkit-transform-style: preserve-3d; -webkit-backface-visibility: hidden;">
					<dl style="position: relative" class="lookhide">
						<dt class="current area">
							<input type="radio" id="i12" name="homeArea" value="331002">
								<label for="i12"> 北京 </label>
						</dt>
					</dl>
					<dl style="position: relative" class="lookhide">
						<dt class="current area">
							<input type="radio" id="i13" name="homeArea" value="331003">
								<label for="i13"> 上海 </label>
						</dt>
					</dl>
					<dl style="position: relative" class="lookhide">
						<dt class="current area">
							<input type="radio" id="i14" name="homeArea" value="331004">
								<label for="i14"> 广州 </label>
						</dt>
					</dl>
					<dl style="position: relative" class="lookhide">
						<dt class="current area">
							<input type="radio" id="i15" name="homeArea" value="331021">
								<label for="i15"> 深圳 </label>
						</dt>
					</dl>
					<dl style="position: relative" class="lookhide">
						<dt class="current area">
							<input type="radio" id="i16" name="homeArea" value="331022">
								<label for="i16"> 天津 </label>
						</dt>
					</dl>




				</div>
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
