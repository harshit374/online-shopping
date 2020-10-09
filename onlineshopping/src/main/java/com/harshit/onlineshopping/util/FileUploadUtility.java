package com.harshit.onlineshopping.util;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

public class FileUploadUtility {

	public static final String ABS_PATH = "/Users/harshit/eclipse-workspace/Spring-Projects/online-shopping/onlineshopping/src/main/webapp/assets/images";
	public static String REAL_PATH = "";
	public static Logger logger = LoggerFactory.getLogger(FileUploadUtility.class);

	
	public static void upload(HttpServletRequest request, MultipartFile file, String code) {

		// get the real path
		REAL_PATH = request.getSession().getServletContext().getRealPath("/assets/images/");
		
		logger.info(REAL_PATH);
		
		// to make sure all the directory exists
		// please create the directories
		if(!new File(ABS_PATH).exists()) {
			// create the directory
			new File(ABS_PATH).mkdirs();
		}
		
		if(!new File(REAL_PATH).exists()) {
			// create the directory
			new File(REAL_PATH).mkdirs();
		}
		
		try {
			file.transferTo(new File(ABS_PATH + code + ".jpg"));
			file.transferTo(new File(REAL_PATH + code + ".jpg"));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
