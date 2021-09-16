package com.hero.stock.forms.transactions.purchaseorder;

public class HERO_STK_ADDPURCHASEORDERREQUEST {
	
	private String purchaseid;
	private String purchasecode;
	private String purchaserefno;
	private String purchasedate;
	private String receiveddate;
	private String totalamt;    
	private String paidamt;
	private String supplierid;
	private String purchasenotes;
	private String purchasetnc;
	private String receivestatus;
	private String purchasestatus;
	private String userid;
	private String returncharge;
	private String notes;
	private String oprn;
	private String itemlist;
	private String storeid;
	private String remarks;
	private String dishlist;
	private String purchasereqeustid;
	
	public String getPurchasereqeustid() {
		return purchasereqeustid;
	}
	public void setPurchasereqeustid(String purchasereqeustid) {
		this.purchasereqeustid = purchasereqeustid;
	}
	public String getDishlist() {
		return dishlist;
	}
	public void setDishlist(String dishlist) {
		this.dishlist = dishlist;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getPurchaseid() {
		return purchaseid;
	}
	public void setPurchaseid(String purchaseid) {
		this.purchaseid = purchaseid;
	}
	public String getPurchasecode() {
		return purchasecode;
	}
	public void setPurchasecode(String purchasecode) {
		this.purchasecode = purchasecode;
	}
	public String getPurchaserefno() {
		return purchaserefno;
	}
	public void setPurchaserefno(String purchaserefno) {
		this.purchaserefno = purchaserefno;
	}
	public String getPurchasedate() {
		return purchasedate;
	}
	public void setPurchasedate(String purchasedate) {
		this.purchasedate = purchasedate;
	}
	public String getReceiveddate() {
		return receiveddate;
	}
	public void setReceiveddate(String receiveddate) {
		this.receiveddate = receiveddate;
	}
	public String getTotalamt() {
		return totalamt;
	}
	public void setTotalamt(String totalamt) {
		this.totalamt = totalamt;
	}
	public String getPaidamt() {
		return paidamt;
	}
	public void setPaidamt(String paidamt) {
		this.paidamt = paidamt;
	}
	public String getSupplierid() {
		return supplierid;
	}
	public void setSupplierid(String supplierid) {
		this.supplierid = supplierid;
	}
	public String getPurchasenotes() {
		return purchasenotes;
	}
	public void setPurchasenotes(String purchasenotes) {
		this.purchasenotes = purchasenotes;
	}
	public String getPurchasetnc() {
		return purchasetnc;
	}
	public void setPurchasetnc(String purchasetnc) {
		this.purchasetnc = purchasetnc;
	}
	public String getReceivestatus() {
		return receivestatus;
	}
	public void setReceivestatus(String receivestatus) {
		this.receivestatus = receivestatus;
	}
	public String getPurchasestatus() {
		return purchasestatus;
	}
	public void setPurchasestatus(String purchasestatus) {
		this.purchasestatus = purchasestatus;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getReturncharge() {
		return returncharge;
	}
	public void setReturncharge(String returncharge) {
		this.returncharge = returncharge;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}
	public String getOprn() {
		return oprn;
	}
	public void setOprn(String oprn) {
		this.oprn = oprn;
	}
	public String getItemlist() {
		return itemlist;
	}
	public void setItemlist(String itemlist) {
		this.itemlist = itemlist;
	}
	
	private String suppliername;
	private String purchasestatusdesc;
	private String recvstatusdesc;
	private String cgsttax;
	private String sgsttax;
	public String getCgsttax() {
		return cgsttax;
	}
	public void setCgsttax(String cgsttax) {
		this.cgsttax = cgsttax;
	}
	public String getSgsttax() {
		return sgsttax;
	}
	public void setSgsttax(String sgsttax) {
		this.sgsttax = sgsttax;
	}
	public String getSuppliername() {
		return suppliername;
	}
	public void setSuppliername(String suppliername) {
		this.suppliername = suppliername;
	}
	public String getPurchasestatusdesc() {
		return purchasestatusdesc;
	}
	public void setPurchasestatusdesc(String purchasestatusdesc) {
		this.purchasestatusdesc = purchasestatusdesc;
	}
	public String getRecvstatusdesc() {
		return recvstatusdesc;
	}
	public void setRecvstatusdesc(String recvstatusdesc) {
		this.recvstatusdesc = recvstatusdesc;
	}
	
	private String fulluomqty;
	private String looseuomqty;
	private String fulluom;
	private String looseuom;
	private String fulluomsno;
	private String looseuomsno;
	private String uompacking;
	public String getFulluomqty() {
		return fulluomqty;
	}
	public void setFulluomqty(String fulluomqty) {
		this.fulluomqty = fulluomqty;
	}
	public String getLooseuomqty() {
		return looseuomqty;
	}
	public void setLooseuomqty(String looseuomqty) {
		this.looseuomqty = looseuomqty;
	}
	public String getFulluom() {
		return fulluom;
	}
	public void setFulluom(String fulluom) {
		this.fulluom = fulluom;
	}
	public String getLooseuom() {
		return looseuom;
	}
	public void setLooseuom(String looseuom) {
		this.looseuom = looseuom;
	}
	public String getFulluomsno() {
		return fulluomsno;
	}
	public void setFulluomsno(String fulluomsno) {
		this.fulluomsno = fulluomsno;
	}
	public String getLooseuomsno() {
		return looseuomsno;
	}
	public void setLooseuomsno(String looseuomsno) {
		this.looseuomsno = looseuomsno;
	}
	public String getUompacking() {
		return uompacking;
	}
	public void setUompacking(String uompacking) {
		this.uompacking = uompacking;
	}
	
	
}
