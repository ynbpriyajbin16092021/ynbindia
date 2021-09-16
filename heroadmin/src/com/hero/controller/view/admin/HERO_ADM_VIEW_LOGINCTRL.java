package com.hero.controller.view.admin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hero.forms.services.admin.login.HERO_ADM_SERVC_ILOGINDAO;
import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.heroutils.HERO_UTILS;

@Controller
@SessionAttributes( {"uid"})
public class HERO_ADM_VIEW_LOGINCTRL {

	private static Logger log = Logger.getLogger(HERO_ADM_VIEW_LOGINCTRL.class);
	
	HttpSession session = null;
	
	@Autowired
	private HERO_ADM_SERVC_ILOGINDAO loginDAO;
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;

	@RequestMapping("/login")
	public ModelAndView loginPremia() {
		return new ModelAndView("/forms/admin/login/login", "message", "");
	}

	@RequestMapping("/heroupload")
	public ModelAndView heroupload() {
		return new ModelAndView("/forms/admin/login/upload", "message", "");
	}
	
	@RequestMapping(value = "/registration")
	public ModelAndView registerPremia() {
		/*
		 * List<LoginRequest> userList = loginDAO.fetchUsers(loginDAO);
		 * ModelAndView model = new ModelAndView();
		 * model.addObject("userList",userList);
		 * model.setViewName("/forms/admin/login/registration"); return model;
		 */
		return new ModelAndView("/forms/admin/login/registration", "message",
				"Success");

	}
	
	@RequestMapping(value="/stockdashboard")
	public ModelAndView gotoHomepage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/template/stockdashboard");
		  
		session = httpRequest.getSession();
		List<Object> usermenuList = new ArrayList<Object>();
		int usertype = (int) session.getAttribute("usertype");
		  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`modulepath`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
		  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
					+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
					+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 1 or applntype = 2) AND `isadminmenu` = '1'  ORDER BY `userid`,`moduleid` ASC";
			 
		  log.info("mainmenuQuery    "+mainmenuQuery);
		  
		  usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery,1);

		  session.setAttribute("mainmenuList",usermenuList);
		
		Map<String, Object> map = (Map<String, Object>) inventoryLOVobj.getDashboardDetails(httpRequest);
		log.info("map      "+map);
		
		String productCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "productCount");
		String supplierCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "supplierCount");
		String storeCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "storeCount");
		String usersCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "userCount");
		String customerCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "customerCount");
		String purchaseAmountStr = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "purchaseAmount");
		String salesAmountStr = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "salesAmount");
		String customerDueStr = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "customerDue");
		
		Double salesAmount = 0.0,purchaseAmount = 0.0,customerDue = 0.0;
		
		if(purchaseAmountStr != null)
		{
			purchaseAmount = Double.parseDouble(purchaseAmountStr);	
		}
		
		if(customerDueStr != null)
		{
			customerDue = Double.parseDouble(customerDueStr);	
		}
		/*Double salesAmount = Double.parseDouble(salesAmountStr);*/
		
		if(salesAmountStr != null)
		{
			salesAmount = Double.parseDouble(salesAmountStr);
		}
		Double profitAmount = salesAmount - purchaseAmount;
		if(profitAmount < 0)
		{
			profitAmount = 0.0;
		}

		model.addObject("productCount",productCount);
		model.addObject("supplierCount",supplierCount);
		model.addObject("storeCount",storeCount);
		model.addObject("usersCount",usersCount);
		model.addObject("customerCount",customerCount);
		
		BigDecimal bd = new BigDecimal(purchaseAmount);
		String formattedPurchaseAmount = HERO_ADM_SERVC_HOSURINVENTORYUTIL.IndianFormat(bd); 
		model.addObject("purchaseAmount",formattedPurchaseAmount);
		
		BigDecimal bd1 = new BigDecimal(salesAmount);
		String formattedSalesAmount = HERO_ADM_SERVC_HOSURINVENTORYUTIL.IndianFormat(bd1); 
		
		model.addObject("salesAmount",formattedSalesAmount);
		
		BigDecimal bd3 = new BigDecimal(customerDue);
		String formattedcustomerDue = HERO_ADM_SERVC_HOSURINVENTORYUTIL.IndianFormat(bd3); 
		model.addObject("customerDue",formattedcustomerDue);
		
		model.addObject("recentBillsList",map.get("recentBills"));
		model.addObject("recentTransferList",map.get("recentTransfers"));
		model.addObject("ordersList",map.get("ordersList"));
		model.addObject("topSellingProductList",map.get("topSellingProductList"));
		
		if(profitAmount == 0){
			model.addObject("profitAmount",0.00);
		}
		else{
			BigDecimal bd2 = new BigDecimal(profitAmount);
			String formattedProfitAmount = HERO_ADM_SERVC_HOSURINVENTORYUTIL.IndianFormat(bd2); 
			model.addObject("profitAmount",formattedProfitAmount);
		}
		return model;
		
		/*return new ModelAndView("/forms/template/stockdashboard","message","success");*/
	}
	
	@RequestMapping(value="/herosettings")
	public ModelAndView gotoHerosettingsPage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/template/herosettings");
		return model;
	}
	
	@RequestMapping(value="/heroclinicsettings")
	public ModelAndView gotoHeroClinicsettingsPage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/template/heroclinicsettings");
		return model;
	}
	
	@RequestMapping(value="/herohome")
	public ModelAndView gotoHerohomePage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		session = httpRequest.getSession();
		String applncontect_URL = HERO_UTILS.getApplicationSontext(httpRequest);
		log.info("applncontect_URL   "+applncontect_URL);
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/home/herohome");
		
		List<Object> usermenuList = new ArrayList<Object>();
		int usertype = (int) session.getAttribute("usertype");
		  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`modulepath`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
		  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
					+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
					+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 0) AND `isadminmenu` = '0'  ORDER BY `userid`,`moduleid` ASC";
			 
		  log.info("mainmenuQuery    "+mainmenuQuery);
		  
		  usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery,0);

		  session.setAttribute("mainmenuList",usermenuList);
		
		
		String heroappConfigQuery = "SELECT `hac_id`,`hac_name`,`hac_sno`,`hac_logo`,`hac_url`,hac_token_validator_url "
				+ "FROM `hero_app_config` WHERE `hac_id`= 1  ORDER BY `hac_sno` ASC";
		List<Object> heroappConfigList = inventoryLOVobj.getAppconfigList(heroappConfigQuery,applncontect_URL);
		model.addObject("heroappConfigList",heroappConfigList);
		
		Map<String, Object> map = (Map<String, Object>) inventoryLOVobj.getDashboardDetails(httpRequest);
		
		String usersCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "userCount");
		String stockmanagerCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "stockmanagerCount");
		String receptCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "receptCount");
		String storeCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "storeCount");
		String taxCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "taxCount");
		String currencyCount = (String)HERO_ADM_SERVC_HOSURINVENTORYUTIL.getValueFromList(map, "currencyCount");
		
		model.addObject("usersCount",usersCount);
		model.addObject("stockmanagerCount",stockmanagerCount);
		model.addObject("storeCount",storeCount);
		model.addObject("receptCount",receptCount);
		model.addObject("taxCount",taxCount);
		model.addObject("currencyCount",currencyCount);
		model.addObject("userRecentList",map.get("userRecent"));
		model.addObject("currencyRecentList",map.get("currencyRecent"));
		model.addObject("taxRecentList",map.get("taxRecent"));
		model.addObject("storeRecentList",map.get("storeRecent"));
		model.addObject("receptRecentList",map.get("receptRecent"));
		model.addObject("stockmanagerRecentList",map.get("stockmanagerRecent"));
		log.info("heroappConfigList Size  "+heroappConfigList.size());
		return model;
	}
	
	
	@RequestMapping(value="/clinicdashboard")
	public ModelAndView gotoHomeClinicpage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		
		session = httpRequest.getSession();
		List<Object> usermenuList = new ArrayList<Object>();
		int usertype = (int) session.getAttribute("usertype");
		  String mainmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
		  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`user_type_id`,fafafont"
					+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
					+ "WHERE `user_type_id` = "+usertype+" AND parentid = 0 AND menu_access = 1 AND `status` = '1' and (applntype = 3) AND `isadminmenu` = '3'  ORDER BY `userid`,`moduleid` ASC";
			 
		  log.info("mainmenuQuery    "+mainmenuQuery);
		  
		  usermenuList = inventoryLOVobj.getMenuList(mainmenuQuery,1);

		  session.setAttribute("mainmenuList",usermenuList);
		
		model.setViewName("/forms/template/clinicdashboard");
		return model;
	}
	
	@RequestMapping(value="/heroadminsettings")
	public ModelAndView gotoHeroAdminsettingsPage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/template/heroadminsettings");
		return model;
	}
	
	@RequestMapping(value="/homeheader")
	public ModelAndView gotoHeroHomeHeaderPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/home/homeheader");
		return model;
	}
	
	
	/*
	@RequestMapping(value="/homeheader")
	public ModelAndView gotoHomeheaderPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/home/homeheader");
		return model;
	}
	
	@RequestMapping(value="/homefooter")
	public ModelAndView gotoHomefooterPage()
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/home/homefooter");
		return model;
	}*/
	
	@RequestMapping(value="/transactioncodeconfig")
	public ModelAndView gotoTransactionCodeConfigPage()
	{
		ModelAndView model = new ModelAndView();
		
		String storeListQuery = "SELECT `store_id` 'key',`store_name` 'value' FROM `hero_stock_store` order by store_name asc";
		List<Object> storeList = inventoryLOVobj.executeQuery(storeListQuery);
		model.addObject("storeList",storeList);
		
		model.setViewName("/forms/admin/masters/transactioncodeconfig");
		return model;
	}
	
	@RequestMapping(value="/addtransactioncode")
	public ModelAndView gotoAddTransactionCodePage(@RequestParam(value="txncodeid")String txncodeid)
	{
		ModelAndView model = new ModelAndView();
		
		log.info("txncodeid   "+txncodeid);
		
		String storeListQuery = "SELECT `store_id` 'key',`store_name` 'value' FROM `hero_stock_store` order by store_name asc";
		String usergroupListQuery = "SELECT `usertype_id` 'key',`usertype_name` 'value' FROM `hero_user_type` ORDER BY `usertype_name` ASC";
		String txnTypeListQuery = "SELECT CONCAT(`TXN_START_NAME`,'-',`TXN_NO_OF_ZEROS`,'-',`TXN_ID`) 'key',`TXN_DESC` 'value' FROM `hero_txns_no_format` WHERE TXN_DESC !=  'Supplier Bill No' ORDER BY `TXN_DESC` ASC";
			 
		log.info("storeListQuery    "+storeListQuery);
		log.info("usergroupListQuery    "+usergroupListQuery);
		log.info("txnTypeListQuery    "+txnTypeListQuery);
		  
		List<Object> storeList = inventoryLOVobj.executeQuery(storeListQuery);
		List<Object> userGroupList = inventoryLOVobj.executeQuery(usergroupListQuery);
		List<Object> txnTypeList = inventoryLOVobj.executeQuery(txnTypeListQuery);
		
		model.addObject("storeList",storeList);
		model.addObject("userGroupList",userGroupList);
		model.addObject("txnTypeList",txnTypeList);
		
		String txncode = "",txnnodeflength = "";
		if(txnTypeList != null && txnTypeList.size() > 0)
		{
			Map<String, Object> txnTypeMap = (Map<String, Object>) txnTypeList.get(0);
			String dbtxncode = (String) txnTypeMap.get("key");
			String[] txncodeArr = dbtxncode.split("-");
			txncode = txncodeArr[0];
			txnnodeflength = txncodeArr[1];
		}
		model.addObject("txnnodeflength",txnnodeflength);
		model.addObject("txncode",txncode);
		
		model.setViewName("/forms/admin/masters/addtransactioncodeconfig");
		return model;
	}
	
}
