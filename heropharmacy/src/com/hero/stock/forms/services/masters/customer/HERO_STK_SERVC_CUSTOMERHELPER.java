package com.hero.stock.forms.services.masters.customer;



import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_SERVC_CUSTOMERHELPER {

	public HERO_STK_RESPONSEINFO loadcustomergroup(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.loadcustomergroup();
		
	}
	
	public HERO_STK_RESPONSEINFO savecustomergroup(String customergroupData,HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.savecustomergroup(customergroupData);
		
	}
	
	public HERO_STK_RESPONSEINFO savecustomer(String customerData,HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj)
	{
		return customerDAOobj.savecustomer(customerData);
		
	}
	
	public HERO_STK_RESPONSEINFO loadcustomers(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomers(httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO loadaddcustomer(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String customerid)
	{
		return customerDAOobj.loadaddcustomer(customerid);
	}
	
	public HERO_STK_RESPONSEINFO loadcustomerviewpagewise(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String pageno,String status,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomerviewpagewise(pageno,status,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO loadcustomerviewstatuswise(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String status,HttpServletRequest httpRequest)
	{
		return customerDAOobj.loadcustomerviewstatuswise(status,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO customeroverviewDetails(HERO_STK_SERVC_ICUSTOMERDAO customerDAOobj,String customerid)
	{
		return customerDAOobj.customeroverviewDetails(customerid);
	}
}
