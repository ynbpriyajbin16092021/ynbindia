package com.hero.forms.services.stock.masters.uom;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_IUOMDAO {

public HERO_STK_RESPONSEINFO saveuom(String CategoryData,HERO_STK_SERVC_IUOMDAO iuomDAOobj);
public HERO_STK_RESPONSEINFO saveuomsetting(String uomsettingData,HERO_STK_SERVC_IUOMDAO iuomDAOobj,HttpServletRequest httpRequest);
public HERO_STK_RESPONSEINFO saveuomconfig(String uomsettingData,HERO_STK_SERVC_IUOMDAO iuomDAOobj,HttpServletRequest httpRequest);
public HERO_STK_RESPONSEINFO loaduoms(HERO_STK_SERVC_IUOMDAO iuomDAOobj);
public HERO_STK_RESPONSEINFO loaduomsetting(HERO_STK_SERVC_IUOMDAO iuomDAOobj);
public HERO_STK_RESPONSEINFO loaduomitemslist(HERO_STK_SERVC_IUOMDAO iuomDAOobj,String uomsettingsid);

}
