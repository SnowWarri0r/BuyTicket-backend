package com.snowwarrior.huikuan.controller;

import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import com.snowwarrior.huikuan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String hello() {
        return userService.hello();
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        userService.register(userRegister);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile() {
        var result = userService.profile();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
