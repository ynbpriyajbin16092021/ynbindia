package com.hero.stock.forms.transactions.product;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.DVConstraint;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataValidation;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.springframework.web.multipart.MultipartFile;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST;
import com.hero.stock.response.HERO_STK_RESPONSE;

import java.util.Random;
import java.util.Random;

public class HERO_STK_SERVC_PRODUCTDAOIMPL implements HERO_STK_SERVC_IPRODUCTDAO {
	
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_PRODUCTDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;
	@Autowired
	private HERO_STK_RESPONSE inventoryResponseOBJ;
	

	
	@Override
	public HERO_STK_RESPONSEINFO saveproduct(String productData) {
		// TODO Auto-generated method stub
		try
		{
			log.info("productData   "+productData);
		HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(productData, 
				"com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");
		
		log.info("Values are     "+request.getAlertcount()+"   "+request.getCategoryid()+"   "+request.getDescription()+"   "+request.getManufacturerid()+"   "
				+request.getProductcode()+"   "+request.getProductid()+"   "+request.getProductname()+"   "+request.getStatusid()+"   "+request.getUnitquantity()+"   "
				+request.getUnittypeid()+"   "+request.getUserid()+"   "+request.getOprn()+"  "+request.getProductbarcode());
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PRODUCT_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_PRODUCT_ID", request.getProductid());//0
		inParamMap.put("P_PRODUCT_NAME", request.getProductname());//1
		inParamMap.put("P_PRODUCT_CODE", request.getProductcode());//2
		inParamMap.put("P_PRODUCT_BAR_CODE", request.getProductbarcode());//3
		inParamMap.put("P_CATEGORY_ID", request.getCategoryid());//4
		inParamMap.put("P_UNIT_TYPE_ID", request.getUnittypeid());//5
		inParamMap.put("P_UNIT_QUANTITY", request.getUnitquantity());//6
		inParamMap.put("P_DESCRIPTION", request.getDescription());//7
		inParamMap.put("P_ALERT_COUNT", request.getAlertcount());//8
		inParamMap.put("P_MANUFACTURER_ID", request.getManufacturerid());//9
		inParamMap.put("P_STATUS_ID", request.getStatusid());//10
		inParamMap.put("P_USER_ID", request.getUserid());//11
		inParamMap.put("P_OPRN", request.getOprn());//12
		inParamMap.put("P_CGST", request.getCgst());//13
	    inParamMap.put("P_OPTTYPE", request.getOpttype());//13
	    inParamMap.put("P_SGST", request.getSgst());//13
		inParamMap.put("P_HSNCODE", request.getHsncode());
		inParamMap.put("P_UNIT_RATE", request.getProductrate());
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO productlist() {
		// TODO Auto-generated method stub
	/*	String query = "SELECT * FROM hero_stock_product a, hero_admin_category b,hero_admin_unit_type c,hero_admin_company d WHERE a.category_id = b.category_id "
				+ "AND a.unit_type_id = c.unit_type_id AND a.manufacturer_id = d.company_id";*/
		
		String query = " SELECT `product_id`,`product_name`,`product_code`,`category_name`,`description`,a.`status_id`,a.`category_id`,"
				+ " `product_bar_code`,a.`unit_type_id`,`unit_quantity`,`unit`,`alert_count`,`company_name`,a.`manufacturer_id`, "
				+ " `user_id`,`opttype`,e.`TAX_ID`cgst,f.`TAX_ID`sgst,`hsncode`,`unit_rate`,CONCAT('Rs.',`unit_rate`)unitratedisp FROM hero_stock_product a  "
				+ " JOIN hero_admin_category b ON a.category_id = b.category_id   JOIN hero_admin_unit_type c ON  a.unit_type_id = c.unit_type_id   "
				+ "  JOIN hero_admin_company d ON a.manufacturer_id = d.company_id    LEFT JOIN `hero_admin_tax`e ON a.`cgst` = e.`TAX_RATE` "
				+ " LEFT JOIN `hero_admin_tax` f ON a.`sgst` = f.`TAX_RATE` ORDER BY `product_id` DESC";
		
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productid", rs.getString("product_id"));
				map.put("alertcount", rs.getString("alert_count"));
				map.put("categoryid", rs.getString("category_id"));
				map.put("description", rs.getString("description"));
				map.put("productcode", rs.getString("product_code"));
				map.put("productname", rs.getString("product_name"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("opttype", rs.getString("opttype"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("productrate", rs.getString("unitratedisp"));
				map.put("statusid", rs.getString("status_id"));
				map.put("manufacturerid", rs.getString("manufacturer_id"));
				map.put("unitquantity", rs.getString("unit_quantity"));
				map.put("unittypeid", rs.getString("unit_type_id"));
				map.put("unit_rate", rs.getString("unit_rate"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				
				map.put("categorydesc", rs.getString("category_name"));
				map.put("manufacturerdesc", rs.getString("company_name"));
				map.put("uomdesc", rs.getString("unit"));
				
				map.put("unitdisp", rs.getInt("unit_quantity")+" "+rs.getString("unit"));
				
				map.put("productidhref", "<a href='productview?prodid="+rs.getString("product_id")+"' "
						+ "onclick='gotoproductview('"+rs.getString("product_id")+"')'>"+rs.getString("product_name")+"</a>");
				
				if(rs.getInt("status_id") == 0)
				{
					map.put("statusdisp", "InActive");
				}
				else if(rs.getInt("status_id") == 1)
				{
					map.put("statusdisp", "Active");
				}
				
				return map;
			}
		});
		log.info(productList);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", HosurInventoryUtil.returnJSONobject(productList));
		log.info(new Gson().toJson(map));*/
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getproductinfo(String productid) {
		// TODO Auto-generated method stub
		String query = "SELECT `product_id`,`product_name`,`description`,`product_code`,`category_id`,`product_bar_code`,a.`unit_type_id`,`unit_quantity`,`alert_count`,`manufacturer_id`,"
				+ "`user_id`,`opttype`,`cgst`,`sgst`,`hsncode`,a.`status_id`,`product_code`,CASE WHEN b.`TAX_TYPE`= 'P' THEN CONCAT(b.TAX_NAME,'(',b.TAX_RATE,'%)')"
				+ " ELSE CONCAT(b.TAX_NAME,'(',b.TAX_RATE,')') END AS label,(`unit_rate`+(`unit_rate`*0.05))unit_rate,"
				+ "CASE WHEN c.`TAX_TYPE`= 'P' THEN CONCAT(c.TAX_NAME,'(',c.TAX_RATE,'%)')ELSE CONCAT(c.TAX_NAME,'(',c.TAX_RATE,')') END AS label2,`unit` unitdesc  "
				+ " FROM hero_stock_product a LEFT JOIN `hero_admin_tax` b ON a.`cgst`= b.`TAX_RATE`"
				+ " LEFT JOIN `hero_admin_tax` c ON a.`sgst`= c.`TAX_RATE` LEFT JOIN `hero_admin_unit_type` d ON d.`unit_type_id`=a.`unit_type_id`"
				+ "where product_id="+productid;
		@SuppressWarnings("unchecked")		
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getString("product_id"));
				map.put("alertcount", rs.getString("alert_count"));
				map.put("categoryid", rs.getString("category_id"));
				map.put("description", rs.getString("description"));
				map.put("productcode", rs.getString("product_code"));
				map.put("productname", rs.getString("product_name"));
				map.put("cgst", rs.getString("label"));
				map.put("sgst", rs.getString("label2"));
				map.put("opttype", rs.getString("opttype"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("statusid", rs.getString("status_id"));
				map.put("manufacturerid", rs.getString("manufacturer_id"));
				map.put("unitquantity", rs.getString("unit_quantity"));
				map.put("unittypeid", rs.getString("unit_type_id"));
				map.put("unitdesc", rs.getString("unitdesc"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("unitrate", rs.getString("unit_rate"));
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(query);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", HosurInventoryUtil.returnJSONobject(productList));
		log.info(new Gson().toJson(map));*/
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO getproductviewinfo(String productid,String userid) {
		// TODO Auto-generated method stub
		

		Map<String, Object> map = new HashMap<String, Object>();
		
		try
		{
			
		String productlistQuery = "SELECT product_id,product_name,`product_code`,a.`unit_type_id`,CONCAT(`unit_quantity`,' ',`unit`)unit,`alert_count`,`manufacturer_id`,"
		+ "`company_name`,a.`status_id`,`description`,a.`category_id`,`category_name`,COALESCE(`cgst`,'-')cgst,COALESCE(`sgst`,'-')sgst,"
		+ "COALESCE(`hsncode`,'-')hsncode FROM hero_stock_product a LEFT JOIN `hero_admin_unit_type` b ON a.`unit_type_id` = b.`unit_type_id` "
		+ "LEFT JOIN `hero_admin_company` c ON a.`manufacturer_id` = c.`company_id` LEFT JOIN `hero_admin_category` d ON a.`category_id` = d.`category_id`  "
		+ " WHERE product_id ="+productid;
		
		log.info("productlistQuery      "+productlistQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(productlistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("productid", rs.getString("product_id"));
				map.put("alertcount", rs.getString("alert_count"));
				map.put("description", rs.getString("description"));
				map.put("productcode", rs.getString("product_code"));
				map.put("productname", rs.getString("product_name"));
				map.put("statusid", rs.getString("status_id"));
				map.put("manufacturerid", rs.getString("manufacturer_id"));
				map.put("unit", rs.getString("unit"));
				map.put("unittypeid", rs.getString("unit_type_id"));
				map.put("categorytypeid", rs.getString("category_id"));
				map.put("categoryname", rs.getString("category_name"));
				map.put("manufacturerdesc", rs.getString("company_name"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("sgst", rs.getString("sgst"));
				map.put("cgst", rs.getString("cgst"));
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(productList);

		map.put("productlist", productList);
		
		}
		catch(Exception e)
		{

      error_log.info(e);
		}
		
		
		try
		{
			
		
		
		String storelistQuery = "SELECT c.`product_id`,`store_name`,COALESCE(SUM(b.`product_count`),0) product_count FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b ON "
				+ "a.`transfer_id` = b.`transfer_id` JOIN `hero_stock` c ON c.`product_id` = b.`product_id` AND b.`batch_id` = c.`batch_id` "
				+ "JOIN `hero_stock_store` d ON d.`store_id` = a.`pharmacy_id` WHERE b.`product_id` ="+productid+" AND b.`product_count` != 0 GROUP BY a.`pharmacy_id`";
		
		log.info("storelistQuery      "+storelistQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> storeList = jdbcTemplate.query(storelistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getString("product_id"));
				map.put("storename", rs.getString("store_name"));
				map.put("productcount", rs.getInt("product_count"));
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(storeList);
		
		if(storeList.size() == 0)
		{
			storelistQuery = "SELECT store_name,"+productid+" product_id FROM `hero_stock_store`";
			
			@SuppressWarnings("unchecked")
			List<Object> emptyStoreList = jdbcTemplate.query(storelistQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					int count = 0;
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("productid", rs.getString("product_id"));
					map.put("storename", rs.getString("store_name"));
					map.put("productcount", 0);
					
					count++;
					map.put("count",count);
					
					return map;
				}
			});
			
			storeList.addAll(emptyStoreList);
		}
		
		map.put("storeList", storeList);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
		String availstockQuery = "SELECT COALESCE(SUM(`product_count`),0) product_count FROM `hero_stock` WHERE `product_id` ="+productid;
		
		log.info("availstockQuery      "+availstockQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> availstockList = jdbcTemplate.query(availstockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productcount", rs.getInt("product_count"));
				
				return map;
			}
		});
		log.info(availstockList);
		
		int availstock = 0;
		if(availstockList != null && availstockList.size() > 0)
		{
			Map<String, Object> availstockmap = (Map<String, Object>) availstockList.get(0);
			if(availstockmap != null)
			availstock = (int) availstockmap.get("productcount");
		}
		
		map.put("availstock", availstock);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
		String tobemovedQuery = "SELECT COALESCE(SUM(`product_count`),0) product_count FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B "
				+ "ON A.`transfer_id` = B.`transfer_id` AND `delivery_status` = 1 AND `product_id` ="+productid;
		
		log.info("tobemovedQuery      "+tobemovedQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> tobemovedList = jdbcTemplate.query(tobemovedQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productcount", rs.getInt("product_count"));
				
				return map;
			}
		});
		log.info(tobemovedList);
		
		int tobemovedcount = 0;
		if(tobemovedList != null && tobemovedList.size() > 0)
		{
			Map<String, Object> tobemovedmap = (Map<String, Object>) tobemovedList.get(0);
			if(tobemovedmap != null)
				tobemovedcount = (int) tobemovedmap.get("productcount");
		}
		
		map.put("tobemovedcount", tobemovedcount);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		
		try
		{
			
		String batchlistQuery = "SELECT `prec_product_id`,`prec_batchno`,SUM(`prec_recving_quantity`)prec_recving_quantity,"
				+ "DATE_FORMAT(`prec_expiry_date`,'%e/%c/%Y')prec_expiry_date,CONCAT('Rs. ',FORMAT(`prec_sel_price`,2) )prec_sel_price,`product_count` "
				+ "FROM `hero_stock_purchase` A JOIN `hero_stock_purchase_request` B ON A.`purchase_code` = B.`pur_req_id` "
				+ "JOIN `hero_stock_purchase_receive_hdr` C ON C.`pur_req_id` = A.`purchase_code` AND C.`pur_req_id` = B.`pur_req_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` D ON C.`prhdr_id` = D.`prec_hdr_id` AND D.`prec_product_id` = B.`pur_product_id` "
				+ "JOIN `hero_stock` E ON E.`product_id` = D.`prec_product_id` AND E.`product_id` = B.`pur_product_id` AND E.`batch_id` = D.`prec_batchno` "
				+ "WHERE `prec_product_id` = "+productid+" GROUP BY `prec_batchno`,`prec_product_id`";
		
		log.info("batchlistQuery      "+batchlistQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> batchList = jdbcTemplate.query(batchlistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("batchno", rs.getString("prec_batchno"));
				map.put("orderedqty", rs.getString("prec_recving_quantity"));
				map.put("availqty", rs.getString("product_count"));
				map.put("mfddate", rs.getString("prec_expiry_date"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("unitprice", rs.getString("prec_sel_price"));
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(batchList);

		map.put("batchList", batchList);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
			
		String purchaselistQuery = "SELECT DATE_FORMAT(`purchase_date`,'%e/%c/%Y')purchase_date,`purchase_code`,"
				+ "CONCAT(`supplier_first_name`)suppliername,pur_quantity,`prec_recving_quantity`,"
				+ "CONCAT('Rs. ',FORMAT(SUM(`prec_pur_price`),2))prec_pur_price,`TAX_RATE` tax_rate,"
				+ "prec_sub_total,`prod_tax_total` "
				+ "FROM `hero_stock_purchase_receive_hdr` A JOIN `hero_stock_purchase_received_dtl` B ON A.`prhdr_id` = B.`prec_hdr_id` "
				+ "JOIN `hero_stock_purchase` C ON C.`purchase_code` = A.`pur_req_id` JOIN `hero_stock_purchase_request` d ON d.`pur_req_id` = A.`pur_req_id` "
				+ "AND d.`pur_product_id` = B.`prec_product_id` JOIN `hero_stock_supplier` e ON e.`supplier_id` = C.`supplier_id` "
				+ "JOIN `hero_admin_tax` f ON A.prhdr_tax_id = f.TAX_ID WHERE `prec_product_id` = "+productid+" GROUP BY `purchase_code`";
		
		log.info("purchaselistQuery      "+purchaselistQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> purchaseList = jdbcTemplate.query(purchaselistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("prodtaxtotal", rs.getString("prod_tax_total"));
				map.put("recqty", rs.getString("prec_recving_quantity"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("suppliername", rs.getString("suppliername"));
				map.put("purchaseqty", rs.getString("pur_quantity"));
				map.put("totalamount", rs.getString("prec_pur_price"));
				map.put("subtotal", rs.getString("prec_sub_total"));
				double taxAmount =(rs.getDouble("tax_rate")*rs.getDouble("prec_sub_total"))/100;
				log.info(rs.getDouble("tax_rate"));
				map.put("taxamount",taxAmount );
				map.put("grandtotalamount", rs.getDouble("prec_sub_total")+taxAmount);
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(purchaseList);

		map.put("purchaseList", purchaseList);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
			
		String transferlistQuery = "SELECT B.`product_id`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,`transfer_no`,SUM(`product_count`)product_count,`batch_id`,"
				+ "`store_name`,`CURR_SYMBOL`,FORMAT(((SUM(`product_count`)) * `product_rate`),2)amount "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` JOIN `hero_stock_store` c "
				+ "ON c.`store_id` = A.`pharmacy_id` JOIN `hero_admin_currency` d ON d.`currency_id` = c.`currency_id` "
				+ "WHERE B.`product_id` = "+productid+" AND `delivery_status` = 2 GROUP BY A.`transfer_no`,`batch_id` "
						+ "ORDER BY d.`currency_id`,A.`transfer_id` ASC";
		
		log.info("transferlistQuery      "+transferlistQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> transferList = jdbcTemplate.query(transferlistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				int count = 0;
				
				String currency = rs.getString("CURR_SYMBOL") == null ? "":rs.getString("CURR_SYMBOL");
				String amount = rs.getString("amount") == null ? "0" : rs.getString("amount");
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("transferno", rs.getString("transfer_no"));
				map.put("productcount", rs.getString("product_count"));
				map.put("storename", rs.getString("store_name"));
				map.put("batchid", rs.getString("batch_id"));
				//map.put("amount", currency+" "+amount);
				map.put("amount", rs.getString("amount"));
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(transferList);

		map.put("transferList", transferList);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
			
		
		
			String saleslistQuery = " SELECT `product_name`,`pos_batch_id`,`pos_sales_count`,`pos_subtotal`,b.`cust_id`,"
					+ " COALESCE(c.`cust_firstname`,'POC CUSTOMER')cust_firstname,`store_name`"
					+"  FROM `hero_stock_pos_prod_details` a JOIN `hero_stock_pos_summary` b ON a.`pos_id` = b.`pos_id` LEFT JOIN `hero_admin_customer` c"
					 +" ON c.`cust_id` = b.`cust_id` LEFT JOIN `hero_stock_store` d ON d.`store_id` = b.`pos_store_id` "
					+"  LEFT JOIN `hero_stock_product` e ON e.`product_id` = a.`pos_prod_id`"
					 +" WHERE `pos_prod_id` ='"+productid+"'";
			
			log.info("saleslistQuery      "+saleslistQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> salesList = jdbcTemplate.query(saleslistQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					// TODO Auto-generated method stub
					int count = 0;
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("productname", rs.getString("product_name"));
					map.put("posbatchid", rs.getString("pos_batch_id"));
					map.put("possalescount", rs.getInt("pos_sales_count"));
					map.put("possubtotal", rs.getString("pos_subtotal"));
					map.put("custid", rs.getString("cust_id"));
					map.put("custfirstname", rs.getString("cust_firstname"));
					map.put("storename", rs.getString("store_name"));
				
				count++;
				map.put("count",count);
				
				return map;
			}
		});
		log.info(salesList);
		
		map.put("salesList", salesList);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getproductsuggestions() {
		// TODO Auto-generated method stub
		log.info("welcome to get product suggestion");
		String selectQuery = "SELECT * FROM hero_stock_product A LEFT JOIN `hero_admin_company` B ON A.`manufacturer_id` = B.`company_id` WHERE A.status_id = 1";
		@SuppressWarnings("unchecked")
		List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
				
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("data", rs.getString("PRODUCT_ID"));
				map.put("value", rs.getString("PRODUCT_NAME"));
				
				map.put("value", rs.getString("PRODUCT_ID"));
				
				StringBuilder labelSB = new StringBuilder();
				labelSB.append(rs.getString("PRODUCT_NAME"));
				if(rs.getString("company_name") != null)
				{
					labelSB.append(" - ");
					labelSB.append(rs.getString("company_NAME"));
				}
				
				map.put("label", labelSB.toString());
				
				/*detail.add(map);*/
		
				log.info("Category Suggestion List        "+map);
				
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
		inventoryResponseOBJ.setResponseObj((categoryList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	 @Override
     public void downloadSampleFormat(String fileName,String columnsheet,HttpServletRequest httpRequest, HttpServletResponse response) 
     
     {
	      /*  int nofocolumn = Integer.parseInt(columnsheet);
			int extensionIndex = fileName.lastIndexOf(".");
			String uploadedFilename = fileName.substring(0,extensionIndex)+"_"+new Date(extensionIndex).toString()+fileName.substring(extensionIndex,fileName.length());
				try {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=SampleExcel.xlsx");
	        
		
				XSSFWorkbook wb = new XSSFWorkbook();
				XSSFSheet sheet=(XSSFSheet) wb.createSheet("productlist");
			
				Row rowSample = sheet.createRow(0);
				int columnCount = 0;

				String columnNames = ("ManufacturerCode *,Product NAME *,Product SKU *,"
						+ "Category Name *,UOM *,UOM TYPE *, Alert Quantity,HSNCODE, Product Rate");
				
				StringTokenizer columnToken = new StringTokenizer(columnNames,",");
				while(columnToken.hasMoreTokens())
				{
					 
					String columnName = columnToken.nextToken();
					
					Cell cell = rowSample.createCell(columnCount);
					cell.setCellValue(columnName);
					XSSFCellStyle cellStyle = wb.createCellStyle();
					cell.setCellStyle((org.apache.poi.ss.usermodel.CellStyle) cellStyle);
					columnCount++;
					
				}
				
				List<String> categoryList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT  (category_name) FROM `hero_admin_category` ");
				List<String> uomList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT `unit` FROM `hero_admin_unit_type`");
			  //  List<String> websiteList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT `name` FROM `hero_websites`");
				//List<String> genderList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT 'male' AS gender UNION ALL SELECT 'female' AS gender UNION ALL SELECT 'unisex' AS gender");
				//List<String> stockstatusList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT 'InStore' AS status_desc UNION ALL SELECT 'Out of Stock' AS status_desc");
				List<String> manufacturerList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT (`company_name`) FROM `hero_admin_company`");
			//	List<String> uompackingList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, "SELECT `hsuts_desc` FROM `hero_stock_unit_type_setting`");
				//List<String> typeList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, "SELECT `type` FROM `products_type`");
				
				
				String[] categoryArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(categoryList);
				String[] uomArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(uomList);
			//	String[] websiteArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(websiteList);
				//String[] genderArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(genderList);
				//String[] stockstatusArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(stockstatusList);
				String[] companyArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(manufacturerList);
				//String[] typeArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(typeList);
				//String[] uompackingArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(uompackingList);
				XSSFDataValidationHelper validationHelper = new XSSFDataValidationHelper(sheet);
				
				
				
				DataValidation dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,categoryArr, sheet,true,nofocolumn,3);
				  dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,uomArr, sheet,true,nofocolumn,5);
				//dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,websiteArr, sheet,true,nofocolumn,1);
				//dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,genderArr, sheet,true,nofocolumn,4);
				//dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,stockstatusArr, sheet,true,nofocolumn,19);
				//dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,typeArr, sheet,true,nofocolumn,8);
				dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,companyArr, sheet,true,nofocolumn,0);
				//dataValidation = HEROHOSURINVENTORYUTIL.getCellList(validationHelper,uompackingArr, sheet,true,nofocolumn,21);
				
				for(int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				     sheet.autoSizeColumn(columnIndex);
				}
			

			
				wb.write(response.getOutputStream());
				
	        } catch (Exception e) {
	           
	        	e.printStackTrace();
	        }
*/
		 /*int nofocolumn = Integer.parseInt(columnsheet);
			int extensionIndex = fileName.lastIndexOf(".");
			String uploadedFilename = fileName.substring(0,extensionIndex)+"_"+new Date(extensionIndex).toString()+fileName.substring(extensionIndex,fileName.length());
				try {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=SampleExcel.xls");
	          
	       	 List<String> categoryList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT  (category_name) FROM `hero_admin_category` ");
			 List<String> manufacturerList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT (`company_name`) FROM `hero_admin_company`");
			 List<String> uomList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT `unit` FROM `hero_admin_unit_type`");
			 String[] categoryArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(categoryList);
			 String[] companyArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(manufacturerList);
			 String[] uomArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(uomList);
			 HSSFWorkbook workbook = new HSSFWorkbook();
			 HSSFSheet realSheet = workbook.createSheet("productlist");
			 HSSFSheet hidde1 = workbook.createSheet("hidden1");
			 HSSFSheet hidden = workbook.createSheet("hidden");
			 HSSFSheet hidden2 = workbook.createSheet("hidden2");
	            int columnCount = 0;
	        	Row rowSample = realSheet.createRow(0);
				String columnNames = ("ManufacturerCode *,Product NAME *,Product SKU *,"
						+ "Category Name *,UOM *,UOM TYPE *, Alert Quantity,HSNCODE, Product Rate");
				
				StringTokenizer columnToken = new StringTokenizer(columnNames,",");
				while(columnToken.hasMoreTokens())
				{
					 
					String columnName = columnToken.nextToken();
					
					Cell cell = rowSample.createCell(columnCount);
					cell.setCellValue(columnName);
					HSSFCellStyle cellStyle = workbook.createCellStyle();
					cell.setCellStyle((org.apache.poi.ss.usermodel.CellStyle) cellStyle);
					columnCount++;
					
				}
				  for (int i = 0, length= categoryArr.length; i < length; i++) {
					    String name = categoryArr[i];
					    HSSFRow row = hidde1.createRow(i);
					    HSSFCell cell = row.createCell(0);
					  
					    cell.setCellValue(name);
					   
					  }
				  
				  Name named1Cell = workbook.createName();
				  named1Cell.setNameName("hidden1");
				  named1Cell.setRefersToFormula("hidden1!$B$1:$B$" + categoryArr.length);
				
				  DVConstraint constraint2 = DVConstraint.createFormulaListConstraint("hidden1");
				
				  CellRangeAddressList addressList11 = new CellRangeAddressList(1, nofocolumn, 3, 3);
				  constraint2 = 
					       DVConstraint.createExplicitListConstraint(categoryArr);
				  HSSFDataValidation validation12 = new HSSFDataValidation(addressList11, constraint2);
				  validation12.setSuppressDropDownArrow(false);
				  workbook.setSheetHidden(1, true);
				  realSheet.addValidationData(validation12);
		 
		 for (int i = 0, length= companyArr.length; i < length; i++) {
			    String name = companyArr[i];
			
			    HSSFRow row = hidden.createRow(i);
			    HSSFCell cell = row.createCell(1);
			    cell.setCellValue(name);
			    
			  }
		 
		  named1Cell.setNameName("hidden");
		  named1Cell.setRefersToFormula("hidden1!$A$1:$A$" + companyArr.length);
		 
		  DVConstraint constraint1 = DVConstraint.createFormulaListConstraint("hidden");
		
		  CellRangeAddressList addressList1 = new CellRangeAddressList(1, nofocolumn, 0, 0);
		  constraint1 = 
			       DVConstraint.createExplicitListConstraint(companyArr);
		  HSSFDataValidation validation1 = new HSSFDataValidation(addressList1, constraint1);
		  validation1.setSuppressDropDownArrow(false);
		  workbook.setSheetHidden(1, true);
		  realSheet.addValidationData(validation1);
		  
	
		  for (int i = 0, length= uomArr.length; i < length; i++) {
			    String name = uomArr[i];
			
			    HSSFRow row = hidden2.createRow(i);
			    HSSFCell cell = row.createCell(1);
			    cell.setCellValue(name);
			    
			  }
		 
		  named1Cell.setNameName("hidden2");
		  named1Cell.setRefersToFormula("hidden1!$A$1:$A$" + uomArr.length);
		 
		  DVConstraint constraint3 = DVConstraint.createFormulaListConstraint("hidden2");
		
		  CellRangeAddressList addressList13 = new CellRangeAddressList(1, nofocolumn, 5, 5);
		  constraint3 = 
			       DVConstraint.createExplicitListConstraint(uomArr);
		  HSSFDataValidation validation13 = new HSSFDataValidation(addressList13, constraint3);
		  validation13.setSuppressDropDownArrow(false);
		  workbook.setSheetHidden(1, true);
		  realSheet.addValidationData(validation13);
		
		
		 

		  for(int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
			  realSheet.autoSizeColumn(columnIndex);
			}
		

		
			workbook.write(response.getOutputStream());*/
		 int nofocolumn = Integer.parseInt(columnsheet);
			int extensionIndex = fileName.lastIndexOf(".");
			String uploadedFilename = fileName.substring(0,extensionIndex)+"_"+new Date(extensionIndex).toString()+fileName.substring(extensionIndex,fileName.length());
				try {
	            response.setContentType("application/vnd.ms-excel");
	            response.setHeader("Content-Disposition", "attachment; filename=SampleExcel.xls");
	            HSSFWorkbook workbook = new HSSFWorkbook();
	   		 HSSFSheet realSheet = workbook.createSheet("productlist");
	   		 HSSFSheet hidden = workbook.createSheet("hidden1");
	   		 HSSFSheet hidden2 = workbook.createSheet("hidden2");
	   		 HSSFSheet hidden3 = workbook.createSheet("hidden3");
	   		 
	   		Row rowSample = realSheet.createRow(0);
			int columnCount = 0;

			String columnNames = ("ManufacturerCode *,Product NAME *,Product SKU *,"
					+ "Category Name *,UOM *,UOM TYPE *, Alert Quantity,HSNCODE, Product Rate");
			
			StringTokenizer columnToken = new StringTokenizer(columnNames,",");
			while(columnToken.hasMoreTokens())
			{
				 
				String columnName = columnToken.nextToken();
				
				Cell cell = rowSample.createCell(columnCount);
				cell.setCellValue(columnName);
				HSSFCellStyle cellStyle = workbook.createCellStyle();
				cell.setCellStyle((org.apache.poi.ss.usermodel.CellStyle) cellStyle);
				columnCount++;
				
			}
			 List<String> categoryList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT  (category_name) FROM `hero_admin_category` ");
			
			
			 String[] categoryArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(categoryList);
			for (int i = 0, length= categoryArr.length; i < length; i++) {
			    String name = categoryArr[i];
			    HSSFRow row = hidden.createRow(i);
			    HSSFCell cell = row.createCell(0);
			    cell.setCellValue(name);
			  }
			
		
			for(int columnIndex = 0; columnIndex < columnCount; columnIndex++) {
				realSheet.autoSizeColumn(columnIndex);
			}
			 Name named1Cell = workbook.createName();
			  named1Cell.setNameName("hidden1");
			 
			  named1Cell.setRefersToFormula("hidden1!$A$1:$A$" + categoryArr.length);
			  DVConstraint constraint1 = DVConstraint.createFormulaListConstraint("hidden1");
			  CellRangeAddressList addressList1 = new CellRangeAddressList(1, nofocolumn, 3, 3);
			 
			  HSSFDataValidation validation1 = new HSSFDataValidation(addressList1, constraint1);
			  workbook.setSheetHidden(1, true);
			  realSheet.addValidationData(validation1);

			  List<String> manufacturerList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT (`company_name`) FROM `hero_admin_company`");
			  String[] companyArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(manufacturerList);
			  
			  for (int i = 0, length= companyArr.length; i < length; i++) {
				    String name1 = companyArr[i];
				    HSSFRow row1 = hidden2.createRow(i);
				    HSSFCell cell1 = row1.createCell(0);
				    cell1.setCellValue(name1);
				  }
		    	System.out.println("proiya two");
		    	 Name named2Cell = workbook.createName();
		    	 named2Cell.setNameName("hidden2");
			 
		    	 named2Cell.setRefersToFormula("hidden2!$A$1:$A$" + companyArr.length);
			  DVConstraint constraint2 = DVConstraint.createFormulaListConstraint("hidden2");
			  CellRangeAddressList addressList2 = new CellRangeAddressList(1, nofocolumn, 0, 0);
			 
			  HSSFDataValidation validation2 = new HSSFDataValidation(addressList2, constraint2);
			  workbook.setSheetHidden(1, true);
			  realSheet.addValidationData(validation2);
			  
			  List<String> uomList = HEROHOSURINVENTORYUTIL.getStringList(jdbcTemplate, " SELECT `unit` FROM `hero_admin_unit_type`");

			  String[] uomArr = HEROHOSURINVENTORYUTIL.convertListtoStringArray(uomList);
			  
			  for (int i = 0, length= uomArr.length; i < length; i++) {
				    String name2 = uomArr[i];
				    HSSFRow row2 = hidden3.createRow(i);
				    HSSFCell cell2 = row2.createCell(0);
				    cell2.setCellValue(name2);
				  }
		    	
		    	 Name named3Cell = workbook.createName();
		    	 named3Cell.setNameName("hidden3");
			 
		    	 named3Cell.setRefersToFormula("hidden3!$A$1:$A$" + uomArr.length);
			  DVConstraint constraint3 = DVConstraint.createFormulaListConstraint("hidden3");
			  CellRangeAddressList addressList3 = new CellRangeAddressList(1, nofocolumn, 5, 5);
			 
			  HSSFDataValidation validation3 = new HSSFDataValidation(addressList3, constraint3);
			  workbook.setSheetHidden(1, true);
			  realSheet.addValidationData(validation3);
			workbook.write(response.getOutputStream());
				}catch (Exception e) {
			           
		        	e.printStackTrace();
		        }
	     
			
      
	     
  }

	@Override
	public HERO_STK_RESPONSEINFO importProducttoStock(String importdata,
			HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		try
		{
		HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST)HEROHOSURINVENTORYUTIL.
				convertJSONtooOBJECT(importdata, "com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");
		
		log.info("Values are          "+request.getFilename()+"   "+httpRequest.getServletContext().getRealPath("")+File.separator+request.getFilename());
		
		String filePath = httpRequest.getServletContext().getRealPath("")+File.separator+"importproduct"+File.separator+request.getFilename();
		log.info("filePath"+  filePath);
		int rowCount = 0;
		
		 try {

			String fileSeq = "";
			StringTokenizer token = new StringTokenizer(request.getFilename(),".");
			int count = 0;
			while(token.hasMoreTokens())
			{
				if(count == 0)
				{
					fileSeq = token.nextToken();	
				}
				else
				{
					token.nextToken();
				}
				
				
				count++;
			}
			
			log.info(fileSeq);
			
			HttpSession session = httpRequest.getSession();
			
			FileInputStream fileInputStream = new FileInputStream(filePath);
			HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
			HSSFSheet worksheet = workbook.getSheet("productlist");
			
			/*XSSFRow row1 = worksheet.getRow(0);
			XSSFCell cellA1 = row1.getCell((short) 0);
			String a1Val = cellA1.getStringCellValue();*/
			int totalCellCount = 0;
			List<Object> excelDatas = new ArrayList<Object>();
			
			Iterator rows = worksheet.rowIterator();
			while (rows.hasNext()) {
				/*log.info("rowCount       "+rowCount);*/	
				HSSFRow row = (HSSFRow) rows.next();
				if(rowCount > 0)
				{
					Iterator cells = row.cellIterator();
					int cellCount = 0;
					Map<String, Object> map = new HashMap<String, Object>();
	                while (cells.hasNext()) {
	                	HSSFCell cell = (HSSFCell) cells.next();
	                    totalCellCount = row.getPhysicalNumberOfCells();
	                    cellCount++;
	                    
	                    switch(cell.getCellType())
	                    {
	                    case 0:
	                    	double value = cell.getNumericCellValue();
	                    	map.put("cell"+cellCount, String.valueOf(value));
	                    	break;
	                    case 1:
	                    	map.put("cell"+cellCount, cell.getStringCellValue());
	                    }
	                    
	                    /*log.info(cell.getCellType()+"   "+map);*/
	                       
	                }
	                map.put("cell"+(totalCellCount + 1), fileSeq);
	                map.put("cell"+(totalCellCount + 2), session.getAttribute("uid"));
	                excelDatas.add(map);
	                /*log.info("-------------------------");*/	
				}
					
                    rowCount++;
			}
			
			log.info("excelDatas   "+excelDatas);
			log.info("Total No. of uploading Products   "+excelDatas.size());
			
			importproductitems(excelDatas,fileSeq);
			
			log.info("Process Completed Successfully "+request.getFilename());
			
			String selectQuery = "select `record_seq_no`,`upload_status`,`error_msg` from hero_stock_product_import_status where `upload_seq_no` = "+fileSeq;
			log.info("selectQuery  "+selectQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> categoryList = jdbcTemplate.query(selectQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
					
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("recindex", rs.getString("record_seq_no"));
					String uploadstatus = rs.getString("upload_status");
					if(uploadstatus != null && uploadstatus.equalsIgnoreCase("Success"))
					{
						map.put("uploadstatus", "<label style='color:green'>"+uploadstatus+"</label>");	
					}
					else
					{
						map.put("uploadstatus", "<label style='color:red'>"+uploadstatus+"</label>");
					}
					
					map.put("errormsg", rs.getString("error_msg"));
					
					/*detail.add(map);*/
			
					/*log.info("Category Suggestion List        "+map);*/
					
					return map;
				}
			});
			
			/*Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", "1");
			map.put("msg", categoryList);
			
			log.info(map);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(map);*/
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(categoryList);
			
		} catch (FileNotFoundException e) {
			error_log.info(e);
			
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		} catch (IOException e) {
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		catch (Exception e) {

            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
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
	public void importproductitems( final List<Object> list, String fileSeq) 
	{
		
		try
		{
			StringBuilder parametersSB = new StringBuilder();
			int totalCellCount = 11;
			
			for(int cell = 1;cell <= totalCellCount;cell++)
			{
				/*if(cell != totalCellCount+1)
				{*/
					parametersSB.append("?,");
				/*}
				else
				{
					parametersSB.append("?");
				}*/
			}
			parametersSB.append("?");
			
			log.info("parametersSB        "+parametersSB);
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PRODUCT_IMPORT( "+parametersSB+" )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int index) throws SQLException 
			{
				
				Map<String, Object> map = (Map<String, Object>) list.get(index);
				for(int cell = 1;cell <= 11;cell++)
				{
					log.info("P_CELL"+cell+","+map.get("cell"+cell)+"   "+(map.get("cell"+cell).getClass()));
					ps.setString(cell, map.get("cell"+cell).toString());
				}
				ps.setInt(12, index);
				
			log.info("ps"+ps);
			log.info(index+" ) Imported Successfully");
			
			/*Map<String, Object> mapMsg = new HashMap<String, Object>();
			mapMsg.put("msg", "Imported Successfully");*/
				
			}
		});
		}
		catch( Exception e )
		{
			//StringWriter sw = new StringWriter();
           // PrintWriter pw = new PrintWriter(sw);
            //e.getMessage(pw);
           
            String stacktraceString = null;
            stacktraceString = e.getMessage().toString();
            e.printStackTrace();
            //System.out.println("String is: "+stacktraceString);
            
            System.out.println("stacktraceString  "+stacktraceString);
			if(stacktraceString != null && stacktraceString != ""){
				jdbcTemplate.update(
					    "INSERT INTO `hero_stock_product_import_status`"
					    + " (`upload_seq_no`,`upload_status`,`error_msg`,`uploaded_by`,`record_seq_no`) VALUES"
					    + "("+fileSeq+",'Fail',\""+stacktraceString+"\",0,1); "				);
			}else{
				jdbcTemplate.update(
					    "INSERT INTO `hero_stock_product_import_status`"
					    + " (`upload_seq_no`,`upload_status`,`error_msg`,`uploaded_by`,`record_seq_no`) VALUES"
					    + "("+fileSeq+",'Fail',\"null occured\",0,1); "				);
			}
			
          error_log.info(e);
          
          e.printStackTrace();
			//throw e;
		}
	}

	
	
	@Override
	public HERO_STK_RESPONSEINFO savedishtype(String productData) {
		// TODO Auto-generated method stub
		try
		{
		 log.info("productData   "+productData);
		 HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(productData, 
		 "com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");	
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_DISHTYPE_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_DISH_ID", request.getDishid());
		inParamMap.put("P_DISHTYPE_NAME", request.getDishtypename());	
		inParamMap.put("P_DISHTYPE_ID", request.getDishtypeid());
		inParamMap.put("P_STATUS", request.getStatus());		
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);	
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO dishtypelist() {
	
		String query = " SELECT a.`dish_id`,`dish_name`,`dishtype_id`,`dishtype_name`,a.`status` "
				+ " FROM `hero_stock_dish_type`a JOIN `hero_stock_dishes`b ON b.`dish_id`=a.`dish_id` ORDER BY  a.`dish_id` DESC";
		
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dishid", rs.getString("dish_id"));			
				map.put("dishname", rs.getString("dish_name"));				
				map.put("dishtypeid", rs.getString("dishtype_id"));	
				map.put("dishtypename", rs.getString("dishtype_name"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);				
				String statusDesign ="";
				if(rs.getString("status").equals("1")){
					 statusDesign = "<span style='color:#519C37;font-weight:bold'>Active</span>";
				}else{
					 statusDesign = "<span style='color:#c75757;font-weight:bold'>In Active</span>";
				}

				map.put("status", statusDesign);
				map.put("statusid", rs.getString("status"));	
				
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
	public HERO_STK_RESPONSEINFO savedish(String productData) {
		// TODO Auto-generated method stub
		try
		{
		 log.info("productData   "+productData);
		 HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(productData, 
		 "com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");	
		
		 SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_DISH_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_DISH_ID", request.getDishid());
			inParamMap.put("P_DISH_NAME", request.getDishname());
			inParamMap.put("P_STATUS", request.getStatus());
			inParamMap.put("P_OPRN", request.getOprn());
			log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);	
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO dishlist() {
	
		String query = " SELECT `dish_id`,`dish_name`,`status`,CASE WHEN `status`=1 THEN 'Active ' ELSE 'InActive' END AS statusdesc "
				+ " FROM `hero_stock_dishes` ORDER BY  `dish_id` DESC";
		
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("dishid", rs.getString("dish_id"));			
				map.put("dishname", rs.getString("dish_name"));		
				map.put("status", rs.getString("status"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);		
				
				String statusDesign ="";
				if(rs.getString("status").equals("1")){
					 statusDesign = "<span style='color:#519C37;font-weight:bold'>Active</span>";
				}else{
					 statusDesign = "<span style='color:#c75757;font-weight:bold'>In Active</span>";
				}

				map.put("statusdesc", statusDesign);
			
				
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
	public HERO_STK_RESPONSEINFO saveterms(String productData) {
		// TODO Auto-generated method stub
		try
		{
			
		HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(productData, 
				"com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");
		
		log.info("Values are     "+request.getTermsname()+"   "+request.getTermsdesc()+"  " );
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_TERMS_AND_CONDITIONS_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_DESC", request.getTermsname());//0
		inParamMap.put("P_TERMS_ID", request.getTermsid());//0
		inParamMap.put("P_TERMS_AND_DESC", request.getTermsdesc());//1
		inParamMap.put("P_USER_ID", request.getUserid());//11
		inParamMap.put("P_OPRN", request.getOprn());//12
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO termslist() {
	
		String query = " SELECT `terms_id`,`terms_desc`,`terms_descriptions` FROM `hero_terms_conditions`";
		
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("terms_id", rs.getString("terms_id"));			
				map.put("terms_desc", rs.getString("terms_desc"));		
				map.put("terms_descriptions", rs.getString("terms_descriptions"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
			
				
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
	public HERO_STK_RESPONSEINFO savecompanymaster(String productData,HttpServletRequest request1) {
		// TODO Auto-generated method stub
		try
		{
			
		HERO_STK_SERVC_PRODUCTREQUEST request = (HERO_STK_SERVC_PRODUCTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(productData, 
				"com.hero.stock.forms.transactions.product.HERO_STK_SERVC_PRODUCTREQUEST");
		
		log.info("Values are     "+request.getTermsname()+"   "+request.getTermsdesc()+"  " );
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_CLIENTS_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_COMPANY_ID", request.getCompanymasterid());
		inParamMap.put("P_COMPANY_NAME", request.getCompanymastername());
		inParamMap.put("P_COMPANY_PHONE", request.getCompanymasterphone());		
		inParamMap.put("P_COMPANY_EMAIL", request.getCompanymasteremail());
		inParamMap.put("P_COMPANY_CONTACT", request.getCompanymastercontactname());
		inParamMap.put("P_COMPANY_ADDRESS", request.getCompanymasteraddress());		
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_OPRN", request.getOprn());
		inParamMap.put("P_COMPANY_LOGO", request.getOrgnlogo());
		inParamMap.put("P_GSTIN_NO", request.getGstinno());
		inParamMap.put("P_BREAKFAST_RATE", request.getBreakfastrate());
		inParamMap.put("P_LUNCH_RATE", request.getLunchrate());
		inParamMap.put("P_DINNER_RATE", request.getDinnerrate());
		inParamMap.put("P_SUPPER_RATE", request.getSupperrate());
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		if(Integer.valueOf((String) resultMap.get("out_genrate_id")) > 0 && !request.getOrgnlogo().equals("")){
			String serverPath = request1.getRealPath("/");
			File dir = new File(serverPath);
			dir = dir.getParentFile();
			serverPath = dir.getAbsolutePath();
			System.out.println("Directory   "+serverPath);
			serverPath = serverPath+File.separator+"customermaster";
			 File file = new File(serverPath+File.separator+request.getOrgnlogo());
			 String fileName = file.toString();

		      int index = fileName.lastIndexOf('.');
		      if(index > 0) {
		          String extension = fileName.substring(index + 1);
		          File rename = new File(serverPath+File.separator+resultMap.get("out_genrate_id")+"."+extension);
		          file.renameTo(rename);
		          System.out.println(fileName + "\t" + extension);
		      	SimpleJdbcCall jdbcCALL1 = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_CLIENTS_MASTER");
				Map<String, Object> inParamMap1 = new HashMap<String, Object>();
				inParamMap1.put("P_COMPANY_ID", resultMap.get("out_genrate_id"));
				inParamMap1.put("P_COMPANY_NAME", request.getCompanymastername());
				inParamMap1.put("P_COMPANY_PHONE", request.getCompanymasterphone());		
				inParamMap1.put("P_COMPANY_EMAIL", request.getCompanymasteremail());
				inParamMap1.put("P_COMPANY_CONTACT", request.getCompanymastercontactname());
				inParamMap1.put("P_COMPANY_ADDRESS", request.getCompanymasteraddress());		
				inParamMap1.put("P_USER_ID", request.getUserid());
				inParamMap1.put("P_OPRN", "UPD_LOGO");
				inParamMap1.put("P_COMPANY_LOGO", resultMap.get("out_genrate_id")+"."+extension);
				inParamMap1.put("P_GSTIN_NO", request.getGstinno());
				inParamMap1.put("P_BREAKFAST_RATE", request.getBreakfastrate());
				inParamMap1.put("P_LUNCH_RATE", request.getLunchrate());
				inParamMap1.put("P_DINNER_RATE", request.getDinnerrate());
				inParamMap1.put("P_SUPPER_RATE", request.getSupperrate());
				
				log.info("inParamMap         "+inParamMap1);
				
				SqlParameterSource in1 = new MapSqlParameterSource(inParamMap1);
			 jdbcCALL1.execute(in1);
		        }
			
			
		}
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
            error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO companymasterlist() {
	
		String query = " SELECT `companyid`,`companyname`,`contactperson`,`emailid`,`phone`,`address`,"
				+ "`logo`,`gstin_no`,`breakfast_rate`,`lunch_rate`,`dinner_rate`,`supper_rate`"
				+ " FROM `hero_stock_client_company` ORDER BY `companyid` DESC";
		
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("companyid", rs.getString("companyid"));			
				map.put("companyname", rs.getString("companyname"));		
				map.put("contactperson", rs.getString("contactperson"));
				map.put("emailid", rs.getString("emailid"));			
				map.put("phone", rs.getString("phone"));		
				map.put("address", rs.getString("address"));
				map.put("orgnlogo", rs.getString("logo"));
				map.put("gstinno", rs.getString("gstin_no"));
				map.put("breakfastrate", rs.getString("breakfast_rate"));
				map.put("lunchRate", rs.getString("lunch_rate"));
				map.put("dinnerRate", rs.getString("dinner_rate"));
				map.put("supperRate", rs.getString("supper_rate"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("addmenu", "<button  style='border:1px solid lightgray;background: #e1dede;'onclick=\"addcustomermenu("+rs.getString("companyid")+")\"> <i style='color: #6259ca !important;'class=\"fa fa-plus\"></i> </button>");
				
				return map;
			}
		});
		log.info(productList);	
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
}
