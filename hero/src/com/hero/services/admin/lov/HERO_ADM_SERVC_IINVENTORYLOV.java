package com.hero.services.admin.lov;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface HERO_ADM_SERVC_IINVENTORYLOV {

	public List<Object> setEmptyList();
	public List<Object> getLOVList(String query);
	public List<Object> getuserTypeList(String query);
	public List<Object> getViewPurchaseList(String query);
	public List<Object> getViewReceiveStockList(String purchaseorderid);
	public List<Object> getStockList(String stockQuery);
	public List<Object> getTransferList(String stockTxrQuery);
	public List<Object> getStockTransferOrderList(String stocktransferviewQuery);
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery);
	public List<Object> getUserdetails(String userDetailQuery);
	public List<Object> getMenuList(String menuQuery, int isadminmenu);
	public List<Object> getBarcodeStoreList(String storeQuery);
	public List<Object> getPOSDiscountList(String taxQuery);
	public List<Object> getSMSTemplateList(String smsTemplateQuery);
	public List<Object> getLowStockList(HttpServletRequest httpRequest);
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest);
	public List<Object> getTaxList(String query);
	public List<Object> getSupplierContactList(String query);
	/*public List<Object> getAppconfigList(String query);*/
	public List<Object> getAppconfigList(String query,String applncontect_URL);
	public List<Object> getforgotPWList(String query);
	public List<Object> getUserCountList(String userCountQuery);
	public List<Object> getUserTypeList(String userTypeQuery);
	public List<Object> getemailTemplateList(String emailtemplateQuery);
	public List<Object> getApplnList(String usertypeApplnQuery);
}
