package com.hero.controller.view.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hero.controller.services.admin.loginmodule.HeroImageView;
import com.hero.services.admin.util.ConnectionUtil;

@Controller
public class HERO_UPLOAD_CTRL {
	Logger log = Logger.getLogger(HERO_UPLOAD_CTRL.class);
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,@RequestParam("applntype")String applntype,
			@RequestParam("requesttype")String requesttype,
			@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		
		String serverPath = request.getRealPath("/");
		JdbcTemplate jdbcTemplate = ConnectionUtil.getSpringMYSQLDAO().getJdbcTemplate();
		
		
		File dir = new File(serverPath);
		dir = dir.getParentFile();
		serverPath = dir.getAbsolutePath();
		//System.out.println("Directory   "+serverPath);
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				int applntypeint = 0, requesttypeint = 0;
				if(applntype != null)
				{
					applntypeint = Integer.parseInt(applntype);
				}
				if(requesttype != null)
				{
					requesttypeint = Integer.parseInt(requesttype);
				}
				
				String selectQuery = " SELECT `hau_path` FROM `hero_admin_upload` WHERE `hau_appln_type` = "
						+ " '"+applntypeint+"' AND `hau_req_type` = '"+requesttypeint+"'";
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
				
				serverPath = serverPath+File.separator+path;
				
				log.info("serverPath   "+serverPath);
		
				dir = new File(serverPath);
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				//System.out.println("serverFile   "+serverFile);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				//System.out.println("Server File Location="+ serverFile.getAbsolutePath());

				return "File Successfully Uploaded";
			} catch (Exception e) {
				return "Failed to Upload";
			}
		} else {
			return "Failed to Upload";
		}
	}

	/**
	 * Upload multiple file using Spring Controller
	 */
	@RequestMapping(value = "/uploadMultipleFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadMultipleFileHandler(@RequestParam("name") String[] names,
			@RequestParam("file") MultipartFile[] files) {

		if (files.length != names.length)
			return "Mandatory information missing";

		String message = "";
		for (int i = 0; i < files.length; i++) {
			MultipartFile file = files[i];
			String name = names[i];
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				String rootPath = System.getProperty("catalina.home");
				File dir = new File(rootPath + File.separator + "tmpFiles");
				if (!dir.exists())
					dir.mkdirs();

				// Create the file on server
				File serverFile = new File(dir.getAbsolutePath()
						+ File.separator + name);
				BufferedOutputStream stream = new BufferedOutputStream(
						new FileOutputStream(serverFile));
				stream.write(bytes);
				stream.close();

				log.info("Server File Location="+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name
						+ "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
}
