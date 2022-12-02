package com.comtrade.controller;

import com.comtrade.model.user.RegistrationResponse;
import com.comtrade.model.user.UserToRegister;
import com.comtrade.security.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    @Autowired
    MyUserDetailService myUserDetailService;

    @GetMapping ("/login")
    public String login(){
        return "login.html";
    }
    @GetMapping("/register")
    public String register(){
        return "register.html";
    }

    @GetMapping({"","/","/home"})
    public String homePage(){
        return "homePage.html";
    }
    @RequestMapping("/skocko")
    public String SkockoIndex(){
        return "skocko.html";
    }
    @RequestMapping("/slagalica")
    public String showGame() {
        return "slagalica.html";
    }
    @RequestMapping("/MojBroj")
    public String mojBrojIndex(){
        return "mojbroj.html";
    }

    @PostMapping("/NewUser")
    public ResponseEntity<RegistrationResponse> addUser(@RequestBody UserToRegister userToRegister){
        myUserDetailService.addUser(userToRegister.getUserName(),userToRegister.getPassword());
        return ResponseEntity.ok().body(new RegistrationResponse("Uspesno ste se registrovali"));
    }
}
