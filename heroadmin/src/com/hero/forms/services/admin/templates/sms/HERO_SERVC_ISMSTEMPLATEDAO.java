package com.hero.forms.services.admin.templates.sms;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_SERVC_ISMSTEMPLATEDAO {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadsmssettings();
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getSMSContent(String templateid);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveSMSContent(String smscontent);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailsettings(String emailcontent);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveEmailcontent(String emailcontent);
}
