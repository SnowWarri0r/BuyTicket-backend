package com.snowwarrior.huikuan.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public class ResponseEntityHelper {
    private ResponseEntityHelper() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    public static ResponseEntity<HashMap<String, String>> ok(String message) {
        HashMap<String, String> map = new HashMap<>();
        map.put("message", message);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
