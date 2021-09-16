package com.hero.clinic.controller.view.clinic;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hero.clinic.services.lov.HERO_CLC_SERVC_ICLINICLOV;
import com.hero.clinic.services.util.HERO_CLC_SERVC_CLINICDAO;
import com.hero.clinic.services.util.HERO_CLC_SERVC_HERBZCLINICUTIL;

@Controller
public class HERO_CLC_VIEW_CLINICSCTRL extends HERO_CLC_SERVC_CLINICDAO 
{
	private static Logger log = Logger.getLogger(HERO_CLC_VIEW_CLINICSCTRL.class);
	
	@Autowired
	private HERO_CLC_SERVC_ICLINICLOV clinicLOVobj;
	
	public static JdbcTemplate jdbcTemplateObj;
	
	@RequestMapping(value="/doctor")
	public ModelAndView gotoDoctor()
	{
		return new ModelAndView("/forms/doctor/doctor");
    }
	
	@RequestMapping(value="/adddoctor")
	public ModelAndView gotoAddDoctor(@RequestParam(value = "did", required = false) String did,HttpServletRequest request)
	{
		log.info("jdbcTemplate   "+jdbcTemplate);
		jdbcTemplateObj = jdbcTemplate;
		
		String query = "";
		int intdid = 0;
		if(did != null)
		{
			intdid = Integer.parseInt(did);
		}
		
		log.info("intdid   "+intdid);
		
		HttpSession session = request.getSession();
		
		if(intdid == 0)
		{
			query = "SELECT `fname`,`lname`,`emailid`,`phone` FROM `hero_user` WHERE `userid` = "+session.getAttribute("uid");
		}
		else
		{
			query = "SELECT `bd_firstname`,`bd_lastname`,`bd_email`,`bd_mobileno`,`bd_speciality`,DATE_FORMAT(`bd_dob`,'%d/%m/%Y') dob"
					+ ",`bd_address`,`bd_city`,`bd_state`,`bd_zipcode`,`bd_country`,`bd_gender`,`bd_imgpath_file`,`bd_id`,bc_id,"
					+ "CONCAT(`bd_prefix`,'.',`bd_firstname`,COALESCE(`bd_lastname`,''))doctorname,`bd_doctor_fee`,`username`,bd_ug_qualification,bd_displayname,"
					+ "bd_known_languages,bd_intercom_no,bd_ug_institution,bd_clinician_no,bd_years_of_exp,bd_workinghours_from,bd_workinghours_to,"
					+ "DATE_FORMAT(`bd_doj`,'%d/%m/%Y') doj,bd_consulting_time,bd_prefix,bd_vistingdays FROM `hero_admin_physician_profile` a JOIN `hero_user` b ON "
					+ "a.`bd_user_id` = b.`userid` WHERE `bd_id` = "+did;
		}
		log.info("query   "+query);
		
		String clinicsQuery = "SELECT `bc_id`,`bc_name`,`bc_address`,`bc_state`,`bc_city`,`bc_country`,`bc_mobileno`,`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,"
				+ "`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,`bc_break_hour_start`,`bc_break_hour_end`,`bc_email`,bc_tin_no,"
				+ "bc_website_name,bc_branch_name,bc_regnno,bc_drug_licence_no,bc_faxno,bc_telephone_no,bc_total_smscount,bc_sms_integration FROM "
				+ "`hero_admin_clinic`";
		
		List<Object> doctorList = clinicLOVobj.getDoctorDetail(query,intdid);
		List<Object> clinicsList = clinicLOVobj.getClinicDetail(clinicsQuery,intdid);
		
		String specialistQuery = "SELECT `hps_id`,`hps_desc` FROM `hero_physician_specialist`";
		@SuppressWarnings("unchecked")
		List<Object> physicianSpecialistList = jdbcTemplate.query(specialistQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("specialistid", rs.getString("hps_id"));
				map.put("specialistdesc", rs.getString("hps_desc"));
				return map;
			}
		});
		
		ModelAndView model = new ModelAndView();
		String userid = "",fname = "",lname = "",phone = "",email = "",ugqualification = "",dob="",dispname = "",clinicid="",knownlangs="",intercomno = "",uginstitution="",
				clinicianno = "",speciality="",yearsofexp="",workinghoursfrom = "",workinghoursto = "",doj = "",consultingtime = "",doctorfee = "",prefix = "",
				visitingdays = "";
		HashMap<String, Object> doctorMap = new HashMap<String, Object>();
		log.info("doctorList   "+doctorList);
		if(doctorList.size() > 0)
		{
			doctorMap = (HashMap<String, Object>) doctorList.get(0);
			log.info("doctorMap   "+doctorMap);
			
			if(doctorMap != null)
			{
				userid = (String) doctorMap.get("userid");
				fname = (String) doctorMap.get("fname");
				lname = (String) doctorMap.get("lname");
				phone = (String) doctorMap.get("phone");
				email = (String) doctorMap.get("email");
				ugqualification = (String) doctorMap.get("ugqualification");
				dob = (String) doctorMap.get("dob");
				dispname = (String) doctorMap.get("dispname");
				clinicid = (String) doctorMap.get("clinicid");
				knownlangs = (String)doctorMap.get("knownlangs");
				intercomno = (String)doctorMap.get("intercomno");
				uginstitution = (String)doctorMap.get("uginstitution");
				clinicianno = (String)doctorMap.get("clinicianno");
				speciality = (String)doctorMap.get("speciality");
				yearsofexp = (String)doctorMap.get("yearsofexp");
				workinghoursfrom = (String)doctorMap.get("workinghoursfrom");
				workinghoursto = (String)doctorMap.get("workinghoursto");
				doj = (String)doctorMap.get("doj");
				consultingtime = (String)doctorMap.get("consultingtime");
				doctorfee =  (String)doctorMap.get("doctorfee");
				prefix =  (String)doctorMap.get("prefix");
				visitingdays = (String)doctorMap.get("visitingdays");
			}
		
		}

		model.addObject("userid",userid);
		model.addObject("fname",fname);
		model.addObject("lname",lname);
		model.addObject("phone",phone);
		model.addObject("email",email);
		model.addObject("dob",dob);
		model.addObject("ugqualification",ugqualification);
		model.addObject("dispname",dispname);
		model.addObject("knownlangs",knownlangs);
		model.addObject("intercomno",intercomno);
		model.addObject("uginstitution",uginstitution);
		model.addObject("clinicianno",clinicianno);
		model.addObject("speciality",speciality);
		model.addObject("yearsofexp",yearsofexp);
		model.addObject("workinghoursfrom",workinghoursfrom);
		model.addObject("workinghoursto",workinghoursto);
		model.addObject("doj",doj);
		model.addObject("consultingtime",consultingtime);
		model.addObject("doctorfee",doctorfee);
		model.addObject("prefix",prefix);
		model.addObject("visitingdays",visitingdays);
		
		model.addObject("clinicsList",clinicsList);
		model.addObject("physicianSpecialistList",physicianSpecialistList);
		
		if(intdid > 0)
		{
			request.setAttribute("specialityname", (String) doctorMap.get("speciality"));
			request.setAttribute("dobname", (String) doctorMap.get("dob"));
			request.setAttribute("addressname", (String) doctorMap.get("address"));
			request.setAttribute("cityname", (String) doctorMap.get("city"));
			request.setAttribute("statename", (String) doctorMap.get("state"));
			
			request.setAttribute("zipcodename", (String) doctorMap.get("zipcode"));
			request.setAttribute("countryname", (String) doctorMap.get("country"));
			request.setAttribute("doctorfee", (String) doctorMap.get("doctorfee"));
			
			String requestFileName = (String)request.getAttribute("fileName");
			log.info("before fileName   "+requestFileName);
			if(requestFileName == null || requestFileName == "")
			{
				request.setAttribute("fileName", (String) doctorMap.get("imgpath"));	
			}
			requestFileName = (String)request.getAttribute("fileName");
			session.setAttribute("profilephoto", requestFileName);
			
			log.info("after fileName   "+requestFileName);
			
			request.setAttribute("clinicid",(String)doctorMap.get("clinicid"));
		}
		
		
		model.setViewName("/forms/doctor/adddoctor");
		 
		return model;
    }
	@RequestMapping(value="/clinics")
	public ModelAndView Clinic(@RequestParam(value="cid")String cid)
	{
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/masters/clinics");
		return model;
    }
	@RequestMapping(value="/addclinic")
	public ModelAndView gotoAddClinic()
	{
		  ModelAndView model = new ModelAndView();
		String currencyQuery="SELECT currency_id,currency FROM hero_admin_currency";
		
		List<Object>  currencyList = clinicLOVobj.getLOVList(currencyQuery);
      
		
		
		model.addObject("currencyList",currencyList); 
		
		model.setViewName("forms/admin/masters/addclinic");
		return model;
		
		
	}
	
	
	/*@RequestMapping(value="/addclinic")
	public ModelAndView addClinic(@RequestParam(value="cid")String cid)
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/admin/masters/addclinic");
		
		return model;
    }*/
	
	@RequestMapping(value="/patients")
	public ModelAndView gotoPatients()
	{
		return new ModelAndView("/forms/patient/patient");
    }
	
	@RequestMapping(value="/addpatient")
	public ModelAndView gotoAddPatient(@RequestParam(value="pid")String pid,HttpServletRequest request)
	{
		jdbcTemplateObj = jdbcTemplate;
		int intpid = 0;
		String patientquery = "";
		ModelAndView model = new ModelAndView();

		String firstname = "",lastname = "",dob = "",gender = "",martial = "",mobno = "",address = "",city = "", state = "",pincode = "",comments = "",email = "",
				nomineename = "",bloodgroup = "",pseqid = "0",clinicid= "",doctorfee = "",smspermission = "";
		log.info("pid   "+pid);
		String patientidQuery="SELECT CONCAT('HP00',`seq_id`+1) id, seq_id FROM `hero_admin_patient_seq` ";
		String clinicsQuery = "SELECT `bc_id`,`bc_name`,`bc_address`,`bc_state`,`bc_city`,`bc_country`,`bc_mobileno`,`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,"
				+ "`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,`bc_break_hour_start`,`bc_break_hour_end`,`bc_email`,`bc_tin_no`,`bc_website_name`,`bc_branch_name`,`bc_regnno`,`bc_drug_licence_no`,`bc_faxno`,`bc_telephone_no`,`bc_sms_integration`,`bc_total_smscount`  FROM "
				+ "`hero_admin_clinic`";
		List<Object> clinicsList = clinicLOVobj.getClinicDetail(clinicsQuery,0);
		 List<Object> patientidList = clinicLOVobj.getLOVList(patientidQuery);
		 model.addObject("patientid",patientidList.get(0));
	
		if(pid != null)
		{
			intpid = Integer.parseInt(pid);
			if(intpid > 0)
			{
				patientquery = "SELECT `hp_id`,`hp_name`,COALESCE(hp_lastname,'')pat_lastname,DATE_FORMAT(`hp_dob`,'%d/%m/%Y')dob,`hp_gender`,`hp_martial`,`hp_mobno`,"
						+ "`hp_address`,`hp_city`,`hp_state`,`hp_pincode`,`hp_medical_comments`,`hp_email`,`hp_nominee_name`,"
						+ "ROUND(DATEDIFF(CURRENT_DATE, STR_TO_DATE(`hp_dob`, '%Y-%m-%d'))/365)age,`hp_created_date`,`hp_bloodgroup`,hp_seq_id,bc_id,`hp_imagepath`,"
						+ "DATE_FORMAT(CURDATE(),'%d/%m/%Y')currentdate,TIME_FORMAT(CURTIME(),'%h:%i %p')currenttime,`hp_sms_permission` FROM `hero_admin_patient` where `hp_seq_id` = "+intpid;
			
				
			List<Object> patientList = clinicLOVobj.getPatientDetail(patientquery, intpid);
			
			log.info("patientList   "+patientList);
			
			if(patientList != null && patientList.size() > 0)
			{
				Map<String, Object> patientMap = (Map<String, Object>) patientList.get(0);
				
				firstname = (String) patientMap.get("firstname");
				lastname = (String) patientMap.get("lastname");
				dob = (String) patientMap.get("dob");
				gender = (String) patientMap.get("gender");
				martial = (String) patientMap.get("martial");
				
				mobno = (String) patientMap.get("mobno");
				address = (String) patientMap.get("address");
				city = (String) patientMap.get("city");
				state = (String) patientMap.get("state");
				pincode = (String) patientMap.get("zipcode");
				
				comments = (String) patientMap.get("medicalcomments");
				email = (String) patientMap.get("email");
				nomineename = (String) patientMap.get("nomineename");
				bloodgroup = (String) patientMap.get("bloodgroup");
				pseqid = (String) patientMap.get("pseqid");
				
				clinicid = (String) patientMap.get("clinicid");
				doctorfee = (String)patientMap.get("doctorfee");
				smspermission = (String)patientMap.get("smspermission");
				
				String requestFileName = (String)request.getAttribute("fileName");
				log.info("before fileName   "+requestFileName);
				if(requestFileName == null || requestFileName == "")
				{
					request.setAttribute("fileName", (String) patientMap.get("imgfilepath"));	
				}
				requestFileName = (String)request.getAttribute("fileName");
				HttpSession session = request.getSession();
				session.setAttribute("profilephoto", requestFileName);
				
				log.info("after fileName   "+requestFileName);
			}
			}
			
			
			request.setAttribute("firstname",firstname);
			request.setAttribute("lastname",lastname);
			request.setAttribute("dob",dob);
			request.setAttribute("gender",gender);
			request.setAttribute("martial",martial);
			
			request.setAttribute("mobno",mobno);
			request.setAttribute("address",address);
			request.setAttribute("city",city);
			request.setAttribute("state",state);
			request.setAttribute("pincode",pincode);
			
			request.setAttribute("comments",comments);
			request.setAttribute("email",email);
			request.setAttribute("nomineename",nomineename);
			request.setAttribute("bloodgroup",bloodgroup);
			request.setAttribute("pseqid",pseqid);
			
			request.setAttribute("clinicid", clinicid);
			request.setAttribute("doctorfee", doctorfee);
			request.setAttribute("smspermission", smspermission);
			
			model.addObject("clinicsList",clinicsList);
			log.info("clinicsList   "+clinicsList);
			
			/*model.addObject("",firstname);
			model.addObject("",firstname);*/
			
		}
		
		model.setViewName("/forms/patient/addpatient");
		
		return model;
    }
	
	@RequestMapping(value="/schedule")
	public ModelAndView gotoSchedule()
	{
		String doctorsQuery = "";
		doctorsQuery = "SELECT `bd_firstname`,`bd_lastname`,`bd_email`,`bd_mobileno`,`bd_speciality`,DATE_FORMAT(`bd_dob`,'%c/%e/%y') dob"
					+ ",`bd_address`,`bd_city`,`bd_state`,`bd_zipcode`,'bd_prefix','bd_displayname','bd_ug_qualification','bd_ug_institution','bd_years_of_exp','bd_known_languages','bd_clinician_no','bd_intercom_no','bd_workinghours_from','bd_workinghours_to','bd_doj','bd_consulting_time','bd_visitingdays','bd_doctor_fee','bd_created_by','bd_created_date','bd_modified_by','bd_modified_date','bd_user_id',`bd_country`,`bd_gender`,`bd_imgpath_file`,`bd_id`,bc_id,"
					+ "CONCAT(`bd_prefix`,`bd_firstname`,COALESCE(`bd_lastname`,''))doctorname,`bd_doctor_fee` FROM `hero_admin_physician_profile` where `bc_id` is not null";
		
		log.info("schedule doctorsQuery   "+doctorsQuery);
		
		String clinicsQuery = "SELECT `bc_id`,`bc_name`,`bc_address`,`bc_state`,`bc_city`,`bc_country`,`bc_mobileno`,`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,"
				+ "`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,`bc_break_hour_start`,'bc_tin_no','bc_website_name','bc_branch_name','bc_regnno','bc_drug_licence_no','bc_faxno','bc_telephone_no','bc_sms_integration','bc_total_smscount',`bc_break_hour_end`,`bc_email` FROM "
				+ "`hero_admin_clinic`";
		
		List<Object> doctorList = clinicLOVobj.getDoctorDetail(doctorsQuery,1);
		List<Object> clinicsList = clinicLOVobj.getClinicDetail(clinicsQuery,0);
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("doctorList",doctorList);
		model.addObject("clinicsList",clinicsList);
		
		log.info("size  "+doctorList.size()+"   "+clinicsList.size());
		
		model.setViewName("/forms/schedule/schedule");
		
		return model;
    }
	
	@RequestMapping(value="/walkin")
	public ModelAndView gotoWalkin()
	{
		String doctorsQuery = "";
		doctorsQuery = "SELECT `bd_firstname`,`bd_lastname`,`bd_email`,`bd_mobileno`,`bd_speciality`,DATE_FORMAT(`bd_dob`,'%c/%e/%y') dob"
					+ ",`bd_address`,`bd_city`,`bd_state`,`bd_zipcode`,'bd_prefix','bd_displayname','bd_ug_qualification','bd_ug_institution','bd_years_of_exp','bd_known_languages','bd_clinician_no','bd_intercom_no','bd_workinghours_from','bd_workinghours_to','bd_doj','bd_consulting_time','bd_visitingdays','bd_doctor_fee','bd_created_by','bd_created_date','bd_modified_by','bd_modified_date','bd_user_id',`bd_country`,`bd_gender`,`bd_imgpath_file`,`bd_id`,bc_id,"
					+ "CONCAT(`bd_prefix`,`bd_firstname`,COALESCE(`bd_lastname`,''))doctorname,`bd_doctor_fee` FROM `hero_admin_physician_profile` where `bc_id` is not null";
		
		log.info("doctorsQuery   "+doctorsQuery);
		String doctorfee = "0";
		
		List<Object> doctorList = clinicLOVobj.getDoctorDetail(doctorsQuery,1);
		
		if(doctorList.size() > 0)
		{
			HashMap<String, Object> doctorMap = (HashMap<String, Object>) doctorList.get(0);
			if(doctorMap != null)
			{
				doctorfee = (String)doctorMap.get("doctorfee");
			}
		}
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("doctorfee",doctorfee);
		model.addObject("doctorList",doctorList);
		model.setViewName("/forms/walkin/walkin");
		
		return model;
	}
	
	@RequestMapping(value="/appointment")
	public ModelAndView gotoAppointment(HttpServletRequest request)
	{
		HttpSession session = request.getSession();
		int usertype = (int) session.getAttribute("usertype");
		log.info("usertype   "+usertype);
		String doctorsQuery = "";
		String clinicsQuery  = "";
		String firstdoctor = "";
		List<Object> doctorList = null;
		
		if(usertype == 4){ // for doctor
		doctorsQuery = "SELECT `bd_firstname`,`bd_lastname`,`bd_email`,`bd_mobileno`,`bd_speciality`,DATE_FORMAT(`bd_dob`,'%c/%e/%y') dob"
					+ ",`bd_address`,`bd_city`,`bd_state`,`bd_zipcode`,`bd_country`,`bd_gender`,`bd_imgpath_file`,`bd_id`,bc_id,"
					+ "CONCAT(`bd_prefix`,`bd_firstname`,COALESCE(`bd_lastname`,''))doctorname,`bd_doctor_fee`,`bd_user_id` FROM `hero_admin_physician_profile`"
					+ " WHERE `bd_user_id` = '"+session.getAttribute("uid")+"'";
		clinicsQuery = "SELECT `hcs_id`,a.`bd_id`,a.`bc_id`,DATE_FORMAT(`hcs_from_date`,'%d/%m/%Y')fromdate,DATE_FORMAT(`hcs_to_date`,'%d/%m/%Y')todate,"
				+ "`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,"
				+ "`bc_break_hour_start`,`bc_break_hour_end` FROM `hero_clinic_schedule` a JOIN `hero_admin_physician_profile` b ON a.`bd_id` = b.`bd_id` "
				+ "JOIN `hero_admin_clinic` c ON a.`bc_id` = c.`bc_id` JOIN `hero_user` d ON d.`userid` = b.`bd_user_id` "
				+ "WHERE d.`userid` = "+session.getAttribute("uid")+" ORDER BY `hcs_from_date`,`hcs_to_date`";
		log.info("doctorsQuery   "+doctorsQuery);
		log.info("clinicsQuery   "+clinicsQuery);
		doctorList = clinicLOVobj.getDoctorDetailforAppointment(doctorsQuery,1);
		firstdoctor = (String)session.getAttribute("uid");
			
		}else if(usertype == 5){ // for reception
		doctorsQuery = "SELECT `bd_firstname`,`bd_lastname`,`bd_email`,`bd_mobileno`,`bd_speciality`,DATE_FORMAT(`bd_dob`,'%c/%e/%y') dob"
					+ ",`bd_address`,`bd_city`,`bd_state`,`bd_zipcode`,`bd_country`,`bd_gender`,`bd_imgpath_file`,`bd_id`,bc_id,"
					+ "CONCAT(`bd_prefix`,`bd_firstname`,COALESCE(`bd_lastname`,''))doctorname,`bd_doctor_fee`,`bd_user_id` FROM `hero_admin_physician_profile`";
		
		log.info("doctorsQuery   "+doctorsQuery);
		
		doctorList = clinicLOVobj.getDoctorDetailforAppointment(doctorsQuery,1);
		log.info("doctorList   "+doctorList);
		String doctorid = "";
		if(doctorList.size() > 0)
		{
			HashMap<String, Object> doctorMap = (HashMap<String, Object>) doctorList.get(0);
			if(doctorMap != null)
			{
				doctorid = (String) doctorMap.get("doctorid");	
				firstdoctor = (String) doctorMap.get("doctorid");
			}
		}
		clinicsQuery = "SELECT `hcs_id`,a.`bd_id`,a.`bc_id`,DATE_FORMAT(`hcs_from_date`,'%d/%m/%Y')fromdate,DATE_FORMAT(`hcs_to_date`,'%d/%m/%Y')todate,"
				+ "`bc_hour_start`,`bc_hour_end`,`bc_time_per_slot`,`bc_min_start`,`bc_min_end`,`bc_break_min_start`,`bc_break_min_end`,`bc_break_mis`,"
				+ "`bc_break_hour_start`,`bc_break_hour_end` FROM `hero_clinic_schedule` a JOIN `hero_admin_physician_profile` b ON a.`bd_id` = b.`bd_id` "
				+ "JOIN `hero_admin_clinic` c ON a.`bc_id` = c.`bc_id` JOIN `hero_user` d ON d.`userid` = b.`bd_user_id` "
				+ "WHERE d.`userid` = "+doctorid+" ORDER BY `hcs_from_date`,`hcs_to_date`";
		log.info("clinicsQuery   "+clinicsQuery);
		}


		List<Object> clinicsList = clinicLOVobj.getDoctorClinicDetail(clinicsQuery,1);

		log.info("doctor   "+doctorList);
		log.info("clinic   "+clinicsList);
		
		String slottime = "00:00:00",starttime = "00:00:00",endtime = "00:00:00",clinicid = "0";
		
		log.info("firstdoctor   "+firstdoctor);
		ModelAndView model = new ModelAndView();
		
		if(clinicsList.size() > 0)
		{
			HashMap<String, Object> clinicMap = (HashMap<String, Object>) clinicsList.get(0);
			if(clinicMap != null)
			{
				String slot = (String)clinicMap.get("timeperslot");
				String starthour = (String)clinicMap.get("starthour");
				String endhour = (String)clinicMap.get("endhour");
				String startmin = (String)clinicMap.get("startmin");
				String endmin = (String)clinicMap.get("endmin");
				clinicid = (String)clinicMap.get("clinicid");
				
				if(slot != null && starthour != null && endhour != null && startmin != null && endmin != null)
				{
					slottime = ("00:").concat(slot).concat(":00");
					starttime = (starthour).concat(":").concat(startmin).concat(":00");
					endtime = (endhour).concat(":").concat(endmin).concat(":00");	
				}
				
			}
		}
		log.info("slottime   "+slottime+"  starttime "+starttime+"   endtime   "+endtime);
		
		model.addObject("slottime",slottime);
		model.addObject("starttime",starttime);
		model.addObject("endtime",endtime);
		model.addObject("doctorList",doctorList);
		model.addObject("firstdoctor",firstdoctor);
		model.addObject("clinicid",clinicid);
		
		model.setViewName("/forms/appointment/appointment");
		
		return model;
	}
	
	@RequestMapping(value="/mypatients")
	public ModelAndView gotoMypatiens()
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/doctor/mypatients");
		
		return model;
	}
	
	@RequestMapping(value="/addprescriptions")
	public ModelAndView gotoAddPrescriptions(@RequestParam(value="pid")String pid,@RequestParam(value="prid")String prid,HttpServletRequest request)
	{
		jdbcTemplateObj = jdbcTemplate;
		int intpid = 0;
		String patientquery = "",dodontsquery="",diagdisp ="",diagbufferhidestr="";
		ModelAndView model = new ModelAndView();

		String firstname = "",lastname = "",dob = "",gender = "",martial = "",mobno = "",address = "",city = "", state = "",pincode = "",comments = "",email = "",
				nomineename = "",bloodgroup = "",pseqid = "0",clinicid= "",doctorfee = "",age = "",currentdate="",currenttime="";
		log.info("pid   "+pid);
		StringBuffer diagbuffer = new StringBuffer();
		StringBuffer diagbufferhide = new StringBuffer();
		
		List<Object> prescriptionsList = new ArrayList<Object>();
		List<Object> dodontsList = new ArrayList<Object>();
		List<Object> medicinesList = new ArrayList<Object>();
		
		if(pid != null)
		{
			intpid = Integer.parseInt(pid);
			if(intpid > 0)
			{
				patientquery = "SELECT `hp_id`,`hp_name`,COALESCE(hp_lastname,'')pat_lastname,DATE_FORMAT(`hp_dob`,'%d/%m/%Y')dob,`hp_gender`,`hp_martial`,`hp_mobno`,"
						+ "`hp_address`,`hp_city`,`hp_state`,`hp_pincode`,`hp_medical_comments`,`hp_email`,`hp_nominee_name`,"
						+ "ROUND(DATEDIFF(CURRENT_DATE, STR_TO_DATE(`hp_dob`, '%Y-%m-%d'))/365)age,`hp_created_date`,`hp_bloodgroup`,hp_seq_id,bc_id,`hp_imagepath`,"
						+ "DATE_FORMAT(CURDATE(),'%d/%m/%Y')currentdate,TIME_FORMAT(CURTIME(),'%h:%i %p')currenttime"
						+ " FROM `hero_admin_patient` where `hp_seq_id` = "+intpid;
			
				dodontsquery = "SELECT * FROM `hero_clinic_prescriptions_dodonts`";
				
			List<Object> patientList = clinicLOVobj.getPatientDetail(patientquery, intpid);
			dodontsList = clinicLOVobj.getDodontsDetail(dodontsquery,0);
			
			log.info("patientList   "+patientList);
			
			if(patientList != null && patientList.size() > 0)
			{
				Map<String, Object> patientMap = (Map<String, Object>) patientList.get(0);
				
				firstname = (String) patientMap.get("firstname");
				lastname = (String) patientMap.get("lastname");
				dob = (String) patientMap.get("dob");
				gender = (String) patientMap.get("gender");
				martial = (String) patientMap.get("martial");
				
				mobno = (String) patientMap.get("mobno");
				address = (String) patientMap.get("address");
				city = (String) patientMap.get("city");
				state = (String) patientMap.get("state");
				pincode = (String) patientMap.get("zipcode");
				
				comments = (String) patientMap.get("medicalcomments");
				email = (String) patientMap.get("email");
				nomineename = (String) patientMap.get("nomineename");
				bloodgroup = (String) patientMap.get("bloodgroup");
				pseqid = (String) patientMap.get("pseqid");
				
				clinicid = (String) patientMap.get("clinicid");
				doctorfee = (String)patientMap.get("doctorfee");
				age = (String)patientMap.get("age");
				
				String requestFileName = (String)request.getAttribute("fileName");
				log.info("before fileName   "+requestFileName);
				if(requestFileName == null || requestFileName == "")
				{
					request.setAttribute("fileName", (String) patientMap.get("imgfilepath"));	
				}
				requestFileName = (String)request.getAttribute("fileName");
				HttpSession session = request.getSession();
				session.setAttribute("profilephoto", requestFileName);
				
				log.info("after fileName   "+requestFileName);
				
				currentdate = (String) patientMap.get("currentdate");
				currenttime = (String) patientMap.get("currenttime"); 
			}
			}
			
			
			request.setAttribute("firstname",firstname);
			request.setAttribute("lastname",lastname);
			request.setAttribute("dob",dob);
			request.setAttribute("gender",((gender != null ? (gender.equals("M") ? "Male" : "Female"):" ")));
			
			if(martial != null && martial.equals("S"))
			{
				martial = "Single";
			}
			else if(martial.equals("M"))
			{
				martial = "Married";
			}
			else if(martial.equals("W"))
			{
				martial = "Widowed";
			}
			else if(martial.equals("D"))
			{
				martial = "Divorced";
			}
			
			request.setAttribute("martial",martial);
			
			request.setAttribute("mobno",mobno);
			request.setAttribute("address",address);
			request.setAttribute("city",city);
			request.setAttribute("state",state);
			request.setAttribute("pincode",pincode);
			
			request.setAttribute("comments",comments);
			request.setAttribute("email",email);
			request.setAttribute("nomineename",nomineename);
			request.setAttribute("bloodgroup",bloodgroup);
			request.setAttribute("pseqid",pseqid);
			
			request.setAttribute("clinicid", clinicid);
			request.setAttribute("doctorfee", doctorfee);
			request.setAttribute("age", age);
			request.setAttribute("patientname", ((firstname == null ? "" : firstname).concat((lastname == null ? "" : lastname))));
			
			request.setAttribute("currentdate", currentdate);
			request.setAttribute("currenttime", currenttime);
		}
		log.info("currentdate is   "+request.getAttribute("currentdate")+"   "+currentdate);
		if(prid != null)
		{
			int intprid = Integer.parseInt(prid);
			if(intprid > 0)
			{
				String prescriptionquery = "";
				String medicinequery = "";
				
				dodontsquery = "SELECT * FROM `hero_clinic_prescriptions_dodonts` a LEFT OUTER JOIN `hero_clinic_patient_prescriptions_dodonts` b ON a.`hpd_id` = b.`hpd_id` "
						+ "WHERE b.`hpp_id` = "+intprid;
				medicinequery = "SELECT `hppm_id`,`hpp_id`,`hppm_medicine_desc`,`hppm_medicine_id`,`hppm_medicine_times`,`hppm_dosage`,`hppm_consume_days`,`hppm_take`,"
						+ "`hppm_notes`  FROM `hero_clinic_patient_prescriptions_details` WHERE `hpp_id` = "+intprid;
				prescriptionquery = "SELECT `hpp_excercise`,`hpp_final_diagnosis` FROM `hero_clinic_patient_prescriptions` WHERE `hpp_id` = "+intprid;
				
				log.info("dodontsquery   "+dodontsquery);
				log.info("prescriptionquery   "+prescriptionquery);
				log.info("medicinequery   "+medicinequery);
				
				prescriptionsList = clinicLOVobj.getPrescriptionsDetail(prescriptionquery, intprid);
				dodontsList = clinicLOVobj.getDodontsDetail(dodontsquery,intprid);
				medicinesList = clinicLOVobj.getMedicineDetail(medicinequery,intprid);
			}
		}
		log.info("medicinesList   "+medicinesList.size());
		
		String excercise = "",existdiagnosis="";
		List<String> existdiagnosisList = new ArrayList<String>();
		List<Object> existdiagnosisMapList = new ArrayList<Object>();
		
		if(prescriptionsList.size() > 0)
		{
			HashMap<String, Object> map = (HashMap<String, Object>) prescriptionsList.get(0);
			if(map.get("excercise") != null)
			excercise = (String) map.get("excercise");
			
			if(map.get("existdiagnosis") != null)
			{
				existdiagnosis = (String) map.get("existdiagnosis");
			}
			
			StringTokenizer existdiagnosistoken = new StringTokenizer(existdiagnosis,",");
			
			diagbuffer.append("[");
			
			while(existdiagnosistoken.hasMoreTokens())
			{
				String value = existdiagnosistoken.nextToken();
				existdiagnosisList.add(value);
				
				/*Map<String, Object> existdiagnosisMap = new HashMap<String, Object>();
				existdiagnosisMap.put("&quot;text&quot;", "&quot;"+value+"&quot;");
				existdiagnosisMap.put("&quot;value&quot;", "&quot;"+value+"&quot;");
				existdiagnosisMapList.add(existdiagnosisMap);*/
				
				diagbuffer.append("{&quot;text&quot;: &quot;"+value+"&quot;, &quot;value&quot; : &quot;"+value+"&quot;},");
				diagbufferhide.append(value+",");
			}
			diagbuffer.append("]");
			diagdisp = diagbuffer.toString();
			diagdisp = (diagdisp.substring(0,diagdisp.length() - 2)).concat("]");
			if(diagdisp != null && diagdisp.equalsIgnoreCase("[]"))
			{
				diagdisp = "";
			}
		}
		log.info("existdiagnosisMapList   "+existdiagnosisMapList);
		diagbufferhidestr = diagbufferhide.toString();
		if(diagbufferhidestr.length() > 0)
		diagbufferhidestr = diagbufferhidestr.substring(0,(diagbufferhidestr.length()) - 1);
		
		model.addObject("excercise",excercise);
		model.addObject("medicinesList",medicinesList);
		model.addObject("dodontsList",dodontsList);
		model.addObject("existdiagnosisList",existdiagnosisList);
		model.addObject("diagdisp",diagdisp);
		model.addObject("diagbufferhide",diagbufferhidestr);
		model.setViewName("/forms/doctor/addprescriptions");
		
		return model;
    }
	
	@RequestMapping(value="/patientview")
	public ModelAndView gotoPatientview(@RequestParam(value="pid")String pid,HttpServletRequest request)
	{
		log.info("pid   "+pid);
		ModelAndView model = new ModelAndView();
		
		String patientquery = "SELECT `hp_id`,`hp_name`,COALESCE(hp_lastname,'')pat_lastname,DATE_FORMAT(`hp_dob`,'%d/%m/%Y')dob,`hp_gender`,`hp_martial`,`hp_mobno`,"
				+ "`hp_address`,`hp_city`,`hp_state`,`hp_pincode`,`hp_medical_comments`,`hp_email`,`hp_nominee_name`,"
				+ "ROUND(DATEDIFF(CURRENT_DATE, STR_TO_DATE(`hp_dob`, '%Y-%m-%d'))/365)age,`hp_created_date`,`hp_bloodgroup`,hp_seq_id,bc_id,`hp_imagepath`"
				+ " FROM `hero_admin_patient` where `hp_seq_id` = "+pid;
		int intpid = 0;
		if(pid != null)
		{
			intpid = Integer.parseInt(pid);
		}

		String firstname = "",lastname = "",dob = "",gender = "",martial = "",mobno = "",address = "",city = "", state = "",pincode = "",comments = "",email = "",
				nomineename = "",bloodgroup = "",pseqid = "0",clinicid= "",doctorfee = "",age = "",patientid = "";
		
	List<Object> patientList = clinicLOVobj.getPatientDetail(patientquery, intpid);
	if(patientList != null && patientList.size() > 0)
	{
		Map<String, Object> patientMap = (Map<String, Object>) patientList.get(0);
		
		patientid = (String) patientMap.get("patientid");
		firstname = (String) patientMap.get("firstname");
		lastname = (String) patientMap.get("lastname");
		dob = (String) patientMap.get("dob");
		gender = (String) patientMap.get("gender");
		
		martial = (String) patientMap.get("martial");
		mobno = (String) patientMap.get("mobno");
		address = (String) patientMap.get("address");
		city = (String) patientMap.get("city");
		state = (String) patientMap.get("state");
		
		pincode = (String) patientMap.get("zipcode");
		comments = (String) patientMap.get("medicalcomments");
		email = (String) patientMap.get("email");
		nomineename = (String) patientMap.get("nomineename");
		bloodgroup = (String) patientMap.get("bloodgroup");
		
		pseqid = (String) patientMap.get("pseqid");
		
		clinicid = (String) patientMap.get("clinicid");
		doctorfee = (String)patientMap.get("doctorfee");
		age = (String)patientMap.get("age");
	}
	
	String tobepaidamountQuery = "SELECT SUM(`bd_doctor_fee`)tobepaid FROM `hero_clinic_patient_prescriptions` A JOIN `hero_admin_physician_profile` B ON A.`bd_id` = B.`bd_user_id` "
			+ "WHERE `hp_id` = "+pid;
	String paidamountQuery = "SELECT SUM(`hpbp_pay_amount`)PAIDAMOUNT FROM `hero_clinic_patient_booking_payment` A JOIN `hero_clinic_patient_prescriptions` B ON "
			+ "A.`hpb_seq_id` = B.`hpb_seq_id` AND `hp_id` = "+pid;
	
	@SuppressWarnings("unchecked")
	List<Object> tobepaidList = jdbcTemplate.query(tobepaidamountQuery, new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {

			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("tobepaid", rs.getDouble("tobepaid"));
			
			return map;
		}
	});
	
	@SuppressWarnings("unchecked")
	List<Object> paidList = jdbcTemplate.query(paidamountQuery, new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int index) throws SQLException {

			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("paid", rs.getDouble("PAIDAMOUNT"));
			
			return map;
		}
	});
	
	Double tobepaidamount = 0.0,paidamount = 0.0,dueamount = 0.0;
	
	if(tobepaidList != null && tobepaidList.size() > 0)
	{
		Map<String, Object> tobepaidMap = (Map<String, Object>) tobepaidList.get(0);
		tobepaidamount = (Double) tobepaidMap.get("tobepaid");
	}
	
	if(paidList != null && paidList.size() > 0)
	{
		Map<String, Object> paidMap = (Map<String, Object>) paidList.get(0);
		paidamount = (Double) paidMap.get("paid");
	}
	
	if(paidamount > 0 && tobepaidamount > 0)
	{
		dueamount = tobepaidamount - paidamount;
	}
	
		model.addObject("tobepaid",tobepaidamount);
		model.addObject("paid",paidamount);
		model.addObject("dueamount",dueamount);
		
		model.addObject("firstname",firstname);
		model.addObject("lastname",lastname);
		model.addObject("dob",dob);
		model.addObject("gender",gender);
		model.addObject("martial",martial);
		
		model.addObject("mobno",mobno);
		model.addObject("email",email);
		model.addObject("address",address);
		model.addObject("city",city);
		model.addObject("state",state);
		
		model.addObject("pincode",pincode);
	
		model.setViewName("/forms/patient/patientview");
		return model;
	}
	
	@RequestMapping(value="/multiselect")
	public ModelAndView gotoMultiselect()
	{
		return new ModelAndView("/forms/poc/multiselect");
    }
	
}
