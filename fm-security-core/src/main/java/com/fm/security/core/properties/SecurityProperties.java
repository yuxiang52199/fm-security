package com.fm.security.core.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author yuxiang
 * Security相关配置属性读取器
 * 读取application.oproperties 里的自定义属性
 */
@ConfigurationProperties(prefix="fm.security")
public class SecurityProperties {
	
	//浏览器授权属性
	private BrowserProperties browser = new BrowserProperties();
	
	//验证码属性
	private ValidateCodeProperties code = new ValidateCodeProperties();

	public BrowserProperties getBrowser() {
		return browser;
	}

	public void setBrowser(BrowserProperties browser) {
		this.browser = browser;
	}
	
	public ValidateCodeProperties getCode() {
		return code;
	}

	public void setCode(ValidateCodeProperties code) {
		this.code = code;
	}



}
