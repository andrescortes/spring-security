package com.tutorial.crud.security.jwt;

import com.tutorial.crud.security.entity.MainUser;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtProvider {

    private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Value("${jwt.secret}")
    private String secretKey;
    @Value("${jwt.expiration}")
    private int expirationTime;

    public String generateToken(Authentication authentication) {
        MainUser mainUser = (MainUser) authentication.getPrincipal();
        return Jwts.builder()
            .setSubject(mainUser.getUsername())
            .setIssuedAt(new Date())
            .setExpiration(new Date(new Date().getTime() + expirationTime * 1000))
            .signWith(SignatureAlgorithm.HS512, secretKey)
            .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser()
            .setSigningKey(secretKey)
            .parseClaimsJws(token)
            .getBody()
            .getSubject();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Token has been create with malformed jwt syntax");
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported jwt token");
        } catch (ExpiredJwtException e) {
            logger.error("Expired jwt token");
        } catch (IllegalArgumentException e) {
            logger.error("Illegal argument exception or empty");
        } catch (SignatureException e) {
            logger.error("Signature exception to jwt token");
        }
        return false;
    }
}
