package com.hero.forms.services.approval.forgotpw;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_IFORGOTPWDAO {


public HERO_ADM_SERVC_INVENTORYRESPONSEINFO resetpassword(String forgotpwid,HERO_ADM_SERVC_IFORGOTPWDAO icurrencyDAOobj);
public HERO_ADM_SERVC_INVENTORYRESPONSEINFO forgotpasswordlist();



}
