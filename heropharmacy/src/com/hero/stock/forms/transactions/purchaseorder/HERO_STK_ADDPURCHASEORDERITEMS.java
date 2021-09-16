package com.hero.stock.forms.transactions.purchaseorder;

public class HERO_STK_ADDPURCHASEORDERITEMS {
private String purchaseorderid;	
private String productid;
private String quantity;
private String index;
private String oprn;
private String porid;
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
public String getPorid() {
	return porid;
}
public void setPorid(String porid) {
	this.porid = porid;
}
public String getProductid() {
	return productid;
}
public void setProductid(String productid) {
	this.productid = productid;
}
public String getQuantity() {
	return quantity;
}
public void setQuantity(String quantity) {
	this.quantity = quantity;
}
public String getIndex() {
	return index;
}
public void setIndex(String index) {
	this.index = index;
}
public String getPurchaseorderid() {
	return purchaseorderid;
}
public void setPurchaseorderid(String purchaseorderid) {
	this.purchaseorderid = purchaseorderid;
}
public String getOprn() {
	return oprn;
}
public void setOprn(String oprn) {
	this.oprn = oprn;
}

private String fulluom;
private String fulluomqty;
private String looseuom;
private String looseuomqty;
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
public String getUompacking() {
	return uompacking;
}
public void setUompacking(String uompacking) {
	this.uompacking = uompacking;
}

}
