package com.craftsmanasia.wechat.port;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.craftsmanasia.wechat.messageResp.Article;
import com.craftsmanasia.wechat.messageResp.NewsMessage;
import com.craftsmanasia.wechat.util.MessageUtil;

public class MenuClickService {
	private String respMessage;

	public String menuClick(Map<String, String> requestMap) {
		// 事件类型
		String eventType = requestMap.get("Event");
		String fromUserName = requestMap.get("FromUserName");
		String toUserName = requestMap.get("ToUserName");
		NewsMessage newsMessage = new NewsMessage();
		newsMessage.setToUserName(fromUserName);
		newsMessage.setFromUserName(toUserName);
		newsMessage.setCreateTime(new Date().getTime());
		newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		newsMessage.setFuncFlag(0);
		// 订阅
		if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
			
		

			List<Article> articleList = new ArrayList<Article>();
			Article article = new Article();
			article.setTitle("卡富文思");
			String introduction = "Hello, Welcome on our joining Craftsman. We are a search and selection firm focusing on Management Consulting and Finance industry, expecially in Asia Market.\n"+ 
			"你好，欢迎加入卡富文思。我们致力于国际咨询公司和金融领域的中高端人才寻访工作，服务于企业和经理人。\n"+
			"企业门户：www.craftsmanasia.com\n"+
			"咨询热线：40006 01218\n"+
			"邮箱地址： enquires@craftsmanasia.com\n"+
			"微信号：CM_Asia";
			article.setDescription(introduction);
			article.setPicUrl("http://weixin.craftsmanasia.com/craftsman_weixin/weixinPort/images/topic_intro.jpg");			
//			article.setUrl("www.craftsmanasia.com");
			articleList.add(article);
			newsMessage.setArticleCount(articleList.size());
			newsMessage.setArticles(articleList);
			respMessage = MessageUtil.newsMessageToXml(newsMessage);
		}
		// 取消订阅
		else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
			// TODO 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
		}
		// 自定义菜单点击事件
		else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {

			String eventKey = requestMap.get("EventKey");
			if (eventKey.equals("32")) {
				List<Article> articleList = new ArrayList<Article>();
				Article article = new Article();
				article.setTitle("卡富文思");
				String introduction = "Our function-focused strategy empowers our consultants to become experts in their specialist areas. Equipped with a proprietary technology platform, Craftsman is able to recruit the right candidate for our clients in the most efficient way. .";
				article.setDescription(introduction);
				article.setPicUrl("http://weixin.craftsmanasia.com/craftsman_weixin/weixinPort/images/aboutUs.png");			
				article.setUrl("http://weixin.craftsmanasia.com/craftsman_weixin/weixinPort/joinUs.html");
				articleList.add(article);
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsMessage);
				

			} 
		}
		return respMessage;
	}

}
