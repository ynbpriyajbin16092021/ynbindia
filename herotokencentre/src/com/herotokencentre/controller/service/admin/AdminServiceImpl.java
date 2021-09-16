package com.herotokencentre.controller.service.admin;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;






import com.herotokencentre.controller.model.HMSBeans;
import com.herotokencentre.controller.response.HMSResponse;
import com.herotokencentre.util.HMSUtility;
import com.herotokencentre.util.HeroUserDetail;


@Service
public class AdminServiceImpl extends HMSBeans implements IAdminService{
	final static Logger logger = Logger.getLogger(AdminServiceImpl.class);
	public static HashMap<String, HeroUserDetail> HERO_TOKENS = new HashMap<String, HeroUserDetail>();
	
	@Override
	public HMSResponse genherotokenkey(JSONObject request) {
	
		HeroUserDetail userDetail = new HeroUserDetail();
		long tokenkey = 0;
		String formData = request.toString();
		logger.info("formData      " + formData + "   " + jdbcTemplate);
		
		try
		{
			HERO_ADM_SERVC_LOGINREQUEST loginrequest = (HERO_ADM_SERVC_LOGINREQUEST) HMSUtility
					.convertJSONtooOBJECT(formData,
							"com.herotokencentre.controller.service.admin.HERO_ADM_SERVC_LOGINREQUEST");
			
			int applnType = loginrequest.getApplntype();
			
			String selectQuery = "";
			
				String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id != '1'";
				
				@SuppressWarnings("unchecked")
				List<Object> usertypeApplnList = jdbcTemplate.query(usertypeApplnQuery, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						// TODO Auto-generated method stub
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("applnid", rs.getInt("usertype_dept"));
						return map;
					}
				});
				
				if(usertypeApplnList!= null && usertypeApplnList.size()>0){
					int a[]=new int[usertypeApplnList.size()];
					for(int i = 0; i<usertypeApplnList.size(); i++){
						Map<String, Object> PatientsDetailsMap = (Map<String, Object>) usertypeApplnList.get(i);
						int applnid = (int) PatientsDetailsMap.get("applnid");
						a[i]=applnid;
					}
					boolean withpharmacy = contains(a, 1);
					if(withpharmacy){
						selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role,currencyid,"
								+ "`gcs_html_code` CURR_SYMBOL ,"
								+ "`CURR_DEC_FORMAT`,user_store_id,`usertype_name`,`usertype_dept` appln_type, `user_lab_id` "
								+ "FROM hero_user a LEFT JOIN `hero_admin_currency` b ON a.`currencyid` = b.`currency_id` "
								+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` "
								+ "JOIN `hero_global_currency_symbols` d ON b.`CURR_SYMBOL` = d.`gcs_id` WHERE username=? and "
								+ "password = ? and status = '1' ";
					}else {
						selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role, 0 currencyid, 0 `CURR_SYMBOL`,"
								+ " 0 `CURR_DEC_FORMAT`,(`user_clinic_id`) AS user_store_id,`usertype_name`,`usertype_dept` appln_type"
								+ " FROM hero_user a  LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` WHERE username=?"
								+ " AND PASSWORD = ? AND STATUS = '1' "; 
					}
				}
			
			
			
			logger.info("selectQuery for Login   "+selectQuery);
			logger.info("loginrequest.getUsername()"+loginrequest.getUsername());
			
		String invUserPW = HMSUtility.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", loginrequest.getPassword());
		logger.info("invUserPW"+invUserPW);
			/*String sarasp = HMSUtility.decrypt("Herbzaliveerpapp", "ppapreevilazbreH", "hZPBPD5FEis=cm9vdA==");
		
			logger.info("mypassword   "+sarasp);*/
			
			/*
			
			String selectQuery = "";
			
			selectQuery = "SELECT CONCAT(`fname`,' ',`lname`)user_name,`username`,`role`,userid,username,role, 0 currencyid, 0 `CURR_SYMBOL`,"
					+ " 0 `CURR_DEC_FORMAT`,(`user_clinic_id`) AS user_store_id,`usertype_name`,`usertype_dept` appln_type "
					+ " FROM hero_user a  LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` WHERE username=?"
					+ " AND PASSWORD = ? AND STATUS = '1'"; 
			*/
				
		@SuppressWarnings("unchecked")
		List<Object> userDetails = jdbcTemplate.query(selectQuery,new Object[]{loginrequest.getUsername(),invUserPW},
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
						map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
						map.put("currencydecimal", rs.getString("CURR_DEC_FORMAT"));
						map.put("storeid", rs.getInt("user_store_id"));
						map.put("applntype", rs.getString("appln_type"));
						map.put("labid", rs.getString("user_lab_id"));
						return map;
					}
				});
		logger.info("userDetails        "+userDetails);
		if(userDetails != null && userDetails.size() > 0)
		{
			userDetail.setUserDetails(userDetails);
			
			Random randomno = new Random();
			tokenkey = randomno.nextLong();
			if(tokenkey < 1)
			{
				tokenkey = tokenkey * -1;
			}
			
			userDetail.setTokenkey(String.valueOf(tokenkey));
			
			logger.info("String.valueOf(tokenkey)  "+String.valueOf(tokenkey));
			
			
			String tokenid="0",username = "",userid="",role="",currencyid="",usertypedesc="",currencydecimal="",currencysymbol="",applntype="";
			int storeid = 0;
				Map<String, Object> userMap = (Map<String, Object>) userDetails.get(0);
				logger.info("userMap"+userMap);
				userid = (String)userMap.get("userid"); 
				username = (String)userMap.get("username"); 
				role = (String)userMap.get("role"); 
				currencyid = (String)userMap.get("currencyid"); 
				usertypedesc = (String)userMap.get("usertypedesc"); 
				currencydecimal = (String)userMap.get("currencydecimal"); 
				currencysymbol = (String)userMap.get("currencysymbol"); 
				storeid = (int)userMap.get("storeid"); 	
				applntype = (String)userMap.get("applntype"); 	
			
			//System.out.println("storeid        "+storeid);
			
			try {
			
				SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_TOKEN_MASTER");
				Map<String, Object> inParamMap = new HashMap<String, Object>();
				inParamMap.put("P_TOKEN_ID", tokenid);
				inParamMap.put("P_TOKEN_KEY", String.valueOf(tokenkey));
				inParamMap.put("P_TOKEN_LOGINOUT_STATUS", "0");
				inParamMap.put("P_TOKEN_UD_USERID", userid);
				inParamMap.put("P_TOKEN_UD_USERNAME", username);
				inParamMap.put("P_TOKEN_UD_ROLE", role);
				inParamMap.put("P_TOKEN_UD_CURRENCY_ID", currencyid);
				inParamMap.put("P_TOKEN_UD_USERTYPE_DESC", usertypedesc);
				inParamMap.put("P_TOKEN_UD_CURRENCY_SYMBOL", currencysymbol);
				inParamMap.put("P_TOKEN_UD_CURRENCY_DECIMAL", currencydecimal);
				inParamMap.put("P_TOKEN_UD_STORE_ID", storeid);
				inParamMap.put("P_OPERATION", "INS");
				logger.info("inparammap    "+ inParamMap);
				
				SqlParameterSource in = new MapSqlParameterSource(inParamMap);
				Map<String, Object> resultMap = jdbcCALL.execute(in);
				logger.info("resultMap    "+resultMap);
			} catch (Exception e) {
			
			e.printStackTrace();
			}
		}
		
		HERO_TOKENS.put(String.valueOf(tokenkey), userDetail);
		
		responseObj.setResponse(userDetail);

		}
		catch(Exception e)
		{
			e.printStackTrace();
			responseBeanObj.setResponseType("F");
			responseBeanObj.setResponseObj(e.getMessage());
			responseObj.setResponse(responseBeanObj);
		}
		
		return responseObj;
	
	}
	@Override
	public HMSResponse getherotokenkey(String tokenkey) {
		// TODO Auto-generated method stub
		
		
		try{
			String selectQuery = "SELECT `token_ud_storeid` store_id, `token_ud_username` user_name,`token_ud_currencyid` currency_id, "
								+"`token_ud_currencysymbol`currency_symbol,`token_ud_currencydecimal`currency_decimal,`token_ud_userid`user_id, "
								+"`token_ud_role`role,`token_ud_usertypedesc` usertype_desc FROM `hero_token` WHERE `token_key` = '"+tokenkey+"'";
			logger.info("select query"+selectQuery);
			
			
			@SuppressWarnings("unchecked")
			List<Object> userDetail = jdbcTemplate.query(selectQuery, new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						
							Map<String, Object> map = new HashMap<String, Object>();
							
							map.put("login", "S");
							map.put("userid", rs.getString("user_id"));
							map.put("username", rs.getString("user_name"));
							map.put("role", rs.getString("role"));
							map.put("currencyid", rs.getString("currency_id"));
							map.put("usertypedesc", rs.getString("usertype_desc"));
							map.put("storeid", rs.getString("store_id"));
							map.put("currencysymbol", rs.getString("currency_symbol"));
							map.put("currencydecimal", rs.getString("currency_decimal"));
							return map;
						}
			});
			logger.info("userdetails"+userDetail);

		HeroUserDetail userDetailsOBJ = new HeroUserDetail();
		userDetailsOBJ.setUserDetails(userDetail);
		userDetailsOBJ.setTokenkey(tokenkey);
		responseObj.setResponse(userDetailsOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			logger.error(e);
		}
		return responseObj;
	}

	public List<Object> getSubMenuList(String submenuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> submenuList = jdbcTemplate.query(submenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				map.put("modulepath", rs.getString("modulepath"));
				
				index++;
				return map;
			}
		});
		/*log.info("First submenuList       "+submenuList);*/
		
		return submenuList;
	}
	@Override
	public void removetoken(String tokenkey) {
		// TODO Auto-generated method stub
		HERO_TOKENS.remove(tokenkey);
	}
	
	public static boolean contains(int[] arr, int item) {
	    for (int n : arr) {
	       if (item == n) {
	          return true;
	       }
	    }
	    return false;
	 }
	
}
