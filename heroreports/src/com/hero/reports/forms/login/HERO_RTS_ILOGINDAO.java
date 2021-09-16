package com.hero.reports.forms.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public interface HERO_RTS_ILOGINDAO {
	public HERO_RTS_RESPONSEINFO validLogin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_RTS_RESPONSEINFO validssologin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_RTS_RESPONSEINFO changepassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_RTS_RESPONSEINFO fetchUserList(HERO_RTS_ILOGINDAO loginDAOobj);
	public HERO_RTS_RESPONSEINFO registerUser(String formData)throws ClassNotFoundException;

}
