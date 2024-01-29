package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	// 회원가입
	// 화면 반환
	// http://localhost:80/user/sign-up
	/**
	 * 회원 가입 페이지 요청
	 * @return signUp.jsp 파일 리턴
	 */
	@GetMapping("/sign-up")
	public String signUpPage() {
		return "user/signUp";
	}
	
	// 회원 가입 요청 처리
	// 주소 설계 http://localhost:80/user/sign-up
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDto dto) { // 페이지 리턴하기에 String
		
		// 1. 인증검사 X
		// 2. 유효성 검사
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		
		// TODO:서비스로 넘길 예정
		userService.createUser(dto); // 서비스에서 Exception Handler 작동 
		// 예외 발생 하지 않으면 성공적으로 return
		
		// TODO: 로그인 페이지로 변경 예정
		return "redirect:/user/sign-up";
	}

}
