package com.hero.services.admin.util;


import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.google.gson.Gson;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSE;

public class HERO_ADM_SERVC_HOSURINVENTORYUTIL{

	public static String DATATABLE_ACTION = "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\""
			+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>";
	
	public static List<Object> smsTemplateList = new ArrayList<Object>();
	public static List<Object> smsSettingsList = new ArrayList<Object>();
	public static List<Object> emailSettingsList = new ArrayList<Object>();
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_HOSURINVENTORYUTIL.class);

	@Value("${PROD.INS}")
	private static String message;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public static Object convertJSONtooOBJECT(String formData, String className)
			throws ClassNotFoundException {
		Class myClass = Class.forName(className);
		Gson gson = new Gson();
		Object request = gson.fromJson(formData, myClass);
		return request;
	}
	
	public static Object convertJSONtooOBJECT(String formData, Class className)
			throws ClassNotFoundException {
		/*Class myClass = Class.forName(className);*/
		Gson gson = new Gson();
		Object request = gson.fromJson(formData, className);
		return request;
	}

	public static ResponseEntity<Object> returnExceptionFormat(Exception e) {
		return new ResponseEntity<Object>(e.getMessage(), new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	public static String returnMessage()
	{
		return message;
	}
	
	public static String returnJSONobject(List<Object> list)
	{
		String JSON = new Gson().toJson(list);
		return JSON;
	}
	
	public static String returnJSONobject(Object obj)
	{
		String JSON = new Gson().toJson(obj);
		return JSON;
	}
	
	public static HERO_ADM_SERVC_INVENTORYRESPONSE returnResponse(Map<String, Object> resultMap,HERO_ADM_SERVC_INVENTORYRESPONSE inventoryResponseOBJ)
	{
		if(resultMap != null)
		{
			if(resultMap.get("#update-count-1") != null && ((int)resultMap.get("#update-count-1")) > 0)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", resultMap.get("out_genrate_id"));
				map.put("msg", resultMap.get("out_result_msg"));
				log.info(map);
				inventoryResponseOBJ.setResponseType((String)resultMap.get("out_result_type"));
				inventoryResponseOBJ.setResponseObj(map);
			}
			else
			{
				inventoryResponseOBJ.setResponseType("F");
				inventoryResponseOBJ.setResponseObj((String)resultMap.get("out_result_msg"));
			}
		}
		return inventoryResponseOBJ;
	}
	
	public static HERO_ADM_SERVC_INVENTORYRESPONSE returnExceptionResponse(HERO_ADM_SERVC_INVENTORYRESPONSE inventoryResponseOBJ,Exception e)
	{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", "0");
		
		if(e instanceof org.springframework.dao.DataIntegrityViolationException)
		{
			map.put("msg", "Dependent Data Exists. You cannot delete this data");	
		}
		else
		{
			map.put("msg", "System Error Occured. Please Contact System Administrator");
		}
		
		log.info(map);
		inventoryResponseOBJ.setResponseType("F");
		inventoryResponseOBJ.setResponseObj(map);
		
		/*inventoryResponseOBJ.setResponseType("F");
		inventoryResponseOBJ.setResponseObj("System Error Occured. Please Contact System Administrator");*/
		
		return inventoryResponseOBJ;
	}
	
	public static String getDataTableActionString(String editMethodName,String deleteMethodName)
	{
		editMethodName = "(\""+editMethodName+"\")";
		deleteMethodName = "(\""+deleteMethodName+"\")";
		
		String action = "<button class=\"edit myBtnTab\" onclick="+editMethodName+"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\" data-toggle=\"modal\" data-target=\"#modal-delet\" onclick="+deleteMethodName+"> <i class=\"fa fa-trash-o\"></i> </button>";
		
		log.info("MethodNames "+editMethodName+"   "+deleteMethodName+"     "+action);
		
		return action;
	}
	
	public static <T> List<T> convertJSONArraytoList(String jsonArrayString,String className) throws JSONException, ClassNotFoundException
	{
		List<T> list = new ArrayList<T>();
		JSONArray jsonArr = new JSONArray(jsonArrayString);
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			T item = (T) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(jsonObj.toString(), className);
			list.add(item);
		}
		return list;
	}
	
	public static Date convertToSQLDate(String stringDate) throws ParseException
	{
		Date parsedstringDate = null;
		java.sql.Date sqlDate = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		parsedstringDate = format.parse(stringDate);
		sqlDate = new java.sql.Date(parsedstringDate.getTime());

		
		log.info("Dates        "+parsedstringDate+"   "+sqlDate);			
		
		return sqlDate;
		}
public static String inventoryformactionscript(String visibleaction1,String visibleaction2)
{
	return "<button class=\"edit myBtnTab\"style=\"display:"+visibleaction1+";\"> <i class=\"fa fa-edit\"></i> </button><button style=\"display:"+visibleaction2+";\" class=\"delete myBtnTab\" data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>";
	
}

public static String encrypt(String key, String initVector, String value) {
    try {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(value.getBytes());
        log.info("ERP Encrypt Value: "
                + Base64.encodeBase64String(encrypted));

        return Base64.encodeBase64String(encrypted);
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return null;
}

public static String decrypt(String key, String initVector, String encrypted) {
    try {
        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

        byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));

        return new String(original);
    } catch (Exception ex) {
        ex.printStackTrace();
    }

    return null;
}

public static int startIndex(int pageNo)
{
	int start = ((pageNo - 1) * 10);
	log.info("Start   "+start);
	return start;
}

public static int endIndex(int pageNo)
{
	int end = ((pageNo) * 10);
	log.info("   End   "+end);
	return end;
}

public static Object getValueFromList(Map<String, Object> map,String mapKey)
{
	Object value = 0;
	List<Object> List = (List<Object>) map.get(mapKey);
	
	if(List != null && List.size() > 0)
	{
		value = List.get(0);
	}
	else
	{
		value = 0;
	}
	
	return value;
}

public static Object executeQuery(String query,JdbcTemplate jdbcTemplateObj)
{
	Object value = 0;
	try
	{
		@SuppressWarnings("unchecked")
		List<Object> outputList = jdbcTemplateObj.query(query, new RowMapper() {
			 
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {

				  String value = rs.getString(1);
				  
				  return value;
			}
		});
		
		value = outputList; 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return value;
}

public static Object executeQueryWithList(String query,JdbcTemplate jdbcTemplateObj)
{
	Object value = 0;
	try
	{
		@SuppressWarnings("unchecked")
		List<Object> outputList = jdbcTemplateObj.query(query, new RowMapper() {
			 
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				ResultSetMetaData rsmd = rs.getMetaData();
				int columnsNumber = rsmd.getColumnCount();
				
				for(int columnLoop = 1;columnLoop <= columnsNumber;columnLoop++)
				{
					String columnName = rsmd.getColumnName(columnLoop);
					String value = rs.getString(columnLoop);
					map.put(columnName, value);
				}
				
				return map;
			}
		});
		
		value = outputList; 
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return value;
}

public static String convertDecimalFormat(int convertRange, double value){

	BigDecimal bd = new BigDecimal(value);
	bd = bd.setScale(convertRange,BigDecimal.ROUND_HALF_UP);
	return bd.toString();
}


public static String IndianFormat(BigDecimal n) {
    DecimalFormat formatter = new DecimalFormat("#,###.00");
    //we never reach double digit grouping so return
    if (n.doubleValue() < 100000) {
        return formatter.format(n.setScale(2, 1).doubleValue());
    }
    StringBuffer returnValue = new StringBuffer();
    //Spliting integer part and decimal part
    String value = n.setScale(2, 1).toString();
    String intpart = value.substring(0, value.indexOf("."));
    String decimalpart = value.substring(value.indexOf("."), value.length());
    //switch to double digit grouping
    formatter.applyPattern("#,##");
    returnValue.append(formatter.format(new BigDecimal(intpart).doubleValue() / 1000)).append(",");
    //appending last 3 digits and decimal part
    returnValue.append(intpart.substring(intpart.length() - 3, intpart.length())).append(decimalpart);
    //returning complete string
    return returnValue.toString();
}
public static int sendSMS(JdbcTemplate jdbcTemplate,String mobieno, String messageContent){
	   
	int sendstatus = 0;
	try{
	
	SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_INSERT_SMS_HISTORY");
	Map<String, Object> inParamMap = new HashMap<String, Object>();
	inParamMap.put("P_ISH_ID", 0);
	inParamMap.put("P_ISH_MOB_NO", mobieno);
	inParamMap.put("P_ISH_SMS_CONTENT", messageContent);
	inParamMap.put("P_ISH_STATUS", 0);
	inParamMap.put("P_SMS_RESPONSE", "");
	inParamMap.put("P_ACTION", "INS");
	
	//log.info("sendSMSNotification inParamMap         "+inParamMap);
	SqlParameterSource in = new MapSqlParameterSource(inParamMap);
	Map<String, Object> resultMap = jdbcCALL.execute(in);
	log.info(resultMap);
	String status = (String)resultMap.get("out_genrate_id");
	if(status == "1"){
		sendstatus = 1;
	}
	
	}catch (Exception e) {
		sendstatus = 0;
		log.info("Error SMS "+e);
	}
   
   return sendstatus;
}
public static int sendMail(JdbcTemplate jdbcTemplate,String to,String msg, String subject){
	int sendstatus = 0;
	try{
	
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_INSERT_EMAIL_HISTORY");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_IMH_ID", 0);
		inParamMap.put("P_IMH_MAIL_ID", to);
		inParamMap.put("P_IMH_MAIL_SUBJECT", subject);
		inParamMap.put("P_IMH_MAIL_CONTENT", msg);
		inParamMap.put("P_IMH_STATUS", 0);
		inParamMap.put("P_IMH_RESPONSE", "");
		inParamMap.put("P_ACTION", "INS");
		
		log.info("sendSMSNotification inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
	String status = (String)resultMap.get("out_genrate_id");
	if(status == "1"){
		sendstatus = 1;
	}
	
	}catch (Exception e) {
		sendstatus = 0;
		log.info("Error SMS "+e);
	}
   
   return sendstatus;
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
