package com.chinatelecom.common;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Print callback json util
 * 
 * @author xgliao
 * 
 */
public class PrintWriterUtil {
	private static Log logger = LogFactory.getLog(PrintWriterUtil.class);

	public static void writeJson(HttpServletRequest request,
			HttpServletResponse response, String data) {
		response.setContentType("application/json");
		String jsonpCallback = request.getParameter("jsonpCallback");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.println(jsonpCallback + "(" + data + ")");
		} catch (IOException e) {
			logger.error("Json Write Error:", e);
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}

	}

	public static void writeJsonWithoutJsonp(HttpServletRequest request,
			HttpServletResponse response, String data) {
		response.setContentType("application/json");
		PrintWriter pw = null;
		try {
			pw = response.getWriter();
			pw.println(data);
		} catch (IOException e) {
			logger.error("Json Write Error:", e);
		} finally {
			if (pw != null) {
				pw.flush();
				pw.close();
			}
		}

	}

	public static void writeError(HttpServletRequest request,
			HttpServletResponse response, String error) {
		String json = "{\"status\":\"error\",\"message\":\"" + error + ".\"}";

		writeJson(request, response, json);
	}
	
	public static void isJson(HttpServletRequest request,
			HttpServletResponse response , String json){
		String option = request.getParameter("option");
		boolean isJson = (option!=null && Integer.valueOf(option) ==0 ) ?true : false ;
		if(isJson)
			writeJsonWithoutJsonp(request, response, json);
		else writeJson(request, response, json);
	}

}
