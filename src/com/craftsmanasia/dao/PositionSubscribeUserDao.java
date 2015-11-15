package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.filter.ResumeSubscribeFilter;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.craftsmanasia.model.ResumeSubscribeStatus;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionSubscribeUserDao {

	public void add(PositionSubscribeUser positionSubscribeUser) throws Exception;

	public void updatePositionSubscribeUser(PositionSubscribeUser positionSubscribeUser);
	
	public PositionSubscribeUser selectPositionSubscribeUserById(@Param("id") int id);
	
	public List<PositionSubscribeUser> selectSubscribedPositionsByUserId(@Param("userId") int userId);
	
	public PositionSubscribeUser selectSubscribedPositionByUserIdAndPositionId(@Param("userId") int userId,
			@Param("positionId") int positionId);
	
	public List<PositionSubscribeUser> searchResumeUsersByFilter(@Param("filter") ResumeSubscribeFilter filter);
	
	public int countResumeUsersByFilter(@Param("filter") ResumeSubscribeFilter filter);
	
	public void addStatus(ResumeSubscribeStatus resumeSubscribeStatus) throws Exception;
	
	public List<ResumeSubscribeStatus> selectResumeSubscribeStatusById(@Param("resumeSubscribeId") int resumeSubscribeId);
}
