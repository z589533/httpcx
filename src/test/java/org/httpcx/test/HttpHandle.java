package org.httpcx.test;

import java.io.Serializable;

import org.httpcx.request.Http;
import org.httpcx.request.bean.HttpAttribute;
import org.httpcx.request.client.HttpCxClient;

/**
 * @date time 2016年5月23日 下午10:55:37
 * @author zhouchengzhuo
 * @description
 * @version 1.0
 * @parameter 
 * @return
 */
public class HttpHandle implements Serializable {

	Http client = new HttpCxClient();
	
	public void httpRequest(String url) {
		HttpAttribute attribute = HttpAttribute.custom().setConnectTimeout(1000).build();
		client.getAsynReq(url, attribute);
	}
}
