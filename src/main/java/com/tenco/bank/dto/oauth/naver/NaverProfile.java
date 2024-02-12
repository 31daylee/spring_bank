package com.tenco.bank.dto.oauth.naver;

import lombok.Data;

@Data
public class NaverProfile {
 	private String resultcode;
    private String message;
    private NaverResponse response;
}
