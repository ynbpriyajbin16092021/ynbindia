package com.hero.stock.fileupload;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class viewimages
 */
@WebServlet("/forms/viewimages")
public class HERO_STK_VIEWIMAGE extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final int DEFAULT_BUFFER_SIZE = 10240; // 10KB.
	private String filePath;
	private static Logger log = Logger.getLogger(HERO_STK_VIEWIMAGE.class);
	private static Logger error_log = Logger.getLogger("requestAppender");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HERO_STK_VIEWIMAGE() {
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
		this.filePath = request.getServletContext().getRealPath("")+File.separator+"inventoryFiles"+File.separator+"CompanyLogo.jpg";
		
		log.info("filePath       "+filePath);
		 
        File file = new File(filePath);
 
        if (!file.exists()) {

            /*response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
            return;*/
        	filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultlogo.jpg";
        	file = new File(filePath);
        	
        }// Set Content type
        log.info("In do get   "+filePath);
        
        try
        {
        	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));

        // Get image contents.
    byte[] bytes = new byte[in.available()];

       in.read(bytes);
       in.close();

       // Write image contents to response.
       response.getOutputStream().write(bytes);
        }
        catch(Exception e)
        {
        	error_log.info(e);
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
            	error_log.info(ee);
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
