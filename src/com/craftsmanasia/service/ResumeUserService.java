package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.ResumeUserDao;
import com.craftsmanasia.filter.UserFilter;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;



@Component
public class ResumeUserService {
	@Autowired
	ResumeUserDao userDao;
	
	public int add(ResumeUser user){
		return userDao.add(user);
	}
	
	public User get(String name,String password){
		return userDao.get(name, password);
	}
	
	public int count1(){
		return userDao.count1();
	}
	
	public void update(User user){
		userDao.update(user);
	}
	
	public ResumeUser selectResumeUserByUserId(int userId) {
		return userDao.selectResumeUserByUserId(userId);
	}
	
	public SearchResult<ResumeUser> searchResumeUsersByUserFilter(UserFilter filter) {
		List<ResumeUser> resumeUsers = userDao.selectResumeUsersByUserFilter(filter);
		SearchResult<ResumeUser> result = new SearchResult<ResumeUser>();
		result.setResult(resumeUsers);
		
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordNumber = userDao.countResumeUsersByUserFilter(filter);
			
			PagingResult pagingResult = new PagingResult();
			pagingResult.setPageNumber(pagingData.getPageNumber());
			pagingResult.setPageSize(pagingData.getPageSize());
			pagingResult.setRecordNumber(recordNumber);
			
			result.setPaged(true);
			result.setPagingResult(pagingResult);
		}
		
		return result;
	}
}
