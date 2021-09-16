package com.hero.stock.forms.transactions.product;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.forms.transactions.product.HERO_STK_SERVC_IPRODUCTDAO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;
import com.hero.stock.response.HERO_STK_RESPONSEINFO;


public class HERO_STK_SERVC_PRODUCTHELPER {
	
	public HERO_STK_RESPONSEINFO saveProduct(String productData,HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.saveproduct(productData);
		 
	}
	
	public HERO_STK_RESPONSEINFO productlist(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.productlist();
		 
	}

	public HERO_STK_RESPONSEINFO getproductsuggestions(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.getproductsuggestions();
		 
	}
	
	public HERO_STK_RESPONSEINFO getproductinfo(HERO_STK_SERVC_IPRODUCTDAO productDAOobj,String productid)
	{
		return productDAOobj.getproductinfo(productid);
		 
	}
	
	public HERO_STK_RESPONSEINFO getproductviewinfo(HERO_STK_SERVC_IPRODUCTDAO productDAOobj,String productid,String userid)
	{
		return productDAOobj.getproductviewinfo(productid,userid);
		 
	}
	
	
	public HERO_STK_RESPONSEINFO importProducttoStock(HERO_STK_SERVC_IPRODUCTDAO productDAOobj,String importdata,HttpServletRequest httpRequest)
	{
		return productDAOobj.importProducttoStock(importdata,httpRequest);
		 
	}
	
	
	public void downloadSampleFormat(String fileName,String columnsheet,HERO_STK_SERVC_IPRODUCTDAO productDAOobj,HttpServletRequest httpRequest,HttpServletResponse httpResponse)
	{
		productDAOobj.downloadSampleFormat(fileName,columnsheet,httpRequest,httpResponse);
	}
	
	
	public HERO_STK_RESPONSEINFO savedishtype(String productData,HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.savedishtype(productData);
		 
	}
	
	public HERO_STK_RESPONSEINFO dishtypelist(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.dishtypelist();
		 
	}
	
	public HERO_STK_RESPONSEINFO savedish(String productData,HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.savedish(productData);
		 
	}
	
	public HERO_STK_RESPONSEINFO dishlist(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.dishlist();
		 
	}
	
	public HERO_STK_RESPONSEINFO saveterms(String productData,HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.saveterms(productData);
		 
	}
	
	public HERO_STK_RESPONSEINFO termslist(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.termslist();
		 
	}
	
	
	public HERO_STK_RESPONSEINFO savecompanymaster(String productData,HERO_STK_SERVC_IPRODUCTDAO productDAOobj,HttpServletRequest request)
	{
		return productDAOobj.savecompanymaster(productData,request);
		 
	}
	
	public HERO_STK_RESPONSEINFO companymasterlist(HERO_STK_SERVC_IPRODUCTDAO productDAOobj)
	{
		return productDAOobj.companymasterlist();
		 
	}

	
}
