<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
   
    <title>My JSP 'resumeinput.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	 <script src="/craftsman_weixin/js/jquery-1.9.1.min.js" type="text/javascript"></script>
	  <script type="text/javascript" src="http://s.ebaoyang.cn/custom-service/My97DatePicker/WdatePicker.js"></script>
	<style>
		span{
			display: none;
		}
	</style>
  </head>
  
  <body>
    	<form action="/craftsman_weixin/admin/company/create.do" method="post">
    		公司名称：<input name="name" type="text"/><br>
    		公司网站：<input name="url" type="text"/><br>
    		公司权重：<input name="weight" type="text"/><br>
    		<input type="submit" value="添加">
    	</form>
  </body>
</html>
