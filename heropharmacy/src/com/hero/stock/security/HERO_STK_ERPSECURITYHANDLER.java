package com.hero.stock.security;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hero.stock.controller.inventory.HERO_STK_LOGINCTRL;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

public class HERO_STK_ERPSECURITYHANDLER implements HandlerInterceptor {

	private static Logger log = Logger.getLogger(HERO_STK_LOGINCTRL.class);
	
	@Override
	public void afterCompletion(HttpServletRequest req,
			HttpServletResponse res, Object obj, Exception exc)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj, ModelAndView mv) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@SuppressWarnings("unused")
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj) throws Exception {
		// TODO Auto-generated method stub
		String url = req.getRequestURI();
		boolean requestProcess = false;
		String tokenkey = req.getHeader("tokenkey");
		log.info("tokenkey   "+tokenkey);
		
		
		if(tokenkey == null){
		HttpSession session = req.getSession();
		
		if(!url.endsWith("login") && !url.endsWith("erpschedule") && !url.endsWith("HERO_TOKEN_VALIDATOR") && !url.endsWith("registerforgotpassword"))
		{
			log.info("session.getAttribute(uid) in Filter   "+session.getAttribute("uid")+"   "+session.getAttribute("tokenkey"));
			if(session.getAttribute("uid") == null)	
			{
				log.info("Session is Empty.");
				res.sendRedirect("login");
				requestProcess = false;
			}
			else
			{
			/*	List<String> accessURL = (List<String>) session.getAttribute("accessURLS");
				log.info("accessURLS   "+accessURL.size()+"   "+url);*/
				requestProcess = true;
			}
		}
		else
		{
			requestProcess = true;
		}
		}
	else{
		log.info(HEROHOSURINVENTORYUTIL.tokenList);
		List<Object> tokenlist = HEROHOSURINVENTORYUTIL.tokenList;
		
		boolean isvalidtoken = false;
		
		Iterator<Object> iterator = tokenlist.iterator();
		
		//Iterator<Map<String, Object>> iterator = tokenlist.iterator();
		while(iterator.hasNext())
		{
			Map<String, Object> srs = (Map<String, Object>) iterator.next();
			if(srs.get("tokenkey").equals(tokenkey)){
				isvalidtoken = true;
				break;
			}else{
				isvalidtoken = false;
			}
		}
		log.info("is valid token  "+isvalidtoken);
		
		requestProcess = isvalidtoken;
		if(requestProcess == false){
			res.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			res.getWriter().println("NOT VALID");
		}
		}
		return requestProcess;
		/*return true;*/
		
	}

}
