package com.hero.forms.services.admin.login;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSE;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYLOV;
import com.hero.forms.services.admin.login.HERO_ADM_SERVC_ILOGINDAO;
import com.hero.forms.services.admin.login.HERO_ADM_SERVC_LOGINREQUEST;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.forms.services.admin.login.HERO_ADM_SERVC_LOGINREQUEST;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;


public class HERO_ADM_SERVC_LOGINDAOIMPL implements HERO_ADM_SERVC_ILOGINDAO {

	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_LOGINDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSE response;
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSEINFO responseInfo;
	@Autowired
	private HERO_ADM_SERVC_HOSURINVENTORYUTIL inventoryUtilOBJ;
	@Autowired
	private HERO_ADM_SERVC_LOGINREQUEST loginObj;
	
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	HttpSession session = null; 
			

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerforgotpassword(String formData,
			HttpServletRequest httpRequest) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		
		HERO_ADM_SERVC_LOGINREQUEST request = (HERO_ADM_SERVC_LOGINREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL
				.convertJSONtooOBJECT(formData,
						"com.hero.forms.services.admin.login.HERO_ADM_SERVC_LOGINREQUEST");
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
	    
	    newPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", newPW);
	    
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_LOGIN_MODULE");
		inParamMap.put("P_USERID", request.getUsername());
		inParamMap.put("P_NEW_PASSWORD", newPW);
		inParamMap.put("P_OPRN", "FORGOT_PW");
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		response = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, response);
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

	

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO herosettingsurl(String modulename)
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
	error_log.info(e);
	/*e.printStackTrace();*/
	response.setResponseType("F");
	response.setResponseObj(e.getMessage());
	responseInfo.setInventoryResponse(response);
}
return responseInfo;
}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validLogin(String formData,final HttpServletRequest httpRequest) throws ClassNotFoundException {
			
		String tokenkey = "";
		String moduleUrl = "";
		session = httpRequest.getSession();
		JSONObject responseJSON;
		try {
			/*URL url = new URL("http://localhost:8080/herotokencentre/genherotokenkey");*/
			String applnurl=HERO_UTILS.getApplicationSontext(httpRequest).concat("/herotokencentre/genherotokenkey");
			//String applnurl="http://localhost:8080/herotokencentre/genherotokenkey";
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
				
				String applntype = (String) responseObj.get("applntype");
				String role = (String) responseObj.get("role");
				
				if(applntype.equals("0")){
					moduleUrl = "../heroadmin/HERO_ADM_SERVC_TOKEN_VALIDATOR?tokenkey=";
				}else if(applntype.equals("1")){
					moduleUrl = "../heropharmacy/HERO_TOKEN_VALIDATOR?tokenkey=";
				}else if(applntype.equals("2") &&( !role.equals("6"))){
					moduleUrl = "../heroclinic/HERO_CLC_SERVC_TOKEN_VALIDATOR?tokenkey=";
				}
				else if(applntype.equals("2") &&( role.equals("6"))){
					moduleUrl = "../heroreception/HERO_CLC_SERVC_TOKEN_VALIDATOR?tokenkey=";
				}
				else if(applntype.equals("3") ){
					moduleUrl = "../herolab/HERO_LAB_TOKEN_VALIDATOR?tokenkey=";
				}
				log.info("applntype   "+role);
				log.info("applntype   "+applntype);
				
				String username = (String) responseObj.get("username");
				String user_name = (String) responseObj.get("username");
				String userid = (String) responseObj.get("userid");

				
				session.setAttribute("usertype",Integer.parseInt((String)responseObj.get("role")));
				session.setAttribute("usertypestr",responseObj.get("role"));
				session.setAttribute("usertypedesc", responseObj.get("usertypedesc"));
				session.setAttribute("username",username.toUpperCase());
				session.setAttribute("user_name",user_name.toUpperCase());
				session.setAttribute("uid",responseObj.get("userid"));
				session.setAttribute("currencyname",responseObj.get("currencyname"));
				session.setAttribute("currencydecimal",responseObj.get("currencydecimal"));
				session.setAttribute("storeid", responseObj.get("storeid"));
			}
					session.setAttribute("tokenkey", tokenkey);
					
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
					
					HERO_ADM_SERVC_HOSURINVENTORYUTIL.smsSettingsList = smsSettingsList;
					String emailSettingsQuery = "SELECT * FROM `hero_email_settings`";
					
					@SuppressWarnings("unchecked")
					List<Object> emailSettingsList = jdbcTemplate.query(emailSettingsQuery,
							new RowMapper() {

								@Override
								public Object mapRow(ResultSet rs, int arg1)
										throws SQLException {
								
									Map<String, Object> emailMap = new HashMap<String, Object>();
									
									emailMap.put("emailid", rs.getString("email_id"));
									emailMap.put("emailpassword", rs.getString("email_password"));
									
									return emailMap;
								}
							});
					HERO_ADM_SERVC_HOSURINVENTORYUTIL.emailSettingsList = emailSettingsList;
					
					Map<String, Object> userDetailMap = new HashMap<String, Object>();
					userDetailMap.put("tokenkey", tokenkey);
					userDetailMap.put("moduleUrl", moduleUrl);
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
			//e1.printStackTrace();
			error_log.info(e1);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			error_log.info(e);
		}		
		

		return responseInfo;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO tokenSignout(String tokenkey)
			throws ClassNotFoundException {
		// TODO Auto-generated method stub
		 log.info("welcome to inner signioutn");
		String username = "",usertypedesc="",currencydecimal="",currencysymbol="";
		
		int tokenid=0,userid=0,role=0,currencyid=0,storeid=0;
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_TOKEN_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_TOKEN_ID", tokenid);
		inParamMap.put("P_TOKEN_KEY", tokenkey);
		inParamMap.put("P_TOKEN_LOGINOUT_STATUS", "1");
		inParamMap.put("P_TOKEN_UD_USERID", userid);
		inParamMap.put("P_TOKEN_UD_USERNAME", username);
		inParamMap.put("P_TOKEN_UD_ROLE", role);
		inParamMap.put("P_TOKEN_UD_CURRENCY_ID", currencyid);
		inParamMap.put("P_TOKEN_UD_USERTYPE_DESC", usertypedesc);
		inParamMap.put("P_TOKEN_UD_CURRENCY_SYMBOL", currencysymbol);
		inParamMap.put("P_TOKEN_UD_CURRENCY_DECIMAL", currencydecimal);
		inParamMap.put("P_TOKEN_UD_STORE_ID", storeid);
		inParamMap.put("P_OPERATION", "UPD");
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		response = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, response);
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	
	}

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO heroViewImage(String formData,HttpServletRequest httpRequest )
	 {
		String filePath = null ;
			try
			{
				HERO_ADM_SERVC_IMAGEREQUEST request = (HERO_ADM_SERVC_IMAGEREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL
					.convertJSONtooOBJECT(formData,
							"com.hero.forms.services.admin.login.HERO_ADM_SERVC_IMAGEREQUEST");
			// TODO Auto-generated method stub
				HttpSession session = httpRequest.getSession();
			String applntype = request.getApplntype();
			String requesttype = request.getRequesttype();
			
			int intApplntype = 0, intRequesttype = 0;
			if(applntype != null)
			{
				intApplntype = Integer.parseInt(applntype);
			}
			if(requesttype != null)
			{
				intRequesttype = Integer.parseInt(requesttype);
			}
			//log.info("Hero Product List   "+InventoryLOVImpl.HERO_ProductList.get(intIndex));
			if(intApplntype > 0)
			{
			byte[] imageData;
			File file = null;
			log.info("intApplntype value     "+intApplntype);
			String contentType = httpRequest.getSession().getServletContext().getMimeType("logo.jpg");
			/*Map<String, Object> prodMap = (Map<String, Object>) HERO_ADM_SERVC_INVENTORYLOVIMPL.HERO_ProductList.get(intIndex);
			log.info("prodMap: "+ prodMap);
			if(prodMap.get("logo") != null)
			{
				log.info("prodMap"+prodMap);
				byte[] logoData = (byte[]) prodMap.get("logo");
				response.setHeader("Content-Type", contentType);
				response.setHeader("Content-Length", String.valueOf(logoData.length));
				response.setHeader("Content-Disposition", "inline; filename=\"" + "logo.jpg" + "\"");
				response.getOutputStream().write(logoData);
			}*/
			if(intApplntype == 2){
				String imgname = request.getImgname();
				
				String selectQuery = " SELECT `hau_path` FROM `hero_admin_upload` WHERE `hau_appln_type` = "
						+ " '"+intApplntype+"' AND `hau_req_type` = '"+intRequesttype+"'";
				log.info("apath queruy   "+selectQuery);
				@SuppressWarnings("unchecked")
				List<Object> pathList = jdbcTemplate.query(selectQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						// TODO Auto-generated method stub
						/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
						
						HashMap<String, String> map = new HashMap<String, String>();
			
						map.put("path", rs.getString("hau_path"));
						
						return map;
					}
				});
	
				String path ="";
				if(pathList != null && pathList.size() > 0){
					Map<String, Object> map = (Map<String, Object>) pathList.get(0);
					path = (String) map.get("path");
					log.info("path    "+map.get("path"));
				}
				
				
				filePath = httpRequest.getSession().getServletContext().getRealPath("");
				File dir = new File(filePath);
				dir = dir.getParentFile();
				filePath = dir.getAbsolutePath()+File.separator+path+File.separator+imgname+".jpg";
				file = new File(filePath);
				log.info("filepath" + filePath);
				if (!file.exists()) {
					
					if(intRequesttype == 1)	{	
			    	filePath = httpRequest.getSession().getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultpatient.png";
			    	file = new File(filePath);	
					}else if(intRequesttype == 2){
						filePath = httpRequest.getSession().getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultphysician.png";
			        	file = new File(filePath);	
					}
			    	
			    	
			    }
	
				}
				
				/* }*/
				log.info("filePath       "+filePath);
				
				BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
				byte[] bytes = new byte[in.available()];
				in.read(bytes);
				in.close();
				response.setResponseObj(bytes);
				}else if(intRequesttype == 5){
					
					String serverPath = httpRequest.getServletContext().getRealPath("");					
					File dir = new File(serverPath);
					dir = dir.getParentFile();
					serverPath = dir.getAbsolutePath();					
					serverPath = serverPath+File.separator+"customermaster";
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(serverPath+File.separator+request.getImgname()));
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					in.close();
					response.setResponseObj(bytes);
				}
				else
				{
					
					
					
					filePath =httpRequest.getSession().getServletContext().getRealPath("");
					File dir = new File(filePath);
					dir = dir.getParentFile();
					filePath = dir.getAbsolutePath()+File.separator+"logo"+File.separator+"companylogo.jpg";
					File file = new File(filePath);
					if (!file.exists()) {
				
				        /*response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
				        return;*/
						filePath = httpRequest.getSession().getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultlogo.png";
				    	file = new File(filePath);
				    }// Set Content type
					
					 
					
					String selectQuery = "SELECT * FROM hero_orgn_table ORDER BY `created_at` DESC";
					@SuppressWarnings("unchecked")
					List<Object> orgnList = jdbcTemplate.query(selectQuery, new RowMapper() {
						
						@Override
						public Object mapRow(ResultSet rs, int arg1) throws SQLException {
							// TODO Auto-generated method stub
							/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
							
							HashMap<String, String> map = new HashMap<String, String>();
				
							map.put("orgnlogo", rs.getString("org_logo"));
							
							log.info("Company Suggestion List        "+map);
							
							return map;
						}
					});
				
					if(orgnList.size() == 0){
						filePath = httpRequest.getSession().getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultlogo.png";
				    	file = new File(filePath);
					}
				
					log.info("filePath       "+filePath);
				
					BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
					byte[] bytes = new byte[in.available()];
					in.read(bytes);
					in.close();
					response.setResponseObj(bytes);
				
				}
				
				response.setResponseType("S");
				//response.setResponseObj(bytes);
				responseInfo.setInventoryResponse(response);
				
				}
			catch(org.springframework.dao.DataIntegrityViolationException i)
			{
				log.error(i);
				response.setResponseType("F");
				response.setResponseObj(i.getMessage());
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

