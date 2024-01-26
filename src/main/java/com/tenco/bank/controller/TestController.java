package com.tenco.bank.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.handler.exception.CustomRestfulException;

@Controller
@RequestMapping("/test")
public class TestController {

	// 주소설계
	// http://localhost:80/test/main
	@GetMapping("/main")
	public void mainPage(){
		// 인증검사
		// 유효성 검사
		// 뷰 지졸브 ---> 해당하는 파일 찾아 (data)
		// return "/WEB-INF/view/layout/main.jsp"
		// prefix : /WEB-INF/view/
		// suffix : .jsp
		// 예외 발생
		throw new CustomRestfulException("페이지가 없네요", HttpStatus.NOT_FOUND);
		//return "layout/main";
		//throw new ArithmeticException(); -> MyRestfulException의 exception 메서드로 빠짐
	
	}

}
