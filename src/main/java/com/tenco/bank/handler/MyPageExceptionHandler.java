package com.tenco.bank.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.tenco.bank.handler.exception.CustomPageException;
/**
 * View 랜더링을 위해 ModelView 객체를 반환하도록 설정되어 있다.
 * 예외처리 Page를 리턴할 때 사용 한다.
 */
@ControllerAdvice
public class MyPageExceptionHandler {
	// CustomPageException 이 발생되면 이 함수를 동작 시키라
	@ExceptionHandler(CustomPageException.class)
	public ModelAndView handlerRuntimeException(CustomPageException e) {
		System.out.println("여기에러확인");
		ModelAndView modelAndView = new ModelAndView("errorPage"); // 동일하게 suffix/prefix 탐
		modelAndView.addObject("statusCode", HttpStatus.NOT_FOUND.value());
		modelAndView.addObject("message", e.getMessage());
		
		return modelAndView; //페이지 반환 + 데이터 내려줌
	}
}
