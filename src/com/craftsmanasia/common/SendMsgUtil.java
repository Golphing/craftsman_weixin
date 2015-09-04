package com.craftsmanasia.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
/*
 * 
 * 
1   发送短信成功(其他请求代表成功)
-1  账号无效或未开户
-2  账号密码错误
-3  下发手机号为空
-4  下发短信内容为空
-5  指定短信企业ID为空
-6  账户或密码错误
-7  账户被冻结
-8  下发短信内容包含非法关键词
-9  账户没有充值或指定企业ID错误
-10 下发短信内容长度超出规定限制，限制为350字符
-11 下发账号余额不足
-20 服务器连接异常
-21 当前短信隶属106营销短信 必须加“尊称”、“退订回复T”
-99 系统未知错误
 * 
 * 
 */
public class SendMsgUtil {
	private static String USR="2656166034";
	private static String PWD="2656166034";
	
	public static String send(String content,String phoneNumber){
		try {
			//发送内容
			String msg =content; 
			msg+="   【卡富文思】";
			// 创建StringBuffer对象用来操作字符串
			StringBuffer sb = new StringBuffer("http://api.106msg.com/TXTJK.aspx?");
	
			// 向StringBuffer追加用户名
			sb.append("type=send&ua="+USR);
	
			// 向StringBuffer追加密码 
			sb.append("&pwd="+PWD);
	
			// 向StringBuffer追加网关id
			sb.append("&gwid=12");
	
			// 向StringBuffer追加手机号码
			sb.append("&mobile="+phoneNumber);
	
			// 向StringBuffer追加消息内容转URL标准码
		
				sb.append("&msg="+URLEncoder.encode(msg,"GB2312"));

				// 创建url对象
				URL url = new URL(sb.toString());

				// 打开url连接
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();

				// 设置url请求方式 ‘get’ 或者 ‘post’
				connection.setRequestMethod("POST");

				// 发送
				BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

				// 返回发送结果
				String inputline = in.readLine();

				// 返回结果为‘100’ 发送成功
				System.out.println(inputline);
				return inputline;

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "-99";		
	}
}
