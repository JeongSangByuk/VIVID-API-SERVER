package com.vivid.apiserver.domain.user.domain;

public class AccountBuilder {

    public static String USER_EMAIL = "test@naver.com";
    public static String USER_NAME = "김철수";
    public static String USER_PASSWORD = "qwer1234";

    public static User build(){
        User user = User.builder()
                .email(USER_EMAIL).name(USER_NAME)
                .password(Password.builder().password(USER_PASSWORD).build())
                .build();

        return user;
    }

    public static User build(String email, String name, String pwd){

        User user = User.builder()
                .email(email).name(name)
                .password(Password.builder().password(pwd).build())
                .build();
        return user;
    }
}