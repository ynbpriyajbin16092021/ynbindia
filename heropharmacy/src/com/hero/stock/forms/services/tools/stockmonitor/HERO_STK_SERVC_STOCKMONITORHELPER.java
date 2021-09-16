package com.hero.stock.forms.services.tools.stockmonitor;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;



public class HERO_STK_SERVC_STOCKMONITORHELPER {
	
	public HERO_STK_RESPONSEINFO getStockMonitorList(HERO_STK_SERVC_ISTOCKMONITORDAO stockmonitorDAOobj,String storeid,String manufacturerid,String categoryid,HttpServletRequest httpRequest)
	{
		return stockmonitorDAOobj.getStockMonitorList(storeid,manufacturerid,categoryid,httpRequest);
		 
	}
	
	public HERO_STK_RESPONSEINFO getStockMonitorListDetails(HERO_STK_SERVC_ISTOCKMONITORDAO stockmonitorDAOobj,String storeid,String productid,String categoryid)
	{
		return stockmonitorDAOobj.getStockMonitorListDetails(storeid,productid,categoryid);
		 
	}
	
}
