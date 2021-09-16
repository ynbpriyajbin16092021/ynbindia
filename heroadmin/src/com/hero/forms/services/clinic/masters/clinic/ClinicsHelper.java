package com.hero.forms.services.clinic.masters.clinic;


import com.hero.services.admin.response.ClinicResponseInfo;

public class ClinicsHelper {

	public ClinicResponseInfo loadclinics(IClinicsDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.loadclinics();
	}
	
	public ClinicResponseInfo saveclinic(String clinicData,IClinicsDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.saveclinic(clinicData);
	}
	
	public ClinicResponseInfo getclinicdetail(String cid,IClinicsDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.getclinicdetail(cid);
	}
	
	public ClinicResponseInfo getprescriptions(String pid,IClinicsDAO clinicsDAOOBJ)
	{
		return clinicsDAOOBJ.getprescriptions(pid);
	}
}
