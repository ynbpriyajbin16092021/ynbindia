package com.hero.forms.services.admin.masters.currency;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_CURRENCYHELPER {



	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecurrency(String currencyData,HERO_ADM_SERVC_ICURRENCYDAO currencyDAOobj)
	{
		return currencyDAOobj.savecurrency(currencyData,currencyDAOobj);		
	 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrency(HERO_ADM_SERVC_ICURRENCYDAO currencyDAOobj)
	{
		return currencyDAOobj.loadcurrency();
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrencysymbolsuggestions(HERO_ADM_SERVC_ICURRENCYDAO currencyDAOobj)
	{
		return currencyDAOobj.loadcurrencysymbolsuggestions();
		 
	}
	
	 

}
