package com.hero.clinic.forms.clinics;

import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;

public interface HERO_CLC_SERVC_ICLINICSDAO {

	public HERO_CLC_SERVC_CLINICRESPONSEINFO loadclinics();
	public HERO_CLC_SERVC_CLINICRESPONSEINFO saveclinic(String doctordata);
	public HERO_CLC_SERVC_CLINICRESPONSEINFO savelab(String doctordata);
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getclinicdetail(String cid);
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getlabdetail(String labid);
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getprescriptions(String pid);
}
