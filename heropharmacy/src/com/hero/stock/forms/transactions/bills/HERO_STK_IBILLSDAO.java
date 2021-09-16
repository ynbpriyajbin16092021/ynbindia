package com.hero.stock.forms.transactions.bills;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_IBILLSDAO {
public HERO_STK_RESPONSEINFO billslist(String supplierid);
public HERO_STK_RESPONSEINFO pohistoryDetails(String pid);
public HERO_STK_RESPONSEINFO savebulkbill(String billsData);
}
