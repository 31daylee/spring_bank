package com.tenco.bank.repository.entity;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;

import org.springframework.http.HttpStatus;

import com.tenco.bank.handler.exception.CustomRestfulException;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

	private Integer id;
	private String username;
	private String password;
	private String fullname;
	private String email;
	private String hp;
	private String originFileName;
	private String uploadFileName;
	private Timestamp createdAt;
	
	// 기본 생성자 - 컴파일러가 기본으로 메모리에 띄어줌

	// 사용자 회원 가입시, 이미지, 이미지 X
	public String setupUserImage() {
	    return (uploadFileName == null) ? 
	    		"/images/default_profile.png" : "/bank/upload/" + uploadFileName;
	}
	
	public void checkUser(Integer principalId) {
		if(this.id != principalId) {
			throw new CustomRestfulException("잘못된 접근입니다", HttpStatus.BAD_REQUEST);
		}
	}
}
