package com.fm.security.core.validate.code.sms;

/**
 * 短信验证码发送器接口
 * 如实现自定义短信验证码发送 只需实现此接口 重写send方法
 * @author yuxiang
 * 
 */
public interface SmsCodeSender {
	
	void send(String mobile,String code);

}
