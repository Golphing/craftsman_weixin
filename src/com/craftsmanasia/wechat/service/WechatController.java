package com.craftsmanasia.wechat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.json.JSONException;
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
	public ModelAndView myResume(HttpServletRequest request)
			throws IOException, JSONException {
		GetWeiXinCode getWeiXinCode = new GetWeiXinCode();
		String openId = getWeiXinCode.sendGet(request.getParameter("code"));
		User user = userService.getByOpenId(openId);

		if (user == null) {
			return new ModelAndView(new RedirectView(
					"../../weixinPort/login.jsp?openId=" + openId));// 登陆过程要把新openId存到数据库中
		} else {
			int userId = user.getId();
			ResumeUser resume = resumeUserService
					.selectResumeUserByUserId(userId);
			List<Work> work = workService.getUserWorksByUserId(userId);
			if (resume == null) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/importResume.jsp?userId=" + userId));
			} else if (work.size() == 0) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/fillWork.jsp?userId=" + userId));
			} else {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/myResume.jsp?userId=" + userId));
			}

		}

	}

	// 注册
	@RequestMapping(value = "/toRegisterPage", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView toRegisterPage(
			HttpSession session,
			@RequestParam(value = "telephone", defaultValue = "") String telephone) {

		User user = userService.getUserByTelephone(telephone);
		session.setAttribute("userId", user.getId());
		return new ModelAndView(new RedirectView(
				"../../weixinPort/register.jsp"));

	}

	// 登陆
	@RequestMapping(value = "/userlogin", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public String login(
			@RequestParam(value = "openId", defaultValue = "0") String openId,
			@RequestParam(value = "telephone", defaultValue = "0") String telephone,
			@RequestParam(value = "password", defaultValue = "0") String password)
			throws IOException, JSONException {

		User user = userService.getUserByTelephone(telephone);
		if (user == null) {

			return "手机号错误";

		} else {
			if (user.getPassword().equals(password)) {
				if (user.getOpenId() != openId) {
					user.setOpenId(openId);
					userService.update(user);
				}
				ResumeUser resume = resumeUserService
						.selectResumeUserByUserId(user.getId());
				List<Work> work = workService.getUserWorksByUserId(user.getId());
				if(resume==null){
					return "1&"+user.getId();
				}else if(work.size()==0){
					return "2&"+user.getId();
				}else{
					return "3&"+user.getId();
				
				}
				

			} else {
				return "密码错误";

			}
		}

	}

	// 点击 我的投递 菜单
	@RequestMapping(value = "/delivery", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView delivery(HttpServletRequest request)
			throws IOException, JSONException {
		GetWeiXinCode getWeiXinCode = new GetWeiXinCode();
		String openId = getWeiXinCode.sendGet(request.getParameter("code"));
		User user = userService.getByOpenId(openId);

		if (user == null) {
			return new ModelAndView(new RedirectView(
					"../../weixinPort/login.jsp?openId=" + openId));// 登陆过程要把新openId存到数据库中
		} else {
			int userId = user.getId();
			ResumeUser resume = resumeUserService
					.selectResumeUserByUserId(userId);
			List<Work> work = workService.getUserWorksByUserId(userId);
			if (resume == null) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/importResume.jsp?userId=" + userId));
			} else if (work.size() == 0) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/fillWork.jsp?userId=" + userId));
			} else {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/jobProgress.jsp?userId=" + userId));
			}

		}

	}

	// 点击 我的收藏 菜单
	@RequestMapping(value = "/myCollection", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView myCollection(HttpServletRequest request)
			throws IOException, JSONException {
		GetWeiXinCode getWeiXinCode = new GetWeiXinCode();
		String openId = getWeiXinCode.sendGet(request.getParameter("code"));
		User user = userService.getByOpenId(openId);

		if (user == null) {
			return new ModelAndView(new RedirectView(
					"../../weixinPort/login.jsp?openId=" + openId));// 登陆过程要把新openId存到数据库中
		} else {
			int userId = user.getId();
			ResumeUser resume = resumeUserService
					.selectResumeUserByUserId(userId);
			List<Work> work = workService.getUserWorksByUserId(userId);
			if (resume == null) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/importResume.jsp?userId=" + userId));
			} else if (work.size() == 0) {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/fillWork.jsp?userId=" + userId));
			} else {
				return new ModelAndView(new RedirectView(
						"../../weixinPort/myCollection.jsp?userId=" + userId));
			}

		}

	}

	// 点击 岗位列表 菜单
	@RequestMapping(value = "/jobList", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
	public ModelAndView jobList(HttpServletRequest request) throws IOException,
			JSONException {
		GetWeiXinCode getWeiXinCode = new GetWeiXinCode();
		String openId = getWeiXinCode.sendGet(request.getParameter("code"));
		User user = userService.getByOpenId(openId);
		int userId;
		if (user != null) {
			userId = user.getId();

		} else {
			userId = 0;
		}
		return new ModelAndView(new RedirectView(
				"../../weixinPort/jobList.jsp?userId=" + userId));// 登陆过程要把新openId存到数据库中

	}
	
	// 点击 名企直招 菜单
		@RequestMapping(value = "/recruitStraight", method = RequestMethod.GET, produces = "text/plain;charset=UTF-8")
		public ModelAndView recruitStraight(HttpServletRequest request) throws IOException,
				JSONException {
			GetWeiXinCode getWeiXinCode = new GetWeiXinCode();
			String openId = getWeiXinCode.sendGet(request.getParameter("code"));
			User user = userService.getByOpenId(openId);
			int userId;
			if (user != null) {
				userId = user.getId();

			} else {
				userId = 0;
			}
			return new ModelAndView(new RedirectView(
					"../../weixinPort/recruitStraight.jsp?userId=" + userId));// 登陆过程要把新openId存到数据库中

		}
}
