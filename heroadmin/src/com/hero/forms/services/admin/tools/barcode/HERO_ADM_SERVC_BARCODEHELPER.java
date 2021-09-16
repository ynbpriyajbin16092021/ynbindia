package com.hero.forms.services.admin.tools.barcode;


import com.hero.services.admin.response.HERO_ADM_SERVC_INVENTORYRESPONSEINFO;

public class HERO_ADM_SERVC_BARCODEHELPER {

	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO getBarcodeProducts(HERO_ADM_SERVC_iBARCODEDAO barcodeDAOobj,String storeid,String productid,String batchid,String eventtype,String changecomp)
	{
		return barcodeDAOobj.getBarcodeProducts(storeid,productid,batchid,eventtype,changecomp);
		 
	}
	
	public HERO_ADM_SERVC_INVENTORYRESPONSEINFO printBarcode(HERO_ADM_SERVC_iBARCODEDAO barcodeDAOobj,HERO_ADM_SERVC_BARCODEREQUEST request)
	{
		return barcodeDAOobj.printBarcode(request);
		 
	}
}
