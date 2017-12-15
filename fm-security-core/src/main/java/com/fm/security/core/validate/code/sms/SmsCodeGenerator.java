package com.fm.security.core.validate.code.sms;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import com.fm.security.core.properties.SecurityProperties;
import com.fm.security.core.validate.code.ValidateCode;
import com.fm.security.core.validate.code.ValidateCodeGenerator;

/**
 * 短信验证码生成器
 * @author yuxiang
 *
 */
@Component
public class SmsCodeGenerator implements ValidateCodeGenerator {

	@Autowired
    private SecurityProperties securityProperties;
		
	@Override
	public ValidateCode generate(ServletWebRequest request) {
		String code=RandomStringUtils.randomNumeric(securityProperties.getCode().getSms().getLenght());
	    return new ValidateCode(code, securityProperties.getCode().getSms().getExpireIn());
	
	}
	

		


}
