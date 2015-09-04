package com.craftsmanasia.filter;

import com.craftsmanasia.model.filter.SearchFilter;
import com.craftsmanasia.utils.StringUtil1;

public class PositionFilter extends SearchFilter{

	private String title;
	private Integer isExpired;
	private String city;
	private String companyName;
	
	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if(!StringUtil1.isNull(title)) {
			this.title = "%" + title + "%";
		}
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		if(!StringUtil1.isNull(companyName)) {
			this.companyName = "%" + companyName + "%";
		}
	}
}
