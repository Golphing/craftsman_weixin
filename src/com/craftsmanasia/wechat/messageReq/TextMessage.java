package com.craftsmanasia.wechat.messageReq;


/** 
 * 文本消息
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */ 
public class TextMessage extends BaseMessage {  
    // 消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  