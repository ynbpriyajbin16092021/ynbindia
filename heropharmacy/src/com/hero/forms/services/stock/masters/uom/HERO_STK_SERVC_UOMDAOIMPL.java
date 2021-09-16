package com.hero.forms.services.stock.masters.uom;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.transaction.annotation.Transactional;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;
import com.hero.stock.util.HERO_STK_INVENTORYDAO;
import com.hero.stock.util.HERO_STK_INVENTORYLOV;

public class HERO_STK_SERVC_UOMDAOIMPL extends HERO_STK_INVENTORYDAO implements HERO_STK_SERVC_IUOMDAO{
	
	String uomsettingid="0",userid=null;
	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_UOMDAOIMPL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Override
	public HERO_STK_RESPONSEINFO saveuom(String uomData,HERO_STK_SERVC_IUOMDAO iuomDAOobj) {

		log.info("uomData        "+uomData);
		
		try
		{
		HERO_STK_SERVC_UOMREQUEST request = (HERO_STK_SERVC_UOMREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomData, "com.hero.forms.services.stock.masters.uom.HERO_STK_SERVC_UOMREQUEST");
		
		log.info("Values are     "+request.getUnittypeid()+"   "+request.getUnit()+"   "+request.getStatusid()+"   "+request.getOprn()+"   "+request.getUomtype());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_ADM_UOM_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_UOM_ID", request.getUnittypeid());
		inParamMap.put("P_UOM_NAME", request.getUnit());
		inParamMap.put("P_STATUS_ID", request.getStatusid());
		inParamMap.put("P_OPERATION", request.getOprn());
		
		inParamMap.put("P_UOM_DESC", request.getUomdesc());
		inParamMap.put("P_UOM_TYPE", request.getUomtype());
		inParamMap.put("P_USERID", request.getUserid());
		
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
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loaduoms(HERO_STK_SERVC_IUOMDAO iuomDAOobj) {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT * FROM hero_admin_unit_type ORDER BY unit_type_id DESC";
		@SuppressWarnings("unchecked")
		List<Object> uomList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				List<String> detail = new ArrayList<String>();
				
				detail.add(rs.getString("uom_desc"));//0
				detail.add(rs.getString("unit"));//1
				String uomtypedesc="";
				if(rs.getInt("uom_type")== 0)
				{
					uomtypedesc = "Full";
				}
				else if(rs.getInt("uom_type")== 1)
				{
					uomtypedesc = "Loose";
				}
				detail.add(uomtypedesc);//2
				detail.add("");//3
				detail.add(rs.getString("uom_type"));//4
				detail.add(rs.getString("unit_type_id"));//5
				
				return detail;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(HEROHOSURINVENTORYUTIL.returnJSONobject(uomList));
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO saveuomsetting(String uomsettingData,
			HERO_STK_SERVC_IUOMDAO iuomDAOobj,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		
		
log.info("uomsettingData        "+uomsettingData);
		
		try
		{
			HttpSession session = httpRequest.getSession();
		HERO_STK_SERVC_UOMREQUEST request = (HERO_STK_SERVC_UOMREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomsettingData, HERO_STK_SERVC_UOMREQUEST.class);
		
		log.info("Values are     "+request.getUomsettingdesc()+"   "+request.getUomsettingid()+"   "+session.getAttribute("uid"));

		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_STOCK_UOM_SETTING_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_UOM_SETTING_ID", request.getUomsettingid());
		inParamMap.put("P_UOM_SETTING_DESC", request.getUomsettingdesc());
		inParamMap.put("P_STATUS", request.getStatusid());
		inParamMap.put("P_OPRN", request.getOprn());
		inParamMap.put("P_USERID", request.getUserid());
		
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
			inventoryResponseOBJ = HEROHOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
		
	}

	@Override
	public HERO_STK_RESPONSEINFO loaduomsetting(
			HERO_STK_SERVC_IUOMDAO iuomDAOobj) {
		// TODO Auto-generated method stub
		String query = "SELECT * FROM hero_stock_unit_type_setting";
		@SuppressWarnings("unchecked")
		List<Object> uomSettingsList = jdbcTemplate.query(query, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("uomsettingdesc", rs.getString("hsuts_desc"));
				map.put("uomsettingid", rs.getString("hsuts_id"));
				map.put("status", rs.getString("hsuts_status"));
				map.put("configuredone", rs.getString("hsuts_configured_done"));
				
				if(rs.getInt("hsuts_status")==1)
				{
					map.put("statusdesc", "Active");
				}
				else if(rs.getInt("hsuts_status")==0)
				{
					map.put("statusdesc", "In-Active");
				}
				else
				{
					map.put("statusdesc", "");
				}
				
				String DATATABLE_ACTION = "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"delete myBtnTab\""
						+ " data-toggle=\"modal\" data-target=\"#modal-delet\"> <i class=\"fa fa-trash-o\"></i> </button>"
						+ "<button class=\"configure myBtnTab\"title=\"Configure\"> <i class=\"fa fa-cogs\"></i> </button>";
				map.put("action", DATATABLE_ACTION);
				
				return map;
			}
		});
		log.info(uomSettingsList);
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("uomSettingsList", uomSettingsList);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(responseMap);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO loaduomitemslist(
			HERO_STK_SERVC_IUOMDAO iuomDAOobj,String uomsettingsid) {
		// TODO Auto-generated method stub
		
		
		
		String UOMQuery = "SELECT concat(`unit_type_id`,'-',uom_type),`uom_desc` FROM `hero_admin_unit_type` order by uom_desc asc";
		List<Object> UOMList = inventoryLOVobj.getLOVList(UOMQuery);
		
		StringBuilder UOM_sb = new StringBuilder();
		UOM_sb.append("<select id=\"uomsel\">");
		Iterator<Object> UOMListITR = UOMList.iterator();
		while(UOMListITR.hasNext())
		{
			HERO_STK_INVENTORYLOV UOMObj = (HERO_STK_INVENTORYLOV) UOMListITR.next();
			UOM_sb.append("<option value=\""+UOMObj.getValue()+"\">"+UOMObj.getLabel()+"</option>");
			
		}
		UOM_sb.append("</select>");
		log.info("UOM_sb   "+UOM_sb.toString());
		
		String fullUOMQuery = "SELECT CONCAT(`unit_type_id`,'-',uom_type),`uom_desc` FROM `hero_admin_unit_type` WHERE `uom_type` = 0  ORDER BY uom_desc ASC";
		List<Object> fullUOMList = inventoryLOVobj.getLOVList(fullUOMQuery);
		
		StringBuilder fullUOM_sb = new StringBuilder();
		fullUOM_sb.append("<select id=\"uomsel\">");
		Iterator<Object> fullUOMListITR = fullUOMList.iterator();
		while(fullUOMListITR.hasNext())
		{
			HERO_STK_INVENTORYLOV fullUOMObj = (HERO_STK_INVENTORYLOV) fullUOMListITR.next();
			fullUOM_sb.append("<option value=\""+fullUOMObj.getValue()+"\">"+fullUOMObj.getLabel()+"</option>");
			
		}
		fullUOM_sb.append("</select>");
		log.info("fullUOM_sb   "+fullUOM_sb.toString());
		
		Map<String, Object> responseMap = new HashMap<String, Object>();
		responseMap.put("uom", UOM_sb);
		responseMap.put("fulluom", fullUOM_sb);
		
		String configQuery = "SELECT `hsutc_id`,`hsuts_id`,`hsuts_full_uom`,`hsuts_loose_qty`,`hsuts_loose_uom`,(SELECT `unit` FROM `hero_admin_unit_type` WHERE "
				+ "`unit_type_id` = `hsuts_full_uom`) fulluomdesc,(SELECT `unit` FROM `hero_admin_unit_type` WHERE `unit_type_id` = `hsuts_loose_uom`) looseuomdesc  FROM "
				+ "`hero_stock_unit_type_config`  WHERE `hsuts_id`= "+uomsettingsid+" ORDER BY `hsuts_sno` ASC";
		@SuppressWarnings("unchecked")
		List<Object> uomConfigList = jdbcTemplate.query(configQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				// TODO Auto-generated method stub
			
				/*String HTML_TABLE = "<tr>" +
						"<td>" + (index+1) + "</td>" +
						"<td>"+rs.getInt("hsuts_full_uom")+"</td><td>=</td><td><input type='number' id="+rs.getInt("hsuts_loose_qty")+" width='20px' value='1'></td><td>"+rs.getInt("hsuts_loose_uom")+"</td>" +
								"<td><input class='btn white-bg fa-check color-font' id='checkbtn"+index+"' type='button' value='&#xf044;' onclick='checkUOMSetting("+index+");'>" +
								"<input class='btn white-bg fa-input red-font' type='button' value='&#xf014;'></td>" +
								"</tr>";
				
				index++;
				return HTML_TABLE;*/
				Map<String, Object>uomConfigMap = new HashMap<String, Object>();
				uomConfigMap.put("fulluom", rs.getInt("hsuts_full_uom"));
				uomConfigMap.put("looseqty", rs.getInt("hsuts_loose_qty"));
				uomConfigMap.put("looseuom", rs.getInt("hsuts_loose_uom"));
				uomConfigMap.put("fulluomdesc", rs.getString("fulluomdesc"));
				uomConfigMap.put("looseuomdesc", rs.getString("looseuomdesc"));
				return uomConfigMap;
			}
		});
		log.info("uomConfigList  "+uomConfigList);
		
		/*StringBuilder fullUOM_sb = new StringBuilder();*/
		
		int index = 0;
		StringBuilder sb = new StringBuilder();
		Iterator<Object> uomConfigListITR = uomConfigList.iterator();
		while(uomConfigListITR.hasNext())
		{
			Map<String, Object> uomConfigMap = (Map<String, Object>) uomConfigListITR.next();
			sb.append("<tr>" +
					"<td>" + (index+1) + "</td>" +
					"<td> 1 "+uomConfigMap.get("fulluomdesc")+"</td><td>=</td><td><input type='number' value="+uomConfigMap.get("looseqty")+" width='20px' value='1'></td>"
							+ "<td>"+uomConfigMap.get("looseuomdesc")+"</td>" +
							"<td>&nbsp;</td>" +
							"</tr>");
			
			index++;
		}
		String saveduom = sb.toString();
		log.info("saveduom   "+saveduom);
		
		responseMap.put("saveduom", saveduom);
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(responseMap);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_STK_RESPONSEINFO saveuomconfig(String uomsettingData,
			HERO_STK_SERVC_IUOMDAO iuomDAOobj, HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		try
		{
			log.info("uomsettingData   "+uomsettingData);
			HttpSession session = httpRequest.getSession();
			userid = (String) session.getAttribute("userid");
		HERO_STK_SERVC_UOMREQUEST request = (HERO_STK_SERVC_UOMREQUEST) HEROHOSURINVENTORYUTIL.convertJSONtooOBJECT(uomsettingData, HERO_STK_SERVC_UOMREQUEST.class);
		List<HERO_UOM_CONFIG_REQUEST> configList = HEROHOSURINVENTORYUTIL.convertJSONArraytoList(request.getUomsettingArray(),HERO_UOM_CONFIG_REQUEST.class);
		uomsettingid = request.getUomsettingid();
		saveuomconfigrations(configList);
		inventoryResponseOBJ.setResponseObj("S");
		inventoryResponseOBJ.setResponseObj("UOM Settings Configured Successfully");
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
	public void saveuomconfigrations( final List<HERO_UOM_CONFIG_REQUEST> list) 
	{
		try
		{
			
			jdbcTemplate.batchUpdate("{call P_HERO_STOCK_UOM_CONFIG_MASTER( ?,?,?,?,?, ?,? )}",
				new BatchPreparedStatementSetter() {					
				public int getBatchSize()
				{
					return list.size();
				}
	
			public void setValues(PreparedStatement ps, int index) throws SQLException 
			{
				HERO_UOM_CONFIG_REQUEST item = list.get(index);
				log.info("Item Values       "+item.getFulluom()+"   "+item.getLooseuom()+"   "+item.getLooseqty()+"   "+uomsettingid);
				ps.setString(1, uomsettingid);
				ps.setString(2, item.getFulluom());
				ps.setString(3, item.getLooseuom());
				ps.setString(4, item.getLooseqty());
				ps.setInt(5, index+1);
				ps.setString(6, userid);
				ps.setString(7, "INS");
			}
		});
		}
		catch( Exception e )
		{
			error_log.info(e);
			throw e;
		}
	}
}
