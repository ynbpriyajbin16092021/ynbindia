package com.hero.stock.forms.services.tools.stockmonitor;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;



public interface HERO_STK_SERVC_ISTOCKMONITORDAO {

	public HERO_STK_RESPONSEINFO getStockMonitorList(String storeid,String productid,String categoryid,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO getStockMonitorListDetails(String storeid,String productid,String categoryid);
	
}
