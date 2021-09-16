package com.hero.stock.forms.services.tools.barcode;

public class HERO_STK_SERVC_BARCODEREQUEST {
	
	private String storeid;
	private String product;
	private int style;
	private String printtype;
	private String batchid;
	private String rate;
	private int continuous;
	
	public String getStoreid() {
		return storeid;
	}
	public void setStoreid(String storeid) {
		this.storeid = storeid;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	public int getStyle() {
		return style;
	}
	public void setStyle(int style) {
		this.style = style;
	}
	public String getPrinttype() {
		return printtype;
	}
	public void setPrinttype(String printtype) {
		this.printtype = printtype;
	}
	public String getBatchid() {
		return batchid;
	}
	public void setBatchid(String batchid) {
		this.batchid = batchid;
	}
	public String getRate() {
		return rate;
	}
	public void setRate(String rate) {
		this.rate = rate;
	}
	public int getContinuous() {
		return continuous;
	}
	public void setContinuous(int continuous) {
		this.continuous = continuous;
	}
	
	
}
