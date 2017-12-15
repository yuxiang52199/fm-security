/**
 * 
 */
package com.fm.security.browser.authentication;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fm.security.browser.support.SimpleResponse;
import com.fm.security.core.properties.LoginType;
import com.fm.security.core.properties.SecurityProperties;

/**
 * @author yuxiang
 * 登录失败处理
 * implements AuthenticationFailureHandler 
 */
@Component("fmAuthenticationFailureHandler")
public class FmAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler   {

	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private SecurityProperties securityProperties;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
        
		logger.info("登录失败");	
		if(LoginType.JSON.equals(securityProperties.getBrowser().getLoginType()))//判断登录结果处理类型
		{
			//JSON 类型
			//返回500 服务器内部错误
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			response.setContentType("application/json;charset=UTF-8");
			response.getWriter().write(objectMapper.writeValueAsString(new SimpleResponse(exception.getMessage())));
			
		}else{
			//指定重定向到失败页面
			super.onAuthenticationFailure(request, response, exception);
		}

	}

}
