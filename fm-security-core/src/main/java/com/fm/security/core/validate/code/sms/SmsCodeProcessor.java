/**
 * 
 */
package com.fm.security.core.validate.code.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import com.fm.security.core.validate.code.ValidateCode;
import com.fm.security.core.validate.code.impl.AbstractValidateCodeProcessor;

/**
 * 短信验证码处理器
 * @author yuxiang
 *
 */
@Component("smsCodeProcessor")
public class SmsCodeProcessor extends AbstractValidateCodeProcessor<ValidateCode> {

	/**
	 * 短信验证码发送器
	 */
	@Autowired
	private SmsCodeSender smsCodeSender;
	
	/* (non-Javadoc)
	 * @see com.fm.security.core.validate.code.impl.AbstractValidateCodeProcessor#send(org.springframework.web.context.request.ServletWebRequest, com.fm.security.core.validate.code.ValidateCode)
	 */
	@Override
	protected void send(ServletWebRequest request, ValidateCode validateCode) throws Exception {
//		String paramName = SecurityConstants.DEFAULT_PARAMETER_NAME_MOBILE;
		String mobile = ServletRequestUtils.getRequiredStringParameter(request.getRequest(), "mobile");
		smsCodeSender.send(mobile, validateCode.getCode());
	}


}
