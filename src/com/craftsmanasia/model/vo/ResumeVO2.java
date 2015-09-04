package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.filter.ResumeSuscribeStatus;
import com.craftsmanasia.utils.DateTimeUtility;

public class ResumeVO2 {

	private int id;
	private String name;
	private String telephone;
	private String gender;
	private String status;
	private String companyName;
	private String wechatAccount;
	private String updateTime;
	
	public static List<ResumeVO2> toVOs(List<PositionSubscribeUser> positionSubscribeUsers) {
		List<ResumeVO2> vos = new ArrayList<ResumeVO2>();
		if(positionSubscribeUsers == null) {
			return vos;
		}
		for(PositionSubscribeUser positionSubscribeUser : positionSubscribeUsers) {
			vos.add(ResumeVO2.toVO(positionSubscribeUser));
		}
		return vos;
	}
	
	public static ResumeVO2 toVO(PositionSubscribeUser positionSubscribeUser) {
		if(positionSubscribeUser == null) {
			return null;
		}
		ResumeVO2 vo = new ResumeVO2();
		vo.setId(positionSubscribeUser.getId());
		
		ResumeUser resumeUser = positionSubscribeUser.getResumeUser();
		vo.setName(resumeUser.getName());
		vo.setGender(resumeUser.getGender());
		vo.setTelephone(resumeUser.getTelephone());
		
		vo.setStatus(ResumeSuscribeStatus.fromid(positionSubscribeUser.getId()).getDescription());
		
		vo.setUpdateTime(DateTimeUtility.formatYYYYMMDD(positionSubscribeUser.getUpdateTime()));
		vo.setCompanyName(positionSubscribeUser.getPosition().getCompany().getName());
		vo.setWechatAccount(positionSubscribeUser.getUser().getWechatAccount());
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getWechatAccount() {
		return wechatAccount;
	}

	public void setWechatAccount(String wechatAccount) {
		this.wechatAccount = wechatAccount;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
