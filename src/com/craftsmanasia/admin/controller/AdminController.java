package com.craftsmanasia.admin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.craftsmanasia.filter.AdministratorFilter;
import com.craftsmanasia.model.Administrator;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;
import com.craftsmanasia.model.vo.AdministratorVO;
import com.craftsmanasia.request.SearchAdminstratorRequest;
import com.craftsmanasia.service.AdministratorService;
import com.craftsmanasia.utils.OrderingProperty;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/administrator")
public class AdminController {
	
	@Autowired
	AdministratorService administratorService;

	/*
	 * 添加管理员
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createAdministrator(@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "telephone", defaultValue = "") String telephone,
			@RequestParam(value = "email", defaultValue = "") String email,
			@RequestParam(value = "password", defaultValue = "") String password) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		if(StringUtil1.isNull(name)) {
			map.put("status", "name can not be null");
			return JSONObject.fromObject(map).toString();
		}
		
		if(StringUtil1.isNull(loginName) || administratorService.getAdministratorByLoginName(loginName) != null) {
			map.put("status", "loginName can not be null or loginName already exists");
			return JSONObject.fromObject(map).toString();
		}
		
		if(StringUtil1.isNull(telephone)) {
			map.put("status", "telephone can not be null");
			return JSONObject.fromObject(map).toString();
		}
		
		if(StringUtil1.isNull(password)) {
			map.put("status", "password can not be null");
			return JSONObject.fromObject(map).toString();
		}
		
		Administrator administrator = new Administrator();
		administrator.setName(name);
		administrator.setLoginName(loginName);
		administrator.setEmail(email);
		administrator.setTelephone(telephone);
		administrator.setPassword(password);
		
		Date now = new Date();
		administrator.setCreateTime(now);
		administrator.setUpdateTime(now);
		administratorService.createAdministrator(administrator);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public String updateAdministrator(@RequestParam(value = "id", defaultValue = "") Integer id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "loginName", required = false) String loginName,
			@RequestParam(value = "telephone", required = false) String telephone,
			@RequestParam(value = "email", required = false) String email,
			@RequestParam(value = "password", required = false) String password) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		if(id == null) {
			map.put("status", "id error");
			return JSONObject.fromObject(map).toString();
		}
		
		if(!StringUtil1.isNull(loginName) && administratorService.getAdministratorByLoginName(loginName) != null) {
			map.put("status", "loginName already exists");
			return JSONObject.fromObject(map).toString();
		}
		
		Administrator administrator = new Administrator();
		administrator.setId(id);
		if(!StringUtil1.isNull(name)) {
			administrator.setName(name);
		}
		
		if(!StringUtil1.isNull(loginName)) {
			administrator.setLoginName(loginName);
		}

		if(!StringUtil1.isNull(email)) {
			administrator.setEmail(email);
		}
		
		if(!StringUtil1.isNull(telephone)) {
			administrator.setTelephone(telephone);
		}
		
		if(!StringUtil1.isNull(password)) {
			administrator.setPassword(password);
		}
		
		Date now = new Date();
		administrator.setUpdateTime(now);
		administratorService.updateAdministrator(administrator);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "/search",method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchAdministrator(SearchAdminstratorRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		if(!request.validatePagingRequest()) {
			map.put("status", "请求页数必须大于0,每页个数必须在0到1000之间");
			return JSONObject.fromObject(map).toString();
		}
		AdministratorFilter filter = new AdministratorFilter();
		
		if(!StringUtil1.isNull(request.getLoginName())) {
			filter.setLoginName(request.getLoginName());
		}
		
		if(!StringUtil1.isNull(request.getName())) {
			filter.setName(request.getName());
		}
		
		if(!StringUtil1.isNull(request.getTelephone())) {
			filter.setTelephone(request.getTelephone());
		}
		
		PagingData pagingData = new PagingData(request.getPageNumber(), request.getPageSize());
		filter.setPagingData(pagingData);
		filter.setPaged(true);
		
		boolean ordered = false;
		List<OrderingProperty> orderingProperties = null;
		if (StringUtils.isNotBlank(request.getOrderBy())) {
	        orderingProperties = new ArrayList<OrderingProperty>();
			OrderingProperty orderingProperty = new OrderingProperty(1, request.getOrderBy(), request.isAsc());
			orderingProperties.add(orderingProperty);
	
			ordered = true;
        }
		filter.setOrdered(ordered);
		filter.setOrderingProperties(orderingProperties);
		
		SearchResult<Administrator> result = administratorService.getAdministratorsByFilter(filter);
		
		List<Administrator> administrators = result.getResult();
		PagingResult pagingResult = result.getPagingResult();
		map.put("data", AdministratorVO.toVOs(administrators));
		map.put("pagingResult", pagingResult);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "/delete",method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String deleteAdministrator(@RequestParam(value = "id", defaultValue = "") Integer id) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(id == null) {
			map.put("status", "没有该管理员");
			return JSONObject.fromObject(map).toString();
		}
		administratorService.deleteAdministratorById(id);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping("/login")
	public ModelAndView login(HttpServletRequest request,HttpSession httpSession,
			@RequestParam(value = "loginName", defaultValue = "") String loginName,
			@RequestParam(value = "password", defaultValue = "") String password) {
		if(StringUtil1.isNull(loginName) || StringUtil1.isNull(password)) {
			// loginName 或 password为空，返回登录页面
			//return "redirect:/admin/views/login.html";
			return new ModelAndView(new RedirectView("../../admin/views/login.html"));
		}
		
		Administrator administrator = administratorService.getAdministratorByLoginName(loginName);
		if(administrator == null) {
			// loginName的administrator不存在，返回登录页面
			//return "redirect:/admin/views/login.html";
			return new ModelAndView(new RedirectView("../../admin/views/login.html"));
		}
		
		if(!administrator.getPassword().equals(password)) {
			// 密码不正确返回登录页面
			//return "redirect:/admin/views/login.html";
			return new ModelAndView(new RedirectView("../../admin/views/login.html"));
		}
		
		httpSession.setAttribute("adminUser", administrator);
		//request.setAttribute("adminUser", administrator);

		System.out.println("yes");
		return new ModelAndView(new RedirectView("../../admin/views/index.html"));
		//return "redirect:/admin/views/index.html";
	}
	
	@RequestMapping("/loginout")
	public ModelAndView loginOut(HttpServletRequest request,HttpSession httpSession) {
		
		//request.removeAttribute("adminUser");
		httpSession.removeAttribute("adminUser");
		//return "redirect:/admin/views/login.html";
		return new ModelAndView(new RedirectView("../../admin/views/login.html"));
	}
}
