package com.tenco.bank.repository.entity;

import java.sql.Timestamp;

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
	private Timestamp createdAt;
	private String originFileName;
	private String uploadFileName;
	
	// 기본 생성자 - 컴파일러가 기본으로 메모리에 띄어줌

	// 사용자 회원 가입시, 이미지, 이미지 X
	public String setupUserImage() {
		return uploadFileName == null ?
				"https://picsum.photos/id/1/350" : "/images/upload/" + uploadFileName;
	}
}
