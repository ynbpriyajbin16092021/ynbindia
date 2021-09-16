package com.hero.services.admin.lov;

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

import com.hero.services.admin.util.ClinicDAO;
import com.hero.services.admin.util.ClinicLOV;
import com.hero.services.admin.util.HerbzClinicUtil;

public class ClinicLOVImpl extends ClinicDAO implements IClinicLOV {

	private static Logger log = Logger.getLogger(ClinicLOVImpl.class);
	@Override
	public List<Object> getLOVList(String query) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> LOVList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				  ClinicLOV lov = new ClinicLOV();
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
		ClinicLOV lov = new ClinicLOV();
		lov.setLabel("--Select--");
		lov.setValue("0");
		LOVList.add(lov);
		return LOVList;
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
		
		String productCountQuery = "SELECT COUNT(*) FROM `hero_stock_product` WHERE `status_id` = 1";
		/*String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier` WHERE `status_id` = 1";*/
		String supplierCountQuery = "SELECT COUNT(*) FROM `hero_stock_supplier`";
		String storeCountQuery = "SELECT COUNT(*) FROM `hero_stock_store` WHERE `status_id` = 1";
		String userCountQuery = "SELECT COUNT(*) FROM `hero_user` WHERE `status` = 1";
		String customerCountQuery = "SELECT COUNT(*) FROM `hero_stock_customer`";
		String purchaseAmountQuery = "SELECT SUM(`prhdr_grand_total_amt`) FROM `hero_stock_purchase_receive_hdr` WHERE `prhdr_created_date` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND CURRENT_DATE";
		String salesAmountQuery = "SELECT SUM(`pos_netamount`) FROM `hero_stock_pos_summary` WHERE `created_at` BETWEEN "
				+ "DATE_SUB(CURRENT_DATE, INTERVAL DAYOFMONTH(CURRENT_DATE)-1 DAY) AND CURRENT_DATE";
		String recentBillsQuery = "SELECT (`purchase_code`) as purchasecode,CONCAT(`supplier_first_name`,`supplier_last_name`)suppliername,"
				+ "SUM(`prhdr_grand_total_amt`)totalamount,DATE_FORMAT(`prhdr_created_date`,'%c/%e/%y')purchaseddate,`ps_status_name` as statusdesc "
				+ "FROM `hero_stock_purchase` a JOIN `hero_stock_supplier` b ON a.`supplier_id` = b.`supplier_id` "
				+ "JOIN `hero_stock_purchase_receive_hdr` c ON a.`purchase_code` = c.`pur_req_id` "
				+ "JOIN  `hero_stock_purchase_status` d ON d.`ps_id` = c.`prhdr_paid_status` GROUP BY `purchase_code` ORDER BY `purchase_id` DESC LIMIT 0,10";
		
		StringBuilder ordersQuerySB = new StringBuilder();
		ordersQuerySB.append("SELECT `poo_prod_id`,`product_name`,SUM(`poo_sales_count`)ordercount,CONCAT(`unit_quantity`,' ',`unit`)uom,`company_name` "
				+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_order_items` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`poo_prod_id` = c.`product_id` "
				+ "LEFT JOIN `hero_unit_type` d ON c.`unit_type_id` = d.`unit_type_id` LEFT JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` "
				+ "WHERE `pos_order_status_id` = 5 ");
		/*if(usertype != null && usertype.equals("3"))*/
		if(usertype > 2)
		{
			ordersQuerySB.append("and `pos_store_id` = "+session.getAttribute("storeid"));
		}
		ordersQuerySB.append("  GROUP BY `poo_prod_id`");
		 
		String topSellingProductQuery = "SELECT `pos_prod_id`,`product_name`,SUM(`pos_sales_count`)salescount,`company_name` "
				+ "FROM `hero_stock_pos_summary` a JOIN `hero_stock_pos_prod_details` b ON a.`pos_id` = b.`pos_id` JOIN `hero_stock_product` c ON b.`pos_prod_id` = c.`product_id` "
				+ "LEFT JOIN `hero_admin_company` e ON e.`company_id` = c.`manufacturer_id` GROUP BY `pos_prod_id` LIMIT 0,10";
		

		
		log.info("recentBillsQuery   "+recentBillsQuery);
		log.info("ordersQuerySB   "+ordersQuerySB.toString());
		log.info("topSellingProductQuery   "+topSellingProductQuery);

		
		Map<String, Object> map = new HashMap<String, Object>();
		
		List<Object> productCountList = (List<Object>) HerbzClinicUtil.executeQuery(productCountQuery, jdbcTemplate);
		List<Object> supplierCountList = (List<Object>) HerbzClinicUtil.executeQuery(supplierCountQuery, jdbcTemplate);
		List<Object> storeCountList = (List<Object>) HerbzClinicUtil.executeQuery(storeCountQuery, jdbcTemplate);
		List<Object> userCountList = (List<Object>) HerbzClinicUtil.executeQuery(userCountQuery, jdbcTemplate);
		List<Object> customerCountList = (List<Object>) HerbzClinicUtil.executeQuery(customerCountQuery, jdbcTemplate);
		List<Object> purchaseAmountList = (List<Object>) HerbzClinicUtil.executeQuery(purchaseAmountQuery, jdbcTemplate);
		List<Object> salesAmountList = (List<Object>) HerbzClinicUtil.executeQuery(salesAmountQuery, jdbcTemplate);
		List<Object> recentBillsList = (List<Object>) HerbzClinicUtil.executeQueryWithList(recentBillsQuery, jdbcTemplate);
		List<Object> ordersList = (List<Object>) HerbzClinicUtil.executeQueryWithList(ordersQuerySB.toString(), jdbcTemplate);
		List<Object> topSellingProductList = (List<Object>) HerbzClinicUtil.executeQueryWithList(topSellingProductQuery, jdbcTemplate);

		
		log.info("recentBillsList   "+recentBillsList);
		
		map.put("productCount", productCountList);
		map.put("supplierCount", supplierCountList);
		map.put("storeCount", storeCountList);
		map.put("userCount", userCountList);
		map.put("customerCount", customerCountList);
		map.put("purchaseAmount", purchaseAmountList);
		map.put("salesAmount", salesAmountList);
		map.put("recentBills", recentBillsList);
		map.put("ordersList", ordersList);
		map.put("topSellingProductList", topSellingProductList);

		
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
				map.put("action", HerbzClinicUtil.DATATABLE_ACTION);
				
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
		
		String timeQuery = "SELECT `hpb_time` FROM `hero_clinic_patient_booking` WHERE `hpb_date` = '"+HerbzClinicUtil.convertToSQLDate(bookingDate)+"' and `bc_id` =  "+clinicId
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
		
		String displayTime = HerbzClinicUtil.AMPM_HourFormat(clinicTime);
		
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
					long diffWithCurrettime = HerbzClinicUtil.timedifference(clinicEndTime, clinicTime);
					
					
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
					
					displayTime = HerbzClinicUtil.AMPM_HourFormat(clinicTime);
					
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
