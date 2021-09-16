package com.hero.stock.forms.sales.pos;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_POSHELPER {

	public HERO_STK_RESPONSEINFO getCustomerDetails(HERO_STK_IPOSDAO posDAOobj/*,String custname*/,HttpServletRequest httpRequest)
	{
		return posDAOobj.getCustomerDetails(/*custname*/httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO getProductItems(HERO_STK_IPOSDAO posDAOobj,String userid)
	{
		return posDAOobj.getProductItems(userid);
	}
	
	public HERO_STK_RESPONSEINFO getPOSProductItems(HERO_STK_IPOSDAO posDAOobj,String userid)
	{
		return posDAOobj.getPOSProductItems(userid,posDAOobj);
	}
	
	public HERO_STK_RESPONSEINFO getProductUsingBarCode(HERO_STK_IPOSDAO posDAOobj,String barcode)
	{
		return posDAOobj.getProductUsingBarCode(barcode);
	}
	
	public HERO_STK_RESPONSEINFO getProductfromTxr(HERO_STK_IPOSDAO posDAOobj,String tprid,String userid)
	{
		return posDAOobj.getProductfromTxr(tprid,userid);
	}
	
	public HERO_STK_RESPONSEINFO getCustomerDetail(HERO_STK_IPOSDAO posDAOobj,String customerid)
	{
		return posDAOobj.getCustomerDetail(customerid);
	}
	
	public HERO_STK_RESPONSEINFO savePOS(HERO_STK_IPOSDAO posDAOobj,String posdata)
	{
		return posDAOobj.savePOS(posdata);
	}
	
	public HERO_STK_RESPONSEINFO POSHistoryDetails(HERO_STK_IPOSDAO posDAOobj,String storeid)
	{
		return posDAOobj.POSHistoryDetails(storeid);
	}
	
	public HERO_STK_RESPONSEINFO deletePOS(HERO_STK_IPOSDAO posDAOobj,String posid)
	{
		return posDAOobj.deletePOS(posid);
	}
	
	public HERO_STK_RESPONSEINFO getPOSDetails(HERO_STK_IPOSDAO posDAOobj,String posid,String storeid,String type)
	{
		return posDAOobj.getPOSDetails(posid,storeid,type);
	}
	
	public HERO_STK_RESPONSEINFO getBillDetails(HERO_STK_IPOSDAO posDAOobj,String billno)
	{
		return posDAOobj.getBillDetails(billno);
	}
	
	public HERO_STK_RESPONSEINFO getCustomerOrders(HERO_STK_IPOSDAO posDAOobj,String storeid)
	{
		return posDAOobj.getCustomerOrders(storeid);
	}
	
	public HERO_STK_RESPONSEINFO saveOrderStatus(HERO_STK_IPOSDAO posDAOobj,String orderdata)
	{
		return posDAOobj.saveOrderStatus(orderdata);
	}
	
	public HERO_STK_RESPONSEINFO getOrderItems(HERO_STK_IPOSDAO posDAOobj,String posid)
	{
		return posDAOobj.getOrderItems(posid);
	}
}
