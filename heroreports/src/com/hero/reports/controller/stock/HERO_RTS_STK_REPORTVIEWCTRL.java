package com.hero.reports.controller.stock;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.reports.lov.HERO_RTS_IINVENTORYLOV;
import com.hero.reports.lov.HERO_RTS_INVENTORYLOVIMPL;

@Controller
public class HERO_RTS_STK_REPORTVIEWCTRL {

	private static Logger log = Logger.getLogger(HERO_RTS_STK_REPORTVIEWCTRL.class);
	
	@Autowired
	private HERO_RTS_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/inventoryreports")
	public ModelAndView gotoInventoryReports( HttpServletRequest httpRequest)
	{
		/*HttpSession session = httpRequest.getSession();
		List<Object> usermenuList = new ArrayList<Object>();
		int usertype = (int) session.getAttribute("usertype");
		  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
		  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
					+ " FROM `hero_reports_module` A LEFT OUTER JOIN `hero_reports_user_menus` B ON A.`moduleid` = B.`moduleid` "
					+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' ORDER BY `userid`,`moduleid` ASC";
		  
		  usermenuList = inventoryLOVobj.getReportMenuList(mainmenuQuery);
		  log.info("usermenuList  "+usermenuList);
		  session.setAttribute("reportmainmenuList",usermenuList);*/
		return new ModelAndView("forms/reports/preindex");
	}
	
	@RequestMapping(value="/reports")
	public ModelAndView gotoReports( HttpServletRequest httpRequest)
	{
		HttpSession session = httpRequest.getSession();
		List<Object> usermenuList = new ArrayList<Object>();
		int usertype = (int) session.getAttribute("usertype");
		 String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
			  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
						+ " FROM `hero_reports_module` A LEFT OUTER JOIN `hero_reports_user_menus` B ON A.`moduleid` = B.`moduleid` "
						+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' ORDER BY `userid`,`moduleid` ASC";
		  
		  usermenuList = inventoryLOVobj.getReportMenuList(mainmenuQuery);
		  log.info("usermenuList  "+usermenuList);
		  session.setAttribute("reportmainmenuList",usermenuList);
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
	
	@RequestMapping(value="/orderreceiving")
	public ModelAndView gotoorderreceiving()
	{
		ModelAndView model = new ModelAndView();
		String companyQuery = "SELECT `companyid`,`companyname` FROM `hero_stock_client_company`";
		List<Object> companyList = inventoryLOVobj.getLOVList( companyQuery);
		model.addObject("companyList",companyList);
		model.setViewName("forms/reports/orderreceiving");
		
		return model;
	}
	@RequestMapping(value="/ordermaking")
	public ModelAndView gotoordermaking()
	{
		ModelAndView model = new ModelAndView();
		String companyQuery = "SELECT `companyid`,`companyname` FROM `hero_stock_client_company`";
		List<Object> companyList = inventoryLOVobj.getLOVList( companyQuery);
		model.addObject("companyList",companyList);
		model.setViewName("forms/reports/ordermaking");
		
		return model;
	}
	@RequestMapping(value="/storerequestreport")
	public ModelAndView gotostorerequestreport()
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("forms/reports/storerequestreport");
		
		return model;
	}
	
	@RequestMapping(value="/vendorbalance")
	public ModelAndView gotoVendorBalance()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`)label FROM `hero_stock_supplier`";
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
	
	@RequestMapping(value="/purchasebyproduct")
	public ModelAndView purchasebyproduct()
	{
		String storeQuery = "SELECT `supplier_id`,CONCAT(`supplier_first_name`,' ',`supplier_last_name`)label FROM `hero_stock_supplier`";
		List<Object> storeList = inventoryLOVobj.getLOVList( storeQuery);
		
		ModelAndView model = new ModelAndView();
		model.addObject("storeList",storeList);
		model.setViewName("forms/reports/purchasebyproduct");
		
		return model;
	}
	
	
	@RequestMapping(value="/outputqty")
	public ModelAndView outputqty()
	{
		String storeQuery = "SELECT `transfer_id`,`transfer_no` FROM `hero_stock_transfer`";
		List<Object> transferList = inventoryLOVobj.getLOVList(storeQuery);
		log.info("transferList   "+transferList);
		ModelAndView model = new ModelAndView();
		model.addObject("transferList",transferList);
		model.setViewName("forms/reports/outputqty");
		
		return model;
	}
	
	
	@RequestMapping(value="/stockreport")
	public ModelAndView stockreport()
	{
		String storeQuery = "SELECT `transfer_id`,`transfer_no` FROM `hero_stock_transfer`";
		List<Object> transferList = inventoryLOVobj.getLOVList(storeQuery);
		log.info("transferList   "+transferList);
		ModelAndView model = new ModelAndView();
		model.addObject("transferList",transferList);
		model.setViewName("forms/reports/stockreport");
		
		return model;
	}
	
	
	@RequestMapping(value="/delierychallan")
	public ModelAndView delierychallan()
	{
		String customerQuery = "SELECT `companyid`,`companyname` FROM `hero_stock_client_company`";
		List<Object> customerList = inventoryLOVobj.getLOVList(customerQuery);
	
		ModelAndView model = new ModelAndView();
		model.addObject("customerList",customerList);
		model.setViewName("forms/reports/delierychallan");
		
		return model;
	}
}
