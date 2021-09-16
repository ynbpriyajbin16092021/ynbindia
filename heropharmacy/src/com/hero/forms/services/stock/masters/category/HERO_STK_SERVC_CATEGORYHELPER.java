package com.hero.forms.services.stock.masters.category;

import org.springframework.beans.factory.annotation.Autowired;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_SERVC_CATEGORYHELPER {

	
	public HERO_STK_RESPONSEINFO savecategory(String categoryId,String categoryname,String userid,String oprn,HERO_STK_SERVC_iCATEGORYDAO categoryDAOobj)
	{
		return categoryDAOobj.savecategory(categoryId,categoryname,userid, oprn);		
	 
	}
	
	public HERO_STK_RESPONSEINFO loadcategories(HERO_STK_SERVC_iCATEGORYDAO categoryDAOobj)
	{
		return categoryDAOobj.loadcategories();
		 
	}
	
	public HERO_STK_RESPONSEINFO getCategorySuggestions(HERO_STK_SERVC_iCATEGORYDAO categoryDAOobj)
	{
		return categoryDAOobj.getCategorySuggestions();
	}
}
