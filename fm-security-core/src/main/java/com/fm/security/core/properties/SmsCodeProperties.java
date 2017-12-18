package com.fm.security.core.properties;

/**
 * 短信验证码属性
 * @author yuxianbg
 *
 */
public class SmsCodeProperties {

	
    private int lenght=6;
	
	private int expireIn=60;
	
	private String url;

	public int getLenght() {
		return lenght;
	}

	public void setLenght(int lenght) {
		this.lenght = lenght;
	}

	public int getExpireIn() {
		return expireIn;
	}
	
	public void setExpireIn(int expireIn) {
		this.expireIn = expireIn;
	}
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
