package com.hero.controller.view.stock;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;

@Controller
public class HERO_STK_VIEW_REPORTCTRL {

	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/inventoryreports")
	public ModelAndView gotoInventoryReports()
	{
		return new ModelAndView("forms/reports/reports");
	}
	
	@RequestMapping(value="/salesbyproduct")
	public ModelAndView gotoInventoryReportsView()
	{
		/*return new ModelAndView("forms/reports/salesbyproduct");*/
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbyproduct");
		
		return model;
	}
	
	@RequestMapping(value="/salesbycustomer")
	public ModelAndView gotoSalesByCustomer()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbycustomer");
		
		return model;
	}
	
	@RequestMapping(value="/salesbyorder")
	public ModelAndView gotoSalesByOrder()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbyorder");
		
		return model;
	}
	
	@RequestMapping(value="/salesfinance")
	public ModelAndView gotoSalesFinance()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesfinance");
		
		return model;
	}
	
	@RequestMapping(value="/salesbymonth")
	public ModelAndView gotoSalesByMonth()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbymonth");
		
		return model;
	}
	
	@RequestMapping(value="/salesbypaymenttype")
	public ModelAndView gotoSalesByPaymenttype()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbypaymenttype");
		
		return model;
	}
	
	@RequestMapping(value="/salesbytaxtype")
	public ModelAndView gotoTaxType()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/salesbytaxtype");
		
		return model;
	}
	
	@RequestMapping(value="/purchasereceivehistory")
	public ModelAndView gotoPurchaseReceiveHistory()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)label FROM `hero_stock_supplier`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/purchasereceivehistory");
		
		return model;
	}
	
	@RequestMapping(value="/purchasebyitem")
	public ModelAndView gotoPurchaseByItem()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)label FROM `hero_stock_supplier`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/purchasebyitem");
		
		return model;
	}
	
	@RequestMapping(value="/purchasebilldetails")
	public ModelAndView gotoPurchaseBillDetails()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)label FROM `hero_stock_supplier`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/purchasebilldetails");
		
		return model;
	}
	
	@RequestMapping(value="/vendorbalance")
	public ModelAndView gotoVendorBalance()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)label FROM `hero_stock_supplier`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/vendorbalance");
		
		return model;
	}
	
	@RequestMapping(value="/stocktxrreport")
	public ModelAndView gotoStockTxrReport()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/stocktxrreport");
		
		return model;
	}
	
	@RequestMapping(value="/warehousestock")
	public ModelAndView gotoWarehouseStockReport()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/warehousestock");
		
		return model;
	}
	
	@RequestMapping(value="/productorders")
	public ModelAndView gotoProductOders()
	{
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/productorders");
		
		return model;
	}
}
