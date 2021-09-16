package com.hero.stock.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hero.stock.response.HERO_STK_RESPONSE;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_INVENTORYDAO {
	@Autowired
	protected HERO_STK_RESPONSE inventoryResponseOBJ;
	@Autowired
	protected HERO_STK_RESPONSEINFO inventoryResponseInfoOBJ;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;

	public HERO_STK_RESPONSEINFO loadtransferlist(String transferid,
			String storeid) {
		// TODO Auto-generated method stub
		return null;
	}
}
