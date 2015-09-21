package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.model.Work;
import com.ebaoyang.dao.MyBatisRepository;
@MyBatisRepository
public interface WorkDao {
	public void add(Work work);
	
	public void updatWork(Work work);
	
	public List<Work> selectUserWorksByUserId(@Param("userId") int userId);

	public void deleteWork(@Param("id") int id);

	public Work selectWorkByWorkId(@Param("workId") int workId);

	
}
