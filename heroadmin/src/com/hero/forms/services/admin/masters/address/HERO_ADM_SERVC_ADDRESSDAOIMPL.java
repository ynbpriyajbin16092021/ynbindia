package com.hero.forms.services.admin.masters.address;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

public class HERO_ADM_SERVC_ADDRESSDAOIMPL extends HERO_ADM_SERVC_INVENTORYDAO implements HERO_ADM_SERVC_ADDRESSDAOOBJ {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	Logger log = Logger.getLogger(HERO_ADM_SERVC_ADDRESSDAOIMPL.class);
	
	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCountry() {
		String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_countries";
		
		log.info("country Query    "+selectQuery);
		
		@SuppressWarnings("unchecked")
		List<Object> countryList = jdbcTemplate.query(selectQuery, new RowMapper() {
			
			@Override
			public Object mapRow(ResultSet rs, int arg1) throws SQLException {
				Map<String,Object> details = new HashMap<String,Object>();
				
				details.put("id",rs.getString("id"));
				details.put("countryname",rs.getString("name"));
				return details;
			}
		});
		
		inventoryResponseOBJ.setResponseType("S");
		inventoryResponseOBJ.setResponseObj(countryList);
		
		inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
		
		return inventoryResponseInfoOBJ;
	}

	@Override
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getStates(int countryId) {
			log.info("countryId  "+countryId);
			String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_states WHERE country_id = '"+countryId+"'";
			
			log.info("state Query    "+selectQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> stateList = jdbcTemplate.query(selectQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String,Object> details = new HashMap<String,Object>();
					
					details.put("id",rs.getString("id"));
					details.put("statename",rs.getString("name"));
					return details;
				}
			});
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(stateList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		
	}
	

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getCities(int stateId) {
			log.info("stateId  "+stateId);
			String selectQuery = "SELECT `id`, `name` FROM hero_admin_lov_city WHERE state_id = '"+stateId+"'";
			
			log.info("hero_city_lov Query    "+selectQuery);
			
			@SuppressWarnings("unchecked")
			List<Object> cityList = jdbcTemplate.query(selectQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					Map<String,Object> details = new HashMap<String,Object>();
					
					details.put("id",rs.getString("id"));
					details.put("cityname",rs.getString("name"));
					return details;
				}
			});
			
			inventoryResponseOBJ.setResponseType("S");
			inventoryResponseOBJ.setResponseObj(cityList);
			
			inventoryResponseInfoOBJ.setInventoryResponse(inventoryResponseOBJ);
			
			return inventoryResponseInfoOBJ;
		
	}
	
	
}
