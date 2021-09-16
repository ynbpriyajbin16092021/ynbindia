package com.hero.stock.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.forms.login.HERO_STK_ILOGINDAO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_LOGINHELPER {

	public HERO_STK_RESPONSEINFO validateLogin(String formData,HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin(formData,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO guestLogin(HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.guestLogin(httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO validateLogin1(String formData,HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.validLogin1(formData,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO validssologin(String formData,HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) 
			 throws ClassNotFoundException
{
return loginDAOobj.validssologin(formData,httpRequest);
}
	
	public HERO_STK_RESPONSEINFO changepassword(String formData,HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.changepassword(formData,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO registerforgotpassword(String formData,HERO_STK_ILOGINDAO loginDAOobj,HttpServletRequest httpRequest) throws ClassNotFoundException
	{
		return loginDAOobj.registerforgotpassword(formData,httpRequest);
	}
	
	public HERO_STK_RESPONSEINFO fetchUserList(HERO_STK_ILOGINDAO loginDAOobj)
	{
		return loginDAOobj.fetchUserList(loginDAOobj);
	}
	
	
	public HERO_STK_RESPONSEINFO herosettingsurl(String modulename, HERO_STK_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
		return loginDAOobj.herosettingsurl(modulename);
	}
	
	public HERO_STK_RESPONSEINFO registerUser(String formData,HERO_STK_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
	return loginDAOobj.registerUser(formData);
	}
	
	public HERO_STK_RESPONSEINFO loadDashboardGraph(HERO_STK_ILOGINDAO loginDAOobj) throws ClassNotFoundException
	{
	return loginDAOobj.loadDashboardGraph();
	}
	
	
}
