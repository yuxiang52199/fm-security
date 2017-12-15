/**
 * 
 */
package com.fm.security.core.validate.code.sms;

/**
 * 默认短信验证码发送器
 * @author yuxiang
 *
 */
public class DefaultSmsCodeSender implements  SmsCodeSender{

	@Override
	public void send(String mobile, String code) {
		// TODO Auto-generated method stub\
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
