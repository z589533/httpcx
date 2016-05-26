package org.httpcx.request.bean;

import org.httpcx.request.Httpcx;

/**
 * @date 2016年5月23日 下午2:14:38
 * @version 1.0
 * @describe
 * @author zhouchengzhuo
 * @parameter
 * @return
 */
public class HttpAttribute {
	/**
	 * 默认火狐
	 * method
	 */
	private String user_Agent=Httpcx.DEFAULT_USER_AGENT;

	private String accept=Httpcx.DEFAULT_ACCEPT;

	private String accept_Language=Httpcx.DEFAULT_ACCEPT_LANGUAGE;

	private String accept_Charset=Httpcx.DEFAULT_ACCEPT_CHARSET;

	private int connectTimeout = Httpcx.DEFAULT_CONNECT_TIMEOUT;

	private String connection=Httpcx.DEFAULT_CONNECTION;
	
	private String accept_Encoding=Httpcx.DEFAULT_ACCEPT_ENCODING;
	
	private String cache_Control=Httpcx.DEFAULT_CACHE_CONTROL;
	
	private String result_Charset=Httpcx.DEFAULTCHARSET;
	
	public HttpAttribute() {
		// TODO Auto-generated constructor stub
	}

	public HttpAttribute(String user_Agent, String accept, String accept_Language, String accept_Charset) {
		super();
		this.user_Agent = user_Agent;
		this.accept = accept;
		this.accept_Language = accept_Language;
		this.accept_Charset = accept_Charset;
	}

	public HttpAttribute(String user_Agent, String accept, String accept_Language, String accept_Charset,
			int connectTimeout) {
		super();
		this.user_Agent = user_Agent;
		this.accept = accept;
		this.accept_Language = accept_Language;
		this.accept_Charset = accept_Charset;
		this.connectTimeout = connectTimeout;
	}


	public HttpAttribute(String user_Agent, String accept, String accept_Language, String accept_Charset,
			int connectTimeout, String connection, String accept_Encoding, String cache_Control) {
		super();
		this.user_Agent = user_Agent;
		this.accept = accept;
		this.accept_Language = accept_Language;
		this.accept_Charset = accept_Charset;
		this.connectTimeout = connectTimeout;
		this.connection = connection;
		this.accept_Encoding = accept_Encoding;
		this.cache_Control = cache_Control;
	}

	
	
	
	public HttpAttribute(String user_Agent, String accept, String accept_Language, String accept_Charset,
			int connectTimeout, String connection, String accept_Encoding, String cache_Control,
			String result_Charset) {
		super();
		this.user_Agent = user_Agent;
		this.accept = accept;
		this.accept_Language = accept_Language;
		this.accept_Charset = accept_Charset;
		this.connectTimeout = connectTimeout;
		this.connection = connection;
		this.accept_Encoding = accept_Encoding;
		this.cache_Control = cache_Control;
		this.result_Charset = result_Charset;
	}

	public String getResult_Charset() {
		return result_Charset;
	}

	public void setResult_Charset(String result_Charset) {
		this.result_Charset = result_Charset;
	}

	public String getConnection() {
		return connection;
	}

	public void setConnection(String connection) {
		this.connection = connection;
	}

	public String getAccept_Encoding() {
		return accept_Encoding;
	}

	public void setAccept_Encoding(String accept_Encoding) {
		this.accept_Encoding = accept_Encoding;
	}

	public String getCache_Control() {
		return cache_Control;
	}

	public void setCache_Control(String cache_Control) {
		this.cache_Control = cache_Control;
	}

	public static HttpAttribute.Builder custom() {
		return new Builder();
	}
	
	public String getUser_Agent() {
		return user_Agent;
	}

	public void setUser_Agent(String user_Agent) {
		this.user_Agent = user_Agent;
	}

	public String getAccept() {
		return accept;
	}

	public void setAccept(String accept) {
		this.accept = accept;
	}

	public String getAccept_Language() {
		return accept_Language;
	}

	public void setAccept_Language(String accept_Language) {
		this.accept_Language = accept_Language;
	}

	public String getAccept_Charset() {
		return accept_Charset;
	}

	public void setAccept_Charset(String accept_Charset) {
		this.accept_Charset = accept_Charset;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

	public static class Builder {
		private String user_Agent;

		private String accept;

		private String accept_Language;

		private String accept_Charset;

		private int connectTimeout;
		
		private String connection;
		
		private String accept_Encoding;
		
		private String cache_Control;

		private String result_Charset;
		
		Builder() {
			super();
			// 设置默认参数
		}

		public HttpAttribute build() {
			return new HttpAttribute(user_Agent, accept, accept_Language, accept_Charset, connectTimeout,connection,accept_Encoding,cache_Control,result_Charset);
		}

		public Builder setUser_Agent(String user_Agent) {
			this.user_Agent = user_Agent;
			return this;
		}

		public Builder setAccept(String accept) {
			this.accept = accept;
			return this;
		}

		public Builder setAccept_Language(String accept_Language) {
			this.accept_Language = accept_Language;
			return this;
		}

		public Builder setAccept_Charset(String accept_Charset) {
			this.accept_Charset = accept_Charset;
			return this;
		}

		public Builder setConnectTimeout(int connectTimeout) {
			this.connectTimeout = connectTimeout;
			return this;
		}

		public Builder setConnection(String connection) {
			this.connection = connection;
			return this;
		}

		public Builder setAccept_Encoding(String accept_Encoding) {
			this.accept_Encoding = accept_Encoding;
			return this;
		}

		public Builder setCache_Control(String cache_Control) {
			this.cache_Control = cache_Control;
			return this;
		}

		public Builder setResult_Charset(String result_Charset) {
			this.result_Charset = result_Charset;
			return this;
		}
		
		

	}
}
