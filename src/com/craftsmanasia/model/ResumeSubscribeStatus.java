package com.craftsmanasia.model;

import java.util.Date;

public class ResumeSubscribeStatus {

	private int id;
	private String status;
	private Date createTime;
	private Date updateTime;
	private Integer positionSubscribeId;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getPositionSubscribeId() {
		return positionSubscribeId;
	}

	public void setPositionSubscribeId(Integer positionSubscribeId) {
		this.positionSubscribeId = positionSubscribeId;
	}
	
}
