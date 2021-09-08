package com.snowwarrior.huikuan.util;

import com.snowwarrior.huikuan.constant.SecurityConstants;
import com.snowwarrior.huikuan.constant.UserRoleConstants;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.xml.bind.DatatypeConverter;
import java.io.Serializable;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class JwtUtils implements Serializable {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
    private static final String SECRET = SecurityConstants.secret;

    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private JwtUtils() {
        throw new IllegalStateException("Cannot create instance of static util class");
    }

    public static String generateToken(String username, String role) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SECRET);
        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .signWith(Keys.hmacShaKeyFor(jwtSecretKey), SignatureAlgorithm.HS256)
                .setSubject(username)
                .claim("role", role)
                .setIssuer("snowwarrior")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
                .compact();
    }

    public static boolean validateToken(String token) {
        try {
            getTokenBody(token);
            return true;
        } catch (ExpiredJwtException e) {
            logger.warn("Request to parse expired JWT : {} failed : {}", token, e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.warn("Request to parse unsupported JWT : {} failed : {}", token, e.getMessage());
        } catch (MalformedJwtException e) {
            logger.warn("Request to parse invalid JWT : {} failed : {}", token, e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.warn("Request to parse empty or null JWT : {} failed : {}", token, e.getMessage());
        }
        return false;
    }

    public static Authentication getAuthentication(String token) {
        Claims claims = getTokenBody(token);
        String role = (String) claims.get("role");
        List<SimpleGrantedAuthority> authorities = Objects.isNull(role) ?
                Collections.singletonList(new SimpleGrantedAuthority(UserRoleConstants.ROLE_USER)) :
                Collections.singletonList(new SimpleGrantedAuthority(role));
        String username = claims.getSubject();

        return new UsernamePasswordAuthenticationToken(username, token, authorities);
    }

    private static Claims getTokenBody(String token) {
        byte[] jwtSecretKey = DatatypeConverter.parseBase64Binary(SECRET);
        return Jwts.parserBuilder()
                .setSigningKey(jwtSecretKey)
                .build().parseClaimsJws(token)
                .getBody();
    }
}
