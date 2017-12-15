/**
 * 
 */
package com.fm.security.core.validate.code;

import org.springframework.security.core.AuthenticationException;

/**
 * @author Administrator
 * 自定义验证码异常
 */
public class ValidateCodeException extends AuthenticationException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4950916428588365975L;

	public ValidateCodeException(String msg) {
		super(msg);
		// TODO Auto-generated constructor stub
	}



}
