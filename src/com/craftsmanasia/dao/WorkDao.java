package com.craftsmanasia.dao;

import com.craftsmanasia.model.Work;
import com.ebaoyang.dao.MyBatisRepository;
@MyBatisRepository
public interface WorkDao {
	public void add(Work work);
}
