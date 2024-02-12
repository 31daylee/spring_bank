package com.tenco.bank.dto.oauth.naver;

import lombok.Data;

@Data
public class NaverResponse {
	private String id;
	private String nickname;
	private String profileImage;
	private String email;
	private String name;
}
