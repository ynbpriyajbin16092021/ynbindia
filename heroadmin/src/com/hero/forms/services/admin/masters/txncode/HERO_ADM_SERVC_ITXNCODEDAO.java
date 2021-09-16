package com.hero.forms.services.admin.masters.txncode;

import javax.servlet.http.HttpServletRequest;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_ITXNCODEDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodelist(String storeid,HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savetxncode(String txncodeData,HttpServletRequest httpRequest);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodedetail(String txncodeid,HttpServletRequest httpRequest);
}
