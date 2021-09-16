package com.hero.reports.forms.login;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.web.servlet.ModelAndView;

import com.hero.heroutils.HERO_TOKEN_UTIL;
import com.hero.reports.lov.HERO_RTS_IINVENTORYLOV;
import com.hero.reports.response.HERO_RTS_RESPONSE;
import com.hero.reports.response.HERO_RTS_RESPONSEINFO;
import com.hero.reports.sso.HeroUserDetail;
import com.hero.reports.util.HEROHOSURINVENTORYUTIL;
import com.hero.reports.util.HERO_RTS_INVENTORYLOV;


public class HERO_RTS_LOGINDAOIMPL implements HERO_RTS_ILOGINDAO {

	private static Logger log = Logger.getLogger(HERO_RTS_LOGINDAOIMPL.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_RTS_RESPONSE response;
	@Autowired
	private HERO_RTS_RESPONSEINFO responseInfo;
	@Autowired
	private HEROHOSURINVENTORYUTIL inventoryUtilOBJ;
	@Autowired
	private HERO_RTS_LOGINREQUEST loginObj;
	
	@Autowired
	private HERO_RTS_IINVENTORYLOV inventoryLOVobj;
	
	HttpSession session = null; 
			
	public HERO_RTS_RESPONSEINFO validLogin(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
		log.info("formData      " + formData + "   " + jdbcTemplate);
		HERO_RTS_LOGINREQUEST request = (HERO_RTS_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.reports.forms.login.HERO_RTS_LOGINREQUEST");
		session = httpRequest.getSession();
		
		String selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role,currencyid,`CURR_SYMBOL`,"
				+ "`CURR_DEC_FORMAT`,user_store_id,`usertype_name` "
				+ "FROM hero_user a LEFT JOIN `hero_admin_currency` b ON a.`currencyid` = b.`currency_id` "
				+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` WHERE username=? and password = ? and status = 1";
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
	
}

log.info("UserName        "+loginObj.getUsername());

List<Object> usermenuList = new ArrayList<Object>();

if(session.getAttribute("usertype") != null)
{
	  int usertype = (int) session.getAttribute("usertype");
	  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
	  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
				+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
				+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) AND `isadminmenu` = '0' ORDER BY `userid`,`moduleid` ASC";
		 
	  log.info("mainmenuQuery    "+mainmenuQuery);
	  
	  usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);

	  session.setAttribute("mainmenuList",usermenuList);

}
	
	
		response.setResponseObj(userDetails);
		response.setResponseType("S");
		
		responseInfo.setInventoryResponse(response);
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

	public HERO_RTS_RESPONSEINFO validssologin(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
		log.info("formData      " + formData + "   " + jdbcTemplate);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		HeroUserDetail request = (HeroUserDetail) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.sso.HeroUserDetail");
		session = httpRequest.getSession();
		
		String selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role,currencyid,`CURR_SYMBOL`,"
				+ "`CURR_DEC_FORMAT`,user_store_id,`usertype_name` "
				+ "FROM hero_user a LEFT JOIN `hero_admin_currency` b ON a.`currencyid` = b.`currency_id` "
				+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` WHERE username=? and status = 1";
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
	  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
	  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
				+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
				+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) and `isadminmenu` = 0 ORDER BY `userid`,`moduleid` ASC";
		 
	  log.info("mainmenuQuery    "+mainmenuQuery);
	  
	  usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery);

	  session.setAttribute("mainmenuList",usermenuList);
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
	public HERO_RTS_RESPONSEINFO changepassword(String formData,
			HttpServletRequest httpRequest) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		HERO_RTS_LOGINREQUEST request = (HERO_RTS_LOGINREQUEST) HEROHOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.inv.forms.login.LoginRequest");
		
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

	public HERO_RTS_RESPONSEINFO fetchUserList(HERO_RTS_ILOGINDAO loginDAOobj) {
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

	public HERO_RTS_RESPONSEINFO registerUser(String formData)
			 {
		
		try
		{
		HERO_RTS_LOGINREQUEST request = (HERO_RTS_LOGINREQUEST) HEROHOSURINVENTORYUTIL
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

	
}

