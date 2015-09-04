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

import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.SearchResult;
import com.craftsmanasia.model.vo.ResumeVO;
import com.craftsmanasia.model.vo.ResumeVO2;
import com.craftsmanasia.service.PositionSubscribeUserService;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/resume")
public class ResumeController {

	@Autowired
	UserService userService;
	
	@Autowired
	ResumeUserService resumeUserService;
	
	@Autowired
	WorkService workService;
	
	@Autowired
	PositionSubscribeUserService positionSubscribeUserService;
	
	/*
	 * admin查询简历
	 * */
	@RequestMapping(value ="/search", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(@RequestParam(value = "pageNumber", defaultValue = "") int pageNumber,
			@RequestParam(value = "pageSize", required=false) int pageSize,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "companyName", required=false) String companyName,
			@RequestParam(value = "title", required=false) String title,
			@RequestParam(value = "telephone", required=false) String telephone) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResumeSubscribeFilter filter = new ResumeSubscribeFilter();
		
		if(!StringUtil1.isNull(name)) {
			filter.setName(name);
		}
		
		if(!StringUtil1.isNull(companyName)) {
			filter.setCompanyName(companyName);
		}
		
		if(!StringUtil1.isNull(title)) {
			filter.setTitle(title);
		}
		
		if(!StringUtil1.isNull(telephone)) {
			filter.setTelephone(telephone);
		}
		
		PagingData pagingData = new PagingData(pageNumber, pageSize);
		filter.setPaged(true);
		filter.setPagingData(pagingData);
		
		SearchResult<PositionSubscribeUser> result = positionSubscribeUserService.searchResumeUsersByFilter(filter);
		
		map.put("data", ResumeVO2.toVOs(result.getResult()));
		map.put("pagingResult", result.getPagingResult());
		map.put("status", "true");
		return JSONObject.fromObject(map).toString();
	}
	
	
}
