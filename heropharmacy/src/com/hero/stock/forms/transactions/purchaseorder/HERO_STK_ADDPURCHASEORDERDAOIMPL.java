package com.hero.stock.forms.transactions.purchaseorder;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_STK_ADDPURCHASEORDERDAOIMPL implements HERO_STK_IADDPURCHASEORDERDAO{
	private static Logger log = Logger.getLogger(HERO_STK_ADDPURCHASEORDERDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	
	@Autowired
	HERO_STK_RESPONSE inventoryResponseOBJ;
	@Autowired
	HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	@Autowired
	JdbcTemplate jdbcTemplate;
	

	@Override
	public HERO_STK_RESPONSEINFO getuomforpacking(String uompackingid) {
		// TODO Auto-generated method stub
		try
		{
//		log.info("uompackingid   "+uompackingid);
		/*String uompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_sno`,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 0 "
				+ "UNION "
				+ "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,(`hsuts_sno`)+1,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 ORDER BY `hsuts_sno`";*/
		
		Map<String, Object> uomPackMap = new HashMap<String, Object>();
		
		String fulluompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_sno`,"
				+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
				+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 0 "
				+ "ORDER BY `hsuts_sno`";
		
	//	log.info("fulluompackingQuery   "+fulluompackingQuery);
		List<Map<String, Object>> fulluomPackingList = jdbcTemplate.queryForList(fulluompackingQuery);
		List<Map<String, Object>> fulluomPacking = new ArrayList<Map<String,Object>>();
		
	//	log.info("fulluomPackingList   "+fulluomPackingList);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<select id=\"uompackingsel\" onchange=\"calculatelooseqty()\" style=\"display:none;\">");
		
		Iterator<Map<String, Object>> fulluomPackingListITR = fulluomPackingList.iterator();
		while(fulluomPackingListITR.hasNext())
		{
			Map<String, Object> fulluomPackingMap = fulluomPackingListITR.next();
			int sno = (int) fulluomPackingMap.get("hsuts_sno");
			long uomid = (long)fulluomPackingMap.get("unit_type_id");
			sb.append("<option value='"+String.valueOf(sno).concat("-").concat(String.valueOf(uomid))+"'>"+fulluomPackingMap.get("uomdesc")+"</option>");
			
			fulluomPackingMap.put("uomval", String.valueOf(sno).concat("-").concat(String.valueOf(uomid)));
			fulluomPacking.add(fulluomPackingMap);
		}
		sb.append("</select>");
		uomPackMap.put("fulluomsel", sb.toString());
		
		sb = new StringBuilder();
		
		String looseUOMCountQuery = " SELECT * FROM `hero_stock_unit_type_config` a JOIN `hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) "
				+ "WHERE `hsuts_id` = "+uompackingid;
		List<Object> looseUOMCount = jdbcTemplate.queryForList(looseUOMCountQuery);
		
		String looseuompackingQuery = "";
		
		if(looseUOMCount.size() > 1)
		{
			looseuompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,`hsuts_sno`,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_full_uom`) uomdesc  FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON  (a.`hsuts_full_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `hsuts_sno` > 1  AND `uom_type` = 0 "
					+ "UNION "
					+ "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,(`hsuts_sno`)+1,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 AND `hsuts_sno` > 1 "
					+ " ORDER BY `uom_type` DESC;";	
		}
		else
		{
			looseuompackingQuery = "SELECT `unit_type_id`,`hsuts_loose_qty`,`uom_type`,`hsuts_full_uom`,`hsuts_loose_uom`,((`hsuts_sno`)+1) hsuts_sno,"
					+ "(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id`=a.`hsuts_loose_uom`) uomdesc FROM `hero_stock_unit_type_config` a JOIN "
					+ "`hero_admin_unit_type` b ON (a.`hsuts_loose_uom` = b.`unit_type_id`) WHERE `hsuts_id` = "+uompackingid+" AND `uom_type` = 1 "
					+ " ORDER BY `uom_type` DESC;";
		}
		
		//log.info("looseuompackingQuery   "+looseuompackingQuery);
		
		List<Map<String, Object>> looseuomPackingList = jdbcTemplate.queryForList(looseuompackingQuery);
		List<Map<String, Object>> looseuomPacking = new ArrayList<Map<String,Object>>();
		
		//log.info("looseuomPackingList   "+looseuomPackingList);
		
		sb = new StringBuilder();
		sb.append("<select id=\"uompackingsel\" onchange=\"calculatelooseqty()\" style=\"display:none;\">");
		
		Iterator<Map<String, Object>> looseuomPackingListITR = looseuomPackingList.iterator();
		while(looseuomPackingListITR.hasNext())
		{
			Map<String, Object> looseuomPackingMap = looseuomPackingListITR.next();
			long sno = (long) looseuomPackingMap.get("hsuts_sno");
			long uomid = (long)looseuomPackingMap.get("unit_type_id");
			sb.append("<option value='"+String.valueOf(sno).concat("-").concat(String.valueOf(uomid))+"'>"+looseuomPackingMap.get("uomdesc")+"</option>");
			
			looseuomPackingMap.put("uomval", String.valueOf(sno).concat("-").concat(String.valueOf(uomid)));
			looseuomPacking.add(looseuomPackingMap);
		}
		sb.append("</select>");
		uomPackMap.put("looseuomsel", sb.toString());
		
		uomPackMap.put("fulluomPackingList", fulluomPacking);
		uomPackMap.put("looseuomPackingList", looseuomPacking);
		
		inventoryResponseOBJ.setResponseObj("S");
		inventoryResponseOBJ.setResponseObj(uomPackMap);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO calculatelooseqty(String uomdata) {
		// TODO Auto-generated method stub
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomdata, HERO_STK_ADDPURCHASEORDERREQUEST.class);
		//log.info("uomdata  "+uomdata);
		//log.info("Values are     "+request.getFulluomqty()+"   "+request.getLooseuomqty()+"   "+request.getFulluom()+"   "+request.getLooseuom());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_CALC_LOOSE_QTY");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_FULL_UOM_SNO", request.getFulluomsno());
		inParamMap.put("P_LOOSE_UOM_SNO", request.getLooseuomsno());
		inParamMap.put("P_LOOSE_UOM", request.getLooseuom());
		inParamMap.put("P_FULL_QTY", request.getFulluomqty());
		inParamMap.put("P_LOOSE_QTY", request.getLooseuomqty());
		inParamMap.put("P_UOM_PACKING_ID", request.getUompacking());
		
		//log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		//log.info(resultMap);
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			error_log.info(e.getMessage());
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO calculateordlooseqty(String uomdata) {
		// TODO Auto-generated method stub
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomdata, HERO_STK_ADDPURCHASEORDERREQUEST.class);
		//log.info("uomdata  "+uomdata);
		//log.info("Values are     "+request.getFulluomqty()+"   "+request.getLooseuomqty()+"   "+request.getFulluom()+"   "+request.getLooseuom());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_CALC_LOOSE_QTY");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_FULL_UOM_SNO", request.getFulluomsno());
		inParamMap.put("P_LOOSE_UOM_SNO", request.getLooseuomsno());
		inParamMap.put("P_LOOSE_UOM", request.getLooseuom());
		inParamMap.put("P_FULL_QTY", request.getFulluomqty());
		inParamMap.put("P_LOOSE_QTY", request.getLooseuomqty());
		inParamMap.put("P_UOM_PACKING_ID", request.getUompacking());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		return inventoryResponseInfoOBJ;
	}
	
	
	
	@Override
	public HERO_STK_RESPONSEINFO savepurchaseorder(String purchaseorderData,
			HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj) {

		log.info("purchaseorderData        "+purchaseorderData);
		
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(purchaseorderData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
		
		log.info("Values are     "+request.getItemlist());
		
		List<HERO_STK_ADDPURCHASEORDERITEMS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS");
		
		List<HERO_STK_ADDPURCHASEORDERITEMS> pritemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS");
		
		
		/*List<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getDishlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERDISHITEMS");
		*/
		
		log.info(request.getItemlist()+"   "+request.getOprn()+"   "+request.getPaidamt()+"   "+request.getPurchasecode()+"   "+request.getPurchasedate()+
				"   "+request.getPurchaseid()+"   "+request.getPurchasenotes()+"   "+request.getPurchaserefno()+"   "+request.getPurchasestatus()+"   "+
				request.getPurchasetnc()+"   "+request.getReceiveddate()+"   "+request.getReceivestatus()+"   "+
				request.getSupplierid()+"   "+request.getTotalamt()+"   "+request.getUserid());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_ORDER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_PURCHASE_ID", request.getPurchaseid());
		inParamMap.put("P_PURCHASE_CODE", request.getPurchasecode());
		inParamMap.put("P_PURCHASE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getPurchasedate()));
		inParamMap.put("P_RECEIVED_DATE", null);
		inParamMap.put("P_TOTAL_AMT", 0.0);
		inParamMap.put("P_PURCHASE_REF_NO", request.getPurchaserefno());
		inParamMap.put("P_PAID_AMT", 0.0);
		inParamMap.put("P_SUPPLIER_ID", request.getSupplierid());
		inParamMap.put("P_PURCHASE_NOTES", request.getPurchasenotes());
		inParamMap.put("P_PURCHASE_TNC", request.getPurchasetnc());
		inParamMap.put("P_RECV_STATUS", request.getReceivestatus());
		inParamMap.put("P_STORE_ID", request.getStoreid());
		inParamMap.put("P_PURCHASE_STATUS", request.getPurchasestatus());
		inParamMap.put("P_USER_ID", request.getUserid());
		inParamMap.put("P_DEL_REMARKS", "");
		inParamMap.put("P_PURCHASE_REQ_ID", request.getPurchasereqeustid());
		inParamMap.put("P_OPRN", request.getOprn());
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		String purchaseOrderNo = null;
		String resultType = (String)resultMap.get("out_result_type");
		
		if(resultType != null && resultType.equals("S"))
		{
			
		 
			purchaseOrderNo = (String)resultMap.get("out_purchase_id");	
			log.info("purchaseOrderNo       "+purchaseOrderNo);
			Iterator<HERO_STK_ADDPURCHASEORDERITEMS> itr = itemList.iterator();
			while(itr.hasNext())
			{
				HERO_STK_ADDPURCHASEORDERITEMS obj = itr.next();
				obj.setPurchaseorderid(purchaseOrderNo);
				obj.setOprn(request.getOprn());
				
				itemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info(obj.getPurchaseorderid()+"   "+obj.getOprn()+" index "+obj.getIndex()+" product id "+obj.getProductid());
			}	
			
			Iterator<HERO_STK_ADDPURCHASEORDERITEMS> pritr = pritemList.iterator();
			while(pritr.hasNext())
			{
				HERO_STK_ADDPURCHASEORDERITEMS obj = pritr.next();
				obj.setPurchaseorderid(request.getPurchasereqeustid());
				obj.setOprn("ORD");
				
				pritemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info("request items  "+obj.getPurchaseorderid()+"   "+obj.getOprn()+" index "+obj.getIndex()+" product id "+obj.getProductid());
			}	
			
			/*Iterator<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishitr = dishList.iterator();
			while(dishitr.hasNext())
			{
				HERO_STK_ADDPURCHASEORDERDISHITEMS obj = dishitr.next();
				obj.setPurchaseid(purchaseOrderNo);
				obj.setOprn("INS");
				
				dishList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info(obj.getPurchaseid()+"   ");
			}*/	
		 
		if(request.getOprn() != null && request.getOprn().equalsIgnoreCase("UPD"))
		{
			purchaseOrderNo = request.getPurchasecode();
			
			jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_ITEMS");
			inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_PURCHASE_ID", request.getPurchasecode());
			inParamMap.put("P_PRODUCT_ID","");
			inParamMap.put("P_QUANTITY", 0);
			inParamMap.put("P_FULL_UOM", 0);
			inParamMap.put("P_FULL_UOM_QTY", 0);
			inParamMap.put("P_LOOSE_UOM", 0);
			inParamMap.put("P_LOOSE_UOM_QTY", 0);
			inParamMap.put("P_UOM_PACKING_ID", 0);
			inParamMap.put("P_OPRN", request.getOprn());
			inParamMap.put("P_CGST", request.getCgsttax());
			inParamMap.put("P_SGST", request.getSgsttax());
			in = new MapSqlParameterSource(inParamMap);
			jdbcCALL.execute(in);
		}
		
		
		savepurchaseitems(itemList, purchaseOrderNo);
		
		savepurchaserequestitems(pritemList, request.getPurchasereqeustid());
		/*savepurchaseorderdishitems(dishList, purchaseOrderNo);*/
		
		}
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}



	@Override
	public HERO_STK_RESPONSEINFO approveitemrequest(String purchaseorderData,
			HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj) {

		log.info("approveitemrequest        "+purchaseorderData);
		
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(purchaseorderData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
		
		log.info("approveitemrequest Values are     "+request.getPurchasereqeustid());
		
		
		List<HERO_STK_ADDPURCHASEORDERITEMS> pritemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS");
		
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_REQUEST");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_PURCHASE_ID", request.getPurchasereqeustid());
		inParamMap.put("P_PURCHASE_CODE", "");
		inParamMap.put("P_PURCHASE_REF_NO", "");
		inParamMap.put("P_PURCHASE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000"));
		inParamMap.put("P_RECEIVED_DATE", null);
		inParamMap.put("P_TOTAL_AMT", 0.0);
		inParamMap.put("P_PAID_AMT", 0.0);
		inParamMap.put("P_SUPPLIER_ID", "");
		inParamMap.put("P_PURCHASE_NOTES", "");
		inParamMap.put("P_PURCHASE_TNC", "");
		inParamMap.put("P_RECV_STATUS", "");
		inParamMap.put("P_PURCHASE_STATUS", "");
		inParamMap.put("P_USER_ID", "");
		inParamMap.put("P_PURCHASE_REQ_ID", "");
		inParamMap.put("P_DEL_REMARKS", "");
		inParamMap.put("P_OPRN", "UPD_APPROVED");
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		log.info("inventoryResponseOBJ"+inventoryResponseOBJ.getResponseType());
			log.info("pritemList  size"+pritemList.size());
		
			Iterator<HERO_STK_ADDPURCHASEORDERITEMS> pritr = pritemList.iterator();
			while(pritr.hasNext())
			{
				HERO_STK_ADDPURCHASEORDERITEMS obj = pritr.next();
				obj.setPurchaseorderid(request.getPurchasereqeustid());
				obj.setOprn("UPD");
				
				pritemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
				
				log.info("request items  "+request.getPurchasereqeustid()+"   "+obj.getPurchaseorderid()+"   "+obj.getOprn()+" index "+obj.getIndex()+" product id "+obj.getProductid());
			}	
			
		savepurchaserequestitems(pritemList, request.getPurchasereqeustid());
		
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Transactional
	public void savepurchaseitems( final List<HERO_STK_ADDPURCHASEORDERITEMS> list,String purchaseordernumber) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_ITEMS( ?,?,?,?,?, ?,?,?,?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_ADDPURCHASEORDERITEMS item = list.get(i);
				log.info("Item Values       "+item+"       "+item.getProductid()+"   "+item.getQuantity()+"   "+item.getPurchaseorderid()+"  "
						+item.getFulluom()+"   "+item.getFulluomqty()+"  "+item.getLooseuom()+"   "+item.getLooseuomqty());
				ps.setString(1, item.getPurchaseorderid());
				ps.setString(2, item.getProductid());
				ps.setString(3, item.getQuantity());
				ps.setString(4, item.getFulluom());
				ps.setString(5, item.getFulluomqty());
				ps.setString(6, item.getLooseuom());
				ps.setString(7, item.getLooseuomqty());
				ps.setString(8, item.getUompacking());
				ps.setString(9, "INS");
				ps.setString(10, item.getPorid());
				ps.setString(11, item.getCgsttax());
				ps.setString(12, item.getSgsttax());
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}

	
	@Transactional
	public void savepurchaseorderdishitems( final List<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishList,String purchaseordernumber) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_DISH_ITEMS( ?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return dishList.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_ADDPURCHASEORDERDISHITEMS item = dishList.get(i);
				log.info("Item Values       "+item+"          "+item.getPurchaseid());
				ps.setString(1, item.getPurchaseid());
				ps.setString(2, item.getDishname());
				ps.setString(3, item.getDishcount());
				ps.setString(4, item.getOprn());
			}
		});
		}
		catch( Exception e )
		{			
			error_log.info(e);
			throw e;
		}
	}

	
	

	
	@Override
	public HERO_STK_RESPONSEINFO deletePurchaseorder(String purchaseorderid, String remarksData) {

		log.info("delete purchaseorderid        "+purchaseorderid);
		String Remarks = "";
		if(remarksData != null && !remarksData.equals("") && remarksData.length() > 0){
			try {
				HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(remarksData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
				Remarks = request.getRemarks();
				log.info("Remarks  "+Remarks);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				error_log.info(e);
			}
		}
		
		try
		{		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_ORDER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_PURCHASE_ID", purchaseorderid);
		inParamMap.put("P_PURCHASE_CODE", "");
		inParamMap.put("P_PURCHASE_REF_NO", "");
		inParamMap.put("P_PURCHASE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000"));
		inParamMap.put("P_RECEIVED_DATE", null);
		inParamMap.put("P_TOTAL_AMT", 0.0);
		inParamMap.put("P_PAID_AMT", 0.0);
		inParamMap.put("P_SUPPLIER_ID", "");
		inParamMap.put("P_PURCHASE_NOTES", "");
		inParamMap.put("P_PURCHASE_TNC", "");
		inParamMap.put("P_RECV_STATUS", "");
		inParamMap.put("P_PURCHASE_STATUS", "");
		inParamMap.put("P_USER_ID", "");
		inParamMap.put("P_PURCHASE_REQ_ID", "");
		inParamMap.put("P_DEL_REMARKS", Remarks);
		inParamMap.put("P_OPRN", "DEL");
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		log.info("inventoryResponseOBJ"+inventoryResponseOBJ.getResponseType());
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	
	@Override
	public HERO_STK_RESPONSEINFO purchaseorderlist(String purchaseorderid) {
		// TODO Auto-generated method stub
		StringBuilder query = new StringBuilder("SELECT purchase_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y - %H:%i')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y - %H:%i')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
				+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,purchase_status,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count FROM hero_stock_purchase_status c ,"
				+ "hero_stock_supplier b,"
				+ " hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id");
		
		if(purchaseorderid != null && !purchaseorderid.equals("0"))
		{
			query.append(" and purchase_id = "+purchaseorderid);
		}
		query.append(" ORDER BY `purchase_id` DESC");
		
		log.info("purchaseorderlist query       "+query);
		@SuppressWarnings("unchecked")
		List<Object> purchaseorderList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				Double dpaidamt = rs.getDouble("paid_amt"),dtotalamt = rs.getDouble("total_amt");
				
				map.put("purchaseid", rs.getString("purchase_id"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("purchasecodenavigate", "<a class='color-font' href='purchaseorderview?pid="+rs.getString("purchase_id")+"'>"+rs.getString("purchase_code")+"</a>");
				map.put("purchaserefno", rs.getString("purchase_refer_no"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("receiveddate", rs.getString("received_date"));
				map.put("totalamt", new DecimalFormat("#.##").format(dtotalamt));    
				map.put("totalamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt)));
				map.put("paidamt", new DecimalFormat("#.##").format(dpaidamt));
				map.put("paidamtdisp", "Rs."+(new DecimalFormat("#.##").format(dpaidamt)));
				map.put("supplierid", rs.getString("supplier_id"));
				map.put("purchasenotes", rs.getString("purchase_notes"));
				map.put("purchasetnc", rs.getString("purchase_tnc"));
				map.put("receivestatus", rs.getString("receive_status"));
				map.put("purchasestatus", rs.getString("purchase_status"));
				map.put("balanceamt", new DecimalFormat("#.##").format(dtotalamt - dpaidamt) );
				map.put("balanceamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt - dpaidamt) ));
				
				if(rs.getInt("purchase_status") == 1)
				{
					map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);	
				}
				else
				{
					map.put("action", "");
				}
				
				map.put("suppliername", rs.getString("supplier_name"));
				if(rs.getInt("purchase_status") == 3)
				{
					map.put("purchasestatusdesc", "Not Paid");	
				}
				else
				{
					map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
				}
				
				map.put("recvstatusdesc", rs.getString("recvstats_desc"));
				
				return map;
			}
		});
		log.info(purchaseorderList);
		
		if(purchaseorderid != null && !purchaseorderid.equals("0"))
		{
			Map<String, Object> purchaseMap = (Map<String, Object>) purchaseorderList.get(0);
			String purchaseorderno = (String) purchaseMap.get("purchasecode");
			
			/*String itemQuery = "SELECT * FROM hero_stock_purchase_request where pur_req_id = '"+purchaseorderno+"'";*/
			String itemQuery = "SELECT pur_req_id,pur_product_id,pur_quantity,`product_name`,coalesce(`company_name`,'')company_name,pur_full_uom,pur_full_qty,"
					+ "pur_loose_uom,pur_loose_qty,hsuts_id FROM hero_stock_purchase_request a JOIN `hero_stock_product` b ON a.`pur_product_id` = b.`product_id` "
					+ "LEFT JOIN `hero_admin_company` c ON c.`company_id` = b.`manufacturer_id` WHERE pur_req_id = '"+purchaseorderno+"'";
			
			log.info("itemQuery     "+itemQuery);
			@SuppressWarnings("unchecked")
			List<Object> itemList = jdbcTemplate.query(itemQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int count) throws SQLException {
					Map<String, Object> map = new HashMap<String, Object>();
					count = count + 1;
					map.put("index", count);
					
					String uompackingid = rs.getString("hsuts_id"),fulluom = rs.getString("pur_full_uom"),fulluomsno = "",looseuom=rs.getString("pur_loose_uom"),looseuomsno="";
					
					map.put("purchaseorderid", rs.getString("pur_req_id"));
					map.put("productid", rs.getString("pur_product_id"));
					map.put("quantity", rs.getString("pur_quantity"));
					map.put("productname", rs.getString("product_name").concat(" - "+rs.getString("company_name")));
					map.put("fulluom", fulluom);
					map.put("fulluomqty", rs.getString("pur_full_qty"));
					map.put("looseuom", looseuom);
					map.put("looseuomqty", rs.getString("pur_loose_qty"));
					map.put("uompacking", rs.getString("hsuts_id"));
					
					String fulluomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_full_uom` = "+fulluom;
					String looseuomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_loose_uom` = "+looseuom;
					List<Map<String, Object>> fulluomList = jdbcTemplate.queryForList(fulluomidquery);
					List<Map<String, Object>> looseuomList = jdbcTemplate.queryForList(looseuomidquery);
					if(fulluomList != null && fulluomList.size() > 0)
					{
						Map<String, Object> fulluomMap = fulluomList.get(0);
						fulluomsno = String.valueOf((int) fulluomMap.get("hsuts_sno"));
					}
					
					if(looseuomList != null && looseuomList.size() > 0)
					{
						Map<String, Object> looseuomMap = looseuomList.get(0);
						looseuomsno = String.valueOf((int) looseuomMap.get("hsuts_sno")+1);
					}
					
					map.put("fulluomsel", fulluomsno.concat("-").concat(fulluom));
					map.put("looseuomsel", looseuomsno.concat("-").concat(looseuom));
					
					HERO_STK_RESPONSEINFO responseInfo = getuomforpacking(uompackingid);
					
					
					HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
					log.info(response.getResponseObj());
					map.put("uomsel", response.getResponseObj());			
					
					
					return map;
				}
			});
			log.info("itemList        "+itemList);
			Map<String, Object> responseMap = new HashMap<String, Object>();
			responseMap.put("purchaseorderList", purchaseorderList);
			responseMap.put("itemList", itemList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);
		}
		
		else
		{
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(purchaseorderList);	
		}
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	
	@Override
	public HERO_STK_RESPONSEINFO getPurchaseDishList(String purchaseid) {
		// TODO Auto-generated method stub
		
		String dishquery = " SELECT `hpdr_id`,a.`dish_name`,`dish_count`,DATE_FORMAT(`created_date`,'%e/%c/%Y')createddate"
				+ " FROM `hero_stock_purrhase_dish_request`a   WHERE  a.`purchase_id` = "+purchaseid;
		
		log.info("itemquery       "+dishquery);
		
		@SuppressWarnings("unchecked")
		List<Object> dishList = jdbcTemplate.query(dishquery, new RowMapper() {
			int count = 0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
				count++;
				map.put("index", count);
				map.put("requestid",   rs.getString("hpdr_id"));
				map.put("dishname",    rs.getString("dish_name"));
				//map.put("dishid",      rs.getString("dish_id"));		
				map.put("dishcount",   rs.getString("dish_count"));
				map.put("createddate", rs.getString("createddate"));		
				
				return map;
			}
		});
		
			log.info("dishList        "+dishList);
			Map<String, Object> responseMap = new HashMap<String, Object>();			
			responseMap.put("dishList", dishList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);	
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
			
		}
		
	@Override
	public HERO_STK_RESPONSEINFO getDishtypeList(String dishid,String hdrid) {
		// TODO Auto-generated method stub
		
		System.out.println(dishid);
		String dishtypequery = " ";
		if(hdrid.equals("0")){
			 dishtypequery ="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid;
		}else{
			String gethdrtypeQuery = "SELECT `dish_type_id` FROM `hero_stock_dishes_setup_hdr` WHERE `dish_setup_id` ="+hdrid+" AND `dish_name_id`="+dishid;
			
			final List<String> nameList = new ArrayList<String>();
			@SuppressWarnings("unchecked")
			List<Object> dishhdrList = jdbcTemplate.query(gethdrtypeQuery, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("dish_type_id",   rs.getString("dish_type_id"));
					nameList.add(rs.getString("dish_type_id"));
					return map;
				}
			});
			
			if(nameList.size() > 0){
				System.out.println(nameList.get(0));
				String typeid =nameList.get(0);
				dishtypequery="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid+" ORDER BY CASE WHEN `dishtype_id`= "+typeid+" THEN 0 ELSE `dishtype_id` END";
			}else{
				dishtypequery ="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid;
			}
			
		}
		
		
		
		
		
		@SuppressWarnings("unchecked")
		List<Object> dishtypeList = jdbcTemplate.query(dishtypequery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("dishTypeId",   rs.getString("dishtype_id"));
				map.put("dishTypeDesc", rs.getString("dishtype_name"));		
				
				return map;
			}
		});
		
			
			Map<String, Object> responseMap = new HashMap<String, Object>();			
			responseMap.put("dishtypeList", dishtypeList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);	
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
			
		}
	@Override
	public HERO_STK_RESPONSEINFO getcustomermenuById(String customerid) {
		String customerMenuQuery="SELECT `setup_id`,`cust_id`,`day`,`food_time`,`dishid`,`dishtypeid`,`dishcount`,`qty_per_person`,`dish_name`,`dishtype_name`"
				+ " FROM `hero_daily_menu_setup` a JOIN `hero_stock_dishes` b ON a.`dishid` = b.`dish_id` JOIN `hero_stock_dish_type` c ON c.`dishtype_id` = a.`dishtypeid`"
				+ " WHERE `cust_id` ="+customerid;
		
		@SuppressWarnings("unchecked")
		List<Object> customerMenuList= jdbcTemplate.query(customerMenuQuery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("setupId",   rs.getString("setup_id"));
				map.put("customerId", rs.getString("cust_id"));	
				map.put("day", rs.getString("day"));	
				map.put("foodtime", rs.getString("food_time"));
				map.put("dishId", rs.getString("dishid"));
				map.put("dishTypeId", rs.getString("dishtypeid"));
				map.put("dishcount", rs.getString("dishcount"));
				map.put("qtyperperson", rs.getString("qty_per_person"));
				map.put("dishName", rs.getString("dish_name"));
				map.put("dishtypeName", rs.getString("dishtype_name"));
				return map;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerMenuList);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_STK_RESPONSEINFO getcustomerDishtypeList(String dishid,String customerid) {
		// TODO Auto-generated method stub
		
		/*System.out.println(dishid);
		String dishtypequery = " ";
	
			String gethdrtypeQuery = "SELECT `dish_type_id` FROM `hero_stock_dishes_setup_hdr` WHERE `dish_setup_id` ="+hdrid+" AND `dish_name_id`="+dishid;
			
			final List<String> nameList = new ArrayList<String>();
			@SuppressWarnings("unchecked")
			List<Object> dishhdrList = jdbcTemplate.query(gethdrtypeQuery, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("dish_type_id",   rs.getString("dish_type_id"));
					nameList.add(rs.getString("dish_type_id"));
					return map;
				}
			});
			
			if(nameList.size() > 0){
				System.out.println(nameList.get(0));
				String typeid =nameList.get(0);
				dishtypequery="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid+" ORDER BY CASE WHEN `dishtype_id`= "+typeid+" THEN 0 ELSE `dishtype_id` END";
			}else{
				dishtypequery ="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid;
			}
			
	
		*/
		
		String dishtypequery ="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishid;
		
		
		@SuppressWarnings("unchecked")
		List<Object> dishtypeList = jdbcTemplate.query(dishtypequery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("dishTypeId",   rs.getString("dishtype_id"));
				map.put("dishTypeDesc", rs.getString("dishtype_name"));		
				
				return map;
			}
		});
		
			
			Map<String, Object> responseMap = new HashMap<String, Object>();			
			responseMap.put("dishtypeList", dishtypeList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);	
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
			
		}
	@Override
	public HERO_STK_RESPONSEINFO getCustomerList(String orderId) {
		String customerQuery = "SELECT DISTINCT(`companyname`)companyname,`ord_customer_id` FROM `hero_order_request_dtl` a JOIN `hero_stock_client_company` b "
				+ " ON a.ord_customer_id=b.`companyid` WHERE `ord_req_id`="+orderId;
		@SuppressWarnings("unchecked")
		List<Object> customerList = jdbcTemplate.query(customerQuery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("customerId",   rs.getString("ord_customer_id"));
				map.put("companyname", rs.getString("companyname"));		
				
				return map;
			}
		});
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(customerList);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getPendingtyransferItemList(String transferId) {
/*		Map<String, Object> outmap = new HashMap<String, Object>();
		String transferQuery = "SELECT `tpr_id`, `companyname`,`dish_name`, `dishtype_name`,a.`dish_count`,`product_name`,`transfer_id`"
				+ " FROM `hero_stock_transfer_product` a JOIN `hero_stock_client_company` b ON b.`companyid`=a.`customer_id`"
				+ " JOIN `hero_stock_dishes` c ON c.`dish_id`= a.`dishid` JOIN `hero_stock_dish_type` d ON d.`dishtype_id`= a.`dishtypeid`"
				+ " JOIN `hero_stock_product`e ON e.`product_id`=a.`product_id` "
				+ " WHERE `transfer_id`="+transferId+" GROUP BY `customer_id`,`dishid`,`dishtypeid`";
		log.info(transferQuery);
		@SuppressWarnings("unchecked")
		List<Object> transferList = jdbcTemplate.query(transferQuery, new RowMapper() {
		int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("tprId",   rs.getString("tpr_id"));
				map.put("transferId",   rs.getString("transfer_id"));
				map.put("companyname", rs.getString("companyname"));	
				map.put("diahName", rs.getString("dish_name"));	
				map.put("diahTypeName", rs.getString("dishtype_name"));	
				map.put("productname", rs.getString("product_name"));
				map.put("dishCount", rs.getString("dish_count"));	
				map.put("index", index);
				
				index++;
				return map;
			}
		});
		
		String transferwithproductQuery = "SELECT `tpr_id`, `companyname`,`dish_name`, `dishtype_name`,a.`dish_count`,`product_name`,`transfer_id`"
				+ " FROM `hero_stock_transfer_product` a JOIN `hero_stock_client_company` b ON b.`companyid`=a.`customer_id`"
				+ " JOIN `hero_stock_dishes` c ON c.`dish_id`= a.`dishid` JOIN `hero_stock_dish_type` d ON d.`dishtype_id`= a.`dishtypeid`"
				+ " JOIN `hero_stock_product`e ON e.`product_id`=a.`product_id` "
				+ " WHERE `transfer_id`="+transferId;
		log.info(transferQuery);
		@SuppressWarnings("unchecked")
		List<Object> transferwithproductList = jdbcTemplate.query(transferwithproductQuery, new RowMapper() {
		int index=1;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("tprId",   rs.getString("tpr_id"));
				map.put("transferId",   rs.getString("transfer_id"));
				map.put("companyname", rs.getString("companyname"));	
				map.put("diahName", rs.getString("dish_name"));	
				map.put("diahTypeName", rs.getString("dishtype_name"));	
				map.put("productname", rs.getString("product_name"));
				map.put("dishCount", rs.getString("dish_count"));	
				map.put("index", index);
				
				index++;
				return map;
			}
		});
		
		
		outmap.put("pendinglistwithproduct", transferwithproductList);
		outmap.put("pendinglistwithoutproduct", transferList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(outmap);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;*/
		String customerQuery ="SELECT DISTINCT(`customer_id`),`companyname`,`companyid` FROM `hero_stock_transfer_product` a "
				+ "JOIN `hero_stock_client_company` b ON a.customer_id = b.`companyid`  WHERE `transfer_id`="+transferId;
		List<Object> customerList = new ArrayList<Object>();
		Map<String, Object> countList = new HashMap<String, Object>();
		Map<String, Object> finalcountList = new HashMap<String, Object>();
		Map<String, Object> finalMap = new HashMap<String, Object>();
		customerList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(customerQuery, jdbcTemplate);
		 StringBuilder customerid = new StringBuilder(" ");
			Iterator<Object> customerListITR = customerList.iterator();
			while(customerListITR.hasNext())
			{
				Map<String, Object> Map = (java.util.Map<String, Object>) customerListITR.next();
				
			String customeridstr = (String) Map.get("companyid"); 
				customerid.append(customeridstr+",");
				
				
			}
			customerid.deleteCharAt(customerid.length()-1);
			String dishQuery = "SELECT DISTINCT(`dishtypeid`),`dishtype_name`,b.`dish_id`,`dish_name`,dishtypeid FROM `hero_stock_transfer_product` a "
					+ "JOIN `hero_stock_dish_type` b ON a.dishtypeid=b.`dishtype_id`  JOIN `hero_stock_dishes` c ON c.`dish_id`=b.dish_id"
					+ " WHERE `transfer_id`="+transferId;
			List<Object> dishList = (List<Object>) HEROHOSURINVENTORYUTIL.executeQueryWithList(dishQuery, jdbcTemplate);
			List<Object> dishLiswithdishtype = new ArrayList<Object>();
			Iterator<Object> dishListITR = dishList.iterator();
			while(dishListITR.hasNext())
			{
				
				Map<String, Object> Map = (java.util.Map<String, Object>) dishListITR.next();
				
			
				String dishCountQuery = "SELECT dishtypeid,`customer_id` FROM `hero_stock_transfer_product` a "
						+ "  WHERE `dishtypeid`="+Map.get("dishtypeid")+" AND `customer_id` IN("+customerid+") AND a.`transfer_id`="+transferId+" GROUP BY `customer_id`";
				
				@SuppressWarnings("unchecked")
				List<Object> countaddList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("dishtypeid", rs.getString("dishtypeid"));
						map.put("customerid", rs.getString("customer_id"));
						
						return map;
					}
				});
				
				String totaldishtypeQuery ="SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+Map.get("dish_id")+" ORDER BY CASE WHEN `dishtype_id`= "+Map.get("dishtypeid")+"  THEN 0 ELSE `dishtype_id` END";
				
				@SuppressWarnings("unchecked")
				List<Object> totaldishtypeList = jdbcTemplate.query(totaldishtypeQuery, new RowMapper() {
					@Override
					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("dishtypeid", rs.getString("dishtype_id"));
						map.put("dishtypename", rs.getString("dishtype_name"));
						
						return map;
					}
				});
				
				Map.put("dishtypelist", totaldishtypeList);
				dishLiswithdishtype.add(Map);
				countList.put((String) Map.get("dishtypeid"),countaddList);
				
			}
			 for (Map.Entry<String, Object> set :
				 countList.entrySet()) {
	 
	            // Printing all elements of a Map
	            
	        	List<Object> obj = (List<Object>) set.getValue();
	        	Iterator<Object> objListITR = obj.iterator();
	        	final List<Object> newarray = new ArrayList<Object>();
	    		while(objListITR.hasNext())
	    		{
	    			final Map<String, Object> Map = (java.util.Map<String, Object>) objListITR.next();
	    			  
	    			   final Map<String, Object> newmap = new HashMap<String, Object>();
	    			   String dishCountQuery = "SELECT `dish_count` FROM `hero_stock_transfer_product` a JOIN"
						+ " `hero_stock_transfer` b ON a.`transfer_id`=b.`transfer_id`  WHERE `customer_id`="+Map.get("customerid")+" AND `dishtypeid`="+Map.get("dishtypeid")+" "
	    						+ " AND  a.`transfer_id`="+transferId;
	    						
	    				
	    				@SuppressWarnings("unchecked")
	    				List<Object> addList = jdbcTemplate.query(dishCountQuery, new RowMapper() {
	    					@Override
	    					public Object mapRow(ResultSet rs, int arg1) throws SQLException {
	    						Map<String, Object> map = new HashMap<String, Object>();
	    						map.put("dish_count", rs.getString("dish_count"));
	    						newmap.put("customerid", Map.get("customerid"));
	    	    				newmap.put("dishcount", rs.getString("dish_count"));
	    						newarray.add(newmap);
	    						return map;
	    					}
	    				});
	    			
	    				
	    		}
	    		finalcountList.put(set.getKey(), newarray);
	        }
			
			 finalMap.put("customerList", customerList);
				finalMap.put("dishList", dishLiswithdishtype);
				finalMap.put("dishcountList", finalcountList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(finalMap);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getDishList(String customerId,String orderId) {
		String dishQuery = "SELECT DISTINCT(`dish_name`)dish_name,`ord_dish_id` FROM `hero_order_request_dtl` a  "
				+ " JOIN `hero_stock_dishes` b ON a.ord_dish_id=b.`dish_id` WHERE `ord_req_id`="+orderId+" AND `ord_customer_id`="+customerId;
		@SuppressWarnings("unchecked")
		List<Object> dishList = jdbcTemplate.query(dishQuery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("dishId",   rs.getString("ord_dish_id"));
				map.put("dishName", rs.getString("dish_name"));		
				
				return map;
			}
		});
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(dishList);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
		
		
		
	}
	@Override
	public HERO_STK_RESPONSEINFO getcusdishTypeList(String customerId,String orderId,String dishId) {
		Map<String, Object> responseMap = new HashMap<String, Object>();		
		String cusdishTypeQuery = "SELECT `ord_dishtype_id`,`dishtype_name`,`ord_dish_count` FROM `hero_order_request_dtl` a  "
				+ " JOIN `hero_stock_dish_type` b ON a.ord_dishtype_id=b.`dishtype_id` WHERE `ord_req_id`="+orderId+" AND `ord_dish_id`="+dishId+" AND `ord_customer_id`="+customerId;
		final List<String> nameList = new ArrayList<String>();
		@SuppressWarnings("unchecked")
		List<Object> custdishTypeList = jdbcTemplate.query(cusdishTypeQuery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("dishTypeId",   rs.getString("ord_dishtype_id"));
				map.put("dishtypeName", rs.getString("dishtype_name"));		
				map.put("dishCount", rs.getString("ord_dish_count"));	
				nameList.add(rs.getString("ord_dishtype_id"));
				return map;
			}
		});
		if(nameList.size() > 0){
		String typeid =nameList.get(0);
		String availabledishTypeQuery = "SELECT `dishtype_id`,`dishtype_name` FROM `hero_stock_dish_type` WHERE `dish_id` = "+dishId+" ORDER BY CASE WHEN `dishtype_id`= "+typeid+" THEN 0 ELSE `dishtype_id` END";
		@SuppressWarnings("unchecked")
		List<Object> availabledishTypeList = jdbcTemplate.query(availabledishTypeQuery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("dishTypeId",   rs.getString("dishtype_id"));
				map.put("dishtypeName", rs.getString("dishtype_name"));		
				
				return map;
			}
		});
		responseMap.put("availabledishTypeList", availabledishTypeList);
		}
		
		responseMap.put("custdishTypeList", custdishTypeList);
	
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(responseMap);	
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
		
		
		
	}
	@Override
	public HERO_STK_RESPONSEINFO getDishProductDetails(String hdrid) {
		// TODO Auto-generated method stub
		
		String dishtypequery ="SELECT `dish_product_id`,`dish_loose_uom`,`dish_loose_qty`,`product_name`,`dish_full_uom`,`dish_full_qty`,"
				+ " `total_qty`,c.`unit` fulluomdesc,d.`unit` looseuomdesc,a.`hsuts_id` FROM `hero_stock_dishes_setup_dtl` a "
				+ " JOIN `hero_stock_product` b ON a.`dish_product_id`  =b.`product_id` "
				+ "  LEFT JOIN `hero_admin_unit_type` c ON c.`unit_type_id`=a.`dish_full_uom`  "
				+ " LEFT JOIN `hero_admin_unit_type` d ON d.`unit_type_id`=b.`unit_type_id`  WHERE `dish_hdr_id`="+hdrid;

		
		
		
		@SuppressWarnings("unchecked")
		List<Object> dishtypeList = jdbcTemplate.query(dishtypequery, new RowMapper() {
		
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
					
				map.put("productId",   rs.getString("dish_product_id"));
				map.put("looseTypeId", rs.getString("dish_loose_uom"));	
				map.put("fullypeId", rs.getString("dish_full_uom"));	
				map.put("looseQty", rs.getString("dish_loose_qty"));
				map.put("fullQty", rs.getString("dish_full_qty"));
				map.put("productName", rs.getString("product_name"));
				map.put("totalqty", rs.getString("total_qty"));
				map.put("fulluomdesc", rs.getString("fulluomdesc"));
				map.put("looseuomdesc", rs.getString("looseuomdesc"));
				map.put("uompacking", rs.getString("hsuts_id"));
				
				return map;
			}
		});
		
			
			Map<String, Object> responseMap = new HashMap<String, Object>();			
			responseMap.put("dishtypeList", dishtypeList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);	
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
			
		}
	
	@Override
	public HERO_STK_RESPONSEINFO getPurchaseRequestDishList(String purchaseid) {
		// TODO Auto-generated method stub
		
		String dishquery = " SELECT `hpdr_id`,a.`dish_name`,`dish_count`,DATE_FORMAT(`created_date`,'%e/%c/%Y')createddate"
				+ " FROM `hero_stock_purrhase_dish_request`a   WHERE  a.`purchase_id` = "+purchaseid;
		
		log.info("itemquery       "+dishquery);
		
		@SuppressWarnings("unchecked")
		List<Object> dishList = jdbcTemplate.query(dishquery, new RowMapper() {
			int count = 0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
				count++;
				map.put("index", count);
				map.put("requestid",   rs.getString("hpdr_id"));
				map.put("dishname",    rs.getString("dish_name"));
				//map.put("dishid",      rs.getString("dish_id"));		
				map.put("dishcount",   rs.getString("dish_count"));
				map.put("createddate", rs.getString("createddate"));		
				
				return map;
			}
		});
		
			log.info("dishList        "+dishList);
			Map<String, Object> responseMap = new HashMap<String, Object>();			
			responseMap.put("dishList", dishList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(responseMap);	
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
			
		}
		

	@Override
	public HERO_STK_RESPONSEINFO getPurchaseorderListPagewise(String pageno,String pid) {
		// TODO Auto-generated method stub
		
		int start = 0,end = 0,intPageno = 0;
		
		if(pageno != null)
		{
			intPageno = Integer.parseInt(pageno);
		}
		
		start = HEROHOSURINVENTORYUTIL.startIndex(intPageno);
		end = HEROHOSURINVENTORYUTIL.endIndex(intPageno);
		
		log.info("Index details are   "+intPageno+"   "+start+"   "+end);
		
		String viewpurchaseQuery = "";
		
		if(intPageno == 1)
		{
			viewpurchaseQuery = "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
				+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,"
				+ "purchase_notes,purchase_tnc,receive_status,purchase_status,CONCAT(supplier_first_name,supplier_last_name) supplier_name,"
				+ "ps_status_name purchase_status_desc,(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id IN ("+pid+")) "
				+ "UNION ALL "
				+ "(SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
				+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,purchase_status,"
				+ "CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
				+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
				+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
				+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b, hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id "
				+ "AND purchase_id NOT IN ("+pid+") ORDER BY purchase_id,`purchase_status` DESC LIMIT "+start+","+end+")";
		}
		else
		{
			viewpurchaseQuery = "SELECT purchase_id,COALESCE(`purchase_id`,0)prhdr_id,purchase_code,purchase_refer_no,"
					+ "DATE_FORMAT(purchase_date,'%e/%c/%Y')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,"
					+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
					+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count "
					+ "FROM hero_stock_purchase_status c ,hero_stock_supplier b,"
					+ " hero_stock_purchase a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id ORDER BY purchase_id,`purchase_status` DESC "
					+ "LIMIT "+start+","+end;
		}
		  log.info("viewpurchaseQuery      "+viewpurchaseQuery);
		  

			// TODO Auto-generated method stub
			
			@SuppressWarnings("unchecked")
			List<Object> purchaseOrderList = jdbcTemplate.query(viewpurchaseQuery, new RowMapper() {
				int index=0;
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {

					  Map<String, Object> map = new HashMap<String, Object>();
					  map.put("index", index);
					  map.put("purchasecode", rs.getString("purchase_code"));
					  map.put("totalamt", rs.getString("total_amt"));
					  map.put("suppliername", rs.getString("supplier_name"));
					  map.put("purchasedate", rs.getString("purchase_date"));
					  map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
					  map.put("purchaseid", rs.getString("purchase_id"));
					  map.put("headerid", rs.getString("prhdr_id"));
	/*				  map.put("referenceno", rs.getString("referenceno"));*/
					  /*map.put("grandamount", rs.getString("grandamount"));*/
					  map.put("statuscode", rs.getString("purchase_status"));
					  map.put("receive_status", rs.getString("receive_status"));
					  map.put("receivestatusdesc", rs.getString("recvstats_desc"));
					  index++;
					  log.info("map    "+map);
					  return map;
				}
			});
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(purchaseOrderList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO processreceivestock(String receiveStockData) {
		// TODO Auto-generated method stub
		
try
{
	HERO_STK_RECEIVESTOCKREQUEST request = (HERO_STK_RECEIVESTOCKREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(receiveStockData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_RECEIVESTOCKREQUEST");
	
	System.out.print("Values are     "+request.getTermsid()+"     "+request.getNotes()+"     "+request.getOrderedtax()+"     "
	+request.getPreceiveorderid()+"     "
			+request.getPrecid()+"     "+request.getPurchaseorderno()+"     "+request.getReceivedate()+"     "+request.getReceivelist()
			+"     "+request.getShippingcost()+"   "+request.getUserid());
	
	List<HERO_STK_RECEIVESTOCKITEMS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getReceivelist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_RECEIVESTOCKITEMS");
	//log.info("itemList       "+itemList);
	
	Iterator<HERO_STK_RECEIVESTOCKITEMS> itemsItr = itemList.iterator();
	while(itemsItr.hasNext())
	{
		HERO_STK_RECEIVESTOCKITEMS item = (itemsItr.next());
		System.out.print(item.getBatchno()+"   "+item.getBonusqty()+"   "+item.getExpirydate()+"   "+item.getOrdered()+"   "+item.getProductdesc()
				+"   "+item.getProductid()+"   "+item.getPurchaseprice()+"   "+item.getReceived()+"   "+item.getReceivingqty()+"   "
				+item.getSalesprice()+"   "+item.getSubtotal()+"   "+item.getUnit()+"   "
				+item.getUnitypeid()+"   "+item.getBarcode());
	}
		
	SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_RECV_HDR");
	Map<String, Object> inParamMap = new HashMap<String, Object>();
	inParamMap.put("P_PRHDR_ID", request.getPrecid());
	inParamMap.put("P_PUR_REQ_ID", request.getPurchaseorderno());
	inParamMap.put("P_PRHDR_RECV_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getReceivedate()));
	inParamMap.put("P_PRHDR_DISCOUNT_TYPE", request.getDiscount());
	inParamMap.put("P_PRHDR_DISCOUNT_VALUE", request.getDiscountvalue());
	inParamMap.put("P_PRHDR_TAX_ID", request.getOrderedtax());
	inParamMap.put("P_PRHDR_INCLUSIVE", request.getInclusive());
	
	inParamMap.put("P_PRHDR_SHIPPING_COST", request.getShippingcost());
	inParamMap.put("P_PRHDR_NOTES", request.getNotes());
	inParamMap.put("P_PRHDR_PAID_AMT", 0);
	inParamMap.put("P_REC_BANK", 0);
	inParamMap.put("P_REQDAYS", 0);
	inParamMap.put("P_REC_CHEQUE_NO", request.getChequeno());
	inParamMap.put("P_REC_PAYMENT_TYPE", 0);
	inParamMap.put("P_PRHDR_USER_ID", request.getUserid());
	inParamMap.put("P_REC_SUPPLIER_INVOICE_NO", request.getSupplierinvoiceno());
	inParamMap.put("P_REC_SUPPLIER_INVOICE_DATE", request.getSupplierinvoicedate() != null ?  HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getSupplierinvoicedate()) : HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000") );
	inParamMap.put("P_OPRN", request.getOprn());
	
	inParamMap.put("P_TERMS", request.getTermsid());
	
	System.out.print("inParamMap         "+inParamMap);
	SqlParameterSource in = new MapSqlParameterSource(inParamMap);
	Map<String, Object> resultMap = jdbcCALL.execute(in);
	System.out.print(resultMap);
	System.out.print(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
	
	String receivestockHdrno = null;
	String taxpertype = request.getInclusive();
	
	 
	receivestockHdrno = (String)resultMap.get("out_genrate_id");	
	System.out.print("receivestockHdrno       "+receivestockHdrno);
	Iterator<HERO_STK_RECEIVESTOCKITEMS> itr = itemList.iterator();
	int itemCount = 0;
	while(itr.hasNext())
	{
		HERO_STK_RECEIVESTOCKITEMS obj = itr.next();
		obj.setReceivehdrno(receivestockHdrno);
		obj.setOprn(request.getOprn());
		obj.setUserid(request.getUserid());
		itemList.set(itemCount, obj);
		
		itemCount++;
		
		request.setPrecid(String.valueOf(receivestockHdrno));
		
		System.out.print(obj.getReceivehdrno()+"   "+obj.getOprn());
	}	
	
	savepurchasereceiveitems(itemList, receivestockHdrno,taxpertype);
	
	
	jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_RECV_HDR");
	inParamMap = new HashMap<String, Object>();
	inParamMap.put("P_PRHDR_ID", request.getPrecid());
	inParamMap.put("P_PUR_REQ_ID", request.getPurchaseorderno());
	inParamMap.put("P_PRHDR_RECV_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getReceivedate()));
	inParamMap.put("P_PRHDR_DISCOUNT_TYPE", request.getDiscount());
	inParamMap.put("P_PRHDR_DISCOUNT_VALUE", request.getDiscountvalue());
	inParamMap.put("P_PRHDR_TAX_ID", request.getOrderedtax());
	inParamMap.put("P_PRHDR_INCLUSIVE", request.getInclusive());
	inParamMap.put("P_PRHDR_SHIPPING_COST", request.getShippingcost());
	inParamMap.put("P_PRHDR_NOTES", request.getNotes());
	inParamMap.put("P_PRHDR_PAID_AMT", 0);
	inParamMap.put("P_REC_BANK", 0);
	inParamMap.put("P_REQDAYS", 0);
	inParamMap.put("P_REC_CHEQUE_NO", request.getChequeno());
	inParamMap.put("P_REC_PAYMENT_TYPE", 0);
	inParamMap.put("P_PRHDR_USER_ID", request.getUserid());
	inParamMap.put("P_REC_SUPPLIER_INVOICE_NO", request.getSupplierinvoiceno());
	inParamMap.put("P_REC_SUPPLIER_INVOICE_DATE", request.getSupplierinvoicedate() != null ?  HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getSupplierinvoicedate()) : HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000") );
	inParamMap.put("P_OPRN", "UPD_TOT_AMT");
	inParamMap.put("P_TERMS", request.getTermsid());
	System.out.print("inParamMap         "+inParamMap);
	in = new MapSqlParameterSource(inParamMap);
	jdbcCALL.execute(in);
	System.out.print("resultMap       "+resultMap);
	inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
}
catch(Exception e)
{
	System.out.print("etgeeryryrh     "+e);
	error_log.info(e);
	inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);

}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	@Transactional
	public void savepurchasereceiveitems( final List<HERO_STK_RECEIVESTOCKITEMS> list,String purchaseordernumber, final String taxpertype) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_RECEIVE_ITEMS( ?,?, ?,?,?,?,?, ?,?,?, ?,?,?,?,?,?,?,?,?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}

			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				try
				{
				HERO_STK_RECEIVESTOCKITEMS item = list.get(i);
				Double subtotalwithTax = (double) 12;
				/*String s = item.getFulluomsel();
				String[] parts = s.split("-"); 
				int fulluomsel = Integer.parseInt(parts[1]);
				log.info("fulluomsel are "+fulluomsel);
				log.info("subtotalwithTax are "+subtotalwithTax);
				log.info(list.get(i));
				String s1 = item.getLooseuomsel();
				String[] parts1 = s1.split("-"); 
				int looseuomsel = Integer.parseInt(parts1[1]);
				log.info("looseuomsel are "+looseuomsel);*/
				
				//System.out.print("looseuomsel    "+looseuomsel);
				log.info("Item values are      "+item.getTaxper()+"   "+item.getProductid()+"   "+item.getReceivingqty()+"  "
						                        + " "+item.getBonusqty()+"    "+item.getHsncode()+"  "+item.getPurchaseprice()+"   "+item.getBatchno()+"  "
						                   		+ " "+item.getBatchno()+"   "+item.getSgst()+"  "+item.getSalesprice()+"  "+item.getExpirydate()+"  "
						                   		+"  "+item.getBarcode()+"   "+item.getOprn()+"  "+item.getUserid()+"  "+item.getUompackingsel()+"   "
						                   		+ ""+item.getFullqty()+"    "+item.getFulluomsel()+"  "+item.getLooseqty()+"  "
						                   		+ " "+item.getReceivehdrno()+"  "+item.getReceivingqty()+"  "+item.getReceivingqty()+" "+item.getLooseuomsel());
				
				ps.setString(1, item.getReceivehdrno());
				ps.setString(2, item.getProductid());
				/*ps.setLong(3, fulluomsel);*/
				ps.setLong(3, 0);
				ps.setLong(4, item.getFullqty());
				/*ps.setLong(5, looseuomsel);*/
				ps.setLong(5, 0);
				ps.setLong(6, item.getLooseqty());
				ps.setLong(7, item.getUompackingsel());
				ps.setString(8, item.getReceivingqty());
				ps.setString(9, item.getBonusqty());
				ps.setString(10, item.getPurchaseprice());
				ps.setString(11, item.getSalesprice());
				ps.setString(12, item.getTaxper());
				
				if(taxpertype.equals("inclusive")){
					/*Double subtotal = Double.parseDouble(item.getReceivingqty()) * Double.parseDouble(item.getPurchaseprice());
					subtotalwithTax = (subtotal + (subtotal * Double.parseDouble(item.getTaxper()) )/100  );*/
					Double subtotal = Double.parseDouble(item.getReceivingqty()) * Double.parseDouble(item.getPurchaseprice());
					subtotalwithTax = (subtotal + (subtotal * ( Double.parseDouble(item.getTaxper()) + Double.parseDouble(item.getSgst())) )/100);
			
				}else if(taxpertype.equals("exclusive")){
					subtotalwithTax =  Double.parseDouble(item.getReceivingqty()) * Double.parseDouble(item.getPurchaseprice());
					
				}
				else{
					/*Double subtotal = Double.parseDouble(item.getReceivingqty()) * Double.parseDouble(item.getPurchaseprice());
					subtotalwithTax = (subtotal + (subtotal * Double.parseDouble(item.getTaxper()) )/100  );*/
					Double subtotal = Double.parseDouble(item.getReceivingqty()) * Double.parseDouble(item.getPurchaseprice());
					subtotalwithTax = (subtotal + (subtotal * ( Double.parseDouble(item.getTaxper()) + Double.parseDouble(item.getSgst())) )/100);
					
				}
				ps.setString(13, String.valueOf(subtotalwithTax));
				ps.setString(14, item.getUserid());
				ps.setString(15, item.getBatchno());
				ps.setString(16, item.getBarcode());
				ps.setDate(17, (Date) HEROHOSURINVENTORYUTIL.convertToSQLDate(item.getExpirydate()));
				ps.setString(18, item.getOprn());
				ps.setString(19, item.getSgst());
				ps.setString(20, item.getHsncode());
				log.info("subtotalwithTax are "+subtotalwithTax);
				System.out.print("subtotalwithTax are "+subtotalwithTax);
				}
				
				catch(Exception e)
				{
					System.out.print("cache items are "+e);
					e.printStackTrace();
				}
			}
		});
		}
		catch( Exception e )
		{
			System.out.print(e);
			error_log.info(e);
			throw e;
		}
	}

	@Override
	public HERO_STK_RESPONSEINFO pobarcode(String productid,String batchno) {
		// TODO Auto-generated method stub
		log.info("Welcme to pobarcode");
		
		log.info("productid   "+productid+"   batchno"+batchno);
		String barcodequery = "SELECT `prec_barcode` bar_code, DATE_FORMAT(`prec_expiry_date`,'%e/%c/%Y') expiry_date,"
				+ " `prec_pur_price` pur_price,  `prec_sel_price` sale_price"
				+ "  FROM `hero_stock_purchase_received_dtl`"+
								" WHERE `prec_product_id` = '"+productid+"' AND `prec_batchno` = '"+batchno+"'";
		
		log.info("barcodequery   "+barcodequery);
		
		@SuppressWarnings("unchecked")
		List<Object> barcodeList = jdbcTemplate.query(barcodequery.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("barcode", rs.getString("bar_code"));
				map.put("expirydate", rs.getString("expiry_date"));
				map.put("purprice", rs.getString("pur_price"));
				map.put("saleprice", rs.getString("sale_price"));
				return map;
			}
		});
		
		if(barcodeList.size()>0){
		
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("barcode", barcodeList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(map);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		else{
			Map<String,Object> map = new HashMap<String, Object>();
			map.put("barcode", barcodeList);
			
			inventoryResponseOBJ.setResponseType("N");
			inventoryResponseOBJ.setResponseObj(map);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		return inventoryResponseInfoOBJ;
	}


	public HERO_STK_RESPONSEINFO pocheckbarcode(String checkbarcode, String productid, String batchno) {
		// TODO Auto-generated method stub
		log.info("Welcme to checkpobarcode");
		
		log.info("checkbarcode   "+checkbarcode+"  productid"+productid+"  batchno "+batchno);
		String checkbarcodequery = "SELECT `prec_barcode` bar_code FROM `hero_stock_purchase_received_dtl`"
				+ " WHERE `prec_barcode` = '"+checkbarcode+"' AND `prec_product_id` != '"+productid+"'"
						+ " AND `prec_batchno` != '"+batchno+"'";                                                                                                                                                                                                                                                                              
		log.info("barcodequery   "+checkbarcodequery);
		
		@SuppressWarnings("unchecked")
		List<Object> checkbarcodeList = jdbcTemplate.query(checkbarcodequery.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("barcode", rs.getString("bar_code"));
				return map;
			}
		});
		
		if(checkbarcodeList.size()>0){
		
			Map<String,Object> map = new HashMap<String, Object>();

			inventoryResponseOBJ.setResponseType("S");
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		else{
			Map<String,Object> map = new HashMap<String, Object>();
			
			inventoryResponseOBJ.setResponseType("N");
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		}
		return inventoryResponseInfoOBJ;
	}

	
	@Override
	public HERO_STK_RESPONSEINFO getReceiveList(String purchaseid,String purchaseheaderid,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		log.info(purchaseheaderid);
		log.info(purchaseid);
		final HttpSession session = httpRequest.getSession();
		
		StringBuilder query = new StringBuilder("SELECT `prhdr_id`,S.`supplier_id`,DATE_FORMAT(prhdr_recv_date,'%e/%c/%Y')prhdr_recv_date,`prhdr_bill_no`,purchase_code,`purchase_refer_no`,"
				+ "`prhdr_grand_total_amt`,COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,`prhdr_inclusive`,`paymode`paymodeid,`opttype`,"
				+ "(`prhdr_grand_total_amt` - (COALESCE(`prhdr_paid_amount`,0)))tobe_paid,`prhdr_discount_amt`,`prhdr_discount_type`,`prhdr_discount_value`,"
				+ "`prhdr_shipping_cost`,prhdr_paid_status,CONCAT(`supplier_first_name`)suppliername,`ct_name`paymode,`reqdays`, "
				+ "`address`,`city`,`state`,`zipcode`,`country_id` FROM `hero_stock_purchase_receive_hdr` JOIN `hero_stock_purchase` A ON `purchase_code` = `pur_req_id` "
				+ "LEFT JOIN `hero_stock_supplier` S ON S.`supplier_id` = A.`supplier_id`  LEFT JOIN `hero_admin_cash_type` b ON b.`ct_id` = S.`paymode`"
				+ " WHERE `purchase_id` =  "+ purchaseid+" ORDER BY `prhdr_id` ASC");
		 
		log.info("getReceiveList query       "+query);
		log.info("getReceiveList values are    "+purchaseid+"   "+purchaseheaderid);
		
		@SuppressWarnings("unchecked")
		List<Object> purchaseorderList = jdbcTemplate.query(query.toString(), new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("purchaseid", rs.getString("prhdr_id"));
				map.put("receiveddate", rs.getString("prhdr_recv_date"));
				map.put("billno", rs.getString("prhdr_bill_no"));
				map.put("purchasecode", rs.getString("purchase_code"));
				map.put("referenceno", rs.getString("purchase_refer_no"));
				map.put("grandtotal", rs.getString("prhdr_grand_total_amt"));
				map.put("paidamount", rs.getString("prhdr_paid_amount"));
				map.put("tobepaid", rs.getString("tobe_paid"));
				map.put("inorexclusive", rs.getString("prhdr_inclusive"));
				map.put("discountamt", rs.getString("prhdr_discount_amt"));
				map.put("discounttype", rs.getString("prhdr_discount_type"));
				map.put("discountvalue", rs.getString("prhdr_discount_value"));
				map.put("shippingamt", rs.getString("prhdr_shipping_cost"));
				map.put("paymode", rs.getString("paymode"));
				map.put("paymodeid", rs.getString("paymodeid"));
				map.put("opttype", rs.getString("opttype"));
				map.put("reqdays", rs.getString("reqdays"));
				
				if(rs.getInt("prhdr_paid_status") <= 3)
				{	
					String usertype = (String) session.getAttribute("usertypestr");
					if(usertype.equals("2")){
						map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION);	
					}else{
						map.put("action", " ");	
					}
				}
				else if(rs.getInt("prhdr_paid_status") == 4)
				{
					map.put("action", "");	
				}
				else
				{
					map.put("action", "");	
				}
				
				map.put("suppliername", rs.getString("suppliername"));
				map.put("address", rs.getString("address"));
				map.put("city", rs.getString("city"));
				map.put("state", rs.getString("state"));
				map.put("zipcode", rs.getString("zipcode"));
				map.put("country", rs.getString("country_id"));
				String paymode = HEROHOSURINVENTORYUTIL.getCreditmode(rs.getString("supplier_id"),rs.getString("prhdr_id"),rs.getString("paymode"),jdbcTemplate);		
				log.info(paymode);
				map.put("paymode", paymode);
				//map.put("paymodeid", paymode);
				
				return map;
			}
		});
		log.info(purchaseorderList);
		
		/*String itemquery = "SELECT PUR_PRODUCT_ID,COALESCE(prec_recving_quantity,0)prec_recving_quantity,COALESCE(prec_pur_price,0)prec_pur_price,"
				+ "COALESCE((`prec_recving_quantity`*`prec_pur_price`),0.00)recv_price,`product_name`,COALESCE(FORMAT(`prhdr_total_amt`,2),0.00)prhdr_total_amt,"
				+ "COALESCE(FORMAT(`prhdr_tax_amt`,2),0.00)prhdr_tax_amt,COALESCE(FORMAT(`prhdr_grand_total_amt`,2),0.00)prhdr_grand_total_amt,"
				+ "`purchase_refer_no`,DATE_FORMAT(`purchase_date`,'%e/%c/%Y')purchase_date FROM hero_stock_purchase,hero_stock_purchase_request a "
				+ "LEFT JOIN hero_stock_purchase_received_dtl ON `pur_product_id` = `prec_product_id` LEFT JOIN `hero_stock_purchase_receive_hdr` ON `prhdr_id` = `prec_hdr_id` "
				+ "LEFT JOIN `hero_stock_product` ON `product_id` = A.`pur_product_id` WHERE a.`pur_req_id` = `purchase_code` AND `prhdr_id` = "+purchaseheaderid;*/
		
		String itemquery = "SELECT `pur_product_id`,`pur_quantity`,COALESCE(DATE_FORMAT(`prhdr_recv_date`,'%e/%c/%Y'),'-')receive_date,"
				+ "COALESCE(`prhdr_bill_no`,'-')billno,`product_name`,`prhdr_inclusive`,COALESCE(prhdr_total_amt,'0.00')prhdr_total_amt,"
				+ "COALESCE(prec_pur_price,'0.00')prec_pur_price,COALESCE((`prec_recving_quantity`*`prec_pur_price`),'0.00')recv_price,"
				+ "COALESCE(prec_recving_quantity,'0')prec_recving_quantity,`prec_tax_per`,"
				
				+ " COALESCE(prec_full_qty,'0')prec_full_qty, COALESCE((SELECT `unit`FROM `hero_admin_unit_type` G WHERE "
				+ " G.`unit_type_id` = `prec_full_uom`),(SELECT `unit` FROM `hero_stock_purchase_received_dtl` P JOIN "
				+ " `hero_stock_unit_type_config` Q  ON  P.`hsuts_id` = Q.`hsuts_id` JOIN `hero_admin_unit_type` R ON  "
				+ " R.`unit_type_id` = Q.`hsuts_full_uom` WHERE `prec_hdr_id` = '"+purchaseid+"' LIMIT 1))prec_full_uom,"
				+ " COALESCE(prec_loose_qty,'0')prec_loose_qty, COALESCE((SELECT `unit`FROM `hero_admin_unit_type` G WHERE G.`unit_type_id` "
				+ " = `prec_loose_uom`),(SELECT `unit` FROM `hero_stock_purchase_received_dtl` P JOIN `hero_stock_unit_type_config` Q  ON "
				+ " P.`hsuts_id` = Q.`hsuts_id` JOIN `hero_admin_unit_type` R ON R.`unit_type_id` = Q.`hsuts_loose_uom` "
				+ " WHERE `prec_hdr_id` = '"+purchaseid+"' LIMIT 1))prec_loose_uom,"
				
				+ "COALESCE(prhdr_tax_amt,'0.00')prhdr_tax_amt,"
				+ "COALESCE(prhdr_grand_total_amt,'0.00')prhdr_grand_total_amt,purchase_refer_no,COALESCE(DATE_FORMAT(`purchase_date`,'%e/%c/%Y'),'-')purchase_date,`prec_free_count`,`prec_batchno`,h.`unit` unitnetdesc "
				+ " FROM `hero_stock_purchase_request` a JOIN`hero_stock_purchase` b ON `purchase_code` = a.`pur_req_id`"
				+ "LEFT JOIN `hero_stock_product` d ON d.`product_id` = a.`pur_product_id` "
				+ "LEFT JOIN `hero_stock_purchase_receive_hdr` c ON b.`purchase_code` = c.`pur_req_id` "
				+ "LEFT JOIN `hero_stock_purchase_received_dtl` e ON e.`prec_hdr_id` = c.`prhdr_id`  "
				+ "AND a.`pur_product_id` = e.`prec_product_id` LEFT JOIN `hero_admin_unit_type` h on h.`unit_type_id` = d.`unit_type_id`"
				+ "WHERE c.`pur_req_id` = '"+purchaseheaderid+"' AND `prhdr_id` = "+purchaseid;
		
		log.info("itemquery       "+itemquery);
		
		@SuppressWarnings("unchecked")
		List<Object> itemList = jdbcTemplate.query(itemquery, new RowMapper() {
			int count = 0;
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				
				Map<String, Object> map = new HashMap<String, Object>();
				count++;
				map.put("index", count);
				map.put("inorexclusive", rs.getString("prhdr_inclusive"));
				map.put("productname", rs.getString("product_name"));
				map.put("recvqty", rs.getString("prec_recving_quantity"));
				map.put("fullqty", rs.getString("prec_full_qty")+" "+rs.getString("prec_full_uom"));
				map.put("looseqty", rs.getString("prec_loose_qty")+" "+rs.getString("prec_loose_uom"));
				map.put("productprice", rs.getString("prec_pur_price"));
				map.put("recvingamt", rs.getString("recv_price"));
				map.put("totalamt", rs.getString("prhdr_total_amt"));
				map.put("taxamt", rs.getString("prhdr_tax_amt"));
				map.put("grandtotalamt", rs.getString("prhdr_grand_total_amt"));
				map.put("refno", rs.getString("purchase_refer_no"));
				map.put("purchasedate", rs.getString("purchase_date"));
				map.put("bonusqty", rs.getString("prec_free_count"));
				map.put("batchno", rs.getString("prec_batchno"));
				map.put("receivedate", rs.getString("receive_date"));
				map.put("taxper", rs.getString("prec_tax_per"));
				map.put("billno", rs.getString("billno"));
				map.put("unitnetdescwithqty", rs.getString("prec_recving_quantity")+" "+rs.getString("unitnetdesc"));
				
				return map;
			}
		});
		log.info("purchaseorderList       "+purchaseorderList);
		log.info("itemList" +itemList);
		
		String firstBillNo = null;
		if(purchaseorderList != null && purchaseorderList.size() > 0)
		{
			Map<String, Object> purchaseMap = (Map<String, Object>) purchaseorderList.get(0);
			firstBillNo = (String) purchaseMap.get("billno");
			
			log.info("firstBillNo  "+firstBillNo);
		}
		
		String billQuery = "SELECT a.`prhdr_id`,DATE_FORMAT(prhdr_recv_date,'%e/%c/%Y')prhdr_recv_date,`prhdr_bill_no`,COALESCE(`supplier_invoice_no`, ' ')supplier_invoice_no,purchase_code,`purchase_refer_no`,"
				+ "`prhdr_grand_total_amt`,COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,(`prhdr_grand_total_amt` - (COALESCE(`prhdr_paid_amount`,0)))tobe_paid,"
				+ "`ps_status_name`,`ct_name`,pp_balance_amt,pp_cheque_no,pp_paid_amt,DATE_FORMAT(`pp_payment_time`,'%e/%c/%Y - %H:%i')pp_payment_time"
				+ " FROM `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` ON `purchase_code` = `pur_req_id` "
				+ "JOIN `hero_stock_purchase_payment` d ON d.`prhdr_id` = a.`prhdr_id` LEFT JOIN `hero_stock_purchase_status` c ON c.`ps_id` = d.`pp_payment_status` "
				+ "LEFT JOIN `hero_admin_cash_type` ON `ct_id` = `pp_payment_type` WHERE `purchase_id` = "+purchaseid+" and prhdr_bill_no = '"+firstBillNo+"'";
				
				log.info("billQuery      "+billQuery);
				@SuppressWarnings("unchecked")
				List<Object> billList = jdbcTemplate.query(billQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("billno", rs.getString("prhdr_bill_no"));
				map.put("receiveddate", rs.getString("prhdr_recv_date"));
				map.put("status", rs.getString("ps_status_name"));
				map.put("paymenttype", rs.getString("ct_name"));
				map.put("grandtotal", rs.getString("prhdr_grand_total_amt"));
				map.put("balanceamt", rs.getString("pp_balance_amt"));
				map.put("chequeno", rs.getString("pp_cheque_no"));
				map.put("paidamt", rs.getString("pp_paid_amt"));
				map.put("paidtime", rs.getString("pp_payment_time"));
				map.put("supplierinvoiceno", rs.getString("supplier_invoice_no"));
				
				Map<String, Object> supplierdefaultdetailsmap = getPayMode(rs.getString("prhdr_id"));
				String paymode = (String) supplierdefaultdetailsmap.get("paymode");
				String reqdays = (String) supplierdefaultdetailsmap.get("reqdays");
				
				map.put("paymode", paymode);
				map.put("reqdays", reqdays);
				
				log.info(paymode);
				
				return map;
			}
		});
		
				log.info("billList      "+billList);
		
		String subtotal = "0.00",taxamt="0.00",grandtotalamt="0.00",refno="",purchasedate="",recievedate="",discounttype="",discountvalue="",discountamt="",shippingcost="",
				suppliername="",address="",city="",state="",zipcode="",country="";
		
		if(purchaseorderList != null && purchaseorderList.size() == 0)
		{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("purchaseid", "");
			map.put("receiveddate", "");
			map.put("billno", "");
		}
		else
		{
			Map<String, Object> purchaseMap = (Map<String, Object>) purchaseorderList.get(0);
			
			log.info("purchaseMap       "+purchaseMap);
			
			suppliername = (String)purchaseMap.get("suppliername");
			address = (String)purchaseMap.get("address");
			city = (String)purchaseMap.get("city");
			state = (String)purchaseMap.get("state");
			zipcode = (String)purchaseMap.get("zipcode");
			country = (String)purchaseMap.get("country");
		}
		
		String userid = String.valueOf(session.getAttribute("uid"));
		String currencyDetailQuery = "SELECT `gcs_html_code` FROM `hero_admin_currency` A  JOIN `hero_user` B ON B.`currencyid` = A.`currency_id`"
				+ " JOIN `hero_global_currency_symbols` C ON C.`gcs_id` = A.`CURR_SYMBOL`  WHERE `userid` = '"+userid+"'";
		
		log.info("currencyDetailQuery      "+currencyDetailQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> currencyDetailList = jdbcTemplate.query(currencyDetailQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("currencycode", rs.getString("gcs_html_code"));
				
				return map;
			}
		});
		
		log.info("currencyDetailList      "+currencyDetailList+"userid"+session.getAttribute("uid"));
		
		String currency_code = "";
		if(currencyDetailList != null && currencyDetailList.size() > 0)
		{
			Map<String, Object> currencyMap = (Map<String, Object>) currencyDetailList.get(0);
			currency_code = (String) currencyMap.get("currencycode");
			
		}
		
		log.info("currency code "+ currency_code);
		log.info("Supplier Details are       "+suppliername+"   "+address+"   "+city+"   "+state+"   "+zipcode+"   "+country);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("purchaseorderList", purchaseorderList);
		map.put("itemList", itemList);
		/*if(billList != null && billList.size() > 0)
			map.put("billList", billList);
			else
			map.put("billList", defaultBillList());*/
		log.info("billList.size() "+billList.size());
		if(billList != null && billList.size() > 0)
			
			map.put("billList", billList);
			
		else{
			Map<String, Object> supplierdefaultdetailsmap = getPayMode(purchaseid);
				String paymode = (String) supplierdefaultdetailsmap.get("paymode");
				String reqdays = (String) supplierdefaultdetailsmap.get("reqdays");
				String supplierinvoiceno = (String) supplierdefaultdetailsmap.get("supplierinvoiceno");
			
				map.put("billList", defaultBillList(paymode,reqdays,supplierinvoiceno));
				map.put("paymode", paymode);
				map.put("reqdays", reqdays);
				log.info(paymode);
			
			}
		        log.info(billList);
		
		
		if(itemList != null && itemList.size() > 0)
		{
			Map<String, Object> itemMap = (Map<String, Object>) itemList.get(0);
			subtotal = (String) itemMap.get("totalamt");
			taxamt = (String) itemMap.get("taxamt");
			grandtotalamt = (String) itemMap.get("grandtotalamt");
			refno=(String)itemMap.get("refno");
			purchasedate = (String)itemMap.get("purchasedate");
			recievedate = (String)itemMap.get("receivedate");
			discounttype = (String)itemMap.get("discounttype");
			discountvalue = (String)itemMap.get("discountvalue");
			discountamt = (String)itemMap.get("discountamt");
			shippingcost = (String)itemMap.get("shippingamt");
			
		}
		
		log.info("Values are        "+subtotal+"   "+taxamt+"   "+grandtotalamt+"   "+refno+"   "+purchasedate+"     "+map.get("purchasedate"));
		
		map.put("subtotal", subtotal);
		map.put("taxamt", taxamt);
		map.put("grandtotalamt", grandtotalamt);
		map.put("refno", refno);
		map.put("purchasedate", purchasedate);
		map.put("recievedate", recievedate);
		map.put("discounttype", discounttype);
		map.put("discountvalue", discountvalue);
		map.put("discountamt", discountamt);
		map.put("shippingcost", shippingcost);
		map.put("currencycode", currency_code);
		
		map.put("suppliername", suppliername);
		map.put("address", address);
		map.put("city", city);
		map.put("state", state);
		map.put("zipcode", zipcode);
		map.put("country", country);
		
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	public List<Object> defaultBillList(String paymode, String reqdays, String supplierinvoiceno)
	{
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("billno", "-");
		map.put("receiveddate", "-");
		map.put("status", "Pending");
		map.put("paymenttype", "-");
		map.put("grandtotal", "");
		map.put("balanceamt", "");
		map.put("chequeno", "");
		map.put("paidamt", "0.00");
		map.put("paidtime", "-");
		map.put("paymode", paymode);
		map.put("reqdays", reqdays);
		map.put("supplierinvoiceno", supplierinvoiceno);
		list.add(map);
		return list;
	}

	@Override
	public HERO_STK_RESPONSEINFO updatereceiveorderpayment(
			String receiveStockData) {

		log.info("receiveStockData        "+receiveStockData);
		
		try
		{
			HERO_STK_RECEIVESTOCKREQUEST request = (HERO_STK_RECEIVESTOCKREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(receiveStockData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_RECEIVESTOCKREQUEST");
			
			log.info("Values are     "+request.getDiscount()+"     "+request.getNotes()+"     "+request.getOrderedtax()+"     "+request.getPreceiveorderid()+"     "
					+request.getPrecid()+"     "+request.getPurchaseorderno()+"     "+request.getReceivedate()+"     "+request.getReceivelist()+"     "
					+request.getShippingcost()+"   "+request.getPaidamount()+"  "+request.getReqdays()+"   "+request.getUserid());
			
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_RECV_HDR");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_PRHDR_ID", request.getPrecid());
			inParamMap.put("P_PUR_REQ_ID", request.getPurchaseorderno());
			inParamMap.put("P_PRHDR_RECV_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getReceivedate()));
			inParamMap.put("P_PRHDR_DISCOUNT_TYPE", request.getDiscount());
			inParamMap.put("P_PRHDR_DISCOUNT_VALUE", request.getDiscountvalue());
			inParamMap.put("P_PRHDR_TAX_ID", request.getOrderedtax());
			inParamMap.put("P_PRHDR_SHIPPING_COST", request.getShippingcost());
			inParamMap.put("P_PRHDR_NOTES", request.getNotes());
			inParamMap.put("P_PRHDR_PAID_AMT", request.getPaidamount());
			inParamMap.put("P_REC_BANK", request.getBank());
			inParamMap.put("P_REC_CHEQUE_NO", request.getChequeno());
			inParamMap.put("P_REC_PAYMENT_TYPE", request.getPaymenttype());
			inParamMap.put("P_REQDAYS", request.getReqdays());
			inParamMap.put("P_PRHDR_USER_ID", request.getUserid());
			inParamMap.put("P_PRHDR_INCLUSIVE", "");
			inParamMap.put("P_REC_SUPPLIER_INVOICE_NO", request.getSupplierinvoiceno());
			inParamMap.put("P_REC_SUPPLIER_INVOICE_DATE", request.getSupplierinvoicedate() != null ?  HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getSupplierinvoicedate()) : HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000") );
			inParamMap.put("P_OPRN", "UPD_PAYMT");
			inParamMap.put("P_TERMS", request.getTermsid());
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO getpurchasereturndata(String purchaseorderid) {
		// TODO Auto-generated method stub
		
		log.info("purchaseorderid       "+purchaseorderid);

		String returnQuery = "SELECT `prec_dtl_id`,`prec_product_id`,`product_name`,`prec_batchno`,`prec_sel_price`,`prec_recving_quantity`,"
				/*+ "`prec_recving_quantity` product_count,`prhdr_discount_type`,`prhdr_discount_value`,`prhdr_tax_amt`,`prec_sub_total`,"*/
				+ "`product_count` product_count,`prhdr_discount_type`,`prhdr_discount_value`,`prhdr_tax_amt`,`prec_sub_total`,"
				/*+ " CONCAT(`prec_full_qty`,' ',e.`unit`)prec_full_qty,CONCAT(`prec_loose_qty`,' ',f.`unit`)prec_loose_qty,"*/
				+ " CONCAT(`prec_full_qty`)prec_full_qty,CONCAT(`prec_loose_qty`)prec_loose_qty,e.`unit`prec_full_uom,f.`unit`prec_loose_uom,"
				+ "`prhdr_discount_amt`,`PREC_RETURN_QTY`,`prhdr_return_charge`,`prhdr_return_notes`,`product_count` stock_count,"
				+ "DATE_FORMAT(COALESCE(`prhdr_return_date`,NOW()),'%d/%m/%Y')prhdr_return_date,prec_free_count,prec_hdr_id,`hsuts_id` uompackingid,k.`unit` FROM `hero_stock_purchase_receive_hdr` a  "
				+ "JOIN `hero_stock_purchase_received_dtl` b ON a.`prhdr_id` = b.`prec_hdr_id` JOIN `hero_stock` c ON b.`prec_product_id` = c.`product_id` "
				+ "AND b.`prec_batchno` = c.`batch_id` JOIN `hero_stock_product` d ON d.`product_id` = b.`prec_product_id` "
				+ " LEFT JOIN `hero_admin_unit_type`e ON e.`unit_type_id`=`prec_full_uom` LEFT JOIN `hero_admin_unit_type`f ON f.`unit_type_id`=`prec_loose_uom` "
				+ " LEFT JOIN `hero_admin_unit_type` k ON  d.`unit_type_id` =   k.`unit_type_id`  WHERE `prec_hdr_id` = "+purchaseorderid; 
		
		log.info("returnQuery     "+returnQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> returnList = jdbcTemplate.query(returnQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int index) throws SQLException {
						// TODO Auto-generated method stub
						Map<String, Object> map = new HashMap<String, Object>();
						
						map.put("dtlid", rs.getString("prec_dtl_id"));
						map.put("productid", rs.getString("prec_product_id"));
						map.put("productname", rs.getString("product_name"));
						map.put("batchno", rs.getString("prec_batchno"));
						map.put("sellprice", rs.getString("prec_sel_price"));
						map.put("receivingqty", rs.getString("prec_recving_quantity") +" "+rs.getString("unit")) ;
						map.put("unit", rs.getString("unit"));
						/*map.put("productcount", rs.getString("product_count"));*/
						/*int productCount = (rs.getInt("product_count") + rs.getInt("prec_free_count")) - rs.getInt("PREC_RETURN_QTY");*/
						int productCount = (rs.getInt("product_count"));
						int stockCount = rs.getInt("stock_count");
						int returnCount = rs.getInt("PREC_RETURN_QTY");

						map.put("fullqty", rs.getString("prec_full_qty"));
						map.put("looseqty", rs.getString("prec_loose_qty"));
						map.put("fulluom", rs.getString("prec_full_uom"));
						map.put("looseuom", rs.getString("prec_loose_uom"));
						
						if(stockCount < productCount)
						{
							productCount = stockCount;
							returnCount = 0;
						}
						log.info("productCount   "+productCount+"   "+rs.getInt("product_count")+"   "+rs.getInt("prec_free_count")+"   "
								+rs.getInt("PREC_RETURN_QTY"));
						map.put("productcount", productCount);
						map.put("discounttype", rs.getString("prhdr_discount_type"));
						map.put("discountvalue", rs.getString("prhdr_discount_value"));
						map.put("taxamt", "Rs. "+rs.getString("prhdr_tax_amt"));
						map.put("subtotal", rs.getString("prec_sub_total"));
						double phdrDiscountValue =(rs.getDouble("prhdr_discount_value") * rs.getDouble("prec_sub_total") )/100;
						map.put("discountamt", HEROHOSURINVENTORYUTIL.convertDecimalFormat(2, phdrDiscountValue));
						log.info("rs.getDouble('prec_sub_total')"+rs.getDouble("prhdr_discount_value")+"rs.getDouble('prhdr_discount_amt')"+rs.getDouble("prhdr_discount_amt")+"phdrDiscountValue"+phdrDiscountValue);
						/*map.put("returnqtydisp", "<input type='hidden' value='"+rs.getString("PREC_RETURN_QTY")+"' id='actualreturnqtytext"+index+"'>"
								+ "<input style='width:80px;' onchange='updatereturnqty("+index+",this.value,"
						+rs.getString("product_count")+","+rs.getString("prec_free_count")+","+rs.getString("prec_recving_quantity")+")'"
								+ "id='returnqtytext"+index+"' name='returnqtyname"+index+"' class='form-control form-white'  type='number' "
								+ "value='"+rs.getString("PREC_RETURN_QTY")+"'>");*/
						map.put("returnqtydisp", "<input type='hidden' value='"+returnCount+"' id='actualreturnqtytext"+index+"'>"
								+ "<input style='width:80px;' onchange='updatereturnqty("+index+",this.value,"
						+productCount+","+rs.getString("prec_free_count")+","+(rs.getInt("prec_recving_quantity") - returnCount)+")'"
								+ "id='returnqtytext"+index+"' name='returnqtyname"+index+"' class='form-control form-white'  type='number' "
								+ "value='0'>");
						
						/*map.put("fullqtydisp", "<input style='width:80px;' onchange='calcreturnlooseqty("+index+")'"
								+ "id='returnfullqtytext"+index+"' name='returnfullqtyname"+index+"' class='form-control form-white'  type='number' "
								+ "value='0'>");
						
						map.put("looseqtydisp", "<input style='width:80px;' onchange='calcreturnlooseqty("+index+")'"
								+ "id='returnlooseqtytext"+index+"' name='returnlooseqtyname"+index+"' class='form-control form-white'  type='number' "
								+ "value='0'>");*/
					
						map.put("action","<button class=\"delete myBtnTab\" onclick=\"deleteReturnproduct("+index+")\"  > "
								+ "<i class=\"fa fa-trash-o\"></i> </button>");
						
						map.put("index", index);
						map.put("returnqty", rs.getString("PREC_RETURN_QTY"));
						map.put("returncharge", rs.getString("prhdr_return_charge"));
						map.put("returnnotes", rs.getString("prhdr_return_notes"));
						map.put("returndate", rs.getString("prhdr_return_date"));
						map.put("bonusqty", rs.getString("prec_free_count"));
						map.put("receivehdrno", rs.getString("prec_hdr_id"));
						map.put("returnedqty", rs.getString("PREC_RETURN_QTY") +" "+rs.getString("unit"));
						
						String uompackingid = rs.getString("uompackingid");
						HERO_STK_RESPONSEINFO responseInfo = getuomforpacking(uompackingid);
						HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
						
						//map.put("uomsel", response.getResponseObj());
						
						  Map<String, Object> smap = (Map<String, Object>) (response.getResponseObj());
						  List<Object> fulluomPackingList = (List<Object>) smap.get("fulluomPackingList");
						  List<Object> looseuomPackingList = (List<Object>) smap.get("looseuomPackingList");
						  log.info("fulluomPackingList  "+fulluomPackingList);
						  log.info("looseuomPackingList   "+looseuomPackingList);
						  
						  map.put("fulluomPackingList",fulluomPackingList);
						  map.put("looseuomPackingList",looseuomPackingList);
						
						index++;
						
						return map;
					}
				});
		
		String returnDate="",returnCharge="",notes="";
		
		if(returnList.size() > 0)
		{
			Map<String, Object> returnMap = (Map<String, Object>) returnList.get(0);
			returnDate = (String) returnMap.get("returndate");
			returnCharge = (String) returnMap.get("returncharge");
			notes = (String) returnMap.get("returnnotes");
		}
		
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("returnList", returnList);
		map.put("returnDate", returnDate);
		map.put("returnCharge", returnCharge);
		map.put("notes", notes);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(map);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	

	@Override
	public HERO_STK_RESPONSEINFO updatepurchasereturn(String returnstockdata) {
		// TODO Auto-generated method stub
		log.info("returnstockdata      "+returnstockdata);
		
		try
		{
		HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(returnstockdata, 
				"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
		
		log.info("Values are     "+request.getOprn()+"   "+request.getReceiveddate()+"   "+request.getReturncharge()+"   "+request.getNotes());
		
		List<HERO_STK_RECEIVESTOCKITEMS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_RECEIVESTOCKITEMS");
		
		log.info("itemList        "+itemList);
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_RETURN");
		
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		
		inParamMap.put("P_PURCHASE_ID", request.getPurchaseid());
		inParamMap.put("P_RETURN_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getReceiveddate()));
		inParamMap.put("P_RETURN_CHARGE", request.getReturncharge());
		inParamMap.put("P_RETURN_NOTES", request.getNotes());
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
		
		returnpurchaseitems(itemList);
		
		inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		}
		catch(Exception e)
		{
			error_log.info(e);
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Transactional
	public void returnpurchaseitems( final List<HERO_STK_RECEIVESTOCKITEMS> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_RETURN_ITEMS( ?,?,?,?,?, ?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_STK_RECEIVESTOCKITEMS item = list.get(i);
				log.info("Item Values       "+item.getProductid()+"   "+item.getReturnqty()+"   "+item.getReceivehdrno()+"   "
				+item.getBatchno()+"   "+item.getReturndate());
				ps.setString(1, item.getReceivehdrno());
				ps.setString(2, item.getProductid());
				ps.setString(3, item.getBatchno());
				ps.setString(4, item.getReturnqty());
				try {
					ps.setDate(5, (Date)HEROHOSURINVENTORYUTIL.convertToSQLDate(item.getReturndate()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				ps.setString(6, item.getReturnnotes());
				ps.setString(7, "RET_STK");
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}
	
	@Override
	public HERO_STK_RESPONSEINFO getBillDetailsList(String purchaseid,String billno) {
		// TODO Auto-generated method stub
		String billQuery = "SELECT a.`prhdr_id`,DATE_FORMAT(prhdr_recv_date,'%e/%c/%Y')prhdr_recv_date,COALESCE(`supplier_invoice_no`, ' ')supplier_invoice_no,`prhdr_bill_no`,purchase_code,`purchase_refer_no`,"
				+ "`prhdr_grand_total_amt`,COALESCE(`prhdr_paid_amount`,0)prhdr_paid_amount,(`prhdr_grand_total_amt` - (COALESCE(`prhdr_paid_amount`,0)))tobe_paid,"
				+ "`ps_status_name`,`ct_name`,pp_balance_amt,pp_cheque_no,pp_paid_amt,DATE_FORMAT(`pp_payment_time`,'%e/%c/%Y - %H:%i')pp_payment_time"
				+ " FROM `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` ON `purchase_code` = `pur_req_id` "
				+ "JOIN `hero_stock_purchase_payment` d ON d.`prhdr_id` = a.`prhdr_id` LEFT JOIN `hero_stock_purchase_status` c ON c.`ps_id` = d.`pp_payment_status` "
				+ "LEFT JOIN `hero_admin_cash_type` ON `ct_id` = `pp_payment_type` WHERE a.`prhdr_id` = "+purchaseid+" and prhdr_bill_no = '"+billno+"'";
		log.info("billQuery      "+billQuery);
@SuppressWarnings("unchecked")
List<Object> billList = jdbcTemplate.query(billQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("billno", rs.getString("prhdr_bill_no"));
				map.put("receiveddate", rs.getString("prhdr_recv_date"));
				map.put("status", rs.getString("ps_status_name"));
				map.put("paymenttype", rs.getString("ct_name"));
				map.put("grandtotal", rs.getString("prhdr_grand_total_amt"));
				map.put("balanceamt", rs.getString("pp_balance_amt"));
				map.put("chequeno", rs.getString("pp_cheque_no"));
				map.put("paidamt", rs.getString("pp_paid_amt"));
				map.put("paidtime", rs.getString("pp_payment_time"));
				map.put("supplierinvoiceno", rs.getString("supplier_invoice_no"));
				
				return map;
			}
		});
		log.info("billList      "+billList);
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		if(billList != null && billList.size() > 0)
		map.put("billList", billList);
		else
		map.put("billList", defaultBillList());	
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(map);
		 
		
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}


	@Override
	public HERO_STK_RESPONSEINFO deleteBill(String purchaseid, String billno,String userid) {
		// TODO Auto-generated method stub
		
try
{
		
	SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_RECV_HDR");
	Map<String, Object> inParamMap = new HashMap<String, Object>();
	inParamMap.put("P_PRHDR_ID", purchaseid);
	inParamMap.put("P_PUR_REQ_ID", "");
	inParamMap.put("P_PRHDR_RECV_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000"));
	inParamMap.put("P_PRHDR_DISCOUNT_TYPE", "");
	inParamMap.put("P_PRHDR_DISCOUNT_VALUE", 0);
	inParamMap.put("P_PRHDR_TAX_ID", 0);
	inParamMap.put("P_PRHDR_SHIPPING_COST", 0);
	inParamMap.put("P_PRHDR_NOTES", "");
	inParamMap.put("P_PRHDR_PAID_AMT", 0);
	inParamMap.put("P_REC_BANK", 0);
	inParamMap.put("P_REC_CHEQUE_NO", "");
	inParamMap.put("P_REC_PAYMENT_TYPE", 0);
	inParamMap.put("P_PRHDR_USER_ID", userid);
	inParamMap.put("P_REC_SUPPLIER_INVOICE_NO", "");
	inParamMap.put("P_REC_SUPPLIER_INVOICE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000") );
	
	inParamMap.put("P_OPRN", "DEL");

	inParamMap.put("P_PRHDR_INCLUSIVE", "");
	inParamMap.put("P_TERMS", "");
	log.info("inParamMap         "+inParamMap);
	SqlParameterSource in = new MapSqlParameterSource(inParamMap);
	Map<String, Object> resultMap = jdbcCALL.execute(in);
	log.info(resultMap);
	log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
	
	inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
}
catch(Exception e)
{

	error_log.info(e);
	inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);

}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	
	public List<Object> defaultBillList()
	{
		List<Object> list = new ArrayList<Object>();
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("billno", "-");
		map.put("receiveddate", "-");
		map.put("status", "Pending");
		map.put("paymenttype", "-");
		map.put("grandtotal", "");
		map.put("balanceamt", "");
		map.put("chequeno", "");
		map.put("paidamt", "0.00");
		map.put("paidtime", "-");
		map.put("supplierinvoiceno", " ");
		list.add(map);
		return list;
	}
	
	  public Map<String, Object> getPayMode(String prhdr_id){
			
			String supplieridquery = "select supplier_id from `hero_stock_purchase_receive_hdr` a JOIN `hero_stock_purchase` "
					+ "ON `purchase_code` = `pur_req_id` where a.`prhdr_id` = "+prhdr_id;
			log.info("supplieridquery  "+supplieridquery);
			@SuppressWarnings("unchecked")
			List<Object> SupplierList = jdbcTemplate.query(supplieridquery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("supplier_id", rs.getString("supplier_id"));
					return map;
				}
				});
			String paymode = null, reqdays = null,supplierinvoiceno = " ";
			if(SupplierList !=  null &&SupplierList.size() > 0){
				Map<String, Object> Suppliermap = (Map<String, Object>) SupplierList.get(0);
				String supplierid = (String) Suppliermap.get("supplier_id");
				log.info(supplierid);
				List<Object> List = HEROHOSURINVENTORYUTIL.getDefaultCreditmode(supplierid,jdbcTemplate);
				int defaultpaymode  = 0;
				int reqdaysint = 0;
				
				if(List != null)
				{
					Map<String, Object> creditmap = (Map<String, Object>) List.get(0);
					defaultpaymode = (int) creditmap.get("paymode");
					reqdaysint = (int) creditmap.get("reqdays");
					supplierinvoiceno = (String) creditmap.get("supplierinvoiceno");
				}
				
				paymode = HEROHOSURINVENTORYUTIL.getCreditmode(supplierid,prhdr_id,String.valueOf(defaultpaymode),jdbcTemplate);
				reqdays = String.valueOf(reqdaysint);
			}
			
			
			
			Map<String, Object> resultmap = new HashMap<String, Object>();
			resultmap.put("paymode", paymode);
			resultmap.put("reqdays", reqdays);
			resultmap.put("supplierinvoiceno", supplierinvoiceno);
			return resultmap;
		}
	  
	  @Override
		public HERO_STK_RESPONSEINFO saveadddishrequest(String purchaseorderData,
				HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj) {
		  try {
			HERO_STK_ADDPURCHASEORDERDISHITEMS request = (HERO_STK_ADDPURCHASEORDERDISHITEMS) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(purchaseorderData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERDISHITEMS");
			List<HERO_STK_ADDPURCHASEORDERITEMS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS");
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_DISH_REQUEST");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_DISH_HDR_ID", request.getDishhdrid());
			inParamMap.put("P_PRODUCT_ID", " ");
			inParamMap.put("P_FULL_UOM_TYPE_ID", " ");
			inParamMap.put("P_FULL_UOM_QTY", " ");
			inParamMap.put("P_TOTAL_COUNT", " ");
			inParamMap.put("P_LOOSE_UOM_TYPE_ID", " ");
			inParamMap.put("P_LOOSE_UOM_QTY", " ");
			inParamMap.put("P_PACKING_ID", " ");
			inParamMap.put("P_DISH_ID", request.getDishid());
			inParamMap.put("P_DISH_TYPE_ID", request.getDishtypeid());
			inParamMap.put("P_DISH_COUNT", request.getDishcount());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_OPRN", request.getOprn());
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
          String resultType = (String)resultMap.get("out_result_type");
          String hdrid = (String)resultMap.get("out_setup_id");
			
			if(resultType != null && resultType.equals("S"))
			{
				Iterator<HERO_STK_ADDPURCHASEORDERITEMS> item= itemList.iterator();
				while(item.hasNext())
				{
					HERO_STK_ADDPURCHASEORDERITEMS obj = item.next();
				jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_DISH_REQUEST");
				inParamMap = new HashMap<String, Object>();
				inParamMap.put("P_DISH_HDR_ID", hdrid);
				inParamMap.put("P_FULL_UOM_TYPE_ID", obj.getFulluom());
				inParamMap.put("P_FULL_UOM_QTY", obj.getFulluomqty());
				inParamMap.put("P_TOTAL_COUNT", obj.getQuantity());
				inParamMap.put("P_PACKING_ID", 0);
				inParamMap.put("P_PRODUCT_ID",obj.getProductid());
				inParamMap.put("P_DISH_ID", request.getDishid());
				inParamMap.put("P_LOOSE_UOM_TYPE_ID", obj.getLooseuom());
				inParamMap.put("P_LOOSE_UOM_QTY", obj.getLooseuomqty());
				inParamMap.put("P_OPRN", "DTLINS");
				inParamMap.put("P_DISH_TYPE_ID", request.getDishtypeid());
				inParamMap.put("P_DISH_COUNT", request.getDishcount());
				inParamMap.put("P_USER_ID", request.getUserid());
				in = new MapSqlParameterSource(inParamMap);
				jdbcCALL.execute(in);
				}
			}
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		  } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  

		  inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		  			
		  			return inventoryResponseInfoOBJ;
		  
		  
	  }
	  
	  @Override
		public HERO_STK_RESPONSEINFO saveaddpurchaserequest(String purchaseorderData,
				HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj) {

			log.info("purchaseorderData        "+purchaseorderData);
			
			try
			{
			HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(purchaseorderData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
			
			log.info("Values are     "+request.getItemlist());
			
			List<HERO_STK_ADDPURCHASEORDERITEMS> itemList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getItemlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERITEMS");
			
			List<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getDishlist(),"com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERDISHITEMS");
			
			
			log.info(request.getItemlist()+"   "+request.getOprn()+"   "+request.getPaidamt()+"   "+request.getPurchasecode()+"   "+request.getPurchasedate()+
					"   "+request.getPurchaseid()+"   "+request.getPurchasenotes()+"   "+request.getPurchaserefno()+"   "+request.getPurchasestatus()+"   "+
					request.getPurchasetnc()+"   "+request.getReceiveddate()+"   "+request.getReceivestatus()+"   "+
					request.getSupplierid()+"   "+request.getTotalamt()+"   "+request.getUserid());
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_REQUEST");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_PURCHASE_ID", request.getPurchaseid());
			inParamMap.put("P_PURCHASE_CODE", request.getPurchasecode());
			inParamMap.put("P_PURCHASE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate(request.getPurchasedate()));
			inParamMap.put("P_RECEIVED_DATE", null);
			inParamMap.put("P_TOTAL_AMT", 0.0);
			inParamMap.put("P_PURCHASE_REF_NO", request.getPurchaserefno());
			inParamMap.put("P_PAID_AMT", 0.0);
			inParamMap.put("P_SUPPLIER_ID", request.getSupplierid());
			inParamMap.put("P_PURCHASE_NOTES", request.getPurchasenotes());
			inParamMap.put("P_PURCHASE_TNC", request.getPurchasetnc());
			inParamMap.put("P_RECV_STATUS", request.getReceivestatus());
			inParamMap.put("P_STORE_ID", request.getStoreid());
			inParamMap.put("P_PURCHASE_STATUS", request.getPurchasestatus());
			inParamMap.put("P_USER_ID", request.getUserid());
			inParamMap.put("P_DEL_REMARKS", "");
			inParamMap.put("P_OPRN", request.getOprn());
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			String purchaseOrderNo = null;
			String resultType = (String)resultMap.get("out_result_type");
			
			if(resultType != null && resultType.equals("S"))
			{
				
			 
				purchaseOrderNo = (String)resultMap.get("out_purchase_id");	
				log.info("purchaseOrderNo       "+purchaseOrderNo);
				Iterator<HERO_STK_ADDPURCHASEORDERITEMS> itr = itemList.iterator();
				while(itr.hasNext())
				{
					HERO_STK_ADDPURCHASEORDERITEMS obj = itr.next();
					obj.setPurchaseorderid(purchaseOrderNo);
					obj.setOprn(request.getOprn());
					
					itemList.set(Integer.parseInt(obj.getIndex())- 1, obj);
					
					log.info(obj.getPurchaseorderid()+"   "+obj.getOprn()+" index "+obj.getIndex()+" product id "+obj.getProductid());
				}	
				
				Iterator<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishitr = dishList.iterator();
				while(dishitr.hasNext())
				{
					HERO_STK_ADDPURCHASEORDERDISHITEMS obj = dishitr.next();
					obj.setPurchaseid(purchaseOrderNo);
					obj.setOprn("INS");
					
					dishList.set(Integer.parseInt(obj.getIndex())- 1, obj);
					
					log.info(obj.getPurchaseid()+"   ");
				}	
			 
			if(request.getOprn() != null && request.getOprn().equalsIgnoreCase("UPD"))
			{
				purchaseOrderNo = request.getPurchasecode();
				
				jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_REQUEST_ITEMS");
				inParamMap = new HashMap<String, Object>();
				inParamMap.put("P_PURCHASE_ID", request.getPurchasecode());
				inParamMap.put("P_PRODUCT_ID","");
				inParamMap.put("P_QUANTITY", 0);
				inParamMap.put("P_FULL_UOM", 0);
				inParamMap.put("P_FULL_UOM_QTY", 0);
				inParamMap.put("P_LOOSE_UOM", 0);
				inParamMap.put("P_LOOSE_UOM_QTY", 0);
				inParamMap.put("P_UOM_PACKING_ID", 0);
				inParamMap.put("P_OPRN", request.getOprn());
				in = new MapSqlParameterSource(inParamMap);
				jdbcCALL.execute(in);
			}
			
			
			savepurchaserequestitems(itemList, purchaseOrderNo);
			
			if(dishList.size() > 0){
				savepurchaserequestdishitems(dishList, purchaseOrderNo);
				
			}
			
			}
			
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
			
			}
			catch(Exception e)
			{
				error_log.info(e.getMessage());
				e.printStackTrace();
				inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
			}
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		}


		@Transactional
		public void savepurchaserequestitems( final List<HERO_STK_ADDPURCHASEORDERITEMS> list,String purchaseordernumber) 
		{
			try
			{
				
				jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_REQUEST_ITEMS( ?,?,?,?,?, ?,?,?,?,?)}",
					new BatchPreparedStatementSetter() {					
					public int getBatchSize()
					{
						return list.size();
					}
		
				public void setValues(PreparedStatement ps, int i) throws SQLException 
				{
					HERO_STK_ADDPURCHASEORDERITEMS item = list.get(i);
					log.info("Item Values       "+item.getOprn()+"       "+item.getProductid()+"   "+item.getQuantity()+"   "+item.getPurchaseorderid()+"  "
							+item.getFulluom()+"   "+item.getFulluomqty()+"  "+item.getLooseuom()+"   "+item.getLooseuomqty());
					ps.setString(1, item.getPurchaseorderid());
					ps.setString(2, item.getProductid());
					ps.setString(3, item.getQuantity());
					ps.setString(4, item.getFulluom());
					ps.setString(5, item.getFulluomqty());
					ps.setString(6, item.getLooseuom());
					ps.setString(7, item.getLooseuomqty());
					/*ps.setString(8, item.getUompacking());*/
					ps.setString(8, "1");
					ps.setString(9, item.getOprn());
					ps.setString(10, item.getPorid());
				}
			});
			}
			catch( Exception e )
			{
				error_log.info(e);
				throw e;
			}
		}

		
		@Transactional
		public void savepurchaserequestdishitems( final List<HERO_STK_ADDPURCHASEORDERDISHITEMS> dishList,
				String purchaseordernumber) 
		{
			try
			{
				
				jdbcTemplate.batchUpdate("{call P_HERO_STOCK_PURCHASE_REQUEST_DISH_ITEMS( ?,?,?,?)}",
					new BatchPreparedStatementSetter() {					
					public int getBatchSize()
					{
						return dishList.size();
					}
		
				public void setValues(PreparedStatement ps, int i) throws SQLException 
				{
					HERO_STK_ADDPURCHASEORDERDISHITEMS item = dishList.get(i);
					log.info("Item Values       "+item+"          "+item.getPurchaseid());
					ps.setString(1, item.getPurchaseid());
					ps.setString(2, item.getDishname());
					ps.setString(3, item.getDishcount());
					ps.setString(4, item.getOprn());
				}
			});
			}
			catch( Exception e )
			{
				error_log.info(e);
				throw e;
			}
		}



		
		@Override
		public HERO_STK_RESPONSEINFO purchaserequestlist(String purchaseorderid) {
			// TODO Auto-generated method stub
			StringBuilder query = new StringBuilder("SELECT purchase_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y - %H:%i:%s')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y - %H:%i')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,purchase_status,"
					+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
					+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count FROM hero_stock_purchase_status c ,"
					+ "hero_stock_supplier b,"
					+ " hero_stock_purchase_order a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id");
			
			if(purchaseorderid != null && !purchaseorderid.equals("0"))
			{
				query.append(" and purchase_id = "+purchaseorderid);
			}
			query.append(" ORDER BY `purchase_id` DESC");
			
			log.info("purchaseorderlist query       "+query);
			@SuppressWarnings("unchecked")
			List<Object> purchaseorderList = jdbcTemplate.query(query.toString(), new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					Double dpaidamt = rs.getDouble("paid_amt"),dtotalamt = rs.getDouble("total_amt");
					
					map.put("purchaseid", rs.getString("purchase_id"));
					map.put("purchasecode", rs.getString("purchase_code"));
					map.put("purchasecodenavigate", "<a class='color-font' href='purchaserequestview?pid="+rs.getString("purchase_id")+"'>"+rs.getString("purchase_code")+"</a>");
					map.put("purchaserefno", rs.getString("purchase_refer_no"));
					map.put("purchasedate", rs.getString("purchase_date"));
					map.put("receiveddate", rs.getString("received_date"));
					map.put("totalamt", new DecimalFormat("#.##").format(dtotalamt));    
					map.put("totalamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt)));
					map.put("paidamt", new DecimalFormat("#.##").format(dpaidamt));
					map.put("paidamtdisp", "Rs."+(new DecimalFormat("#.##").format(dpaidamt)));
					map.put("supplierid", rs.getString("supplier_id"));
					map.put("purchasenotes", rs.getString("purchase_notes"));
					map.put("purchasetnc", rs.getString("purchase_tnc"));
					map.put("receivestatus", rs.getString("receive_status"));
					map.put("purchasestatus", rs.getString("purchase_status"));
					map.put("balanceamt", new DecimalFormat("#.##").format(dtotalamt - dpaidamt) );
					map.put("balanceamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt - dpaidamt) ));
					
					if(rs.getInt("purchase_status") == 1)
					{
						/*map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION1);*/
						map.put("action","<button class=\"edit myBtnTab\" "
								+ " onclick=\"viewPurchaseReqeustProducts("+rs.getString("purchase_id")+")\"> "
							    + "<i class=\"fa fa-eye\"></i> </button>  ");
					}
					else
					{
						map.put("action", "");
					}
					
					map.put("suppliername", rs.getString("supplier_name"));
					if(rs.getInt("purchase_status") == 3)
					{
						map.put("purchasestatusdesc", "Not Paid");	
					}
					else
					{
						map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
					}
					
					map.put("recvstatusdesc", rs.getString("recvstats_desc"));
					
					return map;
				}
			});
			log.info(purchaseorderList);
			
			if(purchaseorderid != null && !purchaseorderid.equals("0"))
			{
				Map<String, Object> purchaseMap = (Map<String, Object>) purchaseorderList.get(0);
				String purchaseorderno = (String) purchaseMap.get("purchasecode");
				
				/*String itemQuery = "SELECT * FROM hero_stock_purchase_request where pur_req_id = '"+purchaseorderno+"'";*/
				String itemQuery = "SELECT pur_req_id,pur_product_id,pur_quantity,`product_name`,coalesce(`company_name`,'')company_name,pur_full_uom,pur_full_qty,"
						+ "pur_loose_uom,pur_loose_qty,hsuts_id,d.`unit` unitdescnet FROM hero_stock_purchase_order_request a JOIN `hero_stock_product` b ON a.`pur_product_id` = b.`product_id` "
						+ "LEFT JOIN `hero_admin_company` c ON c.`company_id` = b.`manufacturer_id` "
						+ " LEFT JOIN `hero_admin_unit_type` d on d.`unit_type_id` = b.`unit_type_id` WHERE pur_req_id = '"+purchaseorderno+"'";
				
				log.info("itemQuery     "+itemQuery);
				@SuppressWarnings("unchecked")
				List<Object> itemList = jdbcTemplate.query(itemQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int count) throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						count = count + 1;
						map.put("index", count);
						
						String uompackingid = rs.getString("hsuts_id"),fulluom = rs.getString("pur_full_uom"),fulluomsno = "",looseuom=rs.getString("pur_loose_uom"),looseuomsno="";
						
						map.put("purchaseorderid", rs.getString("pur_req_id"));
						map.put("productid", rs.getString("pur_product_id"));
						map.put("quantity", rs.getString("pur_quantity"));
						map.put("unitdescnet", rs.getString("unitdescnet"));
						map.put("productname", rs.getString("product_name").concat(" - "+rs.getString("company_name")));
						map.put("fulluom", fulluom);
						map.put("fulluomqty", rs.getString("pur_full_qty"));
						map.put("looseuom", looseuom);
						map.put("looseuomqty", rs.getString("pur_loose_qty"));
						map.put("uompacking", rs.getString("hsuts_id"));
						map.put("activestatus", "1");
						
						String fulluomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_full_uom` = "+fulluom;
						String looseuomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_loose_uom` = "+looseuom;
						List<Map<String, Object>> fulluomList = jdbcTemplate.queryForList(fulluomidquery);
						List<Map<String, Object>> looseuomList = jdbcTemplate.queryForList(looseuomidquery);
						if(fulluomList != null && fulluomList.size() > 0)
						{
							Map<String, Object> fulluomMap = fulluomList.get(0);
							fulluomsno = String.valueOf((int) fulluomMap.get("hsuts_sno"));
						}
						
						if(looseuomList != null && looseuomList.size() > 0)
						{
							Map<String, Object> looseuomMap = looseuomList.get(0);
							looseuomsno = String.valueOf((int) looseuomMap.get("hsuts_sno")+1);
						}
						
//						map.put("fulluomsel", fulluomsno.concat("-").concat(fulluom));
//						map.put("looseuomsel", looseuomsno.concat("-").concat(looseuom));
						
						map.put("fulluomsel", "");
						map.put("looseuomsel", "");
						
						HERO_STK_RESPONSEINFO responseInfo = getuomforpacking(uompackingid);
						HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
						log.info(response.getResponseObj());
						map.put("uomsel", response.getResponseObj());
						
						return map;
					}
				});
				log.info("itemList        "+itemList);
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap.put("purchaseorderList", purchaseorderList);
				responseMap.put("itemList", itemList);
				
				inventoryResponseOBJ.setResponseType("S");
				inventoryResponseOBJ.setResponseObj(responseMap);
			}
			
			else
			{
				inventoryResponseOBJ.setResponseType("S");
				inventoryResponseOBJ.setResponseObj(purchaseorderList);	
			}
			
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		}
		
		
		
		
		@Override
		public HERO_STK_RESPONSEINFO getPurchaseRequestList(String purchaseid,HttpServletRequest httpRequest) {
			// TODO Auto-generated method stub
			
			log.info(purchaseid);
			final HttpSession session = httpRequest.getSession();
			Map<String, Object> map = new HashMap<String, Object>();
			
			
			String itemquery = " SELECT `purchase_code`,DATE_FORMAT(purchase_date,'%e/%c/%Y - %H:%i:%s')purchase_date,"
					+ " `pur_product_id`,CONCAT(`pur_quantity`,' ',z.`unit`)totalQty,`pur_full_uom`,`pur_loose_uom`,"
					+ "  CONCAT(`pur_full_qty`,' ',f.unit)pur_full_qty,CONCAT(`pur_loose_qty`,' ',e.unit)pur_loose_qty,"
					+ " `product_name`,`ps_status_name`,z.`unit` netunitdesc	FROM `hero_stock_purchase_order`a "
					+ "  JOIN `hero_stock_purchase_order_request`b ON a.`purchase_code`=b.`pur_req_id`	"
					+ "  JOIN `hero_stock_product`c ON c.`product_id`=b.`pur_product_id` "
					+ "  JOIN `hero_stock_purchase_status` d ON d.`ps_id`=a.`purchase_status`"
					+ "  LEFT JOIN `hero_admin_unit_type`e ON e.`unit_type_id`= `pur_loose_uom` "
					+ " LEFT JOIN `hero_admin_unit_type`f ON f.`unit_type_id`= `pur_full_uom` "
					+ " LEFT JOIN `hero_admin_unit_type`z ON z.`unit_type_id`= c.`unit_type_id` WHERE `purchase_id` = "+purchaseid;
			
			log.info("itemquery       "+itemquery);
			
			@SuppressWarnings("unchecked")
			List<Object> itemList = jdbcTemplate.query(itemquery, new RowMapper() {
				int count = 0;
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					
					Map<String, Object> map = new HashMap<String, Object>();
					count++;
					map.put("index", count);
					map.put("purchasecode", rs.getString("purchase_code"));
					map.put("productname", rs.getString("product_name"));
					map.put("purchasedate", rs.getString("purchase_date"));
					map.put("totalQty", rs.getString("totalQty"));
					map.put("fullQty", rs.getString("pur_full_qty"));
					map.put("looseqty", rs.getString("pur_loose_qty"));					
					map.put("netunitdesc", rs.getString("netunitdesc"));		
					
					return map;
				}
			});
			
		
			log.info("itemList" +itemList);
			map.put("itemList", itemList);
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(map);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		}
		@Override
		public HERO_STK_RESPONSEINFO delPurchaseOrderRequest(String purchaseorderData,
				HERO_STK_IADDPURCHASEORDERDAO purchaseorderDAOobj) {
		  
		  log.info("purchaseorderData  "+purchaseorderData);
			
			try
			{
			
			HERO_STK_ADDPURCHASEORDERREQUEST request = (HERO_STK_ADDPURCHASEORDERREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(purchaseorderData, "com.hero.stock.forms.transactions.purchaseorder.HERO_STK_ADDPURCHASEORDERREQUEST");
				
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_PURCHASE_REQUEST");
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_PURCHASE_ID", request.getPurchasereqeustid());
			inParamMap.put("P_PURCHASE_CODE", "");
			inParamMap.put("P_PURCHASE_REF_NO", "");
			inParamMap.put("P_PURCHASE_DATE", HEROHOSURINVENTORYUTIL.convertToSQLDate("00/00/0000"));
			inParamMap.put("P_RECEIVED_DATE", null);
			inParamMap.put("P_TOTAL_AMT", 0.0);
			inParamMap.put("P_PAID_AMT", 0.0);
			inParamMap.put("P_SUPPLIER_ID", "");
			inParamMap.put("P_PURCHASE_NOTES", "");
			inParamMap.put("P_PURCHASE_TNC", "");
			inParamMap.put("P_RECV_STATUS", "");
			inParamMap.put("P_PURCHASE_STATUS", "");
			inParamMap.put("P_USER_ID", "");
			inParamMap.put("P_PURCHASE_REQ_ID", "");
			inParamMap.put("P_DEL_REMARKS", request.getRemarks());
			inParamMap.put("P_OPRN", "DEL");
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			Map<String, Object> resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
			log.info("inventoryResponseOBJ"+inventoryResponseOBJ.getResponseType());
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
		public HERO_STK_RESPONSEINFO purchaseapprovedlist(String purchaseorderid) {
			// TODO Auto-generated method stub
			StringBuilder query = new StringBuilder("SELECT purchase_id,purchase_code,purchase_refer_no,DATE_FORMAT(purchase_date,'%e/%c/%Y - %H:%i:%s')purchase_date,"
					+ "DATE_FORMAT(received_date,'%e/%c/%Y - %H:%i')received_date,total_amt,paid_amt,a.supplier_id,purchase_notes,purchase_tnc,receive_status,"
					+ "purchase_status,CONCAT(supplier_first_name) supplier_name,ps_status_name purchase_status_desc,purchase_status,"
					+ "(SELECT ps_status_name FROM hero_stock_purchase_status d WHERE d.ps_id = a.receive_status)recvstats_desc,"
					+ "(SELECT COUNT(*) FROM `hero_stock_purchase_receive_hdr` WHERE `pur_req_id` = `purchase_code`) purchase_count"
					+ " FROM hero_stock_purchase_status c ,hero_stock_supplier b,"
					+ " hero_stock_purchase_order a WHERE a.supplier_id = b.supplier_id AND a.purchase_status = c.ps_id");
			
			if(purchaseorderid != null && !purchaseorderid.equals("0"))
			{
				query.append(" and purchase_id = "+purchaseorderid);
			}
			query.append(" ORDER BY `purchase_id` DESC");
			
			log.info("purchaseorderlist query       "+query);
			@SuppressWarnings("unchecked")
			List<Object> purchaseorderList = jdbcTemplate.query(query.toString(), new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					Map<String, Object> map = new HashMap<String, Object>();
					
					Double dpaidamt = rs.getDouble("paid_amt"),dtotalamt = rs.getDouble("total_amt");
					
					map.put("purchaseid", rs.getString("purchase_id"));
					map.put("purchasecode", rs.getString("purchase_code"));
					map.put("purchasecodenavigate", "<a class='color-font' href='purchaserequestview?pid="+rs.getString("purchase_id")+"'>"+rs.getString("purchase_code")+"</a>");
					map.put("purchaserefno", rs.getString("purchase_refer_no"));
					map.put("purchasedate", rs.getString("purchase_date"));
					map.put("receiveddate", rs.getString("received_date"));
					map.put("totalamt", new DecimalFormat("#.##").format(dtotalamt));    
					map.put("totalamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt)));
					map.put("paidamt", new DecimalFormat("#.##").format(dpaidamt));
					map.put("paidamtdisp", "Rs."+(new DecimalFormat("#.##").format(dpaidamt)));
					map.put("supplierid", rs.getString("supplier_id"));
					map.put("purchasenotes", rs.getString("purchase_notes"));
					map.put("purchasetnc", rs.getString("purchase_tnc"));
					map.put("receivestatus", rs.getString("receive_status"));
					map.put("purchasestatus", rs.getString("purchase_status"));
					map.put("balanceamt", new DecimalFormat("#.##").format(dtotalamt - dpaidamt) );
					map.put("balanceamtdisp", "Rs."+(new DecimalFormat("#.##").format(dtotalamt - dpaidamt) ));
					
					if(rs.getInt("purchase_status") == 1)
					{
						/*map.put("action", HEROHOSURINVENTORYUTIL.DATATABLE_ACTION1);*/
						map.put("action","<button class=\"edit myBtnTab\" "
								+ " onclick=\"viewPurchaseReqeustProducts("+rs.getString("purchase_id")+")\"> "
							    + "<i class=\"fa fa-eye\"></i> </button>  ");
					}
					else
					{
						map.put("action", "");
					}
					
					map.put("suppliername", rs.getString("supplier_name"));
					if(rs.getInt("purchase_status") == 3)
					{
						map.put("purchasestatusdesc", "Not Paid");	
					}
					else
					{
						map.put("purchasestatusdesc", rs.getString("purchase_status_desc"));
					}
					
					map.put("recvstatusdesc", rs.getString("recvstats_desc"));
					
					return map;
				}
			});
			log.info(purchaseorderList);
			
			if(purchaseorderid != null && !purchaseorderid.equals("0"))
			{
				Map<String, Object> purchaseMap = (Map<String, Object>) purchaseorderList.get(0);
				String purchaseorderno = (String) purchaseMap.get("purchasecode");
				String cgstquery = "SELECT `TAX_ID`,`TAX_RATE`,`TAX_NAME` FROM `hero_admin_tax` where `STATUS_ID`=1";
				final List<Map<String, Object>> TAXList = jdbcTemplate.queryForList(cgstquery);
				/*String itemQuery = "SELECT * FROM hero_stock_purchase_request where pur_req_id = '"+purchaseorderno+"'";*/
				String itemQuery = "SELECT pur_req_id,pur_product_id,"
					/*	+ "pur_quantity,"*/
						+" pur_quantity - COALESCE((SELECT SUM(`pur_quantity`) FROM `hero_stock_purchase_request` E WHERE E.`por_id`=a.`pr_id`),0)pur_quantity, "
						+ "`product_name`,coalesce(`company_name`,'')company_name,pur_full_uom,pur_full_qty,`unit_rate`,"
						+ " ((pur_quantity - COALESCE((SELECT SUM(`pur_quantity`) FROM `hero_stock_purchase_request` E  WHERE E.`por_id`=a.`pr_id`),0)) * `unit_rate`)amount,"
						+ "pur_loose_uom,pur_loose_qty,hsuts_id,d.`unit` netunitdesc,`pr_id` porid FROM hero_stock_purchase_order_request a JOIN `hero_stock_product` b ON a.`pur_product_id` = b.`product_id` "
						+ "LEFT JOIN `hero_admin_company` c ON c.`company_id` = b.`manufacturer_id` LEFT JOIN `hero_admin_unit_type` d on d.`unit_type_id` = b.`unit_type_id`"
						+ " WHERE pur_req_id = '"+purchaseorderno+"'   AND `purchase_raised`=1 AND `order_raised`=0 ";
				
				log.info("itemQuery     "+itemQuery);
				@SuppressWarnings("unchecked")
				List<Object> itemList = jdbcTemplate.query(itemQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int count) throws SQLException {
						Map<String, Object> map = new HashMap<String, Object>();
						count = count + 1;
						map.put("index", count);
						
						String uompackingid = rs.getString("hsuts_id"),fulluom = rs.getString("pur_full_uom"),fulluomsno = "",looseuom=rs.getString("pur_loose_uom"),looseuomsno="";
						map.put("porid", rs.getString("porid"));
						map.put("purchaseorderid", rs.getString("pur_req_id"));
						map.put("productid", rs.getString("pur_product_id"));
						map.put("quantity", rs.getString("pur_quantity"));
						//map.put("productname", rs.getString("product_name").concat(" - "+rs.getString("company_name")));
						map.put("productname", rs.getString("product_name"));
						map.put("fulluom", fulluom);
						map.put("fulluomqty", rs.getString("pur_full_qty"));
						map.put("looseuom", looseuom);
						map.put("looseuomqty", rs.getString("pur_loose_qty"));
						map.put("uompacking", rs.getString("hsuts_id"));
						map.put("netunitdesc", rs.getString("netunitdesc"));
						map.put("unitrate", rs.getString("unit_rate"));
						map.put("amount", rs.getDouble("amount"));
						
						String fulluomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_full_uom` = "+fulluom;
						String looseuomidquery = "SELECT `hsuts_sno` FROM `hero_stock_unit_type_config` WHERE `hsuts_id`= "+uompackingid+" AND `hsuts_loose_uom` = "+looseuom;
						List<Map<String, Object>> fulluomList = jdbcTemplate.queryForList(fulluomidquery);
						List<Map<String, Object>> looseuomList = jdbcTemplate.queryForList(looseuomidquery);
						if(fulluomList != null && fulluomList.size() > 0)
						{
							Map<String, Object> fulluomMap = fulluomList.get(0);
							fulluomsno = String.valueOf((int) fulluomMap.get("hsuts_sno"));
						}
						
						if(looseuomList != null && looseuomList.size() > 0)
						{
							Map<String, Object> looseuomMap = looseuomList.get(0);
							looseuomsno = String.valueOf((int) looseuomMap.get("hsuts_sno")+1);
						}
						
						//map.put("fulluomsel", fulluomsno.concat("-").concat(fulluom));
						//map.put("looseuomsel", looseuomsno.concat("-").concat(looseuom));
						
						map.put("fulluomsel","");
						map.put("looseuomsel", "");
						
						HERO_STK_RESPONSEINFO responseInfo = getuomforpacking(uompackingid);
						HERO_STK_RESPONSE response = responseInfo.getInventoryResponse();
						
						/*HERO_STK_RESPONSEINFO responseInfo1 = getTaxlist();
						HERO_STK_RESPONSE response1 = responseInfo1.getInventoryResponse();
						
					    log.info(response1.getResponseObj());	 
						log.info(response.getResponseObj());*/
						 StringBuilder servicesb = new StringBuilder("<select  name=\"cgst"+count+"\"  class=\"form-control\" id=\"cgst"+count+"\"   onchange=\"calculatesubtotal("+count+")\">");
						 for(int i=0;i<TAXList.size();i++){ 
							  Map<String, Object> servicemap=(Map<String, Object>) TAXList.get(i);
							  servicesb.append("<option  value=\""+servicemap.get("TAX_ID")+"\"  data-value=\""+servicemap.get("TAX_RATE")+"\">"+servicemap.get("TAX_NAME")+" </option>");
						  }
						 servicesb.append("</select> "); 
						  String serviceid=servicesb.toString();
						  StringBuilder sgstsb = new StringBuilder("<select  name=\"sgst"+count+"\"  class=\"form-control\" id=\"sgst"+count+"\"   onchange=\"calculatesubtotal("+count+")\">");
							 for(int i=0;i<TAXList.size();i++){ 
								  Map<String, Object> servicemap=(Map<String, Object>) TAXList.get(i);
								  sgstsb.append("<option  value=\""+servicemap.get("TAX_ID")+"\"  data-value=\""+servicemap.get("TAX_RATE")+"\">"+servicemap.get("TAX_NAME")+" </option>");
							  }
							 sgstsb.append("</select> "); 
							  String sgstsbstr=sgstsb.toString();
						map.put("uomsel", response.getResponseObj());
						map.put("cgstselect", serviceid);
						map.put("sgstselect", sgstsbstr);
						
						return map;
					}
				});
				log.info("itemList        "+itemList);
				Map<String, Object> responseMap = new HashMap<String, Object>();
				responseMap.put("purchaseorderList", purchaseorderList);
				responseMap.put("itemList", itemList);
				
				inventoryResponseOBJ.setResponseType("S");
				inventoryResponseOBJ.setResponseObj(responseMap);
			}
			
			else
			{
				inventoryResponseOBJ.setResponseType("S");
				inventoryResponseOBJ.setResponseObj(purchaseorderList);	
			}
			
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		}
		
		
		@Override
		public HERO_STK_RESPONSEINFO getPurchaseBillNoList(String purchaseid) {
			
			    String PurchaseBill = "SELECT DISTINCT `prhdr_id`,`prhdr_bill_no` FROM `hero_stock_purchase_receive_hdr`a "
			    + "JOIN `hero_stock_purchase_received_dtl`b ON b.`prec_hdr_id`=a.`prhdr_id`"
			    + "JOIN `hero_stock`c ON c.`product_id`=`prec_product_id` AND `prec_batchno`=`batch_id` "
				+ " WHERE  `pur_req_id` = '"+purchaseid+"' AND `product_count` > 0";
			
			     log.info("PurchaseBill Query     "+PurchaseBill);
			     List<Object> PurchaseBillList = inventoryLOVobj.getLOVList1(PurchaseBill);
					
			     log.info(PurchaseBillList.size());
					inventoryResponseOBJ.setResponseType("S");
					inventoryResponseOBJ.setResponseObj(PurchaseBillList);
					
					inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
					
					return inventoryResponseInfoOBJ;
				}
		
		@Override
		public HERO_STK_RESPONSEINFO getPurchaseGRNList(String purchaseid) {
			
			    String PurchaseBill = "SELECT DISTINCT `prhdr_id`,`prhdr_bill_no` FROM `hero_stock_purchase_receive_hdr`a "
			    + "JOIN `hero_stock_purchase_received_dtl`b ON b.`prec_hdr_id`=a.`prhdr_id`"
			    + "JOIN `hero_stock`c ON c.`product_id`=`prec_product_id` AND `prec_batchno`=`batch_id` "
				+ " WHERE  `pur_req_id` = '"+purchaseid+"'";
			
			     log.info("PurchaseBill Query     "+PurchaseBill);
			     List<Object> PurchaseBillList = inventoryLOVobj.getLOVList1(PurchaseBill);
					
			     log.info(PurchaseBillList.size());
					inventoryResponseOBJ.setResponseType("S");
					inventoryResponseOBJ.setResponseObj(PurchaseBillList);
					
					inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
					
					return inventoryResponseInfoOBJ;
				}
		
		
		
		@Override
		public HERO_STK_RESPONSEINFO getTaxlist() {
			
			try
			{
		
			Map<String, Object> TaxMap = new HashMap<String, Object>();
			
			String cgstquery = "SELECT `TAX_ID`,`TAX_RATE` FROM `hero_admin_tax`";
			
			log.info("cgstquery   "+cgstquery);
			List<Map<String, Object>> cgstList = jdbcTemplate.queryForList(cgstquery);
			List<Map<String, Object>> cgstPacking = new ArrayList<Map<String,Object>>();
			
			log.info("cgstList   "+cgstList);
			
			StringBuilder sb = new StringBuilder();
			sb.append("<select id=\"cgsttaxselect\"  class=\"cgstbtn\" onchange=\"calculatetaxsubtotal()\" >");
			
			Iterator<Map<String, Object>> cgstPackingListITR = cgstList.iterator();
			while(cgstPackingListITR.hasNext())
			{
				Map<String, Object> cgstPackingMap = cgstPackingListITR.next();
				double taxrate = (double) cgstPackingMap.get("TAX_RATE");
				int taxid = (int)cgstPackingMap.get("TAX_ID");
				sb.append("<option value='"+taxrate+"'>"+taxrate+"</option>");
				
				cgstPackingMap.put("cgstval",taxid);
				cgstPacking.add(cgstPackingMap);
			}
			sb.append("</select>");
			TaxMap.put("cgstsel", sb.toString());
			
			
			
			
			String sgstquery = "SELECT `TAX_ID`,`TAX_RATE` FROM `hero_admin_tax`";
			
			log.info("sgstquery   "+sgstquery);
			List<Map<String, Object>> sgstList = jdbcTemplate.queryForList(sgstquery);
			List<Map<String, Object>> sgstPacking = new ArrayList<Map<String,Object>>();
			
			log.info("sgstList   "+sgstList);
			
			StringBuilder sb1 = new StringBuilder();
			sb1.append("<select id=\"sgsttaxselect\" class=\"sgstbtn\" onchange=\"calculatetaxsubtotal()\" >");
			
			Iterator<Map<String, Object>> sgstPackingListITR = sgstList.iterator();
			while(sgstPackingListITR.hasNext())
			{
				Map<String, Object> sgstPackingMap = sgstPackingListITR.next();
				double taxrate = (double) sgstPackingMap.get("TAX_RATE");
				int taxid = (int)sgstPackingMap.get("TAX_ID");
				sb1.append("<option value='"+taxrate+"'>"+taxrate+"</option>");
				
				sgstPackingMap.put("sgstval",taxid);
				sgstPacking.add(sgstPackingMap);
			}
			sb1.append("</select>");
			TaxMap.put("sgstsel", sb1.toString());
			
			sb = new StringBuilder();
			sb1 = new StringBuilder();
			
			
			TaxMap.put("cgstList", cgstPacking);
			TaxMap.put("sgstList", sgstPacking);
			
			inventoryResponseOBJ.setResponseObj("S");
			inventoryResponseOBJ.setResponseObj(TaxMap);
			
			}
			catch(Exception e)
			{
				error_log.info(e);
				inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
			}
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			return inventoryResponseInfoOBJ;
		}
		
		
}

