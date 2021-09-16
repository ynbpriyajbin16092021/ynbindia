package com.hero.forms.services.admin.masters.bank;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;



import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_ADM_SERVC_BANKDAOIMPL implements HERO_ADM_SERVC_IBANKDAO{
private static Logger log = Logger.getLogger(HERO_ADM_SERVC_BANKDAOIMPL.class);
private static Logger error_log = Logger.getLogger("requestAppender");	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_STK_RESPONSE response;
	@Autowired
	private HERO_STK_RESPONSEINFO responseInfo;
	@Autowired
	private HEROHOSURINVENTORYUTIL inventoryUtilOBJ;

	
	public HERO_STK_RESPONSEINFO loadbanks() {
		String selectQuery = "SELECT `bank_id`,`bank_name` FROM `hero_admin_bank`";
		log.info("selectQuery  "+selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> bankList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				List<Object> details = new ArrayList<Object>();
				
				details.add(rs.getString("bank_name"));
				details.add("");
				details.add(rs.getString("bank_id"));
				return details;
			}
		});
		
		response.setResponseType("S");
		response.setResponseObj(inventoryUtilOBJ.returnJSONobject(bankList));
		
		responseInfo.setInventoryResponse(response);
		
		return responseInfo;
	}


	@Override
	public HERO_STK_RESPONSEINFO savebank(String bankId, String bankname,
			String userid, String oprn) {
try {

			 
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_BANK_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_BANK_ID", bankId);
			inParamMap.put("P_BANK_NAME", bankname);
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
	
	
}
