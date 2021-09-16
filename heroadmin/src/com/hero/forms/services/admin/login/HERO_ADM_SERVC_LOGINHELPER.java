package com.hero.forms.services.admin.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.forms.services.admin.login.HERO_ADM_SERVC_ILOGINDAO;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_LOGINHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validateLogin(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin(formData,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validssologin(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) 
			 throws ClassNotFoundException
	{
	return loginDAOobj.validssologin(formData,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validateLogin1(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin1(formData,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO changepassword(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.changepassword(formData,httpRequest);
	}
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerforgotpassword(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.registerforgotpassword(formData,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO fetchUserList(HERO_ADM_SERVC_ILOGINDAO loginDAOobj)
	{
		return loginDAOobj.fetchUserList(loginDAOobj);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerUser(String formData,HERO_ADM_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
	return loginDAOobj.registerUser(formData);
	}
	
	
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO herosettingsurl(String modulename, HERO_ADM_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
		return loginDAOobj.herosettingsurl(modulename);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO tokenSignout(String tokenkey, HERO_ADM_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
		return loginDAOobj.tokenSignout(tokenkey);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadDashboardGraph(HERO_ADM_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
		return loginDAOobj.loadDashboardGraph();
	}
	
}
