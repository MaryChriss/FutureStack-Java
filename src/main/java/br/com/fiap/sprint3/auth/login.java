package br.com.fiap.sprint3.auth;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class login {

    @GetMapping("/login")
    public String loginPage(){
        return "login";
    }


}