package com.hero.forms.services.admin.masters.usertypes;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_USERTYPEHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadusertypes(HERO_ADM_SERVC_IUSERTYPEDAO usertypeDAOobj)
	{
		return usertypeDAOobj.loadusertypes();
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveusertype(HERO_ADM_SERVC_IUSERTYPEDAO usertypeDAOobj,String usertypeData,HttpServletRequest httpRequest)
	{
		return usertypeDAOobj.saveusertype(usertypeData,httpRequest);
		 
	}
}
