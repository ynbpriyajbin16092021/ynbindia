package com.hero.stock.forms.transactions.stock;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST;
import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_STK_STOCKMODULEDAOIMPL implements HERO_STK_ISTOCKMODULEDAO {
	private static Logger log = Logger.getLogger(HERO_STK_STOCKMODULEDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	
	public String recvStatus = "";
	
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@Autowired
	HERO_STK_RESPONSE inventoryResponseOBJ;
	@Autowired
	HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public HERO_STK_RESPONSEINFO updatestockprice(String stockData) {
		// TODO Auto-generated method stub
try
{
		log.info("stockData     "+stockData);
		
		//HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(stockData, "com.inv.forms.transactions.stock.StockRequest");
		HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(stockData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
		
		log.info("Values are     "+request.getBatchid()+"   "+request.getOprn()+"   "+request.getProductcount()+"   "+request.getProductid()+"   "+
				request.getSellprice()+"   "+request.getStatus()+"   "+request.getStockid());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_MODULE");
		
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		inParamMap.put("P_STOCK_ID", request.getStockid());
		inParamMap.put("P_PRODUCT_ID", request.getProductid());
		inParamMap.put("P_BATCH_ID",  request.getBatchid());
		inParamMap.put("P_PRODUCT_COUNT",  request.getProductcount());
		inParamMap.put("P_STATUS", 1);
		inParamMap.put("P_SELL_PRICE", request.getSellprice());
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		 
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
}
catch(Exception e)
{
	error_log.info(e);
	HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}
	public HERO_STK_RESPONSEINFO getuomforpacking(String uompackingid) {
		// TODO Auto-generated method stub
		try
		{
		log.info("uompackingid   "+uompackingid);
		/*String uompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_sno`,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 0 "
				+ "UNION "
				+ "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,(`hsuts_sno`)+1,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 ORDER BY `hsuts_sno`";*/
		
		Map<String, Object> uomPackMap = new HashMap<String, Object>();
		
		String fulluompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_sno`,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 0 "
				+ "ORDER BY `hsuts_sno`";
		List<Map<String, Object>> fulluomPackingList = jdbcTemplate.queryForList(fulluompackingQuery);
		List<Map<String, Object>> fulluomPacking = new ArrayList<Map<String,Object>>();
		
		log.info("fulluomPackingList   "+fulluomPackingList);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"uompackingsel\" onchange=\"calculatelooseqty()\">");
		
		Iterator<Map<String, Object>> fulluomPackingListITR = fulluomPackingList.iterator();
		while(fulluomPackingListITR.hasNext())
		{
			Map<String, Object> fulluomPackingMap = fulluomPackingListITR.next();
			int sno = (int) fulluomPackingMap.get("hsuts_sno");
			long uomid = (long)fulluomPackingMap.get("unit_type_id");
			sb.append("<option value='"+String.valueOf(sno).concat("-").concat(String.valueOf(uomid))+"'>"+fulluomPackingMap.get("uomdesc")+"</option>");
			
			fulluomPackingMap.put("uomval", String.valueOf(sno).concat("-").concat(String.valueOf(uomid)));
			fulluomPacking.add(fulluomPackingMap);
		}
		sb.append("</select>");
		uomPackMap.put("fulluomsel", sb.toString());
		
		sb = new StringBuilder();
		
		String looseUOMCountQuery = " SELECT * FROM `hero_stock_unit_type_config` a JOIN `hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) "
				+ "WHERE `hsuts_id` = "+uompackingid;
		List<Object> looseUOMCount = jdbcTemplate.queryForList(looseUOMCountQuery);
		
		String looseuompackingQuery = "";
		
		if(looseUOMCount.size() > 1)
		{
			looseuompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,`hsuts_sno`,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `hsuts_sno` > 1  AND `uom_type` = 0 "
					+ "UNION "
					+ "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,(`hsuts_sno`)+1,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 AND `hsuts_sno` > 1 "
					+ "ORDER BY `hsuts_sno`;";	
		}
		else
		{
			looseuompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,((`hsuts_sno`)+1) hsuts_sno,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 "
					+ "ORDER BY `hsuts_sno`;";
		}
		
		List<Map<String, Object>> looseuomPackingList = jdbcTemplate.queryForList(looseuompackingQuery);
		List<Map<String, Object>> looseuomPacking = new ArrayList<Map<String,Object>>();
		
		log.info("looseuomPackingList   "+looseuomPackingList);
		
		sb = new StringBuilder();
		sb.append("<select id=\"uompackingsel\" onchange=\"calculatelooseqty()\">");
		
		Iterator<Map<String, Object>> looseuomPackingListITR = looseuomPackingList.iterator();
		while(looseuomPackingListITR.hasNext())
		{
			Map<String, Object> looseuomPackingMap = looseuomPackingListITR.next();
			long sno = (long) looseuomPackingMap.get("hsuts_sno");
			long uomid = (long)looseuomPackingMap.get("unit_type_id");
			sb.append("<option value='"+String.valueOf(sno).concat("-").concat(String.valueOf(uomid))+"'>"+looseuomPackingMap.get("uomdesc")+"</option>");
			
			looseuomPackingMap.put("uomval", String.valueOf(sno).concat("-").concat(String.valueOf(uomid)));
			looseuomPacking.add(looseuomPackingMap);
		}
		sb.append("</select>");
		uomPackMap.put("looseuomsel", sb.toString());
		
		uomPackMap.put("fulluomPackingList", fulluomPacking);
		uomPackMap.put("looseuomPackingList", looseuomPacking);
		
		inventoryResponseOBJ.setResponseObj("S");
		inventoryResponseOBJ.setResponseObj(uomPackMap);
		
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
	public HERO_STK_RESPONSEINFO calculatelooseqty(String uomdata) {
		// TODO Auto-generated method stub
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomdata, HERO_STK_ADDPURCHASEORDERREQUEST.class);
		log.info("uomdata  "+uomdata);
		log.info("Values are     "+request.getFulluomqty()+"   "+request.getLooseuomqty()+"   "+request.getFulluom()+"   "+request.getLooseuom());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_CALC_LOOSE_QTY");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_FULL_UOM_SNO", request.getFulluomsno());
		inParamMap.put("P_LOOSE_UOM_SNO", request.getLooseuomsno());
		inParamMap.put("P_LOOSE_UOM", request.getLooseuom());
		inParamMap.put("P_FULL_QTY", request.getFulluomqty());
		inParamMap.put("P_LOOSE_QTY", request.getLooseuomqty());
		inParamMap.put("P_UOM_PACKING_ID", request.getUompacking());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_STK_RESPONSEINFO loadProductSuggestion() {
		// TODO Auto-generated method stub
		/*String productQuery = "SELECT DISTINCT(a.`product_id`),`product_name` FROM hero_stock_product a,`hero_stock` b WHERE a.`product_id` = b.`product_id`";*/
		
		String productQuery = "SELECT DISTINCT(a.`product_id`),CONCAT(`product_name`)product_name "
				+ "FROM hero_stock_product a JOIN `hero_stock` b LEFT JOIN `hero_admin_company` C ON C.`company_id` = a.`manufacturer_id` "
				+ "WHERE a.`product_id` = b.`product_id`";
		
		log.info(productQuery);
		List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO loadadjustmenthistory() {
		
		String productQuery = "SELECT DATE_FORMAT(`sa_adjustment_date`,'%e/%c/%Y')sa_adjustment_date,`sa_adjustment_reason`,`sap_prod_id`,`sap_batch_no`,`sap_adjust_qty`,"
				+ " FORMAT(`sap_unit_price`,2)sap_unit_price,`sar_reason`,`product_name` FROM `hero_stock_adjustment` A "
				+ " JOIN `hero_stock_adjustment_product` B ON A.`sa_id` = B.`sa_id` "
				+ " LEFT JOIN `hero_stock_adjustment_reason` C ON A.`sa_adjustment_reason` = C.`sar_id`"
				+ " JOIN `hero_stock_product` D ON D.`product_id` = B.`sap_prod_id`";
		
		
		@SuppressWarnings("unchecked")
		List<Object> productStockList = jdbcTemplate.query(productQuery.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("reasonid", rs.getString("sa_adjustment_reason"));
				map.put("adjdate", rs.getString("sa_adjustment_date"));
				map.put("productname", rs.getString("product_name"));
				map.put("price", rs.getString("sap_unit_price"));
				map.put("adjreason", rs.getString("sar_reason"));
				map.put("adjbatch", rs.getString("sap_batch_no"));
				map.put("adjqty", rs.getString("sap_adjust_qty"));
			
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productStockList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO loadAdjProductSuggestion(String storeId) {
		// TODO Auto-generated method stub
		
		String productQuery = "SELECT DISTINCT(C.`product_id`),CONCAT(`product_name`,' - ',`company_name`)product_name "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` =  C.`product_id` "
				+ "LEFT JOIN `hero_admin_company` D ON D.`company_id` = C.`manufacturer_id` WHERE `pharmacy_id` = '"+storeId+"'";
		log.info("productQuery    "+productQuery);
		
		List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadPurchaseOrderReqSelectBox() {
		// TODO Auto-generated method stub
		
		String productQuery = "SELECT DISTINCT(`purchase_code`),`purchase_id` FROM `hero_stock_purchase_order` a JOIN "
				+ "`hero_stock_purchase_order_request` b ON a.`purchase_code` = b.`pur_req_id` "
				+ "WHERE `purchase_raised` = 0";
		log.info("productQuery    "+productQuery);
		
		List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadBatchid(String productId) {
		// TODO Auto-generated method stub
		String batchQuery = "SELECT `batch_id`,CONCAT(`batch_id`,'(',DATE_FORMAT(`expiry_date`,'%e/%c/%Y'),')')expiry FROM `hero_stock`  "
				+ "WHERE `product_id` = "+productId+" AND `product_count` != 0";
		
		List<Object> batchList = inventoryLOVobj.getLOVList(batchQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(batchList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getstockproductdetail(String productId,
			String batchid,String storeid,String type) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder("SELECT DISTINCT(a.`product_id`)product_id,b.`cgst`,b.`sgst`,(SELECT `hsuts_id` FROM "
				+ "`hero_stock_purchase_received_dtl` WHERE `prec_product_id` ="+productId+"  AND `prec_batchno`= '"+batchid+"' "
						+ " AND `prec_recving_quantity`!=0 LIMIT 1)uom,COALESCE(c.`tpr_id`,0)tpr_id,"
						+ "COALESCE(c.`transfer_id`,0)transfer_id,DATE_FORMAT(b.`expiry_date`,'%Y/%c/%e')expiry_date, "
				+ "`product_name`,b.`product_count` sourcecount,COALESCE(c.`product_count`,0)destinationcount,"
				+ "F_HERO_PROD_PRICE(a.`product_id`,'"+storeid+"',b.`batch_id`,'UNIT_PRICE') sell_price,"
				+ "F_HERO_PROD_PRICE(a.`product_id`,'"+storeid+"',b.`batch_id`,'UNIT_PRICE_DISP') unit_price_disp,b.`product_count` REMAININGQTY,"
				+ "tobe_recvd_prod_count, (SELECT `CURR_CONVERSION_RATE` FROM hero_admin_currency p JOIN `hero_stock_store` q"
				+" ON p.`currency_id` = q.`currency_id` JOIN `hero_stock` r  WHERE  r.`product_id` = '"+productId+"' AND q.`store_id` = '"+storeid+"' "
				+ " AND r.`batch_id` = '"+batchid+"' AND CURDATE() BETWEEN `CURR_FROM_DATE` AND `CURR_TO_DATE`)conversionrate FROM hero_stock_product a,`hero_stock` b "
				+ " LEFT JOIN `hero_stock_transfer_product` c ON c.`product_id` = b.`product_id`  AND b.`batch_id` = c.`batch_id`"
				+ " LEFT JOIN `hero_stock_transfer` d ON d.`transfer_id` = `tpr_id` "
				+ "WHERE a.`product_id` = b.`product_id` AND a.`product_id` = "+productId+" AND b.`batch_id` = '"+batchid+"'");

		if(type != null && type.equalsIgnoreCase("A"))
		{
			query.append(" AND c.`product_count` > 0");
		}
		 
		log.info("query       "+query.toString());
		log.info("values are    "+productId+"   "+batchid);
		
		@SuppressWarnings("unchecked")
		List<Object> productStockList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getString("product_id"));
				map.put("uompackingid", rs.getString("uom"));
				map.put("productname", rs.getString("product_name"));
				
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("expirydate", rs.getString("expiry_date"));
				
				map.put("sourcecount", rs.getString("sourcecount"));
				map.put("unitprice", rs.getString("sell_price"));
				map.put("unitpricedisp", rs.getString("unit_price_disp"));
				map.put("conversionrate", rs.getString("conversionrate"));
				map.put("destinationcount", rs.getString("destinationcount"));
				map.put("tprid", rs.getString("tpr_id"));
				map.put("transferid", rs.getString("transfer_id"));
				map.put("existproductcount", rs.getInt("REMAININGQTY"));
				return map;
			}
		});
		log.info(productStockList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productStockList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO loadStockTxrPagewise(String pageno,HttpServletRequest httpRequest) {
		log.info("pageno   "+pageno);
		
		int intPageno = Integer.parseInt(pageno);
		int start = ((intPageno - 1) * 10);
		int end = ((intPageno) * 10);
		log.info("Start   "+start+"   End   "+end);
		
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		
		StringBuilder stocktransferpageQuerySB = new StringBuilder();
		stocktransferpageQuerySB.append("SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,"
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON A.`pharmacy_id` = C.`store_id` "
				+ "WHERE A.`delivery_status` = B.`status_id`");
		
		if(usertype > 2)
		{
			stocktransferpageQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		stocktransferpageQuerySB.append("  ORDER BY `transfer_id` DESC LIMIT "+start+","+end);
		
		String stocktransferpageQuery = stocktransferpageQuerySB.toString();
		
		log.info("stocktransferpageQuery      "+stocktransferpageQuery);
		  
		List<Object> stockTransferpageList = inventoryLOVobj.getStockTransferOrderList(stocktransferpageQuery);
		  
		  /*log.info("stockTransferpageList     "+stockTransferpageList);*/

		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(stockTransferpageList);
		  
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		  
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO loadStockTxrHistory(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder("SELECT transfer_id,delivery_status,`transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,"
				+ "a.`status_id`,C.`status_desc`,`ord_ref_no`,`order_id`,"
				+ "(SELECT SUM(`tobe_recvd_prod_count`) FROM `hero_stock_transfer_product` SUB WHERE SUB.`transfer_id` = a.`transfer_id`)PROD_COUNT,"
				+ "(SELECT FORMAT((SUM(`product_rate`*`CURR_CONVERSION_RATE`)),2) FROM `hero_stock` STK,`hero_stock_transfer_product` stk_prod,`hero_stock_transfer` TXR"
				+ " WHERE STK.`product_id` = stk_prod.`product_id` AND stk_prod.`transfer_id`=TXR.`transfer_id` AND stk_prod.`batch_id` = STK.`batch_id` "
				+ " AND TXR.`transfer_id` = a.`transfer_id`)AMOUNT,`store_name`,`gcs_html_code`,`currency` FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C "
				+ "ON a.`delivery_status` = C.`status_id`"
				+ " LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id` LEFT JOIN `hero_admin_currency` d ON d.`currency_id` = s.`currency_id`  "
				+ " LEFT JOIN `hero_global_currency_symbols` e  ON d.`CURR_SYMBOL`=e.`gcs_id` LEFT JOIN  `hero_order_request` g ON g.`ord_req_id`=a.`order_id`");
		
		int usertpe = (int)httpRequest.getSession().getAttribute("usertype");
		//ENABLE FOR ALL USERS
		/*if(usertpe > 2)
		{
			query.append("JOIN `hero_user` f ON f.`user_store_id` = s.`store_id` AND `userid` = "+httpRequest.getSession().getAttribute("uid"));
		}*/
		
		query.append(" ORDER BY `transfer_id` DESC");
		
		log.info("loadStockTxrHistory query       "+query);
		
		String recvStatusQuery = "SELECT `status_id`,`status_desc` FROM `hero_stock_trxr_status`";
		
		@SuppressWarnings("unchecked")
		List<Object> recvList = jdbcTemplate.query(recvStatusQuery, new RowMapper() {
			StringBuilder status = new StringBuilder();
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				status.append("<option value='"+rs.getInt(1)+"'>"+rs.getString(2)+"</option>");
				return status.toString();
			}
		});
		
		
		if(recvList != null && recvList.size() > 0)
		{
			recvStatus = (String) recvList.get(recvList.size() - 1);
		}
		log.info("recvStatus      "+recvStatus);
		
		@SuppressWarnings("unchecked")
		List<Object> stockList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				map.put("orderRefNo", rs.getString("ord_ref_no"));
				map.put("orderid", rs.getString("order_id"));
				map.put("transferid", rs.getString("transfer_id"));
				map.put("gcs_html_code", rs.getString("gcs_html_code"));
				map.put("transferno", rs.getString("transfer_no"));
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("statusid", rs.getString("status_id"));
				map.put("deliverystatus", rs.getInt("delivery_status"));
				if(rs.getInt("delivery_status") == 1)
				{
					map.put("status", "<select  class='form-control form-white  selectNor'"
							+ "onchange='changerecvstatus("+index+");' id='statusselect"+index+"'>"
							+ recvStatus+ "</select>");	
				}
				else
				{
					map.put("status", rs.getString("status_desc"));	
				}
				
				
				map.put("productcount", rs.getString("PROD_COUNT"));
				map.put("amount", rs.getString("AMOUNT"));
				map.put("storename", rs.getString("store_name"));
				
				Double grandtotal = (rs.getDouble("AMOUNT") * rs.getInt("PROD_COUNT"));
				
				DecimalFormat df = new DecimalFormat("#.##");
				
				String amountdisp = df.format(grandtotal);
				
				amountdisp = (rs.getString("currency") == null) ? "" : (" "+rs.getString("currency")) +". "+ amountdisp;
				
				String unitPriceDisp = rs.getString("AMOUNT");
				unitPriceDisp += rs.getString("currency") == null ? "" : " "+rs.getString("currency");
				
				map.put("unitpricedisp", unitPriceDisp);
				
				map.put("amountdisp", amountdisp);
				
				if(rs.getInt("delivery_status") == 1)
				{
					/*map.put("action", "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\""
							+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>"
							+ "<button class=\"save myBtnTab\" onclick=\"saverecvstatus("+index+","+rs.getString("transfer_id")+");\"> "
									+ "<i class=\"fa fa-save\"></i> </button>");*/
					
					map.put("action", "<button class=\"delete myBtnTab\""
							+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>"
							+ "<button class=\"save myBtnTab\" onclick=\"saverecvstatus("+index+","+rs.getString("transfer_id")+");\"> "
									+ "<i class=\"fa fa-save\"></i> </button>");
					
				}
				else if(rs.getInt("delivery_status") == 3)
				{
					map.put("action", "<button class=\"delete myBtnTab\""
							+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>");	
				}
				else
				{
					map.put("action", "");
				}
				/*log.info("transferid       "+rs.getString("transfer_id")+"   "+
"<a href='stocktransferhistoryview?tno='"+rs.getString("transfer_no")+"'>"+rs.getString("transfer_no")+"</a>");*/
				map.put("stocktransfernavigate", "<a href='stocktransferhistoryview?tid="+rs.getString("transfer_id")+"'>"+rs.getString("transfer_no")+"</a>");
				map.put("index", index);
				
				return map;
			}
		});
		log.info(stockList);
		
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(stockList);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadtransferlist(String transferid,String storeid) {
		// TODO Auto-generated method stub
		 
		String stockTxrQuery = "SELECT a.`transfer_id`,transfer_no,tpr_id,b.`product_id`,`product_name`,b.`batch_id`,d.`product_count` sourcecount,"
				+ "b.`product_count` destcount,pharmacy_id,"
				+ "`tobe_recvd_prod_count`,F_HERO_PROD_PRICE(b.`product_id`,pharmacy_id,d.`batch_id`,'UNIT_PRICE_DISP')unitprice_disp,"
				+ "F_HERO_PROD_PRICE(b.`product_id`,pharmacy_id,d.`batch_id`,'UNIT_PRICE') sell_price,d.`product_count` REMAININGQTY"
						+ " FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b"
				+ " ON a.`transfer_id` = b.transfer_id LEFT JOIN `hero_stock_product` c ON c.`product_id` = b.`product_id` LEFT JOIN "
				+ "`hero_stock` d ON d.`product_id` = c.`product_id`  AND b.`batch_id` = d.`batch_id` "
				+ "WHERE a.`transfer_id` = "+transferid;
		
		log.info("stockTxrQuery      "+stockTxrQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> receivestockList = jdbcTemplate.query(stockTxrQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				index++;
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("tprid",rs.getString("tpr_id"));
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("batchid", rs.getString("batch_id"));
				map.put("stockdetls", "<div class='row text-muted font-sm'><div class='col-md-6'>Source Stock</div>" +
				"<div class='separationline col-md-6'>Destination Stock</div></div><div class='row font-xs'><div class='col-md-6'>"+rs.getString("destcount")+"</div>" +
				"<div class='separationline col-md-6'>"+rs.getString("sourcecount")+"</div></div>");

				map.put("quantity", "<input type='number' id='quantity"+index+"' value='1'  class='form-control form-Tabel' "
				+ "onblur='updatequantity("+index+")'>" +
				"<input type='text' id='sourcequantity"+index+"' value='"+rs.getString("tobe_recvd_prod_count")+"'  class='form-control form-Tabel'>");
				map.put("productid", rs.getString("product_id"));
				map.put("transferid",rs.getString("transfer_id"));
				map.put("transferno",rs.getString("transfer_no"));
				map.put("productcount", rs.getString("tobe_recvd_prod_count"));
				
				map.put("unitprice", rs.getString("sell_price"));
				map.put("unitpricedisp", rs.getString("unitprice_disp"));
				map.put("action", "<button class=\"delete myBtnTab\" data-toggle=\"modal\" data-target=\"#modal-delet\" " +
						"name='record"+index+"'> <i class=\"fa fa-trash-o\"></i> </button>");
				map.put("sourcecount", rs.getString("sourcecount"));
				map.put("destcount", rs.getString("destcount"));
				map.put("pharmacyid", rs.getString("pharmacy_id"));
				map.put("index", index);
				map.put("existproductcount", (rs.getInt("tobe_recvd_prod_count") + rs.getInt("REMAININGQTY")));
				
				return map;
			}
		});
		log.info(receivestockList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(receivestockList);	
	
	inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
	
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO stockdetail(String transferid) {
		// TODO Auto-generated method stub
		 
		String stockTxrQuery = "SELECT `transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,`pharmacy_id`,`store_name` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_store` ON `store_id` = `pharmacy_id` WHERE `transfer_id` = "+transferid;
		
		String stockProdQuery = "SELECT a.transfer_id,delivery_status,`transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,a.`status_id`,"
				+ "`status_desc`,tobe_recvd_prod_count,"
				+ "((SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`))full_uom,`pur_full_qty`,"
				+  "((SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_loose_uom`))loose_uom,`pur_loose_qty`,"
				+ "`product_rate`,`store_name`,`gcs_html_code` currency,b.`product_id` ,`product_name`,"
				/*+ " (SELECT DISTINCT `prhdr_bill_no`  FROM  `hero_stock_purchase_receive_hdr` m JOIN "
				+ " `hero_stock_purchase_received_dtl` n ON m.`prhdr_id` = n.`prec_hdr_id` WHERE `prec_batchno` =  `batch_id` )batch_id "
*/				+ "  CASE WHEN b.`dishtypeid` = 0 THEN 'To add flavours ' ELSE `dishtype_name` END AS dishtype_name,"
				+ "CASE WHEN f.`dish_count` > 0 THEN CONCAT('For  ',f.`dish_count`,' Persons ') ELSE '' END AS dish_count,`companyname`  "
				+ "FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C ON a.`delivery_status` = C.`status_id` "
				+ "JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` "
				+ "JOIN `hero_stock_product` p ON b.`product_id` = p.`product_id` LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id` "
				+ "LEFT JOIN `hero_admin_currency` d ON d.`currency_id` = s.`currency_id`"
				+ " LEFT JOIN `hero_global_currency_symbols` e ON e.`gcs_id`=d.`CURR_SYMBOL`"
				+ "LEFT JOIN `hero_stock_dishes_setup_hdr`f ON f.`dish_type_id`=b.`dishtypeid` AND f.`dish_name_id`=`dishid` "
				+ " JOIN `hero_stock_client_company`h ON h.`companyid`=b.`customer_id`"
				+ "LEFT JOIN `hero_stock_dish_type`g ON g.`dishtype_id`=b.`dishtypeid` WHERE a.`transfer_id` ="+transferid+" "
				+ "ORDER BY a.`transfer_id`,h.`companyid`,g.`dishtype_id` DESC";
		
		log.info("stockTxrQuery      "+stockTxrQuery);
		log.info("stockProdQuery      "+stockProdQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> receivestockList = jdbcTemplate.query(stockTxrQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				index++;
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("transferno", rs.getString("transfer_no"));
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("pharmacyid", rs.getString("pharmacy_id"));
				map.put("storename", rs.getString("store_name"));
				
				
				return map;
			}
		});
		
		@SuppressWarnings("unchecked")
		List<Object> receivestockProdList = jdbcTemplate.query(stockProdQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				index++;
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("transferid", rs.getString("transfer_id"));
				map.put("deliverystatus", rs.getString("delivery_status"));
				map.put("transferno", rs.getString("transfer_no"));
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("statusid", rs.getString("status_id"));
				map.put("statusdesc", rs.getString("status_desc"));
				map.put("productcount", rs.getString("tobe_recvd_prod_count")+" "+rs.getString("loose_uom"));
				map.put("storename", rs.getString("store_name"));
				map.put("currency", rs.getString("currency"));
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				/*map.put("batchno", rs.getString("batch_id"));*/
				map.put("index", index);
				/*map.put("productRate", rs.getString("currency")+" "+rs.getString("product_rate"));*/
				map.put("productRate", rs.getString("currency")+" "+HEROHOSURINVENTORYUTIL.convertDecimalFormat(2, rs.getDouble("product_rate")));
				map.put("fullqty", rs.getString("pur_full_qty")+" "+rs.getString("full_uom"));
				map.put("looseqty", rs.getString("pur_loose_qty")+" "+rs.getString("loose_uom"));
				double productrate = rs.getDouble("product_rate");
				int quantity = rs.getInt("tobe_recvd_prod_count");
				double totalamount = productrate * quantity;
				
				map.put("totalamount", rs.getString("currency")+" "+HEROHOSURINVENTORYUTIL.convertDecimalFormat(2, totalamount));
				log.info("totalamount "+new DecimalFormat("#.##").format(totalamount));
				map.put("dishtypename", rs.getString("dishtype_name"));
				map.put("dishcount", rs.getString("dish_count"));
				map.put("dishdisplay", rs.getString("dishtype_name")+"    "+rs.getString("dish_count"));
				map.put("companyname", rs.getString("companyname"));
				return map;
			}
		});
		
		log.info(receivestockList);
		log.info(receivestockProdList);
		
		String transferDate = "";
		if(receivestockList != null && receivestockList.size() > 0)
		{
			Map<String, Object> listMap = (Map<String, Object>) receivestockList.get(0);
			transferDate = (String) listMap.get("transferdate");
			
		}
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("receivestockList", receivestockList);
		map.put("receivestockProdList", receivestockProdList);
		map.put("transferDate", transferDate);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);	
	
	inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
	
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO saveStocktransfer(String transferData) {
		// TODO Auto-generated method stub
try
{
		log.info("transferData     "+transferData);
		
		HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(transferData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
		
		log.info("Values are     "+request.getTransferdate()+"  "+request.getCgst()+"   "+request.getSgst()+" "
				+ "  "+request.getTransferid()+"   "+request.getTransferno()+"  "+request.getExpirydate()+"  "+request.getPharmacyid());

		List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = new ArrayList<HERO_STK_STOCKTRANSFERITEMDETAILS>();
		
		if(request.getOprn() != null && !request.getOprn().equals("DEL"))
		{
			itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");	
		}

		log.info("itemList      "+itemList);
		
 boolean stockExist = false;
 Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> stockItr = itemList.iterator();
 while(stockItr.hasNext())
 {
	 HERO_STK_STOCKTRANSFERITEMDETAILS item = stockItr.next();
	 String transferDate = /*HosurInventoryUtil.convertToSQLDate(request.getTransferdate();*/"";
	 String itemCountQuery = "SELECT * FROM `hero_stock_transfer` a,`hero_stock_transfer_product` b WHERE  a.`transfer_id` = b.`transfer_id` AND "
	 		+ "`pharmacy_id` = "+request.getPharmacyid()+" AND `delivery_status` = 1 AND `batch_id` = '"+item.getBatchid()+"' "
	 				+ "AND `transfer_date` = '"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate())+"' AND a.`transfer_id` != "+request.getTransferid();
	 log.info("itemCountQuery   "+itemCountQuery);
	 @SuppressWarnings("unchecked")
		List<Object> itemCountList = jdbcTemplate.query(itemCountQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				index++;
				return index;
			}
		});
		log.info(itemCountList);
		
		if(itemCountList != null && itemCountList.size() > 0)
		{
			stockExist = true;
			break;
		}
 }
 
		log.info("stockExist     "+stockExist);
		
		if(!stockExist)
		{

			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCKTXR_MODULE");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_TXR_ID", request.getTransferid());
			inParamMap.put("P_TXR_NO", request.getTransferno());
			inParamMap.put("P_PHARMACY_ID", request.getPharmacyid());
			inParamMap.put("P_DELV_STATUS", request.getDeliverystatus());
			inParamMap.put("P_TRXR_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate()));
			inParamMap.put("P_STATUS", request.getStatus());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_OPRN", request.getOprn());  
			inParamMap.put("P_ORDER_ID", request.getOrderId());  
			
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			String transferId = null;
			
			
			 
			transferId = (String)resultMap.get("out_genrate_id");	
			log.info("transferId       "+transferId);
			Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> itr = itemList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS obj = itr.next();
				obj.setTransferid(transferId);
				obj.setOprn(request.getOprn());
				obj.setUserid(request.getUserid());
				
				
				itemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info("Item Values       "+obj.getIndex()+"   "+obj.getTprid()+"   "+obj.getProductid()+"   "+obj.getTransferid()+"   "+obj.getProductcount()
						+"   "+obj.getStatusid()+"   "+obj.getBatchid()+"   "+obj.getUnitprice()+"   "+obj.getUnitpricedisp()+"  "
								+ " "+obj.getCgst()+"   "+obj.getSgst()+"   "+obj.getExpirydate()+"  "+obj.getOprn());
			}	
			
			
			if(transferId != null && !transferId.equals("0"))
			System.out.println(itemList);
				savestockproductitems(itemList);	
			
			
			
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		else
		{
			inventoryResponseOBJ.setResponseType("F");
			inventoryResponseOBJ.setResponseObj("Transaction Already Exists");
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
		}
		
}
catch(Exception e)
{
	error_log.info(e);
	HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void savestockproductitems( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list) 
	{
		

		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_TXR_ITEMS( ?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
				log.info("Item Values    "+item.getTprid()+"  "+item.getProductid()+"   "+item.getTransferid()+"  "
						+ " "+item.getProductcount()+"    "+item.getConversionrate()+" "+item.getFulluom()+""
						+ " "+item.getFulluomqty()+"  "+item.getLooseuom()+"  "+item.getLooseuomqty()+" "
						+ ""+item.getUompacking()+" "+item.getStatusid()+" "+item.getBatchid()+" "
						+ ""+item.getUnitprice()+"  "+item.getUnitprice()+"  "+item.getUserid()+"        "
					    + " "+item.getOprn()+" "+item.getCgst()+"  "+item.getSgst()+"  "+item.getExpirydate());
				
				ps.setString(1, item.getTprid());
				ps.setString(2, item.getProductid());
				ps.setString(3, item.getTransferid());
				ps.setString(4, item.getProductcount());
				ps.setString(5, item.getConversionrate());
				
				ps.setString(6, item.getFulluom());
				ps.setString(7, item.getFulluomqty());
				ps.setString(8, item.getLooseuom());
				ps.setString(9, item.getLooseuomqty());
				ps.setString(10, item.getUompacking());
				
				ps.setString(11, item.getStatusid());
				ps.setString(12, item.getBatchid());
				ps.setString(13, item.getUnitprice());
				ps.setString(14, item.getUnitpricedisp());
				/*ps.setString(14, item.getUnitprice());*/
				ps.setString(15, item.getUserid());
				ps.setString(16, item.getOprn());
				
				ps.setString(17, item.getCgst());
				ps.setString(18, item.getSgst());
				ps.setString(19, item.getExpirydate());
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			e.printStackTrace();
			throw e;
		}
	}

	@Override
	public HERO_STK_RESPONSEINFO savestockstatusupdate(String statusData) {
		// TODO Auto-generated method stub
			log.info("statusData     "+statusData);
		try
		{
		HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(statusData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
		
		log.info("Values are     "+request.getTransferid()+"   "+request.getDeliverystatus()+"   "+request.getUserid());

		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCKTXR_MODULE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_TXR_ID", request.getTransferid());
		inParamMap.put("P_TXR_NO", request.getTransferno());
		inParamMap.put("P_PHARMACY_ID", request.getPharmacyid());
		inParamMap.put("P_DELV_STATUS", request.getDeliverystatus());
		inParamMap.put("P_TRXR_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate()));
		inParamMap.put("P_STATUS", request.getStatus());
		inParamMap.put("P_USER_ID", request.getUserid());
		//inParamMap.put("P_OPRN", "STS_UPDT");
		if(request.getDeliverystatus().equals("2")){
			inParamMap.put("P_OPRN", "UPD_STOCK");
		}else{
			inParamMap.put("P_OPRN", "STS_UPDT");
		}
		if(request.getStatus().equals("2") ){
			inParamMap.put("P_OPRN", "READYTODELIVER");
		}
		inParamMap.put("P_ORDER_ID", request.getOrderId()); 
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
				inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO productbatchdetails(String productId) {
		// TODO Auto-generated method stub
		/*String stockQuery = "SELECT `batch_id` ,b.`product_id`,CONCAT('Rs. ',FORMAT(`prec_pur_price`,2))prec_pur_price,"
				+ "DATE_FORMAT(`purchase_date`,'%e/%c/%Y')prhdr_created_date,DATE_FORMAT(`prec_expiry_date`,'%e/%c/%Y')prec_expiry_date,"
				+ "`stock_id`,(`prec_recving_quantity` + `prec_free_count`) product_count,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)supplier_name,`sell_price` "
				+ "FROM `hero_stock_product` A JOIN `hero_stock` B ON A.`product_id` = B.`product_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` rd ON rd.`prec_product_id` = b.`product_id` "
				+ "AND a.`product_id` = rd.`prec_product_id`  AND rd.`prec_batchno` = b.`batch_id` "
				+ "JOIN `hero_stock_purchase_receive_hdr` rh ON rd.`prec_hdr_id` = rh.`prhdr_id`  AND `prhdr_paid_status` = 5 "
				+ "JOIN `hero_stock_purchase` p ON p.`purchase_code` = rh.`pur_req_id` "
				+ "JOIN `hero_stock_supplier` E ON E.`supplier_id` = p.`supplier_id` "
				+ "JOIN `hero_admin_unit_type` C  ON A.`unit_type_id` = C.`unit_type_id` "
				+ "JOIN `hero_admin_category` D  ON A.`category_id` = D.`category_id` "
				+ "WHERE A.`product_id` = B.`product_id` AND C.`unit_type_id` = A.`unit_type_id` AND `product_count` != 0 AND A.product_id = "+productId;*/
		
		String stockQuery = "SELECT `prhdr_bill_no` batch_id  ,`product_name`,B.`product_id`,CONCAT('Rs. ',FORMAT(rd.`prec_pur_price`,2))prec_pur_price,"
		+ "B.`expiry_date`,DATE_FORMAT(p.`purchase_date`,'%e/%c/%Y')prhdr_created_date,DATE_FORMAT(rd.`prec_expiry_date`,'%e/%c/%Y')prec_expiry_date,"
		+ "B.`stock_id`, B.`product_count`,FORMAT(((rd.`prec_pur_price`)+ (`prod_tax_total`/`prec_recving_quantity`)),2)purchaseprice,"
		+ "(E.`supplier_first_name`)supplier_name,B.`sell_price`  FROM `hero_stock_product` A "
		+ " JOIN `hero_stock` B ON A.`product_id` = B.`product_id`JOIN `hero_stock_purchase_received_dtl` rd ON rd.`prec_product_id` = B.`product_id`"
		+ " AND A.`product_id` = rd.`prec_product_id` AND rd.`prec_batchno` = B.`batch_id` "
		+ " JOIN `hero_stock_purchase_receive_hdr` rh ON rd.`prec_hdr_id` = rh.`prhdr_id` "
		+ " JOIN `hero_stock_purchase` p ON p.`purchase_code` = rh.`pur_req_id` "
		+ " JOIN `hero_admin_category` D  ON A.`category_id` = D.`category_id`  JOIN `hero_stock_supplier` E ON E.`supplier_id` = p.`supplier_id`"
		+ " JOIN `hero_admin_unit_type` C  ON A.`unit_type_id` = C.`unit_type_id`  WHERE A.product_id ="+productId+" "
		+ "AND (`product_count`!= 0) AND (`prhdr_paid_status` = 5 OR `prhdr_paid_status`= 7) GROUP BY B.`batch_id`";
		
		log.info("productbatchdetails Query       "+stockQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(stockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("batchno", rs.getString("batch_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("purchasedate", rs.getString("prhdr_created_date"));
				map.put("unitprice", rs.getString("prec_pur_price"));
				map.put("expirydate", "<input type='text' style='width:125px;' class='form-control form-white datepicker' value='"+rs.getString("prec_expiry_date")+"' id='expirydate"+index+"' />");
				map.put("suppliername", rs.getString("supplier_name"));
				map.put("salesprice", rs.getString("sell_price"));
				map.put("salespriceaction", "<input type='number' style='width: 125px;' value='"+rs.getString("sell_price")+"' id='sellprice"+index+"' " +
						"onchange='updatesellprice("+index+",this.value)'>");		
				map.put("productcount", rs.getString("product_count"));
				map.put("action", "<button class='save myBtnTab' onclick='savestockprice("+index+","+rs.getString("stock_id")+","+rs.getString("sell_price")+");'> "
						+ "<i class='fa fa-save'></i> Save </button>");
				map.put("printaction", "<button type='button' class='btn btn-white'><i class='fa fa-barcode'></i> Print </button>");
				index++;
				return map;
			}
		});
		log.info(productList);
		
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(productList);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loadadjustmentbatches(String productId) {
		// TODO Auto-generated method stub
		String batchQuery = "SELECT `batch_id`,`batch_id` FROM `hero_stock`  WHERE `product_id` = "+productId;
		List<Object> batchList = inventoryLOVobj.getLOVList(batchQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(batchList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO saveAdjustments(String adjustmentData) {
		// TODO Auto-generated method stub
try
{
		log.info("adjustmentData     "+adjustmentData);
		
HERO_ATK_STOCKADJUSTMENTREQUEST request = (HERO_ATK_STOCKADJUSTMENTREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(adjustmentData, "com.hero.stock.forms.transactions.stock.HERO_ATK_STOCKADJUSTMENTREQUEST");
		
		log.info("Values are     "+request.getAdjustmentNo()+"   "+request.getAdjustmentdate()+"   "+request.getStoreid()+"   "+request.getStoreid()+"   "
				+request.getNotes()+"   "+request.getAdjustmentProductArray());

		List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = new ArrayList<HERO_STK_STOCKTRANSFERITEMDETAILS>();
		
		if(request.getOprn() != null && !request.getOprn().equals("DEL"))
		{
			itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getAdjustmentProductArray(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");	
		}

		log.info("itemList      "+itemList);
		 

			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_ADJ_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_ADJUSTMENT_NO", request.getAdjustmentNo());
			inParamMap.put("P_ADJUSTMENT_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getAdjustmentdate()));
			inParamMap.put("P_STORE_ID", request.getStoreid());
			inParamMap.put("P_REASON", request.getReason());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_OPRN", request.getOprn());
			
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			String transferId = null;
			
			
			 
			transferId = (String)resultMap.get("out_genrate_id");	
			log.info("transferId       "+transferId);
			 
		/*	Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> itr = itemList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS obj = itr.next();
				obj.setSaid(transferId);*/
				 
				
				Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> itr = itemList.iterator();
				while(itr.hasNext())
				{
					HERO_STK_STOCKTRANSFERITEMDETAILS obj = itr.next();
					obj.setSaid(transferId);
					obj.setAdjstoreid(request.getStoreid());
					obj.setAdjreason(request.getReason());
					
				log.info(request.getStoreid());
				log.info(request.getReason());
				
				itemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info("Item Values       "+obj.getSaid()+"   "+obj.getProductid()+"   "+obj.getBatchid()+"   "+obj.getProductcount()+"   "
						+obj.getUnitprice()+"   "+obj.getOprn());
			}	
			
			saveadjustmentproductitems(itemList);
			
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		 
		
}
catch(Exception e)
{
	error_log.info(e);
	HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void saveadjustmentproductitems( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list) 
	{
		
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_ADJ_PROD( ?,?,?,?,?,?,?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
				
				ps.setString(1, item.getSaid());
				ps.setString(2, item.getProductid());
				ps.setString(3, item.getBatchid());
				ps.setString(4, item.getProductcount());
				ps.setString(5, item.getUnitprice());
				ps.setString(6, item.getOprn());
				ps.setString(7, item.getAdjstoreid());
				ps.setString(8, item.getAdjreason());
			
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO loadPurchasedProductItems(String purchaseid) {
		
		String batchQuery = "SELECT DISTINCT(a.`batch_id`) ,`prhdr_bill_no` FROM `hero_stock` a JOIN `hero_stock_purchase_received_dtl` b"
				+ " ON a.`product_id`= b.`prec_product_id` AND  a.`batch_id` = b.`prec_batchno`  JOIN `hero_stock_purchase_receive_hdr` f"
				+ " ON f.`prhdr_id`=b.`prec_hdr_id` JOIN `hero_stock_purchase`g ON   g.`purchase_code`= f.`pur_req_id`  "
				+ "  WHERE `purchase_id`= "+purchaseid+" AND a.`product_count` > 0";
		log.info("batchQuery  "+batchQuery);
		
		List<Object> batchList = inventoryLOVobj.getLOVList(batchQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(batchList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getStockTransferProducts(String purchaseid, String batchid) {
		
		String productsQuery = "SELECT  a.`product_id`, a.`batch_id`  FROM `hero_stock` a JOIN `hero_stock_purchase_received_dtl` b "
				+ " ON a.`product_id`= b.`prec_product_id` AND  a.`batch_id` = b.`prec_batchno`  JOIN `hero_stock_purchase_receive_hdr` f "
				+ " ON f.`prhdr_id`=b.`prec_hdr_id` JOIN `hero_stock_purchase` g ON   g.`purchase_code`= f.`pur_req_id`  "
				+ "   WHERE g.`purchase_id`= "+purchaseid+" AND a.`batch_id` = "+batchid+" AND a.`product_count` > 0";
		List<Object> productsList = inventoryLOVobj.getLOVList(productsQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productsList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO getdishTypeList(String dishid) {
		
		String productsQuery = "SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type`a "
				+ " JOIN `hero_stock_dishes`b ON a.`dish_id`=b.`dish_id` WHERE b.`dish_id`="+dishid;
		List<Object> productsList = inventoryLOVobj.getLOVList(productsQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productsList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO getIngredientsList(final String dishid,final String dishtypeid, int dishcount) {
	
/*		StringBuilder query = new StringBuilder("SELECT DISTINCT (`total_qty`)totallooseqty,`hsuts_desc`,`dish_name`,a.`dish_id`,`dishtype_name`,`dishtype_id`,`dish_count`,"
				+ " `dish_product_id`,COALESCE(f.`unit`,'')fulluom,COALESCE(g.`unit`,'')looseuom,e.`unit_type_id`dish_loose_uom,i.`hsuts_id`,`dish_full_uom`,"
				+ " COALESCE(`dish_full_qty`,0)dish_full_qty,COALESCE(`dish_loose_qty`,0)dish_loose_qty,`product_name`,"
				+ " COALESCE(SUM(`product_count`),0)product_count,`batch_id`,COALESCE(`sell_price`,0)sell_price, j.`cgst`,j.`sgst`,`expiry_date` FROM `hero_stock_dishes`a "
				+ " JOIN `hero_stock_dish_type`b ON a.`dish_id`=b.`dish_id` JOIN `hero_stock_dishes_setup_hdr`c ON c.`dish_type_id`=b.`dishtype_id`"
				+ "  AND c.`dish_name_id`=a.`dish_id` JOIN `hero_stock_dishes_setup_dtl`d ON d.`dish_hdr_id`=c.`dish_setup_id` "
				+ " JOIN `hero_stock_product`e ON `product_id`= `dish_product_id` LEFT JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=e.`unit_type_id` "
				+ " LEFT JOIN `hero_admin_unit_type`g ON g.`unit_type_id`=e.`unit_type_id` "
				+ " LEFT JOIN `hero_stock_unit_type_config`h ON h.`hsuts_id`=d.`hsuts_id`"
				+ "  LEFT JOIN `hero_stock_unit_type_setting`i ON i.`hsuts_id`=h.`hsuts_id`"
				+ " LEFT JOIN`hero_stock`j ON j.`product_id`= `dish_product_id` "
				+ "  WHERE a.`dish_id`="+dishid+" AND b.`dishtype_id`="+dishtypeid+"  GROUP BY `dish_product_id`");	*/	
		
		 
		StringBuilder query = new StringBuilder("SELECT DISTINCT (`total_qty`)totallooseqty,`hsuts_desc`,`dish_name`,a.`dish_id`,`dishtype_name`,`dishtype_id`,`dish_count`,"
				+ " `dish_product_id`,COALESCE(f.`unit`,'')fulluom,COALESCE(g.`unit`,'')looseuom,e.`unit_type_id`dish_loose_uom,i.`hsuts_id`,`dish_full_uom`,"
				+ " COALESCE(`dish_full_qty`,0)dish_full_qty,COALESCE(`dish_loose_qty`,0)dish_loose_qty,`product_name`,"
				+ " COALESCE(SUM(`product_count`),0)product_count,`batch_id`,COALESCE(`sell_price`,0)sell_price, j.`cgst`,j.`sgst`,`expiry_date` FROM `hero_stock_dishes`a "
				+ " JOIN `hero_stock_dish_type`b ON a.`dish_id`=b.`dish_id` JOIN `hero_stock_dishes_setup_hdr`c ON c.`dish_type_id`=b.`dishtype_id`"
				+ "  AND c.`dish_name_id`=a.`dish_id` JOIN `hero_stock_dishes_setup_dtl`d ON d.`dish_hdr_id`=c.`dish_setup_id` "
				+ " JOIN `hero_stock_product`e ON `product_id`= `dish_product_id` LEFT JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=e.`unit_type_id` "
				+ " LEFT JOIN `hero_admin_unit_type`g ON g.`unit_type_id`=e.`unit_type_id` "
				+ " LEFT JOIN `hero_stock_unit_type_config`h ON h.`hsuts_id`=d.`hsuts_id`"
				+ "  LEFT JOIN `hero_stock_unit_type_setting`i ON i.`hsuts_id`=h.`hsuts_id`"
				+ " LEFT JOIN`hero_stock`j ON j.`product_id`= `dish_product_id` "
				+ "  WHERE  b.`dishtype_id`="+dishtypeid+"  GROUP BY `dish_product_id`");	
		
		log.info("query       "+query.toString());
		
		final float totalcount= dishcount;
		
		@SuppressWarnings("unchecked")
		List<Object> productStockList = jdbcTemplate.query(query.toString(), new RowMapper() {
			int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("dishname", rs.getString("dish_name"));
				map.put("dishtypename", rs.getString("dishtype_name"));
				map.put("productname", rs.getString("product_name"));
				map.put("productid", rs.getString("dish_product_id"));
				map.put("dish_count", rs.getString("dish_count"));
				map.put("totallooseqty", rs.getString("totallooseqty"));
				
				map.put("productcount", rs.getString("product_count"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("batchid", rs.getString("batch_id"));
				map.put("sellprice", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("expiry_date"));
				
				map.put("dishid", rs.getString("dish_id"));
				map.put("dishtypeid", dishtypeid);
				
				map.put("index", index);
				map.put("looseuomid", rs.getString("dish_loose_uom"));	
				map.put("totaldishcount", totalcount);
				
				if( rs.getInt("dish_count") == totalcount){
					log.info(totalcount);
					log.info(rs.getInt("dish_count"));
					log.info(rs.getInt("totallooseqty"));
					log.info(rs.getInt("dish_loose_qty"));
					log.info(rs.getInt("dish_full_qty"));
					
					if(rs.getInt("totallooseqty") == rs.getInt("dish_loose_qty")){
						log.info("IFFFFFF");
						//float totalqty = rs.getInt("dish_loose_qty")+ rs.getInt("dish_full_qty") * rs.getInt("totallooseqty");
						int totalqty = rs.getInt("totallooseqty");
						map.put("looseqty", 0);
						map.put("fullqty", 1);
					//	map.put("totalqty", rs.getInt("dish_loose_qty"));
						map.put("totalqty", totalqty);
						
						map.put("looseqtydisp", 0+' '+rs.getString("looseuom"));
						map.put("totalqtydisp", rs.getInt("dish_loose_qty")+' '+rs.getString("looseuom"));
					}else{
						log.info("ELSEEEEEEE");
						//float totalqty = rs.getInt("dish_loose_qty")+ rs.getInt("dish_full_qty") * rs.getInt("totallooseqty");
						int totalqty = rs.getInt("totallooseqty");
						map.put("fullqty", rs.getString("dish_full_qty"));
						map.put("looseqty", rs.getString("dish_loose_qty"));
					//	map.put("totalqty", rs.getString("dish_loose_qty"));
						map.put("totalqty", totalqty);
						
						map.put("looseqtydisp", rs.getString("dish_loose_qty")+' '+rs.getString("looseuom"));
						map.put("totalqtydisp", rs.getString("dish_loose_qty")+' '+rs.getString("looseuom"));
					}
					map.put("looseuom", rs.getString("looseuom"));							
					
					if(rs.getString("fulluom").equals(" ") || rs.getString("fulluom").equals(null)){
						map.put("fulluom", rs.getString("hsuts_desc"));
						map.put("fulluomid", rs.getString("hsuts_id"));	
						map.put("fullqtydisp", 1+' '+rs.getString("hsuts_desc"));
					}else{
					//	map.put("fulluom", rs.getString("fulluom"));
						map.put("fulluom", rs.getString("fulluom"));
						map.put("fullqtydisp", 1+' '+rs.getString("dish_full_uom"));
					}
				}else if(rs.getInt("dish_count") > totalcount){
					
					double mf = rs.getFloat("dish_count")/totalcount;
					log.info(mf);
					
					double looseqty =  rs.getInt("dish_loose_qty")/mf;
					float fullqty = (float) (rs.getInt("dish_full_qty") / mf);
					log.info(looseqty);
					
					//float totalqty = (float) (fullqty * rs.getInt("totallooseqty") + looseqty);
					float totalqty = (int) (rs.getInt("totallooseqty") /mf);
					log.info(totalqty);
					float looseRem = 0;
					if(totalqty > 0){
						 looseRem = 0;
					}else{
						 looseRem = (float) (rs.getInt("totallooseqty") / mf);
					}
					
					
					log.info(looseRem);
					totalqty = (float) (totalqty + looseRem) ;
					log.info(totalqty);
					
					map.put("looseuom", rs.getString("looseuom"));
				//	map.put("fullqty", rs.getString("dish_full_qty"));
					map.put("fullqty", fullqty);
					map.put("looseqty", looseqty);
					//map.put("totalqty", looseqty);
					map.put("totalqty", totalqty);
					
					map.put("looseqtydisp", looseqty+' '+rs.getString("looseuom"));
					map.put("totalqtydisp", looseqty+' '+rs.getString("looseuom"));
					
					if(rs.getString("fulluom").equals("")){
						map.put("fulluom", rs.getString("hsuts_desc"));
						map.put("fulluomid", rs.getString("hsuts_id"));
						map.put("fullqtydisp", rs.getString("dish_full_qty")+' '+rs.getString("hsuts_desc"));
					}else{
						map.put("fulluom", rs.getString("fulluom"));
						map.put("fulluomid", rs.getString("dish_full_uom"));
						map.put("fullqtydisp", rs.getString("dish_full_qty")+' '+rs.getString("dish_full_uom"));
					}
					
				}else{
					float mf = totalcount/rs.getFloat("dish_count");
					int fullqty = 0;
					log.info("mf   "+mf);					
					
					double looseqty = mf * rs.getInt("dish_loose_qty");
					
					if(rs.getInt("dish_full_qty") > 0){
						 fullqty = (int) (rs.getInt("dish_full_qty")* mf + looseqty/rs.getInt("totallooseqty"));
					}else{
						 fullqty = (int) (looseqty / rs.getInt("totallooseqty"));
					}
					//int fullqty = (int) (looseqty / rs.getInt("totallooseqty"));
					
					
					//float totalqty = (float) (fullqty * rs.getInt("totallooseqty") + looseRem);
					int totalqty = (int) (rs.getInt("totallooseqty") *mf) ;
					double looseRem = 0;
					totalqty = (int) (totalqty + looseRem) ;
					
					log.info("looseqty   "+looseqty);
					log.info("fullqty   "+fullqty);
					
					map.put("looseuom", rs.getString("looseuom"));
					map.put("fullqty",fullqty);
					
					if(looseRem > 0){
						map.put("looseqty", looseRem);	
						map.put("looseqtydisp", looseRem+' '+rs.getString("looseuom"));	
						log.info("looseqtydisp   "+looseRem+' '+rs.getString("looseuom"));
					}else{
						map.put("looseqty", 0);	
						map.put("looseqtydisp", 0+' '+rs.getString("looseuom"));
						
						log.info("looseqtydisp   "+0+' '+rs.getString("looseuom"));
					}
					
					if(rs.getString("fulluom").equals("")){
						map.put("fulluom", rs.getString("hsuts_desc"));
						map.put("fulluomid", rs.getString("hsuts_id"));
						map.put("fullqtydisp",fullqty+' '+rs.getString("hsuts_desc"));
						
						log.info("fullqty   "+fullqty+' '+rs.getString("hsuts_desc"));
					}else{
						map.put("fulluom", rs.getString("fulluom"));
						map.put("fulluomid", rs.getString("dish_full_uom"));
						map.put("fullqtydisp",fullqty+' '+rs.getString("fulluom"));
						
						log.info("fullqty   "+fullqty+' '+rs.getString("fulluom"));
					}
					//map.put("totalqty", looseqty);
					map.put("totalqty", totalqty);
					map.put("totalqtydisp", looseqty+' '+rs.getString("looseuom"));	
					
					
					log.info("fullqty   "+fullqty);
					
					log.info("totalqtydisp   "+looseqty+' '+rs.getString("looseuom"));
					
				}
				index++;
				return map;
			}
		});
		log.info(productStockList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productStockList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO savecustomermenu(String Data) {
		
		try {
			HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(Data, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
			
			
		
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADD_CUSTOMER_MENU");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_CUSTOMER_ID", request.getCustomerid());
			inParamMap.put("P_DAY", "monday");
			inParamMap.put("P_FOOD_TIME", "breakfast");
			inParamMap.put("P_DISH_ID","1");
			inParamMap.put("P_DISH_TYPE_ID", "1");
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_DISH_COUNT", "1");
			inParamMap.put("P_QTYPER_PERSON", "1");
			inParamMap.put("P_OPRN", "INS"); 
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			
			   
				List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");
				saveCustomermenuItems(itemList,request.getUserid());	
				inventoryResponseOBJ.setResponseType("S");
				inventoryResponseOBJ.setResponseObj("Menu created successfully");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		

		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

				return inventoryResponseInfoOBJ;
		
	}
	@Transactional
	public void saveCustomermenuItems( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list,final String userid) 
	{
		log.info("final list size "+list.size());

		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_ADD_CUSTOMER_MENU( ?,?,?,?,?, ?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
			
				
				ps.setString(1,  item.getCustomerid());
				ps.setString(2,  item.getDay());
				ps.setString(3,  item.getFoodtime());
				ps.setString(4,  item.getDishid());
				ps.setString(5,  item.getDishtypeid());				
				ps.setString(6,  userid);
				ps.setString(7,  item.getDishcount());
				ps.setString(8,  item.getQtyperperson());
				ps.setString(9,  "UPD");
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public HERO_STK_RESPONSEINFO saveorderrequest(String Data) {
		
		try {
			HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(Data, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
			
			
		
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ORDER_REQUEST_HDR");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_REQ_ID", request.getOrdId());
			inParamMap.put("P_REQ_REF_NO", request.getRefNo());
			inParamMap.put("P_REQ_ORD_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getDate()));
			inParamMap.put("P_STATUS", request.getStatus());
			inParamMap.put("P_STATUS_desc", request.getStatusDesc());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_OPRN", request.getOprn()); 
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			
			String orderId = (String)resultMap.get("out_genrate_id");
			if(orderId != null && !orderId.equals("0"))
			{    //System.out.println(itemList);
				if(!request.getOprn().equals("DEL")){
					List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");
				
				saveOrderReqItems(itemList,orderId,request.getOprn(),request.getUserid());	}}
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		

		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

				return inventoryResponseInfoOBJ;
		
	}
	@Transactional
	public void saveOrderReqItems( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list,final String orderId,final String oprn, final String userid) 
	{
		log.info("final list size "+list.size());

		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_ORDER_REQUEST_DTL( ?,?,?,?,?, ?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
			
				
				ps.setString(1,  orderId);
				ps.setString(2,  item.getCustomerId());
				/*ps.setString(3,  item.getTransferid());*/
				ps.setString(3,  item.getDishid());
				ps.setString(4,  item.getDishtypeid());
				ps.setString(5,  item.getDishCount());				
				ps.setString(6,  userid);
				ps.setString(7,  oprn);
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			e.printStackTrace();
			throw e;
		}
	}
	@Override
	public HERO_STK_RESPONSEINFO approveStoreRequest(String transferData) {
		try {
			HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(transferData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
			
			
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_APPROVE_STORE_REQUEST_HDR");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_TRANSFER_ID", request.getOrderId());
		
			inParamMap.put("P_OPRN", "UPD"); 
			
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			String transferId = (String)resultMap.get("out_genrate_id");	
			if(transferId != null && !transferId.equals("0"))
			{ 
				if(request.getItemlist().length() > 0){
					List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");
					log.info(itemList);
					saveapprovestorerequest(itemList,"dishcount",request.getOrderId());	
				}
				if(request.getDishtypearray().length() > 0){
					List<HERO_STK_STOCKTRANSFERITEMDETAILS> dishList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getDishtypearray(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");
					saveapprovestorerequest(dishList,"dishtype",request.getOrderId());	
				}
				
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);

			}
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_STK_RESPONSEINFO transferToKitchen(String transferData) {
		// TODO Auto-generated method stub
try
{
		log.info("transferData     "+transferData);
		
		HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(transferData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
		
		log.info("Values are     "+request.getTransferdate()+"  "+request.getCgst()+"   "+request.getSgst()+" "
				+ "  "+request.getTransferid()+"   "+request.getTransferno()+"  "+request.getExpirydate()+"  "+request.getPharmacyid());

		log.info("request.getItemlist()"+request.getItemlist());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCKTXR_MODULE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_TXR_ID", request.getTransferid());
		inParamMap.put("P_TXR_NO", request.getTransferno());
		inParamMap.put("P_PHARMACY_ID", request.getPharmacyid());
		inParamMap.put("P_DELV_STATUS", request.getDeliverystatus());
		inParamMap.put("P_TRXR_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate()));
		inParamMap.put("P_STATUS", request.getStatus());
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn()); 
		inParamMap.put("P_ORDER_ID", request.getOrderId()); 
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		String transferId = null;
		
		
		 
		transferId = (String)resultMap.get("out_genrate_id");
		
		if(transferId != null && !transferId.equals("0")){
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
		    int i;
		    log.info("array.length()  ");
		    JSONArray array = new JSONArray(request.getItemlist());
		    log.info("array.length()  "+array.length());
		    for (i = 0; i < array.length(); i++)
		    {
		        list.add(array.getJSONObject(i));
		        JSONArray  jsonOBJ = array.getJSONObject(i).getJSONArray("dish");
		        JSONArray jArray = jsonOBJ; 
		        log.info("jArray  "+jArray.toString());
		        if (jArray != null) { 
		           for (int i1=0;i1<jArray.length();i1++){ 
		        	   log.info("jArray  "+jArray.getJSONObject(i1).toString());
		        	   JSONObject myObj = jArray.getJSONObject(i1);
		        	   Gson gson = new Gson();
		        	   HERO_STK_STOCKTRANSFERITEMDETAILS myobje = gson.fromJson(myObj.toString(), HERO_STK_STOCKTRANSFERITEMDETAILS.class);
		        	 
		        	    for(int customerid=0;customerid < myobje.getCustomerarray().length;customerid++ ){
		        	    	
		        	    	log.info("customerid"+myobje.getCustomerarray()[customerid]);
		        	    	String cusid = myobje.getCustomerarray()[customerid];
		        	    	String dishcount =myobje.getDishcountarray()[customerid];
		        	    	
		        			SimpleJdbcCall jdbcCALL1 = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_KITCHEN_ITEMS");
		        			
		        			Map<String, Object> inParamMap1 = new HashMap<String, Object>();
		        			inParamMap1.put("P_TPR_ID", myobje.getTransferid());
		        			inParamMap1.put("P_PROD_ID", myobje.getProductid());
		        			inParamMap1.put("P_TXRID", transferId);
		        			inParamMap1.put("P_PROD_COUNT", myobje.getTotalqty());
		        			inParamMap1.put("P_CONVERSN_RATE",myobje.getConversionrate());
		        			inParamMap1.put("P_FULL_UOM", myobje.getFulluomid());
		        			inParamMap1.put("P_FULL_UOM_QTY", myobje.getFulluomqty());
		        			inParamMap1.put("P_LOOSE_UOM", myobje.getLooseuomid()); 
		        			inParamMap1.put("P_LOOSE_UOM_QTY", myobje.getLooseuomqty()); 		        			
		        			inParamMap1.put("P_UOM_PACKING_ID", myobje.getUompacking());
		        			inParamMap1.put("P_STATUS_ID", myobje.getStatusid());
		        			inParamMap1.put("P_BATCH_ID", myobje.getBatchid());
		        			inParamMap1.put("P_UNIT_PRICE", myobje.getUnitprice());
		        			inParamMap1.put("P_UNIT_PRICE_IN_CURR",myobje.getUnitprice());
		        			inParamMap1.put("P_USERID", request.getUserid());
		        			inParamMap1.put("P_OPRN", request.getOprn());
		        			inParamMap1.put("P_CGST", myobje.getCgst()); 
		        			inParamMap1.put("P_SGST", myobje.getSgst()); 		        			
		        			inParamMap1.put("P_EXPIRY_DATE", myobje.getExpirydate());
		        			inParamMap1.put("P_DISH_ID", myobje.getDishid());
		        			inParamMap1.put("P_DISHTYPE_ID", myobje.getDishtypeid());
		        			inParamMap1.put("P_ADD_PRODUCT_STATUS", myobje.getAddproduct());
		        			inParamMap1.put("P_CUSTOMER_ID", cusid);
		        			inParamMap1.put("P_CHANGE_REASON", myobje.getChangereason());
		        			inParamMap1.put("P_DISH_COUNT", dishcount);
		        		
		        			
		        			log.info("inParamMap         "+inParamMap1);
		        			SqlParameterSource in1 = new MapSqlParameterSource(inParamMap1);
		        			Map<String, Object> resultMap1 = jdbcCALL1.execute(in1);
		        	    }
		        	   
		           }
		        }
		        //System.out.println("json  "+array.getJSONObject(i).toString());
		    }
		} catch (JSONException e) {
		    System.out.println(e.getMessage());
		}
		
}
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);	
	
		
/*		
 boolean stockExist = false;
 
 Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> stockItr = itemList.iterator();
 while(stockItr.hasNext())
 {
	 HERO_STK_STOCKTRANSFERITEMDETAILS item = stockItr.next();
	 String transferDate ="";
	 
	 String itemCountQuery = "SELECT * FROM `hero_stock_transfer` a,`hero_stock_transfer_product` b WHERE  a.`transfer_id` = b.`transfer_id` AND "
	 		+ "`pharmacy_id` = "+request.getPharmacyid()+" AND `delivery_status` = 1 AND `batch_id` = '"+item.getBatchid()+"' "
	 				+ "AND `transfer_date` = '"+HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate())+"' AND a.`transfer_id` != "+request.getTransferid();
	 log.info("itemCountQuery   "+itemCountQuery);
	 @SuppressWarnings("unchecked")
		List<Object> itemCountList = jdbcTemplate.query(itemCountQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				index++;
				return index;
			}
		});
		log.info(itemCountList);
		
		if(itemCountList != null && itemCountList.size() > 0)
		{
			stockExist = true;
			break;
		}
 }
 
		log.info("stockExist     "+stockExist);*/
/*		
if(!stockExist)
		{

			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCKTXR_MODULE");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_TXR_ID", request.getTransferid());
			inParamMap.put("P_TXR_NO", request.getTransferno());
			inParamMap.put("P_PHARMACY_ID", request.getPharmacyid());
			inParamMap.put("P_DELV_STATUS", request.getDeliverystatus());
			inParamMap.put("P_TRXR_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate()));
			inParamMap.put("P_STATUS", request.getStatus());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_OPRN", request.getOprn()); 
			inParamMap.put("P_ORDER_ID", request.getOrderId()); 
			
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			String transferId = null;
			
			
			 
			transferId = (String)resultMap.get("out_genrate_id");	
			log.info("transferId       "+transferId);
			log.info("itemList  size     "+itemList.size());
			Iterator<HERO_STK_STOCKTRANSFERITEMDETAILS> itr = itemList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS obj = itr.next();
				obj.setTransferid(transferId);
				obj.setOprn(request.getOprn());
				obj.setUserid(request.getUserid());			
				
				//itemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				log.info(obj.getCustomerid());
				
				log.info("Item Values        "+obj.getProductid()+"      "+obj.getTotalqty());
			}	
			log.info("final item list "+itemList);
			
			if(transferId != null && !transferId.equals("0"))
			{    //System.out.println(itemList);
			saveKitchenProducts(itemList,transferId,request.getOprn(),request.getUserid());	}
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		else
		{
			inventoryResponseOBJ.setResponseType("F");
			inventoryResponseOBJ.setResponseObj("Transaction Already Exists");
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
		}*/
		
}
catch(Exception e)
{
	System.out.println(e);
	error_log.info(e);
	HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}
	@Transactional
	public void saveapprovestorerequest( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list, final String type,final String transferid) 
	{
		log.info("final list size "+list.size());

		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_APPROVE_STORE_REQUEST( ?,?,?,?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
				log.info("final list size "+item.getCustomerid() + " "+item.getDishtypeid() +" "+item.getPreviousdishtypeid() + " "+item.getDishcount());
				ps.setString(1,  transferid);
				ps.setString(2,  item.getCustomerid());
				ps.setString(3,  item.getDishtypeid());
				ps.setString(4,  item.getPreviousdishtypeid());
				ps.setString(5,  item.getDishcount());	
				ps.setString(6,  " ");
				if(type.equals("dishcount")){
					log.info("APPROVEWITHCOUNT");
					ps.setString(7,  "APPROVEWITHCOUNT");	
				}else if(type.equals("dishtype")){
					log.info("dishtype");
					ps.setString(7,  "APPROVEWITHTRYPE");	
				}
				
				
			
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			e.printStackTrace();
			throw e;
		}
	}
	@Transactional
	public void saveKitchenProducts( final List<HERO_STK_STOCKTRANSFERITEMDETAILS> list,final String transferId,final String oprn, final String userid) 
	{
		log.info("final list size "+list.size());

		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_KITCHEN_ITEMS( ?,?,?,?,?, ?,?,?,?,?,?,?,?, ?,?,?,?,?,?,?,?,?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_STOCKTRANSFERITEMDETAILS item = list.get(i);
				log.info(" ");
				log.info("Item Values    "+item.getProductid()+"  "+item.getTotalqty()+" "+item.getDishid()+"  "+item.getDishtypeid());
				log.info(item.getCustomerid());
				ps.setString(1,  item.getTprid());
				ps.setString(2,  item.getProductid());
				ps.setString(3,  transferId);
				ps.setString(4,  item.getTotalqty());
				ps.setString(5,  item.getConversionrate());				
				ps.setString(6,  item.getFulluomid());
				ps.setString(7,  item.getFulluomqty());
				ps.setString(8,  item.getLooseuomid());
				ps.setString(9,  item.getLooseuomqty());
				ps.setString(10, item.getUompacking());				
				ps.setString(11, item.getStatusid());
				ps.setString(12, item.getBatchid());
				ps.setString(13, item.getUnitprice());
				ps.setString(14, item.getUnitprice());
				ps.setString(15, userid);	
				ps.setString(16, oprn);
				ps.setString(17, item.getCgst());
				ps.setString(18, item.getSgst());
				ps.setString(19, item.getExpirydate());
				ps.setString(20, item.getDishid());
				ps.setString(21, item.getDishtypeid());
				ps.setString(22, item.getAddproduct());
				ps.setString(23, item.getCustomerid());
				ps.setString(24, item.getChangereason());
				ps.setString(25, item.getDishCount());
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			e.printStackTrace();
			throw e;
		}
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO getStockProductDetails( final String addproductid) {
	
		String query = "SELECT `batch_id`,SUM(`product_count`)product_count,`sell_price`,a.`cgst`,a.`sgst`,a.`expiry_date`,"
				+ " b.`unit_type_id`looseuomid,`unit`looseuom,`product_name` FROM `hero_stock`a   "
				+ "  JOIN `hero_stock_product`b ON b.`product_id`=a.`product_id` "
				+ "  JOIN `hero_admin_unit_type`c ON c.`unit_type_id`=b.`unit_type_id` WHERE a.`product_id` ="+addproductid;		
		 
		log.info("query       "+query.toString());		
		
		@SuppressWarnings("unchecked")
		List<Object> productStockList = jdbcTemplate.query(query.toString(), new RowMapper() {
			int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();				
				
				map.put("productname", rs.getString("product_name"));
				map.put("productid", addproductid);				
				map.put("stockproductcount", rs.getString("product_count"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("batchid", rs.getString("batch_id"));
				map.put("sellprice", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("expiry_date"));			
				map.put("index", index);
				map.put("looseuomid", rs.getString("looseuomid"));	
				map.put("looseuom", rs.getString("looseuom"));	
			
				index++;
				return map;
			}
		});
		log.info(productStockList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productStockList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_STK_RESPONSEINFO getOrderRequestDetails(String orderId) {
		String orderHeaderQuery="SELECT ord_ref_no,DATE_FORMAT(`ord_order_date`,'%d/%m/%Y') date FROM `hero_order_request` WHERE `ord_req_id` = "+orderId;
		@SuppressWarnings("unchecked")
		List<Object> orderHeaderList = jdbcTemplate.query(orderHeaderQuery.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();				
				
				map.put("ord_ref_no", rs.getString("ord_ref_no"));
				map.put("date", rs.getString("date"));
				
				return map;
			}
		});
		String orderDtlQuery="SELECT `companyname`,`ord_customer_id`,`ord_dish_id`,`dish_name`,`ord_dishtype_id`,`ord_dish_count`, `dishtype_name`"
				+ " FROM `hero_order_request_dtl` a JOIN `hero_stock_client_company` b ON a.`ord_customer_id`=b.`companyid` "
				+ " JOIN `hero_stock_dishes` c ON c.`dish_id`=a.`ord_dish_id` JOIN `hero_stock_dish_type` d ON d.`dishtype_id`=a.`ord_dishtype_id` WHERE `ord_req_id`="+orderId;
		@SuppressWarnings("unchecked")
		List<Object> orderDtlList = jdbcTemplate.query(orderDtlQuery.toString(), new RowMapper() {
			int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();				
				
				map.put("companyname", rs.getString("companyname"));
				map.put("ord_customer_id", rs.getString("ord_customer_id"));
				map.put("ord_dish_id", rs.getString("ord_dish_id"));
				map.put("dish_name", rs.getString("dish_name"));
				map.put("ord_dishtype_id", rs.getString("ord_dishtype_id"));
				map.put("ord_dish_count", rs.getString("ord_dish_count"));
				map.put("dishtype_name", rs.getString("dishtype_name"));
				map.put("index", index);
				index++;
				return map;
			}
		});
	
		Map<String, Object> out = new LinkedHashMap<>();
		out.put("orderHeaderList", orderHeaderList);
		out.put("orderDtlList", orderDtlList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(out);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	
	@Override
	public HERO_STK_RESPONSEINFO getTransferProducts( final String transferid) {
	
		String query = "SELECT `transfer_no`,`product_name`,CONCAT(`product_count`,' ',`unit`)product_count,   "
				+ " `dishtype_name`,`dish_name`,`companyname` FROM  `hero_stock_transfer`a JOIN `hero_stock_transfer_product`b ON a.`transfer_id`=b.`transfer_id` "
				+ " JOIN `hero_stock_product`c ON c.`product_id`=b.`product_id`  "
				+ " JOIN `hero_admin_unit_type`d ON d.`unit_type_id`=c.`unit_type_id` "
				+ " JOIN `hero_stock_dishes`e ON e.`dish_id`=b.`dishid` "
				+ " JOIN `hero_stock_dish_type`f ON f.`dishtype_id`=b.`dishtypeid` "
				+ " JOIN `hero_stock_client_company`g ON g.`companyid`=b.`customer_id` WHERE a.`transfer_id`="+transferid;		
		 
		log.info("query       "+query.toString());		
		
		@SuppressWarnings("unchecked")
		List<Object> transferList = jdbcTemplate.query(query.toString(), new RowMapper() {
			int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();				
				
				map.put("productname", rs.getString("product_name"));
				map.put("transferid", transferid);				
				map.put("productcount", rs.getString("product_count"));
				map.put("companyname", rs.getString("companyname"));
				map.put("dishname", rs.getString("dish_name"));
				map.put("dishtypename", rs.getString("dishtype_name"));
   
   
			    index++;
				return map;
			}
		});
		log.info(transferList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(transferList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO loadstorerequest() {
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		
		Map<String, Object> countList = new HashMap<String, Object>();
		
		Map<String, Object> finalcountList = new HashMap<String, Object>();
		
		String customerQuery = "SELECT DISTINCT(`companyname`),`cust_id` FROM `hero_daily_menu_setup` a "
				+ " JOIN `hero_stock_client_company` b ON b.`companyid`=a.`cust_id`  WHERE `day`= DAYNAME(NOW())";
		//log.info(customerQuery);
		
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
		
		
		
		String dishQuery = "SELECT DISTINCT(`dish_name`),b.`dish_id`,`dishtypeid`,`dishtype_name` FROM `hero_daily_menu_setup` a "
				+ " JOIN `hero_stock_dishes` b ON a.`dishid` =b.`dish_id` JOIN `hero_stock_dish_type` c ON a.`dishtypeid` =c.`dishtype_id`"
				+ "  WHERE `day`= DAYNAME(NOW()) ORDER BY dishtypeid ";
		//log.info(dishQuery);
		
		List<Object> dishList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(dishQuery, jdbcTemplate);
		List<Object> list = new ArrayList<Object>();
		
		Iterator<Object> dishListITR = dishList.iterator();
		while(dishListITR.hasNext())
		{
			
			Map<String, Object> Map = (java.util.Map<String, Object>) dishListITR.next();
			
			String dishid= (String) Map.get("dish_id");
			String dishtypeid= (String) Map.get("dishtypeid");
			
			
			String Query = "SELECT dishtype_id,`dishtype_name` FROM `hero_stock_dishes` a "
					+ " JOIN `hero_stock_dish_type` c ON a.`dish_id` =c.`dish_id`"
					+ " JOIN `hero_daily_menu_setup` d ON d.`dishtypeid` =c.`dishtype_id`"
					+ " WHERE a.`dish_id`="+dishid+"   ORDER BY FIELD(d.`dishtypeid`,"+dishtypeid+" )";
			//log.info(Query);
			
			@SuppressWarnings("unchecked")
			List<Object> cList = jdbcTemplate.query(Query, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("dishtypeid", rs.getString("dishtype_id"));
					map.put("dishtypename", rs.getString("dishtype_name"));
					
					return map;
				}
			});
			
		 
		 	Map.put("dishtypeList", cList);
		 	list.add(Map);
		 	
		 	
			String dishCountQuery = "SELECT dishtypeid,`cust_id` FROM `hero_daily_menu_setup` a "
					+ "  WHERE `dishid`="+Map.get("dish_id")+"   GROUP BY `cust_id`";
			//log.info(dishCountQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> countaddList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("dishtypeid", rs.getString("dishtypeid"));
					map.put("customerid", rs.getString("cust_id"));
					
					return map;
				}
			});
					
			
			/*HERO_STK_RESPONSEINFO typelist = getdishtypeList(dishid,dishtypeid);
			log.info("typelist   "+typelist);
			HERO_STK_RESPONSE response = typelist.getInventoryResponse();
			log.info(response.getResponseObj());*/
			//finalMap.put("dishtypeList", response.getResponseObj());
			countList.put((String) Map.get("dishtypeid"),countaddList);
			
		}
		//log.info("countList   "+countList);
		
		 for (Map.Entry<String, Object> set : countList.entrySet()) {
 
            // Printing all elements of a Map
            
        	List<Object> obj = (List<Object>) set.getValue();
        	
        	Iterator<Object> objListITR = obj.iterator();
        	
        	List<Object> newarray = new ArrayList<Object>();
        	
    		while(objListITR.hasNext())
    		{
    			Map<String, Object> Map = (java.util.Map<String, Object>) objListITR.next();
    			  
    			   Map<String, Object> newmap = new HashMap<String, Object>();
    			   String dishCountQuery = "SELECT `total_dishcount` FROM `hero_daily_menu_setup`"
    			   		+ " WHERE `cust_id` = "+Map.get("customerid")+" AND   `dishtypeid`= "+Map.get("dishtypeid")+" "
    			   				+ " AND `day`= DAYNAME(NOW()) GROUP BY `cust_id`";
				
    				log.info(dishCountQuery);
    				@SuppressWarnings("unchecked")
    				List<Object> addList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
    					@Override
    					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
    						Map<String, Object> map = new HashMap<String, Object>();
    						map.put("dish_count", rs.getString("total_dishcount"));
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
    				
    				//log.info("intcount   "+intcount);
    				
    				newmap.put("customerid", Map.get("customerid"));
    				newmap.put("dishcount", intcount);
    				newarray.add(newmap);
    		}
    		finalcountList.put(set.getKey(), newarray);
        }
		 log.info("finalcountList   "+finalcountList);
		 
		finalMap.put("customerList", customerList);
		finalMap.put("dishList", dishList);
		finalMap.put("dishcountList", finalcountList);
		finalMap.put("list", list);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(finalMap);
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	
/*	@Override
	public HERO_STK_RESPONSEINFO loadstorerequest() {
		
		Map<String, Object> finalMap = new HashMap<String, Object>();
		
		Map<String, Object> countList = new HashMap<String, Object>();
		
		Map<String, Object> finalcountList = new HashMap<String, Object>();
		
		
		String Query = "SELECT DISTINCT(`companyname`),`cust_id` FROM `hero_daily_menu_setup` a "
				+ " JOIN `hero_stock_client_company` b ON b.`companyid`=a.`cust_id` ";
		log.info(Query);
		
		@SuppressWarnings("unchecked")
		List<Object> List = jdbcTemplate.query(Query, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cust_id", rs.getString("cust_id"));			
				map.put("companyname", rs.getString("companyname"));
			
				return map;
			}
		});
		
		String customerQuery = "SELECT `cust_id`,`dishid`,`dishtypeid`,`dishcount`,`dish_name`,`dishtype_name`,`companyname`,`total_dishcount` "
				+ " FROM `hero_daily_menu_setup`a JOIN `hero_stock_client_company`b ON b.`companyid`=a.`cust_id`  "
				+ " JOIN `hero_stock_dishes`c ON c.`dish_id`=a.`dishid` JOIN `hero_stock_dish_type`d ON d.`dishtype_id`=a.`dishtypeid` "
				+ " WHERE `day` =  DAYNAME(NOW()) ;";
		
		log.info(customerQuery);	
		
		@SuppressWarnings("unchecked")
		List<Object> addList = jdbcTemplate.query(customerQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("cust_id", rs.getString("cust_id"));
				map.put("dishid", rs.getString("dishid"));
				map.put("dishtypeid", rs.getString("dishtypeid"));
				map.put("dishcount", rs.getString("dishcount"));
				map.put("dish_name", rs.getString("dish_name"));
				map.put("dishtype_name", rs.getString("dishtype_name"));
				map.put("companyname", rs.getString("companyname"));
				map.put("total_dishcount", rs.getString("total_dishcount"));
				return map;
			}
		});
		finalMap.put("List", addList);
		finalMap.put("customerList", List);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(finalMap);
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}*/
	
	
	@Override
	public HERO_STK_RESPONSEINFO getdishtypeList(String dishid,String dishtypeid) {
		// TODO Auto-generated method stub
		try
		{

		Map<String, Object> Map = new HashMap<String, Object>();
		
		String Query = "SELECT `dishtypeid`,`dishtype_name` FROM `hero_daily_menu_setup`a "
				+ " JOIN `hero_stock_dish_type`d ON d.`dishtype_id`=a.`dishtypeid` "
				+ " WHERE a.`dishid`="+dishid+" ORDER BY FIELD(`dishtypeid`,"+dishtypeid+") ";
		
		log.info("Query   "+Query);
		List<Map<String, Object>> List = jdbcTemplate.queryForList(Query);
		List<Map<String, Object>> listing = new ArrayList<Map<String,Object>>();
		
		log.info("List   "+List);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"uompackingsel\" onchange=\"calculatelooseqty()\" style=\"display:none;\">");
		
		Iterator<Map<String, Object>> fulluomPackingListITR = List.iterator();
		while(fulluomPackingListITR.hasNext())
		{
			Map<String, Object> fulluomPackingMap = fulluomPackingListITR.next();
			int sno = (int) fulluomPackingMap.get("dishtype_name");
			long uomid = (long)fulluomPackingMap.get("dishtypeid");
			sb.append("<option value='"+String.valueOf(sno).concat("-").concat(String.valueOf(uomid))+"'>"+fulluomPackingMap.get("dishtype_name")+"</option>");
			
			fulluomPackingMap.put("dishtype", String.valueOf(sno).concat("-").concat(String.valueOf(uomid)));
			listing.add(fulluomPackingMap);
		}
		sb.append("</select>");
		
		log.info("sb.toString()   "+sb.toString());
		log.info("listing   "+listing);
		
		Map.put("dishtypeselect", sb.toString());
		Map.put("dishtypeList", listing);
		
		inventoryResponseOBJ.setResponseObj("S");
		inventoryResponseOBJ.setResponseObj(Map);
		
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
	public HERO_STK_RESPONSEINFO saveaddtransferproduct(String transferData) {
		// TODO Auto-generated method stub
try
{
		log.info("transferData     "+transferData);
		
		HERO_STK_STOCKREQUEST request = (HERO_STK_STOCKREQUEST)HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(transferData, "com.hero.stock.forms.transactions.stock.HERO_STK_STOCKREQUEST");
		
		log.info("Values are     "+request.getTransferdate()+"  "+request.getCgst()+"   "+request.getSgst()+" "
				+ "  "+request.getTransferid()+"   "+request.getTransferno()+"  "+request.getExpirydate()+"  "+request.getPharmacyid());

		log.info("request.getItemlist()"+request.getItemlist());
		
		List<HERO_STK_STOCKTRANSFERITEMDETAILS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.stock.HERO_STK_STOCKTRANSFERITEMDETAILS");
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_STOCKTXR_MODULE");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_TXR_ID", request.getTransferid());
		inParamMap.put("P_TXR_NO", request.getTransferno());
		inParamMap.put("P_PHARMACY_ID", request.getPharmacyid());
		inParamMap.put("P_DELV_STATUS", request.getDeliverystatus());
		inParamMap.put("P_TRXR_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getTransferdate()));
		inParamMap.put("P_STATUS", request.getStatus());
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn()); 
		inParamMap.put("P_ORDER_ID", request.getOrderId()); 
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		String transferId = null;	
		 
		transferId = request.getTransferid();	
		log.info("transferId  "+transferId);
		if(transferId != null && !transferId.equals("0")){
		List<JSONObject> list = new ArrayList<JSONObject>();
		try {
		    int i;
		    log.info("array.length()  ");
		    JSONArray array = new JSONArray(request.getItemlist());
		    log.info("array.length()  "+array.length());
		    for (i = 0; i < array.length(); i++)
		    {
		        list.add(array.getJSONObject(i));
		        JSONArray  jsonOBJ = array.getJSONObject(i).getJSONArray("dish");
		        log.info("jsonOBJ  "+jsonOBJ);
		        JSONArray jArray = jsonOBJ; 
		        log.info("jArray  "+jArray.toString());
		        
		        if (jArray != null) { 
		           for (int i1=0;i1<jArray.length();i1++){ 
		        	   log.info("jArray  "+jArray.getJSONObject(i1).toString());
		        	   JSONObject myObj = jArray.getJSONObject(i1);
		        	   Gson gson = new Gson();
		        	   HERO_STK_STOCKTRANSFERITEMDETAILS myobje = gson.fromJson(myObj.toString(), HERO_STK_STOCKTRANSFERITEMDETAILS.class);
		        	 
		        	    for(int customerid=0;customerid < myobje.getCustomerarray().length;customerid++ ){
		        	    	
		        	    	log.info("customerid"+myobje.getCustomerarray()[customerid]);
		        	    	String cusid = myobje.getCustomerarray()[customerid];
		        	    	String dishcount =myobje.getDishcountarray()[customerid];
		        	    	
		        			SimpleJdbcCall jdbcCALL1 = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_KITCHEN_ITEMS");
		        			
		        			Map<String, Object> inParamMap1 = new HashMap<String, Object>();
		        			inParamMap1.put("P_TPR_ID", myobje.getTransferid());
		        			inParamMap1.put("P_PROD_ID", myobje.getProductid());
		        			inParamMap1.put("P_TXRID", transferId);
		        			inParamMap1.put("P_PROD_COUNT", myobje.getTotalqty());
		        			inParamMap1.put("P_CONVERSN_RATE",myobje.getConversionrate());
		        			inParamMap1.put("P_FULL_UOM", myobje.getFulluomid());
		        			inParamMap1.put("P_FULL_UOM_QTY", myobje.getFulluomqty());
		        			inParamMap1.put("P_LOOSE_UOM", myobje.getLooseuomid()); 
		        			inParamMap1.put("P_LOOSE_UOM_QTY", myobje.getLooseuomqty()); 		        			
		        			inParamMap1.put("P_UOM_PACKING_ID", myobje.getUompacking());
		        			inParamMap1.put("P_STATUS_ID", myobje.getStatusid());
		        			inParamMap1.put("P_BATCH_ID", myobje.getBatchid());
		        			inParamMap1.put("P_UNIT_PRICE", myobje.getUnitprice());
		        			inParamMap1.put("P_UNIT_PRICE_IN_CURR",myobje.getUnitprice());
		        			inParamMap1.put("P_USERID", request.getUserid());
		        			inParamMap1.put("P_OPRN", request.getOprn());
		        			inParamMap1.put("P_CGST", myobje.getCgst()); 
		        			inParamMap1.put("P_SGST", myobje.getSgst()); 		        			
		        			inParamMap1.put("P_EXPIRY_DATE", myobje.getExpirydate());
		        			inParamMap1.put("P_DISH_ID", myobje.getDishid());
		        			inParamMap1.put("P_DISHTYPE_ID", myobje.getDishtypeid());
		        			inParamMap1.put("P_ADD_PRODUCT_STATUS", myobje.getAddproduct());
		        			inParamMap1.put("P_CUSTOMER_ID", cusid);
		        			inParamMap1.put("P_CHANGE_REASON", myobje.getChangereason());
		        			inParamMap1.put("P_DISH_COUNT", dishcount);
		        		
		        			
		        			log.info("inParamMap         "+inParamMap1);
		        			SqlParameterSource in1 = new MapSqlParameterSource(inParamMap1);
		        			Map<String, Object> resultMap1 = jdbcCALL1.execute(in1);
		        	    }
		        	   
		           }
		        }
		        //System.out.println("json  "+array.getJSONObject(i).toString());
		    }
		} catch (JSONException e) {
		    System.out.println(e.getMessage());
		}
		
}
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);	
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj("Product added sucessfully");
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
}
catch(Exception e)
{
	System.out.println(e);
	error_log.info(e);
	HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

		return inventoryResponseInfoOBJ;
	}
}
