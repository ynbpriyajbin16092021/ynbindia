package com.hero.forms.services.admin.masters.currency;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_ICURRENCYDAO {


public HERO_ADM_SERVC_INVENTORYRESPONSEINFO savecurrency(String currencyData,HERO_ADM_SERVC_ICURRENCYDAO icurrencyDAOobj);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrency();
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO loadcurrencysymbolsuggestions();




}
