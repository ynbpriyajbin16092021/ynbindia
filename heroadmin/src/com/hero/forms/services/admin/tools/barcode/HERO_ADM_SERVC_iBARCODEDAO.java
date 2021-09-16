package com.hero.forms.services.admin.tools.barcode;

import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public interface HERO_ADM_SERVC_iBARCODEDAO {
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getBarcodeProducts(String storeid,String productid,String batchid,String eventtype,String changecomp);
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO printBarcode(HERO_ADM_SERVC_BARCODEREQUEST request);
}
