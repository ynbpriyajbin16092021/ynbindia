package com.hero.stock.forms.services.tools.lowstock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


import org.springframework.jdbc.core.RowMapper;

import com.hero.stock.util.HERO_STK_INVENTORYDAO;

public class HERO_STK_SERVC_LOWSTOCKDAOIMPL extends HERO_STK_INVENTORYDAO  implements HERO_STK_SERVC_ILOWSTOCKDAO {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_LOWSTOCKDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public List<Object> getLowStockProductListDetails(String storeid) {
		
		String fnstoreid = storeid;
		
			// TODO Auto-generated method stub
			List<Object> lowStockList = new ArrayList<Object>();
			
			try
			{
			
				StringBuilder lowStockSB = new StringBuilder();
				
				if(fnstoreid.equals("0"))
				{
					lowStockSB.append("SELECT B.`product_id` product_id,coalesce(SUM(`product_count`),0)product_count,coalesce(`alert_count`,0)alert_count,"
							+ "CONCAT(product_name,' - ',`company_name`)product_name,`store_name`,pharmacy_id  "
					+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
					+ "JOIN `hero_stock_product` D ON D.`product_id` = B.`product_id` "
					+ "LEFT JOIN `hero_stock_store` e ON e.`store_id` = A.`pharmacy_id` LEFT JOIN `hero_admin_company` f ON f.`company_id` = D.`manufacturer_id`"
					+ "WHERE  D.`status_id` = 1 AND `alert_count` > 0 "
					+ "GROUP BY B.`product_id`,`pharmacy_id`");
					
					lowStockSB.append(" UNION ALL "
							+ "SELECT A.`product_id` product_id,COALESCE(SUM(`product_count`),0)product_count,COALESCE(`alert_count`,0)alert_count,"
							+ "CONCAT(product_name,' - ',`company_name`)product_name,"
							+ "'WareHouse' store_name,0 pharmacy_id FROM `hero_stock` A JOIN `hero_stock_product` D ON D.`product_id` = A.`product_id` "
							+ "LEFT JOIN `hero_admin_company` B ON D.`manufacturer_id` = B.`company_id`"
							+ "WHERE  D.`status_id` = 1 AND `alert_count` > 0 GROUP BY A.`product_id` order by pharmacy_id,product_id");
				}
				else
				{
					lowStockSB.append("SELECT B.`product_id` product_id,COALESCE(SUM(`product_count`),0)product_count,COALESCE(`alert_count`,0)alert_count,"
		  +" CONCAT(product_name,' - ',`company_name`)product_name,`store_name`,pharmacy_id  FROM `hero_stock_transfer` A JOIN "
		  +" `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` JOIN `hero_user` c ON c. `user_store_id` = "+fnstoreid
		  +" JOIN `hero_stock_product` D ON D.`product_id` = B.`product_id` LEFT JOIN `hero_stock_store` e ON e.`store_id` = "+fnstoreid
		   +" LEFT JOIN `hero_admin_company` f ON f.`company_id` = D.`manufacturer_id` WHERE A.`pharmacy_id`  = "+fnstoreid+" AND D.`status_id` = 1 AND" 
		   +" `alert_count` > 0 GROUP BY B.`product_id`,`pharmacy_id`");
				}
				
				
				
			/*String lowStockQuery = "SELECT coalesce(SUM(`product_count`),0)product_count,coalesce(`alert_count`,0)alert_count,product_name,`store_name`  "
					+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
					+ "JOIN `hero_user` c ON c. `user_store_id` = a.`pharmacy_id` "
					+ "JOIN `hero_stock_product` D ON D.`product_id` = B.`product_id` "
					+ "LEFT JOIN `hero_stock_store` e ON e.`store_id` = a.`pharmacy_id` "
					+ "WHERE `userid` = "+httpRequest.getSession().getAttribute("uid")+
					" AND D.`status_id` = 1 AND `alert_count` > 0 "
					+ "GROUP BY B.`product_id`,`pharmacy_id`";
			log.info("lowStockQuery   "+lowStockQuery);*/
			log.info("lowStockSBin superadmin store   "+lowStockSB.toString());
			
			@SuppressWarnings("unchecked")
			List<Object> LOVList = jdbcTemplate.query(lowStockSB.toString(), new RowMapper() {
				int index=0;
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {

					  Map<String, Object> map = new HashMap<String, Object>();
					  
					   
						  map.put("index", index);
						  map.put("productname", rs.getString("product_name"));
						  map.put("productcount", rs.getInt("product_count"));
						  map.put("notifyqty", rs.getInt("alert_count"));  
						  map.put("storename", rs.getString("store_name"));
						  index++;
						 
					  
					  return map;
					  
				}
			});
			log.info("Completed   "+LOVList);
			
			Iterator<Object> itr = LOVList.iterator();
			while(itr.hasNext())
			{
				Map<String, Object> map = (Map<String, Object>) itr.next();
				if(map.get("productcount") != null && map.get("notifyqty") != null)
				{
					int productCount = (int)map.get("productcount");
					int notifyCount = (int)map.get("notifyqty");
					log.info("Count Details are    "+productCount+"   "+notifyCount);
					if(productCount < notifyCount)
					{
						lowStockList.add(map);
					}	
				}
				
			}
			log.info("lowStockList size   "+lowStockList.size());	
			
			
			}
			catch(Exception e)
			{
				error_log.info(e);
			}
			return lowStockList;
	}
	
	
	
}
