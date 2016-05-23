package org.httpcx.request.process.handle;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2016年5月23日 下午5:03:49
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class AsynHttpPostHandle implements Runnable {

	public final static Logger logger = LoggerFactory.getLogger(AsynHttpPostHandle.class);

	private CountDownLatch countDownLatch;
	private final CloseableHttpClient httpClient;
	private final HttpPost httpPost;

	public AsynHttpPostHandle(CloseableHttpClient httpClient, HttpPost httpPost, CountDownLatch countDownLatch) {
		this.httpClient = httpClient;
		this.httpPost = httpPost;
		this.countDownLatch = countDownLatch;
	}

	public void run() {
		// TODO Auto-generated method stub
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(httpPost, HttpClientContext.create());
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

}
