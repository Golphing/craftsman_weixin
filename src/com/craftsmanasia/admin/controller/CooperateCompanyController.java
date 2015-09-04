package com.craftsmanasia.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.filter.CooperateCompanyFilter;
import com.craftsmanasia.filter.PositionFilter;
import com.craftsmanasia.model.Company;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;
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
		Map<String,Object> map=new HashMap<String,Object>();
		Company company = new Company();
		if (StringUtil1.isNull(name)) {
			map.put("msg", "company name cannot be null");
			return JSONObject.fromObject(map).toString();
		}
		Company oldcompany = companyService.getCompanyByName(name);
		if (oldcompany!=null && oldcompany.getName().equals(name)) {
			map.put("msg", "company name already exits");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(2, "company already exits").toString();
		}
		if (weight <= 0) {
			map.put("msg", "weight cannot be null");
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
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
		//return JsonUtil.getJson(0, "create success").toString();
	}

	/*
	 * 返回类型：0 成功 1 需要公司名称 
	 */
	@RequestMapping(value = "/modify", method = RequestMethod.POST)
	@ResponseBody
	public String modifyCooperateCompany(@RequestParam(value = "id", defaultValue = "") int id,
			@RequestParam(value = "url", required=false) String url,
			@RequestParam(value = "isExpired", required=false) Integer isExpired,
			@RequestParam(value = "weight", required=false) Integer weight) {
		Map<String,Object> map=new HashMap<String,Object>();
		Company company = new Company();
		company.setId(id);
		if (!StringUtil1.isNull(url)) {
			company.setUrl(url);
		}
		
		if (weight!=null && weight <=0) {
			map.put("status", "weight must > 0");
			return JSONObject.fromObject(map).toString();
		}
		company.setWeight(weight);
		company.setIsExpired(isExpired);
		company.setUpdateTime(new Date());
		
		companyService.update(company);
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 返回类型：0 成功 1 需要公司名称 
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public String deleteCooperateCompany(@RequestParam(value = "id", defaultValue = "") int id) {
		Map<String,Object> map=new HashMap<String,Object>();
		Company company = new Company();
		company.setId(id);
		company.setIsExpired(1);
		company.setUpdateTime(new Date());
		
		companyService.update(company);
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchCooperateCompany(@RequestParam(value = "pageNumber", defaultValue = "") int pageNumber,
			@RequestParam(value = "pageSize", defaultValue = "") int pageSize,
			@RequestParam(value = "name", required=false) String name,
			@RequestParam(value = "isExpired", required=false) Integer isExpired) {
		Map<String,Object> map=new HashMap<String,Object>();
		CooperateCompanyFilter filter = new CooperateCompanyFilter();
		
		filter.setIsExpired(isExpired);
		if(!StringUtil1.isNull(name)) {
			filter.setName(name);
		}
		
		PagingData pagingData = new PagingData(pageNumber, pageSize);
		filter.setPagingData(pagingData);
		filter.setPaged(true);
		
		SearchResult<Company> result = companyService.searchCooperateCompany(filter);
		PagingResult pagingResult = result.getPagingResult();
		map.put("data", CompanyVO.toVOs(result.getResult()));
		map.put("pagingResult", pagingResult);
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}

	/*
	 * 返回类型： 0 成功 1已存在该职位 2.详情不能为Null 3.权重必须大于0
	 * 
	 */
	@RequestMapping("/position/create")
	@ResponseBody
	public String createCooperateCompanyPosition(
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
			map.put("status", "该职位名称已经存在");
			return JSONObject.fromObject(map).toString();
			// return JsonUtil.getJson(1, "title equals null or title already
			// exits").toString();
		}
		
		position.setTitle(title);
		if(StringUtil1.isNull(requirement)) {
			map.put("status", "职位详情不能存在");
			return JSONObject.fromObject(map).toString();
			//return JsonUtil.getJson(2, "requirement can not be null").toString();
		}
		position.setRequirement(requirement);
		position.setWage(wage);
		position.setCity(city);
		if(weight <= 0) {
			map.put("status", "权重必须大于0");
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
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}

	/*
	 * 返回类型：0成功
	 * */
	@RequestMapping(value = "/position/search", method = RequestMethod.GET,  produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchCooperateCompanyPositions(@RequestParam(value = "pageNumber") int pageNumber,
			@RequestParam(value = "pageSize") int pageSize,
			@RequestParam(value = "title", required=false) String title,
			@RequestParam(value = "city", required=false) String city,
			@RequestParam(value = "isExpired", required=false) Integer isExpired,
			@RequestParam(value = "companyId", required=false) Integer companyId,
			@RequestParam(value = "companyName", required=false) String companyName) {
		Map<String,Object> map=new HashMap<String,Object>();
		
		PositionFilter filter = new PositionFilter();
		if(!StringUtil1.isNull(title)) {
			filter.setTitle(title);
		}
		
		if(!StringUtil1.isNull(city)) {
			filter.setCity(city);
		}
		
		if(!StringUtil1.isNull(companyName)) {
			filter.setCompanyName(companyName);
		}
		
		filter.setIsExpired(isExpired);
		filter.setCompanyId(companyId);
		
		PagingData pagingData = new PagingData(pageNumber, pageSize);
		filter.setPaged(true);
		filter.setPagingData(pagingData);
		
		SearchResult<Position> result = positionService.searchPositionsByFilter(filter);
		PagingResult pagingResult = result.getPagingResult();
		
		map.put("data", PositionVO.toVOs(result.getResult()));
		map.put("pagingResult", pagingResult);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	/*
	 * 返回类型：0成功，1positionId非法2weight非法
	 * */
	@RequestMapping(value = "/position/modify")
	@ResponseBody
	public String modifyCooperateCompanyPosition(@RequestParam(value = "positionId", defaultValue = "0") int positionId, 
			@RequestParam(value = "title", required=false) String title,
			@RequestParam(value = "requirement", required=false) String requirement,
			@RequestParam(value = "wage", required=false) String wage,
			@RequestParam(value = "isExpired", required=false) Integer isExpired,
			@RequestParam(value = "city", required=false) String city,
			@RequestParam(value = "weight", required=false) Integer weight) {
		Map<String,Object> map=new HashMap<String,Object>();
		if(positionId <=0) {
			map.put("status", "该职位不存在");
			return JSONObject.fromObject(map).toString();
		}
		if(weight!=null && weight <=0) {
			map.put("status", "权重必须大于0");
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
		newPosition.setWeight(weight);
		newPosition.setUpdateTime(new Date());
		positionService.updateCompanyPosition(newPosition);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 通过positionId获取职位详情
	 * 返回类型：0成功1positionId错误
	 * */
	@RequestMapping(value = "/position/info", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getPositionDetail(@RequestParam(value = "positionId", defaultValue = "0") Integer positionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(positionId <= 0) {
			map.put("status", "职位不存在");
			return JSONObject.fromObject(map).toString();
		}
		Position position =  positionService.getPositionById(positionId);
		map.put("data", PositionVO.toVO(position));
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value = "/position/delete")
	@ResponseBody
	public String deletePosition(@RequestParam(value = "positionId", defaultValue = "0") int positionId) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(positionId <= 0) {
			map.put("status", "该职位不存在");
			return JSONObject.fromObject(map).toString();
		}
		Position position = new Position();
		position.setId(positionId);
		position.setIsExpired(1);
		position.setUpdateTime(new Date());
		positionService.updateCompanyPosition(position);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
}
