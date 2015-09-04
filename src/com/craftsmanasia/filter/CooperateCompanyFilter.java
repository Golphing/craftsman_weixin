package com.craftsmanasia.filter;

import com.craftsmanasia.model.filter.SearchFilter;
import com.craftsmanasia.utils.StringUtil1;

public class CooperateCompanyFilter extends SearchFilter{

	private String name;
	private Integer isExpired;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		if(!StringUtil1.isNull(name)) {
			this.name = "%" + name + "%";
		}
		
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}
	
}
