package com.hero.sso;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hero.heroutils.HERO_TOKEN_UTIL;
import com.hero.heroutils.HERO_UTILS;


/**
 * Servlet implementation class HERO_ADM_SERVC_TOKEN_VALIDATOR
 */
@WebServlet("/HERO_ADM_SERVC_TOKEN_VALIDATOR")
public class HERO_ADM_SERVC_TOKEN_VALIDATOR extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_TOKEN_VALIDATOR.class); 
	private static Logger error_log = Logger.getLogger("requestAppender");

    /**
     * @see HttpServlet#HttpServlet()
     */
    public HERO_ADM_SERVC_TOKEN_VALIDATOR() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try
		{
			
			 String previousURL = "";

		        if (request != null) {
		        	previousURL = request.getHeader("referer");
		        }
		        log.info("previousURL   "+previousURL);
		if(previousURL != null && previousURL.contains("hero"))
		{

			String tokenKey = request.getParameter("tokenkey");
			String applncontect_URL = HERO_UTILS.getApplicationSontext(request);
			HttpSession session = request.getSession();
			session.setAttribute("tokenkey", tokenKey);
			
			//String tokenkeyURL = "http://localhost:8080/herotokencentre/getherotokenkey/"+tokenKey;
			String tokenkeyURL = HERO_UTILS.getApplicationSontext(request)+"/herotokencentre/getherotokenkey/"+tokenKey;
			log.info("tokenkeyURL   "+tokenkeyURL);
			URL url = new URL(tokenkeyURL);
			String outputJSON = HERO_TOKEN_UTIL.HTTPgetMethod(url);
			log.info("outputJSON   "+outputJSON);
			//String ssologinURL = "http://localhost:8080/heroadmin/forms/validssologin";
			String ssologinURL = HERO_UTILS.getApplicationSontext(request)+"/heroadmin/forms/validssologin";
			URL SSOurl = new URL(ssologinURL);
			JSONObject ssoJSON = HERO_TOKEN_UTIL.HTTPpostMethod(outputJSON, SSOurl);
			log.info("ssoJSON in Validator   "+ssoJSON);
			JSONObject inventoryResponseJSON = (JSONObject) ssoJSON.get("inventoryResponse");
			JSONObject responseObjJSON = (JSONObject) inventoryResponseJSON.get("responseObj");
			log.info("responseObjJSON   "+responseObjJSON);
			JSONArray userDetailsArr = (JSONArray) responseObjJSON.get("userDetails");
			JSONArray usermenuListArr = (JSONArray) responseObjJSON.get("usermenuList");
			JSONArray orgnListArr = (JSONArray) responseObjJSON.get("orgnSettingList");
			JSONArray lowStockListArr = (JSONArray) responseObjJSON.get("lowStockList");
			log.info("userDetailsArr   "+userDetailsArr);
			if(userDetailsArr != null && userDetailsArr.size() > 0)
			{
				log.info("responseObjJSON   "+userDetailsArr.get(0));
				JSONObject responseObj = (JSONObject) userDetailsArr.get(0);
				String username = (String) responseObj.get("username");
				String user_name = (String) responseObj.get("username");
				String userid = (String) responseObj.get("userid");
				log.info("usermenuListArr from responseObj   "+responseObj);
				
				session.setAttribute("usertype",Integer.parseInt((String)responseObj.get("role")));
				session.setAttribute("usertypestr",responseObj.get("role"));
				session.setAttribute("usertypedesc", responseObj.get("usertypedesc"));
				session.setAttribute("username",username.toUpperCase());
				session.setAttribute("user_name",user_name.toUpperCase());
				session.setAttribute("uid",responseObj.get("userid"));
				session.setAttribute("currencyname",responseObj.get("currencyname"));
				session.setAttribute("currencydecimal",responseObj.get("currencydecimal"));
				session.setAttribute("modulepath",responseObj.get("modulepath"));
				session.setAttribute("storeid", responseObj.get("storeid"));
				//session.setAttribute("mainmenuList", usermenuListArr);
				log.info("usermenuListArr from responseObj   "+responseObj);
				
				if(orgnListArr != null && orgnListArr.size() > 0)
				{
					Map<String, Object> orgnMap = (Map<String, Object>) orgnListArr.get(0);
					String companyName = orgnMap.get("companyname") != null ? ((String)orgnMap.get("companyname")) : "";
					session.setAttribute("companyname", companyName);
				}
					
				//int lowstockcount = lowStockListArr.size();
				//session.setAttribute("lowstockcount", lowstockcount);
				
				/*session.setAttribute("usertype",rs.getInt("role"));
				session.setAttribute("usertypestr",rs.getString("role"));
				session.setAttribute("usertypedesc", rs.getString("usertype_name"));*/
				
				
				applncontect_URL = applncontect_URL+"/heroadmin/forms/herohome?uid="+userid;
				log.info("applncontect_URL   "+applncontect_URL);
				if(applncontect_URL != null && applncontect_URL.length() > 0)
				{
					response.sendRedirect(applncontect_URL);	
				}
			}
			else
			{
				navigateToLogin(request,response);
			}
		}
		else
		{
			   navigateToLogin(request,response);
		}
		
		}
		catch(Exception e)
		{
			//e.printStackTrace();
			error_log.info(e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
	
	protected void navigateToLogin(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		String applncontect_URL = HERO_UTILS.getApplicationSontext(request);
		applncontect_URL = applncontect_URL+"/hero";
		response.sendRedirect(applncontect_URL);
	}

}
