package com.hero.forms.services.admin.masters.tax;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_TAXHELPER {




	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savetax(String taxData,HERO_ADM_SERVC_ITAXDAO taxDAOobj)
	{
		return taxDAOobj.savetax(taxData,taxDAOobj);		
	 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadtax(HERO_ADM_SERVC_ITAXDAO taxDAOobj)
	{
		return taxDAOobj.loadtax();
		 
	}
	 


}
