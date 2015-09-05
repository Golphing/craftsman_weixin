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

import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.ResumeSuscribeStatus;
import com.craftsmanasia.model.filter.SearchResult;
import com.craftsmanasia.model.vo.ResumeVO2;
import com.craftsmanasia.request.SearchResumeSubscribeRequest;
import com.craftsmanasia.service.PositionSubscribeUserService;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.OrderingProperty;
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
	
	
	@RequestMapping(value ="/search", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(SearchResumeSubscribeRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!request.validatePagingRequest()) {
			map.put("status", "pageNumber必须大于0,pageSize必须大于0且小于1000");
			return JSONObject.fromObject(map).toString();
		}
		ResumeSubscribeFilter filter = new ResumeSubscribeFilter();
		
		if(!StringUtil1.isNull(request.getName())) {
			filter.setName(request.getName());
		}
		
		if(!StringUtil1.isNull(request.getCompanyName())) {
			filter.setCompanyName(request.getCompanyName());
		}
		
		if(!StringUtil1.isNull(request.getTitle())) {
			filter.setTitle(request.getTitle());
		}
		
		if(!StringUtil1.isNull(request.getTelephone())) {
			filter.setTelephone(request.getTelephone());
		}
		
		PagingData pagingData = new PagingData(request.getPageNumber(), request.getPageNumber());
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
		
		SearchResult<PositionSubscribeUser> result = positionSubscribeUserService.searchResumeUsersByFilter(filter);

		map.put("data", ResumeVO2.toVOs(result.getResult()));
		map.put("pagingResult", result.getPagingResult());
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value ="/modify", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String modifyRusemeStatus(@RequestParam(value = "id") int id,
			@RequestParam(value = "statusId") Integer statusId,
			@RequestParam(value = "isPass") Integer isPass) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(isPass == null) {
			map.put("status", "参数传递错误");
			return JSONObject.fromObject(map).toString();
		}
		PositionSubscribeUser positionSubscribeUser = new PositionSubscribeUser();
		positionSubscribeUser.setId(id);
		Date now = new Date();
		if(isPass == 0) {
			positionSubscribeUser.setStatusId(statusId + 1);
			ResumeSuscribeStatus status = ResumeSuscribeStatus.fromid(statusId + 1);
			switch(status.getId()) {
				case 1: positionSubscribeUser.setCreateTime(now);
						break;
				case 2: positionSubscribeUser.setRecommendTime(now);;
						break;
				case 3: positionSubscribeUser.setScreenResumeTime(now);
						break;
				case 4: positionSubscribeUser.setFirstInterviewTime(now);
						break;
				case 5: positionSubscribeUser.setSecondInterviewTime(now);
						break;
				case 6: positionSubscribeUser.setThirdInterviewTime(now);
						break;
				case 7: positionSubscribeUser.setWaitingOfferTime(now);
						break;
				default : map.put("status", "操作错误");
						return JSONObject.fromObject(map).toString();
			}
		} else {
			// 不通过直接将id设置为最后一个
			positionSubscribeUser.setStatusId(8);
			positionSubscribeUser.setRejectTime(now);
		}
		
		positionSubscribeUser.setUpdateTime(now);
		positionSubscribeUserService.updateResumeSubscribe(positionSubscribeUser);
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
}
