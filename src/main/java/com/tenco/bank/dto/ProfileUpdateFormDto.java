package com.tenco.bank.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUpdateFormDto {
	private String fullname;
	private String password;
	private String email;
	private String hp;
	private MultipartFile customFile;
	private String originFileName;
	private String uploadFileName;
}
