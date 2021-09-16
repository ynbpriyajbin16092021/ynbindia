package com.hero.forms.services.admin.masters.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_USERDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_IUSERDAO{
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_USERDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuserrole(String userroledata,String usertype) {
		// TODO Auto-generated method stub
		log.info("userroledata       "+userroledata);
		try
		{
		
		List<HERO_ADM_SERVC_USERREQUEST> userroleList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONArraytoList(userroledata,"com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_USERREQUEST");
		
		log.info("userroleList     "+userroleList);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Iterator<HERO_ADM_SERVC_USERREQUEST> roleItr = userroleList.iterator();
		
		while(roleItr.hasNext())
		{
			HERO_ADM_SERVC_USERREQUEST request = roleItr.next();
			log.info("Values are     "+request.getUsermenusno()+"   "+usertype+"   "+request.getUserid()+"   "+request.getModuleid()+"   "+request.getCreatedby()
					+"   "+request.getMenuaccess());	
			
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_USERROLE_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_USERMENU_SID", request.getUsermenusno());
			inParamMap.put("P_USERTYPE_ID", usertype);
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_MODULE_ID", request.getModuleid());
			inParamMap.put("P_MENU_ACCESS", request.getMenuaccess());
			inParamMap.put("P_CREATED_BY", request.getCreatedby());
			inParamMap.put("P_OPRN", "INS");
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
		}
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuser(String userdata) {
		// TODO Auto-generated method stub
		
		try
		{
			
		HERO_ADM_SERVC_USERREQUEST request = (HERO_ADM_SERVC_USERREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(userdata, "com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_USERREQUEST");
		log.info("Values are      "+request.getFirstname()+"   "+request.getLastname()+"   "+request.getUsertype()+"   "+request.getEmail()+"   "
				+request.getPassword()+"   "+request.getPhoneno()+"   "+request.getStatus()+"   "+request.getStoreid()+"   "+request.getUserid()
				
				+request.getEhrentryid()+"  "+request.getQualification()+"  "+request.getDob()
				+request.getDisplayingname()+""+request.getKnownlanguages()+""+request.getEmergencycontactnumber()+" "+request.getRelation()
				+request.getDoj()+"    "+request.getYearsinyoe()
				+request.getMonthsinyoe()+"  ");

		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_USER_MASTER");
		
		String invUserPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", request.getPassword());
		log.info("invUserPW         "+invUserPW);
		
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_USERID", request.getUserid());
		inParamMap.put("P_USERNAME", request.getUsername());
		inParamMap.put("P_FIRSTNAME", request.getFirstname());
		inParamMap.put("P_LASTNAME", request.getLastname());
		inParamMap.put("P_USERTYPE", request.getUsertype());
		inParamMap.put("P_PASSWORD", invUserPW);
		inParamMap.put("P_EMAIL_ID", request.getEmail());
		inParamMap.put("P_STORE_ID", request.getStoreid());
		inParamMap.put("P_PHONENO", request.getPhoneno());
		inParamMap.put("P_STATUS", request.getStatus());
		
		inParamMap.put("P_ADDRESS", request.getAddress());
		inParamMap.put("P_CITY", request.getCity());
		inParamMap.put("P_STATE", request.getState());
		inParamMap.put("P_COUNTRY", request.getCountry());
		inParamMap.put("P_PINCODE", request.getPincode());
		
		inParamMap.put("P_EHR_ENTRY_ID", request.getEhrentryid());
		inParamMap.put("P_QUALIFICATION", request.getQualification());

		inParamMap.put("P_DOB", HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate(request.getDob()) );
		inParamMap.put("P_DISPLAYING_NAME", request.getDisplayingname());
		
		inParamMap.put("P_KNOWN_LANGUAGE", request.getKnownlanguages());
		inParamMap.put("P_EMERGENCY_CONTACT_NO", request.getEmergencycontactnumber());
		inParamMap.put("P_RELATION", request.getRelation());

		inParamMap.put("P_DOJ", HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate(request.getDoj()) );
		

		inParamMap.put("P_YEARS_YEARSOFEXP", request.getYearsinyoe());
		inParamMap.put("P_MONTHS_YEARSOFEXP", request.getMonthsinyoe());

		inParamMap.put("P_GENDER", request.getGender());
		inParamMap.put("P_CLINIC_ID", request.getClinic());
		inParamMap.put("P_LAB_ID", request.getLab());
		
		inParamMap.put("P_CREATEDBY", request.getCreatedby());
		inParamMap.put("P_OPRN", request.getOprn());

		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveguestuser(String userdata) {
		// TODO Auto-generated method stub
		
		try
		{
			
		HERO_ADM_SERVC_USERREQUEST request = (HERO_ADM_SERVC_USERREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(userdata, "com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_USERREQUEST");
		log.info("Values are      "+request.getFirstname()+"   "+request.getLastname()+"   "+request.getUsertype()+"   "+request.getEmail()+"   "
				+request.getPassword()+"   "+request.getPhoneno()+"   "+request.getStatus()+"   "+request.getStoreid()+"   "+request.getUserid()
				
				+request.getEhrentryid()+"  "+request.getQualification()+"  "+request.getDob()
				+request.getDisplayingname()+""+request.getKnownlanguages()+""+request.getEmergencycontactnumber()+" "+request.getRelation()
				+request.getDoj()+"    "+request.getYearsinyoe()
				+request.getMonthsinyoe()+"  ");
		Date date = new Date();  
	    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
	    String todayDate= formatter.format(date);  
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_USER_MASTER");
		
		String invUserPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.encrypt("Herbzaliveerpapp", "ppapreevilazbreH", request.getPassword());
		log.info("invUserPW         "+invUserPW);
		
		String userCountQuery="SELECT (MAX(`userid`)+1)next_user_id FROM `hero_user`";
		List<Object> userCountList = inventoryLOVobj.getUserCountList(userCountQuery);
		Map<String, Object> map = (Map<String, Object>) userCountList.get(0);
		String dynamicEHR = "1000"+map.get("nextuserid");
		
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_USERID", request.getUserid());
		inParamMap.put("P_USERNAME", request.getEmail());
		inParamMap.put("P_FIRSTNAME", request.getFirstname());
		inParamMap.put("P_LASTNAME", request.getFirstname());
		inParamMap.put("P_USERTYPE", "9");
		inParamMap.put("P_PASSWORD", invUserPW);
		inParamMap.put("P_EMAIL_ID", request.getEmail());
		inParamMap.put("P_STORE_ID", "1");
		inParamMap.put("P_PHONENO", request.getPhoneno());
		inParamMap.put("P_STATUS", request.getStatus());
		
		inParamMap.put("P_ADDRESS", request.getAddress());
		inParamMap.put("P_CITY", request.getCity());
		inParamMap.put("P_STATE", request.getState());
		inParamMap.put("P_COUNTRY", request.getCountry());
		inParamMap.put("P_PINCODE", request.getPincode());
		
		inParamMap.put("P_EHR_ENTRY_ID", dynamicEHR);
		inParamMap.put("P_QUALIFICATION", "12");

		inParamMap.put("P_DOB", HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate( todayDate) );
		inParamMap.put("P_DISPLAYING_NAME", request.getFirstname());
		
		inParamMap.put("P_KNOWN_LANGUAGE", "English");
		inParamMap.put("P_EMERGENCY_CONTACT_NO", request.getEmergencycontactnumber());
		inParamMap.put("P_RELATION", request.getRelation());

		inParamMap.put("P_DOJ", HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertToSQLDate(todayDate));
		

		inParamMap.put("P_YEARS_YEARSOFEXP", "2");
		inParamMap.put("P_MONTHS_YEARSOFEXP", "2");

		inParamMap.put("P_GENDER", request.getGender());
		inParamMap.put("P_CLINIC_ID", request.getClinic());
		inParamMap.put("P_LAB_ID", request.getLab());
		
		inParamMap.put("P_CREATEDBY", request.getCreatedby());
		inParamMap.put("P_OPRN", request.getOprn());

		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		
		
		
		
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO userlist() {
		// TODO Auto-generated method stub
		
		String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id != '1'";
		
		@SuppressWarnings("unchecked")
		List<Object> usertypeApplnList = jdbcTemplate.query(usertypeApplnQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("applnid", rs.getInt("usertype_dept"));
				return map;
			}
		});
		
		String userQuery = "";
		if(usertypeApplnList!= null && usertypeApplnList.size()>0){
			int a[]=new int[usertypeApplnList.size()];
			for(int i = 0; i<usertypeApplnList.size(); i++){
				Map<String, Object> PatientsDetailsMap = (Map<String, Object>) usertypeApplnList.get(i);
				int applnid = (int) PatientsDetailsMap.get("applnid");
				
				a[i]=applnid;
			}
			boolean withpharmacy = HERO_ADM_SERVC_HOSURINVENTORYUTIL.contains(a, 1);
			if(withpharmacy){
				/*userQuery = "SELECT userid,`username`,`emailid`,a.`phone`,`user_store_id`,`status`,`store_name`,`usertype_name` "
						+ " FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` AND `role` != 1 "
						+ " LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role` UNION ALL  "
						+ " SELECT userid,`username`,`emailid`,a.`phone`,`user_clinic_id` user_store_id,`status`,`bc_name` store_name,`usertype_name`"
						+ "  FROM `hero_user` a LEFT JOIN `hero_admin_clinic` b ON a.`user_clinic_id` = b.`bc_id` AND `role` != 1 "
						+ "  LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role`";*/
				userQuery = "SELECT userid,`username`,`emailid`,a.`phone`,`user_store_id`,`status`,CONCAT(`store_name`,' - STORE') store_name,`usertype_name` "
						+ " FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` JOIN `hero_user_type` c ON c.`usertype_id` = a.`role`"
						+ " WHERE `usertype_dept` = 1"
						+ " UNION ALL "
						+ "SELECT userid,`username`,`emailid`,a.`phone`,`user_clinic_id` user_store_id,`status`,CONCAT(`bc_name`, ' - CLINIC') store_name,"
						+ "`usertype_name`  FROM `hero_user` a JOIN `hero_admin_clinic` b ON a.`user_clinic_id` = b.`bc_id`LEFT JOIN "
						+ "`hero_user_type` c ON c.`usertype_id` = a.`role` WHERE `usertype_dept` = 2"
						+ "  UNION ALL "
						+ "SELECT userid,`username`,`emailid`,a.`phone`,`hl_id` user_store_id, `status`,CONCAT(`hl_clinic_desc`, ' - LABORATORY') store_name,"
						+ "`usertype_name`  FROM `hero_user` a JOIN `hero_lab_regn` b ON a.`user_lab_id` = b.`hl_id`JOIN `hero_user_type` c ON "
						+ "c.`usertype_id` = a.`role` WHERE `usertype_dept` = 3 ";
			}else {
				userQuery = " SELECT userid,`username`,`emailid`,a.`phone`,`user_clinic_id` user_store_id,`status`,`bc_name` store_name, "
						+ " `usertype_name` FROM `hero_user` a LEFT JOIN `hero_admin_clinic` b ON a.`user_clinic_id` = b.`bc_id` AND `role` != 1  "
						+ " LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role`";
			}
		}
		
		
		
		
		/*String userQuery = "SELECT userid,`username`,`emailid`,a.`phone`,`user_store_id`,`status`,`store_name`,`usertype_name` "
				+ " FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` AND `role` != 1 "
				+ "LEFT JOIN `hero_user_type` c ON c.`usertype_id` = a.`role`"; */
log.info("userQuery"+userQuery);
		@SuppressWarnings("unchecked")
		List<Object> userList = jdbcTemplate.query(userQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("userid", rs.getInt("userid"));
				map.put("username", rs.getString("username"));
				map.put("email", rs.getString("emailid"));
				map.put("phoneno", rs.getString("phone"));
				map.put("storeid", rs.getString("user_store_id"));
				map.put("usertypedesc", rs.getString("usertype_name"));
				if(rs.getInt("status") == 1)
				{
					map.put("status", "Active");	
				}
				else
				{
					map.put("status", "In-Active");
				}
				map.put("storename", rs.getString("store_name"));
				map.put("action", "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button>");
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(userList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserDetail(final String userid,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		
		HttpSession session = httpRequest.getSession();
String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id != '1'";
		
		@SuppressWarnings("unchecked")
		List<Object> usertypeApplnList = jdbcTemplate.query(usertypeApplnQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("applnid", rs.getInt("usertype_dept"));
				return map;
			}
		});
		
		String userQuery = "";
		String usertypeforeditQuery ="SELECT COUNT(*)qty FROM `hero_user` WHERE   `user_clinic_id` IS NOT NULL AND `userid`="+userid;
		@SuppressWarnings("unchecked")
		List<Object> usertypeList = jdbcTemplate.query(usertypeforeditQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("qty", rs.getInt("qty"));
				return map;
			}
		});
		if(usertypeList!= null && usertypeList.size()>0){
			int a[]=new int[usertypeList.size()];
			for(int i = 0; i<usertypeList.size(); i++){
				Map<String, Object> PatientsDetailsMap = (Map<String, Object>) usertypeList.get(i);
				int qty = (int) PatientsDetailsMap.get("qty");
				
				a[i]=qty;
				
				if(qty == 1){
					userQuery = " SELECT userid,`username`,fname,lname,role,PASSWORD,`emailid`,user_clinic_id,`user_clinic_id` user_store_id,a.`phone`, "
							+ "`status`,`bc_name` store_name, `ehr_entry_id`,`qualification`,DATE_FORMAT(`dob`,'%d/%m/%Y') dob,`displaying_name`,"
							+ " `known_language`,`emergency_contact_no`, `relation`,DATE_FORMAT(`doj`,'%d/%m/%Y')doj,`years_yearsofexp`,"
							+ " `months_yearsofexp`,  `gender`,a.`address`,a.`city`,a.`state`,a.`country`,a.`pincode` FROM `hero_user` a JOIN `hero_admin_clinic` b ON a.`user_clinic_id` = b.`bc_id` "
							+ " AND userid = "+userid;	
					
				}else{
					userQuery = "SELECT userid,`username`,fname,lname,role,password,`emailid`,user_clinic_id,`user_store_id`,a.`phone`,`status`,`store_name`, "
							+ "`ehr_entry_id`,`qualification`,DATE_FORMAT(`dob`,'%d/%m/%Y') dob,`displaying_name`,`known_language`,`emergency_contact_no`, "
							+ "`relation`,DATE_FORMAT(`doj`,'%d/%m/%Y')doj,`years_yearsofexp`,`months_yearsofexp`, "
							+ " `gender`,a.`address`,a.`city`,a.`state`,a.`country`,a.`pincode` FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` AND userid = "+userid;
				}
			}
			
		}
		
		
		/*if(usertypeApplnList!= null && usertypeApplnList.size()>0){
			int a[]=new int[usertypeApplnList.size()];
			for(int i = 0; i<usertypeApplnList.size(); i++){
				Map<String, Object> PatientsDetailsMap = (Map<String, Object>) usertypeApplnList.get(i);
				int applnid = (int) PatientsDetailsMap.get("applnid");
				
				a[i]=applnid;
				
				
			}
			boolean withpharmacy = HERO_ADM_SERVC_HOSURINVENTORYUTIL.contains(a, 1);
		
			if(withpharmacy){
				
				userQuery = "SELECT userid,`username`,fname,lname,role,password,`emailid`,user_clinic_id,`user_store_id`,a.`phone`,`status`,`store_name`, "
						+ "`ehr_entry_id`,`qualification`,DATE_FORMAT(`dob`,'%d/%m/%Y') dob,`displaying_name`,`known_language`,`emergency_contact_no`, "
						+ "`relation`,DATE_FORMAT(`doj`,'%d/%m/%Y')doj,`years_yearsofexp`,`months_yearsofexp`, "
						+ " `gender` FROM `hero_user` a JOIN `hero_stock_store` b ON a.`user_store_id` = b.`store_id` AND userid = "+userid;

			}else {
				userQuery = " SELECT userid,`username`,fname,lname,role,PASSWORD,`emailid`,user_clinic_id,`user_clinic_id` user_store_id,a.`phone`, "
						+ "`status`,`bc_name` store_name, `ehr_entry_id`,`qualification`,DATE_FORMAT(`dob`,'%d/%m/%Y') dob,`displaying_name`,"
						+ " `known_language`,`emergency_contact_no`, `relation`,DATE_FORMAT(`doj`,'%d/%m/%Y')doj,`years_yearsofexp`,"
						+ " `months_yearsofexp`,  `gender` FROM `hero_user` a JOIN `hero_admin_clinic` b ON a.`user_clinic_id` = b.`bc_id` "
						+ " AND userid = "+userid;
			}
		}*/
		
		
		
				
		log.info("userQeruy   "+userQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> userList = jdbcTemplate.query(userQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				String invUserPW = "";
				
				if(rs.getString("password") != null)
				{
					invUserPW = HERO_ADM_SERVC_HOSURINVENTORYUTIL.decrypt("Herbzaliveerpapp", "ppapreevilazbreH", rs.getString("password"));
				}
				log.info("invUserPW      "+invUserPW);
				
				map.put("userid", rs.getInt("userid"));
				map.put("username", rs.getString("username"));
				map.put("firstname", rs.getString("fname"));
				map.put("lastname", rs.getString("lname"));
				map.put("usertype", rs.getString("role"));
				map.put("email", rs.getString("emailid"));
				map.put("storeid", rs.getString("user_store_id"));
				map.put("phoneno", rs.getString("phone"));
				map.put("status", rs.getInt("status"));
				map.put("password", invUserPW);
				map.put("storename", rs.getString("store_name"));
				map.put("clinic", rs.getString("user_clinic_id"));
				
				map.put("ehrentryid", rs.getString("ehr_entry_id"));
				map.put("qualification", rs.getString("qualification"));

				map.put("dob", rs.getString("dob"));
				map.put("displayingname", rs.getString("displaying_name"));
				
				map.put("knownlanguage", rs.getString("known_language"));
				map.put("emergencycontactno", rs.getString("emergency_contact_no"));
				map.put("relation", rs.getString("relation"));
				map.put("doj", rs.getString("doj"));
				
				map.put("yearsyearsofexp", rs.getString("years_yearsofexp"));
				map.put("monthsyearsofexp", rs.getString("months_yearsofexp"));
				map.put("gender", rs.getString("gender"));
				
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("pincode", rs.getString("pincode"));
				
				map.put("action", "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button>");
				
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(userList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savereportuserrole(
			String userroledata, String usertype) {
		// TODO Auto-generated method stub
		log.info("userroledata       "+userroledata);
		try
		{
		
		List<HERO_ADM_SERVC_USERREQUEST> userroleList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONArraytoList(userroledata,"com.hero.forms.services.admin.masters.user.HERO_ADM_SERVC_USERREQUEST");
		
		log.info("userroleList     "+userroleList);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		Iterator<HERO_ADM_SERVC_USERREQUEST> roleItr = userroleList.iterator();
		
		while(roleItr.hasNext())
		{
			HERO_ADM_SERVC_USERREQUEST request = roleItr.next();
			log.info("Values are     "+request.getUsermenusno()+"   "+usertype+"   "+request.getUserid()+"   "+request.getModuleid()+"   "+request.getCreatedby()
					+"   "+request.getMenuaccess());	
			
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_REPORTSUSERROLE_MASTER");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_USERMENU_SID", request.getUsermenusno());
			inParamMap.put("P_USERTYPE_ID", usertype);
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_MODULE_ID", request.getModuleid());
			inParamMap.put("P_MENU_ACCESS", request.getMenuaccess());
			inParamMap.put("P_CREATED_BY", request.getCreatedby());
			inParamMap.put("P_OPRN", "INS");
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
		}
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserLocationList(
			String usertypeid) {
		
		String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id = '"+usertypeid+"'";
		
		@SuppressWarnings("unchecked")
		List<Object> usertypeApplnList = jdbcTemplate.query(usertypeApplnQuery, new RowMapper() {
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("applnid", rs.getInt("usertype_dept"));
				return map;
			}
		});
		
		Map<String, Object> usertypeApplnMap = (Map<String, Object>) usertypeApplnList.get(0);
		
		int applnid = (int) usertypeApplnMap.get("applnid");
		String locationQuery = "";
		if(applnid == 1){
			locationQuery = "SELECT `store_id` value,`store_name` label, 0 starting_year FROM `hero_stock_store`";
			
		}else if(applnid == 3){
			locationQuery = "SELECT `hl_id` value,`hl_clinic_desc` label, 0 starting_year FROM `hero_lab_regn`";
		}else{
			locationQuery = "SELECT `bc_id` value ,`bc_name` label, COALESCE(`bc_start_year`,0) starting_year  FROM `hero_admin_clinic`";
		}
		
		List<Object> locationList = inventoryLOVobj.executeQuery(locationQuery);
		
	/*
			Map<String, Object> map = new HashMap<String, Object>();
		map.put("startingyear", rs.getString("bc_start_year"));*/
	
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(locationList);
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

}
