package com.hero.stock.forms.transactions.bills;

public class HERO_STK_BILLSREQUEST {

	private String billno;
	private String amount;
	private String paiddate;
	private String paymenttype;
	private String bankid;
	private String chequeno;
	private String userid;
	private String popaidstatus;
	public String getPopaidstatus() {
		return popaidstatus;
	}
	public void setPopaidstatus(String popaidstatus) {
		this.popaidstatus = popaidstatus;
	}
	public String getBillno() {
		return billno;
	}
	public void setBillno(String billno) {
		this.billno = billno;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getPaiddate() {
		return paiddate;
	}
	public void setPaiddate(String paiddate) {
		this.paiddate = paiddate;
	}
	public String getPaymenttype() {
		return paymenttype;
	}
	public void setPaymenttype(String paymenttype) {
		this.paymenttype = paymenttype;
	}
	public String getBankid() {
		return bankid;
	}
	public void setBankid(String bankid) {
		this.bankid = bankid;
	}
	public String getChequeno() {
		return chequeno;
	}
	public void setChequeno(String chequeno) {
		this.chequeno = chequeno;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	
	
}
