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
<title></title>
<link rel="stylesheet" type="text/css" href="css/css.css" />
<link href="css/mobiscroll.custom-2.5.0.min.css" rel="stylesheet"
	type="text/css" />
<meta content="" name="keywords" />
<style type="text/css">
.overlay {
	position: fixed;
	top: 0;
	right: 0;
	bottom: 0;
	left: 0;
	z-index: 998;
	width: 100%;
	height: 100%;
	_padding: 0 20px 0 0;
	background-color: rgba(51, 51, 51, 0.8);
	display: none;
}

.showbox {
	position: fixed;
	top: 0;
	left: 50%;
	z-index: 9999;
	opacity: 0;
	filter: alpha(opacity =   0);
	margin-left: -80px;
}

* html .showbox,* html .overlay {
	position: absolute;
	top: expression(eval(document.documentElement.scrollTop) );
}

#AjaxLoading div.loadingWord {
	width: 180px;
	height: 50px;
	line-height: 50px;
	border: 2px solid #fe8323;
	background: #fff4e8;
	text-align: center;
}
</style>
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript">
	$(function() {
	var profession ="";
			var begin_time ="";
			var end_time = "";
			var company = "";
			var position = "";
			var department = "";
			var description = "";
			var remark = "";
var url=window.location.href;
var workId=url.split("=")[1];	
		
			$.get("http://weixin.craftsmanasia.com/craftsman_weixin/resumeAction/search/workById.do?workId="+workId+"&t="+Math.random(), function(data) {
			var jsonObj = eval("(" + data + ")");
			var obj=jsonObj.work;
			 $('input#company').attr('value',obj.company); 
			 $('input#begin_time').attr('value',obj.beginTime);
			 $('input#end_time').attr('value',obj.endTime);
			 $('input#department').attr('value',obj.department);
			 $('input#position').attr('value',obj.position);
		/* 	 $('input#remark').attr('value',obj.remark); */
			 $('input#profession').attr('value',obj.profession);
			  $('input#description').attr('value',obj.description);			 
			document.getElementById("company").onfocus= function() {
            if(this.value==obj.company){
             this.value="";};
   			};
   			document.getElementById("company").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.company;
   			 };
   			 
   			 
   			 document.getElementById("begin_time").onfocus= function() {
            if(this.value==obj.beginTime){
             this.value="";};
   			};
   			document.getElementById("begin_time").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.beginTime;
   			 };
   			 
   			 
   			 document.getElementById("end_time").onfocus= function() {
            if(this.value==obj.endTime){
             this.value="";};
   			};
   			document.getElementById("end_time").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.endTime;
   			 };
   			 
   			 
   			 document.getElementById("department").onfocus= function() 

{
            if(this.value==obj.department){
             this.value="";};
   			};
   			document.getElementById("department").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.department;
   			 };
   			 
   			 
   			 document.getElementById("position").onfocus= function() {
            if(this.value==obj.position){
             this.value="";};
   			};
   			document.getElementById("position").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.position;
   			 };
   			 
   			 
   			/*  document.getElementById("remark").onfocus= function() {
            if(this.value==obj.remark){
             this.value="";};
   			}; 
   			document.getElementById("remark").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.remark;
   			 };*/
   			 
   			  document.getElementById("profession").onfocus= function() {
            if(this.value==obj.profession){
             this.value="";};
   			};
   			document.getElementById("profession").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.profession;
   			 };
   			 
   			  document.getElementById("description").onfocus= function() 

{
            if(this.value==obj.description){
             this.value="";};
   			};
   			document.getElementById("description").onblur= function() {
       		 if(this.value=="")
            	this.value=obj.description;
   			 };
 			 
    });
			
			
			
			
			
	/* 修改 */		
		$('#btn').click(function() {
		
			begin_time = $('input#begin_time').val();
			 end_time = $('input#end_time').val();
			company = $('input#company').val();
		 position= $('input#position').val();
			 department= $('input#department').val();
			description = $('input#description').val();
			profession = $('input#profession').val();
			var myreg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
			
			if (begin_time == "") {
				alert("请输入开始时间!");
			} else if (end_time == "") {
				alert("请输入结束时间!");
			} else if (company == "") {
				alert("请输入公司名称！");
			} else if (position == "") {
				alert("请输入职位！");
			} else if (department == "") {
				alert("请输入部门！");
			}else if (description == "") {
				alert("请输入描述！");
			}else if (profession == "") {
				alert("请输入专业技能！");
			}else if(begin_time>end_time){
				alert("开始时间不能早于结束时间！");			
			} else{
			$.ajax({
				type : "POST",
				url : "http://weixin.craftsmanasia.com/craftsman_weixin/resumeAction/work/modify.do?t="+Math.random(),

				data : "workId="+workId+"&beginTime="+begin_time+"&endTime="+end_time+"&company="+company+"&position="+position+"&department="+department+"&description="+description+"&profession="+profession,
				success : function(msg) {
				var jsonObj = eval("(" + msg + ")");
				
					if(jsonObj.status==true){
					 window.location.href="myResume.jsp?userId="+jsonObj.userId;
					
					}else{alert(jsonObj.status);}
				
				}
			});
}
		});
	});
</script>

</head>
<body>
	<div class="mtzrl_box">
		<div class="mq_top">

			<div class="mq_title">工作经历</div>
			<div class="btn_ch_r"></div>
		</div>
		<div class="log_box">


			<ul>
				<form >
					<li class="username"><input type="text"  id="begin_time" placeholder="开始时间" />
					</li>
					<br /><br />
					<li class="username"><input type="text"  id="end_time" placeholder="结束时间"/>
					</li>
					<br /><br />
					<li class="username"><input type="text"  id="company" placeholder="公司"/>
					</li>
					<br /><br />
					<li class="username"><input type="text"  id="department" placeholder="部门"/>
					</li>
					<br /><br />
					<li class="username"><input type="text" id="position" placeholder="职位"/>
					</li>
					<br /><br />

					
					<!-- <li class="username"><input type="text"  id="remark" />
					</li><br /><br /> -->
<li class="username"><input type="text"  id="profession" placeholder="专业技能"/>
					</li><br /><br />
<li class="username"><input type="text"  id="description" placeholder="详细描述"/>
					</li>
					
				</form>
				<li class="login_free"></li>
				<li class="btn_submit">
					<button type="button" id="btn">提交</button>
				</li>
			</ul>
		</div>

		<script type="text/javascript" src="js/DeleteSession.js"></script>

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

	</div>
	<div class="overlay">&nbsp;</div>
	<div class="showbox" id="AjaxLoading">
		<div class="loadingWord" style="overflow:hidden;zoom:1">
			<img src="../images/loadingx.gif" alt=""
				style="float:left;padding-top:6px;" />跳转中，请稍候...
		</div>
	</div>
	<script type="text/javascript" src="../js/ShowTip.js"></script>





	<script src="js/mobiscroll.custom-2.5.0.min.js" type="text/javascript"></script>

	<script type="text/javascript">
		$(function() {
			

			//初始化日期控件
			var opt = {
				preset : 'date', //日期，可选：date\datetime\time\tree_list\image_text\select
				theme : 'default', //皮肤样式，可选：default\android\android-ics light\android-ics\ios\jqm\sense-ui\wp light\wp
				display : 'modal', //显示方式 ，可选：modal\inline\bubble\top\bottom
				mode : 'scroller', //日期选择模式，可选：scroller\clickpick\mixed
				lang : 'zh',
				dateFormat : 'yyyy-mm', // 日期格式
				setText : '确定', //确认按钮名称
				cancelText : '取消',//取消按钮名籍我
				dateOrder : 'yyyymm', //面板中日期排列格式
				
				monthText : '月',
				yearText : '年', //面板中年月日文字
				showNow : false,
				nowText : "今",
				startYear : 1950, //开始年份  
				endYear : 2015
			//结束年份  
			//endYear:2099 //结束年份
			};

			$("#begin_time,#end_time").mobiscroll(opt);
		});
	</script>


















</body>
</html>
