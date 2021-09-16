package com.hero.forms.services.admin.templates.organization;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_SERVC_ORGANISATIONDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_SERVC_IORGANISATIONDAO{
	private static Logger log = Logger.getLogger(HERO_SERVC_ORGANISATIONDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveOrgn(String orgnData) {
		// TODO Auto-generated method stub
		
		try
		{
		HERO_SERVC_ORGANISATIONREQUEST request = (HERO_SERVC_ORGANISATIONREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(orgnData, "com.hero.forms.services.admin.templates.organization.HERO_SERVC_ORGANISATIONREQUEST");
		
		//log.info("request.getFilepath()     "+request.getFilepath());
		/*InputStream inputStream = new ByteArrayInputStream(request.getFilepath().getBytes());
		
		File imgfile = new File(request.getFilepath());
		  
		FileInputStream fin = new FileInputStream(imgfile);*/
		  
		log.info("Values are     "+request.getEmail()+"   "+request.getMobno()+"   "+request.getOrgnaddress()+"   "+request.getOrgnname()
				+"   "+request.getOrgnlogo());
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%*";
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		String newPW = builder.toString();
		
		
		/* newPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", newPW);*/
		String invUserPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", newPW);
		log.info("Reset Value is   "+invUserPW);
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ORGN_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_ORGN_ID", request.getOrgnid());
		inParamMap.put("P_ORGN_NAME", request.getOrgnname());
		inParamMap.put("P_ORGN_MOB", request.getMobno());
		inParamMap.put("P_ORGN_EMAIL", request.getEmail());
		inParamMap.put("P_ORGN_ADDRESS", request.getOrgnaddress());
		inParamMap.put("P_TRANSFER", request.getTransfer());
		inParamMap.put("P_ORGN_CONTACTPERSON", request.getOrgnaddress());
		inParamMap.put("P_ORGN_CITY", request.getOrgnaddress());
		inParamMap.put("P_ORGN_STATE", request.getOrgnaddress());
		inParamMap.put("P_ORGN_COUNTRY", request.getOrgnaddress());
		inParamMap.put("P_ORGN_LOGO", request.getOrgnlogo());
		inParamMap.put("P_ORGN_PINCODE", 0);
		inParamMap.put("P_ORGN_STARTYEAR", request.getStartyear());
		inParamMap.put("P_ORGN_ENDYEAR", request.getEndyear());
		inParamMap.put("P_ORGN_DATE", request.getStartdate());
		/*inParamMap.put("P_ORGN_LOGO", (InputStream)fin);*/
		inParamMap.put("P_USERID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn());
		inParamMap.put("P_GSTIN_NO", request.getGstinno());
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));

		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", (String)resultMap.get("out_genrate_id"));
		/*map.put("msg", (String)resultMap.get("out_result_type"));*/
		map.put("msg","Organization Registered Successfully username is superadmin password is " +newPW);
		
		log.info("map  "+map);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
			log.info("map  "+count);	
		/*inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);*/
		
		
		try
		{
			jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CLINIC_REGN");
			inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_NEW_PASSWORD", invUserPW);
			log.info("inParamMap         "+inParamMap);
			in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap2 = jdbcCALL.execute(in);
			log.info("    resultMap2   "+resultMap2);
			String messageContent = "your New Password is "+invUserPW+".";
			log.info("messageContent"+messageContent);
			int SmsStatus = HERO_ADM_SERVC_HOSURINVENTORYUTIL.sendSMS(jdbcTemplate,request.getMobno(),messageContent); 
			log.info("SmsStatus"+SmsStatus);
			/*HERO_ADM_SERVC_HOSURINVENTORYUTIL.sendSMS(String mobieno, String messageContent);*/
			 
		}
		catch(Exception e)
		{
			
		}
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
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadOrgn() {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT * FROM hero_orgn_table ORDER BY `created_at` DESC";
		@SuppressWarnings("unchecked")
		List<Object> orgnList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				
				map.put("orgnid", rs.getString("orgn_id"));
				map.put("orgnname", rs.getString("orgn_name"));
				map.put("orgnmobno", rs.getString("orgn_mobile"));
				map.put("orgnemail", rs.getString("orgn_email"));
				map.put("orgnaddress", rs.getString("orgn_address"));
				map.put("orgnstartyear", rs.getString("orgn_startyear"));
				map.put("orgnendyear", rs.getString("orgn_endyear"));
				map.put("orgndate", rs.getString("orgn_date"));
				map.put("orgnlogo", rs.getString("org_logo"));
				
				log.info("Company Suggestion List        "+map);
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((orgnList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
