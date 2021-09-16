package com.hero.stock.forms.services.tools.expirydatechecker;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;



public interface HERO_STK_SERVC_iEXPIRYDATECHECKERDAO {
	public HERO_STK_RESPONSEINFO getExpiryProductListDetails(String storeid,String expiredtype,String expireddays);
}
