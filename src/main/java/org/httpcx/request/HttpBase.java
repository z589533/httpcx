package org.httpcx.request;
/** 
 * @date  2016年5月23日 下午3:06:47 
 * @version 1.0
 * @describe 
 * @author  zhouchengzhuo 
 * @parameter
 * @return
 */
public interface HttpBase {

	 int MAXTOTAL=200;//最大连接数
	 
	 int HTTP_DEFAULTMAXPERROUTE=50;//将每个路由基础的连接增加
	 
	 int HTTP_MAXPERROUTE=50;//将目标主机的最大连接数增加到50
	 
	 int HTTP_RETRY_COUNT=5;//重试次数
	 
	 int RETRYCOUNT=5;
	
}
