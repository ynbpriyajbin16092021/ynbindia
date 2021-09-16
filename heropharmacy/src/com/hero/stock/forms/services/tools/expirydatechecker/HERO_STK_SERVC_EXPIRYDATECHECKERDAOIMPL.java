package com.hero.stock.forms.services.tools.expirydatechecker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

public class HERO_STK_SERVC_EXPIRYDATECHECKERDAOIMPL extends HERO_STK_INVENTORYDAO  implements HERO_STK_SERVC_iEXPIRYDATECHECKERDAO {

	private static Logger log = Logger.getLogger(HERO_STK_SERVC_EXPIRYDATECHECKERDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_STK_RESPONSEINFO getExpiryProductListDetails(String storeid,String expiredtype,String expireddays) {
		// TODO Auto-generated method stub
		log.info("Values in getStockMonitorList      "+storeid);
		
		StringBuilder sb = new StringBuilder();
		
		if(expiredtype != null && expiredtype.equals("0"))
		{
			sb.append("SELECT A.`transfer_id`,B.`product_id`,`product_name`,`product_count`,`batch_id`,DATE_FORMAT(`prec_expiry_date`,'%e/%c/%Y')prec_expiry_date,`company_name`"
					+ " FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` JOIN `hero_stock_product` C "
					+ "ON C.`product_id` = B.`product_id` "
					+ "JOIN `hero_stock_purchase_received_dtl` D ON D.`prec_product_id` = B.`product_id` AND D.`prec_batchno` = B.`batch_id` "
					+ "LEFT JOIN  `hero_admin_company` e ON e.`company_id` = C.`manufacturer_id`"
					+ " WHERE `pharmacy_id` = "+storeid+"  AND `delivery_status` = 2 AND `prec_expiry_date` <= DATE_ADD(CURDATE(), INTERVAL -1 DAY) "
							+ "GROUP BY `prec_batchno`");	
		}
		else
		{
			sb.append("SELECT A.`transfer_id`,B.`product_id`,`product_name`,`product_count`,`batch_id`,DATE_FORMAT(`prec_expiry_date`,'%e/%c/%Y')prec_expiry_date,`company_name`"
					+ " FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` JOIN `hero_stock_product` C "
					+ "ON C.`product_id` = B.`product_id` "
					+ "JOIN `hero_stock_purchase_received_dtl` D ON D.`prec_product_id` = B.`product_id` AND D.`prec_batchno` = B.`batch_id` "
					+ "LEFT JOIN  `hero_admin_company` e ON e.`company_id` = C.`manufacturer_id`"
					+ " WHERE `pharmacy_id` = "+storeid+"  AND `delivery_status` = 2 AND `prec_expiry_date` BETWEEN CURDATE() AND DATE_ADD(CURDATE(), "
							+ "INTERVAL "+expireddays+" DAY) GROUP BY `prec_batchno`;");
		}
		
		
		log.info("getStockMonitorListDetails Builded Query       "+sb.toString());
		
		@SuppressWarnings("unchecked")
		List<Object> stockmonitorList = jdbcTemplate.query(sb.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productname", rs.getString("product_name"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("manufacturername", rs.getString("company_name"));
				map.put("productcount", rs.getString("product_count"));
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
