package io.playdata.security.controller;

import io.playdata.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
//@RequestMapping("/")
public class LoginController {
    @Autowired
    private LoginService loginService;
}
