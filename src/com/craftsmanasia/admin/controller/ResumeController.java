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
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.model.filter.PagingData;
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
	
	
	@RequestMapping(value ="/search", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String searchUser(SearchResumeSubscribeRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(!request.validatePagingRequest()) {
			map.put("msg", "pageNumber必须大于0,pageSize必须大于0且小于1000");
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
		
		if(!StringUtil1.isNull(request.getWechatAccount())) {
			filter.setWechatAccount(request.getWechatAccount());
		}
		
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
		
		SearchResult<PositionSubscribeUser> result = positionSubscribeUserService.searchResumeUsersByFilter(filter);

		map.put("data", ResumeVO2.toVOs(result.getResult()));
		map.put("pagingResult", result.getPagingResult());
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	@RequestMapping(value ="/modify", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String modifyRusemeStatus(@RequestParam(value = "id") int id,
			@RequestParam(value = "reply") String reply,
			@RequestParam(value = "status") String status) {
		Map<String, Object> map = new HashMap<String, Object>();
		ResumeSubscribeStatus resumeSubscribeStatus = new ResumeSubscribeStatus();
		resumeSubscribeStatus.setPositionSubscribeId(id);
		resumeSubscribeStatus.setStatus(status);
		resumeSubscribeStatus.setReply(reply);
		
		Date now = new Date();
		resumeSubscribeStatus.setCreateTime(now);
		resumeSubscribeStatus.setUpdateTime(now);
		try {
			positionSubscribeUserService.addResumeSubscribeStatus(resumeSubscribeStatus);
		} catch (Exception e) {
			map.put("msg", "submit error");
			e.printStackTrace();
			return JSONObject.fromObject(map).toString();
		}
		
		map.put("status", true);
		return JSONObject.fromObject(map).toString();
	}
	
	
}
