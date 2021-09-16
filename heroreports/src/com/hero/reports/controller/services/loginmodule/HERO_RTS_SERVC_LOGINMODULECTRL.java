package com.hero.reports.controller.services.loginmodule;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hero.reports.forms.login.HERO_RTS_ILOGINDAO;
import com.hero.reports.forms.login.HERO_RTS_LOGINHELPER;
import com.hero.reports.response.HERO_RTS_RESPONSEINFO;
import com.hero.reports.util.HEROHOSURINVENTORYUTIL;

@Controller
@SessionAttributes( { "loginObj" })
public class HERO_RTS_SERVC_LOGINMODULECTRL {

	private static Logger log = Logger.getLogger(HERO_RTS_SERVC_LOGINMODULECTRL.class);
	
	@Autowired
	private HERO_RTS_LOGINHELPER loginHelperOBJ;
	@Autowired
	private HERO_RTS_ILOGINDAO loginDAOobj;
	@Autowired
	private HERO_RTS_RESPONSEINFO inventoryResponseInfoOBJ;
	
	
	@RequestMapping(value = "/validlogin", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> validuserlogin(@RequestBody String formData,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to Validate Login   " + formData);

			inventoryResponseInfoOBJ = loginHelperOBJ.validateLogin(formData, loginDAOobj,httpRequest);
			
			List<Object> userDetails = (List<Object>) inventoryResponseInfoOBJ.getInventoryResponse().getResponseObj();
			log.info("size          "+userDetails.size());
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			return HEROHOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	}

	@RequestMapping(value = "/validssologin", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> validssologin(@RequestBody String formData,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to Validate Login   " + formData);

			inventoryResponseInfoOBJ = loginHelperOBJ.validssologin(formData, loginDAOobj,httpRequest);
			
			Map<String, Object> userMap = (Map<String, Object>) inventoryResponseInfoOBJ.getInventoryResponse().getResponseObj();
			List<Object> userDetails = (List<Object>) userMap.get("userDetails");
			log.info("size          "+userDetails.size());
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			return HEROHOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	}
	
	
	@RequestMapping(value = "/changepassword", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> changepassword(@RequestBody String formData,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to Validate Login   " + formData);

			inventoryResponseInfoOBJ = loginHelperOBJ.changepassword(formData, loginDAOobj,httpRequest);
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HEROHOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	}
	
	@RequestMapping(value = "/userlist", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> fetchUserList() {
		log.info("Welcome to userList Method");
		inventoryResponseInfoOBJ = loginHelperOBJ
				.fetchUserList(loginDAOobj);
		/*
		 * return new
		 * ResponseEntity<Object>(response.getLoginResponse().getResponseObj(),
		 * new HttpHeaders(),HttpStatus.OK);
		 */
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
				HttpStatus.OK);
	}

	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> registerUser(@RequestBody String formData) {
		
		try {
			inventoryResponseInfoOBJ = loginHelperOBJ.registerUser(formData, loginDAOobj);
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HEROHOSURINVENTORYUTIL.returnExceptionFormat(e);
		}

	}
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> signOut(HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
		
		try {
			
			HttpSession session = httpRequest.getSession();
			session.invalidate();
			
			/*RequestDispatcher rd = httpRequest.getRequestDispatcher("/forms/login");
			rd.forward(httpRequest, httpResponse);*/
			
			httpResponse.sendRedirect("login");
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HEROHOSURINVENTORYUTIL.returnExceptionFormat(e);
		}

	}

}
