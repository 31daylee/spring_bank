package com.tenco.bank.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignInFormDto;
import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.UnAuthorizedException;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 대상 + 싱글톤으로 관리 됨
public class UserService {

	// DB 접근
	// 의존 주입 DI
	//@Autowired
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	/**
	 * 회원 가입 로직 처리
	 * @param SignUpForDto
	 * return void
	 */
	@Transactional // 트랜잭션 처리 습관
	public void createUser(SignUpFormDto dto) {
		
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.fullname(dto.getFullname())
				.build();
		
		int result = userRepository.insert(user);
		if(result != 1) {
			throw new CustomRestfulException("회원 가입 실패"
					, HttpStatus.INTERNAL_SERVER_ERROR); // 서버 뒷단에서 생기는 에러이기에
		}
		
	}
	
	/**
	 * 로그인 처리
	 * @param SignInFormDto
	 * @return User
	 */
	// select 에도 트랜잭션을 걸어야하는데, 멀티쓰레드 환경에서 다수의 사용자가 접근하는 경우 
	// 발생하는 에러(Phantom Reads) 현상을 방지하기 위해
	public User readUser(SignInFormDto dto) {
		User user = User.builder()
				.username(dto.getUsername())
				.password(dto.getPassword())
				.build();
		User userEntity = userRepository.findByUsernameAndPassword(user);
		
		if(userEntity == null) {
			throw new UnAuthorizedException("인증된 사용자가 아닙니다.", HttpStatus.UNAUTHORIZED);
		}
		return userEntity;
		
	}
	
}