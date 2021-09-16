package com.hero.stock.forms.transactions.bills;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_BILLSHELPER {

	public HERO_STK_RESPONSEINFO billslist(HERO_STK_IBILLSDAO billsDAOobj,String supplierid)
	{
		return billsDAOobj.billslist(supplierid);
		 
	}
	
	public HERO_STK_RESPONSEINFO pohistoryDetails(HERO_STK_IBILLSDAO billsDAOobj,String pid)
	{
		return billsDAOobj.pohistoryDetails(pid);
		 
	}
	
	public HERO_STK_RESPONSEINFO savebulkbill(HERO_STK_IBILLSDAO billsDAOobj,String billData)
	{
		return billsDAOobj.savebulkbill(billData);
		 
	}
}
