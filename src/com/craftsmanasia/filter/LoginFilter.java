package com.craftsmanasia.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

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
		arg0.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		arg1.setContentType("text/html;charset=UTF-8");  
		arg1.setCharacterEncoding("UTF-8");
		Boolean isLogin =(Boolean) request.getSession().getAttribute("isLogin");
		String path=request.getRequestURI();
		System.out.println(path);
		if(path.indexOf("login")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf("hand.png")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf(".jsp")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf(".png")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf(".js")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		if(path.indexOf("index.jsp")>-1){
			arg2.doFilter(arg0, arg1);
			return;
		}
		arg2.doFilter(arg0, arg1);
		return;
		/*if(isLogin==null){		
			System.out.println("enter");
			request.getRequestDispatcher("/views/login.jsp").forward(arg0, arg1);
		}else{
			arg2.doFilter(arg0, arg1);
		}*/

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("login filter init..");
	}

}
