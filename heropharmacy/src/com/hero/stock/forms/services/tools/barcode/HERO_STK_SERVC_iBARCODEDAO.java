package com.hero.stock.forms.services.tools.barcode;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_iBARCODEDAO {
	public HERO_STK_RESPONSEINFO getBarcodeProducts(String storeid,String productid,String batchid,String eventtype,String changecomp);
	public HERO_STK_RESPONSEINFO printBarcode(HERO_STK_SERVC_BARCODEREQUEST request);
}
