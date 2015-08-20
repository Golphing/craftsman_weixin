package com.craftsmanasia.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.craftsmanasia.model.User;
import com.craftsmanasia.service.UserService;


@Controller
@RequestMapping("/login1")
public class LoginController {
	@Autowired
	UserService userService;
	
	@RequestMapping("/showLogin")
	public String showLogin(){
		return "login";
	}
	
	
	@RequestMapping("/register")
	public String register(HttpServletRequest request,
							@RequestParam(value="name",defaultValue="") String name,
							@RequestParam(value="password",defaultValue="") String password,
							@RequestParam(value="password1",defaultValue="") String password1,
							Model model
							
	){
		if(password !=""){
			if(!password.equals(password1)){
				String error ="两次输入密码不一致";
				model.addAttribute("error", error);
				return "register";
			}
		}
		User user=new User();
		user.setName(name);
		user.setPassword(password);
		userService.add(user);
		request.getSession().setAttribute("isLogin", true);
		request.getSession().setAttribute("name", name);
		request.getSession().setAttribute("id", user.getId());
		return "redirect:/index.jsp";
	}
	
	@RequestMapping("/logInn")
	public String logInn(HttpServletRequest request,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password){
		System.out.println(name);
		System.out.println(password);
		User user=userService.get(name, password);
		if(user==null){
			return "login";
		}else{
			request.getSession().setAttribute("isLogin", true);
			request.getSession().setAttribute("name", name);
			request.getSession().setAttribute("id", user.getId());
			return "redirect:/jobRecord/list.do";
		}
		
	}
	
	@RequestMapping("/logOut")
	public String logOut(HttpServletRequest request){
		request.getSession().removeAttribute("isLogin");
		request.getSession().removeAttribute("name");
		request.getSession().removeAttribute("id");
		return "redirect:/jobRecord/showLogin.do";
	}
}
