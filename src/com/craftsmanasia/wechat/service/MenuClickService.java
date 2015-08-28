package com.craftsmanasia.wechat.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.craftsmanasia.wechat.messageResp.Article;
import com.craftsmanasia.wechat.messageResp.NewsMessage;
import com.craftsmanasia.wechat.messageResp.TextMessage;
import com.craftsmanasia.wechat.util.MessageUtil;

public class MenuClickService {
	private String respMessage;

	public String menuClick(Map<String, String> requestMap) {
		

			String eventKey = requestMap.get("EventKey");
			String fromUserName = requestMap.get("FromUserName");
			String toUserName = requestMap.get("ToUserName");
			if (eventKey.equals("11")) {

				

			} else if (eventKey.equals("12")) {

				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				String respContent = "尊敬的用户，您好：\n"
						+ "1,查看本微信注册用户\n输入:c\n" ;
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);

			} else if (eventKey.equals("13")) {
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				String respContent = "尊敬的用户，您好\n请按以下格式";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);

			} else if (eventKey.equals("21")) {

				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);
				List<Article> articleList = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("介绍");
				article.setDescription("本系统共有");				
				articleList.add(article);
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsMessage);
			} else if (eventKey.equals("22")) {
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				String respContent = "尊敬的用户\n";
				
						
					
				
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);
				
				
//				此功能暂时取消
			} else if (eventKey.equals("23")) {
				TextMessage textMessage = new TextMessage();
				textMessage.setToUserName(fromUserName);
				textMessage.setFromUserName(toUserName);
				textMessage.setCreateTime(new Date().getTime());
				textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				textMessage.setFuncFlag(0);
				String respContent=null;
				respContent = "1";
				textMessage.setContent(respContent);
				respMessage = MessageUtil.textMessageToXml(textMessage);	
				
				
			} else if (eventKey.equals("31")) {
				

			} else if (eventKey.equals("32")) {
				NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

				List<Article> articleList = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("简介：");
				article.setDescription("你好，欢迎加入卡富文思。我们致力于国际咨询公司和金融领域的中高端人才寻访工作，服务于企业和经理人。企业门户：www.craftsmanasia.com咨询热线：40006 01218箱地址： enquires@craftsmanasia.com 信号：CM_Asia");
							
				articleList.add(article);
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsMessage);

			} else if (eventKey.equals("33")) {
				

			}
	
		return respMessage;
	}

}
