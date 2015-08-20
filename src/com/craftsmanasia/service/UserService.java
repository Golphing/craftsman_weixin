package com.craftsmanasia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.UserDao;
import com.craftsmanasia.model.User;


@Component
public class UserService {
	@Autowired
	UserDao userDao;
	
	public int add(User user){
		return userDao.add(user);
	}
	
	public User get(String name,String password){
		return userDao.get(name, password);
	}
	
	public int count1(){
		return userDao.count1();
	}
}
