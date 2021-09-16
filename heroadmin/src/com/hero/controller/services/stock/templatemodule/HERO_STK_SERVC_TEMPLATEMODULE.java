package com.hero.controller.services.stock.templatemodule;

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

import com.hero.forms.services.admin.templates.organization.HERO_SERVC_IORGANISATIONDAO;
import com.hero.forms.services.admin.templates.organization.HERO_SERVC_ORGANISATIONHELPER;
import com.hero.forms.services.admin.templates.sms.HERO_SERVC_ISMSTEMPLATEDAO;
import com.hero.forms.services.admin.templates.sms.HERO_SERVC_SMSTEMPLATEHELPER;
import com.hero.services.admin.util.HERO_ADM_SERVC_INVENTORYDAO;

@Controller
public class HERO_STK_SERVC_TEMPLATEMODULE extends HERO_ADM_SERVC_INVENTORYDAO {
	private static Logger log = Logger.getLogger(HERO_STK_SERVC_TEMPLATEMODULE.class);
	@Autowired
	protected HERO_SERVC_ORGANISATIONHELPER orgnHelperOBJ;
	@Autowired
	protected HERO_SERVC_IORGANISATIONDAO iorgnDAOOBJ;
	
	@Autowired
	protected HERO_SERVC_SMSTEMPLATEHELPER smsHelperOBJ;
	@Autowired
	protected HERO_SERVC_ISMSTEMPLATEDAO ismstemplateDAOOBJ;
	
	@RequestMapping(value="/loadorganization",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadOrgn()
	{
		inventoryResponseInfoOBJ = orgnHelperOBJ.loadOrgn(iorgnDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveorganization",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveOrgn(@RequestBody String orgnData)
	{
		log.info("orgnaizationData    "+orgnData);
		inventoryResponseInfoOBJ = orgnHelperOBJ.saveOrgn(iorgnDAOOBJ, orgnData);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/loadsmssettings",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> loadSMSSettings()
	{
		inventoryResponseInfoOBJ = smsHelperOBJ.loadSMSSettings(ismstemplateDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/getsmscontent/{templateid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getSMSContent(@PathVariable("templateid") String templateid)
	{
		inventoryResponseInfoOBJ = smsHelperOBJ.getSMSContent(ismstemplateDAOOBJ,templateid);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/savesmscontent",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveSMSContent(@RequestBody String smscontent)
	{
	inventoryResponseInfoOBJ = smsHelperOBJ.saveSMSContent(ismstemplateDAOOBJ,smscontent);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/saveemailsettings",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveEmailsettings(@RequestBody String emailcontent)
	{
	inventoryResponseInfoOBJ = smsHelperOBJ.saveEmailsettings(ismstemplateDAOOBJ,emailcontent);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
	@RequestMapping(value="/saveemailcontent",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveemailcontent(@RequestBody String emailcontent)
	{
	inventoryResponseInfoOBJ = smsHelperOBJ.saveEmailcontent(ismstemplateDAOOBJ,emailcontent);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ, new HttpHeaders(), HttpStatus.OK);
	}
}
