package com.hero.clinic.forms.clinics;


import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;

public class HERO_CLC_SERVC_CLINICHELPER {

	public HERO_CLC_SERVC_CLINICRESPONSEINFO loadclinics(HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.loadclinics();
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO saveclinic(String clinicData,HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.saveclinic(clinicData);
	}
	

	public HERO_CLC_SERVC_CLINICRESPONSEINFO savelab(String clinicData,HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.savelab(clinicData);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getclinicdetail(String cid,HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.getclinicdetail(cid);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getlabdetail(String labid,HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.getlabdetail(labid);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO getprescriptions(String pid,HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.getprescriptions(pid);
	}
}
