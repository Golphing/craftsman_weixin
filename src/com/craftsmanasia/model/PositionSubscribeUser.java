package com.craftsmanasia.model;

import java.util.Date;

import com.craftsmanasia.model.filter.ResumeSuscribeStatus;

public class PositionSubscribeUser {

	private int id;
	private int positionId;
	private int userId;
	private int statusId;
	private Date createTime;
	private Date recommendTime;
	private Date screenResumeTime;
	private Date firstInterviewTime;
	private Date secondInterviewTime;
	private Date thirdInterviewTime;
	private Date rejectTime;
	
	private Position position;
	private User user;
	private ResumeUser ResumeUser;

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

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getScreenResumeTime() {
		return screenResumeTime;
	}

	public void setScreenResumeTime(Date screenResumeTime) {
		this.screenResumeTime = screenResumeTime;
	}

	public Date getFirstInterviewTime() {
		return firstInterviewTime;
	}

	public void setFirstInterviewTime(Date firstInterviewTime) {
		this.firstInterviewTime = firstInterviewTime;
	}

	public Date getSecondInterviewTime() {
		return secondInterviewTime;
	}

	public void setSecondInterviewTime(Date secondInterviewTime) {
		this.secondInterviewTime = secondInterviewTime;
	}

	public Date getThirdInterviewTime() {
		return thirdInterviewTime;
	}

	public void setThirdInterviewTime(Date thirdInterviewTime) {
		this.thirdInterviewTime = thirdInterviewTime;
	}

	public Date getRejectTime() {
		return rejectTime;
	}

	public void setRejectTime(Date rejectTime) {
		this.rejectTime = rejectTime;
	}

	public Date getRecommendTime() {
		return recommendTime;
	}

	public void setRecommendTime(Date recommendTime) {
		this.recommendTime = recommendTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ResumeUser getResumeUser() {
		return ResumeUser;
	}

	public void setResumeUser(ResumeUser resumeUser) {
		ResumeUser = resumeUser;
	}
}
