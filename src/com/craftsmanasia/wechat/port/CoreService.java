package com.craftsmanasia.wechat.port;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.craftsmanasia.wechat.util.MessageUtil;

/**
 * 核心服务类
 * 
 * @author 牛洪波
 * @date 2015-08-25
 */
public class CoreService {
	/**
	 * 处理微信发来的请求
	 * 
	 * @param request
	 * @return
	 */
	public static String processRequest(HttpServletRequest request) {
		String respMessage = null;
		try {
			// 默认返回的文本消息内容
			String respContent = "请求处理异常，请稍候尝试！";

			// xml请求解析
			Map<String, String> requestMap = MessageUtil.parseXml(request);

		/*	// 发送方帐号（open_id）
			String fromUserName = requestMap.get("FromUserName");
			// 公众帐号
			String toUserName = requestMap.get("ToUserName");*/
			String msgType = requestMap.get("MsgType"); 
			if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) { 
           	 
            	TextService textService = new TextService() ;
            	respMessage = textService.textProcess(requestMap);
            	
            	
            	
            } 
            // 事件推送  调用MenuClickService类
            else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) { 
            	
            	
            	MenuClickService menuClickService = new MenuClickService();
            	respMessage = menuClickService.menuClick(requestMap);
            		 	
            }  
			/*	NewsMessage newsMessage = new NewsMessage();
				newsMessage.setToUserName(fromUserName);
				newsMessage.setFromUserName(toUserName);
				newsMessage.setCreateTime(new Date().getTime());
				newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
				newsMessage.setFuncFlag(0);

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
//				article.setUrl("www.craftsmanasia.com");
				articleList.add(article);
				newsMessage.setArticleCount(articleList.size());
				newsMessage.setArticles(articleList);
				respMessage = MessageUtil.newsMessageToXml(newsMessage);*/
			

			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return respMessage;
	}
}
