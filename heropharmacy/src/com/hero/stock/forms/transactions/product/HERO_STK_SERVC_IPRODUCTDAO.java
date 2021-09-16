package com.hero.stock.forms.transactions.product;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;

public interface HERO_STK_SERVC_IPRODUCTDAO {

	public HERO_STK_RESPONSEINFO saveproduct(String productData);
	public HERO_STK_RESPONSEINFO productlist();
	public HERO_STK_RESPONSEINFO getproductinfo(String productid);
	public HERO_STK_RESPONSEINFO getproductviewinfo(String productid,String userid);
	
	public HERO_STK_RESPONSEINFO importProducttoStock(String importdata,HttpServletRequest httpRequest);

	public HERO_STK_RESPONSEINFO getproductsuggestions();
	
	public void downloadSampleFormat(String fileName,String columnsheet,HttpServletRequest httpRequest,HttpServletResponse response);
	
	public HERO_STK_RESPONSEINFO savedishtype(String productData);
	public HERO_STK_RESPONSEINFO dishtypelist();
	public HERO_STK_RESPONSEINFO savedish(String productData);
	public HERO_STK_RESPONSEINFO dishlist();
	public HERO_STK_RESPONSEINFO saveterms(String productData);
	public HERO_STK_RESPONSEINFO termslist();
	public HERO_STK_RESPONSEINFO savecompanymaster(String productData,HttpServletRequest request);
	public HERO_STK_RESPONSEINFO companymasterlist();
}
