package com.craftsmanasia.model.filter;

import java.util.HashMap;
import java.util.Map;

public enum ResumeSuscribeStatus {
	
	newSubscribe(1,"newSubscribe","新投递"),
	recommended(2,"recommended","简历已推荐"),
	screenResumed(3,"screenResumed","简历通过筛选"),
	firstInterviewed(4,"firstInterviewed","一面通过"),
	secondInterviewed(5,"secondInterviewed","二面通过"),
	thirdInterviewed(6,"thirdInterviewed","三面通过"),
	waitingOffer(7,"waitingOffer","等待offer"),
	rejected(8,"rejected","已拒绝");
	
	private static final Map<String, ResumeSuscribeStatus> code2SettlementType;
	private static Map<Integer,ResumeSuscribeStatus> id2SettlementType;
	
    static{
    	code2SettlementType = new HashMap<String, ResumeSuscribeStatus>();
    	for(ResumeSuscribeStatus resumeSuscribeStatus : ResumeSuscribeStatus.values()) {
    		code2SettlementType.put(resumeSuscribeStatus.getCode(), resumeSuscribeStatus);
    	}
    }
	private int id;
	private String code;
	private String description;
	
	ResumeSuscribeStatus(int id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	
	public static ResumeSuscribeStatus fromcode(String code) {
		return code2SettlementType.get(code);
	}
	
	public static ResumeSuscribeStatus fromid(int id) {
		if(id2SettlementType == null) {
			id2SettlementType = new HashMap<Integer,ResumeSuscribeStatus>();
			for(ResumeSuscribeStatus resumeSuscribeStatus : ResumeSuscribeStatus.values()) {
				id2SettlementType.put(resumeSuscribeStatus.getId(), resumeSuscribeStatus);
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
