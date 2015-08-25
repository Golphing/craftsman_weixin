package com.craftsmanasia.dao;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface ResumeUserDao {
	
	public int add(ResumeUser user);
	
	public User get(String name,String password);
	
	public int count1();
	
	public void update(User user);
}
