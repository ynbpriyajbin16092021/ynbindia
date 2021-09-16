package com.hero.forms.services.stock.masters.store;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_STK_SERVC_STOREDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_STK_SERVC_ISTOREDAO{
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_STOREDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savestore(String storeData,
			HERO_STK_SERVC_ISTOREDAO storeDAOobj) {
		// TODO Auto-generated method stub
		try
		{
		HERO_STK_SERVC_STOREREQUEST request = (HERO_STK_SERVC_STOREREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(storeData, "com.hero.forms.services.stock.masters.store.HERO_STK_SERVC_STOREREQUEST");
		
		log.info("Values are     "+request.getAddress()+"   "+request.getCity()+"   "+request.getCountry()+"   "+request.getCreatedby()+"   "
				+request.getCurrencydesc()+"   "+request.getCurrencytype()+"   "+request.getEmail()+"   "+request.getStatusid()+"   "+request.getOprn()+"   "
				+request.getPhone()+"   "+request.getState()+"   "+request.getStatusdesc()+"      "+request.getStoreid()+"   "+request.getStorename()+"   "
				+request.getZipcode()+"   "+request.getTaxList()+"   "+request.getUserid());
		
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STORE_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_STORE_ID", request.getStoreid());//0
		inParamMap.put("P_STORE_NAME", request.getStorename());//1
		inParamMap.put("P_CURRENCY_ID", request.getCurrencytype());//2
		inParamMap.put("P_ADDRESS", request.getAddress());//3
		inParamMap.put("P_CITY", request.getCity());//4
		inParamMap.put("P_STATE", request.getState());//5
		inParamMap.put("P_COUNTRY", request.getCountry());//6
		inParamMap.put("P_ZIPCODE", request.getZipcode());//7
		inParamMap.put("P_PHONE", request.getPhone());//8
		inParamMap.put("P_EMAIL", request.getEmail());//9
		inParamMap.put("P_STATUS_ID", request.getStatusid());//10
		inParamMap.put("P_CREATED_BY", request.getUserid());//11
		inParamMap.put("P_OPRN", request.getOprn());//12
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
String resultType = (String)resultMap.get("out_result_type");
String storeid = "0";

if(request.getOprn() != null && !request.getOprn().equals("DEL"))
{
		if(resultType != null && resultType.equals("S"))
		{

			List<HERO_STK_SERVC_STORETAX> storeTaxList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONArraytoList(request.getTaxList(),"com.hero.forms.services.stock.masters.store.HERO_STK_SERVC_STORETAX");
		 
			storeid = (String)resultMap.get("out_genrate_id");	
			log.info("storeid       "+storeid);
			Iterator<HERO_STK_SERVC_STORETAX> itr = storeTaxList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_SERVC_STORETAX obj = itr.next();
				obj.setStoreid(storeid);
				
				storeTaxList.set(Integer.parseInt(obj.getIndex()), obj);
				
				log.info(obj.getStoreid()+"   "+obj.getOprn());
			}	

			saveStoretax(storeTaxList);	
			}
			 
		
		}
		
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

	@Transactional
	public void saveStoretax( final List<HERO_STK_SERVC_STORETAX> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_STORE_TAX( ?, ?, ?, ? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_SERVC_STORETAX item = list.get(i);
				log.info("Item Values       "+item.getStoretaxid()+"   "+item.getStoreid()+"   "+item.getTaxid());
				ps.setString(1, item.getStoretaxid());
				ps.setString(2, item.getStoreid());
				ps.setString(3, item.getTaxid());
				ps.setString(4, "INS");
			}
		});
		}
		catch( Exception e )
		{
			//e.printStackTrace();
			error_log.info(e);
			throw e;
		}
	}
	
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadstore() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM hero_stock_store a,hero_admin_currency b,hero_stock_status c WHERE a.`currency_id`=b.`currency_id` AND  a.status_id = c.status_id ORDER BY store_id DESC";
		//String query="SELECT * FROM hero_stock_store ";
		log.info("Store query   "+query);
		@SuppressWarnings("unchecked")
		List<Object> storeList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				String storeid = rs.getString("store_id");
				map.put("storeid", storeid);
				map.put("storename", rs.getString("store_name"));
				map.put("currencytype", rs.getString("currency_id"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("phone", rs.getString("phone"));
				map.put("email", rs.getString("email"));
				map.put("statusid", rs.getString("status_id"));
				map.put("createdby", rs.getString("created_by"));
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);
				
				map.put("statusdesc", rs.getString("status"));
				map.put("currencydesc", rs.getString("currency"));
				
				
				String StoreTaxQuery = "SELECT * FROM `hero_stock_store_tax` WHERE `store_id` = "+storeid;
				@SuppressWarnings("unchecked")
				List<Object> storeTaxList = jdbcTemplate.query(StoreTaxQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						// TODO Auto-generated method stub
						StringBuilder sb = new StringBuilder();
						sb.append(rs.getString("tax_id"));
						
						return sb.toString();
					}
				});
				map.put("storeTaxList", storeTaxList);
				
				return map;
			}
		});
		log.info(storeList);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", HosurInventoryUtil.returnJSONobject(productList));
		log.info(new Gson().toJson(map));*/
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(storeList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getstoreinfo(String storeid) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM hero_stock_store a,hero_admin_currency b,hero_stock_status c WHERE a.currency_id = b.currency_id and a.status_id = c.status_id "
				+ "and store_id = "+storeid;
		String StoreTaxQuery = "SELECT `store_tax_id`,`store_id`,`tax_id` FROM `hero_admin_tax` WHERE `store_id` = "+storeid;
		@SuppressWarnings("unchecked")
		List<Object> storeList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("storeid", rs.getString("store_id"));
				map.put("storename", rs.getString("store_name"));
				map.put("currencytype", rs.getString("currency_id"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("phone", rs.getString("phone"));
				map.put("email", rs.getString("email"));
				map.put("statusid", rs.getString("status_id"));
				map.put("createdby", rs.getString("A.CREATED_BY"));
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);
				
				map.put("statusdesc", rs.getString("status"));
				map.put("currencydesc", rs.getString("currency"));
				
				return map;
			}
		});
		log.info(storeList);
		
		@SuppressWarnings("unchecked")
		List<Object> storeTaxList = jdbcTemplate.query(StoreTaxQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("tax_id"));
				
				return sb.toString();
			}
		});
		log.info(storeList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("storeList", storeList);
		map.put("storeTaxList", storeTaxList);
		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
