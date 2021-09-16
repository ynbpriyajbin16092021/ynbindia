package com.hero.services.admin.fileupload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class viewimages
 */
@WebServlet("/forms/exportsampleformats")
public class HERO_ADM_SERVC_EXPORTSAMPLEFORMATS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String filePath;
	private static Logger log = Logger.getLogger(HERO_ADM_SERVC_EXPORTSAMPLEFORMATS.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HERO_ADM_SERVC_EXPORTSAMPLEFORMATS() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		log.info("Welcome to ViewImage");
		String fileName = request.getParameter("fileName");
		this.filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"sampleformats"+File.separator+"productimportsample.xlsx";
		
		log.info("filePath       "+filePath);
		 
        File file = new File(filePath);
 
        if (!file.exists()) {

            /*response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;*/
        	
        	file = new File(filePath);
        	
        }// Set Content type
        log.info("In do get   "+filePath);
        
        try
        {
        	response.setContentType("APPLICATION/OCTET-STREAM");
            PrintWriter out = response.getWriter();
            
            response.setHeader("Content-Disposition", "attachment; filename=\"" + "Import Product Sample Format.xlsx" + "\"");
            FileInputStream fl = new FileInputStream(filePath);
            int i;
            while ((i = fl.read()) != -1) {
                out.write(i);
            }
            fl.close();
            out.close();
        }
        catch(Exception e)
        {
           log.error(e);
	   return;
        }
        finally
        {
            try
            {
                //in.close();
            }
            catch(Exception ee)
            {
            	log.error(ee);
            }
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
