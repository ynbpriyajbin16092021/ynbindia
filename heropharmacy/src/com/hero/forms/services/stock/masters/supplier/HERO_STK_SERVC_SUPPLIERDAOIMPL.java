package com.hero.forms.services.stock.masters.supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;

public class HERO_STK_SERVC_SUPPLIERDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_SERVC_ISUPPLIERDAO {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_SUPPLIERDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_STK_RESPONSEINFO savesupplier(String supplierData) {
		// TODO Auto-generated method stub
		try
		{
			HERO_STK_SERVC_SUPPLIERREQUEST request = (HERO_STK_SERVC_SUPPLIERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(supplierData, "com.hero.forms.services.stock.masters.supplier.HERO_STK_SERVC_SUPPLIERREQUEST");
		
		log.info("Values are     "+request.getAddress()+"   "+request.getCity()+"   "+request.getSupplierinis()+"   "+request.getCountryid()+"   "
		+request.getEmailid()+"   "+request.getMobile()+"   "+request.getState()+"   "+request.getStatusid()+"   "+request.getSupplierid()+"   "+request.getSupplierfirstname()+"   "
		+request.getSuppliertypeid()+"   "+request.getTelephoneno()+"   "+request.getUserid()+"   "+request.getZipcode()+"   "+request.getSupplierlastname());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_SUPPLIER_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_SUPPLIER_ID", request.getSupplierid());//0
		inParamMap.put("P_SUPPLIER_FIRST_NAME", request.getSupplierfirstname());//1
		inParamMap.put("P_SUPPLIER_LAST_NAME", request.getSupplierlastname());//2
		inParamMap.put("P_SUPPLIER_TYPE_ID", request.getSuppliertypeid());//3
		inParamMap.put("P_SUPPLIER_INIS", request.getSupplierinis());//4
		inParamMap.put("P_ADDRESS", request.getAddress());//5
		inParamMap.put("P_CITY", request.getCity());//6
		inParamMap.put("P_STATE", request.getState());//7
		inParamMap.put("P_ZIP_CODE", request.getZipcode());//8
		inParamMap.put("P_COUNTRY_ID", request.getCountryid());//9
		inParamMap.put("P_MOBILE", request.getMobile());//10
		inParamMap.put("P_TELEPHONE", request.getTelephoneno());//11
		inParamMap.put("P_EMAIL_ID", request.getEmailid());//12
		inParamMap.put("P_STATUS_ID", request.getStatusid());//13
		inParamMap.put("P_USER_ID", request.getUserid());//14
		inParamMap.put("P_TIN_NUMBER", request.getTinnumber());//15
		inParamMap.put("P_OPRN", request.getOprn());//16
		inParamMap.put("P_REQDAYS", request.getRequireddays());//17
		inParamMap.put("P_PAYMODE", request.getPaymentmode());//18
		inParamMap.put("P_OPTTYPE", request.getOpttype());//18
		inParamMap.put("P_CREDIT_MODE", request.getCreditmode());//19
		inParamMap.put("P_TERMS", request.getTerms());//19
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			error_log.info(e);
			e.printStackTrace();
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO supplierlist() {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM hero_stock_supplier a LEFT JOIN hero_stock_supplier_type b ON a.supplier_type_id = b.supplier_type_id ORDER BY supplier_id DESC";
		@SuppressWarnings("unchecked")
		List<Object> productList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				String supplierfirstname = rs.getString("supplier_first_name") == null ? "":rs.getString("supplier_first_name");
				String supplierlastname = rs.getString("supplier_last_name") == null ? "":rs.getString("supplier_last_name");
				String suppliername = supplierfirstname.concat(" "+supplierlastname);
				
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("supplierfirstname", rs.getString("supplier_first_name"));
				map.put("supplierlastname", rs.getString("supplier_last_name"));
				map.put("suppliertypeid", rs.getString("supplier_type_id"));
				map.put("supplierinis", rs.getString("supplier_inis"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("countryid", rs.getString("country_id"));
				map.put("mobile", rs.getString("mobile"));
				map.put("telephoneno", rs.getString("telephone"));
				map.put("emailid", rs.getString("email_id"));
				map.put("statusid", rs.getString("status_id"));
				map.put("userid", rs.getString("user_id"));
				map.put("tinnumber", rs.getString("tin_number"));
				map.put("suppliertypedesc", rs.getString("supplier_type"));
				map.put("suppliername", suppliername);
			/*	map.put("suppliernamehref", "<a href='supplierview?supplier="+rs.getString("supplier_id")+"'>"+suppliername+"</a>");*/
				map.put("suppliernamehref",suppliername);
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("reqdays", rs.getString("reqdays"));
				map.put("paymode", rs.getString("paymode"));
				map.put("opttype", rs.getString("opttype"));
				map.put("creditmode", rs.getString("credit_mode"));
				map.put("terms", rs.getString("terms"));
				return map;
			}
		});
		log.info(productList);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", HosurInventoryUtil.returnJSONobject(productList));
		log.info(new Gson().toJson(map));*/
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(productList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getsupplierinfo(String supplierid) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM hero_stock_supplier where supplier_id="+supplierid;
		@SuppressWarnings("unchecked")
		List<Object> supplierList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("supplierfirstname", rs.getString("supplier_first_name"));
				map.put("supplierlastname", rs.getString("supplier_last_name"));
				map.put("suppliertypeid", rs.getString("supplier_type_id"));
				map.put("supplierinis", rs.getString("supplier_inis"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("countryid", rs.getString("country_id"));
				map.put("mobile", rs.getString("mobile"));
				map.put("telephoneno", rs.getString("telephone"));
				map.put("emailid", rs.getString("email_id"));
				map.put("statusid", rs.getString("status_id"));
				map.put("userid", rs.getString("user_id"));
				map.put("tinnumber", rs.getString("tin_number"));
				map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);
				map.put("reqdays", rs.getString("reqdays"));
				map.put("paymode", rs.getString("paymode"));
				map.put("opttype", rs.getString("opttype"));
				return map;
			}
		});
		log.info(supplierList);
		/*Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", HosurInventoryUtil.returnJSONobject(productList));
		log.info(new Gson().toJson(map));*/
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(supplierList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO getsupplierviewinfo(String supplierid,String supplierstatusid) {
		// TODO Auto-generated method stub
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		try
		{
			String query = "SELECT `supplier_id`,(`supplier_first_name`)suppliername,`mobile`,`ct_name`paymode,`email_id`,`address`,`city`,`state`,`zipcode`,`country_id` "
					+ "FROM `hero_stock_supplier` a JOIN `hero_admin_cash_type` b ON a.`paymode` = b.`ct_id` WHERE `supplier_id` ="+supplierid;
			
			@SuppressWarnings("unchecked")
			List<Object> supplierList = jdbcTemplate.query(query, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("supplierid", rs.getString("supplier_id"));
					map.put("suppliername", rs.getString("suppliername"));
					map.put("mobile", rs.getString("mobile"));
					map.put("emailid", rs.getString("email_id"));
					map.put("address", rs.getString("address"));
					map.put("city", rs.getString("city"));
					map.put("state", rs.getString("state"));
					map.put("zipcode", rs.getString("zipcode"));
					map.put("countryid", rs.getString("country_id"));
					map.put("paymode", rs.getString("paymode"));
					
					return map;
				}
			});
			log.info(supplierList);
			log.info(query);
			
			map.put("supplierlist", supplierList);
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		
		try
		{
			String purchasedquery = "SELECT `supplier_id`,(`supplier_first_name`)suppliername,`mobile`,`ct_name`paymode,`email_id`,`address`,`city`,"
		    + "`state`,`zipcode`,`country_id`,(SELECT SUM(`prec_recving_quantity`) FROM `hero_stock_purchase` a "
		    + "JOIN `hero_stock_supplier` b ON a.`supplier_id` = b.`supplier_id` JOIN `hero_stock_purchase_receive_hdr`c ON c.`pur_req_id` = a.`purchase_code` "
		    + "JOIN `hero_stock_purchase_received_dtl` d ON d.`prec_hdr_id` = c.`prhdr_id` 	WHERE a.`supplier_id` = "+supplierid+")rec,	"
		    + "(SELECT SUM(`pur_quantity`) FROM `hero_stock_purchase` a JOIN `hero_stock_supplier` b ON a.`supplier_id` = b.`supplier_id`	"
		    + "JOIN `hero_stock_purchase_request` c ON c.`pur_req_id` = a.`purchase_code`WHERE a.`supplier_id` = "+supplierid+")ordered,"
		    + "(SELECT COALESCE((SUM(`prhdr_grand_total_amt`) - SUM(COALESCE(`prhdr_paid_amount`,0))),0)  FROM `hero_stock_purchase` a "
		    + " JOIN `hero_stock_purchase_receive_hdr` b ON b.`pur_req_id` = a.`purchase_code` JOIN `hero_stock_supplier` c ON a.`supplier_id` = c.`supplier_id` "
		    + "WHERE a.`supplier_id` = "+supplierid+")balance FROM `hero_stock_supplier` a JOIN `hero_admin_cash_type` b ON a.`paymode` = b.`ct_id` "
		    + "WHERE `supplier_id` ="+supplierid;
			
			log.info(purchasedquery);
			
			@SuppressWarnings("unchecked")
			List<Object> purchasedList = jdbcTemplate.query(purchasedquery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("supplierid", rs.getString("supplier_id"));
					map.put("suppliername", rs.getString("suppliername"));
					map.put("mobile", rs.getString("mobile"));
					map.put("emailid", rs.getString("email_id"));
					map.put("address", rs.getString("address"));
					map.put("city", rs.getString("city"));
					map.put("state", rs.getString("state"));
					map.put("zipcode", rs.getString("zipcode"));
					map.put("countryid", rs.getString("country_id"));
					map.put("paymode", rs.getString("paymode"));
					map.put("orderedqty", rs.getString("ordered"));
					map.put("receivedqty", rs.getString("rec"));
					map.put("balance", rs.getString("balance"));
					
					return map;
				}
			});
			log.info(purchasedList);
			log.info(purchasedquery);
			
			map.put("purchasedList", purchasedList);
		}
		
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DATE_FORMAT(`purchase_date`,'%e/%c/%y')purchase_date,`purchase_code`,(`pur_quantity`)pur_quantity,`ps_status_name`,"
					+ "CONCAT('Rs. ',FORMAT(`prhdr_grand_total_amt`,2))prhdr_total_with_disc,"
					+ "(SELECT `ps_status_name` FROM `hero_stock_purchase_status` F WHERE F.`ps_id` = C.`prhdr_paid_status`)prhdr_paid_status,"
					+ "CONCAT('Rs. ',FORMAT((`prhdr_grand_total_amt` - COALESCE(`prhdr_paid_amount`,0)),2)) BALANCE "
					+ "FROM `hero_stock_purchase` A JOIN `hero_stock_purchase_request` B ON A.`purchase_code` = B.`pur_req_id` "
					+ "JOIN `hero_stock_purchase_receive_hdr` C ON B.`pur_req_id` = C.`pur_req_id` AND A.`purchase_code` = C.`pur_req_id` "
					+ "JOIN `hero_stock_purchase_received_dtl` D ON C.`prhdr_id` = D.`prec_hdr_id` AND B.`pur_product_id` = D.`prec_product_id` "
					+ "JOIN `hero_stock_purchase_status` E ON E.`ps_id` = A.`receive_status` "
					+ "WHERE `supplier_id` ="+supplierid+"  ");
			
			if(supplierstatusid != null && !supplierstatusid.equals("0"))
			{
				sb.append(" AND `receive_status` = "+supplierstatusid);
			}
			sb.append( "  GROUP BY prhdr_id");
			
			String purchasequery = sb.toString();
			
			log.info("purchasequery2     "+purchasequery);
			
			@SuppressWarnings("unchecked")
			List<Object> purchaseList = jdbcTemplate.query(purchasequery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("purchasedate", rs.getString("purchase_date"));
					map.put("purchasecode", rs.getString("purchase_code"));
					map.put("purchaseqty", rs.getString("pur_quantity"));
					map.put("status", rs.getString("ps_status_name"));
					map.put("totalamt", rs.getString("prhdr_total_with_disc"));
					map.put("paidstatus", rs.getString("prhdr_paid_status"));
					map.put("balance", rs.getString("BALANCE"));
					
					return map;
				}
			});
			log.info(purchaseList);
			
			map.put("purchaseList", purchaseList);
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT DATE_FORMAT(`pp_payment_time`,'%e/%c/%y')pp_payment_time,`prhdr_bill_no`,`purchase_refer_no`,"
					+ "CONCAT('Rs. ',FORMAT(`prhdr_grand_total_amt`,2))prhdr_grand_total_amt,`pp_pay_by`,`ct_name` "
					+ "FROM `hero_stock_purchase` A JOIN `hero_stock_purchase_receive_hdr` B ON A.`purchase_code` = B.`pur_req_id` "
					+ "JOIN `hero_stock_purchase_payment` C ON C.`prhdr_id` = B.`prhdr_id` JOIN `hero_admin_cash_type` D ON D.`ct_id` = C.`pp_payment_type` "
					+ "WHERE `supplier_id` = "+supplierid);
			
			String purchasequery = sb.toString();
			
			log.info("purchasequery     "+purchasequery);
			
			@SuppressWarnings("unchecked")
			List<Object> paymenttypeList = jdbcTemplate.query(purchasequery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("paymentdate", rs.getString("pp_payment_time"));
					map.put("billno", rs.getString("prhdr_bill_no"));
					map.put("refno", rs.getString("purchase_refer_no"));
					map.put("totalamount", rs.getString("prhdr_grand_total_amt"));
					map.put("receivedby", rs.getString("pp_pay_by"));
					map.put("paymenttype", rs.getString("ct_name"));
					
					return map;
				}
			});
			log.info(paymenttypeList);
			
			map.put("paymenttypeList", paymenttypeList);
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		try
		{
			StringBuilder sb = new StringBuilder();
			sb.append("SELECT `isc_id`,`supplier_id`,`isc_prefix`,`isc_contact_name`,`isc_email`,`isc_phoneno`,`isc_designation` FROM " 
					+"`hero_stock_supplier_contact` WHERE `supplier_id` = "+supplierid);
			
			String contactlistquery = sb.toString();
			
			log.info("contactlistquery    "+contactlistquery);
			
			@SuppressWarnings("unchecked")
			List<Object> contactList = jdbcTemplate.query(contactlistquery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("iscid", rs.getString("isc_id"));
					map.put("supplierid", rs.getString("supplier_id"));
					map.put("iscprefix", rs.getString("isc_prefix"));
					map.put("isccontactname", rs.getString("isc_contact_name"));
					map.put("iscemail", rs.getString("isc_email"));
					map.put("iscphoneno", rs.getString("isc_phoneno"));
					map.put("iscdesignation", rs.getString("isc_designation"));
					
					return map;
				}
			});
			log.info(contactList);
			
			map.put("contactList", contactList);
		}
		catch(Exception e)
		{
			error_log.info(e);
		}
		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO savesuppliercontact(String supplierContactData) {
		// TODO Auto-generated method stub
		try
		{

			HERO_STK_SERVC_SUPPLIERCONTACTREQUEST request = (HERO_STK_SERVC_SUPPLIERCONTACTREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(supplierContactData, 
					"com.hero.forms.services.stock.masters.supplier.HERO_STK_SERVC_SUPPLIERCONTACTREQUEST");
		
		log.info("Values are     "+request.getContactname()+"   "+request.getContactphone()+"   "+request.getDesignation()+"   "+request.getEmail()+"   "
		+request.getOprn()+"   "+request.getPrefix()+"   "+request.getUserid());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_SUPPIER_CONTACT");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_ISC_ID", request.getIscid());//0
		inParamMap.put("P_SUPPLIER_ID", request.getSupplierid());//1
		inParamMap.put("P_ISC_PREFIX", request.getPrefix());//2
		inParamMap.put("P_CONTACT_NAME", request.getContactname());//3
		inParamMap.put("P_EMAIL", request.getEmail());//4
		inParamMap.put("P_PHONENO", request.getContactphone());//5
		inParamMap.put("P_DESIGNATION", request.getDesignation());//6
		inParamMap.put("P_USERID", request.getUserid());//7
		inParamMap.put("P_OPRN", request.getOprn());//8
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

}
