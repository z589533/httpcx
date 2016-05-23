package org.httpcx.request;

import java.util.Map;

import org.httpcx.request.bean.HttpAttribute;

/**
 * @date 2016年5月23日 上午11:59:19
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public interface Http extends HttpBase {

	/**
	 * 异步无回调POST请求 火狐标准请求
	 */
	public void postAsynReq(final String url, Map<String, String> maps);

	/**
	 * 异步无回调POST请求
	 */
	public void postAsynReq(final String url, Map<String, String> maps, HttpAttribute attribute);

	/**
	 * 异步无回调GET请求 火狐标准请求
	 */
	public void getAsynReq(final String url);
	
	/**
	 * 异步无回调GET请求 
	 */
	public void getAsynReq(final String url, HttpAttribute attribute);

	
	/**
	 * 异步有回调POST请求
	 * 
	 * @return
	 */
	public String postCallReq(final String url, Map<String, String> maps);

	/**
	 * 异步有回调GET请求
	 * 
	 * @return
	 */
	public String getCallReq(final String url);
}
