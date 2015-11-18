package com.craftsmanasia.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;



import net.sf.json.JSON;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.cxf.jaxrs.provider.json.utils.JSONUtils;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.WorkService;



@Controller
@RequestMapping("/c/im")
public class ImportLinkedInController {
	@Autowired
	WorkService workService;
	@Autowired
	ResumeUserService resumeUserService;
	
	 private final String PARAM_USER = "session_key";
	    private final String PARAM_PWD = "session_password";  
	    private final String CHARSET = "gbk";
	    private final String login_url="https://www.linkedin.com/uas/login?goback=&trk=hb_signin";
	    private final String login_submit="https://www.linkedin.com/uas/login-submit";
	    private final String PRE="https://www.linkedin.com/?trk=baidubrand-mainlink";
	    private final static String URL_LOGIN1 = "https://www.linkedin.com/uas/login";
	    private final String URL_LOGIN = "https://www.linkedin.com/uas/login-submit";
	    private String RESUME_URL = "http://i.zhaopin.com";
	    private String GERENXINGXI_URL="http://i.zhaopin.com/Resume/baseinfo/edit?resume_id={resume_id}&ext_id={ext_id}&version_number=1&language_id=1";
	    private String WORK_URL="http://my.zhaopin.com/MYZHAOPIN/resume_experience_edit.asp";
	    private String YZM="https://passport.zhaopin.com/checkcode/img";
	    
	    
	/*
	 * 返回的参数说明：
	 * 		0：成功
	 *     	1：失败
	 *     2：用户名或密码错误
	 */
	    
	@RequestMapping("/ly")
	@ResponseBody
	public String importLy(
			HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password,
			@RequestParam(value="userId") String userId)
	{	
		Map<String,String> map=new HashMap<String,String>();
		try{
			return getResume(name, password,userId);
			
		}catch(Exception e){
			e.printStackTrace();
			map.put("code", "1");
			return net.sf.json.JSONObject.fromObject(map).toString();
		}
		
	}
	
	 public String getResume(String userName, String pwd,String userId1) throws ClientProtocolException, IOException, JSONException{
	        String result = "";
	         int userId=Integer.parseInt(userId1);
	        HttpClient client = new DefaultHttpClient();
	        client=wrapClient(client);
	        client.getParams().setBooleanParameter(
	                CoreProtocolPNames.USE_EXPECT_CONTINUE, false);        
	        client.getParams().setIntParameter("http.socket.timeout", 15000); 
	        client.getParams().setIntParameter("http.connection.timeout", 15000);
	        HttpGet get=new HttpGet(login_url);
	        HttpResponse loginPage=client.execute(get);
	        result = EntityUtils.toString(loginPage.getEntity());
	        get.abort();
	        Document login_page=Jsoup.parse(result);
	        Element headers1=login_page.select("input[name=isJsEnabled]").get(0);      
	        Element headers2=login_page.select("input[name=source_app]").get(0);
	        Element headers3=login_page.select("input[name=tryCount]").get(0);
	        Element headers4=login_page.select("input[name=clickedSuggestion]").get(0);
	        Element headers5=login_page.select("input[name=session_redirect]").get(0);
	        Element headers6=login_page.select("input[name=trk]").get(0);
	        Element headers7=login_page.select("input[name=loginCsrfParam]").get(0);
	        Element headers8=login_page.select("input[name=fromEmail]").get(0);
	        Element headers9=login_page.select("input[name=csrfToken]").get(0);
	        Element headers0=login_page.select("input[name=sourceAlias]").get(0);
	        List<NameValuePair> loginParames = new ArrayList<NameValuePair>();            
	        loginParames.add(new BasicNameValuePair(PARAM_USER, userName));
	        loginParames.add(new BasicNameValuePair(PARAM_PWD, pwd));
	        loginParames.add(new BasicNameValuePair("isJsEnabled", "false"));
	        loginParames.add(new BasicNameValuePair("source_app", headers2.val()));
	        loginParames.add(new BasicNameValuePair("tryCount", headers3.val()));
	        loginParames.add(new BasicNameValuePair("clickedSuggestion", headers4.val()));
	        loginParames.add(new BasicNameValuePair("signin", "登录"));
	        loginParames.add(new BasicNameValuePair("trk", "hb_signin"));
	        loginParames.add(new BasicNameValuePair("loginCsrfParam", headers7.val()));
	        loginParames.add(new BasicNameValuePair("csrfToken", headers9.val()));
	        loginParames.add(new BasicNameValuePair("sourceAlias", headers0.val()));
	        loginParames.add(new BasicNameValuePair("session_redirect", headers5.val()));
	        loginParames.add(new BasicNameValuePair("client_ts", "1440320233643"));
	        loginParames.add(new BasicNameValuePair("client_output", "991579561"));      
	        HttpEntity he = new UrlEncodedFormEntity(loginParames);
	        HttpPost loginP=new HttpPost(login_submit);      
	        loginP.setEntity(he);
	        loginP.addHeader("Referer", "https://www.linkedin.com/uas/login?goback=&trk=hb_signin");
	        loginP.addHeader("Origin", "https://www.linkedin.com");
	        loginP.addHeader("X-IsAJAXForm", "1");
	        loginP.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.63 Safari/537.36");
	        loginP.addHeader("Host", "www.linkedin.com");
	        loginP.addHeader("X-LinkedIn-traceDataContext", "X-LI-ORIGIN-UUID=A9AkFMTVDxQwYWngvCoAAA==");
	       HttpResponse res= client.execute(loginP);
	   //    System.out.println(EntityUtils.toString(res.getEntity()));
	       
	       String result2=EntityUtils.toString(res.getEntity());
	       loginP.abort();
	       System.out.println(result2);
	       Map<String,String> map=new HashMap<String,String>();
	       if(result2.indexOf("\"status\":\"ok\"")==-1){
	    	   map.put("code", "2");
	    	   return net.sf.json.JSONObject.fromObject(map).toString();
	       }
	       HttpGet getH=new HttpGet("http://www.linkedin.com/profile/edit?trk=nav_responsive_sub_nav_edit_profile");
	       HttpResponse re=client.execute(getH);
	       
	       StringBuilder sb=new StringBuilder();
	       HttpEntity enti=re.getEntity();
	       InputStream is=enti.getContent();
	       InputStreamReader isr=new InputStreamReader(is,"UTF-8");
	       BufferedReader br=new BufferedReader(isr);
	       String ss="";
	      
	       while((ss=br.readLine())!= null){
	    	   sb.append(ss);
	       }
	       getH.abort();
	      System.out.println(ss);
	       String sss=sb.toString();
	 //      System.out.println(sss);
	 //      fw.write(sss);
	       //抓取个人信息
	       //抓取邮箱
	       ResumeUser resumeUser=new ResumeUser();
	       int email_start=sss.indexOf("email\":\"");
	       int email_end=sss.indexOf("\"", email_start+8);
	       String email=sss.substring(email_start+8, email_end);
	       System.out.println(email);
	       resumeUser.setEmail(email);
	       //抓取手机号
	       int phone_start=sss.indexOf("\"number\":\"");
	       String phone="";
	       if(phone_start !=-1){
	    	   int phone_end=sss.indexOf("\"",phone_start+10);
		        phone=sss.substring(phone_start+10, phone_end);
		       System.out.println(phone);
	       }
	      
	       resumeUser.setTelephone(phone);
	       //抓取姓名
	       int name_start=sss.indexOf("\"fullname\":\"");
	       int name_end=sss.indexOf("\"",name_start+12);
	       String name=sss.substring(name_start+12, name_end);
	       System.out.println(name);  
	       resumeUser.setName(name);
	       resumeUser.setUserId(userId);
	       //性别，地址，生日没有
	       //抓取工作经历
	       int positions_start=sss.indexOf("\"positions\":");
	       int positions_end=sss.indexOf("}]",positions_start+12);
	  //     System.out.println(positions_start);
	    //   System.out.println(positions_end);
	       String positions=sss.substring(positions_start+12, positions_end+2);
	       System.out.println(positions);
	       positions=StringEscapeUtils.unescapeJava(positions);
	       JSONArray positionsJ=new JSONArray(positions);
	       int size=positionsJ.length();
	       System.out.println("工作一：");
	       Work work=new Work();
	       JSONObject job1=positionsJ.getJSONObject(0);
	       
	       System.out.println(job1);
	     
	       String companyName=(String) job1.get("companyName");//公司名称
	       System.out.println(companyName);
	       work.setCompany(companyName);
	       String position=job1.getString("title");				//职位
	       System.out.println(position);
	      work.setPosition(position);
	      String summary_lb="";
	      try{
	            summary_lb=job1.getString("summary_lb");//工作描述
	    	   summary_lb= StringEscapeUtils.unescapeHtml(summary_lb);
	       }catch(Exception e){
	    	//  e.printStackTrace();
	    	   System.out.println(summary_lb);
	       }
	      
	       work.setDescription(summary_lb);
	       System.out.println(summary_lb);
	       String startdate_iso=job1.getString("startdate_iso");//开始时间
	       String enddate_iso="";//结束时间
	       try{
	    	   enddate_iso=(String) job1.get("enddate_iso");
	       }catch(Exception e){
	    	   DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	    	   enddate_iso=format.format(new Date());
	       }      
	       System.out.println(startdate_iso);
	       work.setBeginTime(startdate_iso);
	       work.setEndTime(enddate_iso);
	       System.out.println(enddate_iso);
	 //      System.out.println(positions);
	       //System.out.println(size);
	       work.setUserId(userId);
	       workService.add(work);
	       
	       if(size>1){
	    	   Work work2=new Work();
	    	   System.out.println("工作二：");
	    	   JSONObject job2=(JSONObject) positionsJ.get(1);
	    	   String companyName1=(String) job2.get("companyName");//公司名称
	    	   System.out.println("公司名称："+companyName1);
	    	   work2.setCompany(companyName1);
	           String position1=job2.getString("title");				//职位
	           System.out.println("职位："+position1);
	           work2.setPosition(position1);
	          String summary_lb1="";
	          try{
	        	   summary_lb1=job2.getString("summary_lb");//工作描述
		           summary_lb1= StringEscapeUtils.unescapeHtml(summary_lb1);
		       }catch(Exception e){
		    	//  e.printStackTrace();
		    	   System.out.println(summary_lb1);
		       }
		       
	          
	           System.out.println("工作描述:"+summary_lb1);
	           work2.setDescription(summary_lb1);
	           String startdate_iso1=job2.getString("startdate_iso");//开始时间
	           String enddate_iso1="";//结束时间
	           try{
	        	   enddate_iso1=(String) job2.get("enddate_iso");
	           }catch(Exception e){
	        	   DateFormat format=new SimpleDateFormat("yyyy-MM-dd");
	        	   enddate_iso1=format.format(new Date());
	           }      
	           System.out.println(startdate_iso1);
	           System.out.println(enddate_iso1);
	           work2.setBeginTime(startdate_iso1);
	           work2.setEndTime(enddate_iso1);
	           work2.setUserId(userId);
	           workService.add(work2);
	       }
	       resumeUserService.add(resumeUser);
	       map.put("code", "0");
	       return net.sf.json.JSONObject.fromObject(map).toString();
    	 
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
				ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
				ClientConnectionManager ccm = base.getConnectionManager();
				SchemeRegistry sr = ccm.getSchemeRegistry();
				sr.register(new Scheme("https", ssf, 443));
				return new DefaultHttpClient(ccm, base.getParams());
			} catch (Exception ex) {
				ex.printStackTrace();
				return null;
			}
		}
}
