package com.hero.stock.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_ILOGINDAO {
	public HERO_STK_RESPONSEINFO validLogin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO guestLogin(HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO validLogin1(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO validssologin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO changepassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO registerforgotpassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO fetchUserList(HERO_STK_ILOGINDAO loginDAOobj);
	public HERO_STK_RESPONSEINFO registerUser(String formData)throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO loadDashboardGraph()throws ClassNotFoundException;
	public HERO_STK_RESPONSEINFO herosettingsurl(String modulename)throws ClassNotFoundException;

}
