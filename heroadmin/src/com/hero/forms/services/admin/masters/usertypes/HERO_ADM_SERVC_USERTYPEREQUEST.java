package com.hero.forms.services.admin.masters.usertypes;

public class HERO_ADM_SERVC_USERTYPEREQUEST {

	private String usertypeid;
	private String usertypedesc;
	private String dept;
	private String usertypeimage;
	public String getUsertypeimage() {
		return usertypeimage;
	}
	public void setUsertypeimage(String usertypeimage) {
		this.usertypeimage = usertypeimage;
	}
	private String status;
	private String oprn;
	public String getUsertypeid() {
		return usertypeid;
	}
	public void setUsertypeid(String usertypeid) {
		this.usertypeid = usertypeid;
	}
	public String getUsertypedesc() {
		return usertypedesc;
	}
	public void setUsertypedesc(String usertypedesc) {
		this.usertypedesc = usertypedesc;
	}
	public String getDept() {
		return dept;
	}
	public void setDept(String dept) {
		this.dept = dept;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOprn() {
		return oprn;
	}
	public void setOprn(String oprn) {
		this.oprn = oprn;
	}
	
}
