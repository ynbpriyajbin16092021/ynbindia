package com.hero.forms.services.stock.masters.category;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_iCATEGORYDAO {
public HERO_STK_RESPONSEINFO savecategory(String categoryId,String categoryname,String userid,String oprn);
public HERO_STK_RESPONSEINFO loadcategories();
public HERO_STK_RESPONSEINFO getCategorySuggestions();
}
