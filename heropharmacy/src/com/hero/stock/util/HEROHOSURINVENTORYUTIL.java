package com.hero.stock.util;


import java.io.File;
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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.CellRangeAddressList;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFSheet;
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
import com.hero.stock.response.HERO_STK_RESPONSE;

public class HEROHOSURINVENTORYUTIL{

	public static String DATATABLE_ACTION = "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\""
			+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>";
	
	
	public static String DATATABLE_ACTION1
	= "<button class=\"edit myBtnTab\" onclick=\"viewPurchaseReqeustProducts()\"> <i class=\"fa fa-eye\"></i> </button>\"  ";
	
	
	/*public static List<Object> smsTemplateList = new ArrayList<Object>();
	public static List<Object> smsSettingsList = new ArrayList<Object>();*/
	
	public static List<Object> tokenList = new ArrayList<Object>();
	
	private static Logger log = Logger.getLogger(HEROHOSURINVENTORYUTIL.class);

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
/*		Class myClass = Class.forName(className);*/
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
	
	public static HERO_STK_RESPONSE returnResponse(Map<String, Object> resultMap,HERO_STK_RESPONSE inventoryResponseOBJ)
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
	
	public static List<String> getStringList(JdbcTemplate jdbcTemplate,String query)
	{
		
		List<String> list = jdbcTemplate.query(query, new RowMapper() {
			@Override
			public String mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				return rs.getString(1);
			}
		});
		return list;
	}
	
	public static String[] convertListtoStringArray(List<String> list)
	{
		String[] listArr = list.toArray(new String[0]);
		return listArr;
	}
	
	public static DataValidation getCellList(DataValidationHelper validationHelper,String[] cellArray,XSSFSheet sheet,boolean dropDownArrow,int EndRow,int Column)
	{
		DataValidation dataValidation = null;
		DataValidationConstraint constraint = null;
		
		
		CellRangeAddressList brandCellList = new  CellRangeAddressList(1,EndRow,Column,Column);
		constraint =validationHelper.createExplicitListConstraint(cellArray);
		dataValidation = validationHelper.createValidation(constraint, brandCellList);
		dataValidation.setSuppressDropDownArrow(dropDownArrow);      
		sheet.addValidationData(dataValidation);
		
		return dataValidation;
	}
	
	public static HERO_STK_RESPONSE returnExceptionResponse(HERO_STK_RESPONSE inventoryResponseOBJ,Exception e)
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
			T item = (T) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(jsonObj.toString(), className);
			list.add(item);
		}
		return list;
	}
	
	public static <T> List<T> convertJSONArraytoList(String jsonArrayString,Class className) throws JSONException, ClassNotFoundException
	{
		List<T> list = new ArrayList<T>();
		JSONArray jsonArr = new JSONArray(jsonArrayString);
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONObject jsonObj = jsonArr.getJSONObject(i);
			T item = (T) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(jsonObj.toString(), className);
			list.add(item);
		}
		return list;
	}
	
	
	public static <T> List<T> convertJSONArraytoArrayList(String jsonArrayString,String className) throws JSONException, ClassNotFoundException
	{
		
		List<T> Mainlist = new ArrayList<T>();
		JSONArray jsonArr = new JSONArray(jsonArrayString);
		
		for (int i = 0; i < jsonArr.length(); i++) {
			JSONArray jsonarray = jsonArr.getJSONArray(i);
			List<T> list = new ArrayList<T>();
			log.info(jsonarray);
			for (int j = 0; j < jsonarray.length(); j++) {
				JSONObject jsonObj = jsonarray.getJSONObject(j);
				log.info(jsonObj);
				T item = (T) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(jsonObj.toString(), className);
				list.add(item);
				log.info(item);
			}
			Mainlist.add((T) list);
		}
		return Mainlist;
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
	
	public static Date convertToSQLDateTime(String stringDate) throws ParseException
	{
		Date parsedstringDate = null;
		java.sql.Date sqlDate = null;
		
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
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

private static final String[] tensNames = {
    "",
    " Ten",
    " Ttwenty",
    " Thirty",
    " Forty",
    " Fifty",
    " Sixty",
    " Seventy",
    " Eighty",
    " Ninety"
  };

  private static final String[] numNames = {
    "",
    " One",
    " Two",
    " Three",
    " Four",
    " Five",
    " Six",
    " Seven",
    " Eight",
    " Nine",
    " Ten",
    " Eleven",
    " Twelve",
    " Thirteen",
    " Fourteen",
    " Fifteen",
    " Sixteen",
    " Seventeen",
    " Eighteen",
    " Nineteen"
  };

  
  private static String convertLessThanOneThousand(int number) {
	    String soFar;

	    if (number % 100 < 20){
	      soFar = numNames[number % 100];
	      number /= 100;
	    }
	    else {
	      soFar = numNames[number % 10];
	      number /= 10;

	      soFar = tensNames[number % 10] + soFar;
	      number /= 10;
	    }
	    if (number == 0) return soFar;
	    return numNames[number] + " hundred" + soFar;
	  }


	  public static String convertNumToString(long number) {
	    // 0 to 999 999 999 999
	    if (number == 0) { return "Zero"; }

	    String snumber = Long.toString(number);

	    // pad with "0"
	    String mask = "000000000000";
	    DecimalFormat df = new DecimalFormat(mask);
	    snumber = df.format(number);

	    // XXXnnnnnnnnn
	    int billions = Integer.parseInt(snumber.substring(0,3));
	    // nnnXXXnnnnnn
	    int millions  = Integer.parseInt(snumber.substring(3,6));
	    // nnnnnnXXXnnn
	    int hundredThousands = Integer.parseInt(snumber.substring(6,9));
	    // nnnnnnnnnXXX
	    int thousands = Integer.parseInt(snumber.substring(9,12));

	    String tradBillions;
	    switch (billions) {
	    case 0:
	      tradBillions = "";
	      break;
	    case 1 :
	      tradBillions = convertLessThanOneThousand(billions)
	      + " Billion ";
	      break;
	    default :
	      tradBillions = convertLessThanOneThousand(billions)
	      + " Billion ";
	    }
	    String result =  tradBillions;

	    String tradMillions;
	    switch (millions) {
	    case 0:
	      tradMillions = "";
	      break;
	    case 1 :
	      tradMillions = convertLessThanOneThousand(millions)
	         + " Million ";
	      break;
	    default :
	      tradMillions = convertLessThanOneThousand(millions)
	         + " Million ";
	    }
	    result =  result + tradMillions;

	    String tradHundredThousands;
	    switch (hundredThousands) {
	    case 0:
	      tradHundredThousands = "";
	      break;
	    case 1 :
	      tradHundredThousands = "One Thousand ";
	      break;
	    default :
	      tradHundredThousands = convertLessThanOneThousand(hundredThousands)
	         + " Thousand ";
	    }
	    result =  result + tradHundredThousands;

	    String tradThousand;
	    tradThousand = convertLessThanOneThousand(thousands);
	    result =  result + tradThousand;

	    // remove extra spaces!
	    return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
	  }
	  
		
	  public static String getCreditmode(String supplierid,String prhdr_id,String defaultpaymode,JdbcTemplate jdbcTemplateObj){
	  	String paymentmode = defaultpaymode;
	  	log.info("paymentmode"+     paymentmode); 
	  	try{
	  	List<Object> List = getDefaultCreditmode(supplierid,jdbcTemplateObj);
	  	int creditmode  = 0;
	  	if(List != null)
	  	{
	  		Map<String, Object> creditmap = (Map<String, Object>) List.get(0);
	  		creditmode = (int) creditmap.get("creditmode");	
	  	}
	  	
	  	 
	  	
	  	if(creditmode == 1){
	  		String payquery = "SELECT COUNT(*) alreadycredited FROM `hero_stock_supplier` JOIN `hero_stock_purchase_payment` WHERE `prhdr_id` = '"+prhdr_id+"' "
	  				+ " AND `supplier_id` = '"+supplierid+"' AND `pp_payment_status` = 7";
	  		log.info(payquery);
	  		@SuppressWarnings("unchecked")
	  		List<Object> PayList = jdbcTemplateObj.query(payquery.toString(), new RowMapper() {
	  			
	  			@Override
	  			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
	  				// TODO Auto-generated method stub
	  				Map<String, Object> map = new HashMap<String, Object>();
	  				map.put("alreadycredited", rs.getInt("alreadycredited"));
	  				return map;
	  				}
	  		});
	  		
	  		Map<String, Object> alreadycreditedmap = (Map<String, Object>) PayList.get(0);
	  		int alreadycreditedmode = 0;
	  		alreadycreditedmode = (int) alreadycreditedmap.get("alreadycredited");
	  		
	  		if(alreadycreditedmode == 1){
	  			paymentmode = defaultpaymode;
	  		}else{
	  			paymentmode = "5";
	  		}
	  		
	  	}
	  	
	  	}catch(Exception e){
	  		e.printStackTrace();
	  	}
	  	
	  	return paymentmode;
	  }
	  
	  public static List getDefaultCreditmode(String supplierid,JdbcTemplate jdbcTemplateObj)
	  {
	  	StringBuilder query = new StringBuilder("SELECT `credit_mode`,`paymode`,`reqdays`,' ' supplier_invoice_no FROM `hero_stock_supplier` WHERE `supplier_id` = '"+supplierid+"' ");
	  	log.info("getDefaultCreditmode       "+query);
	  	int creditmode = 0;
	  	try{
	  	@SuppressWarnings("unchecked")
	  	List<Object> List = jdbcTemplateObj.query(query.toString(), new RowMapper() {
	  		
	  		@Override
	  		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
	  			// TODO Auto-generated method stub
	  			Map<String, Object> map = new HashMap<String, Object>();
	  			map.put("creditmode", rs.getInt("credit_mode"));
	  			map.put("paymode", rs.getInt("paymode"));
	  			map.put("reqdays", rs.getInt("reqdays"));
	  			map.put("supplierinvoiceno", rs.getInt("supplier_invoice_no"));
	  			return map;
	  		}
	  	});
	  	
	  	
	  	return List;
	  	
	  	}catch(Exception e){
	  		
	  	}
	  	return null;
	  	}


	/*  public Map<String, Object> getPayMode(String prhdr_id){
			
			String supplieridquery = "select supplier_id from `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` "
					+ "ON `purchase_code` = `pur_req_id` where a.`prhdr_id` = "+prhdr_id;
			
			@SuppressWarnings("unchecked")
			List<Object> SupplierList = jdbcTemplate.query(supplieridquery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("supplier_id", rs.getString("supplier_id"));
					return map;
				}
				});
			
			Map<String, Object> Suppliermap = (Map<String, Object>) SupplierList.get(0);
			String supplierid = (String) Suppliermap.get("supplier_id");
			
			List<Object> List = HEROHOSURINVENTORYUTIL.getDefaultCreditmode(supplierid,jdbcTemplate);
			int defaultpaymode  = 0;
			int reqdaysint = 0;
			if(List != null)
			{
				Map<String, Object> creditmap = (Map<String, Object>) List.get(0);
				defaultpaymode = (int) creditmap.get("paymode");
				reqdaysint = (int) creditmap.get("reqdays");
			}
			
			String paymode = HEROHOSURINVENTORYUTIL.getCreditmode(supplierid,prhdr_id,String.valueOf(defaultpaymode),jdbcTemplate);
			String reqdays = String.valueOf(reqdaysint);
			
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("paymode", paymode);
			resultmap.put("reqdays", reqdays);
			return resultmap;
		}*/
}
