package com.craftsmanasia.model;

import java.util.Date;
import java.util.List;

public class PositionSubscribeUser {

	private int id;
	private int positionId;
	private int userId;
	private Date createTime;
	private Date updateTime;
	
	private Position position;
	private User user;
	private ResumeUser resumeUser;
	
	private List<ResumeSubscribeStatus> statuses;

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

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public ResumeUser getResumeUser() {
		return resumeUser;
	}

	public void setResumeUser(ResumeUser resumeUser) {
		this.resumeUser = resumeUser;
	}

	public List<ResumeSubscribeStatus> getStatuses() {
		return statuses;
	}

	public void setStatuses(List<ResumeSubscribeStatus> statuses) {
		this.statuses = statuses;
	}
}
