package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.CompanyDao;
import com.craftsmanasia.filter.CooperateCompanyFilter;
import com.craftsmanasia.model.Company;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;

@Component
public class CompanyService {

	@Autowired
	CompanyDao companyDao;
	/*
	 * 添加合作企业
	 * 
	 * */
	public void add(Company company) {
		companyDao.add(company);
	}
	
	/*
	 * 更新合作企业信息
	 * */
	public void update(Company company) {
		companyDao.updateCompany(company);
	}
	
	/*
	 * 通过Id找到公司
	 * */
	public Company getCompanyById(int id) {
		return companyDao.getCompanyById(id);
	}
	
	public Company getCompanyByName(String name) {
		return companyDao.getCompanyByName(name);
	}
	
	/*
	 * 找到所有仍在合作的公司
	 * */
	
	public List<Company> getAllNoExpiredCooperateCompanies() {
		return companyDao.selectAllNoExpiredCooperateCompanys();
	}
	
	public SearchResult<Company> searchCooperateCompany(CooperateCompanyFilter filter) {
		
		List<Company> companies = companyDao.selectCooperateCompanyByFilter(filter);
		SearchResult<Company> result = new SearchResult<Company>();
		
		result.setResult(companies);
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData !=null) {
			int recordNumber = companyDao.countCooperateCompanyByFilter(filter);
			PagingResult pagingResult = new PagingResult();
			
			pagingResult.setRecordNumber(recordNumber);
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageSize());
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
		}
		return result;
	}
}
