
package com.tenco.bank.dto.oauth.kakao;

import lombok.Data;

@Data
public class KakaoAccount {

    private Boolean profileNicknameNeedsAgreement;
    private Boolean profileImageNeedsAgreement;
    private Profile profile;

}
