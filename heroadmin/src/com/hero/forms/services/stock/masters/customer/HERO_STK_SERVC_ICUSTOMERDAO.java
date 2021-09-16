package com.hero.forms.services.stock.masters.customer;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_STK_SERVC_ICUSTOMERDAO {
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomergroup();
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomer(String customerData);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomers(HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadaddcustomer(String customerid);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewpagewise(String pageno,String status,HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewstatuswise(String status,HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO customeroverviewDetails(String customerid);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomergroup(String customerData);
}
