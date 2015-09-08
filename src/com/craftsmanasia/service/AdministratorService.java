package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.AdministratorDao;
import com.craftsmanasia.filter.AdministratorFilter;
import com.craftsmanasia.model.Administrator;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;

@Component
public class AdministratorService {

	@Autowired
	AdministratorDao administratorDao;
	
	public void createAdministrator(Administrator administrator) {
		administratorDao.addAdministrator(administrator);
	}
	
	public void updateAdministrator(Administrator administrator) {
		administratorDao.updateAdministrator(administrator);
	}
	
	public Administrator getAdministratorByLoginName(String loginName) {
		return administratorDao.selectAdministratorByLoginName(loginName);
	}
	
	public SearchResult<Administrator> getAdministratorsByFilter(AdministratorFilter filter) {
		List<Administrator> administrators = administratorDao.selectAdministratorsByFilter(filter);
		
		SearchResult<Administrator> result = new SearchResult<Administrator>();
		result.setResult(administrators);
		
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordNumber = administratorDao.countAdministratorsByFilter(filter);
			
			PagingResult pagingResult = new PagingResult();
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageSize());
			pagingResult.setRecordNumber(recordNumber);
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
			
		}
		return result;
		
	}
	
	public void deleteAdministratorById(int id) {
		administratorDao.deleteAdministratorById(id);
	}
}
