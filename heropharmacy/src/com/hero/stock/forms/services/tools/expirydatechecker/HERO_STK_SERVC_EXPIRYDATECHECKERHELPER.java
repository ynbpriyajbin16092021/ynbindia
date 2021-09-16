package com.hero.stock.forms.services.tools.expirydatechecker;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;



public class HERO_STK_SERVC_EXPIRYDATECHECKERHELPER {

	public HERO_STK_RESPONSEINFO getExpiryProductListDetails(HERO_STK_SERVC_iEXPIRYDATECHECKERDAO expirydatecheckerDAOobj,String storeid,String expiredtype,String expireddays)
	{
		return expirydatecheckerDAOobj.getExpiryProductListDetails(storeid,expiredtype,expireddays);
		 
	}
}
