package com.hero.reports.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public class HERO_RTS_LOGINHELPER {

	public HERO_RTS_RESPONSEINFO validateLogin(String formData,HERO_RTS_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin(formData,httpRequest);
	}
	
	public HERO_RTS_RESPONSEINFO validssologin(String formData,HERO_RTS_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) 
			 throws ClassNotFoundException
{
return loginDAOobj.validssologin(formData,httpRequest);
}
	
	public HERO_RTS_RESPONSEINFO changepassword(String formData,HERO_RTS_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.changepassword(formData,httpRequest);
	}
	
	public HERO_RTS_RESPONSEINFO fetchUserList(HERO_RTS_ILOGINDAO loginDAOobj)
	{
		return loginDAOobj.fetchUserList(loginDAOobj);
	}
	
	public HERO_RTS_RESPONSEINFO registerUser(String formData,HERO_RTS_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
	return loginDAOobj.registerUser(formData);
	}
}
