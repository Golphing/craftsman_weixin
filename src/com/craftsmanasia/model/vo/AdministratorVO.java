package com.craftsmanasia.model.vo;

import java.util.ArrayList;
import java.util.List;

import com.craftsmanasia.model.Administrator;
import com.craftsmanasia.utils.DateTimeUtility;

public class AdministratorVO {

	private Integer id;
	private String loginName;
	private String name;
	private String telephone;
	private String email;
	private String createTime;
	private String updateTime;

	public static List<AdministratorVO> toVOs(List<Administrator> administrators) {
		List<AdministratorVO> vos = new ArrayList<AdministratorVO>();
		if(administrators == null) {
			return vos;
		}
		for(Administrator administrator : administrators) {
			vos.add(AdministratorVO.toVO(administrator));
		}
		return vos;
	}
	
	public static AdministratorVO toVO(Administrator administrator) {
		if(administrator == null) {
			return null;
		}
		AdministratorVO vo = new AdministratorVO();
		vo.setId(administrator.getId());
		vo.setLoginName(administrator.getLoginName());
		vo.setName(administrator.getName());
		vo.setTelephone(administrator.getTelephone());
		vo.setEmail(administrator.getEmail());
		vo.setCreateTime(DateTimeUtility.formatYYYYMMDD(administrator.getCreateTime()));
		vo.setUpdateTime(DateTimeUtility.formatYYYYMMDD(administrator.getUpdateTime()));
		return vo;
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
