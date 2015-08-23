package com.craftsmanasia.dao;

import java.util.List;

import com.craftsmanasia.model.Position;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionDao {

	public void addPosition(Position position);
	
	public void updatePosition(Position position);
	
	public List<Position> getPositionsByCompanyId(int companyId);
	
	public Position getPositionById(int id);
	
	public List<Position> getPositionsByCompanyNameAndPositionName(String companyName, String positionTitle);
}
