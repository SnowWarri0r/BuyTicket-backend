package com.snowwarrior.huikuan.controller;

import com.snowwarrior.huikuan.dto.ChargeDTO;
import com.snowwarrior.huikuan.dto.UserDTO;
import com.snowwarrior.huikuan.dto.UserRegisterDTO;
import com.snowwarrior.huikuan.service.UserService;
import com.snowwarrior.huikuan.util.ResponseEntityHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public ResponseEntity<HashMap<String, String>> hello() {
        return ResponseEntityHelper.ok(userService.hello());
    }

    @PostMapping("/register")
    public ResponseEntity<HashMap<String, String>> register(@RequestBody @Valid UserRegisterDTO userRegister) {
        userService.register(userRegister);
        return ResponseEntityHelper.ok("注册成功");
    }

    @GetMapping("/profile")
    public ResponseEntity<UserDTO> profile() {
        var result = userService.profile();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/charge")
    public ResponseEntity<HashMap<String, String>> charge(@RequestBody @Valid ChargeDTO charge) {
        userService.charge(charge);
        return ResponseEntityHelper.ok("汇款成功");
    }
}
