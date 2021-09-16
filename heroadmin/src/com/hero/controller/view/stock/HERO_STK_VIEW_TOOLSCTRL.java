package com.hero.controller.view.stock;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYLOV;

@Controller
public class HERO_STK_VIEW_TOOLSCTRL {

	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	private static Logger log = Logger.getLogger(HERO_STK_VIEW_TOOLSCTRL.class);
	@RequestMapping("/stockmonitor")
	public ModelAndView gotoStockMonitor()
	{
		ModelAndView model = new ModelAndView();
		  
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		String manufacturerQuery = "SELECT  0 company_id,'All' company_name UNION ALL SELECT `company_id`,`company_name` FROM `hero_admin_company`";
		String categoryQuery = "SELECT  0 category_id,'All' category_name UNION ALL SELECT `category_id`,`category_name` FROM `hero_admin_category`";
		
		log.info("storeQuery      "+storeQuery);
		
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		List<Object> manufacturerList = inventoryLOVobj.getLOVList(manufacturerQuery);
		List<Object> categoryList = inventoryLOVobj.getLOVList(categoryQuery);
		
		model.addObject("storeList",storeList);
		model.addObject("manufacturerList",manufacturerList);
		model.addObject("categoryList",categoryList);
		
		model.setViewName("forms/tools/stockmonitor");
		  
		return model;
		
		
	}
	
	@RequestMapping("/expirydatechecker")
	public ModelAndView gotoExpiryDateChecker()
	{
		ModelAndView model = new ModelAndView();
		
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		
		model.addObject("storeList",storeList);
		
		model.setViewName("forms/tools/expirydatechecker");
		  
		return model;
		
	}
	
	@RequestMapping("/barcodeview")
	public ModelAndView gotoPrintbarcode()
	{
		ModelAndView model = new ModelAndView();
		
		String storeQuery = "SELECT `store_id`,`store_name`,`CURR_SYMBOL` FROM `hero_stock_store` a JOIN `hero_admin_currency` b ON a.`currency_id` = b.`currency_id`";
		
		List<Object> storeList = inventoryLOVobj.getBarcodeStoreList(storeQuery);
		String storeid = "0",productid="0",batchid="0";
		String currency = "";
		
		if(storeList != null && storeList.size() > 0)
		{
		
			Map<String, Object> lov = (Map<String, Object>)storeList.get(0);
			if(lov.get("value") != null)
			{
				storeid = (String)lov.get("value");
				currency = (String)lov.get("currencysymbol");
			}
		}
		
		String productQuery = "SELECT DISTINCT(c.`product_id`),`product_name` FROM `hero_stock_transfer` a JOIN `hero_stock_transfer_product` b ON a.`transfer_id` = b.`transfer_id` JOIN "
				+ "`hero_stock_product` c ON b.`product_id` = c.`product_id` AND `pharmacy_id` = "+storeid;
		List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		if(productList != null && productList.size() > 0)
		{
		
			HERO_ADM_SERVC_INVENTORYLOV lov = (HERO_ADM_SERVC_INVENTORYLOV)productList.get(0);
			if(lov.getValue()!= null)
			{
				productid = lov.getValue();
			}
		}
		
		String batchQuery = "SELECT DISTINCT(`batch_id`),`batch_id` label FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` = C.`product_id` JOIN `hero_stock_store` D ON A.`pharmacy_id` = D.`store_id` "
				+ "WHERE A.`pharmacy_id` = "+storeid+" AND B.`product_id` = "+productid;
		log.info("batchQuery      "+batchQuery);
		List<Object> batchList = inventoryLOVobj.getLOVList(batchQuery);
		if(batchList != null && batchList.size() > 0)
		{
		
			HERO_ADM_SERVC_INVENTORYLOV lov = (HERO_ADM_SERVC_INVENTORYLOV)batchList.get(0);
			if(lov.getValue()!= null)
			{
				batchid = lov.getValue();
			}
		}
		
		String rateQuery = "SELECT DISTINCT(`product_rate`),`product_rate` label FROM `hero_stock_transfer` A JOIN `hero_stock_transfer_product` B ON A.`transfer_id` = B.`transfer_id` "
				+ "JOIN `hero_stock_product` C ON B.`product_id` = C.`product_id` JOIN `hero_stock_store` D ON A.`pharmacy_id` = D.`store_id` "
				+ "WHERE A.`pharmacy_id` = "+storeid+" AND B.`product_id` = "+productid+" and batch_id = '"+batchid+"'";
		log.info("rateQuery      "+rateQuery);
		List<Object> rateList = inventoryLOVobj.getLOVList(rateQuery);
		
		
		model.addObject("storeList",storeList);
		model.addObject("productList",productList);
		model.addObject("batchList",batchList);
		model.addObject("rateList",rateList);
		model.addObject("currency",currency);
		
		model.setViewName("forms/tools/printbarcode");
		  
		return model;
		
	}
	
	@RequestMapping("/lowstockalert")
	public ModelAndView gotoLowStockAlert()
	{
		
		ModelAndView model = new ModelAndView();
		
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		log.info("storeList    "+storeList);
		model.addObject("storeList",storeList);
		
		model.setViewName("forms/tools/lowstockalert");
		  
		return model;
		
	}
	
}
