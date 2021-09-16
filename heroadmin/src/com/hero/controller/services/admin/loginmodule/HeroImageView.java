package com.hero.controller.services.admin.loginmodule;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.hero.services.admin.lov.HERO_ADM_SERVC_INVENTORYLOVIMPL;
import com.hero.services.admin.util.ConnectionUtil;
import com.hero.services.admin.util.HERO_ADM_SERVC_HOSURINVENTORYUTIL;

/**
 * Servlet implementation class HeroImageView
 */
@WebServlet("/forms/HeroImageView")
public class HeroImageView extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(HeroImageView.class);
	private String filePath;

	private static JdbcTemplate jdbcTemplate = ConnectionUtil.getSpringMYSQLDAO().getJdbcTemplate();
	//private JdbcTemplate jdbcTemplate = HERO_ADM_SERVC_HOSURINVENTORYUTIL.getJdbcTemplate();
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HeroImageView() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String applntype = request.getParameter("applntype");
		String requesttype = request.getParameter("requesttype");
		
		int intApplntype = 0, intRequesttype = 0;
		if(applntype != null)
		{
			intApplntype = Integer.parseInt(applntype);
		}
		if(requesttype != null)
		{
			intRequesttype = Integer.parseInt(requesttype);
		}
		//log.info("Hero Product List   "+InventoryLOVImpl.HERO_ProductList.get(intIndex));
		if(intApplntype > 0)
		{
		byte[] imageData;
		File file = null;
		log.info("intApplntype value     "+intApplntype);
		String contentType = this.getServletContext().getMimeType("logo.jpg");
        /*Map<String, Object> prodMap = (Map<String, Object>) HERO_ADM_SERVC_INVENTORYLOVIMPL.HERO_ProductList.get(intIndex);
        log.info("prodMap: "+ prodMap);
        if(prodMap.get("logo") != null)
        {
        	log.info("prodMap"+prodMap);
        	byte[] logoData = (byte[]) prodMap.get("logo");
        	response.setHeader("Content-Type", contentType);
        	response.setHeader("Content-Length", String.valueOf(logoData.length));
        	response.setHeader("Content-Disposition", "inline; filename=\"" + "logo.jpg" + "\"");
        	response.getOutputStream().write(logoData);
        }*/
        if(intApplntype == 2){
        	String imgname = request.getParameter("imgname");
        	
        	String selectQuery = " SELECT `hau_path` FROM `hero_admin_upload` WHERE `hau_appln_type` = "
					+ " '"+intApplntype+"' AND `hau_req_type` = '"+intRequesttype+"'";
			log.info("apath queruy   "+selectQuery);
			@SuppressWarnings("unchecked")
			List<Object> pathList = jdbcTemplate.query(selectQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
					
					HashMap<String, String> map = new HashMap<String, String>();

					map.put("path", rs.getString("hau_path"));
					
					return map;
				}
			});
			
			String path ="";
			if(pathList != null && pathList.size() > 0){
				Map<String, Object> map = (Map<String, Object>) pathList.get(0);
				path = (String) map.get("path");
				log.info("path    "+map.get("path"));
			}
        	
			
			filePath = request.getServletContext().getRealPath("");
			File dir = new File(filePath);
			dir = dir.getParentFile();
			filePath = dir.getAbsolutePath()+File.separator+path+File.separator+imgname+".jpg";
			file = new File(filePath);
			
			if (!file.exists()) {
				
				if(intRequesttype == 1)	{	
	        	filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultpatient.png";
	        	file = new File(filePath);	
				}else if(intRequesttype == 2){
					filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultphysician.png";
		        	file = new File(filePath);	
				}
	        	
	        	
	        }
			
        }

       /* }*/
        log.info("filePath       "+filePath);

    	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
    	byte[] bytes = new byte[in.available()];
    	in.read(bytes);
    	in.close();
    	response.getOutputStream().write(bytes);
		}
		else
		{
			filePath = request.getServletContext().getRealPath("");
			File dir = new File(filePath);
			dir = dir.getParentFile();
			filePath = dir.getAbsolutePath()+File.separator+"logo"+File.separator+"companylogo.jpg";
			File file = new File(filePath);
			if (!file.exists()) {

	            /*response.sendError(HttpServletResponse.SC_NOT_FOUND); // 404.
	            return;*/
				filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultlogo.png";
	        	file = new File(filePath);
	        }// Set Content type
			
			 
			
			String selectQuery = "SELECT * FROM hero_orgn_table ORDER BY `created_at` DESC";
			@SuppressWarnings("unchecked")
			List<Object> orgnList = jdbcTemplate.query(selectQuery, new RowMapper() {
				
				@Override
				public Object mapRow(ResultSet rs, int arg1) throws SQLException {
					// TODO Auto-generated method stub
					/*List<HashMap<String, String>> detail = new ArrayList<HashMap<String,String>>();*/
					
					HashMap<String, String> map = new HashMap<String, String>();

					map.put("orgnlogo", rs.getString("org_logo"));
					
					log.info("Company Suggestion List        "+map);
					
					return map;
				}
			});

			if(orgnList.size() == 0){
				filePath = request.getServletContext().getRealPath("")+File.separator+"resources"+File.separator+"images"+File.separator+"logos"+File.separator+"defaultlogo.png";
	        	file = new File(filePath);
			}

			log.info("filePath       "+filePath);

        	BufferedInputStream in = new BufferedInputStream(new FileInputStream(file));
        	byte[] bytes = new byte[in.available()];
        	in.read(bytes);
        	in.close();
        	response.getOutputStream().write(bytes);
        
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
