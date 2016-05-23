package org.httpcx.test;

import org.httpcx.request.Http;
import org.httpcx.request.bean.HttpAttribute;
import org.httpcx.request.client.HttpCxClient;
import org.junit.Test;

/** 
 * @date  2016年5月23日 下午5:20:24 
 * @version 1.0
 * @describe 
 * @author  zhouchengzhuo 
 * @parameter
 * @return
 */
public class HttpTest {
	/**
	 * method
	 */
	@Test
	public void testHttpGetRunnable() {
		Http client=new HttpCxClient();
		client.getAsynReq("http://www.baidu.com/");
	}
	
	/**
	 * method
	 */
	@Test
	public void testHttpGetRunnable2() {
		Http client=new HttpCxClient();
		HttpAttribute attribute=HttpAttribute.custom().setConnectTimeout(1000).build();
		client.getAsynReq("http://www.baidu.com/", attribute);
	}
	
	
	/**
	 * method
	 */
	@Test
	public void testHttpGetCall() {
		Http client=new HttpCxClient();
		String info=client.getCallReq("http://www.baidu.com/");
		System.out.println("zcz:"+info);
	}
	
	/**
	 * method
	 */
	@Test
	public void testHttpGetCall2() {
		Http client=new HttpCxClient();
		HttpAttribute attribute=HttpAttribute.custom().setConnectTimeout(1000).build();
		String info=client.getCallReq("");
		System.out.println("zcz:"+info);
	}
}
