package com.hero.services.admin.communication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Controller;

import com.hero.services.admin.util.ConnectionUtil;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

@Controller
public class HERO_ADM_SERVC_SEND_INFO extends QuartzJobBean {
	
	private static Logger log = Logger.getLogger("schdulerLogger");
	
	private static JdbcTemplate jdbcTemplate = ConnectionUtil.getSpringMYSQLDAO().getJdbcTemplate();
	
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub
		
		log.info("SCHEDULER TRIGGERED  "+new Date());
		
		// Send SMS
		
		try {
			//log.info("SMS Util called at   "+new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date())+"   "+jdbcTemplate);
			
			
			@SuppressWarnings("unchecked")
			List<Object> smsTemplatelist = jdbcTemplate.query("SELECT `ish_id`,`ish_mob_no`,`ish_sms_content` FROM `hero_sms_history` WHERE `ish_status` = 0 "
					+ " AND ((SELECT COUNT(*) FROM `hero_sms_history` WHERE `ish_status` = -1) = 0) "
					+ "AND `ish_sms_content` IS NOT NULL LIMIT 1", new RowMapper() {
			
			
						
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("ishid", rs.getString("ish_id"));
					map.put("mobno", rs.getString("ish_mob_no"));
					map.put("smscontent", rs.getString("ish_sms_content"));
					
					return map;
				}
			});
			
			
			List<Object> smsSettingsList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.smsSettingsList;
			
			log.info("smsTemplatelist       "+smsTemplatelist.size()+"   smsSettingsList   "+smsSettingsList.size());
			
			final StringBuffer stringBuffer = new StringBuffer();
			String SMSresult = "";
			
			
			if(smsTemplatelist.size() > 0 && smsSettingsList.size() > 0)
			{
				
				Iterator<Object> smsTemplateITR = smsTemplatelist.iterator();
				while(smsTemplateITR.hasNext())
				{
				
				
				Map<String, Object> templateMap = (Map<String, Object>) smsTemplateITR.next();
				String templateMessage = "",mobileno="",ishid="0";
				 
				if(templateMap != null)
				{
					ishid = (String) templateMap.get("ishid");
					templateMessage = (String) templateMap.get("smscontent");
					mobileno = (String) templateMap.get("mobno");
				}
				
				int status = -1;
				SimpleJdbcCall jdbcCALL_before = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_POS_SMS_COMMN");
				Map<String, Object> inParamMap_before = new HashMap<String, Object>();
				inParamMap_before.put("P_ISH_ID", ishid);
				inParamMap_before.put("P_ISH_TXN_ID", 0);
				inParamMap_before.put("P_ISH_CUST_ID", 0);
				inParamMap_before.put("P_ISH_SMS_TEMPLATE_ID", 1);
				inParamMap_before.put("P_ISH_STATUS", status);
				inParamMap_before.put("P_SMS_RESPONSE", SMSresult);
				inParamMap_before.put("P_CRETAED_BY", 0);
				inParamMap_before.put("P_ACTION", "UPD_SMS_STATUS");
				
				log.info("sendSMSNotification inParamMap_before         "+inParamMap_before);
				
				SqlParameterSource in_before = new MapSqlParameterSource(inParamMap_before);
				Map<String, Object> resultMap_before = jdbcCALL_before.execute(in_before);
				
				
				Map<String, Object> settingsMap = (Map<String, Object>) smsSettingsList.get(0);
				
				String apiKey = "apikey=" + settingsMap.get("apikey");
				String message = "&message=" + templateMessage;
				String sender = "&sender=" + "TXTLCL";
				String numbers = "&numbers=" + "91"+mobileno;
				String username = "&username="+settingsMap.get("apiusername");
				String password = "&password="+settingsMap.get("apipassword");
				
				log.info("SMS Settings Values are   "+apiKey+"   "+message+"   "+numbers+"   "+username+"   "+password);
				
				
				HttpURLConnection conn = (HttpURLConnection) new URL("https://api.textlocal.in/send/?").openConnection();
				String data = apiKey + numbers + message + sender+username+password;
				conn.setDoOutput(true);
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Length", Integer.toString(data.length()));
				conn.getOutputStream().write(data.getBytes("UTF-8"));
				final BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				
				String line;
				while ((line = rd.readLine()) != null) {
					stringBuffer.append(line);
				}
				rd.close();
				
				
				
				SMSresult = stringBuffer.toString();
				
				/*String SMSresult = "{\"balance\":975,\"batch_id\":300536696,\"cost\":1,\"num_messages\":1,\"message\":{\"num_parts\":1,\"sender\":\"TXTLCL\","
						+ "\"content\":\"Dear Customer 1 Thanks for choosing us. Your Invoice no is INV-000003Dear Customer, Thanks for Choosing HerbzaLive. Keep in touch with "
						+ "us. Health is Wealth. :)\"},\"receipt_url\":\"\",\"custom\":\"\",\"messages\":[{\"id\":\"1195745367\",\"recipient\":918825996718}],"
						+ "\"status\":\"success\"}";*/
				JSONObject jsonObj = new JSONObject(SMSresult);
				
				log.info(jsonObj.get("status"));
				
				
				if(jsonObj.get("status") != null && ((String)jsonObj.get("status")).equalsIgnoreCase("success"))
				{
					status = 1;
				}
				
				SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_POS_SMS_COMMN");
				Map<String, Object> inParamMap = new HashMap<String, Object>();
				inParamMap.put("P_ISH_ID", ishid);
				inParamMap.put("P_ISH_TXN_ID", 0);
				inParamMap.put("P_ISH_CUST_ID", 0);
				inParamMap.put("P_ISH_SMS_TEMPLATE_ID", 1);
				inParamMap.put("P_ISH_STATUS", status);
				inParamMap.put("P_SMS_RESPONSE", SMSresult);
				inParamMap.put("P_CRETAED_BY", 0);
				inParamMap.put("P_ACTION", "UPD_SMS_STATUS");
				
				log.info("sendSMSNotification inParamMap         "+inParamMap);
				SqlParameterSource in = new MapSqlParameterSource(inParamMap);
				Map<String, Object> resultMap = jdbcCALL.execute(in);
				log.info(resultMap);
				
				}
			}

			//log.info("Result   "+SMSresult);
			
			//return SMSresult;
		} catch (Exception e) {
			log.info("Error SMS "+e);
			//e.printStackTrace();
			//return "Error "+e;
		}
		
		
		
		//Send MAIL
		
		try{
			  
			@SuppressWarnings("unchecked")
			List<Object> emailTemplatelist = jdbcTemplate.query("SELECT `imh_id`,`imh_email_id`,`imh_email_subject`,`imh_email_content` FROM `hero_email_history`    "
					+ "WHERE `imh_staus` = 0 AND ((SELECT COUNT(*) FROM `hero_email_history` WHERE `imh_staus` = -1) = 0) "
					+ " AND `imh_email_content` IS NOT NULL  ", new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("imhid", rs.getString("imh_id"));
					map.put("emailid", rs.getString("imh_email_id"));
					map.put("emailsubject", rs.getString("imh_email_subject"));
					map.put("emailcontent", rs.getString("imh_email_content"));
					
					return map;
				}
			});
			
			List<Object> emailSettingsList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.emailSettingsList;
			
			
			final StringBuffer stringBuffer = new StringBuffer();
			String EMAILresult = "";
			log.info("emailTemplatelist "+emailTemplatelist.size()+"  emailSettingsList  "+emailSettingsList.size());
			if(emailTemplatelist.size() > 0 && emailSettingsList.size() > 0)
			{
				int status = 0;
				log.info("After List");
				Iterator<Object> emailTemplateITR = emailTemplatelist.iterator();
				while(emailTemplateITR.hasNext())
				{
				
				Map<String, Object> templateMap = (Map<String, Object>) emailTemplateITR.next();
				String templateMessage = "",emailid="",imhid="0",templateSubject="";
				 
				if(templateMap != null)
				{
					imhid = (String) templateMap.get("imhid");
					templateSubject = (String) templateMap.get("emailsubject");
					templateMessage = (String) templateMap.get("emailcontent");
					emailid = (String) templateMap.get("emailid");
				}
				
				status = -1;
				SimpleJdbcCall jdbcCALL_before = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_INSERT_EMAIL_HISTORY");
				Map<String, Object> inParamMap_before = new HashMap<String, Object>();
				inParamMap_before.put("P_IMH_ID", imhid);
				inParamMap_before.put("P_IMH_MAIL_ID", "");
				inParamMap_before.put("P_IMH_MAIL_SUBJECT", "");
				inParamMap_before.put("P_IMH_MAIL_CONTENT", "");
				inParamMap_before.put("P_IMH_STATUS", status);
				inParamMap_before.put("P_IMH_RESPONSE", EMAILresult);
				inParamMap_before.put("P_ACTION", "UPD_EMAIL_STATUS");
				
				SqlParameterSource in_before = new MapSqlParameterSource(inParamMap_before);
				Map<String, Object> resultMap_before = jdbcCALL_before.execute(in_before);
				
				log.info("resultMap_before   "+inParamMap_before+resultMap_before);
				
				Map<String, Object> settingsMap = (Map<String, Object>) emailSettingsList.get(0);
				
				final String from_mail = (String) settingsMap.get("emailid");
				final String from_mail_password = (String) settingsMap.get("emailpassword");
				
				log.info("Email Settings Values are   "+from_mail+"   "+from_mail_password +"  "+emailid);
				
				 //Get properties object    
			      Properties props = new Properties();    
			      props.put("mail.smtp.host", "smtp.gmail.com");    
			      props.put("mail.smtp.socketFactory.port", "465");    
			      props.put("mail.smtp.socketFactory.class",    
			                "javax.net.ssl.SSLSocketFactory");    
			      props.put("mail.smtp.auth", "true");    
			      props.put("mail.smtp.port", "465");  
			      //get Session   
			      Session session = Session.getDefaultInstance(props,    
			       new javax.mail.Authenticator() {    
			       protected  PasswordAuthentication getPasswordAuthentication() {    
			       return new PasswordAuthentication(from_mail,from_mail_password);  
			       }    
			      });    
			      //compose message   
			      
			      try {    
			       MimeMessage message = new MimeMessage(session);    
			       message.addRecipient(Message.RecipientType.TO,new InternetAddress(emailid));    
			       message.setSubject(templateSubject);    
			       //message.setText(msg);   
			       message.setContent(templateMessage, "text/html; charset=utf-8");
			       //send message  
			       Transport.send(message);    
			       status = 1; 
			      } catch (MessagingException e) {
			    	  status = 0;
			      }  
				log.info("status"+status);
				
				
				SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_INSERT_EMAIL_HISTORY");
				Map<String, Object> inParamMap = new HashMap<String, Object>();
				inParamMap.put("P_IMH_ID", imhid);
				inParamMap.put("P_IMH_MAIL_ID", "");
				inParamMap.put("P_IMH_MAIL_SUBJECT", "");
				inParamMap.put("P_IMH_MAIL_CONTENT", "");
				inParamMap.put("P_IMH_STATUS", status);
				inParamMap.put("P_IMH_RESPONSE", EMAILresult);
				inParamMap.put("P_ACTION", "UPD_EMAIL_STATUS");
				
				log.info("EMINotification inParamMap         "+inParamMap);
				SqlParameterSource in = new MapSqlParameterSource(inParamMap);
				Map<String, Object> resultMap = jdbcCALL.execute(in);
				log.info(resultMap);
				
				}
			}
			//return EMAILresult;
			
		}catch (Exception e) {
			log.info("Error MAIL "+e);
			//e.printStackTrace();
			//return "Error "+e;
		}
		
	}

}
