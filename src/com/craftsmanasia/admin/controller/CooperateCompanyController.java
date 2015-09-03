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
import com.craftsmanasia.model.vo.CompanyVO;
import com.craftsmanasia.model.vo.PositionVO;
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
		// IsExpired默认为0.创建的时候不会过期
		company.setIsExpired(0);
		Date now = new Date();
		company.setCreateTime(now);
		company.setUpdateTime(now);
		
		companyService.add(company);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
		//return JsonUtil.getJson(0, "create success").toString();
	}

	@RequestMapping("/search")
	@ResponseBody
	public String searchCooperateCompany(HttpServletRequest request) {
		Map<String,Object> map=new HashMap<String,Object>();
		List<Company> companies = companyService.getAllExpiredCompanies();
		map.put("companies", CompanyVO.toVOs(companies));
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
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
		Map<String,Object> map=new HashMap<String,Object>();
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
		Date now = new Date();
		position.setCreateTime(now);
		position.setUpdateTime(now);

		position.setCompanyId(companyId);

		// 默认是0，不过期
		position.setIsExpired(0);
		positionService.addCompanyPosition(position);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}

	/*
	 * 返回类型：0成功1companyId非法
	 * */
	@RequestMapping(value = "/position/search", method = RequestMethod.GET)
	@ResponseBody
	public String searchCooperateCompanyPositions(@RequestParam("companyId") Integer companyId) {
		Map<String,Object> map=new HashMap<String,Object>();
		if (companyId == null || companyId == 0) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
		}
		List<Position> positions = positionService.getCompanyPositionsByCompanyId(companyId);
		
		List<PositionVO> positionvos = PositionVO.toVOs(positions);
		map.put("vos", positionvos);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}
	/*
	 * 返回类型：0成功，1positionId非法2weight非法
	 * */
	@RequestMapping(value = "/position/modify")
	@ResponseBody
	public String modifyCooperateCompanyPosition(@RequestParam(value = "positionId", defaultValue = "0") int positionId, 
			@RequestParam(value = "title", defaultValue = "") String title,
			@RequestParam(value = "requirement", defaultValue = "") String requirement,
			@RequestParam(value = "wage", defaultValue = "") String wage,
			@RequestParam(value = "city", defaultValue = "") String city,
			@RequestParam(value = "weight", defaultValue = "0") int weight) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(positionId <=0) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
		}
		if(weight <=0) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
		}
		Position newPosition = new Position();
		newPosition.setId(positionId);
		if(!StringUtil1.isNull(title)) {
			newPosition.setTitle(title);
		}
		if(!StringUtil1.isNull(requirement)) {
			newPosition.setRequirement(requirement);
		}
		if(!StringUtil1.isNull(wage)) {
			newPosition.setWage(wage);
		}
		if(!StringUtil1.isNull(city)) {
			newPosition.setCity(city);
		}
		if(weight >=0) {
			newPosition.setWeight(weight);
		}
		newPosition.setUpdateTime(new Date());
		positionService.updateCompanyPosition(newPosition);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "/position/modify/status")
	@ResponseBody
	public String modifyPositionStatus(@RequestParam(value = "positionId", defaultValue = "0") int positionId,
			@RequestParam(value="isExpired", defaultValue = "-1") Integer isExpired) {
		Map<String,String> map = new HashMap<String,String>();
		if(positionId <= 0) {
			map.put("code", "1");
			return JSONObject.fromObject(map).toString();
		}
		if(isExpired <=0) {
			map.put("code", "2");
			return JSONObject.fromObject(map).toString();
		}
		Position position = new Position();
		position.setId(positionId);
		position.setIsExpired(isExpired);
		position.setUpdateTime(new Date());
		positionService.updateCompanyPosition(position);
		map.put("code", "0");
		return JSONObject.fromObject(map).toString();
	}
}
