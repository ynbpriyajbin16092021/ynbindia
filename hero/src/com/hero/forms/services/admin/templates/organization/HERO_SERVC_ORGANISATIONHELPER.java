package com.hero.forms.services.admin.templates.organization;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_SERVC_ORGANISATIONHELPER {

	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveOrgn(HERO_SERVC_IORGANISATIONDAO iorgnDAOOBJ,String orgnData)
	{
		return iorgnDAOOBJ.saveOrgn(orgnData);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadOrgn(HERO_SERVC_IORGANISATIONDAO iorgnDAOOBJ)
	{
		return iorgnDAOOBJ.loadOrgn();
	}
}
