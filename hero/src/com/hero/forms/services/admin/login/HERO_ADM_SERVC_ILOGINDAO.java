package com.hero.forms.services.admin.login;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_ILOGINDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO validLogin(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO registerforgotpassword(String formData,HttpServletRequest httpRequest) throws ClassNotFoundException;

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO tokenSignout(String tokenkey)throws ClassNotFoundException;
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO heroViewImage(String formData,HttpServletRequest httpRequest)throws ClassNotFoundException;
}
