package com.tenco.bank.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.tenco.bank.dto.OAuthToken;
import com.tenco.bank.dto.ProfileUpdateFormDto;
import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.dto.oauth.google.GoogleProfile;
import com.tenco.bank.dto.oauth.kakao.KakaoProfile;
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
	
	/**
	 * 로그아웃 기능 
	 * @return user/sign-in 로그인 페이지
	 */
	@GetMapping("/logout")
	public String logout() {
		httpSession.invalidate();
		return "redirect:/user/sign-in";
	}
	/**
	 * 계정 페이지 요청
	 * @return 계정 
	 */
	@GetMapping("/profile")
	public String profile() {
		return "user/profile";
	}
	
	@PostMapping("/profile")
	public String setProfile(ProfileUpdateFormDto dto) {
		// 파일 업로드 
		MultipartFile file = dto.getCustomFile();
		System.out.println("file" + file.getOriginalFilename());
		if(file.isEmpty() == false) {
			// 사용자가 이미지를 업로드 했다면 기능 구현 
			// 파일 사이즈 체크 
			// 20MB 
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일 크기는 20MB 이상 클 수 없습니다", HttpStatus.BAD_REQUEST);
			}
			
			// 서버 컴퓨터에 파일 넣을 디렉토리가 있는지 검사 
			String saveDirectory = Define.UPLOAD_FILE_DIRECTORY;
			// 폴더가 없다면 오류 발생(파일 생성시) 
			File dir = new File(saveDirectory);
			if(dir.exists() == false) {
				dir.mkdir(); // 폴더가 없스면 폴더 생성 
			}
			
			// 파일 이름 (중복 처리 예방) 
			UUID uuid = UUID.randomUUID(); 
			String fileName = uuid + "_" + file.getOriginalFilename();
			System.out.println("fileName : " + fileName);
			// C:\\wok_spring\\upload\ab.png
			String uploadPath 
			= Define.UPLOAD_FILE_DIRECTORY + File.separator + fileName;
			File destination = new File(uploadPath);
			
			try {
				file.transferTo(destination);
			} catch (IllegalStateException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}
		
		return "redirect:/user/sign-in";
	}
	
	/**
	 * 카카오 소셜 로그인
	 * @param code
	 * @return 계좌 목록
	 */
	@GetMapping("/kakao-callback")
	public String kakaoCallback(@RequestParam String code) {
		
		RestTemplate rt1 = new RestTemplate();
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "72919ee7c8ec0f967c858cc03998bbc3");
		params.add("redirect_uri", "http://localhost:80/user/kakao-callback");
		params.add("code", code);
		
		HttpEntity<MultiValueMap<String, String>> reqMsg
		 = new HttpEntity<>(params,headers1);
		
		ResponseEntity<OAuthToken> response =  rt1.exchange("https://kauth.kakao.com/oauth/token",
												HttpMethod.POST, reqMsg, OAuthToken.class);
		System.out.println(response.getBody().getAccessToken());
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization","Bearer " +response.getBody().getAccessToken()) ;
		headers2.add("Content-type","application/x-www-form-urlencoded;charset=utf-8") ;
		HttpEntity<MultiValueMap<String, String>> kakaoInfo = new HttpEntity<>(headers2);
		
		ResponseEntity<KakaoProfile> response2 =  rt2.exchange("https://kapi.kakao.com/v2/user/me",
												HttpMethod.POST, kakaoInfo, KakaoProfile.class);
		
	
		KakaoProfile kakaoProfile = response2.getBody();
		SignUpFormDto dto = SignUpFormDto.builder()
							.username("Kakao_"+ kakaoProfile.getProperties().getNickname())
							.fullname("Kakao")
							.password("asd1234")
							.build();
		
		User oldUser =  userService.readUserByUsername(dto.getUsername());
		if(oldUser == null) {
			userService.createUser(dto);
			oldUser = new User();
			oldUser.setUsername(dto.getUsername());
			oldUser.setFullname(dto.getFullname());
		}
		oldUser.setPassword(null);
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		
		return "redirect:/account/list";

	}
	/**
	 * 구글 소셜 로그인
	 * @param accessCode
	 * @return 계좌 목록
	 */
	@GetMapping("/google-callback")
	public String googleCallback(@RequestParam("code") String accessCode) {
		
		RestTemplate rt1 = new RestTemplate();
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("code", accessCode);
		params.add("client_id", "78534889326-bd77l69smot5kpjddheeu5nsjrcbf8ea.apps.googleusercontent.com");
		params.add("client_secret", "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");
		params.add("redirect_uri", "http://localhost:80/user/google-callback");
		params.add("grant_type", "authorization_code");

		
		ResponseEntity<OAuthToken> response1 =  rt1.postForEntity("https://oauth2.googleapis.com/token",
				params, OAuthToken.class);
		
		
		// 액세스 토큰 -> 사용자 정보
		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		
		headers2.add("Authorization","Bearer " +response1.getBody().getAccessToken()) ;
		HttpEntity<MultiValueMap<String, String>> googleInfo = new HttpEntity<>(headers2);
		ResponseEntity<GoogleProfile> response2 =  rt2.exchange("https://www.googleapis.com/userinfo/v2/me",
													HttpMethod.GET, googleInfo, GoogleProfile.class);
		
		System.out.println(response2.getBody());
		GoogleProfile googleProfile = response2.getBody();
		System.out.println(response2.getBody());
		SignUpFormDto dto = SignUpFormDto.builder()
						                .username("Google_"+ googleProfile.getName())
						                .fullname("Google")
						                .password("1111")
						                .build();
		
		User oldUser =  userService.readUserByUsername(dto.getUsername());
	    if(oldUser == null) {
	        userService.createUser(dto);
	        oldUser = new User();
	        oldUser.setUsername(dto.getUsername());
	        oldUser.setFullname(dto.getFullname());
	    }
	    oldUser.setPassword(null); 
	    
	    httpSession.setAttribute(Define.PRINCIPAL, oldUser);
	  
	    return "redirect:/account/list";
		
	}
	


}
