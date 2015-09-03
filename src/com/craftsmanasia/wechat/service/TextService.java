package com.craftsmanasia.wechat.service;

import java.util.Map;

/** 
 * 核心服务类 
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */ 
public class TextService {
	private String respContent;

	public String textProcess(Map<String, String> requestMap) {
		String respMessage = null;
		String msgContent = requestMap.get("Content");
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		

		return respMessage;
	}
}
