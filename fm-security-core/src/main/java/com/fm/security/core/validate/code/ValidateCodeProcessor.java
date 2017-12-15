package com.fm.security.core.validate.code;

import org.springframework.web.context.request.ServletWebRequest;

/**
 * 验证码处理器接口
 * @author yuxiang
 *
 */
public interface ValidateCodeProcessor {
	

   /**
	 * 验证码放入session时的前缀
	 */
	String SESSION_KEY_PREFIX = "SESSION_KEY_FOR_CODE_";
   
   /**
     *  创建
	 * @param request
	 * @throws Exception
	 */
	void create(ServletWebRequest request) throws Exception ;
}