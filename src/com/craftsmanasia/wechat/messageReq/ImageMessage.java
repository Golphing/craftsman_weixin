package com.craftsmanasia.wechat.messageReq;



/** 
 *  图片消息 
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */ 
public class ImageMessage extends BaseMessage {  
    // 图片链接  
    private String PicUrl;  
  
    public String getPicUrl() {  
        return PicUrl;  
    }  
  
    public void setPicUrl(String picUrl) {  
        PicUrl = picUrl;  
    }  
}  