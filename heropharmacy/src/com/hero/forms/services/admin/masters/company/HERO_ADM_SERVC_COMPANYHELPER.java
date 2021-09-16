package com.hero.forms.services.admin.masters.company;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_ADM_SERVC_COMPANYHELPER {

	public HERO_STK_RESPONSEINFO savecompany(String companyData,HERO_ADM_SERVC_ICOMPANYDAO companyDAOobj)
	{
		return companyDAOobj.savecompany(companyData,companyDAOobj);
	
	}
	
	public HERO_STK_RESPONSEINFO getCompanyList(HERO_ADM_SERVC_ICOMPANYDAO companyDAOobj)
	{
		return companyDAOobj.getCompanyList(companyDAOobj);
		
	}
}
