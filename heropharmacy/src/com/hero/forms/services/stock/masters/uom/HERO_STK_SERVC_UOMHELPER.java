package com.hero.forms.services.stock.masters.uom;


import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_SERVC_UOMHELPER {


	
	public HERO_STK_RESPONSEINFO saveuom(String uomData,HERO_STK_SERVC_IUOMDAO iuomDAOobj)
	{
		return iuomDAOobj.saveuom(uomData,iuomDAOobj);		
	 
	}
	
	public HERO_STK_RESPONSEINFO saveuomconfig(String uomsettingData,HERO_STK_SERVC_IUOMDAO iuomDAOobj,HttpServletRequest httpRequest)
	{
		return iuomDAOobj.saveuomconfig(uomsettingData,iuomDAOobj,httpRequest);		
	 
	}
	
	public HERO_STK_RESPONSEINFO saveuomsetting(String uomsettingData,HERO_STK_SERVC_IUOMDAO iuomDAOobj,HttpServletRequest httpRequest)
	{
		return iuomDAOobj.saveuomsetting(uomsettingData,iuomDAOobj,httpRequest);		
	 
	}
	
	public HERO_STK_RESPONSEINFO loaduoms(HERO_STK_SERVC_IUOMDAO iuomDAOobj)
	{
		return iuomDAOobj.loaduoms(iuomDAOobj);
		 
	}
	
	public HERO_STK_RESPONSEINFO loaduomsetting(HERO_STK_SERVC_IUOMDAO iuomDAOobj)
	{
		return iuomDAOobj.loaduomsetting(iuomDAOobj);
		 
	}
	public HERO_STK_RESPONSEINFO loaduomitemslist(HERO_STK_SERVC_IUOMDAO iuomDAOobj,String uomsettingsid)
	{
		return iuomDAOobj.loaduomitemslist(iuomDAOobj,uomsettingsid);
		 
	}
}
