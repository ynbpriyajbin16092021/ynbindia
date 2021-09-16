package com.herotokencentre.controller.service.admin;

import javax.servlet.annotation.MultipartConfig;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.herotokencentre.controller.model.HMSBeans;

@Controller
/*@MultipartConfig(fileSizeThreshold = 20971520)*/
public class AdminServiceController extends HMSBeans {

	final static Logger logger = Logger.getLogger(AdminServiceController.class);
	
	
	@RequestMapping(value = "/genherotokenkey", method = RequestMethod.POST)
    public ResponseEntity<Object> genherotokenkey(@RequestBody JSONObject request) {
		logger.info("Welcome to genherotokenkey");
         responseObj = adminservice.genherotokenkey(request);
         
         return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/getherotokenkey/{tokenkey}", method = RequestMethod.GET)
    public ResponseEntity<Object> getherotokenkey(@PathVariable(value="tokenkey")String tokenkey) {
		logger.info("Welcome to getherotokenkey");
         responseObj = adminservice.getherotokenkey(tokenkey);
         
         return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
    }
	
	@RequestMapping(value = "/removetoken/{tokenkey}", method = RequestMethod.GET)
    public ResponseEntity<Object> removetoken(@PathVariable(value="tokenkey")String tokenkey) {
		 logger.info("Welcome to getherotokenkey");
         adminservice.removetoken(tokenkey);
         
         return new ResponseEntity<Object>(responseObj, HttpStatus.OK);
    }
}
