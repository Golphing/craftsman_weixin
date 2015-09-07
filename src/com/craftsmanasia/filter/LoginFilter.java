package com.craftsmanasia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		// TODO Auto-generated method stub
		HttpServletRequest request=(HttpServletRequest) arg0;
		HttpServletResponse response=(HttpServletResponse) arg1;
		arg0.setCharacterEncoding("UTF-8");
		arg1.setContentType("text/html;charset=UTF-8");  
		arg1.setCharacterEncoding("UTF-8");
		Boolean isLogin =(Boolean) request.getSession().getAttribute("isLogin");
		Cookie[] cooks=request.getCookies();
		boolean hasSessionId=false;
		int len=0;
		if(cooks!=null)
			len=cooks.length;
		for(int i=0;i<len;i++){
			if("JSESSIONID".equals(cooks[i].getName())){
				hasSessionId=true;
				break;
			}
		}
		if(!hasSessionId){
			String sessionid=request.getSession().getId();
			Cookie sessionId=new Cookie("JSESSIONID", sessionid); 
			sessionId.setMaxAge(60 * 60 * 24 * 14);
			response.addCookie(sessionId);
			
		}		
		String path=request.getRequestURI();
		System.out.println(path);	
		arg2.doFilter(arg0, arg1);
		return;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("login filter init..");
	}

}
