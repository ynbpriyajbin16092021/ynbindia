package com.hero.stock.forms.services.tools.lowstock;

import java.util.List;


public class HERO_STK_SERVC_LOWSTOCKHELPER {
	public List<Object> getLowStockProductListDetails(HERO_STK_SERVC_ILOWSTOCKDAO lowstockDAOobj,String storeid)
	{
		return lowstockDAOobj.getLowStockProductListDetails(storeid);
		 
	}
	
}
