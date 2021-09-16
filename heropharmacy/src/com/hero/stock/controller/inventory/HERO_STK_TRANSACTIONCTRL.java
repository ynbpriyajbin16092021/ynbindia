package com.hero.stock.controller.inventory;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_IADDPURCHASEORDERDAO;
import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;

@Controller
public class HERO_STK_TRANSACTIONCTRL{
	private static Logger log = Logger.getLogger(HERO_STK_TRANSACTIONCTRL.class);
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HERO_STK_IADDPURCHASEORDERDAO addpurchaseorderDAOobj;
	
	@RequestMapping("/invoice")
	public ModelAndView gotoInvoice()

	{
		
		return new ModelAndView("forms/transactions/invoice");

	}
	
	@RequestMapping("/purchaseorderprint/{purchaseid}/{billno}")
	public ModelAndView gotoPurchase(@PathVariable("purchaseid") String purchaseid,@PathVariable("billno") String billno)

	{
		 ModelAndView model = new ModelAndView();
		String organizationquery="SELECT `orgn_name`,`orgn_address`,`orgn_mobile`,`orgn_email`, `purchase_code` FROM `hero_orgn_table` a JOIN  `hero_stock_purchase` b WHERE b.`purchase_id`="+purchaseid;
		String purchaseorderquery="SELECT DATE_FORMAT(CURDATE(),'%e/%c/%Y')currentdate,FORMAT(SUM(((`prec_pur_price` * `prec_recving_quantity` ))),2)prodtotal,FORMAT(SUM(((`prec_pur_price` * `prec_recving_quantity` * `TAX_RATE`)/100)),2)totalwithtax,FORMAT(SUM(((`prec_pur_price` * `prec_recving_quantity` * `TAX_RATE`)/100)+(`prec_pur_price` * `prec_recving_quantity` )),2)grandtotal,(SUM(((`prec_pur_price` * `prec_recving_quantity` * `TAX_RATE`)/100)+(`prec_pur_price` * `prec_recving_quantity` )))grandtotalwords,SUBSTRING_INDEX(`purchase_refer_no`, '-', -1)ponumber,`prhdr_id`,DATE_FORMAT(prhdr_recv_date,'%e/%c/%Y')prhdr_recv_date,`prhdr_bill_no`,purchase_code,`purchase_refer_no`,`mobile`,`email_id`,`prhdr_grand_total_amt`,COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,(`prhdr_grand_total_amt` - (COALESCE(`prhdr_paid_amount`,0)))tobe_paid,`prhdr_discount_amt`,`prhdr_discount_type`,`prhdr_discount_value`,`prhdr_shipping_cost`,prhdr_paid_status,CONCAT(`supplier_first_name`)suppliername,`address`,`city`,`state`,`zipcode`,`country_id`,`purchase_date`,`prhdr_tax_amt`,`prhdr_total_amt` FROM `hero_stock_purchase_receive_hdr` JOIN `hero_stock_purchase` A ON `purchase_code` = `pur_req_id` LEFT JOIN `hero_stock_supplier` S ON S.`supplier_id` = A.`supplier_id` LEFT JOIN `hero_stock_purchase_received_dtl` C ON C.`prec_hdr_id`=`prhdr_id` LEFT JOIN `hero_admin_tax` g ON g.`TAX_ID`=C.`prec_tax_per` WHERE `purchase_id` =  "+purchaseid+"  AND `prhdr_bill_no` ='"+billno+"'" ;
		String purchaseitemquery="SELECT `pur_req_id`,`prec_product_id`,`product_name`,`prec_expiry_date`,`TAX_RATE`,`TAX_TYPE`,CONCAT(FORMAT(((`prec_pur_price` * `prec_recving_quantity` * `TAX_RATE`)/100),2))TAXAMOUNT ,CASE WHEN `TAX_TYPE`='P' THEN CONCAT(`TAX_RATE`,'%') ELSE CONCAT(`TAX_RATE`) END AS gsttax,`prec_batchno`,`prec_free_count`,`category_name`,`prec_recving_quantity`,CONCAT(`prec_full_qty`,(SELECT `unit` FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `prec_full_uom`)) prec_full,CONCAT(FORMAT(((`prec_pur_price` * `prec_recving_quantity` )),2))SUBTOTAL,CONCAT(`prec_loose_qty`,(SELECT `unit` FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `prec_loose_uom`)) prec_loose,`prec_free_count`,CONCAT(FORMAT(`prec_pur_price`,2))prec_pur_price,CONCAT(FORMAT((`prec_pur_price` * `prec_recving_quantity`)+((`prec_pur_price` * `prec_recving_quantity` * `TAX_RATE`)/100),2))amount FROM `hero_stock_purchase` JOIN `hero_stock_purchase_receive_hdr` A ON `purchase_code` = `pur_req_id` JOIN `hero_stock_purchase_received_dtl` B ON A.`prhdr_id` = B.`prec_hdr_id` JOIN `hero_stock_product` C ON C.`product_id` = B.`prec_product_id`  LEFT JOIN `hero_admin_category` f ON f.`category_id`=C.`category_id`  LEFT JOIN `hero_admin_tax` g ON g.`TAX_ID`=B.`prec_tax_per` WHERE  `purchase_id` = "+purchaseid+" AND `prhdr_bill_no` = '"+billno+"'" ;
		
		List<Object> organizationList = inventoryLOVobj.getorganizationlist(organizationquery);
		List<Object> purchaseList = inventoryLOVobj.getpurchaseorderlist(purchaseorderquery);
		List<Object> purchaseitemList = inventoryLOVobj.getpurchaseorderitemlist(purchaseitemquery);
		 model.addObject("organizationList",organizationList);
		 model.addObject("purchaseList",purchaseList);
		 model.addObject("purchaseitemList",purchaseitemList);
		 model.setViewName("forms/transactions/purchaseorder/purchaseorderprint");
		
		 return model;
	}
	
	@RequestMapping("/stocktransferprint/{txrid}")
	public ModelAndView gotoStockprint(@PathVariable("txrid") String txrid)

	{
		 ModelAndView model = new ModelAndView();
		 String stocktransferquery="SELECT `transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date, DATE_FORMAT(CURDATE(),'%e/%c/%Y')currentdate,`pharmacy_id`,`store_name`,"
		                            + "SUBSTRING_INDEX(`transfer_no`, '-', -1)stnumber,`address`,`city`,`state`,`country`,`zipcode`,`phone`,`email`  FROM `hero_stock_transfer` A JOIN `hero_stock_store` ON `store_id` = `pharmacy_id` WHERE `transfer_id` ="+txrid;
		 
		String stocktransferitemquery="SELECT a.transfer_id,delivery_status,`transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,a.`status_id`,"
		                              + " `status_desc`,tobe_recvd_prod_count,FORMAT(CONCAT(product_rate * tobe_recvd_prod_count),2)amount,CONCAT(`pur_full_qty`,(SELECT `unit` FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`))`pur_full_qty`,"
				                      + " CONCAT(`pur_loose_qty`,(SELECT `unit` FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_loose_uom`))`pur_loose_qty`,"
		                              + " `product_rate`,`store_name`,`curr_symbol` currency,b.`product_id` ,`product_name`,`batch_id`,`CURR_CONVERSION_RATE`  FROM `hero_stock_transfer` a "
				                      + " JOIN `hero_stock_trxr_status` C ON a.`delivery_status` = C.`status_id` JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id`"
		                              + " JOIN `hero_stock_product` p ON b.`product_id` = p.`product_id` LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id`"
				                      + " LEFT JOIN `hero_admin_currency` d ON d.`currency_id` = s.`store_id` WHERE a.`transfer_id` ="+txrid;
		String stocktransferfromquery="SELECT * FROM `hero_stock_store` a JOIN `hero_user` b ON a.`store_id`=b.`user_store_id` JOIN `hero_stock_transfer` c ON c.`created_by`=b.`userid` LEFT JOIN `hero_admin_currency` d "
                                        + " ON d.`currency_id`=a.`currency_id` LEFT JOIN `hero_global_currency_symbols` e ON e.`gcs_id`=d.`CURR_SYMBOL` WHERE c.`transfer_id`="+txrid;
		List<Object> stocktransferList = inventoryLOVobj.getstocktransferlist(stocktransferquery);
		List<Object> stocktransferfromList = inventoryLOVobj.getstocktransferfromlist(stocktransferfromquery);
		List<Object> stocktransferitemList = inventoryLOVobj.getstocktransferitemlist(stocktransferitemquery);
		 model.addObject("stocktransferList",stocktransferList);
		 model.addObject("stocktransferitemList",stocktransferitemList);
		 model.addObject("stocktransferfromList",stocktransferfromList);
		 model.setViewName("forms/transactions/stock/stocktransferprint");
		
		 return model;
	}
	
	@RequestMapping("/purchaseorderhistory")
	public ModelAndView gotoPurchaseorderHistory(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		StringBuilder query = new StringBuilder("SELECT a.purchase_id,a.purchase_code,a.purchase_refer_no,DATE_FORMAT(a.`created_date`,'%e/%c/%Y %h:%i')purchase_date,"
				+ "DATE_FORMAT(a.received_date,'%e/%c/%Y')received_date,CONCAT('Rs.',a.total_amt)totalamt,a.total_amt,a.paid_amt,a.supplier_id,a.purchase_notes,a.purchase_tnc,a.receive_status,"
				+ "a.purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,a.purchase_status,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = a.`purchase_code`) purchase_count,e.`purchase_code` requestcode"
				+ "  FROM hero_stock_purchase_status c ,"
				+ "hero_stock_supplier b,"
				+ " hero_stock_purchase a,`hero_user` d,`hero_stock_purchase_order` e  WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id AND d.`userid`=a.`CREATED_BY`  AND a.`purchase_request_id`=e.`purchase_id` "
				/*+ "AND d.`user_store_id`="+session.getAttribute("storeid")*/
				+ " GROUP BY `purchase_id`   ORDER BY `purchase_id` DESC ");
		log.info("purchase order query    "+query.toString());  
		
		List<Object> purchaseList = inventoryLOVobj.getPurchaseOrderHistroyList1(query.toString());
		log.info("purchaseListpurchaseList"+purchaseList);
		 ModelAndView model = new ModelAndView();
		  
		  model.addObject("purchaseList",purchaseList);
		  
		  model.setViewName("forms/transactions/purchaseorder/purchasehistory");
		
		return model;
	}
	
	@RequestMapping("/purchasereturnhistory")
	public ModelAndView gotoPurchasereturnHistory(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		StringBuilder query = new StringBuilder("SELECT `prhdr_id`,purchase_id,purchase_code,purchase_refer_no,DATE_FORMAT(a.`created_date`,'%e/%c/%Y %h:%i')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,CONCAT('Rs.',a.total_amt)totalamt,a.total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
				+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,purchase_status,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count FROM hero_stock_purchase_status c ,"
				+ "hero_stock_supplier b,`hero_stock_purchase_receive_hdr`e,"
				+ " hero_stock_purchase a,`hero_user` d WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id AND d.`userid`=a.`CREATED_BY` "
				/*+ "AND d.`user_store_id`="+session.getAttribute("storeid")*/
				+ " GROUP BY `purchase_id`   ORDER BY `purchase_id` DESC ");
		log.info("purchase order query    "+query.toString()); 
		
		List<Object> purchaseList = inventoryLOVobj.getPurchaseOrderHistroyList(query.toString());
		log.info("purchaseListpurchaseList"+purchaseList);
		 ModelAndView model = new ModelAndView();
		  
		  model.addObject("purchaseList",purchaseList);
		  
		  model.setViewName("forms/transactions/purchaseorder/purchasereturnhistory");
		
		return model;
	}
	
	@RequestMapping("/purchaserequesthistory")
	public ModelAndView purchaserequesthistory(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		StringBuilder query = new StringBuilder("SELECT `prhdr_id`,purchase_id,purchase_code,purchase_refer_no,DATE_FORMAT(a.`created_date`,'%e/%c/%Y %h:%i')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,CONCAT('Rs.',a.total_amt)totalamt,a.total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
				+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,purchase_status,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count FROM hero_stock_purchase_status c ,"
				+ "hero_stock_supplier b,`hero_stock_purchase_receive_hdr`e,"
				+ " hero_stock_purchase a,`hero_user` d WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id AND d.`userid`=a.`CREATED_BY` "
				/*+ "AND d.`user_store_id`="+session.getAttribute("storeid")*/
				+ " ORDER BY `purchase_id` DESC ");
		log.info("purchase order query    "+query.toString()); 
		
		List<Object> purchaseList = inventoryLOVobj.getPurchaseOrderHistroyList(query.toString());
		log.info("purchaseListpurchaseList"+purchaseList);
		 ModelAndView model = new ModelAndView();
		  
		  model.addObject("purchaseList",purchaseList);
		  
		  model.setViewName("forms/transactions/purchaseorder/purchaserequesthistory");
		
		return model;
	}
	@RequestMapping("/dishrequesthistory")
	public ModelAndView dishrequesthistory(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		String query ="SELECT `dish_setup_id`,`dish_name`,`dishtype_name`,`dish_count`,`dish_name_id` FROM `hero_stock_dishes_setup_hdr` a "
				+ "JOIN `hero_stock_dish_type` b ON a.`dish_type_id`=b.`dishtype_id` JOIN `hero_stock_dishes` c ON c.`dish_id`=a.`dish_name_id`"
				+ " ORDER BY `dish_setup_id` DESC";
		
		List<Object> dishList = inventoryLOVobj.getdishHistroyList(query);
		log.info("dishList"+dishList);
		 ModelAndView model = new ModelAndView();
		  
		  model.addObject("dishList",dishList);
		  
		  model.setViewName("forms/transactions/purchaseorder/dishrequesthistory");
		
		return model;
	}
	
	@RequestMapping("/addpurchaseorder")
	public ModelAndView gotoAddPurchaseOrder()
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/addpurchaseorder");*/
		
		String supplierQuery = "SELECT SUPPLIER_ID,SUPPLIER_FIRST_NAME  FROM hero_stock_supplier";
		String uomPackingQuery = "SELECT `hsuts_id` uompackingid,`hsuts_desc` uompackingdesc FROM `hero_stock_unit_type_setting` WHERE `hsuts_status` = 1";
		String referencenoquery="SELECT CONCAT(`F_HERO_ID_FORMAT`(1,(SELECT COALESCE(MAX(`SEQ_ID`),1)FROM `hero_stock_purchase_seq`)),(SELECT COALESCE(MAX(`SEQ_ID`)+1 ,1) FROM `hero_stock_purchase_seq`) )refno";
		String storequery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		String dishquery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` ";
		
		String poQuery = "SELECT DISTINCT(`purchase_code`),`purchase_id` FROM `hero_stock_purchase_order` a JOIN "
				+ "`hero_stock_purchase_order_request` b ON a.`purchase_code` = b.`pur_req_id` "
				+ " WHERE `purchase_raised` = 1  AND `purchase_status` = 7 AND  `order_raised` = 0";
		log.info("poQuery    "+poQuery);
		
		List<Object> supplierList = inventoryLOVobj.getLOVList(supplierQuery);
		  List<Object> uomPackingList = jdbcTemplate.queryForList(uomPackingQuery);
		  List<Object> referencenolist = inventoryLOVobj.refnoList(referencenoquery);
		  List<Object> storeList = inventoryLOVobj.getLOVList(storequery);
		  List<Object> dishList = inventoryLOVobj.getLOVList(dishquery);
		  List<Object> prList = inventoryLOVobj.getLOVListwithoutemptylist(poQuery);
		  log.info(supplierList);
		  log.info(uomPackingList);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("supplierList",supplierList);
		  model.addObject("uomPackingList",uomPackingList);
		  model.addObject("referencenolist",referencenolist);
		  model.addObject("storeList",storeList);
		  model.addObject("dishList",dishList);
		  model.addObject("prList",prList);
		  model.setViewName("forms/transactions/purchaseorder/addpurchaseorder");
		  
		  return model;
		  
	}
	
	@RequestMapping("/addpurchaserequest")
	public ModelAndView gotoAddPurchaseRequest()
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/addpurchaseorder");*/
		
		String supplierQuery = "SELECT SUPPLIER_ID,SUPPLIER_FIRST_NAME  FROM hero_stock_supplier";
		String uomPackingQuery = "SELECT `hsuts_id` uompackingid,`hsuts_desc` uompackingdesc FROM `hero_stock_unit_type_setting` WHERE `hsuts_status` = 1";
		String referencenoquery="SELECT CONCAT(`F_HERO_ID_FORMAT`(6,(SELECT COALESCE(MAX(`SEQ_ID`),1)FROM `hero_stock_purchase_order_seq`)),(SELECT COALESCE(MAX(`SEQ_ID`)+1 ,1) FROM `hero_stock_purchase_order_seq`) )refno";
		String storequery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		String dishquery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` ";
		List<Object> supplierList = inventoryLOVobj.getLOVList(supplierQuery);
		  List<Object> uomPackingList = jdbcTemplate.queryForList(uomPackingQuery);
		  List<Object> referencenolist = inventoryLOVobj.refnoList(referencenoquery);
		  List<Object> storeList = inventoryLOVobj.getLOVList(storequery);
		  List<Object> dishList = inventoryLOVobj.getLOVList(dishquery);
		  log.info(supplierList);
		  log.info(uomPackingList);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("supplierList",supplierList);
		  model.addObject("uomPackingList",uomPackingList);
		  model.addObject("referencenolist",referencenolist);
		  model.addObject("storeList",storeList);
		  model.addObject("dishList",dishList);
		  model.setViewName("forms/transactions/purchaseorder/addpurchaserequest");
		  
		  return model;
		  
	}
	
	
	@RequestMapping("/adddishrequest")
	public ModelAndView gotoAddDishRequest(@RequestParam("did")String did,@RequestParam("id")String id)
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/addpurchaseorder");*/
		  ModelAndView model = new ModelAndView();
		String dishnameQuery = "";
		String dishhdrQuery = "";
				if(did.equals("0")){
					dishnameQuery="SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` WHERE `status` = 1";
				}else{
					dishhdrQuery= "SELECT `dish_count` FROM `hero_stock_dishes_setup_hdr` WHERE `dish_setup_id`="+id;
					dishnameQuery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` WHERE `status` = 1 ORDER BY CASE WHEN `dish_id`= "+did+" THEN 0 ELSE `dish_id` END";
					  List<Object> uomhdrList = jdbcTemplate.queryForList(dishhdrQuery);
					  model.addObject("uomhdrList",uomhdrList.get(0));
				}
				
		String uomPackingQuery = "SELECT `hsuts_id` uompackingid,`hsuts_desc` uompackingdesc FROM `hero_stock_unit_type_setting` WHERE `hsuts_status` = 1";
		
		List<Object> dishnameList = inventoryLOVobj.getLOVList(dishnameQuery);
		  List<Object> uomPackingList = jdbcTemplate.queryForList(uomPackingQuery);
		
		  log.info(dishnameList);
		  log.info(uomPackingList);
		  
		
		  
		  model.addObject("dishnameList",dishnameList);
		  model.addObject("uomPackingList",uomPackingList);
		 
		
		  model.setViewName("forms/transactions/purchaseorder/adddishrequest");
		  
		  return model;
		  
	}
	
	@RequestMapping("/purchaseorderview")
	public ModelAndView gotoPurchaseorderView(@RequestParam("pid")String pid)
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/purchaseorderview");*/
		/*String viewpurchaseQuery = "SELECT `purchase_id`,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,COALESCE(FORMAT(`prhdr_grand_total_amt`,2),0) total_amt,"
				+ "CONCAT(supplier_first_name,supplier_last_name) supplier_name,prhdr_grand_total_amt grandamount,"
				+ "(SELECT `ps_status_name` FROM `hero_stock_purchase_status` WHERE `ps_id` = `prhdr_paid_status`)purchase_status_desc,"
				+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,purchase_id,`purchase_refer_no` referenceno,prhdr_paid_status  FROM "
				+ "hero_stock_supplier b,hero_stock_purchase_status c,hero_stock_purchase a "
				+ "LEFT JOIN `hero_stock_purchase_receive_hdr` ON `pur_req_id` = a.`purchase_code` "
				+ "WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id";*/
		
		String viewpurchaseQuery = "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
				+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,"
				+ "purchase_notes,purchase_tnc,receive_status,purchase_status,CONCAT(supplier_first_name) supplier_name,"
				+ "ps_status_name purchase_status_desc,(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id IN ("+pid+")) "
				+ "UNION ALL "
				+ "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,purchase_status,"
				+ "CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id NOT IN ("+pid+") ORDER BY purchase_id,`purchase_status` DESC LIMIT 0,9)"	;
		  log.info("viewpurchaseQuery      "+viewpurchaseQuery);
		  
		  String viewpurchaseFullQuery = "SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
					+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
					+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
					+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
					+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b,"
					+ " hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id ORDER BY purchase_id,`purchase_status` DESC"	;
		  
		  String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		  String paymenttypeQuery = "SELECT `ct_id`,`ct_name` FROM `hero_admin_cash_type`";
		  
		  List<Object> viewpurchaseorderList = inventoryLOVobj.getViewPurchaseList(viewpurchaseQuery);
		  List<Object> viewpurchaseorderFullList = inventoryLOVobj.getViewPurchaseList(viewpurchaseFullQuery);
		  List<Object> bankList = inventoryLOVobj.getLOVList(bankQuery);
		  List<Object> paymenttypeList = inventoryLOVobj.getLOVList(paymenttypeQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("viewpurchaseorderList",viewpurchaseorderList);
		  model.addObject("purchaselistsize",viewpurchaseorderFullList.size());
		  model.addObject("bankList",bankList);
		  model.addObject("paymenttypeList",paymenttypeList);
		  
		  String firstid = "0";
		  if(viewpurchaseorderList != null && viewpurchaseorderList.size() > 0)
		  {
			Map<String, Object> map = (Map<String, Object>) viewpurchaseorderList.get(0);
			firstid = (String) map.get("headerid")+"$$$"+(String) map.get("purchasecode");
		  }
		  
		  model.addObject("firstid",firstid);
		  
		  log.info("viewpurchaseorderList     "+viewpurchaseorderList);
		  
		  model.setViewName("forms/transactions/purchaseorder/purchaseorderview");
		  
		  return model;
	}
	
	
	@RequestMapping("/purchaserequestview")
	public ModelAndView purchaserequestview(@RequestParam("pid")String pid)
	{
		
		String viewpurchaseQuery = "SELECT `purchase_id`,`purchase_code`,`purchase_date`,`ps_status_name` "
				+ " FROM `hero_stock_purchase_order` a JOIN `hero_stock_purchase_status`b ON a.`purchase_status` =`ps_id`"
				+ " ORDER BY `purchase_id` ASC "	;
		
		  log.info("viewpurchaseQuery      "+viewpurchaseQuery);
		  
		  String viewpurchaseFullQuery = "SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
					+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
					+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
					+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
					+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b,"
					+ " hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id ORDER BY purchase_id,`purchase_status` DESC"	;
		  
		  String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		  String paymenttypeQuery = "SELECT `ct_id`,`ct_name` FROM `hero_admin_cash_type`";
		  
		  List<Object> viewpurchaseorderList = inventoryLOVobj.getViewPurchaseRequestList(viewpurchaseQuery);
		  List<Object> viewpurchaseorderFullList = inventoryLOVobj.getViewPurchaseList(viewpurchaseFullQuery);
		  List<Object> bankList = inventoryLOVobj.getLOVList(bankQuery);
		  List<Object> paymenttypeList = inventoryLOVobj.getLOVList(paymenttypeQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("viewpurchaseorderList",viewpurchaseorderList);
		  model.addObject("purchaselistsize",viewpurchaseorderFullList.size());
		  model.addObject("bankList",bankList);
		  model.addObject("paymenttypeList",paymenttypeList);
		  
		  String firstid = "0";
		  if(viewpurchaseorderList != null && viewpurchaseorderList.size() > 0)
		  {
			Map<String, Object> map = (Map<String, Object>) viewpurchaseorderList.get(0);
			firstid = (String) map.get("headerid")+"$$$"+(String) map.get("purchasecode");
		  }
		  
		  model.addObject("firstid",firstid);
		  
		  log.info("viewpurchaseorderList     "+viewpurchaseorderList);
		  
		  model.setViewName("forms/transactions/purchaseorder/purchaserequestview");
		  
		  return model;
	}
	
	@RequestMapping("/receivestock")
	public ModelAndView gotoReceiveStock(@RequestParam(value = "pid", required = false) String purchaseid, HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		log.info("purchaseid      "+purchaseid);
		/*String receivestockQuery = "SELECT purchase_code,product_name,unit,pur_quantity,prec_quantity,free_product_count,prec_batchno,"
				+ "DATE_FORMAT(prec_expiry_date,'%e/%c/%Y')prec_expiry_date,prec_purchase_price,prec_sales_price FROM hero_stock_product b,hero_stock_purchase_request c,"
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_admin_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
				+ "AND a.purchase_code = c.pur_req_id AND b.product_id = c.pur_product_id AND b.unit_type_id = d.unit_type_id";
		*/
		/*String receivestockQuery = "SELECT COALESCE(prhdr_id,0) prhdr_id,a.purchase_code,prec_dtl_id,"
				+ "COALESCE(DATE_FORMAT(prhdr_recv_date,'%e/%c/%Y'),DATE_FORMAT(NOW(),'%e/%c/%Y')) prhdr_recv_date,"
				+ "pur_quantity,pur_product_id,COALESCE(prec_recving_quantity,0)prec_recving_quantity,COALESCE(prec_free_count,0)prec_free_count,"
				+ "COALESCE(prhdr_shipping_cost,0)prhdr_shipping_cost,FORMAT(COALESCE(prhdr_shipping_cost,0),2)prhdr_shipping_cost_disp,"
				+ "COALESCE(prec_batchno,'')prec_batchno,"
				+ "COALESCE(DATE_FORMAT(prec_expiry_date,'%e/%c/%Y'),'')prec_expiry_date,COALESCE(prec_pur_price,0)prec_pur_price,"
				+ "COALESCE(prec_sel_price,0)prec_sel_price,FORMAT(COALESCE(prec_sub_total,0),2)prec_sub_total,COALESCE(prhdr_discount_type,0)prhdr_discount_type,"
				+ "COALESCE(prhdr_discount_value,0)prhdr_discount_value,COALESCE(prhdr_tax_id,0)prhdr_tax_id,product_name,product_id,unit,"
				+ "COALESCE(prhdr_notes,'')prhdr_notes,FORMAT(prhdr_total_amt,2)prhdr_total_amt,FORMAT(`prhdr_tax_amt`,2)prhdr_tax_amt,"
				+ "FORMAT(`prhdr_discount_amt`,2)prhdr_discount_amt,FORMAT(`prhdr_grand_total_amt`,2)prhdr_grand_total_amt,"
				+ "FORMAT(`prhdr_total_with_disc`,2)prhdr_total_with_disc,prhdr_discount_type FROM `hero_stock_purchase` A JOIN `hero_stock_purchase_request` B "
				+ "ON `purchase_code` = B.`pur_req_id` LEFT JOIN `hero_stock_purchase_receive_hdr` C ON C.`pur_req_id` = A.`purchase_code` "
				+ "LEFT JOIN `hero_stock_purchase_received_dtl` D ON C.`prhdr_id` = D.`prec_hdr_id` AND B.`pur_product_id` = D.`prec_product_id` "
				+ "LEFT JOIN `hero_stock_product` E ON E.`product_id` = B.`pur_product_id` LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id` "
				+ "WHERE `purchase_id` = "+purchaseid;*/
		
		String receivestockQuery = "SELECT  F.`unit` netunitdesc,purchase_id,0 prhdr_id,purchase_code,0 prec_dtl_id,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,`opttype`,`cgst`,`sgst`,pur_quantity,pur_product_id,"
				+ "0 prec_recving_quantity,0 prec_free_count,0 prhdr_shipping_cost,0.00 prhdr_shipping_cost_disp,`hsncode`,'' prec_batchno,'' prec_barcode,'' prec_expiry_date,"
				+ "COALESCE(`unit_rate`,0) prec_pur_price,0 prec_sel_price,0 `prec_tax_per`, FORMAT(COALESCE(0,0),2)prec_sub_total,'%' prhdr_discount_type,COALESCE(0,0)prhdr_discount_value,"
				+ "COALESCE(0,0)prhdr_tax_id,product_name,product_id,unit,'' prhdr_notes,FORMAT(0,2)prhdr_total_amt,FORMAT(0,2)prhdr_tax_amt,"
				+ "FORMAT(0,2)prhdr_discount_amt,FORMAT(0,2)prhdr_grand_total_amt,FORMAT(0,2)prhdr_total_with_disc,FORMAT(0,2)prhdr_total_amt_disp,'exclusive' `prhdr_inclusive`, "
				+ "COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C"
				+ " WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND B.`pur_req_id` = C.`purchase_code` AND `purchase_id` = "+purchaseid
				+ " ),0) RECVDQTY,(SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`)` orderedfulluom`,`pur_full_qty` orderedfullqty,"
				+ "hsuts_id ordereduompackingid,(SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_loose_uom`)`orderedlooseuom`,"
				
				+ "`pur_loose_qty` orderedlooseqty, (SELECT `unit` FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B "
				+ " ON `purchase_code` = B.`pur_req_id` JOIN `hero_stock_unit_type_config` c  ON "
				+ " c.`hsuts_id` = B.`hsuts_id` JOIN `hero_admin_unit_type` d ON d.`unit_type_id` = c.`hsuts_full_uom` "
				+ " WHERE `purchase_id` =  14 LIMIT 1)`receivedfulluom` , 0 `prec_full_qty` , "
				+ "(SELECT `unit` FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B "
				+ " ON `purchase_code` = B.`pur_req_id` JOIN `hero_stock_unit_type_config` c  ON "
				+ " c.`hsuts_id` = B.`hsuts_id` JOIN `hero_admin_unit_type` d ON d.`unit_type_id` = c.`hsuts_loose_uom` "
				+ " WHERE `purchase_id` =  14 LIMIT 1)`receivedlooseuom` , 0 `prec_loose_qty`,"
				+ "CASE WHEN g.`TAX_TYPE`= 'P' THEN CONCAT(g.TAX_NAME,'(',g.TAX_RATE,'%)')ELSE "
				+ " CONCAT(g.TAX_NAME,'(',g.TAX_RATE,')') END AS taxlabel1,CASE WHEN h.`TAX_TYPE`= 'P' THEN "
				+ " CONCAT(h.TAX_NAME,'(',h.TAX_RATE,'%)')ELSE CONCAT(h.TAX_NAME,'(',h.TAX_RATE,')') END AS taxlabel2,`pur_cgst`,`pur_sgst` "

				+ " FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B ON `purchase_code` = B.`pur_req_id` LEFT JOIN "
				+ "`hero_stock_product` E ON E.`product_id` = B.`pur_product_id` LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id`"
				+ " LEFT JOIN `hero_admin_tax`g ON g.`TAX_ID` = E.`cgst` LEFT JOIN `hero_admin_tax`h ON h.`TAX_ID` = E.`sgst` WHERE "
				+ "`purchase_id` =  "+purchaseid+"  AND pur_quantity != COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,"
				+ "`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND "
				+ "B.`pur_req_id` = C.`purchase_code`  AND `purchase_id` = "+purchaseid+ " ),0)";
		
		
		/*String receivestockQuery = "SELECT purchase_id,0 prhdr_id,purchase_code,0 prec_dtl_id,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,`opttype`,`cgst`,`sgst`,"
				+ "pur_quantity"
				priya start
				+"(pur_quantity-COALESCE((SELECT prec_recving_quantity  FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C ,"
				+ " `hero_stock_unit_type_config` d WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id`  AND B.`pur_req_id` = C.`purchase_code` "
				+ "  AND d.`hsuts_id`= A.`hsuts_id`  AND `purchase_id` = "+purchaseid+ " ),0))pur_quantity,"
				priya end
				+ "pur_product_id,"
				+ "0 prec_recving_quantity,0 prec_free_count,0 prhdr_shipping_cost,0.00 prhdr_shipping_cost_disp,`hsncode`,'' prec_batchno,'' prec_barcode,'' prec_expiry_date,"
				+ "COALESCE(`unit_rate`,0) prec_pur_price,0 prec_sel_price,0 `prec_tax_per`, FORMAT(COALESCE(0,0),2)prec_sub_total,'%' prhdr_discount_type,COALESCE(0,0)prhdr_discount_value,"
				+ "COALESCE(0,0)prhdr_tax_id,product_name,product_id,unit,'' prhdr_notes,FORMAT(0,2)prhdr_total_amt,FORMAT(0,2)prhdr_tax_amt,"
				+ "FORMAT(0,2)prhdr_discount_amt,FORMAT(0,2)prhdr_grand_total_amt,FORMAT(0,2)prhdr_total_with_disc,FORMAT(0,2)prhdr_total_amt_disp,'exclusive' `prhdr_inclusive`, "
				+ "COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C"
				+ " WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND B.`pur_req_id` = C.`purchase_code` AND `purchase_id` = "+purchaseid
				+ " ),0) RECVDQTY,(SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`)` orderedfulluom`,"
				+ "`pur_full_qty` orderedfullqty,"
				priya start
				+"COALESCE((SELECT FLOOR((FLOOR((((`pur_full_qty`*`hsuts_loose_qty`)+`pur_loose_qty`)-`prec_recving_quantity`)/`hsuts_loose_qty`)*`hsuts_loose_qty`)/`hsuts_loose_qty`)"
				+ " FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C ,"
				+ " `hero_stock_unit_type_config` d WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id`  AND B.`pur_req_id` = C.`purchase_code` "
				+ "  AND d.`hsuts_id`= A.`hsuts_id`  AND `purchase_id` = "+purchaseid+ " ),0)orderedfullqty,"
				priya end
				+ "hsuts_id ordereduompackingid,(SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_loose_uom`)`orderedlooseuom`,"
				
				+ "`pur_loose_qty` orderedlooseqty,"
				priya start
					+ "COALESCE((SELECT (((`pur_full_qty`*`hsuts_loose_qty`)+`pur_loose_qty`)-`prec_recving_quantity`)-(FLOOR((((`pur_full_qty`*`hsuts_loose_qty`)+`pur_loose_qty`)-`prec_recving_quantity`)/`hsuts_loose_qty`)*`hsuts_loose_qty`)"
					+ "  FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C ,"
					+ " `hero_stock_unit_type_config` d WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id`  AND B.`pur_req_id` = C.`purchase_code` "
					+ "  AND d.`hsuts_id`= A.`hsuts_id`    AND `purchase_id` = "+purchaseid+ " ),0)orderedlooseqty,"
				priya end
			
				+ " (SELECT `unit` FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B "
				+ " ON `purchase_code` = B.`pur_req_id` JOIN `hero_stock_unit_type_config` c  ON "
				+ " c.`hsuts_id` = B.`hsuts_id` JOIN `hero_admin_unit_type` d ON d.`unit_type_id` = c.`hsuts_full_uom` "
				+ " WHERE `purchase_id` =  14 LIMIT 1)`receivedfulluom` , 0 `prec_full_qty` , "
				+ "(SELECT `unit` FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B "
				+ " ON `purchase_code` = B.`pur_req_id` JOIN `hero_stock_unit_type_config` c  ON "
				+ " c.`hsuts_id` = B.`hsuts_id` JOIN `hero_admin_unit_type` d ON d.`unit_type_id` = c.`hsuts_loose_uom` "
				+ " WHERE `purchase_id` =  14 LIMIT 1)`receivedlooseuom` , 0 `prec_loose_qty`,"
				+ "CASE WHEN g.`TAX_TYPE`= 'P' THEN CONCAT(g.TAX_NAME,'(',g.TAX_RATE,'%)')ELSE "
				+ " CONCAT(g.TAX_NAME,'(',g.TAX_RATE,')') END AS taxlabel1,CASE WHEN h.`TAX_TYPE`= 'P' THEN "
				+ " CONCAT(h.TAX_NAME,'(',h.TAX_RATE,'%)')ELSE CONCAT(h.TAX_NAME,'(',h.TAX_RATE,')') END AS taxlabel2 "

				+ " FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B ON `purchase_code` = B.`pur_req_id` LEFT JOIN "
				+ "`hero_stock_product` E ON E.`product_id` = B.`pur_product_id` LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id`"
				+ " LEFT JOIN `hero_admin_tax`g ON g.`TAX_RATE` = E.`cgst` LEFT JOIN `hero_admin_tax`h ON h.`TAX_RATE` = E.`sgst` WHERE "
				+ "`purchase_id` =  "+purchaseid+"  AND pur_quantity != COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,"
				+ "`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND "
				+ "B.`pur_req_id` = C.`purchase_code`  AND `purchase_id` = "+purchaseid+ " ),0)";*/
		

		String otherpurchaseDetailsQuery="";
		log.info("receivestockQuery   "+receivestockQuery);
		String taxQuery = "SELECT TAX_ID,TAX_NAME,TAX_RATE,tax_type FROM hero_admin_tax";
		String pohistoryQuery = "SELECT `prhdr_id`,`pur_req_id`,  `purchase_date`, `purchase_refer_no`,`prhdr_recv_date`, COALESCE(`prhdr_paid_amount`, 0) prhdr_paid_amount "
				+ "FROM `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` b ON a.`pur_req_id` = b.`purchase_code` WHERE  "
				+ "`purchase_id` = '"+purchaseid+"'";
		
		String completedorderQuery="SELECT `product_name`,`pur_full_qty`, (SELECT `unit`FROM `hero_admin_unit_type` G WHERE"
				+ " G.`unit_type_id` = `pur_full_uom`) as full_uom, `pur_loose_qty`,(SELECT `unit`FROM `hero_admin_unit_type` "
				+ "G WHERE G.`unit_type_id` = `pur_loose_uom`) as loose_uom, `pur_quantity`, `unit` FROM `hero_stock_purchase_request` A "
				+ "JOIN `hero_stock_purchase` B ON A.`pur_req_id` = B.`purchase_code`"
		                           +"  JOIN `hero_stock_product` E ON E.`product_id` = A.`pur_product_id` JOIN `hero_admin_unit_type` F "
				                   +"ON F.`unit_type_id` = E.`unit_type_id` WHERE `purchase_id` = '"+purchaseid+"'";
		log.info("completedorderQuery      "+completedorderQuery);
		
		String pendingorderQuery="SELECT `product_name`,`pur_quantity`, SUM(`prec_recving_quantity`) prec_recving_quantity,(`pur_quantity` - SUM(`prec_recving_quantity`)) qty,`unit`"
				+ "  FROM `hero_stock_purchase` a JOIN `hero_stock_purchase_receive_hdr` b ON a.`purchase_code` = b.`pur_req_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` c ON c.`prec_hdr_id` = b.`prhdr_id`  JOIN `hero_stock_product` d ON"
				+ "  d.`product_id` = c.`prec_product_id` JOIN `hero_stock_purchase_request` e ON e.`pur_req_id` = a.`purchase_code` "
				+ "AND  e.`pur_product_id` = c.`prec_product_id`  JOIN `hero_admin_unit_type` F  ON F.`unit_type_id` =  d.`unit_type_id`"
				+ "WHERE `purchase_id` = '"+purchaseid+"' GROUP BY d.`product_id`";
		log.info("pendingorderQuery      "+pendingorderQuery);
		
		String viewpurchaseQuery = "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
				+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,"
				+ "purchase_notes,purchase_tnc,receive_status,purchase_status,CONCAT(supplier_first_name) supplier_name,"
				+ "ps_status_name purchase_status_desc,(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id IN ("+purchaseid+")) "
				+ "UNION ALL "
				+ "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,purchase_status,"
				+ "CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id NOT IN ("+purchaseid+") ORDER BY purchase_id,`purchase_status` DESC LIMIT 0,9)"	;
		  log.info("viewpurchaseQuery      "+viewpurchaseQuery);
		  
		 		  
		  List<Object> viewpurchaseorderList = inventoryLOVobj.getViewPurchaseList(viewpurchaseQuery);

		  String firstid = "0";
		  if(viewpurchaseorderList != null && viewpurchaseorderList.size() > 0)
		  {
			Map<String, Object> map = (Map<String, Object>) viewpurchaseorderList.get(0);
			firstid = (String) map.get("headerid")+"$$$"+(String) map.get("purchasecode");
			 
		  }
		 
		 String termsquery=" SELECT `terms_id`,`terms_desc` FROM `hero_terms_conditions`";
		 
		  log.info("termsquery   "+termsquery);
		  List<Object> taxList = inventoryLOVobj.getTaxList(taxQuery);
		  List<Object> receivestockList = inventoryLOVobj.getViewReceiveStockList(receivestockQuery,addpurchaseorderDAOobj);
		  List<Object> pohistoryList = inventoryLOVobj.executeQuery(pohistoryQuery);
		  List<Object> completeorderList = inventoryLOVobj.executeQuery(completedorderQuery);
		  List<Object> pendingorderitemList = inventoryLOVobj.executeQuery(pendingorderQuery);
		  List<Object> termsList = inventoryLOVobj.getLOVList(termsquery);
		  
		  ModelAndView model = new ModelAndView();
		
		  model.addObject("completeorderList",completeorderList);
		  model.addObject("pendingorderitemList",pendingorderitemList);
		  model.addObject("receivestockList",receivestockList);
		  model.addObject("taxList",taxList);
		  model.addObject("pohistoryList",pohistoryList);
		  model.addObject("view",0);
		  model.addObject("termsList",termsList);
		  log.info("termsList   "+termsList);
		 
		  model.addObject("viewpurchaseorderList",viewpurchaseorderList);
		  model.addObject("firstid",firstid);
		  
		  double totalamount = 0;
		  
		  if(receivestockList != null && receivestockList.size() > 0)
		  {
			  
			  Map<String, Object> recStockObj = (Map<String, Object>) receivestockList.get(0); 
			 
			  model.addObject("purchaseorderno",recStockObj.get("purchaseorderno"));
			  model.addObject("oprn",recStockObj.get("oprn"));
			  model.addObject("headerid",recStockObj.get("headerid"));
			  model.addObject("receivedate",recStockObj.get("receivedate"));
			  model.addObject("discounttype",recStockObj.get("discounttype"));
			  model.addObject("discountvalue",recStockObj.get("discountvalue"));
			  model.addObject("orderedtax",recStockObj.get("orderedtax"));
			  model.addObject("shippingcost",recStockObj.get("shippingcost"));
			  model.addObject("notes",recStockObj.get("notes"));
			  model.addObject("totalamount",recStockObj.get("totalamount"));
			  model.addObject("taxamount",recStockObj.get("taxamount"));
			  model.addObject("discountamount",recStockObj.get("discountamount"));
			  model.addObject("grandtotalamount",recStockObj.get("grandtotalamount"));
			  model.addObject("totalwithdiscamount",recStockObj.get("totalwithdiscamount"));
			  model.addObject("discounttype",recStockObj.get("prhdr_discount_type"));
			  model.addObject("shippingcostdisp",recStockObj.get("shippingcostdisp"));
			  model.addObject("totalamountdisp",recStockObj.get("totalamountdisp"));
			  model.addObject("taxid",recStockObj.get("orderedtax"));
			  model.addObject("purchaseid",recStockObj.get("purchaseid"));
			  model.addObject("cgst",recStockObj.get("cgst"));
			  model.addObject("sgst",recStockObj.get("sgst"));
			  model.addObject("opttype",recStockObj.get("opttype"));
			  model.addObject("hsncode",recStockObj.get("hsncode"));
			  /*Map<String, Object> map = (Map<String, Object>) recStockObj.get("uomsel");
			  model.addObject("fulluomPackingList",(map.get("fulluomPackingList") == null ? new ArrayList() : map.get("fulluomPackingList")));
			  model.addObject("looseuomPackingList",(map.get("looseuomPackingList") == null ? new ArrayList() : map.get("looseuomPackingList")));*/
		  }
		  
		  String userid = String.valueOf(session.getAttribute("uid"));
			String currencyDetailQuery = "SELECT `gcs_html_code`,`CURR_DEC_FORMAT` FROM `hero_admin_currency` A  JOIN `hero_user` B ON B.`currencyid` = A.`currency_id`"
					+ " JOIN `hero_global_currency_symbols` C ON C.`gcs_id` = A.`CURR_SYMBOL`  WHERE `userid` = '"+userid+"'";
			log.info("currencyDetailQuery      "+currencyDetailQuery);
			@SuppressWarnings("unchecked")
			List<Object> currencyDetailList = jdbcTemplate.query(currencyDetailQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("currencycode", rs.getString("gcs_html_code"));
					map.put("currencydecimal", rs.getString("CURR_DEC_FORMAT"));
					
					return map;
				}
			});
			log.info("currencyDetailList      "+currencyDetailList+"userid"+session.getAttribute("uid"));
			
			String currency_code = "",currency_decimal="";
			if(currencyDetailList != null && currencyDetailList.size() > 0)
			{
				Map<String, Object> currencyMap = (Map<String, Object>) currencyDetailList.get(0);
				currency_code = (String) currencyMap.get("currencycode");
				currency_decimal = (String) currencyMap.get("currencydecimal");
			}
			
			String paymenttypeQuery = "SELECT `ct_id`,`ct_name` FROM `hero_admin_cash_type`";

			List<Object> paymenttypeList = inventoryLOVobj.getLOVList(paymenttypeQuery);

			model.addObject("paymenttypeList",paymenttypeList);
			
			log.info("currency code "+ currency_code);
			model.addObject("currencycode",currency_code);
			model.addObject("currencydecimal",currency_decimal);
		 
		  model.setViewName("forms/transactions/purchaseorder/receivestockview");
		  return model;
		
		/*return new ModelAndView("forms/transactions/purchaseorder/receivestockview");*/
	}
	
	@RequestMapping("/receivestockedit")
	public ModelAndView gotoReceiveStockEdit(@RequestParam(value = "pid", required = false) String purchaseid)
	{
		log.info("purchaseid      "+purchaseid);
		
		/*String receivestockQuery = "SELECT `purchase_id`,`prhdr_id`,`purchase_code`,`prec_dtl_id`,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,"
				+ "`prec_product_id`,`product_name`,pur_quantity,pur_product_id,prec_recving_quantity,prec_free_count,prhdr_shipping_cost,"
				+ "FORMAT(COALESCE(prhdr_shipping_cost,0),2)prhdr_shipping_cost_disp,prec_batchno,prec_barcode,DATE_FORMAT(prec_expiry_date,'%e/%c/%Y') prec_expiry_date,"
				+ "prec_pur_price,prec_sel_price,FORMAT(COALESCE(prec_sub_total,0),2)prec_sub_total,prhdr_discount_type,FORMAT(prhdr_total_amt,2)prhdr_total_amt_disp,"
				+ "COALESCE(prhdr_discount_value,0)prhdr_discount_value,COALESCE(prhdr_tax_id,0)prhdr_tax_id,product_name,product_id,unit,prhdr_notes,"
				+ "prhdr_total_amt,prhdr_tax_amt,prhdr_shipping_cost,prhdr_discount_amt,prhdr_grand_total_amt,prhdr_total_with_disc,"
				+ "COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` sub WHERE  `prec_product_id` = `product_id` AND "
				+ "`prhdr_id` = sub.`prec_hdr_id`),0) RECVDQTY FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_receive_hdr` B ON "
				+ "A.`purchase_code` = B.`pur_req_id` LEFT JOIN `hero_stock_purchase_received_dtl` C ON B.`prhdr_id` = C.`prec_hdr_id` "
				+ "JOIN `hero_stock_purchase_request` D ON A.`purchase_code` = D.`pur_req_id` AND D.`pur_product_id` = C.`prec_product_id` "
				+ "JOIN `hero_stock_product` E ON C.`prec_product_id` = E.`product_id` "
				+ "LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id` "
				+ "WHERE `prec_hdr_id` = "+purchaseid;*/
		
		String receivestockQuery = "SELECT `purchase_id`,`prhdr_id`,`purchase_code`,`prec_dtl_id`,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,`opttype`,C.`prec_tax_per`cgst,E.`sgst`,"
				+ "`prec_product_id`,`prec_tax_per`,`product_name`,pur_quantity,pur_product_id,prec_recving_quantity,E.`hsncode`,prec_free_count,prhdr_shipping_cost,"
				+ "FORMAT(COALESCE(prhdr_shipping_cost,0),2)prhdr_shipping_cost_disp,prec_batchno,prec_barcode,DATE_FORMAT(prec_expiry_date,'%e/%c/%Y') prec_expiry_date,"
				+ "prec_pur_price,prec_sel_price,FORMAT(COALESCE(prec_sub_total,0),2)prec_sub_total,prhdr_discount_type,FORMAT(prhdr_total_amt,2)prhdr_total_amt_disp,"
				+ "COALESCE(prhdr_discount_value,0)prhdr_discount_value,COALESCE(prhdr_tax_id,0)prhdr_tax_id,product_name,product_id,unit,prhdr_notes,"
				+ "prhdr_total_amt,prhdr_tax_amt,prhdr_shipping_cost,prhdr_discount_amt,prhdr_grand_total_amt,`prhdr_inclusive`,prhdr_total_with_disc,"
				+ "COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` sub WHERE  `prec_product_id` = `product_id` AND "
				+ "`prhdr_id` = sub.`prec_hdr_id`),0) RECVDQTY,"
				+ " (SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`)` "
				+ " orderedfulluom`,`pur_full_qty` orderedfullqty, D.`hsuts_id` ordereduompackingid,"
				+ " (SELECT `unit`FROM `hero_admin_unit_type` G  WHERE G.`unit_type_id` = `pur_loose_uom`)"
				+ " `orderedlooseuom`, `pur_loose_qty` orderedlooseqty, (SELECT `unit`FROM  `hero_admin_unit_type` G "
				+ "WHERE G.`unit_type_id` = `prec_full_uom`)` receivedfulluom`,`prec_full_qty` prec_full_qty,  "
				+ " (SELECT `unit`FROM `hero_admin_unit_type` G WHERE"
				+ " G.`unit_type_id` = `prec_loose_uom`) `receivedlooseuom`, `prec_loose_qty` prec_loose_qty,"
				+ " CASE WHEN g.`TAX_TYPE`= 'P' THEN CONCAT(g.TAX_NAME,'(',g.TAX_RATE,'%)')ELSE "
				+ " CONCAT(g.TAX_NAME,'(',g.TAX_RATE,')') END AS taxlabel1,CASE WHEN h.`TAX_TYPE`= 'P' THEN "
				+ " CONCAT(h.TAX_NAME,'(',h.TAX_RATE,'%)')ELSE CONCAT(h.TAX_NAME,'(',h.TAX_RATE,')') END AS taxlabel2"
				+ " FROM `hero_stock_purchase` A LEFT JOIN  `hero_stock_purchase_receive_hdr` B ON "
				+ " A.`purchase_code` = B.`pur_req_id` LEFT JOIN `hero_stock_purchase_received_dtl` C ON B.`prhdr_id` = C.`prec_hdr_id` "
				+ " JOIN `hero_stock_purchase_request` D ON A.`purchase_code` = D.`pur_req_id` AND D.`pur_product_id` = C.`prec_product_id` "
				+ " JOIN `hero_stock_product` E ON C.`prec_product_id` = E.`product_id` "
				+ " LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id`"
				+ "    JOIN `hero_admin_tax`g ON g.`TAX_RATE` = C.`prec_tax_per` JOIN `hero_admin_tax`h ON h.`TAX_RATE` = C.`sgst` "
				+ " WHERE `prec_hdr_id` = "+purchaseid;	
		
		
	/*	String completedorderQuery="SELECT `product_name`,`pur_full_qty`, (SELECT `unit`FROM `hero_admin_unit_type` G WHERE"
				+ " G.`unit_type_id` = `pur_full_uom`) as full_uom, `pur_loose_qty`,(SELECT `unit`FROM `hero_admin_unit_type` "
				+ "G WHERE G.`unit_type_id` = `pur_loose_uom`) as loose_uom, `pur_quantity`, `unit` FROM `hero_stock_purchase_request` A "
				+ "JOIN `hero_stock_purchase` B ON A.`pur_req_id` = B.`purchase_code`"
		                           +"  JOIN `hero_stock_product` E ON E.`product_id` = A.`pur_product_id` JOIN `hero_admin_unit_type` F "
				                   +"ON F.`unit_type_id` = E.`unit_type_id` WHERE `purchase_id` = '"+purchaseid+"'";*/
		
		String completedorderQuery=" SELECT `product_name`,`pur_full_qty`, (SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_full_uom`) AS full_uom, "
				+ " `pur_loose_qty`,(SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` = `pur_loose_uom`) AS loose_uom,  "
				+ "`pur_quantity`, `unit` FROM `hero_stock_purchase_request` A  "
				+ "JOIN `hero_stock_purchase_receive_hdr`G ON G.`pur_req_id` =  A.`pur_req_id` "
				+ "JOIN `hero_stock_product` E ON E.`product_id` = A.`pur_product_id` "
				+ "JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id` WHERE `prhdr_id` = '"+purchaseid+"'";
		log.info("completedorderQuery      "+completedorderQuery);
		
		String pendingorderQuery="SELECT `product_name`,`pur_quantity`, SUM(`prec_recving_quantity`) prec_recving_quantity,(`pur_quantity` - SUM(`prec_recving_quantity`)) qty,`unit`"
				+ "  FROM `hero_stock_purchase` a JOIN `hero_stock_purchase_receive_hdr` b ON a.`purchase_code` = b.`pur_req_id` "
				+ "JOIN `hero_stock_purchase_received_dtl` c ON c.`prec_hdr_id` = b.`prhdr_id`  JOIN `hero_stock_product` d ON"
				+ "  d.`product_id` = c.`prec_product_id` JOIN `hero_stock_purchase_request` e ON e.`pur_req_id` = a.`purchase_code` "
				+ "AND  e.`pur_product_id` = c.`prec_product_id`  JOIN `hero_admin_unit_type` F  ON F.`unit_type_id` =  d.`unit_type_id`"
				/*+ "WHERE `purchase_id` = '"+purchaseid+"' GROUP BY d.`product_id`";*/
				+ "WHERE `prhdr_id` = '"+purchaseid+"' GROUP BY d.`product_id`";
		log.info("pendingorderQuery      "+pendingorderQuery);
		
		log.info("receivestockQuery   "+receivestockQuery);
		String taxQuery = "SELECT TAX_ID,TAX_NAME,TAX_RATE,tax_type FROM hero_admin_tax";

		  List<Object> taxList = inventoryLOVobj.getTaxList(taxQuery);
		  List<Object> receivestockList = inventoryLOVobj.getViewReceiveStockList(receivestockQuery,addpurchaseorderDAOobj);
		  List<Object> completeorderList = inventoryLOVobj.executeQuery(completedorderQuery);
		  List<Object> pendingorderitemList = inventoryLOVobj.executeQuery(pendingorderQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("receivestockList",receivestockList);
		  model.addObject("taxList",taxList);
		  model.addObject("completeorderList",completeorderList);
		  model.addObject("pendingorderitemList",pendingorderitemList);
		  
		  String firstid = "0";
		  if(receivestockList != null && receivestockList.size() > 0)
		  {
			Map<String, Object> map = (Map<String, Object>) receivestockList.get(0);
			firstid = (String) map.get("purchaseid")+"$$$"+(String) map.get("purchasecode");
		  }
		  
		  model.addObject("firstid",firstid);
		  
		  if(receivestockList != null && receivestockList.size() > 0)
		  {
			  
			  Map<String, Object> recStockObj = (Map<String, Object>) receivestockList.get(0); 
			  model.addObject("purchaseorderno",recStockObj.get("purchaseorderno"));
			  model.addObject("purchasecode",recStockObj.get("purchasecode"));
			  model.addObject("oprn",recStockObj.get("oprn"));
			  model.addObject("purchaseid",recStockObj.get("purchaseid"));
			  model.addObject("headerid",recStockObj.get("headerid"));
			  model.addObject("receivedate",recStockObj.get("receivedate"));
			  model.addObject("discounttype",recStockObj.get("discounttype"));
			  model.addObject("discountvalue",recStockObj.get("discountvalue"));
			  model.addObject("orderedtax",recStockObj.get("orderedtax"));
			  model.addObject("shippingcost",recStockObj.get("shippingcost"));
			  model.addObject("notes",recStockObj.get("notes"));
			  model.addObject("totalamount",recStockObj.get("totalamount"));
			  model.addObject("taxamount",recStockObj.get("taxamount"));
			  model.addObject("discountamount",recStockObj.get("discountamount"));
			  model.addObject("grandtotalamount",recStockObj.get("grandtotalamount"));
			  model.addObject("totalwithdiscamount",recStockObj.get("totalwithdiscamount"));
			  model.addObject("discounttype",recStockObj.get("discounttype"));
			  model.addObject("shippingcostdisp",recStockObj.get("shippingcostdisp"));
			  model.addObject("totalamountdisp",recStockObj.get("totalamountdisp"));
			  model.addObject("taxid",recStockObj.get("orderedtax"));
			  model.addObject("purchaseid",recStockObj.get("purchaseid"));
			  model.addObject("hsncode",recStockObj.get("hsncode"));
			  model.addObject("cgst",recStockObj.get("cgst"));
			  model.addObject("sgst",recStockObj.get("sgst"));
			  model.addObject("inorex",recStockObj.get("inorex"));
			  model.addObject("opttype",recStockObj.get("opttype"));
			  
			  Map<String, Object> map = (Map<String, Object>) (recStockObj.get("uomsel"));
			  List<Object> fulluomPackingList = (List<Object>) map.get("fulluomPackingList");
			  List<Object> looseuomPackingList = (List<Object>) map.get("looseuomPackingList");
			  log.info("fulluomPackingList  "+fulluomPackingList);
			  log.info("looseuomPackingList   "+looseuomPackingList);
			  
			  model.addObject("fulluomPackingList",fulluomPackingList);
			  model.addObject("looseuomPackingList",looseuomPackingList);
		  }
		  
		  
		  model.setViewName("forms/transactions/purchaseorder/receivestockview");
		  
		  return model;
		
		/*return new ModelAndView("forms/transactions/purchaseorder/receivestockview");*/
	}
	
	@RequestMapping("/purchasereturn")
	public ModelAndView gotoPurchaseReturn(@RequestParam(value = "pid", required = false) String purchaseid)
	{
		log.info("purchaseid      "+purchaseid);
		 
		
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.setViewName("forms/transactions/purchaseorder/purchasereturn");
		  
		  return model;
		
		/*return new ModelAndView("forms/transactions/purchaseorder/receivestockview");*/
	}
	
	@RequestMapping("/bills")
	public ModelAndView gotoBillsHistory()
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/bills");*/
			
		String supplierQuery = "SELECT `supplier_id`,`supplier_first_name`suppliername,`opttype`,`paymode`,`reqdays` FROM `hero_stock_supplier`";
		String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		String paymenttypeQuery = "SELECT `ct_id`,`ct_name` FROM `hero_admin_cash_type`";
		  
		List<Object> bankList = inventoryLOVobj.getLOVList(bankQuery);
		List<Object> paymenttypeList = inventoryLOVobj.getLOVList(paymenttypeQuery);
		List<Object> supplierList = inventoryLOVobj.getsupplierList(supplierQuery);
		  
		ModelAndView model = new ModelAndView();
		  
		model.addObject("bankList",bankList);
		model.addObject("paymenttypeList",paymenttypeList);
		model.addObject("supplierList",supplierList);
		  
		model.setViewName("forms/transactions/purchaseorder/bills");
		  
		return model;
		  
	
	}
	
	@RequestMapping("/stocktransferhistory")
	public ModelAndView gotoStockTransferHistory(HttpServletRequest httpRequest)
	{
		
		ModelAndView model = new ModelAndView();
		StringBuilder query = new StringBuilder("SELECT transfer_id,delivery_status,`transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y %h:%i')transfer_date,"
				+ "a.`status_id`,C.`status_desc`,`ord_ref_no`,`order_id`,`sub_sequent`,"
				+ "(SELECT SUM(`tobe_recvd_prod_count`) FROM `hero_stock_transfer_product` SUB WHERE SUB.`transfer_id` = a.`transfer_id`)PROD_COUNT,"
				+ "(SELECT (COALESCE((SUM(`tobe_recvd_prod_count`*`product_rate`)),0)) FROM `hero_stock` STK,`hero_stock_transfer_product` stk_prod,`hero_stock_transfer` TXR"
				+ " WHERE STK.`product_id` = stk_prod.`product_id` AND stk_prod.`transfer_id`=TXR.`transfer_id` AND stk_prod.`batch_id` = STK.`batch_id` "
				+ " AND TXR.`transfer_id` = a.`transfer_id`)AMOUNT,`store_name`,CURR_SYMBOL `currency`,`gcs_html_code`,order_approved FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C "
				+ "ON a.`delivery_status` = C.`status_id`"
				+ " LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id` LEFT JOIN `hero_admin_currency` d ON d.`currency_id` = s.`currency_id`"
				+ "LEFT JOIN `hero_global_currency_symbols` f ON f.`gcs_id`=d.CURR_SYMBOL LEFT JOIN  `hero_order_request` g ON g.`ord_req_id`=a.`order_id`");
		
		int usertpe = (int)httpRequest.getSession().getAttribute("usertype");
		/*if(usertpe > 2)
		{
			query.append("JOIN `hero_user` e ON e.`user_store_id` = s.`store_id` AND `userid` = "+httpRequest.getSession().getAttribute("uid"));
		}*/
		
		/*query.append(" ORDER BY `delivery_status` ASC");*/
		query.append(" ORDER BY a.`transfer_id` DESC");
		log.info("loadStockTxrHistory query       "+query);
		
		List<Object> stList = inventoryLOVobj.getstList(query.toString(), httpRequest);
		model.setViewName("forms/transactions/stock/stocktransferhistory");
		model.addObject("stList",stList);
		return model;
		
	}
	
	@RequestMapping("/stocktransferhistoryview")
	public ModelAndView gotoStockTransferHistoryView(@RequestParam("tid")String tid,HttpServletRequest httpRequest)
	{
		log.info("tid   "+tid);
		/*String stocktransferviewQuery = "(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`"
				+ " FROM `hero_stock_transfer` A,`hero_stock_trxr_status` B "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` IN ("+tid+") ORDER BY `transfer_id`) "
				+ "UNION ALL "
				+ "(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id` FROM `hero_stock_transfer` A,`hero_stock_trxr_status` B "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` NOT IN ("+tid+") ORDER BY `transfer_id` DESC LIMIT 0,9)";
		*/
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		log.info("usertype   "+usertype);
		StringBuilder stocktransferviewQuerySB = new StringBuilder();
		/*stocktransferviewQuerySB.append("(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`,"
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON C.`store_id` = A.`pharmacy_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` IN ("+tid+") ORDER BY `transfer_id`) "
				+ "UNION ALL "
				+ "(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`, "
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON A.`pharmacy_id` = C.`store_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` NOT IN ("+tid+") ORDER BY `transfer_id` DESC LIMIT 0,9)");*/
		stocktransferviewQuerySB.append("(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`,"
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON C.`store_id` = A.`pharmacy_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` IN ("+tid+")");
		
		/*if(usertype > 2)
		{
			stocktransferviewQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
			
		}*/
		
		stocktransferviewQuerySB.append(" ORDER BY `transfer_id`) UNION ALL "
				+ "(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`, "
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON A.`pharmacy_id` = C.`store_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` NOT IN ("+tid+")");
		
		/*if(usertype > 2)
		{
			stocktransferviewQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}*/
		
		stocktransferviewQuerySB.append(" ORDER BY `transfer_id` DESC LIMIT 0,9)");
		String stocktransferviewQuery = stocktransferviewQuerySB.toString();
		
		StringBuilder stocktransferpageQuerySB = new StringBuilder();
		stocktransferpageQuerySB.append("SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,"
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON C.`store_id` = A.`pharmacy_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` ");
		
		/*if(usertype > 2)
		{
			stocktransferpageQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}*/
		stocktransferpageQuerySB.append(" ORDER BY `transfer_id` DESC");
		String stocktransferpageQuery = stocktransferpageQuerySB.toString();
		
		  log.info("stocktransferviewQuery      "+stocktransferviewQuery);
		  log.info("stocktransferpageQuery      "+stocktransferpageQuery);
		  
		  List<Object> stockTransferList = inventoryLOVobj.getStockTransferOrderList(stocktransferviewQuery);
		  List<Object> stockTransferpageList = inventoryLOVobj.getStockTransferOrderList(stocktransferpageQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  Map<String, Object> map = new HashMap<String, Object>();
		  if(stockTransferList.size() > 0)
		  {
			  map = (Map<String, Object>) stockTransferList.get(0);
		  }
		  
		  if(map != null)
		  {
			  model.addObject("firststockno",map.get("transferno"));
			  model.addObject("txrstorename", map.get("storename"));
			  model.addObject("address", map.get("address"));
			  model.addObject("city", map.get("city"));
			  model.addObject("zipcode", map.get("zipcode"));
			  model.addObject("state", map.get("state"));
			  model.addObject("country", map.get("country"));
			  model.addObject("phone", map.get("phone"));
		  }
		  else
		  {
			  model.addObject("firststockno","0");
			  model.addObject("txrstorename", "");
			  model.addObject("address", "");
			  model.addObject("city", "");
			  model.addObject("zipcode", "");
			  model.addObject("state", "");
			  model.addObject("country", "");
			  model.addObject("phone", "");
		  }
		  
		  model.addObject("stockTransferList",stockTransferList);
		  model.addObject("stocklistsize",stockTransferList.size());
		  model.addObject("stockpagelistsize",stockTransferpageList.size());
		  
		  /*log.info("stockTransferList     "+stockTransferList);*/
		  
		  model.setViewName("forms/transactions/stock/stocktransferhistoryview");
		  
		  return model;
	}

	@RequestMapping("/stockviewhistory")
	public ModelAndView gotoStockviewHistory(HttpServletRequest httpRequest)
	{
	
		HttpSession session = httpRequest.getSession();
		/*String stockQuery = "SELECT `stock_id`,`product_name`,`product_count`,`unit`,`category_name`,`sell_price`,`batch_id` FROM "
				+ "`hero_stock_product` A LEFT JOIN `hero_stock` B ON A.`product_id` = B.`product_id` LEFT JOIN `hero_admin_unit_type` C  ON A.`unit_type_id` = C.`unit_type_id` "
				+ "LEFT JOIN `hero_admin_category` D  ON A.`category_id` = D.`category_id` "
				+ " WHERE A.`product_id` = B.`product_id` AND C.`unit_type_id` = A.`unit_type_id`";*/
		
		String stockQuery = "";
		stockQuery = "SELECT DISTINCT(B.`product_id`)product_id,`product_name`,`unit`,COALESCE(SUM(`product_count`),0)product_count,CATEGORY_NAME  "
				+ "FROM `hero_admin_category` A JOIN `hero_stock_product` B ON A.`category_id` = B.`category_id`"
				+ "LEFT JOIN `hero_admin_unit_type` C ON C.`unit_type_id` = B.`unit_type_id` "
				+ " JOIN `hero_stock` D ON B.`product_id` = D.`product_id`AND `product_count` > 0 GROUP BY B.`product_id`";
		
		/*int storeid = (int) session.getAttribute("storeid");
		
		if(storeid != 0){
		
		stockQuery = "SELECT DISTINCT(B.`product_id`)product_id,`product_name`, `unit`,"
				+ "(SELECT COALESCE(SUM(`product_count`),0) FROM `hero_stock_transfer`a1 JOIN  `hero_stock_transfer_product` b1 "
				+ " ON a1.`transfer_id` = b1.`transfer_id`  WHERE `product_id` = B.`product_id` AND `batch_id` = B.`batch_id` AND "
				+ " `pharmacy_id` = (SELECT `user_store_id` FROM `hero_user`  g WHERE g.`userid` = '"+session.getAttribute("uid")+"')) product_count,"
				+ " CATEGORY_NAME  FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON"
				+ "  A.`transfer_id` = B.`transfer_id` JOIN `hero_stock` C ON C.`product_id` = B.`product_id` "
				+ " AND C.`batch_id` = B.`batch_id` JOIN `hero_stock_product` D  ON D.`product_id` = C.`product_id` AND"
				+ "  C.`product_id` = B.`product_id` JOIN `hero_stock_store` E ON  E.`store_id` = A.`pharmacy_id` "
				+ " JOIN `hero_user` F ON F.`currencyid` = E.`currency_id` JOIN `hero_stock_purchase_received_dtl` g "
				+ " ON g.`prec_product_id` = B.`product_id` AND g.`prec_batchno` = B.`batch_id`  JOIN `hero_admin_currency` h "
				+ " ON h.`currency_id` = F.currencyid AND h.`currency_id` = E.`currency_id` LEFT JOIN `hero_admin_company` I "
				+ " ON I.`company_id` = D.`manufacturer_id` JOIN `hero_admin_unit_type` J ON J.`unit_type_id` = D.`unit_type_id` "
				+ " JOIN `hero_admin_category` K ON  K.`category_id` = D.`category_id` WHERE  F.`userid` = '"+session.getAttribute("uid")+"' AND D.status_id = 1 "
				+ " GROUP BY  B.`product_id`,B.`batch_id`";
		
		}*/
		log.info("StockviewHistory Query      "+stockQuery);

		List<Object> stockList = inventoryLOVobj.getStockList(stockQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("stockList",stockList);
		model.setViewName("forms/transactions/stock/stockviewhistory");
		return model;
		
		/*return new ModelAndView("forms/transactions/stock/stockviewhistory");*/
	}
	
	@RequestMapping("/stocktransfer")
	public ModelAndView gotoStockTransfer(@RequestParam(value="stocktransferid",required=false)String stocktransferid)
	{
		log.info("stocktransferid         "+stocktransferid);
		ModelAndView model = new ModelAndView();
		
	
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";		
		log.info("storeQuery      "+storeQuery);
		
		String dishQuery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` WHERE `status`=1";		
		log.info("dishQuery      "+dishQuery);
		
		String poQuery = "SELECT `pur_req_id`,`purchase_id` FROM `hero_stock_purchase_received_dtl`a "
				+ " JOIN `hero_stock_purchase_receive_hdr`b ON a.`prec_hdr_id`=b.`prhdr_id` "
				+ " JOIN `hero_stock_purchase`c ON c.`purchase_code`=b.`pur_req_id` "
				+ " JOIN `hero_stock`d ON d.`product_id`=a.`prec_product_id` AND a.`prec_batchno`=d.`batch_id` "
				+ " WHERE `product_count` > 0 GROUP BY `pur_req_id`";
		
		log.info("poQuery    "+poQuery);
		
		 List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		 List<Object> prList = inventoryLOVobj.getLOVList(poQuery);	
		 List<Object> dishList = inventoryLOVobj.getLOVList(dishQuery);
		
		model.addObject("storeList",storeList);
		model.addObject("prList",prList);
		model.addObject("dishList",dishList);
		model.setViewName("forms/transactions/stock/stocktransfer");
		
		return model;
		
	}
	
	@RequestMapping("/adjustmenthistory")
	public ModelAndView gotoStockAdjustmentHistory()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("forms/transactions/stock/adjustmenthistory");
		return model;
		
	}
	
	@RequestMapping("/addadjustment")
	public ModelAndView gotoAddAdjustment()
	{
		ModelAndView model = new ModelAndView();
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		String adjReasonQuery = "SELECT `sar_id`,`sar_reason` FROM `hero_stock_adjustment_reason`";
		log.info("storeQuery      "+storeQuery);
		log.info("adjReasonQuery      "+adjReasonQuery);
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		List<Object> reasonList = inventoryLOVobj.getLOVList(adjReasonQuery);
		model.addObject("storeList",storeList);		
		model.addObject("reasonList",reasonList);
		model.setViewName("forms/transactions/stock/addadjustment");
		return model;
		
	}
	
	@RequestMapping("/viewpurchaseorderrequest")
	public ModelAndView gotoviewpurchaseorderrequest()
	{
		String supplierQuery = "SELECT SUPPLIER_ID,SUPPLIER_FIRST_NAME  FROM hero_stock_supplier";
		String uomPackingQuery = "SELECT `hsuts_id` uompackingid,`hsuts_desc` uompackingdesc FROM `hero_stock_unit_type_setting` WHERE `hsuts_status` = 1";
		String referencenoquery="SELECT CONCAT(`F_HERO_ID_FORMAT`(1,(SELECT COALESCE(MAX(`SEQ_ID`),1)FROM `hero_stock_purchase_seq`)),(SELECT COALESCE(MAX(`SEQ_ID`)+1 ,1) FROM `hero_stock_purchase_seq`) )refno";
		String storequery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		String poQuery = "SELECT DISTINCT(`purchase_code`),`purchase_id` FROM `hero_stock_purchase_order` a JOIN "
				+ "`hero_stock_purchase_order_request` b ON a.`purchase_code` = b.`pur_req_id` "
				+ "WHERE `purchase_raised` = 0 and a.purchase_status = 1";
		log.info("poQuery    "+poQuery);
		
		List<Object> supplierList = inventoryLOVobj.getLOVList(supplierQuery);
		  List<Object> uomPackingList = jdbcTemplate.queryForList(uomPackingQuery);
		  List<Object> referencenolist = inventoryLOVobj.refnoList(referencenoquery);
		  List<Object> storeList = inventoryLOVobj.getLOVList(storequery);
		  List<Object> prList = inventoryLOVobj.getLOVListwithoutemptylist(poQuery);
		  log.info(supplierList);
		  log.info(uomPackingList);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("supplierList",supplierList);
		  model.addObject("uomPackingList",uomPackingList);
		  model.addObject("referencenolist",referencenolist);
		  model.addObject("storeList",storeList);
		  model.addObject("prList",prList);
		
		model.setViewName("forms/transactions/purchaseorder/viewpurchaseorderrequest");
		return model;
		
	}
	@RequestMapping("/addmoretransferProducts")
	public ModelAndView addmoretransferProducts(@RequestParam(value="stocktransferid",required=false)String stocktransferid)
	{
		ModelAndView model = new ModelAndView();
     model.setViewName("forms/transactions/stock/addmoretransferProducts");
		
		return model;
	}
	@RequestMapping("/transferProducts")
	public ModelAndView transferProducts(@RequestParam(value="stocktransferid",required=false)String stocktransferid)
	{
		log.info("stocktransferid         "+stocktransferid);
		ModelAndView model = new ModelAndView();
		
	
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store` WHERE `store_id`=2 ";	
		
		String transferQuery = "SELECT `transfer_id`,`transfer_no` FROM `hero_stock_transfer` WHERE `sub_sequent`="+stocktransferid;	
		
		log.info("storeQuery      "+storeQuery);
		String pendingOrderQuery = "";
		String txNoQuery= "";
		if(stocktransferid.equals("0")){
			pendingOrderQuery ="SELECT `ord_req_id`,`ord_ref_no` FROM `hero_order_request` WHERE `status`=1";
			txNoQuery = "SELECT COUNT(*)+1,'' lable FROM `hero_stock_transfer` WHERE `sub_sequent`="+stocktransferid;
		}else{
			 txNoQuery="SELECT COUNT(*)+1,'' lable FROM `hero_stock_transfer` WHERE `sub_sequent`="+stocktransferid;
			pendingOrderQuery ="SELECT `order_id`,`ord_ref_no` FROM `hero_stock_transfer` a JOIN  `hero_order_request` b ON a.`order_id` = b.`ord_req_id` WHERE a.`transfer_id`="+stocktransferid;
			
		}
		String getexistingOrderQuery = "SELECT `transfer_id`,`transfer_no` FROM `hero_stock_transfer` WHERE `sub_sequent`="+stocktransferid;
		 
		
		String poQuery = "SELECT `pur_req_id`,`purchase_id` FROM `hero_stock_purchase_received_dtl`a "
				+ " JOIN `hero_stock_purchase_receive_hdr`b ON a.`prec_hdr_id`=b.`prhdr_id` "
				+ " JOIN `hero_stock_purchase`c ON c.`purchase_code`=b.`pur_req_id` "
				+ " JOIN `hero_stock`d ON d.`product_id`=a.`prec_product_id` AND a.`prec_batchno`=d.`batch_id` "
				+ " WHERE `product_count` > 0 GROUP BY `pur_req_id`";
		
		log.info("poQuery    "+poQuery);
		
		 List<Object> pendingOrderList = inventoryLOVobj.getLOVList(pendingOrderQuery);
		 List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		 List<Object> transferList = inventoryLOVobj.getLOVList(transferQuery);
		 List<Object> prList = inventoryLOVobj.getLOVList(poQuery);	
		 List<Object>  txNoList = inventoryLOVobj.getLOVList(txNoQuery);	
		 List<Object>  getexistingOrderList = inventoryLOVobj.getLOVList(getexistingOrderQuery);
		 
		model.addObject("txNoList",txNoList);
		model.addObject("storeList",storeList);
		model.addObject("prList",prList);
		model.addObject("pendingOrderList",pendingOrderList);
		model.addObject("getexistingOrderList",getexistingOrderList);
		model.addObject("transferList",transferList);
		
		model.setViewName("forms/transactions/stock/transferProducts");
		
		return model;
		
	}
	@RequestMapping("/addOrderRequest")
	public ModelAndView addOrderRequest(@RequestParam(value="id",required=false)String id)
	{
		
		ModelAndView model = new ModelAndView();
	
		String customerQuery = "SELECT `companyid`,`companyname` FROM `hero_stock_client_company` ";	
		String dishQuery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` WHERE `status`=1";	
		String refNoQuery = "SELECT CONCAT(`F_HERO_ID_FORMAT`(5,(SELECT COUNT(*) FROM `hero_order_request`)),(SELECT COUNT(*)+1 FROM `hero_order_request` ) )refno";	
		log.info("customerQuery      "+customerQuery);
		 List<Object> customerList = inventoryLOVobj.getLOVList(customerQuery);
		 List<Object> dishList = inventoryLOVobj.getLOVList(dishQuery);
		 List<Object> referencenolist = inventoryLOVobj.refnoList(refNoQuery);
		 
		model.addObject("customerList",customerList);
		model.addObject("dishList",dishList);
		model.addObject("referencenolist",referencenolist);
		model.setViewName("forms/transactions/purchaseorder/addorderrequest");
		
		return model;
		
	}
	@RequestMapping("/approvestorerequest")
	public ModelAndView approvestorerequest()
	{
		
		ModelAndView model = new ModelAndView();
	
		String requestQuery = "SELECT `transfer_id`,`transfer_no` FROM `hero_stock_transfer` WHERE `delivery_status`=1 AND order_approved =0";
	   List<Object> requestList = inventoryLOVobj.getLOVList(requestQuery);
		model.addObject("requestList",requestList);
		log.info(requestList.size());
		model.setViewName("forms/transactions/purchaseorder/approvstorerequest");
		
		return model;
		
	}
	@RequestMapping("/orderRequestView")
	public ModelAndView orderRequestView(@RequestParam(value="id",required=false)String id)
	{
		
		ModelAndView model = new ModelAndView();
	
		String orderQuery = "SELECT `ord_req_id`,`ord_ref_no` FROM `hero_order_request` WHERE ord_req_id="+id;	
		String orderfullQuery = "SELECT `ord_req_id`,`ord_ref_no` FROM `hero_order_request`";
		 List<Object> orderList = inventoryLOVobj.getLOVList(orderQuery);
		 List<Object> orderfullList = inventoryLOVobj.getLOVList(orderfullQuery);
		
		model.addObject("orderList",orderList);
		model.addObject("orderfullList",orderfullList);
		model.setViewName("forms/transactions/purchaseorder/orderRequestview");
		
		return model;
		
	}
	@RequestMapping("/OrderRequestHistory")
	public ModelAndView OrderRequestHistory()
	{
		
		ModelAndView model = new ModelAndView();
	
		String orderQuery = "SELECT * FROM `hero_order_request` ORDER BY ord_req_id DESC";	
	
		 List<Object> orderList = inventoryLOVobj.executeQuery(orderQuery);
		
	
		model.addObject("orderList",orderList);
		model.setViewName("forms/transactions/purchaseorder/orderRequestHistory");
		
		return model;
		
	}
	
	@RequestMapping("/grnhistory")
	public ModelAndView gotoGrnHistory(HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		StringBuilder query = new StringBuilder("SELECT a.purchase_id,a.purchase_code,a.purchase_refer_no,DATE_FORMAT(a.`created_date`,'%e/%c/%Y %h:%i')purchase_date,"
				+ "DATE_FORMAT(a.received_date,'%e/%c/%Y')received_date,CONCAT('Rs.',a.total_amt)totalamt,a.total_amt,a.paid_amt,a.supplier_id,a.purchase_notes,a.purchase_tnc,a.receive_status,"
				+ "a.purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,a.purchase_status,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = a.`purchase_code`) purchase_count,e.`purchase_code` requestcode FROM hero_stock_purchase_status c ,"
				+ "hero_stock_supplier b,"
				+ " hero_stock_purchase a,`hero_user` d,`hero_stock_purchase_order` e  WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id AND d.`userid`=a.`CREATED_BY`  AND a.`purchase_request_id`=e.`purchase_id` "
				/*+ "AND d.`user_store_id`="+session.getAttribute("storeid")*/
				+ " GROUP BY `purchase_id`   ORDER BY `purchase_id` DESC ");
		log.info("purchase order query    "+query.toString());  
		
		List<Object> purchaseList = inventoryLOVobj.getPurchaseOrderHistroyList1(query.toString());
		log.info("purchaseListpurchaseList"+purchaseList);
		 ModelAndView model = new ModelAndView();
		  
		  model.addObject("purchaseList",purchaseList);
		  
		  model.setViewName("forms/transactions/purchaseorder/grnhistory");
		
		return model;
	}
	
}
