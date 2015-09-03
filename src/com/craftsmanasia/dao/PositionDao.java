package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.model.Position;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionDao {

	public void addPosition(Position position) ;
	
	public void updatePosition(Position position);
	
	public List<Position> getPositionsByCompanyId(@Param("id") int companyId);
	
	public Position getPositionById(@Param("id") int id);
	
	public List<Position> getPositionsByCompanyNameAndTitle(@Param("name")String name, @Param("title") String title);
	
	public Position getPositionsByCompanyIdAndTitle(@Param("id") Integer companyId, @Param("title") String title);

}
