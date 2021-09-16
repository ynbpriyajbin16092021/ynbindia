package com.herotokencentre.controller.model;

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

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.herotokencentre.controller.response.HMSResponse;
import com.herotokencentre.util.HMSUtility;

@Service
public class HMSUtilServiceImpl extends HMSBeans implements IHMSUtilService {
	final static Logger logger = Logger.getLogger(HMSUtilServiceImpl.class);
	@Override
	public HMSResponse branchlov() {

		try {

			String query = "SELECT `hb_id`,`hb_branch_desc` FROM `ha_branch`";

			logger.info("query   " + query);
			@SuppressWarnings("unchecked")
			List<Object> branchList = jdbcTemplate.query(query,
					new RowMapper() {

						@Override
						public Object mapRow(ResultSet rs, int index)
								throws SQLException {
							// TODO Auto-generated method stub
							Map<String, Object> map = new HashMap<String, Object>();
							index++;

							map.put("value", rs.getString("hb_id"));
							map.put("label", rs.getString("hb_branch_desc"));
							map.put("index", index);

							return map;
						}
					});
			logger.info("branchList length   " + branchList.size());

			responseBeanObj.setResponseType("S");
			responseBeanObj.setResponseObj(branchList);

			responseObj.setResponse(responseBeanObj);
		} catch (Exception e) {
			e.printStackTrace();
			responseObj.setResponse(HMSUtility.returnExceptionResponse(
					responseBeanObj, e));
		}

		return responseObj;
	}
 
	@Override
	public List<Object> getMenuList(String menuQuery,String userMenuQuery) {
		// TODO Auto-generated method stub
		logger.info("menuQuery   "+menuQuery);
		logger.info("userMenuQuery   "+userMenuQuery);
		@SuppressWarnings("unchecked")
		List<Object> usermenuList = jdbcTemplate.query(userMenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				/*logger.info("rs.getString(fafafont)        "+rs.getString("fafafont"));*/
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("hmm_id"));
				map.put("modulename", rs.getString("hmm_name"));
				map.put("issubmodule",rs.getString("hmm_ismenu_sub_path"));
				map.put("parentid",rs.getString("hmm_parent_id"));
				map.put("index", index);
				
				if(rs.getInt("hmm_ismenu_sub_path") == 0)
				{
					map.put("mainmenudisp", "mainmenudisp");
					map.put("submenudisp", "mainmenuhidedisp");
					map.put("space", "");
					map.put("componentvisible", true);
					map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
				}
				else
				{
					map.put("mainmenudisp", "mainmenuhidedisp");
					map.put("submenudisp", "mainmenudisp");
					map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
					map.put("style", "");
					map.put("componentvisible", false);
				}
				
				map.put("menudetails",rs.getInt("hmma_menu_access")+"$$$"+rs.getInt("hmm_ismenu_sub_path")+"$$$"+rs.getInt("hmm_parent_id")+"$$$"+rs.getInt("hmm_id")+"$$$");
				map.put("menusno",rs.getString("hmm_id"));
				map.put("usertype",rs.getString("hurm_id"));
				
				if(rs.getInt("hmma_menu_access") == 0)
				{
					map.put("checked", false);
				}
				else
				{
					map.put("checked", true);
				}
				/*String submenuQuery ="SELECT A.`hmm_id`,`hmm_name`,`hmm_ismenu_sub_path`,`hmm_parent_id`,COALESCE(`hmma_id`,0)hmma_id,COALESCE(`hum_id`,0)hum_id,"
				  		+ "COALESCE(`hurm_id`,0)hurm_id,`hmma_menu_access`"
							+ " FROM `ha_module_menus` A LEFT OUTER JOIN `ha_module_menu_access` B ON A.`hmm_id` = B.`hmm_id` "
							+ "WHERE `hurm_id` = "+rs.getString("hurm_id")+" AND `hmm_parent_id` = "+rs.getString("hmm_id")
							+"  ORDER BY `hurm_id`,`hmm_id` ASC";
				logger.info("submenuQuery    "+submenuQuery);
				  List<Object> submenuList = new ArrayList<Object>();
				  submenuList = getSubMenuList(submenuQuery);
				  map.put("submenuList", submenuList);*/
				  
				index++;
				return map;
			}
		});
		logger.info("First usermenuList       "+usermenuList);
		
		if(usermenuList != null && usermenuList.size() == 0)
		{
			@SuppressWarnings("unchecked")
			List<Object> menuList = jdbcTemplate.query(menuQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					/*logger.info("rs.getString(fafafont)        "+rs.getString("fafafont"));*/
					Map<String, Object> map = new HashMap<String, Object>();
					
					map.put("moduleid",rs.getString("hmm_id"));
					map.put("modulename", rs.getString("hmm_name"));
					map.put("issubmodule",rs.getString("hmm_ismenu_sub_path"));
					map.put("parentid",rs.getString("hmm_parent_id"));
					map.put("index", index);
					
					if(rs.getInt("hmm_ismenu_sub_path") == 0)
					{
						map.put("mainmenudisp", "mainmenudisp");
						map.put("submenudisp", "mainmenuhidedisp");
						map.put("space", "");
						map.put("componentvisible", true);
						map.put("style", "style='background-color: #319db5;font-weight: bolder;'");
					}
					else
					{
						map.put("mainmenudisp", "mainmenuhidedisp");
						map.put("submenudisp", "mainmenudisp");
						map.put("space", "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;");
						map.put("style", "");
						map.put("componentvisible", false);
					}
					
					/*map.put("menudetails",rs.getInt("hmma_menu_access")+"$$$"+rs.getInt("hmm_ismenu_sub_path")+"$$$"+rs.getInt("hmm_parent_id")+"$$$"+rs.getInt("hmm_id")+"$$$");*/
					map.put("menudetails","1$$$"+rs.getInt("hmm_ismenu_sub_path")+"$$$"+rs.getInt("hmm_parent_id")+"$$$"+rs.getInt("hmm_id"));
					map.put("checked", true);
					map.put("menusno",rs.getString("hmm_id"));
					/*map.put("usertype",rs.getString("hurm_id"));*/
					
					/*String submenuQuery ="SELECT A.`hmm_id`,`hmm_name`,`hmm_ismenu_sub_path`,`hmm_parent_id`,COALESCE(`hmma_id`,0)hmma_id,COALESCE(`hum_id`,0)hum_id,"
					  		+ "COALESCE(`hurm_id`,0)hurm_id,`hmma_menu_access`"
								+ " FROM `ha_module_menus` A LEFT OUTER JOIN `ha_module_menu_access` B ON A.`hmm_id` = B.`hmm_id` "
								+ "WHERE `hurm_id` = "+rs.getString("hurm_id")+" AND `hmm_parent_id` = "+rs.getString("hmm_id")
								+"  ORDER BY `hurm_id`,`hmm_id` ASC";
					logger.info("submenuQuery    "+submenuQuery);
					  List<Object> submenuList = new ArrayList<Object>();
					  submenuList = getSubMenuList(submenuQuery);
					  map.put("submenuList", submenuList);*/
					  
					index++;
					return map;
				}
			});
			
			usermenuList = menuList;
		}
		logger.info("usermenuList   "+usermenuList);
		return usermenuList;
	}

	public List<Object> getSubMenuList(String submenuQuery) {
		// TODO Auto-generated method stub
		
		@SuppressWarnings("unchecked")
		List<Object> submenuList = jdbcTemplate.query(submenuQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				
				map.put("moduleid",rs.getString("hmm_id"));
				map.put("modulename", rs.getString("hmm_name"));
				map.put("issubmodule",rs.getString("hmm_ismenu_sub_path"));
				map.put("parentid",rs.getString("hmm_parent_id"));
				map.put("index", index);
				
				if(rs.getInt("hmm_ismenu_sub_path") == 0)
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
				
				map.put("menudetails",rs.getInt("hmma_menu_access")+"$$$"+rs.getInt("hmm_ismenu_sub_path")+"$$$"+rs.getInt("hmm_parent_id")+"$$$"+rs.getInt("hmm_id")+"$$$"
						+rs.getString("hmma_id"));
				map.put("menusno",rs.getString("hmma_id"));
				map.put("usertype",rs.getString("hum_is"));
				map.put("modulepath", rs.getString("hmm_path"));
				
				index++;
				return map;
			}
		});
		/*logger.info("First submenuList       "+submenuList);*/
		
		return submenuList;
	}

	@Override
	public Map<String, Object> getCategoryItems(String categoryid,String clinicid) {

		String categorytypeid="",orderheadid="",suborderid="",ordertubeid="",unitid="";
		Map<String, Object> clinicMap = new HashMap<String, Object>();
		
		try
		{
			try
			{
				int intcategoryid = 0;
				if(categoryid != null && categoryid.length() > 0)
				{
					intcategoryid = Integer.parseInt(categoryid);
				}
				
				String categoryQuery = "SELECT `hoc_id`,`hoc_category_desc` FROM `hl_order_category` where hcm_id = "+clinicid;
				if(intcategoryid > 0)
				{
					categoryQuery = categoryQuery.concat(" and hoc_id = "+intcategoryid);
				}
				categoryQuery = categoryQuery.concat(" order by hoc_id asc");
				
				logger.info("categoryquery for orderitemsonload  "+categoryQuery);
				@SuppressWarnings("unchecked")
				List<Object> categoriesList = jdbcTemplate.query(categoryQuery, new RowMapper() {
					
					@Override
					public Object mapRow(ResultSet rs, int index) throws SQLException {
						
						Map<String, Object> map = new HashMap<String, Object>();
						index++;
						
						map.put("categoryid", rs.getString("hoc_id"));
						map.put("categorydesc", rs.getString("hoc_category_desc"));
						
						map.put("index", index);
						
						return map;
					}
				});
				logger.info("categoriesList length   "+categoriesList.size());
				
				
				if(categoriesList.size() > 0)
				{
					HashMap<String, Object> categoryMap = (HashMap<String, Object>) categoriesList.get(0); 
					categoryid = (String) categoryMap.get("categoryid"); 
				}
				
				clinicMap.put("categoriesList", categoriesList);
				clinicMap.put("firstCategoryid", categoryid);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			/*************************************************************************************************************/
			List<Object> categoryTypesList = getCategorytypesforCategory(categoryid,clinicid);
			if(categoryTypesList.size() > 0)
			{
				HashMap<String, Object> categoryTypeMap = (HashMap<String, Object>) categoryTypesList.get(0); 
				categorytypeid = (String) categoryTypeMap.get("categorytypeid");
			}
			logger.info("categorytypeid   "+categorytypeid);
			
			clinicMap.put("categoryTypesList", categoryTypesList);
			clinicMap.put("firstCategoryTypeid", categorytypeid);

			/**************************************************************************************************************/
			List<Object> orderHeadList = getOrderHeadforCategoryTypes(categorytypeid, clinicid);
			if(orderHeadList.size() > 0)
			{
				HashMap<String, Object> orderHeadMap = (HashMap<String, Object>) orderHeadList.get(0); 
				orderheadid = (String) orderHeadMap.get("headid");
			}
			logger.info("categorytypeid   "+categorytypeid);
			
			clinicMap.put("orderHeadList", orderHeadList);
			clinicMap.put("firstorderHeadid", orderheadid);
			
			/**************************************************************************************************************/
			List<Object> suborderList = getSuborderForOrderhead(orderheadid, clinicid);
			if(suborderList.size() > 0)
			{
				HashMap<String, Object> subOrderMap = (HashMap<String, Object>) suborderList.get(0); 
				suborderid = (String) subOrderMap.get("suborderid");
			}
			logger.info("suborderid   "+suborderid);
			
			clinicMap.put("suborderList", suborderList);
			clinicMap.put("suborderACList", suborderList);
			clinicMap.put("firstsuborderid", suborderid);
			
			/*************************************************************************************************************/
			List<Object> ordertubesList = getOrderTubesforCategory(categoryid, clinicid);
			if(ordertubesList.size() > 0)
			{
				HashMap<String, Object> orderTubeMap = (HashMap<String, Object>) ordertubesList.get(0); 
				ordertubeid = (String) orderTubeMap.get("tubeid");
			}
			logger.info("ordertubeid   "+ordertubeid);
			
			clinicMap.put("ordertubesList", ordertubesList);
			clinicMap.put("firstorderTubeid", ordertubeid);
			
			/************************************************************************************************************/
			List<Object> orderunitsList = getOrderUnitsforCategoryType(categorytypeid, clinicid);
			if(orderunitsList.size() > 0)
			{
				HashMap<String, Object> orderUnitMap = (HashMap<String, Object>) orderunitsList.get(0); 
				unitid = (String) orderUnitMap.get("unitid");
			}
			logger.info("ordertubeid   "+ordertubeid);
			
			clinicMap.put("orderunitsList", orderunitsList);
			clinicMap.put("firstunitid", unitid);
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return clinicMap;
	}


	@Override
	public Map<String, Object> getCategoryTypeItems(String categorytypeid,String clinicid) {

		String orderheadid="",suborderid="",unitid="";
		Map<String, Object> clinicMap = new HashMap<String, Object>();
		
		try
		{
			
			
			/**************************************************************************************************************/
			List<Object> orderHeadList = getOrderHeadforCategoryTypes(categorytypeid, clinicid);
			if(orderHeadList.size() > 0)
			{
				HashMap<String, Object> orderHeadMap = (HashMap<String, Object>) orderHeadList.get(0); 
				orderheadid = (String) orderHeadMap.get("headid"); 
			}
			logger.info("orderheadid   "+orderheadid);
			
			clinicMap.put("orderHeadList", orderHeadList);
			clinicMap.put("firstorderHeadid", orderheadid);
			
			/**************************************************************************************************************/
			List<Object> suborderList = getSuborderForOrderhead(orderheadid, clinicid);
			if(suborderList.size() > 0)
			{
				HashMap<String, Object> subOrderMap = (HashMap<String, Object>) suborderList.get(0); 
				suborderid = (String) subOrderMap.get("suborderid"); 
			}
			logger.info("suborderid   "+suborderid);
			
			clinicMap.put("suborderList", suborderList);
			clinicMap.put("suborderACList", suborderList);
			clinicMap.put("firstsuborderid", suborderid);
			
			/************************************************************************************************************/
			List<Object> orderunitsList = getOrderUnitsforCategoryType(categorytypeid, clinicid);
			if(orderunitsList.size() > 0)
			{
				HashMap<String, Object> orderUnitMap = (HashMap<String, Object>) orderunitsList.get(0); 
				unitid = (String) orderUnitMap.get("unitid");
				
			}
			logger.info("unitid   "+unitid);
			
			clinicMap.put("orderunitsList", orderunitsList);
			clinicMap.put("firstunitid", unitid);
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return clinicMap;
	}

	@Override
	public Map<String, Object> getOrderheadItems(String orderheadid, String clinicid) {

		String suborderid="";
		Map<String, Object> clinicMap = new HashMap<String, Object>();
		
		try
		{
			/**************************************************************************************************************/
			List<Object> suborderList = getSuborderForOrderhead(orderheadid, clinicid);
			if(suborderList.size() > 0)
			{
				HashMap<String, Object> subOrderMap = (HashMap<String, Object>) suborderList.get(0); 
				suborderid = (String) subOrderMap.get("suborderid"); 
			}
			logger.info("suborderid   "+suborderid);
			
			clinicMap.put("suborderList", suborderList);
			clinicMap.put("suborderACList", suborderList);
			clinicMap.put("firstsuborderid", suborderid);
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			
		}
		
		return clinicMap;
	}

	
	@Override
	public List<Object> getCategorytypesforCategory(String categoryid,String clinicid) 
	{
		
		String categoryTypeQuery = "";
		
		int intcategoryid = 0;
		
		if(categoryid != null && categoryid != "")
		{
			intcategoryid = Integer.parseInt(categoryid);
		}
		
		categoryTypeQuery = "SELECT `hoct_id`,`hoct_category_desc`,a.`hoc_id`,`hoc_category_desc` FROM `hl_order_category_type` a JOIN `hl_order_category` b "
				+ "ON a.`hoc_id` = b.`hoc_id` and hcm_id = "+clinicid;
		if(intcategoryid > 0)
		{
			categoryTypeQuery = categoryTypeQuery.concat(" where a.`hoc_id` = "+intcategoryid);
		}
		categoryTypeQuery = categoryTypeQuery.concat(" order by hoct_id asc");
		
		logger.info("ordercategorytypes query   "+categoryTypeQuery);
		@SuppressWarnings("unchecked")
		List<Object> categoryTypesList = jdbcTemplate.query(categoryTypeQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				
				map.put("tcategoryid", rs.getString("hoc_id"));
				map.put("tcategorydesc", rs.getString("hoc_category_desc"));
				map.put("categorytypeid", rs.getString("hoct_id"));
				map.put("categorytypedesc", rs.getString("hoct_category_desc"));
				
				map.put("index", index);
				
				return map;
			}
		});
		logger.info("categoryTypesList length   "+categoryTypesList.size());
		
		return categoryTypesList;
	
	
	}

	@Override
	public List<Object> getOrderHeadforCategoryTypes(String categorytypeid,String clinicid) {
		String orderHeadQuery = "SELECT `hoh_id`,a.`hoct_id`,`hoh_code`,`hoh_desc`,`hoh_default_load`,`hoct_category_desc`,`hoc_category_desc`,hoh_code,b.hoc_id FROM "
				+ "`hl_order_head` a JOIN `hl_order_category_type` b ON a.`hoct_id` = b.`hoct_id` JOIN `hl_order_category` c ON b.`hoc_id` = c.`hoc_id` and "
				+ "hcm_id = "+clinicid;
		
		int intcategorytypeid = 0;
		
		if(categorytypeid != null && categorytypeid.length() > 0)
		{
			intcategorytypeid = Integer.parseInt(categorytypeid);
		}
		
		
		orderHeadQuery = orderHeadQuery.concat(" where a.hoct_id = "+intcategorytypeid);
		
		logger.info("orderhead query   "+orderHeadQuery);
		@SuppressWarnings("unchecked")
		List<Object> orderHeadList = jdbcTemplate.query(orderHeadQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				
				map.put("headid", rs.getString("hoh_id"));
				map.put("headdesc", rs.getString("hoh_desc"));
				map.put("categoryid", rs.getString("hoc_id"));
				map.put("categorydesc", rs.getString("hoc_category_desc"));
				map.put("categorytypeid", rs.getString("hoct_id"));
				map.put("categorytypedesc", rs.getString("hoct_category_desc"));
				map.put("defaultload", rs.getString("hoh_default_load"));
				map.put("orderheaddesc", rs.getString("hoh_desc"));
				map.put("orderheadcode", rs.getString("hoh_code"));
				
				if(rs.getInt("hoh_default_load") == 0)
				{
					map.put("defaultloaddisp", "No");
					map.put("defaultloadchk", false);
				}
				else if(rs.getInt("hoh_default_load") == 1)
				{
					map.put("defaultloaddisp", "Yes");
					map.put("defaultloadchk", true);
				}
				else
				{
					map.put("defaultloaddisp", "");
					map.put("defaultloadchk", false);
				}
				
				map.put("index", index);
				
				return map;
			}
		});
		logger.info("orderHeadList length   "+orderHeadList.size());
		
		return orderHeadList;
		}

	@Override
	public List<Object> getSuborderForOrderhead(String headid, String clinicid) {
 
			/*String query = "SELECT `hsoc_id`,a.`hoc_id`,a.`hoct_id`,`hoct_category_desc`,a.`hoh_id`,`hsoc_desc`,`hot_id`,a.`hou_id`,`hou_test_units`,`hrt_id`,hoh_desc,"
					+ "`hsoc_gender`,`hsoc_normal_bound_value`,`hsoc_lower_bound_value`,`hsoc_upper_bound_value`,`hsoc_charge`,`hsoc_discount_appear`,`hoh_desc`,"
					+ "CONCAT(`hsoc_lower_bound_value`,' - ',`hsoc_normal_bound_value`)normalboundrange FROM `hl_sub_order_code` a  JOIN `hl_order_head` b ON a.`hoh_id` = "
					+ "b.`hoh_id` JOIN `hl_order_category_type` c ON a.`hoct_id` = c.`hoct_id` JOIN `hl_order_units` d ON a.`hou_id` = d.`hou_id` join hl_order_category e"
					+ " on e.hoc_id = c.hoc_id and e.hcm_id = "+clinicid;*/
		
		String query = "SELECT a.`hsoc_id`,a.`hoc_id`,a.`hoct_id`,`hoct_category_desc`,a.`hoh_id`,`hsoc_desc`,`hot_id`,a.`hou_id`,`hou_test_units`,`hrt_id`,hoh_desc,"
				+ "`hsoc_gender`,`hsoc_normal_bound_value`,`hsoc_lower_bound_value`,`hsoc_upper_bound_value`,`hsoc_charge`,`hsoc_discount_appear`,`hoh_desc`,"
				+ "CONCAT(`hsoc_lower_bound_value`,' - ',`hsoc_normal_bound_value`)normalboundrange,COALESCE(`hpfs_fee`,`hsoc_charge`) physicianfee FROM "
				+ "`hl_sub_order_code` a JOIN `hl_order_head` b ON a.`hoh_id` = b.`hoh_id` JOIN `hl_order_category_type` c ON a.`hoct_id` = c.`hoct_id` JOIN "
				+ "`hl_order_units` d ON a.`hou_id` = d.`hou_id` JOIN hl_order_category e ON e.hoc_id = c.hoc_id AND e.hcm_id = "+clinicid
				+" LEFT JOIN `hp_physician_fee_structure` f ON f.`hoh_id` = b.`hoh_id` AND f.`hsoc_id` = a.`hsoc_id` ";
			
			int intheadid = 0;
			if(headid != null)
			{
				intheadid = Integer.parseInt(headid);
			}
			
				query = query.concat(" where a.`hoh_id` = "+headid);
			
			logger.info("suborder query   "+query);
			@SuppressWarnings("unchecked")
			List<Object> suborderList = jdbcTemplate.query(query, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int index) throws SQLException {
					
					Map<String, Object> map = new HashMap<String, Object>();
					index++;
					
					map.put("id", rs.getString("hsoc_id"));
					map.put("value", rs.getString("hsoc_desc"));
					
					map.put("suborderid", rs.getString("hsoc_id"));
					map.put("categoryid", rs.getString("hoc_id"));
					map.put("categorytypeid", rs.getString("hoct_id"));
					map.put("categorytypedesc", rs.getString("hoct_category_desc"));
					map.put("headid", rs.getString("hoh_id"));
					
					map.put("suborderdesc", rs.getString("hsoc_desc"));
					map.put("tubeid", rs.getString("hot_id"));
					map.put("unitid", rs.getString("hou_id"));
					map.put("unitdesc", rs.getString("hou_test_units"));
					map.put("reporttime", rs.getString("hrt_id"));
					
					map.put("gender", rs.getString("hsoc_gender"));
					map.put("normalboundvalue", rs.getString("hsoc_normal_bound_value"));
					map.put("lowerboundvalue", rs.getString("hsoc_lower_bound_value"));
					map.put("upperboundvalue", rs.getString("hsoc_upper_bound_value"));
					map.put("testcharge", rs.getDouble("hsoc_charge"));
					
					map.put("discountappear", rs.getString("hsoc_discount_appear"));
					map.put("headdesc", rs.getString("hoh_desc"));
					map.put("normalboundrange", rs.getString("normalboundrange"));
					map.put("headdesc", rs.getString("hoh_desc"));
					map.put("physicianfee", rs.getDouble("physicianfee"));
					map.put("suborderprice", rs.getString("physicianfee"));
					
					map.put("index", index);
					
					return map;
				}
			});
			logger.info("suborderList length   "+suborderList);
			 
		
		return suborderList;
	}

	@Override
	public List<Object> getOrderTubesforCategory(String categoryid,
			String clinicid) {
		
		String orderTubeQuery = "SELECT `hot_id`,`hot_desc`,`hoc_category_desc`,c.`hoc_id` "
				+ "FROM `hl_order_tube` a  JOIN `hl_order_category` c ON c.`hoc_id` = a.`hoc_id` and hcm_id = "+clinicid;
		
		int intcategoryid = 0;
		if(categoryid != null)
		{
			intcategoryid = Integer.parseInt(categoryid);
		}
		
		if(intcategoryid > 0)
		{
			orderTubeQuery = orderTubeQuery.concat(" where a.`hoc_id` = "+intcategoryid);
		}
		orderTubeQuery = orderTubeQuery.concat(" order by hot_id asc");
		
		logger.info("ordertubes orderTubeQuery   "+orderTubeQuery);
		@SuppressWarnings("unchecked")
		List<Object> ordertubesList = jdbcTemplate.query(orderTubeQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				
				map.put("tubeid", rs.getString("hot_id"));
				map.put("tubedesc", rs.getString("hot_desc"));
				map.put("categoryid", rs.getString("hoc_id"));
				map.put("categorydesc", rs.getString("hoc_category_desc"));
				/*map.put("categorytypeid", rs.getString("hoct_id"));
				map.put("categorytypedesc", rs.getString("hoct_category_desc"));*/
				
				map.put("index", index);
				
				return map;
			}
		});
		logger.info("ordertubesList length   "+ordertubesList.size());
		
		return ordertubesList;
	}

	@Override
	public List<Object> getOrderUnitsforCategoryType(String categorytypeid,
			String clinicid) {
		String orderunitQuery = "";
		
		int intcategorytypeid = 0;
		
		orderunitQuery = "SELECT `hou_id`,a.`hoct_id`,`hou_test_units`,`hoc_category_desc`,c.`hoc_id`,`hoct_category_desc` FROM `hl_order_units` a "
				+ "JOIN `hl_order_category_type` b ON a.`hoct_id` = b.`hoct_id` JOIN `hl_order_category` c ON c.`hoc_id` = b.`hoc_id` and hcm_id = "+clinicid;
		
		intcategorytypeid = Integer.parseInt(categorytypeid);
		
		logger.info("intcategorytypeid   "+intcategorytypeid);
		if(intcategorytypeid > 0 || intcategorytypeid == -1)
		{
			orderunitQuery = orderunitQuery.concat(" where a.`hoct_id` = "+intcategorytypeid);
		}
		
		logger.info("orderunits query   "+orderunitQuery);
		@SuppressWarnings("unchecked")
		List<Object> orderunitsList = jdbcTemplate.query(orderunitQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				
				Map<String, Object> map = new HashMap<String, Object>();
				index++;
				
				map.put("unitid", rs.getString("hou_id"));
				map.put("unitdesc", rs.getString("hou_test_units"));
				map.put("categoryid", rs.getString("hoc_id"));
				map.put("categorydesc", rs.getString("hoct_category_desc"));
				map.put("categorytypeid", rs.getString("hoc_id"));
				map.put("categorytypedesc", rs.getString("hoct_category_desc"));
				
				map.put("index", index);
				
				return map;
			}
		});
		logger.info("orderunitsList length   "+orderunitsList.size());
		
		return orderunitsList;
	}

}
