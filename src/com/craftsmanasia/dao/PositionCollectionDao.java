package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.model.PositionCollection;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionCollectionDao {

	public void add(PositionCollection positionCollection);
	
	public List<PositionCollection> selectPositionsByUserId(@Param("userId") int userId);
	
	public void delete(@Param("userId") int userId, @Param("positionId") int positionId);
}
