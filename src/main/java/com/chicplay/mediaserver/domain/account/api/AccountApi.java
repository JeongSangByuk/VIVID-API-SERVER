package com.chicplay.mediaserver.domain.account.api;

import com.chicplay.mediaserver.domain.account.application.AccountService;
import com.chicplay.mediaserver.domain.account.domain.Account;
import com.chicplay.mediaserver.domain.account.dto.AccountDto;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpRequest;
import com.chicplay.mediaserver.domain.account.dto.AccountSignUpResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AccountApi {

    private final AccountService accountService;

    @PostMapping("/api/account")
    public AccountSignUpResponse signUp(@RequestBody @Valid final AccountSignUpRequest dto){

        Account account = accountService.signUp(dto);

        return new AccountSignUpResponse(account);
    }
}