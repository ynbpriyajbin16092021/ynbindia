package com.hero.forms.services.stock.tools.lowstock;

import java.util.List;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_STK_SERVC_ILOWSTOCKDAO {
	public List<Object> getLowStockProductListDetails(String storeid);
}
