package com.hero.controller.services.stock.mastersmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.print.attribute.HashAttributeSet;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hero.forms.services.admin.masters.address.HERO_ADM_SERVC_ADDRESSDAOOBJ;
import com.hero.forms.services.admin.masters.address.HERO_ADM_SERVC_ADDRESS_HELPER;


import com.hero.forms.services.admin.masters.currency.HERO_ADM_SERVC_CURRENCYHELPER;
import com.hero.forms.services.admin.masters.currency.HERO_ADM_SERVC_ICURRENCYDAO;
import com.hero.forms.services.admin.masters.tax.HERO_ADM_SERVC_ITAXDAO;
import com.hero.forms.services.admin.masters.tax.HERO_ADM_SERVC_TAXHELPER;
import com.hero.forms.services.admin.masters.txncode.HERO_ADM_SERVC_ITXNCODEDAO;
import com.hero.forms.services.admin.masters.txncode.HERO_ADM_SERVC_TXNCODEHELPER;
import com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_IUSERDAO;
import com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_USERHELPER;
import com.hero.forms.services.admin.masters.usertypes.HERO_ADM_SERVC_IUSERTYPEDAO;
import com.hero.forms.services.admin.masters.usertypes.HERO_ADM_SERVC_USERTYPEHELPER;
import com.hero.forms.services.stock.masters.customer.HERO_STK_SERVC_CUSTOMERHELPER;
import com.hero.forms.services.stock.masters.customer.HERO_STK_SERVC_ICUSTOMERDAO;
import com.hero.forms.services.stock.masters.store.HERO_STK_SERVC_ISTOREDAO;
import com.hero.forms.services.stock.masters.store.HERO_STK_SERVC_STOREHELPER;
import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;


@Controller
public class HERO_STK_SERVC_MASTERMODULE extends HERO_ADM_SERVC_INVENTORYDAO{

	private static Logger log = Logger.getLogger(HERO_STK_SERVC_MASTERMODULE.class);

	@Autowired
	private HERO_STK_SERVC_CUSTOMERHELPER customerHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj;
	
	
	@Autowired
	private HERO_ADM_SERVC_CURRENCYHELPER currencyHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_ICURRENCYDAO currencyDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_TAXHELPER taxHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_ITAXDAO taxDAOobj;
	
	@Autowired
	private HERO_STK_SERVC_STOREHELPER storeHelperOBJ;
	@Autowired
	private HERO_STK_SERVC_ISTOREDAO storeDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_USERHELPER userroleHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_ADDRESS_HELPER addressMasterHelperOBJ;	
	@Autowired
	private HERO_ADM_SERVC_ADDRESSDAOOBJ addressDAOObj;
	
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@Autowired
	private HERO_ADM_SERVC_USERTYPEHELPER usertypeHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_IUSERTYPEDAO usertypeDAOobj;
	
	@Autowired
	private HERO_ADM_SERVC_TXNCODEHELPER txncodeHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_ITXNCODEDAO txncodeDAOobj;
	
	
	@RequestMapping(value="/savecurrency")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecurrency(@RequestBody String currencyData)
	{
		inventoryResponseInfoOBJ = currencyHelperOBJ.savecurrency(currencyData, currencyDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/currencylist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcurrency()
	{
		inventoryResponseInfoOBJ = currencyHelperOBJ.loadcurrency(currencyDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/currencysymbolsuggestions")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcurrencysymbolsuggestions()
	{
		inventoryResponseInfoOBJ = currencyHelperOBJ.loadcurrencysymbolsuggestions(currencyDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savetax")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savetax(@RequestBody String taxData)
	{
	inventoryResponseInfoOBJ = taxHelperOBJ.savetax(taxData, taxDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/taxlist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadtax()
	{
		inventoryResponseInfoOBJ = taxHelperOBJ.loadtax(taxDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/savestore")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savestore(@RequestBody String storeData)
	{
	inventoryResponseInfoOBJ = storeHelperOBJ.savestore(storeData, storeDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/storelist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadstore()
	{
		inventoryResponseInfoOBJ = storeHelperOBJ.loadstore(storeDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="/savecustomer")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecustomer(@RequestBody String customerData)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.savecustomer(customerData, customerDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadcustomers")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomers(HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomers(customerDAOobj,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadaddcustomer/{customerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadaddcustomer(@PathVariable("customerid") String customerid)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadaddcustomer(customerDAOobj,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/loadcustomerviewpagewise/{pageno}/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomerviewpagewise(@PathVariable("pageno") String pageno,@PathVariable("status") String status,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomerviewpagewise(customerDAOobj,pageno,status,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/loadcustomerviewstatuswise/{status}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadcustomerviewstatuswise(@PathVariable("status") String status,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.loadcustomerviewstatuswise(customerDAOobj,status,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/customerviewdetails/{customerid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> customeroverviewDetails(@PathVariable("customerid") String customerid)
	{
		inventoryResponseInfoOBJ = customerHelperOBJ.customeroverviewDetails(customerDAOobj,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveuserrole/{usertype}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveuserrole(@RequestBody String userroledata,@PathVariable("usertype") String usertype)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.saveuserrole(iuserroleDAOobj,userroledata,usertype);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savereportuserrole/{usertype}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savereportuserrole(@RequestBody String userroledata,@PathVariable("usertype") String usertype)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.savereportuserrole(iuserroleDAOobj,userroledata,usertype);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuserrolemenus/{usertype}/{applntype}")
	public ResponseEntity<Object> gotoUserRole(@PathVariable("usertype") String usertype, @PathVariable("applntype") String applntype)
	{
		String usertypeQuery = "SELECT `usertype_id`,`usertype_name` FROM `hero_user_type`";
		String menuQuery = "SELECT `moduleid`,`modulename`,`issubmodule`,`parentid`,0 user_menu_sno,0 userid,0 usertype,0 menuaccess, 0 isreportmenu"
				+ " FROM `hero_module` WHERE `applntype` = '"+applntype+"' ORDER BY `moduleid` ASC";
		String reportmenuQuery = "SELECT `moduleid`,`modulename`,`issubmodule`,`parentid`,0 user_menu_sno,0 userid,0 usertype,0 menuaccess, 0 isreportmenu"
				+ " FROM `hero_reports_module` WHERE `applntype` = '"+applntype+"' ORDER BY `moduleid` ASC";
		log.info("menuQuery    "+menuQuery);
		
		List<Object> usertypeList = inventoryLOVobj.getLOVList(usertypeQuery);
		
		List<Object> usermenuList = new ArrayList<Object>();
		List<Object> userreportmenuList = new ArrayList<Object>();
		  
		if(usertypeList != null && usertypeList.size() > 0)
		{
			  
			  String usermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
			  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`, COALESCE(`isreportmenu`,0) isreportmenu"
						+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
						+ "WHERE `user_type_id` = "+usertype+" ORDER BY `userid`,`moduleid` ASC";
			 
			  String reportusermenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`, COALESCE(`isreportmenu`,0) isreportmenu"
							+ " FROM `hero_reports_module` A LEFT OUTER JOIN `hero_reports_user_menus` B ON A.`moduleid` = B.`moduleid` "
							+ "WHERE `user_type_id` = "+usertype+" ORDER BY `userid`,`moduleid` ASC";
			  
				
			  log.info("usermenuQuery    "+usermenuQuery);
			  
			  usermenuList = inventoryLOVobj.getUsermenuList(menuQuery,usermenuQuery);
			  userreportmenuList = inventoryLOVobj.getUsermenuList(reportmenuQuery,reportusermenuQuery);
			  
			  
		}
		
		inventoryResponseOBJ.setResponseType("S");
		Map<String, Object> usermenumap = new HashMap<String, Object>();
		usermenumap.put("usermenuList", usermenuList);
		usermenumap.put("userreportmenuList", userreportmenuList);
		
		inventoryResponseOBJ.setResponseObj(usermenumap);
		
		/*inventoryResponseOBJ.setResponseObj(usermenuList);*/
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
			
	}
	
	@RequestMapping(value="/loadusers")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> userlist()
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.userlist(iuserroleDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuserdetail/{userid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getUserDetail(@PathVariable("userid")String userid,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.getUserDetail(iuserroleDAOobj,userid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getuserlocationlist/{usertypeid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getUserLocationList(@PathVariable("usertypeid")String usertypeid)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.getUserLocationList(iuserroleDAOobj,usertypeid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveuser")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveuser(@RequestBody String userdata)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.saveuser(iuserroleDAOobj,userdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveguestuser")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveguestuser(@RequestBody String userdata)
	{
		inventoryResponseInfoOBJ = userroleHelperOBJ.saveguestuser(iuserroleDAOobj,userdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/country",method = RequestMethod.GET)
	public ResponseEntity<Object> getCountry()
	{
		inventoryResponseInfoOBJ = addressMasterHelperOBJ.getCountry(addressDAOObj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/states/{countryId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStates(@PathVariable int countryId)
	{
		log.info("countryid   "+countryId);
		inventoryResponseInfoOBJ = addressMasterHelperOBJ.getStates(addressDAOObj,countryId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/cities/{stateId}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCities(@PathVariable int stateId)
	{
		log.info("stateId   "+stateId);
		inventoryResponseInfoOBJ = addressMasterHelperOBJ.getCities(addressDAOObj,stateId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/usertypeslist")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadusertypes()
	{
		inventoryResponseInfoOBJ = usertypeHelperOBJ.loadusertypes(usertypeDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveusertype")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveusertype(@RequestBody String usertypeData,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = usertypeHelperOBJ.saveusertype(usertypeDAOobj,usertypeData,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/transactioncodelist/{storeid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> transactioncodelist(@PathVariable(value="storeid") String storeid,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = txncodeHelperOBJ.transactioncodelist(txncodeDAOobj,storeid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savetxncodemasterdata")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savetxncode(@RequestBody String txncodeData,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = txncodeHelperOBJ.savetxncode(txncodeDAOobj,txncodeData,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/transactioncodedetail/{txncodeid}")
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> transactioncodedetail(@PathVariable(value="txncodeid") String txncodeid,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = txncodeHelperOBJ.transactioncodedetail(txncodeDAOobj,txncodeid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
}
