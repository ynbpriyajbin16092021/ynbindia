package com.hero.controller.view.stock;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;

@Controller
public class HERO_STK_VIEW_SALESCTRL {
	private static Logger log = Logger.getLogger(HERO_STK_VIEW_SALESCTRL.class);
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping("/pos")
	public ModelAndView gotoPOSScreen(HttpServletRequest httpRequest)
	{
		  HttpSession session = httpRequest.getSession();
		
		  String customergroupQuery = "SELECT `seq_id`,`customer_group_name` FROM `hero_admin_customer_group`";
		  String taxQuery ="SELECT C.`tax_id`,`TAX_NAME`,`TAX_RATE` FROM `hero_stock_store` A JOIN `hero_user` B ON A.`currency_id` = B.`currencyid` "
		  		+ "JOIN `hero_stock_store_tax` C ON A.`store_id` = C.`store_id` JOIN `hero_admin_tax` D ON D.`TAX_ID` = C.`tax_id` WHERE `userid` = "+session.getAttribute("uid");
		  log.info("taxQuery       "+taxQuery);
		  String bankQuery = "SELECT bank_id,bank_name FROM hero_admin_bank";
		  
		  int storeId = (int)httpRequest.getSession().getAttribute("storeid");
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
		  ModelAndView model = new ModelAndView();
		  
		  model.setViewName("forms/sales/poshistory");
		  
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
	public ModelAndView gotoOrderHistory()
	{
		  ModelAndView model = new ModelAndView();
		  
		  String orderStatusQuery = "SELECT `pos_status_id`,`pos_status_desc` FROM `hero_stock_pos_status` WHERE `pos_status_id` IN(5,6,7);";
		  
		  List<Object> orderStatusList = inventoryLOVobj.getLOVList(orderStatusQuery);
		  
		  model.addObject("orderStatusList",orderStatusList);
		  
		  model.setViewName("forms/sales/orderhistory");
		  
		return model;
		
	}
}
