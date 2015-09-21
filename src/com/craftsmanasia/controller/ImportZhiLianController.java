package com.craftsmanasia.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
public class ImportZhiLianController {
	@Autowired
	WorkService workService;
	@Autowired
	ResumeUserService resumeUserService;
	
	
	private final String PARAM_USER = "LoginName";
    private final String PARAM_PWD = "Password";
    private final String PARAM_CHECK="CheckCode";
    private final String CHARSET = "gbk";
    private final String URL_LOGIN = "https://passport.zhaopin.com/";
    private String RESUME_URL = "http://i.zhaopin.com";
    private String GERENXINGXI_URL="http://i.zhaopin.com/Resume/baseinfo/edit?resume_id={resume_id}&ext_id={ext_id}&version_number=1&language_id=1";
    private String WORK_URL="http://my.zhaopin.com/MYZHAOPIN/resume_experience_edit.asp";
    private String YZM="https://passport.zhaopin.com/checkcode/img";
	
    /*
     * 		返回参数说明：
     * 			0    成功
     * 			1 失败
     * 			2 用户名或密码错误
     * 			3 需要验证码
     * 			4 验证码错误
     * 			5 尚未创建简历
     */
    
    
	@RequestMapping("/zl")
	@ResponseBody
	public String importLP(HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password,
			@RequestParam(value="login_verify",defaultValue="") String login_verify,
			@RequestParam(value="userId") String userId1){
		int userId=Integer.parseInt(userId1);	
		HttpClient client=(HttpClient) session.getAttribute("zlclient");
		Map<String,String> map=new HashMap<String,String>();
		if(client==null){			
			client= new DefaultHttpClient();
			 client=wrapClient(client);
			session.setAttribute("zlclient", client);
		}
		System.out.println(client);
		 try {
	            List<NameValuePair> loginParames = new ArrayList<NameValuePair>();
	             
	            loginParames.add(new BasicNameValuePair(PARAM_USER, name));
	            loginParames.add(new BasicNameValuePair(PARAM_PWD, password));
	            if("".equals(login_verify)){
	            	
	            }else{ 
	            	loginParames.add(new BasicNameValuePair(PARAM_CHECK, login_verify));
	            }
	            String s = login(client, loginParames);
	            if(s.indexOf("正在跳转")!= -1){
	            	System.out.println("登录成功!");
	            	 try {
	            		 String result="";
	            		 String resumeURl = RESUME_URL;
	                     HttpGet get = new HttpGet(resumeURl);
	                     HttpResponse response = client.execute(get);
	                     HttpEntity entity = response.getEntity();
	                     result =  EntityUtils.toString(entity, CHARSET);
	                     
	                     String reg = "Resume_ID=(\\d+)";
	                     Pattern p = Pattern.compile(reg);
	                     Matcher m = p.matcher(result);
	                     String resumeId="";
	                     if(m.find()){
	                    	 resumeId=m.group(1);
	                     }
	                     if("".equals(resumeId)){
	                    	 map.put("code", "5");
	                    	 return JSONObject.fromObject(map).toString();
	                     }
	                     String reg1 = "Ext_ID=(\\w+)\\&";
	                     Pattern p1 = Pattern.compile(reg1);
	                     Matcher m1 = p1.matcher(result);
	                     String extId="";
	                     if(m1.find()){
	                    	 System.out.println(m1.group(1));
	                    	 extId=m1.group(1);
	                     }
	                     //获取个人信息
	                     ResumeUser user=new ResumeUser();
	                     String url1=GERENXINGXI_URL.replace("{ext_id}", extId);
	                     url1=url1.replace("{resume_id}", resumeId);
	                     System.out.println(url1);
	                     HttpGet getGeRenXinXi=new HttpGet(url1);
	                     HttpResponse resp1=client.execute(getGeRenXinXi);
	                     HttpEntity geren=resp1.getEntity();
	                     String result1=EntityUtils.toString(geren, CHARSET);	   
	               //      System.out.println(result1);
	                     Document doc=Jsoup.parse(result1);
	                     Element username1=doc.select("input[name=username]").first();
	                     String username=username1.attr("value");
	                     String birthday_y=doc.select("input[name=birth_date_y]").first().attr("value");
	                     String birthday_m=doc.select("input[name=birth_date_m]").first().attr("value");
	                     if(birthday_m.length()<2)
	                    	 birthday_m="0"+birthday_m;
	                     String birthday=birthday_y+"-"+birthday_m+"-01";
	                     System.out.println(username);
	                     user.setName(username);
	                     user.setBirthday(birthday);
	                     String gender=doc.select("input[name=gender]").first().attr("value");
	                   
	                     if("1".equals(gender))
	                    	 gender="男";
	                     else
	                    	 gender="女";
	                     user.setGender(gender);
	                     String phone=doc.select("input[name=contact_num0]").first().attr("value");
	                     user.setTelephone(phone);
	                     String email=doc.select("input[name=emailshow]").first().attr("value");
	                     user.setEmail(email);
	                     user.setUserId(userId);
	                    /* String home=doc.select("input[id=hukouF_button]").first().attr("value");
	                     user.setHome(home);*/
	                     if(getGeRenXinXi!=null)
	                    	 getGeRenXinXi.abort();
	                //     System.out.println(result1);
	                     //获取工作经历一
	                     HttpPost post1=new HttpPost(WORK_URL);
	                     List<NameValuePair> workParames = new ArrayList<NameValuePair>();
	                     workParames.add(new BasicNameValuePair("Language_ID", "1"));
	                     workParames.add(new BasicNameValuePair("ext_id", extId));
	                     workParames.add(new BasicNameValuePair("Resume_ID", resumeId));
	                     workParames.add(new BasicNameValuePair("Version_Number", "1"));
	                     workParames.add(new BasicNameValuePair("RowID", "1"));
	                     HttpEntity he1 = new UrlEncodedFormEntity(workParames);
	                     post1.setEntity(he1);
	                     HttpResponse rep2=client.execute(post1);
	                     HttpEntity et2=rep2.getEntity();
	                     String result3=EntityUtils.toString(et2, CHARSET);
	                     //可以根据值是否为空判断是否有这个工作经历
	            //        System.out.println(result3);
	                     Document doc2=Jsoup.parse(result3);
		                 Elements company11=doc2.select("input[name=cmpany_name]");
		                 if(company11.size()>0){	                    
	                    	
	                    	String company1=company11.first().attr("value");
	                    	if(! "".equals(company1)){
	                    		String workstart_date_y=doc2.select("input[name=workstart_date_y]").first().attr("value");
		                    	String workstart_date_m=doc2.select("input[name=workstart_date_m]").first().attr("value");
		                    	if(workstart_date_m.length()<2){
		                    		workstart_date_m="0"+workstart_date_m;
		                    	}
		                    	String workend_date_y=doc2.select("input[name=workend_date_y]").first().attr("value");
		                    	String workend_date_m=doc2.select("input[name=workend_date_m]").first().attr("value");
		                    	if(workend_date_m.length()<2){
		                    		workend_date_m="0"+workend_date_m;
		                    	}
		                    	String startDate=workstart_date_y+workstart_date_m;
		                    	String endDate=workend_date_y+workend_date_m;
		                    	Work work=new Work();
		                    	work.setBeginTime(startDate);
		                    	work.setEndTime(endDate);
		                    	work.setCompany(company1);
		                    	String position=doc2.select("input[name=customSubJobtype]").first().attr("value");
		                    	work.setPosition(position);
		                    	String description=doc2.select("textarea[name=job_description]").first().text();
		                    	work.setDescription(description);
		                    	work.setUserId(userId);
		                    	/*Elements department=doc2.select("input[titlename=职位类别]");
		                    	String department1=department.first().attr("value");		                    	
		                    	work.setDepartment(department1);*/
		                    	workService.add(work);
	                    	}
	                    	
	                    }else{
	                    	System.out.println("无工作经验1");
	                    }
	                    if(post1!=null){
	                    	post1.abort();
	                    }
	                     
	                     //获取工作经历二
	                     HttpPost post2=new HttpPost(WORK_URL);
	                     List<NameValuePair> workParames1 = new ArrayList<NameValuePair>();
	                     workParames1.add(new BasicNameValuePair("Language_ID", "1"));
	                     workParames1.add(new BasicNameValuePair("ext_id", extId));
	                     workParames1.add(new BasicNameValuePair("Resume_ID", resumeId));
	                     workParames1.add(new BasicNameValuePair("Version_Number", "1"));
	                     workParames1.add(new BasicNameValuePair("RowID", "2"));
	                     HttpEntity he2 = new UrlEncodedFormEntity(workParames1);
	                     post2.setEntity(he2);
	                     HttpResponse rep3=client.execute(post2);
	                     HttpEntity et3=rep3.getEntity();
	                     String result4=EntityUtils.toString(et3, CHARSET);
	             //        System.out.println(result4);
	                     Document doc1=Jsoup.parse(result4);
	                    Elements company12=doc1.select("input[name=cmpany_name]");
	                     if(company12.size()>0){
	                    	 	String company=company12.first().attr("value");
	                    	 	if(! "".equals(company)){
	                    	 		String workstart_date_y=doc1.select("input[name=workstart_date_y]").first().attr("value");
			                    	String workstart_date_m=doc1.select("input[name=workstart_date_m]").first().attr("value");
			                    	if(workstart_date_m.length()<2){
			                    		workstart_date_m="0"+workstart_date_m;
			                    	}
			                    	String workend_date_y=doc1.select("input[name=workend_date_y]").first().attr("value");
			                    	String workend_date_m=doc1.select("input[name=workend_date_m]").first().attr("value");
			                    	if(workend_date_m.length()<2){
			                    		workend_date_m="0"+workend_date_m;
			                    	}
			                    	String startDate=workstart_date_y+workstart_date_m;
			                    	String endDate=workend_date_y+workend_date_m;
			                    	Work work=new Work();
			                    	work.setBeginTime(startDate);
			                    	work.setEndTime(endDate);
			                    	work.setCompany(company);
			                    	String position=doc1.select("input[name=customSubJobtype]").first().attr("value");
			                    	work.setPosition(position);
			                    	String description=doc1.select("textarea[name=job_description]").first().text();
			                    	work.setDescription(description);
			                    	work.setUserId(userId);
			                    	/*Elements department=doc1.select("input[titlename=职位类别]");
			                    	String department1=department.first().attr("value");		                    	
			                    	work.setDepartment(department1);*/
			                    	workService.add(work);
	                    	 	}
		                    	
		                 }else{
		                	 System.out.println("无工作经验2");
		                 }
	                     if(post2!=null){
		                    	post2.abort();
		                   }
	                     resumeUserService.add(user);
	                 }catch(Exception e){
	                 	e.printStackTrace();
	                 	map.put("code", "1");		            	
		            	return JSONObject.fromObject(map).toString();
	                 }	                
	            	map.put("code", "0");	            	
	            	return JSONObject.fromObject(map).toString();
	            }else if(s.indexOf("您的操作有些频繁，请输入验证码") != -1){
	            	System.out.println("您的操作有些频繁，请输入验证码");
	            	HttpGet httpGet=new HttpGet(YZM);
	            	HttpResponse resp=client.execute(httpGet);
	            	InputStream in=resp.getEntity().getContent();
	            	String path="/yzm/"+System.currentTimeMillis() + ".gif";
					String path1=request.getSession().getServletContext().getRealPath(path);
	            	File f=new File(path1);
	            	FileOutputStream fos=new FileOutputStream(f);
	            	byte[] buf=new byte[1024];
	            	int len=0;
	            	while((len=in.read(buf)) != -1){
	            		fos.write(buf, 0, len);
	            	}
	            	fos.close();
	            	in.close();
	            	if(httpGet !=null)
	            		httpGet.abort();
	            	String yzmPath="/craftsman_weixin"+path;
	            	map.put("code", "3");
	            	map.put("yzmUrl", yzmPath);
	            	return JSONObject.fromObject(map).toString();
	            }else if(s.indexOf("请输入正确的验证码")!=-1){
	            	System.out.println("验证码错误");
	            	HttpGet httpGet=new HttpGet(YZM);
	            	HttpResponse resp=client.execute(httpGet);
	            	InputStream in=resp.getEntity().getContent();
	            	String path="/yzm/"+System.currentTimeMillis() + ".gif";
					String path1=request.getSession().getServletContext().getRealPath(path);
	            	File f=new File(path1);
	            	FileOutputStream fos=new FileOutputStream(f);
	            	byte[] buf=new byte[1024];
	            	int len=0;
	            	while((len=in.read(buf)) != -1){
	            		fos.write(buf, 0, len);
	            	}
	            	fos.close();
	            	in.close();
	            	if(httpGet !=null)
	            		httpGet.abort();
	            	String yzmPath="/craftsman_weixin"+path;
	            	map.put("code", "4");
	            	map.put("yzmUrl", yzmPath);
	            	return JSONObject.fromObject(map).toString();
	            	
	            }
	            
	            else{
	            	System.out.println("手机号或邮箱不存在!");
	            	map.put("code", "2");
	            	return JSONObject.fromObject(map).toString();
	            }
	            
	       //     System.out.println(s);
	            
	        } catch (Exception e) {
	            e.printStackTrace();
	            map.put("code", "1");
            	return JSONObject.fromObject(map).toString();
	        }
		
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
     //       System.out.println(s);
            if(s.indexOf("正在跳转")!= -1){
            	System.out.println("登录成功!");
            	String resumeURl = RESUME_URL;
                try {
                    HttpGet get = new HttpGet(resumeURl);
                    HttpResponse response = client.execute(get);
                    HttpEntity entity = response.getEntity();
                    String result = "";
                    result =  EntityUtils.toString(entity, CHARSET);
               //     System.out.println(result);
                    String reg = "Resume_ID=(\\d+)";
                    Pattern p = Pattern.compile(reg);
                    Matcher m = p.matcher(result);
                    String resumeId="";
                    if(m.find()){
                   	 resumeId=m.group(1);
                    }
                    String reg1 = "Ext_ID=(\\w+)\\&";
                    Pattern p1 = Pattern.compile(reg1);
                    Matcher m1 = p1.matcher(result);
                    String extId="";
                    if(m1.find()){
                   	 System.out.println(m1.group(1));
                   	 extId=m1.group(1);
                    }
                    //获取个人信息
                    String url1=GERENXINGXI_URL.replace("{ext_id}", extId);
                    url1=url1.replace("{resume_id}", resumeId);
                    HttpGet getGeRenXinXi=new HttpGet(url1);
                    HttpResponse resp1=client.execute(getGeRenXinXi);
                    HttpEntity geren=resp1.getEntity();
                    String result1=EntityUtils.toString(geren, CHARSET);
                   
                    Document doc=Jsoup.parse(result1);
                    Element username1=doc.select("input[name=username]").first();
                    String username=username1.attr("value");
                    System.out.println(username);
               //     System.out.println(result1);
                    //获取工作经历一
                    HttpPost post1=new HttpPost(WORK_URL);
                    List<NameValuePair> workParames = new ArrayList<NameValuePair>();
                    workParames.add(new BasicNameValuePair("Language_ID", "1"));
                    workParames.add(new BasicNameValuePair("ext_id", extId));
                    workParames.add(new BasicNameValuePair("Resume_ID", resumeId));
                    workParames.add(new BasicNameValuePair("Version_Number", "1"));
                    workParames.add(new BasicNameValuePair("RowID", "1"));
                    HttpEntity he1 = new UrlEncodedFormEntity(workParames);
                    post1.setEntity(he1);
                    HttpResponse rep2=client.execute(post1);
                    HttpEntity et2=rep2.getEntity();
                    String result3=EntityUtils.toString(et2, CHARSET);
                    //可以根据值是否为空判断是否有这个工作经历
             //       System.out.println(result3);
                    
                    //获取工作经历二
                    HttpPost post2=new HttpPost(WORK_URL);
                    List<NameValuePair> workParames1 = new ArrayList<NameValuePair>();
                    workParames1.add(new BasicNameValuePair("Language_ID", "1"));
                    workParames1.add(new BasicNameValuePair("ext_id", extId));
                    workParames1.add(new BasicNameValuePair("Resume_ID", resumeId));
                    workParames1.add(new BasicNameValuePair("Version_Number", "1"));
                    workParames1.add(new BasicNameValuePair("RowID", "2"));
                    HttpEntity he2 = new UrlEncodedFormEntity(workParames1);
                    post2.setEntity(he2);
                    HttpResponse rep3=client.execute(post2);
                    HttpEntity et3=rep3.getEntity();
                    String result4=EntityUtils.toString(et3, CHARSET);
                //    System.out.println(result4);
                    
                }catch(Exception e){
                	e.printStackTrace();
                }
            	return "1";
            }else if(s.indexOf("您的操作有些频繁，请输入验证码") != -1){
            	System.out.println("您的操作有些频繁，请输入验证码");
            	HttpGet httpGet=new HttpGet(YZM);
            	HttpResponse resp=client.execute(httpGet);
            	InputStream in=resp.getEntity().getContent();
            	
            	File f=new File("");
            	FileOutputStream fos=new FileOutputStream(f);
            	byte[] buf=new byte[1024];
            	int len=0;
            	while((len=in.read(buf)) != -1){
            		fos.write(buf, 0, len);
            	}
            	fos.close();
            	in.close();
            	return "2";
            }else if(s.indexOf("请输入正确的验证码")!=-1){
            	System.out.println("验证码错误");
            }
            
            else{
            	System.out.println("登录失败!");
            
            	return "0";
            }
            
       //     System.out.println(s);
            
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return "";
    }
     
    public String getResume(String userName, String pwd,HttpClient client){
        String result = "";
        String status=login(userName, pwd,"", client);
        
        while("2".equals(status)){
        	System.out.println("请输入验证码：");
        	return "";
        }
        if("1".equals(status)){
        	 String resumeURl = RESUME_URL;
             try {
                 HttpGet get = new HttpGet(resumeURl);
                 HttpResponse response = client.execute(get);
                 HttpEntity entity = response.getEntity();
                  
                 result =  EntityUtils.toString(entity, CHARSET);
            //     System.out.println(result);
                 String reg = "Resume_ID=(\\d+)";
                 Pattern p = Pattern.compile(reg);
                 Matcher m = p.matcher(result);
                 String resumeId="";
                 if(m.find()){
                	 resumeId=m.group(1);
                 }
                 String reg1 = "Ext_ID=(\\w+)\\&";
                 Pattern p1 = Pattern.compile(reg1);
                 Matcher m1 = p1.matcher(result);
                 String extId="";
                 if(m1.find()){
                	 System.out.println(m1.group(1));
                	 extId=m1.group(1);
                 }
                 //获取个人信息
                 String url1=GERENXINGXI_URL.replace("{ext_id}", extId);
                 url1=url1.replace("{resume_id}", resumeId);
                 HttpGet getGeRenXinXi=new HttpGet(url1);
                 HttpResponse resp1=client.execute(getGeRenXinXi);
                 HttpEntity geren=resp1.getEntity();
                 String result1=EntityUtils.toString(geren, CHARSET);
                
                 Document doc=Jsoup.parse(result1);
                 Element username1=doc.select("input[name=username]").first();
                 String username=username1.attr("value");
                 System.out.println(username);
            //     System.out.println(result1);
                 //获取工作经历一
                 HttpPost post1=new HttpPost(WORK_URL);
                 List<NameValuePair> workParames = new ArrayList<NameValuePair>();
                 workParames.add(new BasicNameValuePair("Language_ID", "1"));
                 workParames.add(new BasicNameValuePair("ext_id", extId));
                 workParames.add(new BasicNameValuePair("Resume_ID", resumeId));
                 workParames.add(new BasicNameValuePair("Version_Number", "1"));
                 workParames.add(new BasicNameValuePair("RowID", "1"));
                 HttpEntity he1 = new UrlEncodedFormEntity(workParames);
                 post1.setEntity(he1);
                 HttpResponse rep2=client.execute(post1);
                 HttpEntity et2=rep2.getEntity();
                 String result3=EntityUtils.toString(et2, CHARSET);
                 //可以根据值是否为空判断是否有这个工作经历
          //       System.out.println(result3);
                 
                 //获取工作经历二
                 HttpPost post2=new HttpPost(WORK_URL);
                 List<NameValuePair> workParames1 = new ArrayList<NameValuePair>();
                 workParames1.add(new BasicNameValuePair("Language_ID", "1"));
                 workParames1.add(new BasicNameValuePair("ext_id", extId));
                 workParames1.add(new BasicNameValuePair("Resume_ID", resumeId));
                 workParames1.add(new BasicNameValuePair("Version_Number", "1"));
                 workParames1.add(new BasicNameValuePair("RowID", "2"));
                 HttpEntity he2 = new UrlEncodedFormEntity(workParames1);
                 post2.setEntity(he2);
                 HttpResponse rep3=client.execute(post2);
                 HttpEntity et3=rep3.getEntity();
                 String result4=EntityUtils.toString(et3, CHARSET);
             //    System.out.println(result4);
                 
             }catch(Exception e){
             	e.printStackTrace();
             }
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
}
