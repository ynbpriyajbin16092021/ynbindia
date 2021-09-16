package com.hero.forms.services.stock.masters.supplier;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_SERVC_SUPPLIERHELPER {


	
	public HERO_STK_RESPONSEINFO saveSupplier(String supplierData,HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj)
	{
		return supplierDAOobj.savesupplier(supplierData);
		 
	}
	
	public HERO_STK_RESPONSEINFO saveSupplierContact(String supplierContactData,HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj)
	{
		return supplierDAOobj.savesuppliercontact(supplierContactData);
		 
	}
	
	public HERO_STK_RESPONSEINFO supplierlist(HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj)
	{
		return supplierDAOobj.supplierlist();
		 
	}
	
	public HERO_STK_RESPONSEINFO getsupplierinfo(HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj,String supplierid)
	{
		return supplierDAOobj.getsupplierinfo(supplierid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getsupplierviewinfo(HERO_STK_SERVC_ISUPPLIERDAO supplierDAOobj,String supplierid,String supplierstatusid)
	{
		return supplierDAOobj.getsupplierviewinfo(supplierid,supplierstatusid);
		 
	}

}
