package com.hero.forms.services.admin.masters.user;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_USERHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuserrole(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String userroledata,String usertype)
	{
		return iuserroleDAOobj.saveuserrole(userroledata,usertype);
	}
	
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savereportuserrole(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String userroledata,String usertype)
	{
		return iuserroleDAOobj.savereportuserrole(userroledata,usertype);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO userlist(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj)
	{
		return iuserroleDAOobj.userlist();
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserDetail(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String userid,HttpServletRequest httpRequest)
	{
		return iuserroleDAOobj.getUserDetail(userid,httpRequest);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getUserLocationList(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String usertypeid)
	{
		return iuserroleDAOobj.getUserLocationList(usertypeid);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveuser(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String userdata)
	{
		return iuserroleDAOobj.saveuser(userdata);
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO saveguestuser(HERO_ADM_SERVC_IUSERDAO iuserroleDAOobj,String userdata)
	{
		return iuserroleDAOobj.saveguestuser(userdata);
	}
}
