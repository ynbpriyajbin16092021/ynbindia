package com.hero.forms.services.admin.tools.expirydatechecker;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_EXPIRYDATECHECKERHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getExpiryProductListDetails(HERO_ADM_SERVC_iEXPIRYDATECHECKERDAO expirydatecheckerDAOobj,String storeid,String expiredtype,String expireddays)
	{
		return expirydatecheckerDAOobj.getExpiryProductListDetails(storeid,expiredtype,expireddays);
		 
	}
}
