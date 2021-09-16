package com.hero.reports.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hero.reports.response.HERO_RTS_RESPONSE;
import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public class HERO_RTS_INVENTORYDAO {
	@Autowired
	protected HERO_RTS_RESPONSE inventoryResponseOBJ;
	@Autowired
	protected HERO_RTS_RESPONSEINFO inventoryResponseInfoOBJ;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
}
