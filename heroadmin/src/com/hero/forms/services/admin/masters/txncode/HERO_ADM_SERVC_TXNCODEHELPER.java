package com.hero.forms.services.admin.masters.txncode;

import javax.servlet.http.HttpServletRequest;
import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_TXNCODEHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodelist(HERO_ADM_SERVC_ITXNCODEDAO txncodeDAOobj,String storeid,HttpServletRequest httpRequest)
	{
		return txncodeDAOobj.transactioncodelist(storeid,httpRequest);
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savetxncode(HERO_ADM_SERVC_ITXNCODEDAO txncodeDAOobj,String txncodeData,HttpServletRequest httpRequest)
	{
		return txncodeDAOobj.savetxncode(txncodeData,httpRequest);
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO transactioncodedetail(HERO_ADM_SERVC_ITXNCODEDAO txncodeDAOobj,String txncodeid,HttpServletRequest httpRequest)
	{
		return txncodeDAOobj.transactioncodedetail(txncodeid,httpRequest);
		 
	}
}
