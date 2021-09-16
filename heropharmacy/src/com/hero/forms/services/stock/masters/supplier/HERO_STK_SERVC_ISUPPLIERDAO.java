package com.hero.forms.services.stock.masters.supplier;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_ISUPPLIERDAO {


	public HERO_STK_RESPONSEINFO savesupplier(String supplierData);
	public HERO_STK_RESPONSEINFO savesuppliercontact(String supplierContactData);
	public HERO_STK_RESPONSEINFO supplierlist();
	public HERO_STK_RESPONSEINFO getsupplierinfo(String supplierid);
	public HERO_STK_RESPONSEINFO getsupplierviewinfo(String supplierid,String supplierstatusid);
	
}
