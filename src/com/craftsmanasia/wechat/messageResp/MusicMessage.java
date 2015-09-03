package com.craftsmanasia.wechat.messageResp;

/** 
 * 音乐信息 
 *  
 * @author  牛洪波
 * @date 2015-08-25 
 */ 
public class MusicMessage extends BaseMessage {  
    // 音乐  
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }  
}  
