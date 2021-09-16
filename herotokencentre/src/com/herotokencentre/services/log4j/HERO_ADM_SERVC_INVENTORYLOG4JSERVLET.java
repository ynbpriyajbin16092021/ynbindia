package com.herotokencentre.services.log4j;

import java.io.File;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class inventoryLog4jServlet
 */

@WebServlet(name="inventoryLog4jServlet",urlPatterns={"/inventoryLog4jServlet"},
initParams={@WebInitParam(name="log4j-properties-location",value="WEB-INF/log4j.properties")},
loadOnStartup=0)
public class HERO_ADM_SERVC_INVENTORYLOG4JSERVLET extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	private final static Logger log= Logger
			.getLogger(HERO_ADM_SERVC_INVENTORYLOG4JSERVLET.class);
	
	public void init(ServletConfig config) throws ServletException {
		log.info("Log4JInitServlet is initializing log4j");
		String log4jLocation = config.getInitParameter("log4j-properties-location");

		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			File log4jPropFilepath = new File(log4jProp);
			if (log4jPropFilepath.exists()) {
				log.info("Initializing log4j with: " + log4jProp);
				PropertyConfigurator.configure(log4jProp);
				
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
		super.init(config);
	}
	

}
