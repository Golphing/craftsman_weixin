package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.utils.DateTimeUtility;

public class PositionSubscribeUserVO {

	private Integer id;
	private Integer positionId;
	private Integer userId;
	private String createTime;

	private List<String> allStatus;
	private List<String> statusTime;
	private List<String> replies;
	private String positionTitle;
	private String companyName;
	private Integer companyId;
	private String positionCity;
	private String positionWage;

	public static List<PositionSubscribeUserVO> toVOs(List<PositionSubscribeUser> positionSubscribeUsers) {
		List<PositionSubscribeUserVO> vos = new ArrayList<PositionSubscribeUserVO>();
		if(positionSubscribeUsers == null) {
			return vos;
		}
		for(PositionSubscribeUser positionSubscribeUser : positionSubscribeUsers) {
			vos.add(PositionSubscribeUserVO.toVO(positionSubscribeUser));
		}
		return vos;
	}
	
	public static PositionSubscribeUserVO toVO(PositionSubscribeUser positionSubscribeUser) {
		PositionSubscribeUserVO vo = new PositionSubscribeUserVO();
		if(positionSubscribeUser == null) {
			return vo;
		}
		
		vo.setId(positionSubscribeUser.getId());
		vo.setPositionId(positionSubscribeUser.getPositionId());
		vo.setUserId(positionSubscribeUser.getUserId());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getCreateTime()));
		
		Position position = positionSubscribeUser.getPosition();
		vo.setPositionTitle(position.getTitle());
		vo.setCompanyName(position.getCompany().getName());
		vo.setCompanyId(position.getCompanyId());
		vo.setPositionCity(position.getCity());
		vo.setPositionWage(position.getWage());
		
		List<ResumeSubscribeStatus> statuses = positionSubscribeUser.getStatuses();
		List<String> allStatus = new ArrayList<String>();
		List<String> replies = new ArrayList<String>();
		List<String> statusTime = new ArrayList<String>();
		if(statuses != null) {
			for(ResumeSubscribeStatus status : statuses) {
				allStatus.add(status.getStatus());
				if(status.getReply() == null) {
					replies.add("");
				} else {
					replies.add(status.getReply());
				}
				statusTime.add(DateTimeUtility.formatYYYYMMDDHHMMSS(status.getCreateTime()));
			}
		}
		vo.setAllStatus(allStatus);
		vo.setStatusTime(statusTime);
		vo.setReplies(replies);
		
		return vo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getPositionId() {
		return positionId;
	}

	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
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

	public String getPositionTitle() {
		return positionTitle;
	}

	public void setPositionTitle(String positionTitle) {
		this.positionTitle = positionTitle;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public List<String> getReplies() {
		return replies;
	}

	public void setReplies(List<String> replies) {
		this.replies = replies;
	}

	public String getPositionCity() {
		return positionCity;
	}

	public void setPositionCity(String positionCity) {
		this.positionCity = positionCity;
	}

	public String getPositionWage() {
		return positionWage;
	}

	public void setPositionWage(String positionWage) {
		this.positionWage = positionWage;
	}
}
