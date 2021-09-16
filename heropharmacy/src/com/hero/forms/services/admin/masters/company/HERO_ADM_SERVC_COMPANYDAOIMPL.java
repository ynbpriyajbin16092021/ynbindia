package com.hero.forms.services.admin.masters.company;

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

import com.hero.forms.services.stock.masters.category.HERO_STK_SERVC_CATEGORYDAOIMPL;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_ADM_SERVC_COMPANYDAOIMPL implements HERO_ADM_SERVC_ICOMPANYDAO{

	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_COMPANYDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");

	@Autowired
	HERO_STK_RESPONSE inventoryResponseOBJ;
	@Autowired
	HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	@Override
	public HERO_STK_RESPONSEINFO savecompany(String companyData,HERO_ADM_SERVC_ICOMPANYDAO comapnyDAOobj) {

		log.info("companyData        "+companyData);
		
		try
		{
		HERO_ADM_SERVC_COMPANYREQUEST request = (HERO_ADM_SERVC_COMPANYREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(companyData, "com.hero.forms.services.admin.masters.company.HERO_ADM_SERVC_COMPANYREQUEST");
		
		log.info("Values are     "+request.getCompanyid()+"   "+request.getCompanyname()+"   "+request.getStatus()+"   "+request.getOprn());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_COMPANY_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_COMPANY_ID", request.getCompanyid());
		inParamMap.put("P_COMPANY_NAME", request.getCompanyname());
		inParamMap.put("P_STATUS", request.getStatus());
		inParamMap.put("P_OPRN", request.getOprn());
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getCompanyList(HERO_ADM_SERVC_ICOMPANYDAO companyDAOobj) {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT * FROM hero_admin_company ORDER BY COMPANY_id DESC";
		List<Object> companyList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				List<String> detail = new ArrayList<String>();
				
				detail.add(rs.getString("COMPANY_NAME"));//0
				detail.add("");//4
				detail.add(rs.getString("COMPANY_id"));//5
				
				return detail;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(HEROHOSURINVENTORYUTIL.returnJSONobject(companyList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
