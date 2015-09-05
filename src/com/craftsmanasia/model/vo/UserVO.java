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
	
	public static List<UserVO> toVOs(List<ResumeUser> resumeUsers) {
		List<UserVO> vos = new ArrayList<UserVO>();
		if(resumeUsers == null) {
			return vos;
		}
		for(ResumeUser resumeUser : resumeUsers) {
			vos.add(UserVO.toVO(resumeUser));
		}
		return vos;
	}
	
	public static UserVO toVO(ResumeUser resumeUser) {
		if(resumeUser == null) {
			return null;
		}
		UserVO vo = new UserVO();
		User user = resumeUser.getUser();
		vo.setId(user.getId());
		vo.setNickName(user.getNickName());
		vo.setWechatAccount(user.getWechatAccount());
		
		vo.setName(resumeUser.getName());
		vo.setGender(resumeUser.getGender());
		vo.setTelephone(resumeUser.getTelephone());
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
