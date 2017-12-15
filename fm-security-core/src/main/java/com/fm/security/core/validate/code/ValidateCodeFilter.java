/**
 * 
 */
package com.fm.security.core.validate.code;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fm.security.core.properties.SecurityProperties;
import com.fm.security.core.validate.code.image.ImageCode;



/**
 * 
 * @author yuxiang
 * 自定义验证码过滤器
 * 在Username Password Authentication Filter 前进行拦截过滤
 * 
 */
@Component("validateCondeFilter")
public class ValidateCodeFilter extends OncePerRequestFilter implements InitializingBean {
	
	/**
	 * 验证码失败处理器
	 */
	@Autowired
	private AuthenticationFailureHandler authenticationFailureHandler;
	
	/**
	 * 系统配置信息
	 */
	@Autowired
	private SecurityProperties securityProperties;
	
	/**
	 * 系统中校验码处理器
	 */
//	@Autowired
//	private ValidateCodeProcessorHolder validateCodeProcessorHolder;
	
	/**
	 * 系统中所有需要校验码的url
	 */
	@Autowired
	private Map<String, ValidateCodeType> urlMap=new HashMap<>();
	
	/**
	 * 验证请求url与系统配置的url是否匹配的工具类
	 */
	@Autowired
	private AntPathMatcher pathMatcher=new AntPathMatcher();
	
	/* (non-Javadoc)
	 * 初始化要拦截的url配置信息
	 */
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();		
		String[] configUrls=StringUtils.split(securityProperties.getCode().getImage().getUrl(),"");
		if(configUrls!=null){
			for(String configUrl:configUrls){
				urls.add(configUrl);
			}
		}
		urls.add("/authentication/form");
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
//		StringUtils.equals("/authentication/form", request.getRequestURI())
//		&&StringUtils.equalsIgnoreCase("post",request.getMethod())
		boolean action = false;
		for(String url :urls){//匹配拦截地址
			if(pathMatcher.match(url, request.getRequestURI())){
				action = true;
			}
		}
		if(action){
			try{
				//校验验证码
				validate(new ServletWebRequest(request));
			}catch (ValidateCodeException e) {
				// TODO: handle exception
				authenticationFailureHandler.onAuthenticationFailure(request, response, e);
				return ;
			}		
		}
		filterChain.doFilter(request, response);
		
	}
	
	
	private SessionStrategy sessionStrategy=new HttpSessionSessionStrategy();
	
	
	
	private Set<String> urls = new HashSet<>();

	
	
	public Set<String> getUrls() {
		return urls;
	}

	public void setUrls(Set<String> urls) {
		this.urls = urls;
	}

	public SecurityProperties getSecurityProperties() {
		return securityProperties;
	}

	public void setSecurityProperties(SecurityProperties securityProperties) {
		this.securityProperties = securityProperties;
	}
	public AuthenticationFailureHandler getAuthenticationFailureHandler() {
		return authenticationFailureHandler;
	}

	public void setAuthenticationFailureHandler(AuthenticationFailureHandler authenticationFailureHandler) {
		this.authenticationFailureHandler = authenticationFailureHandler;
	}

	

	

	private void validate(ServletWebRequest request)throws ServletRequestBindingException {
		
		// 获取session中的 验证码
		ImageCode codeInSession =(ImageCode)sessionStrategy.getAttribute(request, ValidateCodeController.SESSION_KEY+"IMAGE");
		
		//获取页面文本框里用户输入的验证码
		String codeInRequest = ServletRequestUtils.getStringParameter(request.getRequest(), "imageCode");
		
		if(StringUtils.isBlank(codeInRequest)){
			throw new ValidateCodeException("验证码的值不能为空");
		}
		
		if(codeInSession==null){
			throw new ValidateCodeException("验证码不存在");
		}
		
		if(codeInSession.isExpried()){
			sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY+"IMAGE");
			throw new ValidateCodeException("验证码已过期");
		}
		
		if(!StringUtils.equals(codeInSession.getCode(), codeInRequest)){
			throw new ValidateCodeException("验证码不匹配");
		}
		
		sessionStrategy.removeAttribute(request, ValidateCodeController.SESSION_KEY+"IMAGE");
		
	}
}
