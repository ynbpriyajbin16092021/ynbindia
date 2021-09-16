package com.hero.stock.forms.transactions.purchaseorder;




import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_ADDPURCHASEORDERHELPER {
	

	public HERO_STK_RESPONSEINFO getuomforpacking(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String uompackingid)
	{
		return purchaseorderDAOobj.getuomforpacking(uompackingid);
		 
	}
	
	public HERO_STK_RESPONSEINFO calculatelooseqty(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String uomdata)
	{
		return purchaseorderDAOobj.calculatelooseqty(uomdata);
		 
	}
	
	public HERO_STK_RESPONSEINFO calculateordlooseqty(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String uomdata)
	{
		return purchaseorderDAOobj.calculateordlooseqty(uomdata);
		 
	}
	
public HERO_STK_RESPONSEINFO savepurchaseorder(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderData)
	{
		
		return purchaseorderDAOobj.savepurchaseorder(purchaseorderData,purchaseorderDAOobj);
	
	}
	
	
public HERO_STK_RESPONSEINFO approveitemrequest(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderData)
{
	
	return purchaseorderDAOobj.approveitemrequest(purchaseorderData,purchaseorderDAOobj);

}

  public HERO_STK_RESPONSEINFO saveaddpurchaserequest(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderData)
	{
	
		return purchaseorderDAOobj.saveaddpurchaserequest(purchaseorderData,purchaseorderDAOobj);
	
	}
  public HERO_STK_RESPONSEINFO saveadddishrequest(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderData)
 	{
 	
 		return purchaseorderDAOobj.saveadddishrequest(purchaseorderData,purchaseorderDAOobj);
 	
 	}
  public HERO_STK_RESPONSEINFO delPurchaseOrderRequest(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderData)
	{
	
		return purchaseorderDAOobj.delPurchaseOrderRequest(purchaseorderData,purchaseorderDAOobj);
	
	}
  
  public HERO_STK_RESPONSEINFO deletePurchaseorder(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid, String remarksData)
	{
		return purchaseorderDAOobj.deletePurchaseorder(purchaseorderid,remarksData);
	
	}
	
	public HERO_STK_RESPONSEINFO purchaseorderlist(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.purchaseorderlist(purchaseorderid);
		 
	}
	
	public HERO_STK_RESPONSEINFO purchaserequestlist(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.purchaserequestlist(purchaseorderid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getPurchaseDishList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.getPurchaseDishList(purchaseorderid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getDishtypeList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String dishid,String hdrid)
	{
		return purchaseorderDAOobj.getDishtypeList(dishid,hdrid);
		 
	}
	public HERO_STK_RESPONSEINFO getcustomerDishtypeList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String dishid,String customerid)
	{
		return purchaseorderDAOobj.getcustomerDishtypeList(dishid,customerid);
		 
	}
	public HERO_STK_RESPONSEINFO getcustomermenuById(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String customerid)
	{
		return purchaseorderDAOobj.getcustomermenuById(customerid);
		 
	}
	public HERO_STK_RESPONSEINFO getCustomerList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String orderId)
	{
		return purchaseorderDAOobj.getCustomerList(orderId);
		 
	}
	
	public HERO_STK_RESPONSEINFO getPendingtyransferItemList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String transferId)
	{
		return purchaseorderDAOobj.getPendingtyransferItemList(transferId);
		 
	}
	
	public HERO_STK_RESPONSEINFO getDishList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String customerId,String orderId)
	{
		return purchaseorderDAOobj.getDishList(customerId,orderId);
		 
	}
	
	public HERO_STK_RESPONSEINFO getcusdishTypeList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String customerId,String orderId,String dishId)
	{
		return purchaseorderDAOobj.getcusdishTypeList(customerId,orderId,dishId);
		 
	}
	
	public HERO_STK_RESPONSEINFO getDishProductDetails(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String dishid)
	{
		return purchaseorderDAOobj.getDishProductDetails(dishid);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO getPurchaseRequestDishList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.getPurchaseRequestDishList(purchaseorderid);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO getPurchaseorderListPagewise(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String pageno,String pid)
	{
		return purchaseorderDAOobj.getPurchaseorderListPagewise(pageno,pid);
		 
	}
	
	public HERO_STK_RESPONSEINFO processreceivestock(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String receiveStockData)
	{
		return purchaseorderDAOobj.processreceivestock(receiveStockData);
		 
	}
	
	public HERO_STK_RESPONSEINFO pobarcode(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String productid,String billno)
	{
		return purchaseorderDAOobj.pobarcode(productid,billno);
		 
	}
	
	public HERO_STK_RESPONSEINFO pocheckbarcode(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String barcode, String productid, String batchno)
	{
		return purchaseorderDAOobj.pocheckbarcode(barcode,productid,batchno);
		 
	}
	
	public HERO_STK_RESPONSEINFO getReceiveList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid,String purchaseheaderid, HttpServletRequest httpRequest)
	{
		return purchaseorderDAOobj.getReceiveList(purchaseid,purchaseheaderid,httpRequest);
		 
	}
	
	public HERO_STK_RESPONSEINFO getPurchaseRequestList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid,HttpServletRequest httpRequest)
	{
		return purchaseorderDAOobj.getPurchaseRequestList(purchaseid,httpRequest);
		 
	}
	
	public HERO_STK_RESPONSEINFO getBillDetailsList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid,String billno)
	{
		return purchaseorderDAOobj.getBillDetailsList(purchaseid,billno);
		 
	}
	
	public HERO_STK_RESPONSEINFO deleteBill(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid,String billno,String userid)
	{
		return purchaseorderDAOobj.deleteBill(purchaseid,billno,userid);
		 
	}
	
	public HERO_STK_RESPONSEINFO updatereceiveorderpayment(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String paymentData)
	{
		return purchaseorderDAOobj.updatereceiveorderpayment(paymentData);
		 
	}
	
	public HERO_STK_RESPONSEINFO getpurchasereturndata(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.getpurchasereturndata(purchaseorderid);
		 
	}
	
	public HERO_STK_RESPONSEINFO updatepurchasereturn(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String returnstockdata)
	{
		return purchaseorderDAOobj.updatepurchasereturn(returnstockdata);
		 
	}
	
	public HERO_STK_RESPONSEINFO purchaseapprovedlist(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseorderid)
	{
		return purchaseorderDAOobj.purchaseapprovedlist(purchaseorderid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getPurchaseBillNoList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid)
	{
		return purchaseorderDAOobj.getPurchaseBillNoList(purchaseid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getPurchaseGRNList(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj,String purchaseid)
	{
		return purchaseorderDAOobj.getPurchaseGRNList(purchaseid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getTaxlist(HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj )
	{
		return purchaseorderDAOobj.getTaxlist();
		 
	}

	
}
