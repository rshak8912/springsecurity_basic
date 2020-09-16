package me.study.springsecurity.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @Autowired
    AccountService accountService;

    @GetMapping("/account/{role}/{username}/{password}")
    public Account createAccount(Account account) {
        return accountService.createNew(account);

    }
}
