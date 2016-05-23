package org.httpcx.request;

public class HttpThreadClient {
//
//	//public final static Logger logger = LoggerFactory.getLogger(HttpThreadClient.class);
//
//	private static void config(HttpRequestBase httpRequestBase) {
//		httpRequestBase.setHeader("User-Agent", "Mozilla/5.0");
//		httpRequestBase.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
//		httpRequestBase.setHeader("Accept-Language", "en-US,en,zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3");// "en-US,en;q=0.5");
//		httpRequestBase.setHeader("Accept-Charset", "ISO-8859-1,utf-8,UTF-8,gbk,gb2312;q=0.7,*;q=0.7");
//		// httpRequestBase.setHeader("accept","*/*");
//		// httpRequestBase.setHeader("connection","Keep-Alive");
//		// httpRequestBase.setHeader("user-agent","Mozilla/4.0 (compatible; MSIE
//		// 6.0; Windows NT 5.1;SV1)");
//		// 配置请求的超时设置
//		RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(Config.HTTP_TIME_OUT)
//				.setConnectTimeout(Config.HTTP_TIME_OUT).setSocketTimeout(Config.HTTP_TIME_OUT).build();
//		httpRequestBase.setConfig(requestConfig);
//	}
//
//	public static String sendRequsetPostXML(final String url, final String xml) throws ExecutionException {
//		String info = null;
//		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
//				.register("http", plainsf).register("https", sslsf).build();
//
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
//		// 将最大连接数增加到200
//		cm.setMaxTotal(Config.HTTP_MAXTOTAL);
//		// 将每个路由基础的连接增加到20
//		cm.setDefaultMaxPerRoute(Config.HTTP_DEFAULTMAXPERROUTE);
//
//		// 将目标主机的最大连接数增加到50
//		// System.out.println(captureHost(url));
//		HttpHost localhost = new HttpHost(captureHost(url), captureHostPort(url));
//		cm.setMaxPerRoute(new HttpRoute(localhost), Config.HTTP_MAXPERROUTE);
//
//		// 请求重试处理
//		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
//			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//
//				if (executionCount > Config.HTTP_RETRY_COUNT) {// 如果已经重试了5次，就放弃
//					logger.info("重试失败--- return out=" + url);
//					return false;
//				}
//				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//					logger.info("服务器丢掉了连接=" + url);
//					return true;
//				}
//				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//					logger.info("SSL=" + url);
//					return false;
//				}
//				if (exception instanceof InterruptedIOException) {// 超时
//					logger.info("超时=" + url);
//					return true;
//				}
//				if (exception instanceof UnknownHostException) {// 目标服务器不可达
//					logger.info("目标服务器不可达=" + url);
//					return false;
//				}
//				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
//					logger.info("连接被拒绝=" + url);
//					return false;
//				}
//				if (exception instanceof SSLException) {// ssl握手异常
//					logger.info("ssl握手异常=" + url);
//					return false;
//				}
//				if (exception instanceof SocketTimeoutException) {
//					logger.info("Socket 链接超时=" + url);
//					return false;
//				}
//				HttpClientContext clientContext = HttpClientContext.adapt(context);
//				HttpRequest request = clientContext.getRequest();
//				// 如果请求是幂等的，就再次尝试
//				if (!(request instanceof HttpEntityEnclosingRequest)) {
//					return true;
//				}
//				if (exception instanceof Exception) {
//					logger.info("连接未知异常=" + url);
//					return false;
//				}
//				return false;
//			}
//		};
//
//		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
//				.setRetryHandler(httpRequestRetryHandler).build();
//
//		long start = System.currentTimeMillis();
//		try {
//			ExecutorService executors = Executors.newFixedThreadPool(1);
//			CountDownLatch countDownLatch = new CountDownLatch(1);
//
//			HttpPost httpPost = new HttpPost(url);
//			StringEntity myEntity = new StringEntity(xml, "UTF-8");
//			httpPost.addHeader("Content-Type", "text/xml");
//			httpPost.setEntity(myEntity);
//			config(httpPost);
//
//			// 启动线程抓取
//			// executors.execute(new PostCGBXMLRunnable(dealService,cgb, entity,
//			// cgbReqInfo, reqPayParameter, response,
//			// request, httpClient, httpPost, countDownLatch));
//
//			Future<String> future = executors.submit(new CGBPostHandler(httpClient, httpPost, countDownLatch));
//			info = future.get();
//			countDownLatch.await();
//			executors.shutdown();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
//					+ ", all Thread finish wait....");
//		}
//		long end = System.currentTimeMillis();
//		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
//		return info;
//	}
//
//	public static void sendRequsetPost(final String url, Map<String, String> map) {
//		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
//				.register("http", plainsf).register("https", sslsf).build();
//
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
//		// 将最大连接数增加到200
//		cm.setMaxTotal(Config.HTTP_MAXTOTAL);
//		// 将每个路由基础的连接增加到20
//		cm.setDefaultMaxPerRoute(Config.HTTP_DEFAULTMAXPERROUTE);
//		// 将目标主机的最大连接数增加到50
//		HttpHost localhost = new HttpHost(captureHost(url), captureHostPort(url));
//		cm.setMaxPerRoute(new HttpRoute(localhost), Config.HTTP_MAXPERROUTE);
//
//		// 请求重试处理
//		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
//			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//				if (executionCount >= Config.HTTP_RETRY_COUNT) {// 如果已经重试了5次，就放弃
//					logger.info("return out=" + url);
//					return false;
//				}
//				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//					logger.info("service out url=" + url);
//					return true;
//				}
//				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//					logger.info("SSL=" + url);
//					return false;
//				}
//				if (exception instanceof InterruptedIOException) {// 超时
//					logger.info("time out=" + url);
//					return true;
//				}
//				if (exception instanceof UnknownHostException) {// 目标服务器不可达
//					logger.info("service out=" + url);
//					return false;
//				}
//				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
//					logger.info("service out=" + url);
//					return false;
//				}
//				if (exception instanceof SSLException) {// ssl握手异常
//					return false;
//				}
//				HttpClientContext clientContext = HttpClientContext.adapt(context);
//				HttpRequest request = clientContext.getRequest();
//				// 如果请求是幂等的，就再次尝试
//				if (!(request instanceof HttpEntityEnclosingRequest)) {
//					return true;
//				}
//				return false;
//			}
//		};
//
//		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
//				.setRetryHandler(httpRequestRetryHandler).build();
//
//		long start = System.currentTimeMillis();
//		try {
//			ExecutorService executors = Executors.newFixedThreadPool(1);
//			CountDownLatch countDownLatch = new CountDownLatch(1);
//			HttpPost httpPost = new HttpPost(url);
//			List<NameValuePair> nvps = new ArrayList<NameValuePair>();
//			for (String key : map.keySet()) {
//				nvps.add(new BasicNameValuePair(key, map.get(key)));
//			}
//			try {
//				httpPost.setEntity(new UrlEncodedFormEntity(nvps));
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//			config(httpPost);
//			// 启动线程抓取
//			executors.execute(new PostRunnable(httpClient, httpPost, countDownLatch));
//			countDownLatch.await();
//			executors.shutdown();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
//					+ ", all Thread finish wait....");
//		}
//
//		long end = System.currentTimeMillis();
//		logger.info("url=" + url + " waste of time=" + (end - start) + "ms");
//	}
//
//	public static String sendRequsetGET(final String url) throws ExecutionException {
//		ConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
//		LayeredConnectionSocketFactory sslsf = SSLConnectionSocketFactory.getSocketFactory();
//		Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory> create()
//				.register("http", plainsf).register("https", sslsf).build();
//		String info = null;
//		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(registry);
//		// 将最大连接数增加到200
//		cm.setMaxTotal(Config.HTTP_MAXTOTAL);
//		// 将每个路由基础的连接增加到20
//		cm.setDefaultMaxPerRoute(Config.HTTP_DEFAULTMAXPERROUTE);
//
//		// 将目标主机的最大连接数增加到50
//		HttpHost localhost = new HttpHost(captureHost(url), captureHostPort(url));
//		cm.setMaxPerRoute(new HttpRoute(localhost), Config.HTTP_MAXPERROUTE);
//		// 请求重试处理
//		HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
//			public boolean retryRequest(IOException exception, int executionCount, HttpContext context) {
//				if (executionCount >= Config.HTTP_RETRY_COUNT) {// 如果已经重试了5次，就放弃
//					logger.info("重试失败---已放弃return out=" + url);
//					return false;
//				}
//				if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
//					logger.info("service out url=" + url);
//					return true;
//				}
//				if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
//					logger.info("SSL=" + url);
//					return false;
//				}
//				if (exception instanceof InterruptedIOException) {// 超时
//					logger.info("time out=" + url);
//					return true;
//				}
//				if (exception instanceof UnknownHostException) {// 目标服务器不可达
//					logger.info("service out=" + url);
//					return false;
//				}
//				if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
//					logger.info("service out=" + url);
//					return false;
//				}
//				if (exception instanceof SSLException) {// ssl握手异常
//					return false;
//				}
//
//				HttpClientContext clientContext = HttpClientContext.adapt(context);
//				HttpRequest request = clientContext.getRequest();
//				// 如果请求是幂等的，就再次尝试
//				if (!(request instanceof HttpEntityEnclosingRequest)) {
//					return true;
//				}
//				return false;
//			}
//		};
//
//		CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(cm)
//				.setRetryHandler(httpRequestRetryHandler).build();
//
//		// URL列表数组
//
//		long start = System.currentTimeMillis();
//		try {
//			ExecutorService executors = Executors.newFixedThreadPool(1);
//			CountDownLatch countDownLatch = new CountDownLatch(1);
//			HttpGet httpget = new HttpGet(url);
//			config(httpget);
//			// 启动线程抓取
//			Future<String> future = executors.submit(new CGBGetHandler(httpClient, httpget, countDownLatch));
//			info = future.get();
//			countDownLatch.await();
//			executors.shutdown();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} finally {
//			logger.info("Thread:" + Thread.currentThread().getName() + ",time=" + System.currentTimeMillis()
//					+ ", all Thread finish wait....");
//		}
//		long end = System.currentTimeMillis();
//		logger.info("waste of time=" + (end - start)+"ms");
//		return info;
//	}
//
//	public static String captureHost(String url) {
//		Matcher m = Pattern.compile("^(https://[^/]+|http://[^/]+)").matcher(url);
//		while (m.find()) {
//			return m.group();
//		}
//		return m.group();
//	}
//
//	public static Integer captureHostPort(String url) {
//		String regex = "//(.*?):([0-9]+)";
//		Pattern p = Pattern.compile(regex);
//		Matcher m = p.matcher(url);
//		String port = null;
//		while (m.find()) {
//			port = m.group(2);
//		}
//		if (port == null) {
//			port = "80";
//		}
//		return Integer.parseInt(port);
//	}
//
//	static class PostRunnable implements Runnable {
//
//		private CountDownLatch countDownLatch;
//		private final CloseableHttpClient httpClient;
//		private final HttpPost httpPost;
//
//		public PostRunnable(CloseableHttpClient httpClient, HttpPost httpPost, CountDownLatch countDownLatch) {
//			this.httpClient = httpClient;
//			this.httpPost = httpPost;
//			this.countDownLatch = countDownLatch;
//		}
//
//		@Override
//		public void run() {
//			CloseableHttpResponse response = null;
//			try {
//				response = httpClient.execute(httpPost, HttpClientContext.create());
//				HttpEntity entity = response.getEntity();
//				String info = EntityUtils.toString(entity, "utf-8");
//				logger.info(info);
//				EntityUtils.consume(entity);
//			} catch (IOException e) {
//				e.printStackTrace();
//			} finally {
//				countDownLatch.countDown();
//				try {
//					if (response != null)
//						response.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
}
