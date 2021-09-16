package com.hero.stock.forms.sales.pos;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_IPOSDAO {

	public HERO_STK_RESPONSEINFO getCustomerDetails(/*String custname*/HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO getProductItems(String userid);
	public HERO_STK_RESPONSEINFO getPOSProductItems(String userid,HERO_STK_IPOSDAO iposDAOObj);
	public HERO_STK_RESPONSEINFO getuomforpacking(String uompackingid);
	public HERO_STK_RESPONSEINFO getProductUsingBarCode(String barcode);
	public HERO_STK_RESPONSEINFO getProductfromTxr(String tprid,String userid);
	public HERO_STK_RESPONSEINFO getCustomerDetail(String customerid);
	public HERO_STK_RESPONSEINFO savePOS(String posdata);
	public HERO_STK_RESPONSEINFO POSHistoryDetails(String storeid);
	public HERO_STK_RESPONSEINFO deletePOS(String posid);
	public HERO_STK_RESPONSEINFO getPOSDetails(String posid,String storeid,String type);
	public HERO_STK_RESPONSEINFO getBillDetails(String billno);
	public HERO_STK_RESPONSEINFO getCustomerOrders(String storeid);
	public HERO_STK_RESPONSEINFO saveOrderStatus(String orderdata);
	public HERO_STK_RESPONSEINFO getOrderItems(String posid);
}

