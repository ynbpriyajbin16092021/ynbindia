package com.hero.stock.forms.services.tools.barcode;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public class HERO_STK_SERVC_BARCODEHELPER {

	public HERO_STK_RESPONSEINFO getBarcodeProducts(HERO_STK_SERVC_iBARCODEDAO barcodeDAOobj,String storeid,String productid,String batchid,String eventtype,String changecomp)
	{
		return barcodeDAOobj.getBarcodeProducts(storeid,productid,batchid,eventtype,changecomp);
		 
	}
	
	public HERO_STK_RESPONSEINFO printBarcode(HERO_STK_SERVC_iBARCODEDAO barcodeDAOobj,HERO_STK_SERVC_BARCODEREQUEST request)
	{
		return barcodeDAOobj.printBarcode(request);
		 
	}
}
