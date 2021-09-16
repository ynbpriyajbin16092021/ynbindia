package com.hero.forms.services.stock.tools.stockmonitor;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_STK_SERVC_ISTOCKMONITORDAO {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStockMonitorList(String storeid,String productid,String categoryid,HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStockMonitorListDetails(String storeid,String productid,String categoryid);
	
}
