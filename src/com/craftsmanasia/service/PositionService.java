package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.craftsmanasia.dao.CompanyDao;
import com.craftsmanasia.dao.PositionDao;
import com.craftsmanasia.model.Position;

@Component
public class PositionService {

	@Autowired
	CompanyDao companyDdao;
	
	@Autowired
	PositionDao positionDao;
	/*
	 * 给一个公司发布职位
	 * */
	public void addCompanyPosition(Position position) throws Exception {
		positionDao.addPosition(position);
	}
	
	/*
	 * 更新职位信息
	 * */
	public void updateCompanyPosition(Position position) {
		positionDao.updatePosition(position);
	}
	
	/*
	 * 查找职位信息
	 * */
	
	public List<Position> getCompanyPositions(String companyName, String positionTitle) {
		return positionDao.getPositionsByCompanyNameAndTitle(companyName, positionTitle);
	}
	
	public List<Position> getCompanyPositionsByCompanyId(Integer companyId) throws Exception {
		return positionDao.getPositionsByCompanyId(companyId);
	}
	
	public Position getPositionByCompanyIdAndTitle(int companyId, String title) {
		return positionDao.getPositionsByCompanyIdAndTitle(companyId, title);
	}
}
