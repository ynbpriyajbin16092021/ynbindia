package com.hero.clinic.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;
import com.hero.clinic.services.response.HERO_CLC_SERVC_CLINICRESPONSEINFO;


public interface HERO_CLC_SERVC_ILOGINDAO {
	public HERO_CLC_SERVC_CLINICRESPONSEINFO validLogin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_CLC_SERVC_CLINICRESPONSEINFO validssologin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_CLC_SERVC_CLINICRESPONSEINFO changepassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_CLC_SERVC_CLINICRESPONSEINFO registerforgotpassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_CLC_SERVC_CLINICRESPONSEINFO fetchUserList(HERO_CLC_SERVC_ILOGINDAO loginDAOobj);
	public HERO_CLC_SERVC_CLINICRESPONSEINFO registerUser(String formData)throws ClassNotFoundException;
	public HERO_CLC_SERVC_CLINICRESPONSEINFO herosettingsurl(String modulename)throws ClassNotFoundException;
}
