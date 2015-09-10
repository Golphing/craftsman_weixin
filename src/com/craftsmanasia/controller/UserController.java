package com.craftsmanasia.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.User;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wechat/user")
public class UserController {
	@Autowired
	UserService userService;
	
	@Autowired
	ResumeUserService resumeUserService;
	
	/*
	 * 注册用户
	 * */
	@RequestMapping("/register")
	@ResponseBody
	public String registerUser(@RequestParam(value = "telephone", defaultValue = "") String telephone, 
			@RequestParam(value = "password", defaultValue = "") String password) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtil1.isNull(telephone) || userService.getUserByTelephone(telephone) != null) {
			map.put("status", "电话不能为空或已存在该电话");
			return JSONObject.fromObject(map).toString();
		}
		// password不能为空
		if(StringUtil1.isNull(password)) {
			map.put("status", "密码不能为空 ");
			return JSONObject.fromObject(map).toString();
		}
		User user = new User();
		user.setTelephone(telephone);
		user.setPassword(password);
		userService.add(user);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping("/login")
	public String login(HttpServletRequest request,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "password", defaultValue = "") String password) {
		if(StringUtil1.isNull(telephone) || StringUtil1.isNull(password)) {
			// loginName 或 password为空，返回登录页面
			return "/admin/views/login.html";
		}
		
		User user = userService.getUserByTelephone(telephone);
		if(user == null) {
			// user不存在，返回注册页面
			return "/register.jsp";
		}
		
		if(!user.getPassword().equals(password)) {
			// 密码不正确返回登录页面
			return "/login.jsp";
		}
		
		request.setAttribute("islogin", user);
		// 返回到哪里
		
		return "/admin/views/index.html";
	}
}
