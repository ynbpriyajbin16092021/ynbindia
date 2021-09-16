package com.hero.reports.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hero.reports.controller.stock.HERO_RTS_LOGINCTRL;

public class HERO_RTS_ERPSECURITYHANDLER implements HandlerInterceptor {

	private static Logger log = Logger.getLogger(HERO_RTS_LOGINCTRL.class);
	
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

	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res,
			Object obj) throws Exception {
		// TODO Auto-generated method stub
		
		String url = req.getRequestURI();
		
		HttpSession session = req.getSession();
		boolean requestProcess = false;
		if(!url.endsWith("login") && !url.endsWith("inventoryreports") && !url.endsWith("erpschedule") && !url.endsWith("HERO_RPRT_SERVC_TOKEN_VALIDATOR"))
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
				requestProcess = true;
			}
		}
		else
		{
			requestProcess = true;
		}
		return requestProcess;
		/*return true;*/
	}

}
