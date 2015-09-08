package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.filter.AdministratorFilter;
import com.craftsmanasia.model.Administrator;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface AdministratorDao {
	
	public void addAdministrator(Administrator administrator);
	
	public void updateAdministrator(Administrator administrator);
	
	public Administrator selectAdministratorByLoginName(@Param("loginName")String loginName);

	public List<Administrator> selectAdministratorsByFilter(@Param("filter") AdministratorFilter filter);
	
	public int countAdministratorsByFilter(@Param("filter") AdministratorFilter filter);
	
	public void deleteAdministratorById(@Param("id") int id);
}
