package org.httpcx.request.client;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.io.UnsupportedEncodingException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;

import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.httpcx.request.Http;
import org.httpcx.request.bean.HttpAttribute;
import org.httpcx.request.process.handle.callable.AsynHttpGetCall;
import org.httpcx.request.process.handle.callable.AsynHttpPostCall;
import org.httpcx.request.process.handle.runnable.AsynHttpGetHandle;
import org.httpcx.request.process.handle.runnable.AsynHttpPostHandle;
import org.httpcx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2016年5月23日 下午2:19:50
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class HttpCxClient implements Http {

	public final static Logger logger = LoggerFactory.getLogger(HttpCxClient.class);

	private static void config(HttpAttribute attribute, HttpRequestBase httpRequestBase) {
		httpRequestBase.setHeader("User-Agent", attribute.getUser_Agent());
		httpRequestBase.setHeader("Accept", attribute.getAccept());
		httpRequestBase.setHeader("Accept-Language", attribute.getAccept_Language());
		httpRequestBase.setHeader("Accept-Charset", attribute.getAccept_Charset());
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(attribute.getConnectTimeout())
				.setConnectTimeout(attribute.getConnectTimeout()).setSocketTimeout(attribute.getConnectTimeout())
				.build();
		httpRequestBase.setConfig(requestConfig);
	}

	public void postAsynReq(final String url, Map<String, String> maps) {
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (String key : maps.keySet()) {
				pairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(6000).build();
			HttpCxClient.config(attribute, httpPost);
			executorService.execute(new AsynHttpPostHandle(closeableHttpClient, httpPost, countDownLatch));
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
	}

	public void postAsynReq(String url, Map<String, String> maps, HttpAttribute attribute) {
		/**
		 * 方法
		 */
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (String key : maps.keySet()) {
				pairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpCxClient.config(attribute, httpPost);
			executorService.execute(new AsynHttpPostHandle(closeableHttpClient, httpPost, countDownLatch));
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");

	}

	public void getAsynReq(String url, HttpAttribute attribute) {
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpGet httpGet = new HttpGet(url);
			HttpCxClient.config(attribute, httpGet);
			executorService.execute(new AsynHttpGetHandle(closeableHttpClient, httpGet, countDownLatch));
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
	}

	public void getAsynReq(final String url) {
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpGet httpGet = new HttpGet(url);
			HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(6000).build();
			HttpCxClient.config(attribute, httpGet);
			executorService.execute(new AsynHttpGetHandle(closeableHttpClient, httpGet, countDownLatch));
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
	}

	public String postCallReq(String url, Map<String, String> maps) {
		String info = null;
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (String key : maps.keySet()) {
				pairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(6000).build();
			HttpCxClient.config(attribute, httpPost);
			Future<String> future = executorService
					.submit(new AsynHttpPostCall(closeableHttpClient, httpPost, countDownLatch));
			info = future.get();
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
		return info;
	}

	public String getCallReq(String url) {
		String info = null;
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			ExecutorService executorService = Executors.newFixedThreadPool(1);
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpGet httpGet = new HttpGet(url);
			HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(6000).build();
			HttpCxClient.config(attribute, httpGet);
			Future<String> future = executorService
					.submit(new AsynHttpGetCall(closeableHttpClient, httpGet, countDownLatch));
			info = future.get();
			countDownLatch.await();
			executorService.shutdown();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} finally {
			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
					+ ", all Thread finish wait....");
		}
		long end = System.currentTimeMillis();
		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
		return info;
	}

	private CloseableHttpClient buildHttp(final String url) {
		ConnectionSocketFactory socketFactory = PlainConnectionSocketFactory.getSocketFactory();
		LayeredConnectionSocketFactory connectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
				.register("http", socketFactory).register("https", connectionSocketFactory).build();
		PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager(registry);
		manager.setMaxTotal(MAXTOTAL);
		manager.setDefaultMaxPerRoute(HTTP_DEFAULTMAXPERROUTE);
		HttpHost httpHost = new HttpHost(StringUtil.captureHost(url), StringUtil.captureHostPort(url));
		manager.setMaxPerRoute(new HttpRoute(httpHost), HTTP_MAXPERROUTE);
		HttpRequestRetryHandler requestRetryHandler = new HttpRequestRetryHandler() {
			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
				if (executionCount >= RETRYCOUNT) {
					logger.error("retry count achieve:" + RETRYCOUNT + " have given up:" + url);
					return false;
				}
				if (exception instanceof NoHttpResponseException) {
					logger.warn("service lose link retry:" + url);
					return true;
				}
				if (exception instanceof SSLHandshakeException) {
					logger.error(" SSL non-existent exception:" + url);
					return false;
				}
				if (exception instanceof InterruptedIOException) {
					logger.warn("service time out:" + url);
					return true;
				}
				if (exception instanceof UnknownHostException) {
					logger.error("service hang out:" + url);
					return false;
				}
				if (exception instanceof ConnectTimeoutException) {
					logger.warn("link time out:" + url);
					return true;
				}
				if (exception instanceof SSLException) {
					logger.info("SSL exception:" + url);
					return false;
				}
				HttpClientContext httpClientContext = HttpClientContext.adapt(context);
				HttpRequest request = httpClientContext.getRequest();
				if (!(request instanceof HttpEntityEnclosingRequest)) {
					logger.warn("show service idempotent:" + url);
					return true;
				}
				return false;
			}
		};
		CloseableHttpClient closeableHttpClient = HttpClients.custom().setConnectionManager(manager)
				.setRetryHandler(requestRetryHandler).build();
		return closeableHttpClient;
	}
}
