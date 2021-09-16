package com.hero.stock.lov;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_IADDPURCHASEORDERDAO;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;
import com.hero.stock.util.HERO_STK_INVENTORYLOV;

public class HERO_STK_INVENTORYLOVIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_IINVENTORYLOV {
	private static Logger log = Logger.getLogger(HERO_STK_INVENTORYLOVIMPL.class);
	@Override
	public List<Object> getLOVList(String query) {
		// TODO Auto-generated method stub
		log.info(query);
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HERO_STK_INVENTORYLOV lov = new HERO_STK_INVENTORYLOV();
				  lov.setLabel(rs.getString(2));
				  lov.setValue(rs.getString(1));
				  lov.setIndex(index);
				  
				  index++;
				  
				  return lov;
			}
		});
		if(LOVList != null && LOVList.size() == 0)
		{
			LOVList = setEmptyList();
		}
		return LOVList;
	}
	
	@Override
	public List<Object> getLOVList1(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HERO_STK_INVENTORYLOV lov = new HERO_STK_INVENTORYLOV();
				  lov.setLabel(rs.getString(2));
				  lov.setValue(rs.getString(1));
				  lov.setIndex(index);
				  
				  index++;
				  
				  return lov;
			}
		});
		if(LOVList != null && LOVList.size() == 0)
		{
			//LOVList = setEmptyList();
		}
		return LOVList;
	}
	
	@Override
	public List<Object> getLOVListwithoutemptylist(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HERO_STK_INVENTORYLOV lov = new HERO_STK_INVENTORYLOV();
				  lov.setLabel(rs.getString(2));
				  lov.setValue(rs.getString(1));
				  lov.setIndex(index);
				  
				  index++;
				  
				  return lov;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> setEmptyList() {
		// TODO Auto-generated method stub
		List<Object> LOVList = new ArrayList<Object>();
		HERO_STK_INVENTORYLOV lov = new HERO_STK_INVENTORYLOV();
		lov.setLabel("--Select--");
		lov.setValue("0");
		LOVList.add(lov);
		return LOVList;
	}


	@Override
	public List<Object> getViewPurchaseList(String stocktransferviewQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(stocktransferviewQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("purchasecode", rs.getString("purchase_code"));
				  //map.put("createddate", rs.getString("created_date"));
				  map.put("totalamt", rs.getString("total_amt"));
				  map.put("suppliername", rs.getString("supplier_name"));
				  map.put("purchasedate", rs.getString("purchase_date"));
				  map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
				  map.put("purchaseid", rs.getString("purchase_id"));
				  map.put("headerid", rs.getString("prhdr_id"));
/*				  map.put("referenceno", rs.getString("referenceno"));*/
				  /*map.put("grandamount", rs.getString("grandamount"));*/
				  map.put("statuscode", rs.getString("purchase_status"));
				  map.put("receive_status", rs.getString("receive_status"));
				  map.put("receivestatusdesc", rs.getString("recvstats_desc"));
				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}
	
	
	@Override
	public List<Object> getViewPurchaseRequestList(String stocktransferviewQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(stocktransferviewQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("purchasecode", rs.getString("purchase_code"));				 
				  map.put("purchasedate", rs.getString("purchase_date"));
				  map.put("purchaseid", rs.getString("purchase_id"));
				  map.put("purchasestatus", rs.getString("ps_status_name"));			
				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}
	
	@Override
	public List<Object> getStockTransferOrderList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("transferid", rs.getString("transfer_id"));
				  map.put("transferno", rs.getString("transfer_no"));
				  map.put("deliverystatus", rs.getString("delivery_status"));
				  map.put("status_desc", rs.getString("status_desc"));
				  map.put("storename", rs.getString("store_name"));
				  map.put("address", rs.getString("address"));
				  map.put("city", rs.getString("city"));
				  map.put("zipcode", rs.getString("zipcode"));
				  map.put("state", rs.getString("state"));
				  map.put("country", rs.getString("country"));
				  map.put("phone", rs.getString("phone"));
				  
				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}

	@Override
	public List<Object> getViewReceiveStockList(String receivestockQuery,final HERO_STK_IADDPURCHASEORDERDAO addpurchaseorderDAOobj) {
		// TODO Auto-generated method stub
		/*String query = "SELECT purchase_code,product_id,product_name,unit,pur_quantity,prec_quantity,free_product_count,prec_batchno,"
				+ "DATE_FORMAT(prec_expiry_date,'%e/%c/%Y')prec_expiry_date,prec_purchase_price,prec_sales_price FROM hero_stock_product b,hero_stock_purchase_request c,"
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_stock_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
				+ "AND a.purchase_code = c.pur_req_id AND b.product_id = c.pur_product_id AND b.unit_type_id = d.unit_type_id";*/
		  Random rand = new Random();
        
	      int minRange = 10000, maxRange= 500000;
	      final int randomNumber = rand.nextInt(maxRange - minRange) + minRange;
	      
		@SuppressWarnings("unchecked")
		List<Object> receivestockList = jdbcTemplate.query(receivestockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				//map.put("orderedfirstqty", rs.getString("orderedfirstqty"));
				map.put("purchaseorderno", rs.getString("purchase_code"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("unit", rs.getString("unit"));
				map.put("purquantity", rs.getString("pur_quantity"));
				map.put("receivingqty", (rs.getString("prec_recving_quantity") == null ) ? 0 : rs.getString("prec_recving_quantity"));
				map.put("bonusqty", rs.getString("prec_free_count"));
				
				/*map.put("batchno", rs.getString("prec_batchno"));
				map.put("barcode", rs.getString("prec_barcode"));*/
			    map.put("batchno", randomNumber);
				map.put("barcode", randomNumber);
				
				map.put("totalamountdisp", rs.getString("prhdr_total_amt_disp"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("purchaseprice", rs.getString("prec_pur_price"));
				map.put("taxper", rs.getString("prec_tax_per"));
				map.put("inorex", rs.getString("prhdr_inclusive"));
				map.put("salesprice", rs.getString("prec_sel_price"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("subtotal", rs.getString("prec_sub_total"));
				map.put("receivedate", rs.getString("prhdr_recv_date"));
				map.put("discounttype", rs.getString("prhdr_discount_type"));
				map.put("discountvalue", rs.getString("prhdr_discount_value"));
				map.put("orderedtax", rs.getInt("prhdr_tax_id"));
				map.put("shippingcost", rs.getString("prhdr_shipping_cost"));
				map.put("shippingcostdisp", rs.getString("prhdr_shipping_cost_disp"));
				map.put("notes", rs.getString("prhdr_notes"));
				map.put("totalamount", rs.getString("prhdr_total_amt"));
				map.put("taxamount", rs.getString("prhdr_tax_amt"));
				map.put("discountamount", rs.getString("prhdr_discount_amt"));
				map.put("grandtotalamount", rs.getString("prhdr_grand_total_amt"));
				map.put("totalwithdiscamount", rs.getString("prhdr_total_with_disc"));
				map.put("prhdr_discount_type", rs.getString("prhdr_discount_type"));
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("index", index);
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("opttype", rs.getString("opttype"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("receivedqty", rs.getString("RECVDQTY"));
				map.put("netunitdesc", rs.getString("netunitdesc"));
				map.put("pendingqty", Integer.valueOf((int) (rs.getFloat("pur_quantity")-rs.getFloat("RECVDQTY"))));
				 map.put("tax", rs.getString("taxlabel1"));
					map.put("tax2", rs.getString("taxlabel2"));
					
				if(rs.getString("prhdr_id") != null)
				map.put("headerid", rs.getString("prhdr_id"));
				else
				map.put("headerid", 0);
				
				if(rs.getInt("prhdr_id") == 0)
				map.put("oprn", "INS");
				else
				map.put("oprn", "UPD");
				
				map.put("orderedfulluom", rs.getString("orderedfulluom"));
				map.put("orderedfullqty", rs.getString("orderedfullqty"));
				map.put("orderedlooseuom", rs.getString("orderedlooseuom"));
				map.put("orderedlooseqty", rs.getString("orderedlooseqty"));
				
				String ordereduompackingid = rs.getString("ordereduompackingid");
				map.put("ordereduompackingid", ordereduompackingid);
				log.info("ordereduompackingid  "+ordereduompackingid);
				
				map.put("receivedfulluom", rs.getString("receivedfulluom"));
				map.put("receivedfullqty", rs.getString("prec_full_qty"));
				map.put("receivedlooseuom", rs.getString("receivedlooseuom"));
				map.put("receivedlooseqty", rs.getString("prec_loose_qty"));
				
				map.put("purcgst", rs.getString("pur_cgst"));
				map.put("pursgst", rs.getString("pur_sgst"));

				
				HERO_STK_RESPONSEINFO responseInfo = addpurchaseorderDAOobj.getuomforpacking(ordereduompackingid);
				HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
				log.info(response.getResponseObj());
				map.put("uomsel", response.getResponseObj());
				
				index++;
				
				return map;
			}
		});
		log.info(receivestockList);
		
		return receivestockList;
	}


	@Override
	public List<Object> getStockList(String stockQuery) {
		// TODO Auto-generated method stub
		
@SuppressWarnings("unchecked")
List<Object> stockList = jdbcTemplate.query(stockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("productcount", rs.getString("product_count"));
				map.put("unit", rs.getString("unit"));
				map.put("index", index);
				map.put("categoryname", rs.getString("category_name"));
				/*map.put("batchno", rs.getString("batch_id"));
				map.put("categoryname", rs.getString("category_name"));
				map.put("stockid", rs.getString("stock_id"));
				map.put("sellprice", rs.getString("sell_price"));*/
				
				index++;
				
				return map;
			}
		});
		log.info("stockList      "+stockList);
		
		return stockList;
	}


	@Override
	public List<Object> getTransferList(String stockTxrQuery) {
		// TODO Auto-generated method stub
		/*String query = "SELECT purchase_code,product_id,product_name,unit,pur_quantity,prec_quantity,free_product_count,prec_batchno,"
				+ "DATE_FORMAT(prec_expiry_date,'%e/%c/%Y')prec_expiry_date,prec_purchase_price,prec_sales_price FROM hero_stock_product b,hero_stock_purchase_request c,"
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_stock_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
				+ "AND a.purchase_code = c.pur_req_id AND b.product_id = c.pur_product_id AND b.unit_type_id = d.unit_type_id";*/
		
		@SuppressWarnings("unchecked")
		List<Object> receivestockList = jdbcTemplate.query(stockTxrQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				/*map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("batchid", rs.getString("batch_id"));
				map.put("sourcecount", rs.getString("sourcecount"));
				map.put("destcount", rs.getString("destcount"));
				map.put("toberecvdcount", rs.getString("tobe_recvd_prod_count"));
				map.put("unitprice", rs.getString("sell_price"));
				map.put("index", index);*/
				index++;
				map.put("tprid",rs.getString("transfer_id"));
				map.put("productid", rs.getString("product_id"));
				map.put("transferid",rs.getString("transfer_id"));
				map.put("transferno",rs.getString("transfer_no"));
				map.put("productcount", rs.getString("tobe_recvd_prod_count"));
				map.put("productname", rs.getString("product_name"));
				map.put("batchid", rs.getString("batch_id"));
				map.put("sourcecount", rs.getString("sourcecount"));
				map.put("destcount", rs.getString("destcount"));
				map.put("unitprice", rs.getString("sell_price"));
				map.put("index", index);
				
				if(rs.getString("transfer_id") != null)
				map.put("transferid", rs.getString("transfer_id"));
				else
				map.put("transferid", 0);
				
				if(rs.getInt("transfer_id") == 0)
				map.put("oprn", "INS");
				else
				map.put("oprn", "UPD");
				
				
				
				return map;
			}
		});
		log.info(receivestockList);
		
		return receivestockList;
	}


	@Override
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery) {
		// TODO Auto-generated method stub
		log.info("getUsermenuList   ");

		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(userMenuQuery, new RowMapper() {
			int check1 = 0;
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					check1 = check1 + 1;
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("property", "parent"+check1);
					map.put("style", "style='background-color: #f4f4f4;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("property", "child"+check1);
					map.put("style", "");
					
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				
				index++;
				return map;
			}
		});
		log.info("First usermenuList       "+usermenuList);
		
		if(usermenuList != null && usermenuList.size() == 0)
		{
			@SuppressWarnings("unchecked")
			List<Object> usermenuList1 = jdbcTemplate.query(menuQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("moduleid",rs.getString("moduleid"));
					map.put("modulename", rs.getString("modulename"));
					map.put("issubmodule",rs.getString("issubmodule"));
					map.put("parentid",rs.getString("parentid"));
					map.put("index", index);
					
					if(rs.getInt("issubmodule") == 0)
					{
						
						map.put("mainmenudisp", "mainmenudisp");
						map.put("submenudisp", "mainmenuhidedisp");
						map.put("space", "");
						map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
					}
					else
					{
						map.put("mainmenudisp", "mainmenuhidedisp");
						map.put("submenudisp", "mainmenudisp");
						map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						map.put("style", "");
						
					}
					
					map.put("menudetails","1$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid"));
					
					index++;
					return map;
				}
			});
			
			usermenuList = usermenuList1;
		}
		
		log.info("Second usermenuList       "+usermenuList);

		return usermenuList;
	}


	@Override
	public List<Object> getUserdetails(String userDetailQuery) {
		
		@SuppressWarnings("unchecked")
		List<Object> userDetailList = jdbcTemplate.query(userDetailQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				map.put("username", rs.getString("username"));
				map.put("usertype", rs.getString("role"));
				map.put("user_name", rs.getString("user_name"));
				
				return map;
			}
		});
		log.info(userDetailList);
		
		return userDetailList;
	}
	
	@Override
	public List<Object> refnoList(String referencenoquery) {
		
		@SuppressWarnings("unchecked")
		List<Object> referencenolist = jdbcTemplate.query(referencenoquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				map.put("refno", rs.getString("refno"));
				
				
				return map;
			}
		});
		log.info(referencenolist);
		
		return referencenolist;
	}
	
	
	@Override
	/*public List<Object> getMenuList(String menuQuery) {*/
	public Map<String, Object> getMenuList(String menuQuery) {
		// TODO Auto-generated method stub
		log.info("getMenuList   ");
		Map<String, Object> menuMap = new HashMap<String, Object>();
		List<String> accessURLS = new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(menuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				/*log.info("rs.getString(fafafont)        "+rs.getString("fafafont"));*/
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("modulepath",rs.getString("modulepath"));
				map.put("parentid",rs.getString("parentid"));
				map.put("fafafont",rs.getString("fafafont"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				
				String submenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`modulepath`"
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
							+ "WHERE `user_type_id` = "+rs.getString("user_type_id")+" AND parentid = "+rs.getString("moduleid")
							+" AND menu_access = 1 AND `status` = '1' ORDER BY `userid`,`moduleid` ASC";
					
				  
				log.info("submenu query  "+submenuQuery);  
				
				  List<Object> submenuList = new ArrayList<Object>();
				  submenuList = getSubMenuList(submenuQuery);
				  map.put("submenuList", submenuList);
				
				index++;
				return map;
			}
		});
		/*log.info("First usermenuList       "+usermenuList);*/
		
		String accessURLQuery = "SELECT `modulepath` FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON "
				+ "A.`moduleid` = B.`moduleid` WHERE `user_type_id` = 2 AND menu_access = 1 AND `status` = '1'  AND LENGTH(modulepath) > 0 "
				+ "ORDER BY `userid`,A.`moduleid` ASC";
		@SuppressWarnings("unchecked")
		List<Object> accessURLQueryList = jdbcTemplate.query(accessURLQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				/*log.info("rs.getString(fafafont)        "+rs.getString("fafafont"));*/
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("url", rs.getString("modulepath"));
				return map;
			}
		});
		log.info("accessURLQueryList   "+accessURLQueryList);
		Iterator<Object> accessURLQueryListITR = accessURLQueryList.iterator();
		while(accessURLQueryListITR.hasNext())
		{
			Map<String, Object> accessURLmap = (Map<String, Object>) accessURLQueryListITR.next();
			accessURLS.add((String) accessURLmap.get("url"));
		}
		log.info("accessURLS   "+accessURLS);
		
		menuMap.put("usermenuList", usermenuList);
		menuMap.put("accessURLS", accessURLS);
		/*return usermenuList;*/
		return menuMap;
	}

	
	public List<Object> getSubMenuList(String submenuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> submenuList = jdbcTemplate.query(submenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				map.put("modulepath", rs.getString("modulepath"));
				
				index++;
				return map;
			}
		});
		/*log.info("First submenuList       "+submenuList);*/
		
		return submenuList;
	}


	@Override
	public List<Object> getBarcodeStoreList(String storeQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(storeQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("value", rs.getString("store_id"));
				  map.put("label", rs.getString("store_name"));
				  map.put("currencysymbol", rs.getString("CURR_SYMBOL"));

				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}
	
	@Override
	public List<Object> getPOSDiscountList(String taxQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(taxQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("value", rs.getString("tax_id"));
				  map.put("label", rs.getString("TAX_NAME"));
				  map.put("taxrate", rs.getString("TAX_RATE"));

				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getSMSTemplateList(String smsTemplateQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(smsTemplateQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("templateid", rs.getInt("sms_temp_id"));
				  map.put("templatename", rs.getString("sms_temp_name"));
				  map.put("msgcontent", rs.getString("sms_message_content"));
				  
				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getLowStockList(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		List<Object> lowStockList = new ArrayList<Object>();
		
		try
		{
		
			StringBuilder lowStockSB = new StringBuilder();
			int userType = (int)httpRequest.getSession().getAttribute("usertype");
			
			if(userType <= 2)
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
						+ "'Godown' store_name,0 pharmacy_id FROM `hero_stock` A JOIN `hero_stock_product` D ON D.`product_id` = A.`product_id` "
						+ "LEFT JOIN `hero_admin_company` B ON D.`manufacturer_id` = B.`company_id`"
						+ "WHERE  D.`status_id` = 1 AND `alert_count` > 0 GROUP BY A.`product_id` order by pharmacy_id,product_id");
			}
			else
			{
				lowStockSB.append("SELECT B.`product_id` product_id,coalesce(SUM(`product_count`),0)product_count,coalesce(`alert_count`,0)alert_count,"
						+ "CONCAT(product_name,' - ',`company_name`)product_name,`store_name`,pharmacy_id  "
						+ "FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
						+ "JOIN `hero_user` c ON c. `user_store_id` = A.`pharmacy_id` "
						+ "JOIN `hero_stock_product` D ON D.`product_id` = B.`product_id` "
						+ "LEFT JOIN `hero_stock_store` e ON e.`store_id` = A.`pharmacy_id` "
						+ "LEFT JOIN `hero_admin_company` f ON f.`company_id` = D.`manufacturer_id`"
						+ "WHERE `userid` = "+httpRequest.getSession().getAttribute("uid")+
						" AND D.`status_id` = 1 AND `alert_count` > 0 "
						+ "GROUP BY B.`product_id`,`pharmacy_id`");
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
		log.info("lowStockSB   "+lowStockSB.toString());
		
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
		
		httpRequest.getSession().setAttribute("lowstockcount", lowStockList.size());
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return lowStockList;
	}


	@Override
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		HttpSession session = httpRequest.getSession();
		/*int usertype = (int)session.getAttribute("usertype");
		int storeid = (int) session.getAttribute("storeid");*/
	    /*int userid=(int) session.getAttribute("uid");*/
		int usertype = 0,storeid = 0;
		if(session.getAttribute("usertype") instanceof Integer)
		{
			usertype = (int)session.getAttribute("usertype");
		}
		else
		{
			String usertypeSTR = (String)session.getAttribute("usertype");
			usertype = Integer.parseInt(usertypeSTR);
		}
		if(session.getAttribute("storeid") instanceof Integer)
		{
			storeid = (int) session.getAttribute("storeid");
			
		}
		
		else
		{
			String storeidSTR = String.valueOf(session.getAttribute("storeid"));
			storeid = Integer.parseInt(storeidSTR);
		}
		String orderQuery="SELECT `pos_code`,`pos_status_desc` FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_status` b ON a.`pos_order_status_id`=b.`pos_status_id`"
							+"ORDER BY `pos_id` DESC LIMIT 5";
		String receivestockQuery="SELECT  `product_name`,`prec_recving_quantity` FROM `hero_stock_product` a JOIN `hero_stock_purchase_received_dtl` b"
					+ " ON a.`product_id`=b.`prec_product_id`  ORDER BY `prec_dtl_id` DESC LIMIT 5";
		String receivestockcountQuery="SELECT  COUNT(*) FROM `hero_stock_purchase_received_dtl`";
		String ordercountQuery="SELECT COUNT(*) FROM `hero_stock_pos_summary`";
		String lowstockQuery="SELECT  B.`product_id` product_id,COALESCE(SUM(`product_count`),0)product_count, product_name"
							+ " FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
							+ " JOIN `hero_stock_product` D ON D.`product_id` = B.`product_id` ORDER BY B.`product_id` DESC LIMIT 5";
		String productCountQuery = "SELECT COUNT(*) FROM `hero_stock_product` ";
		/*String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier` WHERE `status_id` = 1";*/
		String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier` WHERE `user_id`="+ session.getAttribute("uid");
		String storeCountQuery = "SELECT COUNT(*) FROM `hero_stock_store` WHERE `status_id` = 1";
		String userCountQuery = "SELECT COUNT(*) FROM `hero_user` WHERE `status` = 1";
		String customerCountQuery = "SELECT COUNT(*) FROM `hero_admin_customer`";
		String purchaseCountQuery ="SELECT COUNT(*) FROM `hero_stock_purchase`";
		String transfersCountQuery="SELECT COUNT(*) FROM `hero_stock_transfer`";
		//String posCountQuery = "SELECT COUNT(*) FROM `hero_stock_pos_order_items`";
		String productRecentQuery ="SELECT `product_name`,`alert_count` FROM `hero_stock_product` ORDER BY `product_id` DESC LIMIT 5 ";
		String purchaseAmountQuery = "SELECT SUM(`prhdr_grand_total_amt`) FROM `hero_stock_purchase_receive_hdr` WHERE `prhdr_created_date` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";
		/*String salesAmountQuery = "SELECT SUM(`pos_netamount`) FROM `hero_stock_pos_summary` WHERE `created_at` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";*/
		String purchaserecentQuery="SELECT `purchase_code`,`supplier_first_name`,`ps_status_name` FROM `hero_stock_purchase` a "
				+ "JOIN `hero_stock_supplier` b  ON a.`supplier_id` = b.`supplier_id` JOIN `hero_stock_purchase_status` c ON a.`receive_status` = c.`ps_id` ORDER BY `purchase_id` DESC LIMIT 5";
		
		String customerDueQuery ="";
		if(usertype == 1)
		{
		customerDueQuery = "  SELECT SUM(b.`CURR_EXCHNG_RATE`*(`pos_balance_amount` * -1))pos_balance_amount FROM `hero_stock_pos_summary` a"
									+" JOIN `hero_admin_currency` b ON b.`CURR_SYMBOL` = a.`currency`";
		}else{
			customerDueQuery = "  SELECT SUM(`pos_balance_amount` * -1)pos_balance_amount FROM `hero_stock_pos_summary` "
					+" WHERE `pos_store_id` = '"+storeid+"'";
		}
		log.info("customerDueQuery  "+customerDueQuery);
		
		String salesAmountQuery = "";
		if(usertype == 1)
		{
		salesAmountQuery = "SELECT SUM(b.`CURR_EXCHNG_RATE`*a.`pos_netamount`) FROM `hero_stock_pos_summary` a JOIN "
				+"`hero_admin_currency` b ON b.`CURR_SYMBOL` = a.`currency` WHERE a.`created_at` BETWEEN "
				+"DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";
		}else{
		salesAmountQuery = "SELECT SUM(`pos_netamount`) FROM `hero_stock_pos_summary` WHERE `pos_store_id` = '"+storeid+"' AND `created_at` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";
		}
		String transferQuery="SELECT `transfer_no`,`status_desc` FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` b "
                            +"ON b.`status_id`=a.`status_id`"
                            +"ORDER BY `transfer_id` DESC LIMIT 5";
		String recentBillsQuery = "SELECT (`purchase_code`) as purchasecode,CONCAT(`supplier_first_name`,`supplier_last_name`)suppliername,"
				+ "SUM(`prhdr_grand_total_amt`)totalamount,DATE_FORMAT(`prhdr_created_date`,'%c/%e/%y')purchaseddate,`ps_status_name` as statusdesc "
				+ "FROM `hero_stock_purchase` a JOIN `hero_stock_supplier` b ON a.`supplier_id` = b.`supplier_id` "
				+ "JOIN `hero_stock_purchase_receive_hdr` c ON a.`purchase_code` = c.`pur_req_id` "
				+ "JOIN  `hero_stock_purchase_status` d ON d.`ps_id` = c.`prhdr_paid_status` GROUP BY `purchase_code` ORDER BY `purchase_id` DESC LIMIT 0,10";
		
		StringBuilder recentTransfersQuerySB = new StringBuilder();
		recentTransfersQuerySB.append("SELECT `transfer_no` ,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,"
									+" `status_desc` ,`store_name`  FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C ON a.`delivery_status` = C.`status_id`" 
									+" LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id` ");	
		
		if(usertype > 2)
		{
			recentTransfersQuerySB.append(" WHERE `store_id`='"+session.getAttribute("storeid")+"'");
		}
		recentTransfersQuerySB.append(" ORDER BY `transfer_id` DESC LIMIT 5");
		
		
		log.info("recentTransfersQuery  "+recentTransfersQuerySB);
		
		StringBuilder ordersQuerySB = new StringBuilder();
		ordersQuerySB.append("SELECT `poo_prod_id`,`product_name`,SUM(`poo_sales_count`)ordercount,CONCAT(`unit_quantity`,' ',`unit`)uom,`company_name` "
				+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_order_items` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`poo_prod_id` = c.`product_id` "
				+ "LEFT JOIN `hero_admin_unit_type` d ON c.`unit_type_id` = d.`unit_type_id` LEFT JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` "
				+ "WHERE `pos_order_status_id` = 5 ");
		/*if(usertype != null && usertype.equals("3"))*/
		if(usertype > 2)
		{
			ordersQuerySB.append("and `pos_store_id` = "+session.getAttribute("storeid"));
		}
		ordersQuerySB.append("  GROUP BY `poo_prod_id`");
		 
		String topSellingProductQuery = "SELECT `pos_prod_id`,`product_name`,SUM(`pos_sales_count`)salescount,`company_name` "
				+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`pos_prod_id` = c.`product_id` "
				+ "LEFT JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` GROUP BY `pos_prod_id` LIMIT 0,10";
		
		log.info("recentBillsQuery   "+recentBillsQuery);
		log.info("ordersQuerySB   "+ordersQuerySB.toString());
		log.info("topSellingProductQuery   "+topSellingProductQuery);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> productCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(productCountQuery, jdbcTemplate);
		List<Object> receivestockCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(receivestockcountQuery, jdbcTemplate);
		List<Object> ordercount = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(ordercountQuery, jdbcTemplate);
		//List<Object> posCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(posCountQuery, jdbcTemplate);
		List<Object> transfersCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(transfersCountQuery, jdbcTemplate);
		List<Object> supplierCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(supplierCountQuery, jdbcTemplate);
		List<Object> storeCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(storeCountQuery, jdbcTemplate);
		List<Object> userCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(userCountQuery, jdbcTemplate);
		List<Object> customerCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(customerCountQuery, jdbcTemplate);
		List<Object> purchaseAmountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(purchaseAmountQuery, jdbcTemplate);
		List<Object> salesAmountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(salesAmountQuery, jdbcTemplate);
		List<Object> recentBillsList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(recentBillsQuery, jdbcTemplate);
		List<Object> ordersList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(ordersQuerySB.toString(), jdbcTemplate);
		List<Object> topSellingProductList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(topSellingProductQuery, jdbcTemplate);
		List<Object> recentTransfersList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(recentTransfersQuerySB.toString(), jdbcTemplate);
		List<Object> customerDueList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(customerDueQuery, jdbcTemplate);
		List<Object> purchaseCountList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQuery(purchaseCountQuery, jdbcTemplate);
		List<Object> purchaserecentList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(purchaserecentQuery, jdbcTemplate);
		List<Object> transferList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(transferQuery, jdbcTemplate);
		List<Object> productRecentList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(productRecentQuery, jdbcTemplate);
		List<Object> lowstockList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(lowstockQuery, jdbcTemplate);
		List<Object> orderList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(orderQuery, jdbcTemplate);
		List<Object> receivestockList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(receivestockQuery, jdbcTemplate);
		log.info("recentBillsList   "+recentBillsList);
		
		map.put("productCount", productCountList);
		map.put("order", orderList);
		map.put("ordercount", ordercount);
		map.put("transfersCount", transfersCountList);
		map.put("supplierCount", supplierCountList);
		map.put("storeCount", storeCountList);
		map.put("userCount", userCountList);
		map.put("customerCount", customerCountList);
		map.put("purchaseAmount", purchaseAmountList);
		map.put("salesAmount", salesAmountList);
		map.put("customerDue", customerDueList);
		map.put("recentBills", recentBillsList);
		map.put("ordersList", ordersList);
		map.put("topSellingProductList", topSellingProductList);
		map.put("recentTransfers", recentTransfersList);
		map.put("purchaseCount", purchaseCountList);
		map.put("purchaserecent", purchaserecentList);
		map.put("recentTransfers", recentTransfersList);
		map.put("transfer", transferList);
		map.put("productRecent", productRecentList);
		map.put("lowstock", lowstockList);
		map.put("ordercount", ordercount);
		map.put("receivestock", receivestockList);
		map.put("receivestockcount", receivestockCountList);
		
		
		return map;
	}


	@Override
	public List<Object> getTaxList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				  if(rs.getString(4) != null && rs.getString(4).equalsIgnoreCase("P"))
				  {
					  map.put("label", rs.getString(2).concat(" ( "+rs.getString(3)+" % ) "));  
				  }
				  else if(rs.getString(4) != null && rs.getString(4).equals("F"))
				  {
					  map.put("label", rs.getString(2).concat(" ( Rs. "+rs.getString(3)+" ) "));
				  }
				  
				  map.put("value", rs.getString(1));
				  map.put("index", index);
				  map.put("taxrate", rs.getString(3));
				  map.put("taxtype", rs.getString(4));
				  index++;
				  
				  return map;
			}
		});
		if(LOVList != null && LOVList.size() == 0)
		{
			LOVList = setEmptyList();
		}
		return LOVList;
	}


	@Override
	public List<Object> getSupplierContactList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("iscid", rs.getString("isc_id"));
				  map.put("supplierid", rs.getString("supplier_id"));
				  map.put("prefix", rs.getString("isc_prefix"));
				  map.put("contactname", rs.getString("isc_contact_name"));
				  map.put("email", rs.getString("isc_email"));
				  map.put("phoneno", rs.getString("isc_phoneno"));
				  map.put("designation", rs.getString("isc_designation"));
				  
				  index++;
				  
				  return map;
			}
		});
		
		return LOVList;
	}

	
	@Override
	public HERO_STK_RESPONSEINFO getCountry() {
			String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_countries";
		
		log.info("country Query    "+selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> countryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,Object> details = new HashMap<String,Object>();
				
				details.put("id",rs.getString("id"));
				details.put("countryname",rs.getString("name"));
				return details;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(countryList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO getStates(int countryId) {
		log.info("countryId  "+countryId);
		String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_states WHERE country_id = '"+countryId+"'";
		
		log.info("state Query    "+selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> stateList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,Object> details = new HashMap<String,Object>();
				
				details.put("id",rs.getString("id"));
				details.put("statename",rs.getString("name"));
				return details;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(stateList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO getCities(int stateId) {
		log.info("stateId  "+stateId);
		String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_city WHERE state_id = '"+stateId+"'";
		
		log.info("hero_city_lov Query    "+selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> cityList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,Object> details = new HashMap<String,Object>();
				
				details.put("id",rs.getString("id"));
				details.put("cityname",rs.getString("name"));
				return details;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(cityList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public List<Object> getdishHistroyList(String query) {


		@SuppressWarnings("unchecked")
		List<Object> POList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("id", rs.getString("dish_setup_id"));
				map.put("dishName", rs.getString("dish_name"));
				map.put("dishNameId", rs.getString("dish_name_id"));
				map.put("dishTypeName", rs.getString("dishtype_name"));
				map.put("count", rs.getString("dish_count"));
				return map;
			}
		});
		
		return POList;
	}
	
	@Override
	public List<Object> getPurchaseOrderHistroyList(String query) {


		@SuppressWarnings("unchecked")
		List<Object> POList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				Double dpaidamt = rs.getDouble("paid_amt"),dtotalamt = rs.getDouble("total_amt");
				
				map.put("index", index+1);
				map.put("prhdrid", rs.getString("prhdr_id"));
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("purchasecodenavigate", "<a title='View Full Details of Your Purchase' data-toggle='tooltip' class='color-font' href='purchaseorderview?pid="+rs.getString("purchase_id")+"'>"+rs.getString("purchase_code")+"</a>");
				map.put("purchaserefno", rs.getString("purchase_refer_no"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("receiveddate", rs.getString("received_date"));
				map.put("totalamt", rs.getString("totalamt"));    
				map.put("totalamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt)));
				map.put("paidamt", new DecimalFormat("#.##").format(dpaidamt));
				map.put("paidamtdisp", "Rs."+(new DecimalFormat("#.##").format(dpaidamt)));
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("purchasenotes", rs.getString("purchase_notes"));
				map.put("purchasetnc", rs.getString("purchase_tnc"));
				map.put("receivestatus", rs.getString("receive_status"));
				map.put("purchasestatus", rs.getString("purchase_status"));
				map.put("prhdrid", rs.getString("prhdr_id"));
				map.put("balanceamt", new DecimalFormat("#.##").format(dtotalamt - dpaidamt) );
				map.put("balanceamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt - dpaidamt) ));
				map.put("suppliername", rs.getString("supplier_name"));
				
				int purchasestatus = rs.getInt("purchase_status");
				int receivestatus = rs.getInt("receive_status");
				
				if(purchasestatus == 1)
				{
					map.put("pur_status_color", "color-font");	
				}
				else if(purchasestatus == 2)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				else if(purchasestatus == 3)
				{
					map.put("pur_status_color", "yellow-font");	
				}
				else if(purchasestatus == 4)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				else if(purchasestatus == 5)
				{
					map.put("pur_status_color", "green-font");	
				}
				else if(purchasestatus == 6)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				
				if(receivestatus == 1)
				{
					map.put("rec_status_color", "blue-font");	
				}
				else if(receivestatus == 2)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				else if(receivestatus == 3)
				{
					map.put("rec_status_color", "yellow-font");	
				}
				else if(receivestatus == 4)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				else if(receivestatus == 5)
				{
					map.put("rec_status_color", "green-font");	
				}
				else if(receivestatus == 6)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				
				if(rs.getInt("purchase_status") == 3)
				{
					map.put("purchasestatusdesc", "Not Paid");	
				}
				else
				{
					map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
				}
				
				map.put("recvstatusdesc", rs.getString("recvstats_desc"));
				
				return map;
			}
		});
		
		return POList;
	}
	
	
	@Override
	public List<Object> getPurchaseOrderHistroyList1(String query) {


		@SuppressWarnings("unchecked")
		List<Object> POList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				Double dpaidamt = rs.getDouble("paid_amt"),dtotalamt = rs.getDouble("total_amt");
				
				map.put("index", index+1);
				//map.put("prhdrid", rs.getString("prhdr_id"));
				map.put("requestcode", rs.getString("requestcode"));
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("purchasecodenavigate", "<a title='View Full Details of Your Purchase' data-toggle='tooltip' class='color-font' href='purchaseorderview?pid="+rs.getString("purchase_id")+"'>"+rs.getString("purchase_code")+"</a>");
				map.put("purchaserefno", rs.getString("purchase_refer_no"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("receiveddate", rs.getString("received_date"));
				map.put("totalamt", rs.getString("totalamt"));    
				map.put("totalamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt)));
				map.put("paidamt", new DecimalFormat("#.##").format(dpaidamt));
				map.put("paidamtdisp", "Rs."+(new DecimalFormat("#.##").format(dpaidamt)));
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("purchasenotes", rs.getString("purchase_notes"));
				map.put("purchasetnc", rs.getString("purchase_tnc"));
				map.put("receivestatus", rs.getString("receive_status"));
				map.put("purchasestatus", rs.getString("purchase_status"));
			//	map.put("prhdrid", rs.getString("prhdr_id"));
				map.put("balanceamt", new DecimalFormat("#.##").format(dtotalamt - dpaidamt) );
				map.put("balanceamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt - dpaidamt) ));
				map.put("suppliername", rs.getString("supplier_name"));
				
				int purchasestatus = rs.getInt("purchase_status");
				int receivestatus = rs.getInt("receive_status");
				
				if(purchasestatus == 1)
				{
					map.put("pur_status_color", "color-font");	
				}
				else if(purchasestatus == 2)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				else if(purchasestatus == 3)
				{
					map.put("pur_status_color", "yellow-font");	
				}
				else if(purchasestatus == 4)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				else if(purchasestatus == 5)
				{
					map.put("pur_status_color", "green-font");	
				}
				else if(purchasestatus == 6)
				{
					map.put("pur_status_color", "lite-gray-font");	
				}
				
				if(receivestatus == 1)
				{
					map.put("rec_status_color", "blue-font");	
				}
				else if(receivestatus == 2)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				else if(receivestatus == 3)
				{
					map.put("rec_status_color", "yellow-font");	
				}
				else if(receivestatus == 4)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				else if(receivestatus == 5)
				{
					map.put("rec_status_color", "green-font");	
				}
				else if(receivestatus == 6)
				{
					map.put("rec_status_color", "lite-gray-font");	
				}
				
				if(rs.getInt("purchase_status") == 3)
				{
					map.put("purchasestatusdesc", "Not Paid");	
				}
				else
				{
					map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
				}
				
				map.put("recvstatusdesc", rs.getString("recvstats_desc"));
				
				return map;
			}
		});
		
		return POList;
	}
 

public String recvStatus = "";
	@Override
	public List<Object> getstList(String query,HttpServletRequest httpRequest) {
		
		int userType = (int)httpRequest.getSession().getAttribute("usertype");
		log.info("userType   "+httpRequest.getSession().getAttribute("usertype"));
		log.info("userTypestr"     +httpRequest.getSession().getAttribute("usertypestr"));
		log.info("usertypedesc    "+httpRequest.getSession().getAttribute("usertypedesc"));
		String recvStatusQuery ="";
		if(userType == 10){
			recvStatusQuery = "SELECT `status_id`,`status_desc` FROM `hero_stock_trxr_status` WHERE `status_id`> 2";
		}else if(userType == 3){
			recvStatusQuery = "SELECT `status_id`,`status_desc` FROM `hero_stock_trxr_status` WHERE `status_id`= 2 OR `status_id`= 4";
		}else{
			recvStatusQuery = "SELECT `status_id`,`status_desc` FROM `hero_stock_trxr_status` WHERE `status_id`> 2";
		}
		
		
		 
		
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
				map.put("sub_sequent", rs.getString("sub_sequent"));
				map.put("order_approved", rs.getString("order_approved"));
				map.put("orderRefNo", rs.getString("ord_ref_no"));
				map.put("orderid", rs.getString("order_id"));
				map.put("transferid", rs.getString("transfer_id"));
				map.put("gcshtmlcode", rs.getString("gcs_html_code"));
				map.put("transferno", rs.getString("transfer_no"));
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("statusid", rs.getString("status_id"));
				map.put("deliverystatus", rs.getInt("delivery_status"));
			
				if(rs.getInt("delivery_status") == 1)
				{
					map.put("status", "<select  class='selectNor select-pad'" 
							+ "onchange='changerecvstatus("+index+");' id='statusselect"+index+"'>"
							+ recvStatus+ "</select>");	
				}
				else if(rs.getInt("delivery_status") == 3)
				{
					map.put("status", "<select  class='selectNor select-pad'" 
							+ "onchange='changerecvstatus("+index+");' id='statusselect"+index+"'>"
							+ recvStatus+ "</select>");	
				}else{
					
				}
				
				
				map.put("productcount", rs.getString("PROD_COUNT"));
				map.put("amount", rs.getString("AMOUNT"));
				map.put("storename", rs.getString("store_name"));
				map.put("status_desc", rs.getString("status_desc"));
				
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
					
					map.put("newaction", "<li><a onclick=\"saverecvstatus("+index+","+rs.getString("transfer_id")+"><i class=\"fa fa-hdd-o\"></i></a></li>"+
											"<li><a><i class=\"fa fa-trash-o\"></i></a></li>");
					
				}
				else if(rs.getInt("delivery_status") == 3)
				{
					map.put("action", "<button class=\"delete myBtnTab\""
							+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>");	
				}
				else
				{
					map.put("newaction", "");
				}
				/*log.info("transferid       "+rs.getString("transfer_id")+"   "+
"<a href='stocktransferhistoryview?tno='"+rs.getString("transfer_no")+"'>"+rs.getString("transfer_no")+"</a>");*/
				map.put("stocktransfernavigate", "<a class='color-font' href='stocktransferhistoryview?tid="+rs.getString("transfer_id")+"'>"+rs.getString("transfer_no")+"</a>");
				map.put("index", index);
				return map;
			}
		});
		log.info(stockList);

		
		return stockList;
	}


	/*@Override
	public HERO_STK_RESPONSEINFO stockdetail(String transferid) {
		// TODO Auto-generated method stub
		return null;
	}*/


	@Override
	public List<Object> getinvList(String invListQuery) {
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
				
				map.put("posid", rs.getInt("pos_id"));
				map.put("poscode", rs.getString("pos_code"));
				map.put("htmlcode", rs.getString("gcs_html_code"));
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
				map.put("posreturnsalescode", rs.getString("pos_return_sales_code"));
				
				if(rs.getInt("pos_orders_avail") > 0 || rs.getString("pos_status_desc").equals("Cancelled"))
				{
					//log.info(rs.getString("pos_status_desc")+"1");
					map.put("action", "<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
							+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				}
				else if(rs.getString("pos_return_sales_code") == null){
					//log.info(rs.getString("pos_status_desc")+"2");
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION+"<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
						+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				}
				else{
					//log.info(rs.getString("pos_status_desc")+"3");
				map.put("action", "<button class=\"print myBtnTab\" onclick='generateinvoicereport("+rs.getInt("pos_id")+")'> "
						+ "<i class=\"fa fa-file-pdf-o\"></i> </button>");
				}
				
				
				map.put("salestype", rs.getString("pos_sales_type"));
				
				index++;
				
				return map;
			}
		});
		
		
		return invoiceList;
	}


	@Override
	public List<Object> getcustomerList(String customerOrderQuery) {
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
		
		
		
		return customerOrderList;
	}	
	
	
	@Override
	public List<Object> getsupplierList(String supplierQuery) {
		// TODO Auto-generated method stub
		
@SuppressWarnings("unchecked")
List<Object> supplierList = jdbcTemplate.query(supplierQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("suppliername", rs.getString("suppliername"));
				map.put("opttype", rs.getString("opttype"));
				map.put("paymode", rs.getString("paymode"));
				map.put("credireddays", rs.getString("reqdays"));
			
				index++;
				
				return map;
			}
		});
		log.info("stockList      "+supplierList);
		
		return supplierList;
	}
	
	
	@Override
	public List<Object> getpositemList(String positemquery) {
		// TODO Auto-generated method stub
		
@SuppressWarnings("unchecked")
List<Object> supplierList = jdbcTemplate.query(positemquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("tax1amt", rs.getString("tax1amt"));
				map.put("tax2amt", rs.getString("tax2amt"));
				map.put("batch", rs.getString("pos_batch_id"));
				map.put("salescount", rs.getString("pos_sales_count"));
				map.put("salesprice", rs.getString("pos_sales_price"));
				map.put("subtotal", rs.getString("pos_subtotal"));
				map.put("hsncode", rs.getString("hsncode"));
				map.put("productname", rs.getString("product_name"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				//map.put("expirydate", rs.getString("expirydate"));
				map.put("taxamt", rs.getString("pos_tax_amount"));
				map.put("netamount", rs.getString("pos_netamount"));
				/*map.put("suppliername", rs.getString("suppliername"));
				map.put("opttype", rs.getString("opttype"));
				map.put("paymode", rs.getString("paymode"));
				map.put("credireddays", rs.getString("reqdays"));*/
				
			
				index++;
				
				return map;
			}
		});
		log.info("stockList      "+supplierList);
		
		return supplierList;
	}
	
	
	@Override
	public List<Object> getposcalcList(String poscalcquery) {
		// TODO Auto-generated method stub
		
@SuppressWarnings("unchecked")
List<Object> supplierList = jdbcTemplate.query(poscalcquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("posid", rs.getString("pos_id"));
				map.put("poscode", rs.getString("pos_code"));
				map.put("custname", rs.getString("CUST_NAME"));
				map.put("tinnumber", rs.getString("tinnumber"));
				map.put("discount", rs.getString("pos_discount"));
				map.put("netamount", rs.getString("pos_netamount"));
				map.put("grandtotal", rs.getString("pos_grand_total"));
				map.put("cgst", rs.getString("cgst"));
				map.put("sgst", rs.getString("sgst"));
				map.put("shippingamt", rs.getString("pos_shipping_cost"));
				map.put("adres", rs.getString("adres"));
				map.put("taxamt", rs.getString("pos_tax_amount"));
				map.put("totaltaxamt", rs.getString("total_tax_amount"));
				map.put("address", rs.getString("street_address"));
				map.put("city", rs.getString("city"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("discount", rs.getString("pos_discount"));
				//map.put("paymode", rs.getString("paymode"));
				//map.put("credireddays", rs.getString("reqdays"));
			
				index++;
				
				return map;
			}
		});
		log.info("stockList      "+supplierList);
		
		return supplierList;
	}
	
	@Override
	public List<Object> getposorgList(String organisationquery) {
		// TODO Auto-generated method stub
		
@SuppressWarnings("unchecked")
List<Object> supplierList = jdbcTemplate.query(organisationquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("orgname", rs.getString("orgn_name"));
				map.put("address", rs.getString("orgn_address"));
				map.put("mobile", rs.getString("orgn_mobile"));
				map.put("email", rs.getString("orgn_email"));
				//map.put("credireddays", rs.getString("reqdays"));
			
				index++;
				
				return map;
			}
		});
		log.info("stockList      "+supplierList);
		
		return supplierList;
	}
	
	@Override
	public List<Object> getorganizationlist(String organizationquery) {
		@SuppressWarnings("unchecked")
		List<Object> organizationList = jdbcTemplate.query(organizationquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("orgnname", rs.getString("orgn_name"));
				map.put("address", rs.getString("orgn_address"));
				map.put("mobile", rs.getString("orgn_mobile"));
				map.put("email", rs.getString("orgn_email"));
				map.put("purchasecode", rs.getString("purchase_code"));
				return map;
			}
		});
		
		
		
		return organizationList;
	}	
	@Override
	public List<Object> getpurchaseorderlist(String purchaseorderquery) {
		@SuppressWarnings("unchecked")
		List<Object> purchaseList = jdbcTemplate.query(purchaseorderquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				int grandtotal= rs.getInt("grandtotalwords");
				String amountInWords = HEROHOSURINVENTORYUTIL.convertNumToString(grandtotal);
				
				map.put("suppliername", rs.getString("suppliername"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("mobile", rs.getString("mobile"));
				map.put("email", rs.getString("email_id"));
				
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("billno", rs.getString("prhdr_bill_no"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("refno", rs.getString("purchase_refer_no"));
				
				map.put("subtotal", rs.getString("prhdr_total_amt"));
				map.put("discount", rs.getString("prhdr_discount_amt"));
				map.put("shippingcost", rs.getString("prhdr_shipping_cost"));
				map.put("grandtotal", rs.getString("prhdr_grand_total_amt"));
				
				map.put("currentdate", rs.getString("currentdate"));
				map.put("podate", rs.getString("prhdr_recv_date"));
				map.put("ponumber", rs.getString("ponumber"));
				
				map.put("prodtotal", rs.getString("prodtotal"));
				map.put("totalwithtax", rs.getString("totalwithtax"));
				map.put("grandtotal", rs.getString("grandtotal"));
				map.put("grandtotalinwords", amountInWords);
				log.info("amountin "+ map.get("grandtotal"));
				return map;
			}
		});
		
		
		
		return purchaseList;
	}	
	@Override
	public List<Object> getpurchaseorderitemlist(String purchaseitemquery) {
		@SuppressWarnings("unchecked")
		List<Object> purchaseitemList = jdbcTemplate.query(purchaseitemquery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("productname", rs.getString("product_name"));
				map.put("fulluom", rs.getString("prec_full"));
				map.put("looseuom", rs.getString("prec_loose"));
				map.put("totalqty", rs.getString("prec_recving_quantity"));
				map.put("bonusqty", rs.getString("prec_free_count"));
				
				map.put("gsttax", rs.getString("gsttax"));
				map.put("rate", rs.getString("prec_pur_price"));
				map.put("amount", rs.getString("amount"));
				map.put("bonusqty", rs.getString("prec_free_count"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("categoryname", rs.getString("category_name"));
				map.put("batchno", rs.getString("prec_batchno"));
				map.put("TAXAMOUNT", rs.getString("TAXAMOUNT"));
				map.put("SUBTOTAL", rs.getString("SUBTOTAL"));
				map.put("index", index);
				
				index++;
				return map;
				
			}
		});
		
		
		
		return purchaseitemList;
	}	
	
	
	@Override
	public List<Object> getstocktransferlist(String stocktransferquery) {
		@SuppressWarnings("unchecked")
		List<Object> stocktransferList = jdbcTemplate.query(stocktransferquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("transferno", rs.getString("transfer_no"));
				map.put("transferdate", rs.getString("transfer_date"));
				map.put("currentdate", rs.getString("currentdate"));
				map.put("stnumber", rs.getString("stnumber"));
				map.put("storename", rs.getString("store_name"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("phone", rs.getString("phone"));
				map.put("email", rs.getString("email"));
				return map;
				
			}
		});
		
		
		
		return stocktransferList;
	}	
	
	
	@Override
	public List<Object> getstocktransferfromlist(String stocktransferfromquery) {
		@SuppressWarnings("unchecked")
		List<Object> stocktransferfromList = jdbcTemplate.query(stocktransferfromquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
			
				map.put("storename", rs.getString("store_name"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("phone", rs.getString("phone"));
				map.put("email", rs.getString("email"));
				map.put("conversionrate", rs.getString("CURR_CONVERSION_RATE"));
				map.put("exchnagerate", rs.getString("CURR_EXCHNG_RATE"));
				map.put("currencyhtmlcode", rs.getString("gcs_html_code"));
				
				return map;
				
			}
		});
		
		
		
		return stocktransferfromList;
	}	
	
	
	@Override
	public List<Object> getstocktransferitemlist(String stocktransferitemquery) {
		@SuppressWarnings("unchecked")
		List<Object> stocktransferitemList = jdbcTemplate.query(stocktransferitemquery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("index",index);
				map.put("productname", rs.getString("product_name"));
				map.put("batchno", rs.getString("batch_id"));
				map.put("fullqty", rs.getString("pur_full_qty"));
				map.put("looseqty", rs.getString("pur_loose_qty"));
				map.put("totalqty", rs.getString("tobe_recvd_prod_count"));
				map.put("rate", rs.getString("product_rate"));
				map.put("amount", rs.getString("amount"));
				map.put("conversionrate", rs.getString("CURR_CONVERSION_RATE"));
				index++;
				return map;
				
			}
		});
		
		
		
		return stocktransferitemList;
	}	
	
	@Override
	public List<Object> executeQuery(String query) {
		// TODO Auto-generated method stub
		List<Object> result = jdbcTemplate.queryForList(query);
		return result;
	}
	

	@Override
	public List<Object> getPurchaseOrderdelHistory(HttpServletRequest httpRequest) {
		
		String PurchaseOrderdelHistoryquery =  "SELECT purchase_id,purchase_code,purchase_refer_no, "
				+ "DATE_FORMAT(a.`created_date`,'%e/%c/%Y %h:%i')purchase_date, DATE_FORMAT(received_date,'%e/%c/%Y')received_date, "
				+ "total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc, receive_status,purchase_status,CONCAT(supplier_first_name) "
				+ "supplier_name,ps_status_name purchase_status_desc,purchase_status, (SELECT ps_status_name FROM hero_stock_purchase_status"
				+ " d WHERE d.ps_id = a.receive_status)recvstats_desc,(SELECT COUNT(*) FROM  `hero_stock_purchase_receive_hdr`"
				+ " WHERE `pur_req_id` = `purchase_code`) purchase_count,`delete_remarks` FROM hero_stock_purchase_status c , "
				+ "hero_stock_supplier b, hero_stock_purchase_history a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = "
				+ " c.ps_id  ORDER BY `purchase_id` DESC  ";
		
		@SuppressWarnings("unchecked")
		List<Object> PurchaseOrderdelHistoryqueryList = jdbcTemplate.query(PurchaseOrderdelHistoryquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("index",index+1);
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("suppliername", rs.getString("supplier_name"));
				map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
				map.put("recvstatsdesc", rs.getString("recvstats_desc"));
				map.put("amount", rs.getString("total_amt"));
				map.put("remarks", rs.getString("delete_remarks"));
				int pid = Integer.parseInt(rs.getString("purchase_id"));
				map.put("action", "<a onclick='opendelpoview("+pid+")' ><i class='fa fa-eye' style='font-size: 18px; color: #d64b4b'></i></a>");
				
				index++;
				return map;
				
			}
		});
		
		
		
		return PurchaseOrderdelHistoryqueryList;
	}


	@Override
	public List<Object> getpurchaseorderdelhistoryview(HttpServletRequest httpRequest, String poid) {
		
		String PurchaseOrderdelHistoryquery =  "SELECT c.`purchase_code`, b.`product_name`, CONCAT( COALESCE(`pur_full_qty`,0), ' ', "
				+ "(SELECT `unit` FROM `hero_admin_unit_type` G WHERE  G.`unit_type_id` = `pur_full_uom`)) full_qty, CONCAT(COALESCE(`pur_loose_qty`,0), ' ', "
				+ "(SELECT `unit` FROM `hero_admin_unit_type` G WHERE  G.`unit_type_id` = `pur_loose_uom`))loose_qty,`pur_quantity` FROM "
				+ "`hero_stock_purchase_request_history` a JOIN `hero_stock_product` b ON a.`pur_product_id` = b.`product_id` JOIN"
				+ " `hero_stock_purchase_history` c ON c.`purchase_code` = a.`pur_req_id`  WHERE c.`purchase_id` = "+poid;
		
		@SuppressWarnings("unchecked")
		List<Object> PurchaseOrderdelHistoryqueryList = jdbcTemplate.query(PurchaseOrderdelHistoryquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("index",index+1);
				
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("productname", rs.getString("product_name"));
				map.put("fullqty", rs.getString("full_qty"));
				map.put("looseqty", rs.getString("loose_qty"));
				map.put("totalqty", rs.getString("pur_quantity"));
				
				index++;
				return map;
				
			}
		});
		
		
		
		return PurchaseOrderdelHistoryqueryList;
	}


	@Override
	public List<Object> updatereadnotificationstatus(HttpServletRequest httpRequest, String poid) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public List<Object> getGRNList(String query) {


		@SuppressWarnings("unchecked")
		List<Object> POList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				
				map.put("index", index+1);
				map.put("grnno", rs.getString("prhdr_bill_no"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("purchaserefno", rs.getString("purchase_refer_no"));
				map.put("grandtotal", rs.getString("prhdr_grand_total_amt"));
				map.put("paidamount", rs.getString("prhdr_paid_amount"));
				map.put("tobepaid", rs.getString("tobe_paid"));
				map.put("recvdate", rs.getString("prhdr_recv_date"));
				
				return map;
			}
		});
		
		return POList;
	}
	
}
