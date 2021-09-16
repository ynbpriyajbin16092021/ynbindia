package com.hero.forms.services.stock.masters.customer;



import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_STK_SERVC_CUSTOMERHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomergroup(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.loadcustomergroup();
		
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomergroup(String customergroupData,HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.savecustomergroup(customergroupData);
		
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomer(String customerData,HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.savecustomer(customerData);
		
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomers(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomers(httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadaddcustomer(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String customerid)
	{
		return customerDAOobj.loadaddcustomer(customerid);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewpagewise(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String pageno,String status,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomerviewpagewise(pageno,status,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewstatuswise(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String status,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomerviewstatuswise(status,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO customeroverviewDetails(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String customerid)
	{
		return customerDAOobj.customeroverviewDetails(customerid);
	}
}
