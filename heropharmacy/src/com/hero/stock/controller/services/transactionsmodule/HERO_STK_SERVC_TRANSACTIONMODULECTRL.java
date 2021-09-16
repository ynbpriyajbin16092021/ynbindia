package com.hero.stock.controller.services.transactionsmodule;

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

import com.hero.stock.forms.transactions.bills.HERO_STK_BILLSHELPER;
import com.hero.stock.forms.transactions.bills.HERO_STK_IBILLSDAO;
import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERHELPER;
import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_IADDPURCHASEORDERDAO;
import com.hero.stock.forms.transactions.stock.HERO_STK_ISTOCKMODULEDAO;
import com.hero.stock.forms.transactions.stock.HERO_STK_STOCKMODULEHELPER;
import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

@Controller
public class HERO_STK_SERVC_TRANSACTIONMODULECTRL {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_TRANSACTIONMODULECTRL.class);
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@Autowired
	private HERO_STK_ADDPURCHASEORDERHELPER addpurchaseorderHelperOBJ;
	@Autowired
	private HERO_STK_IADDPURCHASEORDERDAO addpurchaseorderDAOobj;
	
	@Autowired
	private HERO_STK_STOCKMODULEHELPER stockHelperOBJ;
	@Autowired
	private HERO_STK_ISTOCKMODULEDAO stockDAOobj;
	
	@Autowired
	private HERO_STK_BILLSHELPER billsHelperOBJ;
	@Autowired
	private HERO_STK_IBILLSDAO billsDAOobj;
	
	@Autowired
	private HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;
	

	@RequestMapping(value="/uomforpacking/{uompackingid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getuomforpacking(@PathVariable(value="uompackingid")String uompackingid)
	{
		log.info("welcome to uomforpacking"+uompackingid);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getuomforpacking(addpurchaseorderDAOobj,uompackingid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/calculatelooseqty",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> calculatelooseqty(@RequestBody String uomdata)
	{
		log.info("welcome to uomforpacking"+uomdata);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.calculatelooseqty(addpurchaseorderDAOobj,uomdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	public ResponseEntity<Object> calculateordlooseqty(@RequestBody String uomdata)
	{
		log.info("welcome to uomforpacking"+uomdata);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.calculatelooseqty(addpurchaseorderDAOobj,uomdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/purchaseorderlist/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseorderList(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.purchaseorderlist(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/purchaserequestlist/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseRequestlist(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.purchaserequestlist(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getPurchaseDishList/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseDishList(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseDishList(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getDishtypeList/{dishid}/{hdrid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getDishtypeList(@PathVariable("dishid")String dishid,@PathVariable("hdrid")String hdrid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getDishtypeList(addpurchaseorderDAOobj,dishid,hdrid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getcustomerDishtypeList/{dishid}/{customerid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getcustomerDishtypeList(@PathVariable("dishid")String dishid,@PathVariable("customerid")String customerid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getcustomerDishtypeList(addpurchaseorderDAOobj,dishid,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getcustomermenuById/{customerid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getcustomermenuById(@PathVariable("customerid")String customerid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getcustomermenuById(addpurchaseorderDAOobj,customerid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getCustomerList/{orderId}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getCustomerList(@PathVariable("orderId")String orderId)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getCustomerList(addpurchaseorderDAOobj,orderId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getPendingtyransferItemList/{transferId}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPendingtyransferItemList(@PathVariable("transferId")String transferId)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPendingtyransferItemList(addpurchaseorderDAOobj,transferId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getDishList/{customerId}/{orderId}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getDishList(@PathVariable("customerId")String customerId,@PathVariable("orderId")String orderId)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getDishList(addpurchaseorderDAOobj,customerId,orderId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getcusdishTypeList/{customerId}/{orderId}/{dishId}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getcusdishTypeList(@PathVariable("customerId")String customerId,@PathVariable("orderId")String orderId,@PathVariable("dishId")String dishId)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getcusdishTypeList(addpurchaseorderDAOobj,customerId,orderId,dishId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getDishProductDetails/{dishid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getDishProductDetails(@PathVariable("dishid")String dishid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getDishProductDetails(addpurchaseorderDAOobj,dishid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getPurchaseRequestDishList/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseRequestDishList(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseRequestDishList(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadpurchasepagewise/{pageno}/{pid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseorderListPagewise(@PathVariable("pageno")String pageno,@PathVariable("pid")String pid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseorderListPagewise(addpurchaseorderDAOobj,pageno,pid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveaddpurchaseorder",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveCategory(@RequestBody String purchaseorderData)
	{
		log.info("Values        "+purchaseorderData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.savepurchaseorder(addpurchaseorderDAOobj,purchaseorderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveitemrequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> approveitemrequest(@RequestBody String purchaseorderData)
	{
		log.info("Values        "+purchaseorderData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.approveitemrequest(addpurchaseorderDAOobj,purchaseorderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/saveaddpurchaserequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveaddpurchaserequest(@RequestBody String purchaseorderData)
	{
		log.info("Values        "+purchaseorderData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.saveaddpurchaserequest(addpurchaseorderDAOobj,purchaseorderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/saveadddishrequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveadddishrequest(@RequestBody String purchaseorderData)
	{
		log.info("Values        "+purchaseorderData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.saveadddishrequest(addpurchaseorderDAOobj,purchaseorderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/getOrderRequestDetails/{orderId}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getOrderRequestDetails(@PathVariable("orderId")String orderId)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getOrderRequestDetails(stockDAOobj,orderId);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/saveorderrequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveorderrequest(@RequestBody String orderData)
	{
		log.info("Values        "+orderData);
		inventoryResponseInfoOBJ = stockHelperOBJ.saveorderrequest(stockDAOobj,orderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savecustomermenu",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savecustomermenu(@RequestBody String orderData)
	{
		log.info("Values        "+orderData);
		inventoryResponseInfoOBJ = stockHelperOBJ.savecustomermenu(stockDAOobj,orderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/delPurchaseOrderRequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> delPurchaseOrderRequest(@RequestBody String purchaseorderData)
	{
		log.info("Values        "+purchaseorderData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.delPurchaseOrderRequest(addpurchaseorderDAOobj,purchaseorderData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/deletepurchaseorder/{purchaseorderid}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> deletePurchaseorder(@PathVariable("purchaseorderid")String purchaseorderid,@RequestBody String remarksData)
	{
		log.info("purchaseorderid        "+purchaseorderid);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.deletePurchaseorder(addpurchaseorderDAOobj,purchaseorderid,remarksData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/processreceivestock",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> processreceivestock(@RequestBody String receiveStockData)
	{
		log.info("receiveStockData        "+receiveStockData);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.processreceivestock(addpurchaseorderDAOobj,receiveStockData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/pobarcode/{productid}/{batchno}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> pobarcode(@PathVariable("productid")String productid,@PathVariable("batchno")String batchno)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.pobarcode(addpurchaseorderDAOobj,productid,batchno);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/pocheckbarcode/{checkbarcodeno}/{productid}/{batchno}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> pocheckbarcode(@PathVariable("checkbarcodeno")String checkbarcodeno,@PathVariable("productid")String productid,@PathVariable("batchno")String batchno)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.pocheckbarcode(addpurchaseorderDAOobj,checkbarcodeno,productid,batchno);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/receivelist/{purchaseid}/{purchaseheaderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getReceiveList(@PathVariable("purchaseid")String purchaseid,@PathVariable("purchaseheaderid")String purchaseheaderid, HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getReceiveList(addpurchaseorderDAOobj,purchaseid,purchaseheaderid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getPurchaseRequestList/{purchaseid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseRequestList(@PathVariable("purchaseid")String purchaseid, HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseRequestList(addpurchaseorderDAOobj,purchaseid,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/billdetailslist/{purchaseid}/{billno}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getBillDetailsList(@PathVariable("purchaseid")String purchaseid,@PathVariable("billno")String billno)
	{
		log.info("billno       "+billno);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getBillDetailsList(addpurchaseorderDAOobj,purchaseid,billno);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/deletebill/{purchaseid}/{billno}/{userid}",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> deleteBill(@PathVariable("purchaseid")String purchaseid,@PathVariable("billno")String billno,
			@PathVariable("userid")String userid)
	{
		log.info("billno       "+billno);
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.deleteBill(addpurchaseorderDAOobj,purchaseid,billno,userid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatereceiveorderpayment",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> updatereceiveorderpayment(@RequestBody String paymentData)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.updatereceiveorderpayment(addpurchaseorderDAOobj,paymentData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getpurchasereturndata/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getpurchasereturndata(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getpurchasereturndata(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatepurchasereturn",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> updatepurchasereturn(@RequestBody String returnstockdata)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.updatepurchasereturn(addpurchaseorderDAOobj,returnstockdata);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/updatestockprice",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> stockview(@RequestBody String stockData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.updatestockprice(stockDAOobj,stockData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadstocktxrpagewise/{pageno}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadStockTxrPagewise(@PathVariable("pageno")String pageno,HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadStockTxrPagewise(stockDAOobj,pageno,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadstocktxrhistory",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadStockTxrHistory(HttpServletRequest httpRequest)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadStockTxrHistory(stockDAOobj,httpRequest);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/loadPurchaseOrderReqSelectBox",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadPurchaseOrderReqSelectBox()
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadPurchaseOrderReqSelectBox(stockDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/loadadjproductsuggestion/{storeid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadadjproductsuggestion(@PathVariable("storeid")String storeid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadadjproductsuggestion(stockDAOobj,storeid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadproductsuggestion",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadproductSuggestion()
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadProductSuggestion(stockDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadadjustmenthistory",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadadjustmenthistory()
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadadjustmenthistory(stockDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadbatchidsuggestion/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadbatchidSuggestion(@PathVariable("productid")String productid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadBatchidSuggestion(stockDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadtransferlist/{transferid}/{storeid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadtransferlist(@PathVariable("transferid")String transferid,@PathVariable("storeid")String storeid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadtransferlist(stockDAOobj,transferid,storeid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/stockdetail/{transferid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> stockdetail(@PathVariable("transferid")String transferid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.stockdetail(stockDAOobj,transferid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getstockproductdetail/{productid}/{batchid}/{userid}/{type}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getstockproductdetail(@PathVariable("productid")String productid,
			@PathVariable("batchid")String batchid,
			@PathVariable("userid")String userid,
			@PathVariable("type")String type)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getstockproductdetail(stockDAOobj,productid,batchid,userid,type);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savestocktransferdetails",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveStocktransfer(@RequestBody String transferData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.saveStocktransfer(stockDAOobj,transferData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savestockstatusupdate",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savestockstatusupdate(@RequestBody String statusData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.savestockstatusupdate(stockDAOobj,statusData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/productbatchdetails/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> productbatchdetails(@PathVariable("productid")String productid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.productbatchdetails(stockDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/billslist/{supplierid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> billslist(@PathVariable("supplierid")String supplierid)
	{
		inventoryResponseInfoOBJ = billsHelperOBJ.billslist(billsDAOobj,supplierid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/pohistoryDetails/{pid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> pohistoryDetails(@PathVariable("pid")String pid)
	{
		inventoryResponseInfoOBJ = billsHelperOBJ.pohistoryDetails(billsDAOobj,pid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savebulkbill",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savebulkbill(@RequestBody String billData)
	{
		inventoryResponseInfoOBJ = billsHelperOBJ.savebulkbill(billsDAOobj,billData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadadjustmentbatches/{productid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadadjustmentbatches(@PathVariable("productid")String productid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadadjustmentbatches(stockDAOobj,productid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveadjustments",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveAdjustments(@RequestBody String adjustmentData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.saveAdjustments(stockDAOobj,adjustmentData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/purchaseapprovedlist/{purchaseorderid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> purchaseapprovedlist(@PathVariable("purchaseorderid")String purchaseorderid)
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.purchaseapprovedlist(addpurchaseorderDAOobj,purchaseorderid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/loadPurchasedProductItems/{purchaseid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadPurchasedProductItems(@PathVariable("purchaseid")String purchaseid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.loadPurchasedProductItems(stockDAOobj,purchaseid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getstocktransferproducts/{purchaseid}/{batchid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStockTransferProducts(@PathVariable("purchaseid")String purchaseid,@PathVariable("batchid")String batchid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getStockTransferProducts(stockDAOobj,purchaseid,batchid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getPurchaseBillNoList/{purchaseid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseBillNoList(@PathVariable("purchaseid")String purchaseid)
	{
		
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseBillNoList(addpurchaseorderDAOobj,purchaseid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getdishTypeList/{dishid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getdishTypeList(@PathVariable("dishid")String dishid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getdishTypeList(stockDAOobj,dishid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/getIngredientsList/{dishid}/{dishtypeid}/{dishcount}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getIngredientsList(@PathVariable("dishid")String dishid,
			@PathVariable("dishtypeid")String dishtypeid,	@PathVariable("dishcount")int dishcount)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getIngredientsList(stockDAOobj,dishid,dishtypeid,dishcount);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value="/transferToKitchen",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> transferToKitchen(@RequestBody String transferData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.transferToKitchen(stockDAOobj,transferData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/approveStoreRequest",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> approveStoreRequest(@RequestBody String transferData)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.approveStoreRequest(stockDAOobj,transferData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getPurchaseGRNList/{purchaseid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getPurchaseGRNList(@PathVariable("purchaseid")String purchaseid)
	{
		
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getPurchaseGRNList(addpurchaseorderDAOobj,purchaseid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getStockProductDetails/{addproductid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getStockProductDetails(@PathVariable("addproductid")String addproductid)
	{
		inventoryResponseInfoOBJ = stockHelperOBJ.getStockProductDetails(stockDAOobj,addproductid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
		public ResponseEntity<Object> getTaxlist()
	{
		inventoryResponseInfoOBJ = addpurchaseorderHelperOBJ.getTaxlist(addpurchaseorderDAOobj);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
		
		@RequestMapping(value="/getTransferProducts/{addproductid}",method=RequestMethod.GET)
		@Produces(MediaType.APPLICATION_JSON)
		@ResponseBody
		public ResponseEntity<Object> getTransferProducts(@PathVariable("addproductid")String addproductid)
		{
			inventoryResponseInfoOBJ = stockHelperOBJ.getTransferProducts(stockDAOobj,addproductid);
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
		}	
		
		
		@RequestMapping(value="/loadstorerequest",method=RequestMethod.GET)
		@Produces(MediaType.APPLICATION_JSON)
		@ResponseBody
		public ResponseEntity<Object> loadstorerequest()
		{
			log.info("dgsgdsgsd");
			inventoryResponseInfoOBJ = stockHelperOBJ.loadstorerequest(stockDAOobj);
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
		}
	
		@RequestMapping(value="/saveaddtransferproduct",method=RequestMethod.POST)
		@Produces(MediaType.APPLICATION_JSON)
		@ResponseBody
		public ResponseEntity<Object> saveaddtransferproduct(@RequestBody String transferData)
		{
			inventoryResponseInfoOBJ = stockHelperOBJ.saveaddtransferproduct(stockDAOobj,transferData);
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
		}
}
