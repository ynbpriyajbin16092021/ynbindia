package com.hero.forms.services.clinic.masters.clinic;

import com.hero.services.admin.response.ClinicResponseInfo;

public interface IClinicsDAO {

	public ClinicResponseInfo loadclinics();
	public ClinicResponseInfo saveclinic(String doctordata);
	public ClinicResponseInfo getclinicdetail(String cid);
	public ClinicResponseInfo getprescriptions(String pid);
}
