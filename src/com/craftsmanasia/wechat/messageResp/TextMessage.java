package com.craftsmanasia.wechat.messageResp;

/** 
 * 文本 
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */  
public class TextMessage extends BaseMessage {  
    // 回复的消息内容  
    private String Content;  
  
    public String getContent() {  
        return Content;  
    }  
  
    public void setContent(String content) {  
        Content = content;  
    }  
}  