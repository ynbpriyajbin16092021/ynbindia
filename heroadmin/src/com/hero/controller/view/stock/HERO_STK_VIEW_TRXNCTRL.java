package com.hero.controller.view.stock;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;

@Controller
public class HERO_STK_VIEW_TRXNCTRL{
	private static Logger log = Logger.getLogger(HERO_STK_VIEW_TRXNCTRL.class);
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping("/invoice")
	public ModelAndView gotoInvoice(HttpServletRequest httpRequest)
	{
		
		
		
		ModelAndView model = new ModelAndView();
		
		
		
		model.setViewName("forms/transactions/invoice");
		
		return model;
		
	}
	
	@RequestMapping("/purchaseorderhistory")
	public ModelAndView gotoPurchaseorderHistory()
	{
		return new ModelAndView("forms/transactions/purchaseorder/purchasehistory");
	}
	
	@RequestMapping("/addpurchaseorder")
	public ModelAndView gotoAddPurchaseOrder()
	{
		/*return new ModelAndView("forms/transactions/purchaseorder/addpurchaseorder");*/
		
		String supplierQuery = "SELECT SUPPLIER_ID,CONCAT(SUPPLIER_FIRST_NAME,SUPPLIER_LAST_NAME) FROM hero_stock_supplier";
		  
		  List<Object> supplierList = inventoryLOVobj.getLOVList(supplierQuery);
		  log.info(supplierList);
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("supplierList",supplierList);
		  
		  model.setViewName("forms/transactions/purchaseorder/addpurchaseorder");
		  
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
				+ "purchase_notes,purchase_tnc,receive_status,purchase_status,CONCAT(supplier_first_name,supplier_last_name) supplier_name,"
				+ "ps_status_name purchase_status_desc,(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id IN ("+pid+")) "
				+ "UNION ALL "
				+ "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,purchase_status,"
				+ "CONCAT(supplier_first_name,supplier_last_name) supplier_name,ps_status_name purchase_status_desc,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id NOT IN ("+pid+") ORDER BY purchase_id,`purchase_status` DESC LIMIT 0,9)"	;
		  log.info("viewpurchaseQuery      "+viewpurchaseQuery);
		  
		  String viewpurchaseFullQuery = "SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
					+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name,supplier_last_name) supplier_name,ps_status_name purchase_status_desc,"
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
	
	@RequestMapping("/receivestock")
	public ModelAndView gotoReceiveStock(@RequestParam(value = "pid", required = false) String purchaseid)
	{
		log.info("purchaseid      "+purchaseid);
		/*String receivestockQuery = "SELECT purchase_code,product_name,unit,pur_quantity,prec_quantity,free_product_count,prec_batchno,"
				+ "DATE_FORMAT(prec_expiry_date,'%e/%c/%Y')prec_expiry_date,prec_purchase_price,prec_sales_price FROM hero_stock_product b,hero_stock_purchase_request c,"
				+ "hero_admin_unit_type d,hero_stock_purchase a LEFT JOIN hero_stock_purchase_received ON purchase_code = prec_req_id WHERE purchase_id = 2 "
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
		
		String receivestockQuery = "SELECT purchase_id,0 prhdr_id,purchase_code,0 prec_dtl_id,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,pur_quantity,pur_product_id,"
				+ "0 prec_recving_quantity,0 prec_free_count,0 prhdr_shipping_cost,0.00 prhdr_shipping_cost_disp,'' prec_batchno,'' prec_barcode,'' prec_expiry_date,"
				+ "COALESCE(`unit_rate`,0)prec_pur_price,0 prec_sel_price,FORMAT(COALESCE(0,0),2)prec_sub_total,'%' prhdr_discount_type,COALESCE(0,0)prhdr_discount_value,"
				+ "COALESCE(0,0)prhdr_tax_id,product_name,product_id,unit,'' prhdr_notes,FORMAT(0,2)prhdr_total_amt,FORMAT(0,2)prhdr_tax_amt,"
				+ "FORMAT(0,2)prhdr_discount_amt,FORMAT(0,2)prhdr_grand_total_amt,FORMAT(0,2)prhdr_total_with_disc,FORMAT(0,2)prhdr_total_amt_disp,"
				+ "COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C"
				+ " WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND B.`pur_req_id` = C.`purchase_code` AND `purchase_id` = "+purchaseid
				+ " ),0) RECVDQTY   FROM `hero_stock_purchase` A LEFT JOIN `hero_stock_purchase_request` B ON `purchase_code` = B.`pur_req_id` "
				+ "LEFT JOIN `hero_stock_product` E ON E.`product_id` = B.`pur_product_id` LEFT JOIN `hero_admin_unit_type` F ON F.`unit_type_id` = E.`unit_type_id` "
				+ "WHERE `purchase_id` =  "+purchaseid+"  AND "
				+ "pur_quantity != COALESCE((SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase_received_dtl` A,`hero_stock_purchase_receive_hdr` B,`hero_stock_purchase` C"
				+ " WHERE `prec_product_id` = `pur_product_id` AND A.`prec_hdr_id` = B.`prhdr_id` AND B.`pur_req_id` = C.`purchase_code`  AND `purchase_id` = "+purchaseid
				+ " ),0)";
		
		log.info("receivestockQuery   "+receivestockQuery);
		String taxQuery = "SELECT TAX_ID,TAX_NAME,TAX_RATE,tax_type FROM hero_admin_tax";

		String pohistoryQuery = "SELECT `pur_req_id`, `purchase_date`, `purchase_refer_no`,`prhdr_recv_date`, COALESCE(`prhdr_paid_amount`, 0) prhdr_paid_amount "
				+ "FROM `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` b ON a.`pur_req_id` = b.`purchase_code` WHERE  "
				+ "`purchase_id` = '"+purchaseid+"'";
		log.info("pohistoryQuery   "+pohistoryQuery);
		  List<Object> taxList = inventoryLOVobj.getTaxList(taxQuery);
		  List<Object> receivestockList = inventoryLOVobj.getViewReceiveStockList(receivestockQuery);
		  List<Object> pohistoryList = inventoryLOVobj.executeQuery(pohistoryQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("receivestockList",receivestockList);
		  model.addObject("taxList",taxList);
		  model.addObject("pohistoryList",pohistoryList);
		  
		  log.info("taxList   "+taxList);
		  
		  
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
		  }
		  
		  model.setViewName("forms/transactions/purchaseorder/receivestockview");
		  
		  return model;
		
		/*return new ModelAndView("forms/transactions/purchaseorder/receivestockview");*/
	}
	
	@RequestMapping("/receivestockedit")
	public ModelAndView gotoReceiveStockEdit(@RequestParam(value = "pid", required = false) String purchaseid)
	{
		log.info("purchaseid      "+purchaseid);
		 
		String receivestockQuery = "SELECT `purchase_id`,`prhdr_id`,`purchase_code`,`prec_dtl_id`,DATE_FORMAT(NOW(),'%e/%c/%Y') prhdr_recv_date,"
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
				+ "WHERE `prec_hdr_id` = "+purchaseid;
		
		log.info("receivestockQuery   "+receivestockQuery);
		String taxQuery = "SELECT TAX_ID,TAX_NAME,TAX_RATE,tax_type FROM hero_admin_tax";

		  List<Object> taxList = inventoryLOVobj.getTaxList(taxQuery);
		  List<Object> receivestockList = inventoryLOVobj.getViewReceiveStockList(receivestockQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("receivestockList",receivestockList);
		  model.addObject("taxList",taxList);
		  
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
			
		String supplierQuery = "SELECT SUPPLIER_ID,CONCAT(SUPPLIER_FIRST_NAME,SUPPLIER_LAST_NAME) FROM hero_stock_supplier";
		String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		String paymenttypeQuery = "SELECT `ct_id`,`ct_name` FROM `hero_admin_cash_type`";
		  
		List<Object> bankList = inventoryLOVobj.getLOVList(bankQuery);
		List<Object> paymenttypeList = inventoryLOVobj.getLOVList(paymenttypeQuery);
		List<Object> supplierList = inventoryLOVobj.getLOVList(supplierQuery);
		  
		ModelAndView model = new ModelAndView();
		  
		model.addObject("bankList",bankList);
		model.addObject("paymenttypeList",paymenttypeList);
		model.addObject("supplierList",supplierList);
		  
		model.setViewName("forms/transactions/purchaseorder/bills");
		  
		return model;
		  
	
	}
	/*
	@RequestMapping("/stocktransferhistory")
	public ModelAndView gotoStockTransferHistory(@RequestParam("tid")String tid,HttpServletRequest httpRequest)
	{
		
		ModelAndView model = new ModelAndView();
		StringBuilder query = new StringBuilder("SELECT transfer_id,delivery_status,`transfer_no`,DATE_FORMAT(`transfer_date`,'%e/%c/%Y')transfer_date,"
				+ "a.`status_id`,`status_desc`,"
				+ "(SELECT SUM(`tobe_recvd_prod_count`) FROM `hero_stock_transfer_product` SUB WHERE SUB.`transfer_id` = a.`transfer_id`)PROD_COUNT,"
				+ "(SELECT COALESCE((SUM(`product_rate`*`CURR_CONVERSION_RATE`)),0) FROM `hero_stock` STK,`hero_stock_transfer_product` stk_prod,`hero_stock_transfer` TXR"
				+ " WHERE STK.`product_id` = stk_prod.`product_id` AND stk_prod.`transfer_id`=TXR.`transfer_id` AND stk_prod.`batch_id` = STK.`batch_id` "
				+ " AND TXR.`transfer_id` = a.`transfer_id`)AMOUNT,`store_name`,CURR_SYMBOL `currency` FROM `hero_stock_transfer` a JOIN `hero_stock_trxr_status` C "
				+ "ON a.`delivery_status` = C.`status_id`"
				+ " LEFT JOIN `hero_stock_store` s ON `pharmacy_id` = `store_id` LEFT JOIN `hero_admin_currency` d ON d.`currency_id` = s.`currency_id`  ");
		
		int usertpe = (int)httpRequest.getSession().getAttribute("usertype");
		if(usertpe > 2)
		{
			query.append("JOIN `hero_user` e ON e.`user_store_id` = s.`store_id` AND `userid` = "+httpRequest.getSession().getAttribute("uid"));
		}
		
		query.append(" ORDER BY `delivery_status` ASC");
		
		log.info("loadStockTxrHistory query       "+query);
		model.setViewName("forms/transactions/stock/stocktransferhistory");
		List<Object> stList = inventoryLOVobj.getstList(String Query);
		return model;
		
	}*/
	
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
		
		if(usertype > 2)
		{
			stocktransferviewQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
			
		}
		
		stocktransferviewQuerySB.append(" ORDER BY `transfer_id`) UNION ALL "
				+ "(SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,`pharmacy_id`, "
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON A.`pharmacy_id` = C.`store_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` AND `transfer_id` NOT IN ("+tid+")");
		
		if(usertype > 2)
		{
			stocktransferviewQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		
		stocktransferviewQuerySB.append(" ORDER BY `transfer_id` DESC LIMIT 0,9)");
		String stocktransferviewQuery = stocktransferviewQuerySB.toString();
		
		StringBuilder stocktransferpageQuerySB = new StringBuilder();
		stocktransferpageQuerySB.append("SELECT `transfer_id`,`transfer_no`,`status_desc`,`delivery_status`,"
				+ "`store_name`,`address`,`city`,`zipcode`,`state`,`country`,`phone` "
				+ "FROM `hero_stock_transfer` A JOIN `hero_stock_trxr_status` B LEFT JOIN `hero_stock_store` C ON C.`store_id` = A.`pharmacy_id` "
				+ "WHERE A.`delivery_status` = B.`status_id` ");
		
		if(usertype > 2)
		{
			stocktransferpageQuerySB.append(" AND A.`pharmacy_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
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
	public ModelAndView gotoStockviewHistory()
	{
		/*String stockQuery = "SELECT `stock_id`,`product_name`,`product_count`,`unit`,`category_name`,`sell_price`,`batch_id` FROM "
				+ "`hero_stock_product` A LEFT JOIN `hero_stock` B ON A.`product_id` = B.`product_id` LEFT JOIN `hero_admin_unit_type` C  ON A.`unit_type_id` = C.`unit_type_id` "
				+ "LEFT JOIN `hero_admin_category` D  ON A.`category_id` = D.`category_id` "
				+ " WHERE A.`product_id` = B.`product_id` AND C.`unit_type_id` = A.`unit_type_id`";*/
		
		String stockQuery = "SELECT DISTINCT(B.`product_id`)product_id,`product_name`,`unit`,COALESCE(SUM(`product_count`),0)product_count,CATEGORY_NAME  "
				+ "FROM `hero_admin_category` A JOIN `hero_stock_product` B ON A.`category_id` = B.`category_id`"
				+ "LEFT JOIN `hero_admin_unit_type` C ON C.`unit_type_id` = B.`unit_type_id` "
				+ "LEFT JOIN `hero_stock` D ON B.`product_id` = D.`product_id` GROUP BY B.`product_id`";
		
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
		
		/*String stockTxrQuery = "SELECT a.`transfer_id`,transfer_no,a.`transfer_id`,b.`product_id`,`product_name`,b.`batch_id`,d.`product_count` sourcecount,b.`product_count` destcount,`tobe_recvd_prod_count`,"
				+ "`sell_price` FROM `hero_stock_transfer` a JOIN "
				+ "`hero_stock_transfer_product` b ON a.`transfer_id` = b.transfer_id "
				+ "LEFT JOIN `hero_stock_product` c ON c.`product_id` = b.`product_id` LEFT JOIN `hero_stock` d ON d.`product_id` = c.`product_id` "
				+ "WHERE a.`transfer_id` = "+stocktransferid;*/
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		
		/*log.info("stockTxrQuery      "+stockTxrQuery);*/
		log.info("storeQuery      "+storeQuery);
		
		/*List<Object> transferList = inventoryLOVobj.getTransferList(stockTxrQuery);*/
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		
		String tprid = "0",transferno="";
		
		/*if(transferList != null && transferList.size() > 0)
		{
			Map<String, Object> transferMap = (Map<String, Object>) transferList.get(0);
			tprid = (String)transferMap.get("tprid");
			transferno = (String)transferMap.get("transferno");
		}*/
		
		model.addObject("storeList",storeList);
		/*model.addObject("transferList",transferList);
		model.addObject("tprid",tprid);
		model.addObject("transferno",transferno);*/
		
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
	
	
	
}
