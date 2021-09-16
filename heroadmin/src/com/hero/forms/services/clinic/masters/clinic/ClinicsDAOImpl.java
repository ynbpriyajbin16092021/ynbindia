package com.hero.forms.services.clinic.masters.clinic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.services.admin.lov.IClinicLOV;
import com.hero.services.admin.response.ClinicResponseInfo;
import com.hero.services.admin.util.ClinicDAO;
import com.hero.services.admin.util.HerbzClinicUtil;

public class ClinicsDAOImpl extends ClinicDAO implements IClinicsDAO{

	private static Logger log = Logger.getLogger(ClinicsDAOImpl.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private IClinicLOV clinicLOVobj;
	
	@Override
	public ClinicResponseInfo loadclinics() {
		// TODO Auto-generated method stub
		String clinicsQuery = "SELECT `bc_id`,`bc_name`,`bc_address`,`bc_state`,`bc_city`,`bc_country`,`bc_mobileno`,`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,"
				+ "`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,`bc_break_hour_start`,`bc_break_hour_end` FROM `hero_admin_clinic` "
				+ "order by `bc_id` asc";
		@SuppressWarnings("unchecked")
		List<Object> clinicsList = jdbcTemplate.query(clinicsQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("clinicid", rs.getString("bc_id"));
				map.put("clinicname", rs.getString("bc_name"));
				map.put("address", rs.getString("bc_address"));
				map.put("state", rs.getString("bc_state"));
				map.put("city", rs.getString("bc_city"));
				
				map.put("country", rs.getString("bc_country"));
				map.put("mobileno", rs.getString("bc_mobileno"));
				map.put("starthour", rs.getString("bc_hour_start"));
				map.put("endhour", rs.getString("bc_hour_end"));
				map.put("timeperslot", rs.getString("bc_time_per_slot"));
				
				map.put("startmin", rs.getString("bc_min_start"));
				map.put("endmin", rs.getString("bc_min_end"));
				map.put("breakminstart", rs.getString("bc_break_min_start"));
				map.put("breakminend", rs.getString("bc_break_min_end"));
				map.put("breakmins", rs.getString("bc_break_mis"));
				
				map.put("breakhourstart", rs.getString("bc_break_hour_start"));
				map.put("breakhourend", rs.getString("bc_break_hour_end"));
				map.put("action", HerbzClinicUtil.DATATABLE_ACTION);
				
				String addressDisp = "",starthoursdisp="",endhoursdisp="",breakhoursdisp="",workinghoursdisp="",breakstartdisp="",breakenddisp="";
				
				if(rs.getString("bc_address") != null)
				{
					addressDisp += rs.getString("bc_address")+",";
				}
				if(rs.getString("bc_city") != null)
				{
					addressDisp += rs.getString("bc_city")+",";
				}
				if(rs.getString("bc_state") != null)
				{
					addressDisp += rs.getString("bc_state")+",";
				}
				if(rs.getString("bc_country") != null)
				{
					addressDisp += rs.getString("bc_country")+".";
				}
				
				try
				{
					
					/*log.info("Working HourStart   "+rs.getString("bc_hour_start")+"   "+rs.getString("bc_break_min_start"));
					log.info("Working Hour End   "+rs.getString("bc_hour_end")+"   "+rs.getString("bc_min_end"));
					log.info("Break Hour Start   "+rs.getString("bc_break_hour_start")+"   "+rs.getString("bc_break_min_start"));
					log.info("Break Hour End   "+rs.getString("bc_break_hour_end")+"   "+rs.getString("bc_break_min_end"));*/
					
					starthoursdisp = HerbzClinicUtil.AMPM_HourFormat(rs.getString("bc_hour_start").concat(":").concat(rs.getString("bc_break_min_start")));
					endhoursdisp = HerbzClinicUtil.AMPM_HourFormat(rs.getString("bc_hour_end").concat(":").concat(rs.getString("bc_min_end")));
					breakstartdisp = HerbzClinicUtil.AMPM_HourFormat(rs.getString("bc_break_hour_start").concat(":").concat(rs.getString("bc_break_min_start")));
					breakenddisp = HerbzClinicUtil.AMPM_HourFormat(rs.getString("bc_break_hour_end").concat(":").concat(rs.getString("bc_break_min_end")));
				}
				catch(Exception e)
				{
					//e.printStackTrace();
					error_log.info(e);
				}
				
				workinghoursdisp = starthoursdisp.concat(" - ").concat(endhoursdisp);
				breakhoursdisp = breakstartdisp.concat(" - ").concat(breakenddisp);
				
				addressDisp = addressDisp.substring(0, (addressDisp.length() - 1));
				map.put("addressdisp", addressDisp);
				map.put("workinghoursdisp", workinghoursdisp);
				map.put("breakhoursdisp", breakhoursdisp);
				
				return map;
			}
		});
		log.info(clinicsList);
		
		clinicResponseOBJ.setResponseType("S");
		clinicResponseOBJ.setResponseObj(clinicsList);
		
		clinicResponseInfoOBJ.setClinicResponse(clinicResponseOBJ);
		
		return clinicResponseInfoOBJ;
	}

	@Override
	public ClinicResponseInfo saveclinic(String clinicData) {
		// TODO Auto-generated method stub
		try
		{
			log.info("clinicData   "+clinicData);
			ClinicsRequest request = (ClinicsRequest) HerbzClinicUtil.convertJSONtooOBJECT(clinicData, "com.hero.forms.services.clinic.masters.clinic.ClinicsRequest");
		
		log.info("Values are     "+request.getAddress()+"   "+request.getBreakhourend()+"   "+request.getBreakhourstart()+"   "+request.getBreakminend()+"   "
				+request.getBreakmins()+"   "+request.getBreakminstart()+"   "+request.getCity()+"   "+request.getClinicdesc()+"   "+request.getClinicid()+"   "
				+request.getCountry()+"   "+request.getEndhour()+"   "+request.getEndmin()+"   "+request.getMobileno()+"   "+request.getStarthour()+"   "
				+request.getStartmin()+"   "+request.getState()+"   "+request.getTimeperslot()+"   "+request.getZipcode());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CLINIC_CREATION");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_CLINIC_ID", request.getClinicid());//0
		inParamMap.put("P_CLINIC_NAME", request.getClinicdesc());//1
		inParamMap.put("P_ADDRESS", request.getAddress());//2
		inParamMap.put("P_STATE", request.getState());//3
		inParamMap.put("P_CITY", request.getCity());//4
		inParamMap.put("P_COUNTRY", request.getCountry());//5
		inParamMap.put("P_MOBNO", request.getMobileno());//6
		inParamMap.put("P_WORK_HOUR_START", request.getStarthour());//7
		inParamMap.put("P_WORK_HOUR_END", request.getEndhour());//8
		inParamMap.put("P_WORK_MIN_START", request.getStartmin());//9
		inParamMap.put("P_WORK_MIN_END", request.getEndmin());//10
		inParamMap.put("P_BREAK_HOUR_START", request.getBreakhourstart());//11
		inParamMap.put("P_BREAK_HOUR_END", request.getBreakhourend());//12
		inParamMap.put("P_BREAK_MIN_START", request.getBreakminstart());//13
		inParamMap.put("P_BREAK_MIN_END", request.getBreakminend());//14
		inParamMap.put("P_BREAK_MINS", 60);//15
		inParamMap.put("P_TIME_PER_SLOT", request.getTimeperslot());//16
		inParamMap.put("P_EMAIL", request.getEmail());//16
		inParamMap.put("P_USERID", request.getUserid());//17
		inParamMap.put("P_OPRN", request.getOprn());//18
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		
		clinicResponseOBJ = HerbzClinicUtil.returnResponse(resultMap, clinicResponseOBJ);
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			clinicResponseOBJ = HerbzClinicUtil.returnExceptionResponse(clinicResponseOBJ, e);
		}
		clinicResponseInfoOBJ.setClinicResponse(clinicResponseOBJ);
		return clinicResponseInfoOBJ;
	}
	
	@Override
	public ClinicResponseInfo getclinicdetail(String cid)
	{
		String query = "SELECT `bc_id`,`bc_name`,`bc_address`,`bc_state`,`bc_city`,`bc_country`,`bc_mobileno`,`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,"
				+ "`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,`bc_break_hour_start`,`bc_break_hour_end`,`bc_email` FROM "
				+ "`hero_admin_clinic` WHERE `bc_id` = "+cid;
		
		log.info("query   "+query);
		
		int icid = 0;
		if(cid != null)
		{
			icid = Integer.parseInt(cid);
		}
		
		List<Object> clinicList = clinicLOVobj.getClinicDetail(query, icid);
		
		Map<String, Object> clinicMap = new HashMap<String, Object>();
		
		if(clinicList != null && clinicList.size() > 0)
		{
			clinicMap = (Map<String, Object>) clinicList.get(0);
						
		}
		
		log.info("clinicMap   "+clinicMap);
		
		clinicResponseOBJ.setResponseType("S");
		clinicResponseOBJ.setResponseObj(clinicList);
		
		clinicResponseInfoOBJ.setClinicResponse(clinicResponseOBJ);
		
		return clinicResponseInfoOBJ;
	}

	@Override
	public ClinicResponseInfo getprescriptions(String pid) {
		// TODO Auto-generated method stub
		String prescriptionsQuery = "SELECT DATE_FORMAT(`hppm_created_date`,'%d/%m/%Y %h:%i %p')createddate,`hppm_medicine_desc`,"
				+ "CONCAT(`bd_firstname`,`bd_lastname`)patientname FROM `hero_clinic_patient_prescriptions` A JOIN `hero_clinic_patient_prescriptions_details` B ON "
				+ "A.`hpp_id` = B.`hpp_id` LEFT JOIN `hero_admin_physician_profile` C ON C.`bd_user_id` = A.`bd_id` WHERE `hp_id` = "+pid;
		@SuppressWarnings("unchecked")
		List<Object> prescriptionsList = jdbcTemplate.query(prescriptionsQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("prescribeddate", rs.getString("createddate"));
				map.put("medicinename", rs.getString("hppm_medicine_desc"));
				map.put("patientname", rs.getString("patientname"));
						
				return map;
			}
		});
		log.info(prescriptionsList);
		
		
		String apptsQuery = "SELECT CONCAT(DATE_FORMAT(`hpp_date`,'%d/%m/%Y'),' ',DATE_FORMAT(`hpp_time`,'%h:%i %p'))apptdttime,"
				+ "CONCAT(`bd_firstname`,`bd_lastname`)physician,`hpp_payment_done`,`bd_doctor_fee` FROM `hero_clinic_patient_prescriptions` a JOIN `hero_admin_physician_profile` b ON "
				+ "a.`bd_id` = b.`bd_user_id` WHERE `hp_id` = "+pid;
		
		@SuppressWarnings("unchecked")
		List<Object> apptsList = jdbcTemplate.query(apptsQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("apptdttime", rs.getString("apptdttime"));
				map.put("physician", rs.getString("physician"));
				if(rs.getInt("hpp_payment_done") == 0)
				{
					map.put("paidstatus", "Amount in Due");	
				}
				else
				{
					map.put("paidstatus", "Amount Paid");
				}
				map.put("physicianfee", rs.getDouble("bd_doctor_fee"));
						
				return map;
			}
		});
		log.info(prescriptionsList);
		
		Map<String, Object> returnMap = new HashMap<String, Object>();
		returnMap.put("prescriptionsList", prescriptionsList);
		returnMap.put("apptsList", apptsList);
		
		clinicResponseOBJ.setResponseType("S");
		clinicResponseOBJ.setResponseObj(returnMap);
		
		clinicResponseInfoOBJ.setClinicResponse(clinicResponseOBJ);
		
		return clinicResponseInfoOBJ;
	}

}
