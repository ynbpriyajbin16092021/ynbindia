package com.hero.reports.lov;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hero.reports.response.HERO_RTS_RESPONSEINFO;

public interface HERO_RTS_IINVENTORYLOV {

	public List<Object> setEmptyList();
	public List<Object> getLOVList(String query);
	public List<Object> getViewPurchaseList(String query);
	public List<Object> getViewReceiveStockList(String purchaseorderid);
	public List<Object> getStockList(String stockQuery);
	public List<Object> getTransferList(String stockTxrQuery);
	public List<Object> getStockTransferOrderList(String stocktransferviewQuery);
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery);
	public List<Object> getUserdetails(String userDetailQuery);
	public List<Object> getMenuList(String menuQuery);
	public List<Object> getBarcodeStoreList(String storeQuery);
	public List<Object> getPOSDiscountList(String taxQuery);
	public List<Object> getSMSTemplateList(String smsTemplateQuery);
	public List<Object> getLowStockList(HttpServletRequest httpRequest);
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest);
	public List<Object> getTaxList(String query);
	public List<Object> getSupplierContactList(String query);
	public HERO_RTS_RESPONSEINFO getCountry();
	public HERO_RTS_RESPONSEINFO getStates(int countryId);
	public HERO_RTS_RESPONSEINFO getCities(int stateId);
	public List<Object> getReportMenuList(String menuQuery);
}
