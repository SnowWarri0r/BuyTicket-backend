package com.snowwarrior.huikuan.controller;

import com.snowwarrior.huikuan.auth.JwtUser;
import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserLoginDTO;
import com.snowwarrior.huikuan.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<UserDTO> login(@RequestBody UserLoginDTO userLogin) {
        JwtUser jwtUser = authService.authLogin(userLogin);

        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.set("Authorization", "Bearer " + jwtUser.getToken());
        return new ResponseEntity<>(jwtUser.getUser(), httpHeaders, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        authService.logout();
        return ResponseEntity.ok().build();
    }
}
