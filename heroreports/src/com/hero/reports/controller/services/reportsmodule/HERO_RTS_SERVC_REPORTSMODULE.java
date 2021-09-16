package com.hero.reports.controller.services.reportsmodule;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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







import com.hero.reports.forms.stock.reports.HERO_RTS_STK_IREPORTSDAO;
import com.hero.reports.forms.stock.reports.HERO_RTS_STK_REPORTSHELPER;
import com.hero.reports.util.HERO_RTS_INVENTORYDAO;

@Controller
public class HERO_RTS_SERVC_REPORTSMODULE extends HERO_RTS_INVENTORYDAO{

	@Autowired
	private HERO_RTS_STK_REPORTSHELPER reportsHelperOBJ;
	@Autowired
	private HERO_RTS_STK_IREPORTSDAO reportsDAOobj;
	
	@RequestMapping(value="/inventoryreports/{reportid}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> salesbyproduct(@RequestBody String reportsData,@PathVariable("reportid")int reportid,HttpServletRequest httpRequest)
	{
		
		inventoryResponseInfoOBJ = reportsHelperOBJ.salesbyproduct(reportsData, reportid, httpRequest, reportsDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/orderReports/{status}/{customerId}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> orderReports(@RequestBody String reportsData,@PathVariable("status")int status,@PathVariable("customerId")int customerId,HttpServletRequest httpRequest)
	{
		
		inventoryResponseInfoOBJ = reportsHelperOBJ.orderReports(reportsData, status,customerId, httpRequest, reportsDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getorgndetails",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getorgndetails(@RequestBody String reportsData,HttpServletRequest httpRequest)
	{
		
		inventoryResponseInfoOBJ = reportsHelperOBJ.getorgndetails(reportsData, httpRequest, reportsDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadstorerequestreport",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadstorerequestreport(@RequestBody String reportsData,HttpServletRequest httpRequest)
	{
		
		inventoryResponseInfoOBJ = reportsHelperOBJ.loadstorerequestreport(reportsData, httpRequest, reportsDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/productsuggestions",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getproductsuggestions()
	{
				
		inventoryResponseInfoOBJ = reportsHelperOBJ.getproductsuggestions( reportsDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getpurchasebyproduct/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getpurchasebyproduct(@PathVariable("productid")int productid)
	{
		System.out.print("getpurchasebyproduct "+ productid);
				
		inventoryResponseInfoOBJ = reportsHelperOBJ.getpurchasebyproduct( reportsDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getOutputQtyReport/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getOutputQtyReport(@PathVariable("productid")int productid)
	{
		System.out.print("getOutputQtyReport "+ productid);
				
		inventoryResponseInfoOBJ = reportsHelperOBJ.getOutputQtyReport( reportsDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/generateStockReport/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> generateStockReport(@PathVariable("startdate")String startdate,@PathVariable("enddate")String enddate)
	{
		System.out.print("generateStockReport "+ startdate);
				
		inventoryResponseInfoOBJ = reportsHelperOBJ.generateStockReport( reportsDAOobj,startdate,enddate);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
}
