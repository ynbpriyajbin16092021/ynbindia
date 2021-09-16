package com.hero.forms.services.admin.templates.sms;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_SERVC_SMSTEMPLATEHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadSMSSettings(HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ)
	{
		return ismstemplateDAOOBJ.loadsmssettings();
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getSMSContent(HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ,String templateid)
	{
		return ismstemplateDAOOBJ.getSMSContent(templateid);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveSMSContent(HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ,String smscontent)
	{
		return ismstemplateDAOOBJ.saveSMSContent(smscontent);
	}
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailsettings(HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ,String emailcontent)
	{
		return ismstemplateDAOOBJ.saveEmailsettings(emailcontent);
	}
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailcontent(HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ,String emailcontent)
	{
		return ismstemplateDAOOBJ.saveEmailcontent(emailcontent);
	}
}
