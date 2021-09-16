package com.hero.stock.forms.transactions.stock;


import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_ISTOCKMODULEDAO {

	public HERO_STK_RESPONSEINFO updatestockprice(String stockData);
	public HERO_STK_RESPONSEINFO loadStockTxrPagewise(String pageno,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO loadStockTxrHistory(HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO loadProductSuggestion();
	public HERO_STK_RESPONSEINFO loadadjustmenthistory();
	public HERO_STK_RESPONSEINFO loadAdjProductSuggestion(String storeId);
	public HERO_STK_RESPONSEINFO loadPurchaseOrderReqSelectBox();
	public HERO_STK_RESPONSEINFO loadBatchid(String productId);
	public HERO_STK_RESPONSEINFO loadadjustmentbatches(String productId);
	public HERO_STK_RESPONSEINFO saveAdjustments(String adjustmentData);
	public HERO_STK_RESPONSEINFO getstockproductdetail(String productId,String batchid,String storeid,String type);
	public HERO_STK_RESPONSEINFO saveStocktransfer(String transferData);
	public HERO_STK_RESPONSEINFO loadtransferlist(String transferid,String storeid);
	public HERO_STK_RESPONSEINFO stockdetail(String transferid);
	public HERO_STK_RESPONSEINFO savestockstatusupdate(String statusData);
	public HERO_STK_RESPONSEINFO productbatchdetails(String productId);
	public HERO_STK_RESPONSEINFO calculatelooseqty(String uomdata);
	public HERO_STK_RESPONSEINFO getuomforpacking(String uompackingid);
	
	public HERO_STK_RESPONSEINFO loadPurchasedProductItems(String purchaseid);
	public HERO_STK_RESPONSEINFO getStockTransferProducts(String purchaseid,String batchid);
	public HERO_STK_RESPONSEINFO getdishTypeList(String dishid);
	public HERO_STK_RESPONSEINFO getIngredientsList(String dishid,String dishtypeid,int dishcount);
	public HERO_STK_RESPONSEINFO transferToKitchen(String transferData);
	public HERO_STK_RESPONSEINFO approveStoreRequest(String transferData);
	public HERO_STK_RESPONSEINFO getStockProductDetails(String addproductid);
	public HERO_STK_RESPONSEINFO saveorderrequest(String orderData);
	public HERO_STK_RESPONSEINFO savecustomermenu(String orderData);public HERO_STK_RESPONSEINFO getOrderRequestDetails(String orderId);
	public HERO_STK_RESPONSEINFO getTransferProducts(String addproductid);
	public HERO_STK_RESPONSEINFO loadstorerequest();
	HERO_STK_RESPONSEINFO getdishtypeList(String dishid, String dishtypeid);
	public HERO_STK_RESPONSEINFO saveaddtransferproduct(String transferData);
}
