package com.craftsmanasia.service;

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
}
