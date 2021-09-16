package com.hero.forms.services.admin.masters.bank;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_ADM_SERVC_BANKHELPER {
	public HERO_STK_RESPONSEINFO loadbanks(HERO_ADM_SERVC_IBANKDAO bankDAOobj)
	{
		return bankDAOobj.loadbanks();
		 
	}
	public HERO_STK_RESPONSEINFO savebank(String bankId,String bankName,String userid,String oprn,HERO_ADM_SERVC_IBANKDAO bankDAOobj)
	{
		return bankDAOobj.savebank(bankId,bankName,userid, oprn);		
	 
	}
}
