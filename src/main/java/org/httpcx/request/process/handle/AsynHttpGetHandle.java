package org.httpcx.request.process.handle;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2016年5月23日 下午5:17:14
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class AsynHttpGetHandle implements Runnable {

	public final static Logger logger = LoggerFactory.getLogger(AsynHttpGetHandle.class);

	private CountDownLatch countDownLatch;
	private final CloseableHttpClient httpClient;
	private final HttpGet httpGet;

	public AsynHttpGetHandle(CloseableHttpClient httpClient, HttpGet httpGet, CountDownLatch countDownLatch) {
		this.httpClient = httpClient;
		this.httpGet = httpGet;
		this.countDownLatch = countDownLatch;
	}

	public void run() {
		// TODO Auto-generated method stub
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpGet, HttpClientContext.create());
			HttpEntity entity = response.getEntity();
			String info = EntityUtils.toString(entity, "utf-8");
			EntityUtils.consume(entity);
			logger.info("Thread:" + Thread.currentThread().getName() + " info:" + info);
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
	}
	/**
	 * method
	 */
}
