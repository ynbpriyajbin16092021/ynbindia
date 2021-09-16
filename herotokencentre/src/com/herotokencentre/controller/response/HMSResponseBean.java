package com.herotokencentre.controller.response;



import org.springframework.stereotype.Service;

@Service("hmsresponsebean")
public class HMSResponseBean {
	private String responseType;
	private Object responseObj;
	
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public Object getResponseObj() {
		return responseObj;
	}
	public void setResponseObj(Object responseObj) {
		this.responseObj = responseObj;
	}

}
