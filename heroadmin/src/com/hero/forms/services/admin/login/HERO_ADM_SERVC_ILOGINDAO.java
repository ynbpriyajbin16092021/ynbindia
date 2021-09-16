package com.hero.forms.services.admin.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_ILOGINDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validLogin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validssologin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validLogin1(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO changepassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO fetchUserList(HERO_ADM_SERVC_ILOGINDAO loginDAOobj);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerforgotpassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerUser(String formData)throws ClassNotFoundException;
	
    public HERO_ADM_SERVC_INVENTORYRESPONSEINFO herosettingsurl(String modulename)throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO tokenSignout(String tokenkey)throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadDashboardGraph()throws ClassNotFoundException;
}
