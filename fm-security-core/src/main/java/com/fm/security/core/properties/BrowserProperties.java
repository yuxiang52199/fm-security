package com.fm.security.core.properties;


/**
 * @author yuxiang
 * 浏览器相关配置
 */
public class BrowserProperties {
	//登录页面配置
	private String loginPage="/fm-signIn.html";
	
	private LoginType loginType = LoginType.JSON;
	//token过期秒数
	private int remeberSeconds=3600;

	public int getRemeberSeconds() {
		return remeberSeconds;
	}

	public void setRemeberSeconds(int remeberSeconds) {
		this.remeberSeconds = remeberSeconds;
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public String getLoginPage() {
		return loginPage;
	}

	public void setLoginPage(String loginPage) {
		this.loginPage = loginPage;
	}

}
