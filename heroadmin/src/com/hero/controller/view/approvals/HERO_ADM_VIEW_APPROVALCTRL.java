package com.hero.controller.view.approvals;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;

@Controller
public class HERO_ADM_VIEW_APPROVALCTRL {
	private static Logger log = Logger.getLogger(HERO_ADM_VIEW_APPROVALCTRL.class);
	
	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping("/forgotpasswordlistview")
	public ModelAndView loginPremia() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/forms/admin/approvals/forgotpasswordlist");
		 
		  
		  return model;
	}
}
