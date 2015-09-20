package com.craftsmanasia.wechat.service;
 

 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONException;
import org.json.JSONObject;
 
/**
 * 
 * <p/>
 * HTTP请求工具类
 */
public class GetWeiXinCode {
    /**
     * 发送get请求
     *
     * @return 请求结果
     *
     * @throws IOException
     */
	 static String appId = "wxcf3c15e9cc77561c";  
	    // 第三方用户唯一凭证密钥  
	    static String appSecret = "a9c1e4317ea4da8f4009bd7694c2abef"; 
    public  String sendGet(String code) throws IOException, JSONException {
    	
        String str = "https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appId+"&secret="+appSecret+"&code="+code+"&grant_type=authorization_code";
        StringBuffer result = new StringBuffer(); //用来接受返回值
        URL httpUrl = null; //HTTP URL类 用这个类来创建连接
        URLConnection connection = null; //创建的http连接
        BufferedReader bufferedReader = null; //接受连接受的参数
      
        //创建URL
        httpUrl = new URL(str);
        //建立连接
        connection = httpUrl.openConnection();
        connection.setRequestProperty("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
        connection.setRequestProperty("connection", "keep-alive");
        connection.setRequestProperty("user-agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:34.0) Gecko/20100101 Firefox/34.0");
        connection.connect();
        //接受连接返回参数
        bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            result.append(line);
        }
        bufferedReader.close();
        JSONObject obj = new JSONObject(result.toString());
        String openid = (String)obj.get("openid");
        
        
        return openid;
    }
    
    

	
    
}