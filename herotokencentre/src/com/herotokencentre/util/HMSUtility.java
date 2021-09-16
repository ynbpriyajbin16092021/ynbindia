package com.herotokencentre.util;
 
import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.simple.JSONObject;





import com.google.gson.Gson;
import com.herotokencentre.controller.model.HMSBeans;
import com.herotokencentre.controller.response.HMSResponseBean;

public class HMSUtility extends HMSBeans{

	
	public static HMSResponseBean returnResponse(Map<String, Object> resultMap,HMSResponseBean responsebeanObj)
	{
		if(resultMap != null)
		{
			if(resultMap.get("#update-count-1") != null && ((int)resultMap.get("#update-count-1")) > 0)
			{
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", resultMap.get("out_genrate_id"));
				map.put("msg", resultMap.get("out_result_msg"));
				//System.out.println(map);
				responsebeanObj.setResponseType((String)resultMap.get("out_result_type"));
				responsebeanObj.setResponseObj(map);
			}
			else
			{
				/*responsebeanObj.setResponseType("F");
				responsebeanObj.setResponseObj((String)resultMap.get("out_result_msg"));*/
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("id", 0);
				map.put("msg", resultMap.get("out_result_msg"));
				//System.out.println(map);
				responsebeanObj.setResponseType((String)resultMap.get("out_result_type"));
				responsebeanObj.setResponseObj(map);
				
			}
		}
		return responsebeanObj;
	}
	
	public static HMSResponseBean returnExceptionResponse(HMSResponseBean responseBeanObj,Exception e)
	{
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("id", "0");
		
		if(e instanceof org.springframework.dao.DataIntegrityViolationException)
		{
			map.put("msg", "Record Already Exists");	
		}
		else
		{
			map.put("msg", "System Error Occured. Please Contact System Administrator");
		}
		
		//System.out.println(map);
		responseBeanObj.setResponseType("F");
		responseBeanObj.setResponseObj(map);
		
		return responseBeanObj;
	}
	
	public static String encrypt(String key, String initVector, String value) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

	        byte[] encrypted = cipher.doFinal(value.getBytes());
	        //System.out.println("ERP Encrypt Value: "+ Base64.encodeBase64String(encrypted));

	        return Base64.encodeBase64String(encrypted);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return null;
	}

	public static String decrypt(String key, String initVector, String encryptvalue) {
	    try {
	        IvParameterSpec iv = new IvParameterSpec(initVector.getBytes("UTF-8"));
	        SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

	        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
	        cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);

	        byte[] original = cipher.doFinal(Base64.decodeBase64(encryptvalue));

	        return new String(original);
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }

	    return null;
	}

	public static Object convertJSONtooOBJECT(String formData, String className)
			throws ClassNotFoundException {
		Class myClass = Class.forName(className);
		Gson gson = new Gson();
		Object request = gson.fromJson(formData, myClass);
		return request;
	}
}
