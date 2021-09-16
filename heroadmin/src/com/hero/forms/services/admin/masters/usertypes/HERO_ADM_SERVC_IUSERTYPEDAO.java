package com.hero.forms.services.admin.masters.usertypes;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_IUSERTYPEDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadusertypes();
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveusertype(String usertypeData,HttpServletRequest httpRequest);
}
