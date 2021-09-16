package com.herotokencentre.controller.model;

import org.springframework.beans.factory.annotation.Autowired;
/*import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;*/
import org.springframework.jdbc.core.JdbcTemplate;

import com.herotokencentre.controller.model.IHMSUtilService;
import com.herotokencentre.controller.response.HMSResponse;
import com.herotokencentre.controller.response.HMSResponseBean;
import com.herotokencentre.controller.service.admin.IAdminService;

/*@PropertySource(value = { "classpath:application.properties" })*/
public class HMSBeans {
	
	@Autowired
	protected HMSResponseBean responseBeanObj;
	@Autowired
	protected HMSResponse responseObj;
	@Autowired
	protected JdbcTemplate jdbcTemplate;
	/*@Autowired
    public Environment env;*/
	
	@Autowired
	protected IAdminService adminservice;
	@Autowired
	protected IHMSUtilService utilservice;
	
	}
