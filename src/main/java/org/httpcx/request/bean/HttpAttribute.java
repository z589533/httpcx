package org.httpcx.request.bean;

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
	private String user_Agent="Mozilla/5.0 (Windows NT 6.1; WOW64; rv:46.0) Gecko/20100101 Firefox/46.0";

	private String accept="*/*";

	private String accept_Language="zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3";

	private String accept_Charset="ISO-8859-1,utf-8,UTF-8,gbk,gb2312;q=0.7,*;q=0.7";

	private int connectTimeout = 5000;

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

		Builder() {
			super();
			// 设置默认参数
		}

		public HttpAttribute build() {
			return new HttpAttribute(user_Agent, accept, accept_Language, accept_Charset, connectTimeout);
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

	}
}