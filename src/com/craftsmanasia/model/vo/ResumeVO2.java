package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.filter.ResumeSuscribeStatus;
import com.craftsmanasia.utils.DateTimeUtility;

public class ResumeVO2 {

	private int id;
	private String name;
	private String telephone;
	private String gender;
	private String status;
	private Integer statusId;
	private String companyName;
	private String positionName;
	private String wechatAccount;
	private String updateTime;
	
	private List<String> allStatus;
	private List<String> statusTime;
	private String lastStatus;
	private String lastStatusTime;
	
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
		
		vo.setStatus(ResumeSuscribeStatus.fromid(positionSubscribeUser.getStatusId()).getDescription());
		vo.setStatusId(positionSubscribeUser.getStatusId());
		vo.setUpdateTime(DateTimeUtility.formatYYYYMMDD(positionSubscribeUser.getUpdateTime()));
		vo.setCompanyName(positionSubscribeUser.getPosition().getCompany().getName());
		vo.setWechatAccount(positionSubscribeUser.getUser().getWechatAccount());
		
		vo.setPositionName(positionSubscribeUser.getPosition().getTitle());
		
		List<ResumeSubscribeStatus> statuses = positionSubscribeUser.getStatuses();
		List<String> allStatus = new ArrayList<String>();
		List<String> statusTime = new ArrayList<String>();
		if(statuses != null) {
			for(ResumeSubscribeStatus status : statuses) {
				allStatus.add(status.getStatus());
				statusTime.add(DateTimeUtility.formatYYYYMMDDHHMMSS(status.getCreateTime()));
			}
			if(statuses.size() >0 ) {
				vo.setLastStatus(statuses.get(statuses.size()-1).getStatus());
				vo.setLastStatusTime(DateTimeUtility.formatYYYYMMDDHHMMSS(statuses.get(statuses.size()-1).getCreateTime()));
			}
		}
		vo.setAllStatus(allStatus);
		vo.setStatusTime(statusTime);
		
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

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getPositionName() {
		return positionName;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public List<String> getAllStatus() {
		return allStatus;
	}

	public void setAllStatus(List<String> allStatus) {
		this.allStatus = allStatus;
	}

	public List<String> getStatusTime() {
		return statusTime;
	}

	public void setStatusTime(List<String> statusTime) {
		this.statusTime = statusTime;
	}

	public String getLastStatus() {
		return lastStatus;
	}

	public void setLastStatus(String lastStatus) {
		this.lastStatus = lastStatus;
	}

	public String getLastStatusTime() {
		return lastStatusTime;
	}

	public void setLastStatusTime(String lastStatusTime) {
		this.lastStatusTime = lastStatusTime;
	}
	
}
