package com.hero.forms.services.admin.masters.usertypes;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_USERTYPEDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_IUSERTYPEDAO {
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_USERTYPEDAOIMPL.class);
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadusertypes() {
		// TODO Auto-generated method stub
		String selectQuery = "SELECT usertype_id,usertype_name,COALESCE(`usertype_dept`,'Admin'),`hac_name`,`hac_id`,usertype_status,`usertype_img` FROM `hero_user_type` a "
				+ "RIGHT JOIN  `hero_app_config` b ON a.`usertype_dept` = b.`hac_id` WHERE usertype_id > 1 ORDER BY `usertype_id` DESC";
		@SuppressWarnings("unchecked")
		List<Object> usertpeList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				// TODO Auto-generated method stub
				Map<String, Object> usertypeMap = new HashMap<String, Object>();
				
				usertypeMap.put("usertypename", rs.getString("usertype_name"));
				usertypeMap.put("usertypeid", rs.getString("usertype_id"));
				usertypeMap.put("deptname", rs.getString("hac_name"));
				usertypeMap.put("deptid", rs.getString("hac_id"));
				usertypeMap.put("usertypeimg", rs.getString("usertype_img"));
				usertypeMap.put("action", "<button class=\"edit myBtnTab\"> <i class=\"fa fa-edit\"></i> </button><button class=\"Permission myBtnTab\" onclick='gotoPermission("+rs.getString("hac_id")+","+rs.getString("usertype_id")+")'> <i style=\"font-size:20px !important;\" class=\"fa fa-key\"></i> </button>");
				String status = "";
				if(rs.getInt("usertype_status") == 0)
				{
					status = "In Active";
				}
				else if(rs.getInt("usertype_status") == 1)
				{
					status = "Active";
				}
				usertypeMap.put("statusdesc", status);
				usertypeMap.put("status", rs.getInt("usertype_status"));
				return usertypeMap;
			}
		});
		log.info("usertpeList   "+usertpeList);
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(usertpeList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveusertype(String usertypeData,HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		
		log.info("usertypeData        "+usertypeData);
		HttpSession session = httpRequest.getSession();
		
		try
		{
			HERO_ADM_SERVC_USERTYPEREQUEST request = (HERO_ADM_SERVC_USERTYPEREQUEST) HERO_ADM_SERVC_HOSURINVENTORYUTIL.convertJSONtooOBJECT(usertypeData, HERO_ADM_SERVC_USERTYPEREQUEST.class);
		
		log.info("Values are     "+request.getDept()+"   "+request.getOprn()+"   "+request.getUsertypedesc()+"   "+request.getUsertypeid());
		
		SimpleJdbcCall jdbcCALL = new SimpleJdbcCall(jdbcTemplate).withProcedureName("P_HERO_USERTYPE_MASTER");
		Map<String, Object> inParamMap = new HashMap<String, Object>();
		inParamMap.put("P_USERTUYPE_ID", request.getUsertypeid());
		inParamMap.put("P_USERTYPE_DESC", request.getUsertypedesc());
		inParamMap.put("P_DEPT", request.getDept());
		inParamMap.put("P_USERTYPEIMAGE", request.getUsertypeimage());
		inParamMap.put("P_STATUS", request.getStatus());
		inParamMap.put("P_USERID", session.getAttribute("uid"));
		inParamMap.put("P_OPRN", request.getOprn());
		
		log.info("inParamMap         "+inParamMap);
		
		SqlParameterSource in = new MapSqlParameterSource(inParamMap);
		
		Map<String, Object> resultMap = jdbcCALL.execute(in);
		
		log.info(resultMap);
		log.info(resultMap.get("#update-count-1")+"       "+resultMap.get("out_result_msg")+"      "+resultMap.get("out_result_type"));
		
		inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnResponse(resultMap, inventoryResponseOBJ);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
			inventoryResponseOBJ = HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionResponse(inventoryResponseOBJ, e);
		}
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		return inventoryResponseInfoOBJ;
	}

}
