package com.craftsmanasia.controller;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.craftsmanasia.common.SendMsgUtil;
import com.craftsmanasia.utils.YzmGenerate;
import com.ebaoyang.utils.JsonUtil;

@RequestMapping("/register/")
public class MsgValidateController {	
	@RequestMapping("/sendV")
	public String sendValidate(HttpSession session,@RequestParam(value="phoneNumber") String phoneNumber){
		String yzm=YzmGenerate.getRandNum(6);
		session.setAttribute("yzm", yzm);
		String content="您的验证码是："+yzm+" ,请不要告知他人";
		String status=SendMsgUtil.send(content, phoneNumber);
		return status;
	}
}
