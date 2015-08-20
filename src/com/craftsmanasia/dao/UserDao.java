package com.craftsmanasia.dao;

import com.craftsmanasia.model.User;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface UserDao {
	
	public int add(User user);
	
	public User get(String name,String password);
	
	public int count1();
}
