package com.craftsmanasia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.ResumeUserDao;
import com.craftsmanasia.dao.UserDao;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;



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
}
