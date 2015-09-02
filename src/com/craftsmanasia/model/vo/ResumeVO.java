package com.craftsmanasia.model.vo;

import java.util.List;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.Work;

public class ResumeVO {

	private int id;
	private String name;
	private String telephone;
	private String gender;
	private String birthday;
	private String email;
	private String home;
	
	private List<Work> works;

	public static ResumeVO toVO(ResumeUser resumeUser, List<Work> works) {
		if(resumeUser == null) {
			return null;
		}
		ResumeVO vo = new ResumeVO();
		vo.setId(resumeUser.getId());
		vo.setName(resumeUser.getName());
		vo.setEmail(resumeUser.getEmail());
		vo.setGender(resumeUser.getGender());
		vo.setHome(resumeUser.getHome());
		vo.setBirthday(resumeUser.getBirthday());
		vo.setTelephone(resumeUser.getTelephone());
		
		vo.setWorks(works);
		return vo;
		
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getHome() {
		return home;
	}

	public void setHome(String home) {
		this.home = home;
	}

	public List<Work> getWorks() {
		return works;
	}

	public void setWorks(List<Work> works) {
		this.works = works;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
