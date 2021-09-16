package com.hero.forms.services.stock.masters.store;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_STK_SERVC_ISTOREDAO {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savestore(String storeData,HERO_STK_SERVC_ISTOREDAO storeDAOobj);

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadstore();
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getstoreinfo(String storeid);
}
