package com.hero.forms.services.stock.masters.customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSE;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

public class HERO_STK_SERVC_CUSTOMERDAOIMPL implements HERO_STK_SERVC_ICUSTOMERDAO{
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_CUSTOMERDAOIMPL.class);
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSEINFO inventoryResponseInfoOBJ;
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSE inventoryResponseOBJ;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;	

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomergroup(String customerData) {
		// TODO Auto-generated method stub
		
		try
		{
			HERO_STK_SERVC_CUSTOMERREQUEST request = (HERO_STK_SERVC_CUSTOMERREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(customerData, "com.hero.forms.services.stock.masters.customer.HERO_STK_SERVC_CUSTOMERREQUEST");
			log.info("Values are     "+request.getCustomergroupid()+"   "+request.getCustomergroupname()+"   "+request.getUserid()+"   "
					+request.getOprn());
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CUSTOMER_GROUP");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_CUSTOMER_GROUP_ID", request.getCustomergroupid());//0
			inParamMap.put("P_CUSTOMER_GROUP_NAME", request.getCustomergroupname());//1
			inParamMap.put("P_USER_ID", request.getUserid());//2
			inParamMap.put("P_OPRN", request.getOprn());//3
			
			log.info("inParamMap         "+inParamMap);
			
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
			
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomergroup() {String selectQuery = "SELECT * FROM hero_admin_customer_group";
	@SuppressWarnings("unchecked")
	List<Object> customerGroupList = jdbcTemplate.query(selectQuery, new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			int seqid = rs.getInt("seq_id");
			
			map.put("customergroupid", seqid);
			map.put("customergroupname", rs.getString("customer_group_name"));
			
			if(seqid > 2)
			{
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);	
			}
			else
			{
				map.put("action", "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\"> <i class=\"fa fa-trash-o\"></i> </button>");
			}
			
			
			return map;
		}
	});
	
	inventoryResponseOBJ.setResponseType("S");
	inventoryResponseOBJ.setResponseObj(customerGroupList);
	
	inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
	
	return inventoryResponseInfoOBJ;}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomers(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		/*String loadcustomersQuery = "SELECT cust_id,cust_code,`cust_firstname`,`cust_lastname`,CONCAT(`cust_firstname`,' ',`cust_lastname`)customername,"
				+ "`cust_email`,`cust_mobile_no`,`customer_group`,`customer_group_name` FROM `hero_admin_customer` a JOIN `hero_admin_customer_group` b "
				+ "ON a.`customer_group` = b.`seq_id`";*/
		StringBuilder customerSB = new StringBuilder();
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		if(usertype > 2)
		{
			customerSB.append("SELECT cust_id,cust_code,`cust_firstname`,`cust_lastname`,CONCAT(`cust_firstname`,' ',`cust_lastname`)customername,"
					+ "`cust_email`,`cust_mobile_no`,`cust_status`,`customer_group`,`customer_group_name` ,`store_name`,a.`created_by` "
					+ "FROM `hero_admin_customer` a JOIN `hero_admin_customer_group` b ON a.`customer_group` = b.`seq_id` "
					+ "LEFT JOIN `hero_user` c ON c.`userid` = a.`created_by` LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` "
					+ "WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		else
		{
			customerSB.append("SELECT cust_id,cust_code,`cust_firstname`,`cust_lastname`,CONCAT(`cust_firstname`,' ',`cust_lastname`)customername,"
					+ "`cust_email`,`cust_mobile_no`,`cust_status`,`customer_group`,`customer_group_name` FROM `hero_admin_customer` a JOIN `hero_admin_customer_group` b "
					+ "ON a.`customer_group` = b.`seq_id`");
		}
		String loadcustomersQuery = customerSB.toString();
		
		log.info("loadcustomers Query      "+loadcustomersQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(loadcustomersQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("customerid", rs.getString("cust_id"));
				map.put("customercode", rs.getString("cust_code"));
				map.put("customerfirstname", rs.getString("cust_firstname"));
				map.put("customerlastname", rs.getString("cust_lastname"));
				map.put("customername", rs.getString("customername"));
				map.put("customeremail", rs.getString("cust_email"));
				map.put("customermobileno", rs.getString("cust_mobile_no"));
				String statusDesign ="";
				if(rs.getString("cust_status").equals("active")){
					 statusDesign = "<span style='color:#319db5;font-weight:bold'>Active</span>";
				}else{
					 statusDesign = "<span style='color:#c75757;font-weight:bold'>In Active</span>";
				}
				map.put("customerstatus", statusDesign);
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("customercodelink", "<a href='customerview?customerid="+rs.getInt("cust_id")+"' style='color:blue;font-size:bold;'>"+rs.getString("customername")+"</a>");
				map.put("customergroup", rs.getString("customer_group_name"));
				
				return map;
			}
		});
		log.info("customerList   "+customerList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadaddcustomer(String customerid) {
		// TODO Auto-generated method stub
		String customerQuery = "SELECT cust_id,cust_code,`cust_firstname`,`cust_lastname`,CONCAT(`cust_firstname`,' ',`cust_lastname`)customername,"
				+ "`cust_email`,`cust_mobile_no`,`cust_status`,`customer_group`,`customer_group_name` FROM `hero_admin_customer` a JOIN `hero_admin_customer_group` b "
				+ "ON a.`customer_group` = b.`seq_id` where cust_id = "+customerid;
		log.info("customerQuery in loadaddcustomer   "+customerQuery);
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(customerQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("customerid", rs.getString("cust_id"));
				map.put("customercode", rs.getString("cust_code"));
				map.put("customerfirstname", rs.getString("cust_firstname"));
				map.put("customerlastname", rs.getString("cust_lastname"));
				map.put("customername", rs.getString("customername"));
				map.put("customeremail", rs.getString("cust_email"));
				map.put("customermobileno", rs.getString("cust_mobile_no"));
				map.put("customerstatus", rs.getString("cust_status"));
				map.put("action", HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("customercodelink", "<a href='#'>"+rs.getString("cust_code")+"</a>");
				map.put("customergroup", rs.getString("customer_group"));
				
				return map;
			}
		});
		
		
		String customerAddressQuery = "SELECT `ca_id`,`cust_id`,`street_address`,`city`,`state`,`country`,`zipcode` FROM `hero_admin_customer_address` WHERE `cust_id` = "
									  +customerid;
		
		@SuppressWarnings("unchecked")
		List<Object> customerAddressList = jdbcTemplate.query(customerAddressQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				
				map.put("customeraddressid", rs.getString("ca_id"));
				map.put("customerid", rs.getString("cust_id"));
				map.put("streetaddress", rs.getString("street_address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("country", rs.getString("country"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("streetaddressold", rs.getString("street_address"));
				map.put("cityold", rs.getString("city"));
				map.put("stateold", rs.getString("state"));
				map.put("countryold", rs.getString("country"));
				map.put("zipcodeold", rs.getString("zipcode"));
				map.put("oprn", "UPD");
				map.put("index", index);
				
				return map;
			}
		});
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("customerList", customerList);
		map.put("customerAddressList", customerAddressList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecustomer(String customerData) {
		// TODO Auto-generated method stub
		
		try
		{
			log.info("customerData      "+customerData);
			
			HERO_STK_SERVC_CUSTOMERREQUEST request = (HERO_STK_SERVC_CUSTOMERREQUEST)HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(customerData, "com.hero.forms.services.stock.masters.customer.HERO_STK_SERVC_CUSTOMERREQUEST");
			log.info("Values are     "+request.getCustomercode()+"   "+request.getCustomerfirstname()+"   "+request.getCustomergroupid()+"   "
					+"   "+request.getCustomerlastname()+"   "+request.getEmailid()+"   "+request.getMobileno()+"   "
					+request.getOprn()+"    "+request.getStatus());
			
			List<HERO_STK_SERVC_CUSTOMERADDRESSREQUEST> addressList = new ArrayList<HERO_STK_SERVC_CUSTOMERADDRESSREQUEST>(); 
					
			if(request.getOprn() != null && !request.getOprn().equals("DEL"))
			{
				addressList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONArraytoList(request.getAddresslist(),"com.hero.forms.services.stock.masters.customer.HERO_STK_SERVC_CUSTOMERADDRESSREQUEST");	
			}
			
			
			log.info(addressList);
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_CUSTOMER_MODULE");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_CUST_ID", request.getCustomerid());//0
			inParamMap.put("P_CUST_CODE", request.getCustomercode());//1
			inParamMap.put("P_FIRST_NAME", request.getCustomerfirstname());//2
			inParamMap.put("P_LAST_NAME", request.getCustomerlastname());//3
			inParamMap.put("P_CUST_EMAIL", request.getEmailid());//4
			inParamMap.put("P_CUST_MOBILE_NO", request.getMobileno());//5
			inParamMap.put("P_CUST_STATUS", request.getStatus());//6
			inParamMap.put("P_CUSTOMER_GROUP", request.getCustomergroupid());//7
			inParamMap.put("P_USER_ID", request.getUserid());//8
			inParamMap.put("P_OPRN", request.getOprn());//9
			
			log.info("inParamMap         "+inParamMap);
			
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
			
			String customerId = null;
			
			
			 
			customerId = (String)resultMap.get("out_genrate_id");	
			log.info("customerId       "+customerId);
			
			if(request.getOprn() != null && !request.getOprn().equals("DEL"))
			{
			Iterator<HERO_STK_SERVC_CUSTOMERADDRESSREQUEST> itr = addressList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_SERVC_CUSTOMERADDRESSREQUEST obj = itr.next();
				obj.setCustomerid(customerId);
				obj.setOprn(request.getOprn());
				
				addressList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info(obj.getCustomerid()+"   "+obj.getOprn());
			}	
		 
			savecustomeraddress(addressList);
			}
			
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void savecustomeraddress( final List<HERO_STK_SERVC_CUSTOMERADDRESSREQUEST> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_ADM_CUSTOMER_ADDRESS( ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_SERVC_CUSTOMERADDRESSREQUEST item = list.get(i);
				
				log.info("Item Values       "+item.getCity()+"   "+item.getCountry()+"   "+item.getCustomerid()+"   "+item.getIndex()+"   "+item.getOprn()
						+"   "+item.getState()+"   "+item.getStreetaddress()+"   "+item.getZipcode());
				
				ps.setString(1, item.getCaid());
				ps.setString(2, item.getCustomerid());
				ps.setString(3, item.getStreetaddress());
				ps.setString(4, item.getCity());
				ps.setString(5, item.getState());
				ps.setString(6, item.getCountry());
				ps.setString(7, item.getZipcode());
				ps.setString(8, item.getStreetaddressold());
				ps.setString(9, item.getCityold());
				ps.setString(10, item.getStateold());
				ps.setString(11, item.getCountryold());
				ps.setString(12, item.getZipcodeold());
				ps.setString(13, item.getOprn());
				
			}
		});
		}
		catch( Exception e )
		{
			log.error(e);
			throw e;
		}
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO customeroverviewDetails(String customerid) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		try
		{
		
		String overviewQuery = "SELECT `cust_id`,CONCAT(`cust_firstname`,' ',`cust_lastname`)CUSTNAME,cust_firstname,`cust_email`,`username`,cust_mobile_no "
		  		+ "FROM `hero_admin_customer` a JOIN `hero_user` b ON a.`created_by` = `userid` WHERE `cust_id` = "+customerid;
		
		log.info("overviewQuery   "+overviewQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> overViewList = jdbcTemplate.query(overviewQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("customerid", rs.getInt("cust_id"));
				  map.put("firstname", rs.getString("cust_firstname"));
				  map.put("customername", rs.getString("CUSTNAME"));
				  map.put("email", rs.getString("cust_email"));
				  map.put("createdby", rs.getString("username"));
				  map.put("mobileno", rs.getString("cust_mobile_no"));
				  
				  index++;
				  return map;
			}
		});
		map.put("overViewList", overViewList);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
		try
		{
		String addressQuery = "SELECT `street_address`,`city`,`state`,`country`,`zipcode` FROM `hero_admin_customer` A JOIN `hero_admin_customer_address` B "
				+ "ON A.`cust_id` = B.`cust_id` WHERE A.`cust_id` = "+customerid;
		
		log.info("addressQuery   "+addressQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> addressList = jdbcTemplate.query(addressQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("streetaddress", rs.getString("street_address"));
				  map.put("city", rs.getString("city"));
				  map.put("state", rs.getString("state"));
				  map.put("country", rs.getString("country"));
				  map.put("zipcode", rs.getString("zipcode"));
				  
				  index++;
				  return map;
			}
		});
		map.put("addressList", addressList);
		}
		catch(Exception e)
		{
			log.error(e);
		}

		try
		{
		String invoiceQuery = "SELECT format(SUM(`pos_netamount`),2)pos_netamount,COUNT(*) invoicecount,currency FROM `hero_stock_pos_summary` WHERE `cust_id` = "
		+customerid+" GROUP BY `cust_id` ";
		
		log.info("invoiceQuery   "+invoiceQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> invoiceList = jdbcTemplate.query(invoiceQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("totalamount", rs.getString("pos_netamount"));
				  map.put("invoicecount", rs.getString("invoicecount"));
				  map.put("currency", rs.getString("currency"));
				  
				  index++;
				  return map;
			}
		});
		map.put("invoiceList", invoiceList);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
		try{
			String paymentQuery = "SELECT `pos_code`,`pos_paid_amt`,`pos_grand_total`,`pos_discount`,`pos_netamount`"
					+",`currency`,`pos_tax_amount`,`pos_paid_amt`,"
					+"(`pos_balance_amount`*-1) `pos_balance_amount` FROM `hero_stock_pos_summary` WHERE `cust_id` = '"+customerid+"'";
			log.info("paymentQuery  "+paymentQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> paymentList = jdbcTemplate.query(paymentQuery, new RowMapper(){
				
				@Override
				public Object mapRow(ResultSet rs,int arg1)throws SQLException{
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("poscode", rs.getString("pos_code"));
					map.put("posdiscount", rs.getString("currency")+"."+rs.getString("pos_discount"));
					map.put("postaxamount", rs.getString("currency")+"."+rs.getString("pos_tax_amount"));
					map.put("posgrandtotal", rs.getString("currency")+"."+rs.getString("pos_grand_total"));
					map.put("pospaidamt", rs.getString("currency")+"."+rs.getString("pos_paid_amt"));
					map.put("posbalanceamount", rs.getString("currency")+"."+rs.getString("pos_balance_amount"));
					
					return map;
				}
			});
		map.put("paymentList", paymentList);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
		try
		{
		String orderQuery = "SELECT COUNT(A.`pos_id`) FROM `hero_stock_pos_summary` A JOIN `hero_stock_pos_order_items` B ON A.`pos_id` = B.`pos_id` WHERE `cust_id` = "+customerid
				+ " AND `pos_order_status_id` = 5 GROUP BY B.`pos_id`";
		
		log.info("orderQuery   "+orderQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> orderList = jdbcTemplate.query(orderQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  
				  
				  index++;
				  return map;
			}
		});
		
		map.put("orderListSize", orderList.size());
		}
		catch(Exception e)
		{
			log.error(e);
		}

		try
		{
		String salesQuery = "SELECT `pos_code`,DATE_FORMAT(`created_at`,'%e/%c/%Y')salesat,FORMAT(`pos_netamount`,2)pos_netamount,"
				+ "FORMAT((`pos_paid_amt` - `pos_netamount`),2)balance_amt,`pos_status`,coalesce(`currency`,'')currency,`pos_status_desc`,`pos_paid_amt` "
				+ "FROM `hero_stock_pos_summary` LEFT JOIN `hero_stock_pos_status` ON `pos_status` = `pos_status_id` WHERE `cust_id` = "+customerid
				+ " AND ((`pos_order_code` = '') OR (`pos_order_code` != '' AND `pos_order_status_id` = 6))";
		
		log.info("salesQuery   "+salesQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> salesList = jdbcTemplate.query(salesQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("invoicecode", rs.getString("pos_code"));
				  map.put("salesat", rs.getString("salesat"));
				  map.put("totalamount", rs.getString("currency")+" "+rs.getString("pos_netamount"));
				  map.put("balanceamount", rs.getString("currency")+" "+rs.getString("balance_amt"));
				  map.put("status", rs.getString("pos_status_desc"));
				  map.put("currency", rs.getString("currency"));
				  map.put("type", "Receipt");
				  
				  index++;
				  return map;
			}
		});
		log.info("salesList   "+salesList);
		map.put("salesList", salesList);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
		try
		{
		String customerOrdersQuery = "SELECT A.`pos_id`,`product_name`,`poo_batch_id`,`poo_sales_count`,`poo_sales_price`,`pos_tax_amount`,`pos_netamount`,`currency`,"
				+ "`pos_status_desc` FROM `hero_stock_pos_summary` A JOIN `hero_stock_pos_order_items` B ON A.`pos_id` = B.`pos_id` "
				+ "JOIN `hero_stock_product` C ON B.`poo_prod_id` = C.`product_id` LEFT JOIN `hero_stock_pos_status` D ON A.`pos_order_status_id` = D.`pos_status_id`"
				+ " WHERE `cust_id` = "+customerid;
		
		log.info("customerOrdersQuery   "+customerOrdersQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> customersOrderList = jdbcTemplate.query(customerOrdersQuery, new RowMapper() {
			int index=0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {

				  Map<String, Object> map = new HashMap<String, Object>();
				  
				  map.put("index", index);
				  map.put("productname", rs.getString("product_name"));
				  map.put("batchid", rs.getString("poo_batch_id"));
				  map.put("totalamount", rs.getString("currency")+" "+rs.getString("pos_netamount"));
				  map.put("taxamount", rs.getString("currency")+" "+rs.getString("pos_tax_amount"));
				  map.put("status", rs.getString("pos_status_desc"));
				  map.put("currency", rs.getString("currency"));
				  map.put("unitprice", rs.getString("poo_sales_price"));
				  map.put("salescount", rs.getString("poo_sales_count"));
				  
				  index++;
				  return map;
			}
		});
		log.info("customersOrderList   "+customersOrderList);
		map.put("customersOrderList", customersOrderList);
		}
		catch(Exception e)
		{
			log.error(e);
		}
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewpagewise(String pageno,String status,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		
		int intPageno = 0,start = 0,end = 0;
		if(pageno != null)
		{
			intPageno = Integer.parseInt(pageno);
		}
		start = HERO_ADM_SERVC_HOSURINVENTORYUTIL.startIndex(intPageno);
		end = HERO_ADM_SERVC_HOSURINVENTORYUTIL.endIndex(intPageno);
		
		StringBuilder customerQuerySB = new StringBuilder();
		customerQuerySB.append("SELECT `cust_id`,CONCAT(`cust_firstname`,`cust_lastname`)CUSTNAME FROM `hero_admin_customer` a ");
		
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		if(usertype > 2)
		{
			customerQuerySB.append("LEFT JOIN `hero_user` c ON c.`userid` = a.`created_by` LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` "
					+ "WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		
		if(status.equals("active")){
			customerQuerySB.append(" WHERE `cust_status` =  '"+status+"'");	
		}else if(status.equals("inactive")){
			customerQuerySB.append(" WHERE `cust_status` =  '"+status+"'");	
		}else{
			
		}
		
		if(Integer.parseInt(pageno) > 1)
		{
			customerQuerySB.append(" LIMIT "+start+","+start);	
		}
		else
		{
			customerQuerySB.append(" LIMIT "+start+","+end);
		}
		
		String customerQuery = customerQuerySB.toString();
		log.info("customerQuery   "+customerQuery);
		
		List<Object> customerList = inventoryLOVobj.getLOVList(customerQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcustomerviewstatuswise(String status,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
			
		StringBuilder customerQuerySB = new StringBuilder();
		customerQuerySB.append("SELECT `cust_id`,CONCAT(`cust_firstname`,`cust_lastname`)CUSTNAME FROM `hero_admin_customer` a ");
		
		int usertype = (int)httpRequest.getSession().getAttribute("usertype");
		if(usertype > 2)
		{
			customerQuerySB.append("LEFT JOIN `hero_user` c ON c.`userid` = a.`created_by` LEFT JOIN `hero_stock_store` d ON d.`store_id` = c.`user_store_id` "
					+ "WHERE `store_id` = "+httpRequest.getSession().getAttribute("storeid"));
		}
		if(status.equals("active")){
			customerQuerySB.append(" WHERE `cust_status` =  '"+status+"'");	
		}else if(status.equals("inactive")){
			customerQuerySB.append(" WHERE `cust_status` =  '"+status+"'");	
		}else{
			
		}
		customerQuerySB.append(" LIMIT 0,10");
		String customerStatusQuery = customerQuerySB.toString();
		log.info("customerStatusQuery   "+customerStatusQuery);
		
		List<Object> customerStatusList = inventoryLOVobj.getLOVList(customerStatusQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerStatusList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
}
