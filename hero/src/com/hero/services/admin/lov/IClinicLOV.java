package com.hero.services.admin.lov;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface IClinicLOV {

	public List<Object> setEmptyList();
	public List<Object> getLOVList(String query);
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery);
	public List<Object> getMenuList(String menuQuery);
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest);
	public List<Object> getDoctorDetail(String menuQuery,int intdid);
	public List<Object> getDoctorDetailforAppointment(String menuQuery,int intdid);
	public List<Object> getClinicDetail(String menuQuery,int intcid);
	public List<Object> getPatientDetail(String patientQuery,int intpid);
	public List<Object> getTimeList(HashMap<String, Object> clinicMap,String bookingDate,String clinicId,String doctorid) throws ParseException;
	public List<Object> getClinicsList(String query);
	public List<Object> getPrescriptionsDetail(String prescriptionquery,int intprid);
	public List<Object> getDodontsDetail(String dodontsquery,int dodontid);
	public List<Object> getMedicineDetail(String medicinequery,int intprid);
	public List<Object> getDoctorClinicDetail(String clinicsQuery,int intcid);
	public List<Object> getMedicineHistory(String medicinequery);
	}
