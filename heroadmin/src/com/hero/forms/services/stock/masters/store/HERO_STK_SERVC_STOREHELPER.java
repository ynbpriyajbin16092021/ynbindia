package com.hero.forms.services.stock.masters.store;



import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_STK_SERVC_STOREHELPER {

	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savestore(String storeData,HERO_STK_SERVC_ISTOREDAO storeDAOobj)
	{
		return storeDAOobj.savestore(storeData,storeDAOobj);		
	 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadstore(HERO_STK_SERVC_ISTOREDAO storeDAOobj)
	{
		return storeDAOobj.loadstore();
		 
	}
	 
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getstoreinfo(HERO_STK_SERVC_ISTOREDAO storeDAOobj,String storeid)
	{
		return storeDAOobj.getstoreinfo(storeid);
		 
	}

}
