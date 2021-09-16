package com.hero.services.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSE;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_INVENTORYDAO {
	@Autowired
	protected HERO_ADM_SERVC_INVENTORYRESPONSE inventoryResponseOBJ;
	@Autowired
	protected HERO_ADM_SERVC_INVENTORYRESPONSEINFO inventoryResponseInfoOBJ;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
}
