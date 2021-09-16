package com.hero.forms.services.admin.templates.organization;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSE;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_SERVC_IORGANISATIONDAO {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveOrgn(String orgnData);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadOrgn();
	
	
}
