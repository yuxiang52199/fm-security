package com.fm.security.browser;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fm.security.browser.support.SimpleResponse;
import com.fm.security.core.properties.SecurityProperties;

/**
 * @author yuxiang
 * 接收浏览器访问无权限时的跳转处理
 */
@RestController
public class BrowserSecurityController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	//缓存当前请求到session里去
	private RequestCache requeseCache = new HttpSessionRequestCache();
	//重定向模块
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	//security相关属性配置
	@Autowired
	private SecurityProperties  securityProperties;
	
	/**
	 * 需要身份认证时,跳转到这里
	 * */
	@RequestMapping("/authentication/require")
	@ResponseStatus(code=HttpStatus.UNAUTHORIZED)
	public SimpleResponse requireAuthentication(HttpServletRequest request,HttpServletResponse response) throws IOException{
		SavedRequest savedRequest = requeseCache.getRequest(request, response);
		if(savedRequest!=null){
			//获取引发跳转请求url		
			String targetUrl = savedRequest.getRedirectUrl();
			logger.info("引发跳转的请求是"+targetUrl);
			if(StringUtils.endsWithIgnoreCase(targetUrl, ".html")){
				//如果targetUrl包含.html 则重定向到登录页
				redirectStrategy.sendRedirect(request, response, securityProperties.getBrowser().getLoginPage());
			}

		}		
		return new SimpleResponse("访问的服务需要身份认证，请引导用户到登录页");
	}

}
