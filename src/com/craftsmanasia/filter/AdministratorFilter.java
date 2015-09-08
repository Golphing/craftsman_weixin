package com.craftsmanasia.filter;

import com.craftsmanasia.model.filter.SearchFilter;

public class AdministratorFilter extends SearchFilter{

	private String loginName;
	private String name;
	private String telephone;

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

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
}
