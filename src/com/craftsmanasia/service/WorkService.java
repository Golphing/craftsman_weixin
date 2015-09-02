package com.craftsmanasia.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.craftsmanasia.dao.WorkDao;
import com.craftsmanasia.model.Work;
@Service
public class WorkService {
	
	@Autowired
	WorkDao workDao;
	public void add(Work work){
		workDao.add(work);
	}
	
	public List<Work> getUserWorksByUserId(int userId) {
		return workDao.selectUserWorksByUserId(userId);
	}
	
	public void updatWork(Work work){
		workDao.updatWork(work);
	}
	
	public void deleteWork(int id) {
		workDao.deleteWork(id);
	}
}
