package com.craftsmanasia.admin.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	@Autowired
	UserService userService;
	
	@Autowired
	ResumeUserService resumeUserService;
	
	@Autowired
	WorkService workService;
	/*
	 * 返回类型：0成功1电话不能为空,不能存在已有电话2密码不正确3微信号不能为空
	 * 4姓名不能为空5性别不能为空6生日不能为空
	 * */
	@RequestMapping("/create")
	@ResponseBody
	public String registerUser(@RequestParam(value = "telephone", defaultValue = "") String telephone, 
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "repassword", defaultValue = "") String repassword,
			@RequestParam(value = "nickName", defaultValue = "") String nickName,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "home", defaultValue = "") String home,
			@RequestParam(value = "wechatAccount", defaultValue = "") String wechatAccount) {
		Map<String,String> map = new HashMap<String,String>();
		
		if(StringUtil1.isNull(telephone) || userService.getUserByTelephone(telephone) != null) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
		}
		// password不能为空，repassword不能为空，password equals repassword
		if(StringUtil1.isNull(password) || StringUtil1.isNull(repassword) || !password.equals(repassword)) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(name)) {
			map.put("code", "4");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(gender)) {
			map.put("code", "5");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(birthday)) {
			map.put("code", "6");
			return JSONObject.fromObject(map).toString();
		}
		User user = new User();
		user.setTelephone(telephone);
		user.setPassword(password);
		user.setNickName(nickName);
		user.setWechatAccount(wechatAccount);
		userService.add(user);
		ResumeUser resumeUser = new ResumeUser();
		resumeUser.setTelephone(user.getTelephone());
		resumeUser.setName(name);
		resumeUser.setBirthday(birthday);
		resumeUser.setGender(gender);
		resumeUser.setEmail(email);
		resumeUser.setUserId(user.getId());
		resumeUser.setHome(home);
		
		// 添加简历信息
		resumeUserService.add(resumeUser);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 返回类型：0成功1user非法2起始时间为空3company为空4职位为空
	 * */
	@RequestMapping("/resume/create")
	@ResponseBody
	public String createUserResume(@RequestParam(value = "userId", defaultValue = "0") Integer userId,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			@RequestParam(value = "company", defaultValue = "") String company,
			@RequestParam(value = "position", defaultValue = "") String position,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "profession", defaultValue = "") String profession,
			@RequestParam(value = "remark", defaultValue = "") String remark,
			@RequestParam(value = "department", defaultValue = "") String department) {
		Map<String, String> map = new HashMap<String, String>();
		// 添加工作经历
		
		if(userId == null || userId <=0 || userService.getUserById(userId)==null) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(beginTime)) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(endTime)) {
			endTime = "至今";
		}
		if(StringUtil1.isNull(company)) {
			map.put("code", "3");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(position)) {
			map.put("code", "4");
			return JSONObject.fromObject(map).toString();
		}
		Work work = new Work();
		work.setBeginTime(beginTime);
		work.setEndTime(endTime);
		work.setCompany(company);
		work.setPosition(position);
		work.setDepartment(department);
		work.setDescription(description);
		work.setProfession(profession);
		work.setRemark(remark);
		work.setUserId(userId);
		workService.add(work);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}
}
