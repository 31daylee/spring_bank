package com.tenco.bank.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tenco.bank.dto.SignUpFormDto;
import com.tenco.bank.handler.exception.CustomRestfulException;
import com.tenco.bank.repository.entity.User;
import com.tenco.bank.repository.interfaces.UserRepository;

@Service // IoC 대상
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
}
