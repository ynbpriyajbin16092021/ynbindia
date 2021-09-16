package com.hero.controller.view.stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hero.services.admin.lov.HERO_ADM_SERVC_IINVENTORYLOV;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

@Controller
public class HERO_STK_VIEW_TEMPLATECTRL {

	@Autowired
	private HERO_ADM_SERVC_IINVENTORYLOV inventoryLOVobj;
	
	@RequestMapping(value="/organization")
	public ModelAndView gotoCompany(HttpServletRequest request)
	{
		ModelAndView model = new ModelAndView();
		
		String serverPath = request.getRealPath("/");
		model.addObject("serverPath",serverPath);
		
		model.setViewName("/forms/template/organization");
		
		return model;
	}
	
	@RequestMapping(value="/smstemplate")
	public ModelAndView gotoSMSTemplate()
	{
		
		String usertypeApplnQuery = "SELECT `usertype_id`,`usertype_dept` FROM `hero_user_type` where usertype_id != '1'";
		
		@SuppressWarnings("unchecked")
		List<Object> usertypeApplnList = inventoryLOVobj.getApplnList(usertypeApplnQuery);
		
		String smsTemplateQuery = "";
		String emailtemplateQuery = "";
		if(usertypeApplnList!= null && usertypeApplnList.size()>0){
			int a[]=new int[usertypeApplnList.size()];
			for(int i = 0; i<usertypeApplnList.size(); i++){
				Map<String, Object> PatientsDetailsMap = (Map<String, Object>) usertypeApplnList.get(i);
				int applnid = (int) PatientsDetailsMap.get("applnid");
				a[i]=applnid;
			}
			boolean withpharmacy = HERO_ADM_SERVC_HOSURINVENTORYUTIL.contains(a, 1);
			
			if(withpharmacy){
				smsTemplateQuery = "SELECT `sms_temp_id`,`sms_temp_name`,`sms_message_content`,`param_desc` FROM `hero_sms_template`";
				emailtemplateQuery="SELECT `email_temp_id`,`email_temp_name`,`email_temp_subject`,`email_temp_content`,`param_desc` FROM `hero_email_template`";
			}else {
				smsTemplateQuery = "SELECT `sms_temp_id`,`sms_temp_name`,`sms_message_content`,`param_desc` FROM `hero_sms_template` WHERE `sms_appln_type` != 1";
				emailtemplateQuery="SELECT `email_temp_id`,`email_temp_name`,`email_temp_subject`,`email_temp_content`,`param_desc` FROM `hero_email_template`"
						+ " WHERE `email_temp_appln_type` !=1";
			}
		}
		

		List<Object> emailTemplateList = inventoryLOVobj.getemailTemplateList(emailtemplateQuery);
		List<Object> smsTemplateList = inventoryLOVobj.getSMSTemplateList(smsTemplateQuery);
		
		ModelAndView model = new ModelAndView();
		
		model.addObject("smsTemplateList",smsTemplateList);
		model.addObject("emailTemplateList",emailTemplateList);
		
		model.setViewName("/forms/admin/masters/smstemplate");
		
		return model;
	}
}
