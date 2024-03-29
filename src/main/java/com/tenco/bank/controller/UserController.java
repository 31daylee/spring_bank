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
import org.springframework.web.bind.annotation.DeleteMapping;
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
import com.tenco.bank.dto.oauth.naver.NaverProfile;
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
	@PostMapping("/sign-up")
	public String signProc(SignUpFormDto dto) {
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		if(dto.getFullname() == null || dto.getFullname().isEmpty()) {
			throw new CustomRestfulException("fullname을 입력 하세요", HttpStatus.BAD_REQUEST);
		}
		
		userService.createUser(dto); 

		return "redirect:/user/sign-in";
	}
	
	/**
	 * 로그인 페이지 요청
	 * @return
	 */
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
		
		if(dto.getUsername() == null || dto.getUsername().isEmpty()) {
			throw new CustomRestfulException("username을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		if(dto.getPassword() == null || dto.getPassword().isEmpty()) {
			throw new CustomRestfulException("password을 입력하시오", HttpStatus.BAD_REQUEST);
		}
		User user =  userService.readUser(dto);
		httpSession.setAttribute(Define.PRINCIPAL, user);

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
		User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		MultipartFile file = dto.getCustomFile();
		if(file.isEmpty() == false) {
			if(file.getSize() > Define.MAX_FILE_SIZE) {
				throw new CustomRestfulException("파일 크기는 20MB 이상 클 수 없습니다", HttpStatus.BAD_REQUEST);
			}
			
			String saveDirectory = Define.UPLOAD_FILE_DIRECTORY;
			File dir = new File(saveDirectory);
			if(dir.exists() == false) {
				dir.mkdir(); 
			}
			
			UUID uuid = UUID.randomUUID();
			String fileName = uuid + "_" + file.getOriginalFilename();
			
			String uploadPath 
			= Define.UPLOAD_FILE_DIRECTORY + File.separator + fileName;
			
			File destination = new File(uploadPath);
			
			try {
				file.transferTo(destination);
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
			
			dto.setOriginFileName(file.getOriginalFilename());
			dto.setUploadFileName(fileName);
			
		}
		userService.updateProfile(dto, principal.getId());
		
		return "redirect:/user/profile";
	}

    @PostMapping("/deleteProfileImage")
    public ResponseEntity<String> deleteProfileImage(ProfileUpdateFormDto dto) {
    	User principal = (User)httpSession.getAttribute(Define.PRINCIPAL);
		
    	if(dto.getOriginFileName() != null || dto.getOriginFileName() != null) {
    		dto.setOriginFileName(null);
    		dto.setUploadFileName(null);
    		userService.updateProfile(dto, principal.getId());
    	}
	
        return ResponseEntity.ok("Profile image deleted successfully");
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
							.fullname(kakaoProfile.getProperties().getNickname())
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
						                .fullname(googleProfile.getName())
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
	
	/**
	 * 네이버 소셜 로그인
	 * @param code
	 * @return 계좌 목록
	 */
	@GetMapping("/naver-callback")
	public String naverCallback(@RequestParam String code) {
		
		RestTemplate rt1 = new RestTemplate();
		HttpHeaders headers1 = new HttpHeaders();
		headers1.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "uCYzKaCXfDqcouuqyd0Z");
		params.add("client_secret", "VtbT8UPei0");
		params.add("code", code);
		params.add("state", "STATE_STRING");
		
		HttpEntity<MultiValueMap<String, String>> reqMsg 
			= new HttpEntity<>(params, headers1);
		
		ResponseEntity<OAuthToken> response
			= rt1.exchange("https://nid.naver.com/oauth2.0/token",
						HttpMethod.POST,
						reqMsg,
						OAuthToken.class);

		RestTemplate rt2 = new RestTemplate();
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+ response.getBody().getAccessToken());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		HttpEntity<MultiValueMap<String, String>> naverInfo
			= new HttpEntity<>(headers2);
		
		ResponseEntity<NaverProfile> response2
			= rt2.exchange("https://openapi.naver.com/v1/nid/me",
					HttpMethod.POST,
					naverInfo,
					NaverProfile.class);
		
		NaverProfile naverProfile = response2.getBody();

		
		SignUpFormDto dto = SignUpFormDto.builder()
								 		.username("Naver_"+ naverProfile.getResponse().getNickname())
						                .fullname(naverProfile.getResponse().getName())
						                .email(naverProfile.getResponse().getEmail())
						                .password("1111")
						                .build();
		log.info("====== naver dto {} =====", dto);

		User oldUser = userService.readUserByUsername(dto.getUsername());
		
	    if(oldUser == null) {
	        userService.createUser(dto);
	        oldUser = new User();
	        oldUser.setUsername(dto.getUsername());
	        oldUser.setFullname(dto.getFullname());
	        oldUser.setEmail(dto.getEmail());
	    }
		oldUser.setPassword(null);
		
		httpSession.setAttribute(Define.PRINCIPAL, oldUser);
		
		return "redirect:/account/list";
	}

}
