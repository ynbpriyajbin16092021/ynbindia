package com.hero.reports.forms.stock.reports;

import javax.servlet.http.HttpServletRequest;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public class HERO_RTS_STK_REPORTSHELPER {

	public HERO_RTS_RESPONSEINFO salesbyproduct(String reportsData,int reportid,HttpServletRequest httpRequest,HERO_RTS_STK_IREPORTSDAO reportsDAOobj)
	{
		return reportsDAOobj.salesbyproduct(reportsData,reportid, httpRequest);
	}
	
	public HERO_RTS_RESPONSEINFO orderReports(String reportsData,int status,int customerId,HttpServletRequest httpRequest,HERO_RTS_STK_IREPORTSDAO reportsDAOobj)
	{
		return reportsDAOobj.orderReports(reportsData,status,customerId, httpRequest);
	}
	
	public HERO_RTS_RESPONSEINFO getorgndetails(String reportsData,HttpServletRequest httpRequest,HERO_RTS_STK_IREPORTSDAO reportsDAOobj)
	{
		return reportsDAOobj.getorgndetails(reportsData,httpRequest);
	}
	
	public HERO_RTS_RESPONSEINFO loadstorerequestreport(String reportsData,HttpServletRequest httpRequest,HERO_RTS_STK_IREPORTSDAO reportsDAOobj)
	{
		return reportsDAOobj.loadstorerequestreport(reportsData, httpRequest);
	}
	
	
	public HERO_RTS_RESPONSEINFO getproductsuggestions(HERO_RTS_STK_IREPORTSDAO reportsDAOobj)
	{
		return reportsDAOobj.getproductsuggestions( );
	}
	
	public HERO_RTS_RESPONSEINFO getpurchasebyproduct(HERO_RTS_STK_IREPORTSDAO reportsDAOobj,int productid)
	{
		return reportsDAOobj.getpurchasebyproduct(productid );
	}
	public HERO_RTS_RESPONSEINFO getOutputQtyReport(HERO_RTS_STK_IREPORTSDAO reportsDAOobj,int productid)
	{
		return reportsDAOobj.getOutputQtyReport(productid );
	}
	
	public HERO_RTS_RESPONSEINFO generateStockReport(HERO_RTS_STK_IREPORTSDAO reportsDAOobj,String startdate,String enddate)
	{
		return reportsDAOobj.generateStockReport(startdate,enddate);
	}
}
