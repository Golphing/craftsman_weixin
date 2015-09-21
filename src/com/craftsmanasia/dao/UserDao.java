package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.filter.UserFilter;
import com.craftsmanasia.model.User;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface UserDao {
	
	public int add(User user);
	
	public User get(String telephone,String password);
	
	public int count1();
	
	public void update(User user);
	
	public User getByOpenId(String openId);
	
	public User selectUserById(@Param("id")int id);

	public User selectUserByTelephone(String telephone);
	
	public List<User> selectUsersByUserFilter(@Param("filter") UserFilter filter);
	
	public int countUsersByUserFilter(@Param("filter") UserFilter filter);
	
}
