package com.craftsmanasia.controller;

import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
import org.jsoup.nodes.TextNode;
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
public class ImportLiePinController {
	@Autowired
	WorkService workService;
	@Autowired
	ResumeUserService resumeUserService;
	
	private final String PARAM_USER = "user_login";
    private final String PARAM_PWD = "user_pwd";
    private final String PARAM_CHECK="CheckCode";
    private final String CHARSET = "utf8";
    private final String URL_LOGIN = "http://www.liepin.com/user/ajaxlogin/";
    private String RESUME_URL = "http://c.liepin.com/";
    private String GERENXINGXI_URL="http://c.liepin.com/resume/regresume/?res_id_encode={res_id_encode}&sfrom=c_index";
    private String WORK_URL="http://my.zhaopin.com/MYZHAOPIN/resume_experience_edit.asp";
    private String YZM="https://passport.zhaopin.com/checkcode/img";
    private String URL1="http://c.liepin.com/resume/detail?res_id_encode={res_id_encode}";
    private final static String[] hexDigits = { "0", "1", "2", "3", "4", 
    	   "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" }; 
    
    
    /*
     * 		返回的代码   0表示成功
     * 					1表示失败
     * 					2表示密码或用户名错误
     * 
     */
    
    
	@RequestMapping("/lp")
	@ResponseBody
	public String imlp(@RequestParam(value="name",defaultValue="") String name,
			@RequestParam(value="password",defaultValue="") String password,
			@RequestParam(value="userId") String userId1){
		
			int userId=Integer.parseInt(userId1);	
	        password=Password.createPassword(password);
	        System.out.println(password);
	        String status=getResume(name, password,userId);
	        Map<String,String> map=new HashMap<String,String>();
	        map.put("code", status);
	        return JSONObject.fromObject(map).toString();
	      
		
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
     
    public String getResume(String userName, String pwd,int userId){
    	 String result = "";
         HttpClient client = new DefaultHttpClient();
         client=wrapClient(client);
         String status=login(userName, pwd,"", client);
         ResumeUser user=new ResumeUser();
         if(status.indexOf("\"flag\":\"1\"")!=-1){
             HttpGet get1=new HttpGet(RESUME_URL);
             try {
     			HttpResponse respons1=client.execute(get1);
     			HttpEntity entity=respons1.getEntity();
     			result=EntityUtils.toString(entity, CHARSET);
     			String reg = "res_id_encode=(\\w+)\"";
                 Pattern p = Pattern.compile(reg);
                 Matcher m = p.matcher(result);
                 String res_id="";
                 if(m.find()){
                 	res_id=m.group(1);
                 }
                 if(get1!=null)
     				get1.abort();
                 String geren=GERENXINGXI_URL.replace("{res_id_encode}", res_id);
                 String geren1=URL1.replace("{res_id_encode}", res_id);
                 HttpGet get2=new HttpGet(geren);
                 HttpResponse respons2=client.execute(get2);
     			HttpEntity entity2=respons2.getEntity();
     			String result2=EntityUtils.toString(entity2, CHARSET);
     			if(get2!=null)
     				get2.abort();
     			 HttpGet get3=new HttpGet(geren1);
                  HttpResponse respons3=client.execute(get3);
      			HttpEntity entity3=respons3.getEntity();
      			String result3=EntityUtils.toString(entity3, CHARSET);
      			if(get3!=null)
     				get3.abort();
      			 Document document1=Jsoup.parse(result3);
      			 Elements eles=document1.select("div.card-main h3");
      			 String text=eles.get(0).text();
      			 int len=text.length();
      			 String username=text.substring(0, len-1);
      			 String sex=text.substring(len-1);
      			 user.setName(username);
      			 user.setGender(sex);
      			 Document document2=Jsoup.parse(result3);
      			 Elements eles3=document2.select("div.view-content");
      			 //个人信息
      			 if(eles3 != null){
      				 Element e=eles3.get(1);
      				 Elements ele4=e.select("li");
      				 String age=ele4.get(0).select("p").text();
      				 Calendar calendar=Calendar.getInstance();
      				 int age1=Integer.parseInt(age);
      				 age1=age1*(-1);
      				 calendar.add(Calendar.YEAR, age1);
      				 Date birth=calendar.getTime();
      				 SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd");
      				 String birthday=format.format(birth);
      				 String phone=ele4.get(2).select("p").text();
      				 String mail=ele4.get(3).select("p").text();
      				 String home=ele4.get(4).select("p").text();
      				 user.setEmail(mail);
      				 user.setHome(home);
      				 user.setTelephone(phone);
      				 user.setBirthday(birthday);
      				 user.setUserId(userId);
      			 }
      			 //工作经历
      			Elements ele5=document2.select("div.view-content");
      			System.out.println(ele5.size());
      			Element work=ele5.get(3);
      			Elements companies=work.select("div.view-company");
      			if(companies != null){
      				Element company=companies.get(0);
      				if(company!=null){
      					Elements titles=company.select("h3");
      					Elements details=company.select("div.view-company-main");
      					for(int i=0;i<titles.size();i++){
      						Work work1=new Work();
      						Element e=titles.get(i);
      						List<TextNode> list=e.textNodes();
      						work1.setCompany(list.get(1).text());
      						String years=e.select("b").text();
      						System.out.println(years);
      						String[] years1=years.split("\\s\\-");
      						String startTime=years1[0].replace(".", "");
      						String endTime="";
      						if("至今".equals(years1[1])){
      							SimpleDateFormat format=new SimpleDateFormat("yyyyMM");
      							endTime=format.format(new Date());
      						}else{
      							 endTime=years1[1].replace(".", "");
      						}
      						work1.setBeginTime(startTime);
      						work1.setEndTime(endTime);
      						Element e1=details.get(i);
      						Elements jobMains=e1.select("div.job-main h4");
      						work1.setPosition(jobMains.get(0).textNodes().get(0).text());
      						Elements items=e1.select("div.job-main ul li");
      						String department=items.get(2).text();
      						department=department.substring(5);
      						String description=items.get(5).text();
      						description=description.substring(5);
      						work1.setDepartment(department);
      						work1.setDescription(description);		
      						work1.setUserId(userId);
      						workService.add(work1);
      					}
      				}
      			}
      			resumeUserService.add(user);
     		} catch (Exception e) {
     			// TODO Auto-generated catch block
     			e.printStackTrace();
     			return "1";
     		}
         }else{
         	System.out.println("用户名或密码错误");
         	return "2";
         }
         return "0";
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
