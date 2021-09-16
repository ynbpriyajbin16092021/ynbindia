package com.hero.reports.forms.stock.reports;

import javax.servlet.http.HttpServletRequest;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public interface HERO_RTS_STK_IREPORTSDAO {
	
     public HERO_RTS_RESPONSEINFO salesbyproduct(String reportsData,int reportid,HttpServletRequest httpRequest);
     public HERO_RTS_RESPONSEINFO orderReports(String reportsData,int status,int customerId,HttpServletRequest httpRequest);
     public HERO_RTS_RESPONSEINFO getorgndetails(String reportsData,HttpServletRequest httpRequest);
     public HERO_RTS_RESPONSEINFO loadstorerequestreport(String reportsData,HttpServletRequest httpRequest);
     public HERO_RTS_RESPONSEINFO getproductsuggestions();
     public HERO_RTS_RESPONSEINFO getpurchasebyproduct(int productid);
     public HERO_RTS_RESPONSEINFO getOutputQtyReport(int productid);
     public HERO_RTS_RESPONSEINFO generateStockReport(String startdate,String enddate);
}
