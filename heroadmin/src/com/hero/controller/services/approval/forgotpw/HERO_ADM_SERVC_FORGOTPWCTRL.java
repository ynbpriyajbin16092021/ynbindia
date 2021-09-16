package com.hero.controller.services.approval.forgotpw;

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
import com.hero.forms.services.approval.forgotpw.HERO_ADM_SERVC_FORGOTPWHELPER;
import com.hero.forms.services.approval.forgotpw.HERO_ADM_SERVC_IFORGOTPWDAO;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

@Controller
@SessionAttributes( { "loginObj" })
public class HERO_ADM_SERVC_FORGOTPWCTRL {

	
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_FORGOTPWCTRL.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
	@Autowired
	private HERO_ADM_SERVC_FORGOTPWHELPER forgotpwHelperOBJ;
	@Autowired
	private HERO_ADM_SERVC_IFORGOTPWDAO forgotpwDAOobj;
	@Autowired
	private HERO_ADM_SERVC_INVENTORYRESPONSEINFO inventoryResponseInfoOBJ;
	
	
	@RequestMapping(value = "/forgotpasswordlist", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> forgotpasswordlist(HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to forgotpasswordlist   ");

			inventoryResponseInfoOBJ = forgotpwHelperOBJ.forgotpasswordlist(forgotpwDAOobj);
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
			error_log.info(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	} 
	
	@RequestMapping(value = "/resetpassword/{forgotpwid}", method = RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> resetpassword(@PathVariable(value="forgotpwid")String forgotpwid,HttpServletRequest httpRequest) {
		try {
			log.info("Welcome to forgotpasswordlist   ");

			inventoryResponseInfoOBJ = forgotpwHelperOBJ.resetpassword(forgotpwid,forgotpwDAOobj);
			
			return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(),
					HttpStatus.OK);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			error_log.info(e);
			return HERO_ADM_SERVC_HOSURINVENTORYUTIL.returnExceptionFormat(e);
		}
	} 

}
