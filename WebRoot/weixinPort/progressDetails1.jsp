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
<script type="text/javascript">
$(function(){
var url=window.location.href;
var positionId=url.split("=")[2];
var userId=url.split("=")[1].split("&")[0];
var request ="<%=basePath%>wechat/position/search/subscribed/deatil/info.do?userId="+userId+"&positionId="+positionId;
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
var requestUrl ="<%=basePath%>wechat/position/info.do?positionId="+positionId;
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
		height:'500px'
	},2000);
});
</script>
<style type="text/css">
h2 {font-size: 1em}

</style>
</head>
<body>
    <div class="mtzrl_box">
        
    <script type="text/javascript" src="js/jquery.js"></script>
<div class="mtzrl_header">
    <h1 class="m_logo"></h1>
    <div class="header_btn " id="per_logout">
			
		</div>
		<div class="per_login hide" id="per_login">
			<div class="account"><div class="per_name"><span id="per_name"></span><i class="arrowIcon"></i></div></div>
		</div>
		<div class="quick_links hide">
				<ul>
					
				</ul>
			</div>
  </div>
	
        <div class="job_top c_bg">
            <div class="btn_back_l">
                <a class="btn_back" onclick="history.go(-1)"><span></span>
                    <nav>返回</nav>
                </a>
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
			
			<div class="main">
				<ul class="cbp_tmtimeline">
					<li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left;">投递</h2><h2 align="right" id="s1"></h2>
							<p id="createTime"></p>
						</div>
					</li>
					<li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left">推荐</h2><h2 align="right" id="s2"></h2>
							<p id="recommendTime"></p>
						</div>
					</li><li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left">筛选</h2><h2 align="right" id="s3"></h2>
							<p id="screenResumeTime"></p>
						</div>
					</li><li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left">一面</h2><h2 align="right" id="s4"></h2>
							<p id="firstInterviewTime"></p>
						</div>
					</li><li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left">二面</h2><h2 align="right" id="s5"></h2>
							<p id="secondInterviewTime"></p>
						</div>
					</li><li>
						
						<div class="cbp_tmicon"></div>
						<div class="cbp_tmlabel" >
						
							<h2 style="float:left">三面</h2><h2 align="right" id="s6"></h2>
							<p id="thirdInterviewTime"></p>
						</div>
					</li>
				</ul>
			</div>
		</div>
        
        
         
		</ul>
	</div>
	
</div>
          
            
            <div class="overlay">&nbsp;</div>
           
        </div>
        <div class="job_content hide">
        
        		
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
		    var parent = $(this).parent().parent().parent()
		    if( e && e.stopPropagation)e.stopPropagation()
		    if( e && e.preventDefault )e.preventDefault()
		    if(parent.hasClass("hover")){
			    parent.removeClass("hover")
		    }else{
			    parent.addClass("hover")
			    var hideTip = function(){
				    parent.removeClass("hover")
				    $(document).off("click",hideTip)
			    }
			    $(document).on("click",hideTip)
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
</div><div style="display:none;"></div>

    </div>
   

    <script type="text/javascript">
    $(function(){
     $(".footer").hide();
     
        $.ajax({
		      type: "post",
		      url: "ajax/link.ashx",
		      data:{com_id:'59285',com_rand:'cqa'},
		      dataType: "html",
		      success : function(data){
		            $('.link').html(data);
		        }
		      })
		      
    $(".c_menu ul li").click(function(){
            $(".active").removeClass("active"); 
            $(this).addClass("active"); 
            
            $(".job_content").hide();
            $(".job_content").eq($(".c_menu ul li").index($(this))).show();
            
            if($(".c_menu ul li").index($(this))!=0){
                 $(".footer").show();
            }
            else{
                 $(".footer").hide();
            }
            
        });
  
});
    </script>  
</body>
</html>
