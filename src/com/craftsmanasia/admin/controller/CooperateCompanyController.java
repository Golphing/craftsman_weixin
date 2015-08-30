package com.craftsmanasia.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.Company;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.service.CompanyService;
import com.craftsmanasia.service.PositionService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/admin/company")
public class CooperateCompanyController {

	@Autowired
	CompanyService companyService;

	@Autowired
	PositionService positionService;

	/*
	 * 返回类型：0 成功 1 需要公司名称 2 不能有重复的公司名称 3 公司权重不能为空
	 */
	@RequestMapping("/create")
	@ResponseBody
	public String createCooperateCompany(HttpServletRequest request,
			@RequestParam(value = "name", defaultValue = "") String name,
			@RequestParam(value = "url", defaultValue = "") String url,
			@RequestParam(value = "weight", defaultValue = "-1") int weight) {
		Map<String,String> map=new HashMap<String,String>();
		Company company = new Company();
		if (StringUtil1.isNull(name)) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(1, "name can not be null").toString();
		}
		Company oldcompany = companyService.getCompanyByName(name);
		if (oldcompany!=null && oldcompany.getName().equals(name)) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(2, "company already exits").toString();
		}
		if (weight <= 0) {
			map.put("code", "3");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(3, "weight must be > 0").toString();
		}
		
		company.setName(name);
		company.setUrl(url);
		company.setWeight(weight);
		map.put("name", name);
		map.put("url", url);
		map.put("weight", String.valueOf(weight));
		// IsExpired默认为0.创建的时候不会过期
		company.setIsExpired(0);
		Date now = new Date();
		company.setCreateTime(now);
		company.setUpdateTime(now);
		try {
			companyService.add(company);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
		//return JsonUtil.getJson(0, "create success").toString();
	}

	@RequestMapping("/search")
	@ResponseBody
	public List<Company> searchCooperateCompany(HttpServletRequest request) {
		List<Company> companies = companyService.getAllExpiredCompanies();
		return companies;
	}

	/*
	 * 返回类型： 0 成功 1已存在该职位 2.详情不能为Null 3.权重必须大于0
	 * 
	 */
	@RequestMapping("/position/create")
	@ResponseBody
	public String createCooperateCompanyPosition(HttpServletRequest request,
			@RequestParam(value = "companyId", defaultValue = "1") int companyId,
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "requirement", defaultValue = "") String requirement,
			@RequestParam(value = "wage", defaultValue = "") String wage,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "weight", defaultValue = "-1") int weight) {
		Map<String,String> map=new HashMap<String,String>();
		Position position = new Position();
		// title 为null 或该公司已经存在该职位返回1
		
		if (StringUtil1.isNull(title) || positionService.getPositionByCompanyIdAndTitle(companyId, title) != null) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
			// return JsonUtil.getJson(1, "title equals null or title already
			// exits").toString();
		}
		
		position.setTitle(title);
		if(StringUtil1.isNull(requirement)) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(2, "requirement can not be null").toString();
		}
		position.setRequirement(requirement);
		position.setWage(wage);
		position.setCity(city);
		if(weight <= 0) {
			map.put("code", "3");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(3, "weight must > 0").toString();
		}
		position.setWeight(weight);
		if(companyId!=1) {
			map.put("companyId", String.valueOf(companyId));
		}
		map.put("title", title);
		map.put("requirement", requirement);
		map.put("wage", wage);
		map.put("city", city);
		map.put("weight", String.valueOf(weight));
		Date now = new Date();
		position.setCreateTime(now);
		position.setUpdateTime(now);

		position.setCompanyId(companyId);

		// 默认是0，不过期
		position.setIsExpired(0);
		try {
			positionService.addCompanyPosition(position);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
		//return JsonUtil.getJson(0, "create success").toString();
	}

	@RequestMapping(value = "/position/search", method = RequestMethod.GET)
	@ResponseBody
	public List<Position> searchCooperateCompanyPositions(@RequestParam("companyId") Integer companyId) {
		if (companyId == null || companyId == 0) {
			return null;
		}
		List<Position> positions = null;
		try {
			positions = positionService.getCompanyPositionsByCompanyId(companyId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// return new ModelAndView().addObject("positions", positions);
		return positions;
	}
}
