package com.hero.stock.forms.services.tools.stockmonitor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;



public class HERO_STK_SERVC_STOCKMONITORDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_SERVC_ISTOCKMONITORDAO {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_STOCKMONITORDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_STK_RESPONSEINFO getStockMonitorList(String storeid,
			String manufacturerid, String categoryid,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		log.info("Values in getStockMonitorList      "+storeid+"   "+manufacturerid+"   "+categoryid);
		
		/*SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCK_MONITOR_QUERY");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_STORE_ID", storeid);
		inParamMap.put("P_PRODUCT_ID", productid);
		inParamMap.put("P_CATEGORY_ID", categoryid);
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("v_query"));
		
		String whereCondition = (String)resultMap.get("v_query");
		log.info("whereCondition        "+whereCondition);*/
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		
		StringBuilder sb = new StringBuilder();
		
		if(usertype > 2)
		{
			storeid = String.valueOf(httpRequest.getSession().getAttribute("storeid"));
		}
		
		sb.append("SELECT p.product_id,`product_name`,e.`category_name`,CONCAT(`unit_quantity`,' ',`unit`)unit,SUM(`product_count`)product_count,"
				+ "`company_name`  FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C ON a.`delivery_status` = C.`status_id` "
				+ "JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` JOIN `hero_stock_product` p ON b.`product_id` = p.`product_id` "
				+ "JOIN `hero_admin_category` e ON e.`category_id` = p.`category_id` JOIN `hero_admin_unit_type` u ON u.`unit_type_id` = p.`unit_type_id` "
				+ "LEFT JOIN `hero_admin_company` f ON f.`company_id` = p.`manufacturer_id`"
				+ "WHERE `pharmacy_id` = "+storeid+" AND `delivery_status` = 2 AND (`product_count`> 0 ) ");
		
		if(manufacturerid != null && !manufacturerid.equals("0"))
		{
			sb.append(" AND p.`manufacturer_id` = "+manufacturerid);
		}
		if(categoryid != null && !categoryid.equals("0"))
		{
			sb.append(" AND  e.`category_id` = "+categoryid);
		}
		
		sb.append(" GROUP BY p.product_id");
		/*sb.append("where d.`store_id` = "+storeid+" AND c.`product_id` =  "+productid+" AND E.`category_id` = "+categoryid);
		
		sb.append(" GROUP BY c.`product_id`,d.`store_id` ORDER BY c.`product_id`");*/

		log.info("Builded Query       "+sb.toString());
		
		@SuppressWarnings("unchecked")
		List<Object> stockmonitorList = jdbcTemplate.query(sb.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("categoryname", rs.getString("category_name"));
				map.put("productcode", rs.getString("unit"));
				map.put("productcount", rs.getString("product_count"));
				map.put("companyname", rs.getString("company_name"));
				
				return map;
			}
		});
		log.info(stockmonitorList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(stockmonitorList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getStockMonitorListDetails(String storeid,
			String productid, String categoryid) {
		// TODO Auto-generated method stub
		log.info("Values in getStockMonitorList      "+storeid+"   "+productid+"   "+categoryid);
		
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT p.product_id,`pharmacy_id`,e.`category_id`,`delivery_status`,`product_name`,e.`category_name`,CONCAT(`unit_quantity`,' ',`unit`)unit,"
//				+ "(`product_count`)product_count,CONCAT(' ',FORMAT(`product_rate`,`CURR_DEC_FORMAT`))product_rate,`CURR_SYMBOL`,`batch_id` "
+ "(`product_count`)product_count,CONCAT(' ',FORMAT(`product_rate`,2))product_rate,`CURR_SYMBOL`,`batch_id` "
				+ "FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C ON A.`delivery_status` = C.`status_id` JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` "
				+ "JOIN `hero_stock_product` p ON b.`product_id` = p.`product_id` JOIN `hero_admin_category` e ON e.`category_id` = p.`category_id` "
				+ "JOIN `hero_admin_unit_type` u ON u.`unit_type_id` = p.`unit_type_id` JOIN `hero_stock_store` S ON S.`store_id` = `pharmacy_id` "
				+ "JOIN `hero_admin_currency` f ON f.`currency_id` = S.`currency_id`"
				+ "WHERE b.`product_id` = "+productid+" AND `pharmacy_id` = "+storeid+" AND e.`category_id` = "+categoryid+" AND `delivery_status` = 2 ");
		
		log.info("getStockMonitorListDetails Builded Query       "+sb.toString());
		
		@SuppressWarnings("unchecked")
		List<Object> stockmonitorList = jdbcTemplate.query(sb.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				String currsymbol = rs.getString("CURR_SYMBOL") == null ? " " : rs.getString("CURR_SYMBOL");
				String productrate = rs.getString("product_rate") == null ? " " : rs.getString("product_rate");
				
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("categoryname", rs.getString("category_name"));
				map.put("productcode", rs.getString("unit"));
				map.put("productcount", rs.getInt("product_count"));
				map.put("productrate", currsymbol.concat(productrate));
				map.put("batchno", rs.getString("batch_id"));
				
				return map;
			}
		});
		log.info(stockmonitorList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(stockmonitorList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
