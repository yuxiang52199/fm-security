/**
 * 
 */
package com.fm.security.core.authention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

/**
 * @author yuxiang
 *
 */
public class AbstractChannelSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private AuthenticationSuccessHandler fmAuthenticationSuccessHandler;
	
	@Autowired
	private AuthenticationFailureHandler fmAuthenticationFailureHandler;
		
	@Override
	public void configure(HttpSecurity http) throws Exception {
		
		http.formLogin()
		    .loginPage("")
		    .loginProcessingUrl("")
		    .successHandler(fmAuthenticationSuccessHandler)
		    .failureHandler(fmAuthenticationFailureHandler);
		
		
	}

}
