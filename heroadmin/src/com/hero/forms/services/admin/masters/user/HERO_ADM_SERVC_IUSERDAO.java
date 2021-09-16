package com.hero.forms.services.admin.masters.user;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_IUSERDAO {
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuserrole(String userroledata,String usertype);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savereportuserrole(String userroledata,String usertype);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuser(String userdata);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveguestuser(String userdata);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO userlist();
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserDetail(String userid,HttpServletRequest httpRequest);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserLocationList(String usertypeid);
}
