package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;

public class UserVO {

	private int id;
	private String name;
	private String telephone;
	private String gender;
	private String wechatAccount;
	private String nickName;
	
	public static List<UserVO> toVOs(List<User> users) {
		List<UserVO> vos = new ArrayList<UserVO>();
		if(users == null) {
			return vos;
		}
		for(User user : users) {
			vos.add(UserVO.toVO(user));
		}
		return vos;
	}
	
	public static UserVO toVO(User user) {
		if(user == null) {
			return null;
		}
		UserVO vo = new UserVO();
		
		vo.setId(user.getId());
		vo.setNickName(user.getNickName());
		vo.setWechatAccount(user.getWechatAccount());
		vo.setTelephone(user.getTelephone());
		
		ResumeUser resumeUser = user.getResumeUser();
		if(resumeUser!=null) {
			vo.setName(resumeUser.getName());
			vo.setGender(resumeUser.getGender());
		}
		return vo;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWechatAccount() {
		return wechatAccount;
	}

	public void setWechatAccount(String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	
}
