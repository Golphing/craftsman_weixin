package com.craftsmanasia.model;

import java.util.Date;

public class Company {

	private int id;
	private String name;
	private Date createTime;
	private Date updateTime;
	private String url;
	private Integer weight;
	private Integer isExpired;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Integer isExpired() {
		return isExpired;
	}

	public void setExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getWeight() {
		return weight;
	}

}
