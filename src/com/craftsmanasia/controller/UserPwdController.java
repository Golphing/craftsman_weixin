package com.craftsmanasia.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.common.SendMsgUtil;
import com.craftsmanasia.model.User;
import com.craftsmanasia.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserPwdController {
	@Autowired
	UserService userService;

	@RequestMapping(value ="/findPwd", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String findPwd(HttpSession session,
							@RequestParam(value = "telephone", defaultValue = "") String telephone,
							@RequestParam(value = "yzm", defaultValue = "") String yzm,
							Model model){
		String yzm1=(String) session.getAttribute("yzm");
		Map<String,Object> map = new HashMap<String,Object>();
		if(yzm1==null || !yzm1.equals(yzm)){
			map.put("status", "验证码不正确");
			return JSONObject.fromObject(map).toString();
		}
		User user=userService.getUserByTelephone(telephone);
		if(user==null){
			map.put("status", "没有该用户");
			return JSONObject.fromObject(map).toString();
		}
		String password=user.getPassword();
		String content="您的密码为："+password;
		SendMsgUtil.send(content, telephone);
		map.put("status", "密码已发送到注册手机上");
		return JSONObject.fromObject(map).toString();
		
		
	}
}
