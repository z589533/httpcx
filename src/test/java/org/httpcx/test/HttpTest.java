package org.httpcx.test;

//import org.httpcx.asyn.handle.RequestResult;
import org.httpcx.request.Httpcx;
import org.httpcx.request.bean.HttpAttribute;
import org.httpcx.request.client.HttpCxClient;
import org.junit.Ignore;
import org.junit.Test;
//
//import com.googlecode.asyn4j.core.handler.CacheAsynWorkHandler;
//import com.googlecode.asyn4j.core.handler.DefaultErrorAsynWorkHandler;
//import com.googlecode.asyn4j.service.AsynService;
//import com.googlecode.asyn4j.service.AsynServiceImpl;
//import com.googlecode.asyn4j.springbean.TestBean;

/**
 * @date 2016年5月23日 下午5:20:24
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class HttpTest {
	/**
	 * method
	 */
	@Test
	public void testHttpGetRunnable() {
		for (int i = 0; i < 100; i++) {
			Httpcx client = new HttpCxClient();
			client.getAsynReq("http://www.baidu.com/");
		}
		
	}

	/**
	 * method
	 */
	@Test
	@Ignore
	public void testHttpGetRunnable2() {
		Httpcx client = new HttpCxClient();
		HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(1000).build();
		client.getAsynReq("http://www.baidu.com/", attribute);
	}

	/**
	 * method
	 */
	@Test
	@Ignore
	public void testHttpGetCall() {
		Httpcx client = new HttpCxClient();
		String info = client.getCallReq("http://www.baidu.com/");
		System.out.println("zcz:" + info);
	}

	/**
	 * method
	 */
	@Test
	@Ignore
	public void testHttpGetCall2() {
		Httpcx client = new HttpCxClient();
		HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(1000).build();
		String info = client.getCallReq("");
		System.out.println("zcz:" + info);
	}

//	@Test
//	@Ignore
//	public void asynHttpTest() {
//		AsynService anycService = AsynServiceImpl.getService(300, 3000L, 100, 100, 1000);
//		anycService.setWorkQueueFullHandler(new CacheAsynWorkHandler(100));
//		anycService.setErrorAsynWorkHandler(new DefaultErrorAsynWorkHandler());
//		anycService.init();
//		for (int i = 0; i < 100; i++) {
//			String url= "http://www.baidu.com/?sb=" + i;
//			anycService.addWork(HttpHandle.class, "httpRequest", new Object[] {url },
//					new RequestResult());
//			//System.out.println(anycService.getRunStatInfo());
//		}
//	}
}
