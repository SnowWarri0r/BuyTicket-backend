package com.snowwarrior.huikuan.constant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SnowWarrior
 */
@Component
public class SecurityConstants {

    public static String secret;
    @Value("${jwt.secret}")
    public void setSecret(String secret) {
        SecurityConstants.secret = secret;
    }
}
