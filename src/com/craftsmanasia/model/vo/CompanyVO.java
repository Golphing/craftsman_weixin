package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Company;
import com.craftsmanasia.utils.DateTimeUtility;

public class CompanyVO {

	private int id;
	private String name;
	private String createTime;
	private String updateTime;
	private String url;
	private Integer weight;
	private Integer isExpired;

	public static CompanyVO toVO(Company company) {
		if(company == null) {
			return null;
		}
		CompanyVO vo = new CompanyVO();
		vo.setId(company.getId());
		vo.setIsExpired(company.getIsExpired());
		vo.setName(company.getName());
		vo.setUrl(company.getUrl());
		vo.setWeight(company.getWeight());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDD(company.getCreateTime()));
		vo.setUpdateTime(DateTimeUtility.formatYYYYMMDD(company.getUpdateTime()));
		return vo;
	}
	
	public static List<CompanyVO> toVOs(List<Company> companies) {
		List<CompanyVO> vos = new ArrayList<CompanyVO>();
		if(companies == null) {
			return vos;
		}
		for(Company company : companies) {
			vos.add(CompanyVO.toVO(company));
		}
		return vos;
		
	}
	
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

	public Integer getIsExpired() {
		return isExpired;
	}

	public void setIsExpired(Integer isExpired) {
		this.isExpired = isExpired;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

}
