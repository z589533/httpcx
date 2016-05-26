package org.httpcx.request;

/**
 * @date 2016年5月23日 下午3:06:47
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public interface HttpBase {

	int MAXTOTAL = 200;// 最大连接数

	int HTTP_DEFAULTMAXPERROUTE = 50;// 将每个路由基础的连接增加

	int HTTP_MAXPERROUTE = 50;// 将目标主机的最大连接数增加到50

	int RETRYCOUNT = 5;// 重试次数

	int DEFAULT_THREADCOUNT = Runtime.getRuntime().availableProcessors() * 10;

	String DEFAULTCHARSET = "utf-8";

	String DEFAULT_USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0";

	String DEFAULT_ACCEPT = "*/*";

	String DEFAULT_ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";

	int DEFAULT_CONNECT_TIMEOUT = 5000;

	String DEFAULT_ACCEPT_CHARSET = "ISO-8859-1,utf-8,UTF-8,gbk,gb2312;q=0.7,*;q=0.7";

	String DEFAULT_CONNECTION = "keep-alive";

	String DEFAULT_ACCEPT_ENCODING = "gzip, deflate";

	public static final String DEFAULT_CACHE_CONTROL = "max-age=0";

}
