package com.fm.security.browser;



import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.fm.security.browser.authentication.FmAuthenticationFailureHandler;
import com.fm.security.browser.authentication.FmAuthenticationSuccessHandler;
import com.fm.security.core.authentication.mobile.SmsCodeAuthenticationSecurityConfig;
import com.fm.security.core.properties.SecurityProperties;
import com.fm.security.core.validate.code.SmsCodeFilter;
import com.fm.security.core.validate.code.ValidateCodeFilter;

/**
 * @author yuxiang
 * 浏览器SecurityConfig相关配置
 */
@Configuration
public class BrowserSecurityConfig extends WebSecurityConfigurerAdapter {
    //自定义security配置属性
	@Autowired
	private SecurityProperties securityProperties;
	//登录成功处理器
	@Autowired
	private FmAuthenticationSuccessHandler fmAuthenticationSuccessHandler;
	//登录失败处理器
	@Autowired
	private FmAuthenticationFailureHandler fmAuthenticationFailureHandler;
	//用户服务
	@Autowired 
	private UserDetailsService userDetailsService;
	
	//创建密码加密方式
	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private DataSource dataSource;
	/**
	 * 读写数据库
	 * @return
	 */
	@Bean
	public PersistentTokenRepository persistentTokenRepository(){
		JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
		tokenRepository.setDataSource(dataSource);	
		//自动创建表
		//tokenRepository.setCreateTableOnStartup(true);	
		return tokenRepository;		
	}
	
	@Autowired
	SmsCodeAuthenticationSecurityConfig smsCodeAuthenticationSecurityConfig;
	/*
	 * 配置浏览器授权
	 * 
	 * */
	@Override
	protected void configure(HttpSecurity http) throws Exception {		
		//初始化验证码拦截器
		ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
		//设置验证失败处理器
		validateCodeFilter.setAuthenticationFailureHandler(fmAuthenticationFailureHandler);
		validateCodeFilter.setSecurityProperties(securityProperties);
		validateCodeFilter.afterPropertiesSet();
		
		//初始化验证码拦截器
		SmsCodeFilter smsCodeFilter = new SmsCodeFilter();
		//设置验证失败处理器
		smsCodeFilter.setAuthenticationFailureHandler(fmAuthenticationFailureHandler);
		smsCodeFilter.setSecurityProperties(securityProperties);
		smsCodeFilter.afterPropertiesSet();
		
		http.addFilterBefore(smsCodeFilter, UsernamePasswordAuthenticationFilter.class)
		.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
		.formLogin()//表单登录
		//.loginPage("/fm-signIn.html")
		.loginPage("/authentication/require")//无权限时跳转登录
		.loginProcessingUrl("/authentication/form")//登录拦截地址,拦截成功后跳转MyUserDetailsService 服务
		.successHandler(fmAuthenticationSuccessHandler)//处理登录成功
		.failureHandler(fmAuthenticationFailureHandler)
		.and()
		.rememberMe()
		.tokenRepository(persistentTokenRepository())
		.tokenValiditySeconds(securityProperties.getBrowser().getRemeberSeconds())
		.userDetailsService(userDetailsService)
		.and()
		.authorizeRequests()//对请求进行授权
		.antMatchers("/authentication/require",securityProperties.getBrowser().getLoginPage()
				,"/code/*").permitAll()//匹配器 当访问此登录URL的时候 ，不需要身份认证
		.anyRequest()//所有请求
		.authenticated()//都需要身份认证
		.and()
		.csrf().disable()
		.apply(smsCodeAuthenticationSecurityConfig)
		;//把跨站防护功能取消
	}
	

}
