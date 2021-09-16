package com.hero.stock.lov;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.hero.stock.forms.transactions.purchaseorder.HERO_STK_IADDPURCHASEORDERDAO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_IINVENTORYLOV {

	public List<Object> setEmptyList();
	public List<Object> getLOVList(String query);
	public List<Object> getLOVListwithoutemptylist(String query);
	public List<Object> refnoList(String referencenoquery);
	public List<Object> getinvList(String invListQuery);
	public List<Object> getLOVList1(String query);
	public List<Object> getpositemList(String positemquery);
	public List<Object> getposcalcList(String poscalcquery);
	public List<Object> getposorgList(String organisationquery);
	
	
	public List<Object> getcustomerList(String customerOrderQuery);
	public List<Object> getstList(String query,HttpServletRequest httpRequest);
	public List<Object> getPurchaseOrderHistroyList(String query);
	public List<Object> getPurchaseOrderHistroyList1(String query);
	public List<Object> getGRNList(String query);
	public List<Object> getdishHistroyList(String query);
	public List<Object> getsupplierList(String supplierQuery);
	public List<Object> getorganizationlist(String organizationquery);
	public List<Object> getpurchaseorderlist(String purchaseorderquery);
	public List<Object> getpurchaseorderitemlist(String purchaseitemquery);
	
	public List<Object> getstocktransferlist(String stocktransferquery);
	public List<Object> getstocktransferitemlist(String stocktransferitemquery);
	public List<Object> getstocktransferfromlist(String stocktransferfromquery);
	
	public List<Object> getViewPurchaseList(String query);
	public List<Object> getViewPurchaseRequestList(String query);
	public List<Object> getViewReceiveStockList(String purchaseorderid,HERO_STK_IADDPURCHASEORDERDAO addpurchaseorderDAOobj);
	public List<Object> getStockList(String stockQuery);
	public List<Object> getTransferList(String stockTxrQuery);
	public List<Object> getStockTransferOrderList(String stocktransferviewQuery);
	public List<Object> getUsermenuList(String menuQuery,String userMenuQuery);
	public List<Object> getUserdetails(String userDetailQuery);
	/*public List<Object> getMenuList(String menuQuery);*/
	public Map<String, Object> getMenuList(String menuQuery);
	public List<Object> getBarcodeStoreList(String storeQuery);
	public List<Object> getPOSDiscountList(String taxQuery);
	public List<Object> getSMSTemplateList(String smsTemplateQuery);
	public List<Object> getLowStockList(HttpServletRequest httpRequest);
	public List<Object> getPurchaseOrderdelHistory(HttpServletRequest httpRequest);
	public List<Object> getpurchaseorderdelhistoryview(HttpServletRequest httpRequest, String poid);
	public List<Object> updatereadnotificationstatus(HttpServletRequest httpRequest, String poid);
	public Map<String, Object> getDashboardDetails(HttpServletRequest httpRequest);
	public List<Object> getTaxList(String query);
	public List<Object> getSupplierContactList(String query);
	public HERO_STK_RESPONSEINFO getCountry();
	public HERO_STK_RESPONSEINFO getStates(int countryId);
	public HERO_STK_RESPONSEINFO getCities(int stateId);
	
	public List<Object> executeQuery(String query);
}
