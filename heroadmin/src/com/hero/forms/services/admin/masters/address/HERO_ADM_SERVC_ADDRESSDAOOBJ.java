package com.hero.forms.services.admin.masters.address;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_ADDRESSDAOOBJ {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCountry();
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStates(int countryId);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCities(int stateId);
}
