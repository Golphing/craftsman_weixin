package com.craftsmanasia.wechat.messageReq;
/** 
 * 音频消息
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */ 
public class VoiceMessage extends BaseMessage {  
    // 媒体ID  
    private String MediaId;  
    // 语音格式  
    private String Format;  
  
    public String getMediaId() {  
        return MediaId;  
    }  
  
    public void setMediaId(String mediaId) {  
        MediaId = mediaId;  
    }  
  
    public String getFormat() {  
        return Format;  
    }  
  
    public void setFormat(String format) {  
        Format = format;  
    }  
}  