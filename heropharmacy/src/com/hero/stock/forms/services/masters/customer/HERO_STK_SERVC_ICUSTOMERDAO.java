package com.hero.stock.forms.services.masters.customer;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_ICUSTOMERDAO {
	
	public HERO_STK_RESPONSEINFO loadcustomergroup();
	public HERO_STK_RESPONSEINFO savecustomer(String customerData);
	public HERO_STK_RESPONSEINFO loadcustomers(HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO loadaddcustomer(String customerid);
	public HERO_STK_RESPONSEINFO loadcustomerviewpagewise(String pageno,String status,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO loadcustomerviewstatuswise(String status,HttpServletRequest httpRequest);
	public HERO_STK_RESPONSEINFO customeroverviewDetails(String customerid);
	public HERO_STK_RESPONSEINFO savecustomergroup(String customerData);
}
