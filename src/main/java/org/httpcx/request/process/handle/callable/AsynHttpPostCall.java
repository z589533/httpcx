package org.httpcx.request.process.handle.callable;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.httpcx.request.HttpBase;

/**
 * @date 2016年5月23日 下午5:57:14
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class AsynHttpPostCall implements Callable<String> {

	private CountDownLatch countDownLatch;
	private final CloseableHttpClient httpClient;
	private final HttpPost httpPost;

	public AsynHttpPostCall(CloseableHttpClient httpClient, HttpPost httpPost, CountDownLatch countDownLatch) {
		this.httpClient = httpClient;
		this.httpPost = httpPost;
		this.countDownLatch = countDownLatch;
	};

	public String call() throws Exception {
		// TODO Auto-generated method stub
		String info = null;
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			info = EntityUtils.toString(entity, HttpBase.DEFAULTCHARSET);
			EntityUtils.consume(entity);
			return info;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			countDownLatch.countDown();
			try {
				if (response != null)
					response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}
	/**
	 * method
	 */

}
