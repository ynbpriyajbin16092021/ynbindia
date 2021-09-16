package com.hero.stock.forms.transactions.stock;


import javax.servlet.http.HttpServletRequest;

import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_IADDPURCHASEORDERDAO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_STOCKMODULEHELPER {

	public HERO_STK_RESPONSEINFO updatestockprice(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String stockData)
	{
		return stockDAOobj.updatestockprice(stockData);
		 
	}
	
	public HERO_STK_RESPONSEINFO getuomforpacking(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String uompackingid)
	{
		return stockDAOobj.getuomforpacking(uompackingid);
		 
	}
	
	public HERO_STK_RESPONSEINFO calculatelooseqty(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String uomdata)
	{
		return stockDAOobj.calculatelooseqty(uomdata);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadStockTxrPagewise(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String pageno,HttpServletRequest httpRequest)
	{
		return stockDAOobj.loadStockTxrPagewise(pageno,httpRequest);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadStockTxrHistory(HERO_STK_ISTOCKMODULEDAO stockDAOobj,HttpServletRequest httpRequest)
	{
		return stockDAOobj.loadStockTxrHistory(httpRequest);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadProductSuggestion(HERO_STK_ISTOCKMODULEDAO stockDAOobj)
	{
		return stockDAOobj.loadProductSuggestion();
		 
	}
	
	public HERO_STK_RESPONSEINFO loadadjustmenthistory(HERO_STK_ISTOCKMODULEDAO stockDAOobj)
	{
		return stockDAOobj.loadadjustmenthistory();
		 
	}
	
	public HERO_STK_RESPONSEINFO loadadjproductsuggestion(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String storeid)
	{
		return stockDAOobj.loadAdjProductSuggestion(storeid);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadPurchaseOrderReqSelectBox(HERO_STK_ISTOCKMODULEDAO stockDAOobj)
	{
		return stockDAOobj.loadPurchaseOrderReqSelectBox();
		 
	}
	
	public HERO_STK_RESPONSEINFO loadBatchidSuggestion(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String productid)
	{
		return stockDAOobj.loadBatchid(productid);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadadjustmentbatches(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String productid)
	{
		return stockDAOobj.loadadjustmentbatches(productid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getstockproductdetail(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String productid,String batchid,String storeid,String type)
	{
		return stockDAOobj.getstockproductdetail(productid,batchid,storeid,type);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadtransferlist(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferid,String storeid)
	{
		return stockDAOobj.loadtransferlist(transferid,storeid);
		 
	}
	
	public HERO_STK_RESPONSEINFO stockdetail(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferid)
	{
		return stockDAOobj.stockdetail(transferid);
		 
	}
	
	public HERO_STK_RESPONSEINFO saveStocktransfer(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferData)
	{
		return stockDAOobj.saveStocktransfer(transferData);
		 
	}
	
	public HERO_STK_RESPONSEINFO savestockstatusupdate(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String statusData)
	{
		return stockDAOobj.savestockstatusupdate(statusData);
		 
	}
	
	public HERO_STK_RESPONSEINFO productbatchdetails(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String productid)
	{
		return stockDAOobj.productbatchdetails(productid);
		 
	}
	
	public HERO_STK_RESPONSEINFO saveAdjustments(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String adjustmentData)
	{
		return stockDAOobj.saveAdjustments(adjustmentData);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO loadPurchasedProductItems(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String purchaseid)
	{
		return stockDAOobj.loadPurchasedProductItems(purchaseid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getStockTransferProducts(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String purchaseid,String batchid)
	{
		return stockDAOobj.getStockTransferProducts(purchaseid,batchid);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO getdishTypeList(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String dishid)
	{
		return stockDAOobj.getdishTypeList(dishid);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO getIngredientsList(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String dishid,String dishtypeid,int dishcount)
	{
		return stockDAOobj.getIngredientsList(dishid,dishtypeid,dishcount);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO transferToKitchen(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferData)
	{
		return stockDAOobj.transferToKitchen(transferData);
		 
	}
	
	public HERO_STK_RESPONSEINFO approveStoreRequest(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferData)
	{
		return stockDAOobj.approveStoreRequest(transferData);
		 
	}
	
	public HERO_STK_RESPONSEINFO getStockProductDetails(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String addproductid)
	{
		return stockDAOobj.getStockProductDetails(addproductid);
		 
	}

	public HERO_STK_RESPONSEINFO saveorderrequest(
			HERO_STK_ISTOCKMODULEDAO stockDAOobj, String orderData) {
		return stockDAOobj.saveorderrequest(orderData);
	}

	public HERO_STK_RESPONSEINFO savecustomermenu(
			HERO_STK_ISTOCKMODULEDAO stockDAOobj, String orderData) {
		return stockDAOobj.savecustomermenu(orderData);
	}
	
	public HERO_STK_RESPONSEINFO getOrderRequestDetails(
			HERO_STK_ISTOCKMODULEDAO stockDAOobj, String orderId) {
		return stockDAOobj.getOrderRequestDetails(orderId);
	}
	
	public HERO_STK_RESPONSEINFO getTransferProducts(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String addproductid)
	{
		return stockDAOobj.getTransferProducts(addproductid);
		 
	}
	
	public HERO_STK_RESPONSEINFO loadstorerequest(HERO_STK_ISTOCKMODULEDAO stockDAOobj)
	{
		return stockDAOobj.loadstorerequest();
		 
	}
	
	public HERO_STK_RESPONSEINFO saveaddtransferproduct(HERO_STK_ISTOCKMODULEDAO stockDAOobj,String transferData)
	{
		return stockDAOobj.saveaddtransferproduct(transferData);
		 
	}
}
