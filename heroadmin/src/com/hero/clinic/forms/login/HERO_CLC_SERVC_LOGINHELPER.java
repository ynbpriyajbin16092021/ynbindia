package com.hero.clinic.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;
import com.hero.clinic.forms.login.HERO_CLC_SERVC_ILOGINDAO;
import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;




public class HERO_CLC_SERVC_LOGINHELPER {

	public HERO_CLC_SERVC_CLINICRESPONSEINFO validateLogin(String formData,HERO_CLC_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin(formData,httpRequest);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO validssologin(String formData,HERO_CLC_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) 
			 throws ClassNotFoundException
	{
	return loginDAOobj.validssologin(formData,httpRequest);
	}
	
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO changepassword(String formData,HERO_CLC_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.changepassword(formData,httpRequest);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO registerforgotpassword(String formData,HERO_CLC_SERVC_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.registerforgotpassword(formData,httpRequest);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO fetchUserList(HERO_CLC_SERVC_ILOGINDAO loginDAOobj)
	{
		return loginDAOobj.fetchUserList(loginDAOobj);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO herosettingsurl(String modulename, HERO_CLC_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
		return loginDAOobj.herosettingsurl(modulename);
	}
	
	public HERO_CLC_SERVC_CLINICRESPONSEINFO registerUser(String formData,HERO_CLC_SERVC_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
	return loginDAOobj.registerUser(formData);
	}
}
