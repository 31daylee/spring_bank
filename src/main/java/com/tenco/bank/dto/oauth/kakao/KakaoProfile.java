
package com.tenco.bank.dto.oauth.kakao;

import lombok.Data;
@Data
public class KakaoProfile {

	private Long id;
	private String connectedAt;
	private Properties properties;
	private KakaoAccount kakaoAccount;

}
