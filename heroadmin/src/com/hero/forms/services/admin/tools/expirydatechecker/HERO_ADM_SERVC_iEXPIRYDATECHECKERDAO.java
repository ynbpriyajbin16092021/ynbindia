package com.hero.forms.services.admin.tools.expirydatechecker;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_iEXPIRYDATECHECKERDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getExpiryProductListDetails(String storeid,String expiredtype,String expireddays);
}
