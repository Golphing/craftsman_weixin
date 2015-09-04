package com.craftsmanasia.filter;

import com.craftsmanasia.model.filter.SearchFilter;
import com.craftsmanasia.utils.StringUtil1;

public class ResumeSubscribeFilter extends SearchFilter{

	private String title;
	private Integer statusId;
	private String name;
	private String companyName;
	private String telephone;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(!StringUtil1.isNull(title)) {
			this.title = "%" + title + "%";
		}
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		if(!StringUtil1.isNull(companyName)) {
			this.companyName = "%" + companyName + "%";
		}
	}

	public Integer getStatusId() {
		return statusId;
	}

	public void setStatusId(Integer statusId) {
		this.statusId = statusId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	
}
