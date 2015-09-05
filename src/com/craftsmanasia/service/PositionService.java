package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.CompanyDao;
import com.craftsmanasia.dao.PositionDao;
import com.craftsmanasia.filter.PositionFilter;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;

@Component
public class PositionService {

	@Autowired
	CompanyDao companyDdao;
	
	@Autowired
	PositionDao positionDao;
	/*
	 * 给一个公司发布职位
	 * */
	public void addCompanyPosition(Position position) {
		positionDao.addPosition(position);
	}
	
	/*
	 * 更新职位信息
	 * */
	public void updateCompanyPosition(Position position) {
		positionDao.updatePosition(position);
	}
	
	/*
	 * 查找职位信息
	 * */
	
	public List<Position> getCompanyPositions(String companyName, String positionTitle) {
		return positionDao.getPositionsByCompanyNameAndTitle(companyName, positionTitle);
	}
	
	public List<Position> getCompanyPositionsByCompanyId(Integer companyId) {
		return positionDao.getPositionsByCompanyId(companyId);
	}
	
	public List<Position> getOwnCompanyPositions() {
		return positionDao.selectOwnCompanyPositions();
	}
	
	public Position getPositionByCompanyIdAndTitle(int companyId, String title) {
		return positionDao.getPositionsByCompanyIdAndTitle(companyId, title);
	}
	
	public Position getPositionById(int id) {
		return positionDao.getPositionById(id);
	}
	
	public SearchResult<Position> searchPositionsByFilter(PositionFilter filter) {
		List<Position> positions = positionDao.selectPositionsByFilter(filter);
		SearchResult<Position> result = new SearchResult<Position>();
		result.setResult(positions);
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordNumbers = positionDao.countPositionsByFilter(filter);
			
			PagingResult pagingResult = new PagingResult();
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageSize());
			pagingResult.setRecordNumber(recordNumbers);
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
		}
		
		return result;
	}
}
