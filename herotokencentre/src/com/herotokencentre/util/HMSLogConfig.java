package com.herotokencentre.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class HMSLogConfig
 */
public class HMSLogConfig extends HttpServlet {
	private static final long serialVersionUID = 1L;
private static final Logger logger = Logger
.getLogger(HMSLogConfig.class);

/**
* @see HttpServlet#HttpServlet()
*/
public HMSLogConfig() {
super();
// TODO Auto-generated constructor stub
}

/**
* @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
*      response)
*/
protected void doGet(HttpServletRequest request,
HttpServletResponse response) throws ServletException,
IOException {

logger.info("Log4j info is working");
logger.warn("Log4j warn is working");
logger.debug("Log4j debug is working");
logger.error("Log4j error is working");
//System.out.println("System out is working");
}

/**
* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
*      response)
*/
protected void doPost(HttpServletRequest request,
HttpServletResponse response) throws ServletException,
IOException {
// TODO Auto-generated method stub
}}
