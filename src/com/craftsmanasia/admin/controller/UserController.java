package com.craftsmanasia.admin.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.model.vo.ResumeVO;
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
	 * 注册用户，并向简历表添加信息
	 * */
	@RequestMapping("/create")
	@ResponseBody
	public String registerUser(@RequestParam(value = "telephone", defaultValue = "") String telephone, 
			@RequestParam(value = "password", defaultValue = "") String password,
			@RequestParam(value = "nickName", defaultValue = "") String nickName,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "home", defaultValue = "") String home,
			@RequestParam(value = "wechatAccount", defaultValue = "") String wechatAccount) {
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
		if(StringUtil1.isNull(name)) {
			map.put("status", "姓名不能为空");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(gender)) {
			map.put("status", "性别不能为空");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(birthday)) {
			map.put("status", "生日不能为空");
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
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 获取user信息
	 * */
	@RequestMapping(value ="/search", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(@RequestParam(value = "telephone", defaultValue = "") String telephone) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getUserByTelephone(telephone);
		map.put("data", user);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 获取简历信息，包括工作经历
	 * */
	@RequestMapping(value = "/search/resume",method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUserl(@RequestParam(value = "userId", defaultValue = "") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResumeUser resumeUser = resumeUserService.selectResumeUserByUserId(userId);
		List<Work> works = workService.getUserWorksByUserId(userId);
		
		map.put("data", ResumeVO.toVO(resumeUser, works));
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 添加工作经历
	 * */
	@RequestMapping("/work/create")
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
		Map<String, Object> map = new HashMap<String, Object>();
		// 添加工作经历
		
		if(userId == null || userId <=0 || userService.getUserById(userId)==null) {
			map.put("status", "该用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(beginTime)) {
			map.put("status", "起始时间不能为空");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(endTime)) {
			endTime = "至今";
		}
		if(StringUtil1.isNull(company)) {
			map.put("status", "公司不能为空");
			return JSONObject.fromObject(map).toString();
		}
		if(StringUtil1.isNull(position)) {
			map.put("status", "职位不能为空");
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
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 修改工作经历
	 * */
	@RequestMapping("/work/modify")
	@ResponseBody
	public String modifyUserResume(@RequestParam(value = "workId", defaultValue = "") Integer workId,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			@RequestParam(value = "company", defaultValue = "") String company,
			@RequestParam(value = "position", defaultValue = "") String position,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "profession", defaultValue = "") String profession,
			@RequestParam(value = "remark", defaultValue = "") String remark,
			@RequestParam(value = "department", defaultValue = "") String department) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(workId ==null) {
			map.put("status", "工作经历不存在");
			return JSONObject.fromObject(map).toString();
		}
		
		Work work = new Work();
		work.setId(workId);
		if(!StringUtil1.isNull(beginTime)) {
			work.setBeginTime(beginTime);
		}
		
		if(!StringUtil1.isNull(endTime)) {
			work.setEndTime(endTime);
		}
		
		if(!StringUtil1.isNull(company)) {
			work.setCompany(company);
		}
		
		if(!StringUtil1.isNull(position)) {
			work.setPosition(position);
		}
		
		if(!StringUtil1.isNull(department)) {
			work.setDepartment(department);
		}
		
		if(!StringUtil1.isNull(description)) {
			work.setDescription(description);
		}
		
		if(!StringUtil1.isNull(profession)) {
			work.setProfession(profession);
		}
		
		if(!StringUtil1.isNull(department)) {
			work.setDepartment(department);
		}
		
		workService.updatWork(work);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 删除工作经历
	 * */
	@RequestMapping(value = "/work/delete", method = RequestMethod.GET)
	@ResponseBody
	public String deleteUserResume(@RequestParam(value = "workId", defaultValue = "0") Integer workId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(workId <= 0) {
			map.put("status", "工作经历不存在");
			return JSONObject.fromObject(map).toString();
		}
		workService.deleteWork(workId);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
}
