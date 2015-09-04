package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface ResumeUserDao {
	
	public int add(ResumeUser user);
	
	public User get(String name,String password);
	
	public int count1();
	
	public void update(User user);
	
	public ResumeUser selectResumeUserByUserId(@Param("userId") int userId);
	
}
