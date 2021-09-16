package com.hero.forms.services.stock.tools.stockmonitor;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_STK_SERVC_STOCKMONITORHELPER {
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStockMonitorList(HERO_STK_SERVC_ISTOCKMONITORDAO stockmonitorDAOobj,String storeid,String manufacturerid,String categoryid,HttpServletRequest httpRequest)
	{
		return stockmonitorDAOobj.getStockMonitorList(storeid,manufacturerid,categoryid,httpRequest);
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStockMonitorListDetails(HERO_STK_SERVC_ISTOCKMONITORDAO stockmonitorDAOobj,String storeid,String productid,String categoryid)
	{
		return stockmonitorDAOobj.getStockMonitorListDetails(storeid,productid,categoryid);
		 
	}
	
}
