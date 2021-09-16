package com.hero.controller.services.clinic.clinicmodule;



import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

import com.hero.clinic.forms.clinics.HERO_CLC_SERVC_CLINICHELPER;
import com.hero.clinic.forms.clinics.HERO_CLC_SERVC_ICLINICSDAO;
import com.hero.clinic.services.util.HERO_CLC_SERVC_CLINICDAO;



@Controller
public class HERO_CLC_SERVC_MODULECTRL  extends HERO_CLC_SERVC_CLINICDAO{

	@Autowired
	protected HERO_CLC_SERVC_CLINICHELPER clinicsHelperOBJ;
	@Autowired
	protected HERO_CLC_SERVC_ICLINICSDAO clinicsDAOOBJ;
	 
	@RequestMapping(value="/clinicslist",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> clinicsList()
	{
		
		inventoryResponseInfoOBJ = clinicsHelperOBJ.loadclinics(clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveclinic",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> saveClinic(@RequestBody String clinicData)
	{
		
		inventoryResponseInfoOBJ = clinicsHelperOBJ.saveclinic(clinicData, clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/savelab",method=RequestMethod.POST)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> savelab(@RequestBody String clinicData)
	{
		
		inventoryResponseInfoOBJ = clinicsHelperOBJ.savelab(clinicData, clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);
	}
	
	@RequestMapping(value="/getlabdetail/{labid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getlabdetail(@PathVariable(value="labid") String labid)
	{
		inventoryResponseInfoOBJ = clinicsHelperOBJ.getlabdetail(labid,clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);		
	}
	
	@RequestMapping(value="/getclinicdetail/{cid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getclinicdetail(@PathVariable(value="cid") String cid)
	{
		inventoryResponseInfoOBJ = clinicsHelperOBJ.getclinicdetail(cid,clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);		
	}
	
	@RequestMapping(value="/getprescriptions/{pid}",method=RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@ResponseBody
	public ResponseEntity<Object> getprescriptions(@PathVariable(value="pid") String pid)
	{
		inventoryResponseInfoOBJ = clinicsHelperOBJ.getprescriptions(pid,clinicsDAOOBJ);
		return new ResponseEntity<Object>(inventoryResponseInfoOBJ,new HttpHeaders(),HttpStatus.OK);		
	}
}
