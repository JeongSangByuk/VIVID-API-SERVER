package com.vivid.apiserver.domain.user.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Institution {

    @Column(name = "zoom_access_token")
    private String zoom_access_token;

    @Column(name = "webex_access_token")
    private String webexAccessToken;

    public void changeWebexAccessToken(String webexAccessToken) {
        this.webexAccessToken = webexAccessToken;
    }
}
