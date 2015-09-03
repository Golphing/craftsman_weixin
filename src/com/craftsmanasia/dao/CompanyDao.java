package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.model.Company;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface CompanyDao {

	public void add(Company company);
	
	public void updateCompany(Company company);
	
	public Company getCompanyById(@Param("id") int id);
	
	public Company getCompanyByName(@Param("name")String name);
	
	public List<Company> getAllExpiredCompanys();
}
