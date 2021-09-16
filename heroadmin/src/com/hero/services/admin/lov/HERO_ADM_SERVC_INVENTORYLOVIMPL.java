package com.hero.services.admin.lov;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYLOV;

public class HERO_ADM_SERVC_INVENTORYLOVIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_IINVENTORYLOV {
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_INVENTORYLOVIMPL.class);
	public static List<Object> HERO_ProductList = null;
	@Override
	public List<Object> getLOVList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HERO_ADM_SERVC_INVENTORYLOV lov = new HERO_ADM_SERVC_INVENTORYLOV();
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
    public List<Object> getUserCountList(String userCountQuery) {
            // TODO Auto-generated method stub
            
            @SuppressWarnings("unchecked")
            List<Object> LOVList = jdbcTemplate.query(userCountQuery, new RowMapper() {
                    int index=0;
                    @Override
                    public Object mapRow(ResultSet rs, int arg1) throws SQLException {

                              Map<String, Object> map = new HashMap<String, Object>();
                              map.put("nextuserid", rs.getString("next_user_id"));
                              index++;
                              return map;
                    }
            });
            
            return LOVList;
    }
  
   
	@Override
	public List<Object> setEmptyList() {
		// TODO Auto-generated method stub
		List<Object> LOVList = new ArrayList<Object>();
		HERO_ADM_SERVC_INVENTORYLOV lov = new HERO_ADM_SERVC_INVENTORYLOV();
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
	public List<Object> getViewReceiveStockList(String receivestockQuery) {
		// TODO Auto-generated method stub
		/*String query = "SELECT purchase_code,product_id,product_name,unit,pur_quantity,prec_quantity,free_product_count,prec_batchno,"
				+ "DATE_FORMAT(prec_expiry_date,'%e/%c/%Y')prec_expiry_date,prec_purchase_price,prec_sales_price FROM hero_stock_product b,hero_stock_purchase_request c,"
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
				+ "AND a.purchase_code = c.pur_req_id AND b.product_id = c.pur_product_id AND b.unit_type_id = d.unit_type_id";*/
		
		@SuppressWarnings("unchecked")
		List<Object> receivestockList = jdbcTemplate.query(receivestockQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("purchaseorderno", rs.getString("purchase_code"));
				map.put("productid", rs.getString("product_id"));
				map.put("productname", rs.getString("product_name"));
				map.put("unit", rs.getString("unit"));
				map.put("purquantity", rs.getString("pur_quantity"));
				map.put("receivingqty", (rs.getString("prec_recving_quantity") == null ) ? 0 : rs.getString("prec_recving_quantity"));
				map.put("bonusqty", rs.getString("prec_free_count"));
				map.put("batchno", rs.getString("prec_batchno"));
				map.put("barcode", rs.getString("prec_barcode"));
				map.put("totalamountdisp", rs.getString("prhdr_total_amt_disp"));
				map.put("expirydate", rs.getString("prec_expiry_date"));
				map.put("purchaseprice", rs.getString("prec_pur_price"));
				map.put("salesprice", rs.getString("prec_sel_price"));
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);
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
				map.put("netunitdesc", rs.getString("netunitdesc"));
				map.put("index", index);
				
				map.put("receivedqty", rs.getString("RECVDQTY"));
				
				if(rs.getString("prhdr_id") != null)
				map.put("headerid", rs.getString("prhdr_id"));
				else
				map.put("headerid", 0);
				
				if(rs.getInt("prhdr_id") == 0)
				map.put("oprn", "INS");
				else
				map.put("oprn", "UPD");
				
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
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
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
				map.put("isreportmenu",rs.getString("isreportmenu"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					check1 = check1 + 1;
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("property", "parent"+check1);
					map.put("style", "style='background-color: #6259ca;font-weight: bolder;color:white'");
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
						+rs.getString("user_menu_sno")+"$$$"+rs.getInt("isreportmenu"));
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
					map.put("isreportmenu",rs.getString("isreportmenu"));
					map.put("parentid",rs.getString("parentid"));
					map.put("index", index);
					
					if(rs.getInt("issubmodule") == 0)
					{
						
						map.put("mainmenudisp", "mainmenudisp");
						map.put("submenudisp", "mainmenuhidedisp");
						map.put("space", "");
						map.put("style", "style='background-color: #6259ca;font-weight: bolder;color:white'");
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
	
	int menu_admin = 0;
	@Override
	public List<Object> getMenuList(String menuQuery, int isadminmenu) {
		// TODO Auto-generated method stub
		log.info("getMenuList   ");
		menu_admin = isadminmenu;
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
							+" AND menu_access = 1 AND `status` = '1' AND `isadminmenu` = '"+menu_admin+"' ORDER BY `userid`,`moduleid` ASC";
					
				  
				log.info("submenu query  "+submenuQuery);  
				
				  List<Object> submenuList = new ArrayList<Object>();
				  submenuList = getSubMenuList(submenuQuery);
				  map.put("submenuList", submenuList);
				
				index++;
				return map;
			}
		});
		/*log.info("First usermenuList       "+usermenuList);*/
		
		return usermenuList;
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
				  map.put("paramdesc", rs.getString("param_desc"));
				  index++;
				  return map;
			}
		});
		
		return LOVList;
	}
	@Override
	public List<Object> getemailTemplateList(String emailtemplateQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(emailtemplateQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("templateid", rs.getInt("email_temp_id"));
				  map.put("templatename", rs.getString("email_temp_name"));
				  map.put("msgsubject", rs.getString("email_temp_subject"));
				  map.put("paramdesc", rs.getString("param_desc"));
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
			
			/*if(userType <= 2)
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
			}*/
			
		lowStockSB.append("SELECT 0 product_id, 0 product_count , 0 alert_count ,0 product_name, 0 store_name, 0 pharmacy_id ");
			
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
		else if(session.getAttribute("storeid") instanceof Long){
			storeid = (int) session.getAttribute("storeid");
		}
		else
		{
			String storeidSTR = (String)session.getAttribute("storeid");
			storeid = Integer.parseInt(storeidSTR);
		}
		
		
		String productCountQuery = "SELECT COUNT(*) FROM `hero_stock_product` WHERE `status_id` = 1";
		String storeCountQuery = "SELECT COUNT(*) FROM `hero_stock_store`";
		/*String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier` WHERE `status_id` = 1";*/
		String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier`";
		
		String userCountQuery = "SELECT COUNT(*) FROM `hero_user` WHERE `status` = 1 AND `role` != 9";
		String taxCountQuery ="SELECT COUNT(*) FROM `hero_admin_tax`";
		
		/*String receptCountQuery ="SELECT  COUNT(*) FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id` WHERE b.`usertype_id`= '5' ";*/
		
		String receptCountQuery ="SELECT  COUNT(*) FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id` WHERE b.`usertype_id`= '5' ";
		String stockmanagerCountQuery ="SELECT  COUNT(*) FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id` WHERE b.`usertype_id`= '3' ";
		String currencyCountQuery ="SELECT COUNT(*) FROM `hero_admin_currency` ";
		String customerCountQuery = "SELECT COUNT(*) FROM `hero_admin_customer`";
		String purchaseAmountQuery = "SELECT SUM(`prhdr_grand_total_amt`) FROM `hero_stock_purchase_receive_hdr` WHERE `prhdr_created_date` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";
		/*String salesAmountQuery = "SELECT SUM(`pos_netamount`) FROM `hero_stock_pos_summary` WHERE `created_at` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND DATE_ADD(CURRENT_DATE,INTERVAL 1 DAY )";*/
		
		String userRecentQuery = "SELECT `username`,`usertype_name` FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id` WHERE `role` != 9" 
								+" ORDER BY `userid` DESC LIMIT 5 ";
		
		/*String receptRecentQuery=
                "SELECT `username`,`usertype_name` FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id`" +"WHERE b.`usertype_id`= '5' ORDER BY `userid` DESC LIMIT 5";*/
		
		
		
		String receptRecentQuery=
                "SELECT `username`,`phone` FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id`" +"WHERE b.`usertype_id`= '5' ORDER BY `userid` DESC LIMIT 5"; 
		
		/*String storeRecentQuery="SELECT `store_name`,`status` FROM `hero_stock_store` a JOIN `hero_stock_status` b  ON a.`status_id` = b.`status_id`"
                   +"ORDER BY `store_id` DESC LIMIT 5"; */
		
		String storeRecentQuery=
				"SELECT `store_name`,`currency`,`phone` FROM `hero_stock_store` a JOIN `hero_admin_currency` b  ON a.`currency_id` = b.`currency_id` ORDER BY `store_id` DESC LIMIT 5"; 
		
		
		String stockmanagerRecentQuery="SELECT `username`,`phone` FROM `hero_user` a JOIN `hero_user_type` b  ON a.`role` = b.`usertype_id` WHERE b.`usertype_id`= '3' ORDER BY `userid` DESC LIMIT 5";

		String taxRecentQuery="SELECT `TAX_NAME`,`TAX_RATE` FROM `hero_admin_tax`"
										+"ORDER BY `TAX_ID` DESC LIMIT 5 ";	
		String currencyRecentQuery="SELECT `currency`,`gcs_html_code` FROM `hero_admin_currency` A JOIN `hero_global_currency_symbols` "
				+ " B ON A.`CURR_SYMBOL` = B.`gcs_id`"
									+"ORDER BY `currency_id` DESC LIMIT 5 	";
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
		
		String recentBillsQuery = "SELECT (`purchase_code`) as purchasecode,CONCAT(`supplier_first_name`,`supplier_last_name`)suppliername,"
				+ "SUM(`prhdr_grand_total_amt`)totalamount,DATE_FORMAT(`prhdr_created_date`,'%c/%e/%y')purchaseddate,`ps_status_name` as statusdesc "
				+ "FROM `hero_stock_purchase` a JOIN `hero_stock_supplier` b ON a.`supplier_id` = b.`supplier_id` "
				+ "JOIN `hero_stock_purchase_receive_hdr` c ON a.`purchase_code` = c.`pur_req_id` "
				+ "JOIN  `hero_stock_purchase_status` d ON d.`ps_id` = c.`prhdr_paid_status` GROUP BY `purchase_code` ORDER BY `purchase_id` DESC LIMIT 0,10";
		
		StringBuilder recentTransfersQuerySB = new StringBuilder();
		recentTransfersQuerySB.append("SELECT `transfer_no` ,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,"
									+" `status_desc` ,`store_name`  FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C ON a.`delivery_status` = C.`status_id`" 
									+" LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id`");	
		
		if(usertype > 2)
		{
			recentTransfersQuerySB.append(" WHERE `store_id`='"+session.getAttribute("storeid")+"'");
		}
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
				+ "LEFT JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` LIMIT 0,10";
		
		log.info("recentBillsQuery   "+recentBillsQuery);
		log.info("ordersQuerySB   "+ordersQuerySB.toString());
		log.info("topSellingProductQuery   "+topSellingProductQuery);
		
		
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> productCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(productCountQuery, jdbcTemplate);
		List<Object> stockmanagerCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(stockmanagerCountQuery, jdbcTemplate);
		List<Object> receptCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(receptCountQuery, jdbcTemplate);
		List<Object> storeCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(storeCountQuery, jdbcTemplate);
		List<Object> supplierCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(supplierCountQuery, jdbcTemplate);
		List<Object> storeRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(storeRecentQuery, jdbcTemplate);
		List<Object> receptRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(receptRecentQuery, jdbcTemplate);
		List<Object> stockmanagerRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(stockmanagerRecentQuery, jdbcTemplate);
		List<Object> userCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(userCountQuery, jdbcTemplate);
		List<Object> currencyCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(currencyCountQuery, jdbcTemplate);
		List<Object> taxCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(taxCountQuery, jdbcTemplate);
		List<Object> customerCountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(customerCountQuery, jdbcTemplate);
		List<Object> purchaseAmountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(purchaseAmountQuery, jdbcTemplate);
		List<Object> salesAmountList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(salesAmountQuery, jdbcTemplate);
		List<Object> recentBillsList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(recentBillsQuery, jdbcTemplate);
		List<Object> currencyRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(currencyRecentQuery, jdbcTemplate);
		List<Object> taxRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(taxRecentQuery, jdbcTemplate);
		List<Object> ordersList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(ordersQuerySB.toString(), jdbcTemplate);
		List<Map<String, Object>> topSellingProductList = (List<Map<String, Object>>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(topSellingProductQuery, jdbcTemplate);
		List<Object> recentTransfersList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(recentTransfersQuerySB.toString(), jdbcTemplate);
		List<Object> customerDueList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQuery(customerDueQuery, jdbcTemplate);
		List<Object> userRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(userRecentQuery, jdbcTemplate);
		
		
		log.info("recentBillsList   "+recentBillsList);
		
		map.put("productCount", productCountList);
		map.put("stockmanagerCount", stockmanagerCountList);
		map.put("receptCount", receptCountList);
		map.put("storeCount", storeCountList);
		map.put("supplierCount", supplierCountList);
		map.put("storeCount", storeCountList);
		map.put("userCount", userCountList);
		map.put("currencyCount", currencyCountList);
		map.put("taxCount", taxCountList);
		map.put("customerCount", customerCountList);
		map.put("purchaseAmount", purchaseAmountList);
		map.put("salesAmount", salesAmountList);
		map.put("customerDue", customerDueList);
		map.put("recentBills", recentBillsList);
		map.put("ordersList", ordersList);
		map.put("topSellingProductList", topSellingProductList);
		map.put("recentTransfers", recentTransfersList);
		map.put("userRecent", userRecentList);
		map.put("taxRecent", taxRecentList);
		map.put("receptRecent", receptRecentList);
		map.put("currencyRecent", currencyRecentList);
		map.put("stockmanagerRecent", stockmanagerRecentList);
		map.put("storeRecent", storeRecentList);
		
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
 
	String applicationcontecturmstr = "";
	@Override
	public List<Object> getAppconfigList(String heroappConfigQuery,String applncontect_URL) {
		// TODO Auto-generated method stub
		applicationcontecturmstr = applncontect_URL;
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(heroappConfigQuery, new RowMapper() {
			int index=0;
			byte[ ] imgData = null ;
			Blob image = null;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  image = rs.getBlob(1);
				  imgData = image.getBytes(1,(int)image.length());
				
				  Map<String, Object> map = new HashMap<String, Object>();
				  map.put("index", index);
				  map.put("appconfigid", rs.getString("hac_id"));
				  map.put("appname", rs.getString("hac_name"));
				  map.put("sno", rs.getString("hac_sno"));
				  map.put("logo", rs.getString("hac_logo"));
				  /*byte[] imageData = rs.getBytes("hac_logo");
				  map.put("logo", imageData);*/
				  String navigate_url = "";
				  if(rs.getString("hac_url") != null)
					  navigate_url = rs.getString("hac_url");
				  map.put("navigateurl", navigate_url);
				  map.put("tokenkeyvalidatorurl", applicationcontecturmstr+"/"+rs.getString("hac_token_validator_url"));
				  
				  index++;
				  return map;
			}
		});
		HERO_ProductList = LOVList;
		return LOVList;
	}


	@Override
	public List<Object> getforgotPWList(String query) {
		// TODO Auto-generated method stub
		log.info("getforgotPWList   ");
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("fwpwid",rs.getString("hufh_id"));
				map.put("userid", rs.getString("userid"));
				map.put("username",rs.getString("username"));
				map.put("smssend",rs.getString("hufh_sms_send"));
				map.put("requestraisedat",rs.getString("hufh_raised_at"));
				map.put("requestraisedcount",rs.getString("hufh_raised_count"));
				map.put("index", index);
				 
				index++;
				return map;
			}
		});
		
		return usermenuList;
	}


	@Override
	public List<Object> getuserTypeList(String query) {
		// TODO Auto-generated method stub
		log.info("getforgotPWList   ");
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("usertypeid",rs.getString("usertype_id"));
				map.put("usertypename", rs.getString("usertype_name"));
				map.put("usertypeimg",rs.getString("usertype_img"));
				map.put("deptname",rs.getString("hac_name"));
				map.put("index", index);
				
				index++;
				return map;
			}
		});
		
		return usermenuList;
	}



	@Override
	public List<Object> getUserTypeList(String userTypeQuery) {
		// TODO Auto-generated method stub
		log.info("getforgotPWList   ");
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(userTypeQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("usertypeid",rs.getString("usertype_id"));
				map.put("usertypename", rs.getString("usertype_name"));
				map.put("deptid",rs.getString("usertype_dept"));
				return map;
			}
		});
		
		return usermenuList;
	}



	@Override
	public List<Object> getApplnList(String usertypeApplnQuery) {
		@SuppressWarnings("unchecked")
		List<Object> usertypeApplnList = jdbcTemplate.query(usertypeApplnQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("applnid", rs.getInt("usertype_dept"));
				return map;
			}
		});
		return usertypeApplnList;
	}
	
	@Override
	public List<Object> executeQuery(String query) {
		// TODO Auto-generated method stub
		List<Object> result = jdbcTemplate.queryForList(query);
		return result;
	}
	
}
