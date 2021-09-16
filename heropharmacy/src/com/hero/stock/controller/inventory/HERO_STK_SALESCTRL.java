package com.hero.stock.controller.inventory;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;

@Controller
public class HERO_STK_SALESCTRL {
	private static Logger log = Logger.getLogger(HERO_STK_SALESCTRL.class);
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping("/pos")
	public ModelAndView gotoPOSScreen(HttpServletRequest httpRequest)
	{
		  HttpSession session = httpRequest.getSession();
		  
		
		  String customergroupQuery = "SELECT `seq_id`,`customer_group_name` FROM `hero_admin_customer_group`";
		  /*String taxQuery ="SELECT C.`tax_id`,`TAX_NAME`,`TAX_RATE` FROM `hero_stock_store` A JOIN `hero_user` B ON A.`currency_id` = B.`currencyid` "
		  		+ "JOIN `hero_stock_store_tax` C ON A.`store_id` = C.`store_id` JOIN `hero_admin_tax` D ON D.`TAX_ID` = C.`tax_id` WHERE `userid` = "+session.getAttribute("uid");*/
		  String taxQuery="SELECT c.`tax_id`,`TAX_NAME`,`TAX_RATE` FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` "
				  		+"JOIN `hero_stock_store_tax` c ON c.`store_id` = b.`store_id` JOIN `hero_admin_tax` d ON d.`TAX_ID` = c.`tax_id` WHERE `userid` = "+session.getAttribute("uid");
		  log.info("taxQuery       "+taxQuery);
		  String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		  String storeId = String.valueOf(session.getAttribute("storeid"));
		  //int storeId = (int) session.getAttribute("storeid");
		  String storeQuery = "SELECT `store_name`,`city` FROM `hero_stock_store` WHERE `store_id` = "+storeId;
		  log.info("storeQuery   "+storeQuery);
		  
		  List<Object> customergroupList = inventoryLOVobj.getLOVList(customergroupQuery);
		  List<Object> taxList = inventoryLOVobj.getPOSDiscountList(taxQuery);
		  List<Object> bankList = inventoryLOVobj.getLOVList(bankQuery);
		  List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("customergroupList",customergroupList);
		  model.addObject("taxList",taxList);
		  model.addObject("bankList",bankList);
		  model.addObject("storeList",storeList);
		  
		  model.setViewName("forms/sales/pos");
		  
		  return model;
		
	}
	@RequestMapping("/poshistory")
	public ModelAndView gotoPOSHistoryScreen(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		/*int storeid = (int)httpRequest.getSession().getAttribute("storeid");*/
		String storeid = String.valueOf(session.getAttribute("storeid"));
		ModelAndView model = new ModelAndView();
		String invListQuery = "SELECT A.`pos_id`,`pos_code`,DATE_FORMAT(A.`created_at`,'%e/%c/%Y %h:%i')created_at,A.`cust_id`,`pos_sales_type`,"
				+ "COALESCE(CONCAT(`cust_firstname`, ' ',`cust_lastname`),'POC Customer')CUST_NAME,`CURR_SYMBOL`, `store_name`,"
				+ "FORMAT(`pos_netamount`,2)pos_netamount,`pos_status_desc`, `gcs_html_code`,"
				+ "FORMAT((`pos_balance_amount` * -1),2)pos_balance_amount,`pos_return_sales_code`,`pos_orders_avail`,A.`currency` "
				+ "FROM `hero_stock_pos_summary` A LEFT JOIN `hero_admin_customer` B ON A.`cust_id` = B.`cust_id` JOIN `hero_stock_store` C ON A.`pos_store_id` = C.`store_id` "
				+ "JOIN `hero_stock_pos_status` d ON d.`pos_status_id` = A.`pos_status` JOIN `hero_admin_currency` e ON e.`currency_id` = C.`currency_id`  JOIN `hero_global_currency_symbols` f ON f.`gcs_id`=e.`CURR_SYMBOL` "
				+ "WHERE A.`pos_store_id` = "+storeid+" and `pos_orders_avail` = 0";
				/*+ "WHERE A.`pos_store_id` = "+storeid;*/
		
		log.info("invListQuery    "+invListQuery);
		List<Object> invList = inventoryLOVobj.getinvList(invListQuery);
		model.setViewName("forms/sales/poshistory");
		model.addObject("invList",invList);
		model.addObject("invListsize",invList.size());
		return model;
		
	}
	
	@RequestMapping("/posprint/{posid}")
	public ModelAndView gotoPOSprintscreen(@PathVariable("posid") String posid)
	{
		 ModelAndView model = new ModelAndView();
		 String organisationquery="SELECT `orgn_name`,`orgn_address`,`orgn_mobile`,`orgn_email` "
		 		+ " FROM `hero_orgn_table` ORDER BY `created_at` DESC  LIMIT 1 ";

		String poscalcquery = "SELECT `pos_id`,A.`cust_id`,`pos_code`,A.`pos_delv_address_id`,COALESCE(CONCAT(`cust_firstname`,' ',`cust_lastname`),'POC Customer')CUST_NAME,"
		+ " `tinnumber`,FORMAT(`pos_discount`,2)pos_discount, COALESCE(FORMAT(pos_netamount,2),0)pos_netamount,"
		+ " COALESCE(FORMAT(pos_grand_total+`cgst`+`sgst`,2),0)pos_grand_total,FORMAT(`cgst`,2)cgst,FORMAT(`sgst`,2)sgst,e.`currency`,"
		+ " `pos_payment_type`,`pos_paid_amt`,`pos_discount_type`,`pos_discount_type_value`,`pos_order_code`,`pos_card_number`,`pos_card_type`,"
		+ " `pos_bank_id`,`pos_cheque_no`,`pos_delv_address_id`,pos_sales_type,COALESCE(FORMAT(`pos_shipping_cost`,2),0.00)pos_shipping_cost,"
		+ " `street_address`,d.`city`,d.`country`,d.`zipcode`,"
		+ " CONCAT(COALESCE(`street_address`,''),',',COALESCE(d.`city`,''),'-',COALESCE(d.`zipcode`,''),',',COALESCE(d.`country`,''))adres,"
		+ " DATE_FORMAT(A.`created_at`,'%e/%c/%y')created_at,FORMAT(`pos_tax_amount`,2)pos_tax_amount,"
		+ " FORMAT((`pos_tax_amount`+ `cgst`+`sgst`),2)total_tax_amount  FROM `hero_stock_pos_summary` A "
		+ " LEFT JOIN `hero_admin_customer` B ON A.`cust_id` = B.`cust_id` LEFT JOIN `hero_admin_customer_address` c ON B.`cust_id`=c.`cust_id`"
		+ " JOIN `hero_stock_store` d ON d.`store_id` = A.`pos_store_id`  JOIN `hero_admin_currency`e ON e.`currency_id` = d.`currency_id` "
	    + " WHERE A.`pos_id` = "+posid+" and `pos_orders_avail` = 0";
			
		
		String positemquery="SELECT a.`pos_id`,`pos_discount`,`pos_grand_total`,`pos_netamount`,`pos_tax_amount`,`pos_store_id`,`pos_shipping_cost`,"
		+ "a.`cgst`tax1amt,a.`sgst`tax2amt,`pos_prod_id`,`pos_batch_id`,`pos_sales_count`,`pos_sales_price`,`pos_subtotal`,b.`cgst`,b.`sgst`,"
		+ "b.`hsncode`,`product_name`,DATE_FORMAT(`expiry_date`,'%e/%c/%Y')expirydate FROM `hero_stock_pos_summary` a "
		+ "JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`pos_prod_id` = c.`product_id`"
		+ "JOIN `hero_stock_transfer_product` d ON b.`pos_prod_id` = d.`product_id` AND b.`pos_batch_id` = d.`batch_id`"
		+ "WHERE a.`pos_id` = "+posid+"  UNION ALL "
		+ " SELECT a.`pos_id`,`pos_discount`,`pos_grand_total`,`pos_netamount`,`pos_tax_amount`,`pos_store_id`,`pos_shipping_cost`,"
		+ " a.`cgst`tax1amt,a.`sgst`tax2amt,`poo_prod_id`,`poo_batch_id`,`poo_sales_count`,`poo_sales_price`,`poo_subtotal`,b.`cgst`,b.`sgst`,"
		+ " b.`hsncode`,`product_name`,DATE_FORMAT(`expiry_date`,'%e/%c/%Y')expirydate FROM `hero_stock_pos_summary` a "
		+ " JOIN `hero_stock_pos_order_items` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`poo_prod_id` = c.`product_id`"
		+ " JOIN `hero_stock_transfer_product` d ON b.`poo_prod_id` = d.`product_id` AND b.`poo_batch_id` = d.`batch_id`"
		+ " WHERE a.`pos_id` ="+posid;
		
		log.info("posid         "+posid);
		log.info("positemquery         "+positemquery);
		log.info("organisationquery    "+organisationquery);
		log.info("poscalcquery         "+poscalcquery);
		
		List<Object> positemList = inventoryLOVobj.getpositemList(positemquery);
		List<Object> posList = inventoryLOVobj.getposcalcList(poscalcquery);
		List<Object> posorgList = inventoryLOVobj.getposorgList(organisationquery);
		model.setViewName("forms/sales/posprint");
		model.addObject("positemList",positemList);
		model.addObject("poscalcList",posList);
		model.addObject("posorgList",posorgList);
	
		return model;
		
	}
	
	@RequestMapping("/posauto")
	public ModelAndView gotoPOSAuto()
	{
		  ModelAndView model = new ModelAndView();
		  
		  model.setViewName("forms/sales/posautocomplete");
		  
		return model;
		
	}
	
	@RequestMapping("/orderhistory")
	public ModelAndView gotoOrderHistory(HttpServletRequest httpRequest)
	{
		  ModelAndView model = new ModelAndView();
		  /*int storeid = (int)httpRequest.getSession().getAttribute("storeid");*/
		  HttpSession session = httpRequest.getSession();
		  String storeid = String.valueOf(session.getAttribute("storeid"));
			String customerOrderQuery = "SELECT `pos_id`,`pos_code`,CONCAT(`cust_firstname`,' ',`cust_lastname`)custname,DATE_FORMAT(A.`created_at`,"
					+ "'%e/%c/%Y %h:%i')billdate,"
					+ "CONCAT(`street_address`,', ',`city`,' - ',`zipcode`,', ',`state`,', ',`country`)address,`pos_status_desc`,"
					+ "COALESCE(`pos_balance_amount`,0)pos_balance_amount,`pos_order_status_id`,"
					+ "(SELECT `pos_status_desc` FROM `hero_stock_pos_status` A1 WHERE A1.`pos_status_id` = `pos_order_status_id`)delvstatus "
					+ "FROM `hero_stock_pos_summary` A JOIN `hero_admin_customer` D ON D.`cust_id` = A.`cust_id` LEFT JOIN `hero_admin_customer_address` e "
					+ "ON e.`ca_id` = A.`pos_delv_address_id` "
					+ "LEFT JOIN `hero_stock_pos_status` f ON f.`pos_status_id` = A.`pos_status` WHERE `pos_orders_avail` = 1 AND `pos_store_id` = "+storeid;
			
			log.info("customerOrderQuery      "+customerOrderQuery);
			String orderStatusQuery = "SELECT `pos_status_id`,`pos_status_desc` FROM `hero_stock_pos_status` WHERE `pos_status_id` IN(5,6,7);";
			  
			  List<Object> orderStatusList = inventoryLOVobj.getLOVList(orderStatusQuery);
			List<Object> customerList = inventoryLOVobj.getcustomerList(customerOrderQuery);
			model.addObject("customerList",customerList);
			 model.addObject("orderStatusList",orderStatusList);
			model.setViewName("forms/sales/orderhistory");
			return model;
		  
		  
		
		
	}
}
