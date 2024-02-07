
package com.tenco.bank.dto.oauth.google;

import lombok.Data;

@Data
public class GoogleProfile {

    private String id;
    private String name;
    private String givenName;
    private String familyName;
    private String picture;
    private String locale;

}
