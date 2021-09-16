package com.hero.services.admin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import com.hero.services.admin.response.ClinicResponse;
import com.hero.services.admin.response.ClinicResponseInfo;

public class ClinicDAO {
	@Autowired
	protected ClinicResponse clinicResponseOBJ;
	@Autowired
	protected ClinicResponseInfo clinicResponseInfoOBJ;
	
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
}
