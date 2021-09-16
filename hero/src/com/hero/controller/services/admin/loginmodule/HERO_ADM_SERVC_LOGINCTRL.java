package com.hero.controller.services.admin.loginmodule;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.hero.forms.services.admin.login.HERO_ADM_SERVC_ILOGINDAO;
import com.hero.forms.services.admin.login.HERO_ADM_SERVC_LOGINHELPER;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

@Controller
@SessionAttributes( { "loginObj" })
public class HERO_ADM_SERVC_LOGINCTRL {

	
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_LOGINCTRL.class);
	private static Logger error_log = Logger.getLogger("requestLogger");
	
	@Autowired
	private HERO_ADM_SERVC_LOGINHELPER loginHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_ILOGINDAO loginDAOobj;
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSEINFO inventoryResponseInfoOBJ;
	
	
	@RequestMapping(value = "/validlogin", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> validuserlogin(@RequestBody String formData,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to Validate Login   " + formData);
			
			inventoryResponseInfoOBJ = loginHelperOBJ.validateLogin(formData, loginDAOobj,httpRequest);
			
			String responseType = inventoryResponseInfoOBJ.getInventoryResponse().getResponseType();
			
			if(responseType != null && responseType.equals("S")){
			Map<String, Object> userDetailsMap = (Map<String, Object>) inventoryResponseInfoOBJ.getInventoryResponse().getResponseObj();
			List<Object> userDetails = (List<Object>) userDetailsMap.get("userDetails");
			log.info("size          "+userDetails.size());
			}
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			error_log.info(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	}	
	
	
	@RequestMapping(value = "/signout", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> signOut(HttpServletRequest httpRequest,HttpServletResponse httpResponse) {
		
		try {
			
			HttpSession session = httpRequest.getSession();
			session.invalidate();
			
			/*RequestDispatcher rd = httpRequest.getRequestDispatcher("/forms/admin/login");
			rd.forward(httpRequest, httpResponse);*/
			
			httpResponse.sendRedirect("login");
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}

	}
	
	
	@RequestMapping(value = "/tokensignout/{tokenkey}", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> tokenSignout(@PathVariable("tokenkey")String tokenkey) {
		
		try {
			inventoryResponseInfoOBJ = loginHelperOBJ.tokenSignout(tokenkey,loginDAOobj);
			
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			//e.printStackTrace();
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}

	}
	

	@RequestMapping(value = "/registerforgotpassword", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> registerforgotpassword(@RequestBody String formData,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to registerforgotpassword  " + formData);

			inventoryResponseInfoOBJ = loginHelperOBJ.registerforgotpassword(formData, loginDAOobj,httpRequest);
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	}
	
	@RequestMapping(value = "/heroviewimage", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> heroViewImage(@RequestBody String formData,HttpServletRequest httpRequest) {
		
		try {
			inventoryResponseInfoOBJ = loginHelperOBJ.heroViewImage(formData, loginDAOobj,httpRequest);
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			log.error(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}

	}

}
