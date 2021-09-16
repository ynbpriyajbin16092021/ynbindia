package com.hero.clinic.controller.view.clinic;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hero.clinic.forms.login.HERO_CLC_SERVC_ILOGINDAO;
import com.hero.clinic.services.lov.HERO_CLC_SERVC_ICLINICLOV;
import com.hero.clinic.services.util.HERO_CLC_SERVC_HERBZCLINICUTIL;

@Controller
@SessionAttributes( {"uid"})
public class HERO_CLC_VIEW_LOGINCTRL {

	private static Logger log = Logger.getLogger(HERO_CLC_VIEW_LOGINCTRL.class);
	
	@Autowired
	private HERO_CLC_SERVC_ILOGINDAO loginDAO;
	@Autowired
	private HERO_CLC_SERVC_ICLINICLOV inventoryLOVobj;

	@RequestMapping("/login")
	public ModelAndView loginPremia() {
		return new ModelAndView("/forms/login/login", "message", "");
	}

	@RequestMapping(value = "/registration")
	public ModelAndView registerPremia() {
		/*
		 * List<LoginRequest> userList = loginDAO.fetchUsers(loginDAO);
		 * ModelAndView model = new ModelAndView();
		 * model.addObject("userList",userList);
		 * model.setViewName("/forms/login/registration"); return model;
		 */
		return new ModelAndView("/forms/login/registration", "message",
				"Success");

	}
	//appointmentrecent
	
	@RequestMapping(value="/dashboard")
	public ModelAndView gotoHomepage(@RequestParam(value = "uid", required = false) String uid,HttpServletRequest httpRequest)
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/template/dashboard");
		  
		Map<String, Object> map = (Map<String, Object>) inventoryLOVobj.getDashboardDetails(httpRequest);
		log.info("map      "+map);
		
		String clinicCount = (String)HERO_CLC_SERVC_HERBZCLINICUTIL.getValueFromList(map, "clinicCount");
		String patientCount = (String)HERO_CLC_SERVC_HERBZCLINICUTIL.getValueFromList(map, "patientCount");
		String doctorCount = (String)HERO_CLC_SERVC_HERBZCLINICUTIL.getValueFromList(map, "doctorCount");
		String receptionistCount = (String)HERO_CLC_SERVC_HERBZCLINICUTIL.getValueFromList(map, "receptionistCount");
		String walkinCount = (String)HERO_CLC_SERVC_HERBZCLINICUTIL.getValueFromList(map, "walkinCount");
		
		
		model.addObject("clinicCount",clinicCount);
		model.addObject("patientCount",patientCount);
		model.addObject("doctorCount",doctorCount);
		model.addObject("receptionistCount",doctorCount);
		model.addObject("walkinCount",walkinCount);
		model.addObject("doctorrecentList",map.get("doctorrecent"));
		model.addObject("appointmentrecentList",map.get("appointmentrecent"));
		model.addObject("appointmentfailurerecentList",map.get("appointmentfailurerecent"));
		model.addObject("patientrecentList",map.get("patientrecent"));
		model.addObject("shedulerecentList",map.get("shedulerecent"));
		model.addObject("consultappointmentList",map.get("consultappointment"));
		
		return model;
		
		/*return new ModelAndView("/forms/template/dashboard","message","success");*/
	}
}
