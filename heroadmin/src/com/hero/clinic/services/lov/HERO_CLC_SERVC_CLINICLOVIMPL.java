package com.hero.clinic.services.lov;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import com.hero.clinic.services.util.HERO_CLC_SERVC_CLINICDAO;
import com.hero.clinic.services.util.HERO_CLC_SERVC_CLINICLOV;
import com.hero.clinic.services.util.HERO_CLC_SERVC_HERBZCLINICUTIL;

public class HERO_CLC_SERVC_CLINICLOVIMPL extends HERO_CLC_SERVC_CLINICDAO implements HERO_CLC_SERVC_ICLINICLOV {

	private static Logger log = Logger.getLogger(HERO_CLC_SERVC_CLINICLOVIMPL.class);
	@Override
	public List<Object> getLOVList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HERO_CLC_SERVC_CLINICLOV lov = new HERO_CLC_SERVC_CLINICLOV();
				  lov.setLabel(rs.getString(2));
				  lov.setValue(rs.getString(1));
				  lov.setIndex(index);
				  
				  index++;
				  
				  return lov;
			}
		});
		if(LOVList != null && LOVList.size() == 0)
		{
			LOVList = setEmptyList();
		}
		return LOVList;
	}


	@Override
	public List<Object> setEmptyList() {
		// TODO Auto-generated method stub
		List<Object> LOVList = new ArrayList<Object>();
		HERO_CLC_SERVC_CLINICLOV lov = new HERO_CLC_SERVC_CLINICLOV();
		lov.setLabel("--Select--");
		lov.setValue("0");
		LOVList.add(lov);
		return LOVList;
	}

	@Override
	public List<Object> executeQuery(String query) {
		// TODO Auto-generated method stub
		List<Object> result = jdbcTemplate.queryForList(query);
		return result;
	}
	@Override
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(userMenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #f4f4f4;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				
				index++;
				return map;
			}
		});
		log.info("First usermenuList       "+usermenuList);
		
		if(usermenuList != null && usermenuList.size() == 0)
		{
			@SuppressWarnings("unchecked")
			List<Object> usermenuList1 = jdbcTemplate.query(menuQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("moduleid",rs.getString("moduleid"));
					map.put("modulename", rs.getString("modulename"));
					map.put("issubmodule",rs.getString("issubmodule"));
					map.put("parentid",rs.getString("parentid"));
					map.put("index", index);
					
					if(rs.getInt("issubmodule") == 0)
					{
						map.put("mainmenudisp", "mainmenudisp");
						map.put("submenudisp", "mainmenuhidedisp");
						map.put("space", "");
						map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
					}
					else
					{
						map.put("mainmenudisp", "mainmenuhidedisp");
						map.put("submenudisp", "mainmenudisp");
						map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						map.put("style", "");
					}
					
					map.put("menudetails","1$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid"));
					
					index++;
					return map;
				}
			});
			
			usermenuList = usermenuList1;
		}
		
		log.info("Second usermenuList       "+usermenuList);
		
		return usermenuList;
	}


	@Override
	public List<Object> getMenuList(String menuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(menuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				/*log.info("rs.getString(fafafont)        "+rs.getString("fafafont"));*/
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("fafafont",rs.getString("fafafont"));
				map.put("modulepath",rs.getString("modulepath"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				
				String submenuQuery = "SELECT A.`moduleid`,`modulename`,`issubmodule`,`parentid`,COALESCE(`user_menu_sno`,0)user_menu_sno,COALESCE(`userid`,0)userid,"
				  		+ "COALESCE(`user_type_id`,0)usertype,`menu_access`,`modulepath`"
							+ " FROM `hero_module` A LEFT OUTER JOIN `hero_user_menus` B ON A.`moduleid` = B.`moduleid` "
							+ "WHERE `user_type_id` = "+rs.getString("user_type_id")+" AND parentid = "+rs.getString("moduleid")
							+" AND menu_access = 1 ORDER BY `userid`,`moduleid` ASC";
					
				  
				  
				  List<Object> submenuList = new ArrayList<Object>();
				  submenuList = getSubMenuList(submenuQuery);
				  map.put("submenuList", submenuList);
				
				index++;
				return map;
			}
		});
		/*log.info("First usermenuList       "+usermenuList);*/
		
		return usermenuList;
	}

	
	public List<Object> getSubMenuList(String submenuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> submenuList = jdbcTemplate.query(submenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("moduleid"));
				map.put("modulename", rs.getString("modulename"));
				map.put("issubmodule",rs.getString("issubmodule"));
				map.put("parentid",rs.getString("parentid"));
				map.put("index", index);
				
				if(rs.getInt("issubmodule") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
				}
				
				map.put("menudetails",rs.getInt("menu_access")+"$$$"+rs.getInt("issubmodule")+"$$$"+rs.getInt("parentid")+"$$$"+rs.getInt("moduleid")+"$$$"
						+rs.getString("user_menu_sno"));
				map.put("menusno",rs.getString("user_menu_sno"));
				map.put("usertype",rs.getString("usertype"));
				map.put("modulepath", rs.getString("modulepath"));
				
				index++;
				return map;
			}
		});
		/*log.info("First submenuList       "+submenuList);*/
		
		return submenuList;
	}
 
	@Override
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		HttpSession session = httpRequest.getSession();
		int usertype = (int)session.getAttribute("usertype");
		
		String clinicCountQuery = "SELECT COUNT(*) FROM `hero_admin_clinic`";
		String walkinCountQuery = "SELECT COUNT(*) FROM `hero_clinic_patient_booking`";
		String patientCountQuery="SELECT COUNT(*) FROM `hero_admin_patient`";
		String doctorCountQuery="SELECT COUNT(*) FROM `hero_admin_physician_profile`";
		String receptionistCountQuery="SELECT COUNT(*) FROM `hero_user` WHERE `role`=5";
		String appointmentCountQuery="SELECT COUNT(*) FROM `hero_clinic_patient_booking`";
		String doctorrecentQuery="SELECT CONCAT(`bd_prefix`,`bd_firstname`,`bd_lastname`)doctorname,`hps_desc` "
				+ "FROM `hero_admin_physician_profile` a JOIN `hero_physician_specialist` b ON a.`bd_speciality`=b.`hps_id`"
				+ " ORDER BY `bdc_id` DESC LIMIT 5";
		
		
		
			String appointmentrecentQuery="SELECT CONCAT(`bd_prefix`,`bd_firstname`,`bd_lastname`)doctorname, `hpb_date`,`hpb_time` FROM `hero_clinic_patient_booking` a " 
					 + "JOIN `hero_admin_physician_profile` b  ON a.`bd_id` = b.`bd_id`" 
					 + " ORDER BY `hpb_id` DESC LIMIT 5";
			
			String consultappointmentQuery="SELECT `hp_name` ,`hpb_date` FROM `hero_admin_patient` a JOIN `hero_clinic_patient_booking` b ON a.`hp_id`=b.`hp_id`"
                                  +"WHERE b.`hpb_consult_done`=1 ORDER BY `hpb_id` DESC LIMIT 5";	
	
	
		String appointmentfailurerecentQuery="SELECT CONCAT(`bd_prefix`,`bd_firstname`,`bd_lastname`)doctorname, `hpb_date`,`hpb_time` FROM `hero_clinic_patient_booking` a "
				+"JOIN `hero_admin_physician_profile` b ON a.`bd_id` = b.`bd_id`  AND `hpb_booking_status` = 'c'"
				+ " ORDER BY `hpb_id` DESC LIMIT 5";
		String patientrecentQuery="SELECT `hp_name`,`hp_mobno` FROM `hero_admin_patient` ORDER BY `hp_id` DESC LIMIT 5";
		String shedulerecentQuery="SELECT CONCAT(`bd_prefix`,`bd_firstname`,`bd_lastname`)doctorname,`hcs_from_date`,`hcs_to_date` FROM `hero_admin_physician_profile` "
                                  + " a JOIN `hero_clinic_schedule` b ON a.`bd_id`=b.`bd_id` ORDER BY `hcs_id` DESC LIMIT 5";
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> clinicCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(clinicCountQuery, jdbcTemplate);
		List<Object> patientCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(patientCountQuery, jdbcTemplate);
		List<Object> doctorCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(doctorCountQuery, jdbcTemplate);
		List<Object> receptionistCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(receptionistCountQuery, jdbcTemplate);
		List<Object> appointmentCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(appointmentCountQuery, jdbcTemplate);
		List<Object> walkinCountList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQuery(walkinCountQuery, jdbcTemplate);
		//List<Object> userRecentList = (List<Object>) HERO_ADM_SERVC_HOSURINVENTORYUTIL.executeQueryWithList(userRecentQuery, jdbcTemplate);
		List<Object> doctorrecentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(doctorrecentQuery, jdbcTemplate);
		List<Object> appointmentrecentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(appointmentrecentQuery, jdbcTemplate);
		List<Object> appointmentfailurerecentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(appointmentfailurerecentQuery, jdbcTemplate);
		List<Object> patientrecentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(patientrecentQuery, jdbcTemplate);
		List<Object> shedulerecentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(shedulerecentQuery, jdbcTemplate);
		List<Object> consultappointmentList = (List<Object>) HERO_CLC_SERVC_HERBZCLINICUTIL.executeQueryWithList(consultappointmentQuery, jdbcTemplate);
		
		
		map.put("clinicCount", clinicCountList);
		map.put("patientCount", patientCountList);
		map.put("doctorCount", doctorCountList);
		map.put("receptionistCount", receptionistCountList);
		map.put("appointmentCount", appointmentCountList);
		map.put("walkinCount", walkinCountList);
		map.put("doctorrecent", doctorrecentList);
		map.put("appointmentrecent", appointmentrecentList);
		map.put("appointmentfailurerecent", appointmentfailurerecentList);
		map.put("patientrecent", patientrecentList);
		map.put("shedulerecent", shedulerecentList);
		map.put("consultappointment", consultappointmentList);
		
		return map;
	}


	@Override
	public List<Object> getDoctorDetail(String query,final int intdid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {

				Map<String, Object> map = new HashMap<String, Object>();
				
				
				map.put("fname", rs.getString(1));
				map.put("lname", rs.getString(2));
				map.put("email", rs.getString(3));
				map.put("phone", rs.getString(4));
				
				if(intdid > 0)
				{
					map.put("speciality", rs.getString(5));
					map.put("dob", rs.getString(6));
					map.put("address", rs.getString(7));
					map.put("city", rs.getString(8));
					map.put("state", rs.getString(9));

					map.put("zipcode", rs.getString(10));
					map.put("country", rs.getString(11));
					map.put("imgpath", rs.getString(13));
					map.put("clinicid", rs.getString(15));
					map.put("id", rs.getString("bd_id"));
					map.put("doctorname", rs.getString("doctorname"));
					map.put("doctorfee", rs.getString("bd_doctor_fee"));
					
					map.put("userid", rs.getString(18));
					map.put("ugqualification", rs.getString(19));
					map.put("dispname", rs.getString(20));
					map.put("knownlangs", rs.getString(21));
					map.put("intercomno", rs.getString(22));
					map.put("uginstitution", rs.getString(23));
					map.put("clinicianno", rs.getString(24));
					map.put("yearsofexp", rs.getString(25));
					map.put("workinghoursfrom", rs.getString(26));
					map.put("workinghoursto", rs.getString(27));
					map.put("doj", rs.getString(28));
					map.put("consultingtime", rs.getString(29));
					map.put("prefix", rs.getString(30));
					map.put("visitingdays", rs.getString(31));
				}
				
				return map;
			}
		});
		
		return LOVList;
	}

	@Override
	public List<Object> getDoctorDetailforAppointment(String query,final int intdid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {

				Map<String, Object> map = new HashMap<String, Object>();

				map.put("fname", rs.getString(1));
				map.put("lname", rs.getString(2));
				map.put("email", rs.getString(3));
				map.put("phone", rs.getString(4));
				
				if(intdid > 0)
				{
					map.put("speciality", rs.getString(5));
					map.put("dob", rs.getString(6));
					map.put("address", rs.getString(7));
					map.put("city", rs.getString(8));
					map.put("state", rs.getString(9));

					map.put("zipcode", rs.getString(10));
					map.put("country", rs.getString(11));
					map.put("imgpath", rs.getString(13));
					map.put("clinicid", rs.getString(15));
					map.put("id", rs.getString("bd_id"));
					map.put("doctorname", rs.getString("doctorname"));
					map.put("doctorfee", rs.getString("bd_doctor_fee"));
					map.put("doctorid", rs.getString("bd_user_id"));
				}
				
				return map;
			}
		});
		
		return LOVList;
	}

	@Override
	public List<Object> getClinicDetail(String clinicQuery, int intdid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(clinicQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("clinicid", rs.getString("bc_id"));
				map.put("clinicdesc", rs.getString("bc_name"));
				map.put("address", rs.getString("bc_address"));
				map.put("state", rs.getString("bc_state"));
				map.put("city", rs.getString("bc_city"));
				
				map.put("country", rs.getString("bc_country"));
				map.put("startyear", rs.getString("bc_start_year"));
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
				map.put("email", rs.getString("bc_email"));
				map.put("action", HERO_CLC_SERVC_HERBZCLINICUTIL.DATATABLE_ACTION);
				
				map.put("tinno", rs.getString("bc_tin_no"));
				map.put("websitename", rs.getString("bc_website_name"));
				map.put("branchname", rs.getString("bc_branch_name"));
				map.put("regnno", rs.getString("bc_regnno"));
				map.put("druglicenceno", rs.getString("bc_drug_licence_no"));
				map.put("faxno", rs.getString("bc_faxno"));
				map.put("telno", rs.getString("bc_telephone_no"));
				map.put("smsintegration", rs.getString("bc_sms_integration"));
				map.put("totalsmscount", rs.getString("bc_total_smscount"));
				
				return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getPatientDetail(String patientQuery, int intpid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(patientQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {

				Map<String, Object> map = new HashMap<String, Object>();

				 
					map.put("patientid", rs.getString(1));
					map.put("firstname", rs.getString(2));
					map.put("lastname", rs.getString(3));
					map.put("dob", rs.getString(4));
					map.put("gender", rs.getString(5));
					map.put("martial", rs.getString(6));
					map.put("mobno", rs.getString(7));
					map.put("address", rs.getString(8));
					map.put("city", rs.getString(9));
					map.put("state", rs.getString(10));

					map.put("zipcode", rs.getString(11));
					map.put("medicalcomments", rs.getString(12));
					map.put("email", rs.getString(13));
					map.put("nomineename", rs.getString(14));
					map.put("bloodgroup", rs.getString("hp_bloodgroup"));
					map.put("pseqid", rs.getString("hp_seq_id"));
					map.put("clinicid", rs.getString("bc_id"));
					map.put("imgfilepath", rs.getString("hp_imagepath"));
					map.put("age", rs.getString("age"));
					map.put("currentdate", rs.getString("currentdate"));
					map.put("currenttime", rs.getString("currenttime"));
					/*map.put("clinicid", rs.getString(15));*/
					map.put("smspermission", rs.getString("hp_sms_permission"));
				return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getTimeList(HashMap<String, Object> clinicMap,
			String bookingDate, String clinicId, String doctorid)
			throws ParseException {
		// TODO Auto-generated method stub
		
		String timeQuery = "SELECT `hpb_time` FROM `hero_clinic_patient_booking` WHERE `hpb_date` = '"+HERO_CLC_SERVC_HERBZCLINICUTIL.convertToSQLDate(bookingDate)+"' and `bc_id` =  "+clinicId
				+" and `bd_id` = "+doctorid;
		log.info("timeQuery   "+timeQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> bookingList = jdbcTemplate.query(timeQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  String bookingTime = "";
				  
				  bookingTime = rs.getString(1);
				  
				  return bookingTime;
			}
		});
		log.info("bookingList   "+bookingList);
		
		ArrayList<Object> timeList = new ArrayList<Object>();
		HashMap<String, Object> timeMap = new HashMap<String, Object>();
		
		String clinicStartTime = "",clinicEndTime = "",breakStartTime="",breakEndTime="";
		int timeperslot = 0;
		
		if(clinicMap.get("starthour") != null && clinicMap.get("endhour") != null)
		{
			clinicStartTime = ((String) clinicMap.get("starthour")).concat(":"+(String)clinicMap.get("startmin"));	
			clinicEndTime = ((String) clinicMap.get("endhour")).concat(":"+(String)clinicMap.get("endmin"));
			breakStartTime = ((String) clinicMap.get("startbreakhour")).concat(":"+(String)clinicMap.get("startbreakmin"));
			breakEndTime = ((String) clinicMap.get("endbreakhour")).concat(":"+(String)clinicMap.get("endbreakmin"));
		}
		
		log.info("clinicStartTime   "+clinicStartTime+"   clinicEndTime   "+clinicEndTime+"   breakStartTime   "+breakStartTime+"   breakEndTime   "+breakEndTime);
		
		timeperslot = (int) clinicMap.get("timeperslot");
		
		boolean loop = true;
		
		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date clinincStartDate = df.parse(clinicStartTime);
		Calendar clinicCal = Calendar.getInstance();
		clinicCal.setTime(clinincStartDate);
		String clinicTime = df.format(clinicCal.getTime());
		
		if(!bookingList.contains(clinicTime))
		{
			timeMap.put("slotclass", "nil");
		}
		else
		{
			timeMap.put("slotclass", "blockTime");
		}
		
		String displayTime = HERO_CLC_SERVC_HERBZCLINICUTIL.AMPM_HourFormat(clinicTime);
		
		timeMap.put("timevalue", clinicTime);
		timeMap.put("displaytime", displayTime);
		
		timeList.add(timeMap);
		
		log.info("timeList   "+timeList);
		
		while(loop)
		{
			clinicTime = "";
			
			clinincStartDate = df.parse(clinicStartTime);
			Date clinicEndDate = df.parse(clinicEndTime);
			Date breakStartDate = df.parse(breakStartTime);
			Date breakEndDate = df.parse(breakEndTime);
			
			clinicCal = Calendar.getInstance();
			clinicCal.setTime(clinincStartDate);
			clinicCal.add(Calendar.MINUTE, timeperslot);
			
			clinicTime = df.format(clinicCal.getTime());
			Date currentTimeDate = df.parse(clinicTime);
			int bookingTimeDiff = currentTimeDate.compareTo(clinicEndDate);
			
			if(bookingTimeDiff >= 0)
			{
				
				loop = false;
			}
			
			clinicStartTime = clinicTime;
			
			int breakTimeStartDiff = currentTimeDate.compareTo(breakStartDate);
			int breakTimeEndDiff = currentTimeDate.compareTo(breakEndDate);
			log.info("clinicTime   "+clinicTime+"   Difference   "+breakTimeStartDiff+"   "+breakTimeEndDiff);
			if(breakTimeStartDiff >= 0 && breakTimeEndDiff < 0)
			{
				/*log.info("currentTimeDate   "+currentTimeDate);*/
			}
			else
			{
				if(loop)
				{
					timeMap = new HashMap<String, Object>();
					
					Calendar currentTimeCal = Calendar.getInstance();
					currentTimeCal.setTime(new Date());
					String currenttime = df.format(currentTimeCal.getTime());
					log.info("clinicEndTime in inside   "+clinicEndTime+"   currenttime   "+clinicTime);
					long diffWithCurrettime = HERO_CLC_SERVC_HERBZCLINICUTIL.timedifference(clinicEndTime, clinicTime);
					
					
					if(!bookingList.contains(clinicTime))
					{
						
						timeMap.put("slotclass", "nil");
						/*int innerDiff = clinicTime.compareTo(clinicEndTime);
						if(innerDiff >= 0)
						{
							loop = false;	
						}*/
						if(diffWithCurrettime >= 0)
						{
							loop = false;	
						}
						log.info("bookingSlot difference is   "+currenttime+"   "+diffWithCurrettime+"   "+loop);
					}
					else
					{
						timeMap.put("slotclass", "blockTime");
					}
					
					displayTime = HERO_CLC_SERVC_HERBZCLINICUTIL.AMPM_HourFormat(clinicTime);
					
					timeMap.put("timevalue", clinicTime);
					timeMap.put("displaytime", displayTime);
					
					timeList.add(timeMap);
					
					/*log.info("bookingList.size()   "+bookingList.size());
					if(bookingList.size() == 0)
					{
						loop = false;
					}*/
				}
					
			}
			
				log.info("loop is   "+loop);
		}
		log.info("2nd timeList   "+timeList);
		return timeList;
	}


	@Override
	public List<Object> getClinicsList(String query) {


		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HashMap<String, Object> map = new HashMap<String, Object>();
				  map.put("value", rs.getString(1));
				  map.put("label", rs.getString(2));
				  map.put("starthour", rs.getString(3));
				  map.put("endhour", rs.getString(4));
				  map.put("startmin", rs.getString(5));
				  map.put("endmin", rs.getString(6));
				  map.put("timeperslot", rs.getInt(7));
				  map.put("startbreakhour", rs.getString(8));
				  map.put("endbreakhour", rs.getString(9));
				  map.put("breakmins", rs.getInt(10));
				  map.put("startbreakmin", rs.getString(11));
				  map.put("endbreakmin", rs.getString(12));
				  index++;
				  
				  return map;
			}
		});
		
		return LOVList;
	
	}


	@Override
	public List<Object> getDodontsDetail(String dodontsquery,final int dodontid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(dodontsquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HashMap<String, Object> map = new HashMap<String, Object>();
				  map.put("id", rs.getString(1));
				  map.put("value", rs.getString(2));
				  map.put("index", index);
				  
				  if(dodontid > 0)
				  {
					  map.put("dovalue", rs.getString("hppd_dos"));
					  map.put("dontvalue", rs.getString("hppd_donts"));
				  }
				  else
				  {
					  map.put("dovalue", "");
					  map.put("dontvalue", "");
				  }
				  
				  index++;
				  
				  return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getMedicineDetail(String medicinequery, final int intprid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(medicinequery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HashMap<String, Object> map = new HashMap<String, Object>();
				  
				  index++;
				  
				  if(intprid > 0)
				  {
					  map.put("index", index);
					  map.put("idindex", index);
					  map.put("detailid", rs.getString("hppm_id"));
					  map.put("prescriptionid", rs.getString("hpp_id"));
					  map.put("medicinedesc", rs.getString("hppm_medicine_desc"));
					  map.put("medicineid", rs.getString("hppm_medicine_id"));
					  map.put("medicinetimes", rs.getString("hppm_medicine_times"));
					  map.put("dosage", rs.getString("hppm_dosage"));
					  map.put("consumedays", rs.getString("hppm_consume_days"));
					  map.put("take", rs.getString("hppm_take"));
					  map.put("notes", rs.getString("hppm_notes"));
				  }
				  /*else
				  {
					  map.put("detailid", "0");
					  map.put("prescriptionid", Integer.toString(intprid));
					  map.put("medicinedesc", "");
					  map.put("medicineid", "0");
					  map.put("medicinetimes", "");
					  map.put("dosage", rs.getString("hppm_dosage"));
					  map.put("consumedays", rs.getString("hppm_consume_days"));
					  map.put("take", rs.getString("hppm_take"));
					  map.put("notes", rs.getString("hppm_notes"));
				  }*/
				 
				  
				  return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getPrescriptionsDetail(String prescriptionquery,
			final int intprid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(prescriptionquery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HashMap<String, Object> map = new HashMap<String, Object>();
				  
				  if(intprid > 0)
				  {
					  map.put("excercise", rs.getString("hpp_excercise"));
					  map.put("existdiagnosis", rs.getString("hpp_final_diagnosis"));
				  }
				 
				  index++;
				  
				  return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getDoctorClinicDetail(String clinicsQuery, int intcid) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(clinicsQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("clinicid", rs.getString("bc_id"));
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
				
				
				return map;
			}
		});
		
		return LOVList;
	}


	@Override
	public List<Object> getMedicineHistory(String medicinequery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(medicinequery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  HashMap<String, Object> map = new HashMap<String, Object>();
				  
				  index++;
				   
					  map.put("index", index);
					  map.put("idindex", index);
					  map.put("detailid", rs.getString("hppm_id"));
					  map.put("prescriptionid", rs.getString("hpp_id"));
					  map.put("medicinedesc", rs.getString("hppm_medicine_desc"));
					  map.put("medicineid", rs.getString("hppm_medicine_id"));
					  map.put("medicinetimes", rs.getString("hppm_medicine_times"));
					  map.put("dosage", rs.getString("hppm_dosage"));
					  map.put("consumedays", rs.getString("hppm_consume_days"));
					  map.put("take", rs.getString("hppm_take"));
					  map.put("notes", rs.getString("hppm_notes"));
				  
				  
				  return map;
			}
		});
		
		return LOVList;
	}
}
