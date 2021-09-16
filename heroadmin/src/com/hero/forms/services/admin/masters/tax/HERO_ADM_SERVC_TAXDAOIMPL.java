package com.hero.forms.services.admin.masters.tax;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;








import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_TAXDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_ITAXDAO{
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_TAXDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savetax(String taxData, HERO_ADM_SERVC_ITAXDAO taxDAOobj) {

		log.info("taxData        "+taxData);
		
		try
		{
		HERO_ADM_SERVC_TAXREQUEST request = (HERO_ADM_SERVC_TAXREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(taxData, "com.hero.forms.services.admin.masters.tax.HERO_ADM_SERVC_TAXREQUEST");
		
		log.info("Values are     "+request.getTaxid()+"   "+request.getTaxname()+"   "+request.getTaxtype()+"   "+request.getTaxcode()+"   "
				+request.getTaxrate()+"   "+request.getOprn());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_TAX_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_TAX_ID", request.getTaxid());
		inParamMap.put("P_TAX_NAME", request.getTaxname());
		inParamMap.put("P_TAX_TYPE", request.getTaxtype());
		inParamMap.put("P_TAX_CODE", request.getTaxcode());
		inParamMap.put("P_TAX_RATE", request.getTaxrate());
		inParamMap.put("P_STATUS_ID", request.getStatusid());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadtax() {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT * FROM hero_admin_tax ORDER BY `TAX_ID` DESC ";
		@SuppressWarnings("unchecked")
		List<Object> uomList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				List<String> detail = new ArrayList<String>();
				String taxtype="";
				if(rs.getString("tax_type") != null)
				{
					if(rs.getString("tax_type").equalsIgnoreCase("P"))
					{
						taxtype = "Percentage";
					}
					else if(rs.getString("tax_type").equalsIgnoreCase("F"))
					{
						taxtype = "Fixed";
					}
				}
				
				detail.add(rs.getString("tax_name"));//0
				detail.add(rs.getString("tax_code"));//1
				detail.add(rs.getString("tax_rate"));//2
				detail.add(taxtype);//3
				detail.add("");//4
				detail.add(rs.getString("tax_id"));//5
				detail.add(rs.getString("tax_type"));//6
				return detail;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnJSONobject(uomList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
