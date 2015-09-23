package com.craftsmanasia.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wechat/user")
public class WechatUserController {
	@Autowired
	UserService userService;
	
	@Autowired
	ResumeUserService resumeUserService;
	
	/*
	 * 注册用户
	 * */
	@RequestMapping("/register")
	@ResponseBody
	public String registerUser(HttpSession session,
			@RequestParam(value = "openId", defaultValue = "") String openId,
			@RequestParam(value = "telephone", defaultValue = "") String telephone, 
			@RequestParam(value = "yzm", defaultValue = "") String yzm,
			@RequestParam(value = "password", defaultValue = "") String password
			) {
		
		Map<String,Object> map = new HashMap<String,Object>();
		String yzm1=(String) session.getAttribute("yzm");
		if(yzm1==null || !yzm1.equals(yzm)){
			map.put("status", "验证码不正确");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(telephone) || userService.getUserByTelephone(telephone) != null) {
			map.put("status", "电话不能为空或已存在该电话");

			return JSONObject.fromObject(map).toString();
		}
		if(userService.getUserByTelephone(telephone) != null) {
			map.put("status", "已存在该电话");
			return JSONObject.fromObject(map).toString();
		}

		User user = new User();
		user.setTelephone(telephone);
		user.setPassword(password);
		user.setOpenId(openId);
		userService.add(user);		
		user = userService.getUserByTelephone(telephone);		
		map.put("status", user.getId());
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping("/resume/create")
	@ResponseBody
	public String createResume(@RequestParam(value = "telephone", defaultValue = "") Integer userId,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "home", defaultValue = "") String home) {
		Map<String,Object> map = new HashMap<String,Object>();
		ResumeUser resumeUser = new ResumeUser();
		resumeUser.setTelephone(telephone);
		resumeUser.setName(name);
		resumeUser.setBirthday(birthday);
		resumeUser.setGender(gender);
		resumeUser.setEmail(email);
		resumeUser.setUserId(userId);
		resumeUser.setHome(home);
		resumeUserService.add(resumeUser);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping("/work/create")
	@ResponseBody
	public String createWork(@RequestParam(value = "telephone", defaultValue = "") Integer userId,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "home", defaultValue = "") String home) {
		Map<String,Object> map = new HashMap<String,Object>();
		ResumeUser resumeUser = new ResumeUser();
		resumeUser.setTelephone(telephone);
		resumeUser.setName(name);
		resumeUser.setBirthday(birthday);
		resumeUser.setGender(gender);
		resumeUser.setEmail(email);
		resumeUser.setUserId(userId);
		resumeUser.setHome(home);
		resumeUserService.add(resumeUser);
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
