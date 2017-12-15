/**
 * 
 */
package com.fm.security.core.properties;

/**
 * @author yuxiang
 * 验证码属性配置
 */
public class ValidateCodeProperties {
	//图片验证码
	private ImageCodeProperties image = new ImageCodeProperties();
	//短信验证码
	private SmsCodeProperties sms = new SmsCodeProperties();
	
	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}

	public ImageCodeProperties getImage() {
		return image;
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}



}
