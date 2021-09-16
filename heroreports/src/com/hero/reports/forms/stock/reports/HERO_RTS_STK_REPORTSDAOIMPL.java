package com.hero.reports.forms.stock.reports;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;
import com.hero.reports.util.HEROHOSURINVENTORYUTIL;
import com.hero.reports.util.HERO_RTS_INVENTORYDAO;

public class HERO_RTS_STK_REPORTSDAOIMPL extends HERO_RTS_INVENTORYDAO implements HERO_RTS_STK_IREPORTSDAO{
	private static Logger log = Logger.getLogger(HERO_RTS_STK_REPORTSDAOIMPL.class);
	
	@Override
	public HERO_RTS_RESPONSEINFO loadstorerequestreport(String reportsData,HttpServletRequest httpRequest) {
		HERO_RTS_STK_REPORTSREQUEST request;
		try {
			request = (HERO_RTS_STK_REPORTSREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(reportsData, "com.hero.reports.forms.stock.reports.HERO_RTS_STK_REPORTSREQUEST");
		
		String date = "'"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+
				"' AND DATE_ADD('"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getEnddate())+"', INTERVAL 1 DAY)";
		Map<String, Object> finalMap = new HashMap<String, Object>();
	
		Map<String, Object> countList = new HashMap<String, Object>();
		Map<String, Object> finalcountList = new HashMap<String, Object>();
		String customerQuery = "SELECT DISTINCT(`companyname`),`customer_id` FROM `hero_stock_transfer_product` a JOIN `hero_stock_client_company` b ON b.`companyid`=a.`customer_id` ";
		List<Object> customerList = new ArrayList<Object>();
		customerList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(customerQuery, jdbcTemplate);
		 StringBuilder customerid = new StringBuilder(" ");
		Iterator<Object> customerListITR = customerList.iterator();
		while(customerListITR.hasNext())
		{
			Map<String, Object> Map = (java.util.Map<String, Object>) customerListITR.next();
			
		String customeridstr = (String) Map.get("customer_id"); 
			customerid.append(customeridstr+",");
			
			
		}
		customerid.deleteCharAt(customerid.length()-1);
		
		String dishQuery = "SELECT DISTINCT(`dishtype_name`),`dishtypeid` FROM `hero_stock_transfer_product` a JOIN `hero_stock_dish_type` b ON a.`dishtypeid` =b.`dishtype_id`";
		List<Object> dishList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(dishQuery, jdbcTemplate);
		Iterator<Object> dishListITR = dishList.iterator();
		while(dishListITR.hasNext())
		{
			
			Map<String, Object> Map = (java.util.Map<String, Object>) dishListITR.next();
			
		
			String dishCountQuery = "SELECT dishtypeid,`customer_id` FROM `hero_stock_transfer_product` a JOIN"
					+ " `hero_stock_transfer` b ON a.`transfer_id`=b.`transfer_id` WHERE `dishtypeid`="+Map.get("dishtypeid")+" AND `customer_id` IN("+customerid+") AND DATE(b.`transfer_date`) BETWEEN "+date+" GROUP BY `customer_id`";
			
			@SuppressWarnings("unchecked")
			List<Object> countaddList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("dishtypeid", rs.getString("dishtypeid"));
					map.put("customerid", rs.getString("customer_id"));
					
					return map;
				}
			});
			
			
			countList.put((String) Map.get("dishtypeid"),countaddList);
			
		}
		 for (Map.Entry<String, Object> set :
			 countList.entrySet()) {
 
            // Printing all elements of a Map
            
        	List<Object> obj = (List<Object>) set.getValue();
        	Iterator<Object> objListITR = obj.iterator();
        	List<Object> newarray = new ArrayList<Object>();
    		while(objListITR.hasNext())
    		{
    			Map<String, Object> Map = (java.util.Map<String, Object>) objListITR.next();
    			  
    			   Map<String, Object> newmap = new HashMap<String, Object>();
    			   String dishCountQuery = "SELECT `dish_count` FROM `hero_stock_transfer_product` a JOIN"
					+ " `hero_stock_transfer` b ON a.`transfer_id`=b.`transfer_id`  WHERE `customer_id`="+Map.get("customerid")+" AND `dishtypeid`="+Map.get("dishtypeid")+" "
    						+ " AND DATE(b.`transfer_date`) BETWEEN "+date+" GROUP BY a.`transfer_id` ";
    						
    				
    				@SuppressWarnings("unchecked")
    				List<Object> addList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
    					@Override
    					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
    						Map<String, Object> map = new HashMap<String, Object>();
    						map.put("dish_count", rs.getString("dish_count"));
    						return map;
    					}
    				});
    				int intcount=0;
    				List<Object> countobj = (List<Object>) addList;
    				Iterator<Object> countobjListITR = countobj.iterator();
    				while(countobjListITR.hasNext())
    				{
    					Map<String, Object> Map1 = (java.util.Map<String, Object>) countobjListITR.next();
    					
    					String s = (String) Map1.get("dish_count");
    					 intcount = intcount+ Integer.parseInt(s);
    				}
    				
    				newmap.put("customerid", Map.get("customerid"));
    				newmap.put("dishcount", intcount);
    				newarray.add(newmap);
    		}
    		finalcountList.put(set.getKey(), newarray);
        }
		
		finalMap.put("customerList", customerList);
		finalMap.put("dishList", dishList);
		finalMap.put("dishcountList", finalcountList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(finalMap);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
		
		
	}
	@Override
	public HERO_RTS_RESPONSEINFO orderReports(String reportsData,int status,int customerId,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		HERO_RTS_STK_REPORTSREQUEST request;
		try {
			request = (HERO_RTS_STK_REPORTSREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(reportsData, "com.hero.reports.forms.stock.reports.HERO_RTS_STK_REPORTSREQUEST");
			String date = "'"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+
					"' AND DATE_ADD('"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getEnddate())+"', INTERVAL 1 DAY)";
			String startdate = " '"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+"'" ;
			List<Object> reportsList = new ArrayList<Object>();
			String Query = "";
			if(status == 1){
				Query = "SELECT `dish_name` dishname,`dishtype_name` dishtype,`dish_count` count FROM `hero_stock_transfer_product` a JOIN `hero_stock_dishes` b ON b.`dish_id`=a.`dishid`"
						+ " JOIN `hero_stock_dish_type` c ON c.`dishtype_id`=a.`dishtypeid` JOIN `hero_stock_transfer` d ON d.`transfer_id`=a.`transfer_id`  "
						+ "WHERE `customer_id`="+customerId+" AND d.`status_id`=1 AND d.`transfer_date` BETWEEN "+date+" GROUP BY `dishid`,`dishtypeid`";
			}
			else if(status == 2){
				Query = "SELECT `dish_name` dishname,`dishtype_name` dishtype,(`dish_count`)dish_count"
						+ " FROM `hero_stock_transfer_product` a JOIN `hero_stock_transfer` b ON a.`transfer_id`=b.`transfer_id`"
						+ "  JOIN `hero_stock_dishes` c ON c.`dish_id`= a.`dishid`  JOIN `hero_stock_dish_type` d ON d.`dishtype_id`= a.`dishtypeid`"
						
						+ " WHERE `customer_id`="+customerId+" AND b.`status_id`=2 "
						+ " AND b.`transfer_date` ="+startdate+"  GROUP BY `customer_id`,`dishid`,`dishtypeid`";
			}
			log.info(Query);
			reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(Query, jdbcTemplate);
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(reportsList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return inventoryResponseInfoOBJ;
		
	}
	
	@Override
	public HERO_RTS_RESPONSEINFO getorgndetails(String reportsData,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		HERO_RTS_STK_REPORTSREQUEST request;
		try {
			request = (HERO_RTS_STK_REPORTSREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(reportsData, "com.hero.reports.forms.stock.reports.HERO_RTS_STK_REPORTSREQUEST");
			String date = "'"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+
					"' AND DATE_ADD('"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getEnddate())+"', INTERVAL 1 DAY)";
			String startdate = " '"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+"'" ;
			List<Object> reportsList = new ArrayList<Object>();
			String Query = "";
			
		
				Query = "SELECT * FROM `hero_orgn_table`";
			
			reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(Query, jdbcTemplate);
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(reportsList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			return inventoryResponseInfoOBJ;
		
	}
	@Override
	public HERO_RTS_RESPONSEINFO salesbyproduct(String reportsData,int reportid,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		try
		{
			log.info("reportsData   "+reportsData);
			HERO_RTS_STK_REPORTSREQUEST request = (HERO_RTS_STK_REPORTSREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(reportsData, "com.hero.reports.forms.stock.reports.HERO_RTS_STK_REPORTSREQUEST");
			String date = "'"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+
						"' AND DATE_ADD('"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getEnddate())+"', INTERVAL 1 DAY)";
			log.info("Values are     "+request.getStoreid()+"   "+request.getStartdate()+"   "+request.getEnddate());
			String startdate = " '"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getStartdate())+"'" ;
			String enddate ="' "+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getEnddate())+"' ";
			int usertype = (int)httpRequest.getSession().getAttribute("usertype");
			List<Object> reportsList = new ArrayList<Object>();
			String query = "";
			StringBuilder queryBuilder = new StringBuilder();
			if(reportid == 1)
			{

				
				queryBuilder.append("SELECT `product_name` productname,`product_code` productcode,'' margin,SUM(`pos_sales_count`)salescount,"
						+ "SUM(`pos_sales_count` * `pos_sales_price`)salesprice,a.`created_at`,store_name FROM `hero_stock_pos_summary` a "
						+ "JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`pos_prod_id` = c.`product_id`"
						+ "  AND a.`created_at` BETWEEN "+date
						+ "LEFT JOIN `hero_stock_store` d ON d.`store_id` = a.`pos_store_id`");
				
				if(usertype > 2)
				{
					queryBuilder.append("WHERE `pos_store_id` = "+request.getStoreid()+" AND a.`pos_status` != 8 AND pos_sales_count > 0 ");
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("WHERE `pos_store_id` = "+request.getStoreid()+" AND a.`pos_status` != 8 AND pos_sales_count > 0 ");
					}
				}
				queryBuilder.append(" GROUP BY `pos_store_id`,`pos_prod_id`,`pos_sales_price` ORDER BY a.`pos_store_id`,a.`created_at` ASC");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 2)
			{
				queryBuilder.append("(SELECT `pos_orders_avail`,`pos_code`,`store_name`,COALESCE(`cust_email`,'')email,"
						+ "COALESCE(CONCAT(`cust_firstname`,' ',`cust_lastname`),'POC Customer')customername,"
						+ "CASE WHEN (`pos_netamount` < `pos_paid_amt`) THEN 0 ELSE FORMAT((`pos_netamount` - `pos_paid_amt`),2)   END AS 'balance', "
						+ "0 orderamount,SUM(`pos_grand_total`) grosssales,SUM(`pos_netamount`) netsales "
						+ "FROM `hero_stock_pos_summary` a LEFT JOIN  `hero_admin_customer` b ON a.`cust_id` = b.`cust_id` "
						+ "LEFT JOIN `hero_stock_store` d ON d.`store_id` = a.`pos_store_id` WHERE `pos_orders_avail` = 0 AND "
						+ " a.`created_at` BETWEEN "+date+" ");
				
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				
				queryBuilder.append(" GROUP BY a.`cust_id` ORDER BY a.pos_store_id,a.`cust_id` ASC)");
				
				queryBuilder.append(" UNION ALL ");
				
				queryBuilder.append("(SELECT `pos_orders_avail`,`pos_code`,`store_name`,COALESCE(`cust_email`,'')email,"
						+ "COALESCE(CONCAT(`cust_firstname`,' ',`cust_lastname`),'POC Customer')customername,"
						+ "CASE WHEN (`pos_netamount` < `pos_paid_amt`) THEN 0 ELSE FORMAT((`pos_netamount` - `pos_paid_amt`),2)   END AS 'balance',"
						+ "SUM(`pos_netamount`) orderamount,SUM(`pos_grand_total`) grosssales,SUM(`pos_netamount`) netsales "
						+ "FROM `hero_stock_pos_summary` a LEFT JOIN  `hero_admin_customer` b ON a.`cust_id` = b.`cust_id` "
						+ "LEFT JOIN `hero_stock_store` d ON d.`store_id` = a.`pos_store_id` WHERE `pos_orders_avail` = 1 AND `pos_order_status_id` = 6 AND "
						+ " a.`created_at` BETWEEN "+date+" ");
				
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				
				queryBuilder.append(" GROUP BY a.`cust_id` ORDER BY a.pos_store_id,a.`cust_id` ASC)");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
			}
			else if(reportid == 4)
			{
				queryBuilder.append("SELECT DATE_FORMAT(a.`created_at`,'%e/%c/%Y')orderdate,`pos_order_code`,"
						+ "CONCAT(`cust_firstname`,' ',`cust_lastname`)custname,`pos_code`,`pos_grand_total`,`pos_status_desc`,`pos_shipping_cost`,"
						+ "`pos_netamount`,`store_name` FROM `hero_stock_pos_summary` a JOIN `hero_admin_customer` b ON a.`cust_id` = b.`cust_id` "
						+ "LEFT JOIN `hero_stock_pos_status` c ON c.`pos_status_id` = a.`pos_order_status_id` "
						+ "LEFT JOIN `hero_stock_store` d ON d.`store_id` = a.`pos_store_id`"
						+ " WHERE `pos_orders_avail` = 1 "
						+ "AND a.`created_at` BETWEEN "+date+" ");
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
			}
			else if(reportid == 5)
			{

				queryBuilder.append("SELECT COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_grand_total`),0)),0)gross,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_discount`),0)),0)discount,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_tax_amount`),0)),0)tax,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_shipping_cost`),0)),0)shippingcost,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_netamount`),0)),0)net "
						+ "FROM `hero_stock_pos_summary` a  WHERE "
						+ " a.`created_at` BETWEEN "+date+" ");
				
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				
				queryBuilder.append(" UNION ALL ");
				
				queryBuilder.append("SELECT COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_grand_total`),0)),0)gross,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_discount`),0)),0)discount,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_tax_amount`),0)),0)tax,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_shipping_cost`),0)),0)shippingcost,"
						+ "COALESCE(CONCAT(`currency`,COALESCE(SUM(`pos_netamount`),0)),0)net "
						+ "FROM `hero_stock_pos_summary` a  WHERE `pos_return_sales_code` IS NOT NULL AND  "
						+ " a.`created_at` BETWEEN "+date+" ");
				
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				
				List<Object> outputList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
				Iterator<Object> outputListITR = outputList.iterator();
				Map<String, Object> reportMap = new HashMap<String, Object>();
				
				while(outputListITR.hasNext())
				{
					Map<String, Object> outputmap = (Map<String, Object>) outputListITR.next();
					 reportMap = new HashMap<String, Object>();
					for (String key : outputmap.keySet()) {
						   String value = (String) outputmap.get(key);
						   
						   log.info("key: " + key + " value: " + value);
						   
						  
						   reportMap.put(key, value);
						   reportMap.put("amount", value);
						   
						}
					reportsList.add(reportMap);
				}
				
				}
			else if(reportid == 6)
			{
				queryBuilder.append("SELECT MONTHNAME(`created_at`)orderedmonth,COUNT(`pos_order_code`)orderedcount,SUM(`pos_grand_total`) grosssales,"
						+ "SUM(`pos_discount`) discount,SUM(`pos_netamount`) netsales,SUM(`pos_shipping_cost`) shipping,"
						+ "`pos_tax_amount` tax FROM `hero_stock_pos_summary` a WHERE `pos_order_code` IS NOT NULL AND `pos_order_code` != '' AND  "
						+ "a.`created_at` BETWEEN "+date);
				
				if(usertype > 2)
				{
					queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append("AND `pos_store_id` = "+request.getStoreid());
					}
				}
				queryBuilder.append(" GROUP BY MONTH(`created_at`)");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 7)
			{
				queryBuilder.append("SELECT `ct_name` as ctname,COALESCE(CONCAT(`currency`,SUM(`pos_grand_total`)),0) grosssales,"
						+ "COALESCE(CONCAT(`currency`,SUM(`pos_discount`)),0) discount,"
						+ "COALESCE(CONCAT(`currency`,SUM(`pos_netamount`)),0) netsales,"
						+ "COALESCE(CONCAT(`currency`,SUM(`pos_shipping_cost`)),0) shipping,`pos_tax_amount` tax FROM `hero_stock_pos_summary` a JOIN `hero_admin_cash_type` b "
						+ "ON a.`pos_payment_type` = b.`ct_id`  "
						+ "WHERE a.`created_at` BETWEEN "+date);
				
				if(usertype > 2)
				{
					queryBuilder.append(" AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append(" AND `pos_store_id` = "+request.getStoreid());
					}
				}
				queryBuilder.append(" GROUP BY `pos_payment_type`");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 8)
			{
				/*queryBuilder.append("SELECT `TAX_NAME`,COALESCE(CONCAT(`currency`,SUM(`tax_amount`)),0)taxamount FROM `hero_stock_pos_tax` a "
						+ "JOIN `hero_admin_tax` b ON a.`tax_id` = b.`TAX_ID` JOIN `hero_stock_pos_summary` c ON c.`pos_id` = a.`tax_id`  "
						+ "WHERE c.`created_at` BETWEEN "+date);*/
				
				queryBuilder.append("SELECT `TAX_NAME`,COALESCE(CONCAT(`currency`,SUM(`tax_amount`)),0)taxamount FROM `hero_stock_pos_tax` a "
						+ "JOIN `hero_admin_tax` b ON a.`tax_id` = b.`TAX_ID` JOIN `hero_stock_pos_summary` c ON c.`pos_id` = a.`pos_id`  "
						+ "WHERE c.`created_at` BETWEEN "+date);
				
				if(usertype > 2)
				{
					queryBuilder.append(" AND `pos_store_id` = "+request.getStoreid());
				}
				else
				{
					if(request.getStoreid() != null && !request.getStoreid().equals("0"))
					{
						queryBuilder.append(" AND `pos_store_id` = "+request.getStoreid());
					}
				}
				queryBuilder.append(" GROUP BY a.`tax_id`");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 9)
			{
				queryBuilder.append("SELECT `product_name`,`store_name`,SUM(`poo_sales_count`)ordercount,`company_name` "
						+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_order_items` b ON a.`pos_id` = b.`pos_id` "
						+ "JOIN `hero_stock_product` c ON b.`poo_prod_id` = c.`product_id` JOIN `hero_stock_store` d ON d.`store_id` = a.`pos_store_id` "
						+ "JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` "
						+ "WHERE `pos_order_code` IS NOT NULL AND `pos_order_status_id` = 5 ");
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND `pos_store_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `pos_store_id`,b.`poo_prod_id` ORDER BY `pos_store_id`,b.`poo_prod_id` ASC");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 10)
			{
				queryBuilder.append("SELECT DATE_FORMAT(b.`prhdr_recv_date`,'%e/%c/%Y')receiveddate,`prhdr_id`,b.`pur_req_id`,"
						+ "CONCAT(`supplier_first_name`)suppliername,"
						+ "CONCAT('Rs. ',((SUM(`prec_sub_total`) + `prhdr_tax_amt`+`prhdr_shipping_cost`) - `prhdr_discount_amt`))totalamount,"
						+ "SUM(`prec_recving_quantity`) recvgqty FROM `hero_stock_purchase` a JOIN `hero_stock_purchase_receive_hdr` b "
						+ "ON a.`purchase_code` = b.`pur_req_id` JOIN `hero_stock_purchase_received_dtl` c ON b.`prhdr_id` = c.`prec_hdr_id` "
						+ "JOIN `hero_stock_supplier` d ON d.`supplier_id` = a.`supplier_id`  "
						+ "WHERE b.`prhdr_recv_date` BETWEEN "+date);
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`supplier_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `prhdr_recv_date`,`purchase_code` order by `prhdr_recv_date` ASC");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 11)
			{
				queryBuilder.append("SELECT `product_name`,`prec_batchno`,CONCAT(SUM(`prec_recving_quantity`),' ',`unit`)recvgqty,"
						+ "CONCAT(`supplier_first_name`)suppliername,CONCAT('Rs. ',SUM(`prec_sub_total`))amount,"
						+ "DATE_FORMAT(b.`prhdr_recv_date`,'%e/%c/%Y')receiveddate FROM `hero_stock_purchase` a "
						+ "JOIN `hero_stock_purchase_receive_hdr` b ON a.`purchase_code` = b.`pur_req_id` "
						+ "JOIN `hero_stock_purchase_received_dtl` c ON b.`prhdr_id` = c.`prec_hdr_id` "
						+ "JOIN `hero_stock_supplier` d ON d.`supplier_id` = a.`supplier_id` "
						+ " JOIN `hero_stock_product` e ON e.`product_id` = c.`prec_product_id`"
						+ "JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=e.`unit_type_id` "
						+ "WHERE b.`prhdr_recv_date` BETWEEN "+date);
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`supplier_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `prec_product_id` order by `prec_product_id` ASC");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 12)
			{
				queryBuilder.append("SELECT DATE_FORMAT(`prhdr_created_date`,'%e/%c/%Y')createdat,`prhdr_bill_no`,"
						+ "CONCAT(`supplier_first_name`)suppliername,"
						+ "CONCAT('Rs. ',((SUM(`prec_sub_total`) + `prhdr_tax_amt`+`prhdr_shipping_cost`) - `prhdr_discount_amt`))totalamount,"
						+ "CONCAT('Rs. ',((SUM(`prec_sub_total`) + `prhdr_tax_amt`+`prhdr_shipping_cost`) - `prhdr_discount_amt`) - "
						+ "COALESCE(`prhdr_paid_amount`,0))balance,COALESCE(`ps_status_name`,'Pending')billstatus FROM `hero_stock_purchase` a "
						+ "JOIN `hero_stock_purchase_receive_hdr` b ON a.`purchase_code` = b.`pur_req_id` "
						+ "JOIN `hero_stock_purchase_received_dtl` c ON b.`prhdr_id` = c.`prec_hdr_id` "
						+ "JOIN `hero_stock_supplier` d ON d.`supplier_id` = a.`supplier_id` JOIN `hero_stock_product` e ON e.`product_id` = c.`prec_product_id` "
						+ "LEFT JOIN `hero_stock_purchase_status` F ON F.`ps_id` = `prhdr_paid_status` WHERE b.`prhdr_recv_date` BETWEEN "+date);
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`supplier_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `prhdr_bill_no` ORDER BY b.`prhdr_id`");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 13)
			{
				queryBuilder.append("SELECT CONCAT(`supplier_first_name`)suppliername,"
						+ "CONCAT('Rs. ',SUM(`prhdr_grand_total_amt`))total,CONCAT('Rs. ',COALESCE(SUM(`prhdr_return_charge`),0))retun,"
						+ "CONCAT('Rs. ',COALESCE(SUM(`prhdr_paid_amount`),0))paid,"
						+ "CONCAT('Rs. ',((SUM(`prhdr_grand_total_amt`))- COALESCE(SUM(`prhdr_paid_amount`),0)))balance FROM `hero_stock_purchase` a "
						+ "JOIN `hero_stock_purchase_receive_hdr` b ON a.`purchase_code` = b.`pur_req_id` "					
						+ "JOIN `hero_stock_supplier` d ON d.`supplier_id` = a.`supplier_id` "						
						+ "LEFT JOIN `hero_stock_purchase_status` F ON F.`ps_id` = `prhdr_paid_status` WHERE b.`prhdr_recv_date` BETWEEN "+date);
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`supplier_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY a.`supplier_id` ORDER BY b.`prhdr_id`");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 14)
			{
	if(request.getStoreid() != null && !request.getStoreid().equals("0") && request.getStoreid().equals("1")){
					
					queryBuilder.append("SELECT a.`stock_id`,`product_name`,`store_name`,"
							+ " CONCAT(SUM(`product_count`),' ',`unit`)productcount,`company_name` "
							+ " FROM `hero_stock` a JOIN `hero_stock_product` c ON a.`product_id` = c.`product_id` "
							+ " JOIN `hero_stock_store` d ON d.`store_id` = "+request.getStoreid()+" JOIN `hero_admin_company` e ON "
							+ " e.`company_id` = c.`manufacturer_id`  JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=c.`unit_type_id` WHERE  `created_at` BETWEEN "+date);
				}else{
					queryBuilder.append("SELECT a.`transfer_id`,`product_name`,`store_name`,"
							+ "CONCAT(SUM(`product_count`),' ',`unit`)productcount,`company_name` "
							+ "FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` "
							+ "JOIN `hero_stock_product` c ON b.`product_id` = c.`product_id` JOIN `hero_stock_store` d ON d.`store_id` = 2 "
							+ "JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` "
							+ " JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=c.`unit_type_id` WHERE `delivery_status` = 2"
							+ "  AND `transfer_date` BETWEEN "+date);
				}
				
	if(request.getStoreid() != null && request.getStoreid().equals("2"))
	{
		queryBuilder.append(" AND `pharmacy_id` = 1  GROUP BY `pharmacy_id`,b.`product_id` ORDER BY b.`product_id` ASC ");
//		queryBuilder.append(" GROUP BY `pharmacy_id`,b.`product_id` ORDER BY b.`product_id` ASC");
	}else{
		queryBuilder.append(" GROUP BY a.`product_id` ORDER BY a.`product_id` ASC");
	}
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 15)
			{
				queryBuilder.append("SELECT `store_name`,`product_name`,`batch_id`,`company_name`,"
						+ "CONCAT(SUM(`product_count`),' ',`unit`)productcount,`product_rate`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transferdate,"
						+ "(SUM(`product_count` * `product_rate`))amount "
						+ "FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` "
						+ "JOIN `hero_stock_product` c ON b.`product_id` = c.`product_id` JOIN `hero_admin_company` d ON c.`manufacturer_id` = d.`company_id` "
						+ "JOIN `hero_stock_store` e ON e.`store_id` = a.`pharmacy_id` "
						+ "JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=c.`unit_type_id` WHERE `transfer_date` BETWEEN "+date);
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`pharmacy_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `pharmacy_id`,b.`product_id`,b.`batch_id` ");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 16)
			{
				queryBuilder.append(" SELECT `purchase_code`,`prec_batchno`,`product_name`,CONCAT(SUM(`prec_recving_quantity`),' ',`unit`)qty,"
						+ " DATE_FORMAT(`prec_created_date`,'%e/%c/%Y')prec_created_date,`supplier_first_name`,"
						+ " CONCAT('Rs ',`prhdr_grand_total_amt`)amount FROM `hero_stock_purchase_received_dtl`a "
						+ " JOIN `hero_stock_product`b ON b.`product_id` = a.`prec_product_id` JOIN `hero_stock_purchase_receive_hdr`c ON"
						+ "  c.`prhdr_id`=a.`prec_hdr_id`JOIN `hero_stock_purchase`d ON d.`purchase_code`=c.`pur_req_id` "
						+ " JOIN `hero_admin_unit_type`e ON e.`unit_type_id`=b.`unit_type_id` JOIN`hero_stock_supplier`F ON "
						+ " F.`supplier_id`=d.`supplier_id`WHERE b.`product_id`= 1  ");
				
				if(request.getStoreid() != null && !request.getStoreid().equals("0"))
				{
					queryBuilder.append(" AND a.`pharmacy_id` = "+request.getStoreid());
				}
				
				queryBuilder.append(" GROUP BY `purchase_code` ");
				
				query = queryBuilder.toString();
				log.info("query   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			else if(reportid == 17)
			{
				queryBuilder.append("SELECT `product_name`,(SELECT CONCAT(COALESCE(SUM(`product_count`),0),' ',`unit`) FROM `hero_stock`a "
				+ " JOIN `hero_stock_product`b ON b.`product_id`=a.`product_id` JOIN `hero_admin_unit_type`c ON  c.`unit_type_id`=b.`unit_type_id`"
				+ " WHERE  a.`created_at` < "+startdate+"   AND b.`product_id` =  q.`product_id` GROUP BY b.`product_id`)opening,"
				+ " (SELECT CONCAT(COALESCE(SUM(`prec_recving_quantity`),0),'  ',`unit`) FROM `hero_stock_purchase_received_dtl`a  "
				+ " JOIN `hero_stock_product`b ON b.`product_id`=a.`prec_product_id` JOIN `hero_admin_unit_type`c ON  c.`unit_type_id`=b.`unit_type_id`"
				+ "  WHERE a.`prec_recving_quantity`> 0 AND (a.`prec_created_date`  BETWEEN "+date+" )  "
				+ " AND b.`product_id` =  q.`product_id` GROUP BY b.`product_id`) purchase, "
				+ " (SELECT CONCAT(COALESCE(SUM(`PREC_RETURN_QTY`),0),'  ',`unit`) FROM `hero_stock_purchase_received_dtl`a    "
				+ " JOIN `hero_stock_product`b ON b.`product_id`=a.`prec_product_id` JOIN `hero_admin_unit_type`c ON  c.`unit_type_id`=b.`unit_type_id`"
				+ " WHERE a.`PREC_RETURN_QTY`> 0 AND (a.`prec_created_date`   BETWEEN "+date+" ) "
				+ "  AND b.`product_id` =  q.`product_id` GROUP BY b.`product_id`)retrn,"
				+ "  (SELECT CONCAT(COALESCE(SUM(`tobe_recvd_prod_count`),0),'  ',`unit`) FROM  `hero_stock_transfer_product`a "
				+ "  JOIN `hero_stock_product`b ON b.`product_id`=a.`product_id` JOIN `hero_admin_unit_type`c ON c.`unit_type_id`=b.`unit_type_id` "
				+ " JOIN `hero_stock_transfer`d ON d.`transfer_id`=a.`transfer_id` WHERE a.`tobe_recvd_prod_count`> 0  AND d.transfer_date  "
				+ " BETWEEN "+date+"  AND b.`product_id` =  q.`product_id` "
				+ "  GROUP BY b.`product_id`  )transfer, (SELECT CONCAT(COALESCE(SUM(`product_count`),0),' ',`unit`) FROM `hero_stock`a "
				+ " JOIN `hero_stock_product`b ON b.`product_id`=a.`product_id` JOIN `hero_admin_unit_type`c ON  c.`unit_type_id`=b.`unit_type_id` "
				+ " WHERE  a.`created_at` < "+enddate+"  AND b.`product_id` =  q.`product_id`  GROUP BY b.`product_id`)closing     "
				+ "   FROM `hero_stock`a  JOIN `hero_stock_product` q ON q.`product_id`=a.`product_id`  JOIN `hero_admin_unit_type`c "
				+ " ON c.`unit_type_id`=q.`unit_type_id`   WHERE a.`product_count`> 0 "
				/*+ " AND a.`created_at` BETWEEN "+date+""*/
						+ " GROUP BY q.`product_id`");		
				
				query = queryBuilder.toString();
				log.info("stock reportquery   "+query);
				reportsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(query, jdbcTemplate);
				
			}
			log.info(reportsList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(reportsList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_RTS_RESPONSEINFO getproductsuggestions() {		
		
		String selectQuery = "SELECT `product_id`,`product_name` FROM hero_stock_product A  WHERE A.status_id = 1";
		
		log.info(selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("label", rs.getString("product_name"));				
				map.put("value", rs.getString("PRODUCT_ID"));	
				
				return map;
			}
		});		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((categoryList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	
	@Override
	public HERO_RTS_RESPONSEINFO getpurchasebyproduct(int productid){	
		
	 String selectQuery = " SELECT `purchase_code`,`prec_batchno`,`product_name`,CONCAT(SUM(`prec_recving_quantity`),' ',`unit`)qty,"
						+ " DATE_FORMAT(`prec_created_date`,'%e/%c/%Y')prec_created_date,`supplier_first_name`,"
						+ " CONCAT('Rs ',FORMAT(`prhdr_grand_total_amt`,2))amount FROM `hero_stock_purchase_received_dtl`a "
						+ " JOIN `hero_stock_product`b ON b.`product_id` = a.`prec_product_id` JOIN `hero_stock_purchase_receive_hdr`c ON"
						+ "  c.`prhdr_id`=a.`prec_hdr_id` JOIN `hero_stock_purchase`d ON d.`purchase_code`=c.`pur_req_id` "
						+ " JOIN `hero_admin_unit_type`e ON e.`unit_type_id`=b.`unit_type_id` JOIN`hero_stock_supplier`F ON "
						+ " F.`supplier_id`=d.`supplier_id` WHERE b.`product_id`= "+productid+"  GROUP BY `purchase_code`";
		
		log.info("getpurchasebyproduct Query  "+selectQuery);
		@SuppressWarnings("unchecked")
		List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("purchasecode", rs.getString("purchase_code"));				
				map.put("productname", rs.getString("product_name"));	
				map.put("qty", rs.getString("qty"));				
				map.put("suppliername", rs.getString("supplier_first_name"));	
				map.put("amount", rs.getString("amount"));			
				map.put("date", rs.getString("prec_created_date"));
				
				return map;
			}
		});		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((categoryList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_RTS_RESPONSEINFO getOutputQtyReport(int productid){	
		
	 String selectQuery = "SELECT `transfer_no`,`product_name`,CONCAT(`product_count`,' ',`unit`)product_count FROM  `hero_stock_transfer`a "
	 		+ " JOIN `hero_stock_transfer_product`b ON a.`transfer_id`=b.`transfer_id` "
	 		+ " JOIN `hero_stock_product`c ON c.`product_id`=b.`product_id`"
	 		+ " JOIN `hero_admin_unit_type`d ON d.`unit_type_id`=c.`unit_type_id` WHERE a.`transfer_id`="+productid+" AND `addproductornot`=1";
		
		log.info("getpurchasebyproduct Query  "+selectQuery);
		@SuppressWarnings("unchecked")
		List<Object> transferList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("transfercode", rs.getString("transfer_no"));				
				map.put("productname", rs.getString("product_name"));	
				map.put("qty", rs.getString("product_count"));	
				
				return map;
			}
		});		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((transferList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_RTS_RESPONSEINFO generateStockReport(String startdate,String enddate){	
		
		log.info("startdate  "+startdate);
		log.info("enddate  "+enddate);	
		
		String stockQuery = "";
		
		log.info("generateStockReport Query  "+stockQuery);
		@SuppressWarnings("unchecked")
		List<Object> stockReportList = jdbcTemplate.query(stockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("transfercode", rs.getString("transfer_no"));				
				map.put("productname", rs.getString("product_name"));	
				map.put("qty", rs.getString("product_count"));	
				
				return map;
			}
		});		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj((stockReportList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
}
