package com.herotokencentre.controller.service.admin;

import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import com.herotokencentre.controller.response.HMSResponse;


public interface IAdminService {
	public HMSResponse genherotokenkey(JSONObject request);
	public HMSResponse getherotokenkey(String tokenkey);
	public void removetoken(String tokenkey);
}
