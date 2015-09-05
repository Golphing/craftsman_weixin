package com.craftsmanasia.model.filter;

import java.util.HashMap;
import java.util.Map;

public enum CompanyType {

	self(1,"self","本公司"),
	cooperate(2,"cooperate","合作企业");
	
	public static final Map<String, CompanyType> code2SettlementType;
	public static Map<Integer, CompanyType> id2SettlementType;
	
	static {
		code2SettlementType = new HashMap<String, CompanyType>();
		for(CompanyType companyType : CompanyType.values()) {
			code2SettlementType.put(companyType.getCode(), companyType);
		}
	}
	
	private int id;
	private String code;
	private String description;
	CompanyType(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public static CompanyType getFromCode(String code) {
		return code2SettlementType.get(code);
	}
	
	public static CompanyType getFromId(int id) {
		if(id2SettlementType == null) {
			id2SettlementType = new HashMap<Integer, CompanyType>();
			for(CompanyType companyType : CompanyType.values()) {
				id2SettlementType.put(companyType.getId(), companyType);
			}
		}
		return id2SettlementType.get(id);
	} 
	
	public int getId() {
        return id;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
