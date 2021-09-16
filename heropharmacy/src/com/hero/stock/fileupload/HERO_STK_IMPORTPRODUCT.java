package com.hero.stock.fileupload;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hero.stock.lov.HERO_STK_IINVENTORYLOV;
import com.hero.stock.util.HEROHOSURINVENTORYUTIL;

/**
 * Servlet implementation class fileUpload
 */
@WebServlet("/forms/importProduct")
@MultipartConfig(fileSizeThreshold=1024*1024*1,	// 2MB
maxFileSize=1024*1024*1,		// 10MB
maxRequestSize=1024*1024*50)
public class HERO_STK_IMPORTPRODUCT extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(HERO_STK_IMPORTPRODUCT.class);
	
	private HEROHOSURINVENTORYUTIL inventoryLOVobj = new HEROHOSURINVENTORYUTIL();
	 
	private static final String SAVE_DIR = "importproduct";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HERO_STK_IMPORTPRODUCT() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// gets absolute path of the web application
		log.info("Welcome to FileUpload");
		String appPath = request.getServletContext().getRealPath("");
		// constructs path of the directory to save uploaded file
		
		String savePath = appPath + File.separator + SAVE_DIR;
		log.info("savePath       "+savePath);
		// creates the save directory if it does not exists
		File fileSaveDir = new File(savePath);
		if (!fileSaveDir.exists()) {
			fileSaveDir.mkdir();
		}
		String fileName = "";
		String redirect_fileName = null;
		for (Part part : request.getParts()) {
			fileName =extractFileName(part,fileSaveDir);
			if(redirect_fileName == null)
			{
				redirect_fileName = fileName;
			}
			// refines the fileName in case it is an absolute path
			fileName = new File(fileName).getName();
			String fileWritePath = savePath + File.separator + fileName;
			File fileWriting = new File(fileWritePath);
			log.info("fileWritePath       "+fileWritePath);
			if(!fileWriting.isDirectory())
			part.write(fileWritePath);	
			
		}
		log.info("redirect_fileName       "+redirect_fileName);
		request.setAttribute("fileName", redirect_fileName);
		request.setAttribute("message", "Upload has been done successfully!");
		request.setAttribute("uploadfilepath", fileName);
		
		getServletContext().getRequestDispatcher("/forms/importproduct").forward(
				request, response);
	}
	
	private String extractFileName(Part part,File fileSaveDir) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				
				File[] list = fileSaveDir.listFiles();
				log.info("list        "+list+"   "+list.length);
				int seqNo = list.length + 1;
				String fileType = s.substring(s.indexOf(".") + 1, s.length()-1);
				log.info(fileType);
				return String.valueOf(seqNo)+"."+fileType;
				
			}
		}
		return "";
	}

}
