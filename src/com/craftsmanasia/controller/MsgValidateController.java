package com.craftsmanasia.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.common.SendMsgUtil;
import com.craftsmanasia.utils.YzmGenerate;
import com.ebaoyang.utils.JsonUtil;
@Controller
@RequestMapping("/yzm/")
public class MsgValidateController {	
	@RequestMapping("/sendV")
	@ResponseBody
	public String sendValidate(HttpSession session,@RequestParam(value="phoneNumber") String phoneNumber){
		String yzm=YzmGenerate.getRandNum(6);
		session.setAttribute("yzm", yzm);
		String content="您的验证码是："+yzm+" ,请不要告知他人";
		String status=SendMsgUtil.send(content, phoneNumber);
		return JsonUtil.getJsonObject("status", status).toString();	
	}
}
