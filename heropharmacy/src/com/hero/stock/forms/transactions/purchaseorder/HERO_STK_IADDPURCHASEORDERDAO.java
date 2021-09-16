package com.hero.stock.forms.transactions.purchaseorder;


import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_IADDPURCHASEORDERDAO {
	public HERO_STK_RESPONSEINFO getuomforpacking(String uompackingid);
	public HERO_STK_RESPONSEINFO calculatelooseqty(String uomdata);
	public HERO_STK_RESPONSEINFO calculateordlooseqty(String uomdata);
	public HERO_STK_RESPONSEINFO savepurchaseorder(String purchaseorderData,HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj);
	public HERO_STK_RESPONSEINFO approveitemrequest(String purchaseorderData,HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj);
	public HERO_STK_RESPONSEINFO deletePurchaseorder(String purchaseorderid,String remarksData);
	public HERO_STK_RESPONSEINFO purchaseorderlist(String purchaseorderid);
	public HERO_STK_RESPONSEINFO purchaserequestlist(String purchaseorderid);
	public HERO_STK_RESPONSEINFO getPurchaseDishList(String purchaseorderid);
	public HERO_STK_RESPONSEINFO getDishtypeList(String dishid,String hdrid);
	public HERO_STK_RESPONSEINFO getcustomerDishtypeList(String dishid,String customerid);
	public HERO_STK_RESPONSEINFO getcustomermenuById(String customerid);
	public HERO_STK_RESPONSEINFO getCustomerList(String orderId);
	public HERO_STK_RESPONSEINFO getPendingtyransferItemList(String transferId);
	public HERO_STK_RESPONSEINFO getDishList(String customerId,String orderId);
	public HERO_STK_RESPONSEINFO getcusdishTypeList(String customerId,String orderId,String dishId);
	
	public HERO_STK_RESPONSEINFO getDishProductDetails(String dishid);
	public HERO_STK_RESPONSEINFO getPurchaseRequestDishList(String purchaseorderid);
	public HERO_STK_RESPONSEINFO getPurchaseorderListPagewise(String pageno,String pid);
	public HERO_STK_RESPONSEINFO processreceivestock(String purchaseorderid);
	public HERO_STK_RESPONSEINFO pobarcode(String productid,String billno);
	public HERO_STK_RESPONSEINFO pocheckbarcode(String barcodevalue,String productid, String batchno);
	public HERO_STK_RESPONSEINFO getReceiveList(String purchaseid,String purchaseheaderid,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO getBillDetailsList(String purchaseid,String billno);
	public HERO_STK_RESPONSEINFO deleteBill(String purchaseid,String billno,String userid);
	public HERO_STK_RESPONSEINFO updatereceiveorderpayment(String purchaseorderid);
	public HERO_STK_RESPONSEINFO getpurchasereturndata(String purchaseorderid);
	public HERO_STK_RESPONSEINFO updatepurchasereturn(String returnstockdata);
	
	public HERO_STK_RESPONSEINFO getPurchaseRequestList(String purchaseid,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO saveaddpurchaserequest(String purchaseorderData,HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj);
	public HERO_STK_RESPONSEINFO saveadddishrequest(String purchaseorderData,HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj);
	public HERO_STK_RESPONSEINFO purchaseapprovedlist(String purchaseorderid);
	public HERO_STK_RESPONSEINFO delPurchaseOrderRequest(String purchaseorderData,
			HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj);
	public HERO_STK_RESPONSEINFO getPurchaseBillNoList(String purchaseid);
	public HERO_STK_RESPONSEINFO getPurchaseGRNList(String purchaseid);
	public HERO_STK_RESPONSEINFO getTaxlist();
}
