package com.vivid.apiserver.domain.user.domain;

import lombok.Builder;

import java.io.Serializable;

public class UserSessionDetails implements Serializable {

    private String email;

    @Builder
    public UserSessionDetails(String email, String name,String picture) {
        this.email = email;
    }


}
