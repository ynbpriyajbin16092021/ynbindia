package com.hero.clinic.services.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSE;
import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;

public class HERO_CLC_SERVC_CLINICDAO {
	@Autowired
	protected HERO_CLC_SERVC_CLINICRESPONSE inventoryResponseOBJ;
	@Autowired
	protected HERO_CLC_SERVC_CLINICRESPONSEINFO inventoryResponseInfoOBJ;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
}
