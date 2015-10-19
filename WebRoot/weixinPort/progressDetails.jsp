<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>



<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head><meta charset="utf-8" /><meta name="viewport" content="width=device-width, initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" /><meta name="apple-mobile-web-app-capable" content="yes" /><meta name="apple-mobile-web-app-status-bar-style" content="black" /><meta name="format-detection" content="telephone=yes" /><meta name="format-detection" content="email=no" /><title>
</title><link rel="stylesheet" type="text/css" href="css/css.css" />
<link rel="stylesheet" type="text/css" href="css/login.css" />
<link rel="stylesheet" type="text/css" href="css/progressDetail.css">

<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/default.css" />
		<link rel="stylesheet" type="text/css" href="css/component.css" />
		<script src="js/modernizr.custom.js"></script>
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/bootstrap-responsive.css" rel="stylesheet">
    <link href="css/docs.css" rel="stylesheet">
    
	<style>
		body.from-demo {
			padding-top: 0;
		}
		body.from-demo .navbar {
			display: none;
		}
		body.from-demo .subhead {
			display: none;
		}
		body.from-demo .footer {
			display: none;
		}
		body.from-demo ul.timeline {
			margin-top: 20px;
		}
	</style>

<script type="text/javascript">
$(function(){
var url=window.location.href;
var positionId=url.split("=")[2];
var userId=url.split("=")[1].split("&")[0];
var request ="<%=basePath%>wechat/position/search/subscribed/deatil/info.do?userId="+userId+"&positionId="+positionId+"&t="+Math.random();
$.get(request, function(data) {
					var jsonObj = eval("(" + data + ")");
					var obj = jsonObj.data;					
					document.getElementById("title").innerHTML = obj.position.title;
					if(obj.createTime!=""){document.getElementById("s1").innerHTML="投递成功";}
					if(obj.recommendTime!=""){document.getElementById("s2").innerHTML="推荐成功";}else{document.getElementById("s2").innerHTML="正在进行...";};
					if(obj.screenResumeTime!=""){document.getElementById("s3").innerHTML="筛选成功";}else{document.getElementById("s3").innerHTML="正在进行...";};
					if(obj.firstInterviewTime!=""){document.getElementById("s4").innerHTML="一面成功";}else{document.getElementById("s4").innerHTML="正在进行...";};
					if(obj.secondInterviewTime!=""){document.getElementById("s5").innerHTML="二面成功";}else{document.getElementById("s5").innerHTML="正在进行...";};
					if(obj.thirdInterviewTime!=""){document.getElementById("s6").innerHTML="三面成功";}else{document.getElementById("s6").innerHTML="正在进行...";};
					
					document.getElementById("createTime").innerHTML = obj.createTime;					
					document.getElementById("recommendTime").innerHTML = obj.recommendTime;
					document.getElementById("screenResumeTime").innerHTML = obj.screenResumeTime;
					document.getElementById("firstInterviewTime").innerHTML = obj.firstInterviewTime;
					document.getElementById("secondInterviewTime").innerHTML = obj.secondInterviewTime;
					document.getElementById("thirdInterviewTime").innerHTML = obj.thirdInterviewTime;
					});

/*职位详情  */
var requestUrl ="<%=basePath%>wechat/position/info.do?positionId="+positionId+"&t="+Math.random();
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
				
								
					
	 $(".timeline").eq(0).animate({
		height:'630px'
	},4000); 
});
</script>
<style type="text/css">
h2 {font-size: 1em;font-family: "Microsoft Yahei",Verdana,Arial,Helvetica,sans-serif;}

h1 {font-weight: bold;}
</style>
</head>
<body>
    <div class="mtzrl_box">
        
    <script type="text/javascript" src="js/jquery.js"></script>
<div class="tzrl_box m_index">
		<div class="m_banner">
			<img src="images/top.png" width="320" height="135" />
		</div>
		
</div>
	
        
        <div class="c_menu">
            <ul>
                <li class="active" style="width: 50%;"><a href="javascript:;">职位状态</a> </li>
                <li><a href="javascript:;">职位详情</a> </li>
               
            </ul>
        </div>
        <div class="job_content">
        <div class="container">
        <style type="text/css">
			ul.timeline {
				list-style-type: none;
				background: url("images/version_line.png") repeat-y scroll 10% 0 transparent;
				
				padding: 0;
			}
			
			ul.timeline li {
				position: relative;
				margin-bottom: 20px;
			}
			ul.timeline li .time {
				position: absolute;
				width: 90px;
				text-align: right;
				left: 0;
				top: 10px;
				color: #999;
			}
			ul.timeline li .version {
				position: absolute;
				
				text-align: right;
				
				
				font-size: 40px;
				line-height: 50px;
				color: #3594cb;
				overflow: hidden;
			}
			ul.timeline li .number {
				position: absolute;
				background: url("images/version_dot.png") no-repeat scroll 0 0 transparent;
				width: 56px;
				height: 56px;
				
				line-height: 56px;
				text-align: center;
				color: #fff;
				font-size: 18px;
			}
			ul.timeline li.alt .number {
				background-image: url("images/version_dot_alt.png");
			}
			ul.timeline li .content {
				margin-left: 25%;
				
			}
			ul.timeline li .content pre {
				background-color: #3594cb;
				padding: 10px;
				color: #fff;
				font-size: 13px;
				line-height: 20px;
			}
			ul.timeline li.alt .content pre {
				background-color: #43B1F1;
			}
			
		</style>
		
		
			
<ul class="timeline" >
		
	<li >
		
		<div class="number">1</div>
		<div class="content" >
			<pre><span>-简历投递</span><span style="float:right" id="createTime"></span><h2 id="s1"></h2></pre>
		</div>
	</li>
<li>
		
		<div class="number">2</div>
		<div class="content">
			<pre><span>-简历推荐</span><span style="float:right" id="recommendTime"></span><h2 id="s2"></h2></pre>
		</div>
	</li>
	<li>
		
		<div class="number">3</div>
		<div class="content">
			<pre><span>-简历筛选</span><span style="float:right" id="screenResumeTime"></span><h2 id="s3"></h2></pre>
		</div>
	</li>
	<li>
		
		<div class="number">4</div>
		<div class="content">
			<pre><span>-第一次面试</span><span style="float:right" id="firstInterviewTime"></span><h2 id="s4"></h2></pre>
		</div>
	</li>
	<li>
		
		<div class="number">5</div>
		<div class="content">
			<pre><span>-第二次面试</span><span style="float:right" id="secondInterviewTime"></span><h2 id="s5"></h2></pre>
		</div>
	</li>
	<li>
		
		<div class="number">6</div>
		<div class="content">
			<pre><span>-第三次面试</span><span style="float:right" id="thirdInterviewTime"></span><h2 id="s6"></h2></pre>
		</div>
	</li>
</ul>	
</div>
	
</div>
          
            
            <div class="overlay">&nbsp;</div>
           
        
        <div class="job_content" id="section2">
        
        		
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
			
			</div>

        </div>
       
        
   
   <script type="text/javascript" language="JavaScript" >
    $(function(){
  	    $(".per_name").click(function(e){
		    var parent = $(this).parent().parent().parent();
		    if( e && e.stopPropagation)e.stopPropagation();
		    if( e && e.preventDefault )e.preventDefault();
		    if(parent.hasClass("hover")){
			    parent.removeClass("hover");
		    }else{
			    parent.addClass("hover");
			    var hideTip = function(){
				    parent.removeClass("hover");
				    $(document).off("click",hideTip);
			    };
			    $(document).on("click",hideTip);
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
</div><div style="display:none;"></div>

    </div>
   

    <script type="text/javascript">
    $(function(){
     $("#section2").hide();
    
     $(".footer").hide();
	      
    $(".c_menu ul li").click(function(){
            $(".active").removeClass("active"); 
            $(this).addClass("active"); 
            
            $(".job_content").hide();
            $(".job_content").eq($(".c_menu ul li").index($(this))).show();
            
            if($(".c_menu ul li").index($(this))!=0){
                 $(".footer").show();
                  $(".hide").show();
            }
            else{
                 $(".footer").hide();
            }
            
        });
  
});	
    </script>  
</body>
</html>
