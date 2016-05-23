package org.httpcx.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @date  2016年5月23日 下午2:09:37 
 * @version 1.0
 * @describe 
 * @author  zhouchengzhuo 
 * @parameter
 * @return
 */
public class StringUtil {
	/**
	 * method
	 */
	
	/**
	 * 截取主域名
	 * @param url
	 * @return
	 */
	public static String captureHost(String url) {
		Matcher m = Pattern.compile("^(https://[^/]+|http://[^/]+)").matcher(url);
		while (m.find()) {
			return m.group();
		}
		return m.group();
	}

	/**
	 * 截取端口号
	 * @param url
	 * @return
	 */
	public static Integer captureHostPort(String url) {
		String regex = "//(.*?):([0-9]+)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(url);
		String port = null;
		while (m.find()) {
			port = m.group(2);
		}
		if (port == null) {
			port = "80";
		}
		return Integer.parseInt(port);
	}
}
