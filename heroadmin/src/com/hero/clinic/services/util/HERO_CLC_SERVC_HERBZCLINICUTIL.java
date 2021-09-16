package com.hero.clinic.services.util;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
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
import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSE;

public class HERO_CLC_SERVC_HERBZCLINICUTIL extends HERO_CLC_SERVC_CLINICDAO{

	
	public static String DATATABLE_ACTION = "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\""
			+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>";
	
	
	private static Logger log = Logger.getLogger(HERO_CLC_SERVC_HERBZCLINICUTIL.class);

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
	
	public static HERO_CLC_SERVC_CLINICRESPONSE returnResponse(Map<String, Object> resultMap,HERO_CLC_SERVC_CLINICRESPONSE inventoryResponseOBJ)
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
	
	public static HERO_CLC_SERVC_CLINICRESPONSE returnExceptionResponse(HERO_CLC_SERVC_CLINICRESPONSE inventoryResponseOBJ,Exception e)
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
			T item = (T) HERO_CLC_SERVC_HERBZCLINICUTIL.convertJSONtooOBJECT(jsonObj.toString(), className);
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

public static int generateImageSeqforImageuplaod(JdbcTemplate jdbcTemplate,String uploadtype)
{
	int seqno = 0;
	try
	{
		SimpleJdbcCall jdbcCALL = null;
		Map<String, Object> inParamMap = null;
		SqlParameterSource in = null;
		if(uploadtype != null && uploadtype.equalsIgnoreCase("DOC"))
		{
			jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_DOCTOR_CREATION");
			inParamMap = new HashMap<String, Object>();
			
			inParamMap.put("P_DOCTOR_ID", "");//0
			inParamMap.put("P_PREFIX", "");//1
			inParamMap.put("P_FIRSTNAME", "");//2
			inParamMap.put("P_LASTNAME", "");//3
			inParamMap.put("P_GENDER", "");//4
			inParamMap.put("P_SPECIALITY", "");//5
			inParamMap.put("P_DOB", HERO_CLC_SERVC_HERBZCLINICUTIL.convertToSQLDate("01/01/2000"));//6
			inParamMap.put("P_MOBNO", "");//7
			inParamMap.put("P_EMAIL", "");//8
			inParamMap.put("P_ADDRESS", "");//9
			inParamMap.put("P_CITY", "");//10
			inParamMap.put("P_STATE", "");//11
			inParamMap.put("P_ZIPCODE", 0);//12
			inParamMap.put("P_COUNTRY", "");//13
			inParamMap.put("P_CLINIC_ID", 0);//13
			inParamMap.put("P_IMGPATH", "");//14
			inParamMap.put("P_USER_ID", "");//15
			inParamMap.put("P_OPRN", "IMG_SEQ");//16
			
			log.info("inParamMap         "+inParamMap);
			
			in = new MapSqlParameterSource(inParamMap);
			
			log.info("jdbcCALL   "+jdbcCALL+"   "+in);
		}
		else if(uploadtype != null && uploadtype.equalsIgnoreCase("PAT"))
		{

			jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_PATIENT_CREATION");
			inParamMap = new HashMap<String, Object>();
			
			inParamMap.put("P_PATIENT_ID", 0);//0
			inParamMap.put("P_FIRSTNAME", "");//1
			inParamMap.put("P_LASTNAME", "");//2
			inParamMap.put("P_DOB", HERO_CLC_SERVC_HERBZCLINICUTIL.convertToSQLDate("01/01/2000"));//3
			inParamMap.put("P_GENDER", "");//4
			inParamMap.put("P_MARTIAL", "");//5
			inParamMap.put("P_MOBNO", "");//6
			inParamMap.put("P_ADDRESS", "");//7
			inParamMap.put("P_CITY", "");//8
			inParamMap.put("P_STATE", "");//9
			inParamMap.put("P_PINCODE", 0);//10
			inParamMap.put("P_MEDICAL_COMMENTS", "");//11
			inParamMap.put("P_NOMINEE_NAME", "");//12
			inParamMap.put("P_EMAIL", "");//13
			inParamMap.put("P_IMAGEPATH", "");//14
			inParamMap.put("P_USERID", "");//15
			inParamMap.put("P_OPRN", "IMG_SEQ");//16
			inParamMap.put("P_BLOODGROUP", "");//17
			inParamMap.put("P_CLINICID", 0);//18
			
			log.info("inParamMap         "+inParamMap);
			
			in = new MapSqlParameterSource(inParamMap);
			
			log.info("jdbcCALL   "+jdbcCALL+"   "+in);
		
		}
		
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_genrate_id"));
	
		if(resultMap.get("out_genrate_id") != null)
		{
			seqno = (int)resultMap.get("out_genrate_id");
		}
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
	return seqno;
}

public static String getNameValue(String value)
{
	return value == null ? "" : value;
}

public static String AMPM_HourFormat(String _24HourTime) throws ParseException
{
	String _12HourTime = "";
    SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
    SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
    Date _24HourDt = _24HourSDF.parse(_24HourTime);
    /*log.info(_24HourDt);
    log.info(_12HourSDF.format(_24HourDt));*/
    _12HourTime = _12HourSDF.format(_24HourDt);
    
    return _12HourTime;
}

public static long timedifference(String startTime,String endTime) throws ParseException
{
	
    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
    Date d1 = sdf.parse(startTime);
    Date d2 = sdf.parse(endTime);
    long elapsed = d2.getTime() - d1.getTime();
    
    return elapsed;
}

public static String getCurrenttime()
{
	DateFormat timeFormat = new SimpleDateFormat("HH:mm");
	Date currentDate = new Date();
	String currentTime = timeFormat.format(currentDate);
	return currentTime;
}

public static String getCurrentDate()
{
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	Date systemDate = new Date();
	String currentDate = dateFormat.format(systemDate);
	return currentDate;
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


}

