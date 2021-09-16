package com.hero.stock.forms.services.tools.barcode;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;



import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;
import com.hero.stock.util.HERO_STK_INVENTORYLOV;

public class HERO_STK_SERVC_BARCODEDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_SERVC_iBARCODEDAO{
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_BARCODEDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");

	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@Override
	public HERO_STK_RESPONSEINFO getBarcodeProducts(String storeid,String productid,String batchid,String eventtype,String changecomp) {
		// TODO Auto-generated method stub
		log.info("Values in getStockMonitorList      "+storeid);
		
		String productQuery = "SELECT DISTINCT(c.`product_id`),`product_name` FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` JOIN "
				+ "`hero_stock_product` c ON b.`product_id` = c.`product_id` AND `pharmacy_id` = "+storeid;
				
		
		List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		
		if(eventtype != null && eventtype.equals("L"))
		{
			if(productList != null && productList.size() > 0)
			{
			
				HERO_STK_INVENTORYLOV lov = (HERO_STK_INVENTORYLOV)productList.get(0);
				if(lov.getValue()!= null)
				{
					productid = lov.getValue();
				}
			}	
		}
		
		String batchQuery = "SELECT DISTINCT(`batch_id`),`batch_id` label FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` = C.`product_id` JOIN `hero_stock_store` D ON A.`pharmacy_id` = D.`store_id` "
				+ "WHERE A.`pharmacy_id` = "+storeid+" AND B.`product_id` = "+productid;
		log.info("batchQuery in DAO     "+batchQuery);
		List<Object> batchList = inventoryLOVobj.getLOVList(batchQuery);
		if(changecomp != null && !changecomp.equals("B"))
		{
			if(batchList != null && batchList.size() > 0)
			{
			
				HERO_STK_INVENTORYLOV lov = (HERO_STK_INVENTORYLOV)batchList.get(0);
				if(lov.getValue()!= null)
				{
					batchid = lov.getValue();
				}
			}
		}
		
		
		String rateQuery = "SELECT DISTINCT(`product_rate`),`product_rate` label FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` = C.`product_id` JOIN `hero_stock_store` D ON A.`pharmacy_id` = D.`store_id` "
				+ "WHERE A.`pharmacy_id` = "+storeid+" AND B.`product_id` = "+productid+" and batch_id = '"+batchid+"'";
		log.info("rateQuery in DAO     "+rateQuery);
		List<Object> rateList = inventoryLOVobj.getLOVList(rateQuery);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("productList", productList);
		map.put("batchList", batchList);
		map.put("rateList", rateList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO printBarcode(HERO_STK_SERVC_BARCODEREQUEST request) {
		// TODO Auto-generated method stub
		try
		{
		log.info("Values in DAO Class     "+request.getStoreid()+"   "+request.getPrinttype()+"   "+request.getProduct()+"   "+request.getStyle());
		
		String productQuery ="SELECT DISTINCT(C.`product_id`)product_id,`product_name`,`product_code`,"
				+ "FORMAT(`product_rate`,2)product_rate,`currency`,"
				+ "`CURR_SYMBOL`,`batch_id`,`store_name`/*,date_format(`prec_expiry_date`,'%e/%c/%y')prec_expiry_date*/"
				+ ",CONCAT(COALESCE(product_code,''),'$$$',COALESCE(batch_id,''))barcodecontent,tpr_id "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` = C.`product_id` JOIN `hero_stock_store` D ON A.`pharmacy_id` = D.`store_id` "
				+ "JOIN `hero_admin_currency` e ON e.`currency_id` = d.`currency_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` f ON f.`prec_product_id` = b.`product_id` AND f.`prec_product_id` = c.`product_id` "
				+ "AND f.`prec_batchno` = b.`batch_id` WHERE A.`pharmacy_id` = "+request.getStoreid()+" AND B.`product_id` = "+request.getProduct()
				+" AND batch_id = '"+request.getBatchid()+"' and product_rate = '"+request.getRate()+"'";
		
		log.info("productQuery in print method   "+productQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(productQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("productcode", rs.getString("product_code"));
				map.put("productrate", rs.getString("product_rate"));
				map.put("currency", rs.getString("currency"));
				map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("storename", rs.getString("store_name"));
				map.put("barcodecontent", rs.getString("tpr_id"));
				
				return map;
				}
		});
		log.info(productList);
		
		int loopcount = 0;
		
		if(request.getStyle() == 1 || request.getStyle() == 2)
		{
			loopcount = 10;
		}
		else if(request.getStyle() == 3)
		{
			loopcount = 6;
		}
		else if(request.getStyle() == 4)
		{
			loopcount = 5;
		}
		else if(request.getStyle() == 5)
		{
			loopcount = 6;
		}
		else if(request.getStyle() == 6)
		{
			loopcount = 7;
		}
		else if(request.getStyle() == 7)
		{
			loopcount = 4;
		}
		else if(request.getStyle() == 8)
		{
			loopcount = 5;
		}
		else if(request.getStyle() == 9)
		{
			loopcount = request.getContinuous();
		}
		
		log.info("loopcount      "+loopcount);
		
		List<Object> printList = new ArrayList<Object>();
		Iterator<Object> productListITR = productList.iterator();
		while(productListITR.hasNext())
		{
			Map<String, Object> map = (Map<String, Object>) productListITR.next();
			map.put("style", request.getStyle());
			for(int loop = 0;loop<loopcount;loop++)
			{
				StringBuilder sb = new StringBuilder();
				if(request.getPrinttype() != null && request.getPrinttype().contains("PROD_NAME"))
				{
					if(map.get("productname") != null)
					{
						sb.append((String)map.get("productname"));
						sb.append("\n");
					}
				}
				if(request.getPrinttype() != null && request.getPrinttype().contains("SKU"))
				{
					if(map.get("productcode") != null)
					{
						sb.append((String)map.get("productcode"));
						sb.append("\n");
					}
				}
				if(request.getPrinttype() != null && request.getPrinttype().contains("PRICE"))
				{
					if(map.get("currencysymbol") != null)
					{
						sb.append((String)map.get("currencysymbol"));
						sb.append(" ");
					}
					if(map.get("productrate") != null)
					{
						sb.append((String)map.get("productrate"));
						sb.append("\n");
					}
					
				}
				if(request.getPrinttype() != null && request.getPrinttype().contains("BATCH_NO"))
				{
					if(map.get("batchno") != null)
					{
						sb.append((String)map.get("batchno"));
						sb.append("\n");
					}
				}
				if(request.getPrinttype() != null && request.getPrinttype().contains("STORE_NAME"))
				{
					if(map.get("storename") != null)
					{
						sb.append((String)map.get("storename"));
						sb.append("\n");
					}
				}
				
				
				map.put("content", sb.toString());
				printList.add(map);	
			}
		}
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(printList);
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
