package com.tenco.bank.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.tenco.bank.dto.KakaoProfile;
import com.tenco.bank.dto.OAuthToken;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.service.UserService;
import com.tenco.bank.utils.Define;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
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
	/**
	 * 회원 가입 요청
	 * @param dto
	 * @return 로그인 페이지(/sign-in)
	 */
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
		
		userService.createUser(dto); // 서비스에서 Exception Handler 작동 
		// 예외 발생 하지 않으면 성공적으로 return

		return "redirect:/user/sign-in";
	}
	
	/**
	 * 로그인 페이지 요청
	 * @return
	 */
	// http://localhost:80/user/sign-in
	@GetMapping("/sign-in")
	public String signInPage() {
		return "user/signIn";
	}
	
	/**
	 * 로그인 요청 처리 
	 * @param SignInFormDto
	 * @return account/list.jsp
	 */
	@PostMapping("/sign-in")
	public String signInProc(SignInFormDto dto) {
		
		// 1. 유효성 (원래 인증 먼저 그 후에 유효성)
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		// 서비스 호출 
		User user =  userService.readUser(dto);
		httpSession.setAttribute(Define.PRINCIPAL, user); //principal 이란 key 값을 지님

		return "redirect:/account/list";
	}
	
	// 로그아웃 기능
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
	
	// http://localhost:80/user/kakao-callback?code="xxxxxxx"
	@GetMapping("/kakao-callback")
	// @ResponseBody // <- 데이터를 반환 : 테스트 용
	public String kakaoCallback(@RequestParam String code) {
		
		// POST 방식, Header 구성, body 구성
		RestTemplate rt1 = new RestTemplate();
		// 헤더 구성
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		// body 구성 
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "72919ee7c8ec0f967c858cc03998bbc3");
		params.add("redirect_uri", "http://localhost:80/user/kakao-callback");
		params.add("code", code);
		
		// 헤더 + 바디 결합
		HttpEntity<MultiValueMap<String, String>> reqMsg
		 = new HttpEntity<>(params,headers1);
		
		ResponseEntity<OAuthToken> response =  rt1.exchange("https://kauth.kakao.com/oauth/token",
												HttpMethod.POST, reqMsg, OAuthToken.class);
		System.out.println(response.getBody().getAccessToken());
		// 다시 요청 -- 인증 토큰 -- 사용자 정보 요청
		// Rt 만들어 요청
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " +response.getBody().getAccessToken()) ;
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8") ;
		// 바디 X
		
		// 결합
		HttpEntity<MultiValueMap<String, String>> kakaoInfo = new HttpEntity<>(headers2);
		
		// 요청
		ResponseEntity<KakaoProfile> response2 =  rt2.exchange("https://kapi.kakao.com/v2/user/me",
												HttpMethod.POST, kakaoInfo, KakaoProfile.class);
		
	
		KakaoProfile kakaoProfile = response2.getBody();
		// 최초 사용자 판단 여부 -- 사용자 username 존재 여부 확인
		// 우리 사이트 --> 카카오 
		SignUpFormDto dto = SignUpFormDto.builder()
							.username("OAuth_"+ kakaoProfile.getProperties().getNickname())
							.fullname("Kakao")
							.password("asd1234")
							.build();
		
		User oldUser =  userService.readUserByUsername(dto.getUsername());
		if(oldUser == null) {
			userService.createUser(dto);
			//////////////////////////////
			// oldUser라면 해당하는 정보를 가져와야한다.
			oldUser = new User();
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
		}
		oldUser.setPassword(null);
		// 로그인 처리
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		// 단 최소 요청 사용자라면 회원 후 로그인 처리 
		
		return "redirect:/account/list";
	
		

	}
	


}
