package com.hero.forms.services.admin.masters.txncode;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_TXNCODEDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_ITXNCODEDAO{
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_TXNCODEDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savetxncode(
			String txncodeData, HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		log.info("txncodeData       "+txncodeData);
		try
		{

			HERO_ADM_SERVC_TXNCODEREQUEST request = (HERO_ADM_SERVC_TXNCODEREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(txncodeData, HERO_ADM_SERVC_TXNCODEREQUEST.class);
			log.info("Values are      "+request.getStoreid()+"   "+request.getTxncode()+"   "+request.getTxncodesplit()+"   "+request.getTxnendno()+"   "+request.getTxnnolength()
					+"   "+request.getTxnstartno()+"   "+request.getTxntype()+"   "+request.getUserid()+"   "+request.getUsertype()+request.getOprn());

			Map<String, Object> resultMap = new HashMap<String, Object>();
			
			List<HERO_ADM_SERVC_TXNTYPEITEMS> txntypeList = HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONArraytoList(request.getTxntypelist(),"com.hero.forms.services.admin.masters.txncode.HERO_ADM_SERVC_TXNTYPEITEMS");
			
			SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_TXNCODE_HEADER");
			
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			inParamMap.put("P_TXN_HEADER_ID", request.getTxnid());
			inParamMap.put("P_STORE_ID", request.getStoreid());
			inParamMap.put("P_USERTYPE_ID", request.getUsertype());
			inParamMap.put("P_TXN_TYPE", request.getTxntype());
			inParamMap.put("P_TXN_CODE", request.getTxncode());
			inParamMap.put("P_TXN_NO_LENGTH", request.getTxnnolength());
			inParamMap.put("P_TXN_START_FROM", request.getTxnstartno());
			inParamMap.put("P_TXN_END_TO", request.getTxnendno());
			inParamMap.put("P_TXNCODE_SPLIT", request.getTxncodesplit());
			inParamMap.put("P_USER_ID", request.getUserid());
			
			inParamMap.put("P_OPRN", request.getOprn());
			log.info("inParamMap         "+inParamMap);
			SqlParameterSource in = new MapSqlParameterSource(inParamMap);
			resultMap = jdbcCALL.execute(in);
			log.info(resultMap);
			log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type")+"   "+(String)resultMap.get("out_genrate_id"));
			
			String resultType = (String)resultMap.get("out_result_type");
			
			if(resultType != null && resultType.equals("S"))
			{
			Iterator<HERO_ADM_SERVC_TXNTYPEITEMS> txntypeListITR = txntypeList.iterator();
			while(txntypeListITR.hasNext())
			{
				HERO_ADM_SERVC_TXNTYPEITEMS item = txntypeListITR.next();
				item.setTxnid((String)resultMap.get("out_genrate_id"));
				txntypeList.set(Integer.parseInt(item.getIndex())- 1, item);
			}
			
			savetxncodetypes(txntypeList);
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

	@Transactional
	public void savetxncodetypes( final List<HERO_ADM_SERVC_TXNTYPEITEMS> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_ADM_TXNCODE_DETAILS( ?,?,?,?)}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int i) throws SQLException 
			{
				HERO_ADM_SERVC_TXNTYPEITEMS item = list.get(i);
				log.info("Item Values       "+item.getKey()+"   "+item.getTxnid()+"   "+item.getIndex());
				ps.setString(1, item.getTxnid());
				String[] keyArr = item.getKey().split("-");
				ps.setString(2, keyArr[0]);
				ps.setString(3, item.getLabel());
				ps.setString(4, item.getIndex());
			}
		});
		}
		catch( Exception e )
		{
			//e.printStackTrace();
			error_log.info(e);
			throw e;
		}
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodelist(
			String storeid, HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		String txnCodeQuery = "SELECT `htch_id` seqid,a.`usertype_id` usertypeid,`usertype_name` usertypedesc,`TXN_DESC` txndesc,`htch_txnno_length` txnnolength,`htch_start_from` "
							  + "startfrom,`htch_end_to` endto,'"+HERO_ADM_SERVC_HOSURINVENTORYUTIL.DATATABLE_ACTION+"' action FROM `hero_txn_code_header` a JOIN "
							  + "`hero_user_type` b ON a.`usertype_id` = b.`usertype_id` JOIN `hero_txns_no_format` c ON c.`TXN_ID` = a.`htch_txntype_seqid` ";
							  /*+ "where "
							  + "a.store_id = "+storeid;*/
		
		log.info("txnCodeQuery"+ txnCodeQuery);
		
		List<Object> txnCodesList = jdbcTemplate.queryForList(txnCodeQuery);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(txnCodesList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodedetail(
			String txncodeid, HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		String txnCodeQuery = "SELECT `htch_id` seqid,`store_id` storeid,`usertype_id` usertypeid,`htch_txntype_seqid` txntypeseqid,`htch_txn_code` txncode,"
				+ "`htch_txnno_length` txnnolen,`htch_start_from` txnnostart,`htch_end_to` txnnoend,`htch_txncode_split` txncodesplit,"
				+ "CONCAT(`TXN_START_NAME`,'-',`TXN_NO_OF_ZEROS`,'-',`TXN_ID`) 'txntype'  FROM `hero_txn_code_header` a JOIN `hero_txns_no_format` b ON "
				+ "a.`htch_txntype_seqid` = b.`TXN_ID` WHERE `htch_id` = "+txncodeid;

		Map<String, Object> responseMap = new HashMap<String, Object>();
		
List<Object> txnCodesList = jdbcTemplate.queryForList(txnCodeQuery);


if(txnCodesList != null && txnCodesList.size() > 0)
{
	Map<String, Object> txnCodesMap = (Map<String, Object>) txnCodesList.get(0);
	int txnid = (int) txnCodesMap.get("seqid");
	String txnDetailQuery = "SELECT `htcd_id` detlid,`htch_id` txncodeid,`htcd_txn_code` txncode,`htcd_txn_desc` txncodedesc FROM `hero_txn_code_detail` WHERE "
			+ "`htch_id` = "+txnid+" ORDER BY htcd_index asc";
	@SuppressWarnings("unchecked")
	List<Object> txnDetailList = jdbcTemplate.query(txnDetailQuery.toString(), new RowMapper() {
		
		@Override
		public Object mapRow(ResultSet rs, int arg1) throws SQLException {
			// TODO Auto-generated method stub
			Map<String, Object> map = new HashMap<String, Object>();
			
			map.put("detlid", rs.getString("detlid"));
			map.put("txncodeid", rs.getString("txncodeid"));
			
			String txncode = "";
			int itxncode = 0;
			itxncode = rs.getInt("txncode");
			
			if(itxncode <= 4)
			{
				txncode = itxncode+"-nil";
			}
			else if(itxncode == 5)
			{
				txncode = itxncode+"-txncodeid";
			}
			else if(itxncode == 6)
			{
				txncode = itxncode+"-storeselect";
			}
			else if(itxncode == 7)
			{
				txncode = itxncode+"-usergroupselect";
			}
			map.put("txncode", txncode);
			map.put("txncodedesc", rs.getString("txncodedesc"));
			
			return map;
		}
	});
	responseMap.put("txnDetailList", txnDetailList);
}

responseMap.put("txnCodesList", txnCodesList);


inventoryResponseOBJ.setResponseType("S");
inventoryResponseOBJ.setResponseObj(responseMap);

inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);

return inventoryResponseInfoOBJ;
	}
}
