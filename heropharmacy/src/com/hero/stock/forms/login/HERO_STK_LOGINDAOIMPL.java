package com.hero.stock.forms.login;

import java.net.URL;
import java.nio.charset.Charset;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.servlet.ModelAndView;

import com.hero.heroutils.HERO_TOKEN_UTIL;
import com.hero.heroutils.HERO_UTILS;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.sso.HeroUserDetail;
import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYLOV;


public class HERO_STK_LOGINDAOIMPL implements HERO_STK_ILOGINDAO {

	private static Logger log = Logger.getLogger(HERO_STK_LOGINDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_STK_RESPONSE response;
	@Autowired
	private HERO_STK_RESPONSEINFO responseInfo;
	@Autowired
	private HEROHOSURINVENTORYUTIL inventoryUtilOBJ;
	@Autowired
	private HERO_STK_LOGINREQUEST loginObj;
	
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	HttpSession session = null; 
	
	
	@Override
	public HERO_STK_RESPONSEINFO validLogin(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
		
	String tokenkey = "";
	session = httpRequest.getSession();
	JSONObject responseJSON;
	try {
		/*URL url = new URL("http://localhost:8080/herotokencentre/genherotokenkey");*/
		String applnurl=HERO_UTILS.getApplicationSontext(httpRequest).concat("/herotokencentre/genherotokenkey");
		log.info("applnurl   "+applnurl);
		URL url = new URL(applnurl);
		responseJSON = HERO_TOKEN_UTIL.HTTPpostMethod(formData,url);
		responseJSON = (JSONObject) responseJSON.get("response");
		log.info("responseJSON   "+responseJSON);
		JSONObject userinfoJSON = HERO_TOKEN_UTIL.converttoJSON(responseJSON.toString());
		tokenkey = (String) userinfoJSON.get("tokenkey");
		log.info("tokenkey "+tokenkey);
		
		if(tokenkey != null){
		
		JSONArray userDetailsArr = (JSONArray) userinfoJSON.get("userDetails");
		log.info("userDetailsArr   "+userDetailsArr);
		
		if(userDetailsArr != null && userDetailsArr.size() > 0)
		{
			log.info("responseObjJSON   "+userDetailsArr.get(0));
			JSONObject responseObj = (JSONObject) userDetailsArr.get(0);
			String username = (String) responseObj.get("username");
			String user_name = (String) responseObj.get("username");
			String userid = (String) responseObj.get("userid");

			
			session.setAttribute("usertype",Integer.parseInt((String)responseObj.get("role")));
			session.setAttribute("usertypestr",responseObj.get("role"));
			session.setAttribute("usertypedesc", responseObj.get("usertypedesc"));
			session.setAttribute("username",username.toUpperCase());
			session.setAttribute("user_name",user_name.toUpperCase());
			session.setAttribute("uid",responseObj.get("userid"));
			//session.setAttribute("currencyname",responseObj.get("currencyname"));
			session.setAttribute("currencyname",responseObj.get("currencysymbol"));
			session.setAttribute("currencydecimal",responseObj.get("currencydecimal"));
			session.setAttribute("storeid", responseObj.get("storeid"));
			log.info(session.getAttribute("storeid"));
			List<Object> usermenuList = new ArrayList<Object>();
	
			if(session.getAttribute("usertype") != null)
			{
				  int usertype = (int) session.getAttribute("usertype");
				  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`modulepath`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
							+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) AND `isadminmenu` = '1' ORDER BY `userid`,`moduleid` ASC";
					 
				  log.info("mainmenuQuery    "+mainmenuQuery);
				  
				  /*usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);*/
				  Map<String, Object> menuMap = inventoryLOVobj.getMenuList(mainmenuQuery);
				  List<String> accessURLS = (List<String>)menuMap.get("accessURLS");
				  usermenuList = (List<Object>) menuMap.get("usermenuList");
				  session.setAttribute("mainmenuList",usermenuList);
				  session.setAttribute("accessURLS",accessURLS);
				  log.info("store id"+session.getAttribute("storeid"));
				  
				 
					}
			
		}

		try
		{
			/*String smsQuery = "SELECT * FROM hero_sms_template";
			
			@SuppressWarnings("unchecked")
			List<Object> smsTemplateList = jdbcTemplate.query(smsQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							smsMap.put("templateid", rs.getString("sms_temp_id"));
							smsMap.put("templatename", rs.getString("sms_temp_name"));
							smsMap.put("messagecontent", rs.getString("sms_message_content"));
							
							return smsMap;
						}
					});

			String smsSettingsQuery = "SELECT * FROM hero_sms_settings";
			
			@SuppressWarnings("unchecked")
			List<Object> smsSettingsList = jdbcTemplate.query(smsSettingsQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							smsMap.put("apikey", rs.getString("api_key"));
							smsMap.put("apiusername", rs.getString("api_username"));
							smsMap.put("apipassword", rs.getString("api_password"));
							
							return smsMap;
						}
					});
			
			HEROHOSURINVENTORYUTIL.smsTemplateList = smsTemplateList;
			HEROHOSURINVENTORYUTIL.smsSettingsList = smsSettingsList;*/
			
			
			inventoryLOVobj.getLowStockList(httpRequest);
			
		}
		
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
	String orgnQuery = "SELECT * FROM `hero_orgn_table`";
			
			@SuppressWarnings("unchecked")
			List<Object> orgnSettingList = jdbcTemplate.query(orgnQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							session.setAttribute("companyname", rs.getString("orgn_name"));
							
							return smsMap;
						}
					});
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
			
				Map<String, Object> userDetailMap = new HashMap<String, Object>();
				userDetailMap.put("tokenkey", tokenkey);
				userDetailMap.put("userDetails", userDetailsArr);

				response.setResponseObj(userDetailMap);
				response.setResponseType("S");
				
				responseInfo.setInventoryResponse(response);
		}
		else{
			response.setResponseType("F");
			responseInfo.setInventoryResponse(response);
		}
		
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		error_log.info(e1);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		error_log.info(e);
	}		
	

	return responseInfo;
}
	
	@Override
	public HERO_STK_RESPONSEINFO guestLogin(final HttpServletRequest httpRequest) throws ClassNotFoundException {
	
	
		
	String formData = "{\"Username\":\"guest_user\",\"Password\" :\"admin@123\"}";
	String tokenkey = "";
	session = httpRequest.getSession();
	JSONObject responseJSON;
	try {
		/*URL url = new URL("http://localhost:8080/herotokencentre/genherotokenkey");*/
		String applnurl=HERO_UTILS.getApplicationSontext(httpRequest).concat("/herotokencentre/genherotokenkey");
		log.info("applnurl   "+applnurl);
		URL url = new URL(applnurl);
		responseJSON = HERO_TOKEN_UTIL.HTTPpostMethod(formData,url);
		responseJSON = (JSONObject) responseJSON.get("response");
		log.info("responseJSON   "+responseJSON);
		JSONObject userinfoJSON = HERO_TOKEN_UTIL.converttoJSON(responseJSON.toString());
		tokenkey = (String) userinfoJSON.get("tokenkey");
		log.info("tokenkey "+tokenkey);
		
		JSONObject responseObj = new JSONObject();
		if(tokenkey != null){
		
		JSONArray userDetailsArr = (JSONArray) userinfoJSON.get("userDetails");
		log.info("userDetailsArr   "+userDetailsArr);
		
		if(userDetailsArr != null && userDetailsArr.size() > 0)
		{
			log.info("responseObjJSON   "+userDetailsArr.get(0));
			responseObj = (JSONObject) userDetailsArr.get(0);
			String username = (String) responseObj.get("username");
			String user_name = (String) responseObj.get("username");
			String userid = (String) responseObj.get("userid");

			
			session.setAttribute("usertype",Integer.parseInt((String)responseObj.get("role")));
			session.setAttribute("usertypestr",responseObj.get("role"));
			session.setAttribute("usertypedesc", responseObj.get("usertypedesc"));
			session.setAttribute("username",username.toUpperCase());
			session.setAttribute("user_name",user_name.toUpperCase());
			session.setAttribute("uid",responseObj.get("userid"));
			//session.setAttribute("currencyname",responseObj.get("currencyname"));
			session.setAttribute("currencyname",responseObj.get("currencysymbol"));
			session.setAttribute("currencydecimal",responseObj.get("currencydecimal"));
			session.setAttribute("storeid", responseObj.get("storeid"));
			log.info(session.getAttribute("storeid"));
			List<Object> usermenuList = new ArrayList<Object>();
	
			if(session.getAttribute("usertype") != null)
			{
				  int usertype = (int) session.getAttribute("usertype");
				  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`modulepath`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
							+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) AND `isadminmenu` = '1' ORDER BY `userid`,`moduleid` ASC";
					 
				  log.info("mainmenuQuery    "+mainmenuQuery);
				  
				  /*usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);*/
				  Map<String, Object> menuMap = inventoryLOVobj.getMenuList(mainmenuQuery);
				  List<String> accessURLS = (List<String>)menuMap.get("accessURLS");
				  usermenuList = (List<Object>) menuMap.get("usermenuList");
				  session.setAttribute("mainmenuList",usermenuList);
				  session.setAttribute("accessURLS",accessURLS);
				  log.info("store id"+session.getAttribute("storeid"));
				  
				  Map<String, Object> tokenmap = new HashMap<String, Object>();
				  tokenmap.put("tokenkey", tokenkey );
				  
				  
				  
				  List<Object> tokenList = HEROHOSURINVENTORYUTIL.tokenList ;
				  tokenList.add(tokenmap);
				  
				  log.info("tokenmap"+tokenmap);
				  log.info("tokenList"+tokenList);
				  HEROHOSURINVENTORYUTIL.tokenList = tokenList;
				  
				  //System.out.println("converted words"+HEROHOSURINVENTORYUTIL.convertNumToString(74268));
				  
					}
			
		}

		try
		{
			/*String smsQuery = "SELECT * FROM hero_sms_template";
			
			@SuppressWarnings("unchecked")
			List<Object> smsTemplateList = jdbcTemplate.query(smsQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							smsMap.put("templateid", rs.getString("sms_temp_id"));
							smsMap.put("templatename", rs.getString("sms_temp_name"));
							smsMap.put("messagecontent", rs.getString("sms_message_content"));
							
							return smsMap;
						}
					});

			String smsSettingsQuery = "SELECT * FROM hero_sms_settings";
			
			@SuppressWarnings("unchecked")
			List<Object> smsSettingsList = jdbcTemplate.query(smsSettingsQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							smsMap.put("apikey", rs.getString("api_key"));
							smsMap.put("apiusername", rs.getString("api_username"));
							smsMap.put("apipassword", rs.getString("api_password"));
							
							return smsMap;
						}
					});
			
			HEROHOSURINVENTORYUTIL.smsTemplateList = smsTemplateList;
			HEROHOSURINVENTORYUTIL.smsSettingsList = smsSettingsList;*/
			
			inventoryLOVobj.getLowStockList(httpRequest);
			
		}
		
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
	String orgnQuery = "SELECT * FROM `hero_orgn_table`";
			
			@SuppressWarnings("unchecked")
			List<Object> orgnSettingList = jdbcTemplate.query(orgnQuery,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1)
								throws SQLException {
						
							Map<String, Object> smsMap = new HashMap<String, Object>();
							
							session.setAttribute("companyname", rs.getString("orgn_name"));
							
							return smsMap;
						}
					});
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
			
				Map<String, Object> userDetailMap = new HashMap<String, Object>();
				userDetailMap.put("tokenkey", tokenkey);
				userDetailMap.put("userDetails", responseObj);

				response.setResponseObj(userDetailMap);
				response.setResponseType("S");
				
				responseInfo.setInventoryResponse(response);
		}
		else{
			response.setResponseType("F");
			responseInfo.setInventoryResponse(response);
		}
		
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		error_log.info(e1);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		error_log.info(e);
	}		
	

	return responseInfo;
}
	
	
	public HERO_STK_RESPONSEINFO validLogin1(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
		log.info("formData      " + formData + "   " + jdbcTemplate);
		HERO_STK_LOGINREQUEST request = (HERO_STK_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.stock.forms.login.HERO_STK_LOGINREQUEST");
		session = httpRequest.getSession();
		
		String selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role,currencyid,`CURR_SYMBOL`,"
				+ "`CURR_DEC_FORMAT`,user_store_id,`usertype_name` "
				+ "FROM hero_user a LEFT JOIN `hero_admin_currency` b ON a.`currencyid` = b.`currency_id` "
				+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` WHERE username=? and password = ? and status = 1 AND (`usertype_dept` = '1')";
		try
		{
			log.info("password         "+request.getPassword()+"   "+HEROHOSURINVENTORYUTIL.decrypt("Herbzaliveerpapp", "ppapreevilazbreH", "Q+6IS9hZQv6cKRGqYX31Eg=="));
			log.info("selectQuery for Login   "+selectQuery);
		String invUserPW = HEROHOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", request.getPassword());
		
		@SuppressWarnings("unchecked")
		List<Object> userDetails = jdbcTemplate.query(selectQuery,new Object[]{request.getUsername(),invUserPW},
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("login", "S");
						map.put("userid", rs.getString("userid"));
						map.put("username", rs.getString("username"));
						map.put("role", rs.getString("role"));
						map.put("currencyid", rs.getString("currencyid"));
						map.put("usertypedesc", rs.getString("usertype_name"));
						
 
 
	session.setAttribute("usertype",rs.getInt("role"));
	session.setAttribute("usertypestr",rs.getString("role"));
	session.setAttribute("usertypedesc", rs.getString("usertype_name"));
	String username = "",user_name="",uid="0";
	if(map.get("username") != null)
	{
		username = (String) map.get("username");	
	}
	
	if(map.get("user_name") != null)
	{
		user_name = (String)map.get("user_name");	
	}
	
	if(map.get("userid") != null)
	{
		uid = (String)map.get("userid");
	}
	
	session.setAttribute("username",username.toUpperCase());
	session.setAttribute("user_name",user_name.toUpperCase());
	session.setAttribute("uid",uid);
	session.setAttribute("currencyname",rs.getString("CURR_SYMBOL"));
	session.setAttribute("currencydecimal",rs.getInt("CURR_DEC_FORMAT"));
	session.setAttribute("storeid", rs.getInt("user_store_id"));

	try
	{
		/*String smsQuery = "SELECT * FROM hero_sms_template";
		
		@SuppressWarnings("unchecked")
		List<Object> smsTemplateList = jdbcTemplate.query(smsQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						
						smsMap.put("templateid", rs.getString("sms_temp_id"));
						smsMap.put("templatename", rs.getString("sms_temp_name"));
						smsMap.put("messagecontent", rs.getString("sms_message_content"));
						
						return smsMap;
					}
				});

		String smsSettingsQuery = "SELECT * FROM hero_sms_settings WHERE `inv_sms_send` = '1'";
		
		@SuppressWarnings("unchecked")
		List<Object> smsSettingsList = jdbcTemplate.query(smsSettingsQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						
						smsMap.put("apikey", rs.getString("api_key"));
						smsMap.put("apiusername", rs.getString("api_username"));
						smsMap.put("apipassword", rs.getString("api_password"));
						
						return smsMap;
					}
				});
		
		HEROHOSURINVENTORYUTIL.smsTemplateList = smsTemplateList;
		HEROHOSURINVENTORYUTIL.smsSettingsList = smsSettingsList;*/
		
		inventoryLOVobj.getLowStockList(httpRequest);
		
	}
	catch(Exception e)
	{
		log.error(e);
	}
	
	try
	{
String orgnQuery = "SELECT * FROM `hero_orgn_table`";
		
		@SuppressWarnings("unchecked")
		List<Object> orgnSettingList = jdbcTemplate.query(orgnQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						
						session.setAttribute("companyname", rs.getString("orgn_name"));
						
						return smsMap;
					}
				});
	}
	catch(Exception e)
	{
		log.error(e);
	}
						return map;
					}
				});
log.info("userDetails        "+userDetails);

if(userDetails != null && userDetails.size() > 0)
{
	Map<String, Object> userMap = (Map<String, Object>) userDetails.get(0);
	loginObj.setUsername((String)userMap.get("username")); 
	


log.info("UserName        "+loginObj.getUsername());

List<Object> usermenuList = new ArrayList<Object>();

if(session.getAttribute("usertype") != null)
{
	  int usertype = (int) session.getAttribute("usertype");
	  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`modulepath`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
	  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
				+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
				+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) AND `isadminmenu` = '1' ORDER BY `userid`,`moduleid` ASC";
		 
	  log.info("mainmenuQuery    "+mainmenuQuery);
	  
	  /*usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);*/
	  Map<String, Object> menuMap = inventoryLOVobj.getMenuList(mainmenuQuery);
	  List<String> accessURLS = (List<String>)menuMap.get("accessURLS");
	  usermenuList = (List<Object>) menuMap.get("usermenuList");
	  session.setAttribute("mainmenuList",usermenuList);
	  session.setAttribute("accessURLS",accessURLS);
		}
	
	
		response.setResponseObj(userDetails);
		response.setResponseType("S");
		responseInfo.setInventoryResponse(response);
		}
		else{
			response.setResponseType("F");
			responseInfo.setInventoryResponse(response);
		}
		}
		catch(Exception e)
		{
			/*e.printStackTrace();*/
			log.error(e);
			response.setResponseType("F");
			response.setResponseObj(e.getMessage());
			responseInfo.setInventoryResponse(response);
		}
		return responseInfo;
	}

	public HERO_STK_RESPONSEINFO validssologin(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
		log.info("formData      " + formData + "   " + jdbcTemplate);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		HeroUserDetail request = (HeroUserDetail) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.sso.HeroUserDetail");
		session = httpRequest.getSession();
		
		String selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role,currencyid,"
				+ " `gcs_html_code` CURR_SYMBOL, "
				+ "`CURR_DEC_FORMAT`,user_store_id,`usertype_name` "
				+ "FROM hero_user a LEFT JOIN `hero_admin_currency` b ON a.`currencyid` = b.`currency_id` "
				+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` "
				+ "JOIN `hero_global_currency_symbols` d ON b.`CURR_SYMBOL` = d.`gcs_id`"
				+ " WHERE username=? and status = 1";
		try
		{
			log.info("selectQuery for Login   "+selectQuery);
			String username = null;
			if(formData != null)
			{
				JSONObject responseJSON = HERO_TOKEN_UTIL.converttoJSON(formData);
				if(responseJSON != null)
				{
					Object userDetailOBJ = responseJSON.get("response");
					if(userDetailOBJ != null)
					{
						JSONObject userDetailJSON = (JSONObject)userDetailOBJ;
						List<Object> userDetails = (List<Object>) userDetailJSON.get("userDetails");
						if(userDetails != null && userDetails.size() > 0)
						{
							Map<String, Object> userDetailMap = (Map<String, Object>) userDetails.get(0);
							username = userDetailMap.get("username") != null ? (String) userDetailMap.get("username") : "";
						}
					}
				}
				
			}
		log.info("username   "+username);
		@SuppressWarnings("unchecked")
		List<Object> userDetails = jdbcTemplate.query(selectQuery,new Object[]{username},
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						HashMap<String, Object> map = new HashMap<String, Object>();
						map.put("login", "S");
						map.put("userid", rs.getString("userid"));
						map.put("username", rs.getString("username"));
						map.put("role", rs.getString("role"));
						map.put("currencyid", rs.getString("currencyid"));
						map.put("usertypedesc", rs.getString("usertype_name"));
						map.put("currencyname", rs.getString("CURR_SYMBOL"));
						map.put("currencydecimal", rs.getString("CURR_DEC_FORMAT"));
						map.put("storeid", rs.getString("user_store_id"));
 
 
	session.setAttribute("usertype",rs.getInt("role"));
	session.setAttribute("usertypestr",rs.getString("role"));
	session.setAttribute("usertypedesc", rs.getString("usertype_name"));
	String username = "",user_name="",uid="0";
	if(map.get("username") != null)
	{
		username = (String) map.get("username");	
	}
	
	if(map.get("user_name") != null)
	{
		user_name = (String)map.get("user_name");	
	}
	
	if(map.get("userid") != null)
	{
		uid = (String)map.get("userid");
	}
	
	session.setAttribute("username",username.toUpperCase());
	session.setAttribute("user_name",user_name.toUpperCase());
	session.setAttribute("uid",uid);
	session.setAttribute("currencyname",rs.getString("CURR_SYMBOL"));
	session.setAttribute("currencydecimal",rs.getInt("CURR_DEC_FORMAT"));
	session.setAttribute("storeid", rs.getInt("user_store_id"));

	try
	{
		/*String smsQuery = "SELECT * FROM hero_sms_template";
		
		@SuppressWarnings("unchecked")
		List<Object> smsTemplateList = jdbcTemplate.query(smsQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						
						smsMap.put("templateid", rs.getString("sms_temp_id"));
						smsMap.put("templatename", rs.getString("sms_temp_name"));
						smsMap.put("messagecontent", rs.getString("sms_message_content"));
						
						return smsMap;
					}
				});

		String smsSettingsQuery = "SELECT * FROM hero_sms_settings WHERE `inv_sms_send` = '1'";
		
		@SuppressWarnings("unchecked")
		List<Object> smsSettingsList = jdbcTemplate.query(smsSettingsQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						
						smsMap.put("apikey", rs.getString("api_key"));
						smsMap.put("apiusername", rs.getString("api_username"));
						smsMap.put("apipassword", rs.getString("api_password"));
						
						return smsMap;
					}
				});
		
		HEROHOSURINVENTORYUTIL.smsTemplateList = smsTemplateList;
		HEROHOSURINVENTORYUTIL.smsSettingsList = smsSettingsList;*/
		
		List<Object> lowstockList = inventoryLOVobj.getLowStockList(httpRequest);
		/*responseMap.put("lowstockList", lowstockList);*/
	}
	catch(Exception e)
	{
		log.error(e);
	}
	
	try
	{
String orgnQuery = "SELECT * FROM `hero_orgn_table`";
		
		@SuppressWarnings("unchecked")
		List<Object> orgnSettingList = jdbcTemplate.query(orgnQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						Map<String, Object> smsMap = new HashMap<String, Object>();
						String companyName = rs.getString("orgn_name");
						smsMap.put("companyname", companyName);
						session.setAttribute("companyname", companyName);
						
						return smsMap;
					}
				});
		/*responseMap.put("orgnSettingList", orgnSettingList);*/
	}
	catch(Exception e)
	{
		log.error(e);
	}
						return map;
					}
				});
log.info("userDetails        "+userDetails);

if(userDetails != null && userDetails.size() > 0)
{
	Map<String, Object> userMap = (Map<String, Object>) userDetails.get(0);
	loginObj.setUsername((String)userMap.get("username")); 
	


log.info("UserName        "+loginObj.getUsername());

List<Object> usermenuList = new ArrayList<Object>();

if(session.getAttribute("usertype") != null)
{
	  int usertype = (int) session.getAttribute("usertype");
	  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,`modulepath`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
	  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
				+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
				+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) ORDER BY `userid`,`moduleid` ASC";
		 
	  log.info("mainmenuQuery    "+mainmenuQuery);
	  
	  /*usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);*/
	  Map<String, Object> menuMap = inventoryLOVobj.getMenuList(mainmenuQuery);
	  usermenuList = (List<Object>) menuMap.get("usermenuList");
	  List<String> accessURLS = (List<String>)menuMap.get("accessURLS");
	  
	  session.setAttribute("mainmenuList",usermenuList);
	  session.setAttribute("accessURLS",accessURLS);
	  
	  responseMap.put("usermenuList", usermenuList);
}

try
{
String orgnQuery = "SELECT * FROM `hero_orgn_table`";
	
	@SuppressWarnings("unchecked")
	List<Object> orgnSettingList = jdbcTemplate.query(orgnQuery,
			new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
				
					Map<String, Object> smsMap = new HashMap<String, Object>();
					String companyName = rs.getString("orgn_name");
					smsMap.put("companyname", companyName);
					session.setAttribute("companyname", companyName);
					
					return smsMap;
				}
			});
	responseMap.put("orgnSettingList", orgnSettingList);
	List<Object> lowStockList = inventoryLOVobj.getLowStockList(httpRequest);
	responseMap.put("lowStockList", lowStockList);
}
catch(Exception e)
{
	log.error(e);
}
}


log.info("session.getAttribute(uid)   "+session.getAttribute("uid"));


responseMap.put("userDetails", userDetails);


		response.setResponseObj(responseMap);
		response.setResponseType("S");
		
		responseInfo.setInventoryResponse(response);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.error(e);
			response.setResponseType("F");
			response.setResponseObj(e.getMessage());
			responseInfo.setInventoryResponse(response);
		}
		return responseInfo;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO changepassword(String formData,
			HttpServletRequest httpRequest) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		HERO_STK_LOGINREQUEST request = (HERO_STK_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.stock.forms.login.HERO_STK_LOGINREQUEST");
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_LOGIN_MODULE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_USERID", request.getUserid());
		inParamMap.put("P_OLD_PASSWORD", HEROHOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", request.getPassword()));
		inParamMap.put("P_NEW_PASSWORD", HEROHOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", request.getConfirmPassword()));
		inParamMap.put("P_OPRN", "CHANGE_PW");
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		response = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, response);
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

	@Override
	public HERO_STK_RESPONSEINFO registerforgotpassword(String formData,
			HttpServletRequest httpRequest) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		HERO_STK_LOGINREQUEST request = (HERO_STK_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.stock.forms.login.HERO_STK_LOGINREQUEST");
		String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$%*";
		StringBuilder builder = new StringBuilder();
		int count = 8;
		while (count-- != 0) {
		int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
		builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		
		String newPW = builder.toString();
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_OLD_PASSWORD", newPW);
		log.info("Reset Value is   "+newPW);
	    
	    newPW = HEROHOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", newPW);
	    
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_LOGIN_MODULE");
		inParamMap.put("P_USERID", request.getUsername());
		inParamMap.put("P_NEW_PASSWORD", newPW);
		inParamMap.put("P_OPRN", "FORGOT_PW");
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		response = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, response);
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

	public HERO_STK_RESPONSEINFO fetchUserList(HERO_STK_ILOGINDAO loginDAOobj) {
		String selectQuery = "SELECT * FROM hero_user_login_detl";
		
		@SuppressWarnings("unchecked")
		List<Object> userDetails = jdbcTemplate.query(selectQuery,
				new RowMapper() {

					@Override
					public Object mapRow(ResultSet rs, int arg1)
							throws SQLException {
					
						List<String> detail = new ArrayList<String>();
						detail.add(rs.getString("USER_ID"));
						detail.add(rs.getString("PASS_WORD"));
						detail.add("");
						detail.add(rs.getString("CONFM_PASS_WORD"));
						return detail;
					}
				});
log.info("userDetails        "+userDetails);

		response.setResponseObj(inventoryUtilOBJ.returnJSONobject(userDetails));
		response.setResponseType("S");
		
		responseInfo.setInventoryResponse(response);

		return responseInfo;
	}
	
	
	public HERO_STK_RESPONSEINFO herosettingsurl(String modulename)
	 {

try
{
	
	String smsQuery = "SELECT * FROM hero_settings_config WHERE `hsc_module` = '"+modulename
			+"' order by hsc_sno asc";
	log.info("smsQuery   "+smsQuery);
	@SuppressWarnings("unchecked")
	List<Object> heroSettingsList = jdbcTemplate.query(smsQuery,
			new RowMapper() {

				@Override
				public Object mapRow(ResultSet rs, int arg1)
						throws SQLException {
				
					Map<String, Object> heroSettingsMap = new HashMap<String, Object>();
					
					heroSettingsMap.put("sno", rs.getString("hsc_sno"));
					heroSettingsMap.put("screenname", rs.getString("hsc_screen_name"));
					heroSettingsMap.put("url", rs.getString("hsc_url"));
					
					return heroSettingsMap;
				}
			});
	log.info("heroSettingsList   "+heroSettingsList);
response.setResponseType("S");
response.setResponseObj(heroSettingsList);
responseInfo.setInventoryResponse(response);

}
catch(org.springframework.dao.DataIntegrityViolationException i)
{
	i.printStackTrace();
	response.setResponseType("F");
	response.setResponseObj("User Already Exists");
	responseInfo.setInventoryResponse(response);
}
catch (Exception e) {
	e.printStackTrace();
	response.setResponseType("F");
	response.setResponseObj(e.getMessage());
	responseInfo.setInventoryResponse(response);
}
return responseInfo;
}

	public HERO_STK_RESPONSEINFO registerUser(String formData)
			 {
		
		try
		{
		HERO_STK_LOGINREQUEST request = (HERO_STK_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.inv.forms.login.LoginRequest");
		log.info(request.getUsername() + "   "
				+ request.getPassword() + "   " + request.getConfirmPassword());
		
		int isInsert = jdbcTemplate.update("call P_HERO_LOGIN_MODULE (?,?,?,?)", 
				new Object[]{
				request.getUsername(), 
				request.getPassword(),
				request.getConfirmPassword(),
				request.getOprn()});
		log.info("isInsert        "+isInsert);
		response.setResponseType("S");
		response.setResponseObj(HEROHOSURINVENTORYUTIL.returnMessage());
		responseInfo.setInventoryResponse(response);

		}
		catch(org.springframework.dao.DataIntegrityViolationException i)
		{
			i.printStackTrace();
			response.setResponseType("F");
			response.setResponseObj("User Already Exists");
			responseInfo.setInventoryResponse(response);
		}
		catch (Exception e) {
			log.error(e);
			response.setResponseType("F");
			response.setResponseObj(e.getMessage());
			responseInfo.setInventoryResponse(response);
		}
		return responseInfo;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadDashboardGraph() throws ClassNotFoundException {
		// TODO Auto-generated method stub
log.info("welcome to inside graph  ");
		
		
		StringBuilder query = new StringBuilder("SELECT `pos_store_id` store_id,`store_name`,(SUM(`pos_paid_amt`)*`CURR_CONVERSION_RATE`) paid_amount, "
												+" DATE_FORMAT(`created_at`,'%c/%e/%Y')`created_date` " 
												+" FROM `hero_stock_pos_summary` a  JOIN `hero_stock_store` b ON a.`pos_store_id` = b.`store_id` "
												+" JOIN `hero_admin_currency` c ON b.`currency_id`  = c.`currency_id` "
												+" GROUP BY DATE_FORMAT(`created_at`,'%c/%e/%Y'),`pos_store_id` ");
		
		log.info(" loadDashboardGraphstore query       "+query);
		@SuppressWarnings("unchecked")
		List<Object> graphStoreList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("storeid", rs.getString("store_id"));
				map.put("storename", rs.getString("store_name"));
				map.put("paidamount", rs.getFloat("paid_amount"));
				map.put("createddate", rs.getString("created_date"));

				return map;
			}
		});
		
		StringBuilder queryManufacturer = new StringBuilder("SELECT c.`manufacturer_id` manufacturer_id, DATE_FORMAT(`created_at`,'%c/%e/%Y') created_date, "
				+" SUM(`pos_sales_count`) pos_sales_count,d.`company_name`,(SUM(`pos_paid_amt`)*`CURR_CONVERSION_RATE`) paid_amount " 
				+" FROM `hero_stock_pos_summary`a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` "
				+" JOIN `hero_stock_product` c ON  c.`product_id` = b.`pos_prod_id` JOIN `hero_admin_company` d ON d.`company_id` = c.`manufacturer_id`"
				+"  JOIN `hero_stock_store` e ON a.`pos_store_id` = e.`store_id`  JOIN `hero_admin_currency` f ON e.`currency_id`  = f.`currency_id` "
				+" GROUP BY DATE_FORMAT(`created_at`,'%c/%e/%Y'),c.`manufacturer_id`");

				log.info(" queryManufaucturer query       "+queryManufacturer);
				@SuppressWarnings("unchecked")
				List<Object> graphManufacturerList = jdbcTemplate.query(queryManufacturer.toString(), new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("manufacturerid", rs.getString("manufacturer_id"));
				map.put("manufacturername", rs.getString("company_name"));
				map.put("productcount", rs.getFloat("pos_sales_count"));
				map.put("paidamount", rs.getFloat("paid_amount"));
				map.put("createddate", rs.getString("created_date"));
				
				return map;
				}
				});
		
				
				
				StringBuilder querySales = new StringBuilder("SELECT (SUM(`pos_paid_amt`)*`CURR_CONVERSION_RATE`) paid_amount, "
						+" DATE_FORMAT(`created_at`,'%c/%e/%Y')`created_date`  FROM `hero_stock_pos_summary` a  JOIN `hero_stock_store` b ON  " 
						+" a.`pos_store_id` = b.`store_id`  JOIN `hero_admin_currency` c ON b.`currency_id`  = c.`currency_id` "
						+" GROUP BY DATE_FORMAT(`created_at`,'%c/%e/%Y') ");

					log.info(" querySales query       "+querySales);
					@SuppressWarnings("unchecked")
					List<Object> graphSalesList = jdbcTemplate.query(querySales.toString(), new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int index) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();

					map.put("paidamount", rs.getFloat("paid_amount"));
					map.put("createddate", rs.getString("created_date"));
					
					return map;
					}
					});
					
					
					StringBuilder queryCustomerDue = new StringBuilder("SELECT (SUM(`pos_balance_amount`)*`CURR_CONVERSION_RATE`) paid_amount, "
							+" DATE_FORMAT(`created_at`,'%c/%e/%Y')`created_date`  FROM `hero_stock_pos_summary` a  JOIN `hero_stock_store` b ON  " 
							+" a.`pos_store_id` = b.`store_id`  JOIN `hero_admin_currency` c ON b.`currency_id`  = c.`currency_id` "
							+" GROUP BY DATE_FORMAT(`created_at`,'%c/%e/%Y') ");

						log.info(" queryCustomerDue query       "+queryCustomerDue);
						@SuppressWarnings("unchecked")
						List<Object> graphCustomerDueList = jdbcTemplate.query(queryCustomerDue.toString(), new RowMapper() {
						
						@Override
						public Object mapRow(ResultSet rs, int index) throws SQLException {
						// TODO Auto-generated method stub
						Map<String, Object> map = new HashMap<String, Object>();

						map.put("paidamount", rs.getFloat("paid_amount"));
						map.put("createddate", rs.getString("created_date"));
						
						return map;
						}
						});
						
						
						StringBuilder queryTax = new StringBuilder("SELECT (SUM(`pos_tax_amount`)*`CURR_CONVERSION_RATE`) paid_amount, "
								+" DATE_FORMAT(`created_at`,'%c/%e/%Y')`created_date`  FROM `hero_stock_pos_summary` a  JOIN `hero_stock_store` b ON  " 
								+" a.`pos_store_id` = b.`store_id`  JOIN `hero_admin_currency` c ON b.`currency_id`  = c.`currency_id` "
								+" GROUP BY DATE_FORMAT(`created_at`,'%c/%e/%Y') ");

							log.info(" queryTax query       "+queryTax);
							@SuppressWarnings("unchecked")
							List<Object> graphTaxList = jdbcTemplate.query(queryTax.toString(), new RowMapper() {
							
							@Override
							public Object mapRow(ResultSet rs, int index) throws SQLException {
							// TODO Auto-generated method stub
							Map<String, Object> map = new HashMap<String, Object>();

							map.put("paidamount", rs.getFloat("paid_amount"));
							map.put("createddate", rs.getString("created_date"));
							
							return map;
							}
							});
				
				
		log.info("====================");
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("graphStoreList", graphStoreList);
		resultMap.put("graphManufacturerList", graphManufacturerList);
		resultMap.put("graphSalesList", graphSalesList);
		resultMap.put("graphCustomerDueList", graphCustomerDueList);
		resultMap.put("graphTaxList", graphTaxList);
		
		response = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, response);
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

	
}

