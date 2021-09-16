package com.hero.forms.services.admin.templates.sms;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_SERVC_SMSTEMPLATESERVICEDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_SERVC_ISMSTEMPLATEDAO{
	private static Logger log = Logger.getLogger(HERO_SERVC_SMSTEMPLATESERVICEDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadsmssettings() {
		// TODO Auto-generated method stub
		
		String selectQuery = "select * from hero_sms_settings";
		@SuppressWarnings("unchecked")
		List<Object> smsSettingsist = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("apikey", rs.getString("api_key"));
				map.put("apiusername", rs.getString("api_username"));
				map.put("apipassword", rs.getString("api_password"));
				map.put("smssend", rs.getString("inv_sms_send"));
				
				return map;
			}
		});
		String selectemailQuery = "select * from hero_email_settings";
		@SuppressWarnings("unchecked")
		List<Object> emailSettingsist = jdbcTemplate.query(selectemailQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("emailid", rs.getString("email_id"));
				map.put("emailpassword", rs.getString("email_password"));
				
				return map;
			}
		});
		Map<String,Object> listMap = new HashMap<String, Object>();
		log.info("smssettingList      "+smsSettingsist);
		log.info("emailsettingList        "+emailSettingsist);
	
		listMap.put("smssettingList", smsSettingsist);
	
		listMap.put("emailsettingList", emailSettingsist);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(listMap);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getSMSContent(String templateid) {
		// TODO Auto-generated method stub
		
		String selectQuery = "select * from hero_sms_template where sms_temp_id = "+templateid;
		@SuppressWarnings("unchecked")
		List<Object> smsContentist = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("smstempid", rs.getString("sms_temp_id"));
				map.put("smstempname", rs.getString("sms_temp_name"));
				map.put("smsmsgcontent", rs.getString("sms_message_content"));
				map.put("paramdesc", rs.getString("param_desc"));
				
				return map;
			}
		});
		
		log.info("smsContentist        "+smsContentist);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(smsContentist);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveSMSContent(String smscontent) {
		// TODO Auto-generated method stub
		
		try
		{
		HERO_SERVC_SMSTEMPLATEREQUEST request = (HERO_SERVC_SMSTEMPLATEREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(smscontent, "com.hero.forms.services.admin.templates.sms.HERO_SERVC_SMSTEMPLATEREQUEST");
		
		log.info("request.getFilepath()     "+request.getMessagecontent());
		
		log.info("Values are     "+request.getMessagecontent());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_SMS_TEMPLATE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_MSG_ID", request.getTemplateid());
		inParamMap.put("P_MSG_CONTENT", request.getMessagecontent());
		inParamMap.put("P_APIKEY", request.getApikey());
		inParamMap.put("P_USERNAME", request.getUsername());
		inParamMap.put("P_PASSWORD", request.getPassword());
		inParamMap.put("P_SMS_SEND", request.getSmssend());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailsettings(String emailcontent) {
		// TODO Auto-generated method stub
		
		try
		{
		HERO_SERVC_SMSTEMPLATEREQUEST request = (HERO_SERVC_SMSTEMPLATEREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(emailcontent, "com.hero.forms.services.admin.templates.sms.HERO_SERVC_SMSTEMPLATEREQUEST");
		
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_EMAIL_TEMPLATE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_EMAIL_ID", request.getEmailid());
		inParamMap.put("P_EMAIL_PASSWORD", request.getEmailpassword());
		inParamMap.put("P_EMAIL_TEMP_ID", request.getTemplateid());
		inParamMap.put("P_EMAIL_TEMP_SUBJECT", request.getEmailid());
		inParamMap.put("P_EMAIL_TEMP_CONTENT", request.getEmailid());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailcontent(String emailcontent) {
		// TODO Auto-generated method stub
		
		try
		{
		HERO_SERVC_SMSTEMPLATEREQUEST request = (HERO_SERVC_SMSTEMPLATEREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(emailcontent, "com.hero.forms.services.admin.templates.sms.HERO_SERVC_SMSTEMPLATEREQUEST");
		
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_EMAIL_TEMPLATE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_EMAIL_ID", request.getEmailid());
		inParamMap.put("P_EMAIL_PASSWORD", request.getEmailid());
		inParamMap.put("P_EMAIL_TEMP_ID", request.getTemplateid());
		inParamMap.put("P_EMAIL_TEMP_SUBJECT", request.getEmailid());
		inParamMap.put("P_EMAIL_TEMP_CONTENT", request.getEmailid());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
}
