package com.craftsmanasia.controller;

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

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.craftsmanasia.model.ResumeUser;
import com.craftsmanasia.model.User;
import com.craftsmanasia.model.Work;
import com.craftsmanasia.service.ResumeUserService;
import com.craftsmanasia.service.UserService;
import com.craftsmanasia.service.WorkService;
import com.ebaoyang.utils.JsonUtil;

@Controller
@RequestMapping("/resume/import")
public class Import51JobResumeController {
	@Autowired
	ResumeUserService resumeUserService;
	@Autowired
	WorkService workService;
	
	private final String PARAM_USER = "username";
    private final String PARAM_PWD = "userpwd";
    private final String CHARSET = "gbk";
    private final String URL_LOGIN = "https://mylogin.51job.com/70718817841788820564/my/My_Pmc.php";
    private String RESUME_URL = "http://my.51job.com/cv/CResume/{id}/CV_CResumeManage.php";
    private String GET_RESUME_URL="http://my.51job.com/sc/applyjob/preview_resume.php?ReSumeID={resumeId}&AccountID={accountId}";
    private String EDIT_RESUME_URL="http://my.51job.com/cv/CResume/CV_CModDefault.php?ReSumeID={resumeId}";
    private String YanZhengMaURL="http://my.51job.com/my/My_ShowKey.php";
	/*
	 * 返回状态：0：简历导入成功
	 * 			1：简历导入失败
	 * 			2：用户名或密码错误
	 */
	@RequestMapping("/51job")
	@ResponseBody
	public String import51Job(HttpServletRequest request,
								@RequestParam(value="name",defaultValue="Golphing") String name,
								@RequestParam(value="password",defaultValue="wanggaoping0306") String password
								){
		HttpClient client = new DefaultHttpClient();
        String id = login(name, password, client);
        if("".equals(id))
        	return JsonUtil.getJson(2, "用户名或密码错误").toString();
        String resumeURl = RESUME_URL.replace("{id}", id);
        try {
            HttpGet get = new HttpGet(resumeURl);
            HttpResponse response = client.execute(get);
            HttpEntity entity = response.getEntity();
            String result="";
            result =  EntityUtils.toString(entity, CHARSET);
            String reg = "\\?ReSumeID=(\\d+)";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(result);
            String resumeId="";
            if(m.find()){
            	resumeId=m.group(1);
  //              System.out.println(m.group(1));
            }
     //       System.out.println(id);
            String getUrl=EDIT_RESUME_URL.replace("{resumeId}", resumeId);
          
       //     System.out.println(getUrl);
            get.abort();
            HttpGet get1=new HttpGet(getUrl);
            get1.setHeader("Referer", resumeURl);
            HttpResponse response1=client.execute(get1);
            HttpEntity enti=response1.getEntity();
            String res=EntityUtils.toString(enti,CHARSET);
            int statusCode=response1.getStatusLine().getStatusCode();
            if(statusCode==200){
            	Document document=Jsoup.parse(res);
            	//个人信息保存
            	Elements eles=document.select("div#BPI");
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
                			String reg1 = "(\\d+)";
                            Pattern p1 = Pattern.compile(reg1);
                            Matcher m1 = p1.matcher(value);
                            if(m1.find()){
                            	 String s=m1.group(1);    
                            	 user.setTelephone(s);
                     //            peopleInfo.put(item, s);
                            }
                		}
                		else if("证件号：".equals(item)){
                			String reg1 = "(\\d+)";
                            Pattern p1 = Pattern.compile(reg1);
                            Matcher m1 = p1.matcher(value);
                            if(m1.find()){
                            	 String s=m1.group(1);      
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
                			user.setBirthday(value);
                		}
                		else{
                //			peopleInfo.put(item, value);
                		}
            		
            	}
            	user.setUserId(0);
            	
            	
            	//工作经验   [   部门  职位  工作内容]
            	Elements eles1=document.select("div#WORK");
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
            				times[1]=times[1].substring(0, 3)+"0"+times[1].substring(5, 5);
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
            			Elements d2=department.select("dd");
            			work.setPosition(d2.text());
            			//工作内容
            			Element content=eles11.get(3);
            			Elements d3=department.select("dd");
            			work.setPosition(d3.text());
            			workService.add(work);
            		}
            	}
            	resumeUserService.add(user);
            }else{
            	System.out.println("error");
            	return JsonUtil.getJson(1, "导入失败").toString();
            }
            get1.abort();
            
        } catch (Exception e) {
            e.printStackTrace();
            return JsonUtil.getJson(1, "导入失败").toString();
        } 
         	
        
        
        return JsonUtil.getJson(0, "导入成功").toString();
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
             
            int status = response.getStatusLine().getStatusCode();
            if (StringUtil1.isNull(result)
                    && ((status == HttpStatus.SC_MOVED_TEMPORARILY)
                    || (status == HttpStatus.SC_MOVED_PERMANENTLY)
                        || (status == HttpStatus.SC_SEE_OTHER) || 
                    (status == HttpStatus.SC_TEMPORARY_REDIRECT))) {
                Header header = response.getFirstHeader("location");
                if (header != null) {
                    result = header.getValue();
                }
            }
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
     
    public String login(String user, String password, HttpClient client) {
        try {
            List<NameValuePair> loginParames = new ArrayList<NameValuePair>();
             
            loginParames.add(new BasicNameValuePair(PARAM_USER, user));
            loginParames.add(new BasicNameValuePair(PARAM_PWD, password));
             
            String s = login(client, loginParames);
             
            String reg = "my.51job.com/my/(.+?)/My_Pmc.php";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(s);
            if(m.find()){
                return m.group(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
        return "";
    }
}

class StringUtil1{
	public static boolean isNull(Object obj) {
		boolean flag = false;
		if (null == obj || "".equals(obj))
			flag = true;
		return flag;

	}
}
