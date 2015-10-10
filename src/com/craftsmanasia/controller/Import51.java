package com.craftsmanasia.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;


@Controller
@RequestMapping("/c/im")
public class Import51 {
	@Autowired
	WorkService workService;
	@Autowired
	ResumeUserService resumeUserService;
	@Autowired
	UserService userService;
	private final static String PARAM_USER = "username";
    private final static String PARAM_PWD = "userpwd";
    private final static String CHARSET = "gbk";
    
    private String RESUME_URL = "http://my.51job.com/cv/CResume/{id}/CV_CResumeManage.php";
    private String GET_RESUME_URL="http://my.51job.com/sc/applyjob/preview_resume.php?ReSumeID={resumeId}&AccountID={accountId}";
    private String EDIT_RESUME_URL="http://my.51job.com/cv/CResume/CV_CModDefault.php?ReSumeID={resumeId}";
    private String YanZhengMaURL="http://my.51job.com/my/My_ShowKey.php";
    private static String url1="http://my.51job.com/my/My_SignIn.php";
   
    /*
     * 		返回类型：0   成功
     * 				  1  需要验证码
     * 					2 账号密码错误
     * 					3 验证码错误
     * 					4 用户名或密码不能为空
     * 					5 没绑定手机号
     					6 需验证的用户名和密码错误
    */
	@RequestMapping("/51")
	@ResponseBody
	public String imp(HttpSession session,
			HttpServletRequest request,
			@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password,
			@RequestParam(value="login_verify",defaultValue="") String login_verify,
			@RequestParam(value="userId") String userId1){
		
		System.out.println(name);
		System.out.println(password);
		System.out.println(login_verify);
		System.out.println(userId1);
		Map<String,String> map=new HashMap<String,String>();
		String getUrl="";		
		int userId=Integer.parseInt(userId1);		
		map.put("name", name);
    	map.put("password", password);
		if("".equals(name) | "".equals(password)){
			map.put("code", "4");
			return JSONObject.fromObject(map).toString();
		}
		HttpClient client=(HttpClient) session.getAttribute("client");
		
		if(client==null){			
			client= new DefaultHttpClient();
			session.setAttribute("client", client);
		}
		System.out.println(client);
      
        try {
        	if("".equals(login_verify)){
        		System.out.println("没有验证码  no verigy-------------");
        		HttpGet get = new HttpGet("http://my.51job.com/my/My_SignIn.php");
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();                
                String result =  EntityUtils.toString(entity, CHARSET);
              //  System.out.println(result);
                Document document=Jsoup.parse(result);
                Elements form=document.select("form");
                Element f=form.get(0);
                String url=f.attr("action");
                System.out.println(url);
                if(get !=null)
                	get.abort();
                HttpPost login=new HttpPost(url);
                List<NameValuePair> loginParames = new ArrayList<NameValuePair>();              
                loginParames.add(new BasicNameValuePair(PARAM_USER, name));
                loginParames.add(new BasicNameValuePair(PARAM_PWD, password));
                loginParames.add(new BasicNameValuePair("x", "34"));
                loginParames.add(new BasicNameValuePair("login_verify", ""));
                loginParames.add(new BasicNameValuePair("url", ""));
                loginParames.add(new BasicNameValuePair("y", "21"));
                HttpEntity he = new UrlEncodedFormEntity(loginParames);
                login.setEntity(he);
                login.setHeader("Referer", "http://my.51job.com/my/My_SignIn.php");
                login.setHeader("Host", "mylogin.51job.com");
                login.setHeader("Accept", "text/html, application/xhtml+xml, */*");               	
                login.setHeader("Accept-Language", "zh-CN");               	
                login.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");               	
                login.setHeader("Content-Type", "application/x-www-form-urlencoded");                
                login.setHeader("Accept-Encoding", "gzip, deflate");                
                login.setHeader("Connection", "Keep-Alive");             
                login.setHeader("Cache-Control", "no-cache");
                login.setHeader("DNT", "1");
                HttpResponse response1 = client.execute(login);
                HttpEntity entity1 = response1.getEntity();               
                result = EntityUtils.toString(entity1, CHARSET);
                int status = response1.getStatusLine().getStatusCode();
                System.out.println(result);
                System.out.println(status);
                Header[] heads=response1.getAllHeaders();
                for(Header h:heads){
               	 System.out.println(h.getName()+": "+h.getValue());
                }
                String redi=response1.getFirstHeader("location").getValue();
                if(login!=null)
                	login.abort();
                if(redi.indexOf("errmsg=needVerifyCode")!=-1 | redi.indexOf("errmsg=verifyCodeErr")!=-1){
                	System.out.println("需要验证need verigy");
                	HttpGet get2 = new HttpGet(redi);
                    HttpResponse response2 = client.execute(get2);
                    HttpEntity entity2 = response2.getEntity();    
                    String result2 =  EntityUtils.toString(entity2, CHARSET);
                    System.out.println(result2);
                    if(get2!=null)
                    	get2.abort();
                    Document document2=Jsoup.parse(result2);
                    Elements form2=document.select("img#verifyPic_img");
                    Element f2=form2.get(0);
                    String url2=f2.attr("src");
                    System.out.println("yanzhengma src:"+url2);
                    String path="/yzm/"+System.currentTimeMillis() + ".png";
    				String path1=request.getSession().getServletContext().getRealPath(path);
    				HttpGet httpGet=new HttpGet(url2);
    				HttpResponse resp = client.execute(httpGet);
    				InputStream in=resp.getEntity().getContent();
    	        	File file=new File(path1);
    	        	FileOutputStream fos=new FileOutputStream(file);
    	        	byte[] buf=new byte[1024];
    	        	int len=0;
    	        	while((len=in.read(buf)) != -1){
    	        		fos.write(buf, 0, len);
    	        	}
    	        	fos.close();
    	        	in.close();
    	        	System.out.println("验证码保存在/yzm/下面"+path);
    	        	String yzmPath="/craftsman_weixin"+path;
    	        	map.put("code", "1");
    	        	map.put("yzmUrl", yzmPath);   	
    	        	if(httpGet !=null)
    	        		httpGet.abort();
    	        	return JSONObject.fromObject(map).toString();
                }else if(redi.indexOf("errmsg=UserNameOrPwdError")!=-1){
                	map.put("code", "2");
                	return JSONObject.fromObject(map).toString();
                }else{
                	 String reg = "my.51job.com/my/(.+?)/My_Pmc.php";
                     Pattern p = Pattern.compile(reg);
                     Matcher m = p.matcher(redi);
                     String rid="";
                     if(m.find()){
                         rid= m.group(1);
                     }else{
                     	return "";
                     }
                     String resumeURl = RESUME_URL.replace("{id}", rid);
                     HttpGet get2 = new HttpGet(resumeURl);
                     HttpResponse response2 = client.execute(get2);
                     HttpEntity entity2 = response2.getEntity();    
                     String result2 =  EntityUtils.toString(entity2, CHARSET);
                    // System.out.println(result2);
                     String reg1 = "\\?ReSumeID=(\\d+)";
                     Pattern p1 = Pattern.compile(reg1);
                     Matcher m1 = p1.matcher(result2);
                     String resumeId="";
                     if(m1.find()){
                     	resumeId=m1.group(1);
                     }
                      getUrl=EDIT_RESUME_URL.replace("{resumeId}", resumeId);
                     get2.abort();
                }
        	}else{
        		System.out.println("要验证码的verifying -------");
        		HttpGet get = new HttpGet("http://my.51job.com/my/My_SignIn.php");
                HttpResponse response = client.execute(get);
                HttpEntity entity = response.getEntity();
                 
                String result =  EntityUtils.toString(entity, CHARSET);
                if(get != null)
                	get.abort();
              //  System.out.println(result);
                Document document=Jsoup.parse(result);
                Elements form=document.select("form");
                Element f=form.get(0);
                String url=f.attr("action");
                System.out.println(url);
                HttpPost login=new HttpPost(url);
                List<NameValuePair> loginParames = new ArrayList<NameValuePair>();
                
                loginParames.add(new BasicNameValuePair(PARAM_USER, name));
                loginParames.add(new BasicNameValuePair(PARAM_PWD, password));
                loginParames.add(new BasicNameValuePair("x", "34"));
                loginParames.add(new BasicNameValuePair("login_verify", login_verify));
                loginParames.add(new BasicNameValuePair("url", ""));
                loginParames.add(new BasicNameValuePair("y", "21"));
                HttpEntity he = new UrlEncodedFormEntity(loginParames);
                login.setEntity(he);
                login.setHeader("Referer", "http://my.51job.com/my/My_SignIn.php?url=&errmsg=needVerifyCode");
                login.setHeader("Host", "mylogin.51job.com");
                login.setHeader("Accept", "text/html, application/xhtml+xml, */*");
                login.setHeader("Accept-Language", "zh-CN");	
                login.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; Trident/7.0; rv:11.0) like Gecko");
                login.setHeader("Content-Type", "application/x-www-form-urlencoded");               
                login.setHeader("Accept-Encoding", "gzip, deflate");               
                login.setHeader("Connection", "Keep-Alive");              
                login.setHeader("Cache-Control", "no-cache");              
                login.setHeader("DNT", "1");              	
                HttpResponse response1 = client.execute(login);
                HttpEntity entity1 = response1.getEntity();               
                result = EntityUtils.toString(entity1, CHARSET);
                 
                int status = response1.getStatusLine().getStatusCode();
                System.out.println(result);
                System.out.println(status);
                Header[] heads=response1.getAllHeaders();
                for(Header h:heads){
               	 System.out.println(h.getName()+": "+h.getValue());
                }
                String resume=response1.getFirstHeader("location").getValue();
                if(login !=null)
                	login.abort();
                if(resume.indexOf("errmsg=")!=-1){
                	if(resume.indexOf("errmsg=verifyCodeErr")!=-1){
                		System.out.println("验证码错误");
                		
        	        	map.put("code", "3");
        	        	
                	}else{
                		System.out.println("用户名或密码错误");
                		map.put("code", "6");
                    	
                	}
                	HttpGet get2 = new HttpGet(resume);
                    HttpResponse response2 = client.execute(get2);
                    HttpEntity entity2 = response2.getEntity();    
                    String result2 =  EntityUtils.toString(entity2, CHARSET);
                    System.out.println(result2);
                    Document document2=Jsoup.parse(result2);
                    Elements form2=document.select("img#verifyPic_img");
                    Element f2=form2.get(0);
                    String url2=f2.attr("src");
                    System.out.println("yanzhengma src:"+url2);
                    String path="/yzm/"+System.currentTimeMillis() + ".png";
    				String path1=request.getSession().getServletContext().getRealPath(path);
    				if(get2!=null)
    					get2.abort();
    				HttpGet httpGet=new HttpGet(url2);
    				HttpResponse resp = client.execute(httpGet);
    				InputStream in=resp.getEntity().getContent();
    	        	File file=new File(path1);
    	        	FileOutputStream fos=new FileOutputStream(file);
    	        	byte[] buf=new byte[1024];
    	        	int len=0;
    	        	while((len=in.read(buf)) != -1){
    	        		fos.write(buf, 0, len);
    	        	}
    	        	fos.close();
    	        	in.close();
    	        	System.out.println("验证码保存在/yzm/下面"+path);
    	        	String yzmPath="/craftsman_weixin"+path;
    	        	map.put("yzmUrl", yzmPath);
    	        	if(httpGet !=null)
    	        		httpGet.abort();
    	        	return JSONObject.fromObject(map).toString();
                }
                System.out.println(resume);
                String reg = "my.51job.com/my/(.+?)/My_Pmc.php";
                Pattern p = Pattern.compile(reg);
                Matcher m = p.matcher(resume);
                String rid="";
                if(m.find()){
                    rid= m.group(1);
                }else{
                	System.out.println("验证码错误");
                	return "";
                }
                String resumeURl = RESUME_URL.replace("{id}", rid);
                HttpGet get2 = new HttpGet(resumeURl);
                HttpResponse response2 = client.execute(get2);
                HttpEntity entity2 = response2.getEntity();    
                String result2 =  EntityUtils.toString(entity2, CHARSET);
               // System.out.println(result2);
                String reg1 = "\\?ReSumeID=(\\d+)";
                Pattern p1 = Pattern.compile(reg1);
                Matcher m1 = p1.matcher(result2);
                String resumeId="";
                if(m1.find()){
                	resumeId=m1.group(1);
                }
                 getUrl=EDIT_RESUME_URL.replace("{resumeId}", resumeId);
                 if(get2 !=null)
                	 get2.abort();
                
            
        	}
        	
        	//登录成功后的导入简历操作操作
				HttpGet get1=new HttpGet(getUrl);
              //  get1.setHeader("Referer", resumeURl);
                HttpResponse response3=client.execute(get1);
                HttpEntity enti=response3.getEntity();
                String res=EntityUtils.toString(enti,CHARSET);
                if(get1 !=null)
                	get1.abort();
                System.out.println(res);
                Document document1=Jsoup.parse(res);
            	//个人信息保存
            	Elements eles=document1.select("div#BPI");
   //         	System.out.println(eles.html());
            	Elements dls=eles.select("dl");
            //	Map<String,String> peopleInfo=new HashMap<String, String>();
            	ResumeUser user=new ResumeUser();
            	for(Element dl:dls){
            		Elements es=dl.getElementsByTag("dt");
            		String item=es.text();
            		
            			Elements es1=dl.getElementsByTag("dd");
                		String value=es1.text();
                		if("姓名：".equals(item)){
                			String[] v=value.split(" ");
                			user.setName(v[0]);
                			user.setGender(v[1]);
                	//		peopleInfo.put(item, v[0]);
                	//		peopleInfo.put("性别", v[1]);
                		}else if("手机号码：".equals(item)){
                			String reg2 = "(\\d+)";
                            Pattern p2 = Pattern.compile(reg2);
                            Matcher m2 = p2.matcher(value);
                            if(m2.find()){
                            	 String s=m2.group(1);    
                            	 user.setTelephone(s);
                     //            peopleInfo.put(item, s);
                            }
                		}
                		else if("证件号：".equals(item)){
                			String reg2 = "(\\d+)";
                            Pattern p2 = Pattern.compile(reg2);
                            Matcher m2 = p2.matcher(value);
                            if(m2.find()){
                            	 String s=m2.group(1);      
                            	// user.set
                      //           peopleInfo.put(item, s);
                            }
                		}
                		else if("Email：".equals(item)){
                			user.setEmail(value);
                		}
                		else if("居住地：".equals(item)){
                			user.setHome(value);
                		}
                		else if("出生日期：".equals(item)){
                			if(value !="" && value !=null){
                				String[] ymd=value.split("-");
                				if(ymd[1].length()<2){
                					value=ymd[0]+"-0"+ymd[1]+"-"+ymd[2];
                				}
                			}
                			user.setBirthday(value);
                			
                		}
                		else{
                //			peopleInfo.put(item, value);
                		}
            		
            	}
            	user.setUserId(userId);
            	
            	
            	//工作经验   [   部门  职位  工作内容]
            	Elements eles1=document1.select("div#WORK");
            	Elements items=eles1.select("div.studyUnit");
            	
            	if(items.size()==0){
            		System.out.println("未填写工作经验");
            	}else{
            		for(Element e:items){
            			Work work=new Work();
            			Elements detail=e.select("div.studyText");
            			Elements title=detail.select("div.studyTxet_title");
            			String company=title.text();
            			String[] msgs=company.split("\\s");
            			String[] times=msgs[0].split("\\-");
            			System.out.println(msgs[0]);
            			if(times[0].length()<7){
            				times[0]=times[0].substring(0, 4)+"0"+times[0].substring(5, 6);
            				
            			}else{
            				times[0]=times[0].substring(0, 4)+times[0].substring(5, 7);
            			}
            			
            			if("至今".equals(times[1])){
            				DateFormat format=new SimpleDateFormat("yyyyMM");
            				times[1]=format.format(new Date());
            			}else if(times[1].length()<7){
            				times[1]=times[1].substring(0, 4)+"0"+times[1].substring(5, 6);
            			}else{
            				times[1]=times[1].substring(0, 4)+times[1].substring(5, 7);
            			}
            			work.setBeginTime(times[0]);
            			work.setEndTime(times[1]);
            			work.setCompany(msgs[1]);
            			//部门  职位  工作内容
            			Elements eles11=detail.select("dl.lineDl");
            			//部门
            			Element department=eles11.get(1);
            			Elements d1=department.select("dd");
            			work.setDepartment(d1.text());
            			//职位
            			Element position=eles11.get(0);
            			Elements d2=position.select("dd");
            			work.setPosition(d2.text());
            			//工作内容
            			Element content=eles11.get(3);
            			Elements d3=content.select("dd");
            			work.setDescription(d3.text());
            			work.setUserId(userId);
            			workService.add(work);
            		}
            	}
            	resumeUserService.add(user);
            
           System.out.println("end");
           map.put("code", "0");
           return JSONObject.fromObject(map).toString();
            
        }catch(Exception e){
        	e.printStackTrace();
        	map.put("code", "1");
            return JSONObject.fromObject(map).toString();
        	
        }
		
	}
	
	@RequestMapping("/one")
	public String link1(HttpSession session){
		session.setAttribute("name", "name");
		session.setAttribute("test", "test");
		return "/resume_import/two";
	}
	
	@RequestMapping("/two")
	public String link2(HttpSession session,Model model){
		String name=(String) session.getAttribute("name");
		String test=(String) session.getAttribute("test");
		model.addAttribute("name", name);
		model.addAttribute("test", test);
		return "/resume_import/one";
	}
}
