package com.fm.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * @author Administrator
 * 验证码生成器接口
 */
public interface ValidateCodeGenerator {
	
	ValidateCode generate(ServletWebRequest request);
	
}
