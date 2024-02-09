package com.tenco.bank.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.tenco.bank.handler.AuthInterceptor;

// @Configuration : 스프링 부트 설정 클래스
@Configuration // IoC 대상 : 2개 이상에 IoC(Bean) 사용
public class WebMvcConfig implements WebMvcConfigurer{

	@Autowired // DI
	private AuthInterceptor authInterceptor;
	
	@Autowired
	private ResourceLoader resourceLoader;

	
	@Value("${filePath.files}")
	private String resourcePath;


	@Override
	public void addInterceptors(InterceptorRegistry registry) { 
		registry.addInterceptor(authInterceptor)
				.addPathPatterns("/account/**")
				.addPathPatterns("/auth/**");
				
	}
	@Bean 
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/bank/upload/**")
				.addResourceLocations(resourceLoader.getResource(resourcePath));
	}
	
}
