package com.craftsmanasia.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.Company;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionCollection;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.vo.CompanyVO;
import com.craftsmanasia.model.vo.PositionSubscribeUserVO;
import com.craftsmanasia.model.vo.PositionVO;
import com.craftsmanasia.service.CompanyService;
import com.craftsmanasia.service.PositionService;
import com.craftsmanasia.service.PositionSubscribeUserService;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wechat/position")
public class PositionSubscribeController {

	@Autowired
	UserService userService;
	
	@Autowired
	ResumeUserService resumeUserService;
	
	@Autowired
	WorkService workService;
	
	@Autowired
	PositionSubscribeUserService positionSubscribeUserService;
	
	@Autowired
	PositionService positionService;
	
	@Autowired
	CompanyService companyService;
	/*
	 * 返回类型：0成功1PositionId非法2已订阅
	 * */
	@RequestMapping(value ="/subscribe", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String subscribePosition(@RequestParam(value = "userId", defaultValue = "") Integer userId, 
			@RequestParam(value = "positionId", defaultValue = "") Integer positionId) {
		Map<String,Object> map = new HashMap<String,Object>();
		if(StringUtil1.isNull(userId) || StringUtil1.isNull(positionId)) {
			map.put("status", "submit error");
			return JSONObject.fromObject(map).toString();
		}
		
		User user = new User();
		user.setId(userId);
		// 投递前应先判断是否填写简历
		ResumeUser resumeUser = resumeUserService.selectResumeUserByUserId(userId);
		if(resumeUser == null) {
			map.put("status", "您还未填写简历,请填写简历");
			return JSONObject.fromObject(map).toString();
		}
		
		PositionSubscribeUser oldPositionSubscribeUser = positionSubscribeUserService
					.getSubscribedPositionByUserIdAndPositionId(userId, positionId);
		if(oldPositionSubscribeUser != null) {
			map.put("status", "已经应聘过该职位");
			return JSONObject.fromObject(map).toString();
		}
		
		PositionSubscribeUser positionSubscribeUser = new PositionSubscribeUser();
		positionSubscribeUser.setPositionId(positionId);
		positionSubscribeUser.setUserId(user.getId());

		Date now = new Date();
		positionSubscribeUser.setCreateTime(now);
		positionSubscribeUser.setUpdateTime(now);
		try {
			positionSubscribeUserService.subscribePosition(positionSubscribeUser);
		} catch (Exception e1) {
			e1.printStackTrace();
			map.put("status", "submit error");
			return JSONObject.fromObject(map).toString();
		}
		
		ResumeSubscribeStatus status = new ResumeSubscribeStatus();
		status.setPositionSubscribeId(positionSubscribeUser.getId());
		// 设置默认状态
		status.setStatus("新投递");
		status.setReply("未筛选");
		status.setCreateTime(now);
		status.setUpdateTime(now);
		try {
			positionSubscribeUserService.addResumeSubscribeStatus(status);
		} catch (Exception e) {
			e.printStackTrace();
			map.put("status", "submit error");
			return JSONObject.fromObject(map).toString();
		}
		map.put("status", true);
		
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 找到该用户所有投递职位
	 * 返回类型：0成功1user非法
	 * */
	@RequestMapping(value = "/search/subscribed/info", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchSubscribedPositions(@RequestParam(value = "userId", defaultValue = "0") Integer userId
			) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(userId <= 0) {
			map.put("status", "该用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		List<PositionSubscribeUser> positions = positionSubscribeUserService.getSubscribedPositionsByUserId(userId);
		
		map.put("data", PositionSubscribeUserVO.toVOs(positions));
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 找到投递职位的进度详情
	 * 返回类型：0成功1user非法
	 * */
	@RequestMapping(value = "/search/subscribed/deatil/info", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String getSubscribedDetails(@RequestParam(value = "userId", defaultValue = "0") Integer userId,
			@RequestParam(value = "positionId", defaultValue = "0") Integer positionId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(userId <= 0) {
			map.put("status", "用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		PositionSubscribeUser positionSubscribeUser = positionSubscribeUserService
					.getSubscribedPositionByUserIdAndPositionId(userId, positionId);
		
		map.put("data", PositionSubscribeUserVO.toVO(positionSubscribeUser));
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 通过positionId获取职位详情
	 * 返回类型：0成功1positionId错误
	 * */
	@RequestMapping(value = "/info", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
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
	
	
	/*收藏职位
	 * 返回类型：0成功1用户不存在2职位不存在3已收藏该职位
	 * */
	@RequestMapping(value = "/collect",method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String collectPosition(@RequestParam(value = "positionId", defaultValue = "0") int positionId,
			@RequestParam(value = "userId", defaultValue = "0") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(userId <=0) {
			map.put("status", "用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		if(positionId <=0) {
			map.put("status", "职位不存在");
			return JSONObject.fromObject(map).toString();
		}
		
		List<Position> positions = positionSubscribeUserService.getAllCollectionPositionsByUserId(userId);
		for(Position position : positions) {
			if(position.getId() == positionId) {
				map.put("status", "已收藏该职位");
				return JSONObject.fromObject(map).toString();
			}
		}
		PositionCollection positionCollection = new PositionCollection();
		
		positionCollection.setPositionId(positionId);
		positionCollection.setUserId(userId);
		
		positionSubscribeUserService.collectPosition(positionCollection);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*用户收藏的所有职位
	 * 返回类型：0成功1用户不存在
	 * */
	@RequestMapping(value = "/serach/collection/positions", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchCollectionPositions(@RequestParam(value = "userId", defaultValue = "0") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(userId <= 0) {
			map.put("status", "用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		List<Position> positions = positionSubscribeUserService.getAllCollectionPositionsByUserId(userId);
		map.put("data", PositionVO.toVOs(positions));
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*取消收藏这个职位
	 * 返回类型：0成功1用户不存在2职位不存在
	 * */
	@RequestMapping(value = "/cancle/collection/positions", method = RequestMethod.POST)
	@ResponseBody
	public String cancleCollectionPositios(@RequestParam(value = "positionId", defaultValue = "0") int positionId,
			@RequestParam(value = "userId", defaultValue = "0") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(userId <= 0) {
			map.put("status", "用户不存在");
			return JSONObject.fromObject(map).toString();
		}
		positionSubscribeUserService.cancleCollectionPositionByUserIdAndPosition(userId, positionId);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 微信找到本公司的所有职位信息
	*/
	@RequestMapping(value = "/search/own", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchOwnPositions() {
		Map<String,Object> map=new HashMap<String,Object>();
		
		List<Position> positions = positionService.getOwnCompanyPositions();
		System.out.println(positions);
		List<PositionVO> positionvos = PositionVO.toVOs(positions);
		map.put("data", positionvos);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
		
	}
	
	/*
	 * 微信找到所有合作企业
	 * */
	@RequestMapping(value = "/search/company", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchCooperateCompany() {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Company> companies = companyService.getAllNoExpiredCooperateCompanies();
		map.put("data", CompanyVO.toVOs(companies));
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*
	 * 找到合作企业下的所有职位
	 * */
	@RequestMapping(value = "/search/company/positions", method = RequestMethod.GET,produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchCooperateCompanyPositions(@RequestParam(value = "companyId", defaultValue = "0") int companyId) {
		Map<String, Object> map = new HashMap<String, Object>();
		List<Position> positions = positionService.getCompanyPositionsByCompanyId(companyId);
		map.put("data", PositionVO.toVOs(positions));
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	/*通过title查找职位
	 * 
	 * */
	@RequestMapping(value = "/search/company/position/byTitle", method = RequestMethod.GET, produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String searchPositionsByTitle(@RequestParam(value = "title", defaultValue = "") String title)
			 {
		try {
			title = java.net.URLDecoder.decode(title, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Map<String, Object> map = new HashMap<String, Object>();
		List<Position> position = positionService.getPositionByTitle(title);
		map.put("data", position);
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
}
