package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionSubscribeUserDao {

	public void add(PositionSubscribeUser positionSubscribeUser);

	public void updatePositionSubscribeUser(PositionSubscribeUser positionSubscribeUser);
	
	public List<PositionSubscribeUser> selectSubscribedPositionsByUserId(@Param("userId") int userId);
	
	public PositionSubscribeUser selectSubscribedPositionByUserIdAndPositionId(@Param("userId") int userId,
			@Param("positionId") int positionId);
	
	public List<PositionSubscribeUser> searchResumeUsersByFilter(@Param("filter") ResumeSubscribeFilter filter);
	
	public int countResumeUsersByFilter(@Param("filter") ResumeSubscribeFilter filter);
}
