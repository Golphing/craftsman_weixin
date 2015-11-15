package com.craftsmanasia.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.filter.UserFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.SearchResult;
import com.craftsmanasia.model.vo.ResumeVO;
import com.craftsmanasia.model.vo.UserVO;
import com.craftsmanasia.request.SearchUserRequest;
import com.craftsmanasia.service.PositionSubscribeUserService;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.OrderingProperty;
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
	
	@Autowired
	PositionSubscribeUserService positionSubscribeUserService;
	/*
	 * 注册用户，并向简历表添加信息
	 * */
	@RequestMapping(value ="/create", produces = "text/plain;charset=UTF-8")
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
		
		// 用户由后台注册设置为1
		user.setRegisterType(1);
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
	
	@RequestMapping(value ="/resume/create", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String createResumeUser(@RequestParam(value = "telephone", defaultValue = "") String telephone, 
			@RequestParam(value = "userId", defaultValue = "") String userId,
			@RequestParam(value = "gender", defaultValue = "") String gender,
			@RequestParam(value = "birthday", defaultValue = "") String birthday,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "home", defaultValue = "") String home) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		if(StringUtil1.isNull(telephone)) {
			map.put("status", "电话不能为空");
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
		ResumeUser oldResumeUser =resumeUserService.selectResumeUserByUserId(Integer.parseInt(userId));
		if(oldResumeUser != null) {
			oldResumeUser.setTelephone(telephone);
			oldResumeUser.setName(name);
			oldResumeUser.setBirthday(birthday);
			oldResumeUser.setGender(gender);
			oldResumeUser.setEmail(email);
			
			oldResumeUser.setUserId(Integer.parseInt(userId));
			oldResumeUser.setHome(home);
			resumeUserService.updateResumeUser(oldResumeUser);
		} else {
			ResumeUser resumeUser = new ResumeUser();
			resumeUser.setTelephone(telephone);
			resumeUser.setName(name);
			resumeUser.setBirthday(birthday);
			resumeUser.setGender(gender);
			resumeUser.setEmail(email);
			
			resumeUser.setUserId(Integer.parseInt(userId));
			resumeUser.setHome(home);
			
			// 添加简历信息
			resumeUserService.add(resumeUser);
		}
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	
	/*
	 * 获取user信息
	 * */
	@RequestMapping(value ="/search/bytelephone", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(@RequestParam(value = "telephone", defaultValue = "") String telephone) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userService.getUserByTelephone(telephone);
		map.put("data", user);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value ="/search", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(SearchUserRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!request.validatePagingRequest()) {
			map.put("status", "pageNumber必须大于0,pageSize必须大于0且小于1000");
			return JSONObject.fromObject(map).toString();
		}
		UserFilter filter = new UserFilter();
		
		if(!StringUtil1.isNull(request.getName())) {
			filter.setName(request.getName());
		}
		
		if(!StringUtil1.isNull(request.getNickName())) {
			filter.setNickName(request.getNickName());
		}
		
		if(!StringUtil1.isNull(request.getWechatAccount())) {
			filter.setWechatAccount(request.getWechatAccount());
		}
		
		if(!StringUtil1.isNull(request.getTelephone())) {
			filter.setTelephone(request.getTelephone());
		}
		
		// 后台查询时查询由admin注册的user
		filter.setRegisterType(1);
		
		PagingData pagingData = new PagingData(request.getPageNumber(), request.getPageSize());
		filter.setPaged(true);
		filter.setPagingData(pagingData);
		
		List<OrderingProperty> orderingProperties = null;
    	boolean ordered = false;
        if (StringUtils.isNotBlank(request.getOrderBy())) {
	        orderingProperties = new ArrayList<OrderingProperty>();
			OrderingProperty orderingProperty = new OrderingProperty(1, request.getOrderBy(), request.isAsc());
			orderingProperties.add(orderingProperty);
	
			ordered = true;
        }
		
        filter.setOrdered(ordered);
        filter.setOrderingProperties(orderingProperties);
		
		SearchResult<User> result = userService.searchUsersByUserFilter(filter);

		map.put("data", UserVO.toVOs(result.getResult()));
		map.put("pagingResult", result.getPagingResult());
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 修改简历信息
	 * */
	@RequestMapping("/resume/modify")
	@ResponseBody
	public String modifyUserResume(@RequestParam(value = "resumeId", defaultValue = "") Integer resumeId,
			@RequestParam(value = "userId", defaultValue = "") Integer userId,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "telephone", required = false) String telephone,
			@RequestParam(value = "birthday", required = false) String birthday,
			@RequestParam(value = "gender", required = false) String gender,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "home", required = false) String home) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(resumeId ==null) {
			map.put("status", "简历信息不存在");
			return JSONObject.fromObject(map).toString();
		}
		
		ResumeUser resumeUser = new ResumeUser();
		resumeUser.setId(resumeId);
		
		if(!StringUtil1.isNull(name)) {
			resumeUser.setName(name);
		}
		
		if(!StringUtil1.isNull(telephone)) {
			resumeUser.setTelephone(telephone);
			
			//如果telephone不为null,则也需要改变user的telephone
			User user = new User();
			user.setId(userId);
			user.setTelephone(telephone);
			userService.update(user);
		}
		
		if(!StringUtil1.isNull(birthday)) {
			resumeUser.setBirthday(birthday);
		}
		
		if(!StringUtil1.isNull(gender)) {
			resumeUser.setGender(gender);
		}
		
		if(!StringUtil1.isNull(email)) {
			resumeUser.setEmail(email);
		}
		
		if(!StringUtil1.isNull(home)) {
			resumeUser.setHome(home);
		}
		
		resumeUserService.updateResumeUser(resumeUser);
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
		ResumeUser resumeUser = null;
		try {
			
			resumeUser = resumeUserService.selectResumeUserByUserId(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public String createUserResume(@RequestParam(value = "userId", defaultValue = "0") String userId,
			@RequestParam(value = "beginTime", defaultValue = "") String beginTime,
			@RequestParam(value = "endTime", defaultValue = "") String endTime,
			@RequestParam(value = "company", defaultValue = "") String company,
			@RequestParam(value = "position", defaultValue = "") String position,
			@RequestParam(value = "description", defaultValue = "") String description,
			@RequestParam(value = "profession", defaultValue = "") String profession,
			@RequestParam(value = "remark", required=false) String remark,
			@RequestParam(value = "department", defaultValue = "") String department) {

		Map<String, Object> map = new HashMap<String, Object>();
		// 添加工作经历
		if(userId == null || Integer.parseInt(userId) <=0 || userService.getUserById(Integer.parseInt(userId))==null) {
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
		work.setUserId(Integer.parseInt(userId));
		workService.add(work);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 修改工作经历
	 * */
	@RequestMapping("/work/modify")
	@ResponseBody
	public String modifyUserWork(@RequestParam(value = "workId", defaultValue = "") Integer workId,
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
	/*
	 * 获取工作信息
	 * */
	@RequestMapping(value = "/search/workById",method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String workById(@RequestParam(value = "workId", defaultValue = "") int workId) {
		Map<String, Object> map = new HashMap<String, Object>();
		Work work = workService.getUserWorksByWorkId(workId);
		
		map.put("work", work);
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
}
