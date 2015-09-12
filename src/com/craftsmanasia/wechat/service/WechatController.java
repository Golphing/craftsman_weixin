package com.craftsmanasia.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.craftsmanasia.filter.UserFilter;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.SearchResult;
import com.craftsmanasia.model.vo.ResumeVO;
import com.craftsmanasia.model.vo.ResumeVO2;
import com.craftsmanasia.model.vo.UserVO;
import com.craftsmanasia.request.SearchUserRequest;
import com.craftsmanasia.service.PositionService;
import com.craftsmanasia.service.PositionSubscribeUserService;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.craftsmanasia.utils.OrderingProperty;
import com.craftsmanasia.utils.StringUtil1;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/wechat/user")
public class WechatController {

	@Autowired
	UserService userService;

	@Autowired
	ResumeUserService resumeUserService;
	@Autowired
	PositionService positionService;

	@Autowired
	WorkService workService;

	@Autowired
	PositionSubscribeUserService positionSubscribeUserService;

	/*
	 * 查询用户岗位进度
	 */
	@RequestMapping(value = "/jobprogress", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String registerUser(
			@RequestParam(value = "userId", defaultValue = "") int userId) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 用户投递的所有职位进度-list中放的是职位进度
		List<PositionSubscribeUser> positionSubscribeUserList = positionSubscribeUserService
				.getSubscribedPositionsByUserId(userId);
		//
		List<Position> listPosition = new ArrayList<Position>();
		List<Integer> list = new ArrayList<Integer>();
		for (int j = 0; j < positionSubscribeUserList.size(); j++) {
			// 得到用户订阅职位的详细信息
			Position position = positionService
					.getPositionById(positionSubscribeUserList.get(j)
							.getPositionId());
			listPosition.add(position);

		}

		map.put("position", listPosition);
		return JSONObject.fromObject(map).toString();
	}

	// 点击 我的简历 菜单
	@RequestMapping(value = "/myResume", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView myResume(HttpSession session) {

		User user = (User) session.getAttribute("user");
		if (user == null) {
			// 跳转到登陆页面
			return new ModelAndView(new RedirectView(
					"../../weixinPort/login.jsp"));

		} else {
			// 跳转到简历修改页面或者添加简历页面
			ResumeUser resume = resumeUserService.selectResumeUserByUserId(user
					.getId());

			if (resume == null) {
				// 添加简历
				return new ModelAndView(new RedirectView(
						"../../weixinPort/fillResume.jsp"));
			} else {
				// 修改简历
				return new ModelAndView(new RedirectView(
						"../../weixinPort/modifyResume.jsp"));
			}

		}

	}

	// 点击 我的简历 菜单
	@RequestMapping(value = "/login", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String login(
			HttpSession session,
			@RequestParam(value = "telephone", defaultValue = "0") String telephone,
			@RequestParam(value = "password", defaultValue = "0") String password) {
		User user = userService.getUserByTelephone(telephone);
		if (user == null) {
		
			return "手机号错误";
			
		} else {
			if (user.getPassword().equals(password)) {
				session.setAttribute("user", user);
				
				return "成功";
				
			} else {
				return "密码错误";
				
			}
		}

	}
}
