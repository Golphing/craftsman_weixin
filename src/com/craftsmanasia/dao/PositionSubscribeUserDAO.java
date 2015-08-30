package com.craftsmanasia.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.craftsmanasia.model.Position;
import com.craftsmanasia.model.PositionSubscribeUser;
import com.ebaoyang.dao.MyBatisRepository;

@MyBatisRepository
public interface PositionSubscribeUserDAO {

	public void add(PositionSubscribeUser positionSubscribeUser);

	public List<Position> getPositionsByUserId(@Param("userId") int userId);
}
