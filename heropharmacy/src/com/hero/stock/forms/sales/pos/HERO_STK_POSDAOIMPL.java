package com.hero.stock.forms.sales.pos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

public class HERO_STK_POSDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_IPOSDAO{
	private static Logger log = Logger.getLogger(HERO_STK_POSDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");

	
	public String customerOrderStatus = "";
	StringBuilder batchids = new StringBuilder();
	
	@Override
	public HERO_STK_RESPONSEINFO getCustomerDetails(/*String custname*/HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		String customerQuery = "SELECT A.`cust_id`,CONCAT(`cust_firstname`,' ',`cust_lastname`)custname,`cust_mobile_no`,`street_address`,`cust_email`"
				+ " FROM `hero_admin_customer` A JOIN `hero_admin_customer_address` B "
				+ "ON A.`cust_id` = B.`cust_id` LEFT JOIN `hero_user` c ON c.`userid` = A.`created_by` "
				+ "LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid")+
				" GROUP BY `cust_id`";
		log.info("customerQuery in getCustomerDetails   "+customerQuery);
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(customerQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("custname"));
				sb.append("   ");
				sb.append(rs.getString("cust_mobile_no"));
				sb.append("   ");
				sb.append(rs.getString("street_address"));
				sb.append("   ");
				sb.append(rs.getString("cust_email"));
				sb.append("   ");
				
				map.put("value", rs.getInt("cust_id"));
				map.put("label", sb.toString());
				map.put("custname", rs.getString("custname"));
				map.put("mobile", rs.getString("cust_mobile_no"));
				map.put("address", rs.getString("street_address"));
				map.put("email", rs.getString("cust_email"));
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getProductItems(String userid) {
		// TODO Auto-generated method stub
		String productQuery = "SELECT DISTINCT(B.`product_id`),`product_name`,`company_name`,`product_code`,B.`batch_id`,"
				+ "(SELECT SUM(`product_count`) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ " WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND `pharmacy_id` = F.`user_store_id`)product_count,"
				+ "`store_name`,F.`currencyid`,F.`userid`,`sell_price`,DATE_FORMAT(`prec_expiry_date`,'%c/%e/%y %H:%i:%s') prec_expiry_date,`CURR_SYMBOL` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock` C ON C.`product_id` = B.`product_id` AND C.`batch_id` = B.`batch_id` "
				+ "JOIN `hero_stock_product` D ON D.`product_id` = C.`product_id` AND C.`product_id` = B.`product_id` "
				+ "JOIN `hero_stock_store` E ON E.`store_id` = A.`pharmacy_id` JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` g ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id` "
				+ "JOIN `hero_admin_currency` h ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` "
				+ "LEFT JOIN `hero_admin_company` I ON I.`company_id` = D.`manufacturer_id`"
				+ "WHERE  F.`userid` = "+userid+" and D.status_id = 1 GROUP BY B.`product_id`,B.`batch_id`";
		
		log.info("productQuery    "+productQuery);
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(productQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("value", rs.getInt("product_id"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("product_name"));
				sb.append("   ");
				sb.append(rs.getString("batch_id"));
				sb.append("   ");
				
				map.put("label", sb.toString());
				map.put("productname", rs.getString("product_name"));
				map.put("productid", rs.getString("product_id"));
				map.put("productcode", rs.getString("product_code"));
				map.put("companyname", rs.getString("company_name"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("productcount", rs.getString("product_count"));
				map.put("storename", rs.getString("store_name"));
				map.put("currencyid", rs.getString("currencyid"));
				map.put("price", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("index", index);
				map.put("purchaseqty", 1);
				map.put("oldpurchaseqty", 0);
				map.put("grandtotal", rs.getFloat("sell_price") * rs.getInt("product_count"));
				map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	
	public HERO_STK_RESPONSEINFO getProductUsingBarCode(String barcode){
		String productBarQuery = "SELECT DISTINCT(B.`product_id`),`product_name`,`company_name`,`product_code`,`product_bar_code`,B.`batch_id`,"
				+ "`product_bar_code`,`prec_barcode`,"
				+ "(SELECT SUM(`product_count`) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ " WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND `pharmacy_id` = F.`user_store_id`)product_count,"
				+ "`store_name`,F.`currencyid`,F.`userid`,`product_rate` sell_price,DATE_FORMAT(`prec_expiry_date`,'%c/%e/%y %H:%i:%s') prec_expiry_date,"
				+ "`CURR_SYMBOL` FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock` C ON C.`product_id` = B.`product_id` AND C.`batch_id` = B.`batch_id` "
				+ "JOIN `hero_stock_product` D ON D.`product_id` = C.`product_id` AND C.`product_id` = B.`product_id` "
				+ "JOIN `hero_stock_store` E ON E.`store_id` = A.`pharmacy_id` JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` g ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id` "
				+ "JOIN `hero_admin_currency` h ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` "
				+ "LEFT JOIN `hero_admin_company` I ON I.`company_id` = D.`manufacturer_id`"
				+ "WHERE `prec_barcode` = '"+barcode+"' or "
				+ "`product_bar_code` = '"+barcode+"' and D.status_id = 1 GROUP BY B.`product_id`,B.`batch_id`";
		
		log.info("productBarQuery    "+productBarQuery);

		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(productBarQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("value", rs.getInt("product_id"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("product_name"));
				sb.append("   ");
				sb.append(rs.getString("batch_id"));
				sb.append("   ");
				
				map.put("label", sb.toString());
				map.put("productname", rs.getString("product_name"));
				map.put("productid", rs.getString("product_id"));
				map.put("productcode", rs.getString("product_code"));
				map.put("productbarcode", rs.getString("product_bar_code"));
				map.put("companyname", rs.getString("company_name"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("productcount", rs.getString("product_count"));
				map.put("storename", rs.getString("store_name"));
				map.put("currencyid", rs.getString("currencyid"));
				map.put("price", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("index", index);
				map.put("purchaseqty", 1);
				map.put("oldpurchaseqty", 0);
				map.put("grandtotal", rs.getFloat("sell_price") * rs.getInt("product_count"));
				map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
		
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getPOSProductItems(String userid,final HERO_STK_IPOSDAO iposDAOObj) {
		// TODO Auto-generated method stub
		/*String productQuery = "SELECT DISTINCT(B.`product_id`),`product_name`,`company_name`,`product_code`,`product_bar_code`,B.`batch_id`,"
				+ "(SELECT SUM(`product_count`) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ "(SELECT COALESCE(SUM(`product_count`),0) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ " WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND `pharmacy_id` = (SELECT `user_store_id` FROM `hero_user` "
				+ " g WHERE g.`userid` = '"+userid+"'))product_count,"
				+ "`store_name`,F.`currencyid`,F.`userid`,`product_rate` sell_price,DATE_FORMAT(`prec_expiry_date`,'%c/%e/%y %H:%i:%s') prec_expiry_date,"
				+ "`CURR_SYMBOL` FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock` C ON C.`product_id` = B.`product_id` AND C.`batch_id` = B.`batch_id` "
				+ "JOIN `hero_stock_product` D ON D.`product_id` = C.`product_id` AND C.`product_id` = B.`product_id` "
				+ "JOIN `hero_stock_store` E ON E.`store_id` = A.`pharmacy_id` JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` g ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id` "
				+ "JOIN `hero_admin_currency` h ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` "
				+ "LEFT JOIN `hero_admin_company` I ON I.`company_id` = D.`manufacturer_id`"
				+ "WHERE  F.`userid` = '"+userid+"' and D.status_id = 1 GROUP BY B.`product_id`,B.`batch_id`";
		*/
		
		
		String productQuery = "SELECT DISTINCT(B.`product_id`),`product_name`,`company_name`,`product_code`,`product_bar_code`,B.`batch_id`,`pur_full_qty`, "
				+ " `pur_full_uom`,`pur_loose_qty`,D.`hsncode`,`prec_tax_per`,g.`sgst`, `pur_loose_uom`,B.`hsuts_id`,"
				+ "(SELECT COALESCE(SUM(`product_count`),0) FROM `hero_stock_transfer` a1 JOIN `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ "WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND `pharmacy_id` = (SELECT `user_store_id` FROM `hero_user` "
				+ " g WHERE g.`userid` = '"+userid+"'))product_count, "
				+ " `store_name`,F.`currencyid`,F.`userid`,FORMAT(`product_rate`,2) sell_price,DATE_FORMAT(`prec_expiry_date`,'%c/%e/%y %H:%i:%s') prec_expiry_date,"
				+ " `CURR_SYMBOL` FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ " JOIN `hero_stock` C ON C.`product_id` = B.`product_id` AND C.`batch_id` = B.`batch_id` "
				+ " JOIN `hero_stock_product` D ON D.`product_id` = C.`product_id` AND C.`product_id` = B.`product_id` "
				+ " JOIN `hero_stock_store` E ON E.`store_id` = A.`pharmacy_id` JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` "
				+ " JOIN `hero_stock_purchase_received_dtl` g ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id` "
				+ " JOIN `hero_admin_currency` h ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` "
				+ " LEFT JOIN `hero_admin_company` I ON I.`company_id` = D.`manufacturer_id`"
				+ " WHERE  F.`userid` = '"+userid+"' AND D.status_id = 1 AND  A.`delivery_status`='2'  GROUP BY B.`product_id`,B.`batch_id`";
		
		
		
		log.info("productQuery    "+productQuery);
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(productQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("value", rs.getInt("product_id"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("product_name"));
				sb.append("   ");
				sb.append(rs.getString("product_code"));

				map.put("label", sb.toString());
				map.put("productname", rs.getString("product_name"));
				map.put("productid", rs.getString("product_id"));
				map.put("productcode", rs.getString("product_code"));
				
				map.put("hsncode", rs.getString("hsncode"));
				map.put("sgst", rs.getString("sgst"));
				map.put("cgst", rs.getString("prec_tax_per"));
				
				map.put("productbarcode", rs.getString("product_bar_code"));
				map.put("companyname", rs.getString("company_name"));
				map.put("purfullqty", rs.getString("pur_full_qty"));
				map.put("purfulluom", rs.getString("pur_full_uom"));
				map.put("purlooseqty", rs.getString("pur_loose_qty"));
				map.put("purlooseuom", rs.getString("pur_loose_uom"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("productcount", rs.getString("product_count"));
				map.put("storename", rs.getString("store_name"));
				map.put("currencyid", rs.getString("currencyid"));
				map.put("price", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("index", index);
				map.put("purchaseqty", 1);
				map.put("oldpurchaseqty", 0);
				map.put("grandtotal", rs.getFloat("sell_price") * rs.getInt("product_count"));
				map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
				
				String ordereduompackingid = rs.getString("hsuts_id");
				map.put("ordereduompackingid", ordereduompackingid);
				log.info("ordereduompackingid  "+ordereduompackingid);
				
				HERO_STK_RESPONSEINFO responseInfo = iposDAOObj.getuomforpacking(ordereduompackingid);
				HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
				log.info(response.getResponseObj());
				map.put("uomsel", response.getResponseObj());
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO getProductfromTxr(String tprid, String userid) {
		// TODO Auto-generated method stub
		String productQuery = "SELECT DISTINCT(B.`product_id`),`product_name`,`product_code`,B.`batch_id`,"
				+ "(SELECT SUM(`product_count`) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 ON a1.`transfer_id` = b1.`transfer_id` "
				+ " WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND `pharmacy_id` = F.`user_store_id`)product_count,"
				+ "`store_name`,F.`currencyid`,F.`userid`,`sell_price`,DATE_FORMAT(`prec_expiry_date`,'%c/%e/%y %H:%i:%s') prec_expiry_date,`CURR_SYMBOL` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock` C ON C.`product_id` = B.`product_id` AND C.`batch_id` = B.`batch_id` "
				+ "JOIN `hero_stock_product` D ON D.`product_id` = C.`product_id` AND C.`product_id` = B.`product_id` "
				+ "JOIN `hero_stock_store` E ON E.`store_id` = A.`pharmacy_id` JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` g ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id` "
				+ "JOIN `hero_admin_currency` h ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` "
				+ "WHERE  F.`userid` = "+userid+" and B.tpr_id = "+tprid+" GROUP BY B.`product_id`,B.`batch_id`";
		
		log.info("productQuery form Txr   "+productQuery);
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(productQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("value", rs.getInt("product_id"));
				
				StringBuilder sb = new StringBuilder();
				sb.append(rs.getString("product_name"));
				sb.append("   ");
				sb.append(rs.getString("batch_id"));
				sb.append("   ");
				
				map.put("label", sb.toString());
				map.put("productname", rs.getString("product_name"));
				map.put("productid", rs.getString("product_id"));
				map.put("productcode", rs.getString("product_code"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("productcount", rs.getString("product_count"));
				map.put("storename", rs.getString("store_name"));
				map.put("currencyid", rs.getString("currencyid"));
				map.put("price", rs.getString("sell_price"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("index", index);
				map.put("purchaseqty", 1);
				map.put("oldpurchaseqty", 0);
				map.put("grandtotal", rs.getFloat("sell_price") * rs.getInt("product_count"));
				map.put("currencysymbol", rs.getString("CURR_SYMBOL"));
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO savePOS(String posdata) {

		log.info("posdata        "+posdata);
		
		try
		{
		HERO_STK_POSREQUEST request = (HERO_STK_POSREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(posdata, "com.hero.stock.forms.sales.pos.HERO_STK_POSREQUEST");
		List<HERO_STK_POSORDERITEMS> orderList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getOrderlist(),"com.hero.stock.forms.sales.pos.HERO_STK_POSORDERITEMS");
		
		log.info("savePOS Values are     "+request.getPosid()+"   "+request.getCustomerid()+"   "+request.getDiscount()+"   "+request.getGrandtotal()+"   "
		+request.getNetamount()+"   "+request.getProductlist()+"   "+request.getTaxamountdetails()+"   "+request.getUserid()+"   "+request.getCurrencysymbol()+"   "
				+request.getOprn()+"   "+request.getMobile());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_POS_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_POS_ID", request.getPosid());
		inParamMap.put("P_POS_CODE", request.getPoscode());
		inParamMap.put("P_CUST_ID", request.getCustomerid());
		inParamMap.put("P_GRAND_TOTAL", request.getGrandtotal());
		inParamMap.put("P_TAX_AMOUNT", request.getTotaltaxamount());
		inParamMap.put("P_DISCOUNT", request.getDiscount());
		inParamMap.put("P_NET_AMOUNT", request.getNetamount());
		inParamMap.put("P_PAID_AMOUNT", request.getPaidamount());
		inParamMap.put("P_CURRENCY", request.getCurrencysymbol());
		inParamMap.put("P_PAYMENT_TYPE", request.getPaymenttype());
		inParamMap.put("P_STORE_ID", request.getStoreid());
		inParamMap.put("P_DISC_TYPE", request.getDiscounttype());
		inParamMap.put("P_DISC_TYPE_VALUE", request.getDiscounttypevalue());
		inParamMap.put("P_ORDER_COUNT", orderList.size());
		inParamMap.put("P_CARD_NUMBER", request.getCardnumber());
		inParamMap.put("P_CARD_TYPE", request.getCardtype());
		inParamMap.put("P_BANK_ID", request.getBankid());
		inParamMap.put("P_CHEQUE_NO", request.getChequeno());
		inParamMap.put("P_DELV_ADDRESS_ID", request.getDeliveryaddressid());
		inParamMap.put("P_SHIPPING_COST", request.getShippingcost());
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn());
		inParamMap.put("P_CGST", request.getCgst());
		inParamMap.put("P_SGST", request.getSgst());
		inParamMap.put("P_POS_TAX_TYPE", request.getInclusive());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		String posid = (String)resultMap.get("out_genrate_id");
		
		request.setPosid(posid);
		
		List<HERO_STK_POSPRODUCTDETAILS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getProductlist(),"com.hero.stock.forms.sales.pos.HERO_STK_POSPRODUCTDETAILS");
		List<HERO_STK_POSTAXDETAILS> taxList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getTaxamountdetails(),"com.hero.stock.forms.sales.pos.HERO_STK_POSTAXDETAILS"); 
		
		Iterator<HERO_STK_POSPRODUCTDETAILS> prodITR = itemList.iterator();
		int index = 0;
		while(prodITR.hasNext())
		{
			HERO_STK_POSPRODUCTDETAILS prod = (HERO_STK_POSPRODUCTDETAILS)prodITR.next();
			prod.setPosid(posid);
			prod.setOprn(request.getOprn());
			prod.setStoreid(request.getStoreid());
			prod.setSalescode(request.getSalescode());
			itemList.set(index, prod);
			
			log.info("Product Values are      "+prod.getBatchno()+"   "+prod.getCurrencyid()+"   "+prod.getCurrencysymbol()+"   "+prod.getExpirydate()
					+"   "+prod.getGrandtotal()+"   "+prod.getPrice()+"   "+prod.getProductcode()+"   "+prod.getProductcount()+"   "+prod.getProductid()+"   "
					+prod.getProductname()+"   "+prod.getPurchaseqty()+"   "+prod.getStorename()+"   "+request.getOprn()+"  "
					+ " "+request.getTotaltaxamount()+"  "+prod.getCgst()+"   "+prod.getSgst()+"  "+prod.getHsncode()+"   "+prod.getPosid());
			index++;
		}
		
		index = 0;
		Iterator<HERO_STK_POSORDERITEMS> orderITR = orderList.iterator();
		while(orderITR.hasNext())
		{
			log.info("index       "+index);
			HERO_STK_POSORDERITEMS order = (HERO_STK_POSORDERITEMS)orderITR.next();
			order.setPosid(posid);
			order.setOprn(request.getOprn());
			orderList.set(index, order);
		
			log.info("Product Values are      "+order.getBatchno()+"   "+order.getCurrencyid()+"   "+order.getCurrencysymbol()+"   "+order.getExpirydate()
					+"   "+order.getGrandtotal()+"   "+order.getPrice()+"   "+order.getProductcode()+"   "+order.getProductcount()+"   "+order.getProductid()+"   "
					+order.getProductname()+"   "+order.getPurchaseqty()+"   "+order.getStorename()+"   "+request.getOprn()+"   "+request.getTotaltaxamount()+"   "
					+order.getPosid());
			
			index++;
		}
		
		index = 0;
		Iterator<HERO_STK_POSTAXDETAILS> taxITR = taxList.iterator();
		while(taxITR.hasNext())
		{
			HERO_STK_POSTAXDETAILS tax = (HERO_STK_POSTAXDETAILS)taxITR.next();
			tax.setPosid(posid);
			tax.setOprn(request.getOprn());
			taxList.set(index, tax);
			index++;
			/*log.info("Tax Values are       "+tax.getTaxamount()+"   "+tax.getTaxid()+"   "+tax.getTaxrate());*/
		}
		
		saveposproductitems(itemList);
		saveposorderitems(orderList);
		savepostaxdetails(taxList);
		
		if(request.getCustomerid() != null && !request.getCustomerid().equals("0"))
		{
			sendSMSNotification(request);	
		}
		
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		/*try
		{
			communicationUtil.sendSms(request.getMobile(),0);
		}
		catch(Exception e)
		{
			
		}*/
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			e.printStackTrace();
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void saveposproductitems( final List<HERO_STK_POSPRODUCTDETAILS> list) 
	{
		try
		{
																			//  5          5              5				4
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_POS_PROD_DETAILS( ?,?,?,?,?,   ?,?,?,?,?,    ?,?,?,?,?,    ?,?,?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_POSPRODUCTDETAILS item = list.get(i);
				log.info("Purchase Values    getPodid   "+item.getPodid()+"  getPosid "+item.getPosid()+"  getProductid "+item.getProductid()+" getBatchno "+item.getBatchno()+" getPurfullqty  "
						+item.getPurfullqty()+" getPurfulluom  "+item.getPurfulluom()+"  getPurlooseqty "+item.getPurlooseqty()+" getPurlooseuom  "+item.getPurlooseuom()+"  getOrdereduompackingid "+item.getOrdereduompackingid()+" getPurchaseqty  "
						+item.getPurchaseqty()+"  getOldpurchaseqty "+item.getOldpurchaseqty()+" getPrice  "+item.getPrice()+" getSubtotal  "+item.getSubtotal()+" getOprn  "+item.getOprn()+
						" item.getStoreid()  "+item.getStoreid()+" item.getSalescode()  "+ item.getSalescode());
				
				
				ps.setString(1, item.getPodid());
				ps.setString(2, item.getPosid());
				ps.setString(3, item.getProductid());
				ps.setString(4, item.getBatchno());				
				ps.setString(5, item.getPurfullqty());
				ps.setString(6, item.getPurfulluom());
				ps.setString(7, item.getPurlooseqty());
				ps.setString(8, item.getPurlooseuom());
				ps.setString(9, item.getOrdereduompackingid());				
				ps.setString(10, item.getPurchaseqty());
				ps.setString(11, item.getOldpurchaseqty());
				ps.setString(12, item.getPrice());
				ps.setString(13, item.getSubtotal());
				ps.setString(14, item.getStoreid());				
				ps.setString(15, item.getSalescode());
				ps.setString(16, item.getOprn());
				
				ps.setString(17, item.getCgst());
				ps.setString(18, item.getSgst());
				ps.setString(19, item.getHsncode());
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}
	
	@Transactional
	public void saveposorderitems( final List<HERO_STK_POSORDERITEMS> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_POS_ORDER_DETAILS( ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_POSORDERITEMS item = list.get(i);
				/*log.info("Order Values       "+item.getPooid()+"   "+item.getPosid()+"   "+item.getProductid()+"  "+item.getBatchno()+"   "
						+item.getPurchaseqty()+"   "+item.getPrice()+"   "+item.getSubtotal()+"   "+item.getOprn());*/
				
				log.info("Order Values       "+item.getPooid()+"   "+item.getPosid()+"   "+item.getProductid()+"  "+item.getBatchno()+	
						"   "+item.getOrdfullqty()+"  "+item.getOrdfulluom()+"  "+item.getOrdlooseqty()+"  "+item.getOrdlooseuom()+"  "
						+item.getOrdereduompackingid()+"  "+item.getPurchaseqty()+"   "+item.getPrice()+"   "+item.getSubtotal()+"   "+item.getOprn());
				ps.setString(1, item.getPooid());
				ps.setString(2, item.getPosid());
				ps.setString(3, item.getProductid());
				ps.setString(4, item.getBatchno());
				
				ps.setString(5, item.getOrdfullqty());
				ps.setString(6, item.getOrdfulluom());
				ps.setString(7, item.getOrdlooseqty());
				ps.setString(8, item.getOrdlooseuom());
				ps.setString(9, item.getOrdereduompackingid());
				
				ps.setString(10, item.getPurchaseqty());
				ps.setString(11, item.getPrice());
				ps.setString(12, item.getSubtotal());
				ps.setString(13, item.getOprn());
				ps.setString(14, item.getCgst());
				ps.setString(15, item.getSgst());
				ps.setString(16, item.getHsncode());
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}
	
	@Transactional
	public void savepostaxdetails( final List<HERO_STK_POSTAXDETAILS> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_ADM_POS_TAX_DETAILS( ?, ?, ?, ?,?, ? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_POSTAXDETAILS item = list.get(i);
				log.info("Tax Values       "+item.getPotid()+"   "+item.getPosid()+"   "+item.getTaxid()+"   "+item.getTaxrate()+"   "+item.getTaxamount());
				ps.setString(1, item.getPotid());
				ps.setString(2, item.getPosid());
				ps.setString(3, item.getTaxid());
				ps.setString(4, item.getTaxrate());
				ps.setString(5, item.getTaxamount());
				ps.setString(6, item.getOprn());
				
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}

	public void sendSMSNotification(HERO_STK_POSREQUEST request)
	{
		
		log.info("Welcome to sendSMSNotification");
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_POS_SMS_COMMN");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_ISH_ID", 0);
		inParamMap.put("P_ISH_TXN_ID", request.getPosid());
		inParamMap.put("P_ISH_CUST_ID", request.getCustomerid());
		inParamMap.put("P_ISH_SMS_TEMPLATE_ID", 1);
		inParamMap.put("P_ISH_STATUS", 0);
		inParamMap.put("P_SMS_RESPONSE", "");
		inParamMap.put("P_CRETAED_BY", request.getUserid());
		inParamMap.put("P_ACTION", "SMS_SALES");
		
		log.info("sendSMSNotification inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		
	}
	@Override
	public HERO_STK_RESPONSEINFO getCustomerDetail(String customerid) {
		// TODO Auto-generated method stub
		String customerQuery = "SELECT DISTINCT(A.`cust_id`),`customer_group`,`cust_email`,`cust_mobile_no`,`customer_group_name`,"
				+ "CONCAT(`street_address`,' , ',`city`,' - ', `zipcode`,' , ',`state`,' , ',`country`)address "
				+ "FROM `hero_admin_customer` A JOIN `hero_admin_customer_group` B ON A.`customer_group` = B.`seq_id` "
				+ "LEFT JOIN `hero_admin_customer_address` c ON c.`cust_id` = A.`cust_id` WHERE A.`cust_id` = "+customerid;
		log.info("customerQuery       "+customerQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(customerQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("customerid", rs.getInt("cust_id"));
				map.put("customergroup", rs.getString("customer_group_name"));
				map.put("email", rs.getString("cust_email"));
				map.put("mobile", rs.getString("cust_mobile_no"));
				map.put("address", rs.getString("address"));
				
				return map;
			}
		});
		
		String customerBalanceQuery = "SELECT SUM(`pos_balance_amount`)pos_balance_amount,sum(`pos_credit_amount`)pos_credit_amount"
				+ " FROM `hero_stock_pos_summary` WHERE `cust_id` = "+customerid;
		log.info("customerBalanceQuery       "+customerBalanceQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> customerBalanceList = jdbcTemplate.query(customerBalanceQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("balanceamt", rs.getDouble("pos_balance_amount"));
				map.put("creditamount", rs.getDouble("pos_credit_amount"));
				
				return map;
			}
		});
		
		String posQuery1 = "SELECT pos_id,`pos_code`,`created_at`,CONCAT(`currency`,`pos_netamount`)amount FROM `hero_stock_pos_summary` WHERE `cust_id` = "+customerid;
		String posQuery = "SELECT A.`pos_id`,`pos_code`,DATE_FORMAT(A.`created_at`,'%e/%c/%y')created_at,CONCAT(A.`currency`,`pos_netamount`)amount, A.`cust_id`,"
							+"`pos_sales_type`,COALESCE(CONCAT(`cust_firstname`, ' ',`cust_lastname`),'POC Customer')CUST_NAME,"
							+"`CURR_SYMBOL`, `store_name`,`pos_status_desc`,"
							+"FORMAT((`pos_balance_amount` * -1),2)pos_balance_amount,`pos_return_sales_code`,"
							+"`pos_orders_avail` FROM `hero_stock_pos_summary` A LEFT JOIN `hero_admin_customer` B ON B.`cust_id` = A.`cust_id`"  
							+"JOIN `hero_stock_store` C ON A.`pos_store_id` = C.`store_id` JOIN `hero_stock_pos_status` d ON"
							+" d.`pos_status_id` = A.`pos_status` JOIN `hero_admin_currency` e ON e.`currency_id` = C.`currency_id` WHERE A.`cust_id` = "+customerid;	
		log.info("posQuery       "+posQuery);
		@SuppressWarnings("unchecked")
		List<Object> posList = jdbcTemplate.query(posQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("poscode", rs.getString("pos_code"));
				map.put("salesat", rs.getString("created_at"));
				map.put("amount", rs.getString("amount"));
				map.put("possalestype", rs.getString("pos_sales_type"));
				map.put("custname", rs.getString("CUST_NAME"));
				map.put("currsympol", rs.getString("CURR_SYMBOL"));
				map.put("storename", rs.getString("store_name"));
				map.put("posstatusdesc", rs.getString("pos_status_desc"));
				map.put("posbalanceamount", rs.getString("pos_balance_amount"));
				map.put("posreturnsalescode", rs.getString("pos_return_sales_code"));
				map.put("posordersavail", rs.getString("pos_orders_avail"));
				 
				
log.info("map"+map);				
if(rs.getString("pos_orders_avail").equals("0")){
	String posProductDetailsQuery = "SELECT `pos_id`,`pos_prod_id`,`product_name`,`pos_batch_id`,`pos_sales_count`,`pos_sales_price`,"
			+ "`pos_subtotal`,(SELECT SUM(`product_count`) FROM `hero_stock_transfer` A1 JOIN `hero_stock_transfer_product` B1 "
			+ "ON A1.`transfer_id` = B1.`transfer_id` WHERE B1.`product_id` = B.`pos_prod_id` AND B1.`batch_id` = B.`pos_batch_id` "
			+ "AND `pharmacy_id` = 1)productcount FROM `hero_stock_pos_prod_details` B JOIN `hero_stock_product` C ON C.`product_id` = B.`pos_prod_id` "
			+ "WHERE `pos_id` = "+rs.getInt("pos_id")+" AND `pos_return_qty` = 0";
	log.info("posProductDetailsQuery for sale   "+posProductDetailsQuery);
	
	@SuppressWarnings("unchecked")
	List<Object> posProductList = jdbcTemplate.query(posProductDetailsQuery, new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("productname", rs.getString("product_name"));
			map.put("salescount", rs.getString("pos_sales_count"));
			map.put("subtotal", rs.getString("pos_subtotal"));
			map.put("batchno", rs.getString("pos_batch_id"));
			
			return map;
		}
	});
	log.info("posProductList     "+posProductList);
	map.put("posProductList", posProductList);
}else{
	
	String posProductDetailsQuery = "SELECT `pos_id`,`poo_prod_id`,`product_name`,`poo_batch_id`,`poo_sales_count`,`poo_sales_price`,`poo_subtotal`,"
				+"(SELECT SUM(`product_count`) FROM `hero_stock_transfer` A1 JOIN `hero_stock_transfer_product` B1 ON A1.`transfer_id` = "
				+" B1.`transfer_id` WHERE B1.`product_id` = B.`poo_prod_id` AND B1.`batch_id` = B.`poo_batch_id` AND `pharmacy_id` "
				+" = 1)productcount FROM `hero_stock_pos_order_items` B JOIN `hero_stock_product` C ON C.`product_id` = B.`poo_prod_id` WHERE "
				+"`pos_id` =  "+rs.getInt("pos_id");
	log.info("posProductDetailsQuery for order   "+posProductDetailsQuery);
	
	@SuppressWarnings("unchecked")
	List<Object> posProductList = jdbcTemplate.query(posProductDetailsQuery, new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("productname", rs.getString("product_name"));
			map.put("salescount", rs.getString("poo_sales_count"));
			map.put("subtotal", rs.getString("poo_subtotal"));
			map.put("batchno", rs.getString("poo_batch_id"));
			
			return map;
		}
	});
	log.info("posProductList     "+posProductList);
	map.put("posProductList", posProductList);
}
				
				return map;
			}
		});
		
		String customerTipQuery = "SELECT `product_name`,`pos_batch_id`,`pos_sales_count` "
				+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` "
				+ "JOIN `hero_stock_product` c ON c.`product_id` = b.`pos_prod_id` WHERE `cust_id` = "+customerid+" ORDER BY a.`pos_id` DESC LIMIT 3";
		log.info("customerTipQuery      "+customerTipQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> customerTipList = jdbcTemplate.query(customerTipQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productname", rs.getString("product_name"));
				map.put("batchid", rs.getString("pos_batch_id"));
				map.put("salescount", rs.getString("pos_sales_count"));
				
				
				return map;
			}
		});
		
		
		String custAddressQuery = "SELECT `ca_id`,`cust_id`,CONCAT(`street_address`,', ',`city`,' - ',`zipcode`,', ',`state`,', ',`country`)address"
				+ " FROM `hero_admin_customer_address` WHERE `cust_id` = "+customerid;
		log.info("custAddressQuery       "+custAddressQuery);
		@SuppressWarnings("unchecked")
		List<Object> custAddressList = jdbcTemplate.query(custAddressQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("custaddressid", rs.getString("ca_id"));
				map.put("custid", rs.getString("cust_id"));
				map.put("address", rs.getString("address"));
				map.put("custaddressradio", "<input type='radio' id='custaddressradio'"+index+" name='custaddress' value='"+rs.getString("ca_id")+"' onclick='setcustaddress(this.value);'/>");
				index++;
				return map;
			}
		});
		 
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerList", customerList);
		map.put("customerBalanceList", customerBalanceList);
		map.put("posList", posList);
		map.put("customerTipList", customerTipList);
		map.put("custAddressList", custAddressList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO POSHistoryDetails(String storeid) {
		// TODO Auto-generated method stub
		String invListQuery = "SELECT A.`pos_id`,`pos_code`,`pos_tax_type`,DATE_FORMAT(A.`created_at`,'%e/%c/%Y')created_at,A.`cust_id`,`pos_sales_type`,"
				+ "COALESCE(CONCAT(`cust_firstname`, ' ',`cust_lastname`),'POC Customer')CUST_NAME,`CURR_SYMBOL`, `store_name`,"
				+ "FORMAT(`pos_netamount`,2)pos_netamount,`pos_status_desc`, "
				+ "FORMAT((`pos_balance_amount` * -1),2)pos_balance_amount,`pos_return_sales_code`,`pos_orders_avail` "
				+ "FROM `hero_stock_pos_summary` A LEFT JOIN `hero_admin_customer` B ON A.`cust_id` = B.`cust_id` JOIN `hero_stock_store` C ON A.`pos_store_id` = C.`store_id` "
				+ "JOIN `hero_stock_pos_status` d ON d.`pos_status_id` = A.`pos_status` JOIN `hero_admin_currency` e ON e.`currency_id` = C.`currency_id` "
				/*+ "WHERE A.`pos_store_id` = "+storeid+" and `pos_orders_avail` = 0";*/
				+ "WHERE A.`pos_store_id` = "+storeid;
		
		log.info("invListQuery    "+invListQuery);
		@SuppressWarnings("unchecked")
		List<Object> invoiceList = jdbcTemplate.query(invListQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				String baldisp = "",netdisp = "";
baldisp = (
		(rs.getString("CURR_SYMBOL") == null ? "" : rs.getString("CURR_SYMBOL"))
		.concat(
				(rs.getString("pos_balance_amount") == null ? "" : rs.getString("pos_balance_amount"))
			   )
		  );

netdisp = (
		(rs.getString("CURR_SYMBOL") == null ? "" : rs.getString("CURR_SYMBOL"))
		.concat(
				(rs.getString("pos_netamount") == null ? "" : rs.getString("pos_netamount"))
			   )
		  );
				
				map.put("postaxtype", rs.getString("pos_tax_type"));
				map.put("posid", rs.getInt("pos_id"));
				map.put("poscode", rs.getString("pos_code"));
				map.put("salesat", rs.getString("created_at"));
				map.put("customerid", rs.getString("cust_id"));
				map.put("customername", rs.getString("CUST_NAME"));
				map.put("storename", rs.getString("store_name"));
				map.put("totalamount", rs.getString("pos_netamount"));
				map.put("statusdesc", rs.getString("pos_status_desc"));
				map.put("balanceamount", rs.getString("pos_balance_amount"));
				map.put("balanceamountdisp", baldisp);
				map.put("netamtdisp", netdisp);
				map.put("posordersavail", rs.getInt("pos_orders_avail"));
				
				if(rs.getInt("pos_orders_avail") > 0)
				{
					map.put("action", "<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
							+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				}
				else if(rs.getString("pos_return_sales_code") == null)
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION+"<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
						+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				else
				map.put("action", "<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
						+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				
				map.put("salestype", rs.getString("pos_sales_type"));
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(invoiceList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO deletePOS(String posid) {

		log.info("posid        "+posid);
		
		try
		{
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_POS_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_POS_ID", posid);
		inParamMap.put("P_POS_CODE", 0);
		inParamMap.put("P_CUST_ID", 0.0);
		inParamMap.put("P_GRAND_TOTAL", 0.0);
		inParamMap.put("P_TAX_AMOUNT", 0.0);
		inParamMap.put("P_DISCOUNT", 0.0);
		inParamMap.put("P_NET_AMOUNT", 0.0);
		inParamMap.put("P_PAID_AMOUNT", 0.0);
		inParamMap.put("P_CURRENCY", "");
		inParamMap.put("P_PAYMENT_TYPE", 0);
		inParamMap.put("P_STORE_ID", 0);
		inParamMap.put("P_USER_ID", 0);
		inParamMap.put("P_DISC_TYPE", "");
		inParamMap.put("P_DISC_TYPE_VALUE", 0.0);
		inParamMap.put("P_ORDER_COUNT", 0);
		inParamMap.put("P_CARD_NUMBER", "");
		inParamMap.put("P_CARD_TYPE", "");
		inParamMap.put("P_BANK_ID", 0);
		inParamMap.put("P_CHEQUE_NO", "");
		inParamMap.put("P_DELV_ADDRESS_ID", 0);
		inParamMap.put("P_SHIPPING_COST", 0);
		inParamMap.put("P_OPRN", "DEL");
		inParamMap.put("P_CGST", 0);
		inParamMap.put("P_SGST",0);
		
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
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getPOSDetails(String posid,String storeid,String type) {
		// TODO Auto-generated method stub
		String posMainQuery = "SELECT `pos_id`,A.`cust_id`,`pos_tax_type`,COALESCE(CONCAT(`cust_firstname`,' ',`cust_lastname`),'POC Customer')CUST_NAME,`pos_grand_total`,`pos_discount`,"
				+ "`pos_netamount`,`currency`,`pos_payment_type`,`pos_paid_amt`,`pos_discount_type`,`pos_discount_type_value`,"
				+ "`pos_order_code`,`pos_card_number`,`pos_card_type`,`pos_bank_id`,`pos_cheque_no`,`pos_delv_address_id`,pos_sales_type "
				+ " FROM `hero_stock_pos_summary` A LEFT JOIN `hero_admin_customer` B ON A.`cust_id` = B.`cust_id` "
				+ "WHERE pos_id = "+posid;
		
		if(type != null && type.equals("R"))
		{
			posMainQuery = "SELECT a.`pos_id`,A.`cust_id`,COALESCE(CONCAT(`cust_firstname`,' ',`cust_lastname`),'POC Customer')CUST_NAME,"
					+ "`pos_grand_total`,`pos_discount`,`pos_netamount`,`currency`,`pos_payment_type`,`pos_paid_amt`,`pos_discount_type`,"
					+ "`pos_discount_type_value`,`pos_order_code`,`pos_card_number`,`pos_card_type`,`pos_bank_id`,`pos_cheque_no`,`pos_delv_address_id`,"
					+ "pos_sales_type  FROM `hero_stock_pos_summary` A LEFT JOIN `hero_admin_customer` B ON A.`cust_id` = B.`cust_id` "
					+ "LEFT JOIN `hero_stock_pos_prod_details` c  ON c.`pos_id` = a.`pos_id` WHERE a.pos_id = "+posid;
					/*+ "  AND `pos_sales_count` != (`pos_initial_sales_count`)";*/
		}
		
		log.info("posMainQuery       "+posMainQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> posMainList = jdbcTemplate.query(posMainQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("postaxtype", rs.getString("pos_tax_type"));
				map.put("posid", rs.getInt("pos_id"));
				map.put("custid", rs.getString("cust_id"));
				map.put("custname", rs.getString("CUST_NAME"));
				map.put("totalamount", rs.getString("pos_grand_total"));
				map.put("discountamount", rs.getString("pos_discount"));
				map.put("netamount", rs.getString("pos_netamount"));
				map.put("currency", rs.getString("currency"));
				map.put("paymenttype", rs.getString("pos_payment_type"));
				map.put("paidamount", rs.getString("pos_paid_amt"));
				map.put("discounttype", rs.getString("pos_discount_type"));
				map.put("discounttypevalue", rs.getString("pos_discount_type_value"));
				map.put("ordercode", rs.getString("pos_order_code"));
				map.put("cardnumber", rs.getString("pos_card_number"));
				map.put("cardtype", rs.getString("pos_card_type"));
				map.put("bankid", rs.getString("pos_bank_id"));
				map.put("chequeno", rs.getString("pos_cheque_no"));
				map.put("deliveryaddress", rs.getString("pos_delv_address_id"));
				map.put("salestype", rs.getString("pos_sales_type"));
				
				return map;
			}
		});
		
		String posProdQuery = "SELECT `pos_id`,`pos_prod_id`,`product_name`,`pos_batch_id`,B.`cgst`,B.`sgst`,C.`hsncode`,`pos_sales_count`,`pos_sales_price`,`pos_subtotal`,`sal_full_qty`,`sal_full_uom`,`sale_loose_qty`,`sal_loose_uom`,`sal_uom_packing_id`,"
				+ "(SELECT SUM(`product_count`) FROM `hero_stock_transfer` A1 JOIN `hero_stock_transfer_product` B1 ON A1.`transfer_id` = B1.`transfer_id` "
				+ "WHERE B1.`product_id` = B.`pos_prod_id` AND B1.`batch_id` = B.`pos_batch_id` AND `pharmacy_id` = "+storeid+")productcount"
				+ " FROM `hero_stock_pos_prod_details` B JOIN `hero_stock_product` C ON C.`product_id` = B.`pos_prod_id` WHERE `pos_id` = "+posid;
				/*+" AND `pos_return_qty` = 0";*/
		
		log.info("posProdQuery       "+posProdQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> posProdList = jdbcTemplate.query(posProdQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("posid", rs.getInt("pos_id"));
				map.put("productid", rs.getInt("pos_prod_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getInt("sgst"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("batchno", rs.getString("pos_batch_id"));
				map.put("purchaseqty", rs.getInt("pos_sales_count"));
				map.put("oldpurchaseqty", rs.getInt("pos_sales_count"));
				map.put("price", rs.getDouble("pos_sales_price"));
				map.put("subtotal", rs.getDouble("pos_subtotal"));
				map.put("remainproductcount", rs.getInt("productcount"));
				map.put("productcount", (rs.getInt("productcount")) + rs.getInt("pos_sales_count"));
				map.put("uomfullqty", rs.getInt("sal_full_qty"));
				map.put("fulluomid", rs.getInt("sal_full_uom"));
				map.put("looseuomqty", rs.getInt("sale_loose_qty"));
				map.put("looseuomid", rs.getInt("sal_loose_uom"));
				map.put("ordereduompackingid", rs.getInt("sal_uom_packing_id"));
				map.put("index", index);
				
				index++;
				return map;
			}
		});
		
		String posOrderQuery = "SELECT `pos_id`,`poo_prod_id`,`product_name`,`poo_batch_id`,`poo_sales_count`,`poo_sales_price`,`poo_subtotal`"
				+ " FROM `hero_stock_pos_order_items` a JOIN `hero_stock_product` b ON a.`poo_prod_id` = b.`product_id` "
				+ "WHERE `pos_id` = "+posid;
		
		log.info("posOrderQuery       "+posOrderQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> posOrderList = jdbcTemplate.query(posOrderQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("posid", rs.getInt("pos_id"));
				map.put("productid", rs.getInt("poo_prod_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("batchno", rs.getString("poo_batch_id"));
				map.put("purchaseqty", rs.getInt("poo_sales_count"));
				map.put("price", rs.getDouble("poo_sales_price"));
				map.put("subtotal", rs.getDouble("poo_subtotal"));
				map.put("index", index);
				
				index++;
				return map;
			}
		});
		
		String posTaxQuery = "SELECT `tax_id`,`tax_rate`,`tax_amount` FROM `hero_stock_pos_tax` WHERE `pos_id` = "+posid;
		log.info("posTaxQuery       "+posTaxQuery);
		@SuppressWarnings("unchecked")
		List<Object> posTaxList = jdbcTemplate.query(posTaxQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("taxid", rs.getString("tax_id"));
				map.put("taxrate", rs.getString("tax_rate"));
				map.put("taxamount", rs.getString("tax_amount"));
				
				return map;
			}
		});
		 
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("posMainList", posMainList);
		map.put("posProdList", posProdList);
		map.put("posOrderList", posOrderList);
		map.put("posTaxList", posTaxList);
		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getBillDetails(String billno) {
		// TODO Auto-generated method stub
		
		/*String existReturnQuery = "SELECT `pos_prod_id`,`product_name`,`product_code`,`pos_batch_id`,`pos_sales_count`,`pos_sales_price`,`pos_subtotal`,"
				+ "`pos_initial_sales_count` FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` "
				+ "AND b.`pos_return_sales_code` = '"+billno+"' JOIN `hero_stock_product` c ON c.`product_id` = b.`pos_prod_id`";
		
		log.info("existReturnQuery     "+existReturnQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> existReturnList = jdbcTemplate.query(existReturnQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getInt("pos_prod_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("productcode", rs.getString("product_code"));
				map.put("batchno", rs.getString("pos_batch_id"));
				map.put("productcount", (rs.getInt("pos_initial_sales_count") * -1));
				map.put("purchaseqty", (rs.getInt("pos_sales_count") + (-1)));
				map.put("price", rs.getDouble("pos_sales_price"));
				map.put("subtotal", rs.getString("pos_subtotal"));
				map.put("selprodid", "<input type='checkbox' id='selprodid"+index+"' value='"+rs.getInt("pos_prod_id")+"' >");
				map.put("index", index);
				
				index++;
				
				return map;
			}
		});
		*/
		
		 	String newReturnQuery = "SELECT `pos_prod_id`,`product_name`,`product_code`,`pos_batch_id`,`pos_sales_count`,`pos_sales_price`,`pos_subtotal`,"
		 			+ "`pos_return_qty`,`pos_initial_sales_count`,b.`cgst`,b.`sgst`,b.`hsncode`,  `sal_uom_packing_id`,"
		 			+ " `sal_full_qty`, `sal_full_uom`, `sale_loose_qty`, `sal_loose_uom`"
		 			+ " FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` "
		 			+ "AND `pos_code` = '"+billno+"' JOIN `hero_stock_product` c ON c.`product_id` = b.`pos_prod_id` and pos_sales_count > 0";
			
			log.info("newReturnQuery     "+newReturnQuery);
			
			/*if(existReturnList.size() == 0)
			{*/
			@SuppressWarnings("unchecked")
			List<Object> newReturnList = jdbcTemplate.query(newReturnQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("productid", rs.getInt("pos_prod_id"));
					map.put("productname", rs.getString("product_name"));
					map.put("productcode", rs.getString("product_code"));
					
					map.put("cgst", rs.getInt("cgst"));
					map.put("sgst", rs.getString("sgst"));
					map.put("hsncode", rs.getString("hsncode"));
					
					map.put("batchno", rs.getString("pos_batch_id"));
					map.put("productcount", (rs.getInt("pos_initial_sales_count") * -1));
					map.put("initialsalescount", (rs.getInt("pos_sales_count") * -1));
					map.put("salesqty", (rs.getInt("pos_sales_count")));
					if(rs.getInt("pos_return_qty") == 0)
					{
						map.put("purchaseqty", -1);	
					}
					else
					{
						map.put("purchaseqty", (rs.getInt("pos_return_qty")) + (-1));
					}
					map.put("price", rs.getDouble("pos_sales_price"));
					map.put("subtotal", rs.getString("pos_subtotal"));
					map.put("selprodid", "<input type='checkbox' id='selprodid"+index+"' value='"+rs.getInt("pos_prod_id")+"' >");
					map.put("ordereduompackingid", rs.getString("sal_uom_packing_id"));
					map.put("purfullqty", rs.getString("sal_full_qty"));
					map.put("purfulluom", rs.getString("sal_full_uom"));
					map.put("purlooseqty", rs.getString("sale_loose_qty"));
					map.put("purlooseuom", rs.getString("sal_loose_uom"));
					map.put("index", index);
					
					index++;
					
					return map;
				}
			});
			
			inventoryResponseOBJ.setResponseObj(newReturnList);
			/*}
			else
			{
				inventoryResponseOBJ.setResponseObj(existReturnList);	
			}*/
		
			log.info("getResponseObj       "+inventoryResponseOBJ.getResponseObj());
		
		inventoryResponseOBJ.setResponseType("S");
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getCustomerOrders(String storeid) {
		// TODO Auto-generated method stub
		String customerOrderQuery = "SELECT `pos_id`,`pos_code`,CONCAT(`cust_firstname`,' ',`cust_lastname`)custname,DATE_FORMAT(A.`created_at`,"
				+ "'%e/%c/%Y')billdate,"
				+ "CONCAT(`street_address`,', ',`city`,' - ',`zipcode`,', ',`state`,', ',`country`)address,`pos_status_desc`,`pos_balance_amount`,"
				+ "`pos_order_status_id`,"
				+ "(SELECT `pos_status_desc` FROM `hero_stock_pos_status` A1 WHERE A1.`pos_status_id` = `pos_order_status_id`)delvstatus "
				+ "FROM `hero_stock_pos_summary` A JOIN `hero_admin_customer` D ON D.`cust_id` = A.`cust_id` LEFT JOIN `hero_admin_customer_address` e "
				+ "ON e.`ca_id` = A.`pos_delv_address_id` "
				+ "LEFT JOIN `hero_stock_pos_status` f ON f.`pos_status_id` = A.`pos_status` WHERE `pos_orders_avail` = 1 AND `pos_store_id` = "+storeid;
		
		log.info("customerOrderQuery      "+customerOrderQuery);

		/*String orderStatusQuery = "SELECT `pos_status_id`,`pos_status_desc` FROM `hero_stock_pos_status` WHERE `pos_status_id` IN(5,6,7);";
		
		@SuppressWarnings("unchecked")
		List<Object> orderStatusList = jdbcTemplate.query(orderStatusQuery, new RowMapper() {
			StringBuilder status = new StringBuilder();
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				status.append("<option value='"+rs.getInt(1)+"'>"+rs.getString(2)+"</option>");
				return status.toString();
			}
		});

		if(orderStatusList != null && orderStatusList.size() > 0)
		{
			customerOrderStatus = (String) orderStatusList.get(orderStatusList.size() - 1);
		}
		log.info("customerOrderStatus      "+customerOrderStatus);
		*/
		@SuppressWarnings("unchecked")
		List<Object> customerOrderList = jdbcTemplate.query(customerOrderQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("posid", rs.getInt("pos_id"));
				map.put("poscode", rs.getString("pos_code"));
				map.put("custname", rs.getString("custname"));
				map.put("address", rs.getString("address"));
				map.put("paymentstatus", rs.getString("pos_status_desc"));
				map.put("balanceamt", rs.getString("pos_balance_amount"));
				
				map.put("billdate", rs.getString("billdate"));
				map.put("orderstatus", rs.getString("delvstatus"));	
				/*if(rs.getInt("pos_order_status_id") == 5)
				{
					map.put("orderstatus", "<select  class='form-control form-white  selectNor' id='orderstatusid"+index+"'"
							+ "onchange='changerecvstatus("+index+");' id='statusselect"+index+"'>"
							+ customerOrderStatus+ "</select>");	
				}
				else
				{
					map.put("orderstatus", rs.getString("pos_status_desc"));	
				}*/
				
				
				if(rs.getInt("pos_order_status_id") == 5)
				{
					map.put("poscodehtml", "<label onclick='clickposcode("+rs.getInt("pos_id")+","+index+","+rs.getInt("pos_order_status_id")+
							")'>"+rs.getString("pos_code")+"</label>");
				}
				else
				{
					/*map.put("poscodehtml", rs.getString("pos_code"));*/
					map.put("poscodehtml", "<label onclick='clickposcode("+rs.getInt("pos_id")+","+index+","+rs.getInt("pos_order_status_id")+
							")'>"+rs.getString("pos_code")+"</label>");
				} 
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerOrderList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO saveOrderStatus(String orderdata) {

		log.info("orderdata        "+orderdata);
		
		try
		{
			List<HERO_STK_POSORDERITEMS> orderList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(orderdata,"com.hero.stock.forms.sales.pos.HERO_STK_POSORDERITEMS");
			saveordereditems(orderList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj("Record Updated Successfully");
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	
	@Transactional
	public void saveordereditems( final List<HERO_STK_POSORDERITEMS> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_POS_DELIVER_ORDER( ?,?,?,?,?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_POSORDERITEMS item = list.get(i);
				log.info("Purchase Values       "+item.getPosid()+"   "+item.getProductid()+"  "+item.getBatchno()+"   "
						+item.getPurchaseqty()+"   "+item.getPrice()+"   "+item.getStoreid()+"   "+item.getOprn());
				
				ps.setString(1, item.getPosid());
				ps.setString(2, item.getProductid());
				ps.setString(3, item.getBatchno());
				
				ps.setString(4, item.getPurchaseqty());
				
				
				
				
				ps.setString(5, item.getStoreid());
				ps.setString(6, item.getDeliverystatus());
				ps.setString(7, item.getUserid());
				ps.setString(8, "INS");
				
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
	public HERO_STK_RESPONSEINFO getOrderItems(String posid) {
		// TODO Auto-generated method stub
		String orderItemsQuery = "SELECT `tpr_id`,(FORMAT(`pos_paid_amt`,2))pos_paid_amt,(FORMAT(`pos_balance_amount`,2))pos_balance_amount,"
				+ "(FORMAT((`pos_tax_amount`+A.`cgst`+A.`sgst`),2))pos_tax_amount,`product_name`,`pos_payment_type`,`poo_prod_id`,`poo_batch_id`,`poo_prod_id`,`poo_batch_id`,"
				+ "`product_name`,C.`status_id`,`poo_sales_count`,FORMAT(`poo_sales_price`,2)poo_sales_price,"
				+ "FORMAT(`poo_subtotal`,2)poo_subtotal,A.`currency` CURR_SYMBOL,SUM(`product_count`)product_count,"
				+ "(FORMAT(`pos_netamount`,2))pos_netamount,(FORMAT(`pos_discount_type_value`,2))discamount,"
				+ "(FORMAT(`pos_grand_total`,2))grandtotal,`pos_discount_type_value` "
				+ "FROM `hero_stock_pos_summary` A JOIN `hero_stock_pos_order_items` B ON A.`pos_id` = B.`pos_id` JOIN `hero_stock_product` C ON B.`poo_prod_id` = C.`product_id` "
				+ "JOIN `hero_stock_store` D ON D.`store_id` = A.`pos_store_id` "
				+ "LEFT JOIN `hero_admin_currency` E ON E.`currency_id` = D.`currency_id` AND D.`currency_id` = E.`currency_id` "
				+ "LEFT JOIN `hero_stock_transfer_product` f ON f.`product_id` = B.`poo_prod_id` AND f.`batch_id` = B.`poo_batch_id` AND C.`product_id` = f.`product_id` "
				+ "JOIN `hero_stock_transfer` g ON g.`transfer_id` = f.`transfer_id` AND g.`pharmacy_id` = A.`pos_store_id` "
				+ "WHERE A.`pos_id` = "+posid+" GROUP BY f.`product_id`,f.`batch_id`";
		
		log.info("orderItemsQuery        "+orderItemsQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> orderItemsList = jdbcTemplate.query(orderItemsQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();

				/*tringBuilder salesprice = new StringBuilder();
				salesprice.append(rs.getString("CURR_SYMBOL"));
				salesprice.append("   ");
				salesprice.append(rs.getString("poo_sales_price"));
				
				StringBuilder subtotal = new StringBuilder();
				subtotal.append(rs.getString("CURR_SYMBOL"));
				subtotal.append("   ");
				subtotal.append(rs.getString("poo_subtotal"));*/

				map.put("productid", rs.getString("poo_prod_id"));
				map.put("paidamount", rs.getString("pos_paid_amt"));
				map.put("balanceamount", rs.getString("pos_balance_amount"));
				map.put("taxamount", rs.getString("pos_tax_amount"));
				map.put("paymenttype", rs.getString("pos_payment_type"));
				map.put("productname", rs.getString("product_name"));
				map.put("batchno", rs.getString("poo_batch_id"));
				map.put("purchaseqty", rs.getString("poo_sales_count"));
				//map.put("price", salesprice);
				//map.put("subtotal", subtotal);
				map.put("stockqty", rs.getInt("product_count"));
				map.put("index", index);
				map.put("netamount", rs.getString("pos_netamount"));
				map.put("discamount", rs.getString("discamount"));
				map.put("taxamount", rs.getString("pos_tax_amount"));
				map.put("grandtotal", rs.getString("grandtotal"));
				map.put("discount", rs.getDouble("pos_discount_type_value"));
				map.put("currency", rs.getString("CURR_SYMBOL"));
				
				map.put("price", rs.getDouble("poo_sales_price"));
				map.put("subtotal", rs.getString("poo_subtotal"));
				
				batchids = new StringBuilder();
				String batchListQuery = "SELECT DISTINCT(`batch_id`) FROM `hero_stock_transfer_product`"
						+ " WHERE `product_id` = "+rs.getString("poo_prod_id")+" AND `product_count` > 0";
				
				@SuppressWarnings("unchecked")
				List<Object> batchList = jdbcTemplate.query(batchListQuery, new RowMapper() {
					
					
					@Override
					public Object mapRow(ResultSet rs, int index) throws SQLException {
						// TODO Auto-generated method stub
						batchids.append("<option value='"+rs.getString(1)+"'>"+rs.getString(1)+"</option>");
						return batchids.toString();
					}
				});
				
				map.put("batchids", "<select  class='form-control form-white  selectNor' tooltip='this.value' id='batchid"+index+"'"
						+ " id='statusselect"+index+"'>"
						+ batchids+ "</select>");
				
				index++;
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(orderItemsList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
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
		
		log.info("fulluompackingQuery   "+fulluompackingQuery);
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
		
		log.info("looseuompackingQuery   "+looseuompackingQuery);
		//log.info("looseuomPackingList   "+looseuomPackingList);
		
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


}


