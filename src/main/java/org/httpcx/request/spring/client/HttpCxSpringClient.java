package org.httpcx.request.spring.client;

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
import org.httpcx.request.Httpcx;
import org.httpcx.request.bean.HttpAttribute;
import org.httpcx.request.process.handle.callable.AsynHttpGetCall;
import org.httpcx.request.process.handle.callable.AsynHttpPostCall;
import org.httpcx.request.process.handle.runnable.AsynHttpGetHandle;
import org.httpcx.request.process.handle.runnable.AsynHttpPostHandle;
import org.httpcx.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @date 2016年5月24日 上午11:27:32
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class HttpCxSpringClient implements Httpcx {

	public final static Logger logger = LoggerFactory.getLogger(HttpCxSpringClient.class);

	private String userAgent = DEFAULT_USER_AGENT;

	private String accept = DEFAULT_ACCEPT;

	private String acceptLanguage = DEFAULT_ACCEPT_LANGUAGE;

	private String acceptCharset = DEFAULT_ACCEPT_CHARSET;

	private int connectTimeout = DEFAULT_CONNECT_TIMEOUT;

	private String connection = DEFAULT_CONNECTION;

	private String acceptEncoding = DEFAULT_ACCEPT_ENCODING;

	private String cacheControl = DEFAULT_CACHE_CONTROL;

	private int retryCount = RETRYCOUNT;

	private String resultCharset = DEFAULTCHARSET;

	private int threadCount = DEFAULT_THREADCOUNT;

	ExecutorService executorService = Executors.newFixedThreadPool(threadCount);

	private void config(HttpRequestBase httpRequestBase) {
		httpRequestBase.setHeader("User-Agent", getUserAgent());
		httpRequestBase.setHeader("Accept", getAccept());
		httpRequestBase.setHeader("Accept-Language", getAcceptLanguage());
		httpRequestBase.setHeader("Accept-Charset", getAcceptCharset());
		// 配置请求的超时设置
		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(getConnectTimeout())
				.setConnectTimeout(getConnectTimeout()).setSocketTimeout(getConnectTimeout()).build();
		httpRequestBase.setConfig(requestConfig);
	}

	@Override
	public void postAsynReq(String url, Map<String, String> maps) {
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (String key : maps.keySet()) {
				pairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			config(httpPost);
			executorService
					.execute(new AsynHttpPostHandle(closeableHttpClient, httpPost, countDownLatch, getResultCharset()));
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

	@Override
	public void postAsynReq(String url, Map<String, String> maps, HttpAttribute attribute) {
		this.postAsynReq(url, maps);
	}

	@Override
	public void getAsynReq(String url) {
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpGet httpGet = new HttpGet(url);
			config(httpGet);
			executorService
					.execute(new AsynHttpGetHandle(closeableHttpClient, httpGet, countDownLatch, getResultCharset()));
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

	@Override
	public void getAsynReq(String url, HttpAttribute attribute) {
		// TODO Auto-generated method stub
		this.getAsynReq(url);
	}

	@Override
	public String postCallReq(String url, Map<String, String> maps) {
		String info = null;
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpPost httpPost = new HttpPost(url);
			List<NameValuePair> pairs = new ArrayList<NameValuePair>();
			for (String key : maps.keySet()) {
				pairs.add(new BasicNameValuePair(key, maps.get(key)));
			}
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			config(httpPost);
			Future<String> future = executorService
					.submit(new AsynHttpPostCall(closeableHttpClient, httpPost, countDownLatch, getResultCharset()));
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

	@Override
	public String getCallReq(String url) {
		String info = null;
		CloseableHttpClient closeableHttpClient = buildHttp(url);
		long start = System.currentTimeMillis();
		try {
			CountDownLatch countDownLatch = new CountDownLatch(1);
			HttpGet httpGet = new HttpGet(url);
			config(httpGet);
			Future<String> future = executorService
					.submit(new AsynHttpGetCall(closeableHttpClient, httpGet, countDownLatch, getResultCharset()));
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

	@Override
	public String postCallReq(String url, Map<String, String> maps, HttpAttribute attribute) {
		return this.postCallReq(url, maps);
	}

	@Override
	public String getCallReq(String url, HttpAttribute attribute) {
		// TODO Auto-generated method stub
		return this.getCallReq(url);
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
				if (executionCount >= getRetryCount()) {
					logger.error("retry count achieve:" + getRetryCount() + " have given up:" + url);
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

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAcceptLanguage() {
		return acceptLanguage;
	}

	public void setAcceptLanguage(String acceptLanguage) {
		this.acceptLanguage = acceptLanguage;
	}

	public String getAcceptCharset() {
		return acceptCharset;
	}

	public void setAcceptCharset(String acceptCharset) {
		this.acceptCharset = acceptCharset;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getAcceptEncoding() {
		return acceptEncoding;
	}

	public void setAcceptEncoding(String acceptEncoding) {
		this.acceptEncoding = acceptEncoding;
	}

	public String getCacheControl() {
		return cacheControl;
	}

	public void setCacheControl(String cacheControl) {
		this.cacheControl = cacheControl;
	}

	public int getRetryCount() {
		return retryCount;
	}

	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	public String getResultCharset() {
		return resultCharset;
	}

	public void setResultCharset(String resultCharset) {
		this.resultCharset = resultCharset;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

}
