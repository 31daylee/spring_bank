package com.tenco.bank.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test")
public class TestController {

	// 주소설계
	// http://localhost:80/test/main
	@GetMapping("/main")
	public String mainPage() {
		System.out.print("main test");
		// 인증검사
		// 유효성 검사
		// 뷰 지졸브 ---> 해당하는 파일 찾아 (data)
		// return "/WEB-INF/view/layout/main.jsp"
		// prefix : /WEB-INF/view/
		// suffix : .jsp
		return "layout/main";
	}

}
