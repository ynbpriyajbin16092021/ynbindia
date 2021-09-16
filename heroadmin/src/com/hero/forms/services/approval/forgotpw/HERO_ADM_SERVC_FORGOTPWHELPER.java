package com.hero.forms.services.approval.forgotpw;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_FORGOTPWHELPER {



	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO resetpassword(String forgotpwid,HERO_ADM_SERVC_IFORGOTPWDAO currencyDAOobj)
	{
		return currencyDAOobj.resetpassword(forgotpwid,currencyDAOobj);		
	 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO forgotpasswordlist(HERO_ADM_SERVC_IFORGOTPWDAO forgotpwDAOobj)
	{
		return forgotpwDAOobj.forgotpasswordlist();
		 
	}
	 

}
