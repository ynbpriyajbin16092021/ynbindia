package com.hero.forms.services.admin.masters.company;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_ADM_SERVC_ICOMPANYDAO {
	public HERO_STK_RESPONSEINFO savecompany(String companyData,HERO_ADM_SERVC_ICOMPANYDAO comapnyDAOobj);
	public HERO_STK_RESPONSEINFO getCompanyList(HERO_ADM_SERVC_ICOMPANYDAO companyDAOobj);
}
