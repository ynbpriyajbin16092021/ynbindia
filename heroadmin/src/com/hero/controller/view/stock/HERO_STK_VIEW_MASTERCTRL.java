package com.hero.controller.view.stock;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYLOV;

@Controller
public class HERO_STK_VIEW_MASTERCTRL {
	private static Logger log = Logger.getLogger(HERO_STK_VIEW_MASTERCTRL.class);
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/header")
	public ModelAndView gotoHeader(@RequestParam(value = "uid", required = false) String uid)
	{
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/template/header");
		return model;
		
	}
	
	
	@RequestMapping(value="/currency")
	public ModelAndView gotoCurrency()
	{
		 Random rand = new Random();
		 int rand_int1 = rand.nextInt(1000); 
		  log.info(rand_int1);
		  ModelAndView model = new ModelAndView();
		  model.addObject("random_int", rand_int1);
		  model.setViewName("forms/admin/masters/currency");
		return model;
	}
	@RequestMapping(value="/clinics")
	public ModelAndView gotoClinics()
	{
		return new ModelAndView("forms/admin/masters/clinics");
	}
	@RequestMapping(value="/addclinic")
	public ModelAndView gotoaddClinic()
	{
		  ModelAndView model = new ModelAndView();
			String currencyQuery="SELECT currency_id,currency FROM hero_admin_currency";
			
			List<Object>  currencyList = inventoryLOVobj.getLOVList(currencyQuery);
	      
			
			
			model.addObject("currencyList",currencyList); 
			
			model.setViewName("forms/admin/masters/addclinic");
			return model;
			
		
	}
	@RequestMapping(value="/addlab")
	public ModelAndView gotoAddlab(String labid)
	{
		  ModelAndView model = new ModelAndView();
		  String currencyQuery="SELECT currency_id,currency FROM hero_admin_currency";
			
			List<Object>  currencyList = inventoryLOVobj.getLOVList(currencyQuery);
	      
			
			 String labfullquery="SELECT * FROM `hero_lab_regn`";
			 List<Object> labfullList = inventoryLOVobj.executeQuery(labfullquery);
			  model.addObject("labfullList",labfullList);
			model.addObject("currencyList",currencyList); 
		model.setViewName("forms/admin/masters/addlab");
		return model;
		
		
	}
	
	@RequestMapping(value="/lab")
	public ModelAndView gotoLab()
	{
		
		 ModelAndView model = new ModelAndView();
		 String labfullquery="SELECT * FROM `hero_lab_regn`";
		 List<Object> labfullList = inventoryLOVobj.executeQuery(labfullquery);
		  model.addObject("labfullList",labfullList);
		 model.setViewName("forms/admin/masters/lab");
		 return model;
	}
	@RequestMapping(value="/apporganization")
	public ModelAndView gotoApporganization()
	{
		return new ModelAndView("forms/admin/masters/apporganization");
	}
	@RequestMapping(value="/tax")
	public ModelAndView gotoTaxMaster()
	{
		 Random rand = new Random();
		  int rand_int = rand.nextInt(1000000);
		  log.info(rand_int);
		  ModelAndView model = new ModelAndView();
		  model.addObject("random_int", rand_int);
		  model.setViewName("forms/admin/masters/tax");
		  return model;
	}
	
	@RequestMapping(value="/usertypes")
	public ModelAndView gotoUsertypeMaster()
	{
		String deptQuery = "SELECT `hac_id`,`hac_name` FROM `hero_app_config` WHERE `status`=1  order by hac_name asc ";
		log.info("deptQuery    "+deptQuery);
		
		List<Object> deptList = inventoryLOVobj.getLOVList(deptQuery);
		ModelAndView model = new ModelAndView();
		model.addObject("deptList",deptList);
		model.setViewName("forms/admin/masters/usertypes");
		
		return model;
		/*return new ModelAndView("forms/admin/masters/usertypes");*/
	}
	@RequestMapping(value="/addusertypes")
	public ModelAndView gotoaddUsertypeMaster()
	{
		String deptQuery = "SELECT `hac_id`,`hac_name` FROM `hero_app_config` order by hac_name asc";
		log.info("deptQuery    "+deptQuery);
		
		List<Object> deptList = inventoryLOVobj.getLOVList(deptQuery);
		ModelAndView model = new ModelAndView();
		model.addObject("deptList",deptList);
		model.setViewName("forms/admin/masters/addusertypes");
		
		return model;
		/*return new ModelAndView("forms/admin/masters/usertypes");*/
	}
	
	@RequestMapping(value="/customer")
	public ModelAndView gotoCustomer()
	{
		return new ModelAndView("forms/stock/masters/customer");
	}
	
	@RequestMapping(value="/customerview")
	public ModelAndView gotoCustomerView(@RequestParam("customerid") String customerid,HttpServletRequest httpRequest)
	{
		StringBuilder customerQuerySB = new StringBuilder();
		StringBuilder fullCustomerListQuerySB = new StringBuilder();
		
		customerQuerySB.append("SELECT `cust_id`,CONCAT(`cust_firstname`,`cust_lastname`)CUSTNAME FROM `hero_admin_customer` a ");
		fullCustomerListQuerySB.append("SELECT `cust_id`,CONCAT(`cust_firstname`,`cust_lastname`)CUSTNAME FROM `hero_admin_customer` a ");
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		if(usertype > 2)
		{
			customerQuerySB.append("LEFT JOIN `hero_user` c ON c.`userid` = a.`created_by` LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` "
					+ "WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid"));
			fullCustomerListQuerySB.append("LEFT JOIN `hero_user` c ON c.`userid` = a.`created_by` LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` "
					+ "WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		else
		{
			
		}
		customerQuerySB.append(" LIMIT 0,10");
		
		  String customerQuery = customerQuerySB.toString();
		  String fullCustomerListQuery = fullCustomerListQuerySB.toString();
		  
log.info("customerQuery   "+customerQuery);
log.info("fullCustomerListQuery   "+fullCustomerListQuery);

		  List<Object> customerList = inventoryLOVobj.getLOVList(customerQuery);
		  List<Object> fullCustomerList = inventoryLOVobj.getLOVList(fullCustomerListQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("customerList",customerList);
		  model.addObject("fullCustomerListSize",fullCustomerList.size());
		  
		  log.info("fullCustomerList.size()        "+fullCustomerList.size());
		  
		  model.setViewName("forms/stock/masters/customerview");
		  
		return model;
	}
	
	@RequestMapping(value="/addcustomer")
	public ModelAndView gotoAddCustomer()
	{
		  String customergroupQuery = "SELECT `seq_id`,`customer_group_name` FROM `hero_admin_customer_group`";

		  List<Object> customergroupList = inventoryLOVobj.getLOVList(customergroupQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("customergroupList",customergroupList);
		  
		  model.setViewName("forms/stock/masters/addcustomer");
		  
		  return model;
		  
		
	}
	
	@RequestMapping(value="/userrole")
	public ModelAndView gotoUserRole(@RequestParam(value = "applnid", required = true)  int applnid,@RequestParam(value = "usertypeid", required = true)  int usertypeid)
	{
		int usertypeIdD = usertypeid;
		int usertypeId = applnid;
		int applicationId = applnid + 1;
		String usertypeQuery = "SELECT `usertype_id`,`usertype_name`,`usertype_dept` FROM `hero_user_type` WHERE usertype_id > 1 AND `usertype_dept` = '"+usertypeId+"' AND `usertype_status` = '1'";
		String menuQuery = "SELECT `moduleid`,`modulename`,`issubmodule`,`parentid`,0 user_menu_sno,0 userid,0 usertype,0 menuaccess , 0 isreportmenu"
				+ " FROM `hero_module` WHERE `status` = '1' AND `applntype` = '"+applicationId+"' ORDER BY `moduleid` ASC";
		String reportmenuQuery = "SELECT `moduleid`,`modulename`,`issubmodule`,`parentid`,0 user_menu_sno,0 userid,0 usertype,0 menuaccess, 0 isreportmenu"
				+ " FROM `hero_reports_module` WHERE `status` = '1' AND `applntype` = '"+applicationId+"' ORDER BY `moduleid` ASC";
		log.info("usertypeQuery    "+usertypeQuery);
		log.info("menuQuery    "+menuQuery);
		log.info("reportmenuQuery    "+reportmenuQuery);
		String applnTypeQuery = "SELECT `hac_id`,`hac_name` FROM `hero_app_config`";
		
		List<Object> usertypeList = inventoryLOVobj.getUserTypeList(usertypeQuery);
		List<Object> applnTypeList = inventoryLOVobj.getLOVList(applnTypeQuery);
		List<Object> usermenuList = new ArrayList<Object>();
		List<Object> userreportmenuList = new ArrayList<Object>();
		/*List<Object> stockmanagermenuList = new ArrayList<Object>();
		List<Object> pharmacistmenuList = new ArrayList<Object>();
		List<Object> receptionistmenuList = new ArrayList<Object>();
		List<Object> physicianmenuList = new ArrayList<Object>();*/
		
		String usertypewithimgQuery = "SELECT `usertype_id`,`usertype_name`,`usertype_img`,`hac_name` FROM `hero_user_type` a JOIN `hero_app_config` b "
									  + "ON a.`usertype_dept` = b.`hac_id` WHERE `usertype_id`!='1' AND a.`usertype_dept` = '"+usertypeId+"' AND `usertype_status` = '1'";
		
		List<Object> usertypewithImgList = inventoryLOVobj.getuserTypeList(usertypewithimgQuery);

		  
		if(usertypeList != null && usertypeList.size() > 0)
		{
			/*HERO_ADM_SERVC_INVENTORYLOV lov = (HERO_ADM_SERVC_INVENTORYLOV) usertypeList.get(0) ;
			  String usertype = lov.getValue();
			  String usermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
			  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`"
						+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
						+ "WHERE `user_type_id` = "+usertype+" ORDER BY `userid`,`moduleid` ASC";*/
			String deptid = "0",firstusertypeid = "0";
			if(usertypeIdD>0){
				firstusertypeid = String.valueOf(usertypeIdD);
			}else{
			  Map<String, Object> usertypeMap = (Map<String, Object>) usertypeList.get(0);
			  log.info("usertypeMap   "+usertypeMap);
			  
			  if(usertypeMap != null)
			  {
				  deptid = (String) usertypeMap.get("deptid");
				  firstusertypeid = (String) usertypeMap.get("usertypeid");
			  }
			  log.info("deptid   "+deptid);
			  int intdeptid = 0;
			  if(deptid != null)
			  {
				  intdeptid = Integer.parseInt(deptid);
			  }
			  intdeptid++;
			}
			  String usermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,COALESCE(`menu_access`,0) menu_access, COALESCE(`isreportmenu`,0) isreportmenu "
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` WHERE `status` = '1'  AND "
							+ "`isadminmenu` != '0' AND `applntype` = '"+applicationId+"' "
							+ "AND `user_type_id` = '"+firstusertypeid+"' "
									+ "GROUP BY A.`moduleid` ORDER BY `moduleid`,`userid` ASC";
			  
			  String userreportmenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,COALESCE(`menu_access`,0) menu_access, COALESCE(`isreportmenu`,0) isreportmenu "
							+ " FROM `hero_reports_module` A LEFT OUTER JOIN `hero_reports_user_menus` B ON A.`moduleid` = B.`moduleid` WHERE `status` = '1'  AND "
							+ " `applntype` = '"+applicationId+"' AND `user_type_id` = '"+firstusertypeid+"' GROUP BY A.`moduleid` ORDER BY `moduleid`,`userid` ASC";

			  
			  log.info("usermenuQuery    "+usermenuQuery);
			  log.info("userreportmenuQuery    "+userreportmenuQuery);
			  /*log.info("stockmanagermenuQuery    "+stockmanagermenuQuery);
			  log.info("pharmacistmenuQuery    "+pharmacistmenuQuery);
			  log.info("receptionistmenuQuery    "+receptionistmenuQuery);
			  log.info("physicianmenuQuery    "+physicianmenuQuery);*/
			  
			  usermenuList = inventoryLOVobj.getUsermenuList(menuQuery,usermenuQuery);
			  userreportmenuList = inventoryLOVobj.getUsermenuList(reportmenuQuery,userreportmenuQuery);
			  /*userreportmenuList = inventoryLOVobj.getUsermenuList(menuQuery,usermenuQuery);*/
			  /*stockmanagermenuList = inventoryLOVobj.getUsermenuList(menuQuery,stockmanagermenuQuery);
			  pharmacistmenuList = inventoryLOVobj.getUsermenuList(menuQuery,pharmacistmenuQuery);
			  receptionistmenuList = inventoryLOVobj.getUsermenuList(menuQuery,receptionistmenuQuery);
			  physicianmenuList = inventoryLOVobj.getUsermenuList(menuQuery,physicianmenuQuery);*/
			  log.info("usermenuList    "+usermenuList);
			  log.info("userreportmenuList    "+userreportmenuList);
			  /*log.info("stockmanagermenuList    "+stockmanagermenuList);
			  log.info("pharmacistmenuList    "+pharmacistmenuList);
			  log.info("receptionistmenuList    "+receptionistmenuList);
			  log.info("physicianmenuList    "+physicianmenuList);*/
		}
		  
		ModelAndView model = new ModelAndView();
		model.addObject("applnid",applnid); 
		model.addObject("applnTypeList",applnTypeList); 
		model.addObject("usertypeList",usertypewithImgList);
		model.addObject("usermenuList",usermenuList);
		model.addObject("userreportmenuList",userreportmenuList);
		/*model.addObject("stockmanagermenuList",stockmanagermenuList);
		model.addObject("pharmacistmenuList",pharmacistmenuList);
		model.addObject("receptionistmenuList",receptionistmenuList);
		model.addObject("physicianmenuList",physicianmenuList);*/
		  
		model.setViewName("forms/admin/masters/userrole");
		  
		return model;
		  
		
	}
	

	@RequestMapping(value="/store")
	public ModelAndView gotoStoreMaster()
	{
		/*return new ModelAndView("forms/admin/masters/store");*/
		String currencyQuery = "select currency_id,currency from hero_admin_currency GROUP BY currency";
		 String taxQuery = "SELECT `TAX_ID`,CONCAT(`TAX_NAME`,' ( ',tax_rate,' % )') FROM `hero_admin_tax`";
		  
		  List<Object> currencyList = inventoryLOVobj.getLOVList(currencyQuery);
		  List<Object> taxList = inventoryLOVobj.getLOVList(taxQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("currencyList",currencyList);
		  model.addObject("taxList",taxList);
		  
		  model.setViewName("forms/admin/masters/store");
		  
		  return model;
	}
	
	@RequestMapping(value="/addstore")
	public ModelAndView gotoAddStoreMaster()
	{
		  String currencyQuery = "select currency_id,currency from hero_admin_currency";
		  String taxQuery = "SELECT `TAX_ID`,CONCAT(`TAX_NAME`,' ( ',tax_rate,' % )') FROM `hero_admin_tax`";
		  
		  List<Object> currencyList = inventoryLOVobj.getLOVList(currencyQuery);
		  List<Object> taxList = inventoryLOVobj.getLOVList(taxQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("currencyList",currencyList); 
		  model.addObject("taxList",taxList);
		  
		  model.setViewName("forms/admin/masters/addstore");
		  
		  return model;
		/*return new ModelAndView("forms/masters/addstore");*/
	}
	
	@RequestMapping(value="/user")
	public ModelAndView gotoUserList()
	{
		 
		ModelAndView model = new ModelAndView();
		 
		model.setViewName("/forms/admin/masters/user");
		return model;
		
	}
	
	@RequestMapping(value="/adduser")
	public ModelAndView gotoNewUser()
	{
		
		String usertypeQuery = "SELECT `usertype_id`,`usertype_name` FROM `hero_user_type` where usertype_id != 1";
		String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id != 1";
		String currencyquery="SELECT `currency_id`,`currency` FROM `hero_admin_currency`";
		String userCountQuery="SELECT (MAX(`userid`)+1)next_user_id FROM `hero_user`";
		String clinicQuery = "SELECT `bc_id`,`bc_start_year`  FROM `hero_admin_clinic`";
		String languageQuery = "SELECT `hl_id`,`hl_desc` description FROM `her_admin_language`";
		String qualificationQuery = "SELECT `hq_id`,`hq_desc` qualification FROM `hero_admin_qualification`  WHERE `status`=1";
		List<Object> languageList = new ArrayList<Object>();
		List<Object> qualificationList = new ArrayList<Object>();
		languageList = inventoryLOVobj.executeQuery(languageQuery);
		qualificationList = inventoryLOVobj.executeQuery(qualificationQuery);
		List<Object> usertypeList = inventoryLOVobj.getLOVList(usertypeQuery);
		List<Object> currencyList = inventoryLOVobj.getLOVList(currencyquery);
		List<Object> usertypeApplnList = inventoryLOVobj.getLOVList(usertypeApplnQuery);
		
		
		List<Object> userCountList = inventoryLOVobj.getUserCountList(userCountQuery);
		
		List<Object> clinicList = inventoryLOVobj.getLOVList(clinicQuery);
		log.info(clinicList);
		Map<String, Object> map = (Map<String, Object>) userCountList.get(0);
		String dynamicEHR = "1000"+map.get("nextuserid");
		
		ModelAndView model = new ModelAndView();
		
		
		model.addObject("currencyList",currencyList);
		model.addObject("qualificationList",qualificationList);
		model.addObject("languageList",languageList);
		model.addObject("usertypeList",usertypeList);
		model.addObject("clinicList",clinicList);
		model.addObject("usertypeApplnList",usertypeApplnList);
		model.addObject("ehrvalue",dynamicEHR);
		model.setViewName("/forms/admin/masters/adduser");
		return model;
		

		/*String usertypeQuery = "SELECT `usertype_id`,`usertype_name` FROM `hero_user_type` where usertype_id != 1";
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		
		List<Object> usertypeList = inventoryLOVobj.getLOVList(usertypeQuery);
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		List<Object> userCountList = inventoryLOVobj.getUserCountList(userCountQuery);
		Map<String, Object> map = (Map<String, Object>) userCountList.get(0);
		String dynamicEHR = "1000"+map.get("nextuserid");

		
		ModelAndView model2 = new ModelAndView();
		
		model.addObject("usertypeList",usertypeList);
		model.addObject("storeList",storeList);
		model.addObject("storeList",storeList);
		model1.addObject("ehrvalue",dynamicEHR);
		
		model2.setViewName("/forms/admin/masters/adduser");
		return model2;*/
		

	}
	
	@RequestMapping(value="/addsmstemplate")
	public ModelAndView gotoAddSmstemplate(@RequestParam(value = "smsid", required = false) String smsid)
	{
		/*return new ModelAndView("/forms/masters/addcategory","uid",uid);*/
		ModelAndView model = new ModelAndView();  
		  model.addObject("smsid",smsid);
		  model.setViewName("/forms/admin/masters/addsmstemplate");  
		  return model;
	}
	
	
	@RequestMapping(value="/addcurrency")
	public ModelAndView gotoAddCurrency()
	{
		 Random rand = new Random();
		  int rand_int = rand.nextInt(1000000);
		  log.info(rand_int);
		  ModelAndView model = new ModelAndView();
		  model.addObject("random_int", rand_int);
		  model.setViewName("forms/admin/masters/addcurrency");
			return model;
		
	}
	
	@RequestMapping(value="/addtax")
	public ModelAndView gotoAddTaxMaster()
	{
		 Random rand = new Random();
		  int rand_int = rand.nextInt(1000000);
		  log.info(rand_int);
		  ModelAndView model = new ModelAndView();
		  model.addObject("random_int", rand_int);
		  model.setViewName("forms/admin/masters/addtax");
		  return model;
	}
	/*@RequestMapping(value="/addclinic")
	public ModelAndView gotoAddclinic()
	{
		return new ModelAndView("forms/admin/masters/addclinic");
	}*/
	/*@RequestMapping(value="/addcustomergroup")
	public ModelAndView gotoAddCustomerGroup()
	{
		return new ModelAndView("forms/admin/masters/addcustomergroup");
	}*/
	
	
	
}
