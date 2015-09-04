package com.craftsmanasia.controller;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;



@Controller
@RequestMapping("/c/im")
public class ImportLiePinController {
	private final String PARAM_USER = "user_login";
    private final String PARAM_PWD = "user_pwd";
    private final String PARAM_CHECK="CheckCode";
    private final String CHARSET = "utf8";
    private final String URL_LOGIN = "http://www.liepin.com/user/ajaxlogin/";
    private String RESUME_URL = "http://c.liepin.com/";
    private String GERENXINGXI_URL="http://c.liepin.com/resume/regresume/?res_id_encode={res_id_encode}&sfrom=c_index";
    private String WORK_URL="http://my.zhaopin.com/MYZHAOPIN/resume_experience_edit.asp";
    private String YZM="https://passport.zhaopin.com/checkcode/img";
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", 
    	   "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 
	@RequestMapping("/lp")
	public String imlp(@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password){
		
	        
	        password=Password.createPassword("wanggaoping0306");
	        System.out.println(password);
	        getResume("2645442899@qq.com", password);
	        return "";
		
	}
	public String login(HttpClient client, List<NameValuePair> loginParames) {
        HttpPost post = null;
        String result = "";
        try {
           client.getParams().setBooleanParameter(
                    CoreProtocolPNames.USE_EXPECT_CONTINUE, false);
             
            client.getParams().setIntParameter("http.socket.timeout", 5000); 
            client.getParams().setIntParameter("http.connection.timeout", 5000); 
        
            post = new HttpPost(URL_LOGIN);
             
            HttpEntity he = new UrlEncodedFormEntity(loginParames);
            post.setEntity(he);
             
            HttpResponse response = client.execute(post);
            HttpEntity entity = response.getEntity();
             
            result = EntityUtils.toString(entity, CHARSET);
        } catch (Exception e) {
            if (post != null) {
                post.abort();
            }
            e.printStackTrace();
        } finally {
            if (post != null) {
                post.abort();
            }
        }
         
        return result;
    }
     
    public String login(String user, String password,String yanzheng, HttpClient client) {
        try {
            List<NameValuePair> loginParames = new ArrayList<NameValuePair>();
             
            loginParames.add(new BasicNameValuePair(PARAM_USER, user));
            loginParames.add(new BasicNameValuePair(PARAM_PWD, password));
            if("".equals(yanzheng)){
            	
            }else{
            	loginParames.add(new BasicNameValuePair(PARAM_CHECK, yanzheng));
            }
             
            String s = login(client, loginParames);
           System.out.println(s);
            return s;
           
            
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        
    }
     
    public String getResume(String userName, String pwd){
        String result = "";
        HttpClient client = new DefaultHttpClient();
        client=wrapClient(client);
        String status=login(userName, pwd,"", client);
        if(status.indexOf("\"flag\":\"1\"")!=-1){
        //	System.out.println(status);
            HttpGet get1=new HttpGet(RESUME_URL);
            try {
    			HttpResponse respons1=client.execute(get1);
    			HttpEntity entity=respons1.getEntity();
    			result=EntityUtils.toString(entity, CHARSET);
    //			System.out.println(result);
    			String reg = "res_id_encode=(\\w+)\"";
                Pattern p = Pattern.compile(reg);
                Matcher m = p.matcher(result);
                String res_id="";
                if(m.find()){
                	System.out.println(m.group(1));
                	res_id=m.group(1);
                }
                String geren=GERENXINGXI_URL.replace("{res_id_encode}", res_id);
                HttpGet get2=new HttpGet(geren);
                HttpResponse respons2=client.execute(get2);
    			HttpEntity entity2=respons2.getEntity();
    			String result2=EntityUtils.toString(entity2, CHARSET);
    			System.out.println(result2);
    			
    		} catch (ClientProtocolException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        }else{
        	System.out.println("用户名或密码错误");
        }
        
        
       

        return result;
    }
     
    
    
    public static DefaultHttpClient wrapClient(HttpClient base) {
		try {
			SSLContext ctx = SSLContext.getInstance("TLS");
			X509TrustManager tm = new X509TrustManager() {
				@Override
				public void checkClientTrusted(X509Certificate[] xcs,
						String string) {
				}

				@Override
				public void checkServerTrusted(X509Certificate[] xcs,
						String string) {
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
			};
			ctx.init(null, new TrustManager[] { tm }, null);
			SSLSocketFactory ssf = new SSLSocketFactory(ctx);
			ssf
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = base.getConnectionManager();
			SchemeRegistry sr = ccm.getSchemeRegistry();
			sr.register(new Scheme("https", ssf, 443));
			return new DefaultHttpClient(ccm, base.getParams());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
    
    private static String byteArrayToHexString(byte[] b) { 
    	   StringBuffer resultSb = new StringBuffer(); 
    	   for (int i = 0; i < b.length; i++) { 
    	    resultSb.append(byteToHexString(b[i])); 
    	   } 
    	   return resultSb.toString(); 
    	} 
    
    private static String byteToHexString(byte b) { 
    	   int n = b; 
    	   if (n < 0) 
    	    n = 256 + n; 
    	   int d1 = n / 16; 
    	   int d2 = n % 16; 
    	   return hexDigits[d1] + hexDigits[d2]; 
    	} 
}
