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
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;

@Component
public class PositionSubscribeUserService {
	@Autowired
	PositionSubscribeUserDao positionSubscribeUserDao;
	
	@Autowired
	PositionCollectionDao positionCollectionDao;
	
	public void subscribePosition(PositionSubscribeUser user) throws Exception{
		positionSubscribeUserDao.add(user);
	}
	
	public List<PositionSubscribeUser> getSubscribedPositionsByUserId(int userId) {
		List<PositionSubscribeUser> results = positionSubscribeUserDao.selectSubscribedPositionsByUserId(userId);
		
		if(results == null) {
			return new ArrayList<PositionSubscribeUser>();
		}
		// 设置状态
		for(PositionSubscribeUser result : results) {
			result.setStatuses(positionSubscribeUserDao.selectResumeSubscribeStatusById(result.getId()));
		}
		return results;
	}
	
	public PositionSubscribeUser getSubscribedPositionByUserIdAndPositionId(int userId, int positionId) {
		PositionSubscribeUser positionSubscribeUser = positionSubscribeUserDao
				.selectSubscribedPositionByUserIdAndPositionId(userId, positionId);
		
		// 设置状态
		if(positionSubscribeUser != null) {
			positionSubscribeUser.setStatuses(
					positionSubscribeUserDao.selectResumeSubscribeStatusById(positionSubscribeUser.getId()));
		}
		
		return positionSubscribeUser;
	}
	
	public void collectPosition(PositionCollection positionCollection) {
		positionCollectionDao.add(positionCollection);
	}
	
	public void updateResumeSubscribe(PositionSubscribeUser user) {
		positionSubscribeUserDao.updatePositionSubscribeUser(user);
	}
	
	public PositionSubscribeUser searchResumeSubscribeById(int id) {
		return positionSubscribeUserDao.selectPositionSubscribeUserById(id);
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
		// 设置状态
		for(PositionSubscribeUser resume : resumes) {
			resume.setStatuses(positionSubscribeUserDao.selectResumeSubscribeStatusById(resume.getId()));
		}
		SearchResult<PositionSubscribeUser> result = new SearchResult<PositionSubscribeUser>();
		result.setResult(resumes);
		
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordnumbers = positionSubscribeUserDao.countResumeUsersByFilter(filter);

			PagingResult pagingResult = new PagingResult();
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageSize());
			pagingResult.setRecordNumber(recordnumbers);
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
		}
		return result;
	}
	
	public void addResumeSubscribeStatus(ResumeSubscribeStatus resumeSubscribeStatus) throws Exception {
		positionSubscribeUserDao.addStatus(resumeSubscribeStatus);
	}
}
