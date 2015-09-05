package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.UserDao;
import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.filter.UserFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.filter.PagingData;
import com.craftsmanasia.model.filter.PagingResult;
import com.craftsmanasia.model.filter.SearchResult;



@Component
public class UserService {
	@Autowired
	UserDao userDao;
	
	public int add(User user){
		return userDao.add(user);
	}
	
	public User get(String telephone,String password){
		return userDao.get(telephone, password);
	}
	
	public int count1(){
		return userDao.count1();
	}
	
	public void update(User user){
		userDao.update(user);
	}
	
	public User getByOpenId(String openId){
		return userDao.getByOpenId(openId);
	}
	
	public User getUserById(int id) {
		return userDao.selectUserById(id);
	}
	
	public User getUserByTelephone(String telephone) {
		return userDao.selectUserByTelephone(telephone);
	}
	
	public SearchResult<User> searchUsersByUserFilter(UserFilter filter) {
		List<User> users = userDao.selectUsersByUserFilter(filter);
		SearchResult<User> result = new SearchResult<User>();
		result.setResult(users);
		
		PagingData pagingData = filter.getPagingData();
		if(filter.isPaged() && pagingData != null) {
			int recordNumber = userDao.countUsersByUserFilter(filter);
			
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
