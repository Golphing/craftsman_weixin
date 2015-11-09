package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.utils.DateTimeUtility;

public class PositionSubscribeUserVO {

	private int id;
	private int positionId;
	private int userId;
	private int statusId;
	private String createTime;
	private String recommendTime;
	private String screenResumeTime;
	private String firstInterviewTime;
	private String secondInterviewTime;
	private String thirdInterviewTime;
	private String rejectTime;

	private PositionVO position;

	private List<String> allStatus;
	private List<String> statusTime;
	private String lastStatus;
	private String lastStatusTime;
	private String positionTitle;
	private String companyName;
	private int companyId;

	public static PositionSubscribeUserVO toVO(PositionSubscribeUser positionSubscribeUser) {
		if(positionSubscribeUser == null) {
			return null;
		}
		PositionSubscribeUserVO vo = new PositionSubscribeUserVO();
		vo.setId(positionSubscribeUser.getId());
		vo.setPositionId(positionSubscribeUser.getPositionId());
		vo.setUserId(positionSubscribeUser.getUserId());
		vo.setStatusId(positionSubscribeUser.getStatusId());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getCreateTime()));
		vo.setRecommendTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getRecommendTime()));
		vo.setScreenResumeTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getScreenResumeTime()));
		vo.setFirstInterviewTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getFirstInterviewTime()));
		vo.setSecondInterviewTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getSecondInterviewTime()));
		vo.setThirdInterviewTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getThirdInterviewTime()));
		vo.setRejectTime(DateTimeUtility.formatYYYYMMDDHHMMSS(positionSubscribeUser.getRejectTime()));
		vo.setPosition(PositionVO.toVO(positionSubscribeUser.getPosition()));
		
		Position position = positionSubscribeUser.getPosition();
		vo.setPositionTitle(position.getTitle());
		vo.setCompanyName(position.getCompany().getName());
		vo.setCompanyId(position.getCompanyId());
		
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

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public PositionVO getPosition() {
		return position;
	}

	public void setPosition(PositionVO position) {
		this.position = position;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(String recommendTime) {
		this.recommendTime = recommendTime;
	}

	public String getScreenResumeTime() {
		return screenResumeTime;
	}

	public void setScreenResumeTime(String screenResumeTime) {
		this.screenResumeTime = screenResumeTime;
	}

	public String getFirstInterviewTime() {
		return firstInterviewTime;
	}

	public void setFirstInterviewTime(String firstInterviewTime) {
		this.firstInterviewTime = firstInterviewTime;
	}

	public String getSecondInterviewTime() {
		return secondInterviewTime;
	}

	public void setSecondInterviewTime(String secondInterviewTime) {
		this.secondInterviewTime = secondInterviewTime;
	}

	public String getThirdInterviewTime() {
		return thirdInterviewTime;
	}

	public void setThirdInterviewTime(String thirdInterviewTime) {
		this.thirdInterviewTime = thirdInterviewTime;
	}

	public String getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(String rejectTime) {
		this.rejectTime = rejectTime;
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

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
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
