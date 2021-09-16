package com.hero.controller.services.stock.toolsmodule;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hero.forms.services.admin.tools.barcode.HERO_ADM_SERVC_BARCODEHELPER;
import com.hero.forms.services.admin.tools.barcode.HERO_ADM_SERVC_iBARCODEDAO;
import com.hero.forms.services.admin.tools.expirydatechecker.HERO_ADM_SERVC_EXPIRYDATECHECKERHELPER;
import com.hero.forms.services.admin.tools.expirydatechecker.HERO_ADM_SERVC_iEXPIRYDATECHECKERDAO;
import com.hero.forms.services.stock.tools.lowstock.HERO_STK_SERVC_ILOWSTOCKDAO;
import com.hero.forms.services.stock.tools.lowstock.HERO_STK_SERVC_LOWSTOCKHELPER;
import com.hero.forms.services.stock.tools.stockmonitor.HERO_STK_SERVC_ISTOCKMONITORDAO;
import com.hero.forms.services.stock.tools.stockmonitor.HERO_STK_SERVC_STOCKMONITORHELPER;
import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.lov.HERO_ADM_SERVC_INVENTORYLOVIMPL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

@Controller
public class HERO_STK_SERVC_TOOLSMODULE extends HERO_ADM_SERVC_INVENTORYDAO{

	@Autowired
	protected HERO_STK_SERVC_STOCKMONITORHELPER stockMonitorHelperOBJ;
	@Autowired
	protected HERO_STK_SERVC_ISTOCKMONITORDAO istockMonitorDAOOBJ;
	
	@Autowired
	protected HERO_ADM_SERVC_EXPIRYDATECHECKERHELPER expirydateHelperOBJ;
	@Autowired
	protected HERO_ADM_SERVC_iEXPIRYDATECHECKERDAO iexpirydateDAOOBJ;
	
	@Autowired
	protected HERO_ADM_SERVC_BARCODEHELPER barcodeHelperOBJ;
	@Autowired
	protected HERO_ADM_SERVC_iBARCODEDAO ibarcodeDAOOBJ;
	
	@Autowired
	protected HERO_STK_SERVC_LOWSTOCKHELPER lowstockHelperOBJ;
	@Autowired
	protected HERO_STK_SERVC_ILOWSTOCKDAO ilowstockDAOOBJ;
	
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_TOOLSMODULE.class);
	
	@RequestMapping(value="/stockmonitorlist/{storeid}/{manufacturerid}/{categoryid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStockMonitorList(@PathVariable("storeid")String storeid,
													  @PathVariable("manufacturerid")String manufacturerid,
													  @PathVariable("categoryid")String categoryid,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = stockMonitorHelperOBJ.getStockMonitorList(istockMonitorDAOOBJ,storeid,manufacturerid,categoryid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/stockmonitorlistdetails/{storeid}/{productid}/{categoryid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStockMonitorListDetails(@PathVariable("storeid")String storeid,
													  @PathVariable("productid")String productid,
													  @PathVariable("categoryid")String categoryid)
	{
		inventoryResponseInfoOBJ = stockMonitorHelperOBJ.getStockMonitorListDetails(istockMonitorDAOOBJ,storeid,productid,categoryid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/expiryproductlist/{storeid}/{expiredtype}/{expireddays}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getExpiryProductListDetails(@PathVariable("storeid")String storeid,
															  @PathVariable("expiredtype")String expiredtype,
															  @PathVariable("expireddays")String expireddays)
	{
		inventoryResponseInfoOBJ = expirydateHelperOBJ.getExpiryProductListDetails(iexpirydateDAOOBJ,storeid,expiredtype,expireddays);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getbarcodeproducts/{storeid}/{productid}/{batchid}/{eventtype}/{changecomp}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getBarcodeProductsOnload(@PathVariable("storeid")String storeid,
														   @PathVariable("productid")String productid,
														   @PathVariable("batchid")String batchid,
														   @PathVariable("eventtype")String eventtype,
														   @PathVariable("changecomp")String changecomp)
	{
		inventoryResponseInfoOBJ = barcodeHelperOBJ.getBarcodeProducts(ibarcodeDAOOBJ,storeid,productid,batchid,eventtype,changecomp);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/lowstockdatas",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getLowstockAlert(HttpServletRequest httpRequest)
	{
		List<Object> lowStockList = inventoryLOVobj.getLowStockList(httpRequest);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(lowStockList);
		log.info("lowStockList  "+lowStockList);
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/lowstockproductlist/{storeid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getLowStockProductListDetails(@PathVariable("storeid")String storeid)
	{
		List<Object> lowStockListStore = lowstockHelperOBJ.getLowStockProductListDetails(ilowstockDAOOBJ,storeid);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(lowStockListStore);
		log.info("lowStockList  "+lowStockListStore);
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
}
