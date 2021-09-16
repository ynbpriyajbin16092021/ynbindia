 package com.hero.stock.controller.inventory;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.util.HERO_STK_INVENTORYLOV;

@Controller
public class HERO_STK_MASTERCTRL {
	private static Logger log = Logger.getLogger(HERO_STK_MASTERCTRL.class);
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/header")
	public ModelAndView gotoHeader(@RequestParam(value = "uid", required = false) String uid)
	{
		
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/template/header");
		return model;
		
	}
	
	@RequestMapping(value="/category")
	public ModelAndView gotoCategory(@RequestParam(value = "uid", required = false) String uid)
	{
		/*return new ModelAndView("/forms/masters/category","uid",uid);*/
		ModelAndView model = new ModelAndView();
		  
		  model.addObject("uid",uid);
		 
		  
			
		  model.setViewName("/forms/stock/masters/category");
		  
		  return model;
	}
	
	

	@RequestMapping(value="/addcategory")
	public ModelAndView gotoaddCategory(@RequestParam(value = "uid", required = false) String uid)
	{
		/*return new ModelAndView("/forms/masters/category","uid",uid);*/
		ModelAndView model = new ModelAndView();
		  
		  model.addObject("uid",uid);
		 
		  
			
		  model.setViewName("/forms/admin/masters/addcategory");
		  
		  return model;
	}
	@RequestMapping(value="/bank")
	public ModelAndView gotoBank()
	{
		return new ModelAndView("/forms/admin/masters/bank");
	}
	
	@RequestMapping(value="/addbank")
	public ModelAndView gotoaddBank()
	{
		return new ModelAndView("/forms/admin/masters/addbank");
	}
	
	@RequestMapping(value="/product")
	public ModelAndView gotoProduct()
	{
		/*return new ModelAndView("/forms/stock/masters/product");*/
		

		
		  String manufactureQuery = "select company_id,company_name from hero_admin_company";
		  String categoryQuery = "select category_id,category_name from hero_admin_category";
		  String uomQuery = "select unit_type_id,unit from hero_admin_unit_type";
		  String taxQuery = "SELECT TAX_ID, CASE WHEN `TAX_TYPE`= 'P' THEN CONCAT(TAX_NAME,'(',TAX_RATE,'%)')"
		  		+ " ELSE CONCAT(TAX_NAME,'(',TAX_RATE,')') END AS label FROM `hero_admin_tax`";
		  
		  Random rand = new Random();
		  int rand_int = rand.nextInt(1000000);
		  log.info(rand_int);
		  
		  List<Object> manufactureList = inventoryLOVobj.getLOVList(manufactureQuery);
		  List<Object> categoryList = inventoryLOVobj.getLOVList(categoryQuery);
		  List<Object> uomList = inventoryLOVobj.getLOVList(uomQuery);
		  List<Object> taxList = inventoryLOVobj.getLOVList(taxQuery);
		  
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("manufactureList",manufactureList);
		  model.addObject("categoryList",categoryList);
		  model.addObject("uomList",uomList);
		  model.addObject("random_int", rand_int);
		  model.addObject("taxList",taxList);
		  
		  model.setViewName("/forms/stock/masters/product");
		  
		  return model;
		 
		/*return new ModelAndView("/forms/masters/addproduct");*/
	
    }
	
	@RequestMapping(value="/addproduct")
	public ModelAndView gotoAddProduct()
	{
		
		  String manufactureQuery = "select company_id,company_name from hero_admin_company";
		  String categoryQuery = "select category_id,category_name from hero_admin_category";
		  String uomQuery = "select unit_type_id,unit from hero_admin_unit_type";
		  
		  Random rand = new Random();
		  int rand_int = rand.nextInt(1000000);
		  log.info(rand_int);
		  
		  List<Object> manufactureList = inventoryLOVobj.getLOVList(manufactureQuery);
		  List<Object> categoryList = inventoryLOVobj.getLOVList(categoryQuery);
		  List<Object> uomList = inventoryLOVobj.getLOVList(uomQuery);
		  
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("manufactureList",manufactureList);
		  model.addObject("categoryList",categoryList);
		  model.addObject("uomList",uomList);
		  model.addObject("random_int", rand_int);
		  
		  model.setViewName("/forms/stock/masters/addproduct");
		  
		  return model;
		 
		/*return new ModelAndView("/forms/masters/addproduct");*/
	}
	
	@RequestMapping(value="/productview")
	public ModelAndView gotoProductView()
	{
		
		  String productQuery = "select product_id,product_name from hero_stock_product order by product_id asc";
		  
		  List<Object> productList = inventoryLOVobj.getLOVList(productQuery);
		  
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("productList",productList);
		  
		  model.setViewName("/forms/masters/productview");
		  
		  return model;
	}
	
	@RequestMapping(value="/importproduct")
	public ModelAndView gotoImportProduct()
	{
		
		  ModelAndView model = new ModelAndView();
		  
		  model.setViewName("/forms/stock/masters/importproduct");
		  
		  return model;
	}
	
	@RequestMapping(value="/manufacturercompany")
	public ModelAndView gotoCompany()
	{
		return new ModelAndView("forms/stock/masters/manufacturercompany");
	}
	@RequestMapping(value="/addmanufacturercompany")
	public ModelAndView gotoaddCompany()
	{
		return new ModelAndView("forms/admin/masters/addmanufacturercompany");
	}
		
	@RequestMapping(value="/uom")
	public ModelAndView gotoUOM()
	{
		/*return new ModelAndView("forms/stock/masters/unitofmeasure");*/
		ModelAndView model = new ModelAndView();
		String fullUOMQuery = "SELECT `unit_type_id`,`uom_desc` FROM `hero_admin_unit_type` WHERE`uom_type`= 0 order by uom_desc asc";
		List<Object> fullUOMList = inventoryLOVobj.getLOVList(fullUOMQuery);
		model.addObject("fulluom",fullUOMList);
		model.setViewName("forms/stock/masters/uommaster");
		return model;
	}
	
	@RequestMapping(value="/adduom")
	public ModelAndView gotoaddUOM()
	{
		return new ModelAndView("forms/admin/masters/addunitofmeasurement");
	}

	@RequestMapping(value="/currency")
	public ModelAndView gotoCurrency()
	{
		return new ModelAndView("forms/masters/currency");
	}
	
	@RequestMapping(value="/tax")
	public ModelAndView gotoTaxMaster()
	{
		return new ModelAndView("forms/masters/tax");
	}
	
	
	@RequestMapping(value="/supplier")
	public ModelAndView gotoSupplierMaster()
	{
		  String suppliertypeQuery = "SELECT supplier_type_id,supplier_type FROM hero_stock_supplier_type";

		  List<Object> suppliertypeList = inventoryLOVobj.getLOVList(suppliertypeQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("suppliertypeList",suppliertypeList);
		  
		  model.setViewName("forms/stock/masters/supplier");
		  
		  return model;
	}
	
	@RequestMapping(value="/addsupplier")
	public ModelAndView gotoAddSupplierMaster()
	{
		/*return new ModelAndView("forms/masters/addsupplier");*/
		  String suppliertypeQuery = "SELECT supplier_type_id,supplier_type FROM hero_stock_supplier_type";

		  List<Object> suppliertypeList = inventoryLOVobj.getLOVList(suppliertypeQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("suppliertypeList",suppliertypeList);
		  
		  model.setViewName("/forms/stock/masters/addsupplier");
		  
		  return model;
	}
	
	@RequestMapping(value="/supplierview")
	public ModelAndView gotoSupplierView(@RequestParam("supplier") String supplier)
	{
		/*return new ModelAndView("forms/masters/addsupplier");*/
		  String suppliertypeQuery = "SELECT `supplier_id`,(`supplier_first_name`)suppliername FROM `hero_stock_supplier` a  ORDER BY a.`created_date` DESC";
		  String supplierContactQuery = "SELECT `isc_id`,`supplier_id`,`isc_prefix`,`isc_contact_name`,`isc_email`,`isc_phoneno`,`isc_designation` FROM `hero_stock_supplier_contact` "
		  		+ "WHERE `supplier_id` = "+supplier;
		  
		  List<Object> suppliertypeList = inventoryLOVobj.getLOVList(suppliertypeQuery);
		  List<Object> supplierContactList = inventoryLOVobj.getSupplierContactList(supplierContactQuery);
		  
		  log.info("supplierContactList size  "+supplierContactList.size()+"   "+supplierContactQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("suppliertypeList",suppliertypeList);
		  model.addObject("supplierContactList",supplierContactList);
		  
		  model.setViewName("/forms/masters/supplierview");
		  
		  return model;
	}
	
	@RequestMapping(value="/customergroup")
	public ModelAndView gotoCustomerGroup()
	{
		return new ModelAndView("forms/stock/masters/customergroup");
	}
	
	@RequestMapping(value="/addcustomergroup")
	public ModelAndView gotoaddCustomerGroup()
	{
		return new ModelAndView("forms/admin/masters/addcustomergroup");
	}

	@RequestMapping(value="/customer")
	public ModelAndView gotoCustomer()
	{
		return new ModelAndView("forms/masters/customer");
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
		  
		  model.setViewName("forms/masters/customerview");
		  
		return model;
	}
	
	@RequestMapping(value="/addcustomer")
	public ModelAndView gotoAddCustomer()
	{
		  String customergroupQuery = "SELECT `seq_id`,`customer_group_name` FROM `hero_admin_customer_group`";

		  List<Object> customergroupList = inventoryLOVobj.getLOVList(customergroupQuery);
		  
		  ModelAndView model = new ModelAndView();
		  
		  model.addObject("customergroupList",customergroupList);
		  
		  model.setViewName("forms/masters/addcustomer");
		  
		  return model;
		  
		
	}
	
	@RequestMapping(value="/userrole")
	public ModelAndView gotoUserRole()
	{
		String usertypeQuery = "SELECT `usertype_id`,`usertype_name` FROM `hero_user_type`";
		String menuQuery = "SELECT `moduleid`,`modulename`,`issubmodule`,`parentid`,0 user_menu_sno,0 userid,0 usertype,0 menuaccess"
				+ " FROM `hero_module` WHERE `status` = '1' ORDER BY `moduleid` ASC";
		log.info("menuQuery    "+menuQuery);
		log.info("role comes inside");
		List<Object> usertypeList = inventoryLOVobj.getLOVList(usertypeQuery);
		List<Object> usermenuList = new ArrayList<Object>();
		  
		if(usertypeList != null && usertypeList.size() > 0)
		{
			  HERO_STK_INVENTORYLOV lov = (HERO_STK_INVENTORYLOV) usertypeList.get(0) ;
			  String usertype = lov.getValue();
			  /*String usermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
			  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`"
						+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
						+ "WHERE `user_type_id` = "+usertype+" ORDER BY `userid`,`moduleid` ASC";*/
			  
			  String usermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,COALESCE(`menu_access`,0) menu_access "
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` WHERE `status` = '1' "
							+ "AND `applntype` != '0' GROUP BY A.`moduleid` ORDER BY `moduleid`,`userid` ASC";
				
			  log.info("usermenuQuery    "+usermenuQuery);
			  
			  usermenuList = inventoryLOVobj.getUsermenuList(menuQuery,usermenuQuery);
			  log.info("usermenuList    "+usermenuList);
		}
		  
		ModelAndView model = new ModelAndView();
		  
		model.addObject("usertypeList",usertypeList);
		model.addObject("usermenuList",usermenuList);
		  
		model.setViewName("forms/masters/userrole");
		  
		return model;
		  
		
	}
	
	@RequestMapping(value="/user")
	public ModelAndView gotoUserList()
	{
		 
		ModelAndView model = new ModelAndView();
		 
		model.setViewName("/forms/masters/user");
		return model;
		
	}
	
	@RequestMapping(value="/adduser")
	public ModelAndView gotoNewUser()
	{
		String usertypeQuery = "SELECT `usertype_id`,`usertype_name` FROM `hero_user_type` where usertype_id != 1";
		String storeQuery = "SELECT `store_id`,`store_name` FROM `hero_stock_store`";
		
		List<Object> usertypeList = inventoryLOVobj.getLOVList(usertypeQuery);
		List<Object> storeList = inventoryLOVobj.getLOVList(storeQuery);
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("usertypeList",usertypeList);
		model.addObject("storeList",storeList);
		
		model.setViewName("/forms/masters/adduser");
		return model;
		
	}
	
	@RequestMapping(value="/dish")
	public ModelAndView dish()
	{
		ModelAndView model = new ModelAndView();		
		return new ModelAndView("forms/stock/masters/dish");
	}	
	
	@RequestMapping(value="/dishtype")
	public ModelAndView dishtype()	{
		
		
		String dishQuery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes`  WHERE `status`=1 ";		
		List<Object> dishList = inventoryLOVobj.getLOVList(dishQuery);		
		ModelAndView model = new ModelAndView();		
		model.addObject("dishList",dishList);		  
		model.setViewName("/forms/stock/masters/dishtype");
		  
		return model;
	
	
    }
	
	
	@RequestMapping(value="/terms")
	public ModelAndView termsandconditions()
	{
          ModelAndView model = new ModelAndView();
		  model.setViewName("/forms/stock/masters/termsandconditions");
		  
		  return model;
		 
		
	
    }
	
	@RequestMapping(value="/companymaster")
	public ModelAndView companymaster()
	{
		/*return new ModelAndView("forms/stock/masters/unitofmeasure");*/
		ModelAndView model = new ModelAndView();
		String fullUOMQuery = "SELECT `unit_type_id`,`uom_desc` FROM `hero_admin_unit_type` WHERE`uom_type`= 0 order by uom_desc asc";
		List<Object> fullUOMList = inventoryLOVobj.getLOVList(fullUOMQuery);
		model.addObject("fulluom",fullUOMList);
		model.setViewName("forms/stock/masters/companymaster");
		return model;
	}

	@RequestMapping(value="/addcustomermenu")
	public ModelAndView addcustomermenu(@RequestParam("customerid") String customerid)
	{
		
		ModelAndView model = new ModelAndView();
		String dishQuery = "SELECT `dish_id`,`dish_name` FROM `hero_stock_dishes` WHERE `status`=1";
		List<Object> dishList = inventoryLOVobj.getLOVList(dishQuery);
		model.addObject("dishList",dishList);
		model.setViewName("forms/stock/masters/addcustomermenu");
		return model;
	}
}
