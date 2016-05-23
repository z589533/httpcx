package org.httpcx.request.process.handle.callable;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * @date 2016年5月23日 下午5:53:07
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class AsynHttpGetCall implements Callable<String> {

	private CountDownLatch countDownLatch;
	private final CloseableHttpClient httpClient;
	private final HttpGet httpGet;

	public AsynHttpGetCall(CloseableHttpClient httpClient, HttpGet httpGet, CountDownLatch countDownLatch) {
		this.httpClient = httpClient;
		this.httpGet = httpGet;
		this.countDownLatch = countDownLatch;
	}

	public String call() throws Exception {
		// TODO Auto-generated method stub
		CloseableHttpResponse response = null;
		String info = null;
		try {
			response = httpClient.execute(httpGet, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			info = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			return info;
		} catch (IOException e) {
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

}
