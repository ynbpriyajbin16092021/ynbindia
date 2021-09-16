package com.hero.forms.services.admin.masters.address;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_ADDRESS_HELPER {
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCountry(HERO_ADM_SERVC_ADDRESSDAOOBJ addressDAOObj){
		return addressDAOObj.getCountry();
	}
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStates(HERO_ADM_SERVC_ADDRESSDAOOBJ addressDAOObj, int countryId){
		return addressDAOObj.getStates(countryId);
	}
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCities(HERO_ADM_SERVC_ADDRESSDAOOBJ addressDAOObj, int stateId){
		return addressDAOObj.getCities(stateId);
	}
}
