package com.hero.stock.controller.inventory;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;

@Controller
public class HERO_STK_TEMPLATECTRL {

	@Autowired
	private HERO_STK_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/organization")
	public ModelAndView gotoCompany()
	{
		ModelAndView model = new ModelAndView();
		
		model.setViewName("/forms/template/organization");
		
		return model;
	}
	
	@RequestMapping(value="/smstemplate")
	public ModelAndView gotoSMSTemplate()
	{
		String smsTemplateQuery = "SELECT `sms_temp_id`,`sms_temp_name`,`sms_message_content` FROM `hero_sms_template`";
		List<Object> smsTemplateList = inventoryLOVobj.getSMSTemplateList(smsTemplateQuery);
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("smsTemplateList",smsTemplateList);
		
		model.setViewName("/forms/template/smstemplate");
		
		return model;
	}
}
