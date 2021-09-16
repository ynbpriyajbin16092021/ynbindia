package com.hero.forms.services.admin.masters.currency;

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

public class HERO_ADM_SERVC_CURRENCYDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_ICURRENCYDAO{
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_CURRENCYDAOIMPL.class);
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecurrency(String currencyData,
			HERO_ADM_SERVC_ICURRENCYDAO currencyDAOobj) {

		log.info("currencyData        "+currencyData);
		
		try
		{
		HERO_ADM_SERVC_CURRENCYREQUEST request = (HERO_ADM_SERVC_CURRENCYREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(currencyData, "com.hero.forms.services.admin.masters.currency.HERO_ADM_SERVC_CURRENCYREQUEST");
		
		log.info("Values are     "+request.getCurrencyid()+"   "+request.getCurrency()+"   "+request.getCurrencycode()+"   "+request.getConversionrate()+"   "
				+request.getCurrencysymbol()+"   "+request.getExchangerate()+"   "+request.getConversionrate()+"   "+request.getStatusid()+"   "+request.getBasecurr()
				+"   "+request.getCurrdecimal());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CURRENCY_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_CURRENCY_ID", request.getCurrencyid());
		inParamMap.put("P_CURRENCY", request.getCurrency());
		inParamMap.put("P_STATUS_ID", request.getStatusid());
		inParamMap.put("P_CURR_SYMBOL", request.getCurrencysymbol());
		inParamMap.put("P_CURR_EXCHNG_RATE", request.getExchangerate());
		inParamMap.put("P_CONVERSION_RATE", request.getConversionrate());
		inParamMap.put("P_CURR_CODE", request.getCurrencycode());
		inParamMap.put("P_CURR_DECIMAL", request.getCurrdecimal());
		inParamMap.put("P_BASE_CURR", request.getBasecurr());
		inParamMap.put("P_CURR_FROM_DATE", (request.getFromdate() == null ? "" : HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate(request.getFromdate())));
		inParamMap.put("P_CURR_TO_DATE",(request.getTodate() == null ? "" : HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate(request.getTodate())));
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
			log.error(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrency() {
		// TODO Auto-generated method stub
		/*String selectQuery = "SELECT * FROM hero_admin_currency a JOIN `hero_global_currency_symbols` b ON a.`CURR_SYMBOL` = b.`gcs_id`";*/
		
		String selectQuery = "SELECT `currency_id`,`currency`,`CURR_EXCHNG_RATE`,`CURR_CONVERSION_RATE`,`CURR_DEC_FORMAT`,`CURR_BASE`, "+
							"`CURR_CODE`,`CURR_SYMBOL`,DATE_FORMAT(`CURR_FROM_DATE`,'%d/%m/%Y') CURR_FROM_DATE, "+
							"DATE_FORMAT(`CURR_TO_DATE`,'%d/%m/%Y') CURR_TO_DATE,`gcs_currency_name`,`gcs_currency_code`,`gcs_html_code` "+
							" FROM hero_admin_currency a JOIN `hero_global_currency_symbols` b ON a.`CURR_SYMBOL` = b.`gcs_id` AND a.`status_id` = '1' ORDER BY `currency_id` DESC";
		
		@SuppressWarnings("unchecked")
		List<Object> uomList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				List<String> detail = new ArrayList<String>();
				
				detail.add(rs.getString("currency"));//0
				detail.add(rs.getString("gcs_html_code"));//1
				
				detail.add(rs.getString("CURR_FROM_DATE"));//2
				detail.add(rs.getString("CURR_TO_DATE"));//3
				detail.add(rs.getString("curr_dec_format"));//4
				
				
				String basecurr = "No";
				if(rs.getInt("curr_base") == 1)
				{
					basecurr = "Yes";
				}
				/*detail.add(basecurr);*///5
				detail.add(basecurr);//5
				/*detail.add(rs.getString("curr_base"));*///7
				
				detail.add(rs.getString("currency_id"));//6
				
				detail.add(rs.getString("CURR_CODE"));//7
				
				detail.add(rs.getString("gcs_currency_code")+"-"+rs.getString("gcs_currency_name"));//8
				detail.add(rs.getString("CURR_SYMBOL"));//9
				
				detail.add(rs.getString("curr_exchng_rate"));//10
				detail.add(rs.getString("curr_conversion_rate"));//11
				detail.add(rs.getString("curr_base"));//12
				return detail;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnJSONobject(uomList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrencysymbolsuggestions() {
		// TODO Auto-generated method stub
		log.info("welcome to get currency symbol suggestion");
		String selectQuery = "SELECT * FROM `hero_global_currency_symbols`";
		@SuppressWarnings("unchecked")
		List<Object> currencySymbolList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("data", rs.getString("gcs_html_code"));
				map.put("value", rs.getString("gcs_id"));
				
				StringBuilder labelSB = new StringBuilder();
				labelSB.append(rs.getString("gcs_currency_code"));
				if(rs.getString("gcs_currency_name") != null)
				{
					labelSB.append(" - ");
					labelSB.append(rs.getString("gcs_currency_name"));
				}
				
				map.put("label", labelSB.toString());
				
				/*detail.add(map);
		
				log.info("Category Suggestion List        "+map);*/
				
				return map;
			}
		});
		
		/*String uomQuery = "SELECT `unit_type_id` uomid,`unit` unitdesc,`uom_desc` unitdisplay FROM `hero_admin_unit_type`";
		List<Map<String, Object>> uomList = jdbcTemplate.queryForList(uomQuery);
		Iterator<Map<String, Object>> uomListITR = uomList.iterator();
		while(uomListITR.hasNext())
		{
			log.info(uomListITR.next());
		}*/
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((currencySymbolList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
}
