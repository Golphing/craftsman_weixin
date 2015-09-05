package com.craftsmanasia.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.PositionCollectionDao;
import com.craftsmanasia.dao.PositionSubscribeUserDao;
import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionCollection;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;

@Component
public class PositionSubscribeUserService {
	@Autowired
	PositionSubscribeUserDao positionSubscribeUserDao;
	
	@Autowired
	PositionCollectionDao positionCollectionDao;
	
	public void subscribePosition(PositionSubscribeUser user){
		positionSubscribeUserDao.add(user);
	}
	
	public List<PositionSubscribeUser> getSubscribedPositionsByUserId(int userId) {
		return positionSubscribeUserDao.selectSubscribedPositionsByUserId(userId);
	}
	
	public PositionSubscribeUser getSubscribedPositionByUserIdAndPositionId(int userId, int positionId) {
		return positionSubscribeUserDao.selectSubscribedPositionByUserIdAndPositionId(userId, positionId);
	}
	
	public void collectPosition(PositionCollection positionCollection) {
		positionCollectionDao.add(positionCollection);
	}
	
	public void updateResumeSubscribe(PositionSubscribeUser user) {
		positionSubscribeUserDao.updatePositionSubscribeUser(user);
	}
	
	public List<Position> getAllCollectionPositionsByUserId(int userId) {
		List<PositionCollection> positionCollections = positionCollectionDao.selectPositionsByUserId(userId);
		List<Position> positions = new ArrayList<Position>();
		for(PositionCollection positionCollection : positionCollections) {
			positions.add(positionCollection.getPosition());
		}
		return positions;
		
	}
	
	public void cancleCollectionPositionByUserIdAndPosition(int userId, int positionId) {
		positionCollectionDao.delete(userId,positionId);
	}
	
	public SearchResult<PositionSubscribeUser> searchResumeUsersByFilter(ResumeSubscribeFilter filter) {
		List<PositionSubscribeUser> resumes = positionSubscribeUserDao.searchResumeUsersByFilter(filter);
		
		SearchResult<PositionSubscribeUser> result = new SearchResult<PositionSubscribeUser>();
		result.setResult(resumes);
		
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordnumbers = positionSubscribeUserDao.countResumeUsersByFilter(filter);

			PagingResult pagingResult = new PagingResult();
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageNumber());
			pagingResult.setRecordNumber(recordnumbers);
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
		}
		return result;
	}
	
}
