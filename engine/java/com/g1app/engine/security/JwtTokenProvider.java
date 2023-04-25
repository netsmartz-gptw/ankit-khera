package com.g1app.engine.security;


import com.g1app.engine.exceptions.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;


@Component
public class JwtTokenProvider {

    /**
     * THIS IS NOT A SECURE PRACTICE! For simplicity, we are storing a static key here. Ideally, in a
     * microservices environment, this key would be kept on a config-server.
     */
    //@Value("${security.jwt.token.secret-key:secret-key}") @Delete
   // private String secretKey; @Delete

    private String secretKey = "DoctorCallingUser";

    @Value("${security.jwt.token.expire-length:36000000}")
    private long validityInMilliseconds = 3600000; // 1h

    private static final String KEY_SCOPE  = "scope";

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(String username, AuthScope scope) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put(KEY_SCOPE, scope.value);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, secretKey)//
                .compact();
    }



    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean resolveAndValidate(String bearerToken, AuthScope scopeToVerify){
        String token = resolveToken(bearerToken);

        if(token == null || token.isEmpty()){
            return false;
        }

        return validateToken(token, scopeToVerify);
    }

    public String resolveToken(String bearerToken){
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token, AuthScope scopeToVeify) {
        try {
            Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
            String claimScope =  claims.get(KEY_SCOPE).toString();

            if(scopeToVeify.value.equals(claimScope)){
                return true;
            }
            return false;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token ", HttpStatus.BAD_REQUEST);
        }
    }

}
