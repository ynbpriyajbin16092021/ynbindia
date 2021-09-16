package com.hero.forms.services.admin.masters.bank;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_ADM_SERVC_IBANKDAO {
	public HERO_STK_RESPONSEINFO loadbanks();
	public HERO_STK_RESPONSEINFO savebank(String bankId,String bankname,String userid,String oprn);
}
