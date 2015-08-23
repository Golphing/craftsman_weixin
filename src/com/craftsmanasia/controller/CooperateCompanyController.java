package com.craftsmanasia.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.craftsmanasia.service.CompanyService;

@Controller
public class CooperateCompanyController {

	@Autowired
	CompanyService companyService;
	
	@POST
	@Path("/create/company")
	@Consumes("application/json")
	@Produces("application/json;charset=UTF-8")
	public void createCooperateCompany() {
		
	}
	
	
}
