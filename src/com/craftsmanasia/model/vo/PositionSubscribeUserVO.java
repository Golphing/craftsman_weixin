package com.craftsmanasia.model.vo;

import com.craftsmanasia.model.PositionSubscribeUser;
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
	
}
