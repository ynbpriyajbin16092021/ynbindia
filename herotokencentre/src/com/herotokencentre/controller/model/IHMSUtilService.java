package com.herotokencentre.controller.model;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.herotokencentre.controller.response.HMSResponse;

public interface IHMSUtilService {
	public HMSResponse branchlov();
	public List<Object> getMenuList(String menuQuery,String userMenuQuery);
	
	public Map<String, Object> getCategoryItems(String categoryid,String clinicid);
	public Map<String, Object> getCategoryTypeItems(String categorytypeid,String clinicid);
	public Map<String, Object> getOrderheadItems(String orderheadid,String clinicid);
	
	public List<Object> getCategorytypesforCategory(String categoryid,String clinicid);
	public List<Object> getOrderHeadforCategoryTypes(String categorytypeid,String clinicid);
	public List<Object> getSuborderForOrderhead(String headid,String clinicid);
	public List<Object> getOrderTubesforCategory(String categoryid,String clinicid);
	public List<Object> getOrderUnitsforCategoryType(String categorytypeid,String clinicid);
}
