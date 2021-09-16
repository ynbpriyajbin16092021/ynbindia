package com.hero.forms.services.approval.forgotpw;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_FORGOTPWDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_IFORGOTPWDAO{
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_FORGOTPWDAOIMPL.class);
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO resetpassword(String forgotpwid,
			HERO_ADM_SERVC_IFORGOTPWDAO currencyDAOobj) {

		log.info("forgotpwid        "+forgotpwid);
		
		try
		{
		  
			Map<String, Object> inParamMap = new HashMap<String, Object>(); 
		    
		    
		    
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_LOGIN_MODULE");
			inParamMap.put("P_USERID", forgotpwid);
			inParamMap.put("P_OLD_PASSWORD", "");
			inParamMap.put("P_NEW_PASSWORD", "");
			inParamMap.put("P_OPRN", "RESET_PW");
			
			log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			log.error(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO forgotpasswordlist() {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT `hufh_id`,a.`userid`,`username`,`hufh_sms_send`,DATE_FORMAT(`hufh_raised_at`,'%d/%m/%Y %H:%m')hufh_raised_at,`hufh_new_password`,`hufh_raised_count` "
		  		+ "FROM `hero_user_forgotpw_history` a JOIN `hero_user` b ON a.`userid` = b.`userid` WHERE `hufh_processed` = 0  ORDER BY `hufh_id` ASC";
		@SuppressWarnings("unchecked")
		List<Object> uomList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				List<String> detail = new ArrayList<String>();
				
				
				detail.add(rs.getString("username"));//0
				detail.add(rs.getString("hufh_raised_at"));//1
				detail.add(HERO_ADM_SERVC_HOSURINVENTORYUTIL.decrypt("Herbzaliveerpapp", "ppapreevilazbreH",rs.getString("hufh_new_password")));//2
				detail.add(rs.getString("hufh_raised_count"));//2
				detail.add("");//3
				detail.add(rs.getString("userid"));//4
				detail.add(rs.getString("hufh_id"));//5
				return detail;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnJSONobject(uomList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
