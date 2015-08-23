package com.craftsmanasia.dao;

import java.util.List;

import com.craftsmanasia.model.Company;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface CompanyDao {

	public int add(Company company);
	
	public void updateCompany(Company company);
	
	public Company getCompanyById(int id);
	
	public List<Company> getAllExpiredCompanys();
}
