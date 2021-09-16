package com.hero.reports.forms.login;

public class HERO_RTS_LOGINREQUEST {

	private String Userid;
	private String Username;
	private String Password;
	private String confirmPassword;
	private String oprn;
	
	public String getUserid() {
		return Userid;
	}
	public void setUserid(String userid) {
		Userid = userid;
	}
	public String getUsername() {
		return Username;
	}
	public void setUsername(String username) {
		Username = username;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	public String getOprn() {
		return oprn;
	}
	public void setOprn(String oprn) {
		this.oprn = oprn;
	}
	
	
}
