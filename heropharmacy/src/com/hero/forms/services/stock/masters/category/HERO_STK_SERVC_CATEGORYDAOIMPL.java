package com.hero.forms.services.stock.masters.category;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.stock.forms.login.HERO_STK_LOGINDAOIMPL;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_STK_SERVC_CATEGORYDAOIMPL implements HERO_STK_SERVC_iCATEGORYDAO {
	
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_CATEGORYDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_STK_RESPONSE response;
	@Autowired
	private HERO_STK_RESPONSEINFO responseInfo;
	@Autowired
	private HEROHOSURINVENTORYUTIL inventoryUtilOBJ;
	
	@Override
	public HERO_STK_RESPONSEINFO savecategory(String categoryId,String categoryname,String userid, String oprn) {
		// TODO Auto-generated method stub

		try {

			 
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CATEGORY_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_CATEGORY_ID", categoryId);
			inParamMap.put("P_CATEGORY_NAME", categoryname);
			inParamMap.put("P_CREATED_BY", userid);
			inParamMap.put("P_OPERATION", oprn);
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
			
			response = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, response); 
			
			
		} catch (Exception e) {

error_log.info(e);
response = HEROHOSURINVENTORYUTIL.returnExceptionResponse(response, e);
		}
		responseInfo.setInventoryResponse(response);
		return responseInfo;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadcategories() {
		String selectQuery = "SELECT * FROM hero_admin_category ORDER BY CATEGORY_ID DESC";
		@SuppressWarnings("unchecked")
		List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				List<Object> details = new ArrayList<Object>();
				
				details.add(rs.getString("CATEGORY_NAME"));
				details.add("");
				details.add(rs.getString("CATEGORY_ID"));
				return details;
			}
		});
		
		response.setResponseType("S");
		response.setResponseObj(inventoryUtilOBJ.returnJSONobject(categoryList));
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

	@Override
	public HERO_STK_RESPONSEINFO getCategorySuggestions() {
		// TODO Auto-generated method stub
		String selectQuery = "select * from hero_admin_category";
		@SuppressWarnings("unchecked")
		List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("data", rs.getString("CATEGORY_ID"));
				map.put("value", rs.getString("CATEGORY_NAME"));
				
				/*detail.add(map);*/
		
				log.info("Category Suggestion List        "+map);
				
				return map;
			}
		});
		
		response.setResponseType("S");
		response.setResponseObj((categoryList));
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}

}
