<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<meta charset="utf-8">
<title>卡富文思</title>
<meta name="Keywords" content="">
<meta name="Description" content="">

<!-- 移动设备支持 -->
<meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
<meta content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no" name="viewport">
<meta content="no-cache,must-revalidate" http-equiv="Cache-Control">
<meta content="no-cache" http-equiv="pragma">
<meta content="0" http-equiv="expires">
<meta content="telephone=no, address=no" name="format-detection">
<meta name="apple-mobile-web-app-capable" content="yes"> 
<meta name="apple-mobile-web-app-status-bar-style" content="black-translucent">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />
<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/mod06.css" rel="stylesheet" type="text/css">






</head>

<body>
<div id="wrap" style="background: url('images/11.jpg');background-size:100%;">
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
  
  </div>
   <script type="text/javascript" src="js/jweixin-1.0.0.js"></script>
<script type="text/javascript" src="js/wxsharejs.js?v=1.3"></script><a href="tel:4000601218 ">
<div class="telphone"><img src="images/tel.png"></div>
</a>
<script type="text/javascript" src="js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="js/wxsharejs.js"></script>

</a>




<div class="blankwhite" style="height:30px;"></div>


<div id="div_fx_describe" style="display:none;"></div>

<script type="text/javascript"> 
 
$(document).ready(function() {

			var url=window.location.href;
var userId=url.split("?")[1].split("=")[1];

document.getElementById("fillResume").href="fillResume.jsp?userId="+userId;
document.getElementById("51job").href="../views/resume_import/51input.jsp?userId="+userId;
document.getElementById("zhilian").href="../views/resume_import/zlinput.jsp?userId="+userId;
document.getElementById("liepin").href="../views/resume_import/lpinput.jsp?userId="+userId;

						

})
</script>
</div>
</body>
</html>


