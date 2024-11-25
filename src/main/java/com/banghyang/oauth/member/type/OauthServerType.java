package com.banghyang.oauth.member.type;

import java.util.Locale;

public enum OauthServerType {
    KAKAO,
    ;

    public static OauthServerType fromName(String type) {
        return OauthServerType.valueOf(type.toUpperCase(Locale.ENGLISH));
    }
}
