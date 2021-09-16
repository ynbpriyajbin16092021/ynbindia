package com.hero.stock.controller.services.salesmodule;

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

import com.hero.stock.forms.sales.pos.HERO_STK_IPOSDAO;
import com.hero.stock.forms.sales.pos.HERO_STK_POSHELPER;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

@Controller
public class HERO_STK_SERVC_SALESMODULECTRL extends HERO_STK_INVENTORYDAO {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_SALESMODULECTRL.class);
	@Autowired
	private HERO_STK_POSHELPER posHelperOBJ;
	@Autowired
	private HERO_STK_IPOSDAO posDAOobj;
	
	@RequestMapping(value="/getcustomerautocomplete",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getcustomerautocomplete(
			/*@PathVariable("custname")String custname*/HttpServletRequest httpRequest
			)
	{
		/*log.info("Values        "+custname);*/
		inventoryResponseInfoOBJ = posHelperOBJ.getCustomerDetails(posDAOobj/*, custname*/,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getproductautocomplete/{userid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getProductItems(
			@PathVariable("userid")String userid
			)
	{
		log.info("userid        "+userid);
		inventoryResponseInfoOBJ = posHelperOBJ.getProductItems(posDAOobj, userid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getposproductautocomplete/{userid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPOSProductItems(
			@PathVariable("userid")String userid
			)
	{
		log.info("userid        "+userid);
		inventoryResponseInfoOBJ = posHelperOBJ.getPOSProductItems(posDAOobj, userid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getposproductusingbarcode/{barcode}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getProductUsingBarCode(
			@PathVariable("barcode")String barcode
			)
	{
		log.info("barcode        "+barcode);
		inventoryResponseInfoOBJ = posHelperOBJ.getProductUsingBarCode(posDAOobj, barcode);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getcustomerdetail/{customerid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCustomerDetail(
			@PathVariable("customerid")String customerid
			)
	{
		log.info("customerid        "+customerid);
		inventoryResponseInfoOBJ = posHelperOBJ.getCustomerDetail(posDAOobj, customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getproductfromtxr/{tprid}/{userid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getProductfromTxr(
			@PathVariable("tprid")String tprid,
			@PathVariable("userid")String userid
			)
	{
		/*log.info("Values        "+custname);*/
		inventoryResponseInfoOBJ = posHelperOBJ.getProductfromTxr(posDAOobj,tprid,userid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savepos",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savePOS(@RequestBody String posdata)
	{
		log.info("posdata        "+posdata);
		inventoryResponseInfoOBJ = posHelperOBJ.savePOS(posDAOobj, posdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/poshistorydetails/{storeid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> POSHistoryDetails(@PathVariable("storeid") String storeid)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.POSHistoryDetails(posDAOobj, storeid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/deletepos/{posid}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> deletePOS(@PathVariable("posid") String posid)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.deletePOS(posDAOobj, posid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getposdetails/{posid}/{storeid}/{type}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPOSDetails(@PathVariable("posid") String posid,@PathVariable("storeid") String storeid,@PathVariable("type") String type)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.getPOSDetails(posDAOobj, posid,storeid,type);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getbilldetails/{billno}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getBillDetails(@PathVariable("billno") String billno)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.getBillDetails(posDAOobj, billno);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getcustomerorders/{storeid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCustomerOrders(@PathVariable("storeid")String storeid)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.getCustomerOrders(posDAOobj,storeid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveorderstatus",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveOrderStatus(@RequestBody String orderdata)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.saveOrderStatus(posDAOobj,orderdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getorderitems/{posid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getOrderItems(@PathVariable("posid") String posid)
	{
		
		inventoryResponseInfoOBJ = posHelperOBJ.getOrderItems(posDAOobj,posid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
}
