package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.CompanyDao;
import com.craftsmanasia.model.Company;

@Component
public class CompanyService {

	@Autowired
	CompanyDao companyDdao;
	/*
	 * 添加合作企业
	 * 
	 * */
	public int add(Company company) {
		return companyDdao.add(company);
	}
	
	/*
	 * 更新合作企业信息
	 * */
	public void update(Company company) {
		companyDdao.updateCompany(company);
	}
	
	/*
	 * 通过Id找到公司
	 * */
	public Company getCompanyById(int id) {
		return companyDdao.getCompanyById(id);
	}
	
	/*
	 * 找到所有仍在合作的公司
	 * */
	
	public List<Company> getAllExpiredCompanies() {
		return companyDdao.getAllExpiredCompanys();
	}
}
