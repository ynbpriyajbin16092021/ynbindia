package com.herotokencentre.controller.response;

import org.springframework.stereotype.Service;

@Service("hmsresponse")
public class HMSResponse {
	
	private Object response;

	public Object getResponse() {
		return response;
	}

	public void setResponse(Object response) {
		this.response = response;
	}
	

}
