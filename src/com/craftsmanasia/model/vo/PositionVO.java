package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Position;
import com.craftsmanasia.utils.DateTimeUtility;

public class PositionVO {

	private int id;
	private String title;
	private String createTime;
	private String updateTime;
	private String wage;
	private String requirement;
	private String city;
	private Integer weight;
	private int companyId;
	private Integer isExpired;
	private CompanyVO company;
	
	public static PositionVO toVO(Position position) {
		if(position == null) {
			return null;
		}
		PositionVO vo = new PositionVO();
		vo.setId(position.getId());
		vo.setTitle(position.getTitle());
		vo.setCity(position.getCity());
		vo.setWage(position.getWage());
		vo.setRequirement(position.getRequirement());
		vo.setWage(position.getWage());
		vo.setWeight(position.getWeight());
		vo.setCompanyId(position.getCompanyId());
		vo.setIsExpired(position.getIsExpired());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDD(position.getCreateTime()));
		vo.setUpdateTime(DateTimeUtility.formatYYYYMMDD(position.getUpdateTime()));
		vo.setCompany(CompanyVO.toVO(position.getCompany()));
		return vo;
	}
	
	public static List<PositionVO> toVOs(List<Position> positions) {
		List<PositionVO> vos = new ArrayList<PositionVO>();
		if(positions == null) {
			return vos;
		}
		for(Position position : positions) {
			vos.add(PositionVO.toVO(position));
		}
		return vos;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWage() {
		return wage;
	}

	public void setWage(String wage) {
		this.wage = wage;
	}

	public String getRequirement() {
		return requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCity() {
		return city;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public int getWeight() {
		return weight;
	}

	public void setCompany(CompanyVO company) {
		this.company = company;
	}

	public CompanyVO getCompany() {
		return company;
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
