package com.hero.controller.view.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class HERO_UPLOAD_CTRL {

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFileHandler(@RequestParam("name") String name,@RequestParam("applntype")String applntype,
			@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		
		String serverPath = request.getRealPath("/");
		
		File dir = new File(serverPath);
		dir = dir.getParentFile();
		serverPath = dir.getAbsolutePath();
		//System.out.println("Directory   "+serverPath);
		
		if (!file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();

				// Creating the directory to store file
				int applntypeint = 0;
				if(applntype != null)
				{
					applntypeint = Integer.parseInt(applntype);
				}
				if(applntypeint == 0)
				{
					serverPath = serverPath+File.separator+"logo";
				}
				else if(applntypeint == 1)
				{
					serverPath = serverPath+File.separator+"stock";
				}
				else if(applntypeint == 2)
				{
					serverPath = serverPath+File.separator+"clinic";
				}
				//System.out.println("serverPath   "+serverPath);
		
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

				//System.out.println("Server File Location="+ serverFile.getAbsolutePath());

				message = message + "You successfully uploaded file=" + name
						+ "<br />";
			} catch (Exception e) {
				return "You failed to upload " + name + " => " + e.getMessage();
			}
		}
		return message;
	}
}
