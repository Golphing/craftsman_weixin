package com.craftsmanasia.request;

public class SearchCompanyRequest extends PagingRequest{

	private String name;
	private Integer isExpired;
	private String companyTypeCode;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public String getCompanyTypeCode() {
		return companyTypeCode;
	}

	public void setCompanyTypeCode(String companyTypeCode) {
		this.companyTypeCode = companyTypeCode;
	}
	
}
