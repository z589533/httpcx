package org.httpcx.test;

import org.httpcx.request.Http;
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
	public void testHttpGet() {
		Http client=new HttpCxClient();
		client.getAsynReq("http://www.baidu.com/");
	}
}
