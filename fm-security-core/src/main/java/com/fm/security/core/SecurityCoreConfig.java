package com.fm.security.core;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import com.fm.security.core.properties.SecurityProperties;

//配置类
//让读取器生效SecurityProperties.class
@Configuration
@EnableConfigurationProperties(SecurityProperties.class)
public class SecurityCoreConfig {

}
