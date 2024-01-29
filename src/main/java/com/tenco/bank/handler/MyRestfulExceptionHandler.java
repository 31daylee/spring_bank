package com.tenco.bank.handler;

import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tenco.bank.handler.exception.CustomRestfulException;

@Order(1)
@RestControllerAdvice
public class MyRestfulExceptionHandler{
	
	// 모든 예외 클래스 설정 - > 개발시 모든 예외를 확인할 수 없기에 만들어 두면 좋음
	@ExceptionHandler(Exception.class)
	public void exception(Exception e) {
		System.out.println("------------");
		System.out.println(e.getClass().getName());
		System.out.println(e.getMessage());
		System.out.println("------------");
		
	}
	
	// 만약 restful exception(하위)이 오버라이딩 되어있으면 먼저 타고
	// 그 후 Exception.class(상위)를 타게됨
	@ExceptionHandler(CustomRestfulException.class)
	public String basicException(CustomRestfulException e) {
		StringBuffer sb = new StringBuffer();
		sb.append("<script>");
		sb.append("alert('"+e.getMessage() +"');");		
		sb.append("window.history.back();");	// 이전 페이지로 돌아가기	
		sb.append("</script>");
		return sb.toString();
	}
}
